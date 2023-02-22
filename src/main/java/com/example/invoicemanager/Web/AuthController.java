package com.example.invoicemanager.Web;

import com.example.invoicemanager.Model.Role;
import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Service.RoleService;
import com.example.invoicemanager.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/registration/new")
    public String createUser(User user){
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(1,"USER",null));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleSet);
        userService.saveToDatabase(user);
        return "redirect:/login";
    }

    @GetMapping("/registration")
    public String createUser(Model model){
        model.addAttribute("newUser",new User());
        return "registration";
    }

    @GetMapping("/login")
    public String toLog() {
        return "login";
    }
}
