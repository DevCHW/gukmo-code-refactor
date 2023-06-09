<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String ctxPath = request.getContextPath();
%>

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%=ctxPath %>/resources/css/board/academy/academyDetail.css" />

<div id="academy_img_box" class="w-100">
  <img src="<%=ctxPath%>/resources/images/${academy.academyImage}">
</div>
<div id="academy-detail-box" class="border mt-4">
  <%-- 대표자명 --%>
  <div class="academy_row border-bottom">
    <div class="academy_title px-2 py-2">대표자명</div>
    <div class="academy_content w-100 px-2 py-2">${academy.representativeName}</div>
  </div>

  <%-- 교육기관주소 --%>
  <div class="academy_row border-bottom">
    <div class="academy_title px-2 py-2">교육기관주소</div>
    <div class="academy_content w-100 px-2 py-2">${academy.address}</div>
  </div>

  <%-- 문의처 --%>
  <div class="academy_row border-bottom">
    <div class="academy_title px-2 py-2">문의처</div>
    <div class="academy_content w-100 px-2 py-2">${academy.phone}</div>
  </div>

  <%-- 홈페이지주소 --%>
  <div class="academy_row">
    <div class="academy_title px-2 py-2">홈페이지주소</div>
    <div class="academy_content w-100 px-2 py-2"><a href="${academy.homepage}">${academy.homepage}</a></div>
  </div>
</div>

