<%@ page import="com.sbs.java.blog.dto.Article"%>
<%@ page import="com.sbs.java.blog.dto.Member"%>
<%@ page import="com.sbs.java.blog.dto.ArticleReply"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%
	Article article = (Article) request.getAttribute("article");
	List<ArticleReply> articleReplies = (List<ArticleReply>) request.getAttribute("articleReplies");
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

		<h3>
			조회 :
			<%=article.getHit()%></h1>

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

			<%
				if (session.getAttribute("loginedMemberId") != null) {
					if (article.getMemberId() == (int)session.getAttribute("loginedMemberId")) {
			%>
			<div class="button flex" style="">
				<form action="modify" method="POST" encType="multiplart/form-data">
					<input type="hidden" name="id" value="<%=article.getId()%>" /> <input
						type="hidden" name="regDate" value="<%=article.getRegDate()%>" />
					<input type="hidden" name="title" value="<%=article.getTitle()%>" />
					<input type="hidden" name="body" value="<%=article.getBody()%>" />
					<input type="hidden" name="cateItemId"
						value="<%=article.getCateItemId()%>" /> <input type="submit"
						value="수정" />
				</form>
				<form action="${pageContext.request.contextPath}/s/article/doDelete"
					method="get" encType="multiplart/form-data">
					<input type="hidden" name="id" value="${param.id}" /> <input
						type="submit" value="삭제" />
				</form>
			</div>
			<%
				}
				}
			%>
		
	</div>
</div>


<!-- 댓글 -->
<div class="con comment" style="background-color: pink; width: 100%; margin-top:50px">
	<form action="doComment" method="POST">
		<input type="hidden" name="articleId" value="<%=article.getId()%>" />
		<input type="hidden" name="memberId"
			value="<%=article.getMemberId()%>" />
		<table>
			<tbody>
				<tr>
					<td><textarea style="resize: none;" cols="50" rows="5"
							placeholder="댓글을 입력하세요. " name="body"></textarea></td>
					<td><input style="padding: 30px;" type="submit" value="등록" /></td>
				</tr>

			</tbody>
		</table>
	</form>
</div>

<div class="con  comment-List"
	style="background-color: pink; width: 1000px; margin-top: 50px;">
	<div class="con articleRepliesItem">
		<%
			for (ArticleReply articleReply : articleReplies) {
		%>
		<table style="border: 1px solid red;">
			<tbody>
				<tr>
					<td><%=articleReply.getRegDate()%></td>
				</tr>
				<tr>
					<td><%=articleReply.getBody()%></td>
				</tr>
			</tbody>
		</table>

		<%
			}
		%>
	</div>
</div>



<%@ include file="/jsp/part/foot.jspf"%>