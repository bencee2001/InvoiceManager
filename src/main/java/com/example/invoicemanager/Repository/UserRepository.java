package com.example.invoicemanager.Repository;

import com.example.invoicemanager.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {

    User getByUserName(String username);

}
