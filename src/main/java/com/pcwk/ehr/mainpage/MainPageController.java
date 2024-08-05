package com.pcwk.ehr.mainpage;

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
//메인 페이지 컨트롤러
public class MainPageController extends HttpServlet implements ControllerV, PLog{
	private static final long serialVersionUID = 1L;
    
	MainPageService service;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainPageController() {
		log.debug("-----------------");
    	log.debug("MainPageController()");
    	log.debug("-----------------");
    	
    	service = new MainPageService();
    }
    
	public JView doRetrieve(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("-----------------");
    	log.debug("WelcomeToIndex()");
    	log.debug("-----------------");
    	
		return new JView("/jsp/index.jsp");
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

		default:
			log.debug("작업구분을 확인하세요. workDiv : {}", workDiv);
			break;
		}
    	
    	return viewName;
	}

} // CLASS
