<%@page import="com.projectl.vo.Museum"%>
<%@page import="com.projectl.dao.MuseumDAO"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
 String a = request.getParameter("un");
 MuseumDAO msDao = new MuseumDAO();
 JSONObject result = msDao.getLikedMuseumList(a);
%>
<%=result %>