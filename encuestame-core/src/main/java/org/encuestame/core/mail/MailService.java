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

import org.encuestame.core.service.IService;
import org.encuestame.utils.security.SignUpBean;
import org.encuestame.utils.web.UnitUserBean;
import org.springframework.mail.MailSendException;

/**
 * Interface to implement a mail service.
 * @author Picado, Juan juan@encuestame.org
 * @since May 05, 2009
 * @version $Id$
 */
public interface MailService extends IService {

    /**
     * Send a email.
     * @param to email to send
     * @param subject subject
     * @param text text of body
     *  @throws MailSendException exception
     */
    void send(final String to, final String subject, final String text)
    throws MailSendException;

    /**
     * Send Password Confirmation Email.
     * @param user
     */
    void sendPasswordConfirmationEmail(final SignUpBean user);

    /**
     * Send Renew Password Email.
     * @param unitUserBean {@link UnitUserBean}.
     */
    void sendRenewPasswordEmail(final UnitUserBean unitUserBean);

}
