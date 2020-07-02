package com.sbs.java.blog.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.controller.ArticleController;
import com.sbs.java.blog.controller.Controller;
import com.sbs.java.blog.controller.HomeController;
import com.sbs.java.blog.controller.MemberController;
import com.sbs.java.blog.util.Util;

@WebServlet("/s/*")
public class DispatcherServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");

		// DB 커넥터 로딩 시작
		String driverName = "com.mysql.cj.jdbc.Driver";

		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			System.err.printf("[ClassNotFoundException 예외, %s]\n", e.getMessage());
			resp.getWriter().append("DB 드라이버 클래스 로딩 실패");
			return;
		}
		// DB 커넥터 로딩 성공

		// DB 접속 시작
		String url = "jdbc:mysql://site41.iu.gy:3306/site41?serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeBehavior=convertToNull";
		String user = "site41";
		String password = "sbs123414";

		Connection dbConn = null;

		try {
			// DB 접속 성공
			dbConn = DriverManager.getConnection(url, user, password);

			String contextPath = req.getContextPath();
			String requestURI = req.getRequestURI();
			String actionStr = requestURI.replace(contextPath + "/s/", "");
			String[] actionStrBits = actionStr.split("/");

			String controllerName = actionStrBits[0];
			String actionMethodName = actionStrBits[1];

			Controller controller = null;
			
			switch (controllerName) {
			case "article":
				controller = new ArticleController(dbConn);
				break;
			case "member":
				controller = new MemberController();
				break;
			case "home":
				controller = new HomeController();
				break;
			}

			if (controller != null) {
				String actionResult = controller.doAction(actionMethodName, req, resp);
				if (actionResult.equals("")) {
					resp.getWriter().append("ERROR, CODE 1");
				} else if (actionResult.endsWith(".jsp")) {
					String viewPath = "/jsp/" + actionResult;
					req.getRequestDispatcher(viewPath).forward(req, resp);
				} else if (actionResult.startsWith("plain:")) {
					resp.getWriter().append(actionResult.substring(6));
				} else {
					
				}
			} else {
				resp.getWriter().append("존재하지 않는 페이지 입니다.");
			}
		} catch (SQLException e) {
			Util.printEx("SQL 예외(커넥션 열기)", resp, e);
		} catch (Exception e) {
			Util.printEx("기타 예외", resp, e);
		} finally {
			if (dbConn != null) {
				try {
					dbConn.close();
				} catch (SQLException e) {
					Util.printEx("SQL 예외(커넥션 닫기)", resp, e);
				}
			}
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
