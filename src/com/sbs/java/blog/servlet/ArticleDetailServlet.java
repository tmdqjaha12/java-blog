package com.sbs.java.blog.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.util.DBUtil;

@WebServlet("/s/article/detail")
public class ArticleDetailServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");

		// DB 커넥터 로딩 시작
		String driverName = "com.mysql.cj.jdbc.Driver";

		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			System.err.printf("[ClassNotFoundException 예외, %s]\n", e.getMessage());
			response.getWriter().append("DB 드라이버 클래스 로딩 실패");
			return;
		}
		// DB 커넥터 로딩 성공

		// DB 접속 시작
		String url = "jdbc:mysql://site41.iu.gy:3306/site41?serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true";
		String user = "site41";
		String password = "sbs123414";

		try (Connection connection = DriverManager.getConnection(url, user, password)) {
			// DB 접속 성공

			int id = Integer.parseInt(request.getParameter("id"));
			Article article = getArticle(connection, id);

			request.setAttribute("article", article);
			request.getRequestDispatcher("/jsp/article/detail.jsp").forward(request, response);

		} catch (SQLException e) {
			System.err.printf("[SQLException 예외, %s]\n", e.getMessage());
			response.getWriter().append("DB연결 실패");
			return;
		}
	}

	private Article getArticle(Connection conn, int id) {
		String sql = "";

		sql += String.format("SELECT * ");
		sql += String.format("FROM article ");
		sql += String.format("WHERE id = %d ", id);

		Map<String, Object> row = DBUtil.selectRow(conn, sql);

		return new Article(row);
	}
}
