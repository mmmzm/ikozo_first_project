<%@page import="com.pcwk.ehr.cmn.StringUtil"%>
<%@page import="com.pcwk.ehr.cmn.SearchDTO"%>
<%@page import="com.pcwk.ehr.managebook.ManageBookDTO"%>
<%@page import="com.pcwk.ehr.managebook.ManageBookDao"%>  
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>          
<%@ include file="/jsp/common.jsp" %>  
<%
 List<ManageBookDTO> list =(List<ManageBookDTO>)request.getAttribute("list");

 SearchDTO searchCon =(SearchDTO)request.getAttribute("vo");
 %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>북북 도서관 - 관리자 페이지 02</title>
<link rel="stylesheet" href="/IKUZO/assest/css/bookbook.css?after">
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
    // 페이지 이동 버튼
    const manageUserbtn = document.querySelector("#manageUserbtn"); 
    const manageBookbtn = document.querySelector("#manageBookbtn"); 

    // 작업 구분
    const workDiv = document.querySelector("#work_div");
    
    // 조회 버튼
    const doRetrievebtn = document.querySelector("#doRetrieve");  
    
    // 삭제 버튼
    const deletebtn = document.querySelector("#deleteBtn"); 
    
    // 도서 추가 버튼
    const saveBookBtn = document.querySelector("#saveBookBtn"); 

    // 도서 수정 버튼
    const modifiyBookBtns = document.querySelectorAll(".profile_edit .active"); 
    
    // 도서 검색창
    const searchWord = document.querySelector("#search_word");
    
    // tbody 내의 모든 행을 가져옵니다
    const rows = document.querySelectorAll(".notice-board tbody tr");
    let bookCode = "";
    const bookCodes = [];
    
    // 이벤트 핸들러 시작
    manageUserbtn.addEventListener('click', function(event){ // 회원관리 페이지 이동 버튼 클릭
       moveToMuser();
    }); // click
    manageBookbtn.addEventListener('click', function(event){ // 도서관리 페이지 이동 버튼 클릭
       moveToMbook();
    }); // click
    deletebtn.addEventListener('click', function(event){ // 도서삭제 버튼 클릭
       doDeleteBook(); 
    }); // click 
    doRetrievebtn.addEventListener("click", function(event){ // 조회 버튼 클릭
      event.preventDefault();
      doRetrieve();   
    });  
    saveBookBtn.addEventListener('click', function(event){ // 도서추가 버튼 클릭
       console.log("saveBookBtn click");
        
        // 폼 요소 선택
        let frm = document.getElementById("manage_book_frm");

        frm.work_div.value = "moveToSaveBook";
        
        console.log("frm.work_div.value : " + frm.work_div.value);
          
        console.log("frm.action : " + "<%=cPath%>" + "/ikuzo/manage02.ikuzo");
        frm.action = "<%=cPath%>" + "/ikuzo/manage02.ikuzo";
        
        frm.submit();    
    }); // click 
    // 도서수정 버튼 클릭 시작
    modifiyBookBtns.forEach(function(modifiyBookBtn){ // 도서수정 버튼 클릭
      modifiyBookBtn.addEventListener('click', function(){
        // a태그 동작방지
        event.preventDefault();
        console.log("button click!");
        
        let tr = this.closest('tr'); // 클릭된 링크의 부모 <tr>을 찾습니다.
        let bookCodeEl = tr.querySelector('td.book_code'); // <tr>에서 book_code를 찾습니다.
        
        if (bookCodeEl) {
             let bookCode = bookCodeEl.textContent.trim(); // bookCodeEl의 텍스트 값을 가져옵니다.
             console.log('선택된 book_code:', bookCode);
             
             doSelectOne(bookCode);
             
             // 여기에 book_code 값을 활용하는 추가적인 작업을 수행할 수 있습니다.
        } else {
             console.error('해당 도서의 코드를 찾을 수 없습니다. 잘못된 접근입니다.');
             alert('잘못된 접근입니다!');
        }      
        
      }); // click
    }); // forEach
    // 도서수정 버튼 클릭 끝
    rows.forEach(function(row){ // 테이블의 행 클릭
      row.addEventListener('click', function(){
        console.log("row click!");
        
        // book_code 값 가져오기
        bookCode = row.querySelector("td.book_code").innerText; 
        console.log('선택된 행의 book_code:', bookCode);

        //doSelectOne(bookCode);
      });
    }); // forEach 끝
    
    // 검색창 엔터 후 이벤트 시작
    searchWord.addEventListener("keydown", function(event){
      console.log("keydown", event.key, event.keyCode);
      
      if(event.keyCode == 13){
        console.log(`input.value:${input.value}`);
        doRetrieve();
      }
    });
    // 검색창 엔터 후 이벤트 끝
    // 이벤트 핸들러 끝
    
    // 함수 시작
    // 체크박스 테이블 행 선택함수
    function doCheckRow(){

        // 각 행을 반복 처리합니다
        rows.forEach(function(row) {
            // 현재 행의 체크박스를 찾습니다
            const checkbox = row.querySelector("td.checkbox input.chk");

            // 체크박스가 체크되어 있는지 확인합니다
            if (checkbox.checked) {
                // 현재 행의 book_code를 찾습니다
                bookCode = row.querySelector("td.book_code").innerText;
                bookCodes.push(bookCode);
            }
        });
    }
    
    function moveToMuser(){ // 회원관리페이지이동 함수
      console.log("userbtn");
      window.location.href = "/IKUZO/ikuzo/manage01.ikuzo?work_div=doRetrieve";
    }    
    function moveToMbook(){ // 도서관리페이지이동 함수
      console.log("bookbtn");
      window.location.href = "/IKUZO/ikuzo/manage02.ikuzo?work_div=doRetrieve";
    } 
    
    // 도서 삭제 함수 시작
    function doDeleteBook(){        
        console.log('doDeleteBook');
        workDiv.value = 'doDelete';
        
        doCheckRow();

        // bookCodes를 출력합니다 (필요에 따라 이 배열을 사용할 수 있습니다)
        console.log(bookCode);        
        console.log(bookCodes);        
        
        // bookCodes 체크 여부
        if(isEmpty(bookCodes) == true){
            alert('체크된 도서가 존재하지 않습니다. 잘못된 경로!');
        }else if(false == confirm('해당 도서를 삭제 하시겠습니까?')){
            return;
        }  
        
        // ajax start
        $.ajax({
            type: "GET", 
            url:"/IKUZO/ikuzo/manage02.ikuzo",
            asyn:"true",
            dataType:"html",
            data:{
                "work_div":"doDelete",
                "bookCodes": bookCode
            },
            success:function(response){//통신 성공
                console.log("success response:"+response);
                const messageVO = JSON.parse(response);
                console.log("messageVO.messageId:"+ messageVO.messageId);             
                console.log("messageVO.msgContents:"+ messageVO.msgContents);  
                
                if(isEmpty(messageVO) == false && "1" === messageVO.messageId){
                  alert(messageVO.msgContents);
                  // window.location.href = location.href; 기존 경로 유지
                  window.location.reload();
                }else{
                  alert(messageVO.msgContents);
                }
            },
            error:function(data){//실패시 처리
                console.log("error:"+data);
            }
        });    
      }
    // 도서 삭제 함수 끝
    
    // 도서 수정 함수 시작
    function doSelectOne(bookCode){
      // 폼 요소 선택
      let frm = document.getElementById("manage_book_frm");
      
      // 폼 데이터 설정
      frm.work_div.value = "doSelectOne";
      
      //seq
      frm.seq.value = bookCode;
      frm.action = "<%=cPath%>" + "/ikuzo/manage02.ikuzo";
      
      // 폼 제출
      frm.submit();
    }
    // 도서 수정 함수 끝
    
    // 조회 함수 시작
    function doRetrieve(){
      console.log("doRetrieve");
      
      // 폼 요소 선택
      let frm = document.getElementById("manage_book_frm"); 
      frm.work_div.value = "doRetrieve";
      frm.page_no.value = "1";
      
      console.log("frm.search_div.value : " + frm.search_div.value);
      console.log("frm.search_word.value : " + frm.search_word.value);
      console.log("frm.page_size.value : " + frm.page_size.value);
      
      // 서버로 보낼 액션 설정
      console.log("frm.action : " + "<%=cPath%>" + "/ikuzo/manage02.ikuzo");
      frm.action = "<%=cPath%>" + "/ikuzo/manage02.ikuzo";
      
      // 폼 제출
      frm.submit();         
    }   
    // 조회 함수 끝    
    // 함수 끝
});
//페이징 조회 시작
function pageRetrieve(url, pageNo){
  console.log("url : " + url);
  console.log("pageNo : " + pageNo);
  
  // 폼 요소 선택
  let frm = document.getElementById("manage_book_frm");   
  frm.work_div.value = "doRetrieve";
  
  // 폼 데이터 설정
  frm.page_no.value = pageNo;
   
  // url
  frm.action = url;
  
  // 폼
  frm.submit();
}
// 페이징 조회 끝
</script>
</head>
<body>
<!-- header 시작  -->  
<%@ include file="header.jsp" %>
<!-- header 끝  -->
  
<!-- container -->
<%-- session : ${sessionScope.user} --%>
<section class="container">
    <div class="inner-container">
       <div class="page-title-group">
          <h2 class="page-title">관리자 페이지</h2>
       </div>

        
    <div class="page-list-group">
        <div class="page-list-inner">
            <div>
                <a id ="manageUserbtn">회원 관리</a>
            </div>
            <div class="active">
                <a id ="manageBookbtn">도서 관리</a>
            </div>
        </div>    
    </div>

<div class="category-box">
    <div class="category-wrap">
        <div class="redAlert">
            <a href="#" id ="deleteBtn">삭제</a>
        </div>
        <div class="active">
            <a href="#" id ="saveBookBtn">추가</a>
        </div>
    </div>

    <div class="category-wrap category-wrap2">
        <form action="<%=cPath%>/ikuzo/manage02.ikuzo" name = "manage_book_frm" id = "manage_book_frm">
        <input type = "hidden" name = "work_div" id = "work_div">
        <input type="hidden" name="page_no" id="page_no" placeholder="페이지 번호">
        <input type = "hidden" name = "seq" id = "seq">
            <select style = "cursor : pointer;" name = "page_size" id="page_size">
                <option value="10" >10페이지</option>
                <option value="20" >20페이지</option>
                <option value="30" >30페이지</option>
                <option value="40" >40페이지</option>
            </select>
            <select style = "cursor : pointer;" name = "rent_yn" id="rent_yn">
                <option value="" selected>대여 여부</option>
                <option value="10" >가능</option>
                <option value="20" >불가능</option>
            </select>
            <select name = "search_div" id="search_div">
                <option value="" selected="selected">전체</option>
                <option value="10">도서제목</option>
                <option value="20">장르</option>
                <option value="30">작가</option>
                <option value="40">출판사</option>
            </select>
            <input type="search" name="search_word" id = "search_word" placeholder="검색어를 입력해주세요" value="<%if(null != searchCon){out.print(searchCon.getSearchWord());}%>">
        </form>
        <button type="button" class="btn-control" id = "doRetrieve">
            <span class="icon"></span>
        </button>   
    </div>
</div>

<table class="notice-board">
    <thead>
        <tr>
            <th class="checkbox"><input id="checkAll" type="checkbox"></th>
            <th class="rnum">번호</th>
            <th class="book_name">도서제목</th>
            <th class="genre_name">장르</th>
            <th class="author">작가</th>
            <th class="company">출판사</th>
            <th class="rent_date">대출일</th>
            <th class="due_date">반납예정일</th>
            <th class="returned_date">반납일</th>
            <th class="rent_yn">대출가능여부</th>
            <th class="profile_edit">정보수정</th>
        </tr>
    </thead>
    <tbody>
    <%
    for(ManageBookDTO vo :list) {
    %>
        <tr>
            <td class="book_code" style = "display : none;"><%=vo.getBookCode()%></td>
            <td class="checkbox"><input type="checkbox" class="chk"></td>
            <td class="rnum"><%=vo.getRnum()%></td>
            <td class="book_name"><%=vo.getBookName()%></td>
            <td class="genre_name"><%=vo.getGenre()%></td>
            <td class="author"><%=vo.getAuthor()%></td>
            <td class="publisher"><%=vo.getPublisher()%></td>
            <td class="rent_date"><%=vo.getRentDate()%></td>
            <td class="due_date"><%=vo.getDueDate()%></td>
            <td class="returned_date"><%=vo.getRetunredDate()%></td>
            <td class="rent_yn"><%=vo.getRentYn()%></td>
            <td class="profile_edit">
                <a href="" class="active">수정</a>
            </td>
        </tr> 
    <% } %>                                 
    </tbody>
</table>

<!-- paging start -->
<nav aria-label="Page navigation example" style = "text-align : center;">
<%
// 총글수
SearchDTO pageingVO = (SearchDTO)request.getAttribute("vo");
int totalCnt = pageingVO.getTotalCnt();

// 바닥 글수
int bottomCnt = pageingVO.getBottomCount();

// 페이지 사이즈
int pageSize = pageingVO.getPageSize();

// 페이지 번호
int pageNo = pageingVO.getPageNo();

// pageRetrieve(url, 2);
out.print(StringUtil.renderingPaging(totalCnt, pageNo, pageSize, bottomCnt, "/IKUZO/ikuzo/manage02.ikuzo", "pageRetrieve"));
%>
</nav>
<!-- paging end -->
    
  </div>
</section>
<!-- //container -->

<!-- footer 시작  -->
<%@ include file="footer.jsp" %>
<!-- footer 끝  -->
<script src="/IKUZO/assest/js/check.js"></script>
</body>
</html>