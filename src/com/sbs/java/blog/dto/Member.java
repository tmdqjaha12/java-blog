package com.sbs.java.blog.dto;

import java.util.Map;

public class Member extends Dto {
	private String updateDate;
	private String loginId;
	private String name;
	private String nickname;
	private String loginPw;
	private String loginPwConfirm;

	public Member(Map<String, Object> row) {
		super(row);

		this.updateDate = (String) row.get("updateDate");
		this.loginId = (String) row.get("loginId");
		this.name = (String) row.get("name");
		this.nickname = (String) row.get("nickname");
		this.loginPw = (String) row.get("loginPw");
		this.loginPwConfirm = (String) row.get("loginPwConfirm");
	}
	
	@Override
	public String toString() {
		return "Member [updateDate=" + updateDate + ", loginId=" + loginId + ", name=" + name + ", nickname="
				+ nickname + ", loginPw=" + loginPw + ", loginPwConfirm=" + loginPwConfirm + ", dto=" + super.toString() + "]";
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getLoginPw() {
		return loginPw;
	}

	public void setLoginPw(String loginPw) {
		this.loginPw = loginPw;
	}

	public String getLoginPwConfirm() {
		return loginPwConfirm;
	}

	public void setLoginPwConfirm(String loginPwConfirm) {
		this.loginPwConfirm = loginPwConfirm;
	}

	

}
