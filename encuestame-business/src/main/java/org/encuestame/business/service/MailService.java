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
package org.encuestame.business.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.encuestame.business.service.imp.MailServiceOperations;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.utils.mail.InvitationBean;
import org.encuestame.utils.mail.NotificationBean;
import org.encuestame.utils.security.SignUpBean;
import org.encuestame.utils.web.UserAccountBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Class Implements a Mail Service.
 * @author Picado, Juan juan@encuestame.org
 * @since May 05, 2009
 */

@SuppressWarnings("unchecked")
@Service
public class MailService extends AbstractBaseService implements MailServiceOperations {

    private Log log = LogFactory.getLog(this.getClass());

    /** email to  no-response. **/
    @Value("${mail.noresponse}") private String noEmailResponse;
    /** mail sender. **/
    @Autowired
    private JavaMailSenderImpl mailSender;
    /** template of message. **/
    @Autowired
    private SimpleMailMessage templateMessage;
    /** VelocityEngine. **/
    @Autowired
    private VelocityEngine velocityEngine;
    /**
     *
     */
    private String domainDefault = EnMePlaceHolderConfigurer.getProperty("application.domain");


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

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.mail.MailService#send(java.lang.String, java.lang.String, java.lang.String)
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
        msg.setSubject(buildSubject(subject));
        mailSender.send(msg);
        //log.debug("mail.succesful");
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
        msg.setSubject(buildSubject("test"));
        try{
            //log.info("Sending email");
            //log.debug("Sending host "+mailSender.getHost());
            //log.debug("Sending password "+mailSender.getPassword());
            //log.debug("Sending username "+mailSender.getUsername());
            //log.debug("Sending enconding "+mailSender.getDefaultEncoding());
            //log.debug("Sending protocol "+mailSender.getProtocol());
            //log.debug("Sending port "+mailSender.getPort());
            //log.debug("Sending port auth "+mailSender.getJavaMailProperties().getProperty("mail.smtp.auth"));
            //log.debug("Sending port starttls "+mailSender.getJavaMailProperties().getProperty("mail.smtp.starttls.enable"));
            //log.debug("Sending port required "+mailSender.getJavaMailProperties().getProperty("mail.smtp.starttls.required"));
            mailSender.send(msg);
            //log.info("Sended email");
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
            final String body)throws MailSendException {
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setFrom(getNoEmailResponse());
        msg.setTo(to);
        msg.setText(body);
        msg.setSubject(buildSubject(getMessageProperties("DeleteSubjectInvitation")));
        mailSender.send(msg);
    }

    /**
     * Send Email Invitation.
     * @param user
     */
    public void sendEmailInvitation(final InvitationBean invitation) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
        public void prepare(MimeMessage mimeMessage) throws Exception {
              MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
              message.setTo(invitation.getEmail());
              message.setSubject(buildSubject("New Password Confirmation"));
              message.setFrom(noEmailResponse);
              @SuppressWarnings("rawtypes")
              Map model = new HashMap();
              model.put("invitation", invitation);
              model.put("domain", domainDefault);
              String text = VelocityEngineUtils.mergeTemplateIntoString(
                 velocityEngine, "/org/encuestame/business/mail/templates/invitation.vm", model);
              message.setText(text, true);
           }
        };
        send(preparator);
     }

    /**
     * Send Email Invitation.
     * @param user
     */
    public void sendEmailNotification(final NotificationBean notification) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
           public void prepare(MimeMessage mimeMessage) throws Exception {
              MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
              message.setTo(notification.getEmail());
              message.setSubject(buildSubject("New Password Confirmation"));
              message.setFrom(noEmailResponse);
              Map model = new HashMap();
              model.put("notification", notification);
              String text = VelocityEngineUtils.mergeTemplateIntoString(
                 velocityEngine, "/org/encuestame/business/mail/templates/notification.vm", model);
              message.setText(text, true);
           }
        };
        send(preparator);
     }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.mail.MailService#sendStartUpNotification(java.lang.String)
     */
    public void sendStartUpNotification(
            final String startupMessage){
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
               MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
               message.setTo(EnMePlaceHolderConfigurer
                       .getProperty("setup.email.notification.webmaster"));
               message.setSubject(buildSubject("Start Up Notification"));
               message.setFrom(noEmailResponse);
               Map model = new HashMap();
               model.put("message", startupMessage);
               String text = VelocityEngineUtils.mergeTemplateIntoString(
                  velocityEngine, "/org/encuestame/business/mail/templates/startup.vm", model);
               message.setText(text, true);
            }
         };
         send(preparator);
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
              message.setSubject(buildSubject(getMessageProperties("emailPasswordConfirmation")));
              message.setFrom(noEmailResponse);
              Map model = new HashMap();
              model.put("user", user);
              String text = VelocityEngineUtils.mergeTemplateIntoString(
                 velocityEngine, "/org/encuestame/business/mail/templates/password-confirmation.vm", model);
              message.setText(text, true);
           }
        };
        send(preparator);
     }

    /**
     * Sent email to confirm user account by email.
     * @param user
     */
    public void sendConfirmYourAccountEmail(final SignUpBean user, final String inviteCode) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
           public void prepare(MimeMessage mimeMessage) throws Exception {
              MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
              message.setTo(user.getEmail());
              message.setSubject(buildSubject("Confirm Your Account"));
              message.setFrom(noEmailResponse);
              final Map<String, Object> model = new HashMap<String, Object>();
              model.put("user", user);
              model.put("inviteCode", inviteCode);
              model.put("domain", domainDefault);
              String text = VelocityEngineUtils.mergeTemplateIntoString(
                              velocityEngine, "/org/encuestame/business/mail/templates/confirm-your-account.vm", model);
              message.setText(text, true);
           }
        };
        send(preparator);
     }

    /**
     * Send Renew Password Email.
     * @param unitUserBean {@link UserAccountBean}.
     */
    public void sendRenewPasswordEmail(final UserAccountBean unitUserBean){
        final Map<String, UserAccountBean> model = new HashMap<String, UserAccountBean>();
        model.put("user", unitUserBean);
        this.sendMimeEmail(model, unitUserBean.getEmail(), "Your New Password", this.noEmailResponse,
                           "/org/encuestame/business/mail/templates/renew-password.vm");
    }

    /**
     * Send notification status account.
     * @param user
     * @param message
     */
    public void sendNotificationStatusAccount(final SignUpBean user, final String message) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
           public void prepare(MimeMessage mimeMessage) throws Exception {
              MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
              message.setTo(user.getEmail());
              message.setSubject(buildSubject("Notificaction status account"));
              message.setFrom(noEmailResponse);
              Map model = new HashMap();
              model.put("user", user);
              String text = VelocityEngineUtils.mergeTemplateIntoString(
                 velocityEngine, "/org/encuestame/business/mail/templates/notification-account.vm", model);
              message.setText(text, true);
           }
        };
        send(preparator);
     }
    /**
     * Send Mime Email.
     * @param model
     * @param email
     * @param subject
     * @param from
     * @param template
     */
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
              message.setSubject(buildSubject(subject));
              message.setFrom(from);
              final String text = VelocityEngineUtils.mergeTemplateIntoString(
                 velocityEngine, template, model);
              message.setText(text, true);
           }
        };
        this.send(preparator);
     }

    /**
     *
     * @param subject
     */
    private String buildSubject(final String subject){
        final StringBuilder builder = new StringBuilder();
        builder.append(EnMePlaceHolderConfigurer.getProperty("application.name"));
        builder.append(": ");
        builder.append(subject);
        return builder.toString();
    }

    /**
     * Send Mime Message.
     * @param preparator
     * @throws MailSendException
     */
    public void send(final MimeMessagePreparator preparator) throws MailSendException {
        this.mailSender.send(preparator);
        //log.debug("mail.succesful");
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
