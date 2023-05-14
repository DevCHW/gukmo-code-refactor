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
    $("div#notices").addClass("active");

	$("div.detail-category").click(function(e){
		const target = $(e.currentTarget);
		const id = target.attr("id");
		switch (id) {
		  case "notices" :
			  location.href="<%=ctxPath%>/boards?firstCategory=공지사항";
			  break;
		}
	});//end of Event---
  });//end of $(document).ready(function(){})

</script>


<div id="category" class="d-flex justify-content-center align-content-center">
  <div id="notices" class="detail-category mx-2 px-2 py-1 rounded">공지사항</div>
</div>