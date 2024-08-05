<%@page import="com.pcwk.ehr.cmn.SearchDTO"%>
<%@page import="com.pcwk.ehr.managebook.ManageBookDTO"%>
<%@page import="com.pcwk.ehr.managebook.ManageBookDao"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/IKUZO/assest/css/bookbook.css">
<link rel="stylesheet" href="/IKUZO/assest/css/book_manage.css?after">
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script src="/IKUZO/assest/js/common.js"></script>
<script>
document.addEventListener("DOMContentLoaded", function(){
	
    // 등록화면 진입 확인
    console.log("DOMContentLoaded");
    
    // 작업구분, 도서제목, 그외 객체
    const workDiv = document.querySelector("#work_div");    // 작업구분
    const bookName = document.querySelector("#bookName");  // 도서제목  
    const bookGenre = document.querySelector("#book_genre");// 장르코드
    const isbn = document.querySelector("#isbn");           // isbn
    const bookPubDate = document.querySelector("#book_pub_date");// 출판일
    const publisher = document.querySelector("#publisher"); // 출판사
    const author = document.querySelector("#author"); // 작가
    const bookInfo = document.querySelector("#book_info"); // 책소개    
    const bookForm = document.querySelector("#bookForm"); // form태그
    
    // 등록 버튼 선언
    const doSaveBtn = document.querySelector("#do_save_btn");
    
    // 목록 버튼 선언
    const moveToListBtn = document.querySelector("#move_to_list_btn");

    // 이벤트 핸들러 
    doSaveBtn.addEventListener("click", function(event){
    	event.preventDefault(); // submit 버블링 방지
    	doSave();
    }); 
    
    moveToListBtn.addEventListener('click', function(event){ // 목록 버튼 클릭 
      moveToList();
    }); // click
    // 이벤트 핸들러 끝
    
    function moveToList(){
      alert("목록으로 이동합니다.");
      window.location.href = "/IKUZO/ikuzo/manage02.ikuzo?work_div=doRetrieve";
    }
    
    function doSave(){ 
    
    // 도서 제목
     if(isEmpty(bookName.value) == true){
      title.focus();
      alert('도서 제목을 입력하세요');
      return;
    }
    // ISBN 입력값 확인
    if(isEmpty(isbn.value) == true){
      isbn.focus();
      alert('ISBN을 입력하세요');
      return;
    }
    // ISBN에서 숫자 이외의 문자 제거
    isbn.value = isbn.value.replace(/[^\d]/g, '');
    // ISBN 자리수 확인
    if (isbn.value.length !== 13) {
        isbn.focus();
        alert('ISBN은 숫자 13자리여야 합니다. 다시 입력해주세요.');
        return;
    }    
    // 출판일
    if(isEmpty(bookPubDate.value) == true){
    	bookPubDate.focus();
      alert('출판일을 입력하세요');
      return;
    }
    
    // 출판사
    if(isEmpty(publisher.value) == true){
    	publisher.focus();
      alert('출판사를 입력하세요');
      return;
    }

    // 작가
    if(isEmpty(author.value) == true){
    	author.focus();
      alert('작가를 입력하세요');
      return;
    }

    // 책 소개
    if(isEmpty(bookInfo.value) == true){
    	bookInfo.focus();
      alert('책 소개를 입력해주세요');
      return;
    }
    
    // DML 발생시 !
    if(false == confirm('책 등록 하시겠습니까?')){
      return;
    } 
    
     $.ajax({
          type: "GET", 
          url:"/IKUZO/ikuzo/manage02.ikuzo",
          asyn:"true",
          dataType:"html",
          data:{
              "work_div":"doSave",
              "bookName": bookName.value,
              "bookGenre" : bookGenre.value,
              "isbn": isbn.value,  
              "bookPubDate": bookPubDate.value,  
              "publisher": publisher.value, 
              "author": author.value,  
              "bookInfo": bookInfo.value
          },
          success:function(data){//통신 성공
              console.log("success data:"+data);
              const messageVO = JSON.parse(data);
              console.log("messageId:"+messageVO.messageId);              
              console.log("msgContents:"+messageVO.msgContetns);
              
              if(messageVO.messageId === "1"){
                alert(messageVO.msgContents);
                window.location.href = "/IKUZO/ikuzo/manage02.ikuzo?work_div=doRetrieve";
              }else{
                alert(messageVO.msgContents);               
              }
          },
          error:function(data){//실패시 처리
              console.log("error:"+data);
          }
      }); // ajax end 
    } // doSave end   
}); // DOM END
</script>
</head>
<body>
<!-- header 시작  -->  
<%@ include file="header.jsp" %>
<!-- header 끝  -->
  
<!-- container -->
<section class="container">
	<div class="inner-container">
	 <form action="#" method = "get" name="bookForm" id="bookForm">
	 <input type = "hidden" name = "work_div" id = "work_div" value = "doSave">
	  <label class="form-label">제목<input name = "bookName" id ="bookName" class="form-control" type = "text" required></label> 
	  <label class="form-label">장르
      <select class="form-select" id="book_genre" required>
        <option value="10" selected>소설</option>
        <option value="20">시/에세이</option>
        <option value="30">가정/육아</option>
        <option value="40">건강</option>
        <option value="50">자기계발</option>
        <option value="60">역사/문화</option>
        <option value="70">과학</option>
        <option value="80">외국어</option>
        <option value="90">여행</option>
        <option value="100">만화</option>
        <option value="120">수필</option>
        <option value="130">전공서적_I</option>
      </select>
    </label> 
	  <label class="form-label">ISBN<input placeholder="13자리를 입력하세요" title="숫자  13자리를 입력하세요" minlength="13" maxlength="13" id="isbn" name = "isbn" class="form-control" type="text" pattern="[0-9]{13}" required></label> 
	  <label class="form-label">출판일<input id="book_pub_date" name = "bookPubDate" class="form-control" type = "text" required></label> 
	  <label class="form-label">출판사<input id="publisher" name = "publisher" class="form-control" type = "text" required></label> 
	  <label class="form-label">작가<input id="author" name = "author" class="form-control" type = "text" required></label> 
	  <label class="form-label"><textarea id="book_info" name = "bookInfo" class="form-control" placeholder="책 소개" rows="10" cols="10" required></textarea></label>
	  </form>
	  
	  <input type="button" value="도서추가" id = "do_save_btn" class="btn btn-success">
	  <input type="button" value="목록" id = "move_to_list_btn" class="btn btn-primary">
	</div>
</section>
<!-- //container -->

<!-- footer 시작  -->
<%@ include file="footer.jsp" %>
<!-- footer 끝  -->
</body>
</html>