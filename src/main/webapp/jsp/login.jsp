<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BOOKBOOK도서관 - 로그인 하면 다양한 서비스를 이용할 수 있어요!</title>
<link rel="stylesheet" href="/IKUZO/assest/css/bookbook.css">
<style>
    body {
        font-family: Arial, sans-serif;
    }
    #container{
        padding: 120px;
        text-align: center;
    }
    .content-wrapper {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 60vh;
    }
    .content {
        text-align: center;
        padding: 0px 0px;
    }
    .login-box {
        padding: 20px;
        width: 300px;
        margin: 0 auto;
    }
    .login-box input[type="text"], .login-box input[type="password"] {
        width: calc(100% - 20px);
        padding: 10px;
        margin: 10px 0;
    }
    .login-box input[type="submit"] {
        width: 100%;
        padding: 10px;
        background-color: #27403d;
        color: white;
        border: none;
        cursor: pointer;
    }
    .options {
        margin-top: 20px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        position: relative;
        background-color: #f1f1f1;
        padding: 40px;
    }
    .option-box {
        text-align: center;
        width: 48%;
        background-color: transparent;
    }
    .option-box button, .option-box a {
        padding: 7px;
        border: 1px solid #ccc;
        cursor: pointer;
        width: 47%;
        box-sizing: border-box;
        background-color: white;
        color: #4e4e4e;
        text-decoration: none;
        display: inline-block;
        text-align: center;
    }
    .inline-buttons {
        display: flex;
        justify-content: space-between;
        width: 100%;
    }
    .options::before {
        content: '';
        position: absolute;
        left: 50%;
        top: 0;
        bottom: 0;
        width: 1px;
        background-color: #ccc;
        transform: translateX(-50%);
    }
    .highlight {
        font-size: 20px;
        color: green;
        font-weight: bold;
    }
    .content h2 {
        font-size: 40px;
        position: relative;
        top: -329px;
        color: #27403d;
    }
    .content h3 {
        font-size: 18px;
        position: relative;
        top: -329px;
        color: #777;
    }
    .options {
        background-color: F3E4C2;
        width: 800px;
    }
    
    .highlight {
        color: #27403d;
    }
    
     #loginBtn{
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

         #loginBtn:hover {
            background-color: #D88F2E;  /* 호버 효과 */
        }
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

    <script>
        $(document).ready(function() {
            $("#loginBtn").click(function(event) {
                event.preventDefault(); // 폼의 기본 동작을 막음
                var userId = $("#userId").val();
                var userPw = $("#userPw").val();

                
                $("#logoutBtn").click(function(event) {
                    event.preventDefault(); // 기본 동작 막기
                    logoutAndRedirect(); // 로그아웃 함수 호출
                });
                
                $.ajax({
                    type: "GET",
                    url: "/IKUZO/ikuzo/login.ikuzo", // 상대 경로로 설정
                    dataType: "text",
                    data: {
                        "work_div": "login",
                        "user_id": userId,
                        "user_pw": userPw
                    },
                    success: function(data) {
                        console.log("success data: " + data);

                        if (data.trim() === "성공") {
                            alert('로그인 성공! 환영합니다.');
                            window.location.href = "/IKUZO/jsp/index.jsp"; // 로그인 성공 후 이동할 페이지 경로 수정
                        } else {
                            alert('아이디와 비밀번호를 확인하세요.');
                        }
                    },
                    error: function(xhr, status, error) {
                        console.error("에러 발생: " + error);
                    }
                });
            });
        });

       
    </script>



<script>
//checkInitialLoginStatus 함수: 초기 로그인 상태를 확인하는 비동기 함수
function checkInitialLoginStatus() {
    return fetch('/checkInitialLoginStatus') // 서버에 초기 로그인 상태를 확인하는 요청을 보냄
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); // JSON 형태의 응답을 받음
        })
        .then(data => {
            return {
                isLoggedIn: data.isLoggedIn,
                isAdmin: data.isAdmin // 서버에서 받은 초기 로그인 상태와 관리자 여부 반환
            };
        })
        .catch(error => {
            console.error('Error during initial login status check:', error);
            return { isLoggedIn: false, isAdmin: false }; // 에러 발생 시 초기 상태를 false로 설정
        });
}

// initializeMenuBasedOnLoginStatus 함수: 페이지 로드 시 초기 로그인 상태에 따라 메뉴를 설정하는 함수
function initializeMenuBasedOnLoginStatus() {
    checkInitialLoginStatus().then(({ isLoggedIn, isAdmin }) => {
        if (isLoggedIn) {
            if (isAdmin) {
                showManageMenu(); // 초기 로그인 상태가 true이면서 관리자인 경우
            } else {
                showStartMenu(); // 초기 로그인 상태가 true이면서 일반 사용자인 경우
            }
        } else {
            showLoginMenu(); // 초기 로그인 상태가 false인 경우
        }
    });
}



// showStartMenu 함수: 시작 메뉴를 보여주는 함수
function showStartMenu() {
    var loginItem = document.getElementById("loginItem");
    var joinItem = document.getElementById("joinItem");
    var myPageItem = document.getElementById("myPageItem");
    var myPageUpdate = document.getElementById("myPageUpdate"); // 수정 필요
    var adminPageItem = document.getElementById("adminPageItem");
    var logoutItem = document.getElementById("logoutItem");

    if (loginItem) loginItem.style.display = "none";
    if (joinItem) joinItem.style.display = "none";
    if (myPageItem) myPageItem.style.display = "inline-block";
    if (myPageUpdate) myPageUpdate.style.display = "inline-block"; // 수정 필요
    if (adminPageItem) adminPageItem.style.display = "none";
    if (logoutItem) logoutItem.style.display = "inline-block";
}

// showLoginMenu 함수: 로그인 메뉴를 보여주는 함수
function showLoginMenu() {
    var loginItem = document.getElementById("loginItem");
    var joinItem = document.getElementById("joinItem");
    var myPageItem = document.getElementById("myPageItem");
    var myPageUpdate = document.getElementById("myPageUpdate"); // 수정 필요
    var adminPageItem = document.getElementById("adminPageItem");
    var logoutItem = document.getElementById("logoutItem");

    if (loginItem) loginItem.style.display = "inline-block";
    if (joinItem) joinItem.style.display = "inline-block";
    if (myPageItem) myPageItem.style.display = "none";
    if (myPageUpdate) myPageUpdate.style.display = "none"; // 수정 필요
    if (adminPageItem) adminPageItem.style.display = "inline-block";
    if (logoutItem) logoutItem.style.display = "none";
}

// showManageMenu 함수: 관리 메뉴를 보여주는 함수
function showManageMenu() {
	    var loginItem = document.getElementById("loginItem");
	    var joinItem = document.getElementById("joinItem");
	    var myPageItem = document.getElementById("myPageItem");
	    var adminPageItem = document.getElementById("adminPageItem");
	    var adminPageItem = document.getElementById("adminPageItem");
	    var logoutItem = document.getElementById("logoutItem");
    
    
    if (loginItem) loginItem.style.display = "none";
    if (joinItem) joinItem.style.display = "none";
    if (myPageItem) myPageItem.style.display = "none";
    if (myPageUpdate) myPageUpdate.style.display = "none";
    if (adminPageItem) adminPageItem.style.display = "inline-block";
    if (logoutItem) logoutItem.style.display = "inline-block";
}



function logoutAndRedirect() {
    // 현재 세션 삭제
    sessionStorage.removeItem("loggedIn");

    // 초기화면으로 리다이렉트
    window.location.href = "/index.jsp"; // 초기화면 URL에 맞게 수정
}

</script>


</head>
<body>

  <!-- header 시작  -->  
  <%@ include file="header.jsp" %>
  <!-- header 끝  -->
<section id="container">
<div class="content-wrapper">
    <div class="content">
        <div class="login-box">            
            <form id="login_form">
                <input type="text" id="userId" name="userId" placeholder="아이디를 입력하세요"><br>
                <input type="password" id="userPw" name="userPw" placeholder="비밀번호를 입력하세요"><br>
                <button type="button" id="loginBtn">로그인</button>
            </form>
        </div>
        <h2>BOOKBOOK도서관에 오신 것을 환영합니다!</h2>
        <h3>로그인하시면 다양한 서비스를 이용할 수 있습니다.</h3>
        <div class="options">
            <div class="option-box">
                <p class="highlight">아이디, 비밀번호를 잊어 버렸나요?</p>
                <p style="font-size: 16px; color: #979797;">지금 빠른 처리를 원하시면 <br/> BOOKBOOK 고객센터 02-111-1111로 전화주세요!</p>
                <div class="inline-buttons">
                    <a href="http://localhost:8080/IKUZO/jsp/username-recovery.jsp">아이디 찾기</a>
                    <a href="http://localhost:8080/IKUZO/jsp/password-reset.jsp">비밀번호 초기화</a>
                </div>
            </div>
            <div class="option-box">
                <p class="highlight">아직 회원이 아니신가요?</p>
                <p style="font-size: 16px; color: #979797;">회원가입을 통해 도서관을 경험할 수 있습니다.</p>

                <a href="http://localhost:8080/IKUZO/ikuzo/join.ikuzo?work_div=toJoin" class="join-button">가입하기</a>

            </div>
        </div>
    </div>
</div>
</section>
<!-- footer 시작  -->
<%@ include file="footer.jsp" %>
<!-- footer 끝  -->

</body>
</html>
