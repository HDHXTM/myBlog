package myblog.service;

import com.github.pagehelper.PageInfo;
import myblog.bean.Blog;


public interface BlogServices {

    int deleteById(Integer id);

    Integer insert(Blog record);

    Blog findById(Integer id);

    int updateById(Blog blog);

    PageInfo findAll(int pageNum, int pageSize);

    PageInfo findBlogByTypeId(int pageNum, int pageSize, int id);

    PageInfo findBlogByTagId(int pageNum, int pageSize, int id);

    PageInfo findBlogByUserId(int pageNum, int pageSize, Integer id);

    PageInfo find(int pageNum, int pageSize, String message, String type);

    int findAuthorByBlogId(int blogId);
}