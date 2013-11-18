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

import java.util.Date;
import java.util.List;

import org.encuestame.persistence.dao.IScheduled;
import org.encuestame.persistence.domain.Schedule;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.enums.Status;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * {@link PollDao} Interface.
 * @author Morales,Diana Paola paolaATencuestame.org
 * @since  October 14, 2013
 */
@Repository("scheduledDao")
public class ScheduleDao  extends AbstractHibernateDaoSupport implements IScheduled{


    @Autowired
    public ScheduleDao(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }


	@SuppressWarnings("unchecked")
	public Date retrieveMinimumScheduledDate(final Status status) {
		final DetachedCriteria criteria = DetachedCriteria
				.forClass(Schedule.class);
		criteria.setProjection(Projections.min("scheduleDate"));
		List<Date> results = getHibernateTemplate().findByCriteria(criteria);
		return (results.get(0) == null ? new Date() : results.get(0));

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.encuestame.persistence.dao.IScheduled#retrieveScheduled(org.encuestame
	 * .utils.enums.Status)
	 */
	@SuppressWarnings("unchecked")
	public List<Schedule> retrieveScheduled(final Status status, final Date minimumDate) {
 		System.out.println("Minimun date ---> " + minimumDate);
		final DetachedCriteria criteria = DetachedCriteria
				.forClass(Schedule.class);
		// Between Minimun date and currently date
		criteria.add(Restrictions.between("scheduleDate", minimumDate, DateUtil.getCurrentCalendarDate()));
  		criteria.add(Restrictions.eq("status", status));
		return getHibernateTemplate().findByCriteria(criteria);
	}


	// Recuperar registros con counter menor o igual que y status succes
 	public List<Schedule> retrieveScheduledFailed(){

		return null;

	}

}
