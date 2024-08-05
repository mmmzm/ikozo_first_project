package com.pcwk.ehr.join;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.ControllerV;
import com.pcwk.ehr.cmn.JView;
import com.pcwk.ehr.cmn.MessageVO;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.StringUtil;

/**
 * Servlet implementation class JoinController
 */
//@WebServlet("/JoinController")
public class JoinController extends HttpServlet implements ControllerV, PLog{
	private static final long serialVersionUID = 1L;
	JoinService service;
	
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoinController() {
    	log.debug("---------------------");
    	log.debug("JoinController()");
    	log.debug("---------------------");
    	
    	service = new JoinService();
    	
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

	public JView doSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("---------------------");
    	log.debug("doSave()");
    	log.debug("---------------------");
    	
    	JoinDTO inVO = new JoinDTO();
    	String userId = StringUtil.nvl(request.getParameter("userId"), "");//ajax data구분
    	String userName = StringUtil.nvl(request.getParameter("userName"), "");
    	String userTel = StringUtil.nvl(request.getParameter("userTel"), "");
    	String userPw = StringUtil.nvl(request.getParameter("userPw"), "");
    	String userEmail = StringUtil.nvl(request.getParameter("userEmail"), "");
    	String regId = StringUtil.nvl(request.getParameter("regId"), "");
    	String modId = StringUtil.nvl(request.getParameter("modId"), "");

    	log.debug("userId:"+userId);
    	log.debug("userName:"+userName);
    	log.debug("userTel:"+userTel);
    	log.debug("userPw:"+userPw);
    	log.debug("userEmail:"+userEmail);
    	
    	inVO.setUserId(userId);
    	inVO.setUserName(userName);
    	inVO.setUserTel(userTel);
    	inVO.setUserPw(userPw);
    	inVO.setUserEmail(userEmail);
    	inVO.setRegId(regId);
    	inVO.setModId(modId);
    	
    	int flag = this.service.doSave(inVO);
    	log.debug("flag:"+flag);
    	
    	String message = "";
    	if(flag==1) {
    		message = "등록성공";
    	}else {
    		message= "등록실패";
    	}		
    	return null;
	}
	
	public JView toJoin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("---------------------");
    	log.debug("toJoin()");
    	log.debug("---------------------");
    	
    	return new JView("/jsp/join.jsp");
	}	
	
	public JView idCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("---------------------");
    	log.debug("idCheck()");
    	log.debug("---------------------");
    	
    	JoinDTO inVO = new JoinDTO();
    	String userId = StringUtil.nvl(request.getParameter("userId"),"");
    	log.debug("userId()"+userId);
    	inVO.setUserId(userId);
    	
    	int flag = service.idCheck(inVO);
    	log.debug("flag"+flag);
    	
    	String message = "";
    	if(flag==1) {
    		message = "이미 사용중인 아이디 입니다. 다시 입력해주세요.";
    	}else {
    		message= "사용가능한 아이디 입니다.";
    	}  
    	
    	
    	MessageVO messeageVO = new MessageVO();
    	messeageVO.setMessageId(String.valueOf(flag));
    	messeageVO.setMsgContents(message);
    	
    	log.debug("messeageVO:"+messeageVO);
    	
    	Gson gson=new Gson();
    	String jsonString = gson.toJson(messeageVO);
    	
    	log.debug("jsonString:"+jsonString);
    	
    	//한글깨짐 방지
    	response.setContentType("text/html; charset=UTF-8");
    	 
    	PrintWriter out = response.getWriter();
    	//success data 화면에 출력
    	out.print(jsonString);         
    	
    	return null;
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
    	case "idCheck":
    		viewName=idCheck(request,response);
    		break;
    	case "toJoin":
    		viewName=toJoin(request,response);
    		break;
    	case "doSave"://회원가입
    		viewName=doSave(request,response);
    		break;
    	
    	default:
			log.debug("작업구분을 확인하세요. workDiv:{}",workDiv);
			break;
    	}
    	
    	return viewName;
	}


}
