package com.example.invoicemanager.Web;

import com.example.invoicemanager.Model.Invoice;
import com.example.invoicemanager.Model.ModelInvoice;
import com.example.invoicemanager.Service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/list")
public class ListController {

    private final InvoiceService invoiceService;

    @GetMapping
    public String getList(Model model){
        List<Invoice> invoices = invoiceService.getInvoices();
        model.addAttribute("invoices",invoices);
        return "list";
    }

    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable("id") int id){
        Optional<Invoice> invoice = invoiceService.getInvoiceById(id);
        if(invoice.isPresent()) {
            invoiceService.deleteInvoice(id);
        }
        return "redirect:/list";
    }

    @GetMapping("/select/{id}")
    public String selectInvoice(@PathVariable("id") int id,Model model){
        Optional<Invoice> invoice = invoiceService.getInvoiceById(id);
        if(invoice.isEmpty()) {
            return "redirect:/list";
        }
        Invoice inv = invoice.get();
        model.addAttribute("invoice",inv);
        return "invoice";
    }

    @GetMapping("/create")
    public String toCreateInvoice(Model model){
        model.addAttribute("invoice",new ModelInvoice());
        return "create";
    }

    @PostMapping("/create/new")
    public String createInvoice(@ModelAttribute ModelInvoice mInvoice) throws ParseException {

        System.out.println(mInvoice.getDueDate());
        int id = invoiceService.getNumOfInvociesInDB()+11;

        Invoice invoice = mInvoice.toInvoice();

        invoice.setId(id);

        invoiceService.saveToDatabase(invoice);

        return "redirect:/list";
    }
}
