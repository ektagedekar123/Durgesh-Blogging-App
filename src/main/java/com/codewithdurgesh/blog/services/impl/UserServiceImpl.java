package com.codewithdurgesh.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.entities.Role;
import com.codewithdurgesh.blog.entities.User;
import com.codewithdurgesh.blog.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.payloads.PostResponse;
import com.codewithdurgesh.blog.payloads.UserDto;
import com.codewithdurgesh.blog.payloads.UserResponse;
import com.codewithdurgesh.blog.repositories.RoleRepository;
import com.codewithdurgesh.blog.repositories.UserRepository;
import com.codewithdurgesh.blog.services.UserService;
import com.codewithdurgesh.blog.utils.AppConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userrepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepo;

	@Override
	public UserDto registerNewUser(UserDto userDto) {

		log.info("Enterging Into Persistence to Register New User");
		User user = this.mapper.map(userDto, User.class);

		// Encode our Password
		log.info("Encode Plain Password provided by user");
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		// How To paly with Roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

		user.getRoles().add(role);
		User newUSer = this.userrepo.save(user);
		UserDto userDto2 = this.mapper.map(newUSer, UserDto.class);

		log.info("User Registered Successfully");
		return userDto2;
	}

	@Override
	public UserDto createUser(UserDto userDto) {
		log.info("Initiating dao layer for creating User");
//		User user = this.DtotoUser(userDto);

		User user = this.mapper.map(userDto, User.class);
		User saveuser = userrepo.save(user);
		UserDto usertoDto = this.mapper.map(saveuser, UserDto.class);

//		UserDto usertoDto = this.usertoDto(saveuser);
		log.info("Completed dao layer for creating User");
		return usertoDto;
	}

	@Override
	public UserDto updateUser(UserDto userDto, Long userId) {
		log.info("Enterging Persistence  to update  User with userId :{}", userId);
		User user = userrepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User updateduser = this.userrepo.save(user);
		UserDto usertoDto = this.mapper.map(updateduser, UserDto.class);
		log.info("Successfully Updated  User with userId :{}", userId);
		return usertoDto;
	}

	@Override
	public UserDto getUserById(Long id) {
		log.info("Initiating dao layer for getting user with user id:{}", id);
		User user = userrepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));

		log.info("Completed dao layer for getting user with user id:{}", id);
		return this.mapper.map(user, UserDto.class);
	}

	@Override
	public List<UserDto> getAllUsers() {
		log.info("Initiating dao layer to get All Users");
		List<User> findAll = userrepo.findAll();
		List<UserDto> list = findAll.stream().map((user) -> this.mapper.map(user, UserDto.class))
				.collect(Collectors.toList());
		log.info("Completed dao layer to get All Users");
		return list;
	}

	@Override
	public void deleteUser(Long userId) {
		log.info("Initiating dao layer to Delete User with userId :{}", userId);
		User user = userrepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
		this.userrepo.delete(user);
		log.info("Completed dao layer to delete User with userId :{}", userId);

	}

	@Override
	public UserResponse getAllUserResponse(Integer pageNumber, Integer pageSize) {
		log.info("Initiating dao layer to get All Users with pagination ");

		PageRequest of = PageRequest.of(pageNumber, pageSize);
		Page<User> page = userrepo.findAll(of);
		List<User> content = page.getContent();
		List<UserDto> list = content.stream().map((user) -> this.mapper.map(user, UserDto.class))
				.collect(Collectors.toList());
		log.info("All users Get Successfully ");
		UserResponse userResponse = new UserResponse();
		userResponse.setContent(list);
		userResponse.setPageNumber(page.getNumber());
		userResponse.setPageSize(page.getSize());
		userResponse.setTotalElements(page.getTotalElements());
		userResponse.setTotalPages(page.getTotalPages());
		userResponse.setLastpage(page.isLast());
		
		log.info("Completed dao layer to get All Users with pagination ");
		return userResponse;
	}
	
/*	public User DtotoUser(UserDto userdto) {
		User user2 = this.mapper.map(userdto, User.class);
		User user = new User();
		user.setId(userdto.getId());
		user.setName(userdto.getName());
		user.setEmail(userdto.getEmail());
		user.setPassword(userdto.getPassword());
		user.setAbout(userdto.getAbout());
		return user2;

	}

	public UserDto usertoDto(User user) {

		UserDto userDto = this.mapper.map(user, UserDto.class);
		UserDto userdto = new UserDto();
		userdto.setId(user.getId());
		userdto.setName(user.getName());
		userdto.setEmail(user.getEmail());
		userdto.setPassword(user.getPassword());
		userdto.setAbout(user.getAbout());
		return userDto;

}  */
}
