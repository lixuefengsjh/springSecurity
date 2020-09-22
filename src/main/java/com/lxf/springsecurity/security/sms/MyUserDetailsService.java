package com.lxf.springsecurity.security.sms;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author: lxf
 * @create: 2020-09-21 11:36
 * @description:
 */

public class MyUserDetailsService implements UserDetailsService {
    private PasswordEncoder  passwordEncoder;
    //注入dao,通过数据库返回数据
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User u=new User("lxf",passwordEncoder.encode("lxf--test"), AuthorityUtils.createAuthorityList("/user/adminA","adminB"));
        return u;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
