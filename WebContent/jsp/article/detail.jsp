<%@ page import="com.sbs.java.blog.dto.Article"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%
	Article article = (Article) request.getAttribute("article");
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

<div class="con detail-box-1">
	<h1 class="title"><%=article.getTitle()%></h1>
	<h3 class="hit">
		조회 :
		<%=article.getHit()%></h3>
	<div class="title-line"></div>

	<script type="text/x-template" id="origin1" style="display: none;"><%=article.getBodyForXTemplate()%></script>
	<div id="viewer1"></div>
	<script>
		var editor1__initialValue = getBodyFromXTemplate('#origin1');
		var editor1 = new toastui.Editor({
			el : document.querySelector('#viewer1'),
			initialValue : editor1__initialValue,
			viewer : true,
			plugins : [ toastui.Editor.plugin.codeSyntaxHighlight,
					youtubePlugin, replPlugin, codepenPlugin ]
		});
	</script>
	
	<div class="modify-and-delete">
		<div>
			<form action="modify" method="POST">
				<input type="hidden" name="title" value="<%=article.getTitle()%>" />
				<input type="hidden" name="body" value="<%=article.getBody()%>" />
				<input type="hidden" name="articleId" value="<%=article.getId()%>" />
				<input type="hidden" name="cateItemId" value="<%=article.getCateItemId()%>" />
				<input type="hidden" name="regDate" value="<%=article.getRegDate()%>" />

				<input type="submit" value="수정"/>
			</form>
		</div>
		<div>
			<form action="doDelete" method="get">
				<input type="submit" value="삭제"/>
			</form>
			</div>
		</div>
	</div>



<%@ include file="/jsp/part/foot.jspf"%>