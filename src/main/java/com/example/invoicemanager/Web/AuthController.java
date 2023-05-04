package com.example.invoicemanager.Web;

import com.example.invoicemanager.DTO.UserCreateDTO;
import com.example.invoicemanager.Service.BookkeeperService;
import com.example.invoicemanager.Service.RoleService;
import com.example.invoicemanager.Service.UserService;
import com.example.invoicemanager.libs.Error.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final RoleService roleService;
    private final BookkeeperService bookService;

    @PostMapping("/registration/new")
    public String createUser(UserCreateDTO user , @RequestParam("newRoles")List<String> roles) throws UserAlreadyExistsException {
        userService.saveUser(user, roles);
        return "redirect:/login";
    }

    @GetMapping("/registration")
    public String createUser(Model model){
        model.addAttribute("newUser",new UserCreateDTO());
        model.addAttribute("userRoles", roleService.getRoles());
        model.addAttribute("bookkeepers", bookService.getBookkeepers());
        return "registration";
    }

    @GetMapping("/login")
    public String toLogin() {
        return "login";
    }

}
