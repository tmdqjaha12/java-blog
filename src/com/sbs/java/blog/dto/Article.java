package com.sbs.java.blog.dto;

import java.util.Map;

import lombok.Data;

@Data
public class Article extends Dto {
	private int cateItemId;
	private int memberId;
	private int hit;
	private String title;
	private String body;

	public Article(Map<String, Object> row) {
		super(row);

		this.cateItemId = (int) row.get("cateItemId");
		this.memberId = (int) row.get("memberId");
		this.title = (String) row.get("title");
		this.body = (String) row.get("body");
		this.hit = (int) row.get("hit");
	}

	public String getBodyForXTemplate() {
		return body.replaceAll("(?i)script", "<!--REPLACE:script-->");
	}


}