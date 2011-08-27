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
package org.encuestame.persistence.domain.survey;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.encuestame.persistence.dao.IFolder;
import org.encuestame.persistence.domain.AbstractFolder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;

/**
 * Polls Folder.
 *
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since October 04, 2010
 * @version $Id: $
 */
@Entity
@Table(name = "poll_folder")
@Indexed(index="PollFolder")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PollFolder extends AbstractFolder implements IFolder{
     private Long id;

    /**
     * @return the pollFolderId
     */
     @Id
     @DocumentId
     @GeneratedValue(strategy = GenerationType.AUTO)
     @Column(name = "pollFolderId", unique = true, nullable = true)
    public Long getId() {
        return id;
    }

    /**
     * @param pollFolderId the pollFolderId to set
     */
    public void setId(Long id) {
        this.id = id;
    }
}
