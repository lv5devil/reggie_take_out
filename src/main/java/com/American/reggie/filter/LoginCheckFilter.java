package com.American.reggie.filter;


import com.American.reggie.common.BaseContext;
import com.American.reggie.common.R;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Locale;

@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    private static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
      //  log.info("拦截到请求{}",request.getRequestURI());

        String uri = request.getRequestURI();
        String[] urls=new String[]{

                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/login"
        };
        if(check(uri,urls)){
            log.info("本次请求不需要拦截{}",uri);
            filterChain.doFilter(request,response);
            return;
        }
        if(request.getSession().getAttribute("employee")!=null){
            log.info("用户已经登录，ID为:{}",request.getSession().getAttribute("employee"));
            long empId = (long) request.getSession().getAttribute("employee");
            BaseContext.setThreadLocal(empId);

            filterChain.doFilter(request,response);
            return;
        }
        if(request.getSession().getAttribute("user")!=null){
            log.info("用户已经登录，ID为:{}",request.getSession().getAttribute("user"));
            long userId = (long) request.getSession().getAttribute("user");
            BaseContext.setThreadLocal(userId);

            filterChain.doFilter(request,response);
            return;
        }
        request.getSession().removeAttribute("employee");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        log.info("已经拦截{}",uri);
        log.info("用户未登录");
        return;
    }
    private boolean check(String uri,String[] urls){
        for (String url : urls) {
            if(PATH_MATCHER.match(url,uri)){
                return true;
            }
        }
        return false;
    }
}
