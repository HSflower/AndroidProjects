<%@page import="com.projectl.vo.Museum"%>
<%@page import="com.projectl.dao.MuseumDAO"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<title>null web app</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<% 
	Museum m1 = new Museum("1","Museum");
	MuseumDAO dao = new MuseumDAO();
	m1.setMs_no("1");
	dao.getMuseumData(m1);
	System.out.println("jsp finished");
	String plz = request.getParameter("seq"); //?seq=1
	%>
	<div class="container">
	<!--
	  <h2>Dynamic Tabs</h2>
	  <p>To make the tabs toggleable, add the data-toggle="tab" attribute to each link. Then add a .tab-pane class with a unique ID for every tab and wrap them inside a div element with class .tab-content.</p>
	-->
	  <ul class="nav nav-tabs">
	    <li class="active"><a data-toggle="tab" href="#home">Menu1</a></li>
	    <li><a data-toggle="tab" href="#menu1">Menu 2</a></li>
	    <li><a data-toggle="tab" href="#menu2">Menu 3</a></li>
	  </ul>
	<c:set var="no" value="a"/>
	  <div class="tab-content">
	    <div id="home" class="tab-pane fade in active">
	      <h3><%=m1.getMs_name() %></h3>
	      <b>미술관 위치| </b>
	      <%=m1.getMs_address() %><br>
	      <b>휴관일 | </b>
	      <%=m1.getMs_holiday()%><br>	 
	      <%= m1 %>     
	    </div>
	    <div id="menu1" class="tab-pane fade">
	      <h3>Menu 1</h3>
	      <p></p>
	    </div>
	    <div id="menu2" class="tab-pane fade">
	      <h3>Menu 2</h3>
	      <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam.</p>
	    </div>
	  </div>
	</div>

</body>
</html>