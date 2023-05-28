<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
   String ctxPath = request.getContextPath();
%>

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/resources/css/board/community/communityList.css" />

    <%------------------------------------- 필독 공지사항 리스트 시작 -------------------------------------%>
     <c:forEach var="mustReadNotice" items="${mustReadNotices}">
     <div class="border-top px-2 py-2" style="background-color:#F0F6FA;">
       <div class="d-flex align-items-center my-2">
         <%-- 작성자 프로필사진 --%>
         <c:if test="${fn:substring(mustReadNotice.profileImage,0,4) != 'http'}">
           <a href="#" class="writer_image_box border">
           <img src="<%=ctxPath%>/resources/images/${mustReadNotice.profileImage}"/>
           </a>
         </c:if>
         <c:if test="${fn:substring(mustReadNotice.profileImage,0,4) == 'http'}">
            <a href="#" class="writer_image_box border">
            <img src="${mustReadNotice.profileImage}"/>
            </a>
         </c:if>

         <%-- 작성자 닉네임 --%>
         <%-- 클릭하면 해당 유저의 활동내역 페이지로 이동하게 링크 거세요. --%>
         <a href="#" class="writer_nickname ml-2">
          	 ${mustReadNotice.nickname }
         </a>

         <%-- 작성자 활동점수 --%>
         <div class="writer_point ml-2">
           <i class="fa-solid fa-bolt"></i>
           <span>${mustReadNotice.writerPoint}</span>
         </div>

         <%-- 작성일자 --%>
         <div class="write_date ml-2">
           ${mustReadNotice.writeDate}
         </div>
       </div>

       <%-- 글제목 --%>
       <a href="<%=ctxPath%>/boards/${mustReadNotice.id}" class="subject align-items-center my-2" style="font-size:14px;">
         ${mustReadNotice.subject}
       </a>

       <div class="d-flex justify-content-between align-items-center my-2">
         <div class="d-flex align-items-center">
           <%-- 게시판상세카테고리 클릭 -> 해당 게시판으로 이동--%>
           <div class="detail_category border rounded px-2 py-1">
           	  ${mustReadNotice.secondCategory}
           </div>
           <div class="hashtag ml-1">
             <%-- 해시태그리스트 시작 --%>
             <c:forEach var="noticeHashtag" items="${noticeHashtags}">
                 <c:if test="${mustReadNotice.id == noticeHashtag.boardId}">
                 <a href="#" class="hashtag mx-1">#<span>${hashtag.hashtag}</span></a>
                 </c:if>
             </c:forEach>
             <%-- 해시태그리스트 끝 --%>
           </div>
         </div>

         <%-- 조회수,댓글수,추천수 --%>
         <div class="board_info_box d-flex justify-content-end">
           <%-- 조회수 --%>
           <div>
             <i class="fa-solid fa-eye"></i>
             <span>${mustReadNotice.views}</span>
           </div>

           <%-- 댓글수 --%>
           <div class="ml-2">
             <i class="fa-solid fa-comment-dots"></i>
             <span>${mustReadNotice.commentCount}</span>
           </div>

           <%-- 추천수 --%>
           <div class="ml-2">
             <i class="fa-solid fa-heart"></i>
             <span>${mustReadNotice.likeCount}</span>
           </div>
         </div>
       </div>
     </div>
     </c:forEach>
	 <%------------------------------------- 필독 공지사항 리스트 끝 -------------------------------------%>