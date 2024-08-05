<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/common.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 작성</title>
    <link rel="stylesheet" href="/IKUZO/assest/css/bookbook.css">
    <link rel="stylesheet" href="/IKUZO/assest/css/book_board.css">
    <script src="/IKUZO/assest/js/jquery_3_7_1.js"></script>
    
<style>
    /* 입력 폼 스타일 */
    .form-group {
        margin-bottom: 20px; /* 입력 폼 요소들 간의 간격 설정 */
    }

    .form-control {
        width: 100%; /* 입력 폼 요소들을 100% 너비로 설정 */
        padding: 10px; /* 내부 여백 설정 */
        font-size: 14px; /* 폰트 크기 설정 */
        border: 1px solid #ccc; /* 테두리 설정 */
        border-radius: 4px; /* 테두리의 꼭지점 둥글기 설정 */
    }

    textarea.form-control {
        resize: vertical; /* 텍스트 영역의 크기 조절을 수직 방향으로만 허용 */
    }

    /* 입력 폼 박스 스타일 */
    .input-box {
        background-color: #f2f2f2;
        padding: 20px;
        border: 1px solid #ccc;
        border-radius: 4px;
    }

    /* 인풋 박스 윗 테두리 색상 설정 */
    .input-box {
        margin-top: 13px;
        background-color: #ffffff;
        padding: 18px;
        border: 1px solid #ccc;
        border-top: 3px solid #27403d; /* 윗 테두리 색상 및 굵기 설정 */
    }
</style>


    <script>
        document.addEventListener("DOMContentLoaded", function(){
            console.log('DOMContentLoaded ');

            const doSaveBtn = document.querySelector("#doSave");
            const moveToListBtn = document.querySelector("#moveToList");
            const workDiv = document.querySelector("#work_div"); // 작업구분
            const title = document.querySelector("#title"); // 제목
            const isAdmin = document.querySelector("#isAdmin"); // 관리자 여부
            const contents = document.querySelector("#contents"); // 내용

            doSaveBtn.addEventListener("click", function(){
                $.ajax({
                    type: "POST", 
                    url: "/IKUZO/ikuzo/board.ikuzo",
                    async: true, // 비동기 통신
                    dataType: "html",
                    data: {
                        "work_div": "ajaxDoSave",
                        "title": title.value,
                        "isAdmin": "N",
                        "contents": contents.value
                    },
                    success: function(data){ // 통신 성공
                        console.log("success data:" + data);
                        const messageVO = JSON.parse(data);
                        console.log("messageId:" + messageVO.messageId);
                        console.log("msgContents:" + messageVO.msgContents);

                        if(messageVO.messageId === "1"){
                            alert(messageVO.msgContents);
                            // 등록 성공 시 목록 페이지로 이동
                            window.location.href = "/IKUZO/jsp/board02.jsp";
                        } else {
                            alert(messageVO.msgContents);
                        }
                    },
                    error: function(data){ // 실패 시 처리
                        console.log("error:" + data);
                    }
                }); // ajax end
            });

            moveToListBtn.addEventListener("click", function(){
                // 목록 버튼 클릭 시 boardlist_01.jsp 페이지로 이동
                window.location.href = "/IKUZO/jsp/board01.jsp";
            });
        });   
    </script>
</head>
<body>
<!-- header 시작  -->  
<%@ include file="header.jsp" %>
<!-- header 끝  -->

<!-- container -->
<section class="container">
    <div class="inner-container">
        <div class="page-title-group">
            <h2 class="page-title">게시글 등록</h2>
        </div>
        <div class="page-list-group">
            <div class="page-list-inner">
                <div class="active">
                    <a href="board01.jsp">공지 &amp; 행사</a>
                </div>
                <div>
                    <a href="board02.jsp">소통마당</a>
                </div>
            </div>    
        </div>
        <div class="category-box">
            <div class="category-wrap">
                <div class="active">
                    <a href="#" class="btn btn-primary" id="moveToList">목록</a>
                </div>
                <div class="tab-list-cell">
                    <a href="#" class="btn btn-primary" id="doSave">등록</a>
                </div>
            </div>
        </div>

        <!-- 입력 폼 박스 -->
        <div class="input-box">
            <!-- 제목 입력 폼 -->
            <div class="form-group">
                <label for="title"></label>
                <input type="text" class="form-control" id="title" maxlength="75" required="required" placeholder="제목을 입력하세요">
            </div>
            
            <!-- 내용 입력 폼 -->
            <div class="form-group">
                <label for="contents"></label>
                <textarea class="form-control" rows="17" id="contents" name="contents" placeholder="내용을 입력하세요"></textarea>
            </div>
        </div>
        <!--// 입력 폼 박스 끝 -->
    </div>
</section>

<!-- footer 시작  -->
<%@ include file="footer.jsp" %>
<!-- footer 끝  -->

</body>
</html>
