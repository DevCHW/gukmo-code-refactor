<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
   String ctxPath = request.getContextPath();
%>

  <%-- 직접 만든 CSS --%>
  <link rel="stylesheet" type="text/css" href="<%=ctxPath %>/resources/css/board/boardDetail.css" />

  <%-- 직접만든 javascript --%>
  <script type="text/javascript" src="<%=ctxPath %>/resources/js/board/boardDetail.js" ></script>

  <div id="mask"></div>
  <div class="container my-5">
    <div class="line my-4" style="width:1150px; margin-left: -20px;">

      <a id="category_area" >
        <c:if test="${board.firstCategory == '커뮤니티'}">
      	  <div onclick="location.href='/boards?firstCategory=커뮤니티&secondCategory=자유'">${board.firstCategory}&nbsp;</div>
        </c:if>
        <c:if test="${board.firstCategory == '국비학원'}">
      	  <div onclick="location.href='/boards?firstCategory=국비학원'">${board.firstCategory}&nbsp;</div>
        </c:if>
        <c:if test="${board.firstCategory == '공지사항'}">
      	  <div onclick="location.href='/boards?firstCategory=공지사항'">${board.firstCategory}&nbsp;</div>
        </c:if>
      </a>

      <c:if test="${not empty board.secondCategory}">
      <span>/</span>

      <a id="detail_category_area">
      	<div id="board_detail_category" onclick="goDetailCategory()">&nbsp;${board.secondCategory}</div>
      </a>
      </c:if>
    </div>



    <%-- 글 상세보기 페이지 머리부분(작성자 프로필이미지, 작성자 닉네임, 활동점수, 작성일자, 조회수, 신고버튼, 수정,삭제버튼) --%>
    <div id="detail_header" class="d-flex justify-content-between align-items-center">

      <div id="writer_profile_image_box">
        <%-- 글 작성자 프로필이미지 --%>
        <c:if test="${board.writer.profileImage.substring(0,4) != 'http'}">
              <img src="<%=ctxPath %>/resources/images/${board.writer.profileImage}">
            </c:if>
            <c:if test="${board.writer.profileImage.substring(0,4) == 'http'}">
              <img src="${board.writer.profileImage}">
            </c:if>
      </div>


      <div id="writer_profile_body" class="d-flex flex-column w-100 px-2 py-1">
        <%-- 작성자 닉네임 들어가면 해당 유저의 활동내역을 볼수 있는 페이지로 이동--%>
        <div>
        <a onclick="location.href='<%=ctxPath %>/member/activityOther.do?nickname=${board.writer.nickname}'" id="board_writer_nickname" class="pl-2" style="font-size: 18px; color:#212529;">${board.writer.nickname}</a>
        </div>
        <%-- 활동점수,작성일자,조회수 영역--%>
        <div class="d-flex">
          <%-- 활동점수 --%>
          <div id="board_writer_point" class="mx-2">
            <i class="fa-solid fa-bolt"></i>
            <span>${board.writer.point}</span>
          </div>

          <span>·</span>

          <%-- 작성일자(형식은 약 ?분전, ?시간전, ?일전, ?년전) --%>
          <div id="board_write_date" class="mx-2">
            ${board.writeDate}
          </div>

          <span>·</span>

          <%-- 조회수 --%>
          <div id="board_views" class="mx-2">
            <i class="fa-solid fa-eye"></i>
            <span>${board.views}</span>
          </div>
        </div>
      </div>
      <%-- 신고버튼, 수정or삭제버튼 --%>
      <div id="report_edit_delete_area" class="d-flex justify-content-between align-items-center">

          <c:if test="${not empty loginMember && loginMember.userRole != 'ADMIN' && loginMember.nickname != board.writer.nickname}">
          	<span id="" class="ml-auto btn_report" onclick="openReport()">&#x1F6A8;</span>
          </c:if>


        <c:if test="${loginMember.nickname == board.writer.nickname && loginMember.userRole != 'ADMIN'}">
          <span id="btn_more" class="rounded px-2 py-1" style="margin-left: 30px;"><span id="menu_icon" style="font-size: 20px;">&#8230;</span>
            <div id="update_or_delete" class="border rounded px-3 py-2">
             <c:if test="${board.firstCategory == '커뮤니티'}">
              <span onclick="location.href='<%=ctxPath %>/community/modify.do?boardNum=${board.id}'">수정하기</span>
             </c:if>

             <c:if test="${board.firstCategory == '국비학원'}">
              <span onclick="location.href='<%=ctxPath %>/academy/edit.do?boardNum=${board.id}'">수정하기</span>
             </c:if>

             <c:if test="${not empty board.secondCategory && board.secondCategory == '교육과정'}">
              <span onclick="location.href='<%=ctxPath %>/academy/curriculum/edit.do?boardNum=${board.id}'">수정하기</span>
             </c:if>

              <span id="board_delete">삭제하기</span>
            </div>
          </span>
        </c:if>

        <c:if test="${loginMember.nickname == board.writer.nickname && loginMember.userRole == 'ADMIN'}">
          <span id="btn_more" class="rounded px-2 py-1" style="margin-left: 30px;"><span id="menu_icon" style="font-size: 20px;">&#8230;</span>
            <div id="update_or_delete" class="border rounded px-3 py-2">
              <c:if test="${board.secondCategory == '커뮤니티'}">
              <span onclick="location.href='<%=ctxPath %>/community/modify.do?boardNum=${board.id}'">수정하기</span>
             </c:if>

             <c:if test="${board.secondCategory == '국비학원'}">
              <span onclick="location.href='<%=ctxPath %>/academy/edit.do?boardNum=${board.id}'">수정하기</span>
             </c:if>

             <c:if test="${board.secondCategory == '교육과정'}">
              <span onclick="location.href='<%=ctxPath %>/academy/curriculum/edit.do?boardNum=${board.id}'">수정하기</span>
             </c:if>

             <c:if test="${board.secondCategory == '공지사항'}">
              <span onclick="location.href='<%=ctxPath %>/admin/notice/edit.do?boardNum=${board.id}'">수정하기</span>
             </c:if>

              <span id="board_delete" onclick="del_board(${board.id})">삭제하기</span>
            </div>
          </span>
        </c:if>

        <c:if test="${loginMember.nickname != board.writer.nickname && loginMember.userRole == 'ADMIN'}">
          <span id="btn_more" class="rounded px-2 py-1" style="margin-left: 30px;"><span id="menu_icon" style="font-size: 20px;">&#8230;</span>
            <div id="update_or_delete" class="border rounded px-3 py-2">
            <c:if test="${requestScope.board.secondCategory == '공지사항'}">
              <span onclick="location.href='<%=ctxPath %>/admin/notice/edit.do?boardNum=${board.id}'">수정하기</span>
            </c:if>
            <c:if test="${requestScope.board.secondCategory == '교육과정'}">
              <span onclick="location.href='<%=ctxPath %>/academy/curriculum/edit.do?boardNum=${board.id}'">수정하기</span>
            </c:if>
            <c:if test="${requestScope.board.secondCategory == '국비학원'}">
              <span onclick="location.href='<%=ctxPath %>/academy/edit.do?boardNum=${board.id}'">수정하기</span>
            </c:if>
              <span id="board_delete" onclick="del_board(${board.id})">삭제하기</span>
            </div>
          </span>
        </c:if>

      </div>
    </div>
    <%-- 글 상세보기 페이지 머리부분 --%>


    <%-------------------- 글 본문 시작 ------------------%>
    <div id="content_area" class="d-flex flex-column py-2">
      <div id="subject" class="mt-3">
        <h2 id="board_subject">${board.subject}</h2>
      </div>


	<c:if test="${board.secondCategory == '국비학원'}">
		<%-------------------- academyDetail 호출 시작----------------------%>
		<%-- academyDetail 호출 --%>
		<jsp:include page="/WEB-INF/views/tiles1/board/academy/academyDetail.jsp" />
		<%-------------------- academyDetail 호출 시작 ----------------------%>
	</c:if>


	<c:if test="${board.secondCategory == '교육과정'}">
		<%-------------------- curriculumDetail 호출 시작----------------------%>
		<%-- curriculumDetail 호출 --%>
		<jsp:include page="/WEB-INF/views/tiles1/board/academy/curriculumDetail.jsp" />
		<%-------------------- curriculumDetail 호출 시작 ----------------------%>
	</c:if>


     <%-- 글내용 --%>
      <div id="content" class="mt-3">${board.content} </div>
      <div class="d-flex justify-content-between mt-4">
         <div>
             <%-- 해시태그리스트 반복문시작 --%>
              <c:forEach var="hashtag" items="${board.hashtags}">
                <a id="hashtag" onclick="location.href='<%=ctxPath %>/main_search.do?searchWord=${hashtag}&hashtag=${hashtag}'" class="hashtag mx-1">#<span>${hashtag}</span></a>
              </c:forEach>
              <%-- 해시태그리스트 반복문 끝--%>
         </div>


             <%-- 좋아요 아이콘, 눌렀을경우 &#x1F497; 안눌렀을경우 &#129293;--%>
             <div class="ml-auto">
             <c:if test="${board.likeExist == false}">
                 <div type="button" id="btn_like">
                   <span id="like_icon">&#129293;</span>
                   <span id="like_cnt">${board.likeCount}</span>
                 </div>
             </c:if>

             <c:if test="${board.likeExist == true}">
                 <div type="button" id="btn_like">
                   <span id="like_icon">&#x1F497;</span>
                   <span id="like_cnt">${board.likeCount}</span>
                 </div>
             </c:if>
             </div>
      </div>
    </div>
    <%-------------------- 글 본문 끝 ------------------%>

    <%---------------------- 이전글,다음글 영역 시작 ----------------------%>
    <div id="previous_next_area" class="px-3 d-flex justify-content-center align-items-center rounded mt-4">
      <div class="w-100">


        <div id="previous" class="my-2">
          <span>이전글 |</span>
          <%-- 이전글 a태그 href에 ?num=이전글번호--%>
          <c:if test="${board.prevAndNextBoardDto.previousSubject == '이전글이 없습니다.'}">
            <a id="no_previous">이전글이 없습니다.</a>
          </c:if>

          <c:if test="${board.prevAndNextBoardDto.previousSubject != '이전글이 없습니다.'}">
            <a href="/boards/${board.prevAndNextBoardDto.previousId}">${board.prevAndNextBoardDto.previousSubject}</a>
          </c:if>
        </div>


        <%-- 다음글 a태그 href에 ?num=다음글번호--%>
        <div id="next" class="previous_next_area my-2">
          <span>다음글 |</span>
          <c:if test="${board.prevAndNextBoardDto.nextSubject != '다음글이 없습니다.'}">
            <a href="/boards/${board.prevAndNextBoardDto.nextId}">${board.prevAndNextBoardDto.nextSubject}</a>
          </c:if>

          <c:if test="${board.prevAndNextBoardDto.nextSubject == '다음글이 없습니다.'}">
            <a id="no_next">다음글이 없습니다.</a>
          </c:if>
        </div>

      </div>
    </div>
    <%---------------------- 이전글,다음글 영역 끝 ----------------------%>



    <%---------------------- 광고 영역 시작 ----------------------%>

	<c:if test="${not empty advertisement_List}">

	<div id="demo" class="carousel slide mt-4" data-ride="carousel" >

     <%-- The slideshow --%>
     <div class="carousel-inner" style="height:140px;">


     <div class="carousel-item active">
     	<c:forEach end="0" var="advertisement_List" items="${requestScope.advertisement_List}" varStatus="status">
     	<a href="${advertisement_List.url}">
       		<c:if test="${advertisement_List.filename.substring(0,4) != 'http'}">
              <img src="<%=ctxPath %>/resources/images/${advertisement_List.filename}" style="cursor: pointer; width: 100%; height:140px;">
            </c:if>
            <c:if test="${advertisement_List.filename.substring(0,4) == 'http'}">
               <img src="${advertisement_List.filename}" style="cursor: pointer; width: 100%; height:140px;">
            </c:if>
        </a>
         </c:forEach>
       </div>

         <c:forEach begin="1" var="advertisement_List" items="${requestScope.advertisement_List}" varStatus="status">

    <div class="carousel-item ">
        <c:if test="${advertisement_List.filename.substring(0,4) != 'http'}">
         <a href="${advertisement_List.url}">
          <img src="<%=ctxPath %>/resources/images/${advertisement_List.filename}" style="cursor: pointer; width: 100%; height:140px;">
         </a>
        </c:if>
        <c:if test="${advertisement_List.filename.substring(0,4) == 'http'}">
         <a href="${advertisement_List.url}">
           <img src="${advertisement_List.filename}" style="cursor: pointer; width: 100%; height:140px;">
         </a>
        </c:if>

    </div>


	</c:forEach>


     </div>

     <!-- Left and right controls -->
     <a class="carousel-control-prev" href="#demo" data-slide="prev">
       <span class="carousel-control-prev-icon"></span>
     </a>
     <a class="carousel-control-next" href="#demo" data-slide="next">
       <span class="carousel-control-next-icon"></span>
     </a>

</div>
</c:if>

<c:if test="${empty requestScope.advertisement_List}">
	<div id="advertisement_box" class="mt-4" onclick="location.href='https://kfq.or.kr/_KR/Default.aspx'" style="cursor: pointer;">
      <img src="<%= ctxPath%>/resources/images/학원광고이미지1.PNG"/>
    </div>
</c:if>
    <%---------------------- 광고 영역 끝 ----------------------%>


    <hr>


    <%---------------------- 댓글쓰기 영역 시작 ----------------------%>
    <%-- 댓글 총 갯수, 숫자부분에 넣으면 됨 --%>
    <div id="total_comment_cnt" class="my-3">
      <span id="total_comment">${board.commentCount}</span><span>개의 댓글</span>
    </div>

    <c:if test="${not empty loginMember}">
	    <form name="addWriteFrm" id="addWriteFrm">
	    <div id="write_comment_area" class="border rounded px-4 py-4">
	      <div class="d-flex w-100">
	        <div class="login_user_profile_img_box">
	          <%-- 로그인되어있는 유저 프로필 이미지 --%>
	          <c:if test="${loginMember.profileImage.substring(0,4) != 'http'}">
              	<img src="<%=ctxPath %>/resources/images/${loginMember.profileImage}"/>
              </c:if>
              <c:if test="${loginMember.profileImage.substring(0,4) == 'http'}">
         	    <img src="${loginMember.profileImage}"/>
              </c:if>
	        </div>
	            <input id="userid" type="hidden" name="userid" value="${loginMember.id}"  />
	            <input id="cmt_board_num"  type="hidden" name="cmt_board_num" value="${board.id}"  />
	            <input id="nickname"  type="hidden" name="nickname" value="${loginMember.nickname}"  />
	            <input id="parent_write_nickname" type="hidden" name="parent_write_nickname" value="${board.writer.nickname}"  />
	        <div class="ml-3 w-100">
	          <div class="mb-1">내용</div>
	          <textarea id="content" name="content" class="pl-2 py-2" rows="5"></textarea>
	          <div class="d-flex justify-content-end mt-2">
	            <button type="button" class="btn btn-info" id="btn_comment_save">댓글 쓰기</button>
	          </div>
	        </div>

	      </div>
	    </div>
	    </form>
    </c:if>

    <c:if test="${empty loginMember}">
    <div id="write_comment_area" class="border rounded px-4 py-4">
	      <div class="d-flex w-100 align-items-end pt-3 pl-2" style="font-size:18px;">
	          <span>&#127760; 댓글을 쓰려면&nbsp;</span>
	          <a style="color:#208EC9; text-decoration: underline; font-weight: bold" onclick="no_login_comment()"> 로그인 </a>
	          <span>이 필요합니다</span>
	      </div>
	      <div class="d-flex justify-content-end mt-4">
	            <button type="button" disabled="disabled" class="btn btn-secondary">댓글 쓰기</button>
	       </div>
	</div>

    </c:if>
    <%---------------------- 댓글쓰기 영역 끝 ----------------------%>





    <%---------------------- 댓글리스트 영역(반복문) 시작 ----------------------%>

    <%-- 댓글 반복문 시작 --%>
    <c:forEach var="comment" items="${board.comments}" varStatus="status">
    <div class="comment_area pb-4 mt-2">
      <div class="comment px-3 py-4" id="">

        <%-- 댓글작성자의 프로필이미지, 활동점수, 댓글작성일자 --%>
        <div class="d-flex justify-content-between align-items-center comment_writer_info">
          <div class="comment_writer_profile_img_box mr-2">
            <c:if test="${comment.writer.profileImage.substring(0, 4) != 'http'}">
              <img src="<%=ctxPath %>/resources/images/${comment.writer.profileImage}"/>
            </c:if>
            <c:if test="${comment.writer.profileImage.substring(0, 4) == 'http'}">
         	   <img src="${comment.writer.profileImage}"/>
            </c:if>
          </div>

          <div class="d-flex flex-column w-100">
            <div class="comment_writer_nickname" id ="${comment.id}" onclick="location.href='<%=ctxPath %>/member/activityOther.do?nickname=${bcommentList.nickname}'" style="cursor:pointer; width:20%;">${comment.writer.nickname}</div>
            <div class="mt-1">
              <%-- 댓글작성자 활동점수 --%>
              <span class="mr-2">
                <i class="fa-solid fa-bolt"></i>
                <span>${comment.writer.point}</span>
              </span>

              <span>·</span>
              <%-- 댓글작성일자 --%>
              <span class="ml-2">
                ${comment.writeDate}
              </span>
            </div>
          </div>

          <%-- 오른쪽 영역 --%>
          <%-- 댓글 좋아요버튼 --%>
          <c:if test="${empty loginMember}">
          <div>
	          <div class="comment_like" style="width: 50px;">
	            <%-- 댓글 좋아요 아이콘, 눌렀을경우 &#x1F497; 안눌렀을경우 &#9825;--%>
	            	<span class="comment_like_icon">&#129293;</span>
	            <%-- 댓글 좋아요 갯수 --%>
	            <span class="comment_like_cnt">${comment.likeCount}</span>
	          </div>
	      </div>
          </c:if>

          <%-- 오른쪽 영역 --%>
          <c:if test="${not empty loginMember && loginMember.userRole != 'ADMIN' && loginMember.id != comment.writer.id}">
                  <div style="padding-right: 20px; display: flex;">
			          <div class="comment_like" style="width: 45px;">
			            <%-- 댓글 좋아요 아이콘, 눌렀을경우 &#x1F497; 안눌렀을경우 &#9825;--%>
			            <c:if test="${comment.likeExist == true}">
			            	<span class="comment_like_icon">&#x1F497;</span>
			            </c:if>

			            <c:if test="${comment.likeExist == false}">
			            	<span class="comment_like_icon">&#129293;</span>
			            </c:if>
			            <%-- 댓글 좋아요 갯수 --%>
			            <span id="${comment.likeCount}" class="comment_like_cnt">${comment.likeCount}</span>
			          </div>
			          <div class="d-flex justify-content-between align-items-center comment_edit_delete_area" style="width:0px;">
			          	<span class="comment_btn_report ml-auto">&#x1F6A8;</span>
			          </div>
		          </div>
          </c:if>

          <%-- 오른쪽 영역 --%>
          <c:if test="${not empty loginMember && loginMember.userRole != 'ADMIN' && loginMember.id == comment.writer.id}">
          <div  style="display: flex;">
          	<div class="comment_like" style="width: 35px; margin-top: 9px;">
	            <%-- 댓글 좋아요 아이콘, 눌렀을경우 &#x1F497; 안눌렀을경우 &#9825;--%>
	            <c:if test="${comment.likeExist == true}">
	            	<span class="comment_like_icon">&#x1F497;</span>
	            </c:if>

	            <c:if test="${comment.likeExist == false}">
	            	<span class="comment_like_icon">&#129293;</span>
	            </c:if>
	            <%-- 댓글 좋아요 갯수 --%>
	            <span class="comment_like_cnt">${comment.likeCount}</span>
	          </div>
	          <span class="rounded px-2 py-1 comment_btn_more">
	            <span id="menu_icon" style="font-size: 20px;">&#8230;</span>
          		<div class="border rounded px-3 py-2 comment_update_or_delete">
	            	<span class="btn_comment_edit">수정하기</span>
	            	<span class="comment_delete">삭제하기</span>
          		</div>
          	  </span>
          </div>
          </c:if>

          <%-- 오른쪽 영역 --%>
          <c:if test="${not empty loginMember && loginMember.userRole == 'ADMIN'}">
          <div style="display: flex;">
          	<div class="comment_like" style="width: 35px; margin-top: 9px;">
	            <%-- 댓글 좋아요 아이콘, 눌렀을경우 &#x1F497; 안눌렀을경우 &#9825;--%>
	            <c:if test="${comment.likeExist == true}">
	            	<span class="comment_like_icon">&#x1F497;</span>
	            </c:if>

	            <c:if test="${comment.likeExist == false}">
	            	<span class="comment_like_icon">&#129293;</span>
	            </c:if>
	            <%-- 댓글 좋아요 갯수 --%>
	            <span id="${comment.likeCount}" class="comment_like_cnt">${comment.likeCount}</span>
          	</div>
          	<span class="rounded px-2 py-1 comment_btn_more"><span id="menu_icon" style="font-size: 20px;">&#8230;</span>
          	    <c:if test ="${loginMember.id != comment.writer.id}">
          	    <input type="hidden" id="comment_num" class="" name="" value="${comment.id}" />
          		<div class="border rounded px-3 py-2 comment_update_or_delete">
	            	<c:if test ="${comment.blind == 'NO'}">
	            		<span id="commentBlind">블라인드</span>
	            	</c:if>

	            	<c:if test ="${comment.blind == 'YES'}">
	            		<span id="delCommentBlind">블라인드 해제</span>
	            	</c:if>
          		</div>
          		</c:if>

          		<c:if test ="${loginMember.id == comment.writer.id}">
          		<div class="border rounded px-3 py-2 comment_update_or_delete">
	            	<span class="btn_comment_edit">수정하기</span>
	            	<span class="comment_delete">삭제하기</span>
          		</div>
          		</c:if>
          	</span>
          </div>
          </c:if>

        </div>

        <%-- 댓글 내용 --%>
        <div class="my-3 basic_comment_content">
            <c:if test="${comment.blind == 'NO'}">
			  <div class = "detail_comment" >${comment.content}</div>
			</c:if>

			<c:if test="${comment.blind == 'YES'}">
			  <div class = "detail_comment" >관리자에 의해서 블라인드 처리 되었습니다.</div>
			</c:if>

			<%-- 댓글수정 에디터 --%>
 	        <div class="ml-3 w-100 comment_edit">
	          <textarea class="pl-2 py-2 content rounded" rows="5">${comment.content}</textarea>
	          <div class="d-flex justify-content-end mt-2">
	            <input type="hidden" class="comment_id" value="${comment.id}"/>
	            <button type="button" class="btn_edit_comment btn btn-info mr-3">수정</button>
	            <button type="button" class="btn_comment_edit_close btn btn-light border rounded">취소</button>
	          </div>
	        </div>
        </div>


        <div class="d-flex">
          <%-- 대댓글이 있는 경우 보이기 시작 / 대댓글 없는경우 안보여야 함 시작--%>
 		  <c:if test= "${comment.children.size() != 0}" >
          <div class="btn_comment_toggle big_comment_hide mr-3">
            <span><i class="fa-solid fa-chevron-up"></i>&nbsp;댓글 모두 숨기기</span>
          </div>

          <%-- 대댓글갯수  --%>
          <div class="btn_comment_toggle big_comment_show mr-3">
            <span><i class="fa-solid fa-chevron-down"></i>&nbsp;댓글&nbsp;<span>${comment.children.size()}</span>개 보기</span>
          </div>
		  </c:if>
          <%-- 대댓글이 있는 경우일경우 보이기 끝 대댓글 없는경우 안보여야 함 끝 --%>

          <%-- 댓글쓰기 --%>
          <div class="btn_write_comment">댓글쓰기</div>
        </div>
      </div>

      <%--------------------------------------------------- 대댓글 영역 시작 ---------------------------------------%>
      <div class="d-flex flex-column align-items-end">

        <%--------------------------------- 대댓글 쓰기영역 시작 ------------------------------%>
        <div class="big_comment_write_area pl-4">
          <div class="login_user_profile_img_box">
            <%-- 로그인되어있는 유저 프로필 이미지 --%>
            <c:if test="${loginMember.profileImage.substring(0,4) != 'http'}">
               <img src="<%=ctxPath %>/resources/images/${loginMember.profileImage}"/>
            </c:if>

            <c:if test="${loginMember.profileImage.substring(0,4) == 'http'}">
               <img src="${loginMember.profileImage}"/>
            </c:if>
          </div>

          <div class="ml-3 w-100">
            <div class="mb-1">${loginMember.nickname}</div>
            <input type="hidden" value="${comment.id}"/>
    		<textarea class="pl-2 py-2 content2" name="content" rows="5"></textarea>
            <div class="d-flex justify-content-end mt-2">
              <button type="button" class="btn_big_comment_close btn btn-light border rounded mr-3">취소</button>
              <input type="hidden" class="parentId" name="parentId" value="${comment.id}" />
              <button type="button" class="btn_big_comment_write btn btn-info">댓글 쓰기</button>
            </div>
          </div>
        </div>
        <%----------------------------------- 대댓글 쓰기영역 끝 -----------------------------------%>


      <%----------------------------------- 대댓글리스트(반복문) 시작 -----------------------------------%>
      <c:forEach var="childComment" items="${comment.children}">

	        <div class="big_comment_area pl-4 pt-4" id="">
	          <%-- 대댓글작성자의 프로필이미지, 활동점수, 댓글작성일자 --%>
	          <div class="big_comment_writer_info d-flex justify-content-between align-items-center">
	            <div class="big_comment_writer_profile_img_box mr-3">
	              <c:if test="${childComment.writer.profileImage.substring(0, 4) != 'http'}">
	                  <img src="<%=ctxPath %>/resources/images/${childComment.writer.profileImage}"/>
	                </c:if>
	                <c:if test="${childComment.writer.profileImage.substring(0, 4) == 'http'}">
	             	   <img src="${childComment.writer.profileImage}"/>
	                </c:if>
	            </div>

	            <div class="d-flex flex-column w-100">
	              <div class="comment_writer_nickname" id="${childComment.id}"
	                   onclick="location.href='<%=ctxPath %>/member/activityOther.do?nickname=${spcial_commentList.nickname}'" style="cursor:pointer; width:20%;">${childComment.writer.nickname}</div>
	              <div class="mt-1">
	                <%-- 대댓글작성자 활동점수 --%>
	                <span class="mr-2">
	                  <i class="fa-solid fa-bolt"></i>
	                  <span>${childComment.writer.point}</span>
	                </span>

	                <span>·</span>
	                <%-- 대댓글작성일자 --%>
	                <span class="ml-2">
              			${childComment.writeDate}
	                </span>
	              </div>
	            </div>

	      	   <c:if test="${empty loginMember}">
	      	     <div>
		          <div class="big_comment_like" style="width: 50px; margin-right: 15px;">
		            <%-- 댓글 좋아요 아이콘, 눌렀을경우 &#x1F497; 안눌렀을경우 &#129293;--%>
		            <span class="comment_like_icon">&#129293;</span>
		            <%-- 댓글 좋아요 갯수 --%>
		            <span id="${childComment.likeCount}" class="comment_like_cnt">${childComment.likeCount}</span>
		          </div>
		          </div>
          	   </c:if>

              <c:if test="${not empty loginMember && loginMember.userRole != 'ADMIN' && loginMember.nickname != childComment.writer.nickname}">
                  <div style="padding-right: 35px; display: flex;">
			          <div class="big_comment_like" style="width: 45px;">
			            <%-- 댓글 좋아요 아이콘, 눌렀을경우 &#x1F497; 안눌렀을경우 &#129293;--%>
			            <c:if test="${childComment.likeExist == true}">
			            	<span class="comment_like_icon">&#x1F497;</span>
			            </c:if>

			            <c:if test="${childComment.likeExist == false}">
			            	<span class="comment_like_icon">&#129293;</span>
			            </c:if>
			            <%-- 댓글 좋아요 갯수 --%>
			            <span id="${childComment.likeCount}" class="comment_like_cnt">${childComment.likeCount}</span>
			          </div>
			          <input type="hidden" id="" value="${childComment.writer.nickname}" />
		          	  <input type="hidden" id="" value="${childComment.id}" />
			          <div id="" class="d-flex justify-content-between align-items-center comment_edit_delete_area" style="width:0px;">
			          	<span class="big_comment_btn_report ml-auto">&#x1F6A8;</span>
			          </div>
		          </div>
          	  </c:if>

          	  <c:if test="${not empty loginMember && loginMember.userRole != 'ADMIN' && loginMember.nickname == childComment.writer.nickname}">
          	  <div style="display: flex;">
	          	  <div class="big_comment_like" style="width: 35px; margin-top: 9px;">
		            <%-- 댓글 좋아요 아이콘, 눌렀을경우 &#x1F497; 안눌렀을경우 &#9825;--%>
		            <c:if test="${childComment.likeExist == true}">
		            	<span class="comment_like_icon">&#x1F497;</span>
		            </c:if>

		            <c:if test="${childComment.likeExist == false}">
		            	<span class="comment_like_icon">&#129293;</span>
		            </c:if>
		            <%-- 댓글 좋아요 갯수 --%>
		            <span id="${childComment.likeCount}" class="comment_like_cnt">${childComment.likeCount}</span>
		          </div>
		          <span class="rounded px-2 py-1 comment_btn_more" style="margin-right: 15px;"><span id="menu_icon" style="font-size: 20px;">&#8230;</span>
	          		<div class="border rounded px-3 py-2 comment_update_or_delete">
		            	<span class="btn_comment_edit">수정하기</span>
		            	<span class="comment_delete">삭제하기</span>
	          		</div>
	          	  </span>
	          </div>
          	  </c:if>

	          <c:if test="${not empty loginMember && loginMember.userRole == 'ADMIN'}">
	            <div style="padding-right: 16px; display: flex;">
		          	<div class="big_comment_like" style="width: 35px; margin-top: 9px;">
			            <%-- 댓글 좋아요 아이콘, 눌렀을경우 &#x1F497; 안눌렀을경우 &#9825;--%>
			            <c:if test="${childComment.likeExist == true}">
			            	<span class="comment_like_icon">&#x1F497;</span>
			            </c:if>

			            <c:if test="${childComment.likeExist == false}">
			            	<span class="comment_like_icon">&#129293;</span>
			            </c:if>
			            <%-- 댓글 좋아요 갯수 --%>
			            <span id="${childComment.likeCount}" class="comment_like_cnt">${childComment.likeCount}</span>
		          	</div>
		          	<span class="rounded px-2 py-1 comment_btn_more"><span id="menu_icon" style="font-size: 20px;">&#8230;</span>
		          		<c:if test ="${loginMember.nickname != childComment.writer.nickname}">
		          			<div class="border rounded px-3 py-2 comment_update_or_delete">
		          		    <c:if test ="${childComment.blind == 'NO'}">
			            		<span id="bigCommentBlind">블라인드</span>
			            	</c:if>

			            	<c:if test ="${childComment.blind == 'YES'}">
			            		<span id="delBigCommentBlind">블라인드 해제</span>
			            	</c:if>
		          			</div>

		          		</c:if>
		          		<c:if test ="${loginMember.nickname == childComment.writer.nickname}">
		          		<input type="hidden" id="" value="${childComment.id}" />
		          		<div class="border rounded px-3 py-2 comment_update_or_delete">
			            	<span class="btn_comment_edit">수정하기</span>
			            	<span class="comment_delete">삭제하기</span>
		          		</div>
		          		</c:if>
		          	</span>
	          	</div>
	          </c:if>
      	       <%-- 댓글 신고,수정,삭제 끝 --%>
	          </div>


		       <%-- 대댓글내용 --%>
		       <div class="my-3 special_comment_content" id="${childComment.content}">
		          <c:if test="${childComment.blind == 'NO'}">
					<div class="detail_comment my-3" >${childComment.content}</div>
				  </c:if>

				  <c:if test="${childComment.blind == 'YES'}">
					<div class="detail_comment my-3" >관리자에 의해서 블라인드 처리 되었습니다.</div>
				  </c:if>

				  <%-- 대댓글수정 에디터 --%>
		          <div class="ml-3 w-100 comment_edit">
                      <input type="hidden" value="${childComment.id}"/>
			          <textarea class="pl-2 py-2 content rounded" rows="5">${childComment.content}</textarea>
			          <div class="d-flex justify-content-end mt-2">
	                    <input type="hidden" class="childComment_id" value="${childComment.id}"/>
                        <button type="button" class="btn_edit_comment btn btn-info mr-3">수정</button>
                        <button type="button" class="btn_comment_edit_close btn btn-light border rounded">취소</button>
			          </div>
			      </div>
	           </div>
	        </div>
       </c:forEach>
      <%----------------------------------- 대댓글리스트(반복문) 끝 -----------------------------------%>

      </div>
      <%----------------------------------------- 대댓글 영역 끝 ------------------------------------------%>

    </div>
    </c:forEach>
    <%--------------------댓글 반복문 끝 --------------------%>
  </div>

  <input type="hidden" id="hidden_firstCategory" value="${board.firstCategory}"/>
  <input type="hidden" id="hidden_secondCategory" value="${board.secondCategory}"/>
  <input type="hidden" id="hidden_board_id" value="${board.id}"/>
  <input type="hidden" id="hidden_member_id" value="${loginMember.id}"/>








