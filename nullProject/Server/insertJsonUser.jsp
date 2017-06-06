<%@page import="com.projectl.vo.Museum"%>
<%@page import="com.projectl.dao.MuseumDAO"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    <%@page import="com.projectl.dao.UserDAO" %>
<%
 String a = request.getParameter("ue");
 String b = request.getParameter("un");
 String c = request.getParameter("pw");
 String d = request.getParameter("gd");
 UserDAO userDao = new UserDAO();
 userDao.insertUserData(a,b,c,d);
%>
<%
JSONObject result = userDao.getUEData(a);
%>
<%=result %>