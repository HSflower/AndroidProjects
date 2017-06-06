package com.projectl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class NoticeDAO {

	private static final String NoticeSQL = "select note_no, ms_name, note_title, note_content, note_date from notify a, museum b where a.ms_no = b.ms_no";
	
	private static final String NoticeInsertSQL = "insert into notice() values(?,?,?,?,?,?)";
	
	private static final String NoticeDeleteSQL = "delete from notice where notice_no =?";
	
	public JSONObject getNoticeList(){
		Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        JSONArray arr = new JSONArray();
        JSONArray arrt = new JSONArray();
        JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		int n=0;
        try{
            conn = JDBCUtil.getConnection();
            
            if(conn!=null){
            	stmt = conn.prepareStatement(NoticeSQL);//statement객체생성,
            	rst = stmt.executeQuery();
            	System.out.println("[rst]: "+rst);
            } else{
                rst = null;
            }
            //반환하는 것 여러개일수 있으므로 반복수행, 결과를 데이터객체에 저장
            while(rst.next()){n++;}
            rst = stmt.executeQuery();
        	JSONObject[] temp = new JSONObject[n];
            int i=0;
            String str="[";
            
            while(rst.next()) { //rst의 마지막 까지실행
                // 리스트뷰에서 보여줄 정보만 남기기
            	obj.put("note_no", rst.getString(1));
            	obj.put("ms_name", rst.getString(2));
            	obj.put("note_title", rst.getString(3));
            	obj.put("note_content", rst.getString(4));
            	obj.put("note_date", rst.getString(5));
            	
            	temp[i] = obj;
            	str+= temp[i].toString();
            	if(i < n-1)
            		str+=",";
                
                //mslist.add(msData);
                i++;
            }
            str += "]";

            System.out.println(str);
            
            try{
            	JSONParser jp = new JSONParser();
            	Object o = jp.parse(str);
            	arr = (JSONArray)o;            	
            } catch(Exception e) {}
           
            json.put("notices", arr);
        } catch(SQLException e){
            System.out.println("list e : " + e);
        } finally {
        	System.out.print(arr);
            JDBCUtil.close(stmt, conn);
        }

        return json; // 미술관리스트 객체 반환
	}
	
	public void insertNoticeData(String a, String b, String c, String d){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        int aa;
        
        try{
            conn = JDBCUtil.getConnection();

            stmt = conn.prepareStatement(NoticeInsertSQL);
            stmt.setString(1, a);
            stmt.setString(2, b);
            stmt.setString(3, c);
            stmt.setInt(4, Integer.parseInt(d));
            aa = stmt.executeUpdate();
            
        } catch(SQLException e){
            System.out.println("getUserData e : " + e);
        } finally {
            JDBCUtil.close(stmt, conn);
        }
    }
	
	public void deleteNoticeData(String a){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        
        try{
            conn = JDBCUtil.getConnection();

            stmt = conn.prepareStatement(NoticeInsertSQL);
            stmt.setString(1, a);
            
            stmt.executeUpdate();
            
        } catch(SQLException e){
            System.out.println("getUserData e : " + e);
        } finally {
            JDBCUtil.close(stmt, conn);
        }
    }
}
