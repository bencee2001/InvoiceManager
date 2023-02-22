package com.example.invoicemanager.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Role {

    @Id
    @GeneratedValue
    private Integer id;

    @NonNull
    private String name;

    private String description;

    public Role(String name, String description){
        this.name=name;
        this.description=description;
    }
}
