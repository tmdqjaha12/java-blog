package com.sbs.java.blog.dto;

import java.util.HashMap;
import java.util.Map;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public Map<String, Object> getExtra() {
		return extra;
	}

	public void setExtra(Map<String, Object> extra) {
		this.extra = extra;
	}
}