//package com.projectl.dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//
//import com.projectl.vo.Exhi;
//
//public class ExhiDAO {
//	
//	// ���� �Է��ϴ� sql
//    //private static final String insertSQL = "INSERT INTO museum( TITLE, WRITER, CONTENT )"
//    //        + " VALUES( ?, ?, ?);"
//	// ���� ������Ʈ(����)�ϴ� sql
//	//    private static final String updateSQL = "update board set title=? , content=? where seq = ? ;";
//	//����
//	//    private static final String deleteSQL = "delete from BOARD where seq = ? ;";
//
//    // ���� ����Ʈ ����
//    private static final String exhiListSQL = "select * from exhibition order by ex_no desc;";
//    // �ش��ϴ� ���� ���� ����
//    private static final String exhiSelectSQL = "select * from exhibition where ex_no = ? ;";
//    //private static final String updateCntSQL = "update board set cnt=cnt+1 where seq = ? ;";
//
// // �̼��� ����Ʈ�� �� �̼��� ���� ��������
//    @SuppressWarnings({ "unchecked", "null" })
//	public JSONObject getMuseumList(){
//        Connection conn = null;
//        //PreparedStatement stmt = null;
//        PreparedStatement stmt = null;
//        ResultSet rst = null;
//        JSONArray arr = new JSONArray();
//        JSONObject obj = new JSONObject();
//		JSONObject json = new JSONObject();
//		
//        //ArrayList<Museum> mslist = new ArrayList<Museum>();
//        try{
//            conn = JDBCUtil.getConnection();
//            
//            if(conn!=null){
//            	stmt = conn.prepareStatement(msListSQL);//statement��ü����,
//            	rst = stmt.executeQuery();
//            } else{
//                rst = null;
//            }
//            //��ȯ�ϴ� �� �������ϼ� �����Ƿ� �ݺ�����, ����� �����Ͱ�ü�� ����
//            while(rst.next()) { //rst�� ������ ��������
//                // ����Ʈ�信�� ������ ������ �����
//            	obj.put("ms_no", rst.getString(1));
//            	obj.put("ms_address", rst.getString(2));
//            	obj.put("ms_phone", rst.getString(3));
//            	obj.put("ms_url", rst.getString(4));
//            	obj.put("ms_holiday", rst.getString(5));
//            	obj.put("ms_operating", rst.getString(6));
//            	obj.put("ms_name", rst.getString(7));
//            	obj.put("ms_rating", rst.getString(8));
//            	obj.put("ms_like", rst.getString(9));
//            	obj.put("ms_img", rst.getString(10));
//            	obj.put("ms_exp", rst.getString(11));
//                arr.add(obj);
//                json.put("museums", arr);
//                //mslist.add(msData);
//            }
//        } catch(SQLException e){
//            System.out.println("list e : " + e);
//        } finally {
//        	System.out.print(arr);
//            JDBCUtil.close(stmt, conn);
//        }
//
//        return json; // �̼�������Ʈ ��ü ��ȯ
//    }
//    
//	@SuppressWarnings("unchecked")
//	public JSONObject getLikedMuseumList(String userNo){
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        ResultSet rst = null;
//        JSONArray arr = new JSONArray();
//        JSONObject obj = new JSONObject();
//		JSONObject json = new JSONObject();
//		
//        //ArrayList<Museum> mslist = new ArrayList<Museum>();
//        try{
//            conn = JDBCUtil.getConnection();
//            
//            if(conn!=null){
//            	//user_no ���� �ʿ�
//            	pstmt = conn.prepareStatement(msLikeListSQL);//statement��ü����,
//            	pstmt.setString(1, userNo);
//            	rst = pstmt.executeQuery();
//            } else{
//                rst = null;
//            }
//            //��ȯ�ϴ� �� �������ϼ� �����Ƿ� �ݺ�����, ����� �����Ͱ�ü�� ����
//            while(rst.next()) { //rst�� ������ ��������
//                // ����Ʈ�信�� ������ ������ �����
//            	obj.put("liked_no", rst.getString(1));
//            	obj.put("user_no", rst.getString(2));
//            	obj.put("liked_museum_no", rst.getString(3));
//            	obj.put("liked_exhi_no", rst.getString(4));
//                arr.add(obj);
//                json.put("likeMuseums", arr);
//                //mslist.add(msData);
//            }
//        } catch(SQLException e){
//            System.out.println("list e : " + e);
//        } finally {
//        	System.out.print(arr);
//            JDBCUtil.close(pstmt, conn);
//        }
//
//        return json; //s �̼�������Ʈ ��ü ��ȯ
//    }    
//    
//  //string seq �Ҽ��� ������ ��ü�� �޾Ƽ� ���� ���Ȯ�� �����ϵ��� ����
//    @SuppressWarnings({ "resource", "null" })
//    public void	getExhibitionData(Exhibition exData){
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        ResultSet rst = null;
//
//        try{
//            conn = JDBCUtil.getConnection();
//            //�ϳ��� ���ӿ�û�� �ϳ��� getConnection���� ���ӿ�û�� �� ��, ����� Ŀ�ؼ� ���� (�׷��� ��û ������ ����)
//
//            stmt = conn.prepareStatement(exhiSelectSQL);
//            stmt.setInt(1, Integer.parseInt(exData.getEx_no()));
//            rst = stmt.executeQuery();
//
//            if(rst.next()) { //rst�� �ִ��� Ȯ��
//                // ��ȣ�� db�󿡼� int�̴ϱ� int�� �޾ƾ��ҵ�?
//                exData.setEx_no(rst.getString(1));
//                exData.setEx_name(rst.getString(2));
//                exData.setEx_theme(rst.getString(3));
//                exData.setEx_like(rst.getString(4));
//                exData.setEx_img(rst.getString(5));
//                exData.setMs_no(rst.getString(6));
//                exData.setEx_location(rst.getString(7));
//                exData.setEx_rating(rst.getString(8));
//                exData.setEx_pay(rst.getString(9));
//                exData.setEx_exp(rst.getString(10));
//                exData.setEx_period(rst.getString(11));
//                exData.setEx_ing(rst.getString(12));
//            }
//        } catch(SQLException e){
//            System.out.println("getMsData e : " + e);
//        } finally {
//            JDBCUtil.close(stmt, conn);
//        }
//    }
//}