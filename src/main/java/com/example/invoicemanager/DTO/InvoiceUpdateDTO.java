package com.example.invoicemanager.DTO;

import com.example.invoicemanager.Model.Invoice;
import com.example.invoicemanager.Model.Payment;
import com.example.invoicemanager.Repository.PaymentRepository;
import com.example.invoicemanager.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceUpdateDTO {
    private Integer id;
    private String userName;
    private String buyerName;
    private String issueDate;
    private String dueDate;
    private String itemName;
    private Integer itemNumber;
    private String comment;
    private BigDecimal price;
    private Boolean isNew;
    private Integer payment;

    public Invoice toInvoice(UserRepository userRepository, PaymentRepository paymentRepository) throws ParseException {

        Date iDate = new SimpleDateFormat("yyyy-MM-dd").parse(issueDate);
        Date dDate = new SimpleDateFormat("yyyy-MM-dd").parse(dueDate);

        return Invoice.builder()
                .id(id)
                .user(userRepository.getReferenceById(userName))
                .issueDate(iDate)
                .dueDate(dDate)
                .itemName(itemName)
                .itemNumber(itemNumber)
                .comment(comment)
                .price(price)
                .isNew(true)
                .payment(getPayment(paymentRepository))
                .build();
    }

    private Payment getPayment(PaymentRepository paymentRepository){
        if(this.payment==null){
            return null;
        }
        return paymentRepository.findById(this.payment).orElse(null);
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
