<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BOOKBOOK도서관 - 비밀번호재설정</title>
<link rel="stylesheet" href="/IKUZO/assest/css/bookbook.css">
<style>
/*section*/
section{
  width : 100%;
  height:auto;
}
#container{
padding : 100px;
}
 .content-wrapper {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 80vh; /* 화면 높이의 80%를 차지하도록 설정 */
        }

        .content {
            width: 900px;
            height:auto;
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
        .content form input[type="email"] {
            width: 300px;
            padding: 10px;
            margin-bottom: 0px; /* 간격을 조정하여 입력 폼들을 가깝게 배치 */
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
            background-color: #fff; /* 배경색 변경 */
            box-sizing: border-box;
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
        .content form button {
            width: 150px;
            padding: 10px;
            background-color: #27403d;
            color: #fff;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            font-weight: bold;
        }
        .result {
            margin-top: 20px;
            font-size: 18px;
            color: #27403d;
        }
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function(){
    console.log("Document Loaded");
    const resetBtn = document.querySelector("#resetBtn");
    resetBtn.addEventListener("click", function(event){
            event.preventDefault();
            ajaxResetPassword();
        });
    });

    function ajaxResetPassword() {
        if (!confirm('비밀번호를 재설정 하시겠습니까?')) {
            return;
        }

        var userId = $("#userId").val();       // userId 입력값 가져오기
        var userName = $("#userName").val();   // userName 입력값 가져오기
        var userTel = $("#userTel").val();     // userTel 입력값 가져오기

        $.ajax({
            type: "POST",
            url: "/IKUZO2/jsp/resetPw.do", // 서블릿 매핑 경로 확인 필요
            dataType: "json",
            data: {
                "work_div": "resetPassword",
                "userId": userId,
                "userName": userName,
                "userTel": userTel
            },
            success: function(response) {
                if (response.newPassword) {
                    $("#resetResult").text("회원의 새 비밀번호는 " + response.newPassword + " 입니다.");
                } else {
                    $("#resetResult").text("비밀번호 재설정에 실패했습니다.");
                }
            },
            error: function(xhr, status, error) {
                console.error("AJAX 오류 발생: " + error);
                alert("오류가 발생했습니다. 관리자에게 문의하세요.");
            }
        });
    }

</script>
</head>
<body>
    <!-- 헤더 부분 -->
    <%@ include file="header.jsp" %>
    
    <section>
        <div class="content-wrapper">
            <div class="content">
                <h2>비밀번호 초기화</h2>
                <h3>본인확인 완료 후 비밀번호를 초기화하세요.</h3>
                <form>
                    <input type="text" id="userId" placeholder="아이디를 입력하세요"><br>
                    <input type="text" id="userName" placeholder="이름을 입력하세요"><br>
                    <input type="text" id="userTel" placeholder="전화번호를 입력하세요"><br>
                    <button id="resetBtn">비밀번호 재설정</button>
                </form>
                <div id="resetResult" class="result"></div> <!-- 결과를 표시할 요소 -->
            </div>
        </div>
    </section>

    <!-- 푸터 부분 -->
    <%@ include file="footer.jsp" %>
</body>
</html>
