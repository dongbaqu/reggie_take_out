package com.cwj.reggie.common;

/**
 * @author cwj
 * @version 1.0.0
 * @date 2022/9/16 19:17
 * @description:自定义异常类
 */

public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
