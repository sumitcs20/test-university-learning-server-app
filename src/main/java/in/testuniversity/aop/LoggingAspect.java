package in.testuniversity.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	
    private static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);    
    
   /*
    * @Pointcut to capture all methods within controller package
    */
    @Pointcut("execution(*in.testuniversity.controller..*(..))")
    public void controllerMethods() {}
    
    /*
     * @Pointcut to capture all methods within service methods
     */
    @Pointcut("execution(* in.testuniversity.service..*(..))")
    public void serviceMethods() {}
    
    /*
     * Logs execution for all controller methods
     */
    @Around("controllerMethods()")
    public Object logControllerExecution(ProceedingJoinPoint joinPoint) throws Throwable{
    	return logExecution(joinPoint, "Controller layer");
    }
    
    /**
     * Logs execution details of all service methods.
     */
    @Around("serviceMethods()")
    public Object logServiceExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        return logExecution(joinPoint, "Service layer");
    }
    
    /**
     * Generic logging method for all layers.
     *
     * @param joinPoint ProceedingJoinPoint
     * @param layer     The application layer (Controller/Service)
     * @return Object returned by the actual method
     * @throws Throwable if any exception occurs in the method execution
     */
    public Object logExecution(ProceedingJoinPoint joinPont, String layer) throws Throwable{
    	//Getting the start time, method name, arguments passed, 
    	 long startTime = System.currentTimeMillis();
    	 String methodName = joinPont.getSignature().toShortString();
    	 Object[] args = joinPont.getArgs();
    	 
    	 LOGGER.info("[{}] START - Method: {} | Args: {} ", layer, methodName, args);
    	 
    	 Object result;
    	 try {
    		 result = joinPont.proceed();
    	 }catch(Exception e) {
    		 LOGGER.error("[{}] Exception in method: {} | message: {}",layer, methodName, e.getMessage());
    		 throw e;
    	 }
    	 
    	 long elapsedTime = System.currentTimeMillis() - startTime;
    	 LOGGER.info("[{}] Method executed: {} | Response: {} | Time taken: {}ms",layer, methodName, result, elapsedTime);
    	 
    	 return result;
    }
}

