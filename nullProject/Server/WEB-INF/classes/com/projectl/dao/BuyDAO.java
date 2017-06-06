package com.projectl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.projectl.vo.Buy;

public class BuyDAO {
	//public static final String buyListSQL = "select ms_name, ex_name, ex_img, buy_type from buy a, exhibition b, museum c where a.ex_no=b.ex_no and a.ms_no = b.ms_no and b.ex_no = c.ms_no and a.user_no = ?;";
	public static final String buyListSQL = "select ms_name, ex_name, ex_period, ex_img, buy_type from buy a, exhibition b, museum c where a.ex_no=b.ex_no and a.ms_no = b.ms_no and b.ex_no = c.ms_no and a.user_no = ?;";
	
	public static final String buyInsertSQL = "insert into buy(user_no, ms_no, ex_no, buy_type) values(?, ?, ?, ?);";
	
	public static final String isBuySQL = "select buy_type from buy where user_no = ? and ms_no = ? and ex_no = ?";
	
	private Buy buyData;
	
	public JSONObject getBuyListData(String a){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        JSONArray arr = new JSONArray();
        JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();

		//buyData = new Buy();
		
        try{
            conn = JDBCUtil.getConnection();
            stmt = conn.prepareStatement(buyListSQL);
            stmt.setInt(1, Integer.parseInt(a));
            rst = stmt.executeQuery();
            int sizeT = 0;
            while(rst.next()) {sizeT++;}
            
            JSONObject[] temp = new JSONObject[sizeT];
            int i=0;
            String str="[";
            
            rst = stmt.executeQuery();
            while(rst.next()) { //rst�� �ִ��� Ȯ��
                // ��ȣ�� db�󿡼� int�̴ϱ� int�� �޾ƾ��ҵ�?
            	obj.put("ms_name", rst.getString(1));
               	obj.put("ex_name", rst.getString(2));
               	obj.put("ex_period", rst.getString(3));
               	obj.put("ex_img", rst.getString(4));
               	obj.put("buy_type", rst.getString(5));
            	
            	temp[i] = obj;
            	str+= temp[i].toString();            	
            	if(i < sizeT-1)
            		str+=",";
                
                i++;
            }
            str += "]";
            try{
            	JSONParser jp = new JSONParser();
            	Object o = jp.parse(str);
            	arr = (JSONArray)o;            	
            } catch(Exception e) {}
            json.put("buys", arr);
        } catch(SQLException e){
            System.out.println("getBuyData e : " + e);
        } finally {
            JDBCUtil.close(stmt, conn);
        }
        return json;
    }
	
	public JSONObject getIsBuy(String a, String b, String c) {
		boolean boo = false;
		
		Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        JSONObject obj = new JSONObject();
        
        try{
            conn = JDBCUtil.getConnection();
            //�ϳ��� ���ӿ�û�� �ϳ��� getConnection���� ���ӿ�û�� �� ��, ����� Ŀ�ؼ� ���� (�׷��� ��û ������ ����)

            stmt = conn.prepareStatement(isBuySQL);
            stmt.setInt(1, Integer.parseInt(a));
            stmt.setInt(2, Integer.parseInt(b));
            stmt.setInt(3, Integer.parseInt(c));
            rst = stmt.executeQuery();

            while(rst.next()) {
            	obj.put("buy_type", rst.getString(1));
            	}
            
        } catch(SQLException e){
            System.out.println("getBuyData e : " + e);
        } finally {
            JDBCUtil.close(stmt, conn);
        }
		
		return obj;
	}
	
	public void insertBuyData(String a, String b, String c, String d){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        int aa;
        
        try{
            conn = JDBCUtil.getConnection();

            stmt = conn.prepareStatement(buyInsertSQL);
            stmt.setInt(1, Integer.parseInt(a));
            stmt.setInt(2, Integer.parseInt(b));
            stmt.setInt(3, Integer.parseInt(c));
            stmt.setInt(4, Integer.parseInt(d));
            stmt.executeUpdate();
            
        } catch(SQLException e){
            System.out.println("getBuyData e : " + e);
        } finally {
            JDBCUtil.close(stmt, conn);
        }
    }
}
