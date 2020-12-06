package myblog.config;

import myblog.bean.Tag;
import myblog.bean.Type;
import myblog.service.TagServices;
import myblog.service.TypeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.util.List;

//自定义ContextLoaderListener
@Configuration
public class MyContextLoaderListener extends ContextLoaderListener {
    @Autowired
    private TypeServices typeServices;
    @Autowired
    private TagServices tagServices;

    public MyContextLoaderListener() {super();}

    public MyContextLoaderListener(WebApplicationContext context) {super(context);}

    //  开机时将tags和types放入application域
    @Override
    public void contextInitialized(ServletContextEvent event) {
//        System.out.println("初始化application域");
        ServletContext context = event.getServletContext();
        List<Tag> tags=tagServices.findAll();
        List<Type> types=typeServices.findAll();
        context.setAttribute("tags",tags);
        context.setAttribute("types",types);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {super.contextDestroyed(event);}
}
