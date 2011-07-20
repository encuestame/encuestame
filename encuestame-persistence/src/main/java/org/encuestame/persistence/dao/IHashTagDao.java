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
package org.encuestame.persistence.dao;

import java.util.List;

import org.encuestame.persistence.dao.imp.HashTagDao;
import org.encuestame.persistence.domain.HashTag;
import org.hibernate.HibernateException;

/**
 * Iterface for {@link HashTagDao}.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 25, 2010 5:32:28 PM
 * @version Id:
 */
public interface IHashTagDao extends IBaseDao	{

    /**
     * Create Hash TAg.
     * @param hashTag
     * @throws HibernateException
     */
    void createHashTag(final HashTag hashTag) throws HibernateException;

    /**
     * Get List of HashTags by Keyword.
     * @param keyword keyword
     * @return
     */
    List<HashTag> getListHashTagsByKeyword(final String keyword, final Integer maxResults,
            final Long[] excludes);

    /**
     * Get HashTag By Name.
     * @param hashTag
     * @return
     * @throws HibernateException
     */
    HashTag getHashTagByName(final String hashTag)throws HibernateException;

    /**
     * Get hashTags.
     * @param maxResults
     * @param start
     * @param tagCriteria
     * @return
     */
    List<HashTag> getHashTags( final Integer maxResults,final Integer start, final String tagCriteria);

    /**
     * Get hashTag by Id.
     * @param hashTagId
     * @return
     * @throws HibernateException
     */
    HashTag getHashTagById(final Long hashTagId) throws HibernateException;

    /**
     * Get max-min tag frecuency.
     * @return
     */
   List<Object[]> getMaxMinTagFrecuency();
}
