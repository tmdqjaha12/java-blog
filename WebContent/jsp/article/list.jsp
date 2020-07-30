
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>


<!-- 하이라이트 라이브러리 추가, 토스트 UI 에디터에서 사용됨 -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/highlight.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/styles/default.min.css">

<!-- 하이라이트 라이브러리, 언어 -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/css.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/javascript.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/xml.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/php.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/php-template.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/sql.min.js"></script>

<!-- 코드 미러 라이브러리 추가, 토스트 UI 에디터에서 사용됨 -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.48.4/codemirror.min.css" />

<!-- 토스트 UI 에디터, 자바스크립트 코어 -->
<script
	src="https://uicdn.toast.com/editor/latest/toastui-editor-viewer.min.js"></script>

<!-- 토스트 UI 에디터, 신택스 하이라이트 플러그인 추가 -->
<script
	src="https://uicdn.toast.com/editor-plugin-code-syntax-highlight/latest/toastui-editor-plugin-code-syntax-highlight-all.min.js"></script>

<!-- 토스트 UI 에디터, CSS 코어 -->
<link rel="stylesheet"
	href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />

<!-- LIST.JSP  -->



<!-- 해당 카테고리 시작 -->
<h1 class="con page-bn-1">
	${cateItemName}
</h1>
<!-- 해당 카테고리 끝 -->

<!-- 게시물 수 -->
<div class="con articles-count flex flex-jc-e">총 게시물 수 : ${totalCount}</div>
<!-- 게시물 끝 -->

<!-- 검색 폼 시작 -->
<div class="con search-box flex flex-jc-e">

	<form action="${pageContext.request.contextPath}/s/article/list">
		<input type="hidden" name="page" value="1" /> <input type="hidden"
			name="cateItemId" value="${param.cateItemId}" /> <input
			type="hidden" name="searchKeywordType" value="title" /> <input
			type="text" name="searchKeyword" value="${param.searchKeyword}" />
		<button type="submit">검색</button>
	</form>

</div>
<!-- 검색 폼 끝 -->

<!-- 리스트 시작 -->
<div class="con page-list-1">
	<ul style="text-align:center;">
		<li>게시물 번호</li>
		<li>작성일</li>
		<li>제목</li>
		<li>작성자</li>
		<li>조회수</li>
		<li>비고</li>
	</ul>


	<c:forEach items="${articles}" var="article">
	
	<ul>
		<li><a href="#">${article.id}</a></li>
		<li><a href="#" title="${article.regDate}">${article.regDate}</a></li>
		<li><a href="./detail?id=${article.id}" class="page-list-title" title="${article.title}">${article.title}</a></li>
		<!-- 
    		
			<li><a href="#" style="color:red;">탈퇴회원</a></li>
    		
		 -->
		<li><a href="">${article.extra.writer}</a>
		<!--<c:set var="nickname" value="${memberNickNames.get(article.memberId)}" />
			<c:choose>
	 
			    <c:when test="${empty nickname}">
			        <a href="#" style="color:red;">탈퇴 회원</a>
			    </c:when>
			 
			    <c:otherwise>
			        <a href="#">${memberNickNames.get(article.memberId)}</a>
			    </c:otherwise>
		 
			</c:choose>-->
		</li>
		<li><a href="#">${article.hit}</a></li>
		<li class="bigo" style="text-align: center; line-height: 55px;">
			<div class="inline-block" style="">
				<c:if test="${article.extra.deleteAvailable}">
					<a onclick="if ( confirm('삭제하시겠습니까?') == false ) return false;" href="./doDelete?id=${article.id}">삭제</a>
				</c:if> 
			</div>
			<div class="inline-block" style="">
				<c:if test="${article.extra.modifyAvailable}"> 
					<a href="./modify?id=${article.id}">수정</a>
				</c:if>
			</div>
		</li>
	</ul>
	
	</c:forEach>

</div>
<!-- 리스트 끝 -->

<!-- 게시물 페이징 시작 -->
<div class="con page-box">
	<ul>
		<c:if test="${page-5 > 0}">
			<span><a href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=${page-5}">◀</a></span>
		</c:if>
		
		<c:forEach var="i" begin="${page}" end="${page+4}" step="1">
		<li class="${i == param.page ? 'current' : ''}"><a href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=${i}"
			class="block" <c:if test="${i > totalPage}">style="display:none;"</c:if>>${i}</a></li>
		</c:forEach>
		
		<c:if test="${totalPage-page > 4}">
		<span><a href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=${page+5}">▶</a></span>
		</c:if>
	</ul>
</div>
<!-- 게시물 페이징 끝 -->

<script>

</script>

<%@ include file="/jsp/part/foot.jspf"%>