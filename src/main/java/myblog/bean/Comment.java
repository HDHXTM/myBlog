package myblog.bean;

import java.util.Date;
import java.util.List;

public class Comment {
    private Integer id;

    private Date createTime;

    private Integer blogId;

    private Integer parentId;

    private String content;

    private User user;

    private User toUser;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", user=" + user +
                ", toUser=" + toUser +
                ", blogId=" + blogId +
                ", parentId=" + parentId +
                ", childComments=" + childComments +
                ", content='" + content + '\'' +
                '}';
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    private List<Comment> childComments;

    public List<Comment> getChildComments() {
        return childComments;
    }

    public void setChildComments(List<Comment> childComments) {
        this.childComments = childComments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}