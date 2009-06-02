package org.jp.core.persistence.dao;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.jp.core.persistence.dao.imp.ISurvey;
import org.jp.core.persistence.pojo.SecUsers;
import org.jp.core.persistence.pojo.SurveyFormat;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * encuestame: system online surveys Copyright (C) 2009 encuestame Development
 * Team
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of version 3 of the GNU General Public License as published by the
 * Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * Id: SurveyDaoImp.java Date: 01/06/2009 13:12:44
 * 
 * @author juanpicado package: org.jp.core.persistence.dao
 * @version 1.0
 */
public class SurveyDaoImp extends HibernateDaoSupport implements ISurvey {

	private Log log = LogFactory.getLog(this.getClass());

	/**
	 * 
	 * 
	 * @param searchString
	 * @return
	 * @throws HibernateException
	 */
	public Collection<SurveyFormat> searchSurveyByName(String searchString)
			throws HibernateException {
		
		//List<SurveyFormat> survey = getHibernateTemplate().findByNamedQuery(
		//		"Survey.searchFormatByName", searchString);
		
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
