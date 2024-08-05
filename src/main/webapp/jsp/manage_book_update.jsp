<%@page import="com.pcwk.ehr.cmn.SearchDTO"%>
<%@page import="com.pcwk.ehr.managebook.ManageBookDTO"%>
<%@page import="com.pcwk.ehr.managebook.ManageBookDao"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ include file="/jsp/common.jsp" %>  
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
	   const isAdmin = "${sessionScope.user.isAdmin}";
	    console.log(isAdmin);
	    if(isAdmin !='Y'){
	      window.location.replace("http://localhost:8080/IKUZO/ikuzo/index.ikuzo?work_div=doRetrieve");
	      alert("관리자가 아닙니다");
	    }
	const workDiv = document.querySelector("#work_div");
  const rentCode = document.querySelector("#rent_code");
  const bookCode = document.querySelector("#book_code");
  const bookName = document.querySelector("#book_name");
  const bookGenre = document.querySelector("#book_genre");
  const author = document.querySelector("#author");
  const isbn = document.querySelector("#isbn");
  const publisher = document.querySelector("#publisher");
  const bookPubDate = document.querySelector("#book_pub_date");
  const bookInfo = document.querySelector("#book_info");
  const rentDate = document.querySelector("#rent_date");
  const dueDate = document.querySelector("#due_date");
  const returnedDate = document.querySelector("#returned_date");
  const rentYn = document.querySelector("#rentYn");
  const noreturnCount = document.querySelector("#noreturn_count");
	
	//수정 버튼
  const doUpdateBtn = document.querySelector('#do_update');
  //목록 버튼
  const moveToListBtn = document.querySelector("#move_to_list");	
  //대출연장 버튼
  const doDueDateUpdateBtn = document.querySelector("#do_due_date_update");	
  //반납 버튼
  const doReturnedBtn = document.querySelector("#do_returned");	

  // 이벤트 핸들러  시작
  isbn.addEventListener('input', function() { // isbn 입력 버튼 선택
      doIsbnCheck(this);
  });
  doUpdateBtn.addEventListener('click', function(event){ // 수정 버튼 클릭
    // doUpdate
    doUpdate();
  }); // click  
  moveToListBtn.addEventListener('click', function(event){ // 목록 버튼 클릭
    // moveToList  
    moveToList();
  }); // click
  doDueDateUpdateBtn.addEventListener('click', function(event){ // 대출연장 버튼 클릭
    // doDueDateUpdate  
    doDueDateUpdate();
  }); // click
  doReturnedBtn.addEventListener('click', function(event){ // 대출연장 버튼 클릭
    // doDueDateUpdate  
    doReturned();
  }); // click
  // 이벤트 핸들러 끝  
  
  // 함수 시작
  //목록 이동 시작
  function moveToList(){
    alert("목록으로 이동합니다.");
    window.location.href = "/IKUZO/ikuzo/manage02.ikuzo?work_div=doRetrieve";
  }
  //목록 이동 끝
  //isbn 자릿수 확인 시작
  function doIsbnCheck(isbn) {
	    // ISBN에서 숫자 이외의 문자 제거
	    isbn.value = isbn.value.replace(/[^\d]/g, '');
	
	    // 13자리가 넘는 경우 입력값을 자름
	    if (isbn.value.length > 13) {
	    	  isbn.value = isbn.value.slice(0, 13);
	    }
	
	    // ISBN이 13자리가 아니면 유효성 검사 메시지 설정
	    if (isbn.value.length !== 13) {
	    	  isbn.setCustomValidity('숫자 13자리를 입력하세요');
	    } else {
	    	  isbn.setCustomValidity('');
	    }
	}
  //isbn 자릿수 확인 끝  
  //도서 수정 시작
  function doUpdate(){
      console.log('doUpdate');
    
      // null인지 체크하기 위한 bookCode
      if(isEmpty(bookCode.value) == true){
        alert('도서번호(bookCode)를 확인하세요');
        return;
      }      
      // 도서 제목
      if(isEmpty(bookName.value) == true){
    	  bookName.focus();
        alert('제목을 입력하세요');
        return;
      }
      // ISBN 입력값 확인
      if(isEmpty(isbn.value) == true){
        isbn.focus();
        alert('ISBN을 입력하세요');
        return;
      }
      // ISBN 자리수 확인
      if (isbn.value.length !== 13) {
          isbn.focus();
          alert('ISBN은 13자리여야 합니다. 다시 입력해주세요.');
          return;
      }      
      // 장르 코드
      if(isEmpty(bookGenre.value) == true){
    	  bookGenre.focus();
        alert('장르코드를 입력하세요');
        return;
      }   
      // 출판일 확인
      if(isEmpty(bookPubDate.value) == true){
    	  bookPubDate.focus();
        alert('출판일을 입력하세요');
        return;
      }
      // 출판사 확인
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
      if(false == confirm('책 정보를 수정 하시겠습니까?')){
          return;
      }      
      
      $.ajax({
          type: "POST", 
          url:"/IKUZO/ikuzo/manage02.ikuzo",
          asyn:"true",
          dataType:"html",
          data:{
              "work_div":"doUpdate",
              "bookCode": bookCode.value,
              "bookName": bookName.value,
              "bookGenre" : bookGenre.value,
              "isbn": isbn.value,  
              "bookPubDate": bookPubDate.value,  
              "publisher": publisher.value, 
              "author": author.value,  
              "bookInfo": bookInfo.value
          },
          success:function(response){//통신 성공
              console.log("success data:"+response);
          
              // null, undefined처리
              if(response){
                
                try{
                  const messageVO = JSON.parse(response);
                  console.log("messagveVO.messageId : " + messageVO.messageId);      
                  console.log("messageVO.msgContents:"+ messageVO.msgContents);  
                  
                  if(isEmpty(messageVO) == false && "1" === messageVO.messageId){
                    alert(messageVO.msgContents);
                    window.location.href = "/IKUZO/ikuzo/manage02.ikuzo?work_div=doRetrieve";
                  }else{
                    alert(messageVO.msgContents);
                  }
                }catch(e){
                  console.error("JSON 파싱 에러 : ", e);
                }        
                
              }else{
                console.warn("response가 null혹은 undefined");
                alert("response가 null혹은 undefined");
              }
              
          },
          error:function(data){//실패시 처리
                  console.log("error:"+data);
          }
      });
  } // 도서 수정 끝
  //현재 시간과 날짜를 나타내는 Date 객체 생성
  let sysDate = new Date();

  // 날짜를 YY/MM/DD 형식의 문자열로 가져오기
  let sysYear = sysDate.getFullYear().toString().slice(-2); // 연도에서 뒤에서 두 자리 가져오기
  let sysMonth = ('0' + (sysDate.getMonth() + 1)).slice(-2); // 월 (0부터 시작하므로 +1 필요)
  let sysDay = ('0' + sysDate.getDate()).slice(-2); // 일

  // YY/MM/DD 형식의 오늘 날짜 문자열 생성
  let formattedSysDate = sysYear + '/' + sysMonth + '/' + sysDay;
  // 대출연장 시작
  function doDueDateUpdate(){
	  console.log('doDueDateUpdate');
	    
      // null인지 체크하기 위한 bookCode
      if(isEmpty(bookCode.value) == true){
        alert('도서번호(bookCode)를 확인하세요');
        return;
      }      
      // null인지 체크하기 위한 rentCode
      if((rentCode.value) == true){
        alert('대출 여부를 확인하세요');
        return;
      }      
      // 대출일 확인
      if((rentDate.value) == "없음"){
        rentDate.focus();
        alert('대출 하지 않은 도서입니다');
        return;
      }
      // 대출 연장 횟수 확인
      if((noreturnCount.value) != "Y"){
    	  noreturnCount.focus();
        alert('연장 횟수를 초과했습니다!');
        return;
      }      
      // 반납일 여부 확인
      if((returnedDate.value) != "없음"){
        bookGenre.focus();
        alert('반납된 도서입니다');
        return;
      }   
      // 연체 여부 확인
      console.log(formattedSysDate);
      if((formattedSysDate) > (dueDate.value)){
        bookGenre.focus();
        alert('이미 연체된 도서입니다');
        return;
      }
      
      // DML 발생시 !
      if(false == confirm('대출기간을 연장하시겠습니까?')){
          return;
      }      
      
      $.ajax({
          type: "POST", 
          url:"/IKUZO/ikuzo/manage02.ikuzo",
          asyn:"true",
          dataType:"html",
          data:{
              "work_div":"doDueDateUpdate",
              "rentCode": rentCode.value
          },
          success:function(response){//통신 성공
              console.log("success data:"+response);
          
              // null, undefined처리
              if(response){
                
                try{
                  const messageVO = JSON.parse(response);
                  console.log("messagveVO.messageId : " + messageVO.messageId);      
                  console.log("messageVO.msgContents:"+ messageVO.msgContents);  
                  
                  if(isEmpty(messageVO) == false && "1" === messageVO.messageId){
                    alert(messageVO.msgContents);
                    window.location.href = "/IKUZO/ikuzo/manage02.ikuzo?work_div=doRetrieve";
                  }else{
                    alert(messageVO.msgContents);
                  }
                }catch(e){
                  console.error("JSON 파싱 에러 : ", e);
                }        
                
              }else{
                console.warn("response가 null혹은 undefined");
                alert("response가 null혹은 undefined");
              }
              
          },
          error:function(data){//실패시 처리
                  console.log("error:"+data);
          }
      });
  }
  // 대출연장 끝
  // 대출반납 시작
  function doReturned(){
	  console.log('doReturned');
	    
      // null인지 체크하기 위한 bookCode
      if(isEmpty(bookCode.value) == true){
        alert('도서번호(bookCode)를 확인하세요');
        return;
      }      
      // null인지 체크하기 위한 rentCode
      if((rentCode.value) == true){
        alert('대출 여부를 확인하세요');
        return;
      }      
      // 대출일 확인
      if((rentDate.value) == "없음"){
        rentDate.focus();
        alert('대출 하지 않은 도서입니다');
        return;
      }
      // 반납일 여부 확인
      if((returnedDate.value) != "없음"){
        bookGenre.focus();
        alert('반납된 도서입니다');
        return;
      }   
      
      // DML 발생시 !
      if(false == confirm('이 도서를 반납 처리 하시겠습니까?')){
          return;
      }      
      
      $.ajax({
          type: "POST", 
          url:"/IKUZO/ikuzo/manage02.ikuzo",
          asyn:"true",
          dataType:"html",
          data:{
              "work_div":"doReturned",
              "rentCode": rentCode.value
          },
          success:function(response){//통신 성공
              console.log("success data:"+response);
          
              // null, undefined처리
              if(response){
                
                try{
                  const messageVO = JSON.parse(response);
                  console.log("messagveVO.messageId : " + messageVO.messageId);      
                  console.log("messageVO.msgContents:"+ messageVO.msgContents);  
                  
                  if(isEmpty(messageVO) == false && "1" === messageVO.messageId){
                    alert(messageVO.msgContents);
                    window.location.href = "/IKUZO/ikuzo/manage02.ikuzo?work_div=doRetrieve";
                  }else{
                    alert(messageVO.msgContents);
                  }
                }catch(e){
                  console.error("JSON 파싱 에러 : ", e);
                }        
                
              }else{
                console.warn("response가 null혹은 undefined");
                alert("response가 null혹은 undefined");
              }
              
          },
          error:function(data){//실패시 처리
                  console.log("error:"+data);
          }
      });
  }
  // 대출반납 끝
  // 함수 끝
  
});
</script>
</head>
<body>
<!-- header 시작  -->  
<%@ include file="header.jsp" %>
<!-- header 끝  -->
  
<!-- container -->
<section class="container">
<%-- outVO:${outVO} --%>
  <div class="inner-container">
	<form action="#" class = "form-control" name = "manage_book_update_frm" id = "manage_book_update_frm">
     <input type = "hidden" name = "work_div" id = "work_div">
	   <c:if test ="${outVO.rentCode != 0}">
	   <label style = "display : none;" for = "rent_code">대출 코드<input name = "rent_code" id="rent_code" type = "text" class = "form-control" value = "${outVO.rentCode}" readonly></label><br>       
	   </c:if>
	   <label for = "book_code">도서 코드<input name = "book_code" id="book_code" type = "text" class = "form-control" value = "${outVO.bookCode}" readonly></label><br>       
	   <label for = "book_name">도서 제목<input name = "book_name" id="book_name" type = "text" class = "form-control" value = "${outVO.bookName}" required></label><br>       
	   <label for = "book_genre">장르 번호<input name = "book_genre" id="book_genre" type = "text" class = "form-control" value = "${outVO.bookGenre}" required></label><br>       
	   <label for = "author">작가<input name = "author" id="author" type = "text" class = "form-control" value = "${outVO.author}" required></label><br>       
	   <label for = "isbn">isbn<input name = "isbn" id="isbn" type="text" pattern="[0-9]{13}" placeholder="숫자 13자리를 입력하세요" title="숫자  13자리를 입력하세요" class = "form-control" value = "${outVO.isbn}" required></label><br>       
	   <label for = "publisher">출판사<input name = "publisher" id="publisher" type = "text" class = "form-control" value = "${outVO.publisher}" required></label><br>       
	   <label for = "book_pub_date">출판일<input name = "book_pub_date" id="book_pub_date" type = "text" class = "form-control" value = "${outVO.bookPubDate}" required></label><br>       
	   <label for = "book_info">책소개<textarea id="book_info" name = "bookInfo" class="form-control" placeholder="책 소개" rows="10" cols="10" required>${outVO.bookInfo}</textarea></label><br>       
	   <label for = "rent_date">대출일<input name = "rent_date" id="rent_date" type = "text" class = "form-control" value = "${outVO.rentDate}"></label><br>       
	   <label for = "due_date">반납예정일<input name = "due_date" id="due_date" type = "text" class = "form-control" value = "${outVO.dueDate}" readonly></label><br>       
	   <label for = "returned_date">반납일<input name = "returned_date" id="returned_date" type = "text" class = "form-control" value = "${outVO.retunredDate}" readonly></label><br>       
	   <label for = "rent_yn">대출가능여부<input name = "rent_yn" id="rent_yn" type = "text" class = "form-control" value = "${outVO.rentYn}" readonly></label>          
	   <label for = "noreturn_count">대출연장여부<input name = "noreturn_count" id="noreturn_count" type = "text" class = "form-control" value = "${outVO.noreturnCount}" readonly></label>          
	</form>
	
	<button type="button" id = "do_update">수정</button>
	<button type="button" id = "move_to_list">목록</button>
	<c:if test ="${outVO.retunredDate == '없음' && outVO.rentDate != '없음'}">
	<button type="button" id = "do_due_date_update">대출연장</button>
	<button type="button" id = "do_returned">반납</button>
	</c:if>
  </div>
  
</section>
<!-- //container -->

<!-- footer 시작  -->
<%@ include file="footer.jsp" %>
<!-- footer 끝  -->
</body>
</html>