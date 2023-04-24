package com.example.invoicemanager.Model.dto;

import com.example.invoicemanager.Model.Role;
import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Repository.BookkeeperRepository;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Builder
public class UserDTO {
    private String userName;
    private String name;
    private String password;
    private Long bookkeeper_Id;
    private Date lastLogin;
    private Set<Role> roles;
    private Integer failedLoginAttempts;

    public static UserDTO toUserDTO(User user){
        return UserDTO.builder()
                .userName(user.getUserName())
                .name(user.getName())
                .password(user.getPassword())
                .bookkeeper_Id(
                        user.getBookkeeper()==null ? null : user.getBookkeeper().getId()
                )
                .lastLogin(user.getLastLogin())
                .roles(user.getRoles())
                .failedLoginAttempts(user.getFailedLoginAttempts())
                .build();
    }

    public User toUser(BookkeeperRepository bookkeeperRepository){
        return User.builder()
                .userName(this.getUserName())
                .name(this.getName())
                .password(this.getPassword())
                .bookkeeper(bookkeeperRepository.getReferenceById(this.getBookkeeper_Id()))
                .lastLogin(this.getLastLogin())
                .roles(this.getRoles())
                .failedLoginAttempts(this.getFailedLoginAttempts())
                .build();
    }
}
