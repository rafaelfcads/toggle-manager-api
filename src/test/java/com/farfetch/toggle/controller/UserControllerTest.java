package com.farfetch.toggle.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.farfetch.toggle.config.security.OAuthHelper;
import com.farfetch.toggle.domain.user.User;
import com.farfetch.toggle.domain.user.UserRepository;
import com.google.gson.GsonBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private OAuthHelper oauthHelper;
	
	@Autowired
	private UserRepository repository;
	
	private User userMock;
	
	private String[] roles = { "ROLE_ADMIN" };
	
	@Before
	public void runBeforeTests() throws Exception {
		this.userMock = this.createUser("Joao", "xxzS2", "v1.1.1", this.roles);
	}
	
	@After
	public void runAfterTests() throws Exception {
		this.repository.deleteAll();
	}
	
	@Test
	@WithMockUser(username="admin",roles={"ADMIN"})
	public void shouldRetrieveAllUsers() throws Exception {
		
		this.createUser("Pedro", "xxzS2", "v2.1.1", this.roles);

		this.mockMvc.perform(get("/users")
			.with(this.oauthHelper.bearerToken("my-trusted-client")))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].userName").value("Joao"))
			.andExpect(jsonPath("$[0].password").value("xxzS2"))
			.andExpect(jsonPath("$[0].version").value("v1.1.1"))
			.andExpect(jsonPath("$[0].roles").isArray())
			.andExpect(jsonPath("$[1].userName").value("Pedro"))
			.andExpect(jsonPath("$[1].password").value("xxzS2"))
			.andExpect(jsonPath("$[1].version").value("v2.1.1"))
			.andExpect(jsonPath("$[1].roles").isArray());
	}
	
	@Test
	@WithMockUser(username="admin",roles={"ADMIN"})
	public void shouldRetrieveUserById() throws Exception {

        this.mockMvc.perform(get("/users/" + this.userMock.getId())
			.with(this.oauthHelper.bearerToken("my-trusted-client")))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.userName").value("Joao"))
			.andExpect(jsonPath("$.password").value("xxzS2"))
			.andExpect(jsonPath("$.version").value("v1.1.1"))
			.andExpect(jsonPath("$.roles").value("ROLE_ADMIN"));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"ADMIN"})
	public void shouldRetrieveUserByName() throws Exception {

        this.mockMvc.perform(get("/users/search/findByName?name=" + this.userMock.getUserName())
			.with(this.oauthHelper.bearerToken("my-trusted-client")))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.userName").value("Joao"))
			.andExpect(jsonPath("$.password").value("xxzS2"))
			.andExpect(jsonPath("$.version").value("v1.1.1"))
			.andExpect(jsonPath("$.roles").value("ROLE_ADMIN"));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"ADMIN"})
	public void shouldCreateUser() throws Exception {
		
		User userMock = new User("Lucas", "xxzS2", "v3.1.1", this.roles);

		this.mockMvc.perform(post("/users")
			.with(this.oauthHelper.bearerToken("my-trusted-client"))
			.contentType(MediaType.APPLICATION_JSON)
			.content(new GsonBuilder().create().toJson(userMock)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.userName").value("Lucas"))
			.andExpect(jsonPath("$.password").value("xxzS2"))
			.andExpect(jsonPath("$.version").value("v3.1.1"))
			.andExpect(jsonPath("$.roles").value("ROLE_ADMIN"));
	}
	


	@Test(expected = AssertionError.class) 
	@WithMockUser(username="admin",roles={"ADMIN"})
	public void shouldNotCreateUserNull() throws Exception {
		
		User userMock = null;

		this.mockMvc.perform(post("/users")
			.with(this.oauthHelper.bearerToken("my-trusted-client"))
			.contentType(MediaType.APPLICATION_JSON)
			.content(new GsonBuilder().create().toJson(userMock)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.userName").value("Lucas"))
			.andExpect(jsonPath("$.password").value("xxzS2"))
			.andExpect(jsonPath("$.version").value("v3.1.1"))
			.andExpect(jsonPath("$.roles").value("ROLE_ADMIN"));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"ADMIN"})
	public void shouldUpdateUser() throws Exception {

		this.userMock.setUserName("Alfredo");

		this.mockMvc.perform(put("/users/" + this.userMock.getId())
			.with(this.oauthHelper.bearerToken("my-trusted-client"))
			.contentType(MediaType.APPLICATION_JSON)
			.content(new GsonBuilder().create().toJson(this.userMock)))
			.andExpect(status().is2xxSuccessful());

	}
	
	@Test(expected = AssertionError.class) 
	@WithMockUser(username="admin",roles={"ADMIN"})
	public void shouldNotUpdateUserNull() throws Exception {
		
		User userMock = null;

		this.mockMvc.perform(put("/users/" + this.userMock.getId())
			.with(this.oauthHelper.bearerToken("my-trusted-client"))
			.contentType(MediaType.APPLICATION_JSON)
			.content(new GsonBuilder().create().toJson(userMock)))
			.andExpect(status().is2xxSuccessful());
	}
	
	@Test
	@WithMockUser(username="admin",roles={"ADMIN"})
	public void shouldDeleteUser() throws Exception {
		
		this.mockMvc.perform(delete("/users/" + this.userMock.getId())
			.with(this.oauthHelper.bearerToken("my-trusted-client")))
			.andExpect(status().is2xxSuccessful());
	}
	
	private User createUser(String name, String password, String version, String... roles) throws Exception {
		
		User user = new User(name, password, version, roles);
		
		MvcResult mvcResult = this.mockMvc.perform(post("/users")
			.with(this.oauthHelper.bearerToken("my-trusted-client"))
			.contentType(MediaType.APPLICATION_JSON)
			.content(new GsonBuilder().create().toJson(user)))
			.andExpect(status().isCreated()).andReturn();
		
        return new GsonBuilder().create()
        		.fromJson(mvcResult.getResponse().getContentAsString(), User.class);
        		
	}

}
