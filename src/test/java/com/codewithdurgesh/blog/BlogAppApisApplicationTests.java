package com.codewithdurgesh.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.codewithdurgesh.blog.repositories.UserRepository;
import com.codewithdurgesh.blog.services.UserService;

@SpringBootTest
class BlogAppApisApplicationTests {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
	}
	
	@Test
	public void repoTest()
	{
		String className = this.userRepo.getClass().getName();
		String packageName = this.userRepo.getClass().getPackageName();
		System.out.println(className);    //jdk.proxy2.$Proxy104
		System.out.println(packageName);  //jdk.proxy2
	}
	
	
    @Test
	public void serviceTest() {
    	
    	String name = this.userService.getClass().getName();
    	String packageName = this.userService.getClass().getPackageName();
    	System.out.println(name); // com.codewithdurgesh.blog.services.impl.UserServiceImpl
    	System.out.println(packageName); // com.codewithdurgesh.blog.services.impl
    }
}
