package myblog.controller;

import myblog.service.UserService;
import myblog.service.impl.EmailServices;
import myblog.utils.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

//跳转与验证码
@Controller
public class SystemController {

    @Autowired
    private EmailServices emailServices;
    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;



//    邮箱验证码
    @GetMapping("/mailVerifyCode")
    @ResponseBody
    public Map<String,String> mailVerifyCode(String email,String logName){
        HashMap<String, String> map = new HashMap<>();
        String code=null;
//        当logName空时，（logName==null）=false！！！（"".equals(logName) )=true!!!

        //        注册
        if (!"".equals(logName))
            code = emailServices.sendEmail(email);
//        改密码
        else if (userService.verifyEmail(email,logName)==1)
            code = emailServices.sendEmail(email);
        else {
            map.put("msg","用户名与邮箱不匹配");
            return map;
        }
        HttpSession session = request.getSession();
        session.setAttribute("mailCode",code);

        return map;
    }

//    图片验证码
    @GetMapping("/imgVerifyCode")
    public void imgVerifyCode(OutputStream outputStream){
        try {
            HttpSession session = request.getSession();
//            生成验证码并输出到页面
            String verifyCode = VerifyCodeUtils.outputVerifyImage(120, 40, outputStream, 4);
//            System.out.println("verifyCode:"+verifyCode);
            session.setAttribute("imgCode",verifyCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/checkImgCode/{imgCode}")
    @ResponseBody
    public Map<String,String> checkImgCode(@PathVariable String imgCode)
    {
        HashMap<String, String> map = new HashMap<>();
        HttpSession session = request.getSession();
        String correctCode = (String) session.getAttribute("imgCode");
//        System.out.println("correctCode"+correctCode);
        if(!correctCode.equalsIgnoreCase(imgCode)) {
            map.put("msg", "图片验证码错误");
            return map;
        }
        session.removeAttribute("imgCode");
        map.put("msg","success");
        return map;
    }

    @RequestMapping({"/","/index","/index.html"})
    public String index(){ return "redirect:/blog/findAll";}

    @RequestMapping("/toLogin")
    public String toLogin(){return "user/login";}

    @RequestMapping("/toSelf")
    public String toSelf(){return "user/blogs";}

    @RequestMapping("/toSearch")
    public String toSearch(){return "search";}

    @RequestMapping("/myError")
    public String error(){return "error/error";}

    @RequestMapping("/xpc")
    public String xpc(){return "error/xpc";}

    @RequestMapping("/toAddBlog")
    public String toAddBlog(){return "user/blogs-input";}

    @RequestMapping("toRegister")
    public String toRegister(){return "user/register";}

    @RequestMapping("toChangePwd")
    public String toChangePwd(){return "user/changePwd";}
}
