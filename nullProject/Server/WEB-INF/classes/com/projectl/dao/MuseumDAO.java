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
	
    // 미술관 리스트 선택
    private static final String msListSQL = "select ms_no, ms_address, ms_tel, ms_url, ms_holiday, ms_operating, ms_name, ms_rating, ms_like, ms_img, ms_exp from museum;";
    // 좋아요한 미술관 리스트 선택
    private static final String msLikeListSQL = "select b.ms_no, ms_address, ms_tel, ms_url, ms_holiday, ms_operating, ms_name, ms_rating, ms_like, ms_img, ms_exp from likeTableM a, museum b where user_no=? and a.ms_no = b.ms_no order by ms_no;";
    // 해당하는 미술관 정보 선택
    private static final String msSelectSQL = "select * from museum where ms_no = ? ;";
    
    // 미술관 리스트에 각 미술관 정보 가져오기
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
            	stmt = conn.prepareStatement(msListSQL);//statement객체생성,
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

        return json; // 미술관리스트 객체 반환
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
            	//user_no 설정 필요
            	pstmt = conn.prepareStatement(msLikeListSQL);//statement객체생성,
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
            //반환하는 것 여러개일수 있으므로 반복수행, 결과를 데이터객체에 저장
            while(rst.next()) { //rst의 마지막 까지실행
                // 리스트뷰에서 보여줄 정보만 남기기
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

        return json; //s 미술관리스트 객체 반환
    }

    /**
     * @param msData
     * ms_no만 있는 미술관객체를 받아서 미술관 객체의 번호를 기준으로 값을 가져오는 함수
     */
    public void	getMuseumData(Museum msData){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;

        try{
            conn = JDBCUtil.getConnection();
            //하나의 접속요청에 하나의 getConnection으로 접속요청을 할 것, 종료시 커넥션 종료 (그래야 요청 섞이지 않음)

            stmt = conn.prepareStatement(msSelectSQL);
            stmt.setInt(1, Integer.parseInt(msData.getMs_no()));
            rst = stmt.executeQuery();

            if(rst.next()) { //rst이 있는지 확인
                // 번호가 db상에서 int이니까 int로 받아야할듯?
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
