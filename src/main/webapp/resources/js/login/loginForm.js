
window.onload = function () {
    google.accounts.id.initialize({
      client_id: "1009243602481-q3hk5769gab0ucfqbsf3r1abj4cg8av5.apps.googleusercontent.com",
      callback: handleCredentialResponse
    });
//    google.accounts.id.prompt();
   // One Tap 기능을 사용하지 않기 때문에 주석처리하였다.
  };

$(document).ready(function(){
	// 카카오 로그인 초기화
	Kakao.init("7643955cb5fbab2b0481e4b321cff247");
	
	//아이디에서 엔터입력이벤트
	$("input#userId").keydown(function(e){	//아이디에서 값 입력시 이벤트
	  if(e.keyCode == 13){	//엔터를 했1을 경우
		$("button#btn_login").trigger("click");  
	  }
	});
	
	//패스워드에서 엔터입력이벤트
	$("input#password").keydown(function(e){
	  if(e.keyCode == 13){	//엔터를 했을 경우
	    $("button#btn_login").trigger("click");  
	  }
	});
	
	//회원가입버튼클릭시
	$("span#btn_signup").click(()=>{
		location.href = getContextPath()+'/TOS.do';
	});

	//로그인버튼 클릭시 이벤트
	$("button#btn_login").click(function(){
	  const userId = $("input#userId").val().trim();
	  const password = $("input#password").val().trim();
	  if(userId == ""){	//아이디 값이 비어있다면
		  $("span#password_error").css("display","none");
		  $("span#userId_error").text("아이디를 입력해주세요");
	      $("span#userId_error").css("display","block");
	      return;
	  } else if(password == ""){	//비밀번호 값이 비어있다면
		  $("span#userId_error").css("display","none");
		  $("span#password_error").text("비밀번호를 입력해주세요");
		  $("span#password_error").css("display","block");
		  return;
	  } else {	//아이디도 입력하고 비밀번호도 입력했다면
		  $("span#userId_error").text("");	//아이디 에러 지우기
		  $("span#password_error").text("");	//비밀번호 에러 지우기
		  $("span#userId_error").css("display","none");
		  $("span#password_error").css("display","none");
          const form = document.loginForm;
          form.method="POST";
          form.action= "/login"
          form.submit();
      }
    });
});//end of $(document).ready(function(){})

//Function Declaration

/**
 * 로그인완료처리하기
 */
function login(userId){
	$("input#userId").val(userId);
	const frm = document.login_form;
	frm.action = "/login";
	frm.method = "POST";
	frm.submit();
}//end of method--



/**
 * 카카오로그인 객체 보내기
 * @param response
 * @returns
 */
function kakaoLoginPro(response){
  const userInfo = {userId:response.id,email:response.kakao_account.email,profile_image:response.properties.profile_image,nickname:response.properties.nickname,email_acept:'0'
		  		 ,username:response.properties.nickname,flag:'kakao'}
  $.ajax({
	type : 'POST',
	url : getContextPath()+'/kakaoLoginPro.do',
	data : userInfo,
	dataType : 'json',
	success : function(data){
		console.log(data)
		if(data.JavaData == "YES"){
			user_status(data.userId);
		}else if(data.JavaData == "register"){// 회원가입을 해야하는경우
			userSnsRegisterPro(userInfo);	//소셜로그인 회원가입 메소드
		}else{
			alert("로그인에 실패했습니다");
		}
		
	},
	error: function(xhr, status, error){
		alert("로그인에 실패했습니다."+error);
	}
  });//end of ajax
}//end of method---

/**
 * 카카오로그인창 띄우기
 */
function kakaoLogin(){
	Kakao.Auth.login({
		success: function (response) {
		Kakao.API.request({
			url: '/v2/user/me',
			success: function (response) {
				console.log(response);
				kakaoLoginPro(response);
			},
			fail: function (error) {
				console.log(error)
			},
		})
	},
		fail: function (error) {
			console.log(error)
		},
	});
}//end of method--

/**
 * 소셜로그인 회원가입시키기
 */
function userSnsRegisterPro(userInfo){
  $.ajax({
	type : 'POST',
	url : getContextPath()+'/userSnsRegisterPro.do',
	data : userInfo,
	dataType : 'json',
	success : function(data){
		console.log(data)
		if(data.JavaData == "YES"){
			user_status(data.userId);
		}else{
			alert("로그인에 실패했습니다");
		}
		
	},
	error: function(xhr, status, error){
		alert("로그인에 실패했습니다."+error);
	}
  });//end of ajax
}//end of method---



/**
 * 네이버 로그인 폼 띄우기
 * @return 네이버로그인 폼 URL 을 반환한다.
 */
function viewNaverLoginFrm(){
	let url=getContextPath()+"/login.do";
	$.ajax({
		type : 'get',
		url : getContextPath()+'/naverLogin.do',
		dataType : 'json',
		async:false,
		success : function(json){
			url = json.naverUrl;
		},
		error: function(status, error){
			alert("네이버로그인이 현재 불안정한 상태입니다. 다시 시도해주세요"+error);
		}
	  });//end of ajax
	return url;
}//end of method--




//구글로그인 핸들러
function handleCredentialResponse(response) {
	const responsePayload = parseJwt(response.credential);
//	  console.log("ID: " + responsePayload.sub);
//    console.log('Full Name: ' + responsePayload.name);
//    console.log('Given Name: ' + responsePayload.given_name);
//    console.log('Family Name: ' + responsePayload.family_name);
//    console.log("Image URL: " + responsePayload.picture);
//    console.log("Email: " + responsePayload.email);
    
    const userInfo = {userId:responsePayload.sub,
    				  email:responsePayload.email,
    				  profile_image:responsePayload.picture,
    				  nickname:responsePayload.given_name,
    				  email_acept:'0',
    				  username:responsePayload.name,
    				  flag:'google'}
    $.ajax({
		type : 'post',
		url : getContextPath()+'/googleLoginPro.do',
		data : userInfo,
		dataType : 'json',
		async:false,
		success : function(data){
		  console.log(data)
		  if(data.JavaData == "YES"){
			 user_status(data.userId);
		  }else if(data.JavaData == "register"){// 회원가입을 해야하는경우
			 userSnsRegisterPro(userInfo);	//소셜로그인 회원가입 메소드
		  }else{
		    alert("로그인에 실패했습니다");
		  }
		},
		error: function(xhr, status, error){
			alert("로그인에 실패했습니다."+error);
		}
	});//end of ajax
}//end of method--



/**
 * 구글로그인 유저정보JWT 파싱하기
 * @param token
 */
function parseJwt (token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
};


// 페이스북 로그인

//기존 로그인 상태를 가져오기 위해 Facebook에 대한 호출
function statusChangeCallback(res){
	statusChangeCallback(response);
}

function fnFbCustomLogin(){
	FB.login(function(response) {
		if (response.status === 'connected') {
			console.log(response);
			FB.api('/me', 'get', {fields: 'name,email,picture'}, function(response) {
				let fb_data = jQuery.parseJSON(JSON.stringify(response));
				
				//가져온 데이터 콘솔출력
//				console.log(fb_data);
				
				const userInfo = {userId:fb_data.id
								 ,email:fb_data.email
								 ,profile_image:fb_data.picture.data.url
								 ,nickname:fb_data.name
								 ,email_acept:'0'
			  		 			 ,username:fb_data.name
			  		 			 ,flag:'facebook'};
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
		appId      : '5826169730780578', // 내 앱 ID를 입력한다.
		cookie     : true,
		xfbml      : true,
		version    : 'v10.0'
	});
	FB.AppEvents.logPageView();   
};



/**
 * 페이스북로그인 유저정보 보내기
 * @param userInfo
 * @returns
 */
function facebookLoginPro(userInfo){
  $.ajax({
	type : 'POST',
	url : getContextPath()+'/facebookLoginPro.do',
	data : userInfo,
	dataType : 'json',
	success : function(data){
		console.log(data)
		if(data.JavaData == "YES"){
			user_status(data.userId);	//로그인처리
		}else if(data.JavaData == "register"){// 회원가입을 해야하는경우
			userSnsRegisterPro(userInfo);	//소셜로그인 회원가입 메소드
		}else{
			alert("로그인에 실패했습니다");
		}
		
	},
	error: function(xhr, status, error){
		alert("로그인에 실패했습니다."+error);
	}
  });//end of ajax
}//end of method---
