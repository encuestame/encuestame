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
package org.encuestame.core.persistence.dao;

import java.util.Collection;

import org.encuestame.core.persistence.dao.imp.ISurvey;
import org.encuestame.core.persistence.pojo.SurveyFormat;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Survey Dao.
 * @author Picado, Juan juan@encuestame.org
 * @since June 01, 2009
 * @version $Id$
 */
@Repository
public class SurveyDaoImp extends AbstractHibernateDaoSupport implements ISurvey {

    /**
     * Search survey by name.
     * @param searchString search string
     * @return {@link Collection} of {@link SurveyFormat}
     * @throws HibernateException
     */
    @SuppressWarnings("unchecked")
    public Collection<SurveyFormat> searchSurveyByName(String searchString)
            throws HibernateException {
        return getSession().createCriteria(SurveyFormat.class)
        .add(Restrictions.like("name","%"+searchString+"%"))
        .setMaxResults(10).list();

    }
}
