package com.sbs.java.blog.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.exception.SQLErrorException;

public class DBUtil {
	private HttpServletRequest req;
	private HttpServletResponse resp;

	public DBUtil(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public Map<String, Object> selectRow(Connection dbConn, SecSql sql) {
		List<Map<String, Object>> rows = selectRows(dbConn, sql);

		if (rows.size() == 0) {
			return new HashMap<>();
		}

		return rows.get(0);
	}

	public List<Map<String, Object>> selectRows(Connection dbConn, SecSql sql) throws SQLErrorException {
		List<Map<String, Object>> rows = new ArrayList<>();

		PreparedStatement stmt = null;
		ResultSet rs = null;
		

		try {
			stmt = sql.getPreparedStatement(dbConn);
			rs = stmt.executeQuery();

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
			throw new SQLErrorException("SQL 예외, SQL : " + sql);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new SQLErrorException("SQL 예외, rs 닫기" + sql);
				}
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new SQLErrorException("SQL 예외, stmt 닫기" + sql);
				}
			}
		}

		return rows;
	}

	public int selectRowIntValue(Connection dbConn, SecSql sql) {
		Map<String, Object> row = selectRow(dbConn, sql);

		for (String key : row.keySet()) {
			return (int) row.get(key);
		}

		return -1;
	}

	public String selectRowStringValue(Connection dbConn, SecSql sql) {
		Map<String, Object> row = selectRow(dbConn, sql);

		for (String key : row.keySet()) {
			return (String) row.get(key);
		}

		return "";
	}

	public boolean selectRowBooleanValue(Connection dbConn, SecSql sql) {
		Map<String, Object> row = selectRow(dbConn, sql);

		for (String key : row.keySet()) {
			return ((int) row.get(key)) == 1;
		}

		return false;
	}

	public static int insert(Connection dbConn, SecSql sql) {
		int id = -1;

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = sql.getPreparedStatement(dbConn);
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();

			if (rs.next()) {
				id = rs.getInt(1);
			}

		} catch (SQLException e) {
			throw new SQLErrorException("SQL 예외, SQL : " + sql);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new SQLErrorException("SQL 예외, rs 닫기" + sql);
				}
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new SQLErrorException("SQL 예외, stmt 닫기" + sql);
				}
			}

		}

		return id;
	}

	public void deleteRow(Connection dbConn, SecSql sql) {
//		int affectedRows = 0;

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = sql.getPreparedStatement(dbConn);
			stmt.executeUpdate();
//			affectedRows = stmt.executeUpdate(sql);숫자
		} catch (SQLException e) {
			throw new SQLErrorException("SQL 예외, SQL : " + sql);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new SQLErrorException("SQL 예외, rs 닫기" + sql);
				}
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new SQLErrorException("SQL 예외, stmt 닫기" + sql);
				}
			}
		}
	}

	public static int update(Connection dbConn, SecSql sql) {
		int affectedRows = 0;

		PreparedStatement stmt = null;

		try {
			stmt = sql.getPreparedStatement(dbConn);
			affectedRows = stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLErrorException("SQL 예외, SQL : " + sql);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new SQLErrorException("SQL 예외, stmt 닫기, SQL : " + sql);
				}
			}
		}

		return affectedRows;
	}
}
