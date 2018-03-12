package com.cdut.listener;

import com.cdut.event.UserEvent;
import com.cdut.event.UserRetrievePasswordEvent;
import com.cdut.security.JWTUtil;
import com.google.common.collect.Maps;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by king on 2017/11/6.
 */
@Component
public class UserEventListener {

    private static final Logger logger = LoggerFactory.getLogger(UserEventListener.class);

    private static final String EMAIL_FROM = "dominant_wangcheng@163.com";

    private static final String EMAIL_SUBJECT = "青年地学论坛注册邮箱验证";

    private static final String EMAIL_REST = "青年地学论坛找回密码";

    private static String email_token_prefix = "email:";

    private static String email_reset_prefix = "reset:";


    @Autowired
    private RedisTemplate stringRedisTemplate;



    @Async
    @EventListener
    public void handUserEvent(UserEvent event) {
        logger.info("开始向 [{}] 发送邮件", event.getUsername());
        Map<String, Object> claims = Maps.newHashMap();
        claims.put("ID", event.getId());
        claims.put("time", System.currentTimeMillis());
        //TODO 在content中添加token
        String token = JWTUtil.generateToken(claims);
        //1.将token缓存到redis，key为email:userId,有效期为3天
        String key = this.generateEmailTokenKey(event.getId());
        stringRedisTemplate.opsForValue().set(key, token, 3, TimeUnit.DAYS);
        //2.创建包含token的链接
        String content = generateIdentificationHTML(event.getUsername(), token, event.getEmail());
        sendMailTest(content, event.getEmail());
    }

    @Async
    @EventListener
    public void handUserRetrievePasswordEvent(UserRetrievePasswordEvent event) {
        logger.info("开始向 [{}] 发送邮件", event.getUsername());
        Map<String, Object> claims = Maps.newHashMap();
        claims.put("ID", event.getId());
        claims.put("time", System.currentTimeMillis());
        //TODO 在content中添加token
        String token = JWTUtil.generateToken(claims);
        //1.将token缓存到redis，key为email:userId,有效期为3天
        String key = this.generateEmailRestet(event.getId());
        logger.info("key: [{}]", key);
        stringRedisTemplate.opsForValue().set(key, token, 20, TimeUnit.MINUTES);
        //2.创建包含token的链接
        String content = getResetPasswordEmail(event.getUsername(), token, event.getEmail());
        sendMailTest(content, event.getEmail());
    }

    private static JavaMailSenderImpl generateSenderImpl() {
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
        senderImpl.setHost("smtp.163.com");
        senderImpl.setPort(465);
        senderImpl.setUsername("dominant_wangcheng@163.com");
        //密码
        senderImpl.setPassword("*");
        senderImpl.setDefaultEncoding("UTF-8");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.ssl.enable", true);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        senderImpl.setJavaMailProperties(properties);
        return senderImpl;
    }

    private void sendMailTest(String message, String receiver) {
        try {
            JavaMailSenderImpl senderImpl = generateSenderImpl();
            MimeMessage mimeMessage = senderImpl.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(receiver);
            messageHelper.setFrom("dominant_wangcheng@163.com");
            messageHelper.setText(message, true);
            messageHelper.setSubject("青年地学论坛注册邮箱验证");
            senderImpl.send(mimeMessage);
            logger.info("向[{}] 邮件发送成功！", receiver);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("邮件发送失败！");
        }
    }

    private String generateEmailTokenKey(Long userId) {
        return email_token_prefix + userId.toString();
    }

    private String generateEmailRestet(Long userId) {
        return email_reset_prefix + userId.toString();
    }

    /**
     * 生成确认邮件
     * @param username
     * @param token
     * @param email
     * @return
     */
    private String generateIdentificationHTML(String username, String token, String email) {
        return "<html>\n" +
                "<head>\n" +
                "  <base target=\"_blank\">\n" +
                "  <style type=\"text/css\">\n" +
                "     ::-webkit-scrollbar {\n" +
                "      display: none;\n" +
                "    }\n" +
                "  </style>\n" +
                "  <style id=\"cloudAttachStyle\" type=\"text/css\">\n" +
                "    #divNeteaseBigAttach,\n" +
                "    #divNeteaseBigAttach_bak {\n" +
                "      display: none;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "\n" +
                "<body tabindex=\"0\" role=\"listitem\">\n" +
                "论坛注册地址\n" +
                "  <br>\n" +
                "  <br>\n" +
                "  <p><b>[</b>"+username+"<b>]</b>您好,这封信是由青年地学论坛发送的。</p>\n" +
                "\n" +
                "<p>您收到这封邮件,是由于在青年地学论坛新用户注册地址中使用了这个邮箱地址。如果您并没有访问过青年地学论坛，或没有进行上述操作，请忽略这封邮件。您不需要退订或进行其他进一步的操作。\n" +
                "\n" +
                "\n" +
                "  </p>\n" +
                "  <br> ----------------------------------------------------------------------\n" +
                "  <br>\n" +
                "  <strong>新用户注册说明</strong>\n" +
                "  <br> ----------------------------------------------------------------------\n" +
                "  <br>\n" +
                "  <br>\n" +
                "<p>如果您是青年地学论坛的新用户，我们需要对您的地址有效性进行验证以避免垃圾邮件或地址被滥用。\n" +
                "\n" +
                "  </p>\n" +
                "\n" +
                "  <p>您只需点击下面的链接即可进行用户注册，以下链接有效期为3天。过期可以重新请求发送一封新的邮件验证：\n" +
                "    <br>\n" +
                "\n" +
                "    <a href=\"http://www.qndxlt.com/confirm?mod=register&amp;hash="+token+"&amp;email="+email+"\"\n" +
                "      target=\"_blank\">http://www.qndxlt.com/confirm?mod=register&amp;hash="+token+"&amp;email="+email+"</a>\n" +
                "    <br> (如果上面不是链接形式，请将该地址手工粘贴到浏览器地址栏再访问)\n" +
                "  </p>\n" +
                "\n" +
                "  <p>感谢您的访问，祝您使用愉快!</p>\n" +
                "\n" +
                "\n" +
                "  <p>\n" +
                "    此致\n" +
                "    <br> https://www.qndxlt.com/\n" +
                "  </p>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <style type=\"text/css\">\n" +
                "    body {\n" +
                "      font-size: 14px;\n" +
                "      font-family: arial, verdana, sans-serif;\n" +
                "      line-height: 1.666;\n" +
                "      padding: 0;\n" +
                "      margin: 0;\n" +
                "      overflow: auto;\n" +
                "      white-space: normal;\n" +
                "      word-wrap: break-word;\n" +
                "      min-height: 100px\n" +
                "    }\n" +
                "\n" +
                "    td,\n" +
                "    input,\n" +
                "    button,\n" +
                "    select,\n" +
                "    body {\n" +
                "      font-family: Helvetica, 'Microsoft Yahei', verdana\n" +
                "    }\n" +
                "\n" +
                "    pre {\n" +
                "      white-space: pre-wrap;\n" +
                "      white-space: -moz-pre-wrap;\n" +
                "      white-space: -pre-wrap;\n" +
                "      white-space: -o-pre-wrap;\n" +
                "      word-wrap: break-word;\n" +
                "      width: 95%\n" +
                "    }\n" +
                "\n" +
                "    th,\n" +
                "    td {\n" +
                "      font-family: arial, verdana, sans-serif;\n" +
                "      line-height: 1.666\n" +
                "    }\n" +
                "\n" +
                "    img {\n" +
                "      border: 0\n" +
                "    }\n" +
                "\n" +
                "    header,\n" +
                "    footer,\n" +
                "    section,\n" +
                "    aside,\n" +
                "    article,\n" +
                "    nav,\n" +
                "    hgroup,\n" +
                "    figure,\n" +
                "    figcaption {\n" +
                "      display: block\n" +
                "    }\n" +
                "\n" +
                "    blockquote {\n" +
                "      margin-right: 0px\n" +
                "    }\n" +
                "\n" +
                "  </style>\n" +
                "\n" +
                "  <style id=\"ntes_link_color\" type=\"text/css\">\n" +
                "    a,\n" +
                "    td a {\n" +
                "      color: #064977\n" +
                "    }\n" +
                "\n" +
                "  </style>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
    }

    private String getResetPasswordEmail(String username , String token, String email) {

        return "<html>\n" +
                "<head>\n" +
                "  <base target=\"_blank\">\n" +
                "  <style type=\"text/css\">\n" +
                "     ::-webkit-scrollbar {\n" +
                "      display: none;\n" +
                "    }\n" +
                "  </style>\n" +
                "  <style id=\"cloudAttachStyle\" type=\"text/css\">\n" +
                "    #divNeteaseBigAttach,\n" +
                "    #divNeteaseBigAttach_bak {\n" +
                "      display: none;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "\n" +
                "<body tabindex=\"0\" role=\"listitem\">\n" +
                "论坛密码找回\n" +
                "  <br>\n" +
                "  <br>\n" +
                "  <p><b>[</b>"+username+"<b>]</b>您好，这封信是由青年地学论坛发送的。</p>\n" +
                "\n" +
                "<p>您收到这封邮件，是由于在青年地学论坛进行找回密码操作时使用了这个邮箱地址。如果您并没有访问过青年地学论坛，或没有进行上述操作,请忽略这封邮件。您不需要退订或进行其他进一步的操作。\n" +
                "\n" +
                "\n" +
                "  </p>\n" +
                "  <br> ----------------------------------------------------------------------\n" +
                "  <br>\n" +
                "  <strong>密码找回说明</strong>\n" +
                "  <br> ----------------------------------------------------------------------\n" +
                "  <br>\n" +
                "  <br>\n" +
                "\n" +
                "  </p>\n" +
                "\n" +
                "  <p>您只需点击下面的链接即可进行重置密码的下一步操作,以下链接有效期为10分钟。过期可以用找回密码的方式重新请求发送一封新的邮件验证:\n" +
                "    <br>\n" +
                "\n" +
                "    <a href=\"http://www.qndxlt.com/reset?mod=resetPassword&amp;hash="+token+"&amp;email="+email+"\"\n" +
                "      target=\"_blank\">http://www.qndxlt.com/reset?mod=resetPassword&amp;hash="+token+"&amp;email="+email+"</a>\n" +
                "    <br> (如果上面不是链接形式，请将该地址手工粘贴到浏览器地址栏再访问)\n" +
                "  </p>\n" +
                "\n" +
                "  <p>感谢您的访问,祝您使用愉快!</p>\n" +
                "\n" +
                "\n" +
                "  <p>\n" +
                "此致\n" +
                "    <br> http://www.qndxlt.com/\n" +
                "  </p>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <style type=\"text/css\">\n" +
                "    body {\n" +
                "      font-size: 14px;\n" +
                "      font-family: arial, verdana, sans-serif;\n" +
                "      line-height: 1.666;\n" +
                "      padding: 0;\n" +
                "      margin: 0;\n" +
                "      overflow: auto;\n" +
                "      white-space: normal;\n" +
                "      word-wrap: break-word;\n" +
                "      min-height: 100px\n" +
                "    }\n" +
                "\n" +
                "    td,\n" +
                "    input,\n" +
                "    button,\n" +
                "    select,\n" +
                "    body {\n" +
                "      font-family: Helvetica, 'Microsoft Yahei', verdana\n" +
                "    }\n" +
                "\n" +
                "    pre {\n" +
                "      white-space: pre-wrap;\n" +
                "      white-space: -moz-pre-wrap;\n" +
                "      white-space: -pre-wrap;\n" +
                "      white-space: -o-pre-wrap;\n" +
                "      word-wrap: break-word;\n" +
                "      width: 95%\n" +
                "    }\n" +
                "\n" +
                "    th,\n" +
                "    td {\n" +
                "      font-family: arial, verdana, sans-serif;\n" +
                "      line-height: 1.666\n" +
                "    }\n" +
                "\n" +
                "    img {\n" +
                "      border: 0\n" +
                "    }\n" +
                "\n" +
                "    header,\n" +
                "    footer,\n" +
                "    section,\n" +
                "    aside,\n" +
                "    article,\n" +
                "    nav,\n" +
                "    hgroup,\n" +
                "    figure,\n" +
                "    figcaption {\n" +
                "      display: block\n" +
                "    }\n" +
                "\n" +
                "    blockquote {\n" +
                "      margin-right: 0px\n" +
                "    }\n" +
                "\n" +
                "  </style>\n" +
                "\n" +
                "  <style id=\"ntes_link_color\" type=\"text/css\">\n" +
                "    a,\n" +
                "    td a {\n" +
                "      color: #064977\n" +
                "    }\n" +
                "\n" +
                "  </style>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
    }
}
