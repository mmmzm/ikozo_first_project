<%@page import="com.pcwk.ehr.booklist.BookDTO"%>
<%@page import="java.util.List" %>
<%@page import="com.pcwk.ehr.cmn.SearchDTO"%>
<%@page import="com.pcwk.ehr.cmn.StringUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%> 
<%-- <%
  List<BookDTO> list  = (List<BookDTO>)request.getAttribute("list");
  SearchDTO searchCon = (SearchDTO)request.getAttribute("vo");
%>   --%>
<script>
document.addEventListener("DOMContentLoaded", function(){

  console.log("DOMContentLoaded()");

	const bookListBtn = document.querySelector("#bookList");
	
	const communityBtn = document.querySelector("#announcement");
	
	const communityBtn2 = document.querySelector("#comm-forum");
	
	const moveToMain = document.querySelector("#logoImg");
	
	moveToMain.addEventListener("click", function(event){
		
	 let main = document.getElementById("logoImg");
	 
	 window.location.replace("http://localhost:8080/IKUZO/jsp/index.jsp");
	});
	
	//bookListBtn
	 bookListBtn.addEventListener("click", function(event){
		
	 let list =	document.getElementById("bookList");
	
	window.location.replace("/IKUZO/ikuzo/book.ikuzo?work_div=doRetrieve");
	
	});//-- bookListBtn end
	 
	//communityBtn
	 communityBtn.addEventListener("click", function(event){
			    
		 let comm = document.getElementById("announcement");
			  
		 window.location.replace("http://localhost:8080/IKUZO/jsp/board01.jsp");
		 
	 });//-- communityBtn end
	 
	  //communityBtn2
	   communityBtn2.addEventListener("click", function(event){
	          
	     let comm2 = document.getElementById("comm-forum");
	        
	     window.location.replace("http://localhost:8080/IKUZO/jsp/board02.jsp");
	     
	   });//-- communityBtn2 end
	 
	 
	
 //검색하기 버튼
 const searchInputBtn = document.querySelector("#headerSearchBtn");
 
 searchInputBtn.addEventListener("click", function(event){
	 console.log("searchInputBtn click");
	 doRetrieve();
 });
 
 
 function doRetrieve(){
	 console.log("doRetrieve()");
	 
	 let frm = document.getElementById("headerSearchForm");
	 
	 frm.work_div.value = "doRetrieve";
	 frm.page_no.value = "1";
	 
	 console.log("frm.searchDiv.value:"+frm.searchDiv.value);  
	 console.log("frm.search_word.value:"+frm.search_word.value);
	 /* console.log("frm.page_size.value:"+frm.page_size.value); */
	 
	 //서버로 보낼 액션 설정
	 console.log("frm.action:"+frm.action);
	 frm.action = "/IKUZO/ikuzo/book.ikuzo";
	   
	 //전송
	 frm.submit();
 }
 
 //페이징 조회
 function pageRetrieve(url,pageNo){
   console.log("url:"+url);
   console.log("pageNo:"+pageNo);
   
      //폼 요소 선택
     let frm = document.getElementById("headerSearchForm");
     frm.work_div.value = "doRetrieve";
      //폼 데이터 설정
     frm.page_no.value = pageNo;
      
     //url
     frm.action = url;
     
     //전송
     frm.submit();
 }
 
});//--DOMContentLoaded end
</script>
<header id="header">
    <div>
        <h1 id="logo">
            <a href="#">
                <img alt="logoImg" src="/IKUZO/assest/img/logoImg.png" id="logoImg">
            </a>
        </h1>
    </div>

    <div id="headerMenu">
        <ul>
            <% if (session.getAttribute("user") == null) { %>
                <li id = "hLoginBtn">
                    <a href="http://localhost:8080/IKUZO/ikuzo/login.ikuzo?work_div=moveToLogin">로그인</a>
                </li>
                <li>
                    <a href="http://localhost:8080/IKUZO/ikuzo/join.ikuzo?work_div=toJoin">회원가입</a>
                </li>
            <% } else { %>
                <li>
                    <a href="/IKUZO/jsp/myPage01.jsp">마이페이지</a>
                </li>
                <li>
                    <a href="#">회원정보수정</a>
                </li>
                <li>
                    <a id = "hManageBtn" href="#">관리자페이지</a>
                </li>
                <%-- <% if (session.getAttribute("isAdmin") != null && ((String)session.getAttribute("isAdmin")).equals("Y")) { %>
						    <li>
						        <a href="manage01.jsp">관리자페이지</a>
						    </li>
						    <% } %> --%>
                <li>
                   <a id="gnb_logout_button" href="#" onclick="logoutAndRedirect()">로그아웃</a>
                </li>
            <% } %>
        </ul>
    </div>



    <form id="headerSearchForm" action="#" method="GET"> <!-- 검색할 서블릿 주소를 설정하세요 -->
        <input type="hidden" name="work_div" id="work_div" placeholder="작업구분">
        <input type="hidden" name="page_no" id="page_no"   placeholder="페이지번호">
        <input type="hidden" name="seq"     id="seq"       placeholder="순번">
        <select id="searchDiv" name="searchDiv">
            <option value="10" selected>제목</option>
            <option value="20">출판사</option>
            <option value="30">저자</option>
            <option value="40">장르</option>
        </select>
        <input type="text" id="search_word" name="search_word">
        <button id="headerSearchBtn" type="button"><p>검색하기</p></button>
    </form>
</header>
<form>
<nav class = "main_nav">
    <ul>
        <li><a id="bookList" href="#">자료검색</a></li>
        <li>
            <a  id="community" href="#">커뮤니티</a>
            <ul id="navSubMenu3">
                <li><a id="announcement" href="http://localhost:8080/IKUZO/jsp/board01.jsp">공지사항</a></li>
                <li><a id="comm-forum" href="http://localhost:8080/IKUZO/jsp/board02.jsp">소통마당</a></li>
            </ul>
        </li>
    </ul>
</nav>
</form>

<script>
    function logoutAndRedirect() {
    	console.log('로그아웃');
    	localStorage.removeItem('user');
    }
</script>
