package myblog.service.impl;

import myblog.bean.Role;
import myblog.bean.User;
import myblog.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

//security登录验证
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.findByLogName(s);
//            System.out.println(user);
        if (user!=null) {
            String pwd = user.getPwd();
            ArrayList<GrantedAuthority> list = new ArrayList<>();
            list.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));
//            security只存用户名和角色。只能这样再存一次了，不然前端取不到用户头像。
            HttpSession session = request.getSession();
            user.setPwd("");
            session.setAttribute("user", user);
//            System.out.println(user);
            return new org.springframework.security.core.userdetails.User(user.getNickName(), pwd, list);
        }
        return null;
    }
}
