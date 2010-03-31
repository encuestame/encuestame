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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.service.IService;
import org.encuestame.core.service.Service;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Class Implements a Mail Service.
 * @author Picado, Juan juan@encuestame.org
 * @since May 05, 2009
 * @version $Id$
 */
public class MailServiceImpl extends Service implements MailService, IService {

    private Log log = LogFactory.getLog(this.getClass());

    /** email to  no-response. **/
    private String noEmailResponse;
    /** mail sender. **/
    private JavaMailSenderImpl mailSender;
    /** template of message. **/
    private SimpleMailMessage templateMessage;

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
}
