<%@page import="com.projectl.vo.Museum"%>
<%@page import="com.projectl.dao.MuseumDAO"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    <%@page import="com.projectl.dao.BuyDAO" %>
<%
 String a = request.getParameter("un");
 BuyDAO buyDao = new BuyDAO();
 JSONObject result = buyDao.getBuyListData(a);
%>
<%=result %>