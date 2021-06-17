package myblog.service.impl;

import myblog.bean.User;
import myblog.dao.UserDao;
import myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServicesImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void changeImgById(Integer userId, String photoName) {
        userDao.changeImgById(userId,photoName);
    }

    @Override
    public User findById(int userId) {
        return userDao.findById(userId);
    }

    @Override
    public int verifyEmail(String email, String logName) {
        return userDao.verifyEmail(email,logName);
    }

    @Override
    public int changePwd(String logName, String pwd) {
        return userDao.changePwd(logName,bCryptPasswordEncoder.encode(pwd));
    }

    @Override
    public void addUser(User user) {
//        默认头像
        user.setImg("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2594671789,2155354930&fm=11&gp=0.jpg");
//        加密密码
        user.setPwd(bCryptPasswordEncoder.encode(user.getPwd()));
//        角色权限在dao，强制设为2（普通用户）
        userDao.addUser(user);
    }
}
