<%@page import="com.projectl.vo.Museum"%>
<%@page import="com.projectl.dao.MuseumDAO"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@page import="com.projectl.dao.UserDAO" %>
<%
 String a = request.getParameter("np");
 String b = request.getParameter("un");
 UserDAO userDao = new UserDAO();
 userDao.updateUserData(a, b);
%>