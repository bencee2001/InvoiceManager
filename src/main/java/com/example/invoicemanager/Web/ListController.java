package com.example.invoicemanager.Web;

import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Model.dto.InvoiceCreateDTO;
import com.example.invoicemanager.Model.dto.InvoiceDTO;
import com.example.invoicemanager.Repository.UserRepository;
import com.example.invoicemanager.Service.BookkeeperService;
import com.example.invoicemanager.Service.InvoiceService;
import com.example.invoicemanager.Service.UserService;
import com.example.invoicemanager.libs.Error.NoSuchInvoiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/list")
public class ListController {

    private final InvoiceService invoiceService;
    private final UserService userService;
    private final BookkeeperService bookService;

    @GetMapping
    public String getList(Model model){
        User user = userService.getPrincipalUser();
        model.addAttribute("invoices", InvoiceDTO.toInvoiceDTOList(invoiceService.getInvoicesByUser(user)));
        model.addAttribute("clinetInvoces",bookService.getAllClientInvociesByUser(user));
        return "list";
    }

    @GetMapping("/create")
    public String toCreateInvoice(Model model){
        model.addAttribute("invoice",new InvoiceCreateDTO());
        model.addAttribute("clients", bookService.getClients(userService.getPrincipalUser()));
        return "create";
    }

    @GetMapping("/refreshOwn")
    @ResponseBody
    public List<InvoiceDTO> updateOwnTable(){
        return InvoiceDTO.toInvoiceDTOList(invoiceService.getInvoicesByUser(userService.getPrincipalUser()));
    }

    @GetMapping("/refreshClient")
    @ResponseBody
    public List<InvoiceDTO> updateClientTable(){
        return bookService.getAllClientInvociesByUser(userService.getPrincipalUser());
    }
}
