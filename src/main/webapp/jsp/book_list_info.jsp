<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <!-- board_mng 따라서 해보기 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- bootstrap -->
<link rel="stylesheet" href="/IKUZO/assest/css/bootstrap.css">
<!-- //bootstrap  end -->
<title>Book ListInfo</title>
<script src="/IKUZO/assest/js/jquery_3_7_1.js"></script> <!--jQuery -->
<script src = "/IKUZO/assest/js/common.js"></script> <!-- common.js  -->
<link rel="stylesheet" href="/IKUZO/assest/css/bookbook.css">
<link rel="stylesheet" href="/IKUZO/assest/css/book_info.css">
<script>
document.addEventListener("DOMContentLoaded", function(){
	  console.log('DOMContentLoaded');
	  
	  //목록 이동 버튼
	  const moveToListBtn = document.querySelector("#moveToList")
	  
	  //대출하기 버튼
	  const doRentBtn = document.querySelector("#doRent")
	  
	  //즐겨찾기 버튼
	  const doFavBtn = document.querySelector("#fav")
	  
	  //도서 내용
	  const bookCode = document.querySelector("#book_code");
	  const bookSeq = document.querySelector("#book_seq");     //도서번호
	  const bookName = document.querySelector("#book_name");   //도서제목
	  const isbn = document.querySelector("#isbn");            //isbn
	  const pubDate = document.querySelector("#book_pub_date");//발간날짜
	  const publisher = document.querySelector("#publisher");  //출판사
	  
	  const rentCode = document.querySelector('#book_code');
	  
	  //즐겨찾기 버튼
	  doFavBtn.addEventListener("click",function(event){
		  console.log('doFavBtn click: event'+event);
		  fav();
	  });
	  
	  //대출하기 버튼
	  doRentBtn.addEventListener("click",function(event){
        console.log('doRentBtn click: event'+event);
        doRent();
      });
	  
	  //목록 이동 버튼
	  moveToListBtn.addEventListener("click",function(event){
		    console.log('moveToListBtn click: event'+event);
		    moveToList();
		  });
	  
	  function fav(){
		  console.log('fav()');
		  alert("즐겨찾기에 추가 하시겠습니까?");
		  
		  $.ajax({
			    type: "GET", 
			    url:"/IKUZO/ikuzo/book.ikuzo",
			    asyn:"true",
			    dataType:"html",
			    data:{
			        "work_div":"doFavSave",
			        "book_code" : ${outVO.bookCode }  
			    },
			    success:function(response){//통신 성공
			        console.log("success response:"+response);
			        const messageVO = JSON.parse(response);
		          console.log("messageId:" +messageVO.messageId);
		          console.log("MsgContents:" +messageVO.MsgContents);
		          
		      if(messageVO.messageId === "1"){
		    	  alert(messageVO.msgContents);
		    	  window.location.reload();
		       }else{
		    	   alert(messageVO.msgContents);
		       }
			    },
			    error:function(response){//실패시 처리
			      console.log("error:"+response);
			    }
			});//--doFavSave ajax
	  }//-- fav end
	  
	  //moveToList
	  function moveToList() {
		  console.log('moveToList()');
		  alert("게시 목록으로 이동 합니다.");
		  window.location.href = "/IKUZO/ikuzo/book.ikuzo?work_div=doRetrieve";
		}//--moveToList end
	 
	  function rentcheck(){
		  console.log('rentcheck()');
		  
		  if(isEmpty(rentCode.value) == true){
			  alert('이미 대출된 도서 입니다.');
			  return;
		  }
	  } //-- rentcheck end
	  
	  
	  
	  function doRent() {
		  console.log('doRent()');
		  alert("대출하시겠습니까?");
		  
		   $.ajax({
			    type: "GET", 
			    url:"/IKUZO/ikuzo/book.ikuzo",
			    asyn:"true",//비동기 통신
			    dataType:"html",
			    data:{
			        "work_div":"doSave",
			        "book_code" : ${outVO.bookCode }
			    },
			    success:function(response){
			    	console.log("sussess data:" +response);
			    	const messageVO = JSON.parse(response);
			    	console.log("messageId:" +messageVO.messageId);
			    	console.log("MsgContents:" +messageVO.MsgContents);
			    	
			    	if(messageVO.messageId === "1"){
			    		alert(messageVO.msgContents);
					  window.location.href = "/IKUZO/ikuzo/book.ikuzo?work_div=doRetrieve";
			    	}else{
			    		alert(messageVO.msgContents);
			    	}
			    },
			    error:function(response){//실패시 처리
			      console.log("error:"+response);
			    }
			}); //-- doSave ajax
	  } //-- doRent end
	  
	  // work_div, book_code, userId
	  // rent   rentdate(SYSDATE) due_date(SYSDATE+7) userid 
	  //지금은 book_code만 꺼내올수있게

}); // --DOMContentLoaded end
</script>

</head>
<body>

<!-- header 시작  -->  
<%@ include file="header.jsp" %>
<!-- header 끝  -->

<!-- 버튼 -->
  <div class="mb-2 d-grid gap-2 d-md-flex justify-content-md-left">
  <input type="button" value="목록으로 돌아가기" class="btn btn-success" id="moveToList">
  </div>
  <div class="mb-2 d-grid gap-2 d-md-flex justify-content-md-end">
    <input type="button" value="대출하기" class="btn btn-success" id="doRent">
    <input type="button" value="즐겨찾기" class="btn btn-success" id="fav">
  </div>
  
<!-- 버튼 end -->
 
<form action="#" class="form-horizontal">
  <img class="image" alt="책" src="/IKUZO/assest/img/book.png">
  <input type="hidden" name="book_code" id="book_code">
    <div class="row mb-3">
  <label for="book_seq" class="col-sm-2 col-form-label">도서번호</label>
<!-- bookCode -->
      <input disabled type="text" style="width: 500px" class="form-control" name="book_seq"  id="book_seq" required="required"  value="${outVO.bookCode }" >
    </div> 
<!-- bookName -->
    <div class="row mb-3">
      <label for="book_name" class="col-sm-2 col-form-label">제목</label>
    <div class="col-sm-10">
      <input disabled type="text" style="width: 500px" class="form-control" name="book_name" id="book_name" maxlength="70" required="required" value="${outVO.bookName }">
    </div>
    </div>
 
<!-- isbn -->
    <div class="row mb-3">
      <label for="isbn"  class="col-sm-2 col-form-label">isbn</label>
    <div class="col-sm-10">
      <input disabled type="text" style="width: 500px" class="form-control" name="isbn" id="isbn" value="${outVO.isbn }">
    </div>
    </div>
 
<!-- book_pub_date -->
    <div class="row mb-3">
      <label for="book_pub_date" class="col-sm-2 col-form-label" >발간날짜</label>
    <div class="col-sm-10">
      <input disabled type="text" style="width: 500px" class="form-control" name="book_pub_date" id="book_pub_date" value="${outVO.bookPubDate }">
    </div>
    </div>
            
<!-- publisher -->
    <div class="row mb-3">
      <label for="publisher" class="col-sm-2 col-form-label">출판사</label>
    <div class="col-sm-10">
      <input disabled type="text" style="width: 500px" class="form-control"  name="publisher" id="publisher"  required="required" value="${outVO.publisher }">                
    </div>
    </div>
<!-- book_info -->
    <div class="row mb-3">
      <label for="book_info" class="col-sm-2 col-form-label">내용</label>
        <textarea disabled style="height: 200px; width: 500px" class="form-control" name="book_info" id="book_info">${outVO.bookInfo }</textarea>
    </div>

</form>
 
<!-- footer 시작  -->
<%@ include file="footer.jsp" %> 
<!-- footer 끝  -->
  
  <!--bundle  -->
  <script src="/IKUZO/assest/js/bootstrap.bundle.min.js"></script> 
  <!--// bundle end  -->
</body>
</html>