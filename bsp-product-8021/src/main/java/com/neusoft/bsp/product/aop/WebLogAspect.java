package com.neusoft.bsp.product.aop;

import com.google.gson.Gson;
import com.neusoft.bsp.common.base.R;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@Order(1)
@Slf4j
public class WebLogAspect {
    private Gson gson = new Gson();
    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.neusoft.bsp.product.controller..*(..))")
    public void controllerAspect() {}

    //请求接口前打印内容
    @Before(value = "controllerAspect()")
    public void methodBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //打印请求内容
        log.info("===============Request===============");
        log.info("IP: " + request.getRemoteAddr());
        log.info("Url: " + request.getRequestURL().toString());
        log.info("Method: " + request.getMethod());
        log.info("Class_Method: " + joinPoint.getSignature());
        log.info("Args: " + Arrays.toString(joinPoint.getArgs()));
        log.info("===============Request===============");
    }

    //接口执行完后打印返回内容
    @AfterReturning(returning = "o", pointcut = "controllerAspect()")
    public void methodAfterReturning(R o) {
        log.info("===============Response===============");
        log.info("Response Content: " + gson.toJson(o));
//        log.info("Response Content: " + o.getStatus() + ", " + o.getMsg());
        log.info("Cost Time: " + (System.currentTimeMillis() - startTime.get()));
        log.info("===============Response===============");
    }
}
