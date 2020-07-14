package com.sbs.java.blog.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.ArticleReply;
import com.sbs.java.blog.dto.CateItem;
import com.sbs.java.blog.util.DBUtil;
import com.sbs.java.blog.util.SecSql;

public class ArticleDao extends Dao {
	private Connection dbConn;
	private DBUtil dbUtil;

	public ArticleDao(Connection dbConn, HttpServletRequest req, HttpServletResponse resp) {
		super(req, resp);
		this.dbConn = dbConn;
		dbUtil = new DBUtil(req, resp);
	}

	public List<Article> getForPrintListArticles(int page, int itemsInAPage, int cateItemId, String searchKeywordType,
			String searchKeyword) {
		SecSql secSql = new SecSql();

		int limitFrom = (page - 1) * itemsInAPage;

		secSql.append("SELECT *");
		secSql.append("FROM article");
		secSql.append("WHERE displayStatus = '1'");
		if (cateItemId != 0) {
			secSql.append("AND cateItemId = ?", cateItemId);
		}
		if (searchKeywordType.equals("title") && searchKeyword.length() > 0) {
			secSql.append("AND title LIKE CONCAT(%, ?, %)", searchKeyword);
		}
		secSql.append("ORDER BY id DESC");
		secSql.append("LIMIT ?, ?", limitFrom, itemsInAPage);

		List<Map<String, Object>> rows = dbUtil.selectRows(dbConn, secSql);
		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}

		return articles;
	}

	public int getForPrintListArticlesCount(int cateItemId, String searchKeywordType, String searchKeyword) {
		SecSql secSql = new SecSql();

		secSql.append("SELECT COUNT(*) AS cnt");
		secSql.append("FROM article");
		secSql.append("WHERE displayStatus = '1'");

		if (cateItemId != 0) {
			secSql.append("AND cateItemId = ?", cateItemId);
		}

		if (searchKeywordType.equals("title") && searchKeyword.length() > 0) {
			secSql.append("AND title LIKE CONCAT(%, ?, %)", searchKeyword);
		}

		int count = dbUtil.selectRowIntValue(dbConn, secSql);
		return count;
	}

	public Article getForPrintArticle(int id) {
		SecSql secSql = new SecSql();

		secSql.append("SELECT *, '하승범' AS extra__writer");
		secSql.append("FROM article");
		secSql.append("WHERE 1");
		secSql.append("AND id = ?", id);
		secSql.append("AND displayStatus = '1'");

		return new Article(dbUtil.selectRow(dbConn, secSql));
	}

	public List<CateItem> getForPrintCateItems() {
		SecSql secSql = new SecSql();

		secSql.append("SELECT *");
		secSql.append("FROM cateItem");
		secSql.append("WHERE 1");
		secSql.append("ORDER BY id ASC");

		List<Map<String, Object>> rows = dbUtil.selectRows(dbConn, secSql);
		List<CateItem> cateItems = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			cateItems.add(new CateItem(row));
		}

		return cateItems;
	}

	public CateItem getCateItem(int cateItemId) {
//		String sql = "";
		SecSql secSql = new SecSql();

//		sql += String.format("SELECT * ");
//		sql += String.format("FROM cateItem ");
//		sql += String.format("WHERE 1 ");
//		sql += String.format("AND id = %d", cateItemId);

		secSql.append("SELECT *");
		secSql.append("FROM cateItem");
		secSql.append("WHERE 1");
		secSql.append("AND id = ? ", cateItemId);

		return new CateItem(dbUtil.selectRow(dbConn, secSql));
	}

	public int doWriteArticle(String title, String body, int cateItemId) {
		SecSql secSql = new SecSql();

		secSql.append("INSERT INTO article");
		secSql.append("SET regDate = NOW()");
		secSql.append(", updateDate = NOW()");
		secSql.append(", title = ? ", title);
		secSql.append(", body = ? ", body);
		secSql.append(", displayStatus = '1'");
		secSql.append(", cateItemId = ?", cateItemId);

		return dbUtil.insert(dbConn, secSql);
	}

	public void doDeleteArticle(int id) {
		SecSql secSql = new SecSql();

		secSql.append("DELETE FROM article");
		secSql.append("WHERE 1");
		secSql.append("AND id = ?", id);

		dbUtil.deleteRow(dbConn, secSql);
	}

	public int increaseHit(int id) {
		SecSql sql = SecSql.from("UPDATE article");
		sql.append("SET hit = hit + 1");
		sql.append("WHERE id = ?", id);

		return DBUtil.update(dbConn, sql);
	}

	public int write(int memberId, int cateItemId, String title, String body) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", hit = '0'");
		sql.append(", title = ? ", title);
		sql.append(", body = ? ", body);
		sql.append(", displayStatus = '1'");
		sql.append(", memberId = ?", memberId);
		sql.append(", cateItemId = ?", cateItemId);

		return dbUtil.insert(dbConn, sql);
	}

	public int modify(int id, int cateItemId, String regDate, String title, String body) {
		SecSql sql = new SecSql();

		sql.append("UPDATE article");
		sql.append("SET regDate = ?", regDate);
		sql.append(", updateDate = NOW()");
		sql.append(", title = ? ", title);
		sql.append(", body = ? ", body);
		sql.append(", cateItemId = ?", cateItemId);
		sql.append("WHERE id = ?", id);

//		System.out.println(dbUtil.update(dbConn, sql));
		return dbUtil.update(dbConn, sql);
	}

	public int comment(String articleId, String memberId, String body) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO articleReply");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", articleId = ?", articleId);
		sql.append(", memberId = ?", memberId);
//		sql.append(", articleId = 1");
//		sql.append(", memberId = 1");
		sql.append(", body = ?", body);
		return dbUtil.insert(dbConn, sql);
	}

	public List<ArticleReply> commentList(int articleId) {
		SecSql secSql = new SecSql();

		secSql.append("SELECT *");
		secSql.append("FROM articleReply");
		secSql.append("WHERE articleId = ?", articleId);
		secSql.append("ORDER BY id DESC");

		List<Map<String, Object>> rows = dbUtil.selectRows(dbConn, secSql);
		List<ArticleReply> ArticleReplies = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			ArticleReplies.add(new ArticleReply(row));
		}

		return ArticleReplies;
	}
}
