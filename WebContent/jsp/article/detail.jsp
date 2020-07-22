<%@page import="com.sbs.java.blog.dto.ArticleReply"%>
<%@ page import="com.sbs.java.blog.dto.Article"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%
	Article article = (Article) request.getAttribute("article");
	List<ArticleReply> articleReplies = (List<ArticleReply>) request.getAttribute("articleReplies");
	Map<Integer, String> memberNickNames = (Map<Integer, String>) request.getAttribute("memberNickNames");

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
	
	<div id="viewer1"><%=article.getBody() %></div>
	<% if(loginedMemberId == article.getMemberId()) {%>
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
				<input type="hidden" name="id" value="<%=article.getId()%>"/><!-- 흠? -->
				<input type="submit" value="삭제"/>
			</form>
			</div>
		</div>
		<% } %>
</div>

<% if (request.getAttribute("loginedMember") != null){ %>
<div class="con article-reply-write">
	<div class="reply-button">댓글 쓰기</div>
	<div class="con article-reply-write-box">
		<form action="doReply" method="get" onsubmit="submitReplyForm(this); return false;">
			<input type="hidden" name="id" value="<%=article.getId()%>" />
			<textarea name="body" rows="10" cols="80" style="resize: none; display:block; margin:0 auto;"></textarea>
			<input type="submit" value="작성" style="display:block; width:100px; margin:0 auto; margin-top:10px;"/>
		</form>
	</div>
</div>
<% } %>


<div class="con article-reply-view">

<% for (ArticleReply articleReply : articleReplies) { %>
	<div class="line">
		<table>
			<tbody>
				<tr>
					<th><div class="img-box"><img src="${pageContext.request.contextPath}/resource/img/meloporn_banner.png" alt="" /></div></th>
					<td><h5><a href="#"><%=memberNickNames.get(articleReply.getMemberId())%></a> · <%=articleReply.getRegDate() %></h5></td>
				</tr>
			</tbody>
		</table>
		
		<h4 class="replyBody active"><%=articleReply.getBody() %></h4>
		<% if(loginedMemberId == articleReply.getMemberId()) {%>
		<div class="con article-reply-modify">
				<div class="con article-reply-modify-box">
					<form action="doReplyModify" method="POST" onsubmit="submitReplyForm(this); return false;">
					<input type="hidden" name="articleId" value="${param.id}" /><!-- 흠? -->
					<input type="hidden" name="id" value="<%=articleReply.getId()%>"/>
					<input type="hidden" name="regDate" value="<%=articleReply.getRegDate()%>"/>
					<textarea id="replymodify1" name="body" rows="6" cols="40" style="resize: none; display:inline-block; margin-left:30%; vertical-align:top;"><%=articleReply.getBody() %></textarea>
					<input type="submit" value="수정" style="display:inline-block; height:100px; vertical-align:top;"/>
				</form>
			</div>
		</div>
		
		<section class="reply-modify" >...
			<div class="reply-modify-box">
				<div onclick="test(<%=articleReply.getId()%>)" class="reply-modify-button" >수정</div> <!-- articleReply.getId() 특정 버튼만 작동-->
				<form action="doReplyDelete">
					<input type="hidden" name="replyId" value="<%=articleReply.getId()%>" />
					<input type="hidden" name="id" value="${param.id}" /><!-- 흠? -->
					<input type="submit" value="삭제" />
				</form>
				<form action="">
					<input type="submit" value="신고"/>
				</form>
			</div>
		</section>
		<% } %>
	</div>
	<% } %>
</div>
	
	<script type="text/x-template" id="replymodify1" style="display: none;">getBodyForXTemplate()</script>
	
	<script type="text/x-template" id="origin1" style="display: none;"></script>
	<script>
		var editor1__initialValue = getBodyFromXTemplate('#origin1');
		var editor1 = new toastui.Editor({
			el : document.querySelector('#viewer1'),
			initialValue : editor1__initialValue,
			viewer : true,
			plugins : [ toastui.Editor.plugin.codeSyntaxHighlight,
					youtubePlugin, replPlugin, codepenPlugin ]
		});	
		function replaceAll(viewer1){
			return viewer1.replaceAll("(?i)script", "<!--REPLACE:script-->");
		}

		function submitReplyForm(form) {
			form.body.value = form.body.value.trim();
			if (form.body.value.length == 0) {
				alert('내용을 입력해주세요.');
				form.body.focus();
				return;
			}
	
		  	form.submit();
		}

		function ArticleReplyWrite__init() {
			$('.article-reply-write .reply-button').click(function() {
				var $this = $(this);

				if ($this.hasClass('active')) {
					$this.removeClass('active');
					$('.article-reply-write-box').removeClass('active');
				} else {
					$this.addClass('active')
					$('.article-reply-write-box').addClass('active');
				}
			});
		}
		$(function() {
			ArticleReplyWrite__init();
		});
		

		function ArticleReplyModify__init() {
			$('.reply-modify .reply-modify-button').click(function() {
				var $this = $(this);

				if ($this.hasClass('active')) {
					$this.removeClass('active');
					$('.replyBody').addClass('active');
					$('.article-reply-modify-box').removeClass('active');
				} else {
					$this.addClass('active')
					$('.replyBody').removeClass('active');
					$('.article-reply-modify-box').addClass('active');
				}
			});
		}
		$(function() {
			ArticleReplyModify__init();
		});

//		function test(id){
//			var $this = $(this);
//			var div = document.getElementByClass(id);
//			div.style.disply = "block";
//			div.style.removeProperty("display");
//
//			if ($this.hasClass(id)) {
//				$this.removeClass(id);
//				$('.replyBody').addClass(id);
//				$('.article-reply-modify-box').removeClass(id);
//			} else {
//				$this.addClass(id)
//				$('.replyBody').removeClass(id);
//				$('.article-reply-modify-box').addClass(id);
//			}
//		}

		
//		function ArticleReplyModify__init() {
//			$('.article-reply-view .reply-modify').click(function() {
//				var $this = $(this);

//				if ($this.hasClass('active')) {
//					$this.removeClass('active');
//					$('.reply-modify-box').removeClass('active');
//				} else {
//					$this.addClass('active')
//					$('.reply-modify-box').addClass('active');
//				}
//			});
//		}
//		$(function() {
//			ArticleReplyModify__init();
//		});
			
	</script>



<%@ include file="/jsp/part/foot.jspf"%>