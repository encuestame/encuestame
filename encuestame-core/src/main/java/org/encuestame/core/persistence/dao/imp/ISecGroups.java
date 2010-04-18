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
package org.encuestame.core.persistence.dao.imp;

import java.util.List;

import org.encuestame.core.persistence.pojo.SecGroups;
/**
 * Interface to implement Sec Group Dao.
 * @author Picado, Juan juan@encuestame.org
 * @since 11/05/2009 10:45:30
 * @version $Id$
 */
public interface ISecGroups extends IBaseDao {

    /**
     * Find All Groups.
     * @return
     */
    List<SecGroups> findAllGroups();

    /**
     * Group By Id.
     * @param groupId
     * @return
     */
    SecGroups getGroupById(Long groupId);

    /**
     * Find.
     * @param groupId
     * @return
     */
    SecGroups find(final Long groupId);
}

