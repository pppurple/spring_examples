package spring.aop.com.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class MethodCallLoggingAspect {
    @Before("execution(* *..*Service.*(..))")
    public void beforeLog(JoinPoint jp) {
        System.out.println("[Before]====================================");
        System.out.println("[Before]args:" + Arrays.toString(jp.getArgs()));
        System.out.println("[Before]signature:" + jp.getSignature());
    }

    @AfterReturning(value = "execution(* *..*Service.*(..))", returning = "randomValue")
    public void afterReturningLog(JoinPoint jp, int randomValue) {
        System.out.println("[AfterReturn]====================================");
        System.out.println("[AfterReturn]args:" + Arrays.toString(jp.getArgs()));
        System.out.println("[AfterReturn]signature:" + jp.getSignature());
        System.out.println("[AfterReturn]return:" + randomValue);
    }

    @AfterThrowing(value = "execution(* *..*Service.getLength*(..))", throwing = "e")
    public void afterThrowingLog(JoinPoint jp, NullPointerException e) {
        System.out.println("[AfterThrowing]====================================");
        System.out.println("[AfterThrowing]args:" + Arrays.toString(jp.getArgs()));
        System.out.println("[AfterThrowing]signature:" + jp.getSignature());
    }

    @After("execution(* *..*Service.*(..))")
    public void afterLog(JoinPoint jp) {
        System.out.println("[After]====================================");
        System.out.println("[After]args:" + Arrays.toString(jp.getArgs()));
        System.out.println("[After]signature:" + jp.getSignature());
    }

    @Around("execution(* *..*Service.getRandomValue*(..))")
    public Object aroundLog(ProceedingJoinPoint jp) {
        System.out.println("[Around]====================================");
        Object result = null;
        try {
            // 対象メソッド実行
            result = jp.proceed();
            System.out.println("[Around]args:" + Arrays.toString(jp.getArgs()));
            System.out.println("[Around]signature:" + jp.getSignature());
            System.out.println("[Around]return:" + result);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }
}
