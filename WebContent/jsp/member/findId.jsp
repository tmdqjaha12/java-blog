<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<style>
/* cus */
.login-form-box {
	margin-top: 30px;
}
</style>

<script>
	function submitFindIdForm(form) {
		form.name.value = form.name.value.trim();
		if (form.name.value.length == 0) {
			alert('이름을 입력해주세요.');
			form.name.focus();
			return;
		}
		form.email.value = form.email.value.trim();
		if (form.email.value.length == 0) {
			alert('이메일을 입력해주세요.');
			form.email.focus();
			return;
		}
		form.submit();
	}
</script>

<div class="login-form-box con">
	<form action="doFindId" method="POST" class="form1"
		onsubmit="submitFindIdForm(this); return false;">
		<div class="form-row">
			<div class="label">이름</div>
			<div class="input">
				<input name="name" type="text" placeholder="이름을 입력해주세요." />
			</div>
		</div>
		<div class="form-row">
			<div class="label">이메일</div>
			<div class="input">
				<input name="email" type="text" placeholder="이메일을 입력해주세요." />
			</div>
		</div>
		<div class="form-row">
			<div class="label"></div>
			<div class="input">
				<input type="submit" value="아이디 찾기" /> <a href="../home/main">취소</a>
			</div>
		</div>
	</form>
</div>


<%@ include file="/jsp/part/foot.jspf"%>