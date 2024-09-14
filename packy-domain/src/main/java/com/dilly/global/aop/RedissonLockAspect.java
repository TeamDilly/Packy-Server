package com.dilly.global.aop;

import com.dilly.exception.ConcurrencyFailedException;
import com.dilly.global.util.CustomSpringELParser;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
@ConditionalOnExpression("${ableRedissonLock:true}")
public class RedissonLockAspect {

    @Value("${spring.profiles.active}")
    private String profilePrefix;

    private final RedissonClient redissonClient;
    private final AopForTransaction aopForTransaction;

    @Around("@annotation(com.dilly.global.aop.RedissonLock)")
    public Object redissonLock(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedissonLock annotation = method.getAnnotation(RedissonLock.class);

        String lockKey =
            profilePrefix + ":" + method.getName() + CustomSpringELParser.getDynamicValue(
            signature.getParameterNames(), joinPoint.getArgs(), annotation.value());

        RLock lock = redissonClient.getLock(lockKey);

        try {
            boolean lockable = lock.tryLock(annotation.waitTime(), annotation.leaseTime(),
                annotation.timeUnit());
            if (!lockable) {
                log.info("Lock 획득 실패: {}", lockKey);
                throw new ConcurrencyFailedException();
            }

            log.info("Lock 획득 성공: {}", lockKey);
            return aopForTransaction.proceed(joinPoint);
        } catch (InterruptedException e) {
            log.info("에러 발생");
            throw e;
        } finally {
            if (lock != null && lock.isHeldByCurrentThread()) {
                log.info("Lock 해제");
                lock.unlock();
            }
        }
    }
}
