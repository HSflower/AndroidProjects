<%-- <%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import="org.json.simple.*"%>
<%@ page import="java.util.*, java.sql.*, java.io.*" %>

<%

	Class.forName("com.mysql.jdbc.Driver");
	
	Connection conn = DriverManager.getConnection(
	"jdbc:mysql://ec2-35-161-181-60.us-west-2.compute.amazonaws.com/louvre", 
	"nullteam", 
	"null");
	
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	OutputStream output = response.getOutputStream();
	InputStream input = null; 
	
	String query = "select ex_img from exhibition where ex_no=3";
	
	pstmt = conn.prepareStatement(query);
	rs = pstmt.executeQuery();
	
	
	if (rs.next()) {
        input = rs.getBinaryStream("FILE");
        int byteRead;
        while((byteRead = input.read()) != -1) {
        output.write(byteRead);
        }
        input.close();
    }
	
	pstmt.close();
	conn.close();
	}catch(Exception e){
	e.printStackTrace();
	}
	
%> --%>