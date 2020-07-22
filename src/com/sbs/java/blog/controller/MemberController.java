package com.sbs.java.blog.controller;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.service.MailService;
import com.sbs.java.blog.util.Util;

public class MemberController extends Controller {
	private MailService mailService;

	public MemberController(Connection dbConn, String actionMethodName, HttpServletRequest req,
			HttpServletResponse resp, MailService mailService) {
		super(dbConn, actionMethodName, req, resp);
		this.mailService = mailService;
	}
	
//	public MemberController(Connection dbConn, String actionMethodName, HttpServletRequest req,
//			HttpServletResponse resp) {
//		super(dbConn, actionMethodName, req, resp);
//	}

	public String doAction() {
		switch (actionMethodName) {
		case "join":
			return doActionJoin();
		case "doJoin":
			return doActionDoJoin();
		case "login":
			return doActionLogin();
		case "doLogin":
			return doActionDoLogin();
		case "doLogout":
			return doActionDoLogout();
		case "findId":
			return doActionFindId();
		case "doFindId":
			return doActionDoFindId();
		case "findPw":
			return doActionFindPw();
		case "doFindPw":
			return doActionDoFindPw();
		}

		return "";
	}

	private String doActionDoFindPw() {
		String loginId = req.getParameter("loginId");
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		
		if(memberService.getBooleanForFindPw(loginId, name, email)) {
			String imshiPw = Util.getRandomPassword(8);
			String encryptSHA256ImshiPw = Util.encryptSHA256(imshiPw);
			
			memberService.updateImshiPw(loginId, name, email, encryptSHA256ImshiPw);
			
			mailService.send(email, "임시 비밀번호 발송", name + "님의 임시 비밀번호 : " + imshiPw);
			
			return String.format("html:<script> alert('발송된 임시번호로 로그인해주세요.'); location.replace('login'); </script>");
		}
		
		return String.format("html:<script> alert('유효한 정보를 찾지 못했습니다.'); location.replace('login'); </script>");
	}

	private String doActionFindPw() {
		return "member/findPw.jsp";
	}

	private String doActionDoFindId() {
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String loginId = "";
		
		loginId = memberService.getStringForFindId(name, email);
		
		if(loginId.length() != 0) {
			mailService.send(email, "아이디 발송", name + "님의 아이디 : " + loginId);
			return String.format("html:<script> alert('해당 이메일으로 아이디가 발송되었습니다.'); location.replace('login'); </script>");
		}
			
		
				
		return String.format("html:<script> alert('유효한 정보를 찾지 못했습니다.'); location.replace('login'); </script>");
	}

	private String doActionFindId() {
		return "member/findId.jsp";
}

	private String doActionDoLogin() {
		String loginId = req.getParameter("loginId");
		String loginPw = req.getParameter("loginPwReal");

		int loginedMemberId = memberService.getMemberIdByLoginIdAndLoginPw(loginId, loginPw);

		if (loginedMemberId == -1) {
			return String.format("html:<script> alert('일치하는 정보가 없습니다.'); history.back(); </script>");
		}

		session.setAttribute("loginedMemberId", loginedMemberId);

		return String.format("html:<script> alert('로그인 되었습니다.'); location.replace('../home/main'); </script>");
	}

	private String doActionLogin() {
		return "member/login.jsp";
	}

	private String doActionDoLogout() {
		session.removeAttribute("loginedMemberId");

		return String.format("html:<script> alert('로그아웃 되었습니다.'); location.replace('../home/main'); </script>");
	}

	private String doActionDoJoin() {

		String loginId = req.getParameter("loginId");
		String loginPw = req.getParameter("loginPwReal");
		String name = req.getParameter("name");
		String nickname = req.getParameter("nickname");
		String email = req.getParameter("email");

		boolean isJoinableLoginId = memberService.isJoinableLoginId(loginId);

		if (isJoinableLoginId == false) {
			return String.format("html:<script> alert('%s(은)는 이미 사용중인 아이디 입니다.'); history.back(); </script>", loginId);
		}

		boolean isJoinableNickname = memberService.isJoinableNickname(nickname);

		if (isJoinableNickname == false) {
			return String.format("html:<script> alert('%s(은)는 이미 사용중인 닉네임 입니다.'); history.back(); </script>", nickname);
		}

		boolean isJoinableEmail = memberService.isJoinableEmail(email);

		if (isJoinableEmail == false) {
			return String.format("html:<script> alert('%s(은)는 이미 사용중인 이메일 입니다.'); history.back(); </script>", email);
		}

		memberService.join(loginId, loginPw, name, nickname, email);
		
		mailService.send(email, "가입을 축하드립니다!", name + "반갑습니다.!!");
		
		return String.format("html:<script> alert('%s님 환영합니다.'); location.replace('../home/main'); </script>", name);
	}

	private String doActionJoin() {
		return "member/join.jsp";
	}

	@Override
	public String getControllerName() {
		return "member";
	}
}