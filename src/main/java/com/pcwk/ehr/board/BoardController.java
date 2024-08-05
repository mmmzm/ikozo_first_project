package com.pcwk.ehr.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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

// FrontControllerV에서 작동
public class BoardController extends HttpServlet implements ControllerV, PLog {
	private static final long serialVersionUID = 1L;

	BoardService service;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BoardController() {
		log.debug("-----------------");
		log.debug("BoardController()");
		log.debug("-----------------");

		service = new BoardService();
	}
	
	public JView moveToBoardAdmin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("-----------------");
		log.debug("moveToBoardAdmin()");
		log.debug("-----------------");
		return new JView("/jsp/board01.jsp");
	}	
	
	public JView moveToBoardUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("-----------------");
		log.debug("moveToBoardAdmin()");
		log.debug("-----------------");
		return new JView("/jsp/board02.jsp");
	}	

	public JView moveToReg(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("-----------------");
		log.debug("moveToReg()");
		log.debug("-----------------");
		return new JView("/board/board_reg.jsp");
	}
  
	public JView doSave(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		log.debug("-----------------");
		log.debug("doSave()");
		log.debug("-----------------");
		BoardDTO  inVO=new BoardDTO();
		String title = StringUtil.nvl(request.getParameter("title"), "");
		String regId = StringUtil.nvl(request.getParameter("regId"), "");
		String contents = StringUtil.nvl(request.getParameter("contents"), "");
		
		log.debug("title:{}",title);
		log.debug("regId:{}",regId);
		log.debug("contents:{}",contents);
		
		inVO.setTitle(title);
		inVO.setContents(contents);
		inVO.setRegId(regId);
		inVO.setModId(regId);
		
		int flag = this.service.doSave(inVO);
		log.debug("flag:{}",flag);
		
		if(1 == flag) {
			return new JView("/board/board.do?work_div=doRetrieve");
		}
		
		return null;                   
	}
	
	public JView ajaxDoSave(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		log.debug("-----------------");
		log.debug("ajaxDoSave()");
		log.debug("-----------------");
		BoardDTO  inVO=new BoardDTO();
		String title = StringUtil.nvl(request.getParameter("title"), "");
		String regId = StringUtil.nvl(request.getParameter("regId"), "");
		String contents = StringUtil.nvl(request.getParameter("contents"), "");
		String isAdmin = "N";
		
		log.debug("title:{}",title);
		log.debug("regId:{}",regId);
		log.debug("contents:{}",contents);
		log.debug("isAdmin:{}", isAdmin);
		
		inVO.setTitle(title);
		inVO.setContents(contents);
		inVO.setRegId(regId);
		inVO.setModId(regId);
		inVO.setIsAdmin(isAdmin);
		
		int flag = this.service.doSave(inVO);
		log.debug("flag:{}",flag);		
		
		String message = "";
		if(flag == 1) {
			message = "등록이 완료되었습니다!";
		}else {
			message= "등록실패";
		}
		MessageVO  messageVO =new MessageVO();
		messageVO.setMessageId(String.valueOf(flag));
		messageVO.setMsgContents(message);
		
		log.debug("messageVO:{}",messageVO);
		
		Gson gson=new Gson();
		String jsonString = gson.toJson(messageVO);
		
		log.debug("jsonString:{}",jsonString);
		
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.print(jsonString);
		
		return null;
	}
	
	@Override
	public JView doWork(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		log.debug("-----------------");
		log.debug("doWork()");
		log.debug("-----------------");

		JView viewName = null;

		String workDiv = StringUtil.nvl(request.getParameter("work_div"), "");
		log.debug("workDiv : {} ", workDiv);

		switch (workDiv) {
		case "ajaxDoSave":
			viewName = ajaxDoSave(request, response);
			break;
		case "doSave":
			viewName = doSave(request, response);
			break;
		case "moveToReg":
			viewName = moveToReg(request, response);
			break;
		case "doRetrieve":
			viewName = doRetrieve(request, response);
			break;
        case "boardDetail": // 추가된 부분 - 상세보기 작업구분 처리
        	log.debug("Calling boardDetail");
            viewName = boardDetail(request, response);
            break;
        case "viewBoardDetail": // 추가된 부분 - 게시글 상세보기 작업구분 처리
            viewName = viewBoardDetail(request, response);
            break;
        case "doUpdate":
            viewName = doUpdate(request, response);
            break;    
		case "doDelete":
			viewName = doDel(request, response);
			break;
		case "moveToBoardAdmin":
			viewName = moveToBoardAdmin(request, response);
			break;
		case "moveToBoardUser":
			viewName = moveToBoardUser(request, response);
			break;
		default:
			log.debug("작업구분을 확인 하세요. workDiv : {} ", workDiv);
			break;
		}

		return viewName;
	}

	private JView doRetrieve(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    log.debug("-----------------");
	    log.debug("doRetrieve()");
	    log.debug("-----------------");

	    String seq = request.getParameter("seq");
	    log.debug("seq : {}", seq);

	    if (seq != null) {
	        BoardDTO board = service.getBoardDetail(Integer.parseInt(seq));
	        request.setAttribute("board", board);
	    } else {
	        log.warn("seq 파라미터가 전달되지 않았습니다.");
	        // 예외 처리 또는 오류 메시지 설정
	        // 예: request.setAttribute("errorMessage", "게시글 번호가 전달되지 않았습니다.");
	        //     request.getRequestDispatcher("/error.jsp").forward(request, response);
	    }

	    return new JView("/board/boardDetail.jsp");
	}

	private JView boardDetail(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    log.debug("-----------------");
	    log.debug("boardDetail()");
	    log.debug("-----------------");

	    String seq = StringUtil.nvl(request.getParameter("seq"), "");
	    log.debug("seq : {}", seq);
	    
	    if (!seq.isEmpty()) {
	        // DAO를 통해 조회수 증가 메서드 호출
	        BoardDTO param = new BoardDTO();
	        param.setSeq(Integer.parseInt(seq));
	        
	        // 조회수 증가 메서드 호출
	        int result = service.doUpdateReadcnt(param);
	        
	        if (result > 0) {
	            log.debug("조회수 증가 성공");
	        } else {
	            log.debug("조회수 증가 실패");
	            // 실패 시 예외 처리 또는 메시지 설정
	        }
	        
	        // 상세 조회 메서드 호출
	        BoardDTO board = service.getBoardDetail(Integer.parseInt(seq));
	        log.debug("board : {}", board);
	        
	        // 조회된 게시글 정보를 request에 설정
	        if (board != null) {
	            request.setAttribute("board", board);
	           
		        // 보드 디테일 정보를 세션에 저장
		        HttpSession session = request.getSession();
		        session.setAttribute("boardDetail", board);
	        } else {
	            log.warn("게시글 조회 실패");
	            // 에러 처리 또는 메시지 설정
	        }
	    } else {
	        log.warn("seq 파라미터가 전달되지 않았습니다.");
	        // 예외 처리 또는 오류 메시지 설정
	    }

	    return new JView("/jsp/boardDetail.jsp");
	}


	private JView viewBoardDetail(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    log.debug("-----------------");
	    log.debug("viewBoardDetail()");
	    log.debug("-----------------");

	    String seq = StringUtil.nvl(request.getParameter("seq"), "");
	    log.debug("seq : {}", seq);

	    if (!seq.isEmpty()) {
	        BoardDTO board = service.getBoardDetail(Integer.parseInt(seq));
	        log.debug("board : {}", board);
	        request.setAttribute("board", board);
	        
	        // 보드 디테일 정보를 세션에 저장
	        HttpSession session = request.getSession();
	        session.setAttribute("boardDetail", board);
	        return new JView("/jsp/board_mng.jsp");
	    } else {
	        log.warn("seq 파라미터가 전달되지 않았습니다.");
	        // 예외 처리 또는 오류 메시지 설정
	        // 예: request.setAttribute("errorMessage", "게시글 번호가 전달되지 않았습니다.");
	        //     request.getRequestDispatcher("/error.jsp").forward(request, response);
	        
	        // 에러 페이지로 이동
	        return new JView("/jsp/error.jsp");
	    }
	    
	}
	    
	    public JView doUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        log.debug("-----------------");
	        log.debug("doUpdate()");
	        log.debug("-----------------");

	        BoardDTO inVO = new BoardDTO();
	        String seq = StringUtil.nvl(request.getParameter("seq"), "0");
	        String title = StringUtil.nvl(request.getParameter("title"), "");
	        String contents = StringUtil.nvl(request.getParameter("contents"), "");
	        

	        log.debug("seq:{}", seq);
	        log.debug("title:{}", title);
	        log.debug("contents:{}", contents);
	        

	        inVO.setSeq(Integer.parseInt(seq));
	        inVO.setTitle(title);
	        inVO.setContents(contents);
	        

	        log.debug("inVO:{}", inVO);
	        int flag = service.doUpdate(inVO);
	        String message = "";
	        log.debug("flag:{}", flag);

	        if (1 == flag) {
	            message = "수정 완료";
	        } else {
	            message = "수정 실패!";
	        }

	        MessageVO messageVO = new MessageVO();
	        messageVO.setMessageId(String.valueOf(flag));
	        messageVO.setMsgContents(message);

	        Gson gson = new Gson();
	        String jsonString = gson.toJson(messageVO);

	        log.debug("jsonString:{}", jsonString);
	        response.setContentType("text/html; charset=UTF-8");

	        PrintWriter out = response.getWriter();
	        out.print(jsonString);

	        return null;
	    }
	    
		public JView doDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			log.debug("-----------------");
			log.debug("doDel()");
			log.debug("-----------------");
			
			BoardDTO inVO =new BoardDTO();
			String seq =StringUtil.nvl(request.getParameter("seq"),"0");
			
			inVO.setSeq(Integer.parseInt(seq));
			log.debug("inVO:"+inVO);
			
			int flag = service.doDelete(inVO);
			log.debug("flag:{}",flag);
			
			String message = "";
			if(1==flag) {
				message = "삭제 성공";
			}else {
				message = "삭제 실패";
			}
			
			MessageVO messageVO = new MessageVO();
			messageVO.setMessageId(String.valueOf(flag));
			messageVO.setMsgContents(message);
			log.debug("messageVO:{}",messageVO);
			
			Gson gson=new Gson();
			String jsonString = gson.toJson(messageVO);
			log.debug("jsonString:{}",jsonString);
			
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out= response.getWriter();
			out.print(jsonString);
			return null;
		}
	    
	}

