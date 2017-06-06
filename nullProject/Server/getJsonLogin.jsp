<%@page import="com.projectl.vo.Museum"%>
<%@page import="com.projectl.dao.MuseumDAO"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@page import="com.projectl.dao.UserDAO" %>
<%
 String a = request.getParameter("ue");
 String b = request.getParameter("up");
 UserDAO userDao = new UserDAO();
 JSONObject result = userDao.getUCData(a,b);
%>
<%=result %>