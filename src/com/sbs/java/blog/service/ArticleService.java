package com.sbs.java.blog.service;

import java.sql.Connection;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.sbs.java.blog.dao.ArticleDao;
import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.ArticleReply;
import com.sbs.java.blog.dto.CateItem;
import com.sbs.java.blog.util.Util;

public class ArticleService extends Service {

	private ArticleDao articleDao;

	public ArticleService(Connection dbConn) {
		articleDao = new ArticleDao(dbConn);
	}

	public List<Article> getForPrintListArticles(int actorId, int page, int itemsInAPage, int cateItemId,
			String searchKeywordType, String searchKeyword) {
		List<Article> articles = articleDao.getForPrintListArticles(page, itemsInAPage, cateItemId, searchKeywordType,
				searchKeyword);

		for (Article article : articles) {
			updateArticleExtraDataForPrint(article, actorId);
		}

		return articles;
	}

	private void updateArticleExtraDataForPrint(Article article, int actorId) {
		boolean deleteAvailable = Util.isSuccess(getCheckRsDeleteAvailable(article, actorId));
		article.getExtra().put("deleteAvailable", deleteAvailable);

		boolean modifyAvailable = Util.isSuccess(getCheckRsModifyAvailable(article, actorId));
		article.getExtra().put("modifyAvailable", modifyAvailable);
	}

	public int getForPrintListArticlesCount(int cateItemId, String searchKeywordType, String searchKeyword) {
		return articleDao.getForPrintListArticlesCount(cateItemId, searchKeywordType, searchKeyword);
	}

	public Article getForPrintArticle(int id, int actorId) {
		Article article = articleDao.getForPrintArticle(id);

		updateArticleExtraDataForPrint(article, actorId);

		return article;
	}

	public List<CateItem> getForPrintCateItems() {
		return articleDao.getForPrintCateItems();
	}

	public CateItem getCateItem(int cateItemId) {
		return articleDao.getCateItem(cateItemId);
	}

	public int write(int cateItemId, String title, String body, int memberId) {
		return articleDao.write(cateItemId, title, body, memberId);
	}

	public void increaseHit(int id) {
		articleDao.increaseHit(id);
	}

	public Map<String, Object> getCheckRsModifyAvailable(int id, int actorId) {
		return getCheckRsDeleteAvailable(id, actorId);
	}

	public int modifyArticle(int id, int cateItemId, String title, String body) {
		return articleDao.modifyArticle(id, cateItemId, title, body);
	}

	public void deleteArticle(int id) {
		articleDao.deleteArticle(id);
	}

	private Map<String, Object> getCheckRsModifyAvailable(Article article, int actorId) {
		return getCheckRsDeleteAvailable(article, actorId);
	}

	private Map<String, Object> getCheckRsDeleteAvailable(Article article, int actorId) {
		Map<String, Object> rs = new HashMap<>();

		if (article == null) {
			rs.put("resultCode", "F-1");
			rs.put("msg", "존재하지 않는 게시물 입니다.");

			return rs;
		}

		if (article.getMemberId() != actorId) {
			rs.put("resultCode", "F-2");
			rs.put("msg", "권한이 없습니다.");

			return rs;
		}

		rs.put("resultCode", "S-1");
		rs.put("msg", "작업이 가능합니다.");

		return rs;
	}

	public Map<String, Object> getCheckRsDeleteAvailable(int id, int actorId) {
		Article article = articleDao.getForPrintArticle(id);

		return getCheckRsDeleteAvailable(article, actorId);
	}

	public int writeArticleReply(int articleId, int memberId, String body) {
		return articleDao.writeArticleReply(articleId, memberId, body);
	}

	public List<ArticleReply> getForPrintArticleReplies(int articleId, int actorId) {
		List<ArticleReply> articleReplies = articleDao.getForPrintArticleReplies(articleId, actorId);

		for (ArticleReply articleReply : articleReplies) {
			updateArticleReplyExtraDataForPrint(articleReply, actorId);
		}

		return articleReplies;
	}

	public Map<String, Object> getReplyCheckRsModifyAvailable(int id, int actorId) {
		ArticleReply articleReply = getArticleReply(id);

		return getReplyCheckRsModifyAvailable(articleReply, actorId);
	}
	
	private void updateArticleReplyExtraDataForPrint(ArticleReply articleReply, int actorId) {
		boolean deleteAvailable = Util.isSuccess(getReplyCheckRsDeleteAvailable(articleReply, actorId));
		articleReply.getExtra().put("deleteAvailable", deleteAvailable);

		boolean modifyAvailable = Util.isSuccess(getReplyCheckRsModifyAvailable(articleReply, actorId));
		articleReply.getExtra().put("modifyAvailable", modifyAvailable);
	}

	private Map<String, Object> getReplyCheckRsModifyAvailable(ArticleReply articleReply, int actorId) {
		return getReplyCheckRsDeleteAvailable(articleReply, actorId);
	}

	private Map<String, Object> getReplyCheckRsDeleteAvailable(ArticleReply articleReply, int actorId) {
		Map<String, Object> rs = new HashMap<>();

		if (articleReply == null) {
			rs.put("resultCode", "F-1");
			rs.put("msg", "존재하지 않는 댓글 입니다.");

			return rs;
		}

		if (articleReply.getMemberId() != actorId) {
			rs.put("resultCode", "F-2");
			rs.put("msg", "권한이 없습니다.");

			return rs;
		}

		rs.put("resultCode", "S-1");
		rs.put("msg", "작업이 가능합니다.");

		return rs;
	}

	public Map<String, Object> getReplyCheckRsDeleteAvailable(int id, int actorId) {
		ArticleReply articleReply = this.getArticleReply(id);

		return getReplyCheckRsDeleteAvailable(articleReply, actorId);
	}

	public ArticleReply getArticleReply(int id) {
		return articleDao.getArticleReply(id);
	}
	
	public int modifyArticleReply(int id, String body) {
		return articleDao.modifyArticleReply(id, body);
	}
	
	public int deleteArticleReply(int id) {
		return articleDao.deleteArticleReply(id);
	}

}