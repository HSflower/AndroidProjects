<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import="org.json.simple.*"%>
<%@ page import ="java.sql.*" %>

<%
	String id = request.getParameter("etEmail");
	String pw = request.getParameter("etPassword");

	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	JSONObject jObject = new JSONObject();
	
	Class.forName("com.mysql.jdbc.Driver");
	
	try{
		Connection conn = DriverManager.getConnection(
	    "jdbc:mysql://ec2-35-161-181-60.us-west-2.compute.amazonaws.com/louvre", 
	    "nullteam", 
	    "null");
	  
 		Statement stmt = conn.createStatement();
 		String query = "select * from user";
 		
		ResultSet rs = stmt.executeQuery(query);
		
		int i = 0;
		while(rs.next()) {
		   String _id = rs.getString("user_email");
		   String _pw = rs.getString("user_pw");
		   
		   // 로그인 성공    
		   if(id.equals(_id) && pw.equals(_pw)){
			   i = 1;
			   jObject.put("Result", "succed");
			   jObject.put("Result2", "succed2");
		   }
		}
		
		// 로그인 실패 시
		if(i == 0){ 
			jObject.put("Result", "failed");
			jObject.put("Result2", "failed2");
		}
		
		
		stmt.close();
		conn.close();
		
		jArray.add(0, jObject);
		jsonMain.put("LoginResult", jArray);
		
		out.println(jsonMain.toJSONString());
		}catch(Exception e){
		}
%>