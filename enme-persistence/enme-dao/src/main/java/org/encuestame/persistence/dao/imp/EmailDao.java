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
package org.encuestame.persistence.dao.imp;

import java.util.List;

import org.encuestame.persistence.dao.IEmail;
import org.encuestame.persistence.domain.Email;
import org.encuestame.persistence.domain.EmailList;
import org.encuestame.persistence.domain.EmailSubscribe;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Email Catalog Dao.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since  June 20, 2010
 * @version $Id: $
 */

@Repository("emailDao")
public class EmailDao extends AbstractHibernateDaoSupport implements IEmail{

    @Autowired
    public EmailDao(SessionFactory sessionFactory) {
             setSessionFactory(sessionFactory);
    }

    /**
     * Find Emails by User.
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<EmailList> findListbyUser(final Long userId){
    	// return (Notification) getHibernateTemplate().get(Notification.class, notificationId);
    	final DetachedCriteria criteria = DetachedCriteria.forClass(EmailList.class);
    	criteria.createAlias("user", "usuarioEmail");
    	criteria.add(Restrictions.eq("user.uid", userId));
    	return (List<EmailList>) getHibernateTemplate().findByCriteria(criteria);
     }

    /**
     * Find Emails By List Id.
     * @param emailList
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Email> findEmailsByListId(final Long idList){
    	final DetachedCriteria criteria = DetachedCriteria.forClass(Email.class);
    	criteria.createAlias("emailList", "idListEmail");
    	criteria.add(Restrictions.eq("emailList.idList", idList));
    	return (List<Email>) getHibernateTemplate().findByCriteria(criteria); 
      
     }

    /**
     * Find All Email List.
     * @return
     */
    @SuppressWarnings("unchecked")
    public List findAllEmailList(){
        return getHibernateTemplate().find("FROM EmailList");
    }

    /**
     * Get List Emails by Keyword.
     * @return
     */
    @SuppressWarnings("unchecked")
    public List getListEmailsByKeyword(final String keyword, final Long userId){
        final DetachedCriteria criteria = DetachedCriteria.forClass(EmailList.class);
        criteria.add(Restrictions.like("listName", keyword, MatchMode.ANYWHERE));
        return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * Get Emails by Keyword.
     * @return
     */
    @SuppressWarnings("unchecked")
    public List getEmailsByKeyword(final String keyword, final Long userId){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Email.class);
        criteria.add(Restrictions.like("email", keyword, MatchMode.ANYWHERE));
        return getHibernateTemplate().findByCriteria(criteria);

    }

    /**
     *
     * @param code
     * @return
     */
    public EmailSubscribe getSubscribeAccount(final String code){
    	final DetachedCriteria criteria = DetachedCriteria.forClass(Email.class); 
    	criteria.add(Restrictions.eq("hashCode", code));
    	return (EmailSubscribe) getHibernateTemplate().findByCriteria(criteria); 
    } 
}
