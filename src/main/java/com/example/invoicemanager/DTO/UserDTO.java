package com.example.invoicemanager.DTO;

import com.example.invoicemanager.Model.Role;
import com.example.invoicemanager.Model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String userName;
    private String name;
    private Long bookkeeper_Id;
    private String booker_name;
    private Date lastLogin;
    private Set<Role> roles;
    private Integer failedLoginAttempts;

    public static UserDTO toUserDTO(User user){
        return UserDTO.builder()
                .userName(user.getUserName())
                .name(user.getName())
                .bookkeeper_Id(
                        user.getBookkeeper()==null ? null : user.getBookkeeper().getId()
                )
                .booker_name(
                        user.getBookkeeper()==null ? null : user.getBookkeeper().getUser().getName()
                )
                .roles(user.getRoles())
                .lastLogin(user.getLastLogin())
                .failedLoginAttempts(user.getFailedLoginAttempts())
                .build();
    }

    public static Set<UserDTO> toUserDTOSet(Set<User> users){
        Set<UserDTO> usersDTO = new HashSet<>();
        users.forEach(client -> {
            usersDTO.add(UserDTO.toUserDTO(client));
        });
        return usersDTO;
    }

    public static Collection<UserDTO> toUserDTOCollection(Collection<User> users, Collection<UserDTO> userDTOS){
        users.forEach(client -> {
            userDTOS.add(UserDTO.toUserDTO(client));
        });
        return userDTOS;
    }
}
