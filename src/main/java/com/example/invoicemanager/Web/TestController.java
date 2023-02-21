package com.example.invoicemanager.Web;

import com.example.invoicemanager.Model.Role;
import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Model.UserDTO;
import com.example.invoicemanager.Repository.RoleRepository;
import com.example.invoicemanager.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @GetMapping("/")
    public String getHome(){
        return "redirect:/";
    }
    @GetMapping("/user")
    public String getUser(){
        return "User";
    }
    @GetMapping("/book")
    public String getBook(){
        return "page2";
    }
    @GetMapping("/admin")
    public String getAdmin(){
        return "Admin";
    }



    @GetMapping("/users")
    public List<String> listUsers(){
        List<String> names = new ArrayList<>();
        List<User> users = userRepository.findAll();
        users.forEach(user -> {
            names.add(user.getUserName());
        });
        return names;
    }

    @GetMapping("/roles")
    public List<Role> listRoles(){
        return roleRepository.findAll();
    }


}
