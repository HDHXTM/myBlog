package myblog.config;

import myblog.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/blog/find/**").hasAnyRole("admin","user")
                .antMatchers("/blog/del/**").hasAnyRole("admin","user")
                .antMatchers("/blog/add/**").hasAnyRole("admin","user")
                .antMatchers("/comment/add/**").hasAnyRole("admin","user")
                .antMatchers("/comment/del/**").hasAnyRole("admin","user")
                .antMatchers("/blog/update/**").hasAnyRole("admin","user")
                .antMatchers("/toAddBlog/**").hasAnyRole("admin","user")
                .antMatchers("/user/toChangeImg/**").hasAnyRole("admin","user")
                .antMatchers("/user/changeImg/**").hasAnyRole("admin","user")
                .antMatchers("/admin/**").hasAnyRole("admin","user")
                .anyRequest().permitAll();

//        权限不足时的提示页
        http.exceptionHandling().accessDeniedPage("/error/unauth");

//        被拦截后跳到默认登录页
//        http.formLogin();

//        启用自定义登录页/toLogin,登录按钮的action必须与这里的 loginProcessingUrl("/login")一致.
//        默认参数就是username和password
        http.formLogin().loginPage("/toLogin").permitAll().
             passwordParameter("password").
             usernameParameter("username").
             loginProcessingUrl("/login").
             successForwardUrl("/index");

//        禁用跨站请求伪造保护，启用可保护安全，默认启用，启用后使用ajax非常麻烦且druid后台无法登录
        http.csrf().disable();

//        忽略一些请求，不进行csrf认证
//        http.csrf().ignoringAntMatchers("/weixin/**","/alipay/**","/logout").;
        http.logout().permitAll().logoutUrl("/logout").logoutSuccessUrl("/");

//        开启记住我功能，默认记住2周时间,这里设置为1周
        http.rememberMe().rememberMeParameter("remember-me").tokenValiditySeconds(7*24*60*60);
    }

    //    springboot2.1+/Security5.0+需要设置passwordEncoder
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder);
    }

}

