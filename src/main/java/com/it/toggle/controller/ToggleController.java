package com.it.toggle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.base.Preconditions;
import com.it.toggle.domain.toggle.Toggle;
import com.it.toggle.domain.toggle.ToggleNotFoundException;
import com.it.toggle.domain.toggle.ToggleRepository;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/toggles")
@RepositoryRestController
public class ToggleController {
	
	@Autowired
	private ToggleRepository repository;
	
	@Value("${toggle.required}")
	private String toggleRequiredMessage;
	
	@Value("${toggle.id.required}")
	private String toggleIdRequiredMessage;
	
	@Value("${toggle.id.inconsistent}")
	private String toggleIdInconsistentMessage;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
    public List<Toggle> getAll() {
        return this.repository.findAll(); 
    }
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
    public Toggle getById(@PathVariable String id) {
		Toggle toggle = this.repository.findOne(id);
		if (toggle == null) throw new ToggleNotFoundException(id);
		return toggle;
    }
	
	@RequestMapping(method = RequestMethod.GET, value = "search/findByName")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
    public Toggle getByName(@RequestParam(value="name") String name) {
		Toggle toggle = this.repository.findByName(name);
		if (toggle == null) throw new ToggleNotFoundException(name);
		return toggle;
    }
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
    public Toggle create(@RequestBody Toggle toggle) {
		Preconditions.checkNotNull(toggle, this.toggleRequiredMessage);
		return this.repository.insert(toggle);
    }
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
    public void update(@PathVariable String id, @RequestBody Toggle toggle) {	
		
		Preconditions.checkNotNull(toggle, this.toggleRequiredMessage);
		Preconditions.checkNotNull(toggle.getId(), this.toggleIdRequiredMessage);
		Preconditions.checkArgument(id.equals(toggle.getId()), this.toggleIdInconsistentMessage);
		Preconditions.checkNotNull(this.repository.findOne(id));
		
		this.repository.save(toggle);
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("id") String id) {
		this.repository.delete(id);
	}
}
