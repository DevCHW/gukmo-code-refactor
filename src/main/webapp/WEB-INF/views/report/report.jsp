<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String ctxPath = request.getContextPath();
%>

 <!-- Bootstrap CSS -->
  <link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/bootstrap-4.6.0-dist/css/bootstrap.min.css" >

  <!-- Font Awesome 5 Icons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css">

  <!-- noto sans -->
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700&display=swap" rel="stylesheet">

  <!-- Optional JavaScript -->
  <script type="text/javascript" src="<%= ctxPath%>/resources/jquery/jquery-3.6.0.min.js"></script>
  <script type="text/javascript" src="<%= ctxPath%>/resources/bootstrap-4.6.0-dist/js/bootstrap.bundle.min.js" ></script>

  <!-- 직접 만든 CSS -->
  <style type="text/css">
    button#reportWrite {
    	background-color: #14587D;
    	color: white;
    }
  </style>

  <!-- 직접만든 javascript -->
  <script type="text/javascript" src="<%=ctxPath %>/resources/js/report/report.js" ></script>

  <div class="container mt-3" class="d-flex flex-column">
   <!-- 신고 폼 시작 -->
   <form name="reportFrm">

	  <div class="d-flex justify-content-center">
	  	<h2>신고하기</h2>
	  </div>

      <hr style="background-color: black; height: 1px;">

	  <div class="border-bottom d-flex flex-column">
		<table class="my-2">
			<tbody>
				 <tr>
				 <c:if test="${not empty comment}">
    				 <td style="font-weight:bold">댓글번호</td>
				 </c:if>
				 <c:if test="${not empty comment}">
                     <td style="font-weight:bold">글번호</td>
                 </c:if>

                 <c:if test="${not empty comment}">
    				 <td><span id="commentId" class="input_signup rounded pl-2" >${comment.id}</span></td>
                 </c:if><c:if test="${not empty board}">
	    			 <td><span id="boardId" class="input_signup rounded pl-2" >${board.id}</span></td>
                 </c:if>
				</tr>
			</tbody>
		</table>
	   </div>
	     <!-- 신고자 id -->
		 <input type="hidden" name="reportId" value="${loginMember.id}"/>
  	  <div>

      <select name="simple_report_reason" id="simple_report_reason" class="px-2 py-2 my-2 w-100 border rounded">
	       <option value="">구분</option>
	       <option>스팸홍보/도배글입니다.</option>
	       <option>불법정보를 포함하고 있습니다.</option>
	       <option>청소년에게 유해한 내용입니다.</option>
	       <option>욕설/생명경시/혐오/차별적 표현입니다.</option>
	       <option>개인정보 노출 게시물입니다.</option>
	       <option>불쾌한 표현이 있습니다.</option>
	       <option>기타</option>
      </select>


    <div class="line">
        <label for="detail_report_reason" class="label_signup mt-3">상세사유</label>
    </div>
        <div>
          <textarea name="detail_report_reason" id="detail_report_reason" class="form-control" style="height:250px;" placeholder="신고 상세사유를 적어주세요."></textarea>
        </div>
	</div>
	<footer>
		  <button type="button" id="reportWrite" class="btn border rounded w-100 mt-3">신고하기</button>
	      <button type="button" id="btn_close" class="btn btn-light border rounded w-100 mt-3">취소</button>
	</footer>
	</form>
	 </div>





