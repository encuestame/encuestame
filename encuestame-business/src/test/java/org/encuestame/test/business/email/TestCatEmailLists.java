/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.test.business.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.encuestame.core.mail.MailService;
import org.encuestame.persistence.domain.EmailList;
import org.encuestame.persistence.domain.Emails;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.dao.IProject;
import org.encuestame.test.business.service.config.AbstractServiceBase;
import org.encuestame.utils.mail.InvitationBean;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test List Email Catalog.
 *
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since June 24, 2010
 * @version $Id: $
 */
public class TestCatEmailLists extends AbstractServiceBase {

    private EmailList emailList;
    private Emails emails;
    private Account user;
    private UserAccount secondary;

     /** {@link IProject} **/
    @Autowired
    MailService  mailService;

    @Before
    public void before() {
        this.user = createUser();
        this.secondary = createSecondaryUser("paola", this.user);
        this.emailList = createDefaultListEmail(this.secondary.getSecUser());
        createDefaultListEmail(this.user, "default");
        this.emails = createDefaultEmails("paola@jotadeveloper.com", this.emailList);
    }


    @Test(timeout = 80000)
    public void testSendEmail(){
        createDefaultEmails(getProperty("mail.test.email2"), this.emailList);
        createDefaultEmails(getProperty("mail.test.email3"), this.emailList);
        final List<Emails> catEmails = getCatEmailDao().findEmailsByListId(
                this.emailList.getIdList());
        for (Emails catemails : catEmails) {
              assertNotNull(catemails.getEmail());
              mailService.send(catemails.getEmail().toString(), "Welcome Encuestame List", "Welcome Encuestame List");
            }
    }

    @Test(timeout = 80000)
    public void testSendInvitation(){
          createDefaultEmails(getProperty("mail.test.email2"), this.emailList);
          final List<Emails> catEmails = getCatEmailDao().findEmailsByListId(
                  this.emailList.getIdList());
          for (Emails catemails : catEmails) {
                assertNotNull(catemails.getEmail());
                final InvitationBean invitation = new InvitationBean();
                invitation.setCode("1253");
                invitation.setEmail((catemails.getEmail().toString()));
                invitation.setListName(this.emailList.getListName());
                invitation.setUrlInvitation(URLPOLL);
                mailService.sendEmailInvitation(invitation);
              }
      }



    /**
     * Test Find Emails By List Id.
     */
    @Test
    public void testFindEmailByListId() {
        final List<Emails> catEmails = getCatEmailDao().findEmailsByListId(
                this.emailList.getIdList());
        assertNotNull(catEmails);
        assertEquals("Should be equals", 1, catEmails.size());
    }

    /**
     * Test Find All Email Lists.
     */
    @Test
    public void testFindAllEmailList() {
        final List<EmailList> catLists = getCatEmailDao()
                .findAllEmailList();
        assertEquals("Should be equals", 2, catLists.size());
    }

    /**
     * Test Find Email Lists by User.
     */
    @Test
    public void testFindListByUser() {
        final List<EmailList> listByUser = getCatEmailDao().findListbyUser(this.user.getUid());
        assertNotNull(listByUser);
        assertEquals("Should be equals", 2, listByUser.size());
    }

    /**
     * Test List Emails by Keyword.
     */
    @Test
    public void testListEmailsByKeyword() {
        final String keyword = "default";
        final List<EmailList> catLists = getCatEmailDao()
                .findAllEmailList();
        for (EmailList catListEmails : catLists) {
            assertNotNull(catListEmails.getListName());
        }
        assertEquals("Should be", 2 ,catLists.size());
        final List<EmailList> listEmails = getCatEmailDao()
                    .getListEmailsByKeyword(keyword, this.user.getUid());
        assertNotNull(listEmails);
        assertEquals("Should be equals", 2, listEmails.size());
    }

    /**
     * Test Emails by Keyword.
     */
    @Test
    public void testEmailsByKeyword() {
        final String keywordEmail = "jotadeveloper.com";
        final List<Emails> emails = getCatEmailDao().getEmailsByKeyword(
                keywordEmail, this.user.getUid());

        assertEquals("Should be equals", 1, emails.size());
    }

}
