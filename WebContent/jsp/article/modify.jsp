<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf" %>

<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- 하이라이트 라이브러리 추가, 토스트 UI 에디터에서 사용됨 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/highlight.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/styles/default.min.css">

<!-- 하이라이트 라이브러리, 언어 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/css.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/javascript.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/xml.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/php.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/php-template.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/sql.min.js"></script>

<!-- 코드 미러 라이브러리 추가, 토스트 UI 에디터에서 사용됨 -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.48.4/codemirror.min.css" />

<!-- 토스트 UI 에디터, 자바스크립트 코어 -->
<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>

<!-- 토스트 UI 에디터, 신택스 하이라이트 플러그인 추가 -->
<script src="https://uicdn.toast.com/editor-plugin-code-syntax-highlight/latest/toastui-editor-plugin-code-syntax-highlight-all.min.js"></script>

<!-- 토스트 UI 에디터, CSS 코어 -->
<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />

<div class="article-modify-1" style="margin-top: 300px;">
	<div class="con flex flex-jc-sb flex-column-nowrap" style="border:3px solid pink; width:500px; height:500px;">
		<table class="flex-jc-c">
			<thead>
			<caption><div style="font-weight: bold;"><%=request.getAttribute("id") %>번 게시글 </div>수정</caption>
			</thead>
			<tbody>
				<form action="doModify" encType="multiplart/form-data" method="POST" encType="multiplart/form-data" 
				onsubmit="submitWriteForm(this); return false;">
					<input type="hidden" name="id" value="<%=request.getAttribute("id")%>"/>
					<input type="hidden" name="regDate" value="<%=request.getAttribute("regDate")%>"/>
				
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
						<td><input type="text" placeholder="제목을 입력하세요. " value="<%=request.getAttribute("title") %>" name="title" /></td>
					</tr>

					<tr>
						<th>내용:</th>
						<td><textarea style="resize: none;" cols="50" rows="20"
								placeholder="내용을 입력하세요. " name="body"><%=request.getAttribute("body") %></textarea></td>
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
					<td colspan="2"><input type="submit" value="수정" /> <input
						type="button" value="임시 저장" /> <input type="button"
						value="글 목록으로... " onclick="javascript:location.href='list'" /></td>
				</tr>
				</form>
			</tbody>
		</table>
	</div>
</div>
<%@ include file="/jsp/part/foot.jspf" %>