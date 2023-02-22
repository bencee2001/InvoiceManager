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

    public void createRole(String name){
        roleRepository.save(new Role(name));
    }

    public void setDescription(Integer id, String description){
        Role role = roleRepository.getReferenceById(id);
        role.setDescription(description);
        roleRepository.save(role);
    }

    public List<Role> getRoles(){
        return roleRepository.findAll();
    }

    public Role findById(Integer id){
        return roleRepository.getReferenceById(id);
    }
}
