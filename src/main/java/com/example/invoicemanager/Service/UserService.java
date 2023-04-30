package com.example.invoicemanager.Service;

import com.example.invoicemanager.Model.Bookkeeper;
import com.example.invoicemanager.Model.Invoice;
import com.example.invoicemanager.Model.Role;
import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Model.dto.UserCreateDTO;
import com.example.invoicemanager.Model.dto.UserDTO;
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


    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public List<UserDTO> getUserDTOs(){
        List<User> userList = getUsers();
        return (List<UserDTO>) UserDTO.toUserDTOCollection(userList,new ArrayList<>());
    }

    public User getUserByUsername(String username) throws NoSuchUserExpection {
        Optional<User> optUser = userRepository.findById(username);
        if(optUser.isEmpty())
            throw new NoSuchUserExpection("No user on the username of "+username);
        return optUser.get();
    }

    public void saveUser(UserCreateDTO userDTO, List<String> roles) throws UserAlreadyExistsException {
        if(checkIfUserExistsByUsername(userDTO.getUserName()))
            throw new UserAlreadyExistsException("User already exist.");
        Bookkeeper bookkeeper = bookkeeperRepository.getReferenceById(userDTO.getBookkeeper_Id());
        User user = userDTO.toUserFromDTO(passwordEncoder,bookkeeper,roleService.makeRoleSet(roles));
        userRepository.save(user);
        saveBookkeeper(roles, bookkeeper, user);
    }

    public UserDTO getPrincipalUserDTO(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getReferenceById(principal.getUsername());
        return UserDTO.toUserDTO(user);
    }

    public User getPrincipalUser(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.getReferenceById(principal.getUsername());
    }

    public void deleteByUsername(User user) throws LogedInUserDeleteException, BookkeeperHasClientsExpection {
        if(bookService.checkIfHaveClients(user))
            throw new BookkeeperHasClientsExpection("Bookkeeper "+ user.getUserName()+" has clients.");
        checkIfUserLogedInUser(user.getUserName());
        deleteUser(user);
    }

    public void saveNewRoles(List<String> newRoleIds,User user) throws NoSelectedRoleException, NoSuchBookkeeperExcpetion {
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

    private static Set<Role> getNewRolesForUser(List<String> newRoleIds, List<Role> roleList) {
        Set<Role> newRolesForUser = new HashSet<>();
        roleList.stream().filter(role -> newRoleIds.contains(String.valueOf(role.getId())))
                .toList().forEach(role -> newRolesForUser.add(role));
        return newRolesForUser;
    }

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

    private void deleteUser(User user) {
        if(user.getBookkeeper()==null) {
            userRepository.delete(user);
        }else{
            cleanUser(user);
        }
    }

    private void cleanUser(User user) {
        Bookkeeper bookkeeper = user.getBookkeeper();
        Set<User> userSet = bookkeeper.getClients();
        userSet.remove(user);
        bookkeeper.setClients(userSet);
        bookkeeperRepository.save(bookkeeper);
        user.setBookkeeper(null);
        userRepository.delete(user);
    }

    private void saveBookkeeper(List<String> roles, Bookkeeper bookkeeper, User user) {
        Set<User> userSet = bookkeeper.getClients();
        userSet.add(user);
        bookkeeper.setClients(userSet);
        bookkeeperRepository.save(bookkeeper);
        if(roles.contains(BOOKKEEPER_ROLE_ID_STR))
            bookkeeperRepository.save(new Bookkeeper(user));
    }

    private static void checkIfUserLogedInUser(String username) throws LogedInUserDeleteException {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal.getUsername().equals(username)){
            throw new LogedInUserDeleteException("Don't delete yourself.");
        }
    }

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
