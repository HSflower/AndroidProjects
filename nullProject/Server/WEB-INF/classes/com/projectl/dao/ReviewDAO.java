package com.projectl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ReviewDAO {

	private static final String ReviewSQL = "select ms_no, ex_no, user_name, review_no, review_content, review_rating, review_date from review a, user b where a.user_no = b.user_no and ms_no = ? and ex_no = ?";
	
	private static final String ReviewInsertSQL = "insert into review(user_no, ms_no, ex_no, review_content, review_rating, review_date) values(?,?,?,?,?,?)";
	
	private static final String ReviewDeleteSQL = "delete from review where ms_no=? and ex_no =? and review_no = ?";

	public JSONObject getReview(String a, String b){
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
            	stmt = conn.prepareStatement(ReviewSQL);
                stmt.setInt(1, Integer.parseInt(a));
                stmt.setInt(2, Integer.parseInt(b));
                rst = stmt.executeQuery();
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
            	
            	obj.put("ms_no", rst.getString(1));
            	obj.put("ex_no", rst.getString(2));
            	obj.put("user_name", rst.getString(3));
            	obj.put("review_no", rst.getString(4));
            	obj.put("review_content", rst.getString(5));
            	obj.put("review_rating", rst.getString(6));
            	obj.put("review_date", rst.getString(7));
            	
            	
            	temp[i] = obj;
            	str+= temp[i].toString();
            	if(i < n-1)
            		str+=",";
                
                //mslist.add(msData);
                i++;
            }
            str += "]";

            System.out.println(str);
            
            System.out.println("WWWWWWWWWWW");
            try{
            	JSONParser jp = new JSONParser();
            	Object o = jp.parse(str);
            	arr = (JSONArray)o;            	
            } catch(Exception e) {}
           
            
            json.put("reviews", arr);
        } catch(SQLException e){
            System.out.println("list e : " + e);
        } finally {
        	System.out.print(arr);
            JDBCUtil.close(stmt, conn);
        }

        return json; // 미술관리스트 객체 반환
	}
	
	public void insertReview(String a, String b, String c, String d, String e, String f){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        int aa;
        
        try{
            conn = JDBCUtil.getConnection();

            stmt = conn.prepareStatement(ReviewInsertSQL);
            stmt.setString(1, a);
            stmt.setString(2, b);
            stmt.setString(3, c);
            stmt.setString(4, d);
            stmt.setString(5, e);
            stmt.setString(6, f);
            aa = stmt.executeUpdate();
            
        } catch(SQLException ee){
            System.out.println("getUserData e : " + ee);
        } finally {
            JDBCUtil.close(stmt, conn);
        }
    }
	
	public void deleteReviewData(String a, String b, String c){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        
        try{
            conn = JDBCUtil.getConnection();

            stmt = conn.prepareStatement(ReviewInsertSQL);
            stmt.setString(1, a);
            stmt.setString(2, b);
            stmt.setString(3, c);
            
            stmt.executeUpdate();
            
        } catch(SQLException e){
            System.out.println("getUserData e : " + e);
        } finally {
            JDBCUtil.close(stmt, conn);
        }
    }
}
