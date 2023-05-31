<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

  <%-- 구글 로그인 --%>
  <script src="https://accounts.google.com/gsi/client" async defer></script>
  <%-- 카카오 로그인 --%>
  <script type="text/javascript" src="https://developers.kakao.com/sdk/js/kakao.min.js" charset="utf-8"></script>
  <%-- 페이스북 로그인 --%>
  <script async defer crossorigin="anonymous" src="https://connect.facebook.net/ko_KR/sdk.js#xfbml=1&version=v10.0&appId=${facebookLoginAppId}" nonce="SiOBIhLG"></script>
  <%-- 직접 만든 CSS --%>
  <link rel="stylesheet" type="text/css" href="resources/css/login/login.css" />

  <script type="text/javascript">
    window.onload = function () {
        google.accounts.id.initialize({
          client_id: "${googleLoginClient}",
          callback: handleCredentialResponse
        });
       // google.accounts.id.prompt();
       // One Tap 기능을 사용하지 않기 때문에 주석처리하였다.
    };
    $(document).ready(function(){
      // 카카오 로그인 초기화
      Kakao.init("${kakaoLoginJavascriptKey}");
    });

    // 페이스북 로그인
    //기존 로그인 상태를 가져오기 위해 Facebook에 대한 호출
    function statusChangeCallback(res){
    	statusChangeCallback(response);
    }

    function fnFbCustomLogin(){
    	FB.login(function(response) {
    		if (response.status === 'connected') {
    			FB.api('/me', 'get', {fields: 'id,name,email,picture'},
    			function(response) {
    				let fb_data = jQuery.parseJSON(JSON.stringify(response));

    				const userInfo = {authId:fb_data.id
    								 ,email:fb_data.email
    								 ,profileImage:fb_data.picture.data.url
    			  		 			 ,username:fb_data.name
    			  		 			 ,redirectURL:$("input#returnURL").val()
    			  		 			 ,type:'FACEBOOK'};
    				//페이스북 로그인 처리 메소드 호출
    				facebookLoginPro(userInfo);
    			})
    		} else if (response.status === 'not_authorized') {
    			// 사람은 Facebook에 로그인했지만 앱에는 로그인하지 않았습니다.
    			alert('앱에 로그인해야 이용가능한 기능입니다.');
    		} else {
    			// 그 사람은 Facebook에 로그인하지 않았으므로이 앱에 로그인했는지 여부는 확실하지 않습니다.
    			alert('페이스북에 로그인해야 이용가능한 기능입니다.');
    		}
    	}, {scope: 'public_profile,email'});
    }

    window.fbAsyncInit = function() {
    	FB.init({
    		appId      : '${facebookLoginAppId}', // 내 앱 ID를 입력한다.
    		cookie     : true,
    		xfbml      : true,
    		version    : 'v10.0'
    	});
    	FB.AppEvents.logPageView();
    };
  </script>


  <%-- 직접만든 javascript --%>
  <script type="text/javascript" src="resources/js/login/loginForm.js" ></script>

  <form name="loginForm" id="loginForm">
    <div id="login" class="d-flex flex-column mx-auto my-5">
      <div id="logo_img_box" class="m-auto" style="cursor:pointer;" onclick="location.href='/'">
        <img id="logo" src="resources/images/logo.png" class="rounded">
      </div>

      <div id="about_us" class="my-3">
        <h2 class="text-center">국비의모든것에 오신것을 <br>환영합니다.</h2>
        <p id="about_content" class="text-center">국비의모든것은 소프트웨어 개발자를 꿈꾸는 비전공자들을 위한 <br/>국비지원 학원 정보공유 플랫폼입니다.</p>
      </div>
    
      <%-- sns_login --%>
      <span>SNS로그인</span>
      <div id="sns_login" class="d-flex mt-2">
        <%-- kakao --%>
        <div id="kakao_login" class="sns_login d-flex justify-content-center align-items-center mr-3">
          <img src="resources/images/login/카카오로그인.PNG" class="rounded">
        </div>
      
        <%-- naver --%>
        <div id="naver_login" class="sns_login d-flex justify-content-center align-items-center mr-3">
          <img src="resources/images/login/네이버로그인.png">
        </div>
        

        <%-- facebook --%>
        <div id="facebook_login" class="sns_login d-flex justify-content-center align-items-center mr-3" onclick="fnFbCustomLogin();">
          <img src="resources/images/login/페이스북로그인.svg">
        </div>

        <%-- google --%>
        <div id="google_login" class="sns_login d-flex justify-content-center align-items-center mr-3">
          <div id="g_id_onload"
           data-client_id="1009243602481-q3hk5769gab0ucfqbsf3r1abj4cg8av5.apps.googleusercontent.com"
           data-callback="handleCredentialResponse">
          </div>
          <div class="g_id_signin"
             data-type="icon"
             data-size="large"
             data-logo_alignment="left">
          </div>
        </div>
      </div>


      <%-- login_input --%>
      <div class="line my-4">
        	국비의모든것 아이디로 로그인
      </div>

      <%-- Id --%>
      <div id="input_id" class="d-flex flex-column">
        <label for="userid">아이디</label>
        <input type="text" id="userId" name="userId" class="input_login border rounded pl-2" placeholder="아이디를 입력해주세요"/>
        <span id="userId_error" class="login_error pl-1 mt-2">아이디를 입력해주세요.</span>
      </div>

      <%-- Password --%>
      <div id="input_password" class="d-flex flex-column mt-3">
        <label for="passwd">비밀번호</label>
        <input type="password" id="password" name="password" class="input_login border rounded pl-2" placeholder="비밀번호를 입력해주세요"/>
        <span id="passwd_error" class="login_error pl-1 mt-2">비밀번호를 입력해주세요.</span>
      </div>

      <%-- btn_login --%>
      <button id="btn_login" type="button" class="btn border rounded mt-4">로그인</button>
      <div class="mx-auto my-4">아직 회원이 아니신가요?&nbsp;<span id="btn_signup">회원가입</span></div>
    </div>

    <%-- redirectURL --%>
    <input type="hidden" id="returnURL" name="redirectURL" value="${redirectURL}"/>
  </form>
