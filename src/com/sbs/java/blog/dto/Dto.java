package com.sbs.java.blog.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class Dto {
	private int id;
	private String regDate;
	private String updateDate;
	private Map<String, Object> extra;

	public Dto(Map<String, Object> row) {
		this.id = (int) row.get("id");
		this.regDate = (String) row.get("regDate");
		this.updateDate = (String) row.get("updateDate");
		this.extra = new HashMap<>();

		for (String key : row.keySet()) {
			if (key.startsWith("extra__")) {
				Object value = row.get(key);
				String extraKey = key.substring(7);
				this.extra.put(extraKey, value);
			}
		}
	}
}