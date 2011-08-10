/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.encuestame.business.service.AbstractSurveyService;
import org.encuestame.business.service.DashboardService;
import org.encuestame.business.service.FrontEndService;
import org.encuestame.business.service.ProjectService;
import org.encuestame.business.service.ServiceManager;
import org.encuestame.business.service.TweetPollService;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.security.util.HTMLInputFilter;
import org.encuestame.core.service.AbstractSecurityContext;
import org.encuestame.core.service.SecurityService;
import org.encuestame.core.service.imp.GeoLocationSupport;
import org.encuestame.core.service.imp.IDashboardService;
import org.encuestame.core.service.imp.IFrontEndService;
import org.encuestame.core.service.imp.IPictureService;
import org.encuestame.core.service.imp.IPollService;
import org.encuestame.core.service.imp.IProjectService;
import org.encuestame.core.service.imp.IServiceManager;
import org.encuestame.core.service.imp.ISurveyService;
import org.encuestame.core.service.imp.ITweetPollService;
import org.encuestame.core.service.imp.SearchServiceOperations;
import org.encuestame.core.service.imp.SecurityOperations;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.notifications.Notification;
import org.encuestame.persistence.domain.notifications.NotificationEnum;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.DateClasificatedEnum;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.RelativeTimeEnum;
import org.encuestame.utils.captcha.ReCaptcha;
import org.encuestame.utils.json.ProfileUserAccount;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.encuestame.utils.web.notification.UtilNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * Base Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 13, 2010 10:41:38 PM
 */
@SuppressWarnings("deprecation")
public abstract class AbstractBaseOperations extends AbstractSecurityContext{

     private Logger log = Logger.getLogger(this.getClass());

     /**
      * Simple date format.
      */
     public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_DATE);

     /**
      * Simple time format.
      */
     public static final SimpleDateFormat SIMPLE_TIME_FORMAT = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_TIME);

     /**
      * {@link ReCaptcha}.
      */
     private ReCaptcha reCaptcha;

    /**
     * {@link ServiceManager}.
     */
    @Autowired
    private IServiceManager serviceManager;

    /** Force Proxy Pass Enabled. **/
    @Value("${application.proxyPass}") private Boolean proxyPass;

    /**
     * {@link AuthenticationManager}.
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * @return the serviceManager
     */
    public IServiceManager getServiceManager() {
        return serviceManager;
    }

    /**
     * Get Current Request Attributes.
     * @return {@link RequestAttributes}
     */
    public RequestAttributes getContexHolder(){
         return RequestContextHolder.currentRequestAttributes();
    }

    /**
     * Get {@link ServletRequestAttributes}.
     * @return {@link ServletRequestAttributes}
     */
    public HttpServletRequest getServletRequestAttributes(){
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    /**
     * Get By Username.
     * @param username username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public UserAccount getByUsername(final String username) throws EnMeNoResultsFoundException{
        return getServiceManager().getApplicationServices().getSecurityService().getUserAccount(username);
    }

    /**
     * Get by username without Exceptions.
     * @param username user name
     * @return {@link UserAccount}.
     */
    public UserAccount findByUsername(final String username){
        return getServiceManager().getApplicationServices().getSecurityService().findUserByUserName(username);
    }

    /**
     * Fetch user account currently logged.
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public UserAccount getUserAccount() throws EnMeNoResultsFoundException{
        final UserAccount account = this.getByUsername(this.getUserPrincipalUsername());
        if (account == null) {
            log.fatal("user session not found ");
            throw new EnMeNoResultsFoundException("user not found");
        }
        return account;
    }

    /**
     * Create new tweetPoll.
     * @param question
     * @param hashtags
     * @param answers
     * @param user
     * @return
     * @throws EnMeExpcetion
     */
    @Deprecated
    public TweetPoll createTweetPoll(
            final String question,
            String[] hashtags,
            UserAccount user) throws EnMeExpcetion{
        //create new tweetPoll
        final TweetPollBean tweetPollBean = new TweetPollBean();
        tweetPollBean.getHashTags().addAll(fillListOfHashTagsBean(hashtags));
        // save create tweet poll
        tweetPollBean.setUserId(user.getAccount().getUid());
        tweetPollBean.setCloseNotification(Boolean.FALSE);
        tweetPollBean.setResultNotification(Boolean.FALSE);
        //tweetPollBean.setPublishPoll(Boolean.TRUE); // always TRUE
        tweetPollBean.setSchedule(Boolean.FALSE);
        return getTweetPollService().createTweetPoll(tweetPollBean, question, user);
    }

    /**
     *
     * @param tweetPollBean
     * @return
     * @throws EnMeExpcetion
     */
    public TweetPoll createTweetPoll(
            final TweetPollBean tweetPollBean) throws EnMeExpcetion{
        //create new tweetPoll
        log.debug("createTweetPoll Bean "+tweetPollBean.toString());
        return getTweetPollService().createTweetPoll(tweetPollBean,
                tweetPollBean.getQuestionBean().getQuestionName(),
                getUserAccount());
    }

    /**
     * Update tweetpoll
     * @param tweetPoll {@link TweetPoll}
     * @param question list of questions.
     * @param hashtags
     * @param answers
     * @param user
     * @return
     * @throws EnMeExpcetion
     */
    @Deprecated
    public TweetPoll updateTweetPoll(
         final Long tweetPollId,
         final String question,
         final String[] hashtags,
         final Long[] answers) throws EnMeExpcetion{
         final List<HashTagBean> hashtagsList = fillListOfHashTagsBean(hashtags);
         //return getTweetPollService().updateTweetPoll(tweetPollId, question, answers, hashtagsList);
         return null;
    }

    /**
     *
     * @param tweetPollBean
     * @return
     * @throws EnMeExpcetion
     */
    public TweetPoll updateTweetPoll(
            final TweetPollBean tweetPollBean) throws EnMeExpcetion{
            //final List<HashTagBean> hashtagsList = fillListOfHashTagsBean(hashtags);
            return getTweetPollService().updateTweetPoll(tweetPollBean);
       }

    /**
     * Get Ip Client.
     * @return ip
     */
    public String getIpClient(){
        log.debug("Force Proxy Pass ["+this.proxyPass+"]");
        String ip = getServletRequestAttributes().getRemoteAddr();
        log.debug("Force Proxy Pass ["+ip+"]");
        //FIXME: if your server use ProxyPass you need get IP from x-forwarder-for, we need create
        // a switch change for ProxyPass to normal get client Id.
        // Solution should be TOMCAT configuration.
        log.debug("X-getHeaderNames ["+ getServletRequestAttributes().getHeaderNames()+"]");
        if (this.proxyPass) {
            ip = getServletRequestAttributes().getHeader("X-FORWARDED-FOR");
            log.debug("X-FORWARDED-FOR ["+ip+"]");
        }
        return ip;
    }

    /**
     * Relative Time.
     * @param date
     * @return
     */
    @Deprecated
    protected  HashMap<Integer, RelativeTimeEnum> getRelativeTime(final Date date){
         return DateUtil.getRelativeTime(date);
    }


    /**
     *
     * @param tpbean
     * @param request
     */
    @Deprecated
    public void convertRelativeTime(final TweetPollBean tpbean, final HttpServletRequest request){
        final HashMap<Integer, RelativeTimeEnum> relativeTime = getRelativeTime(tpbean.getCreatedDateAt());
        final Iterator it = relativeTime.entrySet().iterator();
        while (it.hasNext()) {
            final Map.Entry<Integer, RelativeTimeEnum> e = (Map.Entry<Integer, RelativeTimeEnum>)it.next();
            log.debug("--"+e.getKey() + "**" + e.getValue());
            tpbean.setRelativeTime(convertRelativeTimeMessage(e.getValue(), e.getKey(), request));
        }
    }

   /**
    * Convert Relative Time Message.
    * @param relativeTimeEnum
    * @param number
    * @param request
    * @param objects
    * @return
    */
    @Deprecated
   public String convertRelativeTimeMessage(final RelativeTimeEnum relativeTimeEnum, final Integer number,
           final HttpServletRequest request){
       final StringBuilder builder = new StringBuilder();
       //builder.append(number);
       //builder.append(" ");
       log.debug("Convert Message Relative Time");
       log.debug(relativeTimeEnum);
       log.debug(number);
       String str[] = {number.toString()};
       if (relativeTimeEnum.equals(RelativeTimeEnum.ONE_SECOND_AGO)) {
           builder.append(getMessage("relative.time.one.second.ago", request, str));
       } else if(relativeTimeEnum.equals(RelativeTimeEnum.SECONDS_AGO)) {
           builder.append(getMessage("relative.time.one.seconds.ago", request, str));
       } else if(relativeTimeEnum.equals(RelativeTimeEnum.A_MINUTE_AGO)) {
           builder.append(getMessage("relative.time.one.minute.ago", request, str));
       } else if(relativeTimeEnum.equals(RelativeTimeEnum.MINUTES_AGO)) {
           builder.append(getMessage("relative.time.one.minutes.ago", request, str));
       } else if(relativeTimeEnum.equals(RelativeTimeEnum.AN_HOUR_AGO)) {
           builder.append(getMessage("relative.time.one.hour.ago", request, str));
       } else if(relativeTimeEnum.equals(RelativeTimeEnum.HOURS_AGO)) {
           builder.append(getMessage("relative.time.one.hours.ago", request, str));
       } else if(relativeTimeEnum.equals(RelativeTimeEnum.MONTHS_AGO)) {
           builder.append(getMessage("relative.time.one.months.ago", request, str));
       } else if(relativeTimeEnum.equals(RelativeTimeEnum.ONE_MONTH_AGO)) {
           builder.append(getMessage("relative.time.one.month.ago", request, str));
       } else if(relativeTimeEnum.equals(RelativeTimeEnum.ONE_YEAR_AGO)) {
           builder.append(getMessage("relative.time.one.year.ago", request, str));
       } else if(relativeTimeEnum.equals(RelativeTimeEnum.YEARS_AGO)) {
           builder.append(getMessage("relative.time.one.years.ago", request, str));
       }
       return builder.toString();
   }



    /**
     * Get Message with Locale.
     * @param message
     * @param request
     * @param args
     * @return
     */

    public String getMessage(final String message,
            final HttpServletRequest request, Object[] args) {
        String stringValue = "";
        try {
            stringValue = getServiceManager().getMessageSource().getMessage(
                    message, args, getLocale(request));
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace(); //TODO: ENCUESTAME-223 - OPEN
        }
        return stringValue;
    }

    /**
     * Get Message.
     * @param message
     * @param args
     * @return
     */
    //@Deprecated
    public String getMessage(final String message, Object[] args){
        return getMessage(message, null, args);
    }

    /**
     * Get Message.
     * @param message
     * @return
     */
    //@Deprecated
    public String getMessage(final String message){
        return getMessage(message, null, null);
    }

    /**
     * Get Locale.
     * @param request
     * @return
     */
    private Locale getLocale(final HttpServletRequest request) {
        if(request == null){
            return Locale.ENGLISH;
        } else {
            return RequestContextUtils.getLocale(request);
        }
    }

    /**
     * Filter Value.
     * @param value value.
     * @return
     */
    public String filterValue(String value){
        final HTMLInputFilter vFilter = new HTMLInputFilter(true);
        return vFilter.filter(value);
    }

    /**
     * @param serviceManager
     *            the serviceManager to set
     */
    public void setServiceManager(IServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    /**
     * Get {@link AbstractSurveyService}.
     * @return survey service
     */
    public ISurveyService getSurveyService(){
        return getServiceManager().getApplicationServices().getSurveyService();
    }

    /**
     * Get {@link DashboardService}
     * @return
     */
    public IDashboardService getDashboardService(){
        return getServiceManager().getApplicationServices().getDashboardService();
    }

    /**
     * Get {@link SecurityService}.
     * @return
     */
    public SecurityOperations getSecurityService(){
        return getServiceManager().getApplicationServices().getSecurityService();
    }

    /**
     * Get {@link SearchServiceOperations}.
     * @return
     */
    public SearchServiceOperations getSearchService(){
        return getServiceManager().getApplicationServices().getSearchService();
    }

    /**
     * Location Service.
     * @return
     */
    public GeoLocationSupport getLocationService(){
        return getServiceManager().getApplicationServices().getLocationService();
    }

    /**
     * Get {@link TweetPollService}.
     * @return
     */
    public ITweetPollService getTweetPollService(){
        return getServiceManager().getApplicationServices().getTweetPollService();
    }

    public IPollService getPollService(){
        return getServiceManager().getApplicationServices().getPollService();
    }

    /**
     * Get {@link ProjectService}.
     * @return
     */
    public IProjectService getProjectService(){
        return getServiceManager().getApplicationServices().getProjectService();
    }

    /**
     * Get {@link FrontEndService}.
     * @return
     */
    public IFrontEndService getFrontService(){
        return getServiceManager().getApplicationServices().getFrontEndService();
    }

    /**
     * Get Picture Service.
     * @return
     */
    public IPictureService getPictureService(){
        return getServiceManager().getApplicationServices().getPictureService();
    }

    /**
     * @return the authenticationManager
     */
    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    /**
     * @param authenticationManager the authenticationManager to set
     */
    public void setAuthenticationManager(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    /**
     * @return the reCaptcha
     */
    public ReCaptcha getReCaptcha() {
        return reCaptcha;
    }

    /**
     * @param reCaptcha
     *            the reCaptcha to set
     */
    @Autowired
    public void setReCaptcha(final ReCaptcha reCaptcha) {
        this.reCaptcha = reCaptcha;
    }

    /**
     * Get Format Date.
     * @param date
     * @return
     */
    public Date getFormatDate(final String date){
        Assert.assertNotNull(date);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_DATE);
        simpleDateFormat.format(DateUtil.DEFAULT_FORMAT_DATE);
        return simpleDateFormat.getCalendar().getTime();
    }

    /**
     * Get full profile logged user info.
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public ProfileUserAccount getProfileUserInfo() throws EnMeNoResultsFoundException{
        return ConvertDomainBean.convertUserAccountToUserProfileBean(getUserAccount());
    }

    /**
     * Create question with answers.
     * @param questionName question description
     * @param user {@link UserAccount} owner.
     * @return {@link Question}
     * @throws EnMeExpcetion exception
     */
    public Question createQuestion(final String questionName, final String[] answers, final UserAccount user) throws EnMeExpcetion{
        final QuestionBean questionBean = new QuestionBean();
        questionBean.setQuestionName(questionName);
        questionBean.setUserId(user.getUid());
        // setting Answers.
        for (int row = 0; row < answers.length; row++) {
            final QuestionAnswerBean answer = new QuestionAnswerBean();
            answer.setAnswers(answers[row].trim());
            answer.setAnswerHash(RandomStringUtils.randomAscii(5));
            questionBean.getListAnswers().add(answer);
        }
        final Question questionDomain = getSurveyService().createQuestion(
                questionBean);
        return questionDomain;
    }

    /**
     * Create a list of {@link HashTagBean}.
     * @param hashtags array of hashtags strings.
     * @return list of {@link HashTagBean}.
     */
    public List<HashTagBean> fillListOfHashTagsBean(String[] hashtags) {
        final List<HashTagBean> hashtagsList = new ArrayList<HashTagBean>();
        hashtags = hashtags == null ? new String[0] : hashtags;
        log.debug("HashTag size:{" + hashtags.length);
        for (int row = 0; row < hashtags.length; row++) {
            final HashTagBean hashTag = new HashTagBean();
            if (hashtags[row] != null) {
                log.debug("HashTag:{" + hashTag);
                hashTag.setHashTagName(hashtags[row].toLowerCase().trim());
                hashtagsList.add(hashTag);
            }
        }
        return hashtagsList;
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
    * If is not complete check and validate current status.
    * @param tweetPoll
    */
   public void checkTweetPollStatus(final TweetPoll tweetPoll){
       if (!tweetPoll.getCompleted()) {
           getTweetPollService().checkTweetPollCompleteStatus(tweetPoll);
       }
   }

   /**
    *
    * @return
    */
   public Boolean isSocialSignInUpEnabled(){
       return EnMePlaceHolderConfigurer.getBooleanProperty("application.social.signin.enabled");
   }
}
