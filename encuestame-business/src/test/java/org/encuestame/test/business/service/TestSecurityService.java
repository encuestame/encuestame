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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.encuestame.core.service.SecurityService;
import org.encuestame.core.service.imp.SecurityOperations;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.Group;
import org.encuestame.persistence.domain.security.Permission;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.test.business.security.AbstractSpringSecurityContext;
import org.encuestame.utils.enums.EnMePermission;
import org.encuestame.utils.security.SignUpBean;
import org.encuestame.utils.social.SocialProvider;
import org.encuestame.utils.web.UnitGroupBean;
import org.encuestame.utils.web.UnitPermission;
import org.encuestame.utils.web.UserAccountBean;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;

/**
 * Test Security Service.
 * @author Picado, Juan juanATencuestame.org
 * @since 08/11/2009 11:35:01
 * @version $Id$
 */
public class TestSecurityService extends AbstractSpringSecurityContext{

    /** {@link SecurityService}. **/
    @Autowired
    private SecurityOperations securityService;

    /** User Primary. **/
    private Account userPrimary;

    /** User Secondary. **/
    private UserAccount secUserSecondary;

    /** {@link Permission}. **/
    private Permission permission;

    final String inviteCode = "250fc8e1";
    /**
     * Before.
     */
    @Before
    public void initService(){
        this.userPrimary = createAccount();
        this.secUserSecondary = createUserAccount("default", this.userPrimary);
        this.secUserSecondary.setInviteCode(inviteCode);
        final Group group = createGroups("admin");
        final Group group2 = createGroups("editors");
      //  this.secUserSecondary.getSecGroups().add(group);
       // this.secUserSecondary.getSecGroups().add(group2);
        this.permission = createPermission(EnMePermission.ENCUESTAME_EDITOR.name());
        createPermission(EnMePermission.ENCUESTAME_OWNER.name());
        createPermission(EnMePermission.ENCUESTAME_PUBLISHER.name());
        createPermission(EnMePermission.ENCUESTAME_ADMIN.name());
        this.secUserSecondary.getSecUserPermissions().add(this.permission);
        getGroup().saveOrUpdate(this.secUserSecondary);
    }

    /**
     * Test findUserByUserName.
     */
    @Test
    public void testfindUserByUserName(){
        final UserAccount secondary = this.securityService.findUserByUserName(this.secUserSecondary.getUsername());
        assertEquals(this.secUserSecondary.getUid(), secondary.getUid());
        assertEquals(this.secUserSecondary.getPassword(), secondary.getPassword());
        assertEquals(this.secUserSecondary.getCompleteName(), secondary.getCompleteName());
    }

    /**
     * Test findUserByEmail.
     */
    @Test
    public void testfindUserByEmail(){
        final UserAccountBean secondary = this.securityService.findUserByEmail("fake@email.com");
        assertNull(secondary);
        final UserAccountBean secondary2 = this.securityService.findUserByEmail(this.secUserSecondary.getUserEmail());
        assertEquals(this.secUserSecondary.getUid(), secondary2.getId());
        assertEquals(this.secUserSecondary.getUsername(), secondary2.getUsername());
    }

    /**
     * Test loadGroups.
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testloadGroups() throws EnMeNoResultsFoundException{
        createGroups("admin", this.userPrimary);
        createGroups("user", this.userPrimary);
        final List<UnitGroupBean> groups = this.securityService.loadGroups(this.secUserSecondary.getUsername());
        assertEquals(groups.size(), 2);
    }

    /**
     * Load Groups Exception.
     * @throws EnMeNoResultsFoundException
     */
    @Test(expected = EnMeNoResultsFoundException.class)
    public void testloadGroupsException() throws EnMeNoResultsFoundException{
         this.securityService.loadGroups("xxxxxx");
    }

    /**
     * test updateTwitterAccount.
     */
    @Test
    public void testupdateTwitterAccount(){
//        SocialAccount account = createDefaultSettedTwitterAccount(this.userPrimary);
//        final SocialAccountBean bean = ConvertDomainBean.convertSocialAccountToBean(account);
//        this.securityService.updateTwitterAccount(bean, "12345", false);
//        account = getAccountDao().getTwitterAccount(account.getId());
//        //assertEquals(account.getTwitterPassword(), "12345");
//        assertEquals(account.getVerfied(), false);
//        //with id null.
//        this.securityService.updateTwitterAccount(new SocialAccountBean(), "12345", false);
//        bean.setAccountId(1234L);
//        this.securityService.updateTwitterAccount(bean, "12345", false);
    }

    /**
     * test updateSecretTwitterCredentials.
     * @throws EnMeExpcetion
     */
    @Test
    public void testupdateSecretTwitterCredentials() throws EnMeExpcetion{
//         SocialAccount account = createDefaultSettedTwitterAccount(this.userPrimary);
//         final SocialAccountBean bean = ConvertDomainBean.convertSocialAccountToBean(account);
//         bean.setKey(getProperty("twitter.test.token"));
//         bean.setSecret(getProperty("twitter.test.tokenSecret"));
//         this.securityService.updateSecretTwitterCredentials(bean, this.secUserSecondary.getUsername());
//         //pin null
//         bean.setPin(null);
//         this.securityService.updateSecretTwitterCredentials(bean, this.secUserSecondary.getUsername());
//         //pinn empty
//         bean.setPin("");
//         this.securityService.updateSecretTwitterCredentials(bean, this.secUserSecondary.getUsername());
//         //fake data
//         account.setToken("fake key");
//         account.setSecretToken("fake secret");
//         getAccountDao().saveOrUpdate(account);
//         this.securityService.updateSecretTwitterCredentials(bean, this.secUserSecondary.getUsername());
    }

    /**
     * test updateOAuthTokenSocialAccount.
     * @throws EnMeExpcetion
     */
    @Test
    @Ignore
    public void testupdateOAuthTokenSocialAccount() throws EnMeExpcetion{
        SocialAccount account = createDefaultSettedSocialAccount(this.secUserSecondary);
        //this.securityService.updateOAuthTokenSocialAccount(account.getId(), "12345", "fakeTokenSecret", this.secUserSecondary.getUsername());
        account = getAccountDao().getSocialAccountById(account.getId());
        assertEquals(account.getSecretToken(), "fakeTokenSecret");
    }

    /**
     * test updateOAuthTokenSocialAccount with exception.
     * @throws EnMeExpcetion
     */
    @Test(expected = EnMeExpcetion.class)
    @Ignore
    public void testupdateOAuthTokenSocialAccountException() throws EnMeExpcetion{
        //this.securityService.updateOAuthTokenSocialAccount(12345L, "12345", "fakeTokenSecret", this.secUserSecondary.getUsername());
    }

    /**
     * test getTwitterAccount.
     */
    @Test
    public void testgetTwitterAccount(){
//        SocialAccount account = createDefaultSettedTwitterAccount(this.userPrimary);
//        final SocialAccountBean accountBean = this.securityService.getTwitterAccount(account.getId());
//        assertEquals(account.getId(), accountBean.getAccountId());
    }

    /**
     * test deleteUser.
     * @throws EnMeNoResultsFoundException
     */
    @Test(timeout = 30000)
    public void testdeleteUser() throws EnMeNoResultsFoundException{
        final UserAccount tempUser = createUserAccount("second user", this.userPrimary);
        final Long id = tempUser.getUid();
        this.securityService.deleteUser(ConvertDomainBean.convertSecondaryUserToUserBean(tempUser));
        final UserAccount tempUser2 = createUserAccount("second user", getProperty("mail.test.email"), this.userPrimary);
        this.securityService.deleteUser(ConvertDomainBean.convertSecondaryUserToUserBean(tempUser2));
        assertNull(getAccountDao().getUserAccountById(id));
    }

    /**
     * test deleteUser.
     * @throws EnMeNoResultsFoundException
     */
    @Test(expected = EnMeNoResultsFoundException.class)
    public void testdeleteUserNotFound() throws EnMeNoResultsFoundException{
        this.securityService.deleteUser(ConvertDomainBean.convertSecondaryUserToUserBean(new UserAccount()));
    }

    /**
     * test addNewTwitterAccount.
     * @throws EnMeNoResultsFoundException
     */
    @Test
    @Ignore
    public void testaddNewTwitterAccount() throws EnMeNoResultsFoundException{
        //this.securityService.addNewTwitterAccount("encuestameTest", this.secUserSecondary.getUsername());
        assertEquals(getAccountDao().getSocialAccountByAccount(this.userPrimary, SocialProvider.SocialProvider.TWITTER).size(), 1);
    }

    /**
     * Generate Hash Code Invitation.
     */
    @Test
    public void testGenerateHashCodeInvitation(){
        assertNotNull(securityService.generateHashCodeInvitation());
    }

    /**
     * Test Load All Permissions.
     */
    @Test
    public void testLoadAllListPermission(){
         assertEquals("Should be equals", 4, securityService.loadAllListPermission().size());
    }

    /**
     * @throws Exception
     */
    @Test
    public void testLoadListUsers() throws Exception{
        addGroupUser(super.createUserAccount("user 1",this.userPrimary),super.createGroups("editor"));
        addGroupUser(super.createUserAccount("user 2",this.userPrimary),super.createGroups("admon"));
   //     assertEquals("Should be equals", 3, securityService.loadListUsers("user 1").size());
    }

    /**
     * Test User By Username.
     * @throws EnMeExpcetion EnMeExpcetion
     */
    @Test
    public void testSearchUserByUsername() throws EnMeExpcetion{
      final UserAccount userDomain = createUserAccount("user 1",this.userPrimary);
      createUserAccount("user 2",this.userPrimary);
      final UserAccountBean userBean = securityService.searchUserByUsername(userDomain.getUsername());
      assertEquals("Should be equals",userDomain.getUsername(),userBean.getUsername());
    }

    /**
     * @throws EnMeExpcetion EnMeExpcetion
     */
    @Test
    public void testSearchUserByUsernameNotFound() throws EnMeExpcetion{
        assertNull(securityService.searchUserByUsername("user test"));
    }

    /**
     * Test Default User Permission.
     */
    @Test
    public void testDefaulUserPermission(){
        final String defaultPermission = securityService.getDefaultUserPermission();
        assertEquals("Should be","ENCUESTAME_USER".toString(), defaultPermission.toString());
    }


    /**
     *Test Delete Group.
     */
    @Test
    public void testDeleteGroup(){
        final Group groupDomain = createGroups("admin");
        final Long idGroup = groupDomain.getGroupId();
        final UnitGroupBean group = ConvertDomainBean.convertGroupDomainToBean(groupDomain);
        securityService.deleteGroup(group.getId());
        final Group groupRetrieve = getGroup().getGroupById(idGroup);
        assertNull(groupRetrieve);
    }

    /**
     * Setter.
     * @param securityService the securityService to set
     */
    public final void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    /**
     *Test delete Group.
     */
    @Test
    public void testdeleteGroup(){
      final Group groupDomain = createGroups("admin");
      final Long idGroup = groupDomain.getGroupId();
      final UnitGroupBean group = ConvertDomainBean.convertGroupDomainToBean(groupDomain);
      securityService.deleteGroup(group.getId());
      final Group groupRetrieve = getGroup().getGroupById(idGroup);
      assertNull(groupRetrieve);

    }

    /**
    *Test delete User.
     * @throws EnMeExpcetion exception
     */
    @Test
    public void testDeleteUser() throws EnMeExpcetion{
     final UserAccount secUsers = createUserAccount("administrator",this.userPrimary);
     final Long idUser = secUsers.getUid();
     //final String username = secUsers.getUsername();
     final UserAccountBean user = ConvertDomainBean.convertSecondaryUserToUserBean(secUsers);
     securityService.deleteUser(user);
     final Account userRetrieve = getAccountDao().getUserById(idUser);
     assertNull(userRetrieve);
    }



    /**
     * Test Update Group.
     * @throws EnMeExpcetion
     */
    @Test
    public void testUpdateGroup() throws EnMeExpcetion{
      Group secgroups = createGroups("guests");
      Long idGroupUpdate = secgroups.getGroupId();
      UnitGroupBean groupBean = ConvertDomainBean.convertGroupDomainToBean(secgroups);
      groupBean.setGroupName("editors");
      securityService.updateGroup(groupBean);
      Group groupUpdateRetrieve =  getGroup().getGroupById(idGroupUpdate);
      assertEquals("Should be","editors",groupUpdateRetrieve.getGroupName());

    }

    /**
     *Test Update User.
     * @throws EnMeExpcetion exception
     **/
    @Test
    public void testUpdateUser() throws EnMeExpcetion{
      final UserAccount secUsers = createUserAccount("developer",this.userPrimary);
      final Long idUser = secUsers.getUid();
      final UserAccountBean userBean = ConvertDomainBean.convertSecondaryUserToUserBean(secUsers);
      userBean.setName("editor");
      securityService.updateUser(userBean);
      final UserAccount userUpdateRetrieve = getAccountDao().getUserAccountById(idUser);
      assertEquals("shouldbe", "editor", userUpdateRetrieve.getCompleteName());
    }

    /**
     * Test Create Permission.
     */
    @Test
    public void testCreatePermission(){
      final Permission secPerm = createPermission("writer");
      final UnitPermission permissionBean = ConvertDomainBean.convertPermissionToBean(secPerm);
      securityService.createPermission(permissionBean);
      final Permission permissionRetrieve = getPermissionDaoImp().getPermissionById(secPerm.getIdPermission());
      assertNotNull(permissionRetrieve);
      assertEquals("should be","writer", permissionRetrieve.getPermissionDescription());


    }

    /**
     * Test Renew Password.
     * @throws EnMeExpcetion exception
     */
    @Test
    public void testRenewPassword() throws EnMeExpcetion{
      final UserAccount secUser = createUserAccount("paola",this.userPrimary);
      final String passwd = secUser.getPassword();
      final UserAccountBean userPass = ConvertDomainBean.convertSecondaryUserToUserBean(secUser);
      final String retrievePassword = securityService.renewPassword(userPass, passwd);
      assertEquals("should be equals", passwd, retrievePassword);
    }

    /**
     * Test Renew Password without Pass.
     * @throws EnMeExpcetion  EnMeExpcetion
     */
   @Test
   @ExpectedException(EnMeExpcetion.class)
   public void testRenewPasswordwithoutPass()throws EnMeExpcetion{
      final UserAccount secUser = createUserAccount("diana",this.userPrimary);
      UserAccountBean userPassBean = ConvertDomainBean.convertSecondaryUserToUserBean(secUser);
      final String retrievePassword = securityService.renewPassword(userPassBean,null);
      assertEquals("should be equals", null, retrievePassword);
    }

    /**
     * Test Create User without Email.
     * @throws EnMeExpcetion EnMeExpcetion
     **/
      @Test
      @ExpectedException(EnMeExpcetion.class)
       public void testCreateUserwithoutEmail() throws EnMeExpcetion{
        final UserAccountBean userCreateBean = new UserAccountBean();
        userCreateBean.setEmail(null);
        userCreateBean.setUsername("diana");
        securityService.createUser(userCreateBean, this.secUserSecondary.getUsername());
       }

      /**
       * Test Create User without Username.
       * @throws EnMeExpcetion EnMeExpcetion
       */
      @Test
      @ExpectedException(EnMeExpcetion.class)
       public void testCreateUserwithoutUsername() throws EnMeExpcetion{
        final UserAccountBean userCreateBean = new UserAccountBean();
        userCreateBean.setEmail("paola@users.com");
        userCreateBean.setUsername(null);
        securityService.createUser(userCreateBean, this.secUserSecondary.getUsername());
       }

      /**
       * Create default permissions.
       */
      private void createDefaultPermission(){
          createPermission("ENCUESTAME_USER");
      }

      /**
       * Test Create User with Username.
       * @throws EnMeExpcetion EnMeExpcetion
       */
      @Test
       public void testCreateUserwithUsernameEmail() throws EnMeExpcetion{
        createDefaultPermission();
        final UserAccountBean userCreateBean = new UserAccountBean();
        userCreateBean.setEmail("demo3@demo.org");
        userCreateBean.setUsername("demo3");
        userCreateBean.setStatus(true);
        userCreateBean.setName("demo3");
        userCreateBean.setPassword(null);
        userCreateBean.setDateNew(new Date());
        userCreateBean.setPrimaryUserId(createAccount().getUid());
        securityService.createUser(userCreateBean, this.secUserSecondary.getUsername());
        //TODO: need assert
        final UserAccount user = getAccountDao().getUserByUsername(userCreateBean.getUsername());
        assertNotNull("should be equals", user);
        assertEquals("should be equals", 1, user.getSecUserPermissions().size());
       }

      /**
       * Test Create User without Password.
       * @throws EnMeExpcetion EnMeExpcetion
       */
      @Test
       public void testCreateUserwithoutPassword() throws EnMeExpcetion{
        createDefaultPermission();
        UserAccount secCreateUser = new UserAccount();
        UserAccountBean userCreateBean = ConvertDomainBean.convertSecondaryUserToUserBean(secCreateUser);
        userCreateBean.setPassword(null);
        userCreateBean.setEmail("demo2@demo.org");
        userCreateBean.setUsername("demo2");
        userCreateBean.setStatus(true);
        userCreateBean.setName("Diana Paola");
        userCreateBean.setPrimaryUserId(createAccount().getUid());
        userCreateBean.setDateNew(new Date());
        securityService.createUser(userCreateBean, this.secUserSecondary.getUsername());
        //TODO: need assert
        final UserAccount user = getAccountDao().getUserByUsername(userCreateBean.getUsername());
        assertNotNull("should be equals", user);
        assertEquals("should be equals", 1, user.getSecUserPermissions().size());
       }

      /**
       * Test Create User without Password.
       * @throws EnMeExpcetion EnMeExpcetion
       */
      @Test
       public void testCreateUserwithPassword() throws EnMeExpcetion{
        createDefaultPermission();
        UserAccount secCreateUser = new UserAccount();
        UserAccountBean userCreateBean = ConvertDomainBean.convertSecondaryUserToUserBean(secCreateUser);
        userCreateBean.setPassword("12345");
        userCreateBean.setEmail("demo1@demo.org");
        userCreateBean.setUsername("demo1");
        userCreateBean.setStatus(true);
        userCreateBean.setName("Diana Paola");
        userCreateBean.setDateNew(new Date());
        userCreateBean.setPrimaryUserId(createAccount().getUid());
        securityService.createUser(userCreateBean, this.secUserSecondary.getUsername());
        //TODO: need assert
        final UserAccount user = getAccountDao().getUserByUsername(userCreateBean.getUsername());
        assertNotNull("should be equals", user);
        assertEquals("should be equals", 1, user.getSecUserPermissions().size());
       }


      /**
       * Test Create Group.
     * @throws EnMeNoResultsFoundException
       */
      @Test
      public void testCreateGroup() throws EnMeNoResultsFoundException{
        Group secCreateGroup = new Group();
        secCreateGroup.setGroupId(12L);
        secCreateGroup.setGroupDescriptionInfo("1111");
        secCreateGroup.setGroupName("vvvv");
        secCreateGroup.setIdState(1L);
        final UnitGroupBean createGroupBean = ConvertDomainBean.convertGroupDomainToBean(secCreateGroup);
        securityService.createGroup(createGroupBean, this.secUserSecondary.getUsername());
        //TODO: need assert
      }

      /**
       * Test Assing Permission without Id an Username.
       * @throws EnMeExpcetion  EnMeExpcetion
       */
      @Test
      public void testAssignPermissionwithIdUsername() throws EnMeExpcetion{
        final UserAccount secUser = createUserAccount("demo",this.userPrimary);
        final UserAccountBean userPermissionBean = ConvertDomainBean.convertSecondaryUserToUserBean(secUser);
        final UnitPermission permissionBean = ConvertDomainBean.convertPermissionToBean(this.permission);
        userPermissionBean.setId(userPermissionBean.getId());
        userPermissionBean.setUsername("demo");
        securityService.assignPermission(userPermissionBean, permissionBean);
        assertEquals("should be equals", 1, secUser.getSecUserPermissions().size());
      }

        /**
         * Test Assign Permission without Username.
         * @throws EnMeExpcetion EnMeExpcetion
         */
        @Test
        @ExpectedException(EnMeExpcetion.class)
        public void testAssignPermissionwithoutIdUsername() throws EnMeExpcetion{
          final UserAccountBean userPermissionBean = new UserAccountBean();
          final UnitPermission permissionBean = ConvertDomainBean.convertPermissionToBean(this.permission);
          //modify id user id.
          userPermissionBean.setId(1L);
          securityService.assignPermission(userPermissionBean, permissionBean);
      }


        /**
         * Test Assign Permission with Permission.
         * @throws EnMeExpcetion EnMeExpcetion
         */
        @Test
        @ExpectedException(EnMeExpcetion.class)
        public void testAssignPermissionwithPermission() throws EnMeExpcetion{
            final UserAccount secUser = createUserAccount("juanpicado2",this.userPrimary);
            final UserAccountBean userPermissionBean = ConvertDomainBean.convertSecondaryUserToUserBean(secUser);
            final UnitPermission permissionBean = new UnitPermission();
            //modify id permission.
            permissionBean.setId(1L);
            permissionBean.setPermission("auditor");
            securityService.assignPermission(userPermissionBean, permissionBean);
      }


        /**
         * Test Assign Permission with Permission Id and User Id.
         * @throws EnMeExpcetion EnMeExpcetion
         */
        @Test
        @ExpectedException(EnMeExpcetion.class)
        public void testAssignPermissionwithPermissionIdandUserId() throws EnMeExpcetion{
          UserAccountBean userPermissionBean = new UserAccountBean();
          UnitPermission permissionBean = new UnitPermission();
          securityService.assignPermission(userPermissionBean, permissionBean);

      }

        /**
         * Test Assign Group.
         * @throws EnMeExpcetion EnMeExpcetion
         */
        @Test
        public void testAssignGroup() throws EnMeExpcetion{
          final UserAccount users=  createUserAccount("juanpicado",this.userPrimary);
          final Group groups = createGroups("encuestador");
          UserAccountBean userBean = ConvertDomainBean.convertSecondaryUserToUserBean(users);
          UnitGroupBean groupBean = ConvertDomainBean.convertGroupDomainToBean(groups);
          //securityService.assingGroup(userBean, groupBean);
        }

        /**
         * Test searchUsersByEmail.
         */
        @Test
        public void testsearchUsersByEmail(){
            final UserAccount email = createUserAccount("emailUser1", this.userPrimary);
            List<UserAccount> emailUsers = this.securityService.searchUsersByEmail(email.getUserEmail());
            assertEquals(emailUsers.size(), 1);
        }

        /**
         * Test assingGroupFromUser.
         * @throws EnMeExpcetion exception
         */
    /*    @Test
        public void testassingGroupFromUser() throws EnMeExpcetion{
            final UnitUserBean userBean = ConvertDomainBean.convertSecondaryUserToUserBean(this.secUserSecondary);
            final UnitGroupBean groupBean = ConvertDomainBean
                                .convertGroupDomainToBean(createGroups("admin", this.userPrimary));
            this.securityService.assingGroupFromUser(userBean, groupBean);
            final SecUserSecondary userWithGroup = this.securityService.findUserByUserName(userBean.getUsername());
            assertEquals(userWithGroup.getSecGroups().size(), 3);
        }*/

        /**
         * Test assingGroupFromUser with Exception.
         * @throws EnMeExpcetion Exception
         */
        //@Test(expected = EnMeExpcetion.class)
        public void testassingGroupFromUserException() throws EnMeExpcetion{
            final UserAccountBean userBean = ConvertDomainBean.convertSecondaryUserToUserBean(this.secUserSecondary);
            //this.securityService.assingGroupFromUser(userBean, new UnitGroupBean());
        }

        /**
         *Test Load Bean Permission.
         * @throws EnMeExpcetion EnMeExpcetion
         */
        @Test
        public void testloadBeanPermission() throws EnMeExpcetion{
            final UnitPermission permission = securityService.loadBeanPermission(this.permission.getPermission());
            assertNotNull(permission);
        }
        /**
         * Test singupUser.
         */
        @Test
        public void testsingupUser(){
            final SignUpBean bean = createSignUpBean("newUser", "newUser@gmail.com", "12345");
            this.securityService.singupUser(bean);
        }
    /**
     * Test get {@link UserAccountBean} by code.
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testGetUserAccountbyCode() throws EnMeNoResultsFoundException{
        final String inviteCode = RandomStringUtils.randomNumeric(6);
        final UserAccount account = createUserAccount("jota", "jota@jota.com", createAccount());
        account.setInviteCode(inviteCode);
        getAccountDao().saveOrUpdate(account);
        final UserAccountBean userAccBean = securityService.getUserAccountbyCode(inviteCode);
        assertNotNull(userAccBean);
    }

    @Test
    public void testRemoveUnconfirmedAccount(){
        final Calendar createdAt = Calendar.getInstance();
        createdAt.add(Calendar.DATE, -2);
        final Account acc1 = createAccount(Boolean.TRUE);
        createUserAccount(Boolean.FALSE, createdAt.getTime(), "diana", acc1);
        createUserAccount(Boolean.FALSE, createdAt.getTime(), "paola", acc1);
        createUserAccount(Boolean.FALSE, createdAt.getTime(), "isabella", acc1);
        System.out.println("Account Id before --->"+ acc1.getUid());
        securityService.removeUnconfirmedAccount(Boolean.FALSE);
        // System.out.println("UserAccount without to set --->"+ msg);
        assertEquals(acc1.getEnabled(), Boolean.FALSE);
    }

    /**
     * Test get users account available.
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testGetUserAccountsAvailable()
            throws EnMeNoResultsFoundException {
        createUserAccount("jota", "jota@jota.com", createAccount());
        getSpringSecurityLoggedUserAccount();
        final List<UserAccount> accounts = securityService
                .getUserAccountsAvailable(Boolean.TRUE);
        assertEquals(accounts.size(), 3);
    }
}
