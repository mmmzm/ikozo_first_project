package com.pcwk.ehr.brent;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.ControllerV;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.JView;
import com.pcwk.ehr.cmn.MessageVO;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.StringUtil;

public class RentController implements ControllerV, PLog {
	
	RentService serviceR;
	
	public RentController() {
		log.debug("---------------------");
    	log.debug("RentController()");
    	log.debug("---------------------");
	}
	
	
	public JView rentcheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("---------------------");
    	log.debug("rentcheck()");
    	log.debug("---------------------");
    	
    	RentDTO inVO = new RentDTO();
    	int bookCode = Integer.parseInt(StringUtil.nvl(request.getParameter("book_code"), "0"));
    	
    	log.debug("book_code:{}",bookCode);
    	
    	inVO.setBookCode(bookCode);
    	
    	DTO dto = serviceR.doRentSelect(inVO);
    	
    	
    	MessageVO message = new MessageVO();
    	if(dto instanceof RentDTO) {
    		RentDTO outVO = (RentDTO)dto;
    		log.debug("성공 outVO : {}",outVO);
    		
    		HttpSession session = request.getSession();
    		
    		session.setAttribute("rent", outVO);
    		
    		message.setMessageId("20");
    		message.setMsgContents("대출 성공");
    	}else {
    		message = (MessageVO)dto;
    		log.debug("실패 message : {}",message);
    	}
    	
    	Gson gson = new Gson();
    	String jsonString = gson.toJson(message);
    	log.debug("jsonString : {}",jsonString);
    	
    	PrintWriter out = response.getWriter(); 				//24.
		out.print(jsonString); 
    	return null;
	}
		
	@Override
	public JView doWork(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("---------------------");
    	log.debug("doWork()");
    	log.debug("---------------------");
		
    	JView viewName = null;
    	
    	String workDiv = StringUtil.nvl(request.getParameter("work_div"), "");
    	log.debug("workDiv : {}",workDiv);
    	
    	switch(workDiv) {
    	case "rentcheck":
    		viewName = rentcheck(request,response);
    		break;
    	default:
    		log.debug("작업구분을 확인 하세요. workDiv : {}", workDiv);
			break;
    	}
		
		return null;
	}

}
