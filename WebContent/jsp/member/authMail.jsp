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
	function getParameterByName(name) {
	    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
	            results = regex.exec(location.search);
	    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
	}
	
	var patId = getParameterByName('code');

	function submitAuthForm(form) {
		form.authCode.value = form.authCode.value.trim();
		if (form.authCode.value.length == 0) {
			alert('인증번호를 입력해주세요.');
			form.authCode.focus();
			return;
		}

		if (form.authCode.value != patId) {
			alert('옳바른 인증번호를 입력해주세요.');
			form.authCode.focus();
			return;
		}
		
		form.submit();
	}
</script>

<div class="auth-form-box con">
	<form action="doAuthMail" method="POST" class="login-form form1"
		onsubmit="submitAuthForm(this); return false;">
		<input type="hidden" name="redirectUrl" value="${param.afterLoginRedirectUrl}" />
		<input type="hidden" name="authCode" value="${authCode}" />
		<div class="form-row">
			<div class="label">인증번호 입력</div>
			<div class="input">
				<input name="inputAuthCode" type="text" placeholder="인증번호를 입력해주세요." />
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