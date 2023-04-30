package com.example.invoicemanager.Model.dto;

import com.example.invoicemanager.Model.Invoice;
import com.example.invoicemanager.Repository.UserRepository;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Builder
public class InvoiceUpdateDTO {
    private Integer id;
    private String userName;
    private String buyerName;
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
                .id(id)
                .user(userRepository.getReferenceById(userName))
                .issueDate(iDate)
                .dueDate(dDate)
                .itemName(itemName)
                .comment(comment)
                .price(price)
                .isNew(true)
                .build();
    }

    /*public static InvoiceUpdateDTO toInvocieUpdateDTO(Invoice invoice){
        return InvoiceUpdateDTO.builder()
                .id(invoice.getId())
                .issueDate(invoice.getIssueDate().toString())
                .dueDate(invoice.getDueDate().toString())
                .comment(invoice.getComment())
                .itemName(invoice.getItemName())
                .price(invoice.getPrice())
                .userName(invoice.getUser().getUserName())
                .buyerName(invoice.getUser().getName())
                .isNew(invoice.getIsNew())
                .build();
    }*/
}
