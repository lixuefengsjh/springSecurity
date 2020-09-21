package com.lxf.springsecurity.service;

import cn.hutool.http.server.HttpServerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

@Service("myRdbcSwevice")
public class MyRdbcSwevice {

    private AntPathMatcher antPathMatcher=new AntPathMatcher();
    public boolean havePression(Authentication authentication, HttpServerRequest req){
        String url=req.getURI().toString();
        Boolean flag=false;
        for(GrantedAuthority g: authentication.getAuthorities()){
            if(antPathMatcher.match(g.getAuthority(),url)){
                flag=true;
                break;
            }
        }

        return flag;
    }
}
