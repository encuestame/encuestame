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

import org.springframework.mail.MailSendException;

/**
 * Interface to implement a mail service.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since May 05, 2009
 */
public interface MailService {

    /**
     * Send a email.
     * @param to email to send
     * @param subject subject
     * @param text text of body
     *  @throws MailSendException
     */
    public void send(final String to, final String subject, final String text)
    throws MailSendException;

}
