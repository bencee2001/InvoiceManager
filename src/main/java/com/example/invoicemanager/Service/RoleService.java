package com.example.invoicemanager.Service;

import com.example.invoicemanager.Model.Role;
import com.example.invoicemanager.Repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    final RoleRepository roleRepository;

    public List<Role> getRoles(){
        return roleRepository.findAll();
    }

}
