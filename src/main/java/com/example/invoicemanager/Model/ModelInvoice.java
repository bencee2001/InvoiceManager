package com.example.invoicemanager.Model;

import lombok.Data;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class ModelInvoice {
    private String buyerName;
    private String issueDate;
    private String dueDate;
    private String itemName;
    private String comment;
    private BigDecimal price;

    public Invoice toInvoice() throws ParseException {

        Date iDate = new SimpleDateFormat("yyyy-MM-dd").parse(issueDate);
        Date dDate = new SimpleDateFormat("yyyy-MM-dd").parse(dueDate);
        return Invoice.builder()
                .buyerName(buyerName)
                .issueDate(iDate)
                .dueDate(dDate)
                .itemName(itemName)
                .comment(comment)
                .price(price)
                .build();
    }
}
