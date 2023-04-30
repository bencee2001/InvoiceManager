package com.example.invoicemanager.Service;

import com.example.invoicemanager.Model.Role;
import com.example.invoicemanager.Repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService {

    final RoleRepository roleRepository;

    public List<Role> getRoles(){
        return roleRepository.findAll();
    }

    public Set<Role> makeRoleSet(List<String> stringRoles){
        Set<Role> roleSet = new HashSet<>();
        stringRoles.forEach(role->{
            roleSet.add(roleRepository.getReferenceById(Integer.valueOf(role)));
        });
        return roleSet;
    }

}
