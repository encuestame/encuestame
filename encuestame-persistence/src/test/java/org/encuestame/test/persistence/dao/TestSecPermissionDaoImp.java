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
package org.encuestame.test.persistence.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.encuestame.persistence.domain.security.Permission;
import org.encuestame.persistence.domain.EnMePermission;
import org.encuestame.test.config.AbstractBase;
import org.junit.Before;
import org.junit.Test;

/**
 * Test Security Permission Dao.
 * @author Picado, Juan juan@encuestame.org
 * @since 13:00:55
 * @version $Id$
 */
public class TestSecPermissionDaoImp extends AbstractBase {

     private Permission permission;

    /**
     * Before.
     */
    @Before
    public void initData(){
        permission = createPermission(EnMePermission.ENCUESTAME_USER.name());
        createPermission(EnMePermission.ENCUESTAME_EDITOR.name());
        createPermission(EnMePermission.ENCUESTAME_OWNER.name());
        createPermission(EnMePermission.ENCUESTAME_PUBLISHER.name());
        createPermission(EnMePermission.ENCUESTAME_ADMIN.name());
    }

    /**
     * Test Load Permission.
     */
    @Test
    public void testloadPermission(){
        final Permission retrievedPermission = getPermissionDaoImp().loadPermission(EnMePermission.ENCUESTAME_USER);
        assertEquals("should be equals", permission.getPermission(), retrievedPermission.getPermission());
    }

    /**
     * Test Load All Permissions.
     */
    @Test
    public void testloadAllPermissions(){
        final List<Permission> allPermissions = getPermissionDaoImp().loadAllPermissions();
        for (Permission secPermission : allPermissions) {
            log.info("s "+secPermission.getPermission());
        }
        assertEquals("should be equals", 5, allPermissions.size());
    }

    /**
     * Test Find All Permissions.
     */
    @Test
    public void testFindAllPermissions(){
          final List<Permission> findPermissions = getPermissionDaoImp().findAllPermissions();
          for (Permission secPermission : findPermissions) {
              log.info("s "+secPermission.getPermission());
          }
          assertEquals("should be equals", 5, findPermissions.size());
      }

    /**
     *
     */
    @Test
    public void testGetPermissionById(){
        final Permission perm = getPermissionDaoImp().getPermissionById(this.permission.getIdPermission());
        assertNotNull(perm);
        assertEquals("should be equals", EnMePermission.ENCUESTAME_USER, perm.getPermission());
    }
}
