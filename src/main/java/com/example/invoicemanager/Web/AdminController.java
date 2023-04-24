package com.example.invoicemanager.Web;

import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Service.BookkeeperService;
import com.example.invoicemanager.Service.RoleService;
import com.example.invoicemanager.Service.UserService;
import com.example.invoicemanager.libs.Error.BookkeeperHasClientsExpection;
import com.example.invoicemanager.libs.Error.LogedInUserDeleteException;
import com.example.invoicemanager.libs.Error.NoSelectedRoleException;
import com.example.invoicemanager.libs.Error.NoSuchUserExpection;
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
    private final BookkeeperService bookService;

    private final String bookkeeperRoleId = "2";

    @GetMapping
    public String getAdmin(Model model){
        model.addAttribute("users", userService.getUserDTOs());
        model.addAttribute("roles", roleService.getRoles());
        return "admin";
    }

    @PostMapping("/save/{username}")
    public String saveNewRoles(@RequestParam("newRoles") List<String> roleIds,
                               @PathVariable String username) throws NoSelectedRoleException, NoSuchUserExpection {
        User user = userService.getUserByUsername(username);
        if(bookService.checkIfHaveClients(user))
            roleIds.add(bookkeeperRoleId);
        userService.saveNewRoles(roleIds,user);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") String username) throws NoSuchUserExpection, BookkeeperHasClientsExpection, LogedInUserDeleteException {
        User user = userService.getUserByUsername(username);
        if(bookService.checkIfHaveClients(user))
            throw new BookkeeperHasClientsExpection("Bookkeeper "+ username+" has clients.");
        userService.deleteByUsername(user);
        return "redirect:/admin";
    }
}
