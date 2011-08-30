/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.core.util.InternetUtils;
import org.encuestame.core.util.SocialUtils;
import org.encuestame.persistence.dao.IHashTagDao;
import org.encuestame.persistence.dao.ITweetPoll;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.question.QuestionPattern;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollResult;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.social.api.FacebookAPITemplate;
import org.encuestame.social.api.GoogleBuzzAPITemplate;
import org.encuestame.social.api.IdenticaAPITemplate;
import org.encuestame.social.api.LinkedInAPITemplate;
import org.encuestame.social.api.TwitterAPITemplate;
import org.encuestame.social.api.support.BuzzAPIOperations;
import org.encuestame.social.api.support.FacebookAPIOperations;
import org.encuestame.social.api.support.IdenticaAPIOperations;
import org.encuestame.social.api.support.LinkedInAPIOperations;
import org.encuestame.social.api.support.TwitterAPIOperations;
import org.encuestame.utils.MD5Utils;
import org.encuestame.utils.PictureUtils;
import org.encuestame.utils.RestFullUtil;
import org.encuestame.utils.ShortUrlProvider;
import org.encuestame.utils.TweetPublishedMetadata;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.json.QuestionPatternBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import twitter4j.TwitterException;
import twitter4j.http.RequestToken;

/**
 * Survey Service.
 * @author Picado, Juan juan@encuestame.org
 * @since 27/04/2009
 * @version $Id$
 */
@Service
public class AbstractSurveyService extends AbstractChartService {

    /** AnswerPoll Path. **/
    private String answerPollPath;

    /** TweetPoll Results Path. **/
    private String tweetPollResultsPath;

    /** TweetPoll Dao. **/
    @Autowired
    private ITweetPoll tweetPollDao;

    /** Hash Tag Dao. **/
    @Autowired
    private IHashTagDao hashTagDao;

    private Log log = LogFactory.getLog(this.getClass());

    /** Tweet Path, **/
    private String tweetPath;

    /**
     * Twee poll vote.
     */
    private final String TWEETPOLL_VOTE = "/tweetpoll/vote/";

    /**
     * Create Question.
     * @param questionBean {@link QuestionBean}.
     * @throws EnMeExpcetion exception
     */
    public Question createQuestion(
            final QuestionBean questionBean,
            final UserAccount account) throws EnMeExpcetion{
            final Question question = new Question();
            try{
                question.setQuestion(questionBean.getQuestionName());
                question.setSlugQuestion(RestFullUtil.slugify(questionBean.getQuestionName()));
                question.setAccountQuestion(account.getAccount());
                question.setQidKey(MD5Utils.md5(RandomStringUtils.randomAlphanumeric(500)));
                question.setSharedQuestion(false);
                getQuestionDao().saveOrUpdate(question);
//                for (final QuestionAnswerBean answerBean : questionBean.getListAnswers()) {
//                    this.createQuestionAnswer(answerBean, question);
//                }
            } catch (Exception e) {
                log.error(e);
                throw new EnMeExpcetion(e);
            }
            return question;
    }

    /**
     * Save Question Answer.
     * @param answerBean answer
     * @throws EnMeExpcetion EnMeExpcetion
     */
    public QuestionAnswer createQuestionAnswer(
            final QuestionAnswerBean answerBean,
            final Question question){
        log.debug("action createQuestionAnswer "+answerBean.toString());
        final QuestionAnswer answer = new QuestionAnswer();
        answer.setQuestions(question);
        answer.setAnswer(answerBean.getAnswers());
        answer.setProvider(answerBean.getShortUrlType());
        answer.setColor(PictureUtils.getRandomHexColor());
        answer.setUniqueAnserHash(answerBean.getAnswerHash());
        this.getQuestionDao().saveOrUpdate(answer);
        answerBean.setAnswerId(answer.getQuestionAnswerId());
        log.debug("QuestionAnswer created:{"+answerBean.toString());
        return answer;
    }

    /**
     *
     * @param question
     * @return
     */
    public QuestionAnswer createAnswers(final Question question, final String answerText){
         final QuestionAnswer answer = new QuestionAnswer();
          answer.setQuestions(question);
          answer.setAnswer(answerText);
          answer.setQuestions(question);;
          answer.setColor(PictureUtils.getRandomHexColor());
          this.getQuestionDao().saveOrUpdate(answer);
        return answer;
    }

    /**
     * Retrieve {@link QuestionAnswer} by Id.
     * @param id
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public QuestionAnswer getQuestionAnswerById(final Long id) throws EnMeNoResultsFoundException{
        final QuestionAnswer answer = getQuestionDao().retrieveAnswerById(id);
        if(answer == null){
            throw new EnMeNoResultsFoundException("answer not found");
        }
        return  answer;
    }

    /**
     * Update Question Name.
     * @param questionId
     * @param questionName
     */
    public void updateQuestionName(final Long questionId, final String questionName){
            final Question question = getQuestionDao().retrieveQuestionById(questionId);
            if(question != null){
                question.setQuestion(questionName);
                getQuestionDao().saveOrUpdate(question);
            }
    }

    /**
     *
     * @param hashtagBeans
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public List<HashTag> retrieveListOfHashTags(final List<HashTagBean> hashtagBeans) throws EnMeNoResultsFoundException{
        final List<HashTag> tagList = new ArrayList<HashTag>();
        for (HashTagBean unitHashTag : hashtagBeans) {
            HashTag hashTag = getHashTag(unitHashTag.getHashTagName());
            //if is null, create new hashTag.
            if(hashTag == null && unitHashTag.getHashTagName() != null){
                log.debug("created new hashTag:{"+unitHashTag.getHashTagName().toLowerCase());
                hashTag = createHashTag(unitHashTag.getHashTagName().toLowerCase());
            }
            tagList.add(hashTag);
        }
        return tagList;
    }

    /**
     * Create {@link TweetPollSwitch}.
     * @return {@link TweetPollSwitch}.
     */
    public TweetPollSwitch createTweetPollSwitch(
            final TweetPoll tweetPoll,
            final QuestionAnswer answer){
        final TweetPollSwitch tPollSwitch = new TweetPollSwitch();
        tPollSwitch.setAnswers(answer);
        tPollSwitch.setTweetPoll(tweetPoll);
        tPollSwitch.setCodeTweet(MD5Utils.shortMD5(Calendar.getInstance().getTimeInMillis() + answer.getAnswer()));
        tPollSwitch.setDateUpdated(Calendar.getInstance().getTime());
        final StringBuffer voteUrlWithoutDomain = new StringBuffer();
        voteUrlWithoutDomain.append(this.TWEETPOLL_VOTE);
        voteUrlWithoutDomain.append(tPollSwitch.getCodeTweet());
        final StringBuffer completeDomain = new StringBuffer(EnMePlaceHolderConfigurer.getProperty("application.domain"));
        completeDomain.append(voteUrlWithoutDomain.toString());
         log.debug("tweet poll answer vote :{"+voteUrlWithoutDomain.toString());
         if (InternetUtils.validateUrl(completeDomain.toString())) {
             log.debug("createTweetPollSwitch: URL IS VALID");
             log.debug("createTweetPollSwitch: short url provider "+answer.getProvider());
             tPollSwitch.setShortUrl(this.createShortUrl(answer.getProvider(), completeDomain.toString()));
         } else {
             log.debug("createTweetPollSwitch: url IS NOT valid");
             tPollSwitch.setShortUrl(completeDomain.toString());
             log.warn("Invalid format vote url:{"+voteUrlWithoutDomain.toString());
         }
        getTweetPollDao().saveOrUpdate(tPollSwitch);
        return tPollSwitch;
    }

    /**
     * Apply short url based on {@link ShortUrlProvider}
     * @param provider {@link ShortUrlProvider}
     * @param url url
     * @return
     * @throws EnmeFailOperation
     * @throws IOException
     * @throws HttpException
     */
    public String createShortUrl(final ShortUrlProvider provider, final String url){
        log.debug("shortUrlProvider "+url);
        log.debug("shortUrlProvider PROVIDER "+provider);
        String urlShort = url;
        if (provider == null) {
            urlShort = SocialUtils.getGoGl(url,
                    EnMePlaceHolderConfigurer.getProperty("short.google.key"));
        } else if (provider.equals(ShortUrlProvider.GOOGL)) {
            urlShort = SocialUtils.getGoGl(url,
                    EnMePlaceHolderConfigurer.getProperty("short.google.key"));
        } else if (provider.equals(ShortUrlProvider.TINYURL)) {
            urlShort = SocialUtils.getTinyUrl(url);
        } else if (provider.equals(ShortUrlProvider.BITLY)) {
             urlShort = SocialUtils.getBitLy(url,
                     EnMePlaceHolderConfigurer.getProperty("short.bitLy.key"),
                     EnMePlaceHolderConfigurer.getProperty("short.bitLy.login"));
        } else {
             //if is  null, always user bitly.
             urlShort = SocialUtils.getBitLy(url,
                     EnMePlaceHolderConfigurer.getProperty("short.bitLy.key"),
                     EnMePlaceHolderConfigurer.getProperty("short.bitLy.login"));
        }
        log.debug("shortUrlProvider SHORT "+urlShort);
        return urlShort;
    }

    /**
     * Create vote support for each tweetpoll answer.
     * @param questionId
     * @param tweetPoll
     */
    public void updateTweetPollSwitchSupport(final TweetPoll tweetPoll){
        final List<QuestionAnswer> answers = this.getQuestionDao().getAnswersByQuestionId(tweetPoll.getQuestion().getQid());
        log.debug("updateTweetPollSwitchSupport answers size:{"+answers.size());
        //iterate answer for one question
        for (QuestionAnswer answer : answers) {
            //try to locate current switch if exist
            TweetPollSwitch tPollSwitch = getTweetPollDao().getAnswerTweetSwitch(tweetPoll, answer);
            if (tPollSwitch == null) {
                log.debug("created tweetpoll switch for tweetpoll:{"+tweetPoll.getTweetPollId());
                tPollSwitch = this.createTweetPollSwitch(tweetPoll, answer);
            } else {
                log.debug("updated tweetpoll switch:{"+tPollSwitch.getSwitchId()+" for tweetpoll :{"+tweetPoll.getTweetPollId());
            }
            //update answer url.
            answer.setUrlAnswer(tPollSwitch.getShortUrl()); //store url without short.
            getQuestionDao().saveOrUpdate(answer);
        }
    }

    /**
     * Create Hash Tag.
     * @param unitHashTag new tag
     * @return {@link HashTag}
     * @throws EnMeExpcetion exception.
     */
    public HashTag createHashTag(final HashTagBean unitHashTag) {
        final HashTag tag = createHashTag(unitHashTag.getHashTagName());
        getHashTagDao().saveOrUpdate(tag);
        log.debug("Hash Tag Saved.");
        return tag;
    }

    /**
     * Get Tweet Polls by User Id.
     * @param userId user Id.
     * @return list of Tweet polls bean
     */
    public List<TweetPollBean> getTweetsPollsByUserId(final Long userId){
        final List<TweetPoll> tweetPolls = getTweetPollDao().retrieveTweetsByUserId(userId, null, null);
        final List<TweetPollBean> tweetPollsBean = new ArrayList<TweetPollBean>();
        for (TweetPoll tweetPoll : tweetPolls) {
            final TweetPollBean unitTweetPoll = ConvertDomainBean.convertTweetPollToBean(tweetPoll);
             unitTweetPoll.getQuestionBean().setListAnswers(this.retrieveAnswerByQuestionId(unitTweetPoll.getQuestionBean().getId()));
             tweetPollsBean.add(unitTweetPoll);
        }
        return tweetPollsBean;
    }

    /**
     * Retrieve Answer By Question Id.
     * @param questionId question Id
     * @return List of Answers
     */
    public List<QuestionAnswerBean> retrieveAnswerByQuestionId(final Long questionId){
        final List<QuestionAnswer> answers = this.getQuestionDao().getAnswersByQuestionId(questionId);
        log.debug("answers by question id ["+questionId+"] answers size:{"+answers.size());
        final List<QuestionAnswerBean> answersBean = new ArrayList<QuestionAnswerBean>();
        for (QuestionAnswer questionsAnswers : answers) {
            answersBean.add(ConvertDomainBean.convertAnswerToBean(questionsAnswers));
        }
        return answersBean;
    }

    /**
     * Save Tweet Id.
     * @param tweetPollBean {@link TweetPollBean}
     * @throws EnMeExpcetion exception
     */
    public void saveTweetId(final TweetPollBean tweetPollBean) throws EnMeExpcetion{
        final TweetPoll tweetPoll = getTweetPollDao().getTweetPollById(tweetPollBean.getId());
        if(tweetPoll != null){
            //tweetPoll.setTweetId(tweetPollBean.getTweetId());
            //tweetPoll.setPublicationDateTweet(tweetPollBean.getPublicationDateTweet());
            tweetPoll.setPublishTweetPoll(Boolean.TRUE);
            getTweetPollDao().saveOrUpdate(tweetPoll);
        }else{
            throw new EnMeExpcetion("tweet poll not found");
        }
    }

    /**
     * Update Answer Name by Answer Id.
     * @param answerId answer Id
     * @param nameUpdated new name for answer
     * @throws EnMeExpcetion exception
     */
    public void updateAnswerByAnswerId(final Long answerId, String nameUpdated) throws EnMeExpcetion{
            final QuestionAnswer answer = getQuestionDao().retrieveAnswerById(answerId);
            if(answer==null){
                throw new EnMeExpcetion("answer not found");
            }
            answer.setAnswer(nameUpdated);
            getQuestionDao().saveOrUpdate(answer);
    }

    /**
     * Get Twitter Token.
     * @param consumerKey consumer key
     * @param consumerSecret consumer secret
     * @return {@link RequestToken}
     * @throws TwitterException exception
     */
    public RequestToken getTwitterToken(final String consumerKey,  final String consumerSecret) throws TwitterException{
            //return getTwitterService().getTwitterPing(consumerKey, consumerSecret);
            return null;
    }


    /**
     * Generate TweetPoll Text.
     * @param tweetPoll tweetPoll
     * @param url url
     * @return tweet text
     * @throws EnMeExpcetion exception
     */
    public String generateTweetPollText(final TweetPollBean tweetPoll, final String url) throws EnMeExpcetion{
        String tweetQuestionText = "";
        try{
            final TweetPoll tweetPollDomain = getTweetPollDao().getTweetPollById(tweetPoll.getId());
            tweetQuestionText = tweetPollDomain.getQuestion().getQuestion();
            final List<QuestionAnswer> answers = getQuestionDao().getAnswersByQuestionId(tweetPollDomain.getQuestion().getQid());
            if (answers.size() == 2) {
                for (final QuestionAnswer questionsAnswers : answers) {
                    tweetQuestionText += " "+questionsAnswers.getAnswer()+" "+buildUrlAnswer(questionsAnswers, url);
                }
            }
        }
        catch (Exception e) {
            throw new EnMeExpcetion(e);
        }
        return tweetQuestionText;
    }

    /**
     * Build Url Answer.
     * @param anwer answer
     * @throws IOException exception
     * @throws HttpException exception
     */
    private String buildUrlAnswer(final QuestionAnswer answer, final String domain) throws HttpException, IOException{
        StringBuffer stringBuffer = new StringBuffer(domain);
        stringBuffer.append(getTweetPath());
        stringBuffer.append(answer.getUniqueAnserHash());
        //return getTwitterService().getTinyUrl(stringBuffer.toString());
        return null;
    }

    /**
     * Public Tweet Poll (OAuth method).
     * @param tweetText tweet text
     * @return status of tweet
     * @throws EnMeExpcetion exception
     */
    public TweetPublishedMetadata publicTweetPoll(final String tweetText, final SocialAccount socialAccount)
           throws EnMeExpcetion {
        TweetPublishedMetadata published = null;
        log.debug("publicTweetPoll:{ "+tweetText);
        if (socialAccount.getAccounType().equals(SocialProvider.TWITTER)) {
            log.debug("Publish on TWITTER");
            final TwitterAPIOperations twitterAPIOperations = new TwitterAPITemplate(
                    EnMePlaceHolderConfigurer.getProperty("twitter.oauth.consumerSecret"),
                    EnMePlaceHolderConfigurer.getProperty("twitter.oauth.consumerKey"),
                    socialAccount);
            try {
                log.debug("Publish on Twitter 1 ............>");
                published = twitterAPIOperations.updateStatus(tweetText);
                log.debug("Publish on Twitter 2 ...... "+published);
                log.debug("Publish on Twitter 2 ...... "+published.getTweetId());
            } catch (Exception e) {
                log.error(e);
            }
        } else if (socialAccount.getAccounType().equals(SocialProvider.IDENTICA)) {
            log.debug("Publish on IDENTICA");
            IdenticaAPIOperations identicaAPIOperations = new IdenticaAPITemplate(
                    EnMePlaceHolderConfigurer.getProperty("identica.consumer.key"),
                    EnMePlaceHolderConfigurer.getProperty("identica.consumer.secret"),
                    socialAccount.getAccessToken(),
                    socialAccount.getSecretToken());
            try {
                log.debug("Publish on Identica............>");
                published = identicaAPIOperations.updateStatus(tweetText);
                log.debug("Publish on Identica...... "+published);
            } catch (Exception e) {
                log.error(e);
            }
        } else if (socialAccount.getAccounType().equals(SocialProvider.FACEBOOK)) {
            log.debug("Publish on FACEBOOK");
            FacebookAPIOperations facebookAPIOperations = new FacebookAPITemplate(socialAccount.getAccessToken());
            try {
                log.debug("Publish on FACEBOOK............>");
                published = facebookAPIOperations.updateStatus(tweetText);
                log.debug("Publish on FACEBOOK...... "+published);
            } catch (HttpClientErrorException e) {
                log.error("-----------------------FACEBOOK EXPIRED TOKEN----------------------- 1");
                log.error(e.getStatusCode());
                log.error(e.getResponseBodyAsString());
                log.error(e.getStatusText());
                // refresh token point.
                //offline_access scope permission is enabled by default . In this case
                //https://developers.facebook.com/docs/authentication/permissions/
                log.error("-----------------------FACEBOOK EXPIRED TOKEN----------------------- 2");
            } catch (Exception e) {
                log.error(e);
            }
        } else if (socialAccount.getAccounType().equals(SocialProvider.LINKEDIN)) {
            log.debug("Publish on LinkedIn");
            LinkedInAPIOperations linkedInAPIOperations = new LinkedInAPITemplate(
                    EnMePlaceHolderConfigurer.getProperty("linkedIn.oauth.api.key"),
                    EnMePlaceHolderConfigurer.getProperty("linkedIn.oauth.api.secret"),
                    socialAccount.getAccessToken(),
                    socialAccount.getSecretToken());
            try {
                log.debug("Publish on LinkedIn 1............>");
                published = linkedInAPIOperations.updateStatus(tweetText);
                published.setTextTweeted(tweetText);
                published.setDatePublished(new Date());
                published.setTweetId(RandomStringUtils.randomAscii(15));
                log.debug("Publish on LinkedIn 2...... "+published);
            } catch (Exception e) {
                log.error(e);
            }
        } else if (socialAccount.getAccounType().equals(SocialProvider.GOOGLE_BUZZ)) {
            BuzzAPIOperations buzzInAPIOperations = new GoogleBuzzAPITemplate(socialAccount);
            try {
                log.debug("Publish on LinkedIn............>");
                published = buzzInAPIOperations.updateStatus(tweetText);
                published.setTextTweeted(tweetText);
                published.setDatePublished(new Date());
                published.setTweetId(RandomStringUtils.randomAscii(15));
                log.debug("Publish on LinkedIn...... "+published);
            } catch (Exception e) {
                log.error(e);
            }
        }
        if (published != null) {
            log.debug("publicTweetPoll:s "+published.toString());
        }
        return published;
    }

    /**
     * Load all questions.
     * @return List of {@link QuestionBean}
     * @throws EnMeExpcetion exception
     */
    public List<QuestionBean> loadAllQuestions() throws EnMeExpcetion {
        final List<QuestionBean> listQuestionBean = new LinkedList<QuestionBean>();
        try {
            final  List<Question> questionsList = getQuestionDao()
                    .loadAllQuestions();
            if (questionsList.size() > 0) {

               for (Question questions : questionsList) {
                    final QuestionBean q = new QuestionBean();
                    q.setId(Long.valueOf(questions.getQid().toString()));
                    q.setQuestionName(questions.getQuestion());
                    listQuestionBean.add(q);
                }
            }
        } catch (HibernateException e) {
            throw new EnMeExpcetion(e);
        } catch (Exception e) {
            throw new EnMeExpcetion(e);
        }
        return  listQuestionBean;
    }

    /**
     * Load pattern info.
     * @param unitPatternBean {@link QuestionPatternBean}
     * @return {@link QuestionPatternBean}
     * @throws EnMeExpcetion exception
     */
    public QuestionPatternBean loadPatternInfo(QuestionPatternBean unitPatternBean)
            throws EnMeExpcetion {
        if (unitPatternBean != null && unitPatternBean.getId() != null) {
            final QuestionPattern questionPatternDomain = getQuestionDao().loadPatternInfo(
                    unitPatternBean.getId());

            unitPatternBean.setId(questionPatternDomain.getPatternId());

            unitPatternBean.setDescripcion(questionPatternDomain.getDesQid());
            unitPatternBean.setLabel(questionPatternDomain.getLabelQid());
            unitPatternBean.setPatronType(questionPatternDomain.getPatternType());
            unitPatternBean.setTemplate(questionPatternDomain.getPatternTemplate());
            unitPatternBean.setClasspattern("classpatthern");
            unitPatternBean.setLevelpattern("2");
            unitPatternBean.setFinallity("save");
            //TODO : need more properties.
            return unitPatternBean;
        }
        else {
            throw new EnMeExpcetion("unit patter bean is null");
        }
    }

    /**
     * Load all Patrons.
     * @return List of {@link QuestionPatternBean}
     * @throws EnMeExpcetion exception
     */
    public Collection<QuestionPatternBean> loadAllPatrons()
            throws EnMeExpcetion {
        final List<QuestionPatternBean> listPatronBean = new LinkedList<QuestionPatternBean>();
        try {
            final List<QuestionPattern> patronList = getQuestionDao()
                    .loadAllQuestionPattern();
            if (patronList.size() > 0) {
               for (QuestionPattern patron : patronList) {
                    QuestionPatternBean p = new QuestionPatternBean();
                    p.setId(patron.getPatternId());
                    p.setPatronType(patron.getPatternType());
                    listPatronBean.add(p);
                }
            }
        } catch (HibernateException e) {
            throw new EnMeExpcetion(e);
        } catch (Exception e) {
            throw new EnMeExpcetion(e);
        }
        return listPatronBean;
    }

    /**
     * Vote on TweetPoll.
     * @param pollSwitch {@link TweetPollSwitch}
     * @param ip ip
     */
    public void tweetPollVote(final TweetPollSwitch pollSwitch, final String ip){
        final TweetPollResult pollResult = new TweetPollResult();
        pollResult.setIpVote(ip.trim());
        pollResult.setTweetPollSwitch(pollSwitch);
        pollResult.setTweetResponseDate(new Date());
        getTweetPollDao().saveOrUpdate(pollResult);
    }

    /**
     * Get List Suggestion Question.
     * @param questionKeyword question keyword
     * @param username
     * @return unitQuestionBean
     * @throws EnMeNoResultsFoundException
     */
    public List<QuestionBean> listSuggestQuestion(final String questionKeyword, final String username) throws EnMeNoResultsFoundException{
        final List<QuestionBean> unitQuestionBean = new ArrayList<QuestionBean>();
        final List<Question> questionsList = getQuestionDao().retrieveIndexQuestionsByKeyword(
                questionKeyword,
                getPrimaryUser(username), null, null);
        log.info("listSuggestQuestion "+questionsList.size());
        for (Question question : questionsList) {
            unitQuestionBean.add(ConvertDomainBean.convertQuestionsToBean(question));
        }
        return unitQuestionBean;
    }

    /**
     * Validate TweetPoll IP.
     * @param ipVote  ipVote
     * @param tweetPoll tweetPoll
     * @return {@link TweetPollResult}
     */
    public TweetPollResult validateTweetPollIP(final String ipVote, final TweetPoll tweetPoll){
        return getTweetPollDao().validateVoteIP(ipVote, tweetPoll);
    }

    /**
     * Update Question.
     * @param unitQuestionPoll
     * @throws EnMeExpcetion  Exception
     */
     public void updateQuestion(final QuestionBean unitQuestionPoll) throws EnMeExpcetion{
         final Question question = getQuestionDao().retrieveQuestionById(unitQuestionPoll.getId());
         if (question == null){
             throw new EnMeExpcetion("question not found");
         }
         else{
             question.setQuestion(unitQuestionPoll.getQuestionName());
             getQuestionDao().saveOrUpdate(question);
         }
     }

    /**
     * @return the answerPollPath
     */
    public String getAnswerPollPath() {
        return answerPollPath;
    }

    /**
     * @param answerPollPath the answerPollPath to set
     */
    public void setAnswerPollPath(final String answerPollPath) {
        this.answerPollPath = answerPollPath;
    }

    /**
     * @return the tweetPollResultsPath
     */
    public String getTweetPollResultsPath() {
        return tweetPollResultsPath;
    }

    /**
     * @param tweetPollResultsPath the tweetPollResultsPath to set
     */
    public void setTweetPollResultsPath(final String tweetPollResultsPath) {
        this.tweetPollResultsPath = tweetPollResultsPath;
    }

    /**
     * @return the tweetPollDao
     */
    public ITweetPoll getTweetPollDao() {
        return tweetPollDao;
    }

    /**
     * @param tweetPollDao the tweetPollDao to set
     */
    public void setTweetPollDao(final ITweetPoll tweetPollDao) {
        this.tweetPollDao = tweetPollDao;
    }

    /**
     * @return the tweetPath
     */
    public String getTweetPath() {
        return tweetPath;
    }

    /**
     * @param tweetPath the tweetPath to set
     */
    public void setTweetPath(final String tweetPath) {
        this.tweetPath = tweetPath;
    }

    /**
     * @return the hashTagDao
     */
    public final IHashTagDao getHashTagDao() {
        return hashTagDao;
    }

    /**
     * @param hashTagDao the hashTagDao to set
     */
    public final void setHashTagDao(IHashTagDao hashTagDao) {
        this.hashTagDao = hashTagDao;
    }


}
