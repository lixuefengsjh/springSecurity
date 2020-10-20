package com.lxf.springsecurity.security.sms;

import cn.hutool.core.util.StrUtil;
import com.lxf.springsecurity.base.SystemException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PicValidFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws ServletException, IOException {
        String uri=req.getRequestURI();
        if(StrUtil.equals(uri,"/login")){
            //获取会话
            HttpSession session = req.getSession();
            String code= String.valueOf(session.getAttribute("pic"));
            //获取到验证码后立马清楚
            session.setAttribute("pic",null);
            String inputCode=req.getParameter("code");
            if(StrUtil.isEmpty(inputCode)){
                throw new SystemException("请输入验证码",9999);
            }else if(StringUtils.isEmpty(code)){
                throw new SystemException("为获取到对应的验证码，或者验证码已过期",9999);
            }else if(!code.equals(inputCode)){
                throw new SystemException("用户输入的验证码不匹配",9999);
            }
        };
        filterChain.doFilter(req,resp);
    }
}
