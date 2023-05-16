package com.example.invoicemanager.Service;

import com.example.invoicemanager.Model.Invoice;
import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Repository.InvoiceRepository;
import com.example.invoicemanager.libs.Error.CantBeChangedException;
import com.example.invoicemanager.libs.Error.NoSuchInvoiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    /**
     * Save the Invoice to the database
     * @param invoice the invoice
     */
    public void save(Invoice invoice){
        invoiceRepository.save(invoice);
    }

    /**
     * Delete the Invoice by Id
     * @param id the id of the invoice
     * @throws NoSuchInvoiceException if the id is not identify an invoice
     */
    public void deleteInvoice(Integer id) throws NoSuchInvoiceException {
        invoiceRepository.delete(getInvoiceById(id));
    }

    /**
     * Get the Invoice by Id
     * @param id the id of the invoice
     * @return the invoice that get identified by the id
     * @throws NoSuchInvoiceException if the id is not identify an invoice
     */
    public Invoice getInvoiceById(int id) throws NoSuchInvoiceException {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if(invoice.isEmpty()) {
            throw new NoSuchInvoiceException("Id not found: "+id);
        }
        return invoice.get();
    }

    /**
     * Get the User's invoices
     * @param user the User
     * @return list of invoices
     */
    public List<Invoice> getInvoicesByUser(User user) {
        return invoiceRepository.findAllByUser(user);
    }

    /**
     * Count the Invoices of a User, that are not yet seen(invoice.isNew=true) by the User
     * @param user the User
     * @return the number of invoices, that the User not yet seen
     */
    public int getNewInvoiceCountByUser(User user) {
        List<Invoice> invoices = getInvoicesByUser(user);
        int newCnt=0;
        for (Invoice invoice : invoices) {
            if (invoice.getIsNew())
                newCnt++;
        }
        return newCnt;
    }

    /**
     * With this method if the User open the Invoice it's status will change to be open
     * @param id the Invoice Id
     * @param user the User who wnat to open the Invoice
     * @throws NoSuchInvoiceException if the id is not identify an invoice
     */
    public void openInvoice(Integer id, User user) throws NoSuchInvoiceException {
        Invoice invoice = getInvoiceById(id);
        if(user.getUserName().equals(invoice.getUser().getUserName())) {
            invoice.setIsNew(false);
            save(invoice);
        }
    }

    public void update(Invoice invoice) throws CantBeChangedException {
        if(invoice.getPayment()==null){
            invoiceRepository.save(invoice);
        }
        throw new CantBeChangedException("If the invacie payed, it can't be changed.");
    }
}
