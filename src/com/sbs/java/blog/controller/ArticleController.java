package com.sbs.java.blog.controller;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.ArticleReply;
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
			return doActionList();
		case "detail":
			return doActionDetail();
		case "doWrite":
			return doActionDoWrite();
		case "write":
			return doActionWrite();
		case "doModify":
			return doActionDoModify();
		case "modify":
			return doActionModify();
		case "doDelete":
			return doActionWrite();
		case "doReply":
			return doActionReply();
		case "doReplyDelete":
			return doActionReplyDelete();
		}
		return "";
	}
	
	private String doActionReplyDelete() {
		int replyId = Util.getInt(req, "replyId");
		int id = Util.getInt(req, "id");
		articleService.deleteReply(replyId);

		return "html:<script> alert('댓글 삭제 완료'); location.replace('detail?id=" + id + "'); </script>";
	}

	private String doActionReply() {
		int memberId = (int)req.getAttribute("loginedMemberId");
		int articleId = Util.getInt(req, "id");
		String body = Util.getString(req, "body");
		
		articleService.reply(memberId, articleId, body);
		
		return "html:<script> alert('작성 완료!'); location.replace('detail?id=" + articleId + "'); </script>";
	}

	private String doActionModify() {
		int articleId = Util.getInt(req, "articleId");
		String regDate = Util.getString(req, "regDate");

		req.setAttribute("articleId", articleId);
		req.setAttribute("regDate", regDate);
		
		return "article/modify.jsp";
	}

	private String doActionDoModify() {
		int memberId = (int)req.getAttribute("loginedMemberId");
		String title = Util.getString(req, "title");
		String body = Util.getString(req, "body");
		String regDate = Util.getString(req, "regDate");
		int cateItemId = Util.getInt(req, "cateItemId");
		int articleId = Util.getInt(req, "articleId");
		
		int id = articleService.modify(memberId, articleId, cateItemId, title, body, regDate);
		
		return "html:<script> alert('" + id + "번 게시물이 수정되었습니다.'); location.replace('list'); </script>";
	}

	private String doActionWrite() {		
		return "article/write.jsp";
	}

	private String doActionDoWrite() {
		String title = req.getParameter("title");
		String body = req.getParameter("body");
		int cateItemId = Util.getInt(req, "cateItemId");

		int loginedMemberId = (int)req.getAttribute("loginedMemberId");
		
		int id = articleService.write(cateItemId, title, body, loginedMemberId);
		
		return "html:<script> alert('" + id + "번 게시물이 생성되었습니다.'); location.replace('list'); </script>";
	}

	private String doActionDetail() {
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
		
		List<ArticleReply> articleReplies = articleService.getForPrintReplies(id);
		req.setAttribute("articleReplies", articleReplies);
		
		String memberNickName = "";
		Map<Integer, String> memberNickNames = new HashMap<Integer, String>();
		for(ArticleReply articleReply : articleReplies) {
			memberNickName = articleService.getForPrintMemberNickName(articleReply.getMemberId());
			memberNickNames.put(articleReply.getMemberId(), memberNickName);
		}
		req.setAttribute("memberNickNames", memberNickNames);

		return "article/detail.jsp";
	}

	private String doActionList() {
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
		req.setAttribute("cateItemId", cateItemId);
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

		List<Article> articles = articleService.getForPrintListArticles(page, itemsInAPage, cateItemId,
				searchKeywordType, searchKeyword);
		req.setAttribute("articles", articles);
		
		
		String memberNickName = "";
		Map<Integer, String> memberNickNames = new HashMap<Integer, String>();
		for(Article article : articles) {
			memberNickName = articleService.getForPrintMemberNickName(article.getMemberId());
			memberNickNames.put(article.getMemberId(), memberNickName);
		}
		req.setAttribute("memberNickNames", memberNickNames);
		
		return "article/list.jsp";
	}

	@Override
	public String getControllerName() {
		return "article";
	}
}