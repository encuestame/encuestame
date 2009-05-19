package org.jp.core.mail;

import org.apache.log4j.Logger;
import org.jp.core.persistence.dao.CatLocationDaoImp;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

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
 * 
 * Id: MailServiceImpl.java Date: 19/05/2009 14:17:04
 * 
 * @author juanpicado package: org.jp.core.mail
 * @version 1.0
 */
public class MailServiceImpl implements MailService {

	private static Logger log = Logger.getLogger(CatLocationDaoImp.class);

	/** wrapper de Spring sobre javax.mail */
	private JavaMailSenderImpl mailSender;
	private SimpleMailMessage templateMessage;

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public SimpleMailMessage getTemplateMessage() {
		return templateMessage;
	}

	public void setTemplateMessage(SimpleMailMessage templateMessage) {
		this.templateMessage = templateMessage;
	}

	public void send(String to, String subject, String text) {
		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
		// mail del usuario que envía
		msg.setFrom("test@local.com");
		// mails de los destinatarios
		msg.setTo(to);
		// mail de los destinatarios CC (con copia)
		//msg.setCc();
		// mensaje
		msg.setText(text);
		// asunto
		msg.setSubject(subject);
		try {
			mailSender.send(msg);
		} catch (MailException ex) {
			log.info("Error al enviar mail : " + ex.getMessage());
			return;
		}
		log.info(" envio de mail exitoso !!");

	}

}
