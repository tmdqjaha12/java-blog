package com.sbs.java.blog.dto;

import java.util.Map;

import lombok.Data;

@Data
public class Attr extends Dto {
	private String name;
	private String value;

	public Attr(Map<String, Object> row) {
		super(row);

		this.name = (String) row.get("name");
		this.value = (String) row.get("value");
	}

}