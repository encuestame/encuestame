/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.mail;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.encuestame.core.service.AbstractBaseService;
import org.encuestame.core.service.IService;
import org.encuestame.utils.security.SignUpBean;
import org.encuestame.utils.web.UnitUserBean;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Class Implements a Mail Service.
 * @author Picado, Juan juan@encuestame.org
 * @since May 05, 2009
 * @version $Id$
 */
public class MailServiceImpl extends AbstractBaseService implements MailService, IService {

    private Log log = LogFactory.getLog(this.getClass());

    /** email to  no-response. **/
    private String noEmailResponse;
    /** mail sender. **/
    private JavaMailSenderImpl mailSender;
    /** template of message. **/
    private SimpleMailMessage templateMessage;
    /** VelocityEngine. **/
    private VelocityEngine velocityEngine;

    /**
     * setter mail sender.
     * @param mailSender mail sender
     */
    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * getter mail sender.
     * @return mail message
     */
    public SimpleMailMessage getTemplateMessage() {
        return templateMessage;
    }

    /**
     * setter of template message.
     * @param templateMessage template
     */
    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }

    /**
     * Send a email.
     * @param to email to send
     * @param subject subject
     * @param text text of body
     */
    public void send(
            final String to,
            final String subject,
            final String text)
            throws MailSendException
    {
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setFrom(getNoEmailResponse());
        msg.setTo(to);
        // msg.setCc();
        msg.setText(text);
        msg.setSubject(subject);
        mailSender.send(msg);
        log.debug("mail.succesful");
    }

    /**
     * Send invitation.
     * @param to email to send
     * @param code code of password
     * @throws MailSendException mail exception.
     */
    public void sendInvitation(
            final String to,
            final String code) throws MailSendException
    {
        final SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setFrom(getNoEmailResponse());
        msg.setTo(to);
        msg
                .setText("<h1>Invitation to Encuestame</h1><p>Please confirm"
                        +" this invitation <a>http://www.encuesta.me/cod/"
                        + code + "</a>");
        msg.setSubject("test");
        try{
            log.info("Sending email");
            log.debug("Sending host "+mailSender.getHost());
            log.debug("Sending password "+mailSender.getPassword());
            log.debug("Sending username "+mailSender.getUsername());
            log.debug("Sending enconding "+mailSender.getDefaultEncoding());
            log.debug("Sending protocol "+mailSender.getProtocol());
            log.debug("Sending port "+mailSender.getPort());
            log.debug("Sending port auth "+mailSender.getJavaMailProperties().getProperty("mail.smtp.auth"));
            log.debug("Sending port starttls "+mailSender.getJavaMailProperties().getProperty("mail.smtp.starttls.enable"));
            log.debug("Sending port required "+mailSender.getJavaMailProperties().getProperty("mail.smtp.starttls.required"));
            mailSender.send(msg);
            log.info("Sended email");
        }
        catch (Exception e) {
            log.error("Error on send email "+e.getMessage());
        }
    }

    /**
     * Delete notification.
     * @param to mail to send
     * @param body body of message
     * @throws MailSendException exception
     */
    public void sendDeleteNotification(
            final String to,
            final String body)throws MailSendException
   {
        log.debug("sendDeleteNotification ->"+body);
        log.debug("sendDeleteNotification to->"+to);
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        log.debug("sendDeleteNotification setFrom..");
        msg.setFrom(getNoEmailResponse());
        msg.setTo(to);
        msg.setText(body);
        msg.setSubject(getMessageProperties("DeleteSubjectInvitation"));
        log.debug("sendDeleteNotification sending..");
        mailSender.send(msg);
        log.debug("sendDeleteNotification sendend..");
    }

    /**
     * Send Password Confirmation Email.
     * @param user
     */
    public void sendPasswordConfirmationEmail(final SignUpBean user) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
           public void prepare(MimeMessage mimeMessage) throws Exception {
              MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
              message.setTo(user.getEmail());
              message.setSubject("New Password Confirmation");
              message.setFrom(noEmailResponse);
              Map model = new HashMap();
              model.put("user", user);
              String text = VelocityEngineUtils.mergeTemplateIntoString(
                 velocityEngine, "password-confirmation.vm", model);
              message.setText(text, true);
           }
        };
        send(preparator);
     }

    /**
     * Send Renew Password Email.
     * @param unitUserBean {@link UnitUserBean}.
     */
    public void sendRenewPasswordEmail(final UnitUserBean unitUserBean){
        final Map<String, UnitUserBean> model = new HashMap<String, UnitUserBean>();
        model.put("user", unitUserBean);
        this.sendMimeEmail(model, unitUserBean.getEmail(), "Your New Password", this.noEmailResponse,
                           "renew-password.vm");
    }

    /**
     * Send Mime Email.
     * @param model
     * @param email
     * @param subject
     * @param from
     * @param template
     */
    @SuppressWarnings("unchecked")
    public void sendMimeEmail(
            final Map model,
            final String email,
            final String subject,
            final String from,
            final String template) {
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
           public void prepare(MimeMessage mimeMessage) throws Exception {
              final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
              message.setTo(email);
              message.setSubject(subject);
              message.setFrom(from);
              final String text = VelocityEngineUtils.mergeTemplateIntoString(
                 velocityEngine, template, model);
              message.setText(text, true);
           }
        };
        this.send(preparator);
     }

    /**
     * Send Mime Message.
     * @param preparator
     * @throws MailSendException
     */
    public void send(final MimeMessagePreparator preparator) throws MailSendException {
        this.mailSender.send(preparator);
        log.debug("mail.succesful");
    }


    /**
     *getter of no email response.
     * @return noEmailResponse
     */
    private String getNoEmailResponse() {
        return noEmailResponse;
    }

    /**
     * setter of noEmailResponse
     * @param noEmailResponse no response
     */
    public void setNoEmailResponse(final String noEmailResponse) {
        this.noEmailResponse = noEmailResponse;
    }

    /**
     * @return the velocityEngine
     */
    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    /**
     * @param velocityEngine the velocityEngine to set
     */
    public void setVelocityEngine(final VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
}
