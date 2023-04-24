package com.example.invoicemanager.Web;

import com.example.invoicemanager.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

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
