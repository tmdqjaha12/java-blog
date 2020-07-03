package com.sbs.java.blog.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Dao {
	protected HttpServletRequest req;
	protected HttpServletResponse resp;

	public Dao(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}
}
