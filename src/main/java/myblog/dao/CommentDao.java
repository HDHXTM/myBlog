package myblog.dao;

import java.util.List;
import myblog.bean.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDao {

    @Delete("delete from comment where id=#{id}")
    int deleteById(Integer id);

    @Select("select img,nickName,user.id userId,createTime,content,comment.id commentId,toUserId " +
            "from comment,user where user.id=userId and blogId=#{id}")
    @Results(id = "comment",value = {
        @Result(column = "img",property = "user.img"),
        @Result(column = "nickName",property = "user.nickName"),
        @Result(column = "userId",property = "user.id"),
        @Result(column = "commentId",property = "id"),
        @Result(column = "toUserId",property = "toUser.id")
    })
    List<Comment> findByBlogId(int id);

    @Select("select img,nickName,user.id userId,createTime,content,comment.id commentId,toUserId  " +
            " from comment,user where user.id=userId and parentId=#{id} order by createTime")
    @ResultMap(value = "comment")
    List<Comment> findChildComments(Integer id);

    @Insert("insert into comment(content, createTime, userId, blogId, parentId, toUserId) " +
            "VALUES (#{content},#{createTime},#{user.id},#{blogId},#{parentId},#{toUser.id})")
    void addComment(Comment comment);

    @Select("select userId from comment where id=#{commentId} ")
    Integer findAuthorByCommentId(int commentId);
}