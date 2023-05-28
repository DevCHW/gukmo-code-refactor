<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String ctxPath = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

  <%-- 직접 만든 CSS --%>
  <link rel="stylesheet" type="text/css" href="<%=ctxPath%>/resources/css/member/my/activities.css" />

  <%-- 직접만든 javascript --%>
  <script type="text/javascript" src="<%=ctxPath %>/resources/js/member/my/activities.js" ></script>

  <script type="text/javascript">

  		$(document).ready(function(){

  			const checkTab = ${requestScope.checkTab};

  			if(checkTab == null) {
  				$("div#view_activities").css("color","#ffff00");
  				$("div#view_activities").css("font-weight","#ffff00");
  				$("div#view_alarm").css("color","");
  				$("div#view_alarm").css("font-weight","");

  				$("div#alarm").hide();	//알람 감추기
  				$("div#activities").show();	//활동내역 보이기
  			}
  			else {
  				$("div#view_activities").css("color","");
  				$("div#view_activities").css("font-weight","");
  				$("div#view_alarm").css("color","#ffff00");
  				$("div#view_alarm").css("font-weight","bold");

  				$("div#activities").hide();	//활동내역 감추기
  				$("div#alarm").show();	//알람 보이기
  				viewAlarm(1);
  			}
  		});

  </script>

  <div class="container my-4">
  	<%-------------------- 사이드바 시작 ----------------------%>

    <%-- sidebar 호출 --%>
	<jsp:include page="/WEB-INF/views/tiles1/member/my/sidebar.jsp" />


    <%-------------------- 사이드바 끝 ----------------------%>

    <div id="main">
      <%-- main_header --%>
      <div class="main_header border rounded">
        <%-- top --%>
        <div class="d-flex">
          <div class="d-flex w-100 justify-content-between py-4 px-3 align-items-center">
            <div class="d-flex align-items-center">
              <%-- 프사 --%>
              <div id="profile_img_box" class="border">
              	<c:if test="${fn:substring(loginMember.profileImage,0,4) != 'http'}">
                  <img src="<%=ctxPath%>/resources/images/${loginMember.profileImage}"/>
                </c:if>
                <c:if test="${fn:substring(loginMember.profileImage,0,4) == 'http'}">
             	   <img src="${loginMember.profileImage}"/>
                </c:if>
              </div>

              <div class="ml-4 py-1">
                <h4 id="user_nickname">${loginMember.nickname}</h4>
                <div id="point">활동점수&nbsp;<span>${loginMember.point}</span></div>
              </div>
            </div>

            <button type="button" id="btn_go_myId" class="btn border rounded" onclick="location.href='/members/${loginMember.id}/my/account'">
              	나의 계정
            </button>
          </div>
        </div>

        <%-- bottom --%>
        <div id="navbar" class="d-flex justify-content-center border-top">
          <div id="view_activities" class="filter mx-4 py-3">
            	활동내역
          </div>
        </div>
      </div>


      <%-- 활동내역반복문 시작 --%>
	  <div id="activities" class="mt-4">

        <c:forEach var="activity" items="${activities}">
          <%-- 1 --%>
          <div class="activity_box border-top border-bottom py-4">
            <div class="activity_title align-items-center">
              <div class="d-flex align-items-center">

                <%-- 활동이일어난곳의 디테일카테고리--%>
                <c:if test="${not empty activity.activityBoardDto.secondCategory}">
                <div class="detail_category border rounded-pill px-2 py-2" onclick="goDetailCategory()">${activity.activityBoardDto.secondCategory}</div>
                </c:if>

                <c:if test="${empty activity.activityBoardDto.secondCategory}">
                <div class="detail_category border rounded-pill px-2 py-2" onclick="goDetailCategory()">${activity.activityBoardDto.firstCategory}</div>
                </c:if>

                <c:if test="${activity.division eq 'COMMENT_WRITE'}">

                <div class="activity_content ml-2">
                    <%-- 글쓴이 닉네임 --%>
                    <c:forEach var="writerNickname" items="${writerNicknames}">
                    <c:if test="${activity.activityBoardDto.id eq writerNickname.id}">
                 	  <span style="color:black; font-weight:bold">${writerNickname.writerNickname}</span>
                 	</c:if>
                 	</c:forEach>
                 	<span>님의 게시물에 댓글을 달았습니다.</span>
                </div>
                </c:if>

                <c:if test="${activity.division eq 'BOARD_WRITE'}">
                <div class="activity_content ml-2">에 글을 작성하였습니다.</div>
                </c:if>

                <c:if test="${activity.division eq 'BOARD_LIKE'}">
                <div class="activity_content ml-2">
                    <%-- 글쓴이 닉네임 --%>
                    <c:forEach var="writerNickname" items="${writerNicknames}">
                    <c:if test="${activity.activityBoardDto.id eq writerNickname.id}">
                      <span style="color:black; font-weight:bold">${writerNickname.writerNickname}</span>
                    </c:if>
                    </c:forEach>
                 	<span>님의 게시물을 추천하였습니다.</span>
                </div>
                </c:if>

                <c:if test="${activity.division eq 'COMMENT_LIKE'}">
                <div class="activity_content ml-2">
                 	<%-- 글쓴이 닉네임 --%>
                    <c:forEach var="writerNickname" items="${writerNicknames}">
                    <c:if test="${activity.activityBoardDto.id eq writerNickname.id}">
                      <span style="color:black; font-weight:bold">${writerNickname.writerNickname}</span>
                    </c:if>
                    </c:forEach>
                 	<span>님의 게시물에 달린 댓글을 추천하였습니다.</span>
                </div>
                </c:if>

              </div>
              <%-- 활동일자 --%>
              <div class="activity_date">${activity.activityDate}</div>
            </div>
            <%-- 활동이일어난 글제목 --%>
            <div class="activity_subject board mt-2" onclick="location.href='/boards/${activity.activityBoardDto.id}'">${activity.activityBoardDto.subject}</div>
          </div>
        </c:forEach>


        <%-- 활동내역이 없다면 --%>
	    <c:if test="${fn:length(activities) == 0}">
	      <div class="d-flex justify-content-center align-items-center border-top" style="height:300px;">
	      	<div style="font-size:18px; font-weight:bold;">활동내역이 없습니다.</div>
	      </div>
	    </c:if>

        <%----------------------------------------------------------- 페이지 바 시작 ---------------------------------------------%>
        <nav aria-label="...">
          ${pageBar}
        </nav>
        <%----------------------------------------------------------- 페이지 바 끝 ---------------------------------------------%>
      </div>

    </div>
    <%----------------------------------- main 끝-------------------------------------%>


  </div>



</body>
</html>