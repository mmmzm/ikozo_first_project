<%@page import="com.pcwk.ehr.cmn.StringUtil"%>
<%@page import="com.pcwk.ehr.cmn.SearchDTO"%>
<%@page import="com.pcwk.ehr.manageuser.ManageUserDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/common.jsp" %>       
<%
List<ManageUserDTO> list =(List<ManageUserDTO>)request.getAttribute("list");

SearchDTO searchCon =(SearchDTO)request.getAttribute("vo");
%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>북북 도서관 - 관리자 페이지 01</title>
<link rel="stylesheet" href="/IKUZO/assest/css/bookbook.css?after">
<link rel="stylesheet" href="/IKUZO/assest/css/book_manage.css">
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script>
document.addEventListener("DOMContentLoaded", function(){
    const isAdmin = "${sessionScope.user.isAdmin}";
    console.log(isAdmin);
    if(isAdmin !='Y'){
      window.location.replace("http://localhost:8080/IKUZO/ikuzo/index.ikuzo?work_div=doRetrieve");
      alert("관리자가 아닙니다");
    }	
	  // isEmpty 함수 정의
    function isEmpty(value) {
        return (value == null || value.length === 0);
    }
	
    // 작업 구분
    const workDiv = document.querySelector("#work_div");
	
    // 회원 상세 정보 버튼
    const userInfoBtns = document.querySelectorAll(".profile_edit .active"); 

    // 페이지 이동 버튼
    const manageUserbtn = document.querySelector("#manageUserbtn");	

    // 조회 버튼
    const doRetrievebtn = document.querySelector("#doRetrieve");	

    // 검색창
    const searchWord = document.querySelector("#search_word");
    
    // tbody 내의 모든 행을 가져옵니다
    const rows = document.querySelectorAll(".notice-board tbody tr");
    let userId = "";
    const userIds = [];
    
    // 삭제 버튼
    const deletebtn = document.querySelector("#deletebtn");	
    
    // 이벤트 핸들러 시작
    manageUserbtn.addEventListener('click', function(event){ // 회원관리 페이지 이동 버튼 클릭
       moveToMuser();
    }); // click
    manageBookbtn.addEventListener('click', function(event){ // 도서관리 페이지 이동 버튼 클릭
       moveToMbook();
    }); // click
    deletebtn.addEventListener('click', function(event){ // 회원삭제 버튼 클릭
    	doDeleteUser(); 
    }); // click    
    doRetrievebtn.addEventListener("click", function(event){ // 조회 버튼 클릭
    	event.preventDefault();
      doRetrieve();   
    });    
    // 검색창 엔터 후 이벤트 시작
    searchWord.addEventListener("keydown", function(event){
      console.log("keydown", event.key, event.keyCode);
      
      if(event.keyCode == 13){
        console.log(`input.value:${input.value}`);
        doRetrieve();
      }
    });
    // 검색창 엔터 후 이벤트 끝
    // 회원 상세 정보 버튼 클릭 시작
    userInfoBtns.forEach(function(userInfoBtn){ 
    	 userInfoBtn.addEventListener('click', function(){
            // a태그 동작방지
            event.preventDefault();
            console.log("button click!");
            
            let tr = this.closest('tr'); // 클릭된 링크의 부모 <tr>을 찾습니다.
            let userIdEl = tr.querySelector('td.user_id'); // <tr>에서 user_id를 찾습니다.
            
            if (userIdEl) {
                 let userId = userIdEl.textContent.trim(); // userIdEl의 텍스트 값을 가져옵니다.
                 console.log('선택된 userId:', userId);
                 
                 doSelectOne(userId);
                 
                 // 여기에 userId 값을 활용하는 추가적인 작업을 수행할 수 있습니다.
            } else {
                 console.error('해당 회원의 정보를 찾을 수 없습니다. 잘못된 접근입니다.');
                 alert('잘못된 접근입니다!');
            }      
            
          }); // click
        }); // forEach
    // 회원 상세 정보 버튼 클릭 끝    
    // 이벤트 핸들러 끝
    
    // 함수 시작
    function moveToMuser(){ // 회원관리페이지이동 함수
    	console.log("userbtn");
      window.location.href = "/IKUZO/ikuzo/manage01.ikuzo?work_div=doRetrieve";
    }    
    function moveToMbook(){ // 도서관리페이지이동 함수
    	console.log("bookbtn");
      window.location.href = "/IKUZO/ikuzo/manage02.ikuzo?work_div=doRetrieve";
    }    
    
    // 회원 삭제 함수 시작
    function doDeleteUser(){        
        console.log('doDeleteUser');
        workDiv.value = 'doDelete';

        // 각 행을 반복 처리합니다
        rows.forEach(function(row) {
            // 현재 행의 체크박스를 찾습니다
            const checkbox = row.querySelector("td.checkbox input.chk");

            // 체크박스가 체크되어 있는지 확인합니다
            if (checkbox.checked) {
                // 현재 행의 user_id를 찾습니다
                userId = row.querySelector("td.user_id").innerText;
                userIds.push(userId);
            }
        });

        // userIds를 출력합니다 (필요에 따라 이 배열을 사용할 수 있습니다)
        console.log(userId);        
        console.log(userIds);        
        
        // userIds 체크 여부
        if(isEmpty(userIds) == true){
            alert('체크된 회원이 존재하지 않습니다. 잘못된 경로!');
        }else if(false == confirm('삭제 하시겠습니까?')){
        	  return;
        }  
        
        // ajax start
        $.ajax({
            type: "GET", 
            url:"/IKUZO/ikuzo/manage01.ikuzo",
            asyn:"true",
            dataType:"html",
            data:{
                "work_div":"doDelete",
                "userIds": userId
            },
            success:function(response){//통신 성공
                console.log("success response:"+response);
                const messageVO = JSON.parse(response);
                console.log("messageVO.messageId:"+ messageVO.messageId);             
                console.log("messageVO.msgContents:"+ messageVO.msgContents);  
                
                if(isEmpty(messageVO) == false && "1" === messageVO.messageId){
                  alert(messageVO.msgContents);
                  window.location.href = "/IKUZO/ikuzo/manage01.ikuzo?work_div=doRetrieve";
                }else{
                  alert(messageVO.msgContents);
                }
            },
            error:function(data){//실패시 처리
                    console.log("error:"+data);
            }
        });    
      }
    // 회원 삭제 함수 끝
    // 회원 정보 함수 시작
    function doSelectOne(userId){
      // 폼 요소 선택
      let frm = document.getElementById("manage_user_frm");
      
      // 폼 데이터 설정
      frm.work_div.value = "doSelectOne";
      
      //seq
      frm.seq.value = userId;
      frm.action = "<%=cPath%>" + "/ikuzo/manage01.ikuzo";
      
      // 폼 제출
      frm.submit();
    }
    // 회원 정보 함수 끝
    // 조회 함수 시작
    function doRetrieve(){
      console.log("doRetrieve");
      
      // 폼 요소 선택
      let frm = document.getElementById("manage_user_frm"); 
      frm.work_div.value = "doRetrieve";
      frm.page_no.value = "1";
      
      console.log("frm.search_div.value : " + frm.search_div.value);
      console.log("frm.search_word.value : " + frm.search_word.value);
      console.log("frm.page_size.value : " + frm.page_size.value);
      
      // 서버로 보낼 액션 설정
      console.log("frm.action : " + "<%=cPath%>" + "/ikuzo/manage01.ikuzo");
      frm.action = "<%=cPath%>" + "/ikuzo/manage01.ikuzo";
      
      // 폼 제출
      frm.submit();   
      
    }
    // 조회 함수 끝
    // 함수 끝
});         
// 페이징 조회 시작
function pageRetrieve(url, pageNo){
  console.log("url : " + url);
  console.log("pageNo : " + pageNo);
  
  // 폼 요소 선택
  let frm = document.getElementById("manage_user_frm");   
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
<section class="container">
    <div class="inner-container">
       <div class="page-title-group">
          <h2 class="page-title">관리자 페이지</h2>
       </div>
        
    <div class="page-list-group">
        <div class="page-list-inner">
            <div class="active">
                <a href="#" id ="manageUserbtn">회원 관리</a>
            </div>
            <div>
                <a href="#" id ="manageBookbtn">도서 관리</a>
            </div>
        </div>    
    </div>

<div class="category-box">
    <div class="category-wrap">
        <div class="redAlert">
            <a href="#" id ="deletebtn">삭제</a>
        </div>
    </div>

    <div class="category-wrap category-wrap2">
    <p>searchDiv : <%=searchCon.getSearchDiv()%></p>
    <p>page : ${page}</p>
        <form action="<%=cPath%>/ikuzo/manage01.ikuzo" method="get" name = "manage_user_frm" id = "manage_user_frm">
        <input type = "hidden" name = "work_div" id = "work_div">
        <input type="hidden" name="page_no" id="page_no" placeholder="페이지 번호">
        <input type="hidden" name="seq" id="seq" placeholder="순번">
		        <select style = "cursor : pointer;" name = "page_size" id="page_size">
		            <option value="10" >10페이지</option>
                <option value="20" >20페이지</option>
                <option value="30" >30페이지</option>
                <option value="40" >40페이지</option>
			      </select>
			      <select style = "cursor : pointer;" name = "is_admin" id="is_admin">
                <option value="" selected>관리자 여부</option>
                <option value="10" >관리자</option>
                <option value="20" >회원</option>
            </select>
            <select style = "cursor : pointer;" name="search_div" id = "search_div">
                <option value="" selected="selected">전체</option>
                <option value="10" >아이디</option>
                <option value="20" >이름</option>
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
            <th class="user_id">아이디</th>
            <th class="user_name">이름</th>
            <th class="user_group">그룹</th>
            <th class="now_rent_yn">미납도서 유무</th>
            <th class="count">연체금액</th>
            <th class="info">상세정보</th>
        </tr>
    </thead>
    <tbody>
    <%
    for(ManageUserDTO vo :list) {
    %>
        <tr>
          <td class="checkbox"><input type="checkbox" class="chk"></td>
          <td class="rnum"><%=vo.getRnum()%></td>
          <td class="user_id"><%=vo.getUserId()%></td>
          <td class="user_name"><%=vo.getUserName()%></td>
          <td class="user_group"><%=vo.getIsAdmin()%></td>
          <td class="now_rent_yn"><%=vo.getRentBookYn()%></td>
          <td class="count"><%=vo.getExtraSum()%></td>
          <td class="profile_edit">
                <a href="" class="active">수정</a>
            </td>
        </tr>  
    <% } %>           
    </tbody>
</table>

<!-- paging start -->
<div aria-label="Page navigation example" style = "text-align : center;">
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
out.print(StringUtil.renderingPaging(totalCnt, pageNo, pageSize, bottomCnt, "/IKUZO/ikuzo/manage01.ikuzo", "pageRetrieve"));
%>  
</div>
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