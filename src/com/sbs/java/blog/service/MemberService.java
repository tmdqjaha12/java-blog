package com.sbs.java.blog.service;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

import org.apache.tomcat.util.buf.UDecoder;

import com.sbs.java.blog.dao.MemberDao;
import com.sbs.java.blog.dto.Member;
import com.sbs.java.blog.util.Util;

public class MemberService extends Service {
//	private MailService mailService;
	private MemberDao memberDao;
	private AttrService attrService;

	public MemberService(Connection dbConn, AttrService attrService) {//MailService mailService,
		memberDao = new MemberDao(dbConn);
//		this.mailService = mailService;
		this.attrService = attrService;
	}

	public int join(String loginId, String loginPw, String name, String nickname, String email) {
		int id = memberDao.join(loginId, loginPw, name, nickname, email);

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
	
	public int getMemberIdByLoginIdAndNameAndEmail(String loginId, String name, String email) {
		return memberDao.getMemberIdByLoginIdAndNameAndEmail(loginId, name, email);
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
	
	public String genEmailAuthCode(int actorId) {
		String authCode = UUID.randomUUID().toString();
//		String authCode = Util.getRandomPassword(5);
		attrService.setValue("member__" + actorId + "__extra__emailAuthCode", authCode);
		return authCode;
	}// 가입시 혹은 이메일 인증코드 다시보내기 버튼 누를 때 마다 갱신 Code
	
	public String genEmailAuthed(int actorId, String email) {
		attrService.setValue("member__" + actorId + "__extra__emailAuthed", email);
		return email;
	}// 이메일인증__고객이 이메일의 링크를 클릭시 이 데이터가 생성 Code
	
	public void genUseTempPassword(String loginId, String useTemp) {
		attrService.setValue("member__" + loginId + "__extra__useTempPassword", useTemp);
	}// 임시패스워드 & 회원이 패스워드 변경하면 삭제되어야 함 Code 1 0
	
	public String genLastPasswordChangeDate(int actorId) {
		String currentTime = Util.getRegeDateNow();
		attrService.setValue("member__" + actorId + "__extra__lastPasswordChangeDate", currentTime);

		return currentTime;
	}// 가입 혹은 패스워드 변경시 갱신 Code
	
	public String genModifyPrivateAuthCode(int actorId) {
		String authCode = UUID.randomUUID().toString();
		attrService.setValue("member__" + actorId + "__extra__modifyPrivateAuthCode", authCode);
		
		return authCode;
	}// 회원정보 수정시 Code
	
	//get▼
	public boolean isValidModifyPrivateAuthCode(int actorId, String authCode) {
		String authCodeOnDB = attrService.getValue("member__" + actorId + "__extra__modifyPrivateAuthCode");

		return authCodeOnDB.equals(authCode);
	}// 회원정보 수정 AuthCode Fact
	
	public boolean isValidEmailAuthCode(String actorId, String authCode) {
		String authCodeOnDB = attrService.getValue("member__" + actorId + "__extra__emailAuthCode");
		return authCodeOnDB.equals(authCode);
	}// 이메일 인증 AuthCode Fact
	
	public boolean isValidEmailAuthed(int actorId) {
		String authCodeOnDB = attrService.getValue("member__" + actorId + "__extra__emailAuthed");
		return authCodeOnDB.length() > 0;
	}// 이메일 인증 가져오기
	
	public String getEmailAuthed(int actorId) {
		String authCodeOnDB = attrService.getValue("member__" + actorId + "__extra__emailAuthed");
		return authCodeOnDB;
	}// 이메일 가져오기
	
	public boolean isValidUseTempPassword(int actorId) {
		String authCodeOnDB = attrService.getValue("member__" + actorId + "__extra__useTempPassword");
		if(authCodeOnDB.equals("1")) {
			return true;
		}
		return false;
	}// 임시패스워드 여부 가져오기
	
	public String isValidLastPasswordChangeDate(int actorId) {
		String authCodeOnDB = attrService.getValue("member__" + actorId + "__extra__lastPasswordChangeDate");
		return authCodeOnDB;
	}// 패스워드 마지막 변경 날짜 가져오기
	
	//remove▼
	public int removeUseTempPassword(int actorId) {
		int authCodeOnDB = attrService.remove("member__" + actorId + "__extra__useTempPassword");
		return 0;
	}// 임시패스워드 삭제
	
	public void modify(int actorId, String loginPw) {
		memberDao.modify(actorId, loginPw);
	}

	public void memberModify(int actorId, String nickname, String email) {
		memberDao.memberModify(actorId, nickname, email);
	}

}
