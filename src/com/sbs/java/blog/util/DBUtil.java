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

public class DBUtil {
	public static Map<String, Object> selectRow(Connection dbConn, String sql) {
		List<Map<String, Object>> rows = selectRows(dbConn, sql);

		if (rows.size() == 0) {
			return new HashMap<>();
		}

		return rows.get(0);
	}

	public static List<Map<String, Object>> selectRows(Connection dbConn, String sql) {
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
			System.err.println("[SQLException 예외]");
			System.err.println("msg : " + e.getMessage());
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					System.err.println("[SQLException 예외]");
					System.err.println("msg : " + e.getMessage());
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					System.err.println("[SQLException 예외]");
					System.err.println("msg : " + e.getMessage());
				}
			}
		}

		return rows;
	}

	public static int selectRowIntValue(Connection dbConn, String sql) {
		Map<String, Object> row = selectRow(dbConn, sql);

		for (String key : row.keySet()) {
			return (int) row.get(key);
		}

		return -1;
	}

	public static String selectRowStringValue(Connection dbConn, String sql) {
		Map<String, Object> row = selectRow(dbConn, sql);

		for (String key : row.keySet()) {
			return (String) row.get(key);
		}

		return "";
	}

	public static Boolean selectRowBooleanValue(Connection dbConn, String sql) {
		Map<String, Object> row = selectRow(dbConn, sql);

		for (String key : row.keySet()) {
			return ((int) row.get(key)) == 1;
		}

		return false;
	}
}
