/**
 * encuestame:  system online surveys
 * Copyright (C) 2005-2008 encuestame Development Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of version 3 of the GNU General Public
 * License as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 */
package org.encuestame.core.persistence.dao;

import java.util.Date;
import java.util.List;

import org.encuestame.core.persistence.dao.imp.ISurveyFormatDao;
import org.encuestame.core.persistence.pojo.SurveyFormat;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

/**
 * SurveyFormat Dao.
 *
 * @author Morales Urbina, Diana paola@encuestame.org
 * @since November 10, 2009
 */
public class SurveyFormatDao extends AbstractHibernateDaoSupport implements ISurveyFormatDao{

     /**
     * Get User By Id.
     * @param idSidFormat idSidFormat
     * @return SurveyFormat
     * @throws HibernateException hibernate exception
     */
    public SurveyFormat getSurveyFormatById(final int idSidFormat) throws HibernateException{
        return (SurveyFormat) getEnMeSession().get(SurveyFormat.class, idSidFormat);
    }

    /**
     * Get User By Name.
     * @param name name
     * @return SurveyFormat
     * @throws HibernateException hibernate exception
     */
    public SurveyFormat getSurveyFormatbyname(final String name)throws HibernateException {
        return  (SurveyFormat) getEnMeSession()
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

        return getEnMeSession().createQuery("FROM SurveyFormat WHERE date_created >=:start AND date_created<= :end")
                .setDate("start", startDate)
                .setDate("end", endDate)
                .list();
    }

}
