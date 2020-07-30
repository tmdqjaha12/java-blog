package com.sbs.java.blog.dto;

import java.util.Map;

public class Attr extends Dto {
	private String name;
	private String value;

	public Attr(Map<String, Object> row) {
		super(row);

		this.name = (String) row.get("name");
		this.value = (String) row.get("value");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Attr [name=" + name + ", value=" + value + ", getId()=" + getId() + ", getRegDate()=" + getRegDate()
				+ ", getUpdateDate()=" + getUpdateDate() + ", getExtra()=" + getExtra() + "]";
	}
}