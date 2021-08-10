package dev.wre.api.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LoggingAspect {

    private Logger logger = LogManager.getLogger(LoggingAspect.class);

    @After("within(dev.wre.api.controllers.*)")
    public void logMethod(JoinPoint jp){
        logger.info(jp.getSignature().getName() + " invoked");
    }

    @After("within(dev.wre.api.controllers.*)")
    public void logMethodException(){
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        logger.info(request.getMethod() + " request made to "+ request.getRequestURI());
    }


}
