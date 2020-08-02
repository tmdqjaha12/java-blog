<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>
<style>
tr {
	text-align: center;
}

td {
	white-space: nowrap;
}

.mmd:active {
	background-color: black !important;
	color: white;
}

.modify-nick-name, .modify-email {
	display: none;
}

.modify-nick-name.active, .modify-email.active {
	display: block;
}
</style>

<script>
	function submitMemberModifyForm(form) {

		form.loginPw.value = form.loginPw.value.trim();
		if (form.loginPw.value.length == 0) {
			alert('비밀번호를 입력해주세요.');
			form.loginPw.focus();
			return;
		}
		form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
		if (form.loginPwConfirm.value.length == 0) {
			alert('비밀번호 확인을 입력해 주세요');
			form.loginPwConfirm.focus();
			return;
		}
		if (form.loginPw.value != form.loginPwConfirm.value) {
			alert('비밀번호가 일치하지 않습니다.');
			form.loginPwConfirm.focus();
			return;
		}
		//form.curbody.value = form.curbody.value.trim();
		//if (form.curbody.value.length == 0) {
		//	alert('경력 내용을 입력해주세요.');
		//	form.curbody.focus();
		//	return;
		//}
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
	// "추가" 버튼 클릭시 실행 되는 함수 입니다.
	var i = 1;
	function plusFun() {
		i++;
		var $cur = $("<input name=\"curbody" + i + "\" type=\"text\" style=\"display:block; margin:2px auto;\"/>");
		$('#cureer').append($cur);
	}
</script>

<div class="home-aboutMe-modify" style="margin-top: 300px;">
	<div class="con flex flex-jc-sb flex-column-nowrap"
		style="border: 3px solid pink; width: 500px;">
		<c:if test="${isLogined}">
			<table border="1">
				<form action="doMemberModify" method="get"
					class="password-form form1"
					onsubmit="submitMemberModifyForm(this); return false;">
					<input type="hidden" name="loginPwReal" /> <input type="hidden"
						name="loginId" value="${loginedMember.loginId}" />
					<tbody>
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
							<td>
								<div class="modify-nick-name-button"
									onclick="nicknameModifyForm()"
									style="display: inline-block; cursor: pointer;">변경</div>
								<div class="modify-nick-name">
									<input type="text" name="nickname"
										value="${loginedMember.nickname}" />
								</div>
							</td>
						</tr>
						<tr>
							<th>이메일</th>
							<td>
								<div class="modify-email-button" onclick="emailModifyForm()"
									style="display: inline-block; cursor: pointer;">변경</div>
								<div class="modify-email">
									<input type="text" name="email" value="${loginedMember.email}" />
								</div>
							</td>
						</tr>
						<tr>
							<th>경력</th>
							<td id="cureer">
								<input id="cureer" name="curbody1" type="text" style="margin-left: 34px;" placeholder="미구현" />
								<span id="a" class="span"
								onclick='plusFun();'
								style="margin-left: 20px; background-color: #efefef; cursor: pointer; vertical-align: middle; padding: 2px; border: solid 1px black;">+</span></td>
						</tr>

						<tr>
							<th>비밀번호</th>
							<td><input name="loginPw" type="password"
								placeholder="비밀번호 입력" />
						</tr>

						<tr>
							<th>비밀번호 확인</th>
							<td><input name="loginPwConfirm" type="password"
								placeholder="비밀번호 입력 확인" /></td>
						</tr>

						<tr>
							<th>비고</th>
							<td><input type="submit" value="확인" /> <a
								onclick="history.back();" style="cursor: pointer;">취소</a></td>
						</tr>
					</tbody>
				</form>
			</table>
		</c:if>
	</div>
</div>


<script>
	function nicknameModifyForm() {

		var $beforname = $('.modify-nick-name-button');

		if ($beforname.hasClass('active')) {
			$beforname.removeClass('active');
			$('.modify-nick-name').removeClass('active');
		} else {
			$beforname.addClass('active')
			$('.modify-nick-name').addClass('active');
		}
	}
	function emailModifyForm() {

		var $beforname = $('.modify-email-button');

		if ($beforname.hasClass('active')) {
			$beforname.removeClass('active');
			$('.modify-email').removeClass('active');
		} else {
			$beforname.addClass('active')
			$('.modify-email').addClass('active');
		}
	}
</script>

<%@ include file="/jsp/part/foot.jspf"%>