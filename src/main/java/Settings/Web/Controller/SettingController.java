package Settings.Web.Controller;

import Settings.Domain.User;
import Settings.Service.SettingService;
import Utils.WebUtil;
import vo.UserCommentInfo;
import vo.UserPurchaseInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SettingController extends HttpServlet {
    SettingService service=new SettingService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if("/CBMS/setting/loadBalance.do".equals(uri)){
            this.loadBalance(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if("/CBMS/setting/loadBookInfo.do".equals(uri)){
            this.loadBookInfo(req,resp);
        }else if("/CBMS/setting/loadMyComment.do".equals(uri)){
            this.loadMyComment(req,resp);
        }else if("/CBMS/setting/charge.do".equals(uri)){
            this.charge(req,resp);
        }

    }

    private void loadBalance(HttpServletRequest req, HttpServletResponse resp) {
        String usercode = req.getParameter("userId");
        String balance = service.loadBalance(usercode);
        try {
            resp.getWriter().write(balance);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void charge(HttpServletRequest req, HttpServletResponse resp) {
        String usercode = req.getParameter("userId");
        String postBalance = req.getParameter("postBalance");
        boolean flag = service.charge(usercode,postBalance);
        try {
            resp.getWriter().write(flag+"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMyComment(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        System.out.println(userId);
        List<UserCommentInfo> list=service.loadUserComments(userId);
        WebUtil.toJsonAndWrite(resp,list);
    }

    private void loadBookInfo(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        List<UserPurchaseInfo> list=service.loadBookInfo(userId);
        WebUtil.toJsonAndWrite(resp,list);
    }
}
