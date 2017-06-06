<%@page import="com.projectl.vo.Museum"%>
<%@page import="com.projectl.dao.MuseumDAO"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@page import="com.projectl.dao.LikeDAO" %>
<%
 String a = request.getParameter("un");
 String b = request.getParameter("mn");
 String c = request.getParameter("en");
 LikeDAO likeDao = new LikeDAO();
 JSONObject result = likeDao.getLikeE(a,b,c);
%>
<%=result %>