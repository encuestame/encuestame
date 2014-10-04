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
package org.encuestame.core.integration;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mail.MailHeaders;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

/**
 * Mail sender activator.
 * @author Picado, Juan juanATencuestame.org
 * @since
 */
public class MailSenderActivator {

    @Autowired
    private JavaMailSender mailSender;

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMessage(final Message<?> message) {
        //System.out.println("Message is:"+message.getPayload());
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
