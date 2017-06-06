package com.projectl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.projectl.vo.Museum;
import com.projectl.vo.Piece;

public class PieceDAO {
	 // �̼��� ����Ʈ ����
    private static final String pcListSQL = "select * from piece order by pc_no desc;";
    // ���ƿ��� �̼��� ����Ʈ ����
    //private static final String msLikeListSQL = "select * from liked where user_no=? order by user_no desc;";
    // �ش��ϴ� �̼��� ���� ����
    private static final String pcSelectSQL = "select ms_no, ex_no, pc_no, pc_name, pc_author, pc_make, pc_exp, pc_img, pc_audio, pc_size from piece where ms_no = ? and ex_no= ? and pc_no = ? ;";
    
    Piece pcData;
    
    @SuppressWarnings({ "unchecked", "null" })
   	public JSONObject getPieceList(){
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
               	stmt = conn.prepareStatement(pcListSQL);//statement��ü����,
               	rst = stmt.executeQuery();
               	System.out.println("[rst]: "+rst);
               } else{
                   rst = null;
               }
               //��ȯ�ϴ� �� �������ϼ� �����Ƿ� �ݺ�����, ����� �����Ͱ�ü�� ����
               if(rst.first())
               {}
               rst = stmt.executeQuery();
               while(rst.next()) { //rst�� ������ ��������
                   // ����Ʈ�信�� ������ ������ �����
               	rst.next();
               	obj.put("ms_no", rst.getString(1));
               	obj.put("ex_no", rst.getString(2));
               	obj.put("pc_no", rst.getString(3));
               	obj.put("pc_name", rst.getString(4));
               	obj.put("pc_author", rst.getString(5));
               	obj.put("pc_make", rst.getString(6));
               	obj.put("pc_exp", rst.getString(7));
               	obj.put("pc_img", rst.getString(8));
               	obj.put("pc_audio", rst.getString(9));
               	obj.put("pc_size", rst.getString(10));
                   arr.add(obj);
                   //mslist.add(msData);
               }
              
           } catch(SQLException e){
               System.out.println("list e : " + e);
           } finally {
               JDBCUtil.close(stmt, conn);
           }

           return json; // �̼�������Ʈ ��ü ��ȯ
       }
    
    public JSONObject getPieceData(String a, String b, String c){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        JSONObject obj = new JSONObject();

        try{
            conn = JDBCUtil.getConnection();
            //�ϳ��� ���ӿ�û�� �ϳ��� getConnection���� ���ӿ�û�� �� ��, ����� Ŀ�ؼ� ���� (�׷��� ��û ������ ����)

            stmt = conn.prepareStatement(pcSelectSQL);
            stmt.setInt(1, Integer.parseInt(a));
            stmt.setInt(2, Integer.parseInt(b));
            stmt.setInt(3, Integer.parseInt(c));
            rst = stmt.executeQuery();

            if(rst.next()) { //rst�� �ִ��� Ȯ��
                // ��ȣ�� db�󿡼� int�̴ϱ� int�� �޾ƾ��ҵ�?
//                pcData.setMs_no(rst.getString(1));
//                pcData.setEx_no(rst.getString(2));
//                pcData.setPc_no(rst.getString(3));
//                pcData.setPc_name(rst.getString(4));
//                pcData.setPc_author(rst.getString(5));
//                pcData.setPc_make(rst.getString(6));
//                pcData.setPc_exp(rst.getString(7));
//                pcData.setPc_img(rst.getString(8));
//                pcData.setPc_audio(rst.getString(9));
//                pcData.setPc_size(rst.getString(10));
            	obj.put("ms_no", rst.getString(1));
               	obj.put("ex_no", rst.getString(2));
               	obj.put("pc_no", rst.getString(3));
               	obj.put("pc_name", rst.getString(4));
               	obj.put("pc_author", rst.getString(5));
               	obj.put("pc_make", rst.getString(6));
               	obj.put("pc_exp", rst.getString(7));
               	obj.put("pc_img", rst.getString(8));
               	obj.put("pc_audio", rst.getString(9));
               	obj.put("pc_size", rst.getString(10));
            }
        } catch(SQLException e){
            System.out.println("getPcData e : " + e);
        } finally {
            JDBCUtil.close(stmt, conn);
        }
        return obj;
    }
}
