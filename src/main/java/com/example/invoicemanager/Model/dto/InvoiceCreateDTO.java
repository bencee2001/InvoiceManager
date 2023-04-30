package com.example.invoicemanager.Model.dto;

import com.example.invoicemanager.Model.Invoice;
import com.example.invoicemanager.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceCreateDTO {
    private String userName;
    private String issueDate;
    private String dueDate;
    private String itemName;
    private String comment;
    private BigDecimal price;
    private Boolean isNew;

    public Invoice toInvoice(UserRepository userRepository) throws ParseException {

        Date iDate = new SimpleDateFormat("yyyy-MM-dd").parse(issueDate);
        Date dDate = new SimpleDateFormat("yyyy-MM-dd").parse(dueDate);
        return Invoice.builder()
                .user(userRepository.getReferenceById(userName))
                .issueDate(iDate)
                .dueDate(dDate)
                .itemName(itemName)
                .comment(comment)
                .price(price)
                .isNew(true)
                .build();
    }
}
