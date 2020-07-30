package com.sbs.java.blog.service;

import java.sql.Connection;
import java.util.UUID;

import com.sbs.java.blog.dao.MemberDao;
import com.sbs.java.blog.dto.Member;
import com.sbs.java.blog.util.Util;

public class MemberService extends Service {
	private MailService mailService;
	private MemberDao memberDao;
	private AttrService attrService;

	public MemberService(Connection dbConn, MailService mailService, AttrService attrService) {
		memberDao = new MemberDao(dbConn);
		this.mailService = mailService;
		this.attrService = attrService;
	}

	public int join(String loginId, String loginPw, String name, String nickname, String email) {
		int id = memberDao.join(loginId, loginPw, name, nickname, email);

		mailService.send(email, "가입을 환영합니다.", "<a href=\"https://meloporn.my.iu.gy/\" target=\"_blank\">사이트로 이동</a>");

		return id;
	}

	public boolean isJoinableLoginId(String loginId) {
		return memberDao.isJoinableLoginId(loginId);
	}

	public boolean isJoinableNickname(String nickname) {
		return memberDao.isJoinableNickname(nickname);
	}

	public boolean isJoinableEmail(String email) {
		return memberDao.isJoinableEmail(email);
	}
	
	public int getMemberIdByLoginIdAndLoginPw(String loginId, String loginPw) {
		return memberDao.getMemberIdByLoginIdAndLoginPw(loginId, loginPw);
	}

	public Member getMemberById(int id) {
		return memberDao.getMemberById(id);
	}

	public String getStringForFindId(String name, String email) {
		return memberDao.getStringForFindId(name, email);
	}

	public boolean getBooleanForFindPw(String loginId, String name, String email) {
		return memberDao.getBooleanForFindPw(loginId, name, email);
	}
	
	public void updateImshiPw(String loginId, String name, String email, String imshiPw) {
		memberDao.updateImshiPw(loginId, name, email, imshiPw);
	}

	public int getTrueAuthCode(int id) {
		return memberDao.getTrueAuthCode(id);
	}

	public int getTrueMailAuthCode(int id) {
		return memberDao.getTrueMailAuthCode(id);
	}
	
	public String genEmailAuthCode(int actorId) {
		String authCode = Util.getRandomPassword(5);
		System.out.println(authCode);
		attrService.setValue("member__" + actorId + "__extra__emailAuthCode", authCode);

		return authCode;
	}//가입시 혹은 이메일 인증코드 다시보내기 버튼 누를 때 마다 갱신
	
	public String genEmailAuthed(int actorId) {
		String authCode = Util.getRandomPassword(5);
		System.out.println(authCode);
		attrService.setValue("member__" + actorId + "__extra__emailAuthed", authCode);

		return authCode;
	}// 고객이 이메일의 링크를 클릭시 이 데이터가 생성
	public String genUseTempPassword(int actorId) {
		String authCode = Util.getRandomPassword(5);
		System.out.println(authCode);
		attrService.setValue("member__" + actorId + "__extra__useTempPassword", authCode);

		return authCode;
	}//회원이 패스워드 변경하면 삭제되어야 함
	public String genLastPasswordChangeDate(int actorId) {
		String authCode = Util.getRandomPassword(5);
		System.out.println(authCode);
		attrService.setValue("member__" + actorId + "__extra__lastPasswordChangeDate", authCode);

		return authCode;
	}//가입 혹은 패스워드 변경시 갱신
	
	public String genModifyPrivateAuthCode(int actorId) {
		String authCode = UUID.randomUUID().toString();
		System.out.println(authCode);
		attrService.setValue("member__" + actorId + "__extra__modifyPrivateAuthCode", authCode);

		return authCode;
	}

	public boolean isValidModifyPrivateAuthCode(int actorId, String authCode) {
		String authCodeOnDB = attrService.getValue("member__" + actorId + "__extra__modifyPrivateAuthCode");

		return authCodeOnDB.equals(authCode);
	}

	public void modify(int actorId, String loginPw) {
		memberDao.modify(actorId, loginPw);
	}
}
