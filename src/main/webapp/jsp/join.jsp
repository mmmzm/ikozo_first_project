<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/common.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="/IKUZO/assest/css/bookbook.css">
<title>BOOKBOOK도서관 - 회원가입</title>
<script src="/IKUZO/assest/js/jquery_3_7_1.js"></script>
<script src="/IKUZO/assest/js/common.js"></script>
<script>
document.addEventListener("DOMContentLoaded", function(){
  console.log('DOMContentLoaded');
  
  const doSaveBtn = document.querySelector("#doSave");
  const idCheckBtn = document.querySelector("#idCheck");
  
  //객체생성
  const workDiv = document.querySelector("#work_div");//작업구분
  const userName = document.querySelector("#user_name");
  const username_msg = document.querySelector("#username_msg");//이름입력조건확인 메세지
  const userId = document.querySelector("#user_id");
  const userid_msg = document.querySelector("#userid_msg");//아이디입력조건확인 메세지
  const idCheck_msg = document.querySelector("#idCheck_msg");//중복확인여부 메세지
  const regId = document.querySelector("#user_id");
  const modId = document.querySelector("#user_id");
  const userPw = document.querySelector("#user_pw");
  const userpw_msg = document.querySelector("#userpw_msg");//비번입력조건확인 메세지
  const reUserPw = document.querySelector("#re_user_pw");
  const reuserpw_msg = document.querySelector("#reuserpw_msg");//비밀번호재입력 메세지
  const userTel = document.querySelector("#user_tel");
  const usertel_msg = document.querySelector("#usertel_msg");//전화번호입력조건확인 메세지
  const userEmail = document.querySelector("#user_email");
  const useremail_msg = document.querySelector("#useremail_msg");//이메일입력조건확인 메세지
  
  const nameValidation = /^[가-힣]{2,10}$/;//이름 입력조건
  const idValidation = /^[a-zA-Z0-9_-]{4,12}$/;//아이디 입력조건
  const pwValidation = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{4,}$/;//비번 입력조건
  const telValidation = /^\d{3}\d{3,4}\d{4}$/;
  const emailValidation = /^[A-Za-z-0-9\-\.]+@[A-Ja-z-0-9\-\.]+\.[A-Ja-z-0-9]+$/;
  
  let isIdChecked = false;//중복확인 버튼을 눌렀는지 여부
  
   idCheckBtn.addEventListener("click", function(event){
     idCheck();
   });//--idCheckBtn.addEventListener end
   
   doSaveBtn.addEventListener("click", function(event){
     
     event.preventDefault(); // 폼 제출 막기
     if(validateForm()){
       doSave();
     }
   });//--doSaveBtn.addEventListener end
   
   userName.addEventListener('focusout',usernameCheck);
   userId.addEventListener('focusout', useridCheck);
   userPw.addEventListener('focusout',userpwCheck);
   reUserPw.addEventListener('focusout',reuserpwCheck);
   userTel.addEventListener('focusout',usertelCheck);
   userEmail.addEventListener('focusout',useremailCheck);
   
   function usernameCheck(){
     if(nameValidation.test(userName.value)){
       username_msg.innerHTML = '';
       username_msg.style.display='none';
     }else{
       username_msg.innerHTML = '2~10글자 이내 한글 이름을 입력해주세요.';
       username_msg.style.color='red';
       username_msg.style.display='block';
     }
   }
   
   function useridCheck(){
     if( idValidation.test(userId.value)){
       userid_msg.innerHTML = '';
       userid_msg.style.display='none';
     }else{
       userid_msg.innerHTML = '아이디 입력 조건에 맞지 않습니다. 다시 입력해주세요.';
       userid_msg.style.color='red';
       userid_msg.style.display='block';
     }
   }

   function userpwCheck(){
     if(pwValidation.test(userPw.value)){
    	 userpw_msg.innerHTML = '';
    	 userpw_msg.style.display='none';
     }else{
    	 userpw_msg.innerHTML = '비밀번호 입력 조건에 맞지 않습니다. 다시 입력해주세요.';
    	 userpw_msg.style.color='red';
    	 userpw_msg.style.display='block';
     }
   }
   
   function usertelCheck(){
	     if(telValidation.test(userTel.value)){
	    	 usertel_msg.innerHTML = '';
	    	 usertel_msg.style.display='none';
	     }else{
	    	 usertel_msg.innerHTML = '전화번호 입력 조건에 맞지 않습니다. 다시 입력해주세요.';
	    	 usertel_msg.style.color='red';
	    	 usertel_msg.style.display='block';
	     }
	   }

   function useremailCheck(){
       if(emailValidation.test(userEmail.value)){
    	   useremail_msg.innerHTML = '';
    	   useremail_msg.style.display='none';
       }else{
    	   useremail_msg.innerHTML = '이메일 입력 조건에 맞지 않습니다. 다시 입력해주세요.';
    	   useremail_msg.style.color='red';
    	   useremail_msg.style.display='block';
       }
     }
   
   function reuserpwCheck(){
	   if( isEmpty(reUserPw.value)==true){
		      reuserpw_msg.innerHTML = '비밀번호를 재입력하세요.'
		      reuserpw_msg.style.color='red';
		      reuserpw_msg.style.display='block';
	   }else{
         reuserpw_msg.innerHTML = '';
         reuserpw_msg.style.display='none';
         }
   }
   
  function validateForm(){
    //빈칸 입력시 재입력 안내창
    if( isEmpty(userName.value)==true){
        userName.focus();
          alert('이름을 입력하세요.');
          return false;
        }
    if( isEmpty(userId.value)==true){
        userId.focus();
          alert('아이디를 입력하세요.');
          return false;
        }
    
    
    if( isEmpty(userPw.value)==true){
        userPw.focus();
          alert('비밀번호를 입력하세요.');
          return false;
        }
    if( isEmpty(reUserPw.value)==true){
    	reUserPw.focus();
    	reuserpw_msg.innerHTML = '비밀번호를 재입력하세요.'
   		reuserpw_msg.style.color='red';
    	reuserpw_msg.style.display='block';
    	 if(isEmpty(reUserPw.value)==true){
    		 reuserpw_msg.innerHTML = '';
    		 reuserpw_msg.style.display='none';
 	       }
          return false;
        }
    if(reUserPw.value !== userPw.value){
      reUserPw.focus();
          alert('비밀번호가 일치하지 않습니다. 다시 입력하세요.');
          userPw.value='';
          reUserPw.value='';
          return false;
        }
    
    if( isEmpty(userTel.value)==true){
    	userTel.focus();
          alert('전화번호를 입력하세요.');
          return false;
        }
    if( isEmpty(userEmail.value)==true){
        userEmail.focus();
          alert('이메일을 입력하세요.');
          return false;
        }
    // 이용약관 동의 체크 여부 확인
    if(!checkbox.checked){
      alert('서비스 이용약관 및 개인정보취급방침에 동의해야 합니다.');
      return false;
    }
    //중복확인 버튼을 누르지 않았을 때 안내문구
    if(isIdChecked == false){
     idCheck_msg.innerHTML='중복확인이 필요합니다.';
     idCheck_msg.style.color='red';
     idCheck_msg.style.display = 'block';
     userId.focus();
     return false;
    }else if(isIdChecked == true){
	    //DML발생시 컨펌 => 확인:등록 / 취소:돌아가기
	     if(confirm('입력하신 정보로 가입하시겠습니까?') == false){
	       return false;
	     }
    }     
       return true;
     
   }//--function validateForm end
  
  function idCheck(){
    console.log('idCheck()');
    console.log(userId.value);
    workDiv.value = "idCheck"
    
    $.ajax({
            type: "POST", 
            url:"/IKUZO/ikuzo/join.ikuzo",/*컨트롤러 연결된 경로*/
            asyn:"true",
            dataType:"html",
            data:{
                "work_div":"idCheck",/*컨트롤러 안에 작업구분 이름*/
                "userId": userId.value
            },
            success:function(data){//통신 성공
                console.log("success data:"+data);
                const messageVO = JSON.parse(data);
                console.log("messageVO:"+messageVO.messageId);
                console.log("messageVO:"+messageVO.msgContents);
                
                if(messageVO.messageId==="1"){//중복아이디
                  idCheck_msg.innerHTML='아이디를 다시 입력해주세요.';
                  idCheck_msg.style.color='red';
                  idCheck_msg.style.display = 'block';
                  isIdChecked=true;
                  
                  alert(messageVO.msgContents);
                }else{//0: 사용가능아이디
                	idCheck_msg.innerHTML='중복확인이 되었습니다.';
                  idCheck_msg.style.color='black';
                  idCheck_msg.style.display = 'block';
                  isIdChecked=true;
                 
                 alert(messageVO.msgContents);
                }
                
            },
            error:function(data){//실패시 처리
                    console.log("error:"+data);
            }
          });//ajax end
    
   }//--function idCheck() end
   
  function doSave(){
    console.log("doSave Start now");
    console.log(userId.value);
    workDiv.value = "doSave"
    
     $.ajax({
          type: "POST", 
          url:"/IKUZO/ikuzo/join.ikuzo",/*컨트롤러 연결된 경로*/
          asyn:"true",
          dataType:"html",
          data:{
              "work_div":"doSave",/*컨트롤러 안에 작업구분 이름*/
              "userId": userId.value,
              "userName": userName.value,
              "userTel": userTel.value,
              "userPw": userPw.value,
              "userEmail": userEmail.value,
              "regId": regId.value,
              "modId": modId.value
          },
          success:function(response){//통신 성공
              console.log("success response:"+response);
              alert('회원가입이 완료되었습니다.');
              window.location.href="http://localhost:8080/IKUZO/jsp/index.jsp";
           
              if(response){
                  try{
                    const messageVO = JSON.parse(response);
                    console.log("messageVO:"+messageVO.messageId);
                    console.log("messageVO:"+messageVO.msgContents);
                    
                    if(messageVO.messageId==="1"){
                     alert(messageVO.msgContents);
                    }else{
                     alert(messageVO.msgContents);
                    }
                    
                    }catch(e){
                      console.error("JSON 파싱 에러:",e);
                    }
                  }else{
                    
                  }
          },
          error:function(data){//실패시 처리
                  console.log("error:"+data);
                  alert('');
          }
        });//ajax end      
     
   }//--function doSave
});//--DOMContentLoaded end

</script>
<style>
/*section*/
section{
  width : 100%;
  height:auto;
}

 .content-wrapper {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh; /* 화면 높이의 80%를 차지하도록 설정 */
        }

        .content {
            width: 900px;
            border-top: 2px solid #000;
            border-left: 1px solid #e5e5e5;
            border-right: 1px solid #e5e5e5;
            border-bottom: 1px solid #000;
            padding: 20px 0; /* 상하 간격을 조정하여 content가 정중앙에 위치하도록 함 */
            position: relative; /* h2와 h3의 위치를 content에 상대적으로 조정 */
            text-align: center; /* 입력 폼 중앙 정렬 */
        }

          .content h2, .content h3 {   
            line-height: 1.15;
            letter-spacing: -0.03em;
            text-align: left; /* 텍스트를 왼쪽 정렬 */
            position: absolute;
            left: 0px;
            
        }

        .content h2 {
            font-size: 40px;
            
            top: -122px;
            color:  #27403d; 
             
        }

        .content h3 {
            font-size: 16px;
            top: -52px;
            font-weight: normal;
           
        }

        .content form {
            margin-top: 20px; /* 입력 폼 상단 간격 조정 */
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .content form input[type="text"],
        .content form input[type="email"],
        .content form input[type="password"],
        .content form input[type="re_user_pw"],
        .content form input[type="tel"] {
            width: 300px;
            padding: 10px;
            margin-bottom: 0px; /* 간격을 조정하여 입력 폼들을 가깝게 배치 */
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
            background-color: #fff; /* 배경색 변경 */
            box-sizing: border-box;
        }

			 .content form input[type="button"] {
			 }
			 
        .content form input[type="submit"] {
            width: 150px;
            padding: 10px;
            background-color: #27403d; /* 배경색 변경 */
            color: #fff; /* 글씨 색상 변경 */
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            font-weight: bold;
        }

        .content form input[type="submit"]:hover {
            background-color: #D88F2E;  /* 호버 효과 */
        }
</style>
</head>
<body>

  <!-- header 시작  -->  
  <%@ include file="header.jsp" %>
  <!-- header 끝  -->
  
<section id = "container">
<div class="content-wrapper">
    <div class="content">
        <h2>회원가입</h2>
        <h3>BOOKBOOK도서관 계정 생성시 더 많은 서비스 이용이 가능합니다!</h3>
       <form>
        <input type="hidden" name="work_div" id="work_div">
            <input type="text" id="user_name" placeholder="이름을 입력하세요" required="required"><br>
            <p style="display:none;" id="username_msg"></p>
            <input type="text" id="user_id" placeholder="아이디를 입력하세요" required="required"><br>
            <p>4~12자 이내 영문 대/소문자/숫자를 이용해 아이디를 입력하세요.</p>
            <p style="display:none;" id="userid_msg"></p>
            <input type="button" id="idCheck" value="중복확인" class="btn btn-secondary"><br>
            <p style="display:none;" id="idCheck_msg"></p>
            <input type="password" id="user_pw" placeholder="비밀번호를 입력하세요" required="required"><br>
            <p>4자 이상 영문, 숫자, 특수문자(@$!%*#?&)를 포함하여 비밀번호를 입력하세요. </p>
            <p style="display:none;" id="userpw_msg"></p>
            <input type="password" id="re_user_pw" placeholder="비밀번호를 재입력하세요" required="required"><br>
            <p style="display:none;" id="reuserpw_msg"></p>
            <input type="tel" id="user_tel" placeholder="전화번호를 입력하세요" required="required"><br>
            <p>하이픈(-) 제외 숫자 10~11자로 입력하세요. 예)01012345678</p>
            <p style="display:none;" id="usertel_msg"></p>
            <input type="email" id="user_email" placeholder="이메일을 입력하세요" required="required"><br>
            <p>영문 대/소문자/숫자로 시작하고 @를 포함하여 입력하세요.</p>
            <p style="display:none;" id="useremail_msg"></p>
            <label>
                <input type="checkbox" id="checkbox" required> 서비스 이용약관 및 개인정보취급방침을 확인하였고, 이에 동의합니다.
            </label><br>
            <input type="submit" value="가입하기" id="doSave">
        </form>
    </div>
</div>
</section>

  <!-- footer 시작  -->
  <%@ include file="footer.jsp" %>
  <!-- footer 끝  -->

</body>
</html>