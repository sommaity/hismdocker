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

public class InventoryItems {
	@Id
	private int id;
	private String itemName;
	private String description;
	private int quantityAvailable;
	private int reorderThreshold;
	private int unitPrice;

}
