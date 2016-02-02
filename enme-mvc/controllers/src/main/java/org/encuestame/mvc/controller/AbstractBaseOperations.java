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

package org.encuestame.mvc.controller;

import junit.framework.Assert;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.business.service.AbstractSecurityContext;
import org.encuestame.business.service.AbstractSurveyService;
import org.encuestame.business.service.ServiceManager;
import org.encuestame.business.service.TweetPollService;
import org.encuestame.core.service.*;
import org.encuestame.core.util.*;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.captcha.ReCaptcha;
import org.encuestame.utils.enums.RelativeTimeEnum;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.HomeBean;
import org.encuestame.utils.json.ProfileUserAccount;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Base Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 13, 2010 10:41:38 PM
 */
public abstract class AbstractBaseOperations extends AbstractSecurityContext{

     /**
      *
      */
     private Log log = LogFactory.getLog(this.getClass());

             /**
              * Simple date format.
              */
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_DATE);

     /**
      * Simple time format.
      */
     public static final SimpleDateFormat SIMPLE_TIME_FORMAT = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_TIME);

     /**
      *
      */
     protected static final Integer START_DEFAULT = 0;

     /**
      * Max total results to retrieve.
      */
     protected static final Integer MAX_RESULTS = 100;

     /**
      * {@link ReCaptcha}.
      */
     private ReCaptcha reCaptcha;

     /**
      *
      */
     @Resource
     private LocaleResolver localeResolver;

    /**
     * {@link ServiceManager}.
     */
    private IServiceManager serviceManager;

    /**
     *
     */
    @Resource(name = "helpsLinks")
    List<String> listPaths;

    /**
     *
     * @return
     */
    public List<String> getListPaths() {
        return listPaths;
    }


    private IMessageSource messageSourceFactoryBean;


    /**
     * @return the messageSourceFactoryBean
     */
    public IMessageSource getMessageSourceFactoryBean() {
        return messageSourceFactoryBean;
    }

    /**
     * @param messageSourceFactoryBean the messageSourceFactoryBean to set
     */
    @Autowired
    public void setMessageSourceFactoryBean(IMessageSource messageSourceFactoryBean) {
        this.messageSourceFactoryBean = messageSourceFactoryBean;
    }

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
     * @param user
     * @return
     * @throws EnMeExpcetion
     */
    //@Deprecated
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
        return getTweetPollService().createTweetPoll(tweetPollBean, question, user, null);
    }

    /**
     * Create {@link HashTagBean} list.
     * @param arrayHashTags
     * @return
     */
    public List<HashTagBean> createHashTagBeansList(final String[] arrayHashTags) {
        return EnMeUtils.createHashTagBeansList(arrayHashTags);
    }

    /**
     *
     * @param tweetPollBean
     * @return
     * @throws EnMeExpcetion
     */
    public TweetPoll createTweetPoll(final TweetPollBean tweetPollBean) throws EnMeExpcetion{
        //create new tweetPoll
        log.debug("createTweetPoll Bean "+tweetPollBean.toString());
        return getTweetPollService().createTweetPoll(tweetPollBean,
                tweetPollBean.getQuestionBean().getQuestionName(),
                getUserAccount(), null);
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
     * Get the Host IP Address.
     * @param
     * @return ip as string format.
     * @throws UnknownHostException
     */
    public String getIpClient(final HttpServletRequest request) throws UnknownHostException {
        return EnMeUtils.getIP(request, this.proxyPass);
    }

    /**
     *
     * @param max
     * @return
     */
    public Integer limitTotalMax(Integer max) {
        return max == null ? null : (max > this.MAX_RESULTS ? this.MAX_RESULTS : max);
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
            final HttpServletRequest request,
            final Object[] args) {
        String stringValue = "";
        try {
            stringValue = this.getMessageSourceFactoryBean().getMessage(message, args, getLocale(request));
        } catch (Exception e) {
            log.error(e);
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
        if (request == null) {
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
    public String filterValue(String value) {
        final HTMLInputFilter vFilter = new HTMLInputFilter(true);
        return vFilter.filter(value);
    }

    /**
     * @param serviceManager
     *            the serviceManager to set
     */
    @Autowired
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
     *
     * @return
     */
    public ICommentService getCommentService(){
        return getServiceManager().getApplicationServices().getCommentService();
    }

    /**
     *
     * @return
     */
    public StreamOperations getStreamOperations(){
        return getServiceManager().getApplicationServices().getStreamOperations();
    }

    /**
     * Get {@link SecurityOperations}.
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
    public IGeoLocationSupport getLocationService(){
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
     * Get {@link IFrontEndService}.
     * @return
     */
    public IFrontEndService getFrontService(){
        return getServiceManager().getApplicationServices().getFrontEndService();
    }

    /**
     *
     * @return
     */
    public IStatisticsService getStatisticsService(){
        return getServiceManager().getApplicationServices().getStatisticService();
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
            final String hashtagE = hashtags[row];
            if (hashtagE != null && !hashtagE.isEmpty()) {
                log.debug("HashTag:{" + hashTag);
                hashTag.setHashTagName(hashtags[row].toLowerCase().trim());
                hashtagsList.add(hashTag);
            } else {
                log.warn("Trying to save empty or null hashtag?");
            }
        }
        log.debug("hashtag fillListOfHashTagsBean->"+hashtagsList.size());
        return hashtagsList;
    }

    /**
     *
     * @param model
     * @param key
     * @param request
     */
    public void addi18nProperty(
            ModelMap model,
            final String key,
            final HttpServletRequest request,
            final HttpServletResponse response) {
        log.debug("--- after addi18nProperty -->" + request.getLocale().toLanguageTag());
        try {
            getLocaleResolver().setLocale(request, response, getUserAccountLocale(getByUsername(getUserPrincipalUsername())));
        } catch (EnMeNoResultsFoundException e) {
            // 	FIXME: just ignore the locale, for now.
        }
        log.debug("--- before addi18nProperty -->" + request.getLocale().toLanguageTag());
        @SuppressWarnings("unchecked")
        HashMap<String, String> i18n = (HashMap<String, String>) model.get("i18n");
        if (i18n == null) {
            i18n = new HashMap<String, String>();
            model.addAttribute("i18n", i18n);
        }
        i18n.put(key, getMessage(key, request, null));
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

   /**
    * Add to model the social picker messages.
    * @param model
    */
   public void addSocialPickerWidgetMessages(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
       addi18nProperty(model, "social_picker_only_selected", request, response);
       addi18nProperty(model, "social_picker_select_all", request, response);
       addi18nProperty(model, "social_picker_unselect_all", request, response);
       addi18nProperty(model, "social_picker_accounts_selected", request, response);
       addi18nProperty(model, "social_picker_filter_selected", request, response);
       addi18nProperty(model, "e_022", request, response);
   }

   /**
    *
    * @param model
 * @param response
    */
   public void addItemsManangeMessages(ModelMap model, final HttpServletRequest request, HttpServletResponse response) {

       addi18nProperty(model, "detail_manage_by_account", request, response);
       addi18nProperty(model, "detail_manage_today",request, response);
       addi18nProperty(model, "detail_manage_last_week", request, response);
       addi18nProperty(model, "detail_manage_favorites", request, response);
       addi18nProperty(model, "detail_manage_scheduled", request, response);
       addi18nProperty(model, "detail_manage_all", request, response);
       addi18nProperty(model, "detail_manage_published", request, response);
       addi18nProperty(model, "detail_manage_unpublished", request, response);
       addi18nProperty(model, "detail_manage_only_completed", request, response);
       //folder messages
       addi18nProperty(model, "detail_manage_folder_title", request, response);
       addi18nProperty(model, "detail_manage_delete", request, response);
       addi18nProperty(model, "detail_manage_new", request, response);
       addi18nProperty(model, "detail_manage_search", request, response);
       addi18nProperty(model, "detail_manage_folder_replace_name", request, response);
       //filters
       addi18nProperty(model, "detail_manage_filters_advanced", request, response);
       addi18nProperty(model, "detail_manage_filters_order", request, response);
       addi18nProperty(model, "detail_manage_filters_social_network", request, response);
       addi18nProperty(model, "detail_manage_filters_votes_options", request, response);
       // advanced filter
       addi18nProperty(model, "detail_manage_filters_advanced_title", request, response);
       addi18nProperty(model, "detail_manage_filters_advanced_type_to_search", request, response);
       addi18nProperty(model, "detail_manage_filters_advanced_all_results", request, response);
       addi18nProperty(model, "detail_manage_filters_advanced_range_days", request, response);
       // commons
       addi18nProperty(model, "commons_filter", request, response);
       addi18nProperty(model, "commons_confirm", request, response);
       addi18nProperty(model, "commons_yes", request, response);
       addi18nProperty(model, "commons_no", request, response);
       addSocialPickerWidgetMessages(model, request, response);
   }

    /**
     *
     * @param view
     * @param period
     * @param homeMaxItems
     * @param request
     * @return
     * @throws EnMeExpcetion
     */
    public List<HomeBean> filterHomeItems (
            final String view,
            final String period,
            final Integer start,
            final Integer homeMaxItems,
            final HttpServletRequest request) throws EnMeExpcetion {
        TypeSearchResult typeSearchResult = TypeSearchResult.getTypeSearchResult(view);
        final List<HomeBean> homeItems = new ArrayList<HomeBean>();
        if (view.isEmpty()) {
            homeItems.addAll(getFrontService().getFrontEndItems(period, start , homeMaxItems, request));
        } else {
            if (typeSearchResult.equals(TypeSearchResult.TWEETPOLL)) {
                homeItems.addAll(ConvertDomainBean
                        .convertTweetPollListToHomeBean(getFrontService()
                                .searchItemsByTweetPoll(period, start,
                                        homeMaxItems, request)));
            } else if (typeSearchResult.equals(TypeSearchResult.POLL)) {
                homeItems.addAll(ConvertDomainBean
                                .convertPollListToHomeBean(getFrontService()
                                        .searchItemsByPoll(period, start,
                                                homeMaxItems, request)));
            } else if (typeSearchResult.equals(TypeSearchResult.SURVEY)) {
                //TODO: ENCUESTAME-345
                return  ListUtils.EMPTY_LIST;
            } else {
                // return ALL
                homeItems.addAll(getFrontService().getFrontEndItems(period, start , homeMaxItems, request));
            }
        }
        return homeItems;
    }

   /**
    *
    * @return
    */
   public Locale getUserAccountLocale(final UserAccount account) {
       //try {
               //final UserAccount account = getSecurityService().findUserByUserName(username);
               if (account != null) {
                   final String language = account.getLanguage();
                final Locale lang = WidgetUtil.toLocale(language);
                return lang;
               } else {
                   //throw new EnMeExpcetion("anonymous user does not have locale");
                   log.info(account + " user does not have locale");
                   return Locale.ENGLISH;
               }
        //} catch (EnMeExpcetion e) {
            //log.warn(e.getMessage());
            //return Locale.ENGLISH;
        //}
   }

    /**
     * @return the localeResolver
     */
    public LocaleResolver getLocaleResolver() {
        return localeResolver;
    }


    /**
     *
     * @param map
     * @param name
     */
    public void setCss(ModelMap map, String name) {
        map.addAttribute("cssFile", name);
    }

}
