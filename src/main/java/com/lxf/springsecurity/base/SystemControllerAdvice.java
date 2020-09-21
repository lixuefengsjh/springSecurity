package com.lxf.springsecurity.base;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: lxf
 * @create: 2020-09-10 17:04
 * @description: 全局的异常数据处理器
 */
@RestControllerAdvice
public class SystemControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public SystemResponse<Map<String,String>> methodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String,String> errorMap=new HashMap<>();
        for(FieldError error:e.getBindingResult().getFieldErrors()){
            errorMap.put(error.getField(),error.getDefaultMessage());
        }
        return  SystemResponse.failed(errorMap);
    }
}
