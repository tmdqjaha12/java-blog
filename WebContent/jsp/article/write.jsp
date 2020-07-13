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
				<form action="doWrite" method="POST" encType="multiplart/form-data"
					onsubmit="submitWriteForm(this); return false;">
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
					<!-- <div style="z-index:66;" id="editor1"></div> -->
					<td>

						<textarea style="resize: none;" cols="50" rows="20"
							placeholder="내용을 입력하세요. " name="body"></textarea>
					</td>
				</tr>

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
<!--  
<script>
var editor1 = new toastui.Editor({
	el : document.querySelector("#editor1"),
	height : "600px",
	initialEditType : "markdown",
	previewStyle : "vertical",
	initialValue : "# 안녕",
	plugins : [ toastui.Editor.plugin.codeSyntaxHighlight, youtubePlugin,
			replPlugin, codepenPlugin ]
});

</script>
 -->
<%@ include file="/jsp/part/foot.jspf"%>