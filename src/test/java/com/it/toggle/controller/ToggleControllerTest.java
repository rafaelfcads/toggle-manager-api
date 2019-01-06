package com.it.toggle.controller;

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

import com.google.gson.GsonBuilder;
import com.it.toggle.config.security.OAuthHelper;
import com.it.toggle.domain.toggle.Toggle;
import com.it.toggle.domain.toggle.ToggleRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ToggleControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private OAuthHelper oauthHelper;
	
	@Autowired
	private ToggleRepository repository;
	
	private Toggle toggleMock;
	
	
	@Before
	public void runBeforeTests() throws Exception {
		this.toggleMock = this.createToggle("isButtonRed", Boolean.TRUE);
	}
	
	@After
	public void runAfterTests() throws Exception {
		this.repository.deleteAll();
	}
	
	@Test
	@WithMockUser(username="admin",roles={"ADMIN"})
	public void shouldRetrieveAllToggles() throws Exception {
		
		this.createToggle("isButtonWhite", Boolean.FALSE);

		this.mockMvc.perform(get("/toggles")
			.with(this.oauthHelper.bearerToken("my-trusted-client")))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].name").value("isButtonRed"))
			.andExpect(jsonPath("$[0].value").value(Boolean.TRUE))
			.andExpect(jsonPath("$[1].name").value("isButtonWhite"))
			.andExpect(jsonPath("$[1].value").value(Boolean.FALSE));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"ADMIN"})
	public void shouldRetrieveToggleById() throws Exception {

        this.mockMvc.perform(get("/toggles/" + this.toggleMock.getId())
			.with(this.oauthHelper.bearerToken("my-trusted-client")))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("isButtonRed"))
			.andExpect(jsonPath("$.value").value(Boolean.TRUE));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"ADMIN"})
	public void shouldRetrieveToggleByName() throws Exception {

        this.mockMvc.perform(get("/toggles/search/findByName?name=" + this.toggleMock.getName())
			.with(this.oauthHelper.bearerToken("my-trusted-client")))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("isButtonRed"))
			.andExpect(jsonPath("$.value").value(Boolean.TRUE));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"ADMIN"})
	public void shouldCreateToggle() throws Exception {
		
		Toggle toggleMock = new Toggle("isButtonWhite", Boolean.FALSE);

		this.mockMvc.perform(post("/toggles")
			.with(this.oauthHelper.bearerToken("my-trusted-client"))
			.contentType(MediaType.APPLICATION_JSON)
			.content(new GsonBuilder().create().toJson(toggleMock)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.name").value("isButtonWhite"))
			.andExpect(jsonPath("$.value").value(Boolean.FALSE));
	}
	


	@Test(expected = AssertionError.class) 
	@WithMockUser(username="admin",roles={"ADMIN"})
	public void shouldNotCreateToggleNull() throws Exception {
		
		Toggle toggleMock = null;

		this.mockMvc.perform(post("/toggles")
			.with(this.oauthHelper.bearerToken("my-trusted-client"))
			.contentType(MediaType.APPLICATION_JSON)
			.content(new GsonBuilder().create().toJson(toggleMock)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.name").value("isButtonWhite"))
			.andExpect(jsonPath("$.value").value(Boolean.FALSE));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"ADMIN"})
	public void shouldUpdateToggle() throws Exception {

		this.toggleMock.setName("isButton");

		this.mockMvc.perform(put("/toggles/" + this.toggleMock.getId())
			.with(this.oauthHelper.bearerToken("my-trusted-client"))
			.contentType(MediaType.APPLICATION_JSON)
			.content(new GsonBuilder().create().toJson(this.toggleMock)))
			.andExpect(status().is2xxSuccessful());

	}
	
	@Test(expected = AssertionError.class) 
	@WithMockUser(username="admin",roles={"ADMIN"})
	public void shouldNotUpdateToggleNull() throws Exception {
		
		Toggle toggleMock = null;

		this.mockMvc.perform(put("/toggles/" + this.toggleMock.getId())
			.with(this.oauthHelper.bearerToken("my-trusted-client"))
			.contentType(MediaType.APPLICATION_JSON)
			.content(new GsonBuilder().create().toJson(toggleMock)))
			.andExpect(status().is2xxSuccessful());
	}
	
	@Test
	@WithMockUser(username="admin",roles={"ADMIN"})
	public void shouldDeleteToggle() throws Exception {
		
		this.mockMvc.perform(delete("/users/" + this.toggleMock.getId())
			.with(this.oauthHelper.bearerToken("my-trusted-client")))
			.andExpect(status().is2xxSuccessful());
	}
	
	private Toggle createToggle(String name, boolean value) throws Exception {
		
		Toggle toggle = new Toggle(name, value);
		
		MvcResult mvcResult = this.mockMvc.perform(post("/toggles")
			.with(this.oauthHelper.bearerToken("my-trusted-client"))
			.contentType(MediaType.APPLICATION_JSON)
			.content(new GsonBuilder().create().toJson(toggle)))
			.andExpect(status().isCreated()).andReturn();
		
        return new GsonBuilder().create()
        		.fromJson(mvcResult.getResponse().getContentAsString(), Toggle.class);
        		
	}

}
