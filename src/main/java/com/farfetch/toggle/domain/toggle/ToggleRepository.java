package com.farfetch.toggle.domain.toggle;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ToggleRepository extends MongoRepository<Toggle, String> {
	
	public Toggle findByName(String name);
	
}
