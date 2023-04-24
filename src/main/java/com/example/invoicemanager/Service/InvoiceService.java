package com.example.invoicemanager.Service;

import com.example.invoicemanager.Model.Invoice;
import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public List<Invoice> getInvoices(){
        return invoiceRepository.findAll();
    }

    public void saveToDatabase(Invoice invoice){
        invoiceRepository.save(invoice);
    }

    public void deleteInvoice(Integer id){
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if(invoice.isPresent()) {
            invoiceRepository.deleteById(id);
        }
    }

    public Invoice getInvoiceById(int id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if(invoice.isEmpty()){
            return null;
        }
        return invoice.get();
    }

    public List<Invoice> getInvoicesByUser(User user) {
        return invoiceRepository.findAllByUser(user);
    }
}
