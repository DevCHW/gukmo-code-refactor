let recaptcha_ok = false;
let obj = [];
let all_ok = false;

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

$(document).ready(function(){
	//Datepicker 적용
	  $("#curriculum_start_date, #curriculum_end_date, #recruitment_start_date, #recruitment_end_date").datepicker({
	    dateFormat: 'yy-mm-dd'
	  });

	$(document).on("click", "div#small_location2 span", function(e){
	  const target = $(e.currentTarget);
	  const small_location = target.text();
	  $("div#location3").html(`<i class="fa-solid fa-location-dot mr-2"></i>
		   					  <span id="address2">${small_location}</span>`);
	});

    // select 값 잡기
    const urlParams = new URL(location.href).searchParams;
    const detail_category = urlParams.get('detailC');
    $("#detail_category").val(detail_category).attr("selected","selected");

	//	==== 스마트 에디터 구현 시작 ==== //

    //스마트에디터 프레임생성
    nhn.husky.EZCreator.createInIFrame({
        oAppRef: obj,
        elPlaceHolder: "content",
        sSkinURI: "/resources/smartEditor/SmartEditor2Skin.html",
        htParams : {
            // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
            bUseToolbar : true,
            // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
            bUseVerticalResizer : true,
            // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
            bUseModeChanger : true,
        }
    });
    // ==== 스마트 에디터 구현 끝 ==== //


    // ==== 해시태그 구현 시작 ==== //
    let hashtag = {};
    let counter = 0;

    // 태그를 추가한다.
    function addTag(value) {
      hashtag[counter] = value; // 태그를 Object 안에 추가
      counter++; // counter 증가 삭제를 위한 del-btn 의 고유 id 가 된다.
    }

    // 최종적으로 서버에 넘길때 tag 안에 있는 값을 array type 으로 만들어서 넘긴다.
    function marginTag() {
      return Object.values(hashtag).filter(function (word) {
          return word !== "";
        });
    }

    //해시태그 백스페이스로 지워지게 하기
    $("input#hashtag").on("keydown",function(e){
        if(e.keyCode == 8 && $("input#hashtag").val() == ""){	//백스페이스를 눌렀을 때,인풋태그 값이 채워져있지 않다면 해시태그 지워주기
          if($("li.tag-item").text() != ""){	//써놓은 해시태그가 있다면
              let index = $("input#hashtag").prev().children("span.btn_hashtag_delete").attr("idx");
                hashtag[index] = "";
              $("input#hashtag").prev().remove();
              return;
          }
         }
    });


    $("#hashtag").on("keyup", function (e) {
      var self = $(this);

      // input 에 focus 되있을 때 엔터 및 스페이스바 입력시 구동
      if (e.key === "Enter" || e.keyCode == 32) {

        var tagValue = self.val().trim(); // 값 가져오기

        //해시태그는 5개 이상 추가 불가능 합니다.
        if($("li.tag-item").length == 5){
          alert("태그는 5개 이상 추가 불가합니다.");
          //해시태그 쓴 값 비우기
          $("input#hashtag").val("");
          return;
        }

        // 값이 없으면 동작 안합니다.
        if (tagValue !== "") {

          // 같은 태그가 있는지 검사한다. 있다면 해당값이 array 로 return 된다.
          var result = Object.values(hashtag).filter(function (word) {
              return word === tagValue;
            })

         // 해시태그가 중복되었는지 확인
        if (result.length == 0) {
                $("<li class='d-flex align-items-center flex-nowrap mr-2 tag-item'>#"+tagValue+"<span class='btn_hashtag_delete mx-2' style='cursor:pointer; color:darkgray;' idx='"+counter+"'><i class='fa-solid fa-xmark'></i></span></li>").insertBefore("input#hashtag");
                addTag(tagValue);
                self.val("");
            } else {
              alert("태그값이 중복됩니다.");
            }
          }
        e.preventDefault(); // SpaceBar 시 빈공간이 생기지 않도록 방지
      }
    });

    // 삭제 버튼
    // 삭제 버튼은 비동기적 생성이므로 document 최초 생성시가 아닌 검색을 통해 이벤트를 구현시킨다.
    $(document).on("click", ".btn_hashtag_delete", function (e) {
        let index = $(this).attr("idx");
        hashtag[index] = "";
        $(this).parent().remove();
      });
    // ==== 해시태그 구현 끝 ==== //


    //모집인원에 숫자만 입력되게 하기
    $("input#cnt_recruits").keyup(function(){
    	$(this).val($(this).val().replace(/[^0-9]/g,""));
    });


	// 등록 버튼을 클릭했을시 이벤트
    $("button#btn_write").click(function() {
        frm_check();

	    // 폼을 전송
        if(all_ok) {
            const frm = document.writerFrm;
            frm.method = "POST";
            frm.action = "/boards/academy/curriculum/new";
            frm.submit();
	    }
	});


    //수정 버튼을 클릭했을 때 이벤트
    $("button#btn_edit").click(function() {
        frm_check();

	    // 폼을 전송
        if(all_ok) {
            const frm = document.writerFrm;
            frm.method = "POST";
            frm.action = "/boards/academy/curriculum/edit/" + $("input#boardId").val();
            frm.submit();
	    }
	});
});// end of document




//Function Declaration

// 로봇이 아닙니다 클릭시
function callBackRecaptcha(){
    reCAPTCHA();
}

function frm_check() {
    var values = "";
    $("li.tag-item").each(function( index, element) {
      var value = $(this).text().substr(1);
      values += value+ ",";
    });

    values = values.slice(0, -1);
    $("input#hashtags").val(values);

    // ==== 스마트 에디터 구현 시작 ==== //
    // id가 content인 textarea에 에디터에서 대입
    obj.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);


    // 글제목 유효성 검사
    const subject = $("input#subject").val().trim();
    if(subject == "") {
        alert("글제목을 입력하세요!!");
        return;
    }


    //시작일자,마감일자 날짜변수잡기
    const curriculum_start_date = $("input#curriculum_start_date").val();
    const curriculum_end_date = $("input#curriculum_end_date").val();
    const recruitment_start_date = $("input#recruitment_start_date").val();
    const recruitment_end_date = $("input#recruitment_end_date").val();

    //마감일자-시작일자 구하기(메소드호출)
    const curriculumDateGap = getDateGap(curriculum_start_date,curriculum_end_date);
    const recruitmentDateGap = getDateGap(recruitment_start_date,recruitment_end_date);

    //과정 날짜 유효성검사
    if(!isNaN(curriculumDateGap)){  //정상적인 날짜 입력시
      if(curriculumDateGap == 0){
        alert("과정 시작일은 종료일과 같을 수 없습니다.")
        return;
      } else if(curriculumDateGap < 0) {
        alert("과정 종료일자는 시작일자보다 나중이어야 합니다.");
        return;
      }
    } else{
      alert("Error! 과정 날짜를 날짜가 아닌 값을 선택하셨습니다!");
      return;
    }

    //모집 날짜 유효성검사
    if(!isNaN(recruitmentDateGap)){  //정상적인 날짜 입력시
      if(recruitmentDateGap == 0){
        alert("과정 시작일은 종료일과 같을 수 없습니다.")
        return;
      } else if(recruitmentDateGap < 0){
        alert("과정 종료일자는 시작일자보다 나중이어야 합니다.");
        return;
      }
    } else{
      alert("Error! 과정 날짜를 날짜가 아닌 값을 선택하셨습니다!");
      return;
    }


    //신청링크 url 유효성검사
    const url = $("input#join_url").val();
    if(!test_url(url)){	//신청링크 주소를 통과하지 못한다면
        alert("올바른 신청링크를 입력해주세요!");
        return;
    }

    //핵심기술 유효성검사
    const core_technology = $("input#core_technology").val();
    if(core_technology.trim() == ""){
        alert("핵심기술을 입력해주세요!");
        return;
    }

    //모집인원 검사
    const cnt_recruits = $("input#cnt_recruits").val();
    if(cnt_recruits.trim() == ""){
        alert("모집인원을 입력해주세요!");
    }

    //내용 검사
    if($("textarea#content").val().length == 0) {
      alert("글내용을 입력하세요!!");
      return;
    }

    if(!recaptcha_ok){
        alert("매크로방지 봇 통과 후 진행해주세요");
        return;
    }
    all_ok = true;
}

/**
 * 신청링크 주소 유효성검사()
 * @param 사용자가 입력한 교육기관 홈페이지 주소 값
 * @returns 검사를 통과하면 true, 통과하지 못하면 false
 */
function test_url(url){
  const regExp = /(http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;

  if( !(regExp.test(url)) ){	//유효성검사를 통과못하면
    return false;
  }
  else{ //유효성검사를 통과한다면
    return true;
  }
}// end of method---




/**
 * 날짜 유효성검사
 */
function getDateGap(start_date,end_date){
  start_date = start_date.replace(/-/g,'');
  end_date = end_date.replace(/-/g,'');

  return end_date - start_date;
}//end of method--



/**
 * reCAPTCHA v2
 */
function reCAPTCHA(){
	$.ajax({
        url: '/api/v1/recaptcha/verify',
        type: 'post',
        data: {
            recaptcha: $("#g-recaptcha-response").val()
        },
        async:false,
        success: function(res) {
            switch (res.result) {
                case 0:
                    console.log("자동 가입 방지 봇 통과");
                    recaptcha_ok = true;
            		break;
                case 1:
                    console.log("자동 가입 방지 봇 통과 후 시도해주세요");
                    recaptcha_ok = false;
                    break;
                default:
                    console.log("자동 가입 방지 봇을 실행 하던 중 오류가 발생 했습니다. [Error bot Code : " + Number(data) + "]");
                	recaptcha_ok = false;
               		break;
            }
        }
    });//end of $.ajax({})
}//end of method-----




