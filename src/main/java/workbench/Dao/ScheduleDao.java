package workbench.Dao;

import Utils.InsertUtil;
import Utils.SelectUtil;
import vo.ScheduleInfo;
import workbench.Domain.Purchase;
import workbench.Domain.Schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ScheduleDao {
    public List<Schedule> selectSchedulesByMid(String Mid){
        String sql = "select * from tbl_schedule where movieId = ?";
        SelectUtil selectUtil = new SelectUtil();
        return selectUtil.selectByOneParam(sql,Mid,new Schedule());
    }

    public List<ScheduleInfo> selectScheduleInfoByMid(String mid) {
        String sql = "select a.id,b.name hall,a.showTime,a.showPeriod,b.row,b.col from tbl_schedule a join tbl_hall b on a.hallId=b.id where a.movieId=? order by b.name,a.showTime,a.showPeriod";
        return new SelectUtil().selectByOneParam(sql,mid,new ScheduleInfo());
    }

    public int insertSchedule(Schedule schedule) {
        String sql = "insert into tbl_schedule(id,movieId,hallId,showTime,showPeriod) values(?,?,?,?,?)";
        return new InsertUtil().insertHelper(sql,schedule);
    }

    public List<Purchase> selectUserPurchasesByScheduleId(String scheduleId) {
        String sql = "select * from tbl_user_purchase where scheduleId=? order by rows,cols";
        return new SelectUtil().selectByOneParam(sql,scheduleId,new Purchase());
    }

    public int deleteScheduleByMid(Connection conn, String movieId) {
        String sql = "delete from tbl_schedule where movieId = ?";
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
}
