package filter;


import Settings.Domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //登录拦截功能，确保该项目入口为登陆页面
        HttpServletRequest request =(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        String servletName = request.getServletPath();
        if("/login".equals(servletName)||"/index.jsp".equals(servletName)){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            HttpSession session=request.getSession();
            User user = (User) session.getAttribute("user");
            if(user!=null)
            {
                filterChain.doFilter(servletRequest, servletResponse);
            }
            else {
                //否则跳转至登陆页面
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            }
        }
    }

    @Override
    public void destroy() {

    }
}
