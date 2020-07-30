<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<c:set var="pageTitle" value="비밀번호 확인"></c:set>
<%@ include file="/jsp/part/head.jspf"%>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<style>
/* cus */
.password-form-box {
	margin-top: 30px;
}
</style>

<script>
	function submitLoginForm(form) {
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

<div class="password-form-box con">
	<form action="doPasswordForPrivate" method="POST" class="password-form form1"
		onsubmit="submitLoginForm(this); return false;">
		<input type="hidden" name="loginPwReal" />
		<div class="form-row">
			<div class="label">로그인 비번</div>
			<div class="input">
				<input name="loginPw" type="password" placeholder="로그인 비번을 입력해주세요." />
			</div>
		</div>
		<div class="form-row">
			<div class="label">확인</div>
			<div class="input">
				<input type="submit" value="확인" /> <a href="../home/main">취소</a>
			</div>
		</div>
	</form>
</div>

<%@ include file="/jsp/part/foot.jspf"%> 