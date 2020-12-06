package myblog.service.impl;

import myblog.bean.Comment;
import myblog.bean.User;
import myblog.dao.CommentDao;
import myblog.dao.UserDao;
import myblog.service.CommentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServicesImpl implements CommentServices {
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Integer findAuthorByCommentId(int commentId) {
        return commentDao.findAuthorByCommentId(commentId);
    }

    @Override
    public int deleteById(Integer id) {
        return commentDao.deleteById(id);
    }

    @Override
    public void addComment(Comment comment) {
        comment.setCreateTime(new Date());
//        是回复别人，清除blogId
        if (comment.getToUser().getId()!=null)
            comment.setBlogId(null);
        commentDao.addComment(comment);
    }

    @Override
    public List<Comment> findByBlogId(int id) {
        List<Comment> comments = commentDao.findByBlogId(id);
        if (comments != null) {
            for (Comment comment : comments) {
//            查找子评论
                List<Comment> childComments = commentDao.findChildComments(comment.getId());
                if (childComments != null) {
                    for (Comment childComment : childComments) {
//                查找要@的user
                        User toUser = userDao.findById(childComment.getToUser().getId());
                        toUser.setPwd(null);
                        childComment.setToUser(toUser);
                    }
                    comment.setChildComments(childComments);
                }
            }
            return comments;
        }
        return null;
    }
}
