package com.lxf.springsecurity.controller;

import cn.hutool.core.lang.UUID;
import com.lxf.springsecurity.base.SystemResponse;
import com.lxf.springsecurity.util.GraphicHelperUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author: lxf
 * @create: 2020-09-21 16:03
 * @description: 用户相关操作
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户操作管理")
public class UserController {
    Logger logger= LoggerFactory.getLogger(UserController.class);
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
    @GetMapping("/getPicValid")
    @ApiOperation(value = "获取验证码图片基本handler", notes = "获取验证码图片基本handler")
    public void getPicValid(HttpServletRequest req,HttpServletResponse resp){
        //session保持会话技术
        HttpSession session = req.getSession();
        final int width = 100; // 图片宽度
        final int height = 40; // 图片高度
        final String imgType = "jpeg"; // 指定图片格式 (不是指MIME类型)
        try {
            final OutputStream output = resp.getOutputStream();
            String code = GraphicHelperUtil.create(width, height, imgType, output);
            logger.info("------生成的图片验证码为："+code);
            session.setAttribute("pic",code);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param resp
     * @throws IOException
     */
    @GetMapping("/toQQLogin")
    @ApiOperation(value = "将登录界面导向到扣扣进行登录", notes = "将登录界面导向到扣扣进行登录")
    public void toQQLogin(HttpServletResponse resp) throws IOException {
        //Step1：获取Authorization Code
        String url="https://graph.qq.com/oauth2.0/authorize?response_type=code"+
        "&client_id=%s&redirect_uri=%s&state=%s";
        String  clientId="101875318";
        String  redirectUrl="http://127.0.0.1:8080/user/qq/check";
        String  state= UUID.fastUUID().toString().replaceAll("-","");
        url=String.format(url,clientId,redirectUrl,state);
        resp.sendRedirect(url);
    };
    /**
     *
     */
    @GetMapping("/qq/check")
    public SystemResponse useQQLogin(HttpServletRequest req,HttpServletResponse resp){
        String code=req.getParameter("code");
        return  SystemResponse.failed();
    };
}
