package workbench.Dao;

import Settings.Domain.User;
import Utils.DBUtil;
import Utils.SelectUtil;
import Utils.WebUtil;
import vo.UserCommentInfo;
import workbench.Domain.MovieComment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieCommentDao {
    public List<MovieComment> selectCommentsByMid(String Mid){
        /*Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<MovieComment> result=new ArrayList<>();
        try {
            conn= DBUtil.getConnection();
            String sql = "select * from tbl_movie_remark where movieId=? order by createTime desc";
            ps=conn.prepareStatement(sql);
            ps.setString(1,Mid);
            rs=ps.executeQuery();
            while (rs.next()){
                MovieComment movieComment = new MovieComment();
                WebUtil.SingleDaoToObject(rs,movieComment);
                result.add(movieComment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;*/
        String sql = "select a.id,a.comment,a.createTime,b.name createBy,a.editTime,a.grade,a.editFlag,a.movieId from tbl_movie_remark a join tbl_user b on a.createBy = b.usercode where a.movieId=? order by a.createTime desc";
        SelectUtil selectUtil= new SelectUtil();
        return selectUtil.selectByOneParam(sql,Mid,new MovieComment());

    }

    public int insertComment(MovieComment movieComment) {
        Connection conn=null;
        PreparedStatement ps=null;
        int result=0;
        try {
            conn=DBUtil.getConnection();
            String sql="insert into tbl_movie_remark(id,comment,createTime,createBy,editTime,grade,editFlag,movieId) " +
                    "values (?,?,?,?,?,?,?,?)";
            ps=conn.prepareStatement(sql);
            WebUtil.insertHelper(movieComment,ps);
            result=ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }


    public List<UserCommentInfo> selectCommentsByUid(String userId) {
        String sql = "select b.name movieName,a.grade,a.createTime,a.editTime,a.comment,a.editFlag from tbl_movie_remark a join tbl_movie b on a.movieId=b.id where a.createBy=? order by a.createTime desc";
        return new SelectUtil().selectByOneParam(sql,userId,new UserCommentInfo());
    }

    public int deleteCommentById(String commentId) {
        String sql = "delete from tbl_movie_remark where id = ?" ;
        Connection conn=null;
        PreparedStatement ps=null;
        int result=0;
        try {
            conn=DBUtil.getConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,commentId);
            result=ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public int deleteCommentByMovieId(Connection conn, String movieId) {
        String sql = "delete from tbl_movie_remark where movieId = ?";
        PreparedStatement ps;
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

    public int updateComment(String id, String comment) {
        String sql = "update tbl_movie_remark set comment = ? where id = ?";
        Connection conn=null;
        PreparedStatement ps=null;
        int result=0;
        try {
            conn=DBUtil.getConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,comment);
            ps.setString(2,id);
            result=ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}
