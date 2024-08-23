package com.dilly.global.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedissonLock {

    String value(); // 락의 이름
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS; // 시간 단위
    long waitTime() default 5_000L; // 락 획득을 위해 waitTime만큼 대기
    long leaseTime() default 5_000L; // 락 획득 후 leaseTime만큼 락 유지
}
