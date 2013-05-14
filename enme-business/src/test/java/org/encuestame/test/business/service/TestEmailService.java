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
package org.encuestame.test.business.service;

import java.util.UUID;

import org.encuestame.core.service.imp.MailServiceOperations;
import org.encuestame.test.business.service.config.AbstractServiceBase;
import org.encuestame.utils.categories.test.InternetTest;
import org.encuestame.utils.security.SignUpBean;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Email Services Test.
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 12, 2010 10:02:41 AM
 */
@Category(InternetTest.class)
public class TestEmailService extends AbstractServiceBase {

    /**
     * Service email.
     */
    @Autowired
    private MailServiceOperations serviceMail;

    /**
     * Test Send Email.
     * @throws Exception
     */
    @Test(timeout = 30000)
    public void testSendEmail() throws Exception{
        this.serviceMail.sendInvitation(getProperty("mail.test.email"), "12355");
    }

    @Test(timeout = 30000)
    public void testSendConfirmYourAccountEmail() throws Exception{
        final String inviteCode = UUID.randomUUID().toString();
        final String captcha = "CaPtCHa";
        final String email = "dianmorales@gmail.com";
        final String fullName = "";
        final String password = "Diana Paola";
        final String username = "dianmorales";
        final SignUpBean sub = new SignUpBean();
        sub.setCaptcha(captcha);
        sub.setEmail(email);
        sub.setFullName(fullName);
        sub.setPassword(password);
        sub.setUsername(username);
        this.serviceMail.sendConfirmYourAccountEmail(sub, inviteCode);

    }

    /**
     * @return the serviceMail
     */
    public MailServiceOperations getServiceMail() {
        return serviceMail;
    }

    /**
     * @param serviceMail the serviceMail to set
     */
    public void setServiceMail(final MailServiceOperations serviceMail) {
        this.serviceMail = serviceMail;
    }
}
