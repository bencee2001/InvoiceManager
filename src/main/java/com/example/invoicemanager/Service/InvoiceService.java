package com.example.invoicemanager.Service;

import com.example.invoicemanager.Model.Invoice;
import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Model.dto.InvoiceDTO;
import com.example.invoicemanager.Repository.InvoiceRepository;
import com.example.invoicemanager.libs.Error.NoSuchInvoiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public void save(Invoice invoice){
        invoiceRepository.save(invoice);
    }

    public void deleteInvoice(Integer id) throws NoSuchInvoiceException {
        invoiceRepository.delete(getInvoiceById(id));
    }

    public Invoice getInvoiceById(int id) throws NoSuchInvoiceException {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if(invoice.isEmpty()) {
            throw new NoSuchInvoiceException("Id not found: "+id);
        }
        return invoice.get();
    }

    public List<Invoice> getInvoicesByUser(User user) {
        return invoiceRepository.findAllByUser(user);
    }

    public int getNewInvoiceCountByUser(User user) {
        List<Invoice> invoices = getInvoicesByUser(user);
        int newCnt=0;
        for (Invoice invoice : invoices) {
            if (invoice.getIsNew())
                newCnt++;
        }
        return newCnt;
    }

    public void openInvoice(Integer id, User user) throws NoSuchInvoiceException {
        Invoice invoice = getInvoiceById(id);
        if(user.getUserName().equals(invoice.getUser().getUserName())) {
            invoice.setIsNew(false);
            save(invoice);
        }
    }
}
