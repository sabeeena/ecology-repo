package kz.iitu.se242m.yesniyazova.aop;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(kz.iitu.se242m.yesniyazova.service..*)")
    public void serviceLayer() {}

    @Before("serviceLayer()")
    public void beforeService(JoinPoint joinPoint) {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            HttpServletRequest request = attrs.getRequest();
            String ip = request.getRemoteAddr();
            String uri = request.getRequestURI();
            log.info("[AOP] Request from IP: {}, URI: {}", ip, uri);
        }

        log.info("[AOP] Entering method: {} with args: {}",
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @After("serviceLayer()")
    public void afterService(JoinPoint joinPoint) {
        log.info("[AOP] Finished method: {}", joinPoint.getSignature().getName());
    }

    @Around("serviceLayer()")
    public Object aroundService(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.nanoTime();
        try {
            return pjp.proceed();
        } finally {
            long duration = (System.nanoTime() - start) / 1_000_000;
            log.info("[AOP] {} executed in {} ms", pjp.getSignature(), duration);
        }
    }

    @AfterThrowing(pointcut = "serviceLayer()", throwing = "ex")
    public void logExceptions(JoinPoint joinPoint, Throwable ex) {
        log.error("[AOP] Exception in method {}: {}",
                joinPoint.getSignature().getName(), ex.getMessage(), ex);
    }

}
