
// 답글모두숨기기 클릭횟수
let btn_comment_toggle_click_cnt = 0;
$(document).ready(function(){
  //전역변수
  var obj = [];

  // 게시글에 [...]클릭시 이벤트
  $("span#btn_more").click(()=>{
    $("div#mask").show();
    $("div#update_or_delete").show();
    $("div#update_or_delete").css("display","flex");
    $("div#update_or_delete").css("flex-direction","column");
  });// end of Event--


  // 댓글에 [...]클릭시 이벤트
  $("span.comment_btn_more").click(e=>{
	  $("div#mask").show();
	  const target = $(e.currentTarget);
	  target.children(1).show();
	  target.children().show();
	  target.children().css("display","flex");
	  target.children().css("flex-direction","column");
  });// end of Event--

  // 마스크 클릭시 이벤트
  $("div#mask").click(()=>{
    $("div#update_or_delete").hide();
    $("div.comment_update_or_delete").hide();
    $("div#mask").hide();
  });// end of Event--


  // 댓글쓰기 취소버튼 클릭시 이벤트
  $("button.btn_big_comment_close").click(e=>{
    const target = $(e.currentTarget);
    const btn_write_comment = target.parent().parent().parent().parent().prev().find("div.btn_write_comment");

    target.parent().parent().parent().hide(); // 댓글쓰기 영역 숨기기
    btn_write_comment.text("댓글쓰기");
  });// end of Event--


  // 답글 보였다가 숨기기 버튼 클릭이벤트
  $("div.btn_comment_toggle").click(e=>{
    const target = $(e.currentTarget);
    const bigCommentArea = target.parent().parent().next().children("div.big_comment_area");

    if(btn_comment_toggle_click_cnt%2==0){
      target.next().show(); // '댓글 O개 보기' 보이기
      target.hide();        // '댓글모두숨기기' 숨기기
      bigCommentArea.hide();// 대댓글영역 숨기기
    } else{
      target.prev().show(); // '댓글 O개 보기' 보이기
      target.hide();        // '댓글모두숨기기' 숨기기
      bigCommentArea.show();// 대댓글영역 보이기
    }
    btn_comment_toggle_click_cnt++;
  });// end of Event--

  //게시글 삭제버튼 클릭시 이벤트
  $("span#board_delete").click(e => {
    const boardId = $("input#hidden_board_id").val();
    delete_board(boardId);
  });

  // ///////////////////////// 댓글 관련 //////////////////////

  // 댓글 작성 버튼 클릭시 이벤트
  $("div.btn_write_comment").click(e=>{
    const target = $(e.currentTarget);
    // 대댓글 영역
    const bigCommentWriteArea = target.parent().parent().next().children(":first");
    if(target.text().trim() == "댓글쓰기"){ // 댓글쓰기를 눌렀을 때
      bigCommentWriteArea.css("display","flex");
      target.text("댓글쓰기 취소");
    } else if(target.text().trim() == "댓글쓰기 취소"){ // 댓글쓰기 취소를 눌렀을 때
      bigCommentWriteArea.css("display","none");
      target.text("댓글쓰기");
    }
  });// end of Event--

  // 댓글 삭제하기 버튼 클릭시 이벤트
  $("span.comment_delete").click(function(e) {
    if(confirm('정말 삭제하시겠습니까?')) {
       const target = $(e.currentTarget);
       const commentId = $(this).parent().parent().parent().parent().find("div.comment_writer_nickname").attr('id');
       deleteComment(commentId);
    }
  });// end of Event--

  // 댓글에서 ... 버튼 클릭후 수정하기 버튼 클릭시 이벤트
  $(document).on('click', 'span.btn_comment_edit', function(e) {
      const target = $(e.currentTarget);
      const content = target.parent().parent().parent().parent().next().children("div.comment_edit").children("textarea.content").val();
      target.parent().parent().parent().parent().next().children("div.comment_edit").show();
      target.parent().parent().parent().parent().next().children("div.detail_comment").hide();
      $("div#mask").trigger("click");
  });//end of Event--


  // 수정 취소 버튼 클릭시 이벤트
  $("button.btn_comment_edit_close").click(function(e) {
    const target = $(e.currentTarget);
    target.parent().parent().hide();
    target.parent().parent().prev().show();
  });//end of Event--


  // 댓글 수정하기 버튼 클릭시 이벤트
  $("button.btn_edit_comment").click(e => {
	  const target = $(e.currentTarget);
      const commentsId = target.prev().val();
      const content = target.parent().prev().val();

	  if(content.trim() == "") {
		  alert("수정할 내용을 입력하세요!");
		  return;
	  }
	  comment_edit(commentsId, content);
  });//end of Event--


  // 댓글 작성하기 버튼 클릭시 이벤트
  $("button#btn_comment_save").click(e => {
	  const target = $(e.currentTarget);
      const boardId = $("input#hidden_board_id").val();
      const content = target.parent().prev().val();
      const memberId = $("input#hidden_member_id").val();

	  if(content.trim() == "") {
		  alert("수정할 내용을 입력하세요!");
		  return;
	  }
	  const data = {"boardId" : boardId, "content":content, "memberId" : memberId};
	  comment_save(data);
  });//end of Event--

   // 대댓글 작성 버튼 클릭시 이벤트
   $("button.btn_big_comment_write").click(function(e){
     const target = $(e.currentTarget);
     const content = target.parent().prev().val();
     const boardId = $("input#hidden_board_id").val();
     const memberId = $("input#hidden_member_id").val();
     const parentId = target.prev().val();
     if(content.trim() == "") {
         alert("수정할 내용을 입력하세요!");
         return;
     }

	 const data = {"boardId" : boardId,
	               "content":content,
	               "memberId" : memberId,
	               "parentId" : parentId};
     comment_save(data);
   });// end of Event--


  // 댓글 블라인드 버튼 클릭시
  $("span.commentBlind").click(e=>{
	const target = $(e.currentTarget);
	const commentId = target.prev().val();
	comment_blind(commentId);
  });


  //댓글 블라인드해제  버튼 클릭시
  $("span.delCommentBlind").click(e=>{
	const target = $(e.currentTarget);
	const commentId = target.prev().val();
	del_comment_blind(commentId);
  });


  // 게시글 좋아요 버튼 클릭시 이벤트 잡기
  $("div#btn_like").click(e=>{
	const boardId = $("input#hidden_board_id").val();
	const memberId = $("input#hidden_member_id").val();

	if(memberId == "") {    //로그인 안했다면
	  $("button.btn_login").trigger("click");// 로그인페이지로 보내기
	} else {
    	const data = {"boardId": boardId,"memberId": memberId};
	    likeClick(data);	// 좋아요 클릭시 처리 메소드 호출
	}
  });// end of Event----


  // 댓글 좋아요 버튼 클릭시 이벤트 잡기
  $("span.comment_like_icon").click(e=>{
      const target = $(e.currentTarget);
	  const memberId = $("input#hidden_member_id").val();
      const commentId = target.parent().parent().prev().find('div.comment_writer_nickname').attr('id');

      console.log()
      if(memberId == "") {    //로그인 안했다면
        $("button.btn_login").trigger("click");// 로그인페이지로 보내기
      } else {
        const data = {"commentId": commentId,"memberId": memberId};
        commentLikeClick(data, target);	// 좋아요 클릭시 처리 메소드 호출
      }
  });//end of Event---


  // 게시글 신고하기 버튼 클릭시 이벤트
  $("span#btn_board_report").click(function(e){
    const memberId = $("input#hidden_member_id").val();
    const boardId = $("input#hidden_board_id").val();

    const data = {"memberId":memberId, "boardId":boardId};

    reportBoardExistCheck(data);
  });//end of Event---


  // 댓글 신고하기 버튼 클릭시
  $("span.comment_btn_report").click(function(e) {
      const target = $(e.currentTarget);
	  const memberId = $("input#hidden_member_id").val();
      const commentId = target.prev().val();

      const data = {"memberId":memberId, "commentId":commentId};

      reportCommentExistCheck(data);
  })//end of Event--

});// end of $(document).ready(function(){})---


// == Function Declaration == //

//게시글 좋아요
function likeClick(data){
	$.ajax({
		url:'/api/v1/like/board',
		type:'post',
		data:data,
		dataType:"json",
		success:function(res){
		    if(res.result.deleteOrInsert == 'insert') {
                $("span#like_icon").html("&#x1F497;"); // 꽉찬하트
                const like_cnt = parseInt($("span#like_cnt").text()) + 1;	// 좋아요개수
                                                                            // 1더하기
                $("span#like_cnt").html(like_cnt);
		    } else {
                $("span#like_icon").html("&#129293;");	// 빈하트
                const like_cnt = parseInt($("span#like_cnt").text()) - 1;	// 좋아요개수
                                                                            // 1빼기
                $("span#like_cnt").html(like_cnt);
		    }
		},// end of success
		// success 대신 error가 발생하면 실행될 코드
		error: function(request,error){
            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
        }
	});// end of $.ajax({})---
}


// 댓글 좋아요
function commentLikeClick(data, target){
	$.ajax({
		url:'/api/v1/like/comments',
		data:data,
		type:'POST',
		dataType:"json",
		success:function(res){
			if(res.result.deleteOrInsert == 'insert') {
                target.parent().children('.comment_like_icon').html("&#x1F497;");

                const likeCount = parseInt(target.parent().children('.comment_like_cnt').text()) + 1;
                target.parent().children('.comment_like_cnt').html(likeCount);
			} else {
			    target.parent().children('.comment_like_icon').html("&#129293;");
                target.find('span.comment_like_icon').html("&#129293;");
                const likeCount = parseInt(target.parent().children('.comment_like_cnt').text()) - 1;
                target.parent().children('.comment_like_cnt').html(likeCount);
			}
		},// end of success
		// success 대신 error가 발생하면 실행될 코드
		error: function(request,error){
			alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		}
	  });// end of $.ajax({})---
}


// 댓글,대댓글 블라인드 버튼 클릭시
function comment_blind(id) {
	$.ajax({
		url:"/api/v1/admin/comments/" + id + "/blind",
		type:'patch',
		dataType:"json",
		success:function(res){
			if(res.success) {
			    alert("블라인드 처리하였습니다.");
				window.location.reload();
			} else{
				alert("블라인드 기능 오류");
			}
		},// end of success
		// success 대신 error가 발생하면 실행될 코드
		error: function(request,error){
			alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		}
	  });
}//end of method--


//댓글,대댓글 블라인드 해제 버튼 클릭시
function del_comment_blind(id) {
	$.ajax({
		url:"/api/v1/admin/comments/" + id + "/blind/clear",
		type:'patch',
		dataType:"json",
		success:function(res){
			if(res.success) {
                alert("블라인드를 해제하였습니다.");
                window.location.reload();
            } else{
                alert("블라인드 기능 오류");
            }
		},// end of success
		// success 대신 error가 발생하면 실행될 코드
		error: function(request,error){
			alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		}
	  });
}

// 게시글 신고버튼 클릭시
function openBoardReport() {
	// 신고 버튼
	var openWin;
	const boardId = $("input#hidden_board_id").val();

    window.name = "boardDetail";
    // window.open("open할 window", "자식창 이름", "팝업창 옵션");
    openWin = window.open("/report/boards/"+boardId,
            "reportForm", "width=576, height=700, left=500, top= 20");
}

// 댓글 신고버튼 클릭시
function openCommentReport(id) {
	// 신고 버튼
	var openWin;
    window.name = "boardDetail";
    // window.open("open할 window", "자식창 이름", "팝업창 옵션");
    openWin = window.open("/report/comments/"+id,
            "reportForm", "width=576, height=700, left=500, top= 20");
}


// [...]클릭후, 게시글 삭제버튼 클릭시 이벤트
function delete_board(boardId){
  const firstCategory = $("input#hidden_firstCategory").val();
  const secondCategory = $("input#hidden_secondCategory").val();
  if(confirm('정말 삭제하시겠습니까?')) {
      $.ajax({
        url:"/api/v1/boards/" + boardId,
        type:"delete",
        dataType:"json",
        success:function(res){
            if(res.success) {
                alert("게시글 삭제에 성공하였습니다!");
                location.href="/boards?firstCategory=" + firstCategory + "&secondCategory=" + secondCategory;
            } else {
                alert("게시글 삭제에 실패하였습니다. 다시 시도해주세요");
                window.location.reload();
            }
        },
        error: function(request, status, error){
            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
        }
      });//end of ajax--
  } else {
    return false;
  }
};// end of Event--


// 댓글작성
function comment_save(data) {
	$.ajax({
      url:"/api/v1/comments",
      data:data,
      type:"post",
      dataType:"json",
      success:function(res){
          if(res.success) {
              alert("댓글 작성에 성공하였습니다!");
              window.location.reload();
          } else {
              alert("댓글 작성에 실패하였습니다. 다시 시도해주세요");
              window.location.reload();
          }
      },
      error: function(request, status, error){
          alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
      }
  });
}//Event method--


// 비로그인상태에서 댓글작성창 로그인버튼 클릭시 이벤트
function no_login_comment() {
	$("button.btn_login").trigger("click");// 로그인페이지로 보내기
}


// 댓글 삭제
function deleteComment(commentId){
    $.ajax({
        url:"/api/v1/comments/" + commentId,
        type:"delete",
        dataType:"JSON",
        success:function(res){
            if(res.success) {
                alert("댓글을 삭제하였습니다.");
                window.location.reload();
            } else {
                alert("댓글 삭제에 실패하였습니다. 다시 시도해주세요.");
            }
        },
        error: function(request, status, error){
            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
        }
    });
}//end of method--


// 댓글 수정
function comment_edit(commentsId, content) {
  $.ajax({
      url:"/api/v1/comments/"+commentsId,
      data:{"content": content},
      type:"patch",
      dataType:"json",
      success:function(res){
          if(res.success) {
            alert("댓글이 수정되었습니다!");
            window.location.reload();
          } else {
              alert("댓글 수정에 실패하였습니다. 다시 시도하여주세요.");
          }
      },
      error: function(request, status, error){
          alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
      }
  });
}//end of Method--

// 게시물 신고 이미 했는지 체크
function reportBoardExistCheck(data) {
    $.ajax({
        type : 'get',
        url : '/api/v1/report/boards/exist',
        data : data,
        dataType : 'json',
        success : function(res){
            if(res.result){
               alert("이미 신고한 게시물입니다.");
            } else {
               openBoardReport();
            }
        },
        error: function(xhr, status, error){
            alert("서버 오류입니다. 관리자에게 문의해주세요");
        }
    });//end of ajax
}//end of Method--


// 댓글 신고 이미 했는지 체크
function reportCommentExistCheck(data) {
    $.ajax({
        type : 'get',
        url : '/api/v1/report/comments/exist',
        data : data,
        dataType : 'json',
        success : function(res){
            if(res.result){
               alert("이미 신고한 게시물입니다.");
            } else {
               openCommentReport(data.commentId);
            }
        },
        error: function(xhr, status, error){
            alert("서버 오류입니다. 관리자에게 문의해주세요");
        }
    });//end of ajax
}//end of Method--
