package myblog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

//日志
@Aspect    //定义切面
@Component

public class LogAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* myblog.controller.*.*(..))")        //定义切入点表达式
    public void log(){}

    @Before("log()")    //引用切入点
    public void doBefore(JoinPoint joinPoint){

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        //获得类名.方法名
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        //获得方法参数
        Object[] args = joinPoint.getArgs();

        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        System.out.println(requestLog);
        //打印请求信息
        logger.info("Request: {}", requestLog);
    }

//    @After("log()")
//    public void doAfter(){
//        logger.info("------------doAfter------------");
//    }
//
//    @AfterReturning(returning = "result", pointcut = "log()")
//    public void doAfterReturn(Object result){
//
//        //打印返回值
//        logger.info("Result: {}", result);
//    }`


    public class RequestLog{      //用于封装请求信息
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;
        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
