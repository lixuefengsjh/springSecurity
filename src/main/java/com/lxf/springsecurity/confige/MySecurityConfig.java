package com.lxf.springsecurity.confige;

import com.lxf.springsecurity.security.LoginAuthenticationSuccessHandler;
import com.lxf.springsecurity.security.sms.MyUserDetailsService;
import com.lxf.springsecurity.security.sms.SmsAuthenticationFilter;
import com.lxf.springsecurity.security.sms.SmsAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author: lxf
 * @create: 2020-09-21 10:42
 * @description: 安全相关的配置
 */
@Configuration
public class MySecurityConfig  extends WebSecurityConfigurerAdapter {
    @Bean
    PasswordEncoder getPasswordEncoder(){return new BCryptPasswordEncoder();};
    @Bean
     AuthenticationSuccessHandler getAuthenticationSuccessHandler(){
        return new LoginAuthenticationSuccessHandler();
    }
    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {

        SmsAuthenticationProvider smsAuthenticationProvider=new SmsAuthenticationProvider();
        smsAuthenticationProvider.setUserDetailsService(getUserDetailsService());
        builder.authenticationProvider(smsAuthenticationProvider);
        builder.userDetailsService(getUserDetailsService());
    }

    @Bean
    public UserDetailsService getUserDetailsService(){
        return new MyUserDetailsService();
    }
    @Override
    public void configure(HttpSecurity http ) throws Exception {
        http.
                formLogin()
                .loginProcessingUrl("/login")
                .successHandler(getAuthenticationSuccessHandler())
                .and()
            .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/login.html","/sms/login","/login").permitAll()
                .anyRequest().access("@myRdbcSwevice.havePression(authentication,request)");
        SmsAuthenticationFilter smsFilter=new SmsAuthenticationFilter();
        smsFilter.setAuthenticationManager(authenticationManager());
        smsFilter.setAuthenticationSuccessHandler(getAuthenticationSuccessHandler());
        http.addFilterBefore(smsFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
