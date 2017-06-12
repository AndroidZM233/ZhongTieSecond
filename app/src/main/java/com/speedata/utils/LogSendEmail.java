package com.speedata.utils;

import android.content.Context;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class LogSendEmail {

//
    /** 发信人 */
    private String from;
    /** 收信人 */
    private String to;
    /** 主题 */
    private String subject;
    /** 正文 */
    private String body;

    private Context context;

    /**
         * 发送邮件.
     * @param context
         * @return boolean - 发送结果
         */
        public boolean sendMail(Context context) {
            this.context = context;
            if (getBody() == null || getTo() == null || getFrom() == null
                    || getSubject() == null) { return false; }
            try {
                Properties props = new Properties();
                props.put("username", "speedatalog@sina.com");
                props.put("password", "Geofancismart");
                props.put("mail.transport.protocol", "smtp" );
                props.put("mail.smtp.host", "smtp.sina.com");
                props.put("mail.smtp.port", "25" );

                Session session = Session.getDefaultInstance(props); //获取验证会话

                MimeMessage msg = new MimeMessage(session);
//	                MimeBodyPart attachPart = new MimeBodyPart();
//	                FileDataSource fds = new FileDataSource(context.getFilesDir() +"/errlog.txt"); //打开要发送的文件
//
//	                attachPart.setDataHandler(new DataHandler(fds));
//	                attachPart.setFileName(fds.getName());
//	                MimeMultipart allMultipart = new MimeMultipart("mixed"); //附件
//	                allMultipart.addBodyPart(attachPart);//添加
//
//	                msg.setContent(allMultipart); //发邮件时添加附件
                msg.setFrom(new InternetAddress(getFrom()));
                msg.addRecipients(Message.RecipientType.TO, InternetAddress
                        .parse(getTo()));
                msg.setSentDate(new Date());
                msg.setSubject(getSubject());

                msg.setText(getBody());
                msg.saveChanges();

                Transport transport = session.getTransport("smtp");
                transport.connect(props.getProperty("mail.smtp.host"), props
                        .getProperty("username"), props.getProperty("password"));

                System.out.println("正在发送邮件");
                transport.sendMessage(msg, msg.getAllRecipients());
                transport.close();
                System.out.println("发送完毕");

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
                return false;
            }
            return true;
        }

        /**
         * @return Returns the body.
         */
        public String getBody() {
            return body;
        }

        /**
         * @param body
         *            The body to set.
         */
        public void setBody(String body) {
            this.body = body;
        }

        /**
         * @return Returns the from.
         */
        public String getFrom() {
            return from;
        }

        /**
         * @param from
         *            The from to set.
         */
        public void setFrom(String from) {
            this.from = from;
        }

        /**
         * @return Returns the subject.
         */
        public String getSubject() {
            return subject;
        }

        /**
         * @param subject
         *            The subject to set.
         */
        public void setSubject(String subject) {
            this.subject = subject;
        }

        /**
         * @return Returns the to.
         */
        public String getTo() {
            return to;
        }

        /**
         * @param to
         *            The to to set.
         */
        public void setTo(String to) {
            this.to = to;
        }


}
