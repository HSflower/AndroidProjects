<%@page import="com.projectl.vo.Museum"%>
<%@page import="com.projectl.dao.MuseumDAO"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    <% request.setCharacterEncoding("utf-8");%>
    <%@page import="com.projectl.dao.ReviewDAO" %>
<%
 String a = request.getParameter("un");
 String b = request.getParameter("mn");
 String c = request.getParameter("en");
 String d = request.getParameter("rc");
 String e = request.getParameter("rr");
 String f = request.getParameter("rd");
 ReviewDAO reviewDao = new ReviewDAO();
 reviewDao.insertReview(a,b,c,d,e,f);
%>