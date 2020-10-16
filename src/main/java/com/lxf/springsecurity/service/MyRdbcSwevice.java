package com.lxf.springsecurity.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.server.HttpServerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;

@Service("myRdbcSwevice")
public class MyRdbcSwevice {
    private AntPathMatcher antPathMatcher=new AntPathMatcher();
    public Boolean havePression(Authentication authentication, HttpServletRequest request){
        String url=request.getRequestURI();
        request.getRemoteHost();
        String c=request.getContextPath();
        Boolean flag=false;
        for(GrantedAuthority g: authentication.getAuthorities()){
//            if(antPathMatcher.match(g.getAuthority(),url)){
//                flag=true;
//                break;
//            }
            if( checkUrl(g.getAuthority(),url)){
                flag=true;
                break;
            }
        }

        return flag;
    }
    private Boolean checkUrl(String patten,String url){
        String[] pattens=patten.split("/");
        String[] urls=url.split("/");
        if(urls.length>0){
            int i=urls[urls.length-1].indexOf("?")==-1?urls[urls.length-1].length():urls[urls.length-1].indexOf("?");
            urls[urls.length-1]= StrUtil.sub(  urls[urls.length-1],0,i);
        }
        Boolean flag=true;
        for(int i=0;i<pattens.length;i++){
            if(!pattens[i].equals(urls[i])){
                flag=false;
                break;
            }
        }
        return  flag;
    }
}
