package com.example.invoicemanager.Web;

import com.example.invoicemanager.Model.Role;
import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Repository.RoleRepository;
import com.example.invoicemanager.Repository.UserRepository;
import com.example.invoicemanager.Service.RoleService;
import com.example.invoicemanager.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping
    public String getAdmin(Model model){
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleService.getRoles());
        return "admin";
    }

    @PostMapping("/save/{username}")
    public String saveNewRoles(@RequestParam("newRoles") List<String> roleIds,
                               @PathVariable String username){
        userService.saveNewRoles(roleIds,username);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") String username){
        userService.deleteByUsername(username);
        return "redirect:/admin";
    }
}
