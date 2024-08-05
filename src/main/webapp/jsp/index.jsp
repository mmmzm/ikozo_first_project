<%@page import="com.pcwk.ehr.mainpage.MainPageDTO"%>
<%@page import="com.pcwk.ehr.mainpage.MainPageDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.pcwk.ehr.login.LoginDTO" %>
<%
MainPageDao dao = new MainPageDao();
   SearchDTO searchVO = new SearchDTO();
   searchVO.setPageNo(1);
   searchVO.setPageSize(10);

   List<MainPageDTO> listY = dao.doRetrieveAdimY(searchVO);
   for(MainPageDTO voY :listY) {
                System.out.println(voY);
            }
   List<MainPageDTO> listN = dao.doRetrieveAdimN(searchVO);
   for(MainPageDTO voN :listN) {
                System.out.println(voN);
            }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>북북 도서관</title>
<link rel="stylesheet" href="/IKUZO/assest/css/bookbook.css">
<link rel="stylesheet" href="/IKUZO/assest/css/index.css">
<script>
document.addEventListener("DOMContentLoaded", function(){	  
    function loginAndUpdateGreeting() {
        $.ajax({
            url: '<%= request.getContextPath() %>/login.do',
            type: 'POST',
            data: {
                work_div: 'login',
                user_id: $('#user_id').val(),
                user_pw: $('#user_pw').val()
            },
            success: function(response) {
                if(response === "성공") {
                    $.get('<%= request.getContextPath() %>/getUserName.do', function(data) {
                        $('#headerMenu').append('<li><p>' + data + '님 환영합니다</p></li>');
                    });
                } else {
                    alert(response);
                }
            },
            error: function(xhr, status, error) {
                console.error('로그인 실패:', error);
                alert('로그인 중 오류가 발생했습니다.');
            }
        });
    }
});		 
</script>

</head>
 
<body>

    <!-- header 시작  -->  
    <%@ include file="header.jsp" %>
    <!-- header 끝  -->
 
    
    <section id="section">
        <div id="container">
            <div id="sectionDiv">
                <div id="sectionBannerDiv">
                    <ul>
                       <a href="#"><img src = "/IKUZO/assest/img/BANNER.png"></a>
                    </ul>
                </div>
                <div id="sectionLoginDiv">
                    <h2>나만의 도서관</h2>
                    <%
                        LoginDTO user = (LoginDTO) session.getAttribute("user");
                        if (user != null) {
                    %>
                        <p id = "usernamePost"><%= user.getUserName() %>님 환영합니다</p>
                    <%
                        } else {
                    %>
                        <p id ="usernamePost" ><a id="loginBtn" href = "http://localhost:8080/IKUZO/ikuzo/login.ikuzo?work_div=moveToLogin">로그인이 필요합니다</a></p>
                    <%
                        }
                    %>
                </div>
            </div>
            
            <div class="boardGroup">
                <div id="noticeBoard1" class="listWrap">
                     <h2>공지사항</h2>
                    <ul>
                    <%
                    for(MainPageDTO voY :listY) {
                    %>
                        <li>
                            <a><%=voY.getTitle()%></a>
                            <span class="date"><%=voY.getModDt()%></span>
                        </li>
                    <%
                    }
                    %>  
                      
                    </ul>
                    <p class="more">
                        <a href="http://localhost:8080/IKUZO/jsp/board01.jsp">
                            <img src="/IKUZO/assest/img/iconMore.png">
                        </a>
                    </p>
                </div>
                
                <div id="noticeBoard2" class="listWrap">
                    <h2>소통마당</h2>
                    <ul>
                    <%
                    for(MainPageDTO voN :listN) {
                    %>
                        <li>
                            <a><%=voN.getTitle()%></a>
                            <span class="date"><%=voN.getModDt()%></span>
                        </li>
                    <% } %>  
                    </ul>
                    <p class="more2">
                        <a href="http://localhost:8080/IKUZO/jsp/board02.jsp">
                            <img src="/IKUZO/assest/img/iconMore.png">
                        </a>
                    </p>
                </div>
            </div>
            
            <div class="bookListWrap">
   <!--              <div id="bookShowList" >
                    <ul>
                        <li>
                            <a href="" class="cover">
                                <img src="" alt="" onerror="javascript:errorImageFile(this);">
                            </a>
                            <strong class="tit"></strong>
                        </li>
                        <li>
                            <a href="" class="cover">
                                <img src="" alt="" onerror="javascript:errorImageFile(this);">
                            </a>
                            <strong class="tit"></strong>
                        </li>
                        <li>
                            <a href="" class="cover">
                                <img src="" alt="" onerror="javascript:errorImageFile(this);">
                            </a>
                            <strong class="tit"></strong>
                        </li>
                    </ul>
                </div> -->
            </div>
        </div>
    </section>

    <!-- footer 시작  -->
    <%@ include file="footer.jsp" %>
    <!-- footer 끝  -->

</body>
</html>
