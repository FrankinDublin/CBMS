package workbench.Dao;

import Utils.InsertUtil;
import Utils.SelectUtil;
import vo.MovieProfitInfo;
import vo.TicketSetProfitInfo;
import vo.UserPurchaseInfo;
import workbench.Domain.Purchase;
import vo.PurchaseSeatInfo;

import java.util.List;

public class PurchaseDao {
    public int insertPurchase(Purchase purchase) {
        String sql = "insert into tbl_user_purchase(id,userId,scheduleId,rows,cols,ticketSet,cost,dealTime) values (?,?,?,?,?,?,?,?)";
        return new InsertUtil().insertHelper(sql,purchase);
    }

    public List<UserPurchaseInfo> selectPurchaseByUserId(String userId) {
        String sql = "select y.id,x.movieName,x.hallName,x.showTime,x.showPeriod,y.cols,y.rows,z.setInclude,y.cost,y.dealTime from tbl_user_purchase y join (select a.id,a.showTime,a.showPeriod,b.name movieName,c.name hallName from tbl_schedule a join tbl_movie b on a.movieId = b.id join tbl_hall c on a.hallId = c.id) x on y.scheduleId=x.id left join tbl_ticketset z on y.ticketSet=z.id where y.userId=? order by y.dealTime desc";
        return new SelectUtil().selectByOneParam(sql,userId,new UserPurchaseInfo());

    }

    public List<MovieProfitInfo> selectCountGroupBySchedule(String producer) {
        /*
        * 执行结果：
        * 电影名 厂商名 票价 数量
        * */
        String sql = "select c.name,c.producer,c.ticketPrice,b.count from tbl_schedule a join (select scheduleId,count(*) count from tbl_user_purchase group by scheduleId)b on b.scheduleId=a.id join tbl_movie c on a.movieId=c.id where c.producer = ?";
        return new SelectUtil().selectByOneParam(sql,producer,new MovieProfitInfo());
    }

    public List<TicketSetProfitInfo> selectSetsByProducer(String producer) {
        /*
         * 查询套餐出售数量
         * 执行结果：
         * 套餐内容 价格 拥有者 数量
         * */
        String sql = "select a.setInclude,a.price,a.ownerId,b.count from tbl_ticketset a right join (select ticketSet,count(ticketSet) count from tbl_user_purchase group by ticketSet)b on a.id=b.ticketSet where a.ownerId=?";
        return new SelectUtil().selectByOneParam(sql,producer,new TicketSetProfitInfo());
    }

    /*public List<PurchaseSeatInfo> selectRowsAndColsByMid(String movieId) {
        String sql = "select a.rows,a.cols from tbl_user_purchase a join (select id from tbl_schedule where movieId = ?)b on b.id=a.scheduleId";
        return new SelectUtil().selectByOneParam(sql,movieId,new PurchaseSeatInfo());
    }*/



    public List<PurchaseSeatInfo> selectRowsAndColsByScheduleId(String scheduleId) {
        String sql = "select a.rows,a.cols from tbl_user_purchase a join (select id from tbl_schedule where id = ?)b on b.id=a.scheduleId";
        return new SelectUtil().selectByOneParam(sql,scheduleId,new PurchaseSeatInfo());
    }
}
