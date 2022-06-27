package com.joe.testdb.constants;

import org.springframework.http.HttpStatus;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.text.html.HTML;

public enum BootError {
    SUCCESS(HttpStatus.OK, 200, "success", false),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "非法请求"),
    INTERNAL_SERVER(HttpStatus.INTERNAL_SERVER_ERROR, 500, "服务器内部错误"),
    INTERNAL_SERVER_MESSAGE(HttpStatus.INTERNAL_SERVER_ERROR, 500, "服务器内部错误: [%s]"),
    INVALID_PARAM(HttpStatus.BAD_REQUEST, 10001, "参数[%s]%s错误"),
    NOT_FOUND_URL(HttpStatus.NOT_FOUND, 10002, "找不到URL:[%s]"),
    METHOD_NOT_ALLOWED(HttpStatus.BAD_REQUEST, 10003, "请求方法错误: URI[%s]"),
    PKG_FORMAT(HttpStatus.BAD_REQUEST, 10004, "请求报文json格式错误"),
    UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, 9999, "未知错误"),

    DB_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 10005, "数据库错误：table[%s],method[%s],params[s%]"),
    DATA_EXIST(HttpStatus.BAD_REQUEST,10006,"数据重复错误"),

    GENERAL_ERROR(HttpStatus.BAD_REQUEST,10100,"%s");


    //213xxxxx
    private static final int BIZ_ID = 100000;

    private int status;
    private int code;
    private String message;

    public static BootError valueOf(int status) {
        switch (status) {
            case 400:
            case 401:
            case 403:
                return BAD_REQUEST;
            case 500:
            case 502:
            case 503:
            case 504:
            default:
                return INTERNAL_SERVER;
        }
    }

    BootError(HttpStatus status, int code, String msg) {
        this(status, code, msg, true);
    }

    BootError(HttpStatus status, int code, String msg, boolean withPrefix) {
        this.code = code;
        this.status = status.value();
        this.message = msg;
        if (withPrefix) {
            this.code += BIZ_ID;
        }
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    public int status() {
        return status;
    }

}
