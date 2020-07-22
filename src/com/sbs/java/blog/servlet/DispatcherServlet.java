package com.sbs.java.blog.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.app.App;
import com.sbs.java.blog.service.MailService;


public class DispatcherServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		req.setCharacterEncoding("UTF-8"); // 한글깨짐 방지
		
		String gmailId = getServletConfig().getInitParameter("gmailId");
		String gmailPw = getServletConfig().getInitParameter("gmailPw");
		
		MailService mailService = new MailService(gmailId, gmailPw, gmailId, "관리자");
//		boolean sendMailDone = mailService.send("tmdqjaha12@naver.com", "안녕하세요.", "반갑습니다.!!") == 1;

//		resp.getWriter().append(String.format("발송성공 : %b", sendMailDone));
		
//		new App(req, resp).start();
		new App(req, resp, mailService).start();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
