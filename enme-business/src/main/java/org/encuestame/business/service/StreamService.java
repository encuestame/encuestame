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
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;
import org.encuestame.core.service.AbstractBaseService;
import org.encuestame.core.service.imp.StreamOperations;
import org.encuestame.persistence.domain.notifications.Notification;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.DateClasificatedEnum;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.enums.NotificationEnum;
import org.encuestame.utils.web.notification.UtilNotification;
import org.springframework.stereotype.Service;

/**
 * Service realed with stream activity and notifications.
 * @author Picado, Juan juanATencuestame.org
 * @since 12/08/2011
 */
@Service
public class StreamService extends AbstractBaseService implements StreamOperations {


    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.StreamOperations#retrieveLastNotifications(java.lang.Integer, javax.servlet.http.HttpServletRequest)
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
            final Integer limit, final Boolean onlyReaded,  final HttpServletRequest request)
            throws EnMeNoResultsFoundException {
         UserAccount user = getUserAccount(getUserPrincipalUsername());
         final List<Notification> notifications = getNotificationDao()
                 .loadNotificationByUserAndLimit(user.getAccount(), limit, 0,
                         onlyReaded);
         return convertNotificationList(notifications, request);
    }


    /**
     *
     * @param limit
     * @return
  * @throws EnMeNoResultsFoundException
     */
    public List<UtilNotification> loadNotificationByUserAndLimit(
            final Integer limit,
            final Integer start,
            final Boolean onlyUnread,
            final HttpServletRequest request) throws EnMeNoResultsFoundException {
         final List<Notification> notifications = getNotificationDao()
                 .loadNotificationByUserAndLimit(
                         getUserAccount(getUserPrincipalUsername()).getAccount(), limit, start, onlyUnread);
         return convertNotificationList(notifications, request);
    }

    /**
     * Classify notifications by {@link DateClasificatedEnum}.
     */
    @SuppressWarnings("unchecked")
    public HashMap<DateClasificatedEnum, List<UtilNotification>> classifyNotificationList(
            final List<UtilNotification> utilNotifications) {
        final HashMap<DateClasificatedEnum, List<UtilNotification>> response = new HashMap<DateClasificatedEnum, List<UtilNotification>>();
        for (UtilNotification utilNotification : utilNotifications) {
            //TODO: ENCUESTAME-233
            log.debug(utilNotification.toString());
        }
        //TODO: by default awaiting ENCUESTAME-233.
        response.put(DateClasificatedEnum.TODAY, utilNotifications);
        response.put(DateClasificatedEnum.LAST_MONTH, ListUtils.EMPTY_LIST);
        response.put(DateClasificatedEnum.FEW_MONTHS_AGO, ListUtils.EMPTY_LIST);
        response.put(DateClasificatedEnum.LAST_YEAR, ListUtils.EMPTY_LIST);
        response.put(DateClasificatedEnum.LONG_TIME_AGO, ListUtils.EMPTY_LIST);
        response.put(DateClasificatedEnum.THIS_MONTH, ListUtils.EMPTY_LIST);
        response.put(DateClasificatedEnum.THIS_WEEK, ListUtils.EMPTY_LIST);
        return response;
    }

    /**
     * Convert {@link Notification} icon message.
     * @param notificationEnum
     * @return
     */
   public String convertNotificationIconMessage(final NotificationEnum notificationEnum){
       String icon = null;
       /*
        * Help: helpImage
        * Error Network: netWorkErrorImage
        * Like: likeImage
        * Warning: warningImage
        * Unlike: unLikeImage
        * Twitter: twitterImage
        * Poll: pollImage
        */
       if(notificationEnum.equals(NotificationEnum.TWEETPOL_CREATED)){
           icon = "twitterImage";
       } else if(notificationEnum.equals(NotificationEnum.TWEETPOL_REMOVED)){
           icon = "warningImage";
       } else if(notificationEnum.equals(NotificationEnum.TWEETPOLL_PUBLISHED)){
           icon = "twitterImage";
       }
       return icon;
   }

   /**
    * Convert Notification Message.
    * @param notificationEnum
    * @param request
    * @param objects
    * @return
    */
   public String convertNotificationMessage(final NotificationEnum notificationEnum,
           final HttpServletRequest request, final Object[] objects){
          String message = null;
          if(notificationEnum.equals(NotificationEnum.TWEETPOL_CREATED)) {
              message = getMessage("notification.tweetpoll.created", request, null);
          } else if(notificationEnum.equals(NotificationEnum.TWEETPOL_REMOVED)) {
              message = getMessage("notification.tweetpoll.removed", request, objects);
          } else if(notificationEnum.equals(NotificationEnum.TWEETPOLL_PUBLISHED)) {
              message = getMessage("notification.tweetpoll.publish", request, null);
          } else if(notificationEnum.equals(NotificationEnum.SOCIAL_MESSAGE_PUBLISHED)) {
              message = getMessage("notification.social.tweet.published", request, objects);
          }
          return message;
   }

    /**
    *
    * @param notification
    * @param request
    * @return
    */
   public UtilNotification convertNotificationToBean(
           final Notification notification, final HttpServletRequest request) {
        final UtilNotification utilNotification = new UtilNotification();
        utilNotification.setDate(DateUtil.SIMPLE_DATE_FORMAT.format(notification.getCreated()));
        utilNotification.setDescription(this.convertNotificationMessage(notification.getDescription(), request, new Object[]{}));
        utilNotification.setId(notification.getNotificationId());
        utilNotification.setHour(DateUtil.SIMPLE_TIME_FORMAT.format(notification.getCreated()));
        utilNotification.setIcon(convertNotificationIconMessage(notification.getDescription()));
        utilNotification.setType(notification.getDescription().name());
        utilNotification.setUrl(notification.getUrlReference());
        utilNotification.setAdditionalDescription(notification.getAdditionalDescription());
        return utilNotification;
   }

    /**
     * Convert a List of {@link Notification} on a List of {@link UtilNotification}.
     * @param notifications List of {@link Notification}.
     * @param request {@link HttpServletRequest}
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
