$(document).ready(function(){
	///////////// 알림 토글 시작 /////////////////
	$('.alarm_drop').click(function(e){
	    e.stopPropagation();
	     $("#alarm_dropContent").toggle();
	     $("#profile_dropContent").hide();
	     $("#admin_dropContent").hide();
	});

	$("#alarm_dropContent").on("click", function (event) {
	    event.stopPropagation();
	});     
	///////////// 알림 토글 끝 /////////////////

	// 각 알림을 읽었을 경우
});


