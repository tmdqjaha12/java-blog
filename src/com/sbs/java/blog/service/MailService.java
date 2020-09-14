package com.sbs.java.blog.service;

import java.sql.Connection;

import com.sbs.java.blog.util.Util;

public class MailService {
	private String gmailId;
	private String gmailPw;
	private String from;
	private String fromName;

	public MailService(String gmailId, String gmailPw, String from, String fromName) {
		this.gmailId = gmailId;
		this.gmailPw = gmailPw;
		this.from = from;
		this.fromName = fromName;
	}

	public int send(String to, String title, String body) {
		System.out.println("---------------");
		System.out.println(gmailId);
		System.out.println(gmailPw);
		System.out.println(from);
		System.out.println(fromName);
		System.out.println(to);
		System.out.println(title);
		System.out.println(body);
		System.out.println("---------------");
		return Util.sendMail(gmailId, gmailPw, from, fromName, to, title, body);
	}
} 