package com.pcwk.ehr.mypageupdate;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pcwk.ehr.cmn.ControllerV;
import com.pcwk.ehr.cmn.JView;
import com.pcwk.ehr.cmn.PLog;

@WebServlet("/myPageUpdate.do")
public class MyPageUpdateController implements ControllerV, PLog {

    @Override
    public JView doWork(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // 세션에서 로그인된 사용자 아이디 가져오기
        String loggedInUserId = (String) session.getAttribute("loggedInUserId");
        if (loggedInUserId == null) {
            // 로그인되지 않은 상태이므로 처리할 수 없음
            log.error("로그인된 사용자 정보가 없습니다.");
            // 예외 처리 또는 오류 페이지로 리다이렉트 등을 고려할 수 있음
            return null;
        }

        // 비밀번호 파라미터 받아오기
        String password = request.getParameter("password");

        // DAO를 통해 비밀번호 업데이트 처리
        MyPageUpdateDAO myPageUpdateDAO = new MyPageUpdateDAO();
        boolean isSuccess = myPageUpdateDAO.updatePassword(loggedInUserId, password);

        // 업데이트 결과에 따라 페이지 이동 처리
        if (isSuccess) {
            // 업데이트 성공 시
            request.setAttribute("message", "회원 정보가 성공적으로 업데이트 되었습니다.");
            return new JView("/index.jsp");
        } else {
            // 업데이트 실패 시
            request.setAttribute("error", "회원 정보 업데이트에 실패했습니다.");
            return new JView("/errorPage.jsp");
        }
    }
}
