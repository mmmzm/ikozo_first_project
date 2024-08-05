package com.pcwk.ehr.resetpw;

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

@WebServlet("/resetPw.do")
public class ResetPwController implements ControllerV, PLog {
    private ResetPwService service;

    public ResetPwController() {
        service = new ResetPwService();
        log.debug("-----------------");
        log.debug("ResetPwController()");
        log.debug("-----------------");
    }

    @Override
    public JView doWork(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        log.debug("-----------------");
        log.debug("doWork()");
        log.debug("-----------------");

        String workDiv = StringUtil.nvl(request.getParameter("work_div"), "");
        log.debug("workDiv : {}", workDiv);

        switch (workDiv) {
            case "resetPassword":
                resetPassword(request, response);
                break;
            default:
                log.debug("작업구분을 확인 하세요. workDiv : {}", workDiv);
                break;
        }

        return null; 
    }

    public void resetPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("-----------------");
        log.debug("resetPassword()");
        log.debug("-----------------");

        String userId = StringUtil.nvl(request.getParameter("userId"), "");
        String userName = StringUtil.nvl(request.getParameter("userName"), "");
        String userTel = StringUtil.nvl(request.getParameter("userTel"), "");

        log.debug("userId: {}", userId);
        log.debug("userName: {}", userName);
        log.debug("userTel: {}", userTel);

        // 비밀번호 재설정 처리
        boolean isResetSuccessful = service.resetPassword(userId, userName, userTel);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        if (isResetSuccessful) {
            // 비밀번호 재설정 성공 시 새 비밀번호를 반환
            out.print("{\"newPassword\":\"" + service.getNewPassword() + "\"}");
        } else {
            // 비밀번호 재설정 실패 시 오류 메시지 반환
            out.print("{\"error\":\"비밀번호 재설정에 실패했습니다.\"}");
        }
        out.flush();
    }

}
