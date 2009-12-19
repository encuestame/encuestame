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
package org.encuestame.core.service;

import java.util.Date;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.pojo.SecGroups;
import org.encuestame.core.persistence.pojo.SecPermission;
import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.service.util.ConvertDomainBean;
import org.encuestame.test.config.AbstractBaseTest;
import org.encuestame.web.beans.admon.UnitGroupBean;
import org.encuestame.web.beans.admon.UnitPermission;
import org.encuestame.web.beans.admon.UnitUserBean;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;

/**
 * Test Security Service.
 * @author Picado, Juan juan@encuestame.org
 * @since 08/11/2009 11:35:01
 * @version $Id$
 */
public class TestSecurityService extends AbstractBaseTest{

    @Autowired
    private SecurityService securityService;

    private SecUsers userPrimary;

    /**
     * Before.
     */
    @Before
    public void initService(){
        securityService.setSuspendedNotification(getActivateNotifications());
        this.userPrimary = createUser();
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
         createPermission("ADMON");
         createPermission("EDITOR");
        assertEquals("Should be equals",2,securityService.loadAllListPermission().size());
    }

    /**
     * @throws EnMeExpcetion EnMeExpcetion
     */
    @Test
    public void testLoadListUsers() throws EnMeExpcetion{
        addGroupUser(super.createSecondaryUser("user 1",this.userPrimary),super.createGroups("editor"));
        addGroupUser(super.createSecondaryUser("user 2",this.userPrimary),super.createGroups("admon"));
        assertEquals("Should be equals",2,securityService.loadListUsers().size());
    }

    /**
     * Test User By Username.
     * @throws EnMeExpcetion EnMeExpcetion
     */
    @Test
    public void testSearchUserByUsername() throws EnMeExpcetion{
      // final SecUsers userDomain = super.createUsers("user 1",this.userPrimary);
      //  super.createSecondaryUser("user 2",this.userPrimary);
       // UnitUserBean userBean = securityService.searchUserByUsername(userDomain.getUsername());
        //assertEquals("Should be equals",userDomain.getUsername(),userBean.getUsername()
         //       );
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
        //assertEquals("Should be","ENCUESTAME_USER",defaultPermission.toString());
        securityService.setDefaultUserPermission("ENCUESTAME_EDITOR");
        final String newDefaultPermission =  securityService.getDefaultUserPermission();
        assertEquals("Should be","ENCUESTAME_EDITOR",newDefaultPermission.toString());
    }


    /**
     *
     */
    @Test
    public void testDeleteGroup(){
        SecGroups groupDomain = createGroups("admin");
        Long idGroup = groupDomain.getGroupId();
        UnitGroupBean group = securityService.convertGroupDomainToBean(groupDomain);
        securityService.deleteGroup(group);
        SecGroups groupRetrieve = getSecGroup().getGroupById(idGroup);
        assertNull(groupRetrieve);
    }

    /**
     * Setter.
     * @param securityService the securityService to set
     */
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }



    /**
     *Test delete Group.
     */
    @Test
    public void testdeleteGroup(){
      SecGroups groupDomain = createGroups("admin");
      Long idGroup = groupDomain.getGroupId();
      UnitGroupBean group = securityService.convertGroupDomainToBean(groupDomain);
      securityService.deleteGroup(group);
      SecGroups groupRetrieve = getSecGroup().getGroupById(idGroup);
      assertNull(groupRetrieve);

    }

    /**
    *Test delete User.
     */
    @Test
    public void testDeleteUser(){
      //SecUsers secUsers = createUsers("administrator");
     // Long idUser = secUsers.getUid();
      //String username = secUsers.getUsername();
      //UnitUserBean user = securityService.convertUserDaoToUserBean(secUsers);
      //securityService.deleteUser(user);
      //SecUsers userRetrieve = getSecUserDao().getUserById(idUser);
     // assertNull(userRetrieve);
    }



    /**
     * Test Update Group.
     */
    @Test
    public void testUpdateGroup(){
      SecGroups secgroups = createGroups("guests");
      Long idGroupUpdate = secgroups.getGroupId();
      UnitGroupBean groupBean = securityService.convertGroupDomainToBean(secgroups);
      groupBean.setGroupName("editors");
      securityService.updateGroup(groupBean);
      SecGroups groupUpdateRetrieve =  getSecGroup().getGroupById(idGroupUpdate);
      assertEquals("Should be","editors",groupUpdateRetrieve.getGroupName());

    }

    /**
     *Test Update User.
     * @throws EnMeExpcetion exception
     **/
    @Test
    public void testUpdateUser() throws EnMeExpcetion{
      final SecUserSecondary secUsers = createSecondaryUser("developer",createUser());
      Long idUser = secUsers.getUid();
      UnitUserBean userBean = securityService.convertUserDaoToUserBean(secUsers);
      userBean.setName("editor");
      securityService.updateUser(userBean);
      //SecUserSecondary userUpdateRetrieve = getSecUserDao().getSecondaryUserById(idUser);
     // assertEquals("shouldbe", "editor", userUpdateRetrieve.getCompleteName());
    }

    /**
     * Test Create Permission.
     */
    @Test
    public void testCreatePermission(){
      SecPermission secPerm = createPermission("writer");
      UnitPermission permissionBean = ConvertDomainBean.convertPermissionToBean(secPerm);
      securityService.createPermission(permissionBean);
      SecPermission permissionRetrieve = getSecPermissionDaoImp().getPermissionById(secPerm.getIdPermission());
      assertNotNull(permissionRetrieve);
      assertEquals("should be","writer", permissionRetrieve.getPermissionDescription());


    }

    /**
     * Test Renew Password.
     * @throws EnMeExpcetion
     */
    @Test
    public void testRenewPassword() throws EnMeExpcetion{
      SecUserSecondary secUser = createSecondaryUser("paola",this.userPrimary);
      Long idUser = secUser.getUid();
      //String passwd = secUser.getPassword();
      UnitUserBean userPass = ConvertDomainBean.convertUserDaoToUserBean(secUser);
      userPass.setPassword("newPass");
      securityService.updateUser(userPass);
     // SecUsers userPassRetrieve = getSecUserDao().getUserById(idUser);
      //assertEquals("should be", "newPass",userPass.getPassword());


    }

    /**
     * Test Renew Password without Pass.
     * @throws EnMeExpcetion  EnMeExpcetion
     */
    /*   @Test

   public void testRenewPasswordwithoutPass()throws EnMeExpcetion{
      SecUsers secUser = createUsers("diana");

      UnitUserBean userPassBean = ConvertDomainBean.convertUserDaoToUserBean(secUser);
      securityService.renewPassword(userPassBean);

    }
*/
    /**
     * Test Create User without Email.
     * @throws EnMeExpcetion EnMeExpcetion
     **/
      @Test
      @ExpectedException(EnMeExpcetion.class)
       public void testCreateUserwithoutEmail() throws EnMeExpcetion{
        final UnitUserBean userCreateBean = new UnitUserBean();
        userCreateBean.setEmail(null);
        userCreateBean.setUsername("diana");
          securityService.createUser(userCreateBean);
       }

      /**
       * Test Create User without Username.
       * @throws EnMeExpcetion EnMeExpcetion
       */
      @Test
      @ExpectedException(EnMeExpcetion.class)
       public void testCreateUserwithoutUsername() throws EnMeExpcetion{
        UnitUserBean userCreateBean = new UnitUserBean();
        userCreateBean.setEmail("paola@jotadeveloper.com");
        userCreateBean.setUsername(null);
          securityService.createUser(userCreateBean);
       }

      /**
       * Test Create User with Username.
       * @throws EnMeExpcetion EnMeExpcetion
       */
      @Test

       public void testCreateUserwithUsernameEmail() throws EnMeExpcetion{
        final UnitUserBean userCreateBean = new UnitUserBean();
        userCreateBean.setEmail("demo@demo.org");
        userCreateBean.setUsername("demo");
        userCreateBean.setStatus(true);
        userCreateBean.setPublisher(Boolean.TRUE);
        userCreateBean.setName("Diana Paola");
        userCreateBean.setDateNew(new Date());
        userCreateBean.setPrimaryUserId(createUser().getUid());
        securityService.createUser(userCreateBean);
       }

      /**
       * Test Create User without Password.
       * @throws EnMeExpcetion EnMeExpcetion
       */
      @Test

       public void testCreateUserwithoutPassword() throws EnMeExpcetion{
        SecUserSecondary secCreateUser = new SecUserSecondary();
        UnitUserBean userCreateBean = ConvertDomainBean.convertUserDaoToUserBean(secCreateUser);
        userCreateBean.setPassword(null);
        userCreateBean.setEmail("demo@demo.org");
        userCreateBean.setUsername("demo");
        userCreateBean.setStatus(true);
        userCreateBean.setPublisher(Boolean.TRUE);
        userCreateBean.setName("Diana Paola");
        userCreateBean.setPrimaryUserId(createUser().getUid());
        userCreateBean.setDateNew(new Date());
        securityService.createUser(userCreateBean);
       }

      /**
       * Test Create User without Password.
       * @throws EnMeExpcetion EnMeExpcetion
       */
      @Test
       public void testCreateUserwithPassword() throws EnMeExpcetion{
          SecUserSecondary secCreateUser = new SecUserSecondary();
        UnitUserBean userCreateBean = ConvertDomainBean.convertUserDaoToUserBean(secCreateUser);
        userCreateBean.setPassword("12345");
        userCreateBean.setEmail("demo@demo.org");
        userCreateBean.setUsername("demo");
        userCreateBean.setStatus(true);
        userCreateBean.setPublisher(Boolean.TRUE);
        userCreateBean.setName("Diana Paola");
        userCreateBean.setDateNew(new Date());
        userCreateBean.setPrimaryUserId(createUser().getUid());
        securityService.createUser(userCreateBean);
       }


      /**
       * Test Create Group.
       */
      @Test
      public void testCreateGroup(){
        SecGroups secCreateGroup = new SecGroups();
        secCreateGroup.setGroupId(12L);
        secCreateGroup.setGroupDescriptionInfo("1111");
        secCreateGroup.setGroupName("vvvv");
        secCreateGroup.setIdState(1L);
        UnitGroupBean createGroupBean = ConvertDomainBean.convertGroupDomainToBean(secCreateGroup);
        securityService.createGroup(createGroupBean);
      }

      /**
       * Test Assing Permission without Id an Username.
       * @throws EnMeExpcetion  EnMeExpcetion
       */
      @Test

      public void testAssignPermissionwithIdUsername() throws EnMeExpcetion{
        final SecUserSecondary secUser = createSecondaryUser("demo",this.userPrimary);
        final SecPermission secPermission = createPermission("redactor");
       /*0secPermission.setIdPermission(1L);
        secPermission.setPermissionDescription("new Permission");
        secPermission.setPermission("permission");*/
        UnitUserBean userPermissionBean = ConvertDomainBean.convertUserDaoToUserBean(secUser);
        UnitPermission permissionBean = ConvertDomainBean.convertPermissionToBean(secPermission);
        userPermissionBean.setId(1);
        userPermissionBean.setUsername("demo");
        securityService.assignPermission(userPermissionBean, permissionBean);
      }

        /**
         * Test Assign Permission without Username.
         * @throws EnMeExpcetion EnMeExpcetion
         */
        @Test
        public void testAssignPermissionwithoutIdUsername() throws EnMeExpcetion{
          final SecUserSecondary secUser = createSecondaryUser("juanpicado",this.userPrimary);
          final SecPermission secPermission = createPermission("redactor");
         /*0secPermission.setIdPermission(1L);
          secPermission.setPermissionDescription("new Permission");
          secPermission.setPermission("permission");*/
          UnitUserBean userPermissionBean = ConvertDomainBean.convertUserDaoToUserBean(secUser);
          UnitPermission permissionBean = ConvertDomainBean.convertPermissionToBean(secPermission);
          permissionBean.setId(1);
          permissionBean.setPermission("auditor");
          securityService.assignPermission(userPermissionBean, permissionBean);

      }


        /**
         * Test Assign Permission with Permission.
         * @throws EnMeExpcetion EnMeExpcetion
         */
        @Test
        public void testAssignPermissionwithPermission() throws EnMeExpcetion{
          final SecUserSecondary secUser = createSecondaryUser("juanpicado",this.userPrimary);
          final SecPermission secPermission = createPermission("redactor");
         /*0secPermission.setIdPermission(1L);
          secPermission.setPermissionDescription("new Permission");
          secPermission.setPermission("permission");*/
          UnitUserBean userPermissionBean = ConvertDomainBean.convertUserDaoToUserBean(secUser);
          UnitPermission permissionBean = ConvertDomainBean.convertPermissionToBean(secPermission);
          userPermissionBean.setId(1);
          userPermissionBean.setUsername(null);
          permissionBean.setId(1);
          permissionBean.setPermission("analista");
          securityService.assignPermission(userPermissionBean, permissionBean);

      }


        /**
         * Test Assign Permission with Permission Id and User Id.
         * @throws EnMeExpcetion EnMeExpcetion
         */
        @Test
        public void testAssignPermissionwithPermissionIdandUserId() throws EnMeExpcetion{
          final SecUserSecondary secUser = createSecondaryUser("juanpicado",this.userPrimary);
          final SecPermission secPermission = createPermission("redactor");
         /*0secPermission.setIdPermission(1L);
          secPermission.setPermissionDescription("new Permission");
          secPermission.setPermission("permission");*/
          UnitUserBean userPermissionBean = ConvertDomainBean.convertUserDaoToUserBean(secUser);
          UnitPermission permissionBean = ConvertDomainBean.convertPermissionToBean(secPermission);
          //userPermissionBean.setId(1);
          //userPermissionBean.setUsername(null);
          securityService.assignPermission(userPermissionBean, permissionBean);

      }

        /**
         * Test Assign Group.
         * @throws EnMeExpcetion EnMeExpcetion
         */
        @Test
        public void testAssignGroup() throws EnMeExpcetion{
          final SecUserSecondary users=  createSecondaryUser("juanpicado",this.userPrimary);
          final SecGroups groups = createGroups("encuestador");
          UnitUserBean userBean = ConvertDomainBean.convertUserDaoToUserBean(users);
          UnitGroupBean groupBean = ConvertDomainBean.convertGroupDomainToBean(groups);
          securityService.assingGroup(userBean, groupBean);

        }

        /**
         *Test Load Bean Permission.
         * @throws EnMeExpcetion EnMeExpcetion
         */
        @Test
        public void testloadBeanPermission() throws EnMeExpcetion{
          securityService.loadBeanPermission("permission");

        }

}

