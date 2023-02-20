package com.example.invoicemanager.Service;

import com.example.invoicemanager.Model.Role;
import com.example.invoicemanager.Repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    final RoleRepository roleRepository;

    public void createRole(String name, String description){
        roleRepository.save(new Role(name, description));
    }

    public void createRole(String name){
        roleRepository.save(new Role(name));
    }

    public void setDescription(Integer id, String description){
        Role role = roleRepository.getReferenceById(id);
        role.setDescription(description);
        roleRepository.save(role);
    }

    public Role findById(Integer id){
        return roleRepository.getReferenceById(id);
    }
}
