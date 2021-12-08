package myblog.controller;

import com.github.pagehelper.PageInfo;
import myblog.bean.Role;
import myblog.bean.User;
import myblog.service.BlogServices;
import myblog.service.UserService;
import myblog.utils.UploadFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BlogServices blogServices;
    @Autowired
    private HttpServletRequest request;


    @RequestMapping("/register")
    @ResponseBody
    public Map<String,String> register(User user,String mailCode)
    {
        HashMap<String, String> map = new HashMap<>();
        HttpSession session = request.getSession();
        String correctCode=(String) session.getAttribute("mailCode");
//        String correctCode="1";
//        System.out.println(correctCode);
        if (!mailCode.equals(correctCode))
            map.put("msg","验证码错误");
        else {
            try {
                userService.addUser(user);
                session.removeAttribute("mailCode");
                map.put("msg","success");
            } catch (Exception e) {
                map.put("msg","用户名或邮箱已存在！");
            }
        }
        return map;
    }

    @PostMapping("/changePwd")
    @ResponseBody
    public Map<String,String> changePwd(User user,String code)
    {
        HashMap<String, String> map = new HashMap<>();
        HttpSession session = request.getSession();
        String correctCode=(String) session.getAttribute("mailCode");
//        String correctCode="1";
        if (!code.equals(correctCode))
            map.put("msg","邮箱验证码错误");
        else {
            try {
//                成功修改
                if(userService.changePwd(user.getLogName(), user.getPwd())==1) {
                    session.removeAttribute("mailCode");
                    map.put("msg", "success");
                }
                else map.put("msg","服务器错误！");
            } catch (Exception e) {
                map.put("msg","服务器错误！");
            }
        }
        return map;
    }

    @RequestMapping("/homePage/{id}")
    public ModelAndView homePage(@PathVariable("id") Integer id,
                                 @RequestParam(defaultValue = "1",value = "pageNum")int pageNum){
        ModelAndView mv = new ModelAndView();
        PageInfo pageInfo=blogServices.findBlogByUserId(pageNum,5,id);
        mv.addObject("pageInfo",pageInfo);
        mv.addObject("userId",id);
        mv.setViewName("/user/blogs");
        return mv;
    }

    @RequestMapping("/toChangeImg/{userId}")
    public ModelAndView toChangeImg(@PathVariable("userId")int userId){
        ModelAndView mv = new ModelAndView();
        User user=userService.findById(userId);
        mv.addObject("user",user);
        mv.setViewName("/user/changeImg");
        return mv;
    }

    @RequestMapping("/changeImg/{userId}")
//MultipartFile upload名称与<input type="file" name="upload">一致
    public String changeImg(MultipartFile photo,@PathVariable("userId") Integer userId) throws Exception {
        String projectPath = System.getProperty("user.dir");
        String path = projectPath + "\\target\\classes\\static\\userImg";
        Map<String, String> result = UploadFileUtil.uploadPhoto(photo, path);
        String success = result.get("success");
        if ("1".equals(success)){
            String photoName = result.get("photoName");
            userService.changeImgById(userId,photoName);
            User user = (User) request.getSession().getAttribute("user");
            user.setImg(photoName);
            return "redirect:/user/homePage/"+userId;
        }
        else
            return "error/error";
    }
}
