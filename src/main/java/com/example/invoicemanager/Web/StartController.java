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
        return "redirect:/beforeHome";
    }

    @GetMapping("/beforeHome")
    public String saveLoginTime(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        User user = userService.getUserByUsername(username);
        user.setLastLogin(new Date());
        userService.saveToDatabase(user);
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String login(Model model) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        User user = userService.getUserByUsername(username);
        model.addAttribute("object", user);
        return "home";
    }
}
