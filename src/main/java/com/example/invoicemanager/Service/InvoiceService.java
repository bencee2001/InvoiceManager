package com.example.invoicemanager.Service;

import com.example.invoicemanager.Model.Invoice;
import com.example.invoicemanager.Repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public List<Invoice> getInvoices(){
        return invoiceRepository.findAll();
    }

    public void createInvoice(String buyer, Date dueDate, String itemName, String comment, BigDecimal price){
        invoiceRepository.save(Invoice.builder()
                .buyerName(buyer)
                .issueDate(new Date())
                .dueDate(dueDate)
                .itemName(itemName)
                .comment(comment)
                .price(price).build());
    }

    public void saveToDatabase(Invoice invoice){
        invoiceRepository.save(invoice);
    }

    public void setComment(Integer id, String comment){
        Invoice inv = invoiceRepository.getReferenceById(id);
        inv.setComment(comment);
        invoiceRepository.save(inv);
    }

    public int getNumOfInvociesInDB(){
        return invoiceRepository.findAll().size();
    }

    public void deleteInvoice(Integer id){
        invoiceRepository.deleteById(id);
    }

    public Optional<Invoice> getInvoiceById(int id) {
        return invoiceRepository.findById(id);
    }
}
