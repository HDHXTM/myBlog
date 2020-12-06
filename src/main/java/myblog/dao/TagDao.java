package myblog.dao;

import myblog.bean.Tag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagDao {

    @Select("select * from tag")
    List<Tag> findAll();

    @Select("select tag.id,tag.name from tag,blog,blog_tag where " +
            "blog_tag.tagId=tag.id and blog_tag.blogId=blog.id and blog.id=#{id}")
    List<Tag> findBlogTags(int id);

    @Delete("delete from blog_tag where blogId=#{id}")
    void deleteTagsByBlogId(Integer id);

    @Insert("insert into blog_tag(blogId, tagId) VALUES (#{blogId},#{tagId})")
    void addBlogTag(Integer blogId,Integer tagId);
}