/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
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

import java.util.Date;

import org.encuestame.core.persistence.domain.SecUser;

/**
 * Implementation for Folders.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 9, 2010 12:47:26 PM
 * @version $Id:$
 */
public interface IFolder {

    String getFolderName();

    void setFolderName(String folderName);

    SecUser getUsers();

    void setUsers(SecUser users);

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    void setId(Long id);

    Long getId();

}
