<%@ page import="java.util.List"%>
<%@ page import="com.sbs.java.blog.dto.Article"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%
	List<Article> articles = (List<Article>) request.getAttribute("articles");
	int totalPage = (int) request.getAttribute("totalPage");
	int paramPage = (int) request.getAttribute("page");
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

<style>
.page-box {
	margin-top: 50px;
	margin-bottom: 50px;
}

.page-box>ul>li>a {
	padding: 0 10px;
	text-decoration: underline;
	color: #787878;
}

.page-box>ul>li:hover>a {
	color: black;
}

.page-box>ul>li.current>a {
	color: red;
}

.article-list-1 .cate-list-1>ul>li.currentCateItemName {
	opacity: 1;
}

.articlesItem {
	border: 2px solid pink;
	width: 100%;
	background-color: pink;
}

.articlesItem a {
	display: block;
	padding: 20px;
}

.articlesItem>ul>li {
	background-color: white;
}

.articlesItem>ul>li:hover {
	background-color: #ffffff;
	opacity: 0.5;
}

.txt_line {
	width: 200px;
	padding: 0 5px;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.title-bg-1 {
	height: 200px;
	top: 0;
	left: auto;
	right: 0;
}
</style>

<div class="article-list-1" style="margin-top: 300px;">
	<div class="con flex flex-jc-sb flex-column-nowrap">


		<div class="cate-list-1 flex-as-c">
			<ul class="flex">
				<%
					for (CateItem cateItem : cateItems) {
				%>
				<li
					class="<%=cateItem.getName().equals(cateItemName) ? "currentCateItemName" : ""%> flex flex-ai-c"><a
					href="${pageContext.request.contextPath}/s/article/list?cateItemId=<%=cateItem.getId()%>"
					class="flex flex-ai-c"><%=cateItem.getName()%></a></li>
				<%
					}
				%>
			</ul>
		</div>
		<!-- 
		<h1 class="con flex-as-c">
			<cateItemName%>
		</h1>
 -->
		<div class="con flex-as-c">총 게시물 수 : ${totalCount}</div>

		<div class="con flex-as-c articlesItem">
			<ul>
				<%
					for (Article article : articles) {
				%>
				<li style="margin-top: 20px; text-align: right;">
					<a href="./detail?id=<%=article.getId()%>">
						<ul>
							<li>No. <%=article.getId()%></li>
							<li class="txt_line">※ <%=article.getTitle()%></li>
						</ul> 
						<img class="title-bg-1" src="${pageContext.request.contextPath}/resource/img/java.jpg" alt="" />
					</a>
				</li>
				<%
					}
				%>
			</ul>
		</div>
		<!-- 
		<div class="con">
	<ul>
		<%
			for (Article article : articles) {
		%>
		<li><a href="./detail?id=<%=article.getId()%>"><%=article.getId()%>,
				<%=article.getCateItemId()%>, <%=article.getTitle()%></a></li>
		<%
			}
		%>
	</ul>
</div>
 -->
		<div class="con page-box flex-as-c">
			<ul class="flex flex-jc-c">
				<%
					for (int i = 1; i <= totalPage; i++) {
				%>
				<li class="<%=i == paramPage ? "current" : ""%>"><a
					href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=<%=i%>"
					class="block"><%=i%></a></li>
				<%
					}
				%>
			</ul>
		</div>


		<div class="con search-box flex flex-jc-c">

			<form action="${pageContext.request.contextPath}/s/article/list">
				<input type="hidden" name="page" value="1" /> <input type="hidden"
					name="cateItemId" value="${param.cateItemId}" /> <input
					type="hidden" name="searchKeywordType" value="title" /> <input
					type="text" name="searchKeyword" value="${param.searchKeyword}" />
				<button type="submit">검색</button>
			</form>

		</div>
	</div>
</div>

<div style="position: fixed; top: 200px; left: 50px;">
	   
	<button type="button" class="upBtn">▲</button>
	<br /> <br />    
	<button type="button" class="downBtn">▼</button>
	 
	<%
 	if (session.getAttribute("loginedMemberId") != null) {
 %>
	<div class="doWrite"
		style="margin-top: 20px; border: 1px solid black; background-color: #f9c6cf; margin-left: 6px;">
		<a href="${pageContext.request.contextPath}/s/article/write">글쓰기</a>
	</div>
	<%
		}
	%>
	   
	<script>
		    $(".upBtn").click(function() {
			$('html, body').animate({
				scrollTop : 0
			}, 400);
		});

		$(".downBtn").click(function() {
			$('html, body').animate({
				scrollTop : ($('body').height())
			}, 200);
		});
	</script>
	 
</div>

<div class="bottom"></div>


<%@ include file="/jsp/part/foot.jspf"%>