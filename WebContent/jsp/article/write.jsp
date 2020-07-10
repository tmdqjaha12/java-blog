<%@ page import="java.util.List"%>
<%@ page import="com.sbs.java.blog.dto.Article"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>

<div class="article-doWrite-1" style="margin-top: 360px">
	<div class="con flex flex-jc-c" style="background-color: pink;">
		<table class="flex-jc-c">
			<thead>
			<caption>글쓰기</caption>
			</thead>
			<tbody>
				<form action="${pageContext.request.contextPath}/s/article/doWrite" method="get" encType="multiplart/form-data">

					<tr>
						<th>카테고리:</th>
						<td><select name="cateItemId"
							style="width: 100px; height: 30px; top: 300px;">

								<%
									for (CateItem cateItem : cateItems) {
								%>
								<option value="<%=cateItem.getId()%>"><%=cateItem.getName()%></option>
								<%
									}
								%>
						</select></td>
					</tr>
					<tr>
						<th>제목:</th>
						<td><input type="text" placeholder="제목을 입력하세요. " name="title" /></td>
					</tr>

					<tr>
						<th>내용:</th>
						<td><textarea style="resize: none;" cols="50" rows="20"
								placeholder="내용을 입력하세요. " name="body"></textarea></td>
					</tr>

					<!-- <tr style="display:none;">
						<th>첨부파일:</th>
						<td><input type="text" placeholder="파일을 선택하세요. "
							name="filename" /></td>
					</tr>
					<tr>
						<th>비밀번호:</th>
						<td><input type="password" placeholder="비밀번호를 입력하세요" /></td>
					</tr> -->
				<tr>
					<td colspan="2"><input type="submit" value="등록" /> <input
						type="button" value="임시 저장" /> <input type="button"
						value="글 목록으로... " onclick="javascript:location.href='list'" /></td>
				</tr>
				</form>
			</tbody>
		</table>

	</div>
</div>
<%@ include file="/jsp/part/foot.jspf"%>