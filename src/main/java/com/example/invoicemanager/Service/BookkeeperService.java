package com.example.invoicemanager.Service;

import com.example.invoicemanager.Model.Bookkeeper;
import com.example.invoicemanager.Model.Invoice;
import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.DTO.InvoiceDTO;
import com.example.invoicemanager.DTO.UserDTO;
import com.example.invoicemanager.Repository.BookkeeperRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class BookkeeperService {

    private final BookkeeperRepository bookkeeperRepository;
    private final InvoiceService invoiceService;

    /**
     * First call {@link #checkIfBookkeeper(User)},
     * then if true, check user for clients
     * @param user the User we check for clients
     * @return if user is a bookkeeper and have clients true, otherwise false
     */
    public Boolean checkIfHaveClients(User user){
        if(checkIfBookkeeper(user))
          return bookkeeperRepository.findClientsByUser(user).size() > 0;
        return false;
    }

    /**
     * Check if User is a Bookkeper
     * @param user the User we check
     * @return if user is a Bookkeeper true, otherwise false
     */
    private Boolean checkIfBookkeeper(User user){
        return bookkeeperRepository.findByUser(user).isPresent();
    }

    /**
     * Get the Bookkeeper Object by the User object
     * @param user the user, whose bookkeeper object we want
     * @return the Bookkeeper object
     */
    public Bookkeeper getBookkeeperIdentity(User user){
        return bookkeeperRepository.findByUser(user).orElse(null);
    }


    /**
     * Get the Bookkeeper object's clients by the user.
     * Because Bookkeepers can manage their own invoices,
     * the Bookkeeper user object also in the return list
     * @param user the Bookkeeper obejct's User object
     * @return list of clients in UserDTO
     */
    public Set<UserDTO> getClientsByUser(User user) {
        Set<User> clients = bookkeeperRepository.findClientsByUser(user);
        clients.add(user);
        return (Set<UserDTO>) UserDTO.toUserDTOCollection(clients, new HashSet<>());
    }

    /**
     * Get all Bookkeeper in the database
     * @return list of Bookkeeper objects
     */
    public List<Bookkeeper> getBookkeepers() {
        return bookkeeperRepository.findAll();
    }

    /**
     * Get all the client invoices by the Bookkeeper's User Object
     * @param user the Bookkeeper object's User object
     * @return list of the client's invoices
     */
    public List<InvoiceDTO> getAllClientInvoicesByUser(User user){
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
