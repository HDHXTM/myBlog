package myblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;

//开启多线程支持
@EnableAsync
@SpringBootApplication
//启用数据库事务配置
@ImportResource("classpath:transaction.xml")
@MapperScan("myblog.dao")
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }
    @Bean
    public BCryptPasswordEncoder bcEncoder(){return new BCryptPasswordEncoder();}
}
