package myblog.service;

import myblog.bean.User;

public interface UserService {
    void addUser(User user);

    int changePwd(String logName, String pwd);

    int verifyEmail(String email, String logName);


    User findById(int userId);

    void changeImgById(Integer userId,String photoName);
}
