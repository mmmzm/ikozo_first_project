package com.pcwk.ehr.answer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.RequestDispatcher;

import com.google.gson.Gson;
import com.pcwk.ehr.board.BoardDTO;
import com.pcwk.ehr.cmn.ControllerV;
import com.pcwk.ehr.cmn.JView;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.StringUtil;
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.cmn.MessageVO;


// FrontControllerV에서 작동
public class AnswerController extends javax.servlet.http.HttpServlet implements ControllerV, PLog {
    private static final long serialVersionUID = 1L;
    
    private AnswerService answerService; // AnswerService 객체 선언
    
    public AnswerController() {
        log.debug("-----------------");
        log.debug("AnswerController()");
        log.debug("-----------------");

        answerService = new AnswerService(); // AnswerService 객체 초기화
    }

    @Override
    public JView doWork(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("-----------------");
        log.debug("doWork()");
        log.debug("-----------------");

        JView viewName = null;

        String workDiv = StringUtil.nvl(request.getParameter("work_div"), "");
        log.debug("workDiv : {} ", workDiv);

        switch (workDiv) {
            case "listAnswers":
                viewName = listAnswers(request, response);
                break;
            case "doRetrieve":
                try {
                    viewName = doRetrieve(request, response);
                } catch (ServletException e) {
                    log.error("서블릿 예외 발생: {}", e.getMessage());
                    // 예외 처리 로직 추가
                }
                break;
            case "retrieveAnswers":
                viewName = retrieveAnswers(request, response);
                break;
            case "createAnswer":
                viewName = createAnswer(request, response);
                break;
            default:
                log.debug("작업구분을 확인 하세요. workDiv : {} ", workDiv);
                // 필요에 따라 예외 처리 또는 기본 처리 로직을 구현할 수 있음
                break;
        }

        return viewName;
    }

    private JView retrieveAnswers(HttpServletRequest request, HttpServletResponse response) {
        int boardSeq = Integer.parseInt(request.getParameter("boardSeq"));
        List<AnswerDTO> answerList = answerService.retrieveAnswers(boardSeq);

        // JSON 형식으로 변환하여 클라이언트에게 응답
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String jsonList = gson.toJson(answerList);
            out.print(jsonList);
        } catch (IOException e) {
            log.error("댓글 목록을 응답하는 중 오류 발생: {}", e.getMessage());
        }

        return null; // JView 반환
    }

 // 댓글 목록 조회 처리 메서드
    private JView listAnswers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String boardSeqParam = request.getParameter("boardSeq");
        int boardSeq = -1; // 기본값 설정

        // boardSeqParam이 null이 아니고 비어있지 않은 경우에만 변환 시도
        if (boardSeqParam != null && !boardSeqParam.isEmpty()) {
            try {
                boardSeq = Integer.parseInt(boardSeqParam);
            } catch (NumberFormatException e) {
                log.error("게시글 고유번호 파라미터 변환 오류: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request 설정
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().write("게시글 고유번호 파라미터가 올바르지 않습니다.");
                return null;
            }
        } else {
            log.error("게시글 고유번호 파라미터가 없습니다.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request 설정
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("게시글 고유번호 파라미터가 필요합니다.");
            return null;
        }

        List<AnswerDTO> answerList = answerService.retrieveAnswers(boardSeq);

        // JSON 형식으로 변환하여 클라이언트에게 응답
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String jsonList = gson.toJson(answerList);
            out.print(jsonList);
        } catch (IOException e) {
            log.error("댓글 목록을 응답하는 중 오류 발생: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error 설정
            response.getWriter().write("서버에서 오류가 발생했습니다.");
            return null;
        }

        return null; // JView 반환
    }


    private JView doRetrieve(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("-----------------");
        log.debug("doRetrieve()");
        log.debug("-----------------");

        String boardSeqStr = request.getParameter("boardSeq");
        int boardSeq = Integer.parseInt(boardSeqStr);

        List<AnswerDTO> answerList = answerService.retrieveAnswers(boardSeq);

        // JSON 형식으로 변환하여 클라이언트에게 응답
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String jsonList = gson.toJson(answerList);
        out.print(jsonList);
        out.flush();

        return null; // 적절한 JView 반환
    }
    
    // 댓글 등록 처리 메서드
    private JView createAnswer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String contents = request.getParameter("contents");
        String regId = request.getParameter("regId");
        int boardSeq = Integer.parseInt(request.getParameter("boardSeq"));

        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setContents(contents);
        answerDTO.setRegId(regId);
        answerDTO.setBoardSeq(boardSeq);

        answerService.createAnswer(answerDTO);

        // JSON 형식으로 응답
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(answerDTO);
            out.print(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
