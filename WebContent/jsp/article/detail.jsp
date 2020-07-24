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

<div class="con detail-box-1">
	<h1 class="title">${article.title}</h1>
	<h3 class="hit">
		조회 : ${article.hit}</h3>
	<div class="title-line"></div>
	
	<script type="text/x-template">${article.bodyForXTemplate}</script>
	<div class="toast-editor toast-editor-viewer" style="margin:20px;"></div>
					
	<c:if test="${article.extra.deleteAvailable}">
	
	<div class="modify-and-delete">
		<div class="btn">
			<c:if test="${article.extra.deleteAvailable}">
				<a onclick="if ( confirm('삭제하시겠습니까?') == false ) return false;" href="./doDelete?id=${article.id}">삭제</a>
			</c:if>
		</div> 
		<div class="btn">
			<c:if test="${article.extra.modifyAvailable}">
				<a href="./modify?id=${article.id}">수정</a>
			</c:if>
		</div>
	</c:if>
	</div>
</div>

<c:if test="${isLogined}">
<div class="con article-reply-write">
	<div class="reply-button">댓글 쓰기</div>
	<div class="con article-reply-write-box">
		<form action="doReply" method="get" onsubmit="submitReplyForm(this); return false;">
			<input type="hidden" name="id" value="${article.id}" />
			<textarea name="body" rows="10" cols="80" style="resize: none; display:block; margin:0 auto;"></textarea>
			<input type="submit" value="작성" style="display:block; width:100px; margin:0 auto; margin-top:10px;"/>
		</form>
	</div>
</div>
</c:if>


<div class="con article-reply-view">

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
	</script>



<%@ include file="/jsp/part/foot.jspf"%>