package workbench.web.Controller;
import Utils.WebUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import workbench.Domain.Condition;
import workbench.Domain.Movie;
import workbench.Service.UserIntService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UserIntController extends HttpServlet {
    UserIntService service=new UserIntService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if(uri.equals("/CBMS/user/loadMovies.do")){
            this.loadMovies(req,resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if(uri.equals("/CBMS/user/detail.do")){
            this.showDetail(req,resp);
        }
    }

    public void showDetail(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        Movie movie=service.ShowMovieDetail(id);
        req.setAttribute("movie",movie);
        try {
            req.getRequestDispatcher("/workbench/user/detail.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMovies(HttpServletRequest req, HttpServletResponse resp){
        Condition condition = new Condition();
        WebUtil.ParaToObject(req,condition);
        String mark = req.getParameter("sortMark");
        int sortMark=0;
        if(mark!=null)
            sortMark = Integer.parseInt(req.getParameter("sortMark"));
        List<Movie> movieList=service.loadMovies(condition,sortMark);
        ObjectMapper om = new ObjectMapper();
        WebUtil.toJsonAndWrite(resp,movieList);
    }
}
