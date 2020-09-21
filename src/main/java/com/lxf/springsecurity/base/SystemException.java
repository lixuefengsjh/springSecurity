package com.lxf.springsecurity.base;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: lxf
 * @create: 2020-09-10 14:09
 * @description: 系统内部自定义异常
 */
@Getter
public class SystemException extends  RuntimeException {

    private String msg;

    private int responseCode;

    public SystemException( String msg, int responseCode) {
        super(msg);
        this.msg = msg;
        this.responseCode = responseCode;
    }
}
