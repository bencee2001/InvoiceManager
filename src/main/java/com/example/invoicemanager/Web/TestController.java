package com.example.invoicemanager.Web;

import com.example.invoicemanager.Model.*;
import com.example.invoicemanager.Repository.RoleRepository;
import com.example.invoicemanager.Repository.UserRepository;
import com.example.invoicemanager.Service.InvoiceService;
import com.example.invoicemanager.Service.RoleService;
import com.example.invoicemanager.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final InvoiceService invoiceService;
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/")
    public String getHome(){
        return "redirect:/";
    }

    @GetMapping("/user")
    public String getUser(){
        return "user";
    }

    @GetMapping("/admin")
    public String getAdmin(Model model){
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleService.getRoles());

        return "admin";
    }

    @GetMapping("/book")
    public String getBook(){
        return "page2";
    }

    @GetMapping("/beforeHome")
    public String afterlogin(){
        UserDetails user2 = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user2.getUsername();
        User user = userRepository.findById(username).get();
        user.setLastLogin(new Date());
        userRepository.save(user);
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String login(Model model) {
        UserDetails user2 = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user2.getUsername();
        User user = userRepository.findById(username).get();
        model.addAttribute("object", user);
        return "home";
    }

    @GetMapping("/list")
    public String getList(Model model){
        List<Invoice> invoices = invoiceService.getInvoices();
        model.addAttribute("invoices",invoices);
        return "list";
    }

    @GetMapping("/list/delete/{id}")
    public String deleteInvoice(@PathVariable("id") int id){
        Optional<Invoice> invoice = invoiceService.getInvoiceById(id);
        if(invoice.isPresent()) {
            invoiceService.deleteInvoice(id);
        }
        return "redirect:/list";
    }

    @GetMapping("/list/select/{id}")
    public String selectInvoice(@PathVariable("id") int id,Model model){
        Optional<Invoice> invoice = invoiceService.getInvoiceById(id);
        if(invoice.isEmpty()) {
            return "redirect:/list";
        }
        Invoice inv = invoice.get();
        model.addAttribute("invoice",inv);
        return "invoice";
    }

    @GetMapping("/list/create")
    public String toCreateInvoice(Model model){
        model.addAttribute("invoice",new ModelInvoice());
        return "create";
    }

    @PostMapping("/list/create/new")
    public String createInvoice(@ModelAttribute ModelInvoice mInvoice) throws ParseException {

        System.out.println(mInvoice.getDueDate());
        int id = invoiceService.getNumOfInvociesInDB()+11;

        Invoice invoice = mInvoice.toInvoice();

        invoice.setId(id);

        invoiceService.saveToDatabase(invoice);

        return "redirect:/list";
    }



    @GetMapping("/users")
    public List<String> listUsers(){
        List<String> names = new ArrayList<>();
        List<User> users = userRepository.findAll();
        users.forEach(user -> {
            names.add(user.getUserName());
        });
        return names;
    }

    @GetMapping("/roles")
    public List<Role> listRoles(){
        return roleRepository.findAll();
    }


}
