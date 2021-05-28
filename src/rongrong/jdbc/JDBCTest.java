package rongrong.jdbc;

import java.sql.*;

/**
 * @description：
 * @create 2021-05-12 20:09
 */
public class JDBCTest {
    public static void main(String[] args) {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            //注册驱动
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            //获取连接  newsSystem
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/newssystem",
                    "root","root");
            //获取数据库操作对象
            stmt = conn.createStatement();
            //实行SQL语句
            String sql = "select * from admin";
            rs = stmt.executeQuery(sql);
            //处理查询结果集
            while (rs.next()){
                System.out.println(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //释放资源
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


}
