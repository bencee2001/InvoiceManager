package com.example.invoicemanager.DTO;

import com.example.invoicemanager.Model.Invoice;
import com.example.invoicemanager.Model.Payment;
import com.example.invoicemanager.Model.PaymentType;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private Integer id;
    private PaymentType type;
    private Date paydey;
    private BigDecimal cost;

    public static PaymentDTO toPaymentDTO(Payment payment){
        return PaymentDTO.builder()
                .id(payment.getId())
                .type(payment.getType())
                .cost(payment.getCost())
                .paydey(payment.getPaydey())
                .build();
    }

}
