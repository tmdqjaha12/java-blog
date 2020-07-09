package com.sbs.java.blog.dao;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.util.DBUtil;

public class MemberDao extends Dao{
	private Connection dbConn;
	private DBUtil dbUtil;

	public MemberDao(Connection dbConn, HttpServletRequest req, HttpServletResponse resp) {
		super(req, resp);
		this.dbConn = dbConn;
		dbUtil = new DBUtil(req, resp);
	}

	public int join(String loginId, String name, String nickname, String loginPw) {
		String sql = "";
		
		sql += String.format("INSERT INTO `member` ");
		sql += String.format("SET regDate = NOW(), ");
		sql += String.format("updateDate = NOW(), ");
		sql += String.format("memberStatus = 1, ");
		sql += String.format("`loginId` = '%s' ,", loginId);
		sql += String.format("`name` = '%s' ,", name);
		sql += String.format("`nickname` = '%s' ,", nickname);
		sql += String.format("`loginPw` = '%s' ", loginPw);
//		System.out.println("hi");
		
		return dbUtil.insert(dbConn, sql);
	}

}
