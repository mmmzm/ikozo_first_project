<%@page import="com.pcwk.ehr.cmn.SearchDTO"%>
<%@page import="com.pcwk.ehr.managebook.ManageBookDTO"%>
<%@page import="com.pcwk.ehr.managebook.ManageBookDao"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/common.jsp" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="/WEB03/assest/css/bookbook.css">
<link rel="stylesheet" href="/WEB03/assest/css/book_manage.css">
<link rel="stylesheet" href="/WEB02/assest/css/bootstrap.css">
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
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
	<div class="inner-container">
	 <form action="">
	  <label class="form-label">제목<input class="form-control" type = "text"></label> 
	  <label class="form-label">제목
      <select class="form-select">
        <option>전공서적_I</option>
        <option>소설</option>
        <option>시/에세이</option>
        <option>가정/육아</option>
        <option>건강</option>
        <option>자기계발</option>
        <option>역사/문화</option>
        <option>과학</option>
        <option>외국어</option>
        <option>만화</option>
        <option>여행</option>
        <option>수필</option>
      </select>
    </label> 
	  <label class="form-label">ISBN<input class="form-control" type = "text"></label> 
	  <label class="form-label">출판일<input class="form-control" type = "text"></label> 
	  <label class="form-label">출판사<input class="form-control" type = "text"></label> 
	  <label class="form-label">작가<input class="form-control" type = "text"></label> 
	  <label class="form-label"><textarea class="form-control" placeholder="책 소개" rows="10" cols="10"></textarea></label>
	 </form>
	</div>
</section>
<!-- //container -->

<!-- footer 시작  -->
<%@ include file="footer.jsp" %>
<!-- footer 끝  -->
<script src = "/WEB02/assest/js/bootstrap.bundle.min.js"></script>  
<script src="/WEB03/assest/js/check.js"></script>
</body>
</html>