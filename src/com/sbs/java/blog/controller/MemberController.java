package com.sbs.java.blog.controller;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.dto.Member;
import com.sbs.java.blog.util.Util;

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
		case "authEmail":
			return actionAuthEmail();
		case "doSendAuthMail":
			return doActionDoSendMail();
		}

		return "";
	}

	private String doActionDoSendMail() {
		int id = Util.getInt(req, "id");
		String email = Util.getString(req, "email");
		String authCode = memberService.genEmailAuthCode(id); // 회원 attr 테이블 저장 & 인증코드
		String sendBody = "<a href=\"https://blog.ourducks.com/blog/s/member/authEmail?email=" + email + "&authCode="
				+ authCode + "&memberId=" + id + "\" target=\"_blank\">사이트로 이동</a>";//meloporn.my.iu.gy //localhost:8081
		// 회원 인증 링크

		mailService.send(email, "이메일 인증", sendBody); // 메일 발송
		return String.format("html:<script> alert('인증번호 전송 완료.'); location.replace('../home/main'); </script>");
	}

	private String actionAuthEmail() {
		int loginedMemberId = Util.getInt(req, "memberId");
		String strLoginedMemberId = "" + loginedMemberId;
		int memberId = Util.getInt(req, "memberId");
		String authCode = Util.getString(req, "authCode");
		String email = Util.getString(req, "email");
		
		if (memberService.isValidEmailAuthCode(strLoginedMemberId, authCode) == false) {//attr비교
			return String.format(
//					"html:<script> alert('인증번호를 다시 체크해주세요.'); location.replace('authEmail=" + email + "&authCode=" + authCode + "&memberId=" + memberId + "'); </script>");
					"html:<script> alert('인증번호를 다시 체크해주세요.'); location.replace('../home/aboutMe'); </script>");
		}
		
		memberService.genEmailAuthed(loginedMemberId, email);//attr 이메일저장
		
		return String.format("html:<script> alert('인증 되었습니다.'); location.replace('../home/main'); </script>");
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

		return String.format("html:<script> alert('비밀번호가 수정되었습니다.'); location.replace('../home/main'); </script>");
	}

	private String actionModifyPrivate() {
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		String authCode = req.getParameter("authCode");
		if (memberService.isValidModifyPrivateAuthCode(loginedMemberId, authCode) == false) {
			return String.format(
					"html:<script> alert('비밀번호를 	해주세요.'); location.replace('../member/passwordForPrivate'); </script>");
		}

		return "member/modifyPrivate.jsp";
	}

	private String actionDoPasswordForPrivate() {
		String loginPw = req.getParameter("loginPwReal");

		Member loginedMember = (Member) req.getAttribute("loginedMember");
		int loginedMemberId = loginedMember.getId();

		if (loginedMember.getLoginPw().equals(loginPw)) {
			String authCode = memberService.genModifyPrivateAuthCode(loginedMemberId); //비밀번호 수정 authCode 저장 및 return
			memberService.genLastPasswordChangeDate(loginedMemberId); // 비밀번호 수정 날짜 저장
			memberService.removeUseTempPassword(loginedMemberId); // 발급받은 임시패스워드 삭제

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
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		String nickname = Util.getString(req, "nickname");
		String email = Util.getString(req, "email");
		
		String loginId = req.getParameter("loginId");
		String loginPw = req.getParameter("loginPwReal");

		int loginedMemberIdFact = memberService.getMemberIdByLoginIdAndLoginPw(loginId, loginPw);

		if (loginedMemberIdFact == -1) {
			return String.format("html:<script> alert('일치하는 정보가 없습니다.'); history.back(); </script>");
		}
		
		if(memberService.getEmailAuthed(loginedMemberId).equals(email) == false) {
			memberService.genEmailAuthed(loginedMemberId, "");//이메일 인증 초기화
		}
		
		memberService.memberModify(loginedMemberId, nickname, email);

		return String.format("html:<script> alert('저장 완료'); location.replace('../home/aboutMe'); </script>");
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
		String id = memberService.getMemberIdByLoginIdAndNameAndEmail(loginId, name, email) + "";

		if (memberService.getBooleanForFindPw(loginId, name, email)) {
			String imshiPw = Util.getRandomPassword(8);
//			String imshiPw = UUID.randomUUID().toString();
			String encryptSHA256ImshiPw = Util.encryptSHA256(imshiPw);
			
			memberService.updateImshiPw(loginId, name, email, encryptSHA256ImshiPw);
			memberService.genUseTempPassword(id, "1");
			
			mailService.send(email, "비밀번호 찾기/임시비밀번호", "회원님의 임시 비밀번호 : " +imshiPw);

			return String.format("html:<script> alert('발송된 임시번호로 로그인해주세요.'); location.replace('login'); </script>");
		}

		return String.format("html:<script> alert('유효한 정보를 찾지 못했습니다.'); location.replace('findPw'); </script>");
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
			mailService.send(email, "아이디 찾기 결과", "회원님의 아이디 : " + loginId);
			return String.format("html:<script> alert('해당 이메일으로 아이디가 발송되었습니다.'); location.replace('findId'); </script>");
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
		
		String regDate = memberService.isValidLastPasswordChangeDate(loginedMemberId);
		String nowRegDate = Util.getRegeDateNow();
		
		long paraDate = Util.getCalRegDate(regDate, nowRegDate); 
		if(paraDate > 365) {
			paraDate = paraDate/365;
			return String.format("html:<script> alert('비밀번호 변경 권장 : " + paraDate + "년 초과'); location.replace('passwordForPrivate'); </script>");
		}

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

		int joinId = memberService.join(loginId, loginPw, name, nickname, email);// 회원 테이블 저장

		String authCode = memberService.genEmailAuthCode(joinId); // 회원 attr 테이블 저장 & 인증코드
		memberService.genLastPasswordChangeDate(joinId); // 회원 업데이트 attr 테이블 저장
		memberService.genEmailAuthed(joinId, email); // 회원 이메일 attr 테이블 저장
		
		String sendBody = "<a href=\"https://blog.ourducks.com/blog/s/member/authEmail?email=" + email + "&authCode="
				+ authCode + "&memberId=" + joinId + "\" target=\"_blank\">사이트로 이동</a>";//meloporn.my.iu.gy //localhost:8081
		// 회원 인증 링크

		memberService.genEmailAuthed(joinId, "");
		mailService.send(email, "가입을 환영합니다.", sendBody); // 메일 발송

		return String.format("html:<script> alert('%s님 환영합니다. 해당 이메일로 인증 링크를 발송하였습니다.'); location.replace('../home/main'); </script>", name);
	}

	private String doActionJoin() {
		return "member/join.jsp";
	}

	@Override
	public String getControllerName() {
		return "member";
	}
}