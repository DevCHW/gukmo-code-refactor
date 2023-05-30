
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
	String ctxPath = request.getContextPath();
%>
  <%-- Latest compiled and minified CSS --%>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
  <%-- Latest compiled and minified JavaScript --%>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
  <%-- 직접 만든 CSS --%>
  <link rel="stylesheet" type="text/css" href="<%=ctxPath %>/resources/css/admin/advertisement/advertisementForm.css" />
  <%-- 직접만든 javascript --%>
  <script type="text/javascript" src="<%=ctxPath %>/resources/js/admin/advertisement/advertisementForm.js" ></script>


<%-- Main Content --%>
<div id="content">
    <%---------------------------- 사이드바 오른쪽 컨텐츠(container-fluid) 시작 -------------------------------%>
    <%-- Begin Page Content --%>
    <div class="container-fluid">
      <%-- Page Heading --%>
      <h1 class="h3 my-2 text-gray-800">광고등록</h1>
      <p class="mb-4"></p>
	  <%-- DataTales Example --%>
	  <div class="card shadow mb-4">
	      <div class="card-header py-3">
	          <h6 class="m-0 font-weight-bold text-primary">광고등록하기</h6>
	      </div>
	      <div class="card-body">
		      <form name="advertisementNewFrm" enctype="multipart/form-data">
		        <div id="input_box" class="d-flex flex-column m-auto">
		          <%-- 광고분류 --%>
		          <label for="division" class="advertisement_label">광고분류</label>
		          <select name="type" id="division" class="selectpicker border rounded w-100">
		            <option>광고분류 선택</option>
		            <option>MAIN</option>
		            <option>BOARD</option>
		          </select>

		          <%-- 파일 --%>
		          <label for="attach" class="advertisement_label">파일</label>
		          <input type="file" id="attach" name="advertisementFile">

		          <%-- URL주소 --%>
		          <label for="url" class="advertisement_label">URL</label>
		          <input type="text" id="url" name="url" class="advertisement_input border rounded" placeholder="URL을 입력해주세요">
		          <p id="url_error" class="error mt-1">유효한 URL을 입력해주세요</p>

		          <%-- 게시날짜 datepicker--%>
		          <label for="date" class="advertisement_label">날짜</label>
		          <div class="d-flex align-items-center">
		            <input type="text" id="start_date" name="startDate" class="advertisement_input border rounded" placeholder="시작일을 입력해주세요" readonly>
		            <span>&nbsp;~&nbsp;</span>
		            <input type="text" id="end_date" name="endDate" class="advertisement_input border rounded" placeholder="종료일을 입력해주세요" readonly>
		          </div>

		          <div id="btn_area" class="d-flex justify-content-end mt-3">
		            <button id="btn_add" type="button" class="btn btn-light border rounded">등록</button>
		          </div>
		        </div>
		      </form>
	      </div>
	    </div>
    </div>
    <%---------------------------------- 사이드바 오른쪽 컨텐츠(container-fluid) 끝 -------------------------------------%>
  </div>
