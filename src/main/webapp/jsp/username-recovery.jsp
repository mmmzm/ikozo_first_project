<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>BOOKBOOK도서관 - 아이디찾기</title>
    <link rel="stylesheet" href="/IKUZO/assest/css/bookbook.css">
    <style>
        section {
            width: 100%;
            height: auto;
        }
        #container {
            padding: 100px;
        }
        .content-wrapper {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 80vh;
        }
        .content {
            width: 900px;
            border-top: 2px solid #000;
            border-left: 1px solid #e5e5e5;
            border-right: 1px solid #e5e5e5;
            border-bottom: 1px solid #000;
            padding: 20px 0;
            position: relative;
            text-align: center;
        }
        .content h2, .content h3 {
            line-height: 1.15;
            letter-spacing: -0.03em;
            text-align: left;
            position: absolute;
            left: 0px;
        }
        .content h2 {
            font-size: 40px;
            top: -122px;
            color: #27403d;
        }
        .content h3 {
            font-size: 16px;
            top: -52px;
            font-weight: normal;
        }
        .content form {
            margin-top: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .content form input[type="text"] {
            width: 400px;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
            background-color: #fff;
            box-sizing: border-box;
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
        .content form button:hover {
            background-color: #D88F2E;
        }
        .result {
            margin-top: 20px;
            font-size: 18px;
            color: #27403d;
        }
        
     
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        document.addEventListener("DOMContentLoaded", function(){
            console.log("Document Loaded");
            const saveBtn = document.querySelector("#saveBtn");
            saveBtn.addEventListener("click", function(event){
                event.preventDefault();
                ajaxFindUserId();
            });
        });

        function ajaxFindUserId(){
            if(!confirm('아이디를 찾으시겠습니까?')){
                return;
            }
            $.ajax({
                type: "POST",
                url: "/IKUZO2/jsp/findId.do",
                dataType: "json",
                data: {
                    "work_div": "findUserId",
                    "userName": document.querySelector("#userName").value,
                    "userTel": document.querySelector("#userTel").value
                },
                success: function(response){
                    if(response.userId){
                        document.querySelector(".result").innerHTML = "회원의 아이디는 " + response.userId + " 입니다.";
                    } else {
                        document.querySelector(".result").innerHTML = "해당 정보로 조회된 회원이 없습니다.";
                    }
                },
                error: function(){
                    alert("오류가 발생했습니다.");
                }
            });
        }
    </script>
</head>
<body>
    <!-- header 시작 -->
    <%@ include file="header.jsp" %>
    <!-- header 끝 -->
    <section id="container">
        <!-- 중앙 컨텐츠 영역 -->
        <div class="content-wrapper">
            <div class="content">
                <h2>아이디 찾기</h2>
                <h3>가입하신 정보를 이용하여 아이디를 확인하실 수 있습니다.</h3>
                <form>
                    <input type="text" id="userName" name="userName" placeholder="이름을 입력하세요"><br>
                    <input type="text" id="userTel" name="userTel" placeholder="전화번호를 입력하세요 (ex : 01012345678)"><br>
                    <button id="saveBtn">아이디 찾기</button>
                </form>
                <div class="result"></div> <!-- 결과를 표시할 요소 -->
            </div>
        </div>
    </section>
    <!-- footer 시작 -->
    <%@ include file="footer.jsp" %>
    <!-- footer 끝 -->
</body>
</html>
