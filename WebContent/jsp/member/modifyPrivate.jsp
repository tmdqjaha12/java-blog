<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<c:set var="pageTitle" value="개인정보 수정"></c:set>
<%@ include file="/jsp/part/head.jspf"%>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<style>
/* cus */
.modify-private-form-box {
	margin-top: 30px;
}
</style>

<script>
	function ModifyPrivateForm__submit(form) {
		form.loginPw.value = form.loginPw.value.trim();
		if (form.loginPw.value.length == 0) {
			alert('로그인 비번을 입력해주세요.');
			form.loginPw.focus();
			return;
		}
		form.loginPwReal.value = sha256(form.loginPw.value);
		form.loginPw.value = '';
		form.submit();
	}
</script>

<div class="modify-private-form-box con">
	<form action="doModifyPrivate" method="POST" class="form1"
		onsubmit="ModifyPrivateForm__submit(this); return false;">
		<input type="hidden" name="authCode" value="${param.authCode}" />
		<input type="hidden"
			name="loginPwReal" />
		<div class="form-row">
			<div class="label">새 로그인 비번</div>
			<div class="input">
				<input name="loginPw" type="password" placeholder="새 로그인 비번을 입력해주세요." />
			</div>
		</div>
		<div class="form-row">
			<div class="label">새 로그인 비번 확인</div>
			<div class="input">
				<input name="loginPwConfirm" type="password" placeholder="새 로그인 비번을 입력해주세요." />
			</div>
		</div>
		<div class="form-row">
			<div class="label">전송</div>
			<div class="input">
				<input type="submit" value="전송" /> <a href="../home/main">취소</a>
			</div>
		</div>
	</form>
</div>

<%@ include file="/jsp/part/foot.jspf"%> 