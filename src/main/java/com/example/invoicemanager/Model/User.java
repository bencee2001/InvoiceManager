package com.example.invoicemanager.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity(name="users")
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class User {

    @Id
    @NonNull
    private String userName;  //tanulsag mindig Long ID legyen

    @NonNull
    private String name;

    @NonNull
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    private Bookkeeper bookkeeper;

    private Date lastLogin;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="users_roles",
            joinColumns = @JoinColumn(
                    name = "username", referencedColumnName = "userName"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id" , referencedColumnName = "id"
            ))
    private Set<Role> roles;

    @Column(nullable = true)
    private Integer failedLoginAttempts;

}
