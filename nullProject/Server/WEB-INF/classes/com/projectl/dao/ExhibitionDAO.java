package com.projectl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.projectl.vo.Exhibition;

public class ExhibitionDAO {
	
	// ���� �Է��ϴ� sql
    //private static final String insertSQL = "INSERT INTO museum( TITLE, WRITER, CONTENT )"
    //        + " VALUES( ?, ?, ?);"
	// ���� ������Ʈ(����)�ϴ� sql
	//    private static final String updateSQL = "update board set title=? , content=? where seq = ? ;";
	//����
	//    private static final String deleteSQL = "delete from BOARD where seq = ? ;";

    // ���� ����Ʈ ����
    private static final String exhiListSQL = "select ex_no, ex_name, ex_theme, ex_like, ex_img, ms_no, ex_location, ex_rating, ex_pay, ex_exp, ex_period, ex_ing from exhibition order by ex_no desc;";
    // �ش��ϴ� ���� ���� ����
    private static final String exhiSelectSQL = "select * from exhibition where ex_no = ? ;";
    //private static final String updateCntSQL = "update board set cnt=cnt+1 where seq = ? ;";
    
    private static final String exhiLikeListSQL = "select b.ex_no, ex_name, ex_theme, ex_like, ex_img, b.ms_no, ex_location, ex_rating, ex_pay, ex_exp, ex_period, ex_ing from likeTableE a, exhibition b where a.ms_no=b.ms_no and a.ex_no=b.ex_no and user_no=?";

    private static final String exhilikeSQL = "insert into likeTableE(ms_no, ex_no, user_no) values(?, ?, ?);";
    
    private static final String exhiunlikeSQL = "delete from likeTableE where ms_no =? and ex_no=? and user_no=?;";
    
 // �̼��� ����Ʈ�� �� �̼��� ���� ��������
    @SuppressWarnings({ "unchecked", "null" })
	public JSONObject getExhibitionList(){
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
            	stmt = conn.prepareStatement(exhiListSQL);//statement��ü����,
            	rst = stmt.executeQuery();
            } else{
                rst = null;
            }
            while(rst.next()){n++;}
            rst = stmt.executeQuery();
        	JSONObject[] temp = new JSONObject[n];
            int i=0;
            String str="[";
            
            //��ȯ�ϴ� �� �������ϼ� �����Ƿ� �ݺ�����, ����� �����Ͱ�ü�� ����
            while(rst.next()) { //rst�� ������ ��������
                // ����Ʈ�信�� ������ ������ �����
            	obj.put("ex_no", rst.getString(1));
            	obj.put("ex_name", rst.getString(2));
            	obj.put("ex_theme", rst.getString(3));
            	obj.put("ex_like", rst.getString(4));
            	obj.put("ex_img", rst.getString(5));
            	obj.put("ms_no", rst.getString(6));
            	obj.put("ex_location", rst.getString(7));
            	obj.put("ex_rating", rst.getString(8));
            	obj.put("ex_pay", rst.getString(9));
            	obj.put("ex_exp", rst.getString(10));
            	obj.put("ex_period", rst.getString(11));
            	obj.put("ex_ing", rst.getString(12));

            	temp[i] = obj;
            	str+= temp[i].toString();            	
            	if(i < n-1)
            		str+=",";
                
                i++;
            }
            str += "]";
            try{
            	JSONParser jp = new JSONParser();
            	Object o = jp.parse(str);
            	arr = (JSONArray)o;            	
            } catch(Exception e) {}
            json.put("exhibitions", arr);
        } catch(SQLException e){
            System.out.println("list e : " + e);
        } finally {
        	System.out.print(arr);
            JDBCUtil.close(stmt, conn);
        }

        return json; // �̼�������Ʈ ��ü ��ȯ
    }
    
	@SuppressWarnings("unchecked")
	public JSONObject getLikeExhibitionList(String a){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rst = null;
        JSONArray arr = new JSONArray();
        JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		int n=0;
		
        //ArrayList<Museum> mslist = new ArrayList<Museum>();
        try{
            conn = JDBCUtil.getConnection();
            
            if(conn!=null){
            	//user_no ���� �ʿ�
            	pstmt = conn.prepareStatement(exhiLikeListSQL);//statement��ü����,
            	pstmt.setInt(1, Integer.parseInt(a));
            	rst = pstmt.executeQuery();
            } else{
                rst = null;
            }
            while(rst.next()){n++;}
            rst = pstmt.executeQuery();
            JSONObject[] temp = new JSONObject[n];
            int i=0;
            String str="[";
            
            //��ȯ�ϴ� �� �������ϼ� �����Ƿ� �ݺ�����, ����� �����Ͱ�ü�� ����
            while(rst.next()) { //rst�� ������ ��������
                // ����Ʈ�信�� ������ ������ �����
            	obj.put("ex_no", rst.getString(1));
            	obj.put("ex_name", rst.getString(2));
            	obj.put("ex_theme", rst.getString(3));
            	obj.put("ex_like", rst.getString(4));
            	obj.put("ex_img", rst.getString(5));
            	obj.put("ms_no", rst.getString(6));
            	obj.put("ex_location", rst.getString(7));
            	obj.put("ex_rating", rst.getString(8));
            	obj.put("ex_pay", rst.getString(9));
            	obj.put("ex_exp", rst.getString(10));
            	obj.put("ex_period", rst.getString(11));
            	obj.put("ex_ing", rst.getString(12));

            	temp[i] = obj;
            	str+= temp[i].toString();            	
            	if(i < n-1)
            		str+=",";
                
                i++;
            }
            str += "]";
            try{
            	JSONParser jp = new JSONParser();
            	Object o = jp.parse(str);
            	arr = (JSONArray)o;            	
            } catch(Exception e) {}
            json.put("exhibitions", arr);
        } catch(SQLException e){
            System.out.println("list e : " + e);
        } finally {
        	System.out.print(arr);
            JDBCUtil.close(pstmt, conn);
        }

        return json; //s �̼�������Ʈ ��ü ��ȯ
    }    
    
  //string seq �Ҽ��� ������ ��ü�� �޾Ƽ� ���� ���Ȯ�� �����ϵ��� ����
    @SuppressWarnings({ "resource", "null" })
    public void	getExhibitionData(Exhibition exData){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;

        try{
            conn = JDBCUtil.getConnection();
            //�ϳ��� ���ӿ�û�� �ϳ��� getConnection���� ���ӿ�û�� �� ��, ����� Ŀ�ؼ� ���� (�׷��� ��û ������ ����)

            stmt = conn.prepareStatement(exhiSelectSQL);
            stmt.setInt(1, Integer.parseInt(exData.getEx_no()));
            rst = stmt.executeQuery();

            if(rst.next()) { //rst�� �ִ��� Ȯ��
                // ��ȣ�� db�󿡼� int�̴ϱ� int�� �޾ƾ��ҵ�?
                exData.setEx_no(rst.getString(1));
                exData.setEx_name(rst.getString(2));
                exData.setEx_theme(rst.getString(3));
                exData.setEx_like(rst.getString(4));
                exData.setEx_img(rst.getString(5));
                exData.setMs_no(rst.getString(6));
                exData.setEx_location(rst.getString(7));
                exData.setEx_rating(rst.getString(8));
                exData.setEx_pay(rst.getString(9));
                exData.setEx_exp(rst.getString(10));
                exData.setEx_period(rst.getString(11));
                exData.setEx_ing(rst.getString(12));
            }
        } catch(SQLException e){
            System.out.println("getMsData e : " + e);
        } finally {
            JDBCUtil.close(stmt, conn);
        }
    }
}