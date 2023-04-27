package com.example.invoicemanager.Service;

import com.example.invoicemanager.Model.Bookkeeper;
import com.example.invoicemanager.Model.Invoice;
import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Model.dto.UserDTO;
import com.example.invoicemanager.Repository.BookkeeperRepository;
import com.example.invoicemanager.libs.Error.NoSuchBookkeeperExcpetion;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class BookkeeperService {

    private final BookkeeperRepository bookkeeperRepository;
    private final InvoiceService invoiceService;

    public Boolean checkIfHaveClients(User user){
        if(checkIfBookkeeper(user))
          return bookkeeperRepository.findClientsByUser(user).size() > 0;
        return false;
    }

    public Bookkeeper getBookkeeperFromUser(User user){
        return bookkeeperRepository.findByUser(user).orElse(null);
    }

    private Boolean checkIfBookkeeper(User user){
        return bookkeeperRepository.findByUser(user).isPresent();
    }


    public Set<UserDTO> getClients(User user) {
        Set<User> clients = bookkeeperRepository.findClientsByUser(user);
        Set<UserDTO> clientsDTO = new HashSet<>();
        clients.forEach(client -> {
            clientsDTO.add(UserDTO.toUserDTO(client));
        });
        return clientsDTO;
    }

    public List<Bookkeeper> getBookkeepers() {
        return bookkeeperRepository.findAll();
    }

    public List<Invoice> getClientInvocies(Bookkeeper bookkeeper){
        List<Invoice> list = new ArrayList<>();
        bookkeeper.getClients().forEach(client->{
            list.addAll(invoiceService.getInvoicesByUser(client));
        });
        return  list;
    }

}
