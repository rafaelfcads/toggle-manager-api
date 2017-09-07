package com.farfetch.toggle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.farfetch.toggle.domain.user.User;
import com.farfetch.toggle.domain.user.UserNotFoundException;
import com.farfetch.toggle.domain.user.UserRepository;
import com.google.common.base.Preconditions;

@CrossOrigin(origins = "https://consumer-toggle-manager-api.herokuapp.com/")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RepositoryRestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository repository;
	
	@Value("${user.required}")
	private String userRequiredMessage;
	
	@Value("${user.id.required}")
	private String userIdRequiredMessage;
	
	@Value("${user.id.inconsistent}")
	private String userIdInconsistentMessage;
	
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
    public List<User> getAll() {
        return this.repository.findAll(); 
    }
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
    public User getById(@PathVariable String id) {
		User user = this.repository.findOne(id);
		if (user == null) throw new UserNotFoundException(id);
		return user;
    }
	
	@RequestMapping(method = RequestMethod.GET, value = "search/findByName")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
    public User getByName(@RequestParam(value="name") String name) {
		User user = this.repository.findByUserName(name);
		if (user == null) throw new UserNotFoundException(name);
		return user;
    }
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
    public User create(@RequestBody User user) {
		Preconditions.checkNotNull(user, this.userRequiredMessage);
		return this.repository.insert(user);
    }
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
    public void update(@PathVariable String id, @RequestBody User user) {	
		
		Preconditions.checkNotNull(user, this.userRequiredMessage);
		Preconditions.checkNotNull(user.getId(), this.userIdRequiredMessage);
		Preconditions.checkArgument(id.equals(user.getId()), this.userIdInconsistentMessage);
		Preconditions.checkNotNull(this.repository.findOne(id));
		
		this.repository.save(user);
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("id") String id) {
		this.repository.delete(id);
	}

}
