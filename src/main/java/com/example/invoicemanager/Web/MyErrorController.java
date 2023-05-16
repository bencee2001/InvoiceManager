package com.example.invoicemanager.Web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest httpRequest, Model model) {
        Exception e = (Exception) httpRequest.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        model.addAttribute("error",e!=null ? e.getMessage():"Error unknown.");
        return "error";
    }

}
