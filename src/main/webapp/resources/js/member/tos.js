

$(document).ready(function(){
  /**
   * 일반회원가입 클릭시
   */
  $("div#memberSignup").click(()=>{
    let check_length = $("input:checkbox[name='agreement']:checked").length;
    if(check_length < 2){
      alert("이용약관에 동의하여주세요");
      return;
    }
    else if(check_length == 2){
      location.href = "/members/signUp";
    }
  });


  /**
   * 교육기관회원가입 클릭시
   */
  $("div#acaMemberSignup").click(()=>{
    let check_length = $("input:checkbox[name='agreement']:checked").length;
    if(check_length < 2){
      alert("이용약관에 동의하여주세요");
      return;
    }
    else if(check_length == 2){
      location.href = "/members/signUp/academyMember";
    }
  });
});