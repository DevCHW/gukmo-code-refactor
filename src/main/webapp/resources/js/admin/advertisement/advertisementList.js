// js파일에서 contextPath를 알아내는 함수
function getContextPath(){
  let hostIndex = location.href.indexOf(location.host) + location.host.length;
  let contextPath = location.href.substring(hostIndex, location.href.indexOf('/',hostIndex+1));
  return contextPath;
}
//광고내역 js파일입니다.
const today = new Date();

const year = today.getFullYear(); // 년도
let month = today.getMonth() + 1;  // 월
let day = today.getDate();  // 날짜

if(month < 10) month = "0"+month;
if(day < 10) day = "0"+day;
const sysdate = year + '-' + month + '-' + day;

//필터버튼 클릭횟수
let btn_filter_click_cnt = 0;
let data_ok = false;

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

//excel 다운로드시에 전체가 client에서 보여주는 값 이외의 값들도 다운로드 되도록 하는 소스
var oldExportAction = function (self, e, dt, button, config) {
	if (button[0].className.indexOf('buttons-excel') >= 0) {
		if ($.fn.dataTable.ext.buttons.excelHtml5.available(dt, config)) {
			$.fn.dataTable.ext.buttons.excelHtml5.action.call(self, e, dt, button, config);
		}
		else {
			$.fn.dataTable.ext.buttons.excelFlash.action.call(self, e, dt, button, config);
		}
	} else if (button[0].className.indexOf('buttons-print') >= 0) {
		$.fn.dataTable.ext.buttons.print.action(e, dt, button, config);
	}
};

var newExportAction = function (e, dt, button, config) {
	var self = this;
	var oldStart = dt.settings()[0]._iDisplayStart;

	dt.one('preXhr', function (e, s, data) {
		// Just this once, load all data from the server...
		data.start = 0;
		data.length = 2147483647;

		dt.one('preDraw', function (e, settings) {
			// Call the original action function
			oldExportAction(self, e, dt, button, config);

			dt.one('preXhr', function (e, s, data) {
				// DataTables thinks the first item displayed is index 0, but we're not drawing that.
				// Set the property to what it was before exporting.
				settings._iDisplayStart = oldStart;
				data.start = oldStart;
			});

			// Reload the grid with the original page. Otherwise,
			// API functions like table.cell(this) don't work properly.
			setTimeout(dt.ajax.reload, 0);

			// Prevent rendering of the full data to the DOM
			return false;
		});
	});

	// Requery the server with the new one-time export settings
	dt.ajax.reload();
};

var stack_1_list_dt = null;

//Call the dataTables jQuery plugin
$(document).ready(function() {
  $("#end_date").val(sysdate);

  //Selectpicker 적용
  $('.selectpicker').selectpicker();

  //Datepicker 적용
  $("#start_date, #end_date").datepicker({
    dateFormat: 'yy-mm-dd'
  });


  $('#dataTable').DataTable({
	"serverSide": true,
	"order": [[0, 'desc']],
    "processing": true,
    "ajax": {
        "url": "/api/v1/admin/advertisements",
        "type": "POST",
        "dataSrc": function(res) {
            let data = res.data;
            return data;
        },
    },
    "columns" : [
    	{"data": "id"},
        {"data": "type"},
        {"data": "startDate"},
        {"data": "endDate"}
    ],
    dom: 'Bfrtip',
    buttons: [
		{
			extend: 'excel'
			,text: "<img src='../../resources/images/dataTable/excel.png' style='width:25px; height:17px;'/>Excel&nbsp;&nbsp;"
			,filename: '국비의모든것 광고내역'+year+month+day
			,title: '국비의모든것 광고내역'+year+month+day
			,action: newExportAction
		},
		{
			extend: 'copy'
			,text: '📋&nbsp;Copy&nbsp;'
			,title: '국비의모든것 광고내역'+year+month+day
		},
		{
			extend: 'pdf'
			,text: "<img src='https://toppng.com/public/uploads/preview/pdf-icon-11549528510ilxx4eex38.png' style='width:25px; height:20px;'/>&nbsp;PDF&nbsp;"
			,filename: '국비의모든것 광고내역'+year+month+day
		},
		{
			extend: 'csv'
			,text: "<img src='../../resources/images/dataTable/csv.png' style='width:20px; height:20px;'/>&nbsp;CSV&nbsp;'"
			,filename: '국비의모든것 광고내역'+year+month+day
		},
		{
			extend: 'print'
			,text: '️🖨&nbsp;Print&nbsp;'
			,filename: '국비의모든것 광고내역'+year+month+day
		},
	]
  });//end of Event---
  const table = $('#dataTable').DataTable();

  //Export 버튼들 꾸미기
  $("button.buttons-excel").addClass("btn btn-light border mr-2");
  $("button.buttons-copy").addClass("btn btn-light border mr-2");
  $("button.buttons-csv").addClass("btn btn-light border mr-2");
  $("button.buttons-pdf").addClass("btn btn-light border mr-2");
  $("button.buttons-print").addClass("btn btn-light border");



  //기존 dataTable 검색바 숨기기
  $("#dataTable_filter").attr("hidden", "hidden");

  //검색조건 변경시 이벤트
  $('#searchType').change(function (e) {
//	  const target = $(e.currentTarget);
//      let searchTypeVal = target.val();
  });//end of Event--


  //검색버튼을 눌렀을 때 이벤트
  $("#btn_search").click(function () {
      let numCols = table.columns().nodes().length;
      for(let i=0; i<numCols; i++) { table.column(i).search(''); }

      let searchType = $("#searchType").val();
      let searchWord = $("#searchWord").val();

      table.column(searchType).search(searchWord);

      if($("#start_date").val() != ''){
    	  searchWord = $("#start_date").val() + "," +$("#end_date").val();
    	  table.column(2).search(searchWord);
      }
      if($("#division").val() != '구분선택'){
    	  searchWord = $("#division").val();
    	  table.column(1).search(searchWord);
      }
      table.draw();
  });//end of Event--


  //필터버튼 클릭시
  $("#btn_filter").click(function(){
	  btn_filter_click_cnt++;
	  if(btn_filter_click_cnt%2==0){	//짝수번 클릭시 숨기기
		  $("#filter_area").css("display","none");
		  $('#division').selectpicker('hide');
		  $("#division").val('구분선택');
		  $("#start_date").val('');
		  $("#end_date").val(sysdate);
	  }else{							//홀수번 클릭시 보이기
		  $("#filter_area").css("display","flex");
		  $('#status').selectpicker('show');
		  $('#division').selectpicker('show');
	  }
  });//end of Event--


   //날짜 변경시 검색
   $('#start_date').bind('change', function(){
        if($("#start_date").val() ==''){
   	    	return;
   	    } else {
            if(test_date()){	//날짜유효성검사 통과시
                let numCols = table.columns().nodes().length;
                for(let i=0; i<numCols; i++) { table.column(i).search(''); }

                let searchType = $("#searchType").val();

                let searchWord = $("#searchWord").val();
                table.column(searchType).search(searchWord);

                if($("#division").val() != '구분선택'){
                  searchWord = $("#division").val();
                  table.column(1).search(searchWord);
                }

                table.column(2).search($("#start_date").val());
                table.column(3).search($("#end_date").val());
                table.draw();
            }else{
                $("#start_date").val('');
                $("#end_date").val(sysdate);
            }
        }
   });

   $('#end_date').bind('change', function(){
        if($("#start_date").val() ==''){
            return;
        } else{
            if(test_date()){	//날짜유효성검사 통과시
                let numCols = table.columns().nodes().length;
                for(let i=0; i<numCols; i++) { table.column(i).search(''); }

                let searchType = $("#searchType").val();

                let searchWord = $("#searchWord").val();
                table.column(searchType).search(searchWord);

                if($("#division").val() != '구분선택'){
                  searchWord = $("#division").val();
                  table.column(1).search(searchWord);
                }

                table.column(2).search($("#start_date").val());
                table.column(3).search($("#end_date").val());

                table.draw();
            }else{
                $("#start_date").val('');
                $("#end_date").val(sysdate);
            }
        }
   });

   $('#start_date, #end_date').bind('change',function(){
   });//end of Event--


   //division 변경시 검색
   $("#division").change(function(){
	   let status = $("#status").val();
	   let numCols = table.columns().nodes().length;
	   //초기화
	   for(let i=0; i<numCols; i++) { table.column(i).search(''); }

	   //날짜넣기
	   if($("#start_date").val() !=''){
		   let searchWord = $("#start_date").val() + "," +$("#end_date").val();
		   table.column(5).search(searchWord);
	   }

	   //검색조건
	   let searchType = $("#searchType").val();
	   let searchWord = $("#searchWord").val();
	   table.column(searchType).search(searchWord);

	   //구분체크
	   if($("#division").val() != '구분선택'){
    	  searchWord = $("#division").val();
    	  table.column(1).search(searchWord);
       }
       table.draw();
   });//end of Event


   //검색창에서 엔터 입력시 검색되게하기
	$("input#searchWord").keydown(function(e){	//검색창에서 엔터 입력시
	  if(e.keyCode == 13){	//엔터를 했을 경우
		$("#btn_search").trigger("click");
	  }
	});//end of Event--


  //tr 클릭시 링크 걸기
  $(document).on('click', '#dataTable > tbody > tr' , function(e){
	//아이디가 id 인거를 'click', 클릭할때마다 이벤트가 일어난다.
	const target = $(e.currentTarget);
	const id = target.children(":first").text();
	location.href = "/admin/advertisements/"+id;
  });
});//end of $(document).ready(function() {})--





// == Function Declaration == //
/**
 * 날짜 유효성검사
 */
function test_date(){

  let start_date = $("input#start_date").val();
  let end_date = $("input#end_date").val();

  start_date = start_date.replace(/-/g,'');
  end_date = end_date.replace(/-/g,'');

  const dateGap = end_date - start_date;


  if(!isNaN(dateGap)){  //정상적인 날짜 입력시
    if(dateGap > 0){  //통과
      return true;
    } else if(dateGap == 0){
      alert("검색 시작일자와 끝 일자는 같을 수 없습니다.")
      return false;
    } else{
      alert("검색 끝 일자는 시작일자보다 나중이어야 합니다.");
      return false;
    }
  } else{
    alert("Error! 날짜가 아닌 값을 선택하셨습니다!");
    return false;
  }
}//end of method--
