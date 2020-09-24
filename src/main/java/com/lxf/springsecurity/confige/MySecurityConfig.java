package com.lxf.springsecurity.confige;

import com.lxf.springsecurity.security.LoginAuthenticationSuccessHandler;
import com.lxf.springsecurity.security.sms.MyUserDetailsService;
import com.lxf.springsecurity.security.sms.SmsAuthenticationFilter;
import com.lxf.springsecurity.security.sms.SmsAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;

import javax.sql.DataSource;

/**
 * @author: lxf
 * @create: 2020-09-21 10:42
 * @description: 安全相关的配置
 */
@Configuration
@EnableWebSecurity
@Order(1)
public class MySecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource datasource;
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
        MyUserDetailsService userDetailsService= new MyUserDetailsService();
        userDetailsService.setPasswordEncoder(getPasswordEncoder());
        return userDetailsService;
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
                    .antMatchers("/login.html","/sms/login","/login","/oauth/**").permitAll()
                .anyRequest().access("@myRdbcSwevice.havePression(authentication,request)")
                .and()
                .rememberMe().userDetailsService(getUserDetailsService()).tokenRepository(getTokenRepository())
                .and()
                .logout()
                .and()
                .sessionManagement()
                .invalidSessionUrl("/login.html")
                .maximumSessions(1)
                .expiredSessionStrategy(new SimpleRedirectSessionInformationExpiredStrategy("/login.html"))
;
        SmsAuthenticationFilter smsFilter=new SmsAuthenticationFilter();
        smsFilter.setAuthenticationManager(authenticationManager());
        smsFilter.setAuthenticationSuccessHandler(getAuthenticationSuccessHandler());
        http.addFilterBefore(smsFilter, UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    public PersistentTokenRepository getTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl =new JdbcTokenRepositoryImpl();
       // jdbcTokenRepositoryImpl.setCreateTableOnStartup(true);
        jdbcTokenRepositoryImpl.setDataSource(datasource);
        return jdbcTokenRepositoryImpl;
    }
}
