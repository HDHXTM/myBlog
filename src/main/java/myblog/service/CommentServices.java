package myblog.service;

import myblog.bean.Comment;

import java.util.List;

public interface CommentServices {
    int deleteById(Integer id);

    List<Comment> findByBlogId(int id);

    void addComment(Comment comment);

    Integer findAuthorByCommentId(int commentId);
}