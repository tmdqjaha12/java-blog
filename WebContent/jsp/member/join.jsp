<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<style>
/* cus */
.join-form-box {
	margin-top: 30px;
}
</style>

<script>
	function submitJoinForm(form) {
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
		form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
		if (form.loginPwConfirm.value.length == 0) {
			alert('로그인 비번확인을 입력해주세요.');
			form.loginPwConfirm.focus();
			return;
		}
		if (form.loginPw.value != form.loginPwConfirm.value) {
			alert('로그인 비번확인이 일치하지 않습니다.');
			form.loginPwConfirm.focus();
			return;
		}
		form.name.value = form.name.value.trim();
		if (form.name.value.length == 0) {
			alert('이름을 입력해주세요.');
			form.name.focus();
			return;
		}
		form.nickname.value = form.nickname.value.trim();
		if (form.nickname.value.length == 0) {
			alert('별명을 입력해주세요.');
			form.nickname.focus();
			return;
		}
		form.email.value = form.email.value.trim();
		if (form.email.value.length == 0) {
			alert('이메일을 입력해주세요.');
			form.email.focus();
			return;
		}
		form.loginPwReal.value = sha256(form.loginPw.value);
		form.loginPw.value = '';
		form.loginPwConfirm.value = '';
		form.submit();
	}
</script>

<div class="join-form-box con">
	<form action="doJoin" method="get" class="join-form form1"
		onsubmit="submitJoinForm(this); return false;">
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
			<div class="label">로그인 비번확인</div>
			<div class="input">
				<input name="loginPwConfirm" type="password"
					placeholder="로그인 비번확인을 입력해주세요." />
			</div>
		</div>
		<div class="form-row">
			<div class="label">이름</div>
			<div class="input">
				<input name="name" type="text" placeholder="이름을 입력해주세요." />
			</div>
		</div>
		<div class="form-row">
			<div class="label">별명</div>
			<div class="input">
				<input name="nickname" type="text" placeholder="별명을 입력해주세요." />
			</div>
		</div>
		<div class="form-row">
			<div class="label">이메일</div>
			<div class="input">
				<input name="email" type="email" placeholder="이메일을 입력해주세요." />
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