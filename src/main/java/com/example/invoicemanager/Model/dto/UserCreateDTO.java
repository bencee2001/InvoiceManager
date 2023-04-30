package com.example.invoicemanager.Model.dto;

import com.example.invoicemanager.Model.Bookkeeper;
import com.example.invoicemanager.Model.Role;
import com.example.invoicemanager.Model.User;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {
    private String userName;
    private String name;
    private String password;
    private Long bookkeeper_Id;

    public User toUserFromDTO(PasswordEncoder passwordEncoder, Bookkeeper bookkeeper, Set<Role> roleSet){
       return User.builder()
               .userName(this.userName)
               .name(this.name)
               .password(passwordEncoder.encode(this.password))
               .bookkeeper(bookkeeper)
               .lastLogin(null)
               .roles(roleSet)
               .failedLoginAttempts(0)
               .build();
    }
}
