package com.example.invoicemanager.Service;

import com.example.invoicemanager.Model.Bookkeeper;
import com.example.invoicemanager.Model.Invoice;
import com.example.invoicemanager.Model.Role;
import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Model.dto.UserCreateDTO;
import com.example.invoicemanager.Model.dto.UserDTO;
import com.example.invoicemanager.Repository.BookkeeperRepository;
import com.example.invoicemanager.Repository.RoleRepository;
import com.example.invoicemanager.Repository.UserRepository;
import com.example.invoicemanager.libs.Error.LogedInUserDeleteException;
import com.example.invoicemanager.libs.Error.NoSelectedRoleException;
import com.example.invoicemanager.libs.Error.NoSuchBookkeeperExcpetion;
import com.example.invoicemanager.libs.Error.NoSuchUserExpection;
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
    private final RoleRepository roleRepository;
    private final BookkeeperRepository bookkeeperRepository;
    private final BookkeeperService bookService;
    private final InvoiceService invoiceService;

    private final PasswordEncoder passwordEncoder;

    private final String BOOKKEEPER_ROLE_ID_STR = "2";
    private final int BOOKKEEPER_ROLE_POS = 1;


    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public List<UserDTO> getUserDTOs(){
        List<User> userList = userRepository.findAll();
        List<UserDTO> dtoList = new ArrayList<>();
        userList.forEach(user -> {
            dtoList.add(UserDTO.toUserDTO(user));
        });
        return dtoList;
    }

    public User getUserByUsername(String username) throws NoSuchUserExpection {
        Optional<User> optUser = userRepository.findById(username);
        if(optUser.isEmpty())
            throw new NoSuchUserExpection("No user on the username of "+username);
        return optUser.get();
    }

    public void saveUser(UserCreateDTO userDTO, List<String> roles){
        Set<Role> roleSet = new HashSet<>();
        roles.forEach(role->{
            roleSet.add(roleRepository.getReferenceById(Integer.valueOf(role)));
        });
        Bookkeeper bookkeeper = bookkeeperRepository.getReferenceById(userDTO.getBookkeeper_Id());
        User user = userDTO.toUserFromDTO(passwordEncoder,bookkeeper,roleSet);
        userRepository.save(user);

        Set<User> userSet = bookkeeper.getClients();
        userSet.add(user);
        bookkeeper.setClients(userSet);
        bookkeeperRepository.save(bookkeeper);
        if(roles.contains(BOOKKEEPER_ROLE_ID_STR))
            bookkeeperRepository.save(new Bookkeeper(user));
    }

    public UserDTO getPrincipalUserDTO(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        User user = userRepository.findById(username).get();
        return UserDTO.toUserDTO(user);
    }

    public User getPrincipalUser(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        User user = userRepository.findById(username).get();
        return user;
    }

    public void deleteByUsername(User user) throws LogedInUserDeleteException, NoSuchUserExpection {
        checkIfUserLogedInUser(user.getUserName());
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

    private static void checkIfUserLogedInUser(String username) throws LogedInUserDeleteException {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal.getUsername().equals(username)){
            throw new LogedInUserDeleteException("Don't delete yourself.");
        }
    }

    public void saveNewRoles(List<String> newRoleIds,User user) throws NoSelectedRoleException, NoSuchBookkeeperExcpetion {
        if(newRoleIds.size()==0){
            throw new NoSelectedRoleException("At least one role needed.");
        }
        Set<Role> newRolesForUser = new HashSet<>();
        List<Role> roleList = roleRepository.findAll();
        if(user.getRoles().contains(roleList.get(BOOKKEEPER_ROLE_POS)) &&
                !(newRoleIds.contains(BOOKKEEPER_ROLE_ID_STR))){
            Bookkeeper book = bookService.getBookkeeperFromUser(user);
            bookkeeperRepository.delete(book);
        }
        if(!(user.getRoles().contains(roleList.get(BOOKKEEPER_ROLE_POS))) &&
                (newRoleIds.contains(BOOKKEEPER_ROLE_ID_STR))){
            bookkeeperRepository.save(new Bookkeeper(user));
        }
        roleList.stream().filter(role -> newRoleIds.contains(String.valueOf(role.getId())))
                .toList().forEach(role -> newRolesForUser.add(role));
        setSecurityContext(roleList, newRoleIds,user.getUserName());
        user.setRoles(newRolesForUser);
        userRepository.save(user);
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

    public int getNewCount() {
        User user = getPrincipalUser();
        List<Invoice> invoices = invoiceService.getInvoicesByUser(user);
        int cnt=0;
        for (Invoice invoice : invoices) {
            if (invoice.getIsNew())
                cnt++;
        }
        return cnt;
    }
}
