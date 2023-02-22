package com.example.invoicemanager.Web;

import com.example.invoicemanager.Model.Role;
import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Model.UserDTO;
import com.example.invoicemanager.Service.MyUserDetailsService;
import com.example.invoicemanager.Service.RoleService;
import com.example.invoicemanager.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
        System.out.println("---------------------------------");
        getPrincipal();
        return "login";
    }

    private void getPrincipal(){
        User user = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
            UserDetails user2 = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println(user2.getUsername());
            System.out.println(user2.getPassword());
            System.out.println(user2.getAuthorities());
        }else{
            System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
    }

}
