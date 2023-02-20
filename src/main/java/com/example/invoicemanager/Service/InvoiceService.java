package com.example.invoicemanager.Service;

import com.example.invoicemanager.Model.Invoice;
import com.example.invoicemanager.Repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class InvoiceService {

    InvoiceRepository invoiceRepository;

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

    public void setComment(Integer id, String comment){
        Invoice inv = invoiceRepository.getReferenceById(id);
        inv.setComment(comment);
        invoiceRepository.save(inv);
    }

    public void deleteInvoice(Integer id){
        invoiceRepository.deleteById(id);
    }
}
