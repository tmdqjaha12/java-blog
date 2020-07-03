package com.sbs.java.blog.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Service {
	protected HttpServletRequest req;
	protected HttpServletResponse resp;
	
	public Service(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}
}
