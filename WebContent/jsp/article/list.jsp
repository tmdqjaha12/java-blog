<%@ page import="java.util.List"%>
<%@ page import="com.sbs.java.blog.dto.Article"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%
	List<Article> articles = (List<Article>) request.getAttribute("articles");
int totalPage = (int) request.getAttribute("totalPage");
int paramPage = (int) request.getAttribute("page");
int cateItemId = (int) request.getAttribute("cateItemId");
String cateItemName = (String) request.getAttribute("cateItemName");
%>
<!-- 하이라이트 라이브러리 추가, 토스트 UI 에디터에서 사용됨 -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/highlight.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/styles/default.min.css">

<!-- 하이라이트 라이브러리, 언어 -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/css.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/javascript.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/xml.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/php.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/php-template.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/sql.min.js"></script>

<!-- 코드 미러 라이브러리 추가, 토스트 UI 에디터에서 사용됨 -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.48.4/codemirror.min.css" />

<!-- 토스트 UI 에디터, 자바스크립트 코어 -->
<script
	src="https://uicdn.toast.com/editor/latest/toastui-editor-viewer.min.js"></script>

<!-- 토스트 UI 에디터, 신택스 하이라이트 플러그인 추가 -->
<script
	src="https://uicdn.toast.com/editor-plugin-code-syntax-highlight/latest/toastui-editor-plugin-code-syntax-highlight-all.min.js"></script>

<!-- 토스트 UI 에디터, CSS 코어 -->
<link rel="stylesheet"
	href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />

<!-- LIST.JSP  -->

<!-- 해당 카테고리 시작 -->
<h1 class="con page-bn-1">
	<%=cateItemName%>
</h1>
<!-- 해당 카테고리 끝 -->

<!-- 게시물 수 -->
<div class="con articles-count flex flex-jc-e">총 게시물 수 : ${totalCount}</div>
<!-- 게시물 끝 -->

<!-- 검색 폼 시작 -->
<div class="con search-box flex flex-jc-e">

	<form action="${pageContext.request.contextPath}/s/article/list">
		<input type="hidden" name="page" value="1" /> <input type="hidden"
			name="cateItemId" value="${param.cateItemId}" /> <input
			type="hidden" name="searchKeywordType" value="title" /> <input
			type="text" name="searchKeyword" value="${param.searchKeyword}" />
		<button type="submit">검색</button>
	</form>

</div>
<!-- 검색 폼 끝 -->

<!-- 리스트 시작 -->
<div class="con page-list-1">
	<%
		for (Article article : articles) {
	%>
	<ul>
		<li><a href="#"><%=article.getId()%></a></li>
		<li><a href="#" title="<%=article.getRegDate()%>"><%=article.getRegDate()%></a></li>
		<li><a href="./detail?id=<%=article.getId()%>"
			class="page-list-title" title="<%=article.getTitle()%>"><%=article.getTitle()%></a></li>
		<li><a href="#">이름</a></li>
		<li><a href="#"><%=article.getHit()%></a></li>
	</ul>
	<%
		}
	%>

</div>
<!-- 리스트 끝 -->

<!-- 게시물 페이징 시작 -->
<div class="con page-box">
	<ul>
		<span>◀</span>
		<%
		
			for (int i = 1; i < totalPage; i++) {
		%>
		<li class="<%=i == paramPage ? "current" : ""%>">
			<a href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=<%=i%>" class="block">
				<%=i%>
			</a>
		</li>
		<%
			}
		
		
		%>
		<span>▶</span>
	</ul>
</div>
<!-- 게시물 페이징 끝 -->



<%@ include file="/jsp/part/foot.jspf"%>