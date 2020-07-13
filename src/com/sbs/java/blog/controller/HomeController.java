package com.sbs.java.blog.controller;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController extends Controller {
	public HomeController(Connection dbConn, String actionMethodName, HttpServletRequest req,
			HttpServletResponse resp) {
		super(dbConn, actionMethodName, req, resp);
	}

	@Override
	public String doAction() {
		switch (actionMethodName) {
		case "main":
			return doActionMain(req, resp);
		case "aboutMe":
			return doActionAboutMe(req, resp);
		case "new":
			return doActionnew(req, resp);
		}

		return "";
	}

	private String doActionnew(HttpServletRequest req, HttpServletResponse resp) {
		return "home/new.jsp";
	}

	private String doActionAboutMe(HttpServletRequest req, HttpServletResponse resp) {
		return "home/aboutMe.jsp";
	}

	private String doActionMain(HttpServletRequest req, HttpServletResponse resp) {
		return "home/main.jsp";
	}

}
