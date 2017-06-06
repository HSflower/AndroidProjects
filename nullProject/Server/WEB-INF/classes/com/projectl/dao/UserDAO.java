package com.projectl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.projectl.vo.User;

public class UserDAO {
	private static final String userListSQL = "select * from piece order by pc_no desc;";
    // 좋아요한 미술관 리스트 선택
    //private static final String msLikeListSQL = "select * from liked where user_no=? order by user_no desc;";
    // 해당하는 미술관 정보 선택
    private static final String userSelectSQL = "select user_no, user_name, user_email, user_pw, user_gender from user where user_no = ? ;";
    
    private static final String userGetUESQL = "select user_no,user_name from user where user_email = ?;";
    
    private static final String userInsertSQL = "insert into user(user_email, user_name, user_pw, user_gender) values(?, ?, ?, ?);";
    
    private static final String userUpdateSQL = "update user set user_pw = ? where user_no = ?;";
    
    private static final String userChkULSQL = "selct user_email, user_pw from user where user_no = ?";
    
    User userData;
    
    @SuppressWarnings({ "unchecked", "null" })
   	public JSONObject getUserList(){
           Connection conn = null;
           //PreparedStatement stmt = null;
           PreparedStatement stmt = null;
           ResultSet rst = null;
           JSONArray arr = new JSONArray();
           JSONObject obj = new JSONObject();
   		JSONObject json = new JSONObject();
   		
           //ArrayList<Museum> mslist = new ArrayList<Museum>();
           try{
               conn = JDBCUtil.getConnection();
               
               if(conn!=null){
               	stmt = conn.prepareStatement(userListSQL);//statement객체생성,
               	rst = stmt.executeQuery();
               	System.out.println("[rst]: "+rst);
               } else{
                   rst = null;
               }
               //반환하는 것 여러개일수 있으므로 반복수행, 결과를 데이터객체에 저장
               if(rst.first())
               {}
               rst = stmt.executeQuery();
               while(rst.next()) { //rst의 마지막 까지실행
                   // 리스트뷰에서 보여줄 정보만 남기기
               	rst.next();
               	obj.put("user_no", rst.getString(1));
               	obj.put("user_name", rst.getString(2));
               	obj.put("user_email", rst.getString(3));
               	obj.put("user_pw", rst.getString(4));
               	obj.put("user_gender", rst.getString(5));
                   arr.add(obj);
                   //mslist.add(msData);
               }
              
           } catch(SQLException e){
               System.out.println("list e : " + e);
           } finally {
               JDBCUtil.close(stmt, conn);
           }

           return json; // 미술관리스트 객체 반환
       }
    
    public JSONObject getUserData(String a){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        JSONObject obj = new JSONObject();

        try{
            conn = JDBCUtil.getConnection();
            //하나의 접속요청에 하나의 getConnection으로 접속요청을 할 것, 종료시 커넥션 종료 (그래야 요청 섞이지 않음)

            stmt = conn.prepareStatement(userSelectSQL);
            stmt.setInt(1, Integer.parseInt(a));
            rst = stmt.executeQuery();

            if(rst.next()) {
            	obj.put("user_no", rst.getString(1));
               	obj.put("user_name", rst.getString(2));
               	obj.put("user_email", rst.getString(3));
               	obj.put("user_pw", rst.getString(4));
               	obj.put("user_gender", rst.getString(5));
            }
        } catch(SQLException e){
            System.out.println("getUserData e : " + e);
        } finally {
            JDBCUtil.close(stmt, conn);
        }
        return obj;
    }
    
    public JSONObject getUCData(String a, String b){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        JSONObject obj = new JSONObject();
        boolean ch = false;
        
        try{
            conn = JDBCUtil.getConnection();
            //하나의 접속요청에 하나의 getConnection으로 접속요청을 할 것, 종료시 커넥션 종료 (그래야 요청 섞이지 않음)

            stmt = conn.prepareStatement("select user_email, user_pw from user");
            rst = stmt.executeQuery();
            while(rst.next()) {
            	if(rst.getString(1).equals(a))
            	{
            		if(rst.getString(2).equals(b))
            		{
            			ch = true;
            			break;
            		}
            		else
            		{
            			obj.put("user_email","");
    	            	obj.put("user_pw","");
    	            	obj.put("user_no", "-1");
    	               	obj.put("user_name", "");
    	               	obj.put("user_gender", "");
            			return obj;
            		}
            	}
            }
            
            System.out.println(a+b);
            //System.out.println(rst.getString(1)+rst.getString(2));
            
            if(ch)
            {
	            stmt = conn.prepareStatement(userGetUESQL);
	            stmt.setString(1, a);
	            rst = stmt.executeQuery();
	
	            if(rst.next()) {
	            	obj.put("user_email","");
	            	obj.put("user_pw","");
	            	obj.put("user_no", rst.getInt(1));
	               	obj.put("user_name", rst.getString(2));
	               	obj.put("user_gender", "");
	            }
            }
            else
            {
            	obj.put("user_email","");
            	obj.put("user_pw","");
            	obj.put("user_no", "");
               	obj.put("user_name", "");
               	obj.put("user_gender", "");
            }
        } catch(SQLException e){
            System.out.println("getUserData e : " + e);
        } finally {
            JDBCUtil.close(stmt, conn);
        }
        return obj;
    }
    
    public JSONObject getUEData(String a){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        JSONObject obj = new JSONObject();

        try{
            conn = JDBCUtil.getConnection();
            //하나의 접속요청에 하나의 getConnection으로 접속요청을 할 것, 종료시 커넥션 종료 (그래야 요청 섞이지 않음)

            stmt = conn.prepareStatement(userGetUESQL);
            stmt.setString(1, a);
            rst = stmt.executeQuery();

            if(rst.next()) {
            	obj.put("user_email", rst.getString(1));
            	obj.put("user_name", "0");
            	obj.put("user_pw", "0");
            	obj.put("user_no", "0");
            	obj.put("user_gender", "0");
            }
        } catch(SQLException e){
            System.out.println("getUserData e : " + e);
        } finally {
            JDBCUtil.close(stmt, conn);
        }
        return obj;
    }
    
    public void insertUserData(String a, String b, String c, String d){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        int aa;
        
        try{
            conn = JDBCUtil.getConnection();

            stmt = conn.prepareStatement(userInsertSQL);
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
    
    public void updateUserData(String a, String b){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        int aa;
        
        try{
            conn = JDBCUtil.getConnection();

            stmt = conn.prepareStatement(userUpdateSQL);
            stmt.setString(1, a);
            stmt.setInt(2, Integer.parseInt(b));
            stmt.executeUpdate();
            
        } catch(SQLException e){
            System.out.println("getUserData e : " + e);
        } finally {
            JDBCUtil.close(stmt, conn);
        }
    }
}
