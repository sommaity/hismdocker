package com.hims.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document

public class Orders {
	@Id
	private int id;
	private int providerId;
	private String orderDate;
	private String status;

}
