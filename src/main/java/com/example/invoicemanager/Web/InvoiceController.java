package com.example.invoicemanager.Web;

import com.example.invoicemanager.DTO.InvoiceCreateDTO;
import com.example.invoicemanager.DTO.InvoiceDTO;
import com.example.invoicemanager.DTO.InvoiceUpdateDTO;
import com.example.invoicemanager.Repository.PaymentRepository;
import com.example.invoicemanager.Repository.UserRepository;
import com.example.invoicemanager.Service.InvoiceService;
import com.example.invoicemanager.Service.UserService;
import com.example.invoicemanager.libs.Error.CantBeChangedException;
import com.example.invoicemanager.libs.Error.NoSuchInvoiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;


    @PostMapping("/create/new")
    public String createInvoice(@ModelAttribute InvoiceCreateDTO invoiceCreateDTO) throws ParseException {
        invoiceService.save(invoiceCreateDTO.toInvoice(userRepository));
        return "redirect:/list";
    }

    @PostMapping("/update")
    public String updateInvoice(@ModelAttribute InvoiceUpdateDTO invoiceUpdateDTO) throws ParseException, CantBeChangedException {
        invoiceService.update(invoiceUpdateDTO.toInvoice(userRepository, paymentRepository));
        return "redirect:/list";
    }

    @GetMapping("/select/{id}")
    public String selectInvoice(@PathVariable("id") int id,Model model) throws NoSuchInvoiceException {
        invoiceService.openInvoice(id, userService.getPrincipalUser());
        model.addAttribute("invoice",
                InvoiceDTO.toInvoiceDTO(invoiceService.getInvoiceById(id)));
        model.addAttribute("updateInvoice",new InvoiceUpdateDTO());
        return "invoice";
    }

    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable("id") int id) throws NoSuchInvoiceException {
        invoiceService.deleteInvoice(id);
        return "redirect:/list";
    }



}
