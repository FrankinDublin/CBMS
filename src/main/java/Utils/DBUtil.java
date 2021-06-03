package Utils;

import java.sql.*;

public class DBUtil {
    static ThreadLocal<Connection> local = new ThreadLocal<Connection>();
    /*注册驱动*/
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /*获取连接*/
    public static Connection getConnection() throws SQLException {
        Connection conn=local.get();
        if(conn==null) {
            String url = "jdbc:mysql://127.0.0.1:3306/CBMS";
            String un = "root";
            String pw = "root";
            try {
                conn=DriverManager.getConnection(url,un,pw);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            local.set(conn);
        }
        return conn;
    }
    /*关闭流*/
    public static void close(ResultSet rs, Statement ps, Connection conn) {
        if(rs!=null)
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        if(ps!=null)
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        if(conn!=null)
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
    /*开始事务*/
    public static void start_transaction(Connection conn)  {
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*提交事务*/
    public static void commit(Connection conn) {
        try {
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*回滚事务*/
    public static void rollback(Connection conn) {
        try {
            conn.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
