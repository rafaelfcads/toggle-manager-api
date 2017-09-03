package com.farfetch.toggle.domain.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {
	
	public User findByUserName(String userName);
	
	@Override
	@Query("{id: ?#{ hasRole('ROLE_ADMIN') ? {$exists:true} : principal.userId}}")
	public User findOne(String id);
			
}
