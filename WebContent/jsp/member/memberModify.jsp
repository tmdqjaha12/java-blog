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
		form.curbody.value = form.curbody.value.trim();
		if (form.curbody.value.length == 0) {
			alert('경력 내용을 입력해주세요.');
			form.curbody.focus();
			return;
		}
		form.nickname.value = form.nickname.value.trim();
		if (form.nickname.value.length == 0) {
			alert('별명을 입력해주세요.');
			form.nickname.focus();
			return;
		}
		//form.email.value = form.email.value.trim();
		//if (form.email.value.length == 0) {
		//	alert('이메일을 입력해주세요.');
		//	form.email.focus();
		//	return;
		//}
		form.loginPwReal.value = sha256(form.loginPw.value);
		form.loginPw.value = '';
		form.loginPwConfirm.value = '';
		form.submit();
	}

		// "추가" 버튼 클릭시 실행 되는 함수 입니다.
    	function plusFun() {
    	var $cur = $("<input name=\"curbody\" type=\"text\" style=\"display:block; margin:2px auto;\"/>");
    	$('#cureer').append($cur);
    } 
    	
</script>

<style>
tr {
	text-align: center;
}

td {
	white-space: nowrap;
}

.mmd:active, .span:active{
	background-color:black !important;
	color:white;
}
</style>

<div class="" style="margin-top: 300px;">
	<div class="con flex flex-jc-sb flex-column-nowrap"
		style="border: 3px solid pink; width: 500px;">
		<table border="1">
			<tbody>
				<form action="">
					<tr>
						<th>사진</th>
						<td><div class="img-box"
								style="width: 120px; height: 120px; background-color: pink; margin: 0 auto; margin-top: 5px; margin-bottom: 5px; border: 1px solid #920855;"></td>
					</tr>
					<tr>
						<th>아이디</th>
						<td>${loginedMember.loginId}</td>
					</tr>
					<tr>
						<th>이름</th>
						<td>${loginedMember.name}</td>
					</tr>
					<tr>
						<th>별명</th>
						<td><input name="nickname" type="text" value="${loginedMember.nickname}" style="margin-left:90px;"/></td>
					</tr>
					<tr>
						<th>이메일</th>
						<td>${loginedMember.email}</td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<td><input name="loginPw" type="password" /></td>
					</tr>
					<tr>
						<th>비밀번호 확인</th>
						<td><input name="loginPwConfirm" type="password" /></td>
					</tr>
					<tr>
						<th>경력</th>
						<td id="cureer"><input name="curbody" type="text" style="margin-left:34px;"/><span id="a" class="span" onclick='plusFun();' style="margin-left:20px; background-color:#efefef; cursor:pointer; vertical-align: middle; padding:2px; border:solid 1px black;">+</span></td>
					</tr>
	
					<tr>
						<th>비고</th>
						<td><input type="submit" value="작성 완료" style="display:block; width:100%;" /></td>
	
					</tr>

				</form>
			</tbody>
		</table>
	</div>
</div>

<%@ include file="/jsp/part/foot.jspf"%>