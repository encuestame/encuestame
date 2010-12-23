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
package org.encuestame.persistence.dao.imp;

import java.util.List;

import org.encuestame.persistence.dao.IEmail;
import org.encuestame.persistence.domain.EmailList;
import org.encuestame.persistence.domain.Emails;
import org.encuestame.persistence.domain.EmailSubscribe;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Email Catalog Dao.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since  June 20, 2010
 * @version $Id: $
 */

@Repository
public class EmailDao extends AbstractHibernateDaoSupport implements IEmail{

    /**
     * Find Emails by User.
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<EmailList> findListbyUser(final Long userId){
        return getHibernateTemplate().findByNamedParam(
                "from CatEmailLists where usuarioEmail.uid= :userId", "userId", userId);
     }

    /**
     * Find Emails By List Id.
     * @param emailList
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Emails> findEmailsByListId(final Long idList){
        return getHibernateTemplate().findByNamedParam("FROM CatEmails WHERE idListEmail.idList= :idList", "idList", idList);
     }

    /**
     * Find All Email List.
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<EmailList> findAllEmailList(){
        return getHibernateTemplate().find("FROM CatEmailLists");
    }

    /**
     * Get List Emails by Keyword.
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<EmailList> getListEmailsByKeyword(final String keyword, final Long userId){
        final DetachedCriteria criteria = DetachedCriteria.forClass(EmailList.class);
        criteria.add(Restrictions.like("listName", keyword, MatchMode.ANYWHERE));
        return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * Get Emails by Keyword.
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Emails> getEmailsByKeyword(final String keyword, final Long userId){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Emails.class);
        criteria.add(Restrictions.like("email", keyword, MatchMode.ANYWHERE));
        return getHibernateTemplate().findByCriteria(criteria);

    }

    /**
     *
     * @param code
     * @return
     */
    public EmailSubscribe getSubscribeAccount(final String code){
           return (EmailSubscribe) getHibernateTemplate().findByNamedParam("FROM CatSubscribeEmails WHERE hashCode= :code", "code", code);
    }

}
