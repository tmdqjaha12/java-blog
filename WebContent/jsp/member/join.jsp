
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<div class="member-join-1" style="margin-top: 300px;">
	<div class="con "
		style="border: 3px solid pink; width: 500px; height: 500px;">

		<table style="margin: 0 auto; margin-top: 150px;">
			<thead>
			<caption>회원가입</caption>
			</thead>
			<tbody>
				<form action="doJoin" method="POST" encType="multiplart/form-data"
					onsubmit="submitJoinForm(this); return false;">
					<input type="hidden" name="loginPwReal">
					<tr>
						<th>아이디:</th>
						<td><input type="text" placeholder="아이디를 입력하세요. "
							name="loginId" /></td>
					</tr>

					<tr>
						<th>이름:</th>
						<td><input type="text" placeholder="이름을 입력하세요. " name="name" /></td>
					</tr>

					<tr>
						<th>닉네임:</th>
						<td><input type="text" placeholder="닉네임을 입력하세요. "
							name="nickname" /></td>
					</tr>

					<tr>
						<th>비밀번호:</th>
						<td><input type="password" placeholder="비밀번호를 입력하세요. "
							name="loginPw" /></td>
					</tr>

					<tr>
						<th>비밀번호 확인:</th>
						<td><input type="password" placeholder="비밀번호 확인을 입력하세요. "
							name="loginPwConfirm" /></td>
					</tr>

					<tr>
						<td colspan="2"><input type="submit" value="전송" /> <a
							href="../home/main">취소</a></td>
					</tr>
				</form>
			</tbody>
		</table>

	</div>
</div>
<%@ include file="/jsp/part/foot.jspf"%>