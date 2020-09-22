package com.lxf.springsecurity.controller;

import com.lxf.springsecurity.base.SystemResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: lxf
 * @create: 2020-09-21 16:03
 * @description: 用户相关操作
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/adminA")
    public SystemResponse<String> adminA(){
        return  SystemResponse.ok("adminA-------aceess");
    }

    @GetMapping("/adminB")
    public SystemResponse<String> adminB(){
        return  SystemResponse.ok("adminB-------aceess");
    }
}
