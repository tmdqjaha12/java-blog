package com.sbs.java.blog.controller;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.CateItem;
import com.sbs.java.blog.util.Util;

public class ArticleController extends Controller {
	public ArticleController(Connection dbConn, String actionMethodName, HttpServletRequest req,
			HttpServletResponse resp) {
		super(dbConn, actionMethodName, req, resp);
	}

	public void beforeAction() {
		super.beforeAction();
		// 이 메서드는 게시물 컨트롤러의 모든 액션이 실행되기 전에 실행된다.
		// 필요없다면 지워도 된다.
	}

	public String doAction() {
		switch (actionMethodName) {
		case "list":
			return doActionList(req, resp);
		case "detail":
			return doActionDetail(req, resp);
		case "doWrite":
			return doActionDoWrite(req, resp);
		case "doModify":
			return doActionDoModify(req, resp);
		case "doDelete":
			return doActionDoDelete(req, resp);
		}

		return "";
	}

	private String doActionDoDelete(HttpServletRequest req, HttpServletResponse resp) {
		if (Util.empty(req, "id")) {
			return "html:id를 입력해주세요.";
		}

		if (Util.isNum(req, "id") == false) {
			return "html:id를 정수로 입력해주세요.";
		}

		int id = Util.getInt(req, "id");
		
		articleService.doDeleteArticle(id);

		return "article/complDelete.jsp";
	}

	private String doActionDoModify(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		return "article/doModify.jsp";
	}

	private String doActionDoWrite(HttpServletRequest req, HttpServletResponse resp) {
		int id = -1;
		
		if(doWriteArticle(req, resp) == -1) {
			return "article/doWrite.jsp";
		}
		
		return "article/complWrite.jsp";
	}
	
	private int doWriteArticle(HttpServletRequest req, HttpServletResponse resp) {
		int id = -1;
		
		if (Util.empty(req, "title")) {
//			resp.getWriter().append("html:title를 입력해주세요.");
			return id;
		}
		
		if (Util.empty(req, "body")) {
//			resp.getWriter().append("body를 입력해주세요.");
			return id;
		}
		
		if (Util.empty(req, "cateItemId")) {
//			resp.getWriter().append("cateItemId를 입력해주세요.");
			return id;
		}
		
		if (Util.isNum(req, "cateItemId") == false) {
//			resp.getWriter().append("cateItemId를 정수로 입력해주세요.");
			return id;
		}
		
		String title = Util.getString(req, "title");
		String body = Util.getString(req, "body");
		int cateItemId = Util.getInt(req, "cateItemId");
		
		
		id = articleService.doWriteArticle(title, body, cateItemId);
		
		return id;
	}

	private String doActionDetail(HttpServletRequest req, HttpServletResponse resp) {
		if (Util.empty(req, "id")) {
			return "html:id를 입력해주세요.";
		}

		if (Util.isNum(req, "id") == false) {
			return "html:id를 정수로 입력해주세요.";
		}

		int id = Util.getInt(req, "id");

		Article article = articleService.getForPrintArticle(id);

		req.setAttribute("article", article);

		return "article/detail.jsp";
	}

	private String doActionList(HttpServletRequest req, HttpServletResponse resp) {
		int page = 1;

		if (!Util.empty(req, "page") && Util.isNum(req, "page")) {
			page = Util.getInt(req, "page");
		}

		int cateItemId = 0;

		if (!Util.empty(req, "cateItemId") && Util.isNum(req, "cateItemId")) {
			cateItemId = Util.getInt(req, "cateItemId");
		}
		
		String cateItemName = "전체";
		
		if ( cateItemId != 0 ) {
			CateItem cateItem = articleService.getCateItem(cateItemId);
			cateItemName = cateItem.getName();
		}
		req.setAttribute("cateItemName", cateItemName);
		

		String searchKeywordType = "";

		if (!Util.empty(req, "searchKeywordType")) {
			searchKeywordType = Util.getString(req, "searchKeywordType");
		}

		String searchKeyword = "";

		if (!Util.empty(req, "searchKeyword")) {
			searchKeyword = Util.getString(req, "searchKeyword");
		}

		int itemsInAPage = 10;
		int totalCount = articleService.getForPrintListArticlesCount(cateItemId, searchKeywordType, searchKeyword);
		int totalPage = (int) Math.ceil(totalCount / (double) itemsInAPage);

		req.setAttribute("totalCount", totalCount);
		req.setAttribute("totalPage", totalPage);
		req.setAttribute("page", page);

		List<Article> articles = articleService.getForPrintListArticles(page, itemsInAPage, cateItemId, searchKeywordType, searchKeyword);
		req.setAttribute("articles", articles);
		return "article/list.jsp";
	}
}
