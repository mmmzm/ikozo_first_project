<%@page import="com.pcwk.ehr.cmn.SearchDTO"%>
<%@page import="com.pcwk.ehr.manageuser.ManageUserDTO"%>
<%@page import="com.pcwk.ehr.manageuser.ManageUserDao"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ include file="/jsp/common.jsp" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/IKUZO/assest/css/bookbook.css">
<link rel="stylesheet" href="/IKUZO/assest/css/book_manage.css">
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script src="/IKUZO/assest/js/common.js"></script>
<script>
document.addEventListener("DOMContentLoaded", function(){
	
	const isAdmin = "${sessionScope.user.isAdmin}";
	console.log(isAdmin);
	if(isAdmin !='Y'){
	  window.location.replace("http://localhost:8080/IKUZO/ikuzo/index.ikuzo?work_div=doRetrieve");
	  alert("관리자가 아닙니다");
	}
});
</script>
</head>
<body>
<!-- header 시작  -->  
<%@ include file="header.jsp" %>
<!-- header 끝  -->
  
<!-- container -->
<section class="container">
outVO:${outVO}
  <div class="inner-container">
    <table class="notice-board">
		    <thead>
		        <tr>
		            <th class="user_id">아이디</th>
		            <th class="user_name">이름</th>
		            <th class="is_admin">관리자여부</th>
		            <th class="extra_sum">연체요금</th>
		            <th class="rent_yn">미납여부</th>
		            <th class="book_code">도서번호</th>
		            <th class="book_name">도서제목</th>
		        </tr>
		    </thead>
		    <tbody>
		        <tr>
		          <td class="user_id">${outVO.userId}</td>
		          <td class="user_name">${outVO.userName}</td>
              <td class="is_admin">${outVO.isAdmin}</td>
              <td class="extra_sum">${outVO.extraSum}</td>
              <td class="rent_yn">${outVO.rentBookYn}</td>
              <td class="book_code">${outVO.bookCode}</td>
              <td class="book_name">${outVO.bookName}</td>
		        </tr>            
		    </tbody>
		</table>
  </div>
  
</section>
<!-- //container -->

<!-- footer 시작  -->
<%@ include file="footer.jsp" %>
<!-- footer 끝  -->
</body>
</html>