/**
 * encuestame: system online surveys Copyright (C) 2009 encuestame Development
 * Team
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of version 3 of the GNU General Public License as published by the
 * Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.encuestame.core.mail;

import org.encuestame.core.service.Service;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Class Implements a Mail Service.
 * @author Picado, Juan juan@encuestame.org
 * @since May 05, 2009
 */
public class MailServiceImpl extends Service implements MailService {

    /** email to  no-response. **/
    private String noEmailResponse;
    /** mail sender. **/
    private JavaMailSenderImpl mailSender;
    /** template of message. **/
    private SimpleMailMessage templateMessage;

    /**
     * setter mail sender.
     * @param mailSender
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
     * @param templateMessage
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
     * @throws Exception mail exception.
     */
    public void sendInvitation(
            final String to,
            final String code) throws MailSendException
    {
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setFrom(getNoEmailResponse());
        msg.setTo(to);
        msg
                .setText("<h1>Invitation to Encuestame</h1><p>Please confirm"
                        +" this invitation <a>http://www.encuesta.me/cod/"
                        + code + "</a>");
        msg.setSubject(getMessageProperties("SubjectInvitation"));
        mailSender.send(msg);
    }

    /**
     * Delete notification.
     * @param to mail to send
     * @param body body of message
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
     *getter of no email response.
     * @return noEmailResponse
     */
    private String getNoEmailResponse() {
        return noEmailResponse;
    }

    /**
     * setter of noEmailResponse
     * @param noEmailResponse
     */
    public void setNoEmailResponse(final String noEmailResponse) {
        this.noEmailResponse = noEmailResponse;
    }
}
