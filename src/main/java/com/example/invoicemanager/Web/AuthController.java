package com.example.invoicemanager.Web;

import com.example.invoicemanager.Model.Role;
import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Model.UserDTO;
import com.example.invoicemanager.Service.MyUserDetailsService;
import com.example.invoicemanager.Service.RoleService;
import com.example.invoicemanager.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @PostMapping("/registration/new")
    public String createUser(User user){

        System.out.println("Anguler Say: Hello");

        Role role = roleService.findById(1); // user-nek az id-ja
        List<Role> roleSet = new ArrayList<>();
        roleSet.add(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleSet);

        userService.saveToDatabase(user);

        return "redirect:/index.html";
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

    @PostMapping("/home")
    public String login() {
        return "home";
    }

    @GetMapping(value = "/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getPrincipal());
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/index.html";
    }



}
