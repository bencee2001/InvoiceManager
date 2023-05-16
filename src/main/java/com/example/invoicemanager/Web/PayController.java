package com.example.invoicemanager.Web;

import com.example.invoicemanager.Model.PaymentType;
import com.example.invoicemanager.Service.PayService;
import com.example.invoicemanager.libs.Error.NoSuchInvoiceException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/pay")
public class PayController {

    private final PayService payService;

    @GetMapping("/select/{invoiceId}")
    public void selectInvoiceToPay(@PathVariable int invoiceId, @RequestParam("type") PaymentType type) throws NoSuchInvoiceException {
        payService.savePayment(invoiceId,type);
    }
}
