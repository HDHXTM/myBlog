package myblog.controller;

import com.github.pagehelper.PageInfo;
import myblog.service.BlogServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private BlogServices blogServices;

    @RequestMapping("/{id}")
    public ModelAndView findBlogByTagId(@PathVariable("id") int id, @RequestParam(defaultValue = "1",value = "pageNum")int pageNum){
        ModelAndView mv = new ModelAndView();
        mv.addObject("activeTagId",id);
        PageInfo list=blogServices.findBlogByTagId(pageNum,5,id);
        mv.addObject("pageInfo",list);
        mv.setViewName("tags");
        return mv;
    }
}
