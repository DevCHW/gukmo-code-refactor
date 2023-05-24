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
    button#btn_comment_report, button#btn_board_report {
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

	  <div class="d-flex flex-column">
		<table class="my-2">
			<tbody>
				 <tr>
				 <c:if test="${not empty commentId}">
    				 <td style="font-weight:bold; width:100px;">댓글번호</td>
				 </c:if>
				 <c:if test="${not empty boardId}">
                     <td style="font-weight:bold; width:100px;">게시물번호</td>
                 </c:if>

                 <c:if test="${not empty commentId}">
    				 <td><span id="commentId" class="input_signup rounded pl-2" >${commentId}</span></td>
                 </c:if><c:if test="${not empty boardId}">
	    			 <td><span id="boardId" class="input_signup rounded pl-2" >${boardId}</span></td>
                 </c:if>
				</tr>
			</tbody>
		</table>
	   </div>
  	  <div>

      <select name="simpleReason" id="simple_report_reason" class="px-2 py-2 my-2 w-100 border rounded">
	       <option value="사유">사유</option>
	       <option value="스팸홍보/도배글입니다.">스팸홍보/도배글입니다.</option>
	       <option value="불법정보를 포함하고 있습니다.">불법정보를 포함하고 있습니다.</option>
	       <option value="청소년에게 유해한 내용입니다.">청소년에게 유해한 내용입니다.</option>
	       <option value="욕설/생명경시/혐오/차별적 표현입니다.">욕설/생명경시/혐오/차별적 표현입니다.</option>
	       <option value="개인정보 노출 게시물입니다.">개인정보 노출 게시물입니다.</option>
	       <option value="불쾌한 표현이 있습니다.">불쾌한 표현이 있습니다.</option>
	       <option value="기타">기타</option>
      </select>


    <div class="line">
        <label for="detail_report_reason" class="label_signup mt-3" style="font-size:14px; font-weight:bold;">상세사유</label>
    </div>
        <div>
          <textarea name="detailReason" id="detail_report_reason" class="form-control" style="height:250px;" placeholder="신고 상세사유를 적어주세요."></textarea>
        </div>
	</div>
	<footer>
	      <c:if test="${not empty commentId}">
		    <button type="button" id="btn_comment_report" class="btn border rounded w-100 mt-3">신고하기</button>
	      </c:if>
	      <c:if test="${not empty boardId}">
		    <button type="button" id="btn_board_report" class="btn border rounded w-100 mt-3">신고하기</button>
          </c:if>
	      <button type="button" id="btn_close" class="btn btn-light border rounded w-100 mt-3">취소</button>
	</footer>
	</form>
</div>

<input type="hidden" id="hidden_boardId" value="${boardId}"/>
<input type="hidden" id="hidden_commentId" value="${commentId}"/>
<!-- 신고자 id -->
<input type="hidden" id="hidden_reporterId" value="${loginMember.id}"/>





