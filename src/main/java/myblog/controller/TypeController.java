package myblog.controller;

import com.github.pagehelper.PageInfo;
import myblog.service.BlogServices;
import myblog.service.TypeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/type")
public class TypeController {
    @Autowired
    private BlogServices blogServices;

    @RequestMapping("/{id}")
    public ModelAndView findBlogByTypeId(@PathVariable("id") int id,@RequestParam(defaultValue = "1",value = "pageNum")int pageNum){
        ModelAndView mv = new ModelAndView();
        mv.addObject("activeTypeId",id);
        PageInfo list=blogServices.findBlogByTypeId(pageNum,5,id);
        mv.addObject("pageInfo",list);
        mv.setViewName("types");
        return mv;
    }
}
