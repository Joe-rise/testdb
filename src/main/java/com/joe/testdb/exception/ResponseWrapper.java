package com.joe.testdb.exception;

import lombok.Builder;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Builder
public class ResponseWrapper<T> {
    private int code;
    private String msg;
    private T data;



    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }


    public String toString() {
        return "ResponseWrapper(code=" + this.getCode() + ", msg=" + this.getMsg() + ", data=" + this.getData() + ")";
    }

    public ResponseWrapper() {
    }

    public ResponseWrapper(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResponseWrapper fail(int code,String message){
       return ResponseWrapper.builder().code(code).msg(message).build();
    }

}

