package Utils;

import workbench.Domain.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelectUtil{
    public List selectAll(String sql,Object object){
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<Object> result=new ArrayList<>();
        Class c=object.getClass();
        try {
            conn= DBUtil.getConnection();
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()){
                Object obj = null;
                try {
                    obj = c.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                WebUtil.SingleDaoToObject(rs,obj);
                result.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public List selectByOneParam(String sql,String param,Object object){
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<Object> result=new ArrayList<>();
        Class c=object.getClass();
        try {
            conn= DBUtil.getConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,param);
            rs=ps.executeQuery();
            while (rs.next()){
                Object obj= null;
                try {
                    obj = c.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                WebUtil.SingleDaoToObject(rs,obj);
                result.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
    public List<Movie> selectByConditionParams(String sql, List<String> params){
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<Movie> result=new ArrayList<>();
        try {
            conn= DBUtil.getConnection();
            ps=conn.prepareStatement(sql);
            int i=1;
            for(String s:params){
                ps.setString(i++,"%"+s+"%");
            }
            rs=ps.executeQuery();
            while (rs.next()){
                Movie movie = new Movie();
                WebUtil.SingleDaoToObject(rs,movie);
                result.add(movie);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public List selectByParams(String sql, List<String> params, Object object) {
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<Object> result=new ArrayList<>();
        Class c=object.getClass();
        try {
            conn= DBUtil.getConnection();
            ps=conn.prepareStatement(sql);
            int i=1;
            for(String s:params)
                ps.setString(i++,s);
            rs=ps.executeQuery();
            while (rs.next()){
                Object obj= null;
                try {
                    obj = c.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                WebUtil.SingleDaoToObject(rs,obj);
                result.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}
