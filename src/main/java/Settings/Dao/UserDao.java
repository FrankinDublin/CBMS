package Settings.Dao;

import Settings.Domain.User;
import Utils.DBUtil;
import Utils.SelectUtil;
import Utils.WebUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public List<User> selectAllUsers(){
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<User> result=new ArrayList<>();
        try {
            conn= DBUtil.getConnection();
            String sql = "select * from tbl_user";
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()){
                User user=new User();
                WebUtil.SingleDaoToObject(rs,user);
                result.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
    public int InsertUser(User user){
        Connection conn=null;
        PreparedStatement ps=null;
        int result=0;
        try {
            conn= DBUtil.getConnection();
            String sql = "insert into tbl_user(usercode,name,password,role) values(?,?,?,?)";
            ps=conn.prepareStatement(sql);
            ps.setString(1,user.getUsercode());
            /*默认用户名为：用户*/
            ps.setString(2,user.getName());
            ps.setString(3,user.getPassword());
            ps.setString(4,user.getRole());
            result=ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    public int updateBalance(String usercode, String postBalance) {
        String sql = "update tbl_user set balance = ? where usercode = ?";
        Connection conn=null;
        PreparedStatement ps=null;
        int result=0;
        try {
            conn=DBUtil.getConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,postBalance);
            ps.setString(2,usercode);
            result = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public String selectUserBalanceById(String usercode) {
        String sql = "select balance from tbl_user where usercode = ?";
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs = null;
        String result="";
        try {
            conn=DBUtil.getConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,usercode);
            rs=ps.executeQuery();
            rs.next();
            result = rs.getString(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public List<User> selectUserById(String usercode) {
        String sql = "select * from tbl_user where usercode=?";
        return new SelectUtil().selectByOneParam(sql,usercode,new User());
    }
    public int deleteUserByIds(String[] userCodes, String questions) {
        String sql = "delete from tbl_user where usercode in "+questions;
        System.out.println("拼接语句："+sql);
        Connection conn = null;
        PreparedStatement ps = null;
        int result=0;
        try {
            conn= DBUtil.getConnection();
            ps=conn.prepareStatement(sql);
            int i=1;
            for(String s:userCodes){
                ps.setString(i++,s);
            }
            result = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public int updateName(String userCode, String userName) {
        String sql = "update tbl_user set name = ? where usercode = ?";
        Connection conn=null;
        PreparedStatement ps=null;
        int result=0;
        try {
            conn=DBUtil.getConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,userName);
            ps.setString(2,userCode);
            result = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}
