package workbench.web.Controller;

import Settings.Domain.User;
import Utils.DateTimeUtil;
import Utils.UUIDUtil;
import Utils.WebUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import vo.ScheduleInfo;
import workbench.Domain.*;
import workbench.Service.ProducerIntService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ProducerIntController extends HttpServlet {
    ProducerIntService service = new ProducerIntService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if("/CBMS/producer/detail.do".equals(uri)){
            this.showDetail(req,resp);
        }else if("/CBMS/producer/editDetail.do".equals(uri)){
            this.showEditDetail(req,resp);
        }else if("/CBMS/producer/loadUserPurchases.do".equals(uri)){
            this.loadUserPurchases(req,resp);
        } else if("/CBMS/producer/checkProfit.do".equals(uri)){
            this.loadProfit(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if("/CBMS/producer/addMovie.do".equals(uri)){
            this.addMovie(req,resp);
        }else if (uri.equals("/CBMS/producer/myMovie.do")){
            this.showMyMovies(req,resp);
        }else if (uri.equals("/CBMS/producer/loadScheduleDetail.do")){
            this.loadScheduleDetail(req,resp);
        }else if(uri.equals("/CBMS/producer/addSchedule.do")){
            this.addSchedule(req,resp);
        }else if(uri.equals("/CBMS/producer/addSet.do")){
            this.addSet(req,resp);
        }else if(uri.equals("/CBMS/producer/editMovie.do")){
            this.editMovie(req,resp);
        }

    }

    private void editMovie(HttpServletRequest req, HttpServletResponse resp) {
        Movie movie = new Movie();
        WebUtil.ParaToObject(req,movie);
        boolean result=service.updateMovie(movie);
        try {
            resp.getWriter().write(result+"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addSet(HttpServletRequest req, HttpServletResponse resp) {
        TicketSet ticketSet = new TicketSet();
        WebUtil.ParaToObject(req,ticketSet);
        ticketSet.setId(UUIDUtil.getUUID());
        boolean result=service.addSet(ticketSet);
        try {
            resp.getWriter().write(result+"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadProfit(HttpServletRequest req, HttpServletResponse resp) {
        /*
        * 1.盈利金额:totalProfit--String
        * 2.卖出电影数与套餐数:totalMovieSale--String,totalSetSale--String
        * 3.电影与套餐卖出的详细份数:movieDetail--List<MovieProfitInfo>,ticketSetDetail--List<TicketSetProfitInfo>
        * */
        String usercode = req.getParameter("id");
        System.out.println(usercode);
        Map<String,Object> map=service.loadProfit(usercode);
        req.setAttribute("totalProfit",map.get("totalProfit"));
        req.setAttribute("totalMovieSale",map.get("totalMovieSale"));
        req.setAttribute("totalSetSale",map.get("totalSetSale"));
        ObjectMapper om = new ObjectMapper();
        String movieDetailStr = "";
        String ticketSetDetailStr = "";
        try {
            movieDetailStr=om.writeValueAsString(map.get("movieDetail"));
            ticketSetDetailStr=om.writeValueAsString(map.get("ticketSetDetail"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        req.setAttribute("movieDetail",movieDetailStr);
        req.setAttribute("ticketSetDetail",ticketSetDetailStr);
        try {
            req.getRequestDispatcher("/workbench/producer/profitDetail.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUserPurchases(HttpServletRequest req, HttpServletResponse resp) {
        String scheduleId = req.getParameter("scheduleId");
        List<Purchase> userPurchaseList = service.loadUserPurchases(scheduleId);
        WebUtil.toJsonAndWrite(resp,userPurchaseList);
    }

    private void addSchedule(HttpServletRequest req, HttpServletResponse resp) {
        Schedule schedule = new Schedule();
        WebUtil.ParaToObject(req,schedule);
        schedule.setId(UUIDUtil.getUUID());
        boolean flag=service.addSchedule(schedule);
        try {
            resp.getWriter().write(flag+"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadScheduleDetail(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("movieId");
        List<Hall> hallList=service.loadScheduleDetail();
        WebUtil.toJsonAndWrite(resp,hallList);
    }

    private void showEditDetail(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        List<ScheduleInfo> scheduleInfoList =service.ShowEditDetail(id);
        String result="";
        ObjectMapper om = new ObjectMapper();
        try {
            result=om.writeValueAsString(scheduleInfoList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        req.setAttribute("scheduleInfoList",result);
        req.setAttribute("movie",service.ShowMovieDetail(id));
        try {
            req.getRequestDispatcher("/workbench/producer/editDetail.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showDetail(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        Movie movie=service.ShowMovieDetail(id);
        req.setAttribute("movie",movie);
        try {
            req.getRequestDispatcher("/workbench/producer/detail.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showMyMovies(HttpServletRequest req, HttpServletResponse resp) {
        String producer = req.getParameter("producer");
        Condition condition = new Condition();
        condition.setProducer(producer);
        WebUtil.ParaToObject(req,condition);
        List<Movie> movieList=service.showMyMovie(condition);
        WebUtil.toJsonAndWrite(resp,movieList);
    }

    private void addMovie(HttpServletRequest req, HttpServletResponse resp) {
        Movie movie = new Movie();
        WebUtil.ParaToObject(req,movie);
        movie.setId(UUIDUtil.getUUID());
        User user = (User)req.getSession().getAttribute("user");
        movie.setProducer(user.getUsercode());
        movie.setCreateTime(DateTimeUtil.getSysTime());
        System.out.println(movie.getRank());
        boolean flag=service.addMovie(movie);
        try {
            resp.getWriter().write(flag+"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
