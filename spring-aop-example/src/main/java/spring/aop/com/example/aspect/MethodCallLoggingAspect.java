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
import java.util.Objects;

@Aspect
@Component
public class MethodCallLoggingAspect {
    @Before("execution(* *..*Service.*(..))")
    public void beforeLog(JoinPoint jp) {
        System.out.println("====================================");
        System.out.println("Advice before log");
        System.out.println("args:" + Arrays.toString(jp.getArgs()));
        System.out.println("signature:" + jp.getSignature());
        System.out.println("kind:" + jp.getKind());
    }

    @AfterReturning("execution(* *..*Service.*(..))")
    public void afterReturningLog(JoinPoint jp) {
        System.out.println("====================================");
        System.out.println("Advice after returning log");
        System.out.println("args:" + Arrays.toString(jp.getArgs()));
        System.out.println("signature:" + jp.getSignature());
        System.out.println("kind:" + jp.getKind());
    }

    @AfterThrowing(value = "execution(* *..*Service.*(..))", throwing = "e")
    public void afterThrowingLog(JoinPoint jp, Exception e) {
        System.out.println("====================================");
        System.out.println("Advice after throwing log");
        System.out.println("args:" + Arrays.toString(jp.getArgs()));
        System.out.println("signature:" + jp.getSignature());
        System.out.println("kind:" + jp.getKind());
        e.printStackTrace();
    }

    @After("execution(* *..*Service.*(..))")
    public void afterLog(JoinPoint jp) {
        System.out.println("====================================");
        System.out.println("Advice after log");
        System.out.println("args:" + Arrays.toString(jp.getArgs()));
        System.out.println("signature:" + jp.getSignature());
        System.out.println("kind:" + jp.getKind());
    }

    @Around("execution(* *..*Service.*(..))")
    public Object aroundLog(ProceedingJoinPoint jp) {
        System.out.println("====================================");
        System.out.println("Advice around log");
        Object result = null;
        try {
            result = jp.proceed();
            System.out.println("args:" + Arrays.toString(jp.getArgs()));
            System.out.println("signature:" + jp.getSignature());
            System.out.println("return:" + result);
            System.out.println("kind:" + jp.getKind());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }
}
