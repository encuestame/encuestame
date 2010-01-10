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
      final SecUserSecondary userDomain = createSecondaryUser("user 1",this.userPrimary);
      createSecondaryUser("user 2",this.userPrimary);
      final UnitUserBean userBean = securityService.searchUserByUsername(userDomain.getUsername());
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
        //assertEquals("Should be","ENCUESTAME_USER".toString(), defaultPermission.toString());
        final String newDefaultPermission =  securityService.getDefaultUserPermission();
        assertEquals("Should be","ENCUESTAME_EDITOR".toString(), newDefaultPermission.toString());
    }


    /**
     *Test Delete Group.
     */
    @Test
    public void testDeleteGroup(){
        final SecGroups groupDomain = createGroups("admin");
        final Long idGroup = groupDomain.getGroupId();
        final UnitGroupBean group = securityService.convertGroupDomainToBean(groupDomain);
        securityService.deleteGroup(group);
        final SecGroups groupRetrieve = getSecGroup().getGroupById(idGroup);
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
      final SecGroups groupDomain = createGroups("admin");
      final Long idGroup = groupDomain.getGroupId();
      final UnitGroupBean group = securityService.convertGroupDomainToBean(groupDomain);
      securityService.deleteGroup(group);
      final SecGroups groupRetrieve = getSecGroup().getGroupById(idGroup);
      assertNull(groupRetrieve);

    }

    /**
    *Test delete User.
     * @throws EnMeExpcetion exception
     */
    @Test
    public void testDeleteUser() throws EnMeExpcetion{
     final SecUserSecondary secUsers = createSecondaryUser("administrator",this.userPrimary);
     final Long idUser = secUsers.getUid();
     final String username = secUsers.getUsername();
     final UnitUserBean user = securityService.convertUserDaoToUserBean(secUsers);
     securityService.deleteUser(user);
     final SecUsers userRetrieve = getSecUserDao().getUserById(idUser);
     assertNull(userRetrieve);
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
      final SecUserSecondary secUsers = createSecondaryUser("developer",this.userPrimary);
      final Long idUser = secUsers.getUid();
      final UnitUserBean userBean = securityService.convertUserDaoToUserBean(secUsers);
      userBean.setName("editor");
      securityService.updateUser(userBean);
      final SecUserSecondary userUpdateRetrieve = getSecUserDao().getSecondaryUserById(idUser);
      assertEquals("shouldbe", "editor", userUpdateRetrieve.getCompleteName());
    }

    /**
     * Test Create Permission.
     */
    @Test
    public void testCreatePermission(){
      final SecPermission secPerm = createPermission("writer");
      final UnitPermission permissionBean = ConvertDomainBean.convertPermissionToBean(secPerm);
      securityService.createPermission(permissionBean);
      final SecPermission permissionRetrieve = getSecPermissionDaoImp().getPermissionById(secPerm.getIdPermission());
      assertNotNull(permissionRetrieve);
      assertEquals("should be","writer", permissionRetrieve.getPermissionDescription());


    }

    /**
     * Test Renew Password.
     * @throws EnMeExpcetion exception
     */
    @Test
    public void testRenewPassword() throws EnMeExpcetion{
      final SecUserSecondary secUser = createSecondaryUser("paola",this.userPrimary);
      final String passwd = secUser.getPassword();
      final UnitUserBean userPass = ConvertDomainBean.convertUserDaoToUserBean(secUser);
      final String retrievePassword = securityService.renewPassword(userPass, passwd);
      assertEquals("should be equals", passwd, retrievePassword);


    }

    /**
     * Test Renew Password without Pass.
     * @throws EnMeExpcetion  EnMeExpcetion
     */
   @Test
   public void testRenewPasswordwithoutPass()throws EnMeExpcetion{
      final SecUserSecondary secUser = createSecondaryUser("diana",this.userPrimary);
      UnitUserBean userPassBean = ConvertDomainBean.convertUserDaoToUserBean(secUser);
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
        final UnitUserBean userCreateBean = new UnitUserBean();
        userCreateBean.setEmail("paola@jotadeveloper.com");
        userCreateBean.setUsername(null);
        securityService.createUser(userCreateBean);
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
        final UnitUserBean userCreateBean = new UnitUserBean();
        userCreateBean.setEmail("demo@demo.org");
        userCreateBean.setUsername("demo");
        userCreateBean.setStatus(true);
        userCreateBean.setPublisher(Boolean.TRUE);
        userCreateBean.setName("demo");
        userCreateBean.setPassword(null);
        userCreateBean.setDateNew(new Date());
        userCreateBean.setPrimaryUserId(createUser().getUid());
        securityService.createUser(userCreateBean);
        //TODO: need assert
        final SecUserSecondary user = getSecUserDao().getUserByUsername(userCreateBean.getUsername());
        assertNotNull("should be equals", user);
        assertEquals("should be equals",1, user.getSecUserPermissions().size());
       }

      /**
       * Test Create User without Password.
       * @throws EnMeExpcetion EnMeExpcetion
       */
      @Test
       public void testCreateUserwithoutPassword() throws EnMeExpcetion{
        createDefaultPermission();
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
        //TODO: need assert
        final SecUserSecondary user = getSecUserDao().getUserByUsername(userCreateBean.getUsername());
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
        //TODO: need assert
        final SecUserSecondary user = getSecUserDao().getUserByUsername(userCreateBean.getUsername());
        assertNotNull("should be equals", user);
        assertEquals("should be equals", 1, user.getSecUserPermissions().size());
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
        final UnitGroupBean createGroupBean = ConvertDomainBean.convertGroupDomainToBean(secCreateGroup);
        securityService.createGroup(createGroupBean);
        //TODO: need assert
      }

      /**
       * Test Assing Permission without Id an Username.
       * @throws EnMeExpcetion  EnMeExpcetion
       */
      @Test
      public void testAssignPermissionwithIdUsername() throws EnMeExpcetion{
        final SecUserSecondary secUser = createSecondaryUser("demo",this.userPrimary);
        final SecPermission secPermission = createPermission("admin");
        final SecPermission secPermission2 = createPermission("editor");
        final UnitUserBean userPermissionBean = ConvertDomainBean.convertUserDaoToUserBean(secUser);
        final UnitPermission permissionBean = ConvertDomainBean.convertPermissionToBean(secPermission);
        final UnitPermission permissionBean2 = ConvertDomainBean.convertPermissionToBean(secPermission2);
        userPermissionBean.setId(userPermissionBean.getId());
        userPermissionBean.setUsername("demo");
        securityService.assignPermission(userPermissionBean, permissionBean);
        securityService.assignPermission(userPermissionBean, permissionBean2);
        assertEquals("should be equals", 2, secUser.getSecUserPermissions().size());
      }

        /**
         * Test Assign Permission without Username.
         * @throws EnMeExpcetion EnMeExpcetion
         */
        @Test
        @ExpectedException(EnMeExpcetion.class)
        public void testAssignPermissionwithoutIdUsername() throws EnMeExpcetion{
          final SecPermission permission = createPermission("editor");
          final UnitUserBean userPermissionBean = new UnitUserBean();
          final UnitPermission permissionBean = ConvertDomainBean.convertPermissionToBean(permission);
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
            final SecUserSecondary secUser = createSecondaryUser("juanpicado",this.userPrimary);
            final UnitUserBean userPermissionBean = ConvertDomainBean.convertUserDaoToUserBean(secUser);
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
          UnitUserBean userPermissionBean = new UnitUserBean();
          UnitPermission permissionBean = new UnitPermission();
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
