<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>

<style>
tr {
	text-align: center;
}

td {
	white-space: nowrap;
}

.mmd:active {
	background-color:black !important;
	color:white;
}
</style>

<div class="home-aboutMe-1" style="margin-top: 300px;">
	<div class="con flex flex-jc-sb flex-column-nowrap"
		style="border: 3px solid pink; width: 500px;">
		<table border="1">
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
					<td>${loginedMember.nickname}</td>
				</tr>
				<tr>
					<th>이메일</th>
					<td>${loginedMember.email}</td>
				</tr>
				<tr>
					<th>경력</th>
					<td>---</td>
				</tr>

				<tr>
					<th>비고</th>
					<td><a
						href="${pageContext.request.contextPath}/s/member/memberModify" 
						class="block mmd" style="border:0.5px solid black; background-color:#efefef;">수정</a></td>

				</tr>


			</tbody>
		</table>
	</div>
</div>

<%@ include file="/jsp/part/foot.jspf"%>