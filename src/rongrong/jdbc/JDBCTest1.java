package rongrong.jdbc;

import java.sql.*;

/**
 * @description：
 * @auther lurongrong
 * @create 2021-05-12 22:53
 */
public class JDBCTest1 {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            //注册驱动

            Class.forName("com.mysql.jdbc.Driver");

            //获取连接
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/newssystem","root","root");
            //获取数据库对象
            stmt = conn.createStatement();
            //执行SQL语句
            String sql = "select * from admin";
            rs = stmt.executeQuery(sql);
            //处理查询结果集
            while (rs.next()){
                System.out.println(rs.getString(2));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }

       if (rs != null){
           try {
               rs.close();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
       if (stmt != null){
           try {
               stmt.close();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
       if (conn != null){
           try {
               conn.close();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }

    }
}
