package rongrong.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @description：使用PreparedStatament进行增删改查
 * @auther lurongrong
 * @create 2021-05-14 14:42
 */
public class JDBCTest6 {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            //注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获取数据库连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/newssystem","root","root");

            //获取预编译 的数据库操作语言
            String sql = "insert into admin(username,password) values(?,?)";
            ps = conn.prepareStatement(sql);
            //给？传值
            ps.setString(1,"钟融");
            ps.setString(2,"123");
            //执行SQL语句
            int count = ps.executeUpdate();
            System.out.println(count);

        } catch (Exception e) {
            e.printStackTrace();
            //释放连接
        }finally {
            if (ps != null){
                try {
                    ps.close();
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
