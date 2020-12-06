package myblog.bean;

public class User {
    private Integer id;
    private String logName;
    private String nickName;
    private String pwd;
    private String img;
    private Role role;
    private String email;

    public User() {}

    public User(Integer id, String logName, String nickName, String pwd, String img, Role role, String email) {
        this.id = id;
        this.logName = logName;
        this.nickName = nickName;
        this.pwd = pwd;
        this.img = img;
        this.role = role;
        this.email = email;
    }

    public User(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", logName='" + logName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", pwd='" + pwd + '\'' +
                ", img='" + img + '\'' +
                ", role=" + role +
                ", email='" + email + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
