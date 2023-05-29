// js파일에서 contextPath를 알아내는 함수
function getContextPath(){
  let hostIndex = location.href.indexOf(location.host) + location.host.length;
  let contextPath = location.href.substring(hostIndex, location.href.indexOf('/',hostIndex+1));
  return contextPath;
}


/**
 * js 파일은 전부 필드선언 - 이벤트(document.ready) - 함수선언 의 구성으로 이루어져 있음
 */



$.datepicker.setDefaults({
  dateFormat: 'yy-mm',
  prevText: '이전 달',
  nextText: '다음 달',
  monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
  monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
  dayNames: ['일', '월', '화', '수', '목', '금', '토'],
  dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
  dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
  showMonthAfterYear: true,
  yearSuffix: '년'
});


// == Field Declaration == //
let date_ok = false;
let date_change_cnt = 0;

// == Event Declaration == //
$(document).ready(function(){
  //Datepicker 적용
  $("#start_date, #end_date").datepicker({
    dateFormat: 'yy-mm-dd'
  });

  //수정 버튼 클릭시 이벤트
  $("button#btn_edit_advertisement").click(function(e){
    const target = $(e.currentTarget);
    const btnText = target.text();

    if(btnText == '완료'){  //완료 버튼을 누를시
      if(date_ok || date_change_cnt == 0){
        editAdvertisement(); //광고정보수정메소드
        target.text("수정");
        target.next().css("display","none");
        $("input#advertisementFile").css("display","none");
        $("input#advertisementFile").prev().css("display","inline");
        $("div.edit_select_area").css("display","none");
        $("div.edit_select_area").prev().css("display","block");
        $("div#datepicker_area").css("display","none");
        $("div#datepicker_area").prev().css("display","block");
      } else{
        alert("날짜를 제대로 입력해주세요!");
      }

    } else{ //수정 버튼을 누를시
      target.text("완료");

      target.next().css("display","block");
      $("input#advertisementFile").css("display","block");
      $("input#advertisementFile").prev().css("display","none");
      $("div.edit_select_area").css("display","block");
      $("div.edit_select_area").prev().css("display","none");
      $("div#datepicker_area").css("display","flex");
      $("div#datepicker_area").prev().css("display","none");
    }
  });//end of Event--


  //수정하기 취소버튼 클릭시
  $("button#btn_edit_close").click(function(){
    $("div.edit_select_area").css("display","none");
    $("div.edit_select_area").prev().css("display","block");
    $("button#btn_edit_close").css("display","none");
    $("button#btn_edit_advertisement").text("수정");
    $("div#datepicker_area").css("display","none");
    $("div#datepicker_area").prev().css("display","block");
  });//end of Event--


  let input_start_date = "";
  let input_end_date = "";
  $("input#start_date").click(function(){
	 input_start_date = $("input#start_date").val();
  });
  $("input#end_date").click(function(){
	 input_end_date = $("input#end_date").val();
  });
  //날짜 변경시 유효성검사하기
  $("input#start_date").change(function(){
    date_change_cnt++;
    date_ok = false;
    let dateGap = test_date();
    if(!isNaN(dateGap)){  //정상적인 날짜 입력시
      if(dateGap > 0){  //통과
        date_ok = true;
      } else if(dateGap == 0){
        alert("광고 시작일은 종료일과 같을 수 없습니다.");
        $("input#start_date").val(input_start_date);
        date_ok = false;
      } else{
        alert("광고 종료일자는 시작일자보다 나중이어야 합니다.");
        $("input#start_date").val(input_start_date);
        date_ok = false;
      }
    } else{
      alert("Error! 날짜가 아닌 값을 선택하셨습니다!");
      $("input#start_date").val(input_start_date);
      date_ok = false;
    }
  });//end of Event--

  //날짜 변경시 유효성검사하기
  $("input#end_date").change(function(){
    date_change_cnt++;
    date_ok = false;
    let dateGap = test_date();
    if(!isNaN(dateGap)){  //정상적인 날짜 입력시
      if(dateGap > 0){  //통과
        date_ok = true;
      } else if(dateGap == 0){
        alert("광고 시작일은 종료일과 같을 수 없습니다.");
        $("input#end_date").val(input_end_date);
        date_ok = false;
      } else{
        alert("광고 종료일자는 시작일자보다 나중이어야 합니다.");
        $("input#end_date").val(input_end_date);
        date_ok = false;
      }
    } else{
      alert("Error! 날짜가 아닌 값을 선택하셨습니다!");
      $("input#end_date").val(input_end_date);
      date_ok = false;
    }
  });//end of Event--



  //삭제 버튼 클릭시
  $("button#btn_delete").click(function(){
	 let bool = confirm("해당광고를 삭제하시겠습니까?");
	 if(bool){
		 deleteAd();
	 }
  });//end of Event--


});//end of $(document).ready(function(){})--



// == Function Declaration == //

/**
 * 광고 정보 수정 완료시 수정해주기
 * @param {*} advertisement_num(광고번호)
 */
function editAdvertisement(){
  const id = $("input#hidden_advertisementId").val();
  var queryString = $("form[name=editAdvertisementFrm]").serialize();

  $("form[name=editAdvertisementFrm]").ajaxForm({
    url : "/api/v1/admin/advertisements/"+ id,
    data:queryString,
    type:"patch",
    dataType:"JSON",
    success:function(res) {
      if(res.success){ //광고정보수정에 성공했다면
        alert("수정되었습니다!");
		window.location.reload();
      } else { //광고정보수정에 실패했다면
        alert("수정에 실패하였습니다. 다시 시도해주세요");
      }
    },
    error: function(request, status, error){
      alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
    }
  });//end of ajax--
  $("form[name=editAdvertisementFrm]").submit();
}//end of method--


/**
 * 날짜 유효성검사
 */
function test_date(){
  let start_date = $("input#start_date").val();
  let end_date = $("input#end_date").val();

  start_date = start_date.replace(/-/g,'');
  end_date = end_date.replace(/-/g,'');

  return end_date - start_date;
}//end of method--

/**
 * 광고삭제하기
 */
function deleteAd(){
	const id = $("input#hidden_advertisementId").val();
	$.ajax({
        url: '/api/v1/admin/advertisements/' + id,
        type: 'delete',
        dataType : 'json',
        success: function(res) {
        	if(res.success){
        		alert("삭제되었습니다!");
        		location.href = "/admin/advertisements";
        	}else{
        		alert("광고를 삭제하는데 실패하였습니다. 다시 시도해주세요");
        	}
        },
    	error: function(request,status,error){
    		alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
    	}
    });//end of $.ajax({})
}//end of method--