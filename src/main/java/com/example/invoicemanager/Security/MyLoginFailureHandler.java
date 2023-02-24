package com.example.invoicemanager.Security;

import com.example.invoicemanager.Model.User;
import com.example.invoicemanager.Repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MyLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        Optional<User> optUser = userRepository.findById(username);

        if(optUser.isPresent()){
            User user = optUser.get();
            user.setFailedLoginAttempts(user.getFailedLoginAttempts()+1);
        }

        super.onAuthenticationFailure(request, response, exception);
    }
}
