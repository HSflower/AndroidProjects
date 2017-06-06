package com.projectl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.projectl.vo.Museum;

public class MuseumDAO {
	
    // �̼��� ����Ʈ ����
    private static final String msListSQL = "select ms_no, ms_address, ms_tel, ms_url, ms_holiday, ms_operating, ms_name, ms_rating, ms_like, ms_img, ms_exp from museum;";
    // ���ƿ��� �̼��� ����Ʈ ����
    private static final String msLikeListSQL = "select b.ms_no, ms_address, ms_tel, ms_url, ms_holiday, ms_operating, ms_name, ms_rating, ms_like, ms_img, ms_exp from likeTableM a, museum b where user_no=? and a.ms_no = b.ms_no order by ms_no;";
    // �ش��ϴ� �̼��� ���� ����
    private static final String msSelectSQL = "select * from museum where ms_no = ? ;";
    
    // �̼��� ����Ʈ�� �� �̼��� ���� ��������
    @SuppressWarnings({ "unchecked", "null" })
	public JSONObject getMuseumList(){
        Connection conn = null;
        //PreparedStatement stmt = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        JSONArray arr = new JSONArray();
        JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		int n=0;
        //ArrayList<Museum> mslist = new ArrayList<Museum>();
        try{
            conn = JDBCUtil.getConnection();
            
            if(conn!=null){
            	stmt = conn.prepareStatement(msListSQL);//statement��ü����,
            	rst = stmt.executeQuery();
            	System.out.println("[rst]: "+rst);
            } else{
                rst = null;
            }
            //��ȯ�ϴ� �� �������ϼ� �����Ƿ� �ݺ�����, ����� �����Ͱ�ü�� ����
            while(rst.next()){n++;}
            rst = stmt.executeQuery();
        	JSONObject[] temp = new JSONObject[n];
            int i=0;
            String str="[";
            
            while(rst.next()) { //rst�� ������ ��������
                // ����Ʈ�信�� ������ ������ �����
            	obj.put("ms_no", rst.getString(1));
            	obj.put("ms_address", rst.getString(2));
            	obj.put("ms_tel", rst.getString(3));
            	obj.put("ms_url", rst.getString(4));
            	obj.put("ms_holiday", rst.getString(5));
            	obj.put("ms_operating", rst.getString(6));
            	obj.put("ms_name", rst.getString(7));
            	obj.put("ms_rating", rst.getString(8));
            	obj.put("ms_like", rst.getString(9));
            	obj.put("ms_img", rst.getString(10));
            	obj.put("ms_exp", rst.getString(11));
            	
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
           
            json.put("museums", arr);
        } catch(SQLException e){
            System.out.println("list e : " + e);
        } finally {
        	System.out.print(arr);
            JDBCUtil.close(stmt, conn);
        }

        return json; // �̼�������Ʈ ��ü ��ȯ
    }
    
	@SuppressWarnings("unchecked")
	public JSONObject getLikedMuseumList(String a){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rst = null;
        JSONArray arr = new JSONArray();
        JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		int n = 0;
        //ArrayList<Museum> mslist = new ArrayList<Museum>();
        try{
            conn = JDBCUtil.getConnection();
            
            if(conn!=null){
            	//user_no ���� �ʿ�
            	pstmt = conn.prepareStatement(msLikeListSQL);//statement��ü����,
            	pstmt.setInt(1, Integer.parseInt(a));
            	rst = pstmt.executeQuery();
            } else{
                rst = null;
            }
            
            while(rst.next()) {n++;}
            
            System.out.println(n);
            
            rst = pstmt.executeQuery();
            
            JSONObject[] temp = new JSONObject[n];
            int i=0;
            String str="[";
            //��ȯ�ϴ� �� �������ϼ� �����Ƿ� �ݺ�����, ����� �����Ͱ�ü�� ����
            while(rst.next()) { //rst�� ������ ��������
                // ����Ʈ�信�� ������ ������ �����
            	obj.put("ms_no", rst.getString(1));
            	obj.put("ms_address", rst.getString(2));
            	obj.put("ms_tel", rst.getString(3));
            	obj.put("ms_url", rst.getString(4));
            	obj.put("ms_holiday", rst.getString(5));
            	obj.put("ms_operating", rst.getString(6));
            	obj.put("ms_name", rst.getString(7));
            	obj.put("ms_rating", rst.getString(8));
            	obj.put("ms_like", rst.getString(9));
            	obj.put("ms_img", rst.getString(10));
            	obj.put("ms_exp", rst.getString(11));
            	
            	temp[i] = obj;
            	str+= temp[i].toString();
            	System.out.println(obj);
            	if(i < n-1)
            		str+=",";
                
                i++;
            }
            str += "]";
            
            System.out.println(str);
            
            try{
            	JSONParser jp = new JSONParser();
            	Object o = jp.parse(str);
            	arr = (JSONArray)o;            	
            } catch(Exception e) {}
           
            json.put("museums", arr);
            
        } catch(SQLException e){
            System.out.println("list e : " + e);
        } finally {
        	System.out.print(arr);
            JDBCUtil.close(pstmt, conn);
        }

        return json; //s �̼�������Ʈ ��ü ��ȯ
    }

    /**
     * @param msData
     * ms_no�� �ִ� �̼�����ü�� �޾Ƽ� �̼��� ��ü�� ��ȣ�� �������� ���� �������� �Լ�
     */
    public void	getMuseumData(Museum msData){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;

        try{
            conn = JDBCUtil.getConnection();
            //�ϳ��� ���ӿ�û�� �ϳ��� getConnection���� ���ӿ�û�� �� ��, ����� Ŀ�ؼ� ���� (�׷��� ��û ������ ����)

            stmt = conn.prepareStatement(msSelectSQL);
            stmt.setInt(1, Integer.parseInt(msData.getMs_no()));
            rst = stmt.executeQuery();

            if(rst.next()) { //rst�� �ִ��� Ȯ��
                // ��ȣ�� db�󿡼� int�̴ϱ� int�� �޾ƾ��ҵ�?
                msData.setMs_no(rst.getString(1));
                msData.setMs_address(rst.getString(2));
                msData.setMs_phone(rst.getString(3));
                msData.setMs_url(rst.getString(4));
                msData.setMs_holiday(rst.getString(5));
                msData.setMs_operating(rst.getString(6));
                msData.setMs_name(rst.getString(7));
                msData.setMs_rating(rst.getString(8));
                msData.setMs_like(rst.getString(9));
                msData.setMs_Img(rst.getString(10));
                msData.setMs_exp(rst.getString(11));
            }
        } catch(SQLException e){
            System.out.println("getMsData e : " + e);
        } finally {
            JDBCUtil.close(stmt, conn);
        }
    }

}
