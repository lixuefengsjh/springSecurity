package com.lxf.springsecurity.controller;

import com.lxf.springsecurity.base.SystemResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: lxf
 * @create: 2020-09-21 16:03
 * @description: 用户相关操作
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户操作管理")
public class UserController {
    @GetMapping("/adminA/{id}")
    @ApiOperation(value = "测试重定向功能", notes = "测试重定向功能")
    @ApiImplicitParams(
            @ApiImplicitParam(name="id",value="id号",required=true)
           )
    public SystemResponse<String> adminA(@PathVariable("id") String id, HttpServletResponse response, HttpServletRequest req){
        //测试重定向功能   return "redirect:/user/adminB";
        if("a".equals(id)){
            try {

                req.getContextPath();
                System.out.println("---------测试重定向流程------:"+ req.getContextPath());
                response.sendRedirect("/user/adminB");
                System.out.println("---------测试重定向流程------");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return  SystemResponse.ok("adminA----重定向---aceess");
        }else {
            return  SystemResponse.ok("adminA-------aceess");
        }

    }

    @GetMapping("/adminB")
    @ApiOperation(value = "",notes = "")
    public SystemResponse<String> adminB(){

        return  SystemResponse.ok("adminB-------aceess");
    }
}
