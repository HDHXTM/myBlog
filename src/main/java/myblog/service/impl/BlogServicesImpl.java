package myblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import myblog.bean.Blog;
import myblog.bean.User;
import myblog.dao.BlogDao;
import myblog.service.BlogServices;
import myblog.service.CommentServices;
import myblog.service.TagServices;
import myblog.utils.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service

public class BlogServicesImpl implements BlogServices {
    @Autowired
    private BlogDao blogDao;
    @Autowired
    private TagServices tagServices;
    @Autowired
    private CommentServices commentServices;

    @Override
    public PageInfo findBlogByUserId(int pageNum, int pageSize, Integer id) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> list=blogDao.findBlogByUserId(id);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }


    @Override
    public PageInfo findBlogByTagId(int pageNum, int pageSize, int id) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> list=blogDao.findBlogByTagId(id);
        return new PageInfo(list);
    }

    @Override
    public PageInfo findBlogByTypeId(int pageNum, int pageSize, int id) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> list=blogDao.findBlogByTypeId(id);
        return new PageInfo(list);
    }

    @Override
    public PageInfo findAll(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogs = blogDao.findAll();
        return new PageInfo(blogs);
    }

    @Override
    public int deleteById(Integer id) {
        return blogDao.deleteById(id);
    }


    @Override
    public int findAuthorByBlogId(int blogId) {
        return blogDao.findAuthorByBlogId(blogId);
    }

    @Override
    public PageInfo find(int pageNum, int pageSize, String message, String type) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> list;
        if (!"".equals(type))
            list=blogDao.findByNameAndType(message,type);
        else
            list=blogDao.findByName(message);
        return new PageInfo(list);
    }

    @Override
    public Integer insert(Blog blog) {
        Date date = new Date();
        blog.setCreateTime(date);
        blog.setUpdateTime(date);

        return blogDao.add(blog);
    }

    @Override
    public Blog findById(Integer id) {
        Blog blog = blogDao.findById(id);
        blog.setTags(tagServices.findBlogTags(id));
        blog.setComments(commentServices.findByBlogId(id));
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(blog.getContent()));
        return blog;
    }

    @Override
    public int updateById(Blog blog) {
        blog.setUpdateTime(new Date());
        return blogDao.updateById(blog);
    }
}
