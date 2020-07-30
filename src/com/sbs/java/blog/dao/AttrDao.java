package com.sbs.java.blog.dao;

import java.sql.Connection;

import com.sbs.java.blog.dto.Attr;
import com.sbs.java.blog.util.DBUtil;
import com.sbs.java.blog.util.SecSql;

public class AttrDao {
	private Connection dbConn;

	public AttrDao(Connection dbConn) {
		this.dbConn = dbConn;
	}

	public int setValue(String relTypeCode, int relId, String typeCode, String type2Code, String value) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO attr (regDate, updateDate, `relTypeCode`, `relId`, `typeCode`, `type2Code`, `value`)");
		sql.append("VALUES (NOW(), NOW(), ?, ?, ?, ?, ?)", relTypeCode, relId, typeCode, type2Code, value);
		sql.append("ON DUPLICATE KEY UPDATE");
		sql.append("updateDate = NOW()");
		sql.append(", `value` = ?", value);

		return DBUtil.update(dbConn, sql);
	}

	public Attr get(String relTypeCode, int relId, String typeCode, String type2Code) {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM attr");
		sql.append("WHERE 1");
		sql.append("AND `relTypeCode` = ?", relTypeCode);
		sql.append("AND `relId` = ?", relId);
		sql.append("AND `typeCode` = ?", typeCode);
		sql.append("AND `type2Code` = ?", type2Code);

		return new Attr(DBUtil.selectRow(dbConn, sql));
	}

	public String getValue(String relTypeCode, int relId, String typeCode, String type2Code) {
		SecSql sql = new SecSql();

		sql.append("SELECT `value`");
		sql.append("FROM attr");
		sql.append("WHERE 1");
		sql.append("AND `relTypeCode` = ?", relTypeCode);
		sql.append("AND `relId` = ?", relId);
		sql.append("AND `typeCode` = ?", typeCode);
		sql.append("AND `type2Code` = ?", type2Code);

		return DBUtil.selectRowStringValue(dbConn, sql);
	}

	public int remove(String relTypeCode, int relId, String typeCode, String type2Code) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM attr");
		sql.append("WHERE 1");
		sql.append("AND `relTypeCode` = ?", relTypeCode);
		sql.append("AND `relId` = ?", relId);
		sql.append("AND `typeCode` = ?", typeCode);
		sql.append("AND `type2Code` = ?", type2Code);

		return DBUtil.delete(dbConn, sql);
	}
}