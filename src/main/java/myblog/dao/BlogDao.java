package myblog.dao;

import java.util.List;
import myblog.bean.Blog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogDao {

    @Delete("delete from blog where id=#{id}")
    int deleteById(Integer id);

    //当id为自增时，加入@Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    //对blog对象赋值自增id
    @Insert("insert into blog(title, content, picture, createTime, updateTime, type, author) VALUES " +
            " (#{title},#{content},#{picture},#{createTime},#{updateTime},#{type},#{user.id}) ")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    Integer add(Blog blog);

    
    @Select("select DISTINCT blog.id blogId,blog.content,blog.title,user.id userId,blog.picture,blog.updateTime,type.name type,user.nickName,user.img " +
            "from blog,blog_tag,tag,type,user where " +
            "blog.id=blog_tag.blogId and blog.type=type.id and blog.author=user.id and blog_tag.tagId=tag.id and blog.id=#{id}")
    @Results(id = "blog",value = {
//            有两个id，只能！只能！只能！nmbd！通过取别名来自动赋值
            @Result(column = "blogId", property = "id"),
            @Result(column = "nickName", property = "user.nickName"),
            @Result(column = "img", property = "user.img"),
            @Result(column = "userId", property = "user.id"),
    })
    Blog findById(Integer id);

    @Update("update blog set title=#{title}, content=#{content}, picture=#{picture},updateTime=#{updateTime},type=#{type} where id=#{id} ")
    int updateById(Blog blog);

    @Select("select DISTINCT blog.id blogId,blog.title,blog.picture,blog.updateTime,blog.createTime,type.name type,user.nickName,user.img,user.id userId " +
            "from blog,blog_tag,tag,type,user where " +
            "blog.id=blog_tag.blogId and blog.type=type.id and blog.author=user.id and blog_tag.tagId=tag.id order by blog.createTime ")
    @ResultMap(value = "blog")
    List<Blog> findAll();

    @Select("select DISTINCT blog.id blogId,blog.title,blog.picture,blog.updateTime,blog.createTime,type.name type,user.nickName,user.img,user.id userId " +
            " from blog,blog_tag,tag,type,user where " +
            " blog.id=blog_tag.blogId and blog.type=type.id and blog.author=user.id and blog_tag.tagId=tag.id and blog.type=#{id} order by blog.createTime ")
    @ResultMap(value = "blog")
    List<Blog> findBlogByTypeId(int id);

    @Select("select DISTINCT blog.id blogId,blog.title,blog.picture,blog.updateTime,blog.createTime,type.name type,user.nickName,user.img,user.id userId " +
            " from blog,blog_tag,tag,type,user where " +
            " blog.id=blog_tag.blogId and blog.type=type.id and blog.author=user.id and blog_tag.tagId=tag.id and tag.id=#{id} order by blog.createTime ")
    @ResultMap(value = "blog")
    List<Blog> findBlogByTagId(int id);

    @Select("select DISTINCT blog.id blogId,blog.title,blog.picture,blog.updateTime,blog.createTime,type.name type,user.nickName,user.img,user.id userId " +
            " from blog,blog_tag,tag,type,user where " +
            " (blog.id=blog_tag.blogId and blog.type=type.id and blog.author=user.id and blog_tag.tagId=tag.id) and " +
            " (blog.title like '%${name}%' or blog.content like '%${name}%') order by blog.createTime ")
    @ResultMap(value = "blog")
    List<Blog> findByName(String name);

    @Select("select DISTINCT blog.id blogId,blog.title,blog.updateTime,blog.createTime,type.name type,user.nickName,user.id userId " +
            " from blog,blog_tag,tag,type,user where " +
            " blog.id=blog_tag.blogId and blog.type=type.id and blog.author=user.id and blog_tag.tagId=tag.id and user.id=#{id} order by blog.createTime ")
    @ResultMap(value = "blog")
    List<Blog> findBlogByUserId(Integer id);

    @Select("select DISTINCT blog.id blogId,blog.title,blog.picture,blog.updateTime,blog.createTime,type.name type,user.nickName,user.img,user.id userId " +
            " from blog,blog_tag,tag,type,user where " +
            " (blog.id=blog_tag.blogId and blog.type=type.id and blog.author=user.id and blog_tag.tagId=tag.id and " +
            " type.id=#{type} ) and" +
            " (blog.title like '%${message}%' or blog.content like '%${message}%') order by blog.createTime ")
    @ResultMap(value = "blog")
    List<Blog> findByNameAndType(String message, String type);

    @Select("select author from blog where id=#{blogId}")
    int findAuthorByBlogId(int blogId);
}