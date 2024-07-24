package com.hims.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.hims.model.InventoryItems;
//simport com.hims.model.User;

public interface InventoryItemsRepository extends MongoRepository<InventoryItems,Integer>{
	
	@Query("{ '_id' : ?0 }")
	InventoryItems findUserByid(int id);

}
