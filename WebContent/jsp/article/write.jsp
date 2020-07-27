
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%@ include file="/jsp/part/toastUiEditor.jspf" %>

<style>
/* cus */
.write-form-box {
    margin-top: 30px;
}
</style>
		<script>
		var submitWriteFormDone = false;
		function submitWriteForm(form) {
		if ( submitWriteFormDone ) {
			alert('처리중입니다.');
			return;
		}
		
		form.title.value = form.title.value.trim();
		if ( form.title.value.length == 0 ) {
			alert('제목을 입력해주세요.');
			form.title.focus();
		return false;
		}
		
		var editor = $(form).find('.toast-editor').data('data-toast-editor');
		var body = editor.getMarkdown();
		body = body.trim();
		if ( body.length == 0 ) {
			alert('내용을 입력해주세요.');
			editor.focus();
			return false;
		}
		
		form.body.value = body;

		form.submit();
		submitWriteFormDone = true;
	}
</script>

<div class="write-form-box con">
	<form action="doWrite" method="POST" class="write-form form1" onsubmit="submitWriteForm(this); return false;">
	<input type="hidden" name="body">
		<div class="form-row">
			<div class="label">카테고리 선택</div>
			<div class="input">
				<select name="cateItemId">
					<c:forEach items="${cateItems}" var="cateItem">
					<option value="${cateItem.id}">${cateItem.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-row">
			<div class="label">제목</div>
			<div class="input">
				<input name="title" type="text" placeholder="제목을 입력해주세요." />
			</div>
		</div>
		<div class="form-row">
			<div class="label">내용</div>
			<div class="input">
				<script type="text/x-template"></script>
				<div class="toast-editor"></div>
			</div>
		</div>
		<div class="form-row">
			<div class="label"></div>
			<div class="input">
				<input type="submit" value="작성" /> <a href="list">취소</a>
			</div>
		</div>
	</form>
</div>

<script type="text/x-template" id="editor1" style="display: none;">getBodyForXTemplate()</script>

<%@ include file="/jsp/part/foot.jspf"%>