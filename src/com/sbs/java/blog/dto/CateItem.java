package com.sbs.java.blog.dto;

import java.util.Map;

import lombok.Data;

@Data
public class CateItem extends Dto {
	private String name;

	public CateItem(Map<String, Object> row) {
		super(row);

		this.name = (String) row.get("name");
	}

}