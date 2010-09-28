/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
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

import org.encuestame.core.persistence.domain.CatEmailLists;
import org.encuestame.core.persistence.domain.CatEmails;
import org.encuestame.core.persistence.domain.CatSubscribeEmails;

/**
 * Email Catalog Interface.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since  June 20, 2010
 * @version $Id: $
 */
public interface ICatEmail extends IBaseDao {

     /**
      * Find Email List By User Id.
      * @param userId
      * @return
      */
    public List<CatEmailLists> findListbyUser(final Long userId);


     /**
      * Find Emails by Email ListId.
      * @param emailListId
      * @return
      */
    public List<CatEmails> findEmailsByListId(final Long emailListId);

     /**
      * Find All Email List.
      * @return
      */
    public List<CatEmailLists> findAllEmailList();

     /**
      * Find List Email by Id.
      * @param listEmailId
      * @return
      */
    public CatEmailLists getListEmailById(final Long listEmailId);

     /**
      * Find Email List by Keyword.
      * @param keyword
      * @param userId
      * @return
      */
    public List<CatEmailLists> getListEmailsByKeyword(final String keyword, final Long userId);

    /**
     * Find Emails by Keyword.
     * @param keyword
     * @param userId
     * @return
     */
    public List<CatEmails> getEmailsByKeyword(final String keyword, final Long userId);

    /**
     *
     * @param code
     * @return
     */
    public CatSubscribeEmails getSubscribeAccount(final String code);
}
