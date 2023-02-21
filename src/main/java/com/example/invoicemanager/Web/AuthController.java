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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/auth")
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

        Role role = roleService.findById(1); // user-nek az id-ja
        Set<Role> roleSet = new HashSet<>();
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
        System.out.println("---------------------------------");
        getPrincipal();
        return "login";
    }

    @GetMapping("/home")
    public String login() {
        System.out.println("---------------------------------");
        getPrincipal();
        return "home";
    }


    @GetMapping("/book")
    public String getBook(){
        getPrincipal();
        return "page2";
    }

    private void getPrincipal(){
        User user = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
            UserDetails user2 = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println("s");
            System.out.println(user2.getUsername());
            System.out.println(user2.getPassword());
            System.out.println(user2.getAuthorities());
            System.out.println("s");
        }else{
            System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
    }


    /*@GetMapping(value = "/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getPrincipal());
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/index.html";
    }*/



}
