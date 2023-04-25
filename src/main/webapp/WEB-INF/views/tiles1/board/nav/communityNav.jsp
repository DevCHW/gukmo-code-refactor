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
    const category = "${category}";
    switch (category) {
	  case "free" :	//보고있는 페이지가 "자유게시판"일 경우 .active 추가
		  $("div#freeBoards").addClass("active");
		  break;
	  case "question" : //보고있는 페이지가 "Q&A"일 경우 .active 추가
		  $("div#questions").addClass("active");
		  break;
	  case "studies" : //보고있는 페이지가 "스터디"일 경우 .active 추가
		  $("div#studies").addClass("active");
		  break;
	  case "hobbies" : //보고있는 페이지가 "취미모임"일 경우 .active 추가
		  $("div#hobbies").addClass("active");
		  break;
	  case "reviews" : //보고있는 페이지가 "수강/취업후기"일 경우 .active 추가
		  $("div#reviews").addClass("active");
		  break;
	}//end of switch-case---


	$("div.detail-category").click(function(e){
		const target = $(e.currentTarget);
		const id = target.attr("id");
		switch (id) {
		  case "freeBoards" :
			  location.href="<%=ctxPath%>/boards?category=community&category=freeBoards";
			  break;
		  case "questions" :
			  location.href="<%=ctxPath%>/boards?category=community&category=questions";
			  break;
		  case "studies" :
			  location.href="<%=ctxPath%>/boards?category=community&category=studies";
			  break;
		  case "hobbies" :
			  location.href="<%=ctxPath%>/boards?category=community&category=hobbies";
			  break;
		  case "reviews" :
			  location.href="<%=ctxPath%>/boards?category=community&category=reviews";
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
