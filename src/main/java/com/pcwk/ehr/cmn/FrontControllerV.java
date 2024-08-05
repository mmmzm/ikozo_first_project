package com.pcwk.ehr.cmn;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pcwk.ehr.answer.AnswerController;
import com.pcwk.ehr.board.BoardController;
import com.pcwk.ehr.booklist.BookController;
import com.pcwk.ehr.favorite.FavoriteController;
import com.pcwk.ehr.findId.FindIdController;
import com.pcwk.ehr.join.JoinController;
import com.pcwk.ehr.login.LoginController;
import com.pcwk.ehr.mainpage.MainPageController;
import com.pcwk.ehr.managebook.ManageBookController;
import com.pcwk.ehr.manageuser.ManageUserController;
import com.pcwk.ehr.managebook.ManageBookController;
import com.pcwk.ehr.manageuser.ManageUserController;
import com.pcwk.ehr.mypage.LoanListController;
import com.pcwk.ehr.mypageupdate.MyPageUpdateController;
import com.pcwk.ehr.resetpw.ResetPwController;


/**
 * Servlet implementation class FrontControllerV
 */
@WebServlet("*.ikuzo")
public class FrontControllerV extends HttpServlet implements PLog {
	private static final long serialVersionUID = 1L;
       
	private Map<String, ControllerV> controllerMap = new HashMap<String, ControllerV>();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontControllerV() {  
    	log.debug("FrontControllerV()");
    	
    	controllerMap.put("/IKUZO/ikuzo/manage01.ikuzo", new ManageUserController()); // 회원관리자 페이지
    	controllerMap.put("/IKUZO/ikuzo/manage02.ikuzo", new ManageBookController()); // 도서관리자 페이지    	

    	controllerMap.put("/IKUZO/ikuzo/board.ikuzo", new BoardController()); // 게시판 페이지   
    	controllerMap.put("/IKUZO/ikuzo/answer.ikuzo", new AnswerController()); // 댓글 페이지	

    	controllerMap.put("/IKUZO/ikuzo/book.ikuzo", new BookController()); // 도서 목록 페이지    	
    	
    	controllerMap.put("/IKUZO/ikuzo/index.ikuzo", new MainPageController());// 메인 페이지  
   	

    	controllerMap.put("/IKUZO/ikuzo/mypage01.ikuzo", new LoanListController()); // 마이 페이지 [대출목록]    	


    	controllerMap.put("/IKUZO/ikuzo/join.ikuzo", new JoinController());//회원가입 페이지
    	controllerMap.put("/IKUZO/ikuzo/findId.ikuzo",  new FindIdController()); // 계정 찾기 페이지
    	controllerMap.put("/IKUZO/ikuzo/resetPw.ikuzo", new ResetPwController()); // 비밀번호 초기화 페이지
    	controllerMap.put("/IKUZO/ikuzo/favorite.ikuzo", new FavoriteController()); // 마이 페이지 [즐겨찾기]
    	controllerMap.put("/IKUZO/ikuzo/login.ikuzo", new LoginController()); // 로그인 페이지
    	controllerMap.put("/IKUZO/ikuzo/myPageUpdate.ikuzo", new MyPageUpdateController()); // 마이페이지 [업데이트]
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		log.debug("requestURI : {}", requestURI);
		
		ControllerV controller = controllerMap.get(requestURI);
		// com.pcwk.ehr.servlet.Controller@581969c9
		log.debug("controller : {}", controller);
		
		// controller가 null이면 404 예외 발생
		if (null == controller) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
	
    	// request 인코딩 처리 : 
    	request.setCharacterEncoding("UTF-8");		
		
		JView jview = controller.doWork(request, response);
		log.debug("jview:{}",jview);
		
		//각각의 컨트롤러가 forward 로직을 제대로 수행하고 있는지 하나하나 
		//신경쓸 필요가 없고 JView 객체만 제대로 반환한다면 무리없이 동작한다.
		
		if(null != jview  && jview.getViewName().length()>1) {
			jview.render(request, response);
		}
	}

}
