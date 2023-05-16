package com.example.invoicemanager.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @GeneratedValue
    private Integer id;

    @NonNull
    @ManyToOne
    @JoinColumn(name="user_name")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @NonNull
    private Date issueDate;

    @NonNull
    private Date dueDate;

    @NonNull
    private String itemName;

    @NonNull
    private Integer itemNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="payment_id", referencedColumnName = "id")
    private Payment payment;

    @NonNull
    private String comment;

    @NonNull
    private BigDecimal price;

    private Boolean isNew;
}
