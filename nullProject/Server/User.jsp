<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import="org.json.simple.*"
%>
<%@ page import ="java.sql.*" %>
<%
    //�޾ƿ� email�� pw�� String���·� ����
	String id = request.getParameter("user_email");
	String pw = request.getParameter("user_pw");
	
	//String id = request.getParameter("id");
	//String pw = request.getParameter("pw");
	
	//�ʱ� ����
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
			
			// id&pw ��ġ �� �ȵ���̵� ���� msg
			if(id.equals(_id) && pw.equals(_pw)){
				i=1;
				jObject.put("Result", "succed");
			}
		}
		
		if(i==0){
			jObject.put("Result", "failed");
		}
		
		// ��ü �ݱ�
		stmt.close();
		conn.close();
		
		jArray.add(0, jObject); //������ ���� ��ü�� �ϳ��� �迭 ���·� ����
		//out.println(jArray.toJSONString());
		
		jsonMain.put("Login", jArray); //���������� �迭�� �ϳ��� ����(���� �����͸� �迭�� ����)
		out.println(jsonMain.toJSONString()); //�ȵ���̵�� �����͸� ����
	} catch(Exception e){
        System.out.println("getExhiData e : " + e);
	}
%>