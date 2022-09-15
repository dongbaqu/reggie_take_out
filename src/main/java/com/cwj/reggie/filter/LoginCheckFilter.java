package com.cwj.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.cwj.reggie.common.BaseContext;
import com.cwj.reggie.common.Constants;
import com.cwj.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cwj
 * @version 1.0.0
 * @date 2022/9/13 20:12
 * @description:检查用户是否登录
 */

@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //1.获取匹配器，支持通配符
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        log.info("拦截到请求：{}",request.getRequestURI());
        //1.获取本次请求的URI
        String requestURI = request.getRequestURI();

        log.info("拦截到请求requestURI：{}",requestURI);

        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };
        //2.判断本次请求是否需要处理
        boolean check = check(urls, requestURI);

        //3.如果不需要处理，则直接放行
        if (check){
            log.info("本次请求{}不需要处理",requestURI);
            filterChain.doFilter(request,response);
            return;
        }
        //4.判断登录状态。如果已登录，则直接放行
        if (request.getSession().getAttribute(Constants.EMPLOYEE_SESSION) != null){
            log.info("用户id:{}",request.getSession().getAttribute(Constants.EMPLOYEE_SESSION));

            long id = Thread.currentThread().getId();
            log.info("线程id为： "+ id);
            //将用户id存储
            BaseContext.setCurrentId((Long) request.getSession().getAttribute(Constants.EMPLOYEE_SESSION));

            filterChain.doFilter(request,response);
            return;
        }
        //5.如果未登录返回未登录结果，通过输出流方式向客户端页面相应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        log.info("用户没有登录");
        return;
    }


    public boolean check(String[] urls,String requestURl){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURl);
            if (match){
                return true;
            }
        }
        return false;
    }
}
