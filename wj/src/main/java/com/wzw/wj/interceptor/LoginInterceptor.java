package com.wzw.wj.interceptor;

import com.wzw.wj.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @ClassName LoginInterceptor
 * @Description 登录拦截
 * @Author wzw
 * @Date 2020/5/25
 * @Version 1.0
 **/
public class LoginInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle (HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
    /*    HttpSession session = httpServletRequest.getSession();
        String contextPath = session.getServletContext().getContextPath();
        String[] requirePages=new String[]{"index"};
        String uri = httpServletRequest.getRequestURI();
        uri = StringUtils.remove(uri, contextPath+"/");
        String page=uri;
        if(beginwith(page,requirePages)){//请求路径一样，判断登录状态
            User user =(User) session.getAttribute("user");
            if(user==null){
                httpServletResponse.sendRedirect("login");
                PrintWriter writer = httpServletResponse.getWriter();
                writer.write("please login!");
                return false;
            }
        }*/
        //放行options请求，否则无法让前端带上自定义的header信息，导致sessionid改变，shiro验证失败
       /* if(HttpMethod.OPTIONS.toString().equals(httpServletRequest.getMethod())){
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            return true;
        }
        Subject subject = SecurityUtils.getSubject();
        //使用shiro验证
        if(!subject.isAuthenticated()&&!subject.isRemembered()){
            return false;
        }*/
        return true;
    }
    public boolean beginwith(String page,String[] requirePages){
        boolean result=false;
        for(String requirePage:requirePages){
            if(StringUtils.startsWith(page,requirePage)){
                result= true;
                break;
            }
        }
        return result;
    }
}
