package com.hxb.common.exception;

/**
 * @author hexinbang@foxmail.com
 * @date 2019/10/16 16:55
 */
public class BusinessException extends RuntimeException {

    private String msg;

    public BusinessException(String msg){
        super(msg);
    }


}
