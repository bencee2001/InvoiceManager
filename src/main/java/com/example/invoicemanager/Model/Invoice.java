package com.example.invoicemanager.Model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity(name="invoices")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Invoice {

    @Id
    @NonNull
    private Integer id;

    @NonNull
    private String buyerName;

    @NonNull
    private Date issueDate;

    @NonNull
    private Date dueDate;

    @NonNull
    private String itemName;

    @NonNull
    private String comment;

    @NonNull
    private BigDecimal price;
}
