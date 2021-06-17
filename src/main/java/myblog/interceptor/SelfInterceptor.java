package myblog.interceptor;

import myblog.bean.User;
import myblog.service.BlogServices;
import myblog.service.CommentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Objects;

@Component
//只能操作自己的数据。管理员除外
public class SelfInterceptor implements HandlerInterceptor {
    @Autowired
    private CommentServices commentServices;
    @Autowired
    private BlogServices blogServices;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI =request.getRequestURI();
//        String str="/blog/del/21";
        String[] str = requestURI.split("/+");//按/切割
//        str=[, blog, del, 21]
//        System.out.println(Arrays.toString(str));
        String req =str[1];Integer id=Integer.parseInt(str[3]);
        HttpSession session = request.getSession();
        User user=(User) session.getAttribute("user");
        Integer AuthorId=null;
        try {
//            操作评论
            if("comment".equals(req))
                AuthorId = commentServices.findAuthorByCommentId(id);
//            操作帖子
            else if("blog".equals(req))
                AuthorId = blogServices.findAuthorByBlogId(id);
//            操作用户
            else if("user".equals(req))
                AuthorId=id;
        } catch (Exception e) {
//            System.out.println("异常截下");
            response.sendRedirect("/myError");
            return false;
        }
//        System.out.println("作者id："+AuthorId+"  操作者id："+user.getId());
        if(Objects.equals(user.getId(), AuthorId)) {
//            System.out.println("作者，放行");
            return true;
        }else if (user.getRole().getId()==1){
//            System.out.println("管理员，放行");
            return true;
        }
//        不是作者或管理员
        response.sendRedirect("/xpc");
        return false;
    }

}
