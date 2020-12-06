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
        return userDao.changePwd(logName,pwd);
    }

    @Override
    public void addUser(User user) {
//        默认头像
        user.setImg("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584367404804&di=070c78aac95428c480b480a87b534e96&imgtype=0&src=http%3A%2F%2Fbbs.cnlinfo.net%2Fup%2Ftou%2F150611164743.jpg");
//        加密密码
        user.setPwd(bCryptPasswordEncoder.encode(user.getPwd()));
//        角色在dao，强制设为2（普通用户）
        userDao.addUser(user);
    }
}
