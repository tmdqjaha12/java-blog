package com.sbs.java.blog.session;

import com.sbs.java.blog.dto.Member;

public class Session {
	private static Member loginedMember;

	public static Member getLoginedMember() {
		return loginedMember;
	}

	public static void setLoginedMember(Member loginedMember) {
		loginedMember = loginedMember;
	}

	public boolean isLogined() {
		return loginedMember != null;
	}
}