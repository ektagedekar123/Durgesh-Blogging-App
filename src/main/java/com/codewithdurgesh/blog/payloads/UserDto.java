package com.codewithdurgesh.blog.payloads;

import java.util.HashSet;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.codewithdurgesh.blog.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDto {

	private Long id;

	@NotEmpty
	@Size(min = 4, message = "Username must be of min 4 characters")
	private String name;

	@Email(message = "Email Id is not Valid")
	private String email;

	@NotBlank
	@Size(min = 4, max = 10, message = "Password must be of min 4 chars & max 10 chars")
//	@Pattern(regexp ="[a-zA-Z]{4}[0-9]?[^a-zA-Z0-9]?[a-zA-z0-9]{4}", message = "Invalid password")
	@Pattern(regexp = "[a-zA-z0-9][a-zA-Z0-9-[^a-zA-z0-9]]+", message = "Invalid Password")
	private String password;

	@NotNull
	private String about;

	private Set<RoleDto> roles = new HashSet<>();
}
