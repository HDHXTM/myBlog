//package myblog.config;
//
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
////security+验证码拦截器
//public class filter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        System.out.println("拦截器");
//        if (request.getRequestURI().equals("/login") && request.getMethod().equalsIgnoreCase("post")) {
//            String userCode = request.getParameter("imgCode");
//            String correctCode = (String) request.getSession().getAttribute("imgCode");
//            System.out.println(userCode);
//            System.out.println(correctCode);
//            if (userCode.equals(correctCode))
//                filterChain.doFilter(request, response);
//            else System.out.println("验证码错误");
//        }
//        else filterChain.doFilter(request, response);
//    }
//}
