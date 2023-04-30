<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctxPath = request.getContextPath();
%>

<!-- 직접 만든 CSS -->
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/resources/css/board/navbar.css" />

<!-- 직접만든 javascript -->
<script type="text/javascript">
  $(document).ready(function(){
    let secondCategory = "${boardRequest.secondCategory}";
    switch (secondCategory) {
	  case "자유" :	//보고있는 페이지가 "자유게시판"일 경우 .active 추가
		  $("div#freeBoards").addClass("active");
		  break;
	  case "질문" : //보고있는 페이지가 "Q&A"일 경우 .active 추가
		  $("div#questions").addClass("active");
		  break;
	  case "스터디" : //보고있는 페이지가 "스터디"일 경우 .active 추가
		  $("div#studies").addClass("active");
		  break;
	  case "취미모임" : //보고있는 페이지가 "취미모임"일 경우 .active 추가
		  $("div#hobbies").addClass("active");
		  break;
	  case "수강/취업후기" : //보고있는 페이지가 "수강/취업후기"일 경우 .active 추가
		  $("div#reviews").addClass("active");
		  break;
	}//end of switch-case---


	$("div.detail-category").click(function(e){
		const target = $(e.currentTarget);
		const id = target.attr("id");
		switch (id) {
		  case "freeBoards" :
			  location.href="<%=ctxPath%>/boards?firstCategory=커뮤니티&secondCategory=자유";
			  break;
		  case "questions" :
			  location.href="<%=ctxPath%>/boards?firstCategory=커뮤니티&secondCategory=질문";
			  break;
		  case "studies" :
			  location.href="<%=ctxPath%>/boards?firstCategory=커뮤니티&secondCategory=스터디";
			  break;
		  case "hobbies" :
			  location.href="<%=ctxPath%>/boards?firstCategory=커뮤니티&secondCategory=취미모임";
			  break;
		  case "reviews" :
			  location.href="<%=ctxPath%>/boards?firstCategory=커뮤니티&secondCategory=수강/취업후기";
			  break;
		}
	});//end of Event---
  });//end of $(document).ready(function(){})

</script>


<div id="category" class="d-flex justify-content-center align-content-center">
  <div id="freeBoards" class="detail-category mx-2 px-2 py-1 rounded">자유게시판</div>
  <div id="questions" class="detail-category mx-2 px-2 py-1 rounded">Q&A</div>
  <div id="studies" class="detail-category mx-2 px-2 py-1 rounded">스터디</div>
  <div id="hobbies" class="detail-category mx-2 px-2 py-1 rounded">취미모임</div>
  <div id="reviews" class="detail-category mx-2 px-2 py-1 rounded">수강/취업후기</div>
</div>
