package com.hims.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.hims.model.User;
public interface UserRepository extends MongoRepository<User,Integer> {
	
	@Query("{ 'email' : ?0 }")
	User findUserByEmail(String email);
	

}
