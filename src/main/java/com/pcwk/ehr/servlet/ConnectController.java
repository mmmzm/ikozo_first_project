package com.pcwk.ehr.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pcwk.ehr.board.BoardDTO;
import com.pcwk.ehr.cmn.ControllerV;
import com.pcwk.ehr.cmn.JView;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.StringUtil;

/**
 * Servlet implementation class BoardController
 */
public class ConnectController extends HttpServlet implements PLog,ControllerV{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnectController() {
    	log.debug("---------------------");
        log.debug("ConnectController()");
        log.debug("---------------------");
    }
    
    
    //GET방식 호출
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("---------------------");
        log.debug("doGet()");
        log.debug("---------------------");
		doWork(req, resp);
	}
	
	//POST방식 호출
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("---------------------");
        log.debug("doPost()");
        log.debug("---------------------");
		doWork(req, resp);
	}
	
	/**
	 * get/post 방식 호출을 처리
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	public JView doWork(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("---------------------");
        log.debug("doWork()");
        log.debug("---------------------");
        
        JView viewName = null;
        
        //request 인코딩 처리:
        request.setCharacterEncoding("UTF-8");
        
        String workDiv = StringUtil.nvl(request.getParameter("work_div"), "");
        
        
        log.debug("workDiv : {}",workDiv);
        
        switch (workDiv) {
		case "doRetrieve":
			doRetrieve(request, response);
			break;
		case "doSave":
			doSave(request, response);
			break;
		default:
			log.debug("작업구분을 확인 하세요."+workDiv);
			break;
		}
//        if(workDiv.equals("doRetrieve")) {
//        	doRetrieve(request, response);
//        }else if(workDiv.equals("doSave")){
//        	doSave(request, response);
//        }else {
//        	log.debug("작업구분을 확인 하세요."+workDiv);
//        }
        
        return viewName;
	}
	
	public void doSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("---------------------");
        log.debug("doSave()");
        log.debug("---------------------");
	}

	public JView doRetrieve(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("---------------------");
        log.debug("doRetrieve()");
        log.debug("---------------------");
        
        //JSP veiwName 전달
    	JView viewName = null;
        
	    String userNm = request.getParameter("user_nm");
	    log.debug("user_nm : {}",userNm);
	    
	    //Model: Service객체
	    
	    
	    //UI전달 데이터 설정
	    request.setAttribute("userNm", userNm+"님");
	    
//	    RequestDispatcher dispacher = request.getRequestDispatcher("/ajsp/j02/j04_connect_response.jsp");
//	    dispacher.forward(request, response);
	    
	    return viewName = new JView("/ajsp/j02/j04_connect_response.jsp");
	}
}
