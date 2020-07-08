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


<div class="article-detail-1" style="margin-top: 300px;">
	<div class="con flex flex-jc-sb flex-column-nowrap"
		style="border: 3px solid pink; width: 1000px;">

		<div class="title" style="text-align: center;">
			<h1><%=article.getTitle()%></h1>
			<div style="border-bottom: 3px solid pink; margin: 0 10px;"></div>
		</div>

		<script type="text/x-template" id="origin1" style="display: none;"><%=article.getBody()%></script>
		<div id="viewer1" style="margin: 30px;"></div>
		<script>
			var editor1__initialValue = $('#origin1').html().trim();
			var editor1 = new toastui.Editor({
				el : document.querySelector('#viewer1'),
				initialValue : editor1__initialValue,
				viewer : true,
				plugins : [ toastui.Editor.plugin.codeSyntaxHighlight,
						youtubePlugin, replPlugin, codepenPlugin ]
			});
		</script>
	</div>
	
	<form action="${pageContext.request.contextPath}/s/article/doModify" method="get" encType="multiplart/form-data">
		<input	type="submit" value="수정" /> 
	</form>
	<form action="${pageContext.request.contextPath}/s/article/doDelete" method="get" encType="multiplart/form-data">
		<input type="hidden" name="id" value="${param.id}" />
		<input	type="submit" value="삭제" /> 
	</form>
</div>



<%@ include file="/jsp/part/foot.jspf"%>