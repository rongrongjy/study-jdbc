package rongrong.jdbc;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * @description：
 * @auther lurongrong
 * @create 2021-05-12 22:53
 */
public class JDBCTest2 {
    public static void main(String[] args) {
        //连接数据库与的信息统一写到属性配置文件中
        //资源绑定器
        ResourceBundle bundle = ResourceBundle.getBundle("resources/db");
        //通过属性配置文件拿到配置信息
        String driver = bundle.getString("driver");
        String url = bundle.getString("url");
        String user = bundle.getString("user");
        String password = bundle.getString("password");

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;


        try{
            //注册驱动

            Class.forName(driver);

            //获取连接
            conn = DriverManager.getConnection(url,user,password);
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
