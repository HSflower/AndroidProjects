<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    <%@page import="com.projectl.dao.ReviewDAO" %>
<%
 String a = request.getParameter("mn");
 String b = request.getParameter("en");
 ReviewDAO reviewDao = new ReviewDAO();
 JSONObject result = reviewDao.getReview(a,b);
%>
<%=result %>