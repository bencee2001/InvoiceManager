package com.example.invoicemanager.Service;

import com.example.invoicemanager.Model.Role;
import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Repository.RoleRepository;
import com.example.invoicemanager.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.digester.Rule;
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
    private final PasswordEncoder passwordEncoder;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void saveUser(User user, List<String> roles){
        Set<Role> roleSet = new HashSet<>();
        roles.forEach(role->{
            roleSet.add(roleRepository.getReferenceById(Integer.valueOf(role)));
        });
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleSet);
        user.setFailedLoginAttempts(0);
        userRepository.save(user);
    }

    public User getPrincipalUser(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        User user = userRepository.findById(username).get();
        return user;
    }

    public void deleteByUsername(String username){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal.getUsername().equals(username)){
            throw new RuntimeException("Don't delete yourself.");
        }
        userRepository.deleteById(username);
    }

    public void saveNewRoles(List<String> newRoleIds,String username){
        if(newRoleIds.size()==0){
            throw new RuntimeException("At least one role needed.");
        }
        Set<Role> newRolesForUser = new HashSet<>();
        List<Role> roleList = roleRepository.findAll();
        roleList.stream().filter(role -> newRoleIds.contains(String.valueOf(role.getId())))
                .toList().forEach(role -> newRolesForUser.add(role));
        setSecurityContext(roleList, newRoleIds,username);
        User user = userRepository.findById(username).get();
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
}
