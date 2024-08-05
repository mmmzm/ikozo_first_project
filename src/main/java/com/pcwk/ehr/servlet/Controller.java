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
 * Servlet implementation class Controller
 */
//@WebServlet("/connect/connect.do")
public class Controller extends HttpServlet implements ControllerV, PLog{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
    	log.debug("-----------------");
    	log.debug("ConnectController()");
    	log.debug("-----------------");
    }

    // Get방식 호출
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.debug("-----------------");
    	log.debug("doGet()");
    	log.debug("-----------------");
		doWork(request, response);
	}

	// Post방식 호출
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.debug("-----------------");
    	log.debug("doPost()");
    	log.debug("-----------------");
		doWork(request, response);
	}
	
	/*
	 * get(), post방식 호출을 처리
	 * */
	public JView doWork(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.debug("-----------------");
    	log.debug("doWork()");
    	log.debug("-----------------");
    	
    	JView viewName = null;
    	
    	// 한글깨짐 방지 request 인코딩 처리 : 
    	request.setCharacterEncoding("UTF-8");
    	
    	String workDiv = StringUtil.nvl(request.getParameter("work_div"), "");
    	
    	log.debug("workDiv : {}", workDiv);
    	
    	// if 문
//    	if (workDiv.equals("doRetrieve")) {
//			doRetrieve(request, response);
//		}else if(workDiv.equals("doSave")){
//			doSave(request, response);
//		}else {
//			log.debug("작업구분을 확인 하세요. workDiv : " + workDiv);
//		}
    	
    	// switch 문
    	switch(workDiv) {
    	case "doRetrieve": doRetrieve(request, response);
    					   break;
    	case "doSave": doSave(request, response);
		   			   break;
		default : log.debug("작업구분을 확인 하세요. workDiv : " + workDiv);   	
				  break;
    	}
    	
    	return viewName;
	}
	
	public void doSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.debug("-----------------");
    	log.debug("doSave()");
    	log.debug("-----------------");	
	}
	
	public JView doRetrieve(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.debug("-----------------");
    	log.debug("doRetrieve()");
    	log.debug("-----------------");		
    	
    	// JSP viewName 저장(경로)
    	JView viewName = null;
    	
    	String userNm = request.getParameter("user_nm");    	
    	log.debug("user_nm : {}", userNm);
	
    	// UI전달 데이터 설정
    	request.setAttribute("userNm", userNm + "님");
    	
    	// RequestDispatcher dispacher = request.getRequestDispatcher("/jsp/j02/j04_connect_response.jsp");
    	// dispacher.forward(request, response);
    	
    	return viewName = new JView("/jsp/j02/j04_connect_response.jsp");
	}

}
