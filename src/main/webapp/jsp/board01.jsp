<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.pcwk.ehr.board.BoardDao" %>
<%@ page import="com.pcwk.ehr.board.BoardDTO" %>

<%
    // 한 페이지에 보여질 게시글 수
    int pageSize = 10;

    // 현재 페이지 번호
    int currentPage = 1;
    String pageParam = request.getParameter("page");
    if (pageParam != null) {
        currentPage = Integer.parseInt(pageParam);
    }

    // 게시글 리스트를 가져오는 메서드 호출
    BoardDao dao = new BoardDao();
    String isAdmin = "Y"; // 관리자 여부 "Y"로 설정
    List<BoardDTO> boardList = dao.getAdminBoardList(isAdmin);

    // 전체 게시글 수
    int totalPosts = boardList.size();

    // 전체 페이지 수 계산
    int totalPages = (int) Math.ceil((double) totalPosts / pageSize);

    // 현재 페이지에 보여줄 게시글 범위 계산
    int startIdx = (currentPage - 1) * pageSize;
    int endIdx = Math.min(currentPage * pageSize, totalPosts);

    // 현재 페이지에서 출력할 게시글 번호 계산
    int idx = (currentPage - 1) * pageSize + 1;
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>공지행사</title>
    <link rel="stylesheet" href="/IKUZO/assest/css/bookbook.css">
    <link rel="stylesheet" href="/IKUZO/assest/css/book_board.css">
    <script src="/IKUZO/assest/js/jquery_3_7_1.js"></script>
</head>
<script>
$(document).ready(function() {
    $('.board-title').click(function(event) {
        event.preventDefault(); // 기본 링크 동작을 막음
        var seq = $(this).data('seq'); // 클릭한 게시글의 seq를 가져옴

        $.ajax({
            type: "POST", // POST로 변경
            url: "/IKUZO/ikuzo/board.ikuzo",
            async: true, // 비동기 통신
            dataType: "html",
            data: {
                "work_div": "boardDetail",
                "seq": seq // 클릭한 게시글의 seq를 전달
            },
             success: function(data) {
               console.log("success ");
                 // boardDetail2.jsp로 이동
                 window.location.href = "boardDetail.jsp";
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
</script>

<body>
<!-- header 시작  -->
<%@ include file="header.jsp" %>
<!-- header 끝  -->
<!-- container -->
<section class="container">
    <div class="inner-container">
        <div class="page-title-group">
            <h2 class="page-title">공지 &amp; 행사</h2>
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
                    <a href="${pageContext.request.contextPath}/jsp/board_reg.jsp">게시</a>
                </div>
                <div class="tab-list-cell">
                    <a href="#">공지사항</a>
                </div>
                <div class="tab-list-cell">
                    <a href="#">도서관 소식</a>
                </div>
            </div>
        </div>

        <table class="notice-board">
            <thead>
                <tr>
                    <th class="num">No.</th>
                    <th class="category">제목</th>
                    <th class="subject">내용</th>
                    <th class="writer">작성자</th>
                    <th class="date">작성일</th>
                    <th class="count">조회수</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    for (int i = startIdx; i < endIdx; i++) {
                        BoardDTO board = boardList.get(i);
                %>
                <tr>
                    <td class="num"><%= idx++ %></td>
                    <td class="category"><a href="#" class="board-title" data-seq="<%= board.getSeq() %>"><%= board.getTitle() %></a></td>
                    <td class="text-left subject"><%= board.getContents() %></td>
                    <td class="writer"><%= board.getRegId() %></td>
                    <td class="date"><%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(board.getRegDt()) %></td>
                    <td class="count"><%= board.getReadCnt() %></td>
                </tr>
                <% 
                    }
                %>
            </tbody>
        </table>

        <div class="paging-wrap">
            <div class="pagination">
                <%-- 이전 페이지 링크 --%>
                <a href="<%= currentPage > 1 ? "?page=" + (currentPage - 1) : "#" %>" class="prev page-item">이전</a>

                <%-- 페이지 링크 --%>
                <% for (int i = 1; i <= totalPages; i++) { %>
                    <a href="?page=<%= i %>" class="page-item <%= currentPage == i ? "current" : "" %>"><%= i %></a>
                <% } %>

                <%-- 다음 페이지 링크 --%>
                <a href="<%= currentPage < totalPages ? "?page=" + (currentPage + 1) : "#" %>" class="next page-item">다음</a>                 
            </div>
        </div>

    </div>
</section>
<!-- //container -->
<!-- footer 시작  -->
<%@ include file="footer.jsp" %>
<!-- footer 끝  -->
</body>
</html>
