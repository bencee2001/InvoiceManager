package com.example.invoicemanager.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final MyLoginSuccessHandler loginSuccessHandler;
    private final MyLoginFailureHandler loginFailureHandler;

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("/auth/**","**/*.css").permitAll();
                    auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();
                    auth.requestMatchers(HttpMethod.GET,"/admin").hasAuthority("ADMIN");
                    auth.requestMatchers("/list/create/**").hasAuthority("BOOK");
                    auth.anyRequest().authenticated();
                })
                .formLogin(login->{
                        login.loginPage("/auth/login");
                        login.defaultSuccessUrl("/home");
                        login.permitAll();
                        login.successHandler(loginSuccessHandler);
                        //login.failureHandler(loginFailureHandler);
                })
                .logout(logout->{
                    logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
                    logout.logoutSuccessUrl("/");
                    logout.deleteCookies("JSESSIONID");
                    logout.invalidateHttpSession(true);
                });
        return http.build();

    }

    @Bean
    ApplicationListener<AuthenticationSuccessEvent> succesEvent(){
        return event -> {
            System.out.println("Success Login" + event.getAuthentication().getClass().getSimpleName()+" "+event.getAuthentication().getName());
        };
    }
}
