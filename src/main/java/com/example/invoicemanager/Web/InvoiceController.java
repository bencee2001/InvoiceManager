package com.example.invoicemanager.Web;

import com.example.invoicemanager.Model.Bookkeeper;
import com.example.invoicemanager.Model.Invoice;
import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Model.dto.InvoiceCreateDTO;
import com.example.invoicemanager.Model.dto.InvoiceDTO;
import com.example.invoicemanager.Model.dto.InvoiceUpdateDTO;
import com.example.invoicemanager.Model.dto.UserDTO;
import com.example.invoicemanager.Repository.UserRepository;
import com.example.invoicemanager.Service.BookkeeperService;
import com.example.invoicemanager.Service.InvoiceService;
import com.example.invoicemanager.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/list")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final UserService userService;
    private final BookkeeperService bookService;
    private final UserRepository userRepository;

    @GetMapping
    public String getList(Model model){
        UserDetails userDetails = (UserDetails)SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        User user = userRepository.getReferenceById(userDetails.getUsername());

        model.addAttribute("invoices",InvoiceDTO.toInvoiceDTOList(
                                                        invoiceService.getInvoicesByUser(user)));
        Bookkeeper bookkeeper = bookService.getBookkeeperFromUser(user);
        if(bookkeeper!=null){       // Role konnyebb lenne enum-bol hivni
            System.out.println("hello");
            model.addAttribute("clinetInvoces",InvoiceDTO.toInvoiceDTOList(
                                                        bookService.getClientInvocies(bookkeeper)));
        }else{
            System.out.println("bello");
            model.addAttribute("clinetInvoces",null);
        }
        return "list";
    }

    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable("id") int id){
        invoiceService.deleteInvoice(id);
        return "redirect:/list";
    }

    @GetMapping("/select/{id}")
    public String selectInvoice(@PathVariable("id") int id,Model model){
        Invoice invoice = invoiceService.getInvoiceById(id);
        if(userService.getPrincipalUser().getUserName().equals(invoice.getUser().getUserName())) {
            invoice.setIsNew(false);
            invoiceService.save(invoice);
        }
        model.addAttribute("invoice",
                InvoiceDTO.toInvoiceDTO(invoiceService.getInvoiceById(id)));
        return "invoice";
    }

    @GetMapping("/create")
    public String toCreateInvoice(Model model){
        Set<UserDTO> clients = bookService.getClients(userService.getPrincipalUser());
        model.addAttribute("invoice",new InvoiceCreateDTO());
        model.addAttribute("clients", clients);
        return "create";
    }

    @PostMapping("/create/new")
    public String createInvoice(@ModelAttribute InvoiceCreateDTO invoiceCreateDTO) throws ParseException {
        System.out.println("createInvoice: "+invoiceCreateDTO.toInvoice(userRepository));
        invoiceService.save(invoiceCreateDTO.toInvoice(userRepository));
        return "redirect:/list";
    }

    @PostMapping("/update")
    public String updateInvoice(@ModelAttribute InvoiceUpdateDTO invoiceUpdateDTO) throws ParseException {
        System.out.println(invoiceUpdateDTO.getId());
        invoiceService.save(invoiceUpdateDTO.toInvoice(userRepository));
        return "redirect:/list";
    }

    @GetMapping("/refreshOwn")
    @ResponseBody
    public List<InvoiceDTO> updateOwnTable(){
        return InvoiceDTO.toInvoiceDTOList(
                invoiceService.getInvoicesByUser(
                        userService.getPrincipalUser()));
    }

    @GetMapping("/refreshClient")
    @ResponseBody
    public List<InvoiceDTO> updateClientTable(){
        return InvoiceDTO.toInvoiceDTOList(
                bookService.getClientInvocies(
                        bookService.getBookkeeperFromUser(
                            userService.getPrincipalUser())));
    }

}
