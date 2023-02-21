package com.example.invoicemanager.Service;

import com.example.invoicemanager.Model.Role;
import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.digester.Rule;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void modifyRoles(String userName, Set<Role> newRoles){
        Optional<User> optUser = userRepository.findById(userName);
        User user;
        if(optUser.isEmpty()){
            throw new RuntimeException("no such user");
        }
        user = optUser.get();
        user.setRoles(newRoles);
    }

    public void saveToDatabase(User user){
        userRepository.save(user);
    }
}
