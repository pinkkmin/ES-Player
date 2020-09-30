package com.player.es.Utils;

import lombok.Data;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import sun.awt.image.ShortInterleavedRaster;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class EmailUtils {
    private static final String PROPERTIES_DEFAULT = "application.properties";
    public static String host;
    public static Integer port;
    public static String userName;
    public static String passWord;
    public static String timeout;
    public static Properties properties;
     static  {
        properties = new Properties();
        try{
            InputStream inputStream = EmailUtils.class.getClassLoader().getResourceAsStream(PROPERTIES_DEFAULT);
            properties.load(inputStream);
            inputStream.close();
            port = 465;
            host = properties.getProperty("mailHost");
            userName = properties.getProperty("mailUser");
            passWord = properties.getProperty("mailPwd");
            timeout = properties.getProperty("mailTimeOut");
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public boolean toSend(String to, int keyNumber,int type){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setUsername(userName);
        javaMailSender.setPort(port);
        javaMailSender.setPassword(passWord);
        javaMailSender.setDefaultEncoding("UTF-8");
        Properties p = new Properties();
        p.setProperty("mail.smtp.timeout", timeout);
        p.setProperty("mail.smtp.auth", "false");
        p.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailSender.setJavaMailProperties(p);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setTo(to);
            messageHelper.setFrom(userName,"CMF&LSS");
            messageHelper.setSubject("C&L篮球球员管理系统");
            String content ;
            if(type == 0) { //注册
                content =  new String("<span style=\"font-size:21px;\">Hi，终于等到你，欢迎注册C&L篮球球员管理系统" +
                        "，这是你的验证码，请妥善保管，验证码在5分钟内有效。</span><br />");
            }
            else{  // 找回密码
               content = new String("<span style=\"font-size:21px;\">你好，您在刚刚通过邮箱找回密码" +
                        "，这是你的验证码，请妥善保管，验证码在5分钟内有效。</span><br />");
            }
            content += "<h1 style=\"font-size:35px;color:#409EFF;\">"+keyNumber+"</h1>";
            messageHelper.setText( content,true);
            javaMailSender.send(mimeMessage);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
