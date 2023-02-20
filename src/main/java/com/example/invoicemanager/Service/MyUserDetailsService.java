package com.example.invoicemanager.Service;

import com.example.invoicemanager.Model.MyUserDetails;
import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("hello bello");
        User user = userRepository.getByUserName(username);
        System.out.println(user.getUserName()+" "+user.getPassword());

        if(user == null){
            throw new UsernameNotFoundException(username);
        }

        return new MyUserDetails(user);
    }
}