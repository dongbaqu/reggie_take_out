package com.cwj.reggie.common;

/**
 * @author cwj
 * @version 1.0.0
 * @date 2022/9/16 11:19
 * @description:
 */

/**
 * 基于ThreadLocal封装工具类，用户保存和获取当前登录用户id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }


    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
