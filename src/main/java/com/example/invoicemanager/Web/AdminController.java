package com.example.invoicemanager.Web;


import com.example.invoicemanager.Service.RoleService;
import com.example.invoicemanager.Service.UserService;
import com.example.invoicemanager.libs.Error.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping
    public String getAdmin(Model model){
        model.addAttribute("users", userService.getUserDTOs());
        model.addAttribute("roles", roleService.getRoles());
        return "admin";
    }

    @PostMapping("/save/{username}")
    public String saveNewRoles(@RequestParam("newRoles") List<String> roleIds,
                               @PathVariable String username) throws NoSelectedRoleException, NoSuchUserExpection, NoSuchBookkeeperExcpetion {
        userService.saveNewRoles(roleIds,userService.getUserByUsername(username));
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") String username) throws NoSuchUserExpection, BookkeeperHasClientsExpection, LogedInUserDeleteException {
        userService.deleteByUsername(userService.getUserByUsername(username));
        return "redirect:/admin";
    }
}
