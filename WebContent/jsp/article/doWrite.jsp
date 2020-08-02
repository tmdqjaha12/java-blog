
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>

<div class="article-list-1" style="margin-top: 300px;">
	<div class="con flex flex-jc-sb flex-column-nowrap" style="border:3px solid pink; width:500px; height:500px;">
	
		<div class="img-box" style="width:120px; height:120px; background-color:pink; margin:0 auto; margin-top:5px; border:1px solid #920855;">
			
		</div>
		<ul class="itsMe" style="margin:0 auto;">
			<li>이름 : 홍길동</li>
			<li>이메일 : --------@-----.---</li>
			<li>연락처 : -------------</li>
		</ul>
		
		<ul class="career" style="margin:0 auto; margin-bottom:5px;">
			<li>경력5 : ----</li>
			<li>경력4 : ----</li>
			<li>경력3 : ----</li>
			<li>경력2 : ----</li>
			<li>경력1 : ----</li>
		</ul>
	</div>
</div>
<!-- 
<div class="home-menu-1" style="margin-top: 500px">
	<div class="con"
		style="display: none; background-color: pink; width: 100px; height: 100px">
		<table>
			<thead>
			<caption>글쓰기</caption>
			</thead>
			<tbody>
				<form action="write_ok.jsp" method="post"
					encType="multiplart/form-data">
					<tr>
						<th>제목:</th>
						<td><input type="text" placeholder="제목을 입력하세요. "
							name="subject" /></td>
					</tr>
					<tr>
						<th>내용:</th>
						<td><textarea cols="10" placeholder="내용을 입력하세요. "
								name="content"></textarea></td>
					</tr>
					<tr>
						<th>첨부파일:</th>
						<td><input type="text" placeholder="파일을 선택하세요. "
							name="filename" /></td>
					</tr>
					<tr>
						<th>비밀번호:</th>
						<td><input type="password" placeholder="비밀번호를 입력하세요" /></td>
					</tr>
					<tr>
						<td colspan="2"><input type="button" value="등록"
							onclick="sendData()" /> <input type="button" value="reset" /> <input
							type="button" value="글 목록으로... "
							onclick="javascript:location.href='list.jsp'" /></td>
					</tr>
				</form>
			</tbody>
		</table>

	</div>
</div> -->
<%@ include file="/jsp/part/foot.jspf"%>