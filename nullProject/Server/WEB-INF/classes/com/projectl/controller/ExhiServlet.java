package com.projectl.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.projectl.dao.ExhibitionDAO;
import com.projectl.dao.MuseumDAO;
import com.projectl.vo.Exhibition;

public class ExhiServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7193906640422620585L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userno  = request.getParameter("userno");
		switch(userno){
		case "likedMuseum":
			System.out.println("action=liked");
			doGetLikedMuseumList(request, response);
		case "getExhiTab":
			System.out.println("action=getExhiTab");
			doGetExhi(request, response);
			break;
		case "nullMuseumTab":
			System.out.println("action=nullMuseumTag");
			doGetMuseum(request, response);
			break;
		}
	}
	void doGetLikedMuseumList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		
		String returnURL = "/getJsonLikeMuseumList.jsp";
		request.setAttribute("userNo", userNo);
		
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(returnURL);
		dispatcher.forward(request, response);
	}

	void doGetExhi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//board table�젒洹쇳빐�꽌 媛��졇�삤�뒗 硫붿꽌�뱶 �씠�슜 - BoardDAO�씠�슜(�씠 �럹�씠吏��뿉�꽌 �궗�슜�븷 硫붿꽌�뱶 �깮�꽦 �븘�슂)
		//boardDAO�뿉 db�젒洹쇳븯�뒗 硫붿꽌�뱶媛� 異붽��릺�뼱 �뿬湲곗꽌 洹� 硫붿꽌�뱶瑜� �궗�슜
		ExhibitionDAO dao = new ExhibitionDAO();
		
		Exhibition exhi = new Exhibition();
		exhi.setEx_no(request.getParameter("seq"));
		dao.getExhibitionData(exhi);

		String returnURL = "/getExhiTab.jsp";
		request.setAttribute("exhi", exhi);
		int exhino = Integer.parseInt(exhi.getEx_no());
		request.setAttribute("exhino", exhino);
		// board瑜� $�몴�떆濡� jsp�뿉�꽌 �궗�슜 媛��뒫

		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(returnURL);
		dispatcher.forward(request, response);
	}

	void doGetMuseum(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//board table�젒洹쇳빐�꽌 媛��졇�삤�뒗 硫붿꽌�뱶 �씠�슜 - BoardDAO�씠�슜(�씠 �럹�씠吏��뿉�꽌 �궗�슜�븷 硫붿꽌�뱶 �깮�꽦 �븘�슂)
		//boardDAO�뿉 db�젒洹쇳븯�뒗 硫붿꽌�뱶媛� 異붽��릺�뼱 �뿬湲곗꽌 洹� 硫붿꽌�뱶瑜� �궗�슜
		ExhibitionDAO dao = new ExhibitionDAO();
		
		Exhibition board = new Exhibition();
		//board.setSeq(request.getParameter("seq"));
		dao.getExhibitionData(board);
		
		String returnURL = "getBoard.jsp";
		String format = request.getParameter("format");
		if("json".equals(format)) {
			//android�쓽 寃쎌슦 , �슂泥춙rl�뿉 ?format=json�씠 �엳�쑝硫�
			Gson gson = new Gson();
			String result = gson.toJson(board);
			request.setAttribute("result", result);
			returnURL = "result.jsp";
		} else{ //json�쇅�쓽 紐⑤뱺 format�룄 �뿬湲곕줈 
			request.setAttribute("board", board);
		}
		// board瑜� $�몴�떆濡� jsp�뿉�꽌 �궗�슜 媛��뒫
		RequestDispatcher dispatcher = request.getRequestDispatcher(returnURL);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

}
