package com.joe.testdb.interceptor.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {
    /**
     * 限流Key
     */
    String key()default "rate_limit:";

    /**
     * 限流时间，单位秒
     */
    int time()default 60;

    /**
     * 限流次数，默认100次
     */
    int count()default 100;

    /**
     * 限流类型，默认全局限流
     */
    LimitType limitType()default LimitType.DEFAULT;
}
