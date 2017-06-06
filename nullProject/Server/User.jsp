<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import="org.json.simple.*"
%>
<%@ page import ="java.sql.*" %>
<%
    //받아온 email과 pw을 String형태로 저장
	String id = request.getParameter("user_email");
	String pw = request.getParameter("user_pw");
	
	//String id = request.getParameter("id");
	//String pw = request.getParameter("pw");
	
	//초기 선언
	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	JSONObject jObject = new JSONObject();
	
	//jdbc driver connect
	Class.forName("com.mysql.jdbc.Driver"); 

	try{
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://ec2-35-161-181-60.us-west-2.compute.amazonaws.com/louvre", 
				"nullteam", "null");
		
		Statement stmt = conn.createStatement();
		String query = "select * from user";
		ResultSet rs = stmt.executeQuery(query);
		
		int i = 0;
		while(rs.next()){
			String _id = rs.getString("user_email");
			String _pw = rs.getString("user_pw");
			
			// id&pw 일치 시 안드로이드 보낼 msg
			if(id.equals(_id) && pw.equals(_pw)){
				i=1;
				jObject.put("Result", "succed");
			}
		}
		
		if(i==0){
			jObject.put("Result", "failed");
		}
		
		// 객체 닫기
		stmt.close();
		conn.close();
		
		jArray.add(0, jObject); //위에서 만든 객체를 하나의 배열 형태로 만듬
		//out.println(jArray.toJSONString());
		
		jsonMain.put("Login", jArray); //최종적으로 배열을 하나로 묶음(담은 데이터를 배열로 만듬)
		out.println(jsonMain.toJSONString()); //안드로이드로 데이터를 날림
	} catch(Exception e){
        System.out.println("getExhiData e : " + e);
	}
%>