package com.hims.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hims.model.Orders;

public interface OrderRepository extends MongoRepository<Orders, Integer>{

}
