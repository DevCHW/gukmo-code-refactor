let all_ok = false;

$(document).ready(function(){
    //댓글신고버튼 클릭시
	$("button#btn_comment_report").click(function() {
		frm_check();
		if(all_ok) {
            const commentId = $("input#hidden_commentId").val();
            const reporterId = $("input#hidden_reporterId").val();
            const simpleReason = $("select#simple_report_reason").val();
            const detailReason = $("textarea#detail_report_reason").val();
            const data = {"commentId":commentId,
                          "reporterId":reporterId,
                          "simpleReason":simpleReason,
                          "detailReason":detailReason};
            comment_report(data);
        }
	});//end of Event--


	//게시글신고버튼 클릭시
    $("button#btn_board_report").click(function() {
        frm_check();

        if(all_ok) {
            const boardId = $("input#hidden_boardId").val();
            const reporterId = $("input#hidden_reporterId").val();
            const simpleReason = $("select#simple_report_reason").val();
            const detailReason = $("textarea#detail_report_reason").val();
            const data = {"boardId":boardId,
                          "reporterId":reporterId,
                          "simpleReason":simpleReason,
                          "detailReason":detailReason};
            board_report(data);
        }
    });//end of Event--

	//사유 바꿀 시
	$("select#simple_report_reason").change(function(e) {
	  const value = e.target.value;
	  if(value != "사유") {
	    $("textarea#detail_report_reason").val(value);
	  } else {
	    $("textarea#detail_report_reason").val("");
	    $("textarea#detail_report_reason").focus();
	  }
	});
});// end of document---



//Function Declaration
function frm_check() {
    const simpleReason = $("select#simple_report_reason").val().trim();
	const detailReason = $("textarea#detail_report_reason").val().trim();
    if(simpleReason == "" || simpleReason == "사유") {
        alert("사유를 선택해주세요");
        return;
    }

    if(simpleReason == "기타" && detailReason == "") {
        alert("상세 사유를 입력해주세요");
        return;
    }

    all_ok = true;
}//end of method--

// 댓글신고
function comment_report(data) {
    $.ajax({
        type : 'POST',
        url : '/api/v1/report/comments',
        data : data,
        dataType : 'json',
        success : function(res){
            if(res.success){
                alert("신고 처리가 완료되었습니다!");
                window.close();
            } else {
                alert("신고 처리에 실패하였습니다. 다시 시도해주세요");
            }
        },
        error: function(xhr, status, error){
            alert("신고 처리에 실패하였습니다. 다시 시도해주세요");
        }
    });//end of ajax
}//end of Method--

// 게시물 신고
function board_report(data) {
    $.ajax({
        type : 'POST',
        url : '/api/v1/report/boards',
        data : data,
        dataType : 'json',
        success : function(res){
            if(res.success){
                alert("신고 처리가 완료되었습니다!");
                window.close();
            } else {
                alert("신고 처리에 실패하였습니다. 다시 시도해주세요");
            }
        },
        error: function(xhr, status, error){
            alert("신고 처리에 실패하였습니다. 다시 시도해주세요");
        }
    });//end of ajax
}//end of Method--

