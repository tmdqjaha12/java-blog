package com.sbs.java.blog.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.CateItem;
import com.sbs.java.blog.util.DBUtil;

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
		String sql = "";

		int limitFrom = (page - 1) * itemsInAPage;

		sql += String.format("SELECT * ");
		sql += String.format("FROM article ");
		sql += String.format("WHERE displayStatus = 1 ");
		if (cateItemId != 0) {
			sql += String.format("AND cateItemId = %d ", cateItemId);
		}
		if (searchKeywordType.equals("title") && searchKeyword.length() > 0) {
			sql += String.format("AND title LIKE CONCAT('%%', '%s', '%%')", searchKeyword);
		}
		sql += String.format("ORDER BY id DESC ");
		sql += String.format("LIMIT %d, %d ", limitFrom, itemsInAPage);

		List<Map<String, Object>> rows = dbUtil.selectRows(dbConn, sql);
		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}

		return articles;
	}

	public int getForPrintListArticlesCount(int cateItemId, String searchKeywordType, String searchKeyword) {
		String sql = "";

		sql += String.format("SELECT COUNT(*) AS cnt ");
		sql += String.format("FROM article ");
		sql += String.format("WHERE displayStatus = 1 ");

		if (cateItemId != 0) {
			sql += String.format("AND cateItemId = %d ", cateItemId);
		}

		if (searchKeywordType.equals("title") && searchKeyword.length() > 0) {
			sql += String.format("AND title LIKE CONCAT('%%', '%s', '%%')", searchKeyword);
		}

		int count = dbUtil.selectRowIntValue(dbConn, sql);
		return count;
	}

	public Article getForPrintArticle(int id) {
		String sql = "";

		sql += String.format("SELECT *, '장희성' AS extra__writer ");
		sql += String.format("FROM article ");
		sql += String.format("WHERE 1 ");
		sql += String.format("AND id = %d ", id);
		sql += String.format("AND displayStatus = 1 ");

		return new Article(dbUtil.selectRow(dbConn, sql));
	}

	public List<CateItem> getForPrintCateItems() {
		String sql = "";

		sql += String.format("SELECT * ");
		sql += String.format("FROM cateItem ");
		sql += String.format("WHERE 1 ");
		sql += String.format("ORDER BY id ASC ");

		List<Map<String, Object>> rows = dbUtil.selectRows(dbConn, sql);
		List<CateItem> cateItems = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			cateItems.add(new CateItem(row));
		}

		return cateItems;
	}

	public CateItem getCateItem(int cateItemId) {
		String sql = "";

		sql += String.format("SELECT * ");
		sql += String.format("FROM cateItem ");
		sql += String.format("WHERE 1 ");
		sql += String.format("AND id = %d ", cateItemId);

		return new CateItem(dbUtil.selectRow(dbConn, sql));
	}
}
