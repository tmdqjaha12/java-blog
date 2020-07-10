package com.sbs.java.blog.controller;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.service.ArticleService;
import com.sbs.java.blog.service.MemberService;

public class MemberController extends Controller {

	public MemberController(Connection dbConn, String actionMethodName, HttpServletRequest req,
			HttpServletResponse resp) {
		super(dbConn, actionMethodName, req, resp);
	}
	
	public void beforeAction() {
		super.beforeAction();
		// 이 메서드는 게시물 컨트롤러의 모든 액션이 실행되기 전에 실행된다.
		// 필요없다면 지워도 된다.
	}

	@Override
	public String doAction() {
		switch (actionMethodName) {
		case "join":
			return doActionJoin(req, resp);
		case "doJoin":
			return doActionDoJoin(req, resp);
		case "login":
			return doActionLogin(req, resp);
		case "doLogin":
			return doActionDoLogin(req, resp);	
		}

		return "";
	}

	private String doActionLogin(HttpServletRequest req, HttpServletResponse resp) {
		return "member/login.jsp";
	}
	
	private String doActionDoLogin(HttpServletRequest req, HttpServletResponse resp) {
		
		String loginId = req.getParameter("loginId");
		String loginPw = req.getParameter("loginPwReal");
		
		int id = memberSercive.login(loginId, loginPw);
		
		if(id == 0) {
			return "html:<script> alert('유효하지 않는 정보입니다.'); location.replace('login'); </script>";
		}
		
		return "html:<script> alert('로그인 완료'); location.replace('../home/main'); </script>";
	}

	private String doActionJoin(HttpServletRequest req, HttpServletResponse resp) {
		return "member/join.jsp";
	}
	
	private String doActionDoJoin(HttpServletRequest req, HttpServletResponse resp) {

		String loginId = req.getParameter("loginId");
		String name = req.getParameter("name");
		String nickname = req.getParameter("nickname");
		String loginPw = req.getParameter("loginPwReal");
		
//		int cateItemId = Util.getInt(req, "cateItemId");

		int id = memberSercive.join(loginId, name, nickname, loginPw);

		return "html:<script> alert('" + id + "번째 회원을 환영합니다.'); location.replace('../home/main'); </script>";
	}

}
