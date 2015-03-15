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
package org.encuestame.business.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.encuestame.core.service.AbstractBaseService;
import org.encuestame.core.service.imp.StreamOperations;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.persistence.dao.imp.NotificationDao;
import org.encuestame.persistence.domain.notifications.Notification;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.enums.DateClasificatedEnum;
import org.encuestame.utils.enums.NotificationEnum;
import org.encuestame.utils.web.notification.UtilNotification;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service realed with stream activity and notifications.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since 12/08/2011
 */
@Service
@Transactional
public class StreamService extends AbstractBaseService implements StreamOperations {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Define a limit of result to retrieve by category
     */
    @Value("${not.list.max.by.category}")
    private Integer maxByCategory;

    /**
     * default start
     */
    private static final Integer defaultStart = 0;

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.core.service.imp.StreamOperations#retrieveLastNotifications
     * (java.lang.Integer, javax.servlet.http.HttpServletRequest)
     */
    public List<UtilNotification> retrieveLastNotifications(
            final Integer limit, final HttpServletRequest request)
            throws EnMeNoResultsFoundException {
        return this.retrieveLastNotifications(limit, Boolean.TRUE, request);
    }

    /**
     *
     * @param limit
     * @param onlyReaded
     * @param request
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public List<UtilNotification> retrieveLastNotifications(
            final Integer limit, final Boolean onlyReaded,
            final HttpServletRequest request)
            throws EnMeNoResultsFoundException {
        UserAccount user = getUserAccount(getUserPrincipalUsername());
        final List<Notification> notifications = getNotificationDao()
                .loadNotificationByUserAndLimit(user.getAccount(), limit,
                        defaultStart, onlyReaded);
        return convertNotificationList(notifications, request);
    }

    /**
     *
     * @param limit
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public List<UtilNotification> loadNotificationByUserAndLimit(
            final Integer limit, final Integer start, final Boolean onlyUnread,
            final HttpServletRequest request)
            throws EnMeNoResultsFoundException {
        final List<Notification> notifications = getNotificationDao()
                .loadNotificationByUserAndLimit(
                        getUserAccount(getUserPrincipalUsername()).getAccount(),
                        limit, start, onlyUnread);
        return convertNotificationList(notifications, request);
    }

    /**
     *
     * @param daysFromStart
     * @return
     * @throws EnMeNoResultsFoundException
     */
    private List<Notification> filterByDate(final Date daysFromStart, final Date daysFromEnd) throws EnMeNoResultsFoundException{
        final Account account = getUserAccount(getUserPrincipalUsername()).getAccount();
         final List<Notification> list = getNotificationDao()
                 .loadNotificationByDate(
                         account,
                         this.maxByCategory,
                         EnMeUtils.DEFAULT_START,
                         daysFromStart,
                         daysFromEnd, false);
         return list;
    }

    /**
     * Classify notifications by {@link DateClasificatedEnum}.
     * @throws EnMeNoResultsFoundException
     */
    public HashMap<DateClasificatedEnum, List<UtilNotification>> classifyNotificationList(
            final List<UtilNotification> utilNotifications,
            final HttpServletRequest request)
            throws EnMeNoResultsFoundException {
        final HashMap<DateClasificatedEnum, List<UtilNotification>> response = new HashMap<DateClasificatedEnum, List<UtilNotification>>();
        // today notification
        final List<Notification> todayNotifications = filterByDate(DateUtil.getTodayStartDate(), DateUtil.getNextDayMidnightDate());
        // this week (minus today)
        DateTime thisWeek = new DateTime(DateUtil.getTodayStartDate());
        thisWeek = thisWeek.minusMinutes(1);
        Date finalThisWeek = thisWeek.minusWeeks(1).toDate();
        final List<Notification> thisWeekList = filterByDate(finalThisWeek, thisWeek.toDate());
        // this months notifications
        final DateTime finalThisWeekMinusOne = new DateTime(finalThisWeek).minusMinutes(1);
        final DateTime finalThisMonth = finalThisWeekMinusOne.minusWeeks(3);
        final List<Notification> thisMonth = filterByDate(finalThisMonth.toDate(), finalThisWeekMinusOne.toDate());
        // last months notifications
        final DateTime lastMontFinal = finalThisMonth.minusMinutes(1);
        final DateTime lastMontInit = lastMontFinal.minusDays(DateClasificatedEnum.THIS_MONTH.toNumber());
        final List<Notification> lastMonth = filterByDate(lastMontInit.toDate(), lastMontFinal.toDate());
        // few months ago
        final DateTime lastFewMontFinal = lastMontInit.minusMinutes(1);
        final DateTime lastFewMontInit = lastFewMontFinal.minusMonths(4); // 4 months, 4 + (1) last + 1 (current) = 6
        final List<Notification> fewMonth = filterByDate(lastFewMontInit.toDate(), lastFewMontFinal.toDate());
        // last year
        final DateTime lastYearFinal = lastFewMontInit.minusMinutes(1);
        final DateMidnight lastYeaInit = new DateMidnight(lastYearFinal).withWeekOfWeekyear(1).withDayOfWeek(1);
        final List<Notification> lastYear = filterByDate(lastYeaInit.toDate(), lastYearFinal.toDate());
        // long time ago
        final DateTime longTimeFinal = new DateTime(lastYeaInit).minusMinutes(1);
        final DateMidnight longTimeInit = new DateMidnight(longTimeFinal).minusYears(3).withWeekOfWeekyear(1).withDayOfWeek(1);
        final List<Notification> longtime = filterByDate(longTimeInit.toDate(), longTimeFinal.toDate());
        response.put(DateClasificatedEnum.TODAY, convertNotificationList(todayNotifications, request));
        response.put(DateClasificatedEnum.THIS_WEEK, convertNotificationList(thisWeekList, request));
        response.put(DateClasificatedEnum.THIS_MONTH, convertNotificationList(thisMonth, request));
        response.put(DateClasificatedEnum.LAST_MONTH, convertNotificationList(lastMonth, request));
        response.put(DateClasificatedEnum.FEW_MONTHS_AGO, convertNotificationList(fewMonth, request));
        response.put(DateClasificatedEnum.LAST_YEAR, convertNotificationList(lastYear, request));
        response.put(DateClasificatedEnum.LONG_TIME_AGO, convertNotificationList(longtime, request));
        return response;
    }

    /**
     * Convert {@link Notification} icon message.
     *
     * @param notificationEnum
     * @return
     */
    public String convertNotificationIconMessage(
            final NotificationEnum notificationEnum) {
        String icon = null;
        /*
         * Help: helpImage Error Network: netWorkErrorImage Like: likeImage
         * Warning: warningImage Unlike: unLikeImage Twitter: twitterImage Poll:
         * pollImage
         */
        if (notificationEnum.equals(NotificationEnum.TWEETPOL_CREATED)) {
            icon = "twitterImage";
        } else if (notificationEnum.equals(NotificationEnum.TWEETPOL_REMOVED)) {
            icon = "warningImage";
        } else if (notificationEnum
                .equals(NotificationEnum.TWEETPOLL_PUBLISHED)) {
            icon = "twitterImage";
        }
        return icon;
    }

    /**
     * Convert Notification Message.
     *
     * @param notificationEnum
     * @param request
     * @param objects
     * @return
     */
    public String convertNotificationMessage(
            final NotificationEnum notificationEnum,
            final HttpServletRequest request, final Object[] objects) {
        String message = null;
        if (notificationEnum.equals(NotificationEnum.TWEETPOL_CREATED)) {
            message = getMessage("notification.tweetpoll.created", request,
                    null);
        } else if (notificationEnum.equals(NotificationEnum.TWEETPOL_REMOVED)) {
            message = getMessage("notification.tweetpoll.removed", request,
                    objects);
        } else if (notificationEnum
                .equals(NotificationEnum.TWEETPOLL_PUBLISHED)) {
            message = getMessage("notification.tweetpoll.publish", request,
                    null);
        } else if (notificationEnum
                .equals(NotificationEnum.SOCIAL_MESSAGE_PUBLISHED)) {
            message = getMessage("notification.social.tweet.published",
                    request, objects);
        } else if (notificationEnum
                .equals(NotificationEnum.POLL_CREATED)) {
            message = getMessage("notification.poll.created",
                    request, objects);
        } else if (notificationEnum
                .equals(NotificationEnum.POLL_PUBLISHED)) {
            message = getMessage("notification.poll.publish",
                    request, objects);
        }
        return message;
    }

    /**
     * Convert {@link NotificationDao} to {@link UtilNotification}
     * @param notification
     * @param request
     * @return
     */
    public UtilNotification convertNotificationToBean(
            final Notification notification, final HttpServletRequest request) {
        final UtilNotification utilNotification = new UtilNotification();
        // If the creation date is within the range of 48 hours shown the
        // relative date otherwise the original date.
        //FIXME: this must be work for the front-side (moment.js)
        if(DateUtil.isWithinCurrentDate(notification.getCreated())){
            utilNotification.setDate(this.convertRelativeTimeToString(notification.getCreated(), request));
        }
        else {
            utilNotification.setDate(DateUtil.SIMPLE_DATE_FORMAT.format(notification.getCreated()));
        }
        utilNotification.setRealDate(notification.getCreated());
        utilNotification.setDescription(this.convertNotificationMessage(
                notification.getDescription(), request, new Object[] {}));
        utilNotification.setId(notification.getNotificationId());
        utilNotification.setHour(DateUtil.SIMPLE_TIME_FORMAT
                .format(notification.getCreated()));
        utilNotification.setIcon(convertNotificationIconMessage(notification
                .getDescription()));
        utilNotification.setType(notification.getDescription().name());
        utilNotification.setUrl(notification.getUrlReference());
        utilNotification.setReaded(notification.getReaded());
        utilNotification.setAdditionalDescription(notification
                .getAdditionalDescription());
        return utilNotification;
    }

    /**
     * Convert a List of {@link Notification} on a List of
     * {@link UtilNotification}.
     *
     * @param notifications
     *            List of {@link Notification}.
     * @param request
     *            {@link HttpServletRequest}
     * @return
     */
    public List<UtilNotification> convertNotificationList(
            final List<Notification> notifications,
            final HttpServletRequest request) {
        final List<UtilNotification> utilNotifications = new ArrayList<UtilNotification>();
        for (Notification notification : notifications) {
            utilNotifications.add(convertNotificationToBean(notification,
                    request));
        }
        return utilNotifications;
    }
}
