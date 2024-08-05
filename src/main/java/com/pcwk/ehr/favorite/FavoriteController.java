package com.pcwk.ehr.favorite;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.pcwk.ehr.cmn.ControllerV;
import com.pcwk.ehr.cmn.JView;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.StringUtil;

@WebServlet("/favorite.do")
public class FavoriteController extends HttpServlet implements ControllerV, PLog {

    private static final long serialVersionUID = 1L;
    private FavoriteService favoriteService;

    public FavoriteController() {
        favoriteService = new FavoriteService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("---------------------");
        log.debug("process()");
        log.debug("---------------------");

        String workDiv = StringUtil.nvl(request.getParameter("work_div"), "");

        switch (workDiv) {
            case "doRetrieve":
                doRetrieve(request, response);
                break;
            default:
                log.debug("작업구분을 확인하세요. workDiv: {}", workDiv);
                break;
        }
    }

    private void doRetrieve(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("---------------------");
        log.debug("doRetrieve()");
        log.debug("---------------------");

        List<FavoriteDTO> favorites = favoriteService.doRetrieve(new FavoriteDTO()); // 즐겨찾기 목록 조회 메서드 호출

        // JSON 형태로 변환하여 응답
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(new Gson().toJson(favorites));
        out.flush();
    }
	@Override
	public JView doWork(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
