package myblog.service.impl;

import myblog.utils.sendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailServices {
    @Autowired
    private sendMail sendMail;

    public String sendEmail(String UserEmail)
    {
//        生成验证码
        String chars = "0123456789abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb= new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int rand = (int) (Math.random() * (10 + 26 * 2));
            sb.append(chars.charAt(rand));
        }
        String code = sb.toString();
//        System.out.println("验证码:"+code);

        sendMail.easyEmail("验证码",code,UserEmail);
        return code;
    }
}
