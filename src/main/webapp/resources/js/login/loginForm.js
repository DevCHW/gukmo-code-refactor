


$(document).ready(function(){
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
	
	//회원가입버튼클릭시 회원가입 페이지로 이동
	$("span#btn_signup").click(()=>{
		location.href = '/members/tos';
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
		  $("span#passwd_error").text("비밀번호를 입력해주세요");
		  $("span#passwd_error").css("display","block");
		  return;
	  } else {	//아이디도 입력하고 비밀번호도 입력했다면
		  $("span#userId_error").text("");	//아이디 에러 지우기
		  $("span#passwd_error").text("");	//비밀번호 에러 지우기
		  $("span#userId_error").css("display","none");
		  $("span#passwd_error").css("display","none");
          const form = document.loginForm;
          form.method="POST";
          form.action= "/login"
          form.submit();
      }
    });

    // 카카오로그인 버튼 클릭시 이벤트
    $("div#kakao_login").click(()=>{
        kakaoLogin();
    });//end of Event--

    // 네이버로그인 버튼 클릭시 이벤트
    $("div#naver_login").click(()=>{
        const url = viewNaverLoginFrm();
        location.href = url;
    });//end of Event--
});//end of $(document).ready(function(){})

//Function Declaration

/**
 * 카카오로그인 객체 보내기
 * @param response
 * @returns
 */
function kakaoLoginPro(response){
  const userInfo = {authId:response.id,
                    email:response.kakao_account.email,
                    profileImage:response.properties.profile_image,
		  		    username:response.properties.nickname,
		  		    redirectURL:$("input#returnURL").val(),
		  		    type:'KAKAO'}
  $.ajax({
	type : 'POST',
	url : '/api/v1/oauth/kakao',
	data : userInfo,
	dataType : 'json',
	success : function(res){
		if(res.success){
			location.href = res.result.redirectURL;
		} else {
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
 * 네이버 로그인 폼 띄우기
 * @return 네이버로그인 폼 URL 을 반환한다.
 */
function viewNaverLoginFrm(){
	let url="/login";
	const redirectURL = {redirectURL:$("input#returnURL").val()};
	$.ajax({
		type : 'get',
		url : '/api/v1/oauth/naver/url',
		data : redirectURL,
		dataType : 'json',
		async:false,
		success : function(res){
			url = res.result;
		},
		error: function(status, error){
			alert("현재 네이버로그인이 불안정한 상태입니다. 다시 시도해주세요"+error);
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
    
    const userInfo = {authId:responsePayload.sub,
    				  email:responsePayload.email,
    				  profileImage:responsePayload.picture,
    				  username:responsePayload.name,
    				  redirectURL:$("input#returnURL").val(),
    				  type:'GOOGLE'}
    $.ajax({
		type : 'post',
		url : '/api/v1/oauth/google',
		data : userInfo,
		dataType : 'json',
		async:false,
		success : function(res){
		  if(res.success){
              location.href = res.result.redirectURL;
          } else {
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






/**
 * 페이스북로그인 유저정보 보내기
 * @param userInfo
 * @returns
 */
function facebookLoginPro(userInfo){
  $.ajax({
	type : 'POST',
	url : '/api/v1/oauth/facebook',
	data : userInfo,
	dataType : 'json',
	success : function(res){
		if(res.success){
            location.href = res.result.redirectURL;
        } else {
            alert("로그인에 실패했습니다");
        }
	},
	error: function(xhr, status, error){
		alert("로그인에 실패했습니다."+error);
	}
  });//end of ajax
}//end of method---
