package com.example.invoicemanager.Web;

import com.example.invoicemanager.Model.Role;
import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Security.MyUserDetails;
import com.example.invoicemanager.Security.MyUserDetailsService;
import com.example.invoicemanager.Service.RoleService;
import com.example.invoicemanager.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final RoleService roleService;
    private final MyUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/registration/new")
    public String createUser(User user , @RequestParam("newRoles")List<String> roles){
        userService.saveUser(user, roles);
        return "redirect:/login";
    }

    @GetMapping("/registration")
    public String createUser(Model model){
        model.addAttribute("newUser",new User());
        model.addAttribute("userRoles", roleService.getRoles());
        return "registration";
    }

    @GetMapping("/login")
    public String toLog() {
        return "login";
    }

}
