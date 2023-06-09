<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String ctxPath = request.getContextPath();
%>
<%-- Custom styles for this page --%>
<link href="<%=ctxPath %>/resources/css/dataTables/dataTables.bootstrap4.min.css" rel="stylesheet">
<%-- dataTable bootstrap4 --%>
<script src="<%=ctxPath %>/resources/js/dataTables/jquery.dataTables.min.js"></script>
<script src="<%=ctxPath %>/resources/js/dataTables/dataTables.bootstrap4.min.js"></script>


<script type="text/javascript">
    $(document).ready(function(){
      let html = "";
      let statusArr = ['ACTIVE','SUSPENDED','WAIT'];

      const currentStatus = "${member.status}";
      const authorityArr = ['MEMBER', 'ACADEMY', 'ADMIN'];
      const currentAuthority = "${member.userRole}";

      //회원상태 select태그에 값 넣기
      for(let i = 0; i<statusArr.length ;i++){
        if(statusArr[i] != currentStatus){  //회원상태가 같지 않다면
          html += "<option>"+statusArr[i]+"</option>";
        } else{ //회원 상태가 같다면 기본값으로 넣기
          html += "<option selected>"+statusArr[i]+"</option>";
        }
      }//end of for--
      $("select#select_status").html(html);

      html = ""; // html 초기화
      //회원권한 select태그에 값 넣기
      for(let i = 0; i<authorityArr.length ;i++){
        if(authorityArr[i] != currentAuthority){  //회원상태가 같지 않다면
          html += "<option>"+authorityArr[i]+"</option>";
        } else{ //회원 상태가 같다면 기본값으로 넣기
          html += "<option selected>"+authorityArr[i]+"</option>";
        }
      }//end of for--
      $("select#select_authority").html(html);

    });//end of $(document).ready(function(){})--
</script>

<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<script src="https://code.highcharts.com/modules/accessibility.js"></script>

<script src="https://code.highcharts.com/modules/wordcloud.js"></script>

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%=ctxPath %>/resources/css/admin/member/memberDetail.css" />
<link rel="stylesheet" type="text/css" href="<%=ctxPath %>/resources/css/admin/member/chart.css" />

<%-- 직접만든 javascript --%>
<script type="text/javascript" src="<%=ctxPath %>/resources/js/admin/member/memberDetail.js" ></script>


<%-- Main Content --%>
<div id="content" class="px-3 py-3">
  <%-- Begin Page Content --%>
  <div class="container-fluid">
  <%-- Page Heading --%>
      <div class="card shadow mb-4 mt-4">
     	<div class="card-body">
     		<%-- 회원 기본정보 박스 시작 --%>
		   <div id="member_basic_info_box">
		     <div id="id_image_box" class="align-items-center my-3">
		       <%-- profile_image border속성 지워야 함--%>
		       <div id="profile_img_box" class="mr-3 border">
		         <c:if test="${member.profileImage.substring(0,4) != 'http'}">
		           <img src="<%=ctxPath %>/resources/images/${member.profileImage}">
		         </c:if>
		         <c:if test="${member.profileImage.substring(0,4) == 'http'}">
		           <img src="${member.profileImage}">
		         </c:if>
		       </div>
		       <%-- nickname --%>
		       <div id="nickname">
		         ${member.nickname} 회원 정보
		       </div>
		     </div>

		     <div class="wrap_box">
		       <div>
		         <%-- 이메일수신동의일경우 나타내기 --%>
		         <c:if test="${member.emailAccept == 'YES'}">
		           <button id="btn_send_email" type="button" class="btn btn-light border rounded" data-toggle="modal" data-target="#sendEmailModal" data-dismiss="modal">이메일 전송하기</button>
		         </c:if>
		       </div>

		       <hr>

		       <%-- userid --%>
		       <c:if test="${not empty member.userId}">
		       <div id="userid" class="member_basic_info py-3">
		         <span class="info_title">아이디</span>
		         <span>${member.userId}</span>
		       </div>
		       </c:if>

		       <%-- username --%>
		       <div id="username" class="member_basic_info py-3">
		         <span class="info_title">회원이름</span>
		         <span>${member.username}</span>
		       </div>

		       <%-- email --%>
		       <div id="email" class="member_basic_info py-3">
		         <span class="info_title">이메일</span>
		         <span>${member.email}</span>
		       </div>

		       <%-- point --%>
		       <div id="point" class="member_basic_info py-3">
		         <span class="info_title">활동점수</span>
		         <span>${member.point}</span>
		       </div>


		       <%-- email_acept if문으로 표현 0:동의 1:비동의--%>
		       <div id="email_acept" class="member_basic_info py-3">
		         <span class="info_title">이메일수신동의여부</span>
		         <span>
                <c:if test="${member.emailAccept == 'NO'}">거부</c:if>
		        <c:if test="${member.emailAccept == 'YES'}">동의</c:if>
		         </span>
		       </div>



		       <%-- status --%>
		       <div id="status" class="member_basic_info py-3">
		         <span class="info_title">상태</span>
		         <select name="status" id="select_status" class="border rounded">
		         </select>
		         <span class="mr-2">${member.status}</span>
		         <span id="btn_insert_penalty_modal" data-toggle="modal" data-target="#insert_penalty_modal" data-dismiss="modal">정지사유등록</span>
		         <%-- 회원상태가 정지일경우 정지사유보기 버튼 --%>
		         <c:if test="${member.status == 'SUSPENDED'}">
		           <span id="view_reason" data-toggle="modal" data-target="#penalty_reason" data-dismiss="modal">정지사유보기</span>
		         </c:if>
		       </div>


				 <%-- 만약 member 에 academy_name 이 비어있지 않다면 --%>

				 <c:if test="${not empty member.academyName}">
		         <%-- academyName --%>
		         <div id="academy_name" class="member_basic_info py-3">
		           <span class="info_title">교육기관명</span>
		           <span>${member.academyName}</span>
		         </div>

		         <%-- company_num --%>
		         <div id="company_num" class="member_basic_info py-3">
		           <span class="info_title">사업자등록번호</span>
		           <span>${member.companyNum}</span>
		         </div>
		         <%-- homepage --%>
		         <div id="homepage" class="member_basic_info py-3">
		           <span class="info_title">홈페이지URL</span>
		           <span>${member.homepage}</span>
		         </div>

		         <%-- tel --%>
		         <div id="tel" class="member_basic_info py-3">
		           <span class="info_title">교육기관 전화번호</span>
		           <span>${member.tel}</span>
		         </div>
		       </c:if>




		       <%-- join_date --%>
		       <div id="join_date" class="member_basic_info py-3">
		         <span class="info_title">가입일자</span>
		         <span>${member.joinDate}</span>
		       </div>


		       <%-- authority --%>
		       <div id="authority" class="member_basic_info d-flex align-items-center py-3">
		         <span class="info_title">권한</span>
		         <select name="authority" id="select_authority" class="border rounded">
		         </select>
		         <span>${member.userRole}</span>
		       </div>

		       <%-- 소셜계정 연동여부 --%>
		       <div id="sns_login" class="member_basic_info d-flex align-items-center py-3">
		         <span class="info_title">
		           SNS 연동여부
		         </span>

		         <c:forEach var="oauth" items="${oauthList}">
		         <c:if test="${oauth == 'KAKAO'}">
		           <div class="sns_image mx-1">
		             <img src="<%=ctxPath %>/resources/images/login/카카오로그인.PNG" class="rounded">
		           </div>
		         </c:if>
		         <c:if test="${oauth == 'NAVER'}">
		           <div class="sns_image mx-1">
		             <img src="<%=ctxPath %>/resources/images/login/네이버로그인.png" class="rounded">
		           </div>
		         </c:if>
		         <c:if test="${oauth == 'FACEBOOK'}">
		           <div class="sns_image mx-1">
		             <img src="<%=ctxPath %>/resources/images/login/구글로그인.PNG" class="border rounded">
		           </div>
		         </c:if>
		         <c:if test="${oauth == 'GOOGLE'}">
		           <div class="sns_image mx-1">
		             <img src="<%=ctxPath %>/resources/images/login/페이스북로그인.svg" class="rounded">
		           </div>
		         </c:if>
		         </c:forEach>
		         <c:if test="${empty oauthList}">
		         	<span>SNS연동회원 X</span>
		         </c:if>
		       </div>
		     </div>
		   </div>
		   <%-- 회원 기본정보 박스 끝 --%>
		   <div class="d-flex justify-content-end">
		   	<button type="button" class="btn btn-light border rounded mr-3" onclick="javascript:history.back()">뒤로가기</button>
		     <button id="btn_editInfo" type="button" class="btn btn-light border rounded mr-3">수정</button>
		     <button id="btn_editClose" type="button" class="btn btn-light border rounded">취소</button>
		   </div>
        </div>
      </div>


   <hr>


   <%-- 회원관련 데이터 네비게이션 바 클릭시 전부 ajax로 html 태그 넣기 --%>
   <div class="card shadow my-2 py-2 m-auto" style="width:80%;">
    	<div class="card-body">
    		<div id="member_navbar" class="d-flex justify-content-center align-items-center my-2 py-2">
		       <div id="write_board_list_nav" class="px-3 py-2">작성게시물</div>
		       <div id="report_nav" class="px-3 py-2">신고내역</div>
		    </div>
    	</div>
    </div>

   <%-- 회원관련 데이터 네비게이션 바 클릭시 전부 ajax로 html 태그 넣기 --%>


      <%-- 작성한 게시물 내역 시작 --%>
      <div id="member_write_board_list" class="detail_info_area my-3">
        <div class="card shadow mb-4 mt-4">
        	<div class="card-header py-3">
             	<h6 class="m-0 font-weight-bold text-primary">${member.nickname}회원 작성게시물</h6>
          	</div>
	       <div class="card-body">
	       	 <%-- 데이터테이블 활동내역 --%>
	         <table class="table table-bordered mt-3" id="dataTable-write-board" width="100%">
	            <thead>
	                <tr>
	                  <th>글번호</th>
	                  <th>카테고리</th>
	                  <th>상세카테고리</th>
	                  <th>제목</th>
	                  <th>작성일자</th>
	                </tr>
	            </thead>
	         </table>
	       </div>
	    </div>

      </div>
      <%-- 작성한 게시물 내역 끝--%>



      <%-- 신고내역 시작 --%>
      <div id="member_report_list" class="detail_info_area my-3">
        <%-- 회원이 신고한내역 시작 --%>
        <div class="card shadow mb-4 mt-4">
        	<div class="card-header py-3">
             	<h6 class="m-0 font-weight-bold text-primary">${member.nickname}님의 신고한 내역</h6>
          	</div>
	       <div class="card-body">
	       	 <%-- 데이터테이블 신고한내역 --%>
	         <table class="table table-bordered mt-3" id="dataTable-report" width="100%">
	            <thead>
	                <tr>
	                  <th>구분</th>
	                  <th>사유</th>
	                  <th>신고일자</th>
	                  <th>게시글번호</th>
	                  <th>댓글번호</th>
	                </tr>
	            </thead>
	         </table>
	       </div>
	    </div>
      </div>
      <%-- 신고내역 끝 --%>
  </div>
</div>
<%---------------------------------- 사이드바 오른쪽 컨텐츠(div#main) 끝 -------------------------------------%>



  <%------------------------------------------------- modal시작 --------------------------------%>

  <%---------------- 정지 사유보기 모달 시작 ------------------%>
  <div class="modal fade" id="penalty_reason">
    <div class="modal-dialog">
      <div class="modal-content">

        <%-- Modal header --%>
        <div class="modal-header">
          <h5 class="modal-title">${member.nickname}회원 정지사유</h5>
          <button type="button" class="close email_certificationClose" data-dismiss="modal">&times;</button>
        </div>

        <%-- Modal body --%>
        <div class="modal-body">
          <div class="m-auto">
            <%-- 정지번호 --%>
            <div class="my-2">
              <div class="penalty_reason">정지번호</div>
              <div>${penalty.penalty_num}</div>
            </div>

            <%-- 정지간단사유 --%>
            <div class="my-2">
              <div class="penalty_reason">정지사유</div>
              <div>${penalty.simple_penalty_reason}</div>
            </div>

            <%-- 정지상세사유 --%>
            <div class="my-2">
              <div class="penalty_reason">상세</div>
              <div id="penalty_detail_reason">
                <p>${penalty.detail_penalty_reason}</p>
              </div>
            </div>

            <%-- 정지기간 --%>
            <div class="my-2">
              <div class="penalty_reason">정지 기간</div>
              <div class="d-flex">
                <span>${penalty.penalty_start_date}</span>
                <span>&nbsp;~&nbsp;</span>
                <span>${penalty.penalty_end_date}</span>
                <span>(${penalty.penalty_period}일)</span>
              </div>
            </div>
          </div>

        </div>

        <%-- Modal footer --%>
        <div class="modal-footer">
          <button type="button" class="btn border penalty_reason_close" data-dismiss="modal">닫기</button>
        </div>
      </div>

    </div>
  </div>
  <%--------------------- 정지 사유보기 모달 끝 -------------------%>




  <%------------- 이메일 보내기 모달 시작 ----------------%>
  <div class="modal fade" id="sendEmailModal">
    <div class="modal-dialog">
      <div class="modal-content">

        <%-- Modal header --%>
        <div class="modal-header">
          <h5 class="modal-title">이메일 발송하기</h5>
          <button type="button" class="close email_certificationClose" data-dismiss="modal">&times;</button>
        </div>

        <%-- Modal body --%>
        <div class="modal-body d-flex flex-column">
          <label for="email_message">메세지</label>
          <textarea name="email_message" id="email_message" cols="30" rows="10" class="px-2 py-2 border rounded" placeholder="회원에게 전송할 메세지를 입력해주세요."></textarea>
        </div>

        <%-- Modal footer --%>
        <div class="modal-footer">
          <button type="button" class="btn border" onclick="sendEmail('${member.email}')">전송</button>
          <button type="button" class="btn border sendEmailModal_close" data-dismiss="modal">닫기</button>
        </div>
      </div>

    </div>
  </div>
  <%-- 이메일 보내기 모달 끝 --%>



  <%-- 정지등록 폼 모달 시작 --%>
  <div class="modal fade" id="insert_penalty_modal">
    <div class="modal-dialog">
      <div class="modal-content">

        <%-- Modal header --%>
        <div class="modal-header">
          <h5 class="modal-title">${member.nickname} 정지사유등록하기</h5>
          <button type="button" class="close email_certificationClose" data-dismiss="modal">&times;</button>
        </div>

        <%-- Modal body --%>
        <div class="modal-body d-flex flex-column">
          <form name="penaltyRegisterFrm">

            <div class="d-flex flex-column py-3 px-3">

              <%-- 닉네임 --%>
              <label for="nickname"></label>
              <input type="text" id="nickname" name="nickname" value="${member.nickname}" class="border rounded my-2" style="height:40px;" readonly>

              <%-- 정지간단사유 --%>
              <label for="simple_penalty_reason">정지사유</label>
              <select name="simpleReason" id="simple_penalty_reason" class="border rounded my-2" style="height:40px;">
                <option>욕설/비방</option>
                <option>허위글게시</option>
                <option>정치적인글</option>
                <option>기타사유</option>
              </select>

              <%-- 정지상세사유 --%>
              <div id="detail_penalty_reason_area" class="flex-column">
                <label for="detail_penalty_reason">상세사유 작성</label>
                <textarea name="detailReason" id="detail_penalty_reason" cols="30" rows="10" placeholder="정지 사유를 상세하게 작성하세요." class="border rounded px-2 py-2 my-2"> </textarea>
              </div>

              <%-- 정지기간 --%>
              <label for="penalty_period">정지기간</label>
              <select name="period" id="penalty_period" class="border rounded my-2" style="height:40px;">
                <option>7일</option>
                <option>30일</option>
                <option>90일</option>
                <option>150일</option>
                <option>300일</option>
              </select>
            </div>
          <input type="hidden" name="memberId" value="${member.id}"/>
          </form>
        </div>

        <%-- Modal footer --%>
        <div class="modal-footer">
          <button type="button" class="btn border insert_penalty_modal_close" data-dismiss="modal">저장</button>
        </div>
      </div>
    </div>
  </div>
  <%--------------------- 정지등록 폼 모달 끝 -------------------------%>


  <%------------------------------------------------- modal끝 --------------------------------%>
  <input type="hidden" id="hidden_member_id" value="${member.id}"/>
  <input type="hidden" id="hidden_member_status" value="${member.status}"/>
  <input type="hidden" id="hidden_member_userRole" value="${member.userRole}"/>