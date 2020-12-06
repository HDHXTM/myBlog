package myblog.controller;

import myblog.bean.Comment;
import myblog.service.CommentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentServices commentServices;

    @RequestMapping("/add")
    @ResponseBody
    public Boolean addComment(Comment comment)
    {
//        System.out.println(comment);
        commentServices.addComment(comment);
        return true;
    }

    @RequestMapping("/del/{id}")
    @ResponseBody
    public Boolean delComment(@PathVariable Integer id)
    {
        commentServices.deleteById(id);
        return true;
    }
}
