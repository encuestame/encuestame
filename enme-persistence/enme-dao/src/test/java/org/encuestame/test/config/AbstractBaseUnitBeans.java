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
package org.encuestame.test.config;

import java.util.Date;
import java.util.List;

import org.encuestame.persistence.domain.GeoPointFolderType;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.enums.LayoutEnum;
import org.encuestame.utils.enums.TypeSearch;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.FolderBean;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.security.SignUpBean;
import org.encuestame.utils.web.CommentBean;
import org.encuestame.utils.web.CreatePollBean;
import org.encuestame.utils.web.DashboardBean;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.encuestame.utils.web.SurveyBean;
import org.encuestame.utils.web.UnitAttachment;
import org.encuestame.utils.web.UnitEmails;
import org.encuestame.utils.web.UnitLists;
import org.encuestame.utils.web.UnitLocationBean;
import org.encuestame.utils.web.UnitLocationFolder;
import org.encuestame.utils.web.PollBean;
import org.encuestame.utils.web.UnitProjectBean;
import org.encuestame.utils.web.UnitSurveySection;
import org.encuestame.utils.web.UserAccountBean;
import org.encuestame.utils.web.search.Search;
import org.encuestame.utils.web.search.TweetPollSearchBean;

/**
 * Abstract Base Unit Beans.
 * @author Morales Urbina, Diana paolaATencuestame.org
 * @since 19/04/2010 20:54:56
 */

public abstract class AbstractBaseUnitBeans extends AbstractBase{

    /**
     *
     * @param questionName
     * @return
     */
    public CreatePollBean createPollBean(
            final String questionName,
            final String[] answer,
            final String[] hashtag,
            final String showComments,
            final String showResults,
            final Boolean multipleSelection,
            final Integer limitVotes,
            final Long closeDate) {
        //"ssss", this.answers, "ALL", "APPROVE" ,Boolean.TRUE, this.tagBeanList
        final CreatePollBean createPollBean = new CreatePollBean();
        createPollBean.setQuestionName(questionName);
        createPollBean.setAnswers(answer);
        createPollBean.setHashtags(hashtag);
        createPollBean.setAllowAdd(Boolean.FALSE); //disable by default
        createPollBean.setLimitVote(limitVotes);
        createPollBean.setMultiple(multipleSelection);
        createPollBean.setResults(showResults);
        createPollBean.setShowComments(showComments);
        createPollBean.setCloseDate(closeDate);
        createPollBean.setFolder_name(null); //disable by default
        return createPollBean;
    }

    /**
     * Helper to {@link CreatePollBean} Private
     * @param questionName
     * @param answer
     * @param hashtag
     * @param showComments
     * @param showResults
     * @param multipleSelection
     * @param limitVotes
     * @param closeDate
     * @param isHidden
     * @param isPasswordProtected
     * @param password
     * @return
     */
    public CreatePollBean createPrivatePollBean(
            final String questionName,
            final String[] answer,
            final String[] hashtag,
            final String showComments,
            final String showResults,
            final Boolean multipleSelection,
            final Integer limitVotes,
			final Long closeDate,
			final Boolean isHidden,
			final Boolean isPasswordProtected,
			final String password) {
    	final CreatePollBean pollBean = this.createPollBean(questionName, answer, hashtag, showComments, showResults, multipleSelection, limitVotes, closeDate);
    	pollBean.setIsHidden(isHidden);
    	pollBean.setIsPasswordProtected(isPasswordProtected);
    	pollBean.setPassword(password);
    	return pollBean;
    }

    /**
     * Create Unit Question Helper.
     * @param questionName
     * @param stateId
     * @param userId
     * @param listAnswers
     * @param pattern
     * @return
     */
    @SuppressWarnings("unchecked")
    public QuestionBean createUnitQuestionBean(
            final String questionName,
            final Long stateId,
            final Long userId,
            final List listAnswers){
         final QuestionBean question = new QuestionBean();
         question.setQuestionName(questionName);
         question.setStateId(stateId);
         question.setUserId(userId);
         question.setListAnswers(listAnswers);
         return question;
    }

     /**
      *
      * @param answerHash
      * @param answers
      * @param questionId
      * @return
      */
     public QuestionAnswerBean createAnswersBean(
             final String answerHash,
             final String answers,
             final Long questionId){
         final QuestionAnswerBean answerBean = new QuestionAnswerBean();
         answerBean.setAnswerHash(answerHash);
         answerBean.setAnswers(answers);
         answerBean.setQuestionId(questionId);
        return answerBean;
     }


    /**
     * Create Unit Tweet Poll.
     * @param allowLiveResults
     * @param closeNotification
     * @param completed
     * @param publicationDateTweet
     * @param publishPoll
     * @param resultNotification
     * @param schedule
     * @param scheduleDate
     * @param tweetUrl
     * @param userId
     * @param questionBean
     * @param userTwitterAccount
     * @return
     */

     public TweetPollBean createTweetPoll(
             final Boolean allowLiveResults,
             final Boolean closeNotification,
             final Boolean completed,
             final Date publicationDateTweet,
             final Boolean publishPoll,
             final Boolean resultNotification,
             final Boolean schedule,
             final Date scheduleDate,
             final String tweetUrl,
             final Long userId,
             final QuestionBean questionBean,
             final String userTwitterAccount

             ){
         TweetPollBean unitTweetPoll = new TweetPollBean();
         unitTweetPoll.setUserId(userId);
         unitTweetPoll.setAllowLiveResults(allowLiveResults);
         unitTweetPoll.setCloseNotification(closeNotification);
         unitTweetPoll.setCompleted(completed);
         unitTweetPoll.setPublishPoll(publishPoll);
         unitTweetPoll.setQuestionBean(createUnitQuestionBean("", 1L, 1L, null));
         unitTweetPoll.setResultNotification(resultNotification);
         unitTweetPoll.setResults(null);
         unitTweetPoll.setSchedule(schedule);
         unitTweetPoll.setScheduleDate(scheduleDate);
         unitTweetPoll.setTweetUrl(tweetUrl);
        // unitTweetPoll.setTwitterUserAccount(null);
        return unitTweetPoll;
     }

     /**
      * Create {@link TweetPollBean}
      * @param allowLiveResults
      * @param closeNotification
      * @param completed
      * @param publicationDateTweet
      * @param publishPoll
      * @param resultNotification
      * @param schedule
      * @param scheduleDate
      * @param tweetUrl
      * @param userId
      * @param questionBean
      * @param userTwitterAccount
      * @param tpollId
      * @return
      */
    public TweetPollBean createTweetPoll(
        final Boolean allowLiveResults,
        final Boolean closeNotification,
        final Boolean completed,
        final Date publicationDateTweet,
        final Boolean publishPoll,
        final Boolean resultNotification,
        final Boolean schedule,
        final Date scheduleDate,
        final String tweetUrl,
        final Long userId,
        final QuestionBean questionBean,
        final String userTwitterAccount,
        final Long tpollId
        ){
        final TweetPollBean tpollBean = this.createTweetPoll(allowLiveResults,
                closeNotification, completed, publicationDateTweet,
                publishPoll, resultNotification, schedule, scheduleDate,
                tweetUrl, userId, questionBean, userTwitterAccount);
        return tpollBean;

    }

    /**
     * Helper Create Unit Tweet Poll Publicated.
     *
     * @param publicationDateTweet
     * @param publishPoll
     * @param tweetUrl
     * @param userId
     * @param questionBean
     * @param userTwitterAccount
     * @param captcha
     * @param allowLiveResults
     * @param resultNotification
     * @param limitVotes
     * @param relevance
     * @param hits
     * @param likeVote
     * @param dislikeVote
     * @param createDate
     * @param scheduleDate
     * @param updateDate
     * @param dateToLimit
     * @return
     */
    public TweetPollBean createUnitTweetPollPublicated(
            final Date publicationDateTweet, final Boolean publishPoll,
            final String tweetUrl, final Long userId,
            final QuestionBean questionBean, final String userTwitterAccount,
            final Boolean captcha, final Boolean allowLiveResults,
            final Boolean resultNotification, final Integer limitVotes,
            final Long relevance, final Long hits, final Long likeVote,
            final Long dislikeVote, final String createDate,
            final Date scheduleDate, final Date updateDate,
            final String dateToLimit) {
        TweetPollBean unitTweetPoll = new TweetPollBean();
        unitTweetPoll.getCloseNotification();
        unitTweetPoll.setCaptcha(captcha);
        unitTweetPoll.setAllowLiveResults(allowLiveResults);
        unitTweetPoll.setLimitVotes(limitVotes);
        unitTweetPoll.setResultNotification(resultNotification);
        unitTweetPoll.setRelevance(relevance);
        unitTweetPoll.setHits(hits);
        unitTweetPoll.setLikeVote(likeVote);
        unitTweetPoll.setDislikeVote(dislikeVote);
        unitTweetPoll.setCreateDate(createDate);
        unitTweetPoll.setScheduleDate(scheduleDate);
        unitTweetPoll.setUpdateDate(updateDate);
        unitTweetPoll.setDateToLimit(dateToLimit);
        unitTweetPoll.setUpdateDate(updateDate);
        unitTweetPoll.setUserId(userId);
        unitTweetPoll.setPublishPoll(publishPoll);
        unitTweetPoll.setQuestionBean(createUnitQuestionBean("", 1L, 1L,
                null));
        unitTweetPoll.setResults(null);
        unitTweetPoll.setTweetUrl(tweetUrl);

        // unitTweetPoll.setTwitterUserAccount(null);
        return unitTweetPoll;
    }

    /**
     * Helper Create Unit Tweet Poll Publicated.
     * @param publicationDateTweet
     * @param publishPoll
     * @param tweetUrl
     * @param userId
     * @param questionBean
     * @param userTwitterAccount
     * @return
     */
    public TweetPollBean createUnitTweetPollPublicated(
            final Date publicationDateTweet, final Boolean publishPoll,
            final String tweetUrl, final Long userId,
            final QuestionBean questionBean, final String userTwitterAccount) {
        TweetPollBean unitTweetPoll = new TweetPollBean();
        unitTweetPoll.setUserId(userId);
        unitTweetPoll.setPublishPoll(publishPoll);
        unitTweetPoll.setQuestionBean(createUnitQuestionBean("", 1L, 1L,
                null));
        unitTweetPoll.setResults(null);
        unitTweetPoll.setTweetUrl(tweetUrl);

        // unitTweetPoll.setTwitterUserAccount(null);
        return unitTweetPoll;
      }

    /**
     * Helper Create Unit TweetPoll.
     *
     * @param publishPoll
     * @param tweetUrl
     * @param userId
     * @param questionBean
     * @return
     */
    public TweetPollBean createUnitTweetPoll(final Boolean publishPoll,
            final String tweetUrl, final Long userId,
            final QuestionBean questionBean) {
        final TweetPollBean tpBean = this.createUnitTweetPollPublicated(
                new Date(), publishPoll, tweetUrl, userId, questionBean, "",
                Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, null, 1L, 1L, 1L,
                1L, null, null, null, null);
        return tpBean;
    }

     /**
      *
      * @param tweetPoll
      * @param answers
      * @param codeTweet
      * @return
      */
     public TweetPollSwitch createTweetPollSwitch(
             final TweetPoll tweetPoll,
             final QuestionAnswer answers,
             final String codeTweet) {
         final TweetPollSwitch tweetPollSwitch = new TweetPollSwitch();
         tweetPollSwitch.setAnswers(answers);
         tweetPollSwitch.setCodeTweet(codeTweet);
         tweetPollSwitch.setTweetPoll(tweetPoll);
        return tweetPollSwitch;

     }

     /**
      *
      * @param questionId
      * @param questionName
      * @param stateId
      * @param userId
      * @param listAnswers
      * @param pattern
      * @return
      */
     @SuppressWarnings("unchecked")
     public QuestionBean createUnitQuestion(
             final Long questionId,
             final String questionName,
             final Long stateId,
             final Long userId,
             final List listAnswers){
          final QuestionBean question = new QuestionBean();
          question.setId(questionId);
          question.setQuestionName(questionName);
          question.setStateId(stateId);
          question.setUserId(userId);
          question.setListAnswers(listAnswers);
          return question;
     }

     /**
      * Helper
      * Create Unit Email List.
      * @param emailListId
      * @param createdAt
      * @param listName
      * @param userId
      * @return
      */
     public UnitLists createUnitEmailList(
            final Long emailListId,
            final Date createdAt,
            final String listName,
            final Long userId)
     {
         final UnitLists emailList = new UnitLists();
         emailList.setId(emailListId);
         emailList.setCreatedAt(createdAt);
         emailList.setListName(listName);
         emailList.setUserId(userId);

         return emailList;
     }

     /**
      * Helper
      * Create Unit Emails.
      * @param idEmail Email Id
      * @param emailName Email
      * @param listsId Email List Id
      * @return
      */
     public UnitEmails createUnitEmails(
             final Long idEmail,
             final String emailName,
             final Long listsId){
         final UnitEmails unitEmails = new UnitEmails();
         unitEmails.setIdEmail(idEmail);
         unitEmails.setEmailName(emailName);
         unitEmails.setListsId(listsId);
         return unitEmails;
     }

     /**
      * Create Unit Location Bean.
      * @param name
      * @return
      */
     public UnitLocationBean createUnitLocationBean(final String name){
         final UnitLocationBean locationBean = new UnitLocationBean();
         locationBean.setAccuracy(232);
         locationBean.setAddress("address");
         locationBean.setCountryCode("SP");
         locationBean.setCountryName("spain");
         locationBean.setLat(124.232F);
         locationBean.setLevel(1);
         locationBean.setLng(-2321.23F);
         locationBean.setName(name);
         locationBean.setStatus("ACTIVE");
         locationBean.setTidtype(21L);
         return locationBean;
     }

     /**
      * Create Unit Location Folder.
      * @param name
      * @return
      */
     public UnitLocationFolder createUnitLocationFolder(final String name){
         final UnitLocationFolder folder = new UnitLocationFolder();
         folder.setName(name);
         folder.setType(GeoPointFolderType.GROUPING.name());
         return folder;
     }

     /**
      * Create SignUpBean.
      * @param username username
      * @param email email.
      * @param password password
      * @return {@link SignUpBean}.
      */
     public SignUpBean createSignUpBean(final String username, final String email, final String password){
         final SignUpBean signUpBean = new SignUpBean();
             signUpBean.setCaptcha("12345");
             signUpBean.setEmail(email);
             signUpBean.setFullName(username);
             signUpBean.setPassword(password);
             signUpBean.setUsername(username);
         return signUpBean;
     }

     /**
      * Create {@link UserAccountBean}.
      * @param username
      * @return
      */
     public UserAccountBean createUnitUserBean(final String username, final String email){
         final UserAccountBean bean = new UserAccountBean();
         bean.setDateNew(new Date());
         bean.setEmail(email);
         bean.setUsername(username);
         bean.setPassword("xxxxx");
         return bean;
     }

     /**
      * Create Unit Attachment Bean.
      * @param filename
      * @param uploadDate
      * @param projectBean
      * @return
      */
     public UnitAttachment createUnitAttachment(final String filename, final Date uploadDate,
                            final UnitProjectBean projectBean){
        final UnitAttachment unitAttachmentBean = new UnitAttachment();
        unitAttachmentBean.setFilename(filename);
        unitAttachmentBean.setProjectBean(projectBean);
        unitAttachmentBean.setUploadDate(uploadDate);
        return unitAttachmentBean;
     }

     /**
      * Create folder bean.
      * @param createdAt
      * @param folderName
      * @return
      */
     public FolderBean createFolderBean(final Date createdAt, final String folderName){
        final FolderBean fbean = new FolderBean();
        fbean.setCreateAt(createdAt);
        fbean.setFolderName(folderName);
        return fbean;
     }

     /**
      * Create unitPoll.
      * @param closeNotification
      * @param completedPoll
      * @param creationDate
      * @param finishDate
      * @param hashPoll
      * @param publishPoll
      * @param questionBean
      * @param showResultsPoll
      * @return
      */
     public PollBean createUnitPoll(final Boolean closeNotification, final Boolean completedPoll,
                                    final Date creationDate, final Date finishDate, final String hashPoll,
                                    final Boolean publishPoll, final QuestionBean questionBean,
                                    final Boolean showResultsPoll){
        final PollBean unitPoll = new PollBean();
        unitPoll.setCloseNotification(closeNotification);
        unitPoll.setCompletedPoll(completedPoll);
        unitPoll.setCreateDate(DateUtil.DOJO_DATE_FORMAT.format(creationDate));
        unitPoll.setFinishDate(finishDate);
        unitPoll.setPublishPoll(publishPoll);
        unitPoll.setQuestionBean(questionBean);
        unitPoll.setShowResultsPoll(showResultsPoll);
        return unitPoll;
     }

     /**
      * Create unitPoll default.
      * @param questionBean
      * @return
      */
     public PollBean createUnitPollDefault(final QuestionBean questionBean){
        return this.createUnitPoll(Boolean.TRUE, Boolean.TRUE, new Date(), new Date(), "h1a2s3hP", Boolean.TRUE,
                questionBean, Boolean.TRUE);
     }

     /**
      * Create dashboard bean.
      * @param dashboardName
      * @param dashboardDesc
      * @param favorite
      * @param layout
      * @param sequence
      * @param counter
      * @return
      */
     public DashboardBean createDashboardBean(
             final String dashboardName,
             final String dashboardDesc,
             final Boolean favorite,
             final LayoutEnum layout,
             final Integer sequence,
             final Integer counter){
         final DashboardBean dashboardBean = new DashboardBean();
         dashboardBean.setDashboardName(dashboardName);
         dashboardBean.setDashboardDesc(dashboardDesc);
         dashboardBean.setFavorite(favorite);
         dashboardBean.setLayout(layout.toString());
         dashboardBean.setSequence(sequence);
         dashboardBean.setFavoriteCounter(counter);
         return dashboardBean;
     }

     /**
      * Create comment bean.
      * @param comment
      * @param createdAt
      * @param userId
      * @param tweetPollId
      * @param pollId
      * @return
      */
     public CommentBean createCommentBean(final String comment,
             final Date createdAt, final Long userId, final Long tweetPollId, final Long pollId){
         final CommentBean commentBean = new CommentBean();
         commentBean.setComment(comment);
         commentBean.setCreatedAt(createdAt);
         commentBean.setUserAccountId(userId);
         commentBean.setId(tweetPollId);
         commentBean.setType(TypeSearchResult.TWEETPOLL);
         return commentBean;
     }

     /**
      *
      * @param surveyName
      * @param ownerUsername
      * @param createdAt
      * @return
      */
     public SurveyBean createSurveyBean(final String surveyName, final String ownerUsername, final Date createdAt) {
         final SurveyBean surveyBean = new SurveyBean();
         surveyBean.setTicket(1);
         surveyBean.setOwnerUsername(ownerUsername);
         surveyBean.setName(surveyName);
         surveyBean.setCreatedAt(createdAt);
         return surveyBean;
     }

     /**
      * Create {@link UnitSurveySection}.
      * @param name
      * @param description
      * @param survey
      * @return
      */
     public UnitSurveySection createSurveySection(final String name,
             final String description, final Survey survey) {
         final UnitSurveySection sectionBean = new UnitSurveySection();
         sectionBean.setDescription(description);
         sectionBean.setName(name);
         return sectionBean;
     }

     /**
     *
     * @param isPublished
     * @param isComplete
     * @param isFavourite
     * @param isScheduled
     * @param keyword
     * @param period
     * @param max
     * @param start
     * @return
     */
    public TweetPollSearchBean createTweetpollSearchBean(
            final Boolean isPublished, final Boolean isComplete,
            final Boolean isFavourite, final Boolean isScheduled,
            final String keyword, final String period, final Integer max,
            final Integer start, final  TypeSearch typeSearch) {
        final TweetPollSearchBean tpollSearchBean = new TweetPollSearchBean();
        tpollSearchBean.setIsComplete(isComplete);
        tpollSearchBean.setIsFavourite(isFavourite);
        tpollSearchBean.setIsPublished(isPublished);
        tpollSearchBean.setIsScheduled(isScheduled);
        tpollSearchBean.setKeyword(keyword);
        tpollSearchBean.setMax(max);
        tpollSearchBean.setPeriod(period);
        tpollSearchBean.setStart(start);
        tpollSearchBean.setTypeSearch(typeSearch);
        return tpollSearchBean;
    }

}
