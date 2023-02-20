package com.example.invoicemanager.Web;

import com.example.invoicemanager.Model.Role;
import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Model.UserDTO;
import com.example.invoicemanager.Service.MyUserDetailsService;
import com.example.invoicemanager.Service.RoleService;
import com.example.invoicemanager.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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

    /*@PostMapping("/registration")
    public void createUser(@RequestBody UserDTO userDTO){

        System.out.println("Anguler Say: Hello");

        Role role = roleService.findById(1); // user-nek az id-ja
        List<Role> roleSet = new ArrayList<>();
        roleSet.add(role);

        User user = new User(
                userDTO.getUsername(),
                userDTO.getName(),
                passwordEncoder.encode(userDTO.getPassword()),
                null,
                roleSet);

        userService.saveToDatabase(user);
    }*/

    @RequestMapping("/registration")
    public void createUser(@RequestBody String hello){

        System.out.println("Anguler Say: "+ " " +hello);


    }

}
