<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String ctxPath = request.getContextPath();
%>

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%=ctxPath %>/resources/css/board/community/communityForm.css" />
<%-- Google reCAPTCHA v2 --%>
<script src="https://www.google.com/recaptcha/api.js"></script>
<%-- 직접만든 javascript --%>
<script type="text/javascript" src="<%=ctxPath %>/resources/js/board/community/communityForm.js" ></script>

  <div class="container my-5">
    <%-- 커뮤니티 작성 폼 시작 --%>
    <form name="writeFrm" class="d-flex flex-column" enctype="multipart/form-data">
	  <%-- 등록용 정보 --%>
	  <input type="hidden" name="memberId" value="${loginMember.id}" />
	  <input type="hidden" name="firstCategory" value="커뮤니티"/>
	  <input type="hidden" name="orgin_hashTag"
	         value="<c:forEach var="hashtag" items="${hashtags}">${hashtag}></c:forEach>"
	         />
	  <input type="hidden" id="hashtags" name="hashtags" value=""/>
	  <input type="hidden" id="boardId" name="id" value="${board.id}"/>

      <%-- category --%>
      <label for="secondCategory" class="community_label">구분</label>
      <select name="secondCategory" id="secondCategory" class="community_input pl-2 border rounded" value="${board.secondCategory}">
        <c:if test="${empty board}">
          <option value="${board.secondCategory}">구분을 선택해주세요</option>
        </c:if>
        <c:if test="${not empty board}">
          <option value="${board.secondCategory}">${board.secondCategory}</option>
        </c:if>
        <option value="스터디">스터디</option>
        <option value="자유">자유</option>
        <option value="QnA">Q&A</option>
        <option value="취미모임">취미모임</option>
        <option value="수강/취업후기">수강/취업 후기</option>
      </select>

      <%-- subject --%>
      <label for="subject" class="community_label mt-3">제목</label>
      <input type="text" id="subject" name="subject" class="community_input border rounded pl-2" value="${board.subject}" placeholder="제목을 입력하세요" maxlength="50">

      <%-- hashtag --%>
      <label for="hashtag" class="community_label mt-3">태그</label>
      <div id="hashtag_box" class="border rounded pl-2">
        <ul id="hashtag_list" class="d-flex align-items-center">
          <c:forEach var="hashtag" items="${hashtags}" varStatus="status">
          <li class='d-flex align-items-center flex-nowrap mr-2 tag-item'>#${hashtag}<span class='btn_hashtag_delete mx-2' style='cursor:pointer; color:darkgray; idx='${status.count}'><i class='fa-solid fa-xmark'></i></span></li>
		  </c:forEach>
          <input type="text" id="hashtag" name="hashtag" class="border-0" placeholder="태그를 설정하세요(최대 5개)">
        </ul>
      </div>


      <%-- content --%>
      <label for="content" class="community_label mt-3">본문</label>
      <textarea name="content" id="content" class="px-2 py-2 border rounded" cols="30" rows="15" style="width:100%;margin:0;">${board.content}</textarea>
    </form>
    <%-- 커뮤니티 작성 폼 끝 --%>


    <%-- Google reCAPTCHA --%>
    <div class="d-flex justify-content-center my-5">
      <div class="g-recaptcha" data-callback="callBackRecaptcha" data-sitekey="6LdO7zkjAAAAAFk660Urlo0EbazNdIIW9aFnJXLH"></div>
    </div>

    <%-- 수정일 경우에는 등록 대신 수정버튼 태그라이브러리로 구현예정 --%>
    <div id="btn_wrapper" class="d-flex justify-content-end mt-3">
    <c:if test="${not empty board}" >
	  <button id="btn_modify" type="button" class="btn border rounded">수정</button>
    </c:if>
    <c:if test="${empty board}" >
      <button id="btn_write" type="button" class="btn border rounded">등록</button>
    </c:if>
      <button id="btn_cancle" type="button" class="btn border rounded ml-3" onclick="javascript:history.back()">취소</button>
    </div>
  </div>