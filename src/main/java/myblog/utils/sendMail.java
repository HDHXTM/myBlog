package myblog.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class sendMail {
//    值来自application.yml
    @Value("${myEmail}")
    private String myEmail;

    @Autowired
    private JavaMailSenderImpl mailSender;

    //    开新线程发送简单邮件
    @Async
    public void easyEmail(String title,String text,String targetMail)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(title);
        message.setText(text);
        message.setFrom(myEmail);
        message.setTo(targetMail);
        mailSender.send(message);
    }

    @Async
    public void complexEmail(String title,String text,File[] files,String targetMail) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        true代表启用附件
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject(title);
//        true支持html转化
        helper.setText(text,true);
        if (files!=null)
        {
            for (File file : files)
                helper.addAttachment(file.getName(),file);
        }
        helper.setFrom(myEmail);
        helper.setTo(targetMail);
        mailSender.send(mimeMessage);
    }
}
