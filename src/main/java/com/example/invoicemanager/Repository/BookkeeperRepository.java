package com.example.invoicemanager.Repository;

import com.example.invoicemanager.Model.Bookkeeper;
import com.example.invoicemanager.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;


public interface BookkeeperRepository extends JpaRepository<Bookkeeper, Long> {

    @Query(
            "select bc.clients "+
            "from Bookkeeper bc " +
                    "where bc.user=:username"
    )
    public Set<User> findClientsByUser(@Param("username") User user);

    public Optional<Bookkeeper> findByUser(User user);
}
