package com.sbs.java.blog.controller;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.ArticleReply;
import com.sbs.java.blog.dto.CateItem;
import com.sbs.java.blog.dto.Member;
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
		case "write":
			return doActionWrite(req, resp);
		case "doWrite":
			return doActionDoWrite(req, resp);
		case "modify":
			return doActionModify(req, resp);
		case "doModify":
			return doActionDoModify(req, resp);
		case "doDelete":
			return doActionDoDelete(req, resp);
		case "doComment":
			return doActionDoComment(req, resp);
		}
		return "";
	}

	private String doActionDoComment(HttpServletRequest req, HttpServletResponse resp) {
		String articleId = Util.getString(req, "articleId");
		String memberId = Util.getString(req, "memberId");
		String body = Util.getString(req, "body");

		int id = articleService.comment(articleId, memberId, body);

		return "html:<script> location.replace('detail?id=" + articleId + "'); </script>";
	}

//	private String doActionList(HttpServletRequest req, HttpServletResponse resp) {
//		String articleId = Util.getString(req, "articleId");
//		String memberId = Util.getString(req, "memberId");
//		String body = Util.getString(req, "body");
//		int id = articleService.comment(articleId, memberId, body);
//		
//		int page = 1;
//
//		if (!Util.empty(req, "page") && Util.isNum(req, "page")) {
//			page = Util.getInt(req, "page");
//		}
//
//		int itemsInAPage = 5;
//		int totalCount = articleService.getForPrintListCommentCount(cateItemId);
//		int totalPage = (int) Math.ceil(totalCount / (double) itemsInAPage);
//
//		req.setAttribute("totalCount", totalCount);
//		req.setAttribute("totalPage", totalPage);
//		req.setAttribute("page", page);
//
//		List<Article> articles = articleService.getForPrintListArticles(page, itemsInAPage, cateItemId, searchKeywordType, searchKeyword);
//		req.setAttribute("articles", articles);
//		return "article/list.jsp";
//	}

	private String doActionDoModify(HttpServletRequest req, HttpServletResponse resp) {
		int id = Util.getInt(req, "id");
		int cateItemId = Util.getInt(req, "cateItemId");
		String regDate = Util.getString(req, "regDate");
		String title = Util.getString(req, "title");
		String body = Util.getString(req, "body");

		int id_ = articleService.modify(id, cateItemId, regDate, title, body);

//		System.out.println(id_);

		return "html:<script> alert('수정완료'); location.replace('list'); </script>";
	}

	private String doActionModify(HttpServletRequest req, HttpServletResponse resp) {

		int id = Util.getInt(req, "id");
		int cateItemId = Util.getInt(req, "cateItemId");
		String regDate = Util.getString(req, "regDate");
		String title = Util.getString(req, "title");
		String body = Util.getString(req, "body");

		req.setAttribute("id", id);
		req.setAttribute("cateItemId", cateItemId);
		req.setAttribute("regDate", regDate);
		req.setAttribute("title", title);
		req.setAttribute("body", body);

		return "article/modify.jsp";
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

		return "html:<script> alert('삭제 완료'); location.replace('list'); </script>";
	}

	private String doActionWrite(HttpServletRequest req, HttpServletResponse resp) {
		return "article/write.jsp";
	}

	private String doActionDoWrite(HttpServletRequest req, HttpServletResponse resp) {
		String title = req.getParameter("title");
		String body = req.getParameter("body");
		int cateItemId = Util.getInt(req, "cateItemId");
		int loginedMemberId = 0;
		if(session.getAttribute("loginedMemberId") != null) {
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
		}

		int id = articleService.write(loginedMemberId, cateItemId, title, body);

		return "html:<script> alert('" + id + "번 게시물이 생성되었습니다.'); location.replace('list'); </script>";
	}

	private String doActionDetail(HttpServletRequest req, HttpServletResponse resp) {		
		if (Util.empty(req, "id")) {
			return "html:id를 입력해주세요.";
		}

		if (Util.isNum(req, "id") == false) {
			return "html:id를 정수로 입력해주세요.";
		}

		int id = Util.getInt(req, "id");
		articleService.increaseHit(id);

		Article article = articleService.getForPrintArticle(id);

		req.setAttribute("article", article);

		List<ArticleReply> articleReplies = articleService.commentList(id);

		req.setAttribute("articleReplies", articleReplies);

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

		if (cateItemId != 0) {
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

		System.out.println("page : " + page);
		System.out.println("itemsInAPage : " + itemsInAPage);
		System.out.println("cateItemId : " + cateItemId);
		System.out.println("searchKeywordType : " + searchKeywordType);
		System.out.println("searchKeyword : " + searchKeyword);
		
		
		List<Article> articles = articleService.getForPrintListArticles(page, itemsInAPage, cateItemId,
				searchKeywordType, searchKeyword);
		req.setAttribute("articles", articles);
		return "article/list.jsp";
	}
}
