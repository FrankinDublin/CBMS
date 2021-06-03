package workbench.web.Controller;

import Utils.WebUtil;
import vo.PurchaseSeatInfo;
import workbench.Service.DetailService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DetailController extends HttpServlet {
    DetailService service=new DetailService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if(uri.equals("/CBMS/detail/loadOccupiedSeat.do")){
            this.loadOccupiedSeat(req,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if(uri.equals("/CBMS/detail/loadDetail.do")){
            this.loadDetail(req,resp);
        }else if(uri.equals("/CBMS/detail/updateGrade.do")){
            this.updateGrade(req,resp);
        }
    }

    private void updateGrade(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("movieId");
        String grade = req.getParameter("grade");
        service.updateGrade(id,grade);
    }

    private void loadOccupiedSeat(HttpServletRequest req, HttpServletResponse resp) {
        String scheduleId = req.getParameter("scheduleId");
        /*List<PurchaseSeatInfo> resultList=service.loadOccupiedSeat(movieId);
        WebUtil.toJsonAndWrite(resp,resultList);*/
        List<PurchaseSeatInfo> resultList=service.loadOccupiedSeat(scheduleId);
        WebUtil.toJsonAndWrite(resp,resultList);
    }

    private void loadDetail(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, List> map=service.getDetails(req.getParameter("Mid"),req.getParameter("ProducerId"));
        /*System.out.println(map);*/
        WebUtil.toJsonAndWrite(resp,map);
    }
    }
