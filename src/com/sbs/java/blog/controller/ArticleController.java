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
		case "doReply":
			return doActionDoReply();
		case "doReplyModify":
			return doActionDoReplyModify();
		case "doReplyDelete":
			return doActionDoReplyDelete();
		}
		return "";
	}

	private String doActionDoReplyDelete() {
		int replyId = Util.getInt(req, "replyId");
		int id = Util.getInt(req, "id");
		articleService.deleteReply(replyId);

		return "html:<script> alert('댓글 삭제 완료'); location.replace('detail?id=" + id + "'); </script>";
	}
	
	private String doActionDoReplyModify() {
		int memberId = (int)req.getAttribute("loginedMemberId");
		int articleId = Util.getInt(req, "articleId");
		int id = Util.getInt(req, "id");
		String regDate = Util.getString(req, "regDate");
		String body = Util.getString(req, "body");
		
		System.out.println(memberId);
		System.out.println(articleId);
		System.out.println(id);
		System.out.println(regDate);
		System.out.println(body);
		
		int id_ = articleService.modifyReply(id, articleId, memberId, regDate, body);
		
		return "html:<script> alert('수정 완료!'); location.replace('detail?id=" + articleId + "'); </script>";
	}


	private String doActionDoReply() {
		int memberId = (int)req.getAttribute("loginedMemberId");
		int articleId = Util.getInt(req, "id");
		String body = Util.getString(req, "body");
		
		articleService.reply(memberId, articleId, body);
		
		return "html:<script> alert('작성 완료!'); location.replace('detail?id=" + articleId + "'); </script>";
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
		
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		List<Article> articles = articleService.getForPrintListArticles(loginedMemberId, page, itemsInAPage, cateItemId,
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