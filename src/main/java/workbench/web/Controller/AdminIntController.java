package workbench.web.Controller;

import Settings.Domain.User;
import Utils.WebUtil;
import workbench.Domain.Movie;
import workbench.Domain.TicketSet;
import workbench.Service.AdminIntService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AdminIntController extends HttpServlet {
    AdminIntService service = new AdminIntService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if("/CBMS/admin/detail.do".equals(uri)){
            this.showDetail(req,resp);
        }else if("/CBMS/admin/loadUsers.do".equals(uri)){
            this.loadUsers(req,resp);
        }else if("/CBMS/admin/loadSets.do".equals(uri)){
            this.loadSets(req,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if("/CBMS/admin/deleteComment.do".equals(uri)){
            this.deleteComment(req,resp);
        }else if("/CBMS/admin/deleteMovie.do".equals(uri)){
            this.deleteMovie(req,resp);
        }else if("/CBMS/admin/getUser.do".equals(uri)){
            this.getUser(req,resp);
        }else if("/CBMS/admin/getSet.do".equals(uri)){
            this.getSet(req,resp);
        }else if("/CBMS/admin/deleteUser.do".equals(uri)){
            this.deleteUser(req,resp);
        }else if("/CBMS/admin/deleteSet.do".equals(uri)){
            this.deleteSet(req,resp);
        }else if("/CBMS/admin/updateUser.do".equals(uri)){
            this.updateUser(req,resp);
        }else if("/CBMS/admin/updateSet.do".equals(uri)){
            this.updateSet(req,resp);
        }else if("/CBMS/admin/updateComment.do".equals(uri)){
            this.updateComment(req,resp);
        }else if("/CBMS/admin/updateMovie.do".equals(uri)){
            this.updateMovie(req,resp);
        }

    }

    private void updateMovie(HttpServletRequest req, HttpServletResponse resp) {
        Movie movie = new Movie();
        WebUtil.ParaToObject(req,movie);
        boolean result=service.updateMovie(movie);
        try {
            resp.getWriter().write(result+"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateComment(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        String comment = req.getParameter("comment");
        boolean flag = service.updateComment(id,comment);
        try {
            resp.getWriter().write(flag+"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateSet(HttpServletRequest req, HttpServletResponse resp) {
        String setInclude = req.getParameter("setInclude");
        String setPrice = req.getParameter("setPrice");
        String setId = req.getParameter("setId");
        boolean flag = service.updateSet(setId,setInclude,setPrice);
        try {
            resp.getWriter().write(flag+"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp) {
        String userCode = req.getParameter("userCode");
        String userName = req.getParameter("userName");
        boolean flag = service.updateUser(userCode,userName);
        try {
            resp.getWriter().write(flag+"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteSet(HttpServletRequest req, HttpServletResponse resp) {
        String[] ids = req.getParameterValues("id");
        boolean flag=service.deleteSet(ids);
        try {
            resp.getWriter().write(flag+"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) {
        String[] userCodes = req.getParameterValues("usercode");
        boolean flag=service.deleteUser(userCodes);
        try {
            resp.getWriter().write(flag+"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getSet(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        List<TicketSet> ticketSetList = service.selectSetById(id);
        TicketSet ticketSet = ticketSetList.get(0);
        WebUtil.toJsonAndWrite(resp,ticketSet);
    }

    private void getUser(HttpServletRequest req, HttpServletResponse resp) {
        String usercode = req.getParameter("id");
        List<User> userList = service.selectUserById(usercode);
        User user = userList.get(0);
        WebUtil.toJsonAndWrite(resp,user);
    }

    private void loadSets(HttpServletRequest req, HttpServletResponse resp) {
        List<TicketSet> ticketSetList = service.selectAllSets();
        WebUtil.toJsonAndWrite(resp,ticketSetList);
    }

    private void loadUsers(HttpServletRequest req, HttpServletResponse resp) {
        List<User> userList = service.selectAllUsers();
        WebUtil.toJsonAndWrite(resp,userList);
    }

    private void deleteMovie(HttpServletRequest req, HttpServletResponse resp) {
        String movieId = req.getParameter("movieId");
        boolean flag=service.deleteMovie(movieId);
        try {
            resp.getWriter().write(flag+"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteComment(HttpServletRequest req, HttpServletResponse resp) {
        String commentId = req.getParameter("commentId");
        boolean flag=service.deleteComment(commentId);
        try {
            resp.getWriter().write(flag+"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showDetail(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        Movie movie=service.ShowMovieDetail(id);
        req.setAttribute("movie",movie);
        try {
            req.getRequestDispatcher("/workbench/Admin/detail.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
