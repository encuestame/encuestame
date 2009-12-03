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
package org.encuestame.core.service;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.pojo.SecGroups;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.test.config.AbstractBaseTest;
import org.encuestame.web.beans.admon.UnitGroupBean;
import org.encuestame.web.beans.admon.UnitUserBean;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class Description.
 * @author Picado, Juan juan@encuestame.org
 * @since 08/11/2009 11:35:01
 * File name: $HeadURL$
 * Revision: $Revision$
 * Last modified: $Date$
 * Last modified by: $Author$
 */
public class TestSecurityService extends AbstractBaseTest{

    @Autowired
    private SecurityService securityService;

    /**
     * Before.
     */
    @Before
    public void initService(){
        securityService.setSuspendedNotification(false);
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
        super.createPermission("ADMON");
        super.createPermission("EDITOR");
        assertEquals("Should be equals",4,securityService.loadAllListPermission().size());
    }

    /**
     * @throws EnMeExpcetion EnMeExpcetion
     */
    @Test
    public void testLoadListUsers() throws EnMeExpcetion{
        addGroupUser(super.createUsers("user 1"),super.createGroups("editor"));
        addGroupUser(super.createUsers("user 2"),super.createGroups("admon"));
        assertEquals("Should be equals",3,securityService.loadListUsers().size());
    }

    /**
     * Test User By Username.
     * @throws EnMeExpcetion EnMeExpcetion
     */
    @Test
    public void testSearchUserByUsername() throws EnMeExpcetion{
       final SecUsers userDomain = super.createUsers("user 1");
        super.createUsers("user 2");
        UnitUserBean userBean = securityService.searchUserByUsername(userDomain.getUsername());
        assertEquals("Should be equals",userDomain.getUsername(),userBean.getUsername()
                );
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
      SecUsers secUsers = createUsers("administrator");
      Long idUser = secUsers.getUid();
      String username = secUsers.getUsername();
      UnitUserBean user = securityService.convertUserDaoToUserBean(secUsers);
      securityService.deleteUser(user);
      SecUsers userRetrieve = getSecUserDao().getUserById(idUser);
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
      assertEquals("Should be","editors",groupUpdateRetrieve.getName());

    }

    /**
     *Test Update User
     **/
    @Test
    public void testUpdateUser(){
      SecUsers secUsers = createUsers("developer");
      Long idUser = secUsers.getUid();
      UnitUserBean userBean = securityService.convertUserDaoToUserBean(secUsers);
      userBean.setName("editor");
      securityService.updateUser(userBean);
      SecUsers userUpdateRetrieve = getSecUserDao().getUserById(idUser);
      assertEquals("shouldbe", "editor", userUpdateRetrieve.getName());

    }

}
;
