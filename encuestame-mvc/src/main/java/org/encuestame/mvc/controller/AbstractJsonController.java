/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.mvc.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.encuestame.core.security.SecurityUtils;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.core.util.DateUtil;
import org.encuestame.core.util.RelativeTimeEnum;
import org.encuestame.persistence.dao.INotification;
import org.encuestame.persistence.dao.imp.NotificationDao;
import org.encuestame.persistence.domain.notifications.NotificationEnum;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.security.ProfileUserAccount;
import org.encuestame.utils.web.UserAccountBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Abstract Json Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 15, 2010 11:31:20 AM
 * @version $Id:$
 */
public abstract class AbstractJsonController extends BaseController{

    /** Model. **/
    private ModelMap jsonMap = new ModelMap();

    /** {@link NotificationDao}. **/
    @Autowired
    private INotification notificationDao;

    /**
     * Domain.
     */
    // @Value("${application.domain}") private String appDomainSetted;

     /**
      * Tweet Path Vote.
      */
    // @Value("${answers.tweetPathVote}") private String tweetPathVote;

    /** Success. **/
    private Map<String, Object> sucess = new HashMap<String, Object>();

    /** Error Json. **/
    private Map<String, Object> error = new HashMap<String, Object>();

    /**
     * Return Data.
     * @return
     */
    protected ModelMap returnData(){
         Map<String, Object> response = new HashMap<String, Object>();
         Assert.notNull(this.sucess);
         Assert.notNull(this.error);
         response.put("success", this.sucess);
         response.put("error", this.error);
         return this.jsonMap.addAllAttributes(response);
    }

    /**
     * Relative Time.
     * @param date
     * @return
     */
    protected  HashMap<Integer, RelativeTimeEnum> getRelativeTime(final Date date){
         return DateUtil.getRelativeTime(date);
    }

    /**
     * Set Error.
     * @param error error.
     */
    @Deprecated
    protected void setError(final Object error){
         this.error.put("message", error);
         this.sucess =  new HashMap<String, Object>();
    }

    /**
     * Set Error.
     * @param error error.
     */
    protected void setError(final Object error,  final HttpServletResponse response){
         this.error = new HashMap<String, Object>();
         this.error.put("message", error);
         this.sucess =  new HashMap<String, Object>();
         response.setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    /**
     * Set List of Errors.
     * @param listError
     * @param response
     */
    protected void setError(final HashMap<String, Object> listError,  final HttpServletResponse response){
        this.error = new HashMap<String, Object>();
        this.error.put("message", listError);
        this.sucess =  new HashMap<String, Object>();
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
   }

    /**
     * Set Item Response.
     * @param label
     * @param item
     */
    protected void setItemResponse(final String label, final Object item){
         this.sucess.put(label, item);
    }

    /**
     * Set Item Response.
     * @param label
     * @param item
     */
    protected void setItemResponse(final Map<String, Object> map){
         this.error = new HashMap<String, Object>();
         this.sucess = map;
    }

    /**
     * Set Succes Response.
     */
    protected void setSuccesResponse(){
        final Map<String, Object> response = new HashMap<String, Object>();
        response.put("r", 0);
        setItemResponse(response);
    }

    /**
     * Set Item Read Store Response.
     * @param label label
     * @param id id
     * @param items items
     */
    protected void setItemReadStoreResponse(final String label, final String id, final Object items){
         final Map<String, Object> store = new HashMap<String, Object>();
         store.put("identifier", id);
         store.put("label", label);
         store.put("items", items);
         setItemResponse(store);
    }

    /**
     * Handler for {@link AccessDeniedException}
     * @param ex exception
     * @return {@link ModelAndView}.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleException (final AccessDeniedException ex, HttpServletResponse httpResponse) {
      log.error("handleException "+ ex.getMessage());
      httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
      ModelAndView mav = new ModelAndView();
      mav.setViewName("MappingJacksonJsonView");
      final Map<String, Object> response = new HashMap<String, Object>();
      response.put("message", ex.getMessage());
      //TODO: add internationalitazion
      response.put("description", "Application does not have permission for this action");
      response.put("status", httpResponse.SC_FORBIDDEN);
      response.put("session", SecurityUtils.checkIsSessionIsExpired(getSecCtx().getAuthentication()));
      response.put("anonymousUser", SecurityUtils.checkIsSessionIsAnonymousUser(getSecCtx().getAuthentication()));
      mav.addObject("error",  response);
      return mav;
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
           if(notificationEnum.equals(NotificationEnum.TWEETPOL_CREATED)){
               message = getMessage("notification.tweetpoll.created", request, null);
           } else if(notificationEnum.equals(NotificationEnum.TWEETPOL_REMOVED)){
               message = getMessage("notification.tweetpoll.removed", request, objects);
           } else if(notificationEnum.equals(NotificationEnum.TWEETPOLL_PUBLISHED)){
               message = getMessage("notification.tweetpoll.publish", request, null);
           }
           return message;
    }

    /**
     * Convert Relative Time Message.
     * @param relativeTimeEnum
     * @param number
     * @param request
     * @param objects
     * @return
     */
    public String convertRelativeTimeMessage(final RelativeTimeEnum relativeTimeEnum, final Integer number,
            final HttpServletRequest request){
        final StringBuilder builder = new StringBuilder();
        //builder.append(number);
        //builder.append(" ");
        log.debug("Convert Message Relative Time");
        log.debug(relativeTimeEnum);
        log.debug(number);
        String str[] = {number.toString()};
        if(relativeTimeEnum.equals(RelativeTimeEnum.ONE_SECOND_AGO)){
            builder.append(getMessage("relative.time.one.second.ago", request, str));
        } else if(relativeTimeEnum.equals(RelativeTimeEnum.SECONDS_AGO)) {
            builder.append(getMessage("relative.time.one.seconds.ago", request, str));
        } else if(relativeTimeEnum.equals(RelativeTimeEnum.A_MINUTE_AGO)) {
            builder.append(getMessage("relative.time.one.minute.ago", request, str));
        } else if(relativeTimeEnum.equals(RelativeTimeEnum.MINUTES_AGO)){
            builder.append(getMessage("relative.time.one.minutes.ago", request, str));
        } else if(relativeTimeEnum.equals(RelativeTimeEnum.AN_HOUR_AGO)){
            builder.append(getMessage("relative.time.one.hour.ago", request, str));
        } else if(relativeTimeEnum.equals(RelativeTimeEnum.HOURS_AGO)){
            builder.append(getMessage("relative.time.one.hours.ago", request, str));
        } else if(relativeTimeEnum.equals(RelativeTimeEnum.MONTHS_AGO)){
            builder.append(getMessage("relative.time.one.months.ago", request, str));
        } else if(relativeTimeEnum.equals(RelativeTimeEnum.ONE_MONTH_AGO)){
            builder.append(getMessage("relative.time.one.month.ago", request, str));
        } else if(relativeTimeEnum.equals(RelativeTimeEnum.ONE_YEAR_AGO)){
            builder.append(getMessage("relative.time.one.year.ago", request, str));
        } else if(relativeTimeEnum.equals(RelativeTimeEnum.YEARS_AGO)){
            builder.append(getMessage("relative.time.one.years.ago", request, str));
        }
        return builder.toString();
    }

    /**
     * Convert Notification Icon Message.
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
     * Get Url Domain.
     * @param request
     * @param realDomain
     * @return
     */
    public String getUrlDomain(final HttpServletRequest request, final Boolean realDomain){
        final StringBuilder builder = new StringBuilder();
        if(realDomain){
            String header = request.getHeader("X-Forwarded-Host");
            log.debug("Host header  "+header);
            if(header != null) {
                    // we are only interested in the first header entry
                    header = new StringTokenizer(header,",").nextToken().trim();
            }
            if(header == null) {
                    header = request.getHeader("Host");
            }
            builder.append(header);
            log.debug("Host "+builder.toString());
        } else {
            builder.append("/");
        }
        return builder.toString();
    }

    /**
     * Get User.
     * @param userId user Id.
     * @return
     * @throws EnMeNoResultsFoundException exception
     */
    public UserAccountBean getUser(final Long userId) throws EnMeNoResultsFoundException{
        Assert.notNull(userId);
        return getSecurityService().getUserCompleteInfo(userId, getUserPrincipalUsername());
    }

    /**
     * @return the notificationDao
     */
    public INotification getNotificationDao() {
        return notificationDao;
    }

    /**
     * @param notificationDao the notificationDao to set
     */
    public void setNotificationDao(INotification notificationDao) {
        this.notificationDao = notificationDao;
    }
}
