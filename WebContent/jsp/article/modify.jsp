<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="게시물 수정"></c:set>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%@ include file="/jsp/part/toastUiEditor.jspf"%>

<style>
/* cus */
.modify-form-box {
	margin-top: 30px;
}
</style>


<script>
	var submitModifyFormDone = false;
	function submitModifyForm(form) {
		if (submitModifyFormDone) {
			alert('처리중입니다.');
			return;
		}
		form.title.value = form.title.value.trim();
		if (form.title.value.length == 0) {
			alert('제목을 입력해주세요.');
			form.title.focus();
			return false;
		}
		var editor = $(form).find('.toast-editor').data('data-toast-editor');
		var body = editor.getMarkdown();
		body = body.trim();
		if (body.length == 0) {
			alert('내용을 입력해주세요.');
			editor.focus();
			return false;
		}
		form.body.value = body;
		form.submit();
		submitModifyFormDone = true;
	}
</script>

<div class="modify-form-box con">
	<form action="doModify" method="POST" class="modify-form form1"
		onsubmit="submitModifyForm(this); return false;">
		<input type="hidden" name="id" value="${article.id}"> <input
			type="hidden" name="body">
		<div class="form-row">
			<div class="label">번호</div>
			<div class="input">${article.id}</div>
		</div>
		<div class="form-row">
			<div class="label">날짜</div>
			<div class="input">${article.regDate}</div>
		</div>
		<div class="form-row">
			<div class="label">카테고리 선택</div>
			<div class="input">
				<select name="cateItemId">
					<c:forEach items="${cateItems}" var="cateItem">
						<option ${article.cateItemId == cateItem.id ? 'selected' : ''} value="${cateItem.id}">${cateItem.name}</option>
					</c:forEach>

				</select>
			</div>
		</div>
		<div class="form-row">
			<div class="label">제목</div>
			<div class="input">
				<input value="${article.title}" name="title" type="text"
					placeholder="제목을 입력해주세요." />
			</div>
		</div>
		<div class="form-row">
			<div class="label">내용</div>
			<div class="input">
				<script type="text/x-template">${article.bodyForXTemplate}</script>
				<div class="toast-editor"></div>
			</div>
		</div>
		<div class="form-row">
			<div class="label">수정</div>
			<div class="input">
				<input type="submit" value="수정" /> <a href="list">취소</a>
			</div>
		</div>
	</form>
</div>

<%@ include file="/jsp/part/foot.jspf"%>