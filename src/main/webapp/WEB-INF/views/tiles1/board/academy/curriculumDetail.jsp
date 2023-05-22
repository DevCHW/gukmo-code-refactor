<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String ctxPath = request.getContextPath();
%>


<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%=ctxPath %>/resources/css/board/academy/curriculumDetail.css" />

<div id="curriculum-detail-box" class="border mt-4">
      <%-- 교육기관 --%>
      <div class="curriculum_row border-bottom">
        <div class="curriculum_title px-2 py-2">교육기관</div>
        <div class="curriculum_content w-100 px-2 py-2">${curriculum.academyName}</div>
      </div>

      <%-- 핵심기술 --%>
      <div class="curriculum_row border-bottom">
        <div class="curriculum_title px-2 py-2">핵심기술</div>
        <div class="curriculum_content w-100 px-2 py-2">${curriculum.coreTechnology}</div>
      </div>

      <%-- 과정기간 --%>
      <div class="curriculum_row border-bottom">
        <div class="curriculum_title px-2 py-2">과정기간</div>
        <div class="curriculum_content w-100 px-2 py-2">${curriculum.curriculumStartDate} ~ ${curriculum.curriculumEndDate}&nbsp;(${curriculum.curriculumPeriod}일)</div>
      </div>
      <%-- 모집기간 --%>
      <div class="curriculum_row border-bottom">
        <div class="curriculum_title px-2 py-2">모집기간</div>
        <div class="curriculum_content w-100 px-2 py-2">${curriculum.recruitmentStartDate} ~ ${curriculum.recruitmentEndDate}&nbsp;(${curriculum.recruitmentPeriod}일)</div>
      </div>
      <%-- 모집인원 --%>
      <div class="curriculum_row border-bottom">
        <div class="curriculum_title px-2 py-2">모집인원</div>
        <div class="curriculum_content w-100 px-2 py-2">${curriculum.recruitsCount}명</div>
      </div>
      <%-- 신청링크 --%>
      <div class="curriculum_row">
        <div class="curriculum_title px-2 py-2">신청링크</div>
        <div class="curriculum_content w-100 px-2 py-2"><a href="${curriculum.url}">${curriculum.url}</a></div>
      </div>
    </div>