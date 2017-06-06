package com.projectl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class LikeDAO {
	
	private static final String likeESQL ="select user_no from likeTableE where user_no=? and ms_no=? and ex_no=?";
	
	private static final String likeCHIESQL = "insert into likeTableE(user_no, ms_no, ex_no) values(?,?,?)";
	
	private static final String likeCHDESQL = "delete from likeTableE where user_no=? and ms_no=? and ex_no=?";
	
	private static final String likeMSQL ="";
	
	public JSONObject getLikeE(String a, String b, String c){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rst = null;
        JSONObject obj = new JSONObject();
		
        try{
            conn = JDBCUtil.getConnection();
            
            if(conn!=null){
            	pstmt = conn.prepareStatement(likeESQL);//statement객체생성,
            	pstmt.setInt(1, Integer.parseInt(a));
            	pstmt.setInt(2, Integer.parseInt(b));
            	pstmt.setInt(3, Integer.parseInt(c));
            	rst = pstmt.executeQuery();
            } else{
                rst = null;
            }
            //반환하는 것 여러개일수 있으므로 반복수행, 결과를 데이터객체에 저장
            if(rst.next()) { //rst의 마지막 까지실행
                // 리스트뷰에서 보여줄 정보만 남기기
            	if(!rst.getString(1).equals(""))
            		obj.put("like", "true");
            	else
            		obj.put("like", "false");
            }
            else
            	obj.put("like", "false");
           
        } catch(SQLException e){
            System.out.println("list e : " + e);
        } finally {
            JDBCUtil.close(pstmt, conn);
        }

        return obj; // 미술관리스트 객체 반환
    }
	
	public JSONObject getLikeCHE(String a, String b, String c){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rst = null;
        JSONObject obj = new JSONObject();
		
        try{
            conn = JDBCUtil.getConnection();
            
            if(conn!=null){
            	pstmt = conn.prepareStatement(likeESQL);//statement객체생성,
            	pstmt.setInt(1, Integer.parseInt(a));
            	pstmt.setInt(2, Integer.parseInt(b));
            	pstmt.setInt(3, Integer.parseInt(c));
            	rst = pstmt.executeQuery();
            } else{
                rst = null;
            }
            //반환하는 것 여러개일수 있으므로 반복수행, 결과를 데이터객체에 저장
            if(rst.next()) { 
                // 리스트뷰에서 보여줄 정보만 남기기
            	if(!rst.getString(1).equals(""))
            	{
            		obj.put("like", "false");
            		pstmt = conn.prepareStatement(likeCHDESQL);//statement객체생성,
                	pstmt.setInt(1, Integer.parseInt(a));
                	pstmt.setInt(2, Integer.parseInt(b));
                	pstmt.setInt(3, Integer.parseInt(c));
                	pstmt.executeUpdate();
            	}
            	else
            	{
            		obj.put("like", "true");
            		pstmt = conn.prepareStatement(likeCHIESQL);//statement객체생성,
                	pstmt.setInt(1, Integer.parseInt(a));
                	pstmt.setInt(2, Integer.parseInt(b));
                	pstmt.setInt(3, Integer.parseInt(c));
                	pstmt.executeUpdate();
            	}
            }
            else
            {
            	obj.put("like", "true");
            	pstmt = conn.prepareStatement(likeCHIESQL);//statement객체생성,
            	pstmt.setInt(1, Integer.parseInt(a));
            	pstmt.setInt(2, Integer.parseInt(b));
            	pstmt.setInt(3, Integer.parseInt(c));
            	pstmt.executeUpdate();
            }
           
        } catch(SQLException e){
            System.out.println("list e : " + e);
        } finally {
            JDBCUtil.close(pstmt, conn);
        }

        return obj; // 미술관리스트 객체 반환
    }
}
