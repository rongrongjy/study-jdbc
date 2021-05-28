package rongrong.jdbc;

import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * @description：
 * @auther lurongrong
 * @create 2021-05-14 14:15
 */
public class JDBCTest5 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("请输入desc或asc【desc表示降序，asc表示升序】：");
        //用户输入
        String orderKey = s.next();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;


//        ResourceBundle bundle = ResourceBundle.getBundle("resources/db");
//
//        bundle.getString("")

        try {
            //注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获取数据库连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/newssystem","root","root");
            //获取数据库中的操作对象
            stmt = conn.createStatement();
            //执行SQL语句
            String sql = "select * from admin order by id " + orderKey;
            rs = stmt.executeQuery(sql);
            //处理查询结果集
            while (rs.next()){
                System.out.println(rs.getString(2));;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
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
