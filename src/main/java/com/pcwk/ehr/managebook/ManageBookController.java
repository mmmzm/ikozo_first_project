package com.pcwk.ehr.managebook;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.ControllerV;
import com.pcwk.ehr.cmn.JView;
import com.pcwk.ehr.cmn.MessageVO;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.cmn.StringUtil;
import com.pcwk.ehr.login.LoginDTO;

/**
 * Servlet implementation class BoardController
 */
//@WebServlet(description = "게시판 컨트롤러", urlPatterns = { "/board/board.do" })
public class ManageBookController extends HttpServlet implements ControllerV, PLog{
	private static final long serialVersionUID = 1L;
    
	ManageBookService service;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageBookController() {
		log.debug("-----------------");
    	log.debug("manage02Controller()");
    	log.debug("-----------------");
    	
    	service = new ManageBookService();
    }
    
	public JView doRetrieve(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("-----------------");
    	log.debug("doRetrieve()");
    	log.debug("-----------------");
    	
    	// JSP viewName 저장(경로)
    	JView viewName = null;
    	
    	HttpSession session = request.getSession();
    	
    	SearchDTO inVO = new SearchDTO();
    	//http://localhost:8080/WEB02/board/board.do?work_div=doRetrieve&page_no=2&page_size=20
    	//page_no
    	//page_size
    	String pageNo = StringUtil.nvl(request.getParameter("page_no"), "1");
    	String pageSize = StringUtil.nvl(request.getParameter("page_size"), "10");
    	
    	String searchWord = StringUtil.nvl(request.getParameter("search_word"), "");    	
    	String searchDiv = StringUtil.nvl(request.getParameter("search_div"), "");    	
    	String rentYn = StringUtil.nvl(request.getParameter("rent_yn"), "");    	
    	
    	log.debug("pageNo : {}", pageNo);
    	log.debug("pageSize : {}", pageSize);
    	log.debug("searchWord : {}", searchWord);
    	log.debug("searchDiv : {}", searchDiv);
    	log.debug("rentYn : {}", rentYn);
    	
    	inVO.setPageNo(Integer.parseInt(pageNo));
    	inVO.setPageSize(Integer.parseInt(pageSize));
    	inVO.setSearchDiv(searchDiv);
    	inVO.setSearchWord(searchWord);
    	inVO.setRentYn(rentYn);

    	log.debug("inVO : {}", inVO);
    	
    	// service call
    	List<ManageBookDTO> list = service.doRetrieve(inVO);
    	
    	// reutrn 데이터 확인
    	int i = 0;
    	for (ManageBookDTO vo : list) {
    		log.debug("i : {}, vo : {}", ++i, vo);			
		}
    	
    	// UI 데이터 전달
    	request.setAttribute("list", list);
    	
    	// 검색조건 UI로 전달
    	request.setAttribute("vo", inVO);  
		
		// RequestDispatcher dispacher = request.getRequestDispatcher("/board/board_list.jsp");
		// dispacher.forward(request, response);		     
    	
    	// paging : 총글수 totalCnt
		// currentpageNo : pageNo
		// rowPerPage : pageSize
		// bottomCount : 10    	
	
		int bottomCount = 10;
	
		int totalCnt = 0; // 총글수
		
		if (null != list && list.size() > 0) {
			ManageBookDTO pagingVO = list.get(0);
			totalCnt = pagingVO.getTotalCnt();
			log.debug("totalCnt : {}", totalCnt);	
			
			inVO.setTotalCnt(totalCnt);
		}  
    	
		inVO.setBottomCount(bottomCount);
		
		// 검색조건 UI로 전달
    	request.setAttribute("vo", inVO); 
    	log.debug("inVO : {}", inVO);
    	return viewName = new JView("/jsp/manage02.jsp");
	}
	
	private JView doSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("-----------------");
		log.debug("doSave()");
		log.debug("-----------------");
		ManageBookDTO inVO = new ManageBookDTO();
		String bookName = request.getParameter("bookName");
		String bookGenreStr = request.getParameter("bookGenre");
		int bookGenre = Integer.parseInt(bookGenreStr); // String을 int로 변환
		String isbn = StringUtil.nvl(request.getParameter("isbn"), "");
		String bookPubDate = StringUtil.nvl(request.getParameter("bookPubDate"), "");
		String publisher = StringUtil.nvl(request.getParameter("publisher"), "");
		String author = StringUtil.nvl(request.getParameter("author"), "");
		String bookInfo = StringUtil.nvl(request.getParameter("bookInfo"), "");
		
//		String regId = StringUtil.nvl(request.getParameter("regId"), ""); // 로그인 기능 없을때 사용 
//		String modId = StringUtil.nvl(request.getParameter("modId"), ""); // 로그인 기능 없을때 사용
		
		HttpSession session = request.getSession();
		log.debug("login account check : {}", session.getAttribute("user"));
		
		String regId = "";
		if(session != null && null != session.getAttribute("user")) { LoginDTO
		member = (LoginDTO) session.getAttribute("user"); regId =
		member.getUserId(); }else {
		 
		}
		 
		
		log.debug("bookName :{} ", bookName);
		log.debug("isbn : {}", isbn);
		log.debug("bookPubDate : {}", bookPubDate);
		log.debug("publisher : {}", publisher);
		log.debug("author : {}", author);
		log.debug("bookInfo : {}", bookInfo);
		
		inVO.setBookName(bookName);
		inVO.setBookGenre(bookGenre);
		inVO.setIsbn(isbn);
		inVO.setBookPubDate(bookPubDate);
		inVO.setPublisher(publisher);
		inVO.setAuthor(author);
		inVO.setBookInfo(bookInfo);
		inVO.setRegId(regId);
		inVO.setModId(regId);
		log.debug("inVO :{} ", inVO);
		int flag = this.service.doSave(inVO);
		log.debug("flag : {}", flag);
		
		String message = "";
		if(flag == 1) {
			message = "등록성공";
		}else {
			message = "등록실패";			
		}
		
		MessageVO messageVO = new MessageVO();
		messageVO.setMessageId(String.valueOf(flag));
		messageVO.setMsgContents(message);
		
		log.debug(message);
		
		Gson gson = new Gson();
		String jsonString = gson.toJson(messageVO);
		
		log.debug("jsonString : {}", jsonString);
		
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.print(jsonString);
		
		return null;
	}
	
	// doDelete HttpServlet에 존재해서 doDel로 메서드 이름 변경
	public JView doDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		log.debug("-----------------");
    	log.debug("doDel()");
    	log.debug("-----------------");
    	
    	ManageBookDTO inVO = new ManageBookDTO();
    	String bookCodesStr = StringUtil.nvl(request.getParameter("bookCodes"), "0");
    	int bookCodes = Integer.parseInt(bookCodesStr); // String을 int로 변환
    	
    	inVO.setBookCode(bookCodes);
    	log.debug("inVO" + inVO);
    	
    	int flag = service.doDelete(inVO);
    	log.debug("flag : {}", flag);
    	
    	String message = "";
    	if (1 == flag) {
			message = "삭제 성공";
		}else {
			message = "삭제 실패입니다";
		}
    	
    	MessageVO messageVO = new MessageVO();    	
    	messageVO.setMessageId(String.valueOf(flag));
    	messageVO.setMsgContents(message);
    	log.debug("messageVO : {}", messageVO);
    	
    	Gson gson = new Gson();
    	String jsonString = gson.toJson(messageVO);
    	log.debug("jsonString : {}", jsonString);
    	
    	response.setContentType("text/html; charset=UTF-8");
    	
    	PrintWriter out = response.getWriter();
    	out.print(jsonString);
    	
		return new JView("");
	}
	
	private JView moveToSaveBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("-----------------");
    	log.debug("moveToSaveBook()");
    	log.debug("-----------------");
    	
		return new JView("/jsp/manage_book_save.jsp");
	}
	
	public JView doSelectOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		log.debug("-----------------");
    	log.debug("doSelectOne()");
    	log.debug("-----------------");
    	ManageBookDTO inVO = new ManageBookDTO();
    	String seq = StringUtil.nvl(request.getParameter("seq"), "0");
    	
    	inVO.setBookCode(Integer.parseInt(seq));
    	log.debug("inVO" + inVO);
    	
    	ManageBookDTO outVO = this.service.doSelectOne(inVO);
    	log.debug("outVO" + outVO);
    	
    	// UI 데이터 전달
    	request.setAttribute("outVO", outVO);
    	
		return new JView("/jsp/manage_book_update.jsp");
	}
	
	public JView doUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		log.debug("-----------------");
    	log.debug("doUpdate()");
    	log.debug("-----------------");
		
		ManageBookDTO inVO = new ManageBookDTO();
		String bookCode = StringUtil.nvl(request.getParameter("bookCode"), "0");
		String bookName = StringUtil.nvl(request.getParameter("bookName"), "");
		String bookGenre = StringUtil.nvl(request.getParameter("bookGenre"), "");
		String isbn = StringUtil.nvl(request.getParameter("isbn"), "");
		String bookPubDate = StringUtil.nvl(request.getParameter("bookPubDate"), "");
		String publisher = StringUtil.nvl(request.getParameter("publisher"), "");
		String author = StringUtil.nvl(request.getParameter("author"), "");
		String bookInfo = StringUtil.nvl(request.getParameter("bookInfo"), "");
		
//		String modId = StringUtil.nvl(request.getParameter("modId"), ""); // 로그인 기능 없을때 사용
    	
		HttpSession session = request.getSession();
		log.debug("login account check : {}", session.getAttribute("user"));
		
		String modId = "";
		if(session != null && null != session.getAttribute("user")) {
			LoginDTO member = (LoginDTO) session.getAttribute("user");
			modId = member.getUserId();
		}
		else{
		
		}
		
		inVO.setBookCode(Integer.parseInt(bookCode));
		inVO.setBookName(bookName);
		inVO.setBookGenre(Integer.parseInt(bookGenre));
		inVO.setIsbn(isbn);
		inVO.setBookPubDate(bookPubDate);
		inVO.setPublisher(publisher);
		inVO.setAuthor(author);
		inVO.setBookInfo(bookInfo);
		inVO.setModId(modId);
		
    	log.debug("inVO" + inVO);
    	
    	int flag = service.doUpdate(inVO);
    	log.debug("flag : {}", flag);
    	
    	String message = "";
    	if (1 == flag) {
			message = "수정 성공";
		}else {
			message = "수정 실패입니다";
		}
		
    	MessageVO messageVO = new MessageVO();
    	messageVO.setMessageId(String.valueOf(flag));
    	messageVO.setMsgContents(message);
    	
    	Gson gson = new Gson();
    	String jsonString = gson.toJson(messageVO);
    	
    	log.debug("jsonString : " + jsonString);
    	response.setContentType("text/html; charset=UTF-8");
    	
    	PrintWriter out = response.getWriter();
    	out.print(jsonString);
    	
		return new JView("");
	}
	
	public JView doDueDateUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		log.debug("-----------------");
		log.debug("doDueDateUpdate()");
		log.debug("-----------------");
		
		ManageBookDTO inVO = new ManageBookDTO();
		String rentCode = StringUtil.nvl(request.getParameter("rentCode"), "0");
		
		HttpSession session = request.getSession();
		log.debug("login account check : {}", session.getAttribute("member"));
		
		String modId = "";
		
		if(session != null && null != session.getAttribute("user")) { LoginDTO
		member = (LoginDTO) session.getAttribute("user"); modId =
		member.getUserId(); }else {
		
		}		
		
		inVO.setModId(modId);
		inVO.setRentCode(Integer.parseInt(rentCode));
		
		log.debug("inVO" + inVO);
		
		int flag = service.doDueDateUpdate(inVO);
		log.debug("flag : {}", flag);
		
		String message = "";
		if (1 == flag) {
			message = "연장 성공";
		}else {
			message = "연장 오류 입니다";
		}
		
		MessageVO messageVO = new MessageVO();
		messageVO.setMessageId(String.valueOf(flag));
		messageVO.setMsgContents(message);
		
		Gson gson = new Gson();
		String jsonString = gson.toJson(messageVO);
		
		log.debug("jsonString : " + jsonString);
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.print(jsonString);
		
		return new JView("");
	}
	
	public JView doReturned(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		log.debug("-----------------");
		log.debug("doReturned()");
		log.debug("-----------------");
		
		ManageBookDTO inVO = new ManageBookDTO();
		String rentCode = StringUtil.nvl(request.getParameter("rentCode"), "0");
		
		HttpSession session = request.getSession();
		log.debug("login account check : {}", session.getAttribute("member"));
		
		String modId = "";
		
		if(session != null && null != session.getAttribute("user")) { LoginDTO
		member = (LoginDTO) session.getAttribute("user"); modId =
		member.getUserId(); }else {
		
		}		
		
		inVO.setModId(modId);
		inVO.setRentCode(Integer.parseInt(rentCode));
		
		log.debug("inVO" + inVO);
		
		int flag = service.doReturned(inVO);
		log.debug("flag : {}", flag);
		
		String message = "";
		if (1 == flag) {
			message = "반납 성공";
		}else {
			message = "반납 오류 입니다";
		}
		
		MessageVO messageVO = new MessageVO();
		messageVO.setMessageId(String.valueOf(flag));
		messageVO.setMsgContents(message);
		
		Gson gson = new Gson();
		String jsonString = gson.toJson(messageVO);
		
		log.debug("jsonString : " + jsonString);
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.print(jsonString);
		
		return new JView("");
	}
	
	@Override
	public JView doWork(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("-----------------");
    	log.debug("doWork()");
    	log.debug("-----------------");
    	
    	JView viewName = null;
    	
    	String workDiv = StringUtil.nvl(request.getParameter("work_div"), "");
    	
    	log.debug("workDiv : {}", workDiv);
    	
    	switch (workDiv) {
    	case "moveToSaveBook":
    		viewName = moveToSaveBook(request, response);
    		break;
    	case "doDelete":
    		viewName = doDel(request, response);
    		break; 	
    	case "doSave":
    		viewName = doSave(request, response);
    		break;   
		case "doRetrieve":
			viewName = doRetrieve(request, response);
			break;
		case "doSelectOne":
			viewName = doSelectOne(request, response);
			break;
		case "doUpdate":
    		viewName = doUpdate(request, response);
    		break;	
		case "doDueDateUpdate":
			viewName = doDueDateUpdate(request, response);
			break;	
		case "doReturned":
			viewName = doReturned(request, response);
			break;	

		default:
			log.debug("작업구분을 확인하세요. workDiv : {}", workDiv);
			break;
		}
    	
    	return viewName;
	}

} // CLASS
