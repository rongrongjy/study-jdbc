package rongrong.jdbc;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * @description：怎么避免SQL注入？
 *
 * 注入的主要原因值：先进行了字符串的拼接，在执行SQL语句
 * java.sql.Statement接口的特点：先进行字符串的拼接，然后再进行SQL语句的编译
 *               优点：使用Statement可以进行SQL语句的拼接
 *               缺点：因为拼接 的存在，导致可能给不法分子机会，导致SQL注入
 *  java.sql.PrepardStatement接口的特点：先进行SQL语句的编译，然后再进行SQL语句的传值
 *                优点：避免SQL注入
 *                缺点：没有办法进行SQL语句的拼接，只能给SQL语句传值
 *
 *             PrepardStatement预编译的数据操作对象
 * 即使用户信息中有SQL关键字，但是只要不参加编译就没事
 * @auther lurongrong
 * @create 2021-05-14 11:45
 */
public class JDBCTest4 {
    public static void main(String[] args) {
        //初始化一个界面，可以让用户输入用户名和密码
        Map<String,String> userLoginInfo = initUI();
        //连接数据库验证用户名和密码是否正确
        Boolean ok = checkNameAndPwd(userLoginInfo.get("loginName"),userLoginInfo.get("loginPwd"));
        //jdbc代码


        System.out.println(ok ? "登录成功" : "登录失败");

    }

    /**
     * 验证用户名和密码
     * @param loginName 登录名
     * @param loginPwd 登录密码
     * @return true表示登陆成功 false表示登录失败
     */
    private static Boolean checkNameAndPwd(String loginName, String loginPwd) {
        boolean ok = false;//默认登录失败的！
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        ResourceBundle bundle = ResourceBundle.getBundle("resources/db");
        String driver = bundle.getString("driver");
        String loginName1 = bundle.getString("user");
        String loginPwd1 = bundle.getString("password");
        String url = bundle.getString("url");

        try {
            //注册驱动
            Class.forName(driver);
            //获取连接
            conn = DriverManager.getConnection(url,loginName1,loginPwd1);
            //获取预编译的数据库操作对象
            //注意：一个问号？是一个占位符，一个占位符只能接收一个“值或数据”
            String sql = "select * from admin where username = ? and password = ?";
            stmt = conn.prepareStatement(sql);//此时会发送SQL给DBMS，进行SQL语句的编译
            //给占位符？传值
            stmt.setString(1,loginName);//1代表第一个？（占位符）
            stmt.setString(2,loginPwd);//2代表第二个占位符
            //执行SQL语句
//            System.out.println(sql);
            rs = stmt.executeQuery();
            //结果集只有一条数据，可以不用while
            if (rs.next()){
//                System.out.println(rs.getString(5));
                ok = true;
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
        return ok;
    }

    /**
     * 初始化界面，并且接收用户的输入
     * @return 存储登录名和密码的map集合
     */
    private static Map<String, String> initUI() {
        System.out.println("欢迎使用该系统，请输入用户名和密码进行身份认证");
        Scanner scanner = new Scanner(System.in);
        System.out.println("用户名：");
        String loginName = scanner.next();
        System.out.println("密码：");
        String loginPwd = scanner.next();
        //将用户名和密码放到map集合中
        Map<String, String> userLoginInfo = new HashMap<>();
        userLoginInfo.put("loginName",loginName);
        userLoginInfo.put("loginPwd",loginPwd);
        //返回map
        return userLoginInfo;
    }
}
