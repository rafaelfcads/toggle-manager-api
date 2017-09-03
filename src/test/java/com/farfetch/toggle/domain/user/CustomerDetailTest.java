package com.farfetch.toggle.domain.user;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerDetailTest {
	
	@Test
	public void shouldCreateCustomerDetail() throws Exception {

		String[] roles = {"ADMIN"};
		User user = new User();
		user.setUserName("Test Unit");
		user.setId("xx2");
		user.setPassword("test Unit Security");
		user.setRoles(roles);
		
		CustomUserDetail customUserDetail = new CustomUserDetail(user);
		
		assertEquals("Test Unit", customUserDetail.getUsername());
		assertEquals("xx2", customUserDetail.getUserId());
		assertEquals("test Unit Security", customUserDetail.getPassword());
		assertTrue(customUserDetail.isAccountNonExpired());
		assertTrue(customUserDetail.isAccountNonLocked());
		assertTrue(customUserDetail.isCredentialsNonExpired());
		assertTrue(customUserDetail.isEnabled());
	}

}
