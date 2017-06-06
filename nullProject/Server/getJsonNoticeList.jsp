<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    <%@page import="com.projectl.dao.NoticeDAO" %>
<%
 NoticeDAO noteDao = new NoticeDAO();
 JSONObject result = noteDao.getNoticeList();
%>
<%=result %>