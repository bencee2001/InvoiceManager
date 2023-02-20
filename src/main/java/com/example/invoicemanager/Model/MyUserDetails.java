package com.example.invoicemanager.Model;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class MyUserDetails implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("hello3");
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        user.getRoles().forEach(role -> {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        });
        System.out.println("hello4");
        return roles;
    }

    @Override
    public String getPassword() {
        System.out.println(user.getUserName()+" "+user.getPassword());
        System.out.println("hello");

        return user.getPassword();
    }

    @Override
    public String getUsername() {
        System.out.println("hello1");
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
