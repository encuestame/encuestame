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

package org.encuestame.core.persistence.dao;



import java.util.List;

import org.encuestame.core.persistence.dao.imp.ICatEmail;
import org.encuestame.core.persistence.pojo.CatEmails;
import org.encuestame.core.persistence.pojo.CatListEmails;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Email Catalog Dao.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since  June 20, 2010
 * @version $Id: $
 */

@Repository
public class CatEmailDao extends AbstractHibernateDaoSupport implements ICatEmail{

    /**
     * Find Emails by User
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CatListEmails> findListbyUser(final Long userId){
        return getHibernateTemplate().findByNamedParam(
                "from CatListEmails where usuarioEmail.uid= :userId", "userId", userId);
     }

    /**
     *
     * @param emailList
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CatEmails> findEmailsByListId(final Long idList){
        return getHibernateTemplate().findByNamedParam("FROM CatEmails WHERE idListEmail.idList= :idList", "idList", idList);
     }

    /**
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CatListEmails> findAllEmailList(){
        return getHibernateTemplate().find("FROM CatListEmails");
    }

    /**
     *
     * @return
     */
    public CatListEmails getListEmailById(final Long listEmailId){
        return (CatListEmails) getHibernateTemplate().get(CatListEmails.class,listEmailId);

    }

    /**
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CatListEmails> getListEmailsByKeyword(final String keyword, final Long userId){
        final DetachedCriteria criteria = DetachedCriteria.forClass(CatListEmails.class);
      //  criteria.add(Restrictions.like("listName", keyword, MatchMode.ANYWHERE));
        criteria.add(Restrictions.like("listName","%"+keyword));
        return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CatEmails> getEmailsByKeyword(final String keyword, final Long userId){
        final DetachedCriteria criteria = DetachedCriteria.forClass(CatEmails.class);
       // criteria.add(Restrictions.like("email", keyword, MatchMode.ANYWHERE));
        criteria.add(Restrictions.like("email", "%"+ keyword));
        return getHibernateTemplate().findByCriteria(criteria);

    }


}
