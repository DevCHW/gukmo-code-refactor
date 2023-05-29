<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String ctxPath = request.getContextPath();
%>
<%-- 광고 상세보기 페이지입니다.. --%>

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%=ctxPath %>/resources/css/admin/advertisement/advertisementDetail.css" />
<%-- 직접만든 javascript --%>
<script type="text/javascript" src="<%=ctxPath %>/resources/js/admin/advertisement/advertisementDetail.js" ></script>

<%-- Main Content --%>
<div id="content">
  <%---------------------------- 사이드바 오른쪽 컨텐츠(container-fluid) 시작 -------------------------------%>
  <%-- Begin Page Content --%>
  <div class="container-fluid">
  	  <%-- Page Heading --%>
      <h1 class="h3 my-2 text-gray-800">${adDetail.client_name}고객 광고</h1>
      <p class="mb-4"></p>
      <%-- DataTales Example --%>
	  <div class="card shadow mb-4">
      	    <div class="card-body">
			  <div id="advertisement_basic_info_title" class="d-flex my-3">
			    <div>
			      <%-- 광고번호--%>
			      <div class="basic_info_title py-3 border-right border-bottom">광고번호</div>

			      <%-- 광고구분 --%>
			      <div class="basic_info_title py-3 border-right border-bottom">구분</div>

			      <%-- 첨부파일 --%>
			      <div class="basic_info_title py-3 border-right border-bottom">첨부파일</div>

			      <%-- URL --%>
			      <div class="basic_info_title py-3 border-right border-bottom">URL</div>

			      <%-- 광고기간 --%>
			      <div class="basic_info_title py-3 border-right border-bottom">광고기간</div>
			    </div>

			    <form name="editAdvertisementFrm" class="w-100" enctype="multipart/form-data">
			      <div id="advertisement_basic_info_area">
			        <%-- 광고번호 --%>
			        <div class="py-3 px-3 border-bottom">${advertisement.id}</div>

			        <%-- 광고구분 --%>
			        <div class="py-3 px-3 border-bottom">${advertisement.type}</div>

			        <%-- 첨부파일(다운로드 링크) --%>
			        <div class="py-3 px-3 border-bottom">
			          <span id="attach_file" onclick="location.href='/admin/advertisements/${advertisement.id}/download'">${advertisement.fileName}</span>
			          <input type="file" id="advertisementFile" name="advertisementFile" class="my-0 py-0"/>
			        </div>

			        <%-- URL --%>
			        <div class="py-3 px-3 border-bottom">${advertisement.url}</div>

			        <%-- 광고기간 --%>
			        <div class="py-3 px-3 border-bottom">
			          <span>${advertisement.startDate}</span>
			          <span>&nbsp;~&nbsp;</span>
			          <span>${advertisement.endDate}</span>
			          <span>(${advertisement.period})일</span>
			        </div>

			        <div id="datepicker_area" class="py-3 px-3 border-bottom">
			          <input type="text" id="start_date" name="startDate" value="${advertisement.startDate}" class="pl-2 border rounded" readonly>
			          <span>&nbsp;~&nbsp;</span>
			          <input type="text" id="end_date" name="endDate" value="${advertisement.endDate}" class="pl-2 border rounded" readonly>
			        </div>
			      </div>
			      <input type="hidden" id="hidden_advertisementId" name="advertisementId" value="${advertisement.id}">
			    </form>
			  </div>

			  <div class="d-flex justify-content-end">
			  <button type="button" class="btn btn-light border rounded mr-3" onclick="javascript:history.back()">뒤로가기</button>
			    <button id="btn_edit_advertisement" type="button" class="btn btn-light border rounded mr-3">수정</button>
			    <button id="btn_delete" type="button" class="btn btn-light border rounded">삭제</button>
			    <button id="btn_edit_close" type="button" class="btn btn-light border rounded">취소</button>
			  </div>
			</div>
		</div>
	</div>
</div>
<%---------------------------------- (div#main) 끝 -------------------------------------%>


