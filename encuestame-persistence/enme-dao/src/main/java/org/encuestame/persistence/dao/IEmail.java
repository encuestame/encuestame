/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
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

import org.encuestame.persistence.domain.EmailList;
import org.encuestame.persistence.domain.Email;
import org.encuestame.persistence.domain.EmailSubscribe;

/**
 * Email Catalog Interface.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since  June 20, 2010
 * @version $Id: $
 */
public interface IEmail extends IBaseDao {

    /**
    * Find Email List By User Id.
    * @param userId
    * @return
    */
    List<EmailList> findListbyUser(final Long userId);


    /**
    * Find Emails by Email ListId.
    * @param emailListId
    * @return
    */
    List<Email> findEmailsByListId(final Long emailListId);

    /**
    * Find All Email List.
    * @return
    */
    List<EmailList> findAllEmailList();

    /**
    * Find Email List by Keyword.
    * @param keyword
    * @param userId
    * @return
    */
    List<EmailList> getListEmailsByKeyword(final String keyword, final Long userId);

    /**
    * Find Emails by Keyword.
    * @param keyword
    * @param userId
    * @return
    */
    List<Email> getEmailsByKeyword(final String keyword, final Long userId);

    /**
    *
    * @param code
    * @return
    */
    EmailSubscribe getSubscribeAccount(final String code);
}
