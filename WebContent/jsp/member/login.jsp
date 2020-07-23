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
	function submitLoginForm(form) {
		form.loginId.value = form.loginId.value.trim();
		if (form.loginId.value.length == 0) {
			alert('로그인 아이디를 입력해주세요.');
			form.loginId.focus();
			return;
		}
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

<div class="login-form-box con">
	<form action="doLogin" method="POST" class="login-form form1"
		onsubmit="submitLoginForm(this); return false;">
		<input type="hidden" name="redirectUrl" value="${param.afterLoginRedirectUrl}" />
		<input type="hidden" name="loginPwReal" />
		<div class="form-row">
			<div class="label">로그인 아이디</div>
			<div class="input">
				<input name="loginId" type="text" placeholder="로그인 아이디를 입력해주세요." />
			</div>
		</div>
		<div class="form-row">
			<div class="label">로그인 비번</div>
			<div class="input">
				<input name="loginPw" type="password" placeholder="로그인 비번을 입력해주세요." />
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

<div class="find-box con">
	<div class="align-find-box">
		<form action="findId" method="POST">
			<input type="submit" value="아이디 찾기"/>
		</form>
		<form action="findPw" method="POST">
			<input type="submit" value="비밀번호 찾기"/>
		</form>
	</div>
</div>



<%@ include file="/jsp/part/foot.jspf"%>