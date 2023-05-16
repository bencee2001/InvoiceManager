package com.example.invoicemanager.Service;

import com.example.invoicemanager.Model.Invoice;
import com.example.invoicemanager.Model.Payment;
import com.example.invoicemanager.Model.PaymentType;
import com.example.invoicemanager.Repository.PaymentRepository;
import com.example.invoicemanager.libs.Error.NoSuchInvoiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
@AllArgsConstructor
public class PayService {

    private final PaymentRepository paymentRepository;
    private final InvoiceService invoiceService;

    public void savePayment(int invoiceId, PaymentType type) throws NoSuchInvoiceException {
        Invoice invoice = invoiceService.getInvoiceById(invoiceId);
        Payment payment = createPayment(type, invoice);
        invoice.setPayment(payment);
        paymentRepository.save(payment);
        invoiceService.save(invoice);
    }

    private static Payment createPayment(PaymentType type, Invoice invoice) {
        return Payment.builder()
                .paydey(new Date())
                .cost(invoice.getPrice().multiply(BigDecimal.valueOf(invoice.getItemNumber())))
                .type(type)
                .invoice(invoice)
                .build();
    }
}
