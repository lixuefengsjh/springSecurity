package com.lxf.springsecurity.activitiListner;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: lxf
 * @create: 2020-10-15 10:22
 * @description: ces
 */

public class TestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("进入前工作---------");
        HttpServletRequest req= (HttpServletRequest) servletRequest;
        HttpServletResponse resp= (HttpServletResponse) servletResponse;
        String url=req.getRequestURI();
        if(url.equals("/adminB")){
            System.out.println("正在处理------------------");
            filterChain.doFilter(req,resp);
        }
        System.out.println("离开后得工作------------------");
    }

    @Override
    public void destroy() {

    }
}
