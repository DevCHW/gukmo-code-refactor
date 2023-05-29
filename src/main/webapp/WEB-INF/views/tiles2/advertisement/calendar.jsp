<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String ctxPath = request.getContextPath();
%>

<script src='<%=ctxPath %>/resources/fullcalendar-6.0.0/dist/index.global.js'></script>
<script type="text/javascript">

$(document).ready(function(event){

	$(document).on("click",".fc-event-title-container",function() {
		var bool = confirm("해당 광고 상세보기 페이지로 이동하시겠습니까?");
		if(bool){
		} else {
			return false;
		}
	});


	var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
      headerToolbar: {
        left: 'prev,next today',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek,timeGridDay'
      },
      locale:"ko",
      navLinks: true, // can click day/week names to navigate views
      editable: true,
      selectable: true,
  	  events:function(info, successCallback, failureCallback) {
	   	  $.ajax({
	         url: "/api/v1/admin/advertisements",
 			 type: "get",
	         dataType:"json",
	         success:function(res) {
	        	 var events = [];
	        	 if(res.result.length > 0){
	        		 $.each(res.result, function(index, advertisement) {
          			   events.push({
                              title: advertisement.id,
                              start: advertisement.startDate,
                              end: advertisement.endDate,
                              url: '/admin/advertisements/'+advertisement.id
                 		});
	        		 });
	        	 }
	        	successCallback(events);
			 },// end of success
			 error: function(request,error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			 }

		  });// end of $.ajax({})---

  	    } //end of function function(info, successCallback, failureCallback)

    });
    calendar.render();
});
</script>


<%-- Main Content --%>
<div id="content">
  <%-- Begin Page Content --%>
  <div class="container-fluid">
  	  <%-- Page Heading --%>
      <h1 class="h3 my-2 text-gray-800">광고일정</h1>

      <div class="card shadow mb-4">
     	<div class="card-body">

     		<div id='calendar'>
     			<div id="btn_add_schedule">
     				<button type="button" class="btn add_sche">일정 추가</button>
     			</div>
     		</div>
		</div>
	  </div>
  </div>
</div>
</html>
