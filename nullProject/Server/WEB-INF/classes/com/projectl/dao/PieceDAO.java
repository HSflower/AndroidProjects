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
	 // 미술관 리스트 선택
    private static final String pcListSQL = "select * from piece order by pc_no desc;";
    // 좋아요한 미술관 리스트 선택
    //private static final String msLikeListSQL = "select * from liked where user_no=? order by user_no desc;";
    // 해당하는 미술관 정보 선택
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
               	stmt = conn.prepareStatement(pcListSQL);//statement객체생성,
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

           return json; // 미술관리스트 객체 반환
       }
    
    public JSONObject getPieceData(String a, String b, String c){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        JSONObject obj = new JSONObject();

        try{
            conn = JDBCUtil.getConnection();
            //하나의 접속요청에 하나의 getConnection으로 접속요청을 할 것, 종료시 커넥션 종료 (그래야 요청 섞이지 않음)

            stmt = conn.prepareStatement(pcSelectSQL);
            stmt.setInt(1, Integer.parseInt(a));
            stmt.setInt(2, Integer.parseInt(b));
            stmt.setInt(3, Integer.parseInt(c));
            rst = stmt.executeQuery();

            if(rst.next()) { //rst이 있는지 확인
                // 번호가 db상에서 int이니까 int로 받아야할듯?
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
