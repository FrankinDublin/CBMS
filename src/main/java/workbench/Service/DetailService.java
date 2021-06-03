package workbench.Service;

import workbench.Dao.*;
import workbench.Domain.Hall;
import vo.PurchaseSeatInfo;
import workbench.Domain.Schedule;
import workbench.Domain.TicketSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailService {
    private HallDao hallDao=new HallDao();
    private ScheduleDao scheduleDao=new ScheduleDao();
    private TicketSetDao ticketSetDao=new TicketSetDao();
    private PurchaseDao purchaseDao = new PurchaseDao();
    private MovieDao movieDao = new MovieDao();

    public Map<String,List> getDetails(String Mid,String OwnerId){
        List<Hall> hallList = hallDao.selectAllHalls();
        List<Schedule> scheduleList=scheduleDao.selectSchedulesByMid(Mid);
        List<TicketSet> ticketSetList=ticketSetDao.selectTicketSetByOwnership(OwnerId);
        Map<String,List> map = new HashMap<>();
        map.put("hallList",hallList);
        map.put(("ticketSetList"),ticketSetList);
        map.put("scheduleList",scheduleList);
        return map;
    }

    /*public List<PurchaseSeatInfo> loadOccupiedSeat(String movieId) {
        return purchaseDao.selectRowsAndColsByMid(movieId);
    }*/


    public List<PurchaseSeatInfo> loadOccupiedSeat(String scheduleId) {
        return purchaseDao.selectRowsAndColsByScheduleId(scheduleId);
    }

    public boolean updateGrade(String id, String grade) {
        return movieDao.updateMovieGrade(id,grade)==1;
    }
}
