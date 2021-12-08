package myblog.controller;

import com.github.pagehelper.PageInfo;
import myblog.bean.Blog;
import myblog.bean.Tag;
import myblog.bean.User;
import myblog.service.BlogServices;
import myblog.service.TagServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogServices blogServices;
    @Autowired
    private TagServices tagServices;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/findAll2")
    @ResponseBody
    public PageInfo findAll2(@RequestParam(defaultValue = "1",value = "pageNum")int pageNum)
    {
        return blogServices.findAll(pageNum, 5);
    }

    @RequestMapping("/id/{id}")
    @ResponseBody
    public Blog detail2(@PathVariable("id") Integer id)
    {
        return blogServices.findById(id);
    }


    @RequestMapping("/findAll")
    public ModelAndView findAll(@RequestParam(defaultValue = "1",value = "pageNum")int pageNum)
    {
        ModelAndView mv = new ModelAndView();
        PageInfo pageInfo = blogServices.findAll(pageNum, 5);
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("index");
        return mv;
    }



    /**
     *
     * @param message title或content
     * @param type
     * @param pageNum
     * @return
     */
    @RequestMapping("/find")
    public ModelAndView find(String message,String type,
                                   @RequestParam(defaultValue = "1",value = "pageNum")int pageNum)
    {
        ModelAndView mv = new ModelAndView();
        PageInfo pageInfo = blogServices.find(pageNum, 5,message,type);
        mv.addObject("pageInfo",pageInfo);
//        type1防止重名
        mv.addObject("type1",type);
//        点击下一页时使用
        mv.addObject("message",message);
        mv.setViewName("index");
        return mv;
    }

//    帖子详情
    @RequestMapping("/{id}")
    public ModelAndView detail(@PathVariable("id") Integer id)
    {
        Blog blog = blogServices.findById(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("blog",blog);
        mv.setViewName("blog");
        return mv;
    }



    @RequestMapping("/del/{id}")
    @ResponseBody
    public Map<String,String> del(@PathVariable("id") Integer id){
        HashMap<String, String> map = new HashMap<>();
        try {
            blogServices.deleteById(id);
        } catch (Exception e) {
            map.put("msg","服务器错误！");
            return map;
        }
        map.put("msg","success");
        return map;
    }

    @RequestMapping("/update/{id}")
    public ModelAndView update(@PathVariable("id") Integer id)
    {
        Blog blog = blogServices.findById(id);

//        前端没法整啊啊啊啊。blogs-input.html  67行
        List<Tag> tags = blog.getTags();
        StringBuilder sb = new StringBuilder();
        for (Tag tag : tags) {
            sb.append(tag.getId()+",");
        }
//        去除最后一个","
        String tagsId = sb.deleteCharAt(sb.length() - 1).toString();
//        System.out.println(tagsId);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/blogs-input");
        mv.addObject("blog",blog);
        mv.addObject("tagsId",tagsId);
        return mv;
    }

    @RequestMapping("/save")
    @ResponseBody
    public Map<String,String> save(Blog blog,Integer[] tagsId){
        HashMap<String, String> map = new HashMap<>();
//        是修改
        if (blog.getId()!=null) {
            try {
                blogServices.updateById(blog);
                tagServices.updateBlogTags(blog.getId(),tagsId);
            } catch (Exception e) {
                map.put("msg","服务器错误！");
                return map;
            }
        }
//        是添加
        else {
            try {
//                设置作者
                User user = (User) request.getSession().getAttribute("user");
                blog.setUser(new User(user.getId()));

//                Dao@option 赋值自增id
//                System.out.println("处理前："+blog.getId());
//                Integer id = blogServices.insert(blog);
//                System.out.println("返回值："+id);
//                System.out.println("处理后: "+blog.getId());
                blogServices.insert(blog);
                tagServices.updateBlogTags(blog.getId(),tagsId);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("msg","服务器错误！");
                return map;
            }
        }
        map.put("msg","success");
        return map;
    }
}
