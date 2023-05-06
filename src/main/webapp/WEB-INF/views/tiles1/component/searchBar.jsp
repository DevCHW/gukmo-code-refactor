<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String ctxPath = request.getContextPath();
%>

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%=ctxPath %>/resources/css/component/searchBar.css" />

<%-- 직접만든 javascript --%>
<script type="text/javascript">
  $(document).ready(function(){
	  let sort= "${requestScope.sort}";


	  //검색버튼 클릭이벤트
	  $("button#btn_search").click(function(){
		const keyword = $("input#searchWord").val();
		//saveKeyword(keyword);

		const firstCategory = "${boardRequest.firstCategory}";
		const secondCategory = "${boardRequest.secondCategory}";
		const sort = "${boardRequest.sort}";
		location.href="?firstCategory=" + firstCategory + "&secondCategory=" + secondCategory +"&keyword=" + keyword +"&sort=" + sort;
	  });

	  //검색창에서 엔터눌렀을 시
	  $("input#searchWord").keydown(function(e){
	    if(e.keyCode == 13){	//엔터를 했을 경우
		  $("button#btn_search").trigger("click");
	    }
	  });

  });//end of $(document).ready(function(){})--

  //Function Declaration
  /**
   * 검색하면 검색어 결과 저장하기.
   */

  /** 일단 주석
  function saveKeyword(keyword){
	  const data = {keyword:keyword,
			  		userid : '${sessionScope.user.userid}',
			  		detail_category : '${requestScope.detail_category}'}
	  $.ajax({
		type : 'GET',
		url : '<%=ctxPath%>/saveKeyword.do',
		data : data,
		dataType : 'json',
		async : false,
		success : function(data){
		},
		error: function(xhr, status, error){
			alert("로그인에 실패했습니다."+error);
		}
	  });//end of ajax
  }
   */
</script>

    <!-- 검색창 영역 -->
   <div class="searchBar d-flex my-3 justify-content-center">
     <input type="text" id="searchWord" class="pl-3" value="${boardRequest.keyword}" placeholder="검색어를 입력해주세요"></input>
     <button type="button" id="btn_search">
       <i class="fa-solid fa-magnifying-glass" style="color:#208EC9;"></i>
     </button>
   </div>