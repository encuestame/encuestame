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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.encuestame.core.persistence.dao.imp.ISurvey;
import org.encuestame.core.persistence.pojo.SurveyFormat;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

/**
 * Survey Dao.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since June 01, 2009
 */
public class SurveyDaoImp extends AbstractHibernateDaoSupport implements ISurvey {

    /**
     * Search survey by name.
     * @param searchString
     * @return
     * @throws HibernateException
     */
    public Collection<SurveyFormat> searchSurveyByName(String searchString)
            throws HibernateException {
        Criteria crit = getSession().createCriteria(SurveyFormat.class);
        crit.add(Restrictions.like("name","%"+searchString+"%"));
        crit.setMaxResults(10);
        List<SurveyFormat> survey = crit.list();
        log.info("Encuestas Encontradas->" + survey);
        if (survey != null) {
            return survey;
        } else {
            return survey = new LinkedList<SurveyFormat>();
        }
    }
}
