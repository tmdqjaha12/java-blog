package com.sbs.java.blog.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuth extends Authenticator {

	PasswordAuthentication pa;

	public MailAuth(String mailId, String mailPw) {

		pa = new PasswordAuthentication(mailId, mailPw);
	}

	public PasswordAuthentication getPasswordAuthentication() {
		return pa;
	}
}