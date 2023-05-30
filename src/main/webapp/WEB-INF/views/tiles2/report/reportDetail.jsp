<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String ctxPath = request.getContextPath();
%>

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%=ctxPath %>/resources/css/admin/report/reportDetail.css" />


<%-- Main Content --%>
<div id="content">

    <%-- Begin Page Content --%>
    <div class="container-fluid">
      <%-- Page Heading --%>
      <h1 class="h3 my-2 text-gray-800">${report.id}번 신고내용</h1>
      <p class="mb-4">접수된 신고내용을 상세히 검토 후 접수처리해주세요.</p>

      <%-- DataTales Example --%>
      <div class="card shadow mb-4">
      	<div class="card-header py-3">
        	<h6 class="m-0 font-weight-bold text-primary">신고내역</h6>
        </div>
        <div class="card-body">
	      <%-- 신고상세보기시작 --%>
	      <div id="report_detail_box">
	        <%-- 신고번호 --%>
	        <div id="report_num">
	          <span class="report_info_title py-3">신고번호</span>
	          <span class="py-3">${report.id}</span>
	        </div>

	        <%-- 신고분류 --%>
	        <div id="report_type">
	          <span class="report_info_title py-3">신고분류</span>
	          <span id="span_report_type" class="py-3">${report.reportType}</span>
	        </div>

	        <%-- 신고 글번호 --%>
	        <c:if test="${report.boardId != null}">
	        <div id="report_num">
	          <span class="report_info_title py-3">신고 글번호</span>
	          <span class="py-3">${report.boardId}</span>
	        </div>
	        </c:if>

	        <c:if test="${report.commentId != null}">
	        <%-- 신고 댓글번호 --%>
            <div id="report_num">
              <span class="report_info_title py-3">신고 댓글번호</span>
              <span class="py-3">${report.commentId}</span>
            </div>
            </c:if>

	        <%-- 신고자 닉네임 --%>
	        <div id="reported_nickname">
	          <span class="report_info_title py-3">신고자 닉네임</span>
	          <span class="py-3">${report.nickname}</span>
	        </div>

	        <%-- 신고사유 --%>
	        <div id="simple_report_reason">
	          <span class="report_info_title py-3">신고사유</span>
	          <span class="py-3">${report.simpleReason}</span>
	        </div>

	        <%-- 신고사유가 기타사유인경우 상세보기 --%>
	        <div id="detail_report_reason">
	          <span class="report_info_title py-3">상세사유</span>
	          <span class="py-3">${report.detailReason}</span>
	        </div>

	        <%-- 신고날짜 --%>
	        <div id="report_date">
	          <span class="report_info_title py-3">신고날짜</span>
	          <span class="py-3">${report.reportDate}</span>
	        </div>

	      </div>
	      <%-- 신고상세보기 끝 --%>

	      <div id="div_btn_area" class="d-flex justify-content-end mt-3">
	      <button type="button" class="btn btn-light border rounded mr-3" onclick="javascript:history.back()">뒤로가기</button>
	      </div>
        </div>
      </div>
	</div>
</div>
<%---------------------------------- (div#main) 끝 -------------------------------------%>

