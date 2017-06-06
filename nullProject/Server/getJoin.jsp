<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import="org.json.simple.*"%>
<%@ page import ="java.sql.*" %>

<%


	/* try{
 		String id = "abcd";
		String pw = "abcd";
		String name = "alpha";
		String gd = "0";
				 
 		String id = request.getParameter("etEmail");
		String pw = request.getParameter("etPassword");
		String name = request.getParameter("etName");
		String gd = request.getParameter("gender");
		 
		Class.forName("com.mysql.jdbc.Driver");
		
		Connection conn = DriverManager.getConnection(
	    "jdbc:mysql://ec2-35-161-181-60.us-west-2.compute.amazonaws.com/louvre", 
	    "nullteam", 
	    "null");
		
		PreparedStatement pstmt = null;
 		String query = "insert into user(user_email, user_pw, user_name, user_gender) values(?, ?, ?, ?)";
 	
 	 	pstmt = conn.prepareStatement(query);	
		pstmt.setString(1, id);
		pstmt.setString(2, pw);
		pstmt.setString(3, name);
		pstmt.setString(4, gd);
		
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}catch(Exception e){
		e.printStackTrace();
	} */ 
%>
