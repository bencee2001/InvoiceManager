package com.example.invoicemanager.Web;

import com.example.invoicemanager.Model.invoiceDTO;
import com.example.invoicemanager.Service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/list")
public class ListController {

    private final InvoiceService invoiceService;

    @GetMapping
    public String getList(Model model){
        model.addAttribute("invoices",invoiceService.getInvoices());
        return "list";
    }

    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable("id") int id){
        invoiceService.deleteInvoice(id);
        return "redirect:/list";
    }

    @GetMapping("/select/{id}")
    public String selectInvoice(@PathVariable("id") int id,Model model){
        model.addAttribute("invoice",invoiceService.getInvoiceById(id));
        return "invoice";
    }

    @GetMapping("/create")
    public String toCreateInvoice(Model model){
        model.addAttribute("invoice",new invoiceDTO());
        return "create";
    }

    @PostMapping("/create/new")
    public String createInvoice(@ModelAttribute invoiceDTO mInvoice) throws ParseException {
        invoiceService.saveToDatabase(mInvoice.toInvoice());
        return "redirect:/list";
    }
}
