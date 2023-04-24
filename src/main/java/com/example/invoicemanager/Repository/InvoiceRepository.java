package com.example.invoicemanager.Repository;

import com.example.invoicemanager.Model.Invoice;
import com.example.invoicemanager.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice,Integer> {

    List<Invoice>findAllByUser(User user);
}
