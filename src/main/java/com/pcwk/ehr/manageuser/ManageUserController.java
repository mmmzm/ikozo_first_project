package com.pcwk.ehr.manageuser;

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

/**
 * Servlet implementation class BoardController
 */
//@WebServlet(description = "게시판 컨트롤러", urlPatterns = { "/board/board.do" })
public class ManageUserController extends HttpServlet implements ControllerV, PLog{
	private static final long serialVersionUID = 1L;
    
	ManageUserService service;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageUserController() {
		log.debug("-----------------");
    	log.debug("manage01Controller()");
    	log.debug("-----------------");
    	
    	service = new ManageUserService();
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
    	String isAdmin = StringUtil.nvl(request.getParameter("is_admin"), ""); 
    	
    	log.debug("pageNo : {}", pageNo);
    	log.debug("pageSize : {}", pageSize);
    	log.debug("searchWord : {}", searchWord);
    	log.debug("searchDiv : {}", searchDiv);
    	log.debug("isAdmin : {}", isAdmin);
    	
    	inVO.setPageNo(Integer.parseInt(pageNo));
    	inVO.setPageSize(Integer.parseInt(pageSize));
    	inVO.setSearchDiv(searchDiv);
    	inVO.setSearchWord(searchWord);
    	inVO.setIsAdmin(isAdmin);

    	log.debug("inVO : {}", inVO);
    	
    	// service call
    	List<ManageUserDTO> list = service.doRetrieve(inVO);
    	
    	// reutrn 데이터 확인
    	int i = 0;
    	for (ManageUserDTO vo : list) {
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
			ManageUserDTO pagingVO = list.get(0);
			totalCnt = pagingVO.getTotalCnt();
			log.debug("totalCnt : {}", totalCnt);	
			
			inVO.setTotalCnt(totalCnt);
		}    	
		
		inVO.setBottomCount(bottomCount);
    	
		// 검색조건 UI로 전달
    	request.setAttribute("vo", inVO); 
    	log.debug("inVO : {}", inVO);	
    	return viewName = new JView("/jsp/manage01.jsp");
	}
	
	// doDelete HttpServlet에 존재해서 doDel로 메서드 이름 변경
	public JView doDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		log.debug("-----------------");
    	log.debug("doDel()");
    	log.debug("-----------------");
    	
    	ManageUserDTO inVO = new ManageUserDTO();
    	String userIds = StringUtil.nvl(request.getParameter("userIds"), "0");
    	
    	inVO.setUserId(userIds);
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
	
	public JView doUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		log.debug("-----------------");
		log.debug("doUpdate()");
		log.debug("-----------------");
		
		ManageUserDTO inVO = new ManageUserDTO();
		String userIds = StringUtil.nvl(request.getParameter("userIds"), "0");
		
		inVO.setUserId(userIds);
		log.debug("inVO" + inVO);
		
		int flag = service.doUpdate(inVO);
		log.debug("flag : {}", flag);
		
		String message = "";
		if (1 == flag) {
			message = "반납 완료";
		}else {
			message = "반납 업데이트 실패입니다";
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
	
	public JView doSelectOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		log.debug("-----------------");
    	log.debug("doSelectOne()");
    	log.debug("-----------------");
    	ManageUserDTO inVO = new ManageUserDTO();
    	String seq = StringUtil.nvl(request.getParameter("seq"), "0");
    	
    	inVO.setUserId(seq);
    	log.debug("inVO" + inVO);
    	
    	ManageUserDTO outVO = this.service.doSelectOne(inVO);
    	log.debug("outVO" + outVO);
    	
    	// UI 데이터 전달
    	request.setAttribute("outVO", outVO);
    	
		return new JView("/jsp/manage_user_info.jsp");
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
    	case "doRetrieve":
    		viewName = doRetrieve(request, response);
    		break;  
    	case "doDelete":
    		viewName = doDel(request, response);
    		break;
    	case "doUpdate":
    		viewName = doUpdate(request, response);
    		break;
    	case "doSelectOne":
			viewName = doSelectOne(request, response);
			break;

		default:
			log.debug("작업구분을 확인하세요. workDiv : {}", workDiv);
			break;
		}
    	
    	return viewName;
	}

} // CLASS
