package com.example.invoicemanager.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Bookkeeper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="username")
    @NonNull
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookkeeper")
    private Set<User> clients;
}
