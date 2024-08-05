<%@page import="com.pcwk.ehr.cmn.StringUtil"%>
<%@page import="com.pcwk.ehr.cmn.SearchDTO"%>
<%@page import="com.pcwk.ehr.mypage.LoanListDTO"%>
<%@page import="com.pcwk.ehr.mypage.LoanListDao"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
<%@ include file="/jsp/common.jsp" %>
<%
LoanListDao dao = new LoanListDao();
SearchDTO searchVO = new SearchDTO();
searchVO.setPageNo(1);
searchVO.setPageSize(10);
List<LoanListDTO> list = (List<LoanListDTO>)request.getAttribute("list");
SearchDTO searchCon = (SearchDTO)request.getAttribute("vo");
%>  
<%-- searchCon:<%=searchCon%> --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>북북 도서관</title>
<link rel="stylesheet" href="/IKUZO/assest/css/bookbook.css">
<link rel="stylesheet" href="/IKUZO/assest/css/book_manage.css">
<script src="/IKUZO/assest/js/jquery_3_7_1.js"></script>

<!-- 호출 -->  
<script>
document.addEventListener("DOMContentLoaded", function(){
  //조회버튼 객체생성
  const doRetrieveBtn = document.querySelector("#doRetrieve");
  
  //html객체를 자바스크립트에서 생성
  const searchWord = document.querySelector("#search_word");
  
  //이벤트 핸들러
  doRetrieveBtn.addEventListener("click", function(event){
      console.log('doRetrieveBtn click');
      doRetrieve();
  });//--doRetrieveBtn.addEventListener end
  
  searchWord.addEventListener("keydown", function(event){
	    console.log('keydown');
	    
	   if(event.keyCode===13){//13:엔터값(엔터값과 클릭이 같게 설정)
	      doRetrieve();
	    }
	   
	 });//--searchWord.addEventListener end
  
});//--addEventListener "DOMContentLoaded" end

function doRetrieve(){
  console.log("doRetrieve()");
  
	//폼 요소 선택
  let frm = document.getElementById("my_page_frm");

  //폼 데이터 설정
  frm.work_div.value = "doRetrieve";
  frm.page_no.value = "1";
  
  //form에 action 속성
  frm.action="<%=cPath%>"+"/ikuzo/mypage01.ikuzo";
  
  //폼 제출
  frm.submit();
}  //--function doRetrieve() end

//페이징 조회 함수 만들기
function pageRetrieve(url,pageNo){
    console.log("url"+url);
    console.log("pageNo"+pageNo);
    
  //폼 요소 선택
   let frm = document.getElementById("my_page_frm");
   frm.work_div.value = "doRetrieve";
   
   //폼 데이터 설정
   frm.page_no.value = pageNo;
   
   //url
   frm.action=url;
   
   //폼 제출
   frm.submit();
 }//--function pageRetrieve() end
 
</script>
</head>

<body>
<!-- header 시작  -->  
<%@ include file="/jsp/header.jsp" %>
<!-- header 끝  -->
  
<!-- container -->
<section class="container">
    <div class="inner-container">
       <div class="page-title-group">
          <h2 class="page-title">마이 페이지</h2>
       </div>

        
    <div class="page-list-group">
        <div class="page-list-inner">
            <div class="active">
                <a href="myPage01.jsp">대출 목록</a>
            </div>
            <div>
                <a href="myPage02.jsp">즐겨 찾기</a>
            </div>
        </div>    
    </div>

<div class="category-box">
   

    <div class="category-wrap category-wrap2" style = "float : right;">
        <form action="#" id="my_page_frm">
        <input type="hidden" name="work_div" id="work_div" placeholder="작업구분">
        <input type="hidden" name="page_no" id="page_no" placeholder="페이지번호">

            <select name="search_div" id="search_div">
                <option value="" selected="selected">전체</option>
                <option value="10">도서제목</option>
                <option value="20">장르</option>
                <option value="30">작가</option>
            </select>
            <input type="search" name="search_word" id="search_word" placeholder="검색어를 입력해주세요" 
                    value="<%if(null != searchCon){out.print(searchCon.getSearchWord());} %>" >
       
       
        </form>
        
        <!-- 버튼 -->
        <button type="button" class="btn-control" id="doRetrieve">
            <span class="icon"></span>
        </button>   
        <!--// 버튼 ----------------------------------------------------------------->
    </div>
    
</div>

<table class="notice-board">
    <thead>
        <tr>
            <th class="rent_num">no</th>
            <th class="rent_book">도서제목</th>
            <th class="rent_genre">장르</th>
            <th class="rent_author">작가</th>
            <th class="rent_date">대출날짜</th>
            <th class="due_date">반납예정일</th>
            <th class="extra_sum">연체금액</th>
        </tr>
    </thead>
    <tbody>
  
     <%
     if(null!=list&&list.size()>0){
       for(LoanListDTO vo :list) {
       %>
       
        <tr>
            <td class="rent_num"><%=vo.getNum()%></td>  
            <td class="user_id"><%=vo.getBookName()%></td>
            <td class="user_name"><%=vo.getGenreName()%></td>
            <td class="rent_author"><%=vo.getAuthor()%></td>
            <td class="rent_date"><%=vo.getRentDate()%></td>
            <td class="due_date"><%=vo.getDueDate()%></td>
            <td class="extra_sum"><%=vo.getExtraSum()%></td>
        </tr>
        
      <% }
       }
       %>  
                                    
    </tbody>
</table>

<!-- paging -->
<div aria-label="Page navigation example" style = "text-align : center;">

  <%
     //총글수 가져오기
     SearchDTO pagingVO = (SearchDTO)request.getAttribute("vo");
     int totalCnt = pagingVO.getTotalCnt();
     //바닥 글수
     int bottomCnt = pagingVO.getBottomCount();        
     //페이지사이즈
     int pageSize = pagingVO.getPageSize();
     //페이지번호
     int pageNo = pagingVO.getPageNo();
     //pageRetrieve(url,2)
     out.print(StringUtil.renderingPaging(totalCnt, pageNo, pageSize, bottomCnt, "/IKUZO/ikuzo/mypage01.ikuzo", "pageRetrieve"));
   %>
      

</div>
<!--// paging end --------------------------------------------------------->

</div>
</section>
<!-- //container -->

<!-- footer 시작  -->
<%@ include file="footer.jsp" %>
<!-- footer 끝  -->

</body>
</html>