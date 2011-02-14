package org.encuestame.core.integration;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.mail.MailHeaders;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class MailSenderActivator {

    @Autowired
    private JavaMailSender mailSender;

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMessage(final Message<?> message) {
        System.out.println("Message is:"+message.getPayload());
        final MessageHeaders messageHeaders = message.getHeaders();
        messageHeaders.get(MailHeaders.TO);
        final String content = message.getPayload().toString();

        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage)
                    throws MessagingException {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setSubject("Some new subject");
                message.setTo(messageHeaders.get(MailHeaders.TO).toString().split(";"));
                message.setFrom(messageHeaders.get(MailHeaders.FROM).toString());
                message.setText(content, true);
            }
        };
        mailSender.send(preparator);

    }
}
