package com.example.invoicemanager.Web;

import com.example.invoicemanager.Service.InvoiceService;
import com.example.invoicemanager.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final InvoiceService invoiceService;

    @GetMapping("/")
    public String getHome(){
        return "redirect:/home";
    }


    @GetMapping("/home")
    public String toHome(Model model) {
        model.addAttribute("object", userService.getPrincipalUserDTO());
        model.addAttribute("newInvoiceCount", invoiceService.getNewInvoiceCountByUser(userService.getPrincipalUser()));
        return "home";
    }

    @GetMapping("/refreshCount")
    @ResponseBody
    public int getNewCount(){
        return invoiceService.getNewInvoiceCountByUser(userService.getPrincipalUser());
    }
}
