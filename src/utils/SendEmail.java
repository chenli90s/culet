package utils;

import com.sun.mail.util.MailSSLSocketFactory;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Chenli
 * @version $Rev$
 * @time 2017/2/25 13:44
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class SendEmail {

    public static void send(String email,String uuid){
        Properties properties = new Properties();
        properties.put("mail.transport.protocol","smtp");
        properties.put("mail.smtp.host","smtp.qq.com");
        properties.put("mail.smtp.auth",true);
        properties.put("mail.debug",true);
        // 开启SSL加密，否则会失败
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);

        //nmxhwwtrgpgwfebf  //aybipnzvcaorfeci

        Session defaultInstance = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("1294848111@qq.com","aybipnzvcaorfeci");
            }
        });
        MimeMessage mimeMessage = new MimeMessage(defaultInstance);
        try {
            mimeMessage.setSubject("注册邮箱验证");
            mimeMessage.setSentDate(new Date());
            mimeMessage.setSender(new InternetAddress("admin@chenli90s.cn"));
            mimeMessage.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(email));
            mimeMessage.setText("你好：\n  你注册，为了确认邮箱安全性");
            mimeMessage.setContent("<a href='loaclhost:8080/user/user_activiteEmail" +
                    "?user.id="+uuid+"'>请点击此验证</a>", "text/html;charset=UTF-8");

            mimeMessage.saveChanges();   // 保存邮件(可选)

            Transport transport = defaultInstance.getTransport();
            transport.connect();
            System.out.println(email);
            transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }
}
