package com.sbs.java.blog.dao;

import java.sql.Connection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.Member;
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

	public Member login(String loginId, String loginPw) {
		SecSql secSql = new SecSql();
		
		secSql.append("SELECT *");
		secSql.append("FROM member");
		secSql.append("WHERE 1");
		secSql.append("AND loginId = ?", loginId);
		secSql.append("AND loginPw = ?", loginPw);
//		
//		secSql.append("SELECT EXISTS (");
//		secSql.append("SELECT * FROM member");
//		secSql.append("WHERE loginId = ?", loginId);
//		secSql.append("AND loginPw = ?", loginPw);
//		secSql.append(")AS success");
		if(new Member(dbUtil.selectRow(dbConn, secSql)).getId() == 0) {
			return null;
		}
		
		return new Member(dbUtil.selectRow(dbConn, secSql));
	}

	
}
