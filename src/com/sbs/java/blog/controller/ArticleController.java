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
			return doActionDoDelete();
		case "doWriteReply":
			return doActionDoWriteReply();
		case "doModifyReply":
			return doActionDoModifyReply();
		case "modifyReply":
			return doActionModifyReply();
		case "doDeleteReply":
			return doActionDoDeleteReply();
		}
		return "";
	}
	
	


	private String doActionDoDeleteReply() {
		if (Util.empty(req, "id")) {
			return "html:id를 입력해주세요.";
		}

		if (Util.isNum(req, "id") == false) {
			return "html:id를 정수로 입력해주세요.";
		}

		int id = Util.getInt(req, "id");

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		Map<String, Object> getReplyCheckRsDeleteAvailableRs = articleService.getReplyCheckRsDeleteAvailable(id, loginedMemberId);

		if (Util.isSuccess(getReplyCheckRsDeleteAvailableRs) == false) {
			return "html:<script> alert('" + getReplyCheckRsDeleteAvailableRs.get("msg") + "'); history.back(); </script>";
		}

		articleService.deleteArticleReply(id);

		String redirectUrl = Util.getString(req, "redirectUrl", "list");

		return "html:<script> alert('" + id + "번 댓글이 삭제되었습니다.'); location.replace('" + redirectUrl + "'); </script>";
	}
	
	private String doActionDoModifyReply() {
		if (Util.empty(req, "id")) {
			return "html:id를 입력해주세요.";
		}

		if (Util.isNum(req, "id") == false) {
			return "html:id를 정수로 입력해주세요.";
		}

		int id = Util.getInt(req, "id");
		String body = Util.getString(req, "body");

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		Map<String, Object> getReplyCheckRsModifyAvailableRs = articleService.getReplyCheckRsModifyAvailable(id,
				loginedMemberId);

		if (Util.isSuccess(getReplyCheckRsModifyAvailableRs) == false) {
			return "html:<script> alert('" + getReplyCheckRsModifyAvailableRs.get("msg")
					+ "'); history.back(); </script>";
		}

		articleService.modifyArticleReply(id, body);

		String redirectUrl = Util.getString(req, "redirectUri", "list");

		redirectUrl = Util.getNewUrl(redirectUrl, "lastWorkArticleReplyId", id + "");

		return "html:<script> alert('" + id + "번 댓글이 수정되었습니다.'); location.replace('" + redirectUrl + "'); </script>";
	}
	
	private String doActionModifyReply() {
		if (Util.empty(req, "id")) {
			return "html:id를 입력해주세요.";
		}

		if (Util.isNum(req, "id") == false) {
			return "html:id를 정수로 입력해주세요.";
		}

		int id = Util.getInt(req, "id");

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		ArticleReply articleReply = articleService.getArticleReply(id);
		req.setAttribute("articleReply", articleReply);

		Article article = articleService.getForPrintArticle(articleReply.getArticleId(), loginedMemberId);
		req.setAttribute("article", article);

		return "article/modifyReply.jsp";
	}


	private String doActionDoWriteReply() {
		if (Util.empty(req, "articleId")) {
			return "html:articleId를 입력해주세요.";
		}

		if (Util.isNum(req, "articleId") == false) {
			return "html:articleId를 정수로 입력해주세요.";
		}

		int articleId = Util.getInt(req, "articleId");

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		String body = Util.getString(req, "body");
		String redirectUrl = Util.getString(req, "redirectUrl");

		int id = articleService.writeArticleReply(articleId, loginedMemberId, body);
		
		redirectUrl = Util.getNewUrl(redirectUrl, "generatedArticleReplyId", id + "");

		return "html:<script> alert('" + id + "번 댓글이 작성되었습니다.'); location.replace('" + redirectUrl + "'); </script>";
	}

	
	private String doActionDoDelete() {
		if (Util.empty(req, "id")) {
			return "html:id를 입력해주세요.";
		}

		if (Util.isNum(req, "id") == false) {
			return "html:id를 정수로 입력해주세요.";
		}
		
		int id = Util.getInt(req, "id");
		
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
//		System.out.println(loginedMemberId);
		
		Map<String, Object> getCheckRsDeleteAvailableRs = articleService.getCheckRsDeleteAvailable(id, loginedMemberId);

		if (Util.isSuccess(getCheckRsDeleteAvailableRs) == false) {
			return "html:<script> alert('" + getCheckRsDeleteAvailableRs.get("msg") + "'); history.back(); </script>";
		}
		
		articleService.deleteArticle(id);
		
		return "html:<script> alert('" + id + "번 게시물이 삭제되었습니다.'); location.replace('list'); </script>";
	}

	private String doActionModify() {
		if (Util.empty(req, "id")) {
			return "html:id를 입력해주세요.";
		}

		if (Util.isNum(req, "id") == false) {
			return "html:id를 정수로 입력해주세요.";
		}

		int id = Util.getInt(req, "id");

		articleService.increaseHit(id);

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		Article article = articleService.getForPrintArticle(id, loginedMemberId);

		req.setAttribute("article", article);

		return "article/modify.jsp";
	}

	private String doActionDoModify() {
		if (Util.empty(req, "id")) {
			return "html:id를 입력해주세요.";
		}

		if (Util.isNum(req, "id") == false) {
			return "html:id를 정수로 입력해주세요.";
		}

		int id = Util.getInt(req, "id");

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		Map<String, Object> getCheckRsModifyAvailableRs = articleService.getCheckRsModifyAvailable(id, loginedMemberId);

		if (Util.isSuccess(getCheckRsModifyAvailableRs) == false) {
			return "html:<script> alert('" + getCheckRsModifyAvailableRs.get("msg") + "'); history.back(); </script>";
		}

		int cateItemId = Util.getInt(req, "cateItemId");
		String title = Util.getString(req, "title");
		String body = Util.getString(req, "body");

		articleService.modifyArticle(id, cateItemId, title, body);

		return "html:<script> alert('" + id + "번 게시물이 수정되었습니다.'); location.replace('detail?id=" + id + "'); </script>";
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
		
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		Article article = articleService.getForPrintArticle(id, loginedMemberId);
		
		req.setAttribute("article", article);
		

		List<ArticleReply> articleReplies = articleService.getForPrintArticleReplies(id, loginedMemberId);

		req.setAttribute("articleReplies", articleReplies);
		
//		List<ArticleReply> articleReplies = articleService.getForPrintReplies(id);
//		req.setAttribute("articleReplies", articleReplies);
//		
//		String memberNickName = "";
//		Map<Integer, String> memberNickNames = new HashMap<Integer, String>();
//		for(ArticleReply articleReply : articleReplies) {
//			memberNickName = articleService.getForPrintMemberNickName(articleReply.getMemberId());
//			memberNickNames.put(articleReply.getMemberId(), memberNickName);
//		}
//		req.setAttribute("memberNickNames", memberNickNames);
//		
		
		return "article/detail.jsp";
	}

	private String doActionList() {
		long startTime = System.nanoTime();
		
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
		
		int nowPage = page;
		
		if(page % 5 != 0) {
			page = page/5;
			page = (page*5)+1;
		} else if(page % 5 == 0) {
			page = page - 4;
		}
		
		req.setAttribute("page", page);
		
		req.setAttribute("totalCount", totalCount);
		req.setAttribute("totalPage", totalPage);
		req.setAttribute("cPagedoReply", page);
		
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		List<Article> articles = articleService.getForPrintListArticles(loginedMemberId, nowPage, itemsInAPage, cateItemId,
				searchKeywordType, searchKeyword);
		req.setAttribute("articles", articles);
		
		
		String memberNickName = "";
		Map<Integer, String> memberNickNames = new HashMap<Integer, String>();
		for(Article article : articles) {
			memberNickName = articleService.getForPrintMemberNickName(article.getMemberId());
			memberNickNames.put(article.getMemberId(), memberNickName);
		}
		req.setAttribute("memberNickNames", memberNickNames);
		
		long endTime = System.nanoTime();
		long estimatedTime = endTime - startTime;
		// nano seconds to seconds
		double seconds = estimatedTime / 1000000000.0;
		System.out.println("seconds : " + seconds);
		
		return "article/list.jsp";
	}

	@Override
	public String getControllerName() {
		return "article";
	}
}