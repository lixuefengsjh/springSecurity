package com.lxf.springsecurity.confige;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author: lxf
 * @create: 2020-09-11 17:40
 * @description:
 */
@Configuration
public class RestTemplateConfig {
    @Bean
   public  RestTemplate getRestTemplate(){
       return  new RestTemplate();
   }
}
