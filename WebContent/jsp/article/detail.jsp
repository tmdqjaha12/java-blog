<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sbs.java.blog.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%@ include file="/jsp/part/toastUiEditor.jspf"%>

<div class="con detail-box-1">
	<h1 class="title">${article.title}</h1>
	<h3 class="hit">조회 : ${article.hit}</h3>
	<div class="title-line"></div>

	<script type="text/x-template">${article.bodyForXTemplate}</script>
	<div class="toast-editor toast-editor-viewer" style="margin: 20px;"></div>

	<c:if test="${article.extra.deleteAvailable}">

		<div class="modify-and-delete">
			<div class="btn">
				<c:if test="${article.extra.deleteAvailable}">
					<a onclick="if ( confirm('삭제하시겠습니까?') == false ) return false;"
						href="./doDelete?id=${article.id}">삭제</a>
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

<c:if test="${isLogined == false}">
	<div class="con">

		<c:url value="/s/member/login" var="loginUrl">
			<c:param name="afterLoginRedirectUrl"
				value="${currentUrl}&jsAction=WriteReplyForm__focus" />
		</c:url>
		<a href="${loginUrl}">로그인</a> 후 이용해주세요.
	</div>
</c:if>

<c:if test="${isLogined}">


	<div class="con article-reply-write">
		<div class="reply-button">댓글 작성</div>
		<div class="con article-reply-write-box"></div>
	</div>

	<div class="write-reply-form-box con">
		<form action="doWriteReply" method="POST" class="write-reply-form form1" onsubmit="WriteReplyForm__submit(this); return false;">
			<input type="hidden" name="articleId" value="${article.id}">
			
			<c:set var="redirectUrl"
				value="${Util.getNewUrlRemoved(currentUrl, 'lastWorkArticleReplyId')}" />
			<c:set var="redirectUrl"
				value="${Util.getNewUrl(redirectUrl, 'jsAction', 'WriteReplyList__showDetail')}" />

			<input type="hidden" name="redirectUrl" value="${redirectUrl}">
			<input type="hidden" name="body">
			<div class="form-row">
				<div class="label">내용</div>
				<div class="input">
					<script type="text/x-template"></script>
					<div data-toast-editor-height="300" class="toast-editor"></div>
				</div>
			</div>
			<div class="form-row">
				<div class="label">작성</div>
				<div class="input flex">
					<input type="submit" value="작성" /> <input class="cancel"
						type="button" value="취소" />
				</div>
			</div>
		</form>
	</div>
</c:if>

<script>
	function WriteReplyList__showTop() {
		var top = $('.article-replies-list-box').offset().top;
		$(window).scrollTop(top);
	}
	function WriteReplyList__showDetail() {
		WriteReplyList__showTop();
		var $tr = $('.article-replies-list-box > table > tbody > tr[data-id="'
				+ param.lastWorkArticleReplyId + '"]');
		
		$tr.addClass('high');
		setTimeout(function() {
			$tr.removeClass('high');
		}, 1000);
	}
</script>

<script>
		var WriteReplyForm__submitDone = false;
		function WriteReplyForm__focus() {
			var editor = $('.write-reply-form .toast-editor').data(
					'data-toast-editor');
			editor.focus();
		}
		function WriteReplyForm__submit(form) {
			if (WriteReplyForm__submitDone) {
				alert('처리중입니다.');
				return;
			}
			var editor = $(form).find('.toast-editor')
					.data('data-toast-editor');
			var body = editor.getMarkdown();
			body = body.trim();
			if (body.length == 0) {
				alert('내용을 입력해주세요.');
				editor.focus();
				return false;
			}
			form.body.value = body;
			form.submit();
			WriteReplyForm__submitDone = true;
		}
		function WriteReplyForm__init() {
			$('.write-reply-form .cancel').click(
					function() {
						var editor = $('.write-reply-form .toast-editor').data(
								'data-toast-editor');
						editor.setMarkdown('');
					});
		}
		$(function() {
			WriteReplyForm__init();
		});
	</script>

<style>

.article-replies-list-box>table>tbody>tr {
	transition: background-color 1s;
} 

.article-replies-list-box>table>tbody>tr.high {
	background-color: #dfdfdf;
}
</style>

<div class="con article-replies-list-box table-box" style="margin-left:38%;">
	<table>
		<colgroup>
			<col width="100">
			<col width="200">
			<col>
			<col width="120">
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>날짜</th>
				<th>내용</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${articleReplies}" var="articleReply">
				<tr data-id="${articleReply.id}">
					<td class="text-align-center">${articleReply.id}</td>
					<td class="text-align-center">${articleReply.regDate}</td>
					<td class="padding-left-10 padding-right-10"><script
							type="text/x-template">${articleReply.bodyForXTemplate}</script>
						<div class="toast-editor toast-editor-viewer"></div></td>
					<td class="text-align-center"><c:if
							test="${articleReply.extra.deleteAvailable}">
							<c:set var="afterDeleteReplyRedirectUrl"
								value="${Util.getNewUrlRemoved(currentUrl, 'lastWorkArticleReplyId')}" />
							<c:set var="afterDeleteReplyRedirectUrl"
								value="${Util.getNewUrlAndEncoded(afterDeleteReplyRedirectUrl, 'jsAction', 'WriteReplyList__showTop')}" />

							<c:set var="afterModifyReplyRedirectUrl"
								value="${Util.getNewUrlRemoved(currentUrl, 'lastWorkArticleReplyId')}" />
							<c:set var="afterModifyReplyRedirectUrl"
								value="${Util.getNewUrlAndEncoded(afterModifyReplyRedirectUrl, 'jsAction', 'WriteReplyList__showDetail')}" />

							<a onclick="if ( confirm('삭제하시겠습니까?') == false ) return false;"
								href="./doDeleteReply?id=${articleReply.id}&redirectUrl=${afterDeleteReplyRedirectUrl}">삭제</a>
						</c:if> <c:if test="${articleReply.extra.modifyAvailable}">
							<a
								href="./modifyReply?id=${articleReply.id}&redirectUrl=${afterModifyReplyRedirectUrl}">수정</a>
						</c:if></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>




<c:if test="${isLogined}">

</c:if>

<div class="con article-reply-view"></div>

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
				$('.write-reply-form-box').removeClass('active');
			} else {
				$this.addClass('active')
				$('.write-reply-form-box').addClass('active');
			}
		});
	}
	$(function() {
		ArticleReplyWrite__init();
	});
</script>



<%@ include file="/jsp/part/foot.jspf"%>