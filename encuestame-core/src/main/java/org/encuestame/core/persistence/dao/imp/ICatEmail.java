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

import org.encuestame.core.persistence.pojo.CatEmails;
import org.encuestame.core.persistence.pojo.CatListEmails;

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
	public List<CatListEmails> findListbyUser(final Long userId);


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
	public List<CatListEmails> findAllEmailList();

	 /**
	  * Find List Email by Id.
	  * @param listEmailId
	  * @return
	  */
	public CatListEmails getListEmailById(final Long listEmailId);

	 /**
	  * Find Email List by Keyword.
	  * @param keyword
	  * @param userId
	  * @return
	  */
	public List<CatListEmails> getListEmailsByKeyword(final String keyword, final Long userId);

	/**
	 * Find Emails by Keyword.
	 * @param keyword
	 * @return
	 */
	public List<CatEmails> getEmailsByKeyword(final String keyword);
}
