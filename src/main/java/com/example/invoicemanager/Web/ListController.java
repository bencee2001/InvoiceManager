package com.example.invoicemanager.Web;

import com.example.invoicemanager.Model.dto.InvoiceCreateDTO;
import com.example.invoicemanager.Model.dto.InvoiceUpdateDTO;
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
        model.addAttribute("invoice",new InvoiceCreateDTO());
        return "create";
    }

    @PostMapping("/create/new")
    public String createInvoice(@ModelAttribute InvoiceCreateDTO invoiceCreateDTO) throws ParseException {
        invoiceService.saveToDatabase(invoiceCreateDTO.toInvoice());
        return "redirect:/list";
    }

    @PostMapping("/update")
    public String updateInvoice(@ModelAttribute InvoiceUpdateDTO invoiceUpdateDTO) throws ParseException {
        System.out.println(invoiceUpdateDTO.getId());
        invoiceService.saveToDatabase(invoiceUpdateDTO.toInvoice());
        return "redirect:/list";
    }
}
