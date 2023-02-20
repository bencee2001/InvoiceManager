package com.example.invoicemanager.Repository;

import com.example.invoicemanager.Model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice,Integer> {
}
