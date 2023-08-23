package com.nature.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Item {

	private Long itemId;
	private String itemName;
	private Integer price;
	
}
