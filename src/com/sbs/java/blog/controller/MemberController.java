package com.sbs.java.blog.controller;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.dto.Attr;
import com.sbs.java.blog.dto.Member;
import com.sbs.java.blog.service.MailService;
import com.sbs.java.blog.util.Util;
import java.util.UUID;

public class MemberController extends Controller {

	public MemberController(Connection dbConn, String actionMethodName, HttpServletRequest req,
			HttpServletResponse resp) {
		super(dbConn, actionMethodName, req, resp);
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
		case "authMail":
			return doActionauthMail();
		case "doAuthMail":
			return doActionDoAuthMail();
		case "doChat":
			return doActionDoChat();
		case "memberModify":
			return doActionMemberModify();
		case "doMemberModify":
			return doActionDoMemberModify();
		case "getLoginIdDup":
			return actionGetLoginIdDup();
		case "passwordForPrivate":
			return actionPasswordForPrivate();
		case "doPasswordForPrivate":
			return actionDoPasswordForPrivate();
		case "modifyPrivate":
			return actionModifyPrivate();
		case "doModifyPrivate":
			return actionDoModifyPrivate();
		case "attr":
			return doActionAttr();
		}

		return "";
	}
	
	private String doActionAttr() {
		String authCode = Util.getRandomPassword(5);
		attrService.setValue("member__1__extra__emailAuthCode", authCode);
		req.getAttribute("loginedMemberId");
		Attr tempPasswordExpireDateAttr = attrService.get("member__1__extra__emailAuthCode");
//		attrService.remove("member__1__extra__emailAuthCode");
		return "html:" + tempPasswordExpireDateAttr.getId();
	}

	private String actionDoModifyPrivate() {
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		String authCode = req.getParameter("authCode");

		if (memberService.isValidModifyPrivateAuthCode(loginedMemberId, authCode) == false) {
			return String.format(
					"html:<script> alert('비밀번호를 다시 체크해주세요.'); location.replace('../member/passwordForPrivate'); </script>");
		}

		String loginPw = req.getParameter("loginPwReal");

		memberService.modify(loginedMemberId, loginPw);
		Member loginedMember = (Member) req.getAttribute("loginedMember");
		loginedMember.setLoginPw(loginPw); // 크게 의미는 없지만, 의미론적인 면에서 해야 하는

		return String.format("html:<script> alert('개인정보가 수정되었습니다.'); location.replace('../home/main'); </script>");
	}

	private String actionModifyPrivate() {
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		String authCode = req.getParameter("authCode");
		if (memberService.isValidModifyPrivateAuthCode(loginedMemberId, authCode) == false) {
			return String.format(
					"html:<script> alert('비밀번호를 다시 체크해주세요.'); location.replace('../member/passwordForPrivate'); </script>");
		}

		return "member/modifyPrivate.jsp";
	}

	private String actionDoPasswordForPrivate() {
		String loginPw = req.getParameter("loginPwReal");

		Member loginedMember = (Member) req.getAttribute("loginedMember");
		int loginedMemberId = loginedMember.getId();

		if (loginedMember.getLoginPw().equals(loginPw)) {
			String authCode = memberService.genModifyPrivateAuthCode(loginedMemberId);

			return String
					.format("html:<script> location.replace('modifyPrivate?authCode=" + authCode + "'); </script>");
		}

		return String.format("html:<script> alert('비밀번호를 다시 입력해주세요.'); history.back(); </script>");
	}

	private String actionPasswordForPrivate() {
		return "member/passwordForPrivate.jsp";
	}

	private String actionGetLoginIdDup() {
		String loginId = req.getParameter("loginId");

		boolean isJoinableLoginId = memberService.isJoinableLoginId(loginId);

		if (isJoinableLoginId) {
			return "json:{\"msg\":\"사용할 수 있는 아이디 입니다.\", \"resultCode\": \"S-1\", \"loginId\":\"" + loginId + "\"}";
		} else {
			return "json:{\"msg\":\"사용할 수 없는 아이디 입니다.\", \"resultCode\": \"F-1\", \"loginId\":\"" + loginId + "\"}";
		}
	}

	private String doActionDoMemberModify() {
		Member member = (Member) req.getAttribute("loginedMember");
		if (req.getParameter("loginPwReal").equals(member.getLoginPw())) {
			return "";
		}
		return null;
	}

	private String doActionMemberModify() {
		return "member/memberModify.jsp";
	}

	private String doActionDoChat() {
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		session.setAttribute("loginedMemberId", loginedMemberId);
		return "member/chatpage.jsp";
	}

	private String doActionDoFindPw() {
		String loginId = req.getParameter("loginId");
		String name = req.getParameter("name");
		String email = req.getParameter("email");

		if (memberService.getBooleanForFindPw(loginId, name, email)) {
			String imshiPw = Util.getRandomPassword(8);
			String encryptSHA256ImshiPw = Util.encryptSHA256(imshiPw);

			memberService.updateImshiPw(loginId, name, email, encryptSHA256ImshiPw);

//			mailService.send(email, "임시 비밀번호 발송", name + "님의 임시 비밀번호 : " + imshiPw, "");

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

		if (loginId.length() != 0) {
//			mailService.send(email, "아이디 발송", name + "님의 아이디 : " + loginId, "");
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

		String redirectUrl = Util.getString(req, "redirectUrl", "../home/main");

		return String.format("html:<script> alert('로그인 되었습니다.'); location.replace('" + redirectUrl + "'); </script>");
	}

	private String doActionLogin() {
		return "member/login.jsp";
	}

	private String doActionDoLogout() {
		session.removeAttribute("loginedMemberId");

		return String.format("html:<script> alert('로그아웃 되었습니다.'); location.replace('../home/main'); </script>");
	}

	private String doActionauthMail() {
		String authCode = Util.getString(req, "code");
		req.setAttribute("authCode", authCode);
		return "member/authMail.jsp";
	}

	private String doActionDoAuthMail() {
		String authCode = Util.getString(req, "authCode");
		String inputAuthCode = Util.getString(req, "inputAuthCode");

		if (authCode.equals(inputAuthCode)) {
			int loginedMemberId = (int) req.getAttribute("loginedMemberId");
			memberService.getTrueAuthCode(loginedMemberId);

			return String.format("html:<script> alert('인증 완료!'); location.replace('../home/main'); </script>");
		}

		return String.format("html:<script> alert('인증 실패!'); history.back(); </script>");
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
		
		int joinId = memberService.join(loginId, loginPw, name, nickname, email);
//		
//		String authCode = memberService.genAuthMailAuthCode(joinId);
//		
//		
//		
//		
//		String loginPw = req.getParameter("loginPwReal");
//
//		Member loginedMember = (Member) req.getAttribute("loginedMember");
//		int loginedMemberId = loginedMember.getId();
//
//		if (loginedMember.getLoginPw().equals(loginPw)) {
//			String authCode = memberService.genAuthMailAuthCode(loginedMemberId);
//
//			return String
//					.format("html:<script> location.replace('modifyPrivate?authCode=" + authCode + "'); </script>");
//		}
//
//		return String.format("html:<script> alert('비밀번호를 다시 입력해주세요.'); history.back(); </script>");
		

//		String authCode = Util.getRandomPassword(5);
//		String massage = "<a href=\"https://meloporn.my.iu.gy/blog/s/member/authMail?code=" + authCode + "\">인증하기</a>";
//		String authMassage = Util.getHtmlFormEmail(massage, authCode);

//		mailService.send(email, "가입을 축하드립니다!", name + "님환영합니다.", authMassage);

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