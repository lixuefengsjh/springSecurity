package com.lxf.springsecurity.security.sms;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author: lxf
 * @create: 2020-09-21 10:56
 * @description: 短信验证相关的token
 */
public class SmsAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 530L;
    private final String phone;


    public SmsAuthenticationToken(String phone) {
        super((Collection)null);
        this.phone = phone;
        this.setAuthenticated(false);
    }

    public SmsAuthenticationToken(String phone,  Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.phone = phone;
        super.setAuthenticated(true);
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
