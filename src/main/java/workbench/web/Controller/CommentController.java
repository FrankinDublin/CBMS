package workbench.web.Controller;

import Utils.DateTimeUtil;
import Utils.UUIDUtil;
import Utils.WebUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import workbench.Domain.MovieComment;
import workbench.Service.CommentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CommentController extends HttpServlet {
    CommentService service = new CommentService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if(uri.equals("/CBMS/comment/loadComments.do")){
            this.loadComments(req,resp);
        }else if(uri.equals("/CBMS/comment/addComment.do")){
            this.addComment(req,resp);
        }
    }
    public void loadComments(HttpServletRequest req,HttpServletResponse resp){
        String Mid = req.getParameter("movieId");
        List<MovieComment> movieCommentList=service.loadComments(Mid);
        WebUtil.toJsonAndWrite(resp,movieCommentList);
    }
    public void addComment(HttpServletRequest req,HttpServletResponse resp){
        MovieComment movieComment=new MovieComment();
        WebUtil.ParaToObject(req,movieComment);
        movieComment.setCreateTime(DateTimeUtil.getSysTime());
        movieComment.setId(UUIDUtil.getUUID());
        movieComment.setEditFlag("0");
        service.addComment(movieComment);
    }
}
