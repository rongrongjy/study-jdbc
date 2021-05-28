package rongrong.jdbc;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * @description：模拟用户登录
 *
 * 用户名：
 * abc
 * 密码：
 * abc' or '1'='1
 * select * from admin where username = 'abc' and password = 'abc''
 * 登录成功（这里假设是成功，实际是失败的）
 * 一上随意输入一个用户名和密码，登陆成功了，这种现象被称为SQL注入现象！
 *
 * 导致SQL注入的原因是？如何解决？
 *      导致SQL注入的根本原因是：用户不是一般的用户，用户是懂的程序的，输入的用户信息以及密码信息
 *      中含有SQL语句的关键字，这个SQL语句的关键字和底层的SQL语句进行“字符创的拼接”
 *      导致原SQL语句的含义被扭曲了，最最最主要的原因是：用户提供的信息参与了SQL语句的编译
 *
 *      主要原因是：这个程序是先进性的字符串的拼接，然后再进行SQL语句的编译，正好被注入。
 *
 *
 * @auther lurongrong
 * @create 2021-05-13 21:30
 */
public class JDBCTest3 {
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
        Statement stmt = null;
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
            //获取数据库对象
            stmt = conn.createStatement();
            //执行SQL语句
            String sql = "select * from admin where username = '" + loginName + "' and password = '" + loginPwd+ "'";
            System.out.println(sql);
            rs = stmt.executeQuery(sql);
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
