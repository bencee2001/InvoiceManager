package com.example.invoicemanager.DTO;

/*
thymeleaf nem tudja kiirni Stringből a date-et,
viszont date objectumot nem tud kivinni csak stringet
*/

import com.example.invoicemanager.Model.Invoice;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class InvoiceDTO {
    private Integer id;
    private String userName;
    private String buyerName;
    private Date issueDate;
    private Date dueDate;
    private String itemName;
    private String comment;
    private BigDecimal price;
    private Boolean isNew;

    public static InvoiceDTO toInvoiceDTO(Invoice invoice){
        return InvoiceDTO.builder()
                .id(invoice.getId())
                .issueDate(invoice.getIssueDate())
                .dueDate(invoice.getDueDate())
                .comment(invoice.getComment())
                .itemName(invoice.getItemName())
                .price(invoice.getPrice())
                .userName(invoice.getUser().getUserName())
                .buyerName(invoice.getUser().getName())
                .isNew(invoice.getIsNew())
                .build();
    }

    public static List<InvoiceDTO> toInvoiceDTOList(List<Invoice> invoices){
        List<InvoiceDTO> list=new ArrayList<>();
        invoices.forEach(invoice -> {
            list.add(toInvoiceDTO(invoice));
        });
        return list;
    }
}
