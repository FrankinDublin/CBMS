package workbench.Dao;

import Settings.Domain.User;
import Utils.DBUtil;
import Utils.InsertUtil;
import Utils.SelectUtil;
import Utils.WebUtil;
import workbench.Domain.Condition;
import workbench.Domain.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MovieDao {
    public List<Movie> selectAllMovies(Condition condition, int count, int sortMark){
        String piece1 = " order by rank";
        String piece2 = " order by rank desc";
        String sql = "select * from tbl_movie";
        String sql2 = WebUtil.connectSqlStatement(sql,condition,count);
        String sql3="";
        if(sortMark%2==0) sql3=sql2+piece2;
        else if(sortMark%2==1) sql3=sql2+piece1;
        else return new SelectUtil().selectByConditionParams(sql2,WebUtil.transferConditionIntoArray(condition));
        return new SelectUtil().selectByConditionParams(sql3,WebUtil.transferConditionIntoArray(condition));
       /* Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<Movie> result=new ArrayList<>();
        try {
            conn= DBUtil.getConnection();

            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()){
                Movie temp = new Movie();
                WebUtil.SingleDaoToObject(rs,temp);
                result.add(temp);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;*/
    }
    public Movie selectMovieById(String id){
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Movie movie=new Movie();
        try {
            conn=DBUtil.getConnection();
            String sql = "select * from tbl_movie where id = ?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,id);
            rs=ps.executeQuery();
            rs.next();
            WebUtil.SingleDaoToObject(rs,movie);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return movie;
    }

    public int insertMovie(Movie movie) {
        String sql = "insert into tbl_movie(id,name,producer,showTime,ticketPrice,introduce,movieType,createTime) values(?,?,?,?,?,?,?,?)";
        return new InsertUtil().insertHelper(sql,movie);
    }

    public List<Movie> selectMovieByProducer(String producer, int count) {
        String sql="select * from tbl_movie where producer=? order by createTime desc";
        return new SelectUtil().selectByOneParam(sql,producer,new Movie());
    }

    public int updateMovie(Movie movie) {
        Connection conn=null;
        PreparedStatement ps=null;
        int result=0;
        try {
            conn=DBUtil.getConnection();
            String sql = "update tbl_movie set name=?,showTime=?,ticketPrice=?,movieType=?,introduce=? where id=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,movie.getName());
            ps.setString(2,movie.getShowTime());
            ps.setString(3,movie.getTicketPrice());
            ps.setString(4,movie.getMovieType());
            ps.setString(5,movie.getIntroduce());
            ps.setString(6,movie.getId());
            result=ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public int deleteMovieById(Connection conn, String movieId) {
        String sql = "delete from tbl_movie where id = ?";
        PreparedStatement ps=null;
        int result=0;
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1,movieId);
            result=ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public int updateMovieGrade(String id, String grade) {
        String sql = "update tbl_movie set rank = ? where id = ?";
        Connection conn=null;
        PreparedStatement ps=null;
        int result=0;
        try {
            conn=DBUtil.getConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,grade);
            ps.setString(2,id);
            result=ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}
