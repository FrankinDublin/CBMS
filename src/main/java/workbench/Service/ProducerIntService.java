package workbench.Service;

import vo.MovieProfitInfo;
import vo.ScheduleInfo;
import vo.TicketSetProfitInfo;
import workbench.Dao.*;
import workbench.Domain.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProducerIntService {
    MovieDao movieDao = new MovieDao();
    ScheduleDao scheduleDao = new ScheduleDao();
    HallDao hallDao = new HallDao();
    PurchaseDao purchaseDao = new PurchaseDao();
    TicketSetDao ticketSetDao = new TicketSetDao();
    public boolean addMovie(Movie movie) {
        return movieDao.insertMovie(movie)==1;
    }

    public List<Movie> showMyMovie(Condition condition) {
        int count=0;
        if (condition.getName()!="")
            count+=1;
        if (condition.getProducer()!="")
            count+=1;
        if (condition.getShowTime()!="")
            count+=1;
        if (condition.getMovieType()!="")
            count+=1;
        return movieDao.selectAllMovies(condition,count,0);
    }

    public Movie ShowMovieDetail(String id) { return movieDao.selectMovieById(id); }

    public List<ScheduleInfo> ShowEditDetail(String Mid) {
        return scheduleDao.selectScheduleInfoByMid(Mid);
    }

    public boolean addSchedule(Schedule schedule) {
        return scheduleDao.insertSchedule(schedule)==1;
    }

    public List<Hall> loadScheduleDetail() {
         return hallDao.selectAllHalls();
    }

    public List<Purchase> loadUserPurchases(String scheduleId) {
        return scheduleDao.selectUserPurchasesByScheduleId(scheduleId);
    }

    public Map<String, Object> loadProfit(String producer) {
        /*
         *查询各电影出售数量
         * 执行结果：
         * 电影名 厂商名 票价 数量
         * */
        List<MovieProfitInfo> movieProfitInfoList=purchaseDao.selectCountGroupBySchedule(producer);
        System.out.println("电影明细："+movieProfitInfoList);
        /*
        * 查询套餐出售数量
        * 执行结果：
        * 套餐内容 价格 拥有者 数量
        * */
        List<TicketSetProfitInfo> ticketSetProfitInfoList = purchaseDao.selectSetsByProducer(producer);
        System.out.println("套餐明细："+ticketSetProfitInfoList);
        int totalProfit = 0;
        int totalMovieSale = 0;
        int totalSetSale = 0;
        for(MovieProfitInfo m:movieProfitInfoList){
            /*总销量+=当前电影销量*/
            totalMovieSale+=Integer.parseInt(m.getCount());
            /*总利润+=当前电影票价*当前电影销量*/
            totalProfit+=Integer.parseInt(m.getTicketPrice())*Integer.parseInt(m.getCount());
        }
        for(TicketSetProfitInfo t:ticketSetProfitInfoList){
            /*总销量+=当前套餐销量*/
            totalSetSale+=Integer.parseInt(t.getCount());
            /*总利润+=当前套餐价格*当前套餐销量*/
            totalProfit+=Integer.parseInt(t.getPrice())*Integer.parseInt(t.getCount());
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("totalProfit",totalProfit);
        resultMap.put("totalMovieSale",totalMovieSale);
        resultMap.put("totalSetSale",totalSetSale);
        resultMap.put("movieDetail",movieProfitInfoList);
        resultMap.put("ticketSetDetail",ticketSetProfitInfoList);
        return resultMap;
    }

    public boolean addSet(TicketSet ticketSet) {
        return ticketSetDao.insertTicketSet(ticketSet)==1;
    }

    public boolean updateMovie(Movie movie) { return movieDao.updateMovie(movie)==1; }
}
