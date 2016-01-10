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
package org.encuestame.test.business.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.encuestame.core.service.imp.MailServiceOperations; 
import org.encuestame.persistence.domain.Email;
import org.encuestame.persistence.domain.EmailList;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.test.business.config.AbstractServiceBase;
import org.encuestame.utils.categories.test.InternetTest;
import org.encuestame.utils.mail.InvitationBean;
import org.encuestame.utils.mail.NotificationBean;
import org.encuestame.utils.security.SignUpBean;
import org.encuestame.utils.web.UserAccountBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test List Email Catalog.
 *
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since June 24, 2010
 * @version $Id: $
 */
@Category(InternetTest.class)
public class TestCatEmailLists extends AbstractServiceBase {

    private EmailList emailList;
    private Email emails;
    private Account user;
    private UserAccount secondary;

      
    @Autowired
    MailServiceOperations  mailService;

    @Before
    public void before() {
        this.user = createAccount();
        this.secondary = createUserAccount("paola", this.user);
        this.emailList = createDefaultListEmail(this.secondary.getAccount());
        createDefaultListEmail(this.user, "default");
        //this.emails = createDefaultEmails("encuestameteam@gmail.com", this.emailList);
        createDefaultEmails(getProperty("mail.test.email"), this.emailList);
        createDefaultEmails(getProperty("mail.test.email2"), this.emailList);
        createDefaultEmails(getProperty("mail.test.email3"), this.emailList);
    }


    @Test(timeout = 80000)
    public void testSendEmail() throws Exception{
        createDefaultEmails(getProperty("mail.test.email2"), this.emailList);
        createDefaultEmails(getProperty("mail.test.email3"), this.emailList);
        final List<Email> emailList = getCatEmailDao().findEmailsByListId(
                this.emailList.getIdList());
        for (Email emails : emailList) {
              assertNotNull(emails.getEmail());
              mailService.send(emails.getEmail().toString(), "Welcome Encuestame List", "Welcome Encuestame List");
            }
    }

    @Test(timeout = 80000)
    public void testSendInvitation() throws Exception{
          createDefaultEmails(getProperty("mail.test.email2"), this.emailList);
          final List<Email> emailList = getCatEmailDao().findEmailsByListId(
                  this.emailList.getIdList());
          for (Email emails : emailList) {
                assertNotNull(emails.getEmail());
                final InvitationBean invitation = new InvitationBean();
                invitation.setCode("1253");
                invitation.setEmail((emails.getEmail().toString()));
                invitation.setListName(this.emailList.getListName());
                invitation.setUrlInvitation(URLPOLL);
                mailService.sendEmailInvitation(invitation);
              }
      }

    /**
     * Test Send email notification
     * template:notification.vm
     * @throws Exception
     */
    @Test(timeout = 80000)
    public void testSendNotificationEmail() throws Exception{
          final List<Email> emailList = getCatEmailDao().findEmailsByListId(
                  this.emailList.getIdList());
          for (Email emails : emailList) {
                assertNotNull(emails.getEmail());
                final NotificationBean notification = new NotificationBean();
                notification.setCode("5678");
                notification.setEmail(emails.getEmail());
                notification.setMessage("My personal message");
                notification.setUrlUnsuscribe(URLPOLL);
                mailService.sendEmailNotification(notification);
              }
      }

    /**
     * Test Renew password email
     * template: renew-password.vm
     * @throws Exception
     */
    @Test(timeout = 80000)
    public void testSendRenewPasswordEmail() throws Exception{
          final List<Email> emailList = getCatEmailDao().findEmailsByListId(
                  this.emailList.getIdList());
          for (Email emails : emailList) {
                assertNotNull(emails.getEmail());
                final UserAccountBean unitUserBean = new UserAccountBean();
                unitUserBean.setUsername(this.secondary.getUsername());
                unitUserBean.setPassword("password");
                unitUserBean.setEmail(emails.getEmail());
                mailService.sendRenewPasswordEmail(unitUserBean);
              }
      }

    /**
     * Test Notification status account email.
     * template: notification-account.vm
     * @throws Exception
     */
    @Test(timeout = 80000)
    public void testSendNotificationStatusAccountEmail() throws Exception{
          final List<Email> emailList = getCatEmailDao().findEmailsByListId(
                  this.emailList.getIdList());
          for (Email emails : emailList) {
                assertNotNull(emails.getEmail());
                final SignUpBean signUpBean = new SignUpBean();
                signUpBean.setCaptcha("mycaptcha");
                signUpBean.setEmail(this.emails.toString());
                signUpBean.setFullName("mifullname");
                signUpBean.setPassword("mipassword");
                signUpBean.setUsername("myusername");
                mailService.sendNotificationStatusAccount(signUpBean, "Mi mensaje");
              }
      }

    /**
     * Test Confirm your account email.
     * template: confirm-your-account.vm
     * @throws Exception
     */
    @Test(timeout = 80000)
    public void testSendConfirmYourAccountEmail() throws Exception{
          final List<Email> emailList = getCatEmailDao().findEmailsByListId(
                  this.emailList.getIdList());
          for (Email emails : emailList) {
                assertNotNull(emails.getEmail());
                final SignUpBean signUpBean = new SignUpBean();
                signUpBean.setCaptcha("mycaptcha");
                signUpBean.setEmail(this.emails.toString());
                signUpBean.setFullName("mifullname");
                signUpBean.setPassword("mipassword");
                signUpBean.setUsername("myusername");
                mailService.sendConfirmYourAccountEmail(signUpBean, "myCode");
              }
      }

    /**
     * Test password confirmation
     * template: password-confirmation.vm
     * @throws Exception
     */
    @Test(timeout = 80000)
    public void testSendPasswordConfirmationEmail() throws Exception{
        final List<Email> emailList = getCatEmailDao().findEmailsByListId(
                this.emailList.getIdList());
        for (Email emails : emailList) {
              assertNotNull(emails.getEmail());
              final SignUpBean signUpBean = new SignUpBean();
              signUpBean.setCaptcha("mycaptcha");
              signUpBean.setEmail(emails.getEmail());
              signUpBean.setFullName("mifullname");
              signUpBean.setPassword("mipassword");
              signUpBean.setUsername("myusername");
              mailService.sendPasswordConfirmationEmail(signUpBean);
            }
    }

    /**
     * Test startup notification email.
     * template: startup.vm
     */
    @Test(timeout = 80000)
    public void testSendStartUpNotificationEmail(){
        final List<Email> emailList = getCatEmailDao().findEmailsByListId(
                this.emailList.getIdList());
        for (Email emails : emailList) {
              assertNotNull(emails.getEmail());
              final String message= " My personal message";
              mailService.sendStartUpNotification(message);
            }
    }

    /**
     * Test Find Emails By List Id.
     */
    @Test
    public void testFindEmailByListId() {
        final List<Email> emailList = getCatEmailDao().findEmailsByListId(
                this.emailList.getIdList());
        assertNotNull(emailList);
        assertEquals("Should be equals", 1, emailList.size());
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
        final List<Email> emails = getCatEmailDao().getEmailsByKeyword(
                keywordEmail, this.user.getUid());

        assertEquals("Should be equals", 1, emails.size());
    }

}
