package com.example.invoicemanager.Service;

import com.example.invoicemanager.Model.Bookkeeper;
import com.example.invoicemanager.Model.Invoice;
import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Model.dto.InvoiceDTO;
import com.example.invoicemanager.Model.dto.UserDTO;
import com.example.invoicemanager.Repository.BookkeeperRepository;
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

    private Boolean checkIfBookkeeper(User user){
        return bookkeeperRepository.findByUser(user).isPresent();
    }

    public Bookkeeper getBookkeeperIdentity(User user){
        return bookkeeperRepository.findByUser(user).orElse(null);
    }


    public Set<UserDTO> getClients(User user) {
        Set<User> clients = bookkeeperRepository.findClientsByUser(user);
        clients.add(user);
        return (Set<UserDTO>) UserDTO.toUserDTOCollection(clients, new HashSet<>());
    }

    public List<Bookkeeper> getBookkeepers() {
        return bookkeeperRepository.findAll();
    }

    public List<InvoiceDTO> getAllClientInvociesByUser(User user){
        Bookkeeper bookkeeper = getBookkeeperIdentity(user);
        if(bookkeeper==null)
            return null;
        List<Invoice> list = new ArrayList<>();
        bookkeeper.getClients().forEach(client->{
            list.addAll(invoiceService.getInvoicesByUser(client));
        });
        return InvoiceDTO.toInvoiceDTOList(list);
    }

}
