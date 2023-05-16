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
    private Integer itemNumber;
    private BigDecimal price;
    private Boolean isNew;
    private PaymentDTO payment;

    public static InvoiceDTO toInvoiceDTO(Invoice invoice){
        return InvoiceDTO.builder()
                .id(invoice.getId())
                .issueDate(invoice.getIssueDate())
                .dueDate(invoice.getDueDate())
                .comment(invoice.getComment())
                .itemName(invoice.getItemName())
                .itemNumber(invoice.getItemNumber())
                .price(invoice.getPrice())
                .userName(invoice.getUser().getUserName())
                .buyerName(invoice.getUser().getName())
                .isNew(invoice.getIsNew())
                .payment(invoice.getPayment()!=null ? PaymentDTO.toPaymentDTO(invoice.getPayment()):null)
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
