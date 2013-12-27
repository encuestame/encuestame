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

import org.encuestame.persistence.dao.INotification;
import org.encuestame.persistence.domain.notifications.Notification;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Notification Dao.
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 18, 2010 7:10:33 PM
 * @version $Id:$
 */
@Repository("notificationDao")
public class NotificationDao extends AbstractHibernateDaoSupport implements INotification {

    /**
     * Constructor.
     * @param sessionFactory {@link SessionFactory}.
     */
    @Autowired
    public NotificationDao(final SessionFactory sessionFactory) {
             setSessionFactory(sessionFactory);
    }

    /**
     * Load Notifications By {@link Account} and Limit. This method add all notifications without User (global)
     * @param secUser {@link Account}
     * @param limit limit
     * @return
     */
    @SuppressWarnings("unchecked")
    public final List loadNotificationByUserAndLimit(
            final Account user,
            final Integer limit,
            final Integer start,
            final Boolean onlyUnread){
         final DetachedCriteria criteria = DetachedCriteria.forClass(Notification.class);
            criteria.add(Restrictions.or(Restrictions.eq("account", user), Restrictions.isNull("account")));
            if (onlyUnread) {
                criteria.add(Restrictions.eq("readed", Boolean.FALSE));
            }
            criteria.addOrder(Order.desc("created"));
            return getHibernateTemplate().findByCriteria(criteria, start, limit);
    }

    /*
     *  (non-Javadoc)
     * @see org.encuestame.persistence.dao.INotification#loadNotificationByDate(org.encuestame.persistence.domain.security.Account, java.lang.Integer, java.lang.Integer, java.util.Date, java.util.Date, java.lang.Boolean)
     */
    @SuppressWarnings("unchecked")
    public final List loadNotificationByDate(
            final Account user,
            final Integer limit,
            final Integer start,
            final Date startDate,
            final Date endDate,
            final Boolean onlyUnread) {
         final DetachedCriteria criteria = DetachedCriteria.forClass(Notification.class);
            criteria.add(Restrictions.or(Restrictions.eq("account", user), Restrictions.isNull("account")));
            criteria.add(Restrictions.between("created", startDate, endDate));
            criteria.addOrder(Order.desc("created"));
            return getHibernateTemplate().findByCriteria(criteria, start, limit);
    }

    /**
     * Retrieve Notification Status
     * @param secUser
     * @return
     */
    public final Long retrieveTotalNotificationStatus(final Account secUser) {
        return retrieveCountNotification(secUser, "select count(*) from Notification WHERE account = :secUser");
    }

    /**
     * Retrieve Count Notification.
     * @param secUser
     * @param query
     * @return
     */
    @SuppressWarnings("unchecked")
    private Long retrieveCountNotification(final Account account, final String query) {
        Long resultsSize = 0L;
        final List<Object> list =  getHibernateTemplate().findByNamedParam("select count(*) from Notification "
                +" WHERE account =:user", "user", account);
        if (list.get(0) instanceof Long) {
            resultsSize = (Long) list.get(0);
        }
        return resultsSize;
    }

    /**
     * Retrieve total not readed notification status.
     * @param user {@link UserAccount}.
     * @return total not readed notification.
     */
    @SuppressWarnings("unchecked")
    public final Long retrieveTotalNotReadedNotificationStatus(final Account user) {
         final DetachedCriteria crit = DetachedCriteria.forClass(Notification.class);
         crit.setProjection(Projections.rowCount());
         crit.add(Restrictions.eq("readed", Boolean.FALSE));
         final List results = getHibernateTemplate().findByCriteria(crit);
         log.debug("retrieveTotalNotReadedNotificationStatus "+results.size());
         return (Long) (results.get(0) == null ? 0 : results.get(0));
    }

    /**
     * Get Notification.
     * @param notificationId
     * @return
     */
    public final Notification retrieveNotificationById(final Long notificationId){
        return (Notification) getHibernateTemplate().get(Notification.class, notificationId);
    }
}
