package com.pcwk.ehr.findId;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.pcwk.ehr.cmn.ControllerV;
import com.pcwk.ehr.cmn.JView;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.StringUtil;

@WebServlet("/findId.do")
public class FindIdController implements ControllerV, PLog {

    private FindIdService service;

    public FindIdController() {
        service = new FindIdService();
        log.debug("-----------------");
		log.debug("FindIdController()");
		log.debug("-----------------");
		
		service = new FindIdService();
    }
    


    @Override
    public JView doWork(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("-----------------");
        log.debug("doWork()");
        log.debug("-----------------");

        String workDiv = StringUtil.nvl(request.getParameter("work_div"), "");
        log.debug("workDiv : {}", workDiv);

        switch (workDiv) {
            case "findUserId":
                findUserId(request, response);
                break;
            default:
                log.debug("작업구분을 확인 하세요. workDiv : {}", workDiv);
                break;
        }

        return null; 
    }

    public void findUserId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("-----------------");
        log.debug("findUserId()");
        log.debug("-----------------");

        String userName = StringUtil.nvl(request.getParameter("userName"), "");
        String userTel = StringUtil.nvl(request.getParameter("userTel"), "");

        log.debug("userName: {}", userName);
        log.debug("userTel: {}", userTel);

        String userId = service.findUserId(userName, userTel);
        log.debug("userId: {}", userId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.print("{\"userId\":\"" + userId + "\"}");
        out.flush();
    }
}

