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
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.test.config.AbstractBaseTest;
import org.encuestame.web.beans.admon.UnitUserBean;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class Description.
 * @author Picado, Juan juan@encuestame.org
 * @since 08/11/2009 11:35:01
 */
public class TestSecurityService extends AbstractBaseTest{

    @Autowired
    private SecurityService securityService;

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
        assertEquals("Should be equals",2,securityService.loadAllListPermission().size());
    }

    @Test
    public void testLoadListUsers() throws EnMeExpcetion{
        addGroupUser(super.createUsers("user 1"),super.createGroups("editor"));
        addGroupUser(super.createUsers("user 2"),super.createGroups("admon"));
        assertEquals("Should be equals",2,securityService.loadListUsers().size());
    }

    /**
     * Test User By Username.
     * @throws EnMeExpcetion
     */
    @Test
    public void testSearchUserByUsername() throws EnMeExpcetion{
       final SecUsers userDomain = super.createUsers("user 1");
        super.createUsers("user 2");
        UnitUserBean userBean = securityService.searchUserByUsername(userDomain.getUsername());
        assertEquals("Should be equals",userDomain.getUsername(),userBean.getUsername()
                );
    }

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
        assertEquals("Should be","ENCUESTAME_USER",defaultPermission.toString());
        securityService.setDefaultUserPermission("ENCUESTAME_EDITOR");
        final String newDefaultPermission =  securityService.getDefaultUserPermission();
        assertEquals("Should be","ENCUESTAME_EDITOR",newDefaultPermission.toString());
    }

    /**
     * Setter.
     * @param securityService the securityService to set
     */
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
