package com.example.unknown.aop;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 定义切点，匹配所有Controller层的方法
    @Pointcut("execution(* com.example.unknown.controller..*(..))")
    public void controllerMethods() {
    }

    // 在方法执行前打印请求参数
    @Before("controllerMethods()")
    public void logBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("【请求URL】: {}", request.getRequestURL());

        Object[] args = joinPoint.getArgs();
        List<Object> argList = Arrays.stream(args).map(arg -> {
            if (arg instanceof MultipartFile) {
                MultipartFile file = (MultipartFile) arg;
                return "MultipartFile: " + file.getOriginalFilename() + ", Size: " + file.getSize();
            } else {
                return arg;
            }
        }).collect(Collectors.toList());
        logger.info("【请求参数】: {}", CollectionUtil.isNotEmpty(argList) ? JSONObject.toJSONString(argList) : null);
    }

    // 在方法返回后打印返回结果
    @AfterReturning(pointcut = "controllerMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("【返回结果】: {}", Objects.nonNull(result) ? JSONObject.toJSONString(result) : null);
    }
}
