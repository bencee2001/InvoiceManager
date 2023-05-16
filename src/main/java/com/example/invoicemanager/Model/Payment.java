package com.example.invoicemanager.Model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private PaymentType type;

    @NonNull
    private Date paydey;

    @NonNull
    private BigDecimal cost;

    @OneToOne(mappedBy = "payment")
    private Invoice invoice;
}
