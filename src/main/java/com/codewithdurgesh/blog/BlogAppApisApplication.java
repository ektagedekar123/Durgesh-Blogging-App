package com.codewithdurgesh.blog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.codewithdurgesh.blog.entities.Role;
import com.codewithdurgesh.blog.repositories.RoleRepository;
import com.codewithdurgesh.blog.utils.AppConstants;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);

		System.out.println("Durgesh Blogging App is running.........");
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println(this.passwordEncoder.encode("rtyuio"));

		try {
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");

			Role role1 = new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_NORMAL");

			List<Role> list = List.of(role, role1);

			List<Role> saveAll = this.roleRepo.saveAll(list);
			saveAll.forEach(r -> System.out.println(r.getName()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
