package com.example.invoicemanager.Web;

import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Controller
@RequiredArgsConstructor
public class StartController {

    private final UserService userService;

    @GetMapping("/")
    public String getHome(){
        return "redirect:/home";
    }


    @GetMapping("/home")
    public String toHome(Model model) {
        model.addAttribute("object", userService.getPrincipalUser());
        return "home";
    }
}
