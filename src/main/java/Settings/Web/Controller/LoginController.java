package Settings.Web.Controller;

import Settings.Domain.User;
import Settings.Service.LoginService;
import Utils.WebUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class LoginController extends HttpServlet {
    private LoginService service  = new LoginService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if("/CBMS/login/login.do".equals(uri)){
            this.Login(req,resp);
        }else if("/CBMS/login/register.do".equals(uri)){
            this.Register(req,resp);
        }
    }
    public void Login(HttpServletRequest req, HttpServletResponse resp){
        User params = new User();
        WebUtil.ParaToObject(req,params);
        Map<String,String> map = new HashMap<>();
        map.put("success",null);
        map.put("msg",null);
        String result="";
        try {
            params=service.login(params);
            map.put("success","true");
            map.put("role", params.getRole());
            req.getSession().setAttribute("user",params);
        } catch (Exception e) {
            //e.printStackTrace();
            map.put("msg",e.getMessage());
            map.put("success","false");
        }
        ObjectMapper om = new ObjectMapper();
        try {
            result=om.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            PrintWriter pw =resp.getWriter();
            pw.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Register(HttpServletRequest req,HttpServletResponse resp){
        User params=new User();
        WebUtil.ParaToObject(req,params);
        Map<String,String> map = new HashMap<>();
        map.put("success",null);
        map.put("msg",null);
        String result="";
        try {
            service.register(params);
            map.put("success","true");
        } catch (Exception e) {
           // e.printStackTrace();
            map.put("msg",e.getMessage());
            map.put("success","false");
        }
        ObjectMapper om = new ObjectMapper();
        try {
            result=om.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            PrintWriter pw =resp.getWriter();
            pw.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
