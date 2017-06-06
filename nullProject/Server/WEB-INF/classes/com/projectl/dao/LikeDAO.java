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
            	pstmt = conn.prepareStatement(likeESQL);//statement��ü����,
            	pstmt.setInt(1, Integer.parseInt(a));
            	pstmt.setInt(2, Integer.parseInt(b));
            	pstmt.setInt(3, Integer.parseInt(c));
            	rst = pstmt.executeQuery();
            } else{
                rst = null;
            }
            //��ȯ�ϴ� �� �������ϼ� �����Ƿ� �ݺ�����, ����� �����Ͱ�ü�� ����
            if(rst.next()) { //rst�� ������ ��������
                // ����Ʈ�信�� ������ ������ �����
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

        return obj; // �̼�������Ʈ ��ü ��ȯ
    }
	
	public JSONObject getLikeCHE(String a, String b, String c){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rst = null;
        JSONObject obj = new JSONObject();
		
        try{
            conn = JDBCUtil.getConnection();
            
            if(conn!=null){
            	pstmt = conn.prepareStatement(likeESQL);//statement��ü����,
            	pstmt.setInt(1, Integer.parseInt(a));
            	pstmt.setInt(2, Integer.parseInt(b));
            	pstmt.setInt(3, Integer.parseInt(c));
            	rst = pstmt.executeQuery();
            } else{
                rst = null;
            }
            //��ȯ�ϴ� �� �������ϼ� �����Ƿ� �ݺ�����, ����� �����Ͱ�ü�� ����
            if(rst.next()) { 
                // ����Ʈ�信�� ������ ������ �����
            	if(!rst.getString(1).equals(""))
            	{
            		obj.put("like", "false");
            		pstmt = conn.prepareStatement(likeCHDESQL);//statement��ü����,
                	pstmt.setInt(1, Integer.parseInt(a));
                	pstmt.setInt(2, Integer.parseInt(b));
                	pstmt.setInt(3, Integer.parseInt(c));
                	pstmt.executeUpdate();
            	}
            	else
            	{
            		obj.put("like", "true");
            		pstmt = conn.prepareStatement(likeCHIESQL);//statement��ü����,
                	pstmt.setInt(1, Integer.parseInt(a));
                	pstmt.setInt(2, Integer.parseInt(b));
                	pstmt.setInt(3, Integer.parseInt(c));
                	pstmt.executeUpdate();
            	}
            }
            else
            {
            	obj.put("like", "true");
            	pstmt = conn.prepareStatement(likeCHIESQL);//statement��ü����,
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

        return obj; // �̼�������Ʈ ��ü ��ȯ
    }
}
