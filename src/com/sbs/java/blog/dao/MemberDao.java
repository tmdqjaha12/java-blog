package com.sbs.java.blog.dao;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.util.DBUtil;
import com.sbs.java.blog.util.SecSql;

public class MemberDao extends Dao{
	private Connection dbConn;
	private DBUtil dbUtil;

	public MemberDao(Connection dbConn, HttpServletRequest req, HttpServletResponse resp) {
		super(req, resp);
		this.dbConn = dbConn;
		dbUtil = new DBUtil(req, resp);
	}

	public int join(String loginId, String name, String nickname, String loginPw) {
		SecSql secSql = new SecSql();
		
		secSql.append("INSERT INTO member");
		secSql.append("SET regDate = NOW()");
		secSql.append(", updateDate = NOW()");
		secSql.append(", memberStatus = '1'");
		secSql.append(", loginId = ?", loginId);
		secSql.append(", name = ?", name);
		secSql.append(", nickname = ?", nickname);
		secSql.append(", loginPw = ?", loginPw);
		
		return dbUtil.insert(dbConn, secSql);
	}

	public int login(String loginId, String loginPw) {
		SecSql secSql = new SecSql();
		
//		secSql.append("SELECT *");
//		secSql.append("FROM member");
//		secSql.append("WHERE loginId = ?", loginId);
//		secSql.append("AND loginPw = ?", loginPw);
		
		secSql.append("SELECT EXISTS (");
		secSql.append("SELECT * FROM member");
		secSql.append("WHERE loginId = ?", loginId);
		secSql.append("AND loginPw = ?", loginPw);
		secSql.append(")AS success");
		
		return dbUtil.exists(dbConn, secSql);
	}

}
