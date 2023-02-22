package com.example.invoicemanager.Web;

import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Service.RoleService;
import com.example.invoicemanager.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/admin")
    public String getAdmin(Model model){
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleService.getRoles());
        return "admin";
    }
}
