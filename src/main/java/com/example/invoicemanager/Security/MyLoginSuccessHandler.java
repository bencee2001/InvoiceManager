package com.example.invoicemanager.Security;

import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class MyLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        MyUserDetails userDetails =  (MyUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        user.setLastLogin(new Date());
        user.setFailedLoginAttempts(0);

        userRepository.save(user);

        super.onAuthenticationSuccess(request, response, authentication);
    }

}
