package com.joe.testdb.interceptor.aop.redisLimter;

/**
 * 限流类型
 */
public enum LimitType {
    /**
     * 默认类型：全局限流
     */
    DEFAULT,
    /**
     * 根据请求IP地址限流
     */
    IP;
}
