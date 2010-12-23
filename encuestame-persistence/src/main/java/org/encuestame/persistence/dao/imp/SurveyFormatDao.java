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
package org.encuestame.persistence.dao.imp;

import java.util.Date;
import java.util.List;

import org.encuestame.persistence.dao.ISurveyFormatDao;
import org.encuestame.persistence.domain.survey.SurveyFormat;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * SurveyFormat Dao.
 * @author Morales Urbina, Diana paola@encuestame.org
 * @since November 10, 2009
 * @version $Id$
 */
@Repository("surveyFormatDaoImp")
public class SurveyFormatDao extends AbstractHibernateDaoSupport implements ISurveyFormatDao{

	@Autowired
	public SurveyFormatDao(SessionFactory sessionFactory) {
	 		setSessionFactory(sessionFactory);
    }
	
     /**
     * Get User By Id.
     * @param idSidFormat idSidFormat
     * @return SurveyFormat
     * @throws HibernateException hibernate exception
     */
    public SurveyFormat getSurveyFormatById(final int idSidFormat) throws HibernateException{
        return (SurveyFormat) getSession().get(SurveyFormat.class, idSidFormat);
    }

    /**
     * Get User By Name.
     * @param name name
     * @return SurveyFormat
     * @throws HibernateException hibernate exception
     */
    public SurveyFormat getSurveyFormatbyname(final String name)throws HibernateException {
        return  (SurveyFormat) getSession()
        .createCriteria(SurveyFormat.class)
        .add(Restrictions.eq("formatname", name))
        .uniqueResult();
    }


    /**
     * Get SurveyFormat By Date.
     * @param startDate startDate
     * @param endDate endDate
     * @return SurveyFormat
     * @throws HibernateException hibernate exception
     */
    @SuppressWarnings("unchecked")
    public List<SurveyFormat> getSurveyFormatbyDate(final Date startDate, final Date endDate){

        return getSession().createQuery("FROM SurveyFormat WHERE date_created >=:start AND date_created<= :end")
                .setDate("start", startDate)
                .setDate("end", endDate)
                .list();
    }

}
