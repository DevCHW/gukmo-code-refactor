// js파일에서 contextPath를 알아내는 함수
function getContextPath(){
  let hostIndex = location.href.indexOf(location.host) + location.host.length;
  let contextPath = location.href.substring(hostIndex, location.href.indexOf('/',hostIndex+1));
  return contextPath;
}

// == Field Declaration == //
let btn_insert_penalty_modal_click = 0;


// == Event Declaration == //
$(document).ready(function(){


	// === 전체 datepicker 옵션 일괄 설정하기 ===
	 //     한번의 설정으로 $("input#fromDate"), $('input#toDate')의 옵션을 모두 설정할 수 있다.
	$.datepicker.setDefaults({
       dateFormat: 'yy-mm-dd'  // Input Display Format 변경
      ,showOtherMonths: true   // 빈 공간에 현재월의 앞뒤월의 날짜를 표시
      ,showMonthAfterYear:true // 년도 먼저 나오고, 뒤에 월 표시
      ,changeYear: true        // 콤보박스에서 년 선택 가능
      ,changeMonth: true       // 콤보박스에서 월 선택 가능
      ,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
      ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
      ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
      ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
  });

	// input 을 datepicker로 선언
  $("input#fromDate").datepicker();
  $("input#toDate").datepicker();
  $("input#fromDate2").datepicker();
  $("input#toDate2").datepicker();


    //From의 초기값을 오늘 날짜로 설정
	$('input#fromDate').datepicker('setDate', '-1M'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, +1M:한달후, +1Y:일년후)
	$('input#fromDate2').datepicker('setDate', '-1M'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, +1M:한달후, +1Y:일년후)


    //To의 초기값을 3일후로 설정
    $('input#toDate').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, +1M:한달후, +1Y:일년후)
    $('input#toDate2').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, +1M:한달후, +1Y:일년후)

    // datepicker 날짜 범위 제한
    $('input#fromDate, input#toDate, input#fromDate2, input#toDate2').datepicker("option",'minDate', '-2Y');
		 $('input#fromDate, input#toDate, input#fromDate2, input#toDate2').datepicker("option",'maxDate', 'today');

		 $('input#toDate, input#toDate2').datepicker("option",'onClose', function (selectedDate) {
			if(selectedDate.length==10)
         $("#fromDate, input#fromDate2").datepicker("option", "maxDate", selectedDate);
     	else
     		$("#fromDate, input#fromDate2").datepicker("option", "maxDate", max);
     });
    $('#fromDate, input#fromDate2').datepicker("option", "onClose", function (selectedDate) {
    	if(selectedDate.length==10)
            $("#toDate, input#toDate2").datepicker("option", "minDate", selectedDate);
        else
            $("#toDate, input#toDate2").datepicker("option", "minDate", min);
    });





  //네비게이션 바 클릭시 해당 메뉴 글자 색상 변경하기
  $("div#member_navbar > div").click(function(e){
    const target = $(e.currentTarget);
    const menu_name = target.text();

    //기존 것 다 지우기
    $("div#member_navbar > div").css("border-bottom","");
    $("div#member_navbar > div").css("font-size","");
    $("div#member_navbar > div").css("font-weight","");
    $("div#member_navbar > div").css("color","");

    target.css("border-bottom","solid 3px #208EC9");
    target.css("font-size","14px");
    target.css("font-weight","bold");
    target.css("color","#208EC9");

    //다른영역 전부 display:none 시키는 함수 호출
    memberDetailAreaClear();
    const id = $("input#hidden_member_id").val();
    switch (menu_name) {
      case '작성게시물':  //작성게시물 메뉴 클릭시
        write_board_list_nav(id);
      break;
      case '신고내역':  //신고내역 메뉴 클릭시
        report_nav(id);
      break;
    }//end of switch-case

  });//end of Event--

  //기본값 작성게시물 네비바 클릭시키기
  $("div#write_board_list_nav").trigger("click");

  //정지사유등록 버튼 클릭시 클릭횟수 증가
  $("span#btn_insert_penalty_modal").click(()=>{btn_insert_penalty_modal_click++;})



  //회원정보수정을 정지로 선택하였으면 정지사유등록 모달버튼 보여주기
  $("select#select_status").change(function(){
    const editStatusVal = $("select#select_status").val();

    if(editStatusVal == 'SUSPENDED'){
      let bool = confirm("해당 회원을 정지시키겠습니까?");
      if(bool) {
        alert("정지사유를 작성해주세요");
        $("span#btn_insert_penalty_modal").show();
      }
    } else {
    	$("span#btn_insert_penalty_modal").hide();
    }
  });//end of Event--

  //회원정보수정을 정지로 선택하였으면 정지사유등록 모달버튼 보여주기
    $("select#select_authority").change(function(){
      const editUserRoleVal = $("select#select_authority").val();
      const originUserRole = $("input#hidden_member_userRole").val();

      if(editUserRoleVal == 'ACADEMY'&&
         originUserRole != 'ACADEMY'){
         alert("교육기관회원으로는 변경할 수 없습니다.");
         $("select#select_authority").val(originUserRole);
      }
    });//end of Event--



  //회원정보수정 버튼 클릭시
  $("button#btn_editInfo").click(function(e){
    const target = $(e.currentTarget);
    const btnText = target.text();
    if(btnText == '수정'){  //수정버튼을 눌렀다면
      btn_insert_penalty_modal_click = 0;
      $("select#select_status").show();
      $("select#select_status").next().hide();
      $("select#select_authority").show();
      $("select#select_authority").next().hide();
      $("button#btn_editClose").show();
      $("span#btn_insert_penalty_modal").hide();
  	  $("span#btn_insert_refuse_modal").hide();
      target.text("완료");

    } else if (btnText == '완료'){  //완료버튼을 눌렀다면
      const editStatusVal = $("select#select_status").val();
      if(editStatusVal == 'SUSPENDED') {  //회원 상태를 정지로 변경하고자 했다면
    	if($("input#hidden_member_status").val() != 'SUSPENDED'){	//기존에 정지인 회원이 아니라면
    	  if(btn_insert_penalty_modal_click == 0){	//정지사유 등록 클릭을 한번도 하지 않았다면
    	    alert("정지사유를 등록해주세요.");
    	    return;
    	  } else {  //정지 사유도 등록하고 정지회원 등록하려 했다면,
    	    const status = $("select#select_status").val();
            const authority = $("select#select_authority").val();
            const id = $("input#hidden_member_id").val();
            const data = {"userRole" : authority, "status" : editStatusVal};
            penaltyRegister();
            editMemberInfo(data, id);
            return;
    	  }
    	}
      }
      const status = $("select#select_status").val();
      const authority = $("select#select_authority").val();
      const id = $("input#hidden_member_id").val();
      const data = {"userRole" : authority, "status" : editStatusVal};
      editMemberInfo(data, id);
    }//end of else--
  });//end of Event--




  //회원정보수정 취소 버튼 클릭시
  $("button#btn_editClose").click(function(){
    $("button#btn_editInfo").text("수정");
    $("select#select_status").hide();
    $("select#select_status").next().show();
    $("select#select_authority").hide();
    $("select#select_authority").next().show();
    $("button#btn_editClose").hide();
    $("span#btn_insert_penalty_modal").hide();
	$("span#btn_insert_refuse_modal").hide();
  });//end of Event--



  //정지등록 폼에서 기타사유 선택시 상세사유작성 textarea 보여주기
  $("select#simple_penalty_reason").change(function(){
    const simplePenaltyReasonVal = $("select#simple_penalty_reason").val();

    if(simplePenaltyReasonVal == '기타사유'){ //기타사유를 선택했다면
      $("div#detail_penalty_reason_area").css("display","flex");
      $("textarea[name='detail_penalty_reason']").focus();
    } else{
      $("div#detail_penalty_reason_area").css("display","none");
    }
  });//end of Event---


  //정렬버튼 클릭시


  //정렬옵션 클릭시 이벤트
  $("select#sort").change(e=>{
    const target = $(e.currentTarget);
    const sort = target.val();

    console.log(sort);

    activitiesChart_nav(sessionStorage.getItem("userid"));
  });


  $("button#btn_activityChart").click(function (){
	  activitiesChart_nav(sessionStorage.getItem("userid"));
  });


  $("button#btn_loginChart").click(function(){
	  login_record_nav(sessionStorage.getItem("userid"));
  });

});//end of $(document).ready(function(){})-








// == Function Declaration == //
/**
 * 회원에게 이메일전송하기
 */
function sendEmail(email){
    const message = $("textarea#email_message").val();
	$.ajax({
	  url:"/api/v1/admin/email/" + email,
	  data:{"message":message},
	  type:"post",
	  dataType:"json",
	  success:function(res){
	    if(res.result.sendMailSuccess){	//이메일 전송에 성공했다면
          $("button.sendEmailModal_close").trigger("click");  //닫기버튼 클릭
        }
        alert(res.result.message);
	  },//end of success
	  //success 대신 error가 발생하면 실행될 코드
	  error: function(request,status,error){
	  	alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	  }
    });//end of $.ajax({})---
}//end of method---

/**
 * 회원 정보 수정해주기
 */
function editMemberInfo(data, id){
  $.ajax({
    url:"/api/v1/admin/members/" + id,
    data : data,
    type:"patch",
    dataType:"json",
    success:function(res){
      if(res.success){	//회원정보 수정에 성공했다면
        alert("회원정보 수정 성공!");
        location.reload();
      } else {	//회원정보 수정에 실패했다면
        alert("회원정보 수정 실패!");
      }
    },//end of success
    //success 대신 error가 발생하면 실행될 코드
    error: function(request,status,error){
      alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
    }
  });//end of $.ajax({})---
}//end of method--


/**
 * 정지내역등록하기
 */
function penaltyRegister(){
  let queryString = $("form[name=penaltyRegisterFrm]").serialize();
  $("form[name=penaltyRegisterFrm]").ajaxForm({
    url : "/api/v1/admin/penalty",
    data:queryString,
    type:"POST",
    dataType:"JSON",
    success:function(res) {
      if(!res.success){ //정지내역 등록에 성공했다면
        alert("회원수정에 실패하였습니다. 다시 시도해주세요");
      }
    },
    error: function(request, status, error){
      alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
    }
  });//end of ajax--
  $("form[name=penaltyRegisterFrm]").submit();

}//end of method---



/**
 * 회원 상세정보 네비게이션 바 클릭시 다 지우기
 */
function memberDetailAreaClear(){
  $("div.detail_info_area").css("display","none");
}//end of method--


/**
 * 네비게이션 바에서 작성게시물 클릭시 실행될 함수
 */
function write_board_list_nav(id){
  $("div#member_write_board_list").css("display","block");
  let table = $("#dataTable-write-board").DataTable();
  table.destroy();
  $('#dataTable-write-board').DataTable({
	"serverSide": true,
	"order" : [[ 0, "desc" ]],
	"paging":true,
	"pageLength": 10,
    "processing": true,
    "searching": false,
    "ajax": {
        "url": "/api/v1/admin/boards/members/" + id,
        "type": "post",
        "dataSrc": function(res) {
            let data = res.data;
            return data;
        },
    },
    "columns" : [
        {"data": "id"},
        {"data": "firstCategory"},
        {"data": "secondCategory"},
        {"data": "subject"},
        {"data": "writeDate"},
    ],
  });//end of Event----
}//end of method--


/**
 * 네비게이션 바에서 신고내역 클릭시 실행될 함수
 */
function report_nav(id){
  $("div#member_report_list").css("display","block");

  let table = $("#dataTable-report").DataTable();
  table.destroy();
  $('#dataTable-report').DataTable({
	"serverSide": true,
	"order" : [[ 0, "desc" ]],
	"paging":true,
	"pageLength": 10,
    "processing": true,
    "searching": false,
    "ajax": {
        "url": "/api/v1/admin/reports/members/" + id,
        "type": "POST",
        "dataSrc": function(res) {
            let data = res.data;
            return data;
        },
    },
    "columns" : [
        {"data": "reportType"},
        {"data": "simpleReason"},
        {"data": "reportDate"},
        {"data": "boardId"},
        {"data": "commentsId"},
    ],
  });//end of Event----



  table = $("#dataTable-reported").DataTable();
  table.destroy();
  $('#dataTable-reported').DataTable({
	"serverSide": true,
	"aaSorting": [],
	"order" : [[ 0, "desc" ]],
	"paging":true,
    "processing": true,
    "searching": false,
    "ajax": {
        "url": getContextPath()+"/admin/member/getReportedData.do",
        "type": "POST",
        "data":{nickname:nickname},
        "dataSrc": function(res) {
            let data = res.data;
            return data;
        },
    },
    "columns" : [
        {"data": "REPORT_TYPE"},
        {"data": "REPORTED_NICKNAME"},
        {"data": "SIMPLE_REPORT_REASON"},
        {"data": "REPORT_DATE"},
        {"data": "RECEIPT"},
    ],
  });//end of Event----
}//end of method--

