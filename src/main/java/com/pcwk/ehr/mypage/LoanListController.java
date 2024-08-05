package com.pcwk.ehr.mypage;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pcwk.ehr.board.BoardDTO;
import com.pcwk.ehr.cmn.ControllerV;
import com.pcwk.ehr.cmn.JView;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.cmn.StringUtil;


/**
 * Servlet implementation class LoanListController
 */
//각 화면마다 컨트롤러 필요
//@WebServlet("/LoanListController")
public class LoanListController extends HttpServlet implements ControllerV, PLog{
	private static final long serialVersionUID = 1L;
	
	MypageService service;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoanListController() {
    	log.debug("---------------------");
    	log.debug("LoanListController()");
    	log.debug("---------------------");
    	
    	service = new MypageService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("---------------------");
    	log.debug("doGet()");
    	log.debug("---------------------");
    	doWork(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("---------------------");
    	log.debug("doPost()");
    	log.debug("---------------------");
    	doWork(request,response);
	}

	public JView doRetrieve(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("---------------------");
    	log.debug("doRetrieve()");
    	log.debug("---------------------");
    	HttpSession session = request.getSession();
    	
    	//JSP viewName 저장(경로)
    	JView viewName = null;
    	
    	SearchDTO inVO = new SearchDTO();
    	
    	String pageNo = StringUtil.nvl(request.getParameter("page_no"),"1");
    	String pageSize = StringUtil.nvl(request.getParameter("page_size"),"10");
    	String searchDiv = StringUtil.nvl(request.getParameter("search_div"),"");
    	String searchWord = StringUtil.nvl(request.getParameter("search_word"),"");
    	
    	log.debug("pageNo:{}",pageNo);
    	log.debug("pageSize:{}",pageSize);
    	log.debug("searchDiv:{}",searchDiv);
    	
    	inVO.setPageNo(Integer.parseInt(pageNo));
    	inVO.setPageSize(Integer.parseInt(pageSize));
    	inVO.setSearchDiv(searchDiv);
    	inVO.setSearchWord(searchWord);
    	log.debug("inVO:{}",inVO);
    	
    	//service call
    	List<LoanListDTO> list = service.doRetrieve(inVO);
    	
    	//리턴 데이터 확인
    	int i=0;
		for(LoanListDTO vo : list) {
			log.debug("i:{}, vo:{}", ++i, vo);
		}
		
		//UI 데이터 전달
		request.setAttribute("list", list);
		
		int bottomCount = 10;
		int totalCnt = 0;//총글수
		
		if(null!=list && list.size()>0) {
			LoanListDTO pagingVO = list.get(0);
			totalCnt = pagingVO.getTotalCnt();
			log.debug("totalCnt", totalCnt);
			
			//inVO에 totalCnt 담기
			inVO.setTotalCnt(totalCnt);
		}
		
		inVO.setBottomCount(bottomCount);
				
		//검색조건 UI로 전달
		request.setAttribute("vo", inVO);
		
		return viewName = new JView("/jsp/myPage01.jsp");
    	
	}	
	@Override
	public JView doWork(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("---------------------");
    	log.debug("doWork()");
    	log.debug("---------------------");
    	
    	JView viewName = null;
    	
    	String workDiv = StringUtil.nvl(request.getParameter("work_div"),"");
    	log.debug("workDiv:{}",workDiv);
    	
    	switch(workDiv) {
    	case "doRetrieve":
    		viewName = doRetrieve(request,response);
    		break;
    		
    	default:
			log.debug("작업구분을 확인하세요. workDiv:{}",workDiv);
			break;
    	
    	}
    	
    	return viewName;
    	
    	
	}//doWork end

}//class end
