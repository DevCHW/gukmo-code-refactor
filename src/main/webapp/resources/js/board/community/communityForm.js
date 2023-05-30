let recaptcha_ok = false;
let all_ok = false;
let obj = [];

$(document).ready(function() {

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

      // ==== 해시태그 구현 시작 ==== //
      var hashtag = {};
      var counter = 0;

      // 태그를 추가한다.
      function addTag(value) {
    	hashtag[counter] = value; // 태그를 Object 안에 추가
        counter++; // counter 증가 삭제를 위한 del-btn 의 고유 id 가 된다.
      }

      function marginTag() {
          return Object.values(hashtag).filter(function (word) {
              return word !== "";
            });
        }

      // 최종적으로 서버에 넘길때 tag 안에 있는 값을 만들어서 넘긴다.
      function strTag() {
        return Object.values(hashtag).join(',');
      }

      $("#hashtag").on("keydown", function (e) {
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
          var index = $(this).attr("idx");
          hashtag[index] = "";
          $(this).parent().remove();
        });
        // ==== 해시태그 구현 끝 ==== //

		$("#secondCategory").change(function(){
	    	let secondCategory = $("#secondCategory").val();
	    	obj.getById["content"].exec("SET_IR", [""]);

	    	var sHTML;

	    	switch (secondCategory) {
	    	case "자유":
				sHTML =`자유게시판 유의사항<br>
						⁃	신고가 된 게시글도 통보없이 삭제될 수 있습니다.<br>
						⁃	부적절한 게시글 발견 시 신고해주세요.<br>
						⁃	광고 및 사기성 게시물은 통보없이 삭제될 수 있습니다.`;
				break;
			case "QnA":
				sHTML =`QnA 유의사항<br>
					    ⁃	신고가 된 게시글도 통보없이 삭제될 수 있습니다.<br>
						⁃	부적절한 게시글 발견 시 신고해주세요.<br>
						⁃	광고 및 사기성 게시물은 통보없이 삭제될 수 있습니다.`;
				break;
			case "수강/취업후기":
				sHTML=`수강/취업 후기 게시판 합격 후기를 자유롭게 남겨주세요 :)<br>
						<br>
						학원<br>
							1.	지원한 학원에 대한 정보를 작성해주세요.<br>
							&nbsp;&nbsp;-	지원시기 :<br>
							&nbsp;&nbsp;-	학원위치 :<br>
							&nbsp;&nbsp;-	교육기간 :<br>
							&nbsp;&nbsp;-	프로그래밍 언어 :<br>
							&nbsp;&nbsp;-	면접방식 :<br>
							&nbsp;&nbsp;-	학원 후기 :<br>
						    <br><br><br>
						취업<br>
							1.	지원한 회사에 대한 정보를 작성해주세요.<br>
							&nbsp;&nbsp;-	지원시기 :<br>
							&nbsp;&nbsp;-	지원회사와 부서 :<br>
							&nbsp;&nbsp;-	나의 합격 스펙 :<br>
							2.	서류 단계 :<br>
							3.	인적성 단계 :<br>
							4.	면접 단계 :<br>
							&nbsp;&nbsp;-	면접일자 :<br>
							&nbsp;&nbsp;-	면접형태(ex.일대일, 다대다 등) :<br>
							5.	본인만의 꿀팁 또는 노하우 :<br>
						    <br><br><br>
						수강/취업 후기 게시판 유의사항<br>
							⁃	신고가 된 게시글도 통보없이 삭제될 수 있습니다.<br>
							⁃	부적절한 게시글 발견 시 신고해주세요.<br>
							⁃	광고 및 사기성 게시물은 통보없이 삭제될 수 있습니다.`;
				break;
			case "스터디":
				sHTML=`스터디 게시판<br>
						1.	스터디 종류 :<br>
						2.	모집 인원 :<br>
						3.	장소/시간 :<br>
						4.	회비 :<br>
						5.	진행 방식 :<br>
						6.	팀장 연락처 :<br>
						7.	커리큘럼 :<br>
						8.	기타 :<br>
					     <br><br><br>
					스터디 게시판 유의사항<br>
						⁃	신고가 된 게시글도 통보없이 삭제될 수 있습니다.<br>
						⁃	부적절한 스터디 발견 시 신고해주세요.<br>
						⁃	광고 및 사기성 게시물은 통보없이 삭제될 수 있습니다.`;
				break;

			case "취미모임":
				sHTML=`

				취미 모임 게시판<br>
				1.	모임 종류 :<br>
				2.	모집 인원 :<br>
				3.	장소/시간 :<br>
				4.	회비 :<br>
				5.	진행 방식 :<br>
				6.	모임장 연락처 :<br>
				7.	모임 방향성 :<br>
				8.	기타 :<br>
			    <br><br><br>
			    취미 모임 게시판 유의사항<br>
				⁃	신고가 된 게시글도 통보없이 삭제될 수 있습니다.<br>
				⁃	부적절한 모임 발견 시 신고해주세요.<br>
				⁃	광고 및 사기성 게시물은 통보없이 삭제될 수 있습니다.`;
				break;

			}

	    	obj.getById["content"].exec("PASTE_HTML", [sHTML]);
    });

	// 등록 버튼을 클릭했을시
    $("button#btn_write").click(function() {
    	frm_check();
	    // 폼을 전송
    	if(all_ok){
    		const frm = document.writeFrm;
    		frm.method = "POST";
    		frm.action = "/boards/community/new";
    		frm.submit();
    	}
	});

	// 수정 버튼을 클릭했을시
    $("button#btn_modify").click(function() {
    	frm_check();
    	if(all_ok) {
		    // 폼을 전송
    		const frm = document.writeFrm;
    		frm.method = "POST";
		    frm.action = "/boards/community/edit/" + $("input#boardId").val();
		    frm.submit();
    	}
	});
});// end of document

//Function Declaration
// 로봇이 아닙니다 클릭시
function callBackRecaptcha(){
    reCAPTCHA();
}

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


/**
 * 폼 유효성 검사
 */
function frm_check(){
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

    // 카테고리 유효성 검사
    const secondCategory = $("select#secondCategory").val();
    if(secondCategory.trim() == "") {
        alert("카테고리를 선택하세요!!");
        return;
    }

    // 글제목 유효성 검사
    const subject = $("input#subject").val().trim();
    if(subject == "") {
        alert("글제목을 입력하세요!!");
        return;
    }

    if($("textarea#content").val().length == 0) {
      alert("글내용을 입력하세요!!");
      return;
    }

    if(!recaptcha_ok) {
        alert("매크로 방지 봇 통과 후 진행해주세요.");
        return;
    }
    all_ok = true;
}//end of method--



