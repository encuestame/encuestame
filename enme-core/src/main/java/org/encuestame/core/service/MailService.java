/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.service.imp.MailServiceOperations;
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
 * @author Picado, Juan juanATencuestame.org
 * @since May 05, 2009
 */

@SuppressWarnings("unchecked")
@Service(value = "mailService")
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
     *
     */
    private String logoUrl = EnMePlaceHolderConfigurer.getProperty("application.mail.logo.url");


    /**
     * Define the default locale on notifications emails.
     */
    private String defaultLocale = EnMePlaceHolderConfigurer.getProperty("mail.locale");


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
    @Deprecated
    public void send(
            final String to,
            final String subject,
            final String text)
            throws MailSendException {
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
            final String code) throws MailSendException {
        final SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setFrom(getNoEmailResponse());
        msg.setTo(to);
        msg.setText("<h1>Invitation to Encuestame</h1><p>Please confirm"
                        +" this invitation <a>http://www.encuesta.me/cod/"
                        + code + "</a>");
        msg.setSubject(buildSubject("test"));
        try {
        mailSender.send(msg);
        } catch (Exception e) {
            log.error("Error on send email "+e.getMessage());
        }
    }

    /**
     *
     * @return
     */
    private Locale buildCurrentLocale() {
        log.debug("Default locale for Mail " + this.defaultLocale);
        log.debug("Default locale for Mail " + new Locale(this.defaultLocale));
        return new Locale(this.defaultLocale);
    }


    /**
     * Build the greeting footer message.
     * @return
     */
    private void getGreetingMessage(Map<String, Object> model) {
        //mail.footer.greeting
        final StringBuffer buffer = new StringBuffer();
        final String[] properties = {EnMePlaceHolderConfigurer.getProperty("mail.footer.greeting")};
        buffer.append(getMessageProperties("mail.message.greeting", buildCurrentLocale(), properties));
        model.put("greeting", buffer.toString());
    }

    /**
     * Get the logo source.
     * @param model
     */
    private void getLogo(Map<String, Object> model) {
        if (EnMePlaceHolderConfigurer.getProperty("application.mail.source").equals("url")) {
            model.put("mailLogo", EnMePlaceHolderConfigurer.getProperty("application.mail.logo.url"));
        } else {
            model.put("mailLogo", EnMePlaceHolderConfigurer.getProperty("application.mail.logo.base64"));
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
        msg.setSubject(buildSubject(getMessageProperties("email.message.delete.invitation",
                buildCurrentLocale(),
                null)));
        mailSender.send(msg);
    }

    /**
     * Send Email Invitation.
     * @param invitation {@link InvitationBean}
     */
    public void sendEmailInvitation(final InvitationBean invitation) {
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
        public void prepare(MimeMessage mimeMessage) throws Exception {
              MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
              message.setTo(invitation.getEmail());
              message.setSubject(buildSubject(getMessageProperties("email.messages.new.confirmation")));
              message.setFrom(noEmailResponse);
              @SuppressWarnings("rawtypes")
              Map model = new HashMap();
              getLogo(model);

              model.put("invitation", invitation);
              model.put("domain", domainDefault);
			  model.put("username", "MyUsername");
              model.put("presentationMessage", getMessageProperties("mail.message.default.user.presentation", buildCurrentLocale(), null));
              model.put("subscribeMessage", getMessageProperties("mail.message.subscribe", buildCurrentLocale(), null));
              model.put("unSubscribeMessage", getMessageProperties("mail.message.unsubscribe", buildCurrentLocale(), null));
              getGreetingMessage(model);
              final String text = VelocityEngineUtils.mergeTemplateIntoString(
                 velocityEngine, "/org/encuestame/business/mail/templates/invitation.vm", "utf-8", model);
              message.setText(text, true);
           }
        };
        send(preparator);
     }

    /**
     * Send email notification.
     * @param notification {@link NotificationBean}
     * Will by replaced by queued email
     */
    @Deprecated
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

   /**
    * Sent a email after system startup.
    */
    public void sendStartUpNotification( final String startupMessage){
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
               MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
               message.setTo(EnMePlaceHolderConfigurer
                       .getProperty("setup.email.notification.webmaster"));
               message.setSubject(buildSubject(
                       getMessageProperties("mail.message.startup", buildCurrentLocale(), null)));
               message.setFrom(noEmailResponse);
               final Map model = new HashMap();
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
     * @deprecated will be removed on 1.147
     */
    @Deprecated
    public void sendPasswordConfirmationEmail(final SignUpBean user) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
           public void prepare(MimeMessage mimeMessage) throws Exception {
              MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
              message.setTo(user.getEmail());
              message.setSubject(buildSubject(getMessageProperties("email.password.remember.confirmation")));
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
     * @param user {@link SignUpBean}
     * @param inviteCode invite code string.
     */
    public void sendConfirmYourAccountEmail(
            final SignUpBean user,
            final String inviteCode) {
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
           public void prepare(MimeMessage mimeMessage) throws Exception {
              final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
              log.debug("confirm account to " + user.getEmail());
              message.setTo(user.getEmail());
              message.setSubject(buildSubject(
                      getMessageProperties("email.message.confirmation.message",
                      buildCurrentLocale(),
                      null)));
              message.setFrom(noEmailResponse);
              final Map<String, Object> model = new HashMap<String, Object>();
              if (user.getFullName() == null) {
                  // build
                  user.setFullName(getMessageProperties("mail.message.default.user.full.presentation",
                          buildCurrentLocale(),
                          null));
              } else {
                  // build anomymous the salute
                  final String _fullName = user.getFullName();
                  final StringBuffer salute = new StringBuffer(getMessageProperties("mail.message.default.user.presentation",
                          buildCurrentLocale(),
                          null));
                  salute.append(" ");
                  salute.append(_fullName);
                  user.setFullName(salute.toString());
              }
              getLogo(model);
              model.put("user", user);
              model.put("inviteCode", inviteCode);
              model.put("domain", domainDefault);
              model.put("successMessage", getMessageProperties("mail.message.registration.success", buildCurrentLocale(), null));
              model.put("confirmMessage", getMessageProperties("mail.message.confirm.please", buildCurrentLocale(), null));
              model.put("confirmMessageSubfooter", getMessageProperties("mail.message.confirm.subfooter", buildCurrentLocale(), null));
              getGreetingMessage(model);
              // create the template
              final String text = VelocityEngineUtils.mergeTemplateIntoString(
                              velocityEngine, "/org/encuestame/business/mail/templates/confirm-your-account.vm",
                              model);
              message.setText(text, Boolean.TRUE);
           }
        };
        send(preparator);
     }

    /**
     * Send Renew Password Email.
     * @param unitUserBean {@link UserAccountBean}.
     */
    public void sendRenewPasswordEmail(final UserAccountBean unitUserBean) {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", unitUserBean);
        getLogo(model);
        model.put("domain", domainDefault);
        model.put("passwordNewTitle", getMessageProperties("mail.message.new.title", buildCurrentLocale(), null));
        model.put("passwordRequestMessage", getMessageProperties("mail.message.new.password.request", buildCurrentLocale(), null));
        model.put("passwordNewConfirmed", getMessageProperties("mail.message.new.confirmed", buildCurrentLocale(), null));
        getGreetingMessage(model);
        this.sendMimeEmail(model, unitUserBean.getEmail(),
                getMessageProperties("mail.message.new.password", buildCurrentLocale(), null),
                this.noEmailResponse,
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
              getLogo(model);
              model.put("user", user);
              final String[] properties = {EnMePlaceHolderConfigurer.getProperty("mail.message.app.name")};
              model.put("presentationMessage", getMessageProperties("mail.message.default.user.presentation", buildCurrentLocale(), null));
              model.put("userActivateMessage", getMessageProperties("mail.message.user.activate", buildCurrentLocale(), properties));

              getGreetingMessage(model);
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
        builder.append(" : ");
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
