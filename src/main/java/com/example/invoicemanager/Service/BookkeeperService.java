package com.example.invoicemanager.Service;

import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Repository.BookkeeperRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookkeeperService {

    private final BookkeeperRepository bookkeeperRepository;

    public Boolean checkIfHaveClients(User user){
        if(checkIfBookkeeper(user))
          return bookkeeperRepository.findClientsByUser(user).size() > 0;
        return false;
    }

    private Boolean checkIfBookkeeper(User user){
        return bookkeeperRepository.findByUser(user).isPresent();
    }

}
