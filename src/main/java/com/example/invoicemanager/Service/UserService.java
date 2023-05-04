package com.example.invoicemanager.Service;

import com.example.invoicemanager.Model.Bookkeeper;
import com.example.invoicemanager.Model.Role;
import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.DTO.UserCreateDTO;
import com.example.invoicemanager.DTO.UserDTO;
import com.example.invoicemanager.Repository.BookkeeperRepository;
import com.example.invoicemanager.Repository.UserRepository;
import com.example.invoicemanager.libs.Error.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BookkeeperRepository bookkeeperRepository;
    private final RoleService roleService;
    private final BookkeeperService bookService;

    private final PasswordEncoder passwordEncoder;

    private final String BOOKKEEPER_ROLE_ID_STR = "2";
    private final int BOOKKEEPER_ROLE_POS = 1;


    /**
     * Get all User from the database
     * @return list of users
     */
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    /**
     * Get all Users from the database in UserDTO object
     * @return list of userDTO objects
     */
    public List<UserDTO> getUserDTOs(){
        List<User> userList = getUsers();
        return (List<UserDTO>) UserDTO.toUserDTOCollection(userList,new ArrayList<>());
    }

    /**
     * Get the User object by its UserName(Id)
     * @param username the UserName
     * @return the User
     * @throws NoSuchUserException if there is no User by the UserName
     */
    public User getUserByUsername(String username) throws NoSuchUserException {
        Optional<User> optUser = userRepository.findById(username);
        if(optUser.isEmpty())
            throw new NoSuchUserException("No user on the username of "+username);
        return optUser.get();
    }

    /**
     * From the DTO and the roles it's create the User object and save it to the database
     * it's ensured the bookkeeper objects are updated and saved too.
     * @param userDTO required User values
     * @param roles the rights of the new user
     * @throws UserAlreadyExistsException if the user is already exists
     */
    public void saveUser(UserCreateDTO userDTO, List<String> roles) throws UserAlreadyExistsException {
        if(checkIfUserExistsByUsername(userDTO.getUserName()))
            throw new UserAlreadyExistsException("User already exist.");
        Bookkeeper bookkeeper = bookkeeperRepository.getReferenceById(userDTO.getBookkeeper_Id());
        User user = userDTO.toUserFromDTO(passwordEncoder,bookkeeper,roleService.makeRoleSet(roles));
        userRepository.save(user);
        saveBookkeeper(roles, bookkeeper, user);
    }

    /**
     * Get the authenticated User object in UserDTO
     * @return The User in UserDTO
     */
    public UserDTO getPrincipalUserDTO(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getReferenceById(principal.getUsername());
        return UserDTO.toUserDTO(user);
    }

    /**
     * Get the authenticated User object
     * @return The User
     */
    public User getPrincipalUser(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.getReferenceById(principal.getUsername());
    }

    /**
     * Delete the User from the database.
     * @param user The User
     * @throws LogedInUserDeleteException if try to delete authenticated User (checked by {}
     * @throws BookkeeperHasClientsException if the User is a Bookkeeper and has clients, it can't be deleted
     */
    public void deleteByUsername(User user) throws LogedInUserDeleteException, BookkeeperHasClientsException {
        if(bookService.checkIfHaveClients(user))
            throw new BookkeeperHasClientsException("Bookkeeper "+ user.getUserName()+" has clients.");
        checkIfUserLoggedInUser(user.getUserName());
        deleteUser(user);
    }

    /**
     * Set the new Roles for the User
     * @param newRoleIds list of strings with the Roles Ids
     * @param user The User
     * @throws NoSelectedRoleException if the newRoleIds is empty
     * @throws NoSuchBookkeeperException if User not a Bookkeeper
     */
    public void saveNewRoles(List<String> newRoleIds,User user) throws NoSelectedRoleException, NoSuchBookkeeperException {
        if(newRoleIds.size()==0){
            throw new NoSelectedRoleException("At least one role needed.");
        }
        if(bookService.checkIfHaveClients(user)) {
            newRoleIds.add(BOOKKEEPER_ROLE_ID_STR);
        }
        List<Role> roleList = roleService.getRoles();
        setBookkeeperRole(newRoleIds, user, roleList);
        setUserNewRoles(newRoleIds, user, roleList);
    }

    private void setUserNewRoles(List<String> newRoleIds, User user, List<Role> roleList) {
        Set<Role> newRolesForUser = getNewRolesForUser(newRoleIds, roleList);
        setSecurityContext(roleList, newRoleIds, user.getUserName());
        user.setRoles(newRolesForUser);
        userRepository.save(user);
    }

    /**
     * Convert the list of string to set of Roles
     * @param newRoleIds list of strings with the Roles Ids
     * @param roleList every role
     * @return set of roles
     */
    private static Set<Role> getNewRolesForUser(List<String> newRoleIds, List<Role> roleList) {
        Set<Role> newRolesForUser = new HashSet<>();
        roleList.stream().filter(role -> newRoleIds.contains(String.valueOf(role.getId())))
                .toList().forEach(role -> newRolesForUser.add(role));
        return newRolesForUser;
    }

    /**
     * delete User from bookkeeper if the book role taken away
     * @param newRoleIds list of strings with the Roles Ids
     * @param user The User
     * @param roleList list of new Roles
     */
    private void setBookkeeperRole(List<String> newRoleIds, User user, List<Role> roleList) {
        if(user.getRoles().contains(roleList.get(BOOKKEEPER_ROLE_POS)) &&
                !(newRoleIds.contains(BOOKKEEPER_ROLE_ID_STR))){
            Bookkeeper book = bookService.getBookkeeperIdentity(user);
            bookkeeperRepository.delete(book);
        }else if(!(user.getRoles().contains(roleList.get(BOOKKEEPER_ROLE_POS))) &&
                  (newRoleIds.contains(BOOKKEEPER_ROLE_ID_STR))){
                    bookkeeperRepository.save(new Bookkeeper(user));
        }
    }

    /**
     * check if User have bookkeeper and then delete it
     * @param user The User
     */
    private void deleteUser(User user) {
        if(user.getBookkeeper()==null) {
            userRepository.delete(user);
        }else{
            cleanUser(user);
        }
    }

    /**
     * delete User's Bookkeeper agenda and the User it's self
     * @param user the User
     */
    private void cleanUser(User user) {
        Optional<Bookkeeper> bookkeeper = bookkeeperRepository.findByUser(user);
        bookkeeper.ifPresent(bookkeeperRepository::delete);
        userRepository.delete(user);
    }

    /**
     * If the User has "book" role then they will also be saved as a bookkeeper.
     * @param roles User's roles
     * @param bookkeeper The Bookkeeper
     * @param user The User
     */
    private void saveBookkeeper(List<String> roles, Bookkeeper bookkeeper, User user) {
        if(roles.contains(BOOKKEEPER_ROLE_ID_STR))
            bookkeeperRepository.save(new Bookkeeper(user));
    }

    private static void checkIfUserLoggedInUser(String username) throws LogedInUserDeleteException {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal.getUsername().equals(username)){
            throw new LogedInUserDeleteException("Don't delete yourself.");
        }
    }

    /**
     * update the User roles in the SecurityContext
     * @param roleList every role
     * @param newRoleIds list of strings with the Roles Ids
     * @param username the User username
     */
    private void setSecurityContext(List<Role> roleList,List<String> newRoleIds,String username){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails= (UserDetails) auth.getPrincipal();
        if(userDetails.getUsername().equals(username)) {
            List<GrantedAuthority> updatedAuthorities = new ArrayList<>();
            roleList.stream().filter(role -> newRoleIds.contains(String.valueOf(role.getId())))
                    .toList().forEach(role -> updatedAuthorities.add(new SimpleGrantedAuthority(role.getName())));
            Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
    }

    private Boolean checkIfUserExistsByUsername(String username){
        List<User> users=getUsers();
        users = users.stream().filter(userfromlist -> {
            return userfromlist.getUserName().equals(username);
        }).toList();
        return users.size() != 0;
    }
}
