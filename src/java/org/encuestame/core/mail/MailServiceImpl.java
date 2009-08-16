package org.encuestame.core.mail;

import org.apache.log4j.Logger;
import org.encuestame.core.persistence.dao.CatLocationDaoImp;
import org.encuestame.core.service.MasterService;
import org.springframework.mail.MailSendException;
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
 * @author juanpicado package: org.encuestame.core.mail
 * @version 1.0
 */
public class MailServiceImpl extends MasterService implements MailService {

	private static Logger log = Logger.getLogger(CatLocationDaoImp.class);

	private String noEmailResponse;
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

	/**
	 * send email
	 */
	public void send(String to, String subject, String text)
			throws MailSendException {
		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
		msg.setFrom(getNoEmailResponse());
		msg.setTo(to);
		// msg.setCc();
		msg.setText(text);
		msg.setSubject(subject);
		mailSender.send(msg);
		// log.info(getMessageSource("mail.succesful"));
	}

	/**
	 * send invitation
	 * 
	 * @param to
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public void sendInvitation(String to, String code) throws MailSendException {
		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
		msg.setFrom(getNoEmailResponse());
		msg.setTo(to);
		msg
				.setText("<h1>Invitation to Encuestame</h1><p>Please confirm this invitation <a>http://www.encuesta.me/cod/"
						+ code + "</a>");
		msg.setSubject(getMessageProperties("SubjectInvitation"));
		mailSender.send(msg);
	}

	/**
	 * delete notification
	 * @param to
	 * @param noti
	 */
	public void sendDeleteNotification(String to, String noti)throws MailSendException {
		log.info("sendDeleteNotification ->"+noti);
		log.info("sendDeleteNotification to->"+to);
		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
		log.info("sendDeleteNotification setFrom..");
		msg.setFrom(getNoEmailResponse());
		msg.setTo(to);
		msg.setText(noti);
		msg.setSubject(getMessageProperties("DeleteSubjectInvitation"));
		log.info("sendDeleteNotification sending..");
		mailSender.send(msg);
		log.info("sendDeleteNotification sendend..");
	}

	private String getNoEmailResponse() {
		return noEmailResponse;
	}

	public void setNoEmailResponse(String noEmailResponse) {
		this.noEmailResponse = noEmailResponse;
	}

}
