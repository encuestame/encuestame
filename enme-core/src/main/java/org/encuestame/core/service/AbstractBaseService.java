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
package org.encuestame.core.service;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.service.imp.MailServiceOperations;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.Email;
import org.encuestame.persistence.domain.EmailList;
import org.encuestame.persistence.domain.EmailSubscribe;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.notifications.Notification;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMePollNotFoundException;
import org.encuestame.persistence.exception.EnMeTweetPollNotFoundException;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.MD5Utils;
import org.encuestame.utils.ValidationUtils;
import org.encuestame.utils.enums.HashTagRate;
import org.encuestame.utils.enums.NotificationEnum;
import org.encuestame.utils.enums.RelativeTimeEnum;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.HomeBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.web.UnitEmails;
import org.encuestame.utils.web.UnitLists;
import org.encuestame.utils.web.UserAccountBean;
import org.encuestame.utils.web.stats.HashTagDetailStats;
import org.hibernate.HibernateException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.servlet.support.RequestContextUtils;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.http.AccessToken;

/**
 * Service.
 * @author Picado, Juan juanATencuestame.org
 * @since 22/05/2009 1:02:45
 */
@Service
public abstract class AbstractBaseService extends AbstractDataSource {

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * {@link MessageSourceFactoryBean}.
     */
    @Autowired
    private MessageSourceFactoryBean messageSourceFactoryBean;

    /**
     *  {@link MailService}.
     */
    @Resource()
    private MailServiceOperations mailService;

    /**
     * Constructor.
     */
    public AbstractBaseService() {}


    /**
     * Create hashTag details stats.
     *
     * @param label
     * @param value
     * @param subLabel
     * @return
    */
    public HashTagDetailStats createTagDetailsStats(final String label,
                final Long value, final String subLabel) {
        final HashTagDetailStats tagDetails = new HashTagDetailStats();
        tagDetails.setLabel(label);
        tagDetails.setValue(value);
        tagDetails.setSubLabel(subLabel);
    return tagDetails;
    }

    /**
     *
     * @param tagRate
     * @param request
     * @param objects
     * @return
     */
    public String convertHashTagButtonStatsLabel(final HashTagRate tagRate,
            final HttpServletRequest request, final Object[] objects) {
        String message = null;
        if (tagRate.equals(HashTagRate.LBL_USAGE)) {
            message = getMessage("hashtag.stats.usage.label.hits", request,
                    null);
        } else if (tagRate.equals(HashTagRate.LBL_HITS)) {
            message = getMessage("hashtag.stats.usage.label.visited", request,
                    null);
        } else if (tagRate.equals(HashTagRate.LBL_VOTES)) {
            message = getMessage("hashtag.stats.usage.label.voted", request,
                    null);
        } else if (tagRate.equals(HashTagRate.LBL_SOCIAL_NETWORK)) {
            message = getMessage("hashtag.stats.usage.label.social", request,
                    null);
        } else if (tagRate.equals(HashTagRate.SUB_LBL_TIMES)) {
            message = getMessage("hashtag.stats.usage.sublabel.times", request,
                    null);
        } else if (tagRate.equals(HashTagRate.SUB_LBL_TWEETS)) {
            message = getMessage("hashtag.stats.usage.sublabel.tweets",
                    request, null);
        } else if (tagRate.equals(HashTagRate.SUB_LBL_VOTES)) {
            message = getMessage("hashtag.stats.usage.sublabel.votes", request,
                    null);
        }
        return message;
    }

    /**
     *
     * @param tagRate
     * @param request
     * @param objects
     * @return
     */
    public String convertHashTagDataRangeLabelMessage(
            final HashTagRate tagRate,
            final HttpServletRequest request,
            final Object[] objects) {
        String message = null;
        if (tagRate.equals(HashTagRate.JANUARY)) {
            message = getMessage("hashtag.stats.range.label.month.january", request,
                    null);
        } else if (tagRate.equals(HashTagRate.FEBRUARY)) {
            message = getMessage("hashtag.stats.range.label.month.february", request,
                    null);
        } else if (tagRate.equals(HashTagRate.MARCH)) {
            message = getMessage("hashtag.stats.range.label.month.march", request,
                    objects);
        } else if (tagRate.equals(HashTagRate.APRIL)) {
            message = getMessage("hashtag.stats.range.label.month.april", request,
                    null);
        } else if (tagRate.equals(HashTagRate.MAY)) {
            message = getMessage("hashtag.stats.range.label.month.may", request,
                    objects);
        } else if (tagRate.equals(HashTagRate.JUNE)) {
            message = getMessage("hashtag.stats.range.label.month.june",
                    request, null);
        } else if (tagRate.equals(HashTagRate.JULY)) {
            message = getMessage("hashtag.stats.range.label.month.july", request,
                    objects);
        } else if (tagRate.equals(HashTagRate.AUGUST)) {
            message = getMessage("hashtag.stats.range.label.month.august", request,
                    null);
        } else if (tagRate.equals(HashTagRate.SEPTEMBER)) {
            message = getMessage("hashtag.stats.range.label.month.september", request,
                    null);
        } else if (tagRate.equals(HashTagRate.OCTOBER)) {
            message = getMessage("hashtag.stats.range.label.month.october", request,
                    null);
        } else if (tagRate.equals(HashTagRate.NOVEMBER)) {
            message = getMessage("hashtag.stats.range.label.month.november", request,
                    objects);
        } else if (tagRate.equals(HashTagRate.DECEMBER)) {
            message = getMessage("hashtag.stats.range.label.month.december", request,
                    null);
        } else if (tagRate.equals(HashTagRate.MONDAY)) {
            message = getMessage("hashtag.stats.range.label.week.monday", request,
                    null);
        } else if (tagRate.equals(HashTagRate.TUESDAY)) {
            message = getMessage("hashtag.stats.range.label.week.tuesday", request,
                    null);
        } else if (tagRate.equals(HashTagRate.WEDNESDAY)) {
            message = getMessage("hashtag.stats.range.label.week.wednesday", request,
                    null);
        } else if (tagRate.equals(HashTagRate.THURSDAY)) {
            message = getMessage("hashtag.stats.range.label.week.thursday", request,
                    null);
        } else if (tagRate.equals(HashTagRate.FRIDAY)) {
            message = getMessage("hashtag.stats.range.label.week.friday", request,
                    null);
        } else if (tagRate.equals(HashTagRate.SATURDAY)) {
            message = getMessage("hashtag.stats.range.label.week.saturday", request,
                    null);
        } else if (tagRate.equals(HashTagRate.SUNDAY)) {
            message = getMessage("hashtag.stats.range.label.week.sunday", request,
                    null);
        }

        return message;
    }

    /**
     * Get total hash tag hits.
     *
     * @param id
     * @param filterby
     * @return
     */
    public Long getTotalHits(
            final Long id,
            final TypeSearchResult filterby,
            final SearchPeriods periods) {
        final Long totalHashTagHits = getFrontEndDao().getTotalHitsbyType(id,
                TypeSearchResult.HASHTAG, periods.toDays());
        return totalHashTagHits;
    }

    /**
     *
     * @param tagName
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public HashTag getHashTag(
            String tagName,
            final Boolean exceptionIfNotFound)
            throws EnMeNoResultsFoundException {
        Assert.notNull(tagName);
        final HashTag hashTag = getHashTagDao().getHashTagByName(
                ValidationUtils.removeNonAlphanumericCharacters(tagName
                        .toLowerCase()));
        log.warn("AService getHashTag - is "+tagName+" on  database ?->"+hashTag);
        if (hashTag == null) {
            //if possible we can't exception to allow create a new with the parameter.
            if (exceptionIfNotFound || exceptionIfNotFound == null) {
                throw new EnMeNoResultsFoundException("hashtag [" + hashTag+ "] not found");
            } else {
                return null;
            }
        } else {
            log.debug("getHashTag "+hashTag);
            return hashTag;
        }
    }

    /**
     * Convert a normal {@link Date} to Relative format Time .
     * @param tpbean
     * @param request
     */
    public TweetPollBean  convertTweetPollRelativeTime(final TweetPollBean tpbean, final HttpServletRequest request){
        final HashMap<Integer, RelativeTimeEnum> relativeTime =  DateUtil.getRelativeTime(tpbean.getCreatedDateAt());
        @SuppressWarnings("rawtypes")
        final Iterator it = relativeTime.entrySet().iterator();
        while (it.hasNext()) {
            @SuppressWarnings("unchecked")
            final Map.Entry<Integer, RelativeTimeEnum> e = (Map.Entry<Integer, RelativeTimeEnum>)it.next();
            if (log.isTraceEnabled()) {
                log.trace("--"+e.getKey() + "**" + e.getValue());
            }
            tpbean.setRelativeTime(convertRelativeTimeMessage(e.getValue(), e.getKey(), request));
        }
        return tpbean;
    }

    /**
     * Fill the {@link HomeBean} relative Time.
     * @param homeBean {@link HomeBean}
     * @param request {@link HttpServletRequest}.
     * @return
     */
    public void fillHomeBeanRelativeTime(final List<HomeBean> listHomeBean,
            final HttpServletRequest request) {
        for (HomeBean homeBean : listHomeBean) {
            final HashMap<Integer, RelativeTimeEnum> relativeTime = DateUtil
                    .getRelativeTime(homeBean.getCreateDateComparable());
            @SuppressWarnings("rawtypes")
            final Iterator it = relativeTime.entrySet().iterator();
            while (it.hasNext()) {
                @SuppressWarnings("unchecked")
                final Map.Entry<Integer, RelativeTimeEnum> e = (Map.Entry<Integer, RelativeTimeEnum>) it
                        .next();
                if (log.isDebugEnabled()) {
                    log.debug("--" + e.getKey() + "**" + e.getValue());
                }
                homeBean.setRelativeTime(convertRelativeTimeMessage(
                        e.getValue(), e.getKey(), request));
            }

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
    public String convertRelativeTimeMessage(
            final RelativeTimeEnum relativeTimeEnum,
            final Integer number,
            final HttpServletRequest request){
        final StringBuilder builder = new StringBuilder();
        //builder.append(number);
        //builder.append(" ");
        //log.debug("Convert Message Relative Time");
        //log.debug("Relative ENUM -->"+relativeTimeEnum);
        //log.debug("NUMBER -->"+number);
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
        } else if(relativeTimeEnum.equals(RelativeTimeEnum.RIGTH_NOW)) {
            builder.append(getMessage("relative.time.one.rightnow", request, str));
        } else if(relativeTimeEnum.equals(RelativeTimeEnum.YESTERDAY)) {
            builder.append(getMessage("relative.time.yesterday", request, str));
        } else if(relativeTimeEnum.equals(RelativeTimeEnum.DAYS_AGO)) {
            builder.append(getMessage("relative.time.daysago", request, str));
        }
        //log.debug("convertRelativeTimeMessage builder-->"+builder);
        return builder.toString();
    }

    /**
     * Convert a relative time to {@link String}.
     * @param relative
     * @param request
     * @return
     */
    public String convertRelativeTimeToString(
            final Date relativeDate,
            final HttpServletRequest request) {
        final HashMap<Integer, RelativeTimeEnum> relative = DateUtil.getRelativeTime(relativeDate);
        String timeValue = "";
        final Iterator<Entry<Integer, RelativeTimeEnum>> it = relative.entrySet().iterator();
        while (it.hasNext()) {
          final Map.Entry<Integer, RelativeTimeEnum> e = (Map.Entry<Integer, RelativeTimeEnum>)it.next();
          log.debug("convertRelativeTimeToString --"+e.getKey() + "**" + e.getValue());
          timeValue = convertRelativeTimeMessage(e.getValue(), e.getKey(), request);
       }
        return timeValue;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IFrontEndService#getTweetPollsByHashTag(java.lang.String, java.lang.Integer, java.lang.Integer, org.encuestame.utils.enums.TypeSearchResult)
     */
    public List<TweetPoll> getTweetPollsByHashTag(
            final String tagName,
            final Integer initResults,
            final Integer maxResults,
            final TypeSearchResult filter,
            final SearchPeriods searchPeriods) {
        final List<TweetPoll> tweetsbyTag = getTweetPollDao()
                .getTweetpollByHashTagName(tagName, initResults, maxResults,
                        filter, searchPeriods);
        return tweetsbyTag;
    }

    /**
     * Get surveys by HashTag.
     *
     * @param tagName
     * @param initResults
     * @param maxResults
     * @param filter
     * @return
     */
    public List<Survey> getSurveysByHashTag(
            final String tagName,
            final Integer initResults,
            final Integer maxResults,
            final TypeSearchResult filter,
            final SearchPeriods searchPeriods) {
        final List<Survey> surveysByTag = getSurveyDaoImp()
                .getSurveysByHashTagName(tagName, initResults, maxResults,
                        filter, searchPeriods);
        return surveysByTag;
    }

    /**
     * Getter.
     * @return {@link MessageSourceFactoryBean}
     */
    public MessageSourceFactoryBean getMessageSourceFactoryBean() {
        return messageSourceFactoryBean;
    }


    /**
     * Setter.
     * @param messageSource {@link MessageSourceFactoryBean}
     */
    public void setMessageSourceFactoryBean(final MessageSourceFactoryBean messageSourceFactoryBean) {
        this.messageSourceFactoryBean = messageSourceFactoryBean;
    }

    /**
     * Getter by property Id.
     * @param propertieId propertieId
     * @return value of properties
     */
    public String getMessageProperties(final String propertieId) {
        return getMessageSourceFactoryBean() == null ? propertieId : getMessageSourceFactoryBean()
                .getMessage(propertieId, null, null);
    }

    /**
     * Get the property id with {@link Locale}
     * @param propertyId the id of the property
     * @param locale {@link Locale}
     * @param object list of items to embebed in the property text
     * @return
     */
    public String getMessageProperties(final String propertieId, final Locale locale , final Object[] object) {
        final String message = getMessageSourceFactoryBean() == null ? propertieId : getMessageSourceFactoryBean()
                .getMessage(propertieId, object, locale);
        log.debug(" ::: Translated Message " + message);
        return message;
    }

    /**
     * Return the locale inside the {@link HttpServletRequest}.
     * @param request
     * @return
     */
    private Locale getLocale(final HttpServletRequest request){
        return RequestContextUtils.getLocale(request);
    }

    /**
     * Return the i18 message by {@link Locale}.
     * @param message
     * @param request
     * @param args
     * @return
     */
    public String getMessage(final String message,
            final HttpServletRequest request, Object[] args) {
        String stringValue = "";
        try {
            stringValue = getMessageSourceFactoryBean().getMessage(
                    message, args, getLocale(request));
        } catch (Exception e) {
            log.error(e);  //TODO: ENCUESTAME-223 - OPEN
        }
        return stringValue;
    }

    /**
     * Create Email List.
     * @param unitLists
     * @return
     * @throws EnMeExpcetion
     */
    public UnitLists createEmailLists(final UnitLists unitLists) throws EnMeExpcetion{
        if (unitLists!=null){
            try {
                final EmailList listsDomain = new EmailList();
                listsDomain.setCreatedAt(unitLists.getCreatedAt());
                listsDomain.setListName(unitLists.getListName());
                listsDomain.setUsuarioEmail(getAccountDao().getUserById(unitLists.getUserId()));
                getEmailListsDao().saveOrUpdate(listsDomain);
                unitLists.setId(listsDomain.getIdList());
              } catch (HibernateException e) {
                  throw new EnMeExpcetion(e);
              } catch (Exception e) {
                  throw new EnMeExpcetion(e);
              }
              return unitLists;
          } else {
              throw new EnMeExpcetion("email list is not valid");
          }
      }

    /**
     * Create Emails.
     * @param unitEmails
     * @return
     * @throws EnMeExpcetion
     */
    public UnitEmails createEmail(final UnitEmails unitEmails) throws EnMeExpcetion{
        if(unitEmails!= null){
            try {//
                final EmailList emailList = new EmailList();
                final String codeSubscribe = MD5Utils.md5(String.valueOf(System.currentTimeMillis()));
                final Email emailsDomain = new Email();
                emailsDomain.setEmail(unitEmails.getEmailName());
                emailsDomain.setSubscribed(Boolean.FALSE); //By Default is FALSE, user need subscribe.
                emailsDomain.setIdListEmail(emailList);
                getEmailListsDao().saveOrUpdate(emailsDomain);
                unitEmails.setIdEmail(emailsDomain.getIdEmail());
                //TODO: Necesitamos crear el registro con el hash !!
                final EmailSubscribe subscribe = new EmailSubscribe();
                subscribe.setEmail(emailsDomain);
                subscribe.setList(emailList);
                subscribe.setHashCode(codeSubscribe);
                getEmailListsDao().saveOrUpdate(subscribe);
                if (EnMePlaceHolderConfigurer.getBooleanProperty("application.email.enabled")) {
                    getMailService().send(emailsDomain.getEmail(),"Invitation to Subscribe Encuestame List","Invitation to Subscribe");
                }
                //TODO:Enviamos correo al usuario para que confirme su subscripcion.
            }
            catch (Exception e) {
                throw new EnMeExpcetion(e);
            }
            return unitEmails;
        } else {
            throw new EnMeExpcetion("Email is null");
        }
    }

    /**
     * Create Notification.
     * @param description
     * @param secUser
     * @return
     */
    public Notification createNotification(final NotificationEnum description, final String additional,  final Account account) {
        final Notification notification = new Notification();
        notification.setDescription(description);
        notification.setAccount(account);
        notification.setAdditionalDescription(additional);
        getNotificationDao().saveOrUpdate(notification);
        return notification;
    }


    /**
     * Create {@link Notification} with url reference.
     * @param description
     * @param additional
     * @param urlReference
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public Notification createNotification(
            final NotificationEnum description,
            final String additional,
            final String urlReference,
            final Boolean group) throws EnMeNoResultsFoundException{
        final Notification notification = new Notification();
        notification.setDescription(description);
        notification.setAccount(getUserAccount(getUserPrincipalUsername()).getAccount());
        notification.setAdditionalDescription(additional);
        notification.setUrlReference(urlReference);
        notification.setCreated(Calendar.getInstance().getTime());
        notification.setGroup(group);
        getNotificationDao().saveOrUpdate(notification);
        return notification;
    }

    /**
     *
     * @param subscriptionCode
     * @param subscriptionOption
     * @return
     * @throws EnMeExpcetion
     */
    public Boolean subscribeEmails(final String subscriptionCode, final String subscriptionOption) throws EnMeExpcetion{
        Boolean success = false;
        EmailSubscribe subscribe = getEmailListsDao().getSubscribeAccount(subscriptionCode);
        if (subscribe!=null){
            try {
                   Email emails = new Email();
                   if(subscriptionOption.equals("subscribe"))
                   {
                       emails.setSubscribed(Boolean.TRUE);
                   }
                   else {
                       emails.setSubscribed(Boolean.FALSE);
                   }
                   success = Boolean.TRUE;
                }
            catch (Exception e) {
                 throw new EnMeExpcetion(e);
            }
            return success;
        }

        else {
            throw new EnMeExpcetion("Email Not Found in Subscribe List");
        }
     }

    /**
     * Get Access Token.
     * @param token
     * @param tokenSecret
     * @param pin
     * @return
     * @throws TwitterException
     */
    @SuppressWarnings("unused")
    private AccessToken getAccessToken(
            final Twitter twitter,
            final String token,
            final String tokenSecret) throws TwitterException {

            final AccessToken accessToken =  twitter.getOAuthAccessToken(token, tokenSecret);
            log.info(String.format("Auth Token {%s Token Secret {%s ", accessToken.getToken(), accessToken.getTokenSecret()));
            twitter.setOAuthAccessToken(accessToken);
            if (accessToken != null) {
                    log.info(String.format("Got access token for user %s", accessToken.getScreenName()));
            }
            return accessToken;
    }


    /**
     * Load list of users.
     * @return list of users with groups and permission
     * @throws EnMeNoResultsFoundException
     * @throws EnMeExpcetion excepcion
     */
    public List<UserAccountBean> loadListUsers(
           final String currentUsername,
           final Integer maxResults,
           final Integer start) throws EnMeNoResultsFoundException {
        log.info("currentUsername "+currentUsername);
        List<UserAccountBean> loadListUsers = new LinkedList<UserAccountBean>();
        final UserAccount userAccount = this.getUserAccount(currentUsername);
        if(userAccount != null){
            final Collection<UserAccount> listUsers = getAccountDao()
                 .retrieveListOwnerUsers(userAccount.getAccount(), start, maxResults);
                log.info("list users " + listUsers.size());
                loadListUsers = ConvertDomainBean.convertCollectionUsersToBean(listUsers);
        }
        return loadListUsers;
    }

    /**
     * Retrieve the number of user unconfirmed accounts by {@link Account}.
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public Integer retrieveListUserUnconfirmedByAccount() throws EnMeNoResultsFoundException {
        final Account userAccount = this.getUserAccount(this.getUserPrincipalUsername()).getAccount();
        final List<UserAccount> unconfirmedAccounts = this.getAccountDao().retrieveListUserUnconfirmedByAccount(userAccount);
        return unconfirmedAccounts.size();
    }

    /**
     * Validate Owner Group.
     * @param user
     * @param loggedUserName
     * @return
     */
    private Boolean validateOwnerGroup(final UserAccount user, final String loggedUserName){
        Boolean validate = Boolean.FALSE;
        final UserAccount owner = getAccountDao().getUserByUsername(loggedUserName);
        if(user != null && owner != null){
            if(user.getAccount().getUid().equals(owner.getAccount().getUid())){
                validate = Boolean.TRUE;
            }
        }
        log.debug("validateOwnerGroup info "+validate);
        return validate;
    }

    /**
     * Get User Complete Info.
     * @param currentUsername
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public UserAccountBean getUserCompleteInfo(final Long userId, final String currentUsername) throws EnMeNoResultsFoundException {
        UserAccountBean userInfo = null;
        final UserAccount user = getAccountDao().getUserAccountById(userId);
        if(this.validateOwnerGroup(user, currentUsername)){
            userInfo =  ConvertDomainBean.convertSecondaryUserToUserBean(user);
            log.debug("getUserCompleteInfo info "+userInfo.getId());
        }
        return userInfo;
    }

    /**
     * Get Validated User.
     * @param userId
     * @param currentUsername
     * @return
     */
    public UserAccount getValidateUser(final Long userId, final String currentUsername){
        final UserAccount user = getAccountDao().getUserAccountById(userId);
        UserAccount expetedUser = null;
        if(this.validateOwnerGroup(user, currentUsername)){
            expetedUser = user;
        }
        return expetedUser;
    }

    /**
     *
     * @param account
     * @throws EnmeFailOperation
     */
    public void createDirectoryAccount(final Account account){
        File file;
        try {
            file = new File(DirectorySetupOperations.getProfilesDirectory(account.getUid().toString()));
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (EnmeFailOperation e) {
            log.fatal("not able to create user configuration profile");
            e.printStackTrace();
        }
    }

    /**
     * @return the mailServiceOperations
     */
    @Autowired
    public MailServiceOperations getMailService() {
        return mailService;
    }

    /**
     * @param mailServiceOperations the mailServiceOperations to set
     */
    public void setMailService(final MailServiceOperations mailServiceOperations) {
        this.mailService = mailServiceOperations;
    }

    /**
     * Get total comments by item type {@link TweetPoll}, {@link Poll} or {@link Survey}.
     * @param itemId
     * @param itemType
     * @return
     */
    protected Long getTotalCommentsbyType(final Long itemId, final TypeSearchResult itemType){
        final Long totalComments = getCommentsOperations().getTotalCommentsbyItem(itemId, itemType, null , null);
        return totalComments;
    }

    /**
     * Get {@link TweetPoll}.
     * @param tweetPollId
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public TweetPoll getTweetPollById(final Long tweetPollId, final String username) throws EnMeNoResultsFoundException {
        TweetPoll tweetPoll = null;
        if (username != null) {
            tweetPoll = getTweetPollDao()
                    .getTweetPollByIdandUserId(tweetPollId,
                            getUserAccount(username).getAccount().getUid());
        } else {
            tweetPoll = getTweetPollDao().getTweetPollById(tweetPollId);
        }
        if (tweetPoll == null) {
            log.error("tweet poll invalid with this id "+tweetPollId);
            throw new EnMeTweetPollNotFoundException("tweet poll invalid with this id "+tweetPollId);
        }
        return tweetPoll;
    }

    /**
     * Get {@link TweetPoll} by id
     * @param id
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public TweetPoll getTweetPollById(final Long id) throws EnMeNoResultsFoundException {
         return this.getTweetPollById(id, null);
    }

     /**
      * Get {@link Poll} by id.
      * @param pollId
      * @return
      * @throws EnMeNoResultsFoundException
      */
    public Poll getPollById(final Long pollId)
            throws EnMeNoResultsFoundException {
        final Poll poll = this.getPollDao().getPollById(pollId);
        if (poll == null) {
            throw new EnMePollNotFoundException("poll invalid with this id "
                    + pollId);
        }
        return poll;
    }

    public HashTagDetailStats createHastagItemDetailGraph(final String label,
			final Long value, final String subLabel, final Long milisec, final DateTime dateTimeLabel) {
		final HashTagDetailStats tagDetails = new HashTagDetailStats();
		tagDetails.setLabel(label);
		tagDetails.setValue(value);
		tagDetails.setSubLabel(subLabel);
		tagDetails.setMilisecondsDate(milisec);
		tagDetails.setDateValue(dateTimeLabel);
		return tagDetails;
	}


}
