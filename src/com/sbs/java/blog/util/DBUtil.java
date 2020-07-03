package com.sbs.java.blog.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DBUtil {
	private HttpServletRequest req;
	private HttpServletResponse resp;

	public DBUtil(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public Map<String, Object> selectRow(Connection dbConn, String sql) {
		List<Map<String, Object>> rows = selectRows(dbConn, sql);

		if (rows.size() == 0) {
			return new HashMap<>();
		}

		return rows.get(0);
	}

	public List<Map<String, Object>> selectRows(Connection dbConn, String sql) {
		List<Map<String, Object>> rows = new ArrayList<>();

		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = dbConn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData metaData = rs.getMetaData();
			int columnSize = metaData.getColumnCount();

			while (rs.next()) {
				Map<String, Object> row = new HashMap<>();

				for (int columnIndex = 0; columnIndex < columnSize; columnIndex++) {
					String columnName = metaData.getColumnName(columnIndex + 1);
					Object value = rs.getObject(columnName);

					if (value instanceof Long) {
						int numValue = (int) (long) value;
						row.put(columnName, numValue);
					} else if (value instanceof Timestamp) {
						String dateValue = value.toString();
						dateValue = dateValue.substring(0, dateValue.length() - 2);
						row.put(columnName, dateValue);
					} else {
						row.put(columnName, value);
					}
				}

				rows.add(row);
			}
		} catch (SQLException e) {
			Util.printEx("SQL 예외, SQL : " + sql, resp, e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					Util.printEx("SQL 예외, stmt 닫기", resp, e);
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					Util.printEx("SQL 예외, rs 닫기", resp, e);
				}
			}
		}

		return rows;
	}

	public int selectRowIntValue(Connection dbConn, String sql) {
		Map<String, Object> row = selectRow(dbConn, sql);

		for (String key : row.keySet()) {
			return (int) row.get(key);
		}

		return -1;
	}

	public String selectRowStringValue(Connection dbConn, String sql) {
		Map<String, Object> row = selectRow(dbConn, sql);

		for (String key : row.keySet()) {
			return (String) row.get(key);
		}

		return "";
	}

	public boolean selectRowBooleanValue(Connection dbConn, String sql) {
		Map<String, Object> row = selectRow(dbConn, sql);

		for (String key : row.keySet()) {
			return ((int) row.get(key)) == 1;
		}

		return false;
	}
}
