
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<div class="member-login-1" style="margin-top: 300px;">
	<div class="con "
		style="border: 3px solid pink; width: 500px; height: 500px;">

		<table style="margin: 0 auto; margin-top: 150px;">
			<thead>
			<caption>로그인</caption>
			</thead>
			<tbody>
				<form action="doLogin" method="POST" encType="multiplart/form-data"
					onsubmit="submitLoginForm(this); return false;">
					<input type="hidden" name="loginPwReal">
					<tr>
						<th>아이디:</th>
						<td><input type="text" placeholder="아이디를 입력하세요. "
							name="loginId" /></td>
					</tr>

					<tr>
						<th>비밀번호:</th>
						<td><input type="password" placeholder="비밀번호를 입력하세요. "
							name="loginPw" /></td>
					</tr>

					<tr>
						<td colspan="2"><input type="submit" value="로그인" /> <a
							href="../home/main">취소</a></td>
					</tr>
				</form>
			</tbody>
		</table>

	</div>
</div>
<%@ include file="/jsp/part/foot.jspf"%>