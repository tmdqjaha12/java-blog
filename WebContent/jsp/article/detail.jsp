<%@page import="com.sbs.java.blog.dto.ArticleReply"%>
<%@ page import="com.sbs.java.blog.dto.Article"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%@ include file="/jsp/part/toastUiEditor.jspf" %>
<%
	Article article = (Article) request.getAttribute("article");
	List<ArticleReply> articleReplies = (List<ArticleReply>) request.getAttribute("articleReplies");
	Map<Integer, String> memberNickNames = (Map<Integer, String>) request.getAttribute("memberNickNames");

%>
<div class="con detail-box-1">
	<h1 class="title"><%=article.getTitle()%></h1>
	<h3 class="hit">
		조회 :
		<%=article.getHit()%></h3>
	<div class="title-line"></div>
	
	<script type="text/x-template"><%=article.getBodyForXTemplate()%></script>
	<div class="toast-editor toast-editor-viewer" style="margin:20px;"></div>
					
	<% if(loginedMemberId == article.getMemberId()) {%>
	<div class="modify-and-delete">
		<div class="btn">
			<%
				if ((boolean) article.getExtra().get("deleteAvailable")) {
			%>
			<a onclick="if ( confirm('삭제하시겠습니까?') == false ) return false;"
				href="./doDelete?id=<%=article.getId()%>">삭제</a>
			<%
				}
			%>
		</div>
		<div class="inline-block">
			<%
				if ((boolean) article.getExtra().get("modifyAvailable")) {
			%>
			<a href="./modify?id=<%=article.getId()%>">수정</a>
			<%
				}
			%>
		</div>
		<% } %>
	</div>
		
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
	
	<script>

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
			
	</script>



<%@ include file="/jsp/part/foot.jspf"%>