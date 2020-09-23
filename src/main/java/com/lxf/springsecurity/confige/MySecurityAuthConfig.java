package com.lxf.springsecurity.confige;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @author: lxf
 * @create: 2020-09-23 17:41
 * @description: 授权服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class MySecurityAuthConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client-01")
                .autoApprove(false)
                .secret(passwordEncoder.encode("123456"))
                .authorizedGrantTypes("authorization_code")
                .redirectUris("https://www.baidu.com/");
    }
}
