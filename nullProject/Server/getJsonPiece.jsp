<%@page import="com.projectl.vo.Museum"%>
<%@page import="com.projectl.dao.MuseumDAO"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@page import="com.projectl.dao.PieceDAO" %>
<%
 String a = request.getParameter("mn");
 String b = request.getParameter("en");
 String c = request.getParameter("pn");
 PieceDAO pcDao = new PieceDAO();
 JSONObject result = pcDao.getPieceData(a,b,c);
%>
<%=result %>