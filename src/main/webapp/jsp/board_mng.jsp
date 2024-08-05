<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.pcwk.ehr.board.BoardDao" %>
<%@ page import="com.pcwk.ehr.board.BoardDTO" %>
<%@ include file="/jsp/common.jsp" %> 
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 편집</title>
    <script src="/IKUZO/assest/js/jquery_3_7_1.js"></script>
    <script src="${pageContext.request.contextPath}/asset/js/jquery_3_7_1.js"></script>
    <link rel="stylesheet" href="/IKUZO/assest/css/bookbook.css">
    <link rel="stylesheet" href="/IKUZO/assest/css/book_board.css">
    <script src="/IKUZO/assest/js/jquery_3_7_1.js"></script>
    
<style>
    /* 인풋 박스 윗 테두리 색상 설정 */
    .box {
        margin-top: 13px;
        background-color: #ffffff;
        padding: 18px;
        border: 1px solid #ccc;
        border-top: 3px solid #27403d; /* 윗 테두리 색상 및 굵기 설정 */
    }
    
     .mb-3{
        margin-bottom: 20px; /* 입력 폼 요소들 간의 간격 설정 */
    }
    
        .form-control {
        width: 100%; /* 입력 폼 요소들을 100% 너비로 설정 */
        padding: 10px; /* 내부 여백 설정 */
        font-size: 14px; /* 폰트 크기 설정 */
        border: 1px solid #ccc; /* 테두리 설정 */
        border-radius: 4px; /* 테두리의 꼭지점 둥글기 설정 */
    }
</style>    
</head>
<script>
$(document).ready(function() {
    // 수정 버튼 클릭 시 처리
    $('#doUpdate').click(function(event) {
        var updatedTitle = $('#title').val();
        var updatedContents = $('#contents').val();
        doUpdate(updatedTitle, updatedContents); // 수정 함수 호출
    });

    // 삭제 버튼 클릭 시 처리
    $('#doDelete').click(function(event) {
        doDelete(); // 삭제 함수 호출
    });

    // 목록 버튼 클릭 시 처리
    $('#moveToList').click(function(event) {
        moveToList(); // 목록으로 이동 함수 호출
    });
});

function moveToList() {
    console.log('moveToList()');
    alert("게시 목록으로 이동 합니다.");
    window.location.href = "/IKUZO/jsp/board01.jsp";
}

function doUpdate(updatedTitle, updatedContents) {
    console.log('doUpdate()');

    var seq = $('#seq1').text().trim(); // seq 값을 가져옵니다.

    // 필수 입력값 체크
    if (seq.length === 0) {
        alert('Seq를 확인 하세요.');
        return;
    }
    if (updatedTitle.trim() === '') {
        alert('제목을 확인 하세요.');
        return;
    }
    if (updatedContents.trim() === '') {
        alert('내용을 확인 하세요.');
        return;
    }

    $.ajax({
        type: "POST",
        url: "/IKUZO/ikuzo/board.ikuzo",
        async: true,
        dataType: "html",
        data: {
            "work_div": "doUpdate",
            "seq": seq,
            "title": updatedTitle,
            "contents": updatedContents
        },
        success: function(response) {
            console.log("success data:" + response);

            // 서버에서 받은 응답을 처리
            if (response) {
                try {
                    const messageVO = JSON.parse(response);
                    console.log("messageVO.messageId:" + messageVO.messageId);
                    console.log("messageVO.msgContents:" + messageVO.msgContents);

                    if (messageVO.messageId === "1") {
                        alert(messageVO.msgContents);
                        window.location.href = "/IKUZO/jsp/board01.jsp";
                    } else {
                        alert(messageVO.msgContents);
                    }

                } catch (e) {
                    console.error("JSON 파싱 에러:", e);
                }
            } else {
                console.warn("response가 null 혹은 undefined.");
                alert("서버로부터 응답을 받지 못했습니다.");
            }
        },
        error: function(data) {
            console.error("error:", data);
            alert("서버와의 통신 중 에러가 발생했습니다.");
        }
    });
}

function doDelete() {
    console.log('doDelete()');
    var seq = $('#seq1').text().trim(); // seq 값을 가져옵니다.

    // seq 값 체크
    if (seq.length === 0) {
        alert('Seq를 확인 하세요.');
        return;
    }

    if (!confirm('삭제 하시겠습니까?')) {
        return;
    }

    $.ajax({
        type: "GET",
        url: "/IKUZO/ikuzo/board.ikuzo",
        async: true,
        dataType: "html",
        data: {
            "work_div": "doDelete",
            "seq": seq
        },
        success: function(response) {
            console.log("success response:" + response);
            const messageVO = JSON.parse(response);
            console.log("messageVO.messageId:" + messageVO.messageId);
            console.log("messageVO.msgContents:" + messageVO.msgContents);

            if (messageVO.messageId === "1") {
                alert(messageVO.msgContents);
                window.location.href = "/IKUZO/jsp/board01.jsp";
            } else {
                alert(messageVO.msgContents);
            }
        },
        error: function(data) {
            console.log("error:" + data);
            alert("서버와의 통신 중 에러가 발생했습니다.");
        }
    });
}
</script>
<body>
<!-- header 시작  -->  
<%@ include file="header.jsp" %>
<!-- header 끝  -->

<!-- container -->
<div class="container">
    <section class="container">
        <div class="inner-container">
            <div class="page-title-group">
                <h2 class="page-title">게시글 편집</h2>
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
                        <a href="#" class="btn btn-primary" id="doUpdate">수정</a>
                    </div>
                    <div class="tab-list-cell">
                        <a href="#" class="btn btn-primary" id="doDelete">삭제</a>
                    </div>
                </div>
            </div>

            <!-- 새로운 박스 시작 -->
            <div class="box">
                <%
                    BoardDTO board = (BoardDTO) request.getAttribute("board");
                    if (board == null) {
                        board = (BoardDTO) session.getAttribute("boardDetail");
                    }
                    if (board != null) {
                        String title = board.getTitle();
                        String contents = board.getContents();
                        int seq = board.getSeq();
                %>
                <!-- form -->
                <form id="boardForm">
                     <div class="mb-3">
                        <label for="seq" class="form-label">고유번호:</label>
                        <span id="seq1"><%= seq %></span>
                    </div>
                    <div class="mb-3">
                        <label for="title" class="form-label"></label>
                        <input type="text" class="form-control" id="title" name="title" value="<%= title %>">
                    </div>
                    <div class="mb-3">
                        <label for="contents" class="form-label"></label>
                        <textarea class="form-control" id="contents" name="contents" rows="5"><%= contents %></textarea>
                    </div>
                </form>
                <% } %>
            </div>
            <!-- 새로운 박스 끝 -->

        </div>
    </section>
</div>
<!-- //container -->
<!-- footer 시작  -->
<%@ include file="footer.jsp" %>
<!-- footer 끝  -->

</body>
</html>
