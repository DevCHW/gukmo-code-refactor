<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
   String ctxPath = request.getContextPath();
%>
<%-- 직접 만든 CSS --%>
<link rel="stylesheet" href="<%=ctxPath%>/resources/css/hello/header.css">

<%-- 직접만든 javascript --%>
<script type="text/javascript">
    const redirectURL = window.location.pathname + window.location.search;
    //로그인 버튼 클릭시 이동
    function goLoginPage() {
        location.href='/login?redirectURL=' + encodeURIComponent(redirectURL);
    }

    //로그아웃
    function logout() {
        const form = document.logoutForm;
        form.action = "/logout";
        form.method = "POST";
        form.submit();
    }
    $(document).ready(function(){
        $("input#redirectURL").val(encodeURIComponent(redirectURL));
    });

</script>

<script type="text/javascript" src="<%=ctxPath%>/resources/js/hello/header.js"></script>

<%-- navbar --%>
<nav bar class="mainNav_bar w-100">
	<%-- 슬라이드 메뉴 시작 --%>
	<div id="slide_mask"></div>
     <div id="slide_menu">
       <div id="menu_close"><i id="btn_menu_close" class="fas fa-light fa-x"></i></div>
       <ul id="menu_list">
         <li class="border-bottom"><a href="/boards?firstCategory=국비학원">국비학원</a></li>
         <li class="border-bottom"><a href="/boards?firstCategory=커뮤니티&secondCategory=자유">커뮤니티</a></li>
         <li class="border-bottom"><a href="/boards?firstCategory=공지사항">공지사항</a></li>

         <c:if test="${loginMember.userRole eq 'ADMIN'}">
         <li class="border-bottom"><a href="/admin">국모 관리</a></li>
         </c:if>

         <c:if test="${not empty loginMember}">
         <li class="border-bottom"><a href="/members/${loginMember.id}/my/info">내 정보</a></li>
         <li class="border-bottom"><a href="/members/${loginMember.id}/my/account">내 계정</a></li>
         <li class="border-bottom"><a href="/members/${loginMember.id}/my/activities">활동내역</a></li>
         </c:if>

         <c:if test="${empty loginMember}">
         <li class="border-bottom"><a href="#" onclick="goLoginPage()">로그인</a></li>
         </c:if>

         <c:if test="${not empty loginMember}">
         <li class="border-bottom"><a href="#" onclick="logout()" style="color:red;">로그아웃</a></li>
         </c:if>
       </ul>
     </div>
     <%--슬라이드 메뉴 끝 --%>
     
     
	<nav class="navbar navbar-expand-lg bg-white mainNav w-100">
		<%-- 로고 및 메뉴 영역 --%>
		<div class="main_left">
			<%-- Brand/logo --%>
			<a class="navbar-brand" href="/" style="margin-right:52px;">
			   <img src="<%=ctxPath%>/resources/images/mainLogo.png" style="width:150px; height:43.75px;">
			</a>

			<%-- Links --%>
			<nav>
				<ul class="mainCate">
					<li><a class="nav-link" href="/boards?firstCategory=국비학원">국비학원</a></li>
					<li><a class="nav-link" href="/boards?firstCategory=커뮤니티&secondCategory=자유">커뮤니티</a></li>
					<li><a class="nav-link" href="/boards?firstCategory=공지사항">공지사항</a></li>

					<%-- 관리자로 로그인 했을 경우 추가 메뉴 --%>
					<c:if test="${sessionScope.loginMember.userRole eq 'ADMIN'}">
						<li class="adminMenu">
							<a class="nav-link adminMenu" href="/admin">국모 관리</a>
						</li>
					</c:if>
				</ul>
			</nav>
		</div>

		<%-- login 메뉴 영역 --%>
		<div class="main_right">
			<%-- 슬라이드 메뉴 열기 버튼 --%>
			<div id="btn_slide_menu_open" class="px-3 py-2 border rounded">
				<i class="fa-solid fa-bars"></i>
			</div>
			
			<%-- 비로그인 시 --%>
			<c:if test="${empty loginMember}">
				<div class="non-login">
					<button type="button" class="btn_login" id="login" onclick="goLoginPage()">로그인</button>
					<button type="button" class="btn_regist" id="regist" onclick="location.href='/members/tos'">회원가입</button>
				</div>
			</c:if>
			
			<%-- 로그인 시 --%>
			<c:if test="${not empty loginMember}">
				<div id="start_login" class="login justify-content-between align-items-center">

					<%-- 프로필 drop --%>
					<div class="dropdown">
						<div class="dropbtn">
							<c:if test="${fn:substring(loginMember.profileImage,0,4) != 'http'}">
			                  <img src="<%=ctxPath%>/resources/images/${loginMember.profileImage}"/>
			                </c:if>
			                <c:if test="${fn:substring(loginMember.profileImage,0,4) == 'http'}">
			             	   <img src="${loginMember.profileImage}"/>
			                </c:if>
						</div>
						<div id="profile_dropContent" class="dropdown-content2">
							<div class="px-1 py-1">
								<a href="/members/${loginMember.id}/my/info"> <i class="fa-solid fa-gear"></i> 내 정보</a>
								<a href="/members/${loginMember.id}/my/account"> <i class="fa-solid fa-user"></i> 내 계정</a>
								<a href="/members/${loginMember.id}/my/activities"> <i class="fa-solid fa-gear"></i> 활동내역</a>
								<a href="#" onclick="logout()">로그아웃</a>
							</div>
						</div>
					</div>
				</div>
			</c:if>
		</div>
	</nav>
</nav>
<%-- end of navbar --%>

<%-- scrollTop button --%>
<div id='scroll-to-top'>
    <span class='fa-stack fa-lg'>
        <i class='fa fa-circle fa-stack-2x circle'></i>
        <i class='fa fa-angle-double-up fa-stack-1x fa-inverse up-arrow'></i>
    </span>
</div>
<%-- end of scrollTop button --%>

<form name="logoutForm">
    <input id="redirectURL" name="redirectURL" type="hidden" value=""/>
</form>
