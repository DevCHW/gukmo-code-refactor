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
      case "국비학원" :	//보고있는 페이지가 "자유게시판"일 경우 .active 추가
          $("div#academies").addClass("active");
          break;
      case "교육과정" : //보고있는 페이지가 "Q&A"일 경우 .active 추가
          $("div#curricula").addClass("active");
          break;
      default :
          $("div#academies").addClass("active");
          break;
    }//end of switch-case---

	$("div.detail-category").click(function(e){
		const target = $(e.currentTarget);
		const id = target.attr("id");
		switch (id) {
		  case "academies" :
			  location.href="<%=ctxPath%>/boards?firstCategory=국비학원";
			  break;
		  case "curricula" :
			  location.href="<%=ctxPath%>/boards?firstCategory=국비학원&secondCategory=교육과정";
			  break;
		}
	});//end of Event---
  });//end of $(document).ready(function(){})

</script>


<div id="category" class="d-flex justify-content-center align-content-center">
  <div id="academies" class="detail-category mx-2 px-2 py-1 rounded">국비학원</div>
  <div id="curricula" class="detail-category mx-2 px-2 py-1 rounded">교육과정</div>
</div>
