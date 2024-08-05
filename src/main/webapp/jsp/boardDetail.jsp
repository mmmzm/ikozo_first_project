<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="com.pcwk.ehr.board.BoardDTO" %>
<%@ page import="com.pcwk.ehr.answer.AnswerDTO" %>
<%@ include file="/jsp/common.jsp" %> 
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 상세보기</title>
    <link rel="stylesheet" href="/IKUZO/assest/css/bookbook.css">
    <link rel="stylesheet" href="/IKUZO/assest/css/book_board.css">
    <script src="/IKUZO/assest/js/jquery_3_7_1.js"></script>
    <style>
        .info-box {
            margin-top: 13px;
            background-color: #ffffff;
            padding: 30px;
            border: 1px solid #ccc;
            border-top: 3px solid #27403d; /* 윗 테두리 색상 및 굵기 설정 */
        }

        .info-box table {
            width: 100%;
        }
        .info-box table td {
            width: 30%;
            padding-left: 10px;
        }
        
        .info-box table td.contents {
            padding-top: 10px;
        }

        .comment-box {
            border: 1px solid #ccc;
            padding: 10px;
            margin-top: 20px;
            border-top: 3px solid #27403d;
        }

        .comment-header h3 {
            margin: 0;
        }

        .comment-header button {
            margin-left: auto;
            float: right;
            background-color: #27403d; /* 배경색 변경 */
            color: #ffffff; /* 글씨색 변경 */
            margin: 5px 5px;
            padding: 7px 10px;
            overflow: hidden;
            border-radius: 5px;
            border: none; /* 기본 버튼 테두리 제거 */
            cursor: pointer; /* 포인터 커서로 변경 */
            font-size: 16px;
        }

        #commentForm textarea {
            width: 100%;
            min-height: 90px;
            resize: vertical;
            border: 1px solid #ccc;
        }
    </style>
</head>

<% 
    BoardDTO board = (BoardDTO) request.getAttribute("board");
    if (board == null) {
        board = (BoardDTO) session.getAttribute("boardDetail");
    }
    int boardSeq = (board != null) ? board.getSeq() : -1;
%>

<script>
$(document).ready(function() {
    // 페이지 로딩 시 댓글 목록 가져오기
    loadCommentList();

    // 댓글 작성 버튼 클릭 이벤트
    $('#submitComment').click(function(event) {
        event.preventDefault();

        var contents = $('#content').val();
        var regId = '<%= session.getAttribute("userId") %>'; // 세션에서 사용자 ID 가져오기
        var boardSeq = <%= board.getSeq() %>; // 현재 게시글의 고유번호
        
        // 댓글 내용이 비어 있는지 확인
        if (contents.trim() === '') {
            alert('등록 실패! 댓글 내용을 입력해주세요');
            return; // 댓글 등록 중단
        }

        // 댓글 작성 요청
        $.ajax({
            type: "POST",
            url: "/IKUZO/ikuzo/answer.ikuzo",
            dataType: "json",
            data: {
                "work_div": "createAnswer",
                "contents": contents,
                "regId": regId,
                "boardSeq": boardSeq
            },
            success: function(data) {
                console.log("댓글 등록 성공: ", data);
                // 댓글 등록 후, 목록 다시 불러오기
                loadCommentList();
                // 댓글 입력 폼 초기화
                $('#content').val('');
                
                alert('댓글 등록 성공');
            
            },
            error: function(xhr, status, error) {
                console.log('댓글 등록 실패');
                console.log("상태: " + status);
                console.log("에러: " + error);
                console.log("응답 텍스트: " + xhr.responseText);
            }
        });
    });

    $('.edit-button').click(function(event) {
        event.preventDefault(); // 기본 링크 동작을 막음
        var seq = $(this).data('seq'); // 클릭한 게시글의 seq를 가져옴

        $.ajax({
            type: "POST",
            url: "/IKUZO/ikuzo/board.ikuzo",
            async: true,
            dataType: "html",
            data: {
                "work_div": "viewBoardDetail",
                "seq": seq // 클릭한 게시글의 seq를 전달
            },
            success: function(data) {
                console.log("Retrieved data: ", data); // 데이터 확인 로그
                localStorage.setItem("boardData", JSON.stringify(data));
                window.location.href = "board_mng.jsp";
            },
            error: function(xhr, status, error) {
                console.log('데이터를 불러오는 중 에러가 발생했습니다.');
                console.log("상태: " + status);
                console.log("에러: " + error);
                console.log("응답 텍스트: " + xhr.responseText);
            }
        });
    });
});

function loadCommentList() {
    var boardSeq = <%= board.getSeq() %>; // 현재 게시글의 고유번호
    if (isNaN(boardSeq) || boardSeq === -1) {
        console.error('boardSeq 값이 유효하지 않습니다.');
        return;
    }

    $.ajax({
        type: "GET",
        url: "/IKUZO/ikuzo/answer.ikuzo",
        dataType: "json",
        data: {
            action: "retrieveAnswers",
            boardSeq: boardSeq, // 현재 게시글의 고유번호
            work_div: "listAnswers"
        },
        success: function(data) {
            console.log("Retrieved comment list: ", data);

            // 댓글 목록을 동적으로 추가
            var commentList = $('#commentList');
            commentList.empty(); // 기존 목록 초기화

            if (data.length === 0) {
                console.log('댓글이 없습니다.');
                var listItem = $('<li>').text('댓글이 없습니다.');
                commentList.append(listItem);
            } else {
                $.each(data, function(index, answer) {
                    // 댓글 데이터를 HTML 형식으로 생성하여 추가
                    var listItem = $('<li>').html(
                        '<div class="comment">' +
                        '<div class="author"><strong>' + answer.regId + '</strong> ' + answer.regDt + '</div>' +
                        '<div class="content">' + answer.contents + '</div>' +
                        '</div>'
                    );
                    commentList.append(listItem);
                });
            }
        },
        error: function(xhr, status, error) {
            console.log('댓글 목록을 불러오는 중 에러가 발생했습니다.');
            console.log("상태: " + status);
            console.log("에러: " + error);
            console.log("응답 텍스트: " + xhr.responseText);
        }
    });
}
</script>
<body>
<!-- header 시작  -->  
<%@ include file="header.jsp" %>
<!-- header 끝  -->

<!-- container -->
<section class="container">
    <div class="inner-container">
        <div class="page-title-group">
            <h2 class="page-title"></h2>
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
                    <a href="board01.jsp" class="btn">목록</a>
                </div>
                <div class="tab-list-cell">
                    <a href="#" class="edit-button" data-seq="<%= board.getSeq() %>">편집</a>
                </div>
            </div>
        </div>

        <!-- 세션에서 BoardDTO 객체 가져오기 -->
        <% 
            // 이미 스크립틀릿 코드 상단에서 board 객체를 가져왔음
            if (board != null) {
        %>
        <div class="info-box">
<table>
    <tr>
        <th style="font-size: 40px; text-align: left; padding: 5px;"><%= board.getTitle() %></th>
        <td colspan="10" style="text-align: right;"><%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(board.getRegDt()) %></td>
    </tr>

    <tr>
        <td style="font-size: 20px;"><%= board.getRegId() %></td>
        <td colspan="8"></td>
        <td style="text-align: right;"><%= "조회: " + board.getReadCnt() %></td>
    </tr>
    <tr>
        <td colspan="9"></td>
        <td style="text-align: right;">고유번호: <%= board.getSeq() %></td>
    </tr>
    <tr>
    <td colspan="10" style="padding: 10px 0;">
        <!-- 선을 넣을 영역 -->
        <div style="border-bottom: 1px solid #ccc;"></div>
    </td>
  </tr>
    <tr>
        <td style="font-size: 20px; text-align: left; padding: 5px;"><%= board.getContents() %></td>

    </tr>
</table>


 
        </div>
        <% } else { %>
        <div>게시글 데이터를 불러올 수 없습니다.</div>
        <% } %>    

<!-- 댓글 부분 -->
<div class="comment-box">
    <div class="comment-header">
        <div style="display: flex; align-items: center;">
            <h3 style="margin: 0;">comment</h3>
            <button type="button" id="submitComment" style="margin-left: auto;">등록</button>
        </div>
    </div>
    <div id="commentForm">
        <div>
            <label for="content"></label>
            <textarea id="content" name="content" rows="4" required placeholder="comment를 남겨주세요"></textarea>
        </div>
    </div>

    <div id="answer-list">
        <ul id="commentList">
            <!-- 댓글 목록 출력 -->
            <c:forEach var="answer" items="${answerList}">
                <li>
                    ${answer.regId}:
                    ${answer.contents}<br>
                    <small>${answer.regDt}</small>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
<!-- // 댓글 부분 -->

    </div>
</section>

<!-- // container -->

<!-- footer 시작  -->
<%@ include file="footer.jsp" %>
<!-- footer 끝  -->

</body>
</html>
