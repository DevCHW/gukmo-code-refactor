<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String ctxPath = request.getContextPath();
%>

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%=ctxPath %>/resources/css/board/academy/curriculumForm.css" />
<%-- Google reCAPTCHA v2 --%>
<script src="https://www.google.com/recaptcha/api.js"></script>
<%-- 직접만든 javascript --%>
<script type="text/javascript" src="<%=ctxPath%>/resources/js/board/academy/curriculumForm.js" ></script>

  <div class="container my-5">
    <%-- 교육과정 작성 폼 시작 --%>
    <form name="writerFrm" class="d-flex flex-column">

	  <%-- 등록용 닉네임 --%>
	  <input type="hidden" name="memberId" value="${loginMember.id}" />
      <input type="hidden" name="orgin_hashTag" value="<c:forEach var="hashtags" items="${hashtags}">${hashtags}></c:forEach>"/>
      <input type="hidden" id="hashtags" name="hashtags" value=""/>
      <c:if test="${not empty curriculum}">
        <input type="hidden" id="boardId" name="boardId" value="${curriculum.id}"/>
      </c:if>


	  <%-- 학원명 --%>
      <div class="d-flex align-items-center my-3">
        <label for="academy_name" class="curriculum_label mt-3">교육기관명</label>
        <c:if test="${loginMember.userRole == 'ACADEMY' && empty curriculum}">
          <input type="text" id="academy_name" name="academyName" class="curriculum_input border rounded pl-2 w-100" value="${academyMember.academyName}" readonly>
        </c:if>
        <c:if test="${loginMember.userRole == 'ADMIN' && empty curriculum}">
          <input type="text" id="academy_name" name="academyName" class="curriculum_input border rounded pl-2 w-100" placeholder="교육기관명을 입력해주세요" maxlength="50">
        </c:if>
        <c:if test="${not empty curriculum}">
            <input type="text" id="academy_name" name="academyName" class="curriculum_input border rounded pl-2 w-100" placeholder="교육기관명을 입력해주세요" value="${curriculum.academyName}" maxlength="50" readonly>
        </c:if>
      </div>

      <%-- 교육과정명 --%>
      <div class="d-flex align-items-center my-3">
        <label for="subject" class="curriculum_label mt-3">교육과정명</label>
        <input type="text" id="subject" name="subject" class="curriculum_input border rounded pl-2 w-100" placeholder="교육과정명을 입력해주세요!" value="${curriculum.subject}" maxlength="50">
      </div>

      <%-- 과정기간 --%>
      <div class="d-flex align-items-center my-3">
        <label for="period" class="curriculum_label">과정기간</label>
        <input type="text" id="curriculum_start_date" name="curriculumStartDate" class="curriculum_input border rounded pl-2 w-100" placeholder="과정 시작일자" value="${curriculum.curriculumStartDate}" readonly>
        <span class="tilde text-center">&nbsp;~&nbsp;</span>
        <input type="text" id="curriculum_end_date" name="curriculumEndDate" class="curriculum_input border rounded pl-2 w-100" placeholder="과정 마감일자" value="${curriculum.curriculumEndDate}" readonly>
      </div>


      <%-- 모집기간 --%>
      <div class="d-flex align-items-center my-3">
        <label for="period" class="curriculum_label">모집기간</label>
        <input type="text" id="recruitment_start_date" name="recruitmentStartDate" class="curriculum_input border rounded pl-2 w-100" placeholder="모집 시작일자" value="${curriculum.recruitmentStartDate}" readonly>
        <span class="tilde text-center">&nbsp;~&nbsp;</span>
        <input type="text" id="recruitment_end_date" name="recruitmentEndDate" class="curriculum_input border rounded pl-2 w-100" placeholder="모집 마감일자" value="${curriculum.recruitmentEndDate}" readonly>
      </div>


      <%-- 모집인원 --%>
      <div class="d-flex align-items-center my-3">
        <label for="cnt_recruits" class="curriculum_label mt-3">모집인원(명)</label>
        <input type="text" id="cnt_recruits" name="recruitsCount" class="curriculum_input border rounded pl-2 w-100" placeholder="모집인원을 입력해주세요!(명)" value="${curriculum.recruitsCount}" maxlength="4">
      </div>


      <%-- 신청URL --%>
      <div class="d-flex align-items-center my-3">
        <label for="join_url" class="curriculum_label mt-3">과정신청링크</label>
        <input type="text" id="join_url" name="url" class="curriculum_input border rounded pl-2 w-100" value="${curriculum.url}" placeholder="과정을 신청할 수 있는 URL을 입력해주세요!">
      </div>


      <%-- 핵심기술 --%>
      <div class="d-flex align-items-center my-3">
        <label for="core_technology" class="curriculum_label mt-3">핵심기술</label>
        <input type="text" id="core_technology" name="coreTechnology" class="curriculum_input border rounded pl-2 w-100" value="${curriculum.coreTechnology}" placeholder="교육과정 핵심기술을 입력해주세요 ex)java,Spring">
      </div>


      <%-- content --%>
      <label for="content" class="curriculum_label my-3">과정소개글</label>
      <textarea name="content" id="content" class="px-2 py-2 w-100 border rounded" cols="30" rows="15" placeholder="교육과정 상세내용">${curriculum.content}</textarea>


      <%-- hashtag --%>
      <label for="hashtag" class="curriculum_label my-3">태그</label>
      <div id="hashtag_box" class="border rounded pl-2">
        <ul id="hashtag_list" class="d-flex align-items-center">
          <c:forEach var="hashtag" items="${hashtags}" varStatus="status">
          <li class='d-flex align-items-center flex-nowrap mr-2 tag-item'>#${hashtag}<span class='btn_hashtag_delete mx-2' style='cursor:pointer; color:darkgray; idx='${status.count}'><i class='fa-solid fa-xmark'></i></span></li>
		  </c:forEach>
          <input type="text" id="hashtag" name="hashtag" class="border-0 w-auto" placeholder="태그를 설정하세요(최대 5개)">
        </ul>
      </div>
    </form>
    <%-- 교육과정 작성 폼 끝 --%>

    <%-- Google reCAPTCHA --%>
    <div class="d-flex justify-content-center my-5">
      <div class="g-recaptcha" data-callback="callBackRecaptcha" data-sitekey="6LdO7zkjAAAAAFk660Urlo0EbazNdIIW9aFnJXLH"></div>
    </div>

    <%-- 수정일 경우에는 등록 대신 수정버튼 태그라이브러리로 구현예정 --%>
    <div id="btn_wrapper" class="d-flex justify-content-end my-3">
      <c:if test="${not empty curriculum}" >
      <button id="btn_edit" type="button" class="btn border rounded">수정</button>
      </c:if>
      <c:if test="${empty curriculum}" >
      <button id="btn_write" type="button" class="btn border rounded">등록</button>
      </c:if>
      <button id="btn_cancle" type="button" class="btn border rounded ml-3" onclick="javascript:history.back()">취소</button>
    </div>
  </div>