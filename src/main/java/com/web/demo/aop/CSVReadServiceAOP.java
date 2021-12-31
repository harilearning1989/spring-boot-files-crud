package com.web.demo.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CSVReadServiceAOP {

    //Expression AccessSpecifier ReturnType Package.ClassName.MethodName(..) Arguments
    /*@Pointcut("execution(* com.example.demo.service.CSVReadServiceImpl.*())")
    private void selectAll(){}*/

    @Pointcut("execution(public * com.web.demo.services.CSVReadServiceImpl.getIndiaStates())")
    private void selectAll(){}

    @Pointcut("execution(public * com.web.demo.services.CSVReadServiceImpl.readCropByMandal(..))")
    private void readCropByMandal(){}

    @Before("selectAll()")
    public void executeMethod(){
        System.out.println("Hello World AOP executeMethod selectAll");
    }
    @Around("readCropByMandal()")
    public Object readCropByMandalAOP(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Hello World AOP readCropByMandalAOP readCropByMandal");
        ObjectMapper mapper = new ObjectMapper();
        String methodeName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().toString();
        Object[] array = pjp.getArgs();
        System.out.println("Class Name==="+className+"===method Name==="+methodeName+"==="+
                mapper.writeValueAsString(array));
        Object obj = pjp.proceed();
        //System.out.println(mapper.writeValueAsString(obj));
        return obj;
    }
}
