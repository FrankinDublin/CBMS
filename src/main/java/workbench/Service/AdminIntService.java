package workbench.Service;

import Settings.Dao.UserDao;
import Settings.Domain.User;
import Utils.DBUtil;
import Utils.WebUtil;
import workbench.Dao.MovieCommentDao;
import workbench.Dao.MovieDao;
import workbench.Dao.ScheduleDao;
import workbench.Dao.TicketSetDao;
import workbench.Domain.Movie;
import workbench.Domain.TicketSet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AdminIntService {
    MovieDao movieDao = new MovieDao();
    MovieCommentDao movieCommentDao = new MovieCommentDao();
    ScheduleDao scheduleDao = new ScheduleDao();
    UserDao userDao = new UserDao();
    TicketSetDao ticketSetDao = new TicketSetDao();

    public Movie ShowMovieDetail(String id) { return movieDao.selectMovieById(id); }

    public boolean deleteComment(String commentId) {
        return movieCommentDao.deleteCommentById(commentId)==1;
    }

    public boolean deleteMovie(String movieId) {
        Connection conn = null;
        boolean flag = true;
        try {
            conn=DBUtil.getConnection();
            DBUtil.start_transaction(conn);
            movieCommentDao.deleteCommentByMovieId(conn,movieId);
            scheduleDao.deleteScheduleByMid(conn,movieId);
            int result=movieDao.deleteMovieById(conn,movieId);
            if(result!=1) throw new SQLException();
            DBUtil.commit(conn);
        } catch (SQLException throwables) {
            DBUtil.rollback(conn);
            flag=false;
            throwables.printStackTrace();
        }
        return flag;
    }

    public List<User> selectAllUsers() {
        return userDao.selectAllUsers();
    }

    public List<TicketSet> selectAllSets() { return ticketSetDao.selectAllSets(); }

    public List<TicketSet> selectSetById(String id) { return ticketSetDao.selectSetById(id); }

    public List<User> selectUserById(String usercode) {
        return userDao.selectUserById(usercode);
    }

    public boolean deleteSet(String[] ids) {
        String questions = WebUtil.questionMarkSpawner(ids.length);
        return ticketSetDao.deleteSetByIds(ids,questions)!=0;
    }

    public boolean deleteUser(String[] userCodes) {
        String questions = WebUtil.questionMarkSpawner(userCodes.length);
        return userDao.deleteUserByIds(userCodes,questions)!=0;
    }

    public boolean updateUser(String userCode, String userName) {
        return userDao.updateName(userCode,userName)==1;
    }

    public boolean updateSet(String setId, String setInclude, String setPrice) {
        return ticketSetDao.updateSet(setId,setInclude,setPrice)==1;
    }

    public boolean updateComment(String id, String comment) {
        return movieCommentDao.updateComment(id,comment)==1;
    }

    public boolean updateMovie(Movie movie) {
        return movieDao.updateMovie(movie)==1;
    }
}
