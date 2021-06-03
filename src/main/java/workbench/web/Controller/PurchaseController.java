package workbench.web.Controller;

import Utils.DateTimeUtil;
import Utils.UUIDUtil;
import Utils.WebUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import workbench.Domain.Purchase;
import workbench.Service.PurchaseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PurchaseController extends HttpServlet {
    PurchaseService service=new PurchaseService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if(uri.equals("/CBMS/purchase/purchase.do")){
            this.purchase(req,resp);
        }else if(uri.equals("/CBMS/purchase/updateBalance.do")){
            this.updateBalance(req,resp);
        }

    }

    private void updateBalance(HttpServletRequest req, HttpServletResponse resp) {
        String usercode = req.getParameter("userId");
        String postBalance = req.getParameter("postBalance");
        Boolean flag = service.updateBalance(usercode,postBalance);
        try {
            resp.getWriter().write(flag+"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void purchase(HttpServletRequest req, HttpServletResponse resp) {
        Purchase purchase = new Purchase();
        WebUtil.ParaToObject(req,purchase);
        purchase.setId(UUIDUtil.getUUID());
        purchase.setDealTime(DateTimeUtil.getSysTime());
        boolean result=service.purchase(purchase);
        try {
            PrintWriter pw = resp.getWriter();
            pw.write(result+"");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
