package com.example.invoicemanager;

import com.example.invoicemanager.Model.Role;
import com.example.invoicemanager.Repository.RoleRepository;
import com.example.invoicemanager.Service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
class InvoicemanagerApplicationTests {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	RoleService roleService;


	@Test
	void contextLoads() {
		roleService.createRole("hello");
		roleService.createRole("bello");

		List<Role> roles = roleRepository.findAll();

		Role role=roles.get(0);

		System.out.println(role.getId() + " " + role.getName());

		assertEquals(roles.size(),2);
	}

}
