/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2010
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

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.service.imp.IPollService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.persistence.domain.Email;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.encuestame.persistence.domain.survey.PollResult;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMePollNotFoundException;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.MD5Utils;
import org.encuestame.utils.enums.CommentOptions;
import org.encuestame.utils.enums.NotificationEnum;
import org.encuestame.utils.enums.QuestionPattern;
import org.encuestame.utils.enums.TypeSearch;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.FolderBean;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.web.PollBean;
import org.encuestame.utils.web.PollBeanResult;
import org.encuestame.utils.web.PollDetailBean;
import org.encuestame.utils.web.UnitLists;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Poll Service.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since  April 01, 2010
 */
@Service
public class PollService extends AbstractSurveyService implements IPollService{

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());


    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#filterPollByItemsByType(org.encuestame.utils.enums.TypeSearch, java.lang.String, java.lang.Integer, java.lang.Integer, org.encuestame.utils.enums.TypeSearchResult)
     */

    public List<PollBean> filterPollByItemsByType(
            final TypeSearch typeSearch,
            String keyword, Integer max, Integer start)
            throws EnMeNoResultsFoundException, EnMeExpcetion {
        log.debug("filterPollByItemsByType");
        log.debug("--> "+typeSearch);
        log.debug("--> "+keyword);
        log.debug("--> "+max);
        log.debug("--> "+start);
        final List<PollBean> list = new ArrayList<PollBean>();
        if (TypeSearch.KEYWORD.equals(typeSearch)) {
            list.addAll(this.searchPollByKeyword(keyword, max, start));
        } else if (TypeSearch.BYOWNER.equals(typeSearch)) {
            list.addAll(ConvertDomainBean.convertListToPollBean(getPollDao()
                    .findAllPollByEditorOwner(
                            getUserAccount(getUserPrincipalUsername()), max,
                            start)));
       } else if (TypeSearch.LASTDAY.equals(typeSearch)) {
            list.addAll(ConvertDomainBean.convertListToPollBean(this.
            getPollDao().retrievePollToday(
                                        getUserAccount(getUserPrincipalUsername())
                                                .getAccount(), max, start,
                                        DateUtil.getNextDayMidnightDate())));
      } else if (TypeSearch.LASTWEEK.equals(typeSearch)) {
            list.addAll(ConvertDomainBean.convertListToPollBean(this
                    .getPollDao().retrievePollLastWeek(
                            getUserAccount(getUserPrincipalUsername())
                                    .getAccount(), max, start,
                            DateUtil.getNextDayMidnightDate())));
        } else if (TypeSearch.FAVOURITES.equals(typeSearch)) {
            list.addAll(ConvertDomainBean.convertListToPollBean(getPollDao()
                    .retrieveFavouritesPoll(
                            getUserAccount(getUserPrincipalUsername()), max,
                            start)));
        } else if (TypeSearch.ALL.equals(typeSearch)) {
            list.addAll(ConvertDomainBean.convertListToPollBean(this
                    .getPollsByRange(max, start, null)));
        } else {
           throw new EnMeExpcetion("operation not valid");
        }
        log.debug("Poll Search Items : "+list.size());
        return list;
    }

   /*
    * (non-Javadoc)
    * @see org.encuestame.core.service.imp.IPollService#createPoll(java.lang.String, java.lang.String[], java.lang.Boolean, java.lang.String, java.lang.Boolean)
    */
    public Poll createPoll(final String questionName, final String[] answers, final Boolean showResults,
        final String commentOption, final Boolean notification) throws EnMeExpcetion{
        final UserAccount user = getUserAccount(getUserPrincipalUsername());
        Assert.notNull(answers);
        log.debug("poll list answer=>" + answers);
        Assert.notNull(user);
        Assert.notNull(questionName);
        final Poll pollDomain = new Poll();
        try {
            final QuestionBean questionBean = new QuestionBean();
            questionBean.setQuestionName(questionName);
            final Question question = createQuestion(questionBean, user, QuestionPattern.CUSTOMIZABLE_SELECTION);
            log.debug("question found : {"+question);
            log.debug("answers found : {"+answers.length);
            if (question == null) {
                throw new EnMeNoResultsFoundException("Question not valid");
            } else if (answers.length  == 0 ) {
                  throw new EnMeNoResultsFoundException("answers are required to create Poll");
            }
            else{
            //TODO: move hash to util.
            final String hashPoll = MD5Utils.md5(RandomStringUtils.randomAlphanumeric(500));
            log.debug("OPTION SHOW COMMENTS GETTED BEFORE---> " +commentOption);
            final CommentOptions commentOpt = CommentOptions.getCommentOption(commentOption);
            log.debug("OPTION SHOW COMMENTS GETTED ENUM---> " +commentOpt);
            pollDomain.setEditorOwner(user);
            pollDomain.setCreatedAt(Calendar.getInstance().getTime());
            pollDomain.setPollHash(hashPoll);
            pollDomain.setQuestion(question);
            pollDomain.setPollCompleted(Boolean.FALSE);
            pollDomain.setHits(EnMeUtils.HIT_DEFAULT);
            pollDomain.setRelevance(EnMeUtils.RATE_DEFAULT);
            pollDomain.setLikeVote(EnMeUtils.LIKE_DEFAULT);
            pollDomain.setDislikeVote(EnMeUtils.DISLIKE_DEFAULT);
            pollDomain.setNumbervotes(EnMeUtils.VOTE_MIN);
            pollDomain.setEditorOwner(user);
            pollDomain.setOwner(user.getAccount());
            pollDomain.setShowResults(showResults);

            pollDomain.setShowComments(commentOpt == null ? null :commentOpt);
            pollDomain.setPublish(Boolean.TRUE);
            pollDomain.setNotifications(notification);
            pollDomain.setPublish(Boolean.TRUE);
            log.debug("poll list answer=>" + answers.length);
            for (int row = 0; row < answers.length; row++) {
                 final String answersText = answers[row];
                 Assert.notNull(answersText);
                 if (!answersText.isEmpty()) {
                     log.debug("creatong answer=>" + question.getQidKey());
                     log.debug("creatong answer=>" + answersText.trim());
                     createAnswers(question, answersText.trim());

                 }
            }
            this.getPollDao().saveOrUpdate(pollDomain);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.equals(e);
            throw new EnMeExpcetion(e);
        }
        return pollDomain;
    }

    /**
     * Get Poll by Id without user session.
     * @param pollId poll id.
     * @return
     * @throws EnMeNoResultsFoundException
     */
     private Poll getPoll(final Long pollId) throws EnMeNoResultsFoundException{
         return this.getPollDao().getPollById(pollId);
     }

     /**
      * Remove Poll
      * @param pollId
      * @throws EnMeNoResultsFoundException
      */
    public void removePoll(final Long pollId) throws EnMeNoResultsFoundException{
        final Poll pollDomain = this.getPoll(pollId);
        if(pollDomain != null){
              getPollDao().delete(pollDomain);
          } else {
              throw new EnMeNoResultsFoundException("Poll not found");
          }
      }

    /**
     * Search Polls by Question keyword.
     * @param keywordQuestion
     * @param username
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeExpcetion
     */

    public List<PollBean> searchPollByKeyword(final String keywordQuestion, final Integer maxResults,
        final Integer start) throws EnMeExpcetion{
        log.debug("search keyword Poll  "+keywordQuestion);
        List<Poll> polls = new ArrayList<Poll>();
        if (keywordQuestion == null) {
            throw new EnMeExpcetion("keyword is mandatory");
        } else {
            polls = getPollDao().getPollsByQuestionKeyword(keywordQuestion,
                    getUserAccount(getUserPrincipalUsername()), maxResults, start);
        }
        log.debug("search keyword polls size "+polls.size());
        return ConvertDomainBean.convertListToPollBean(polls);
       }

    /**
     * Search Polls by Folder Id.
     * @param folderId
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */

    public List<PollBean> searchPollsByFolder(final Long folderId, final String username) throws EnMeNoResultsFoundException{
        final PollFolder pollFolder = getPollDao().getPollFolderById(folderId);
        List<Poll> polls = new ArrayList<Poll>();
        if (pollFolder != null){
            polls = getPollDao().getPollsByPollFolderId(getUserAccount(getUserPrincipalUsername()), pollFolder);
        }
        log.info("search polls by folder size "+polls.size());
        return ConvertDomainBean.convertSetToPollBean(polls);
    }


    /**
     * List Poll ByUser.
     * @param currentUser currentUser
     * @return unitPoll
     * @throws EnMeNoResultsFoundException
     */

    public List<PollBean> listPollByUser(final Integer maxResults, final Integer start) throws EnMeNoResultsFoundException{
        final List<PollBean> unitPoll = new ArrayList<PollBean>();
        final List<Poll> polls = getPollDao().findAllPollByEditorOwner(getUserAccount(getUserPrincipalUsername()), maxResults, start);
         for (Poll poll : polls) {
             unitPoll.add(ConvertDomainBean.convertPollDomainToBean(poll));
        }
        return unitPoll;
    }

   /*
    * (non-Javadoc)
    * @see org.encuestame.core.service.imp.IPollService#vote(java.lang.Long, java.lang.String, java.lang.String, java.lang.Long)
    */
    public void vote(final Long pollId, final String slug,
            final String ipAddress,
            final Long answerId)
            throws EnMeNoResultsFoundException {
        log.debug("vote "+pollId);
        log.debug("vote "+slug);
        log.debug("vote "+ipAddress);
        log.debug("vote "+answerId);
        final Poll poll = this.getPollDao().getPollById(pollId, slug, false);
        if (poll == null) {
            throw new EnMeNoResultsFoundException("poll not found");
        }
        final PollResult pr = new PollResult();
        pr.setAnswer(this.getQuestionAnswerById(answerId));
        //validate IP address.
        pr.setIpaddress(ipAddress);
        pr.setPoll(poll);
        pr.setVotationDate(Calendar.getInstance().getTime());
        getPollDao().saveOrUpdate(pr);
    }

    /**
     * Get Polls by Folder.
     * @param folder
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */

    public List<PollBean> getPollsByFolder(final FolderBean folder) throws EnMeNoResultsFoundException{
        final List<Poll> polls = getPollDao().getPollsByPollFolder(getUserAccount(getUserPrincipalUsername()), getPollFolder(folder.getId()));
        return ConvertDomainBean.convertSetToPollBean(polls);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#updateQuestionPoll(java.lang.Long, org.encuestame.persistence.domain.question.Question)
     */
    public PollBean updateQuestionPoll(final Long pollId, final Question question)
            throws EnMeExpcetion {
          final Poll poll = this.getPollById(pollId, getUserAccount(getUserPrincipalUsername()));
          if (poll == null) {
              throw new EnMeNoResultsFoundException("poll not found");
          }
          else{
              poll.setQuestion(question) ;
              getPollDao().saveOrUpdate(poll);
          }
           return ConvertDomainBean.convertPollDomainToBean(poll);
    }

    /**
     * Create url poll Access.
     * @param poll
     * @param domain
     * @param currentUser
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    private String createUrlPollAccess(final Poll poll) {
        StringBuffer urlBuffer = new StringBuffer("/poll/");
        urlBuffer.append(poll.getPollHash());
        urlBuffer.append("/");
        urlBuffer.append(poll.getPollId());
        urlBuffer.append("/");
        urlBuffer.append(poll.getQuestion().getSlugQuestion());
        return urlBuffer.toString();
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#createPollNotification(org.encuestame.persistence.domain.survey.Poll)
     */
    public void createPollNotification(final Poll poll) throws EnMeNoResultsFoundException {
        createNotification(NotificationEnum.POLL_PUBLISHED,
                getMessageProperties("notification.poll.publish"),
                this.createUrlPollAccess(poll), false);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#publicPollByList(org.encuestame.persistence.domain.survey.Poll, org.encuestame.utils.web.UnitLists)
     */
    @SuppressWarnings("unused")
    public void publicPollByList(final Poll poll, final UnitLists emailList) {
        final List<Email> emailsList = getEmailListsDao().findEmailsByListId(emailList.getId());
        final String urlPoll = this.createUrlPollAccess(poll);
        if(emailList !=null){
                 for (Email emails : emailsList) {
                   getMailService().send(emails.getEmail(),"New Poll", urlPoll);
                  }
         }
         else{
             log.warn("Not Found Emails in your EmailList");
        }
    }

    /**
     * Retrieve Folder Poll.
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException exception
     */

    public List<FolderBean> retrieveFolderPoll() throws EnMeNoResultsFoundException {
        final List<PollFolder> folders = getPollDao().getPollFolderByUserAccount(getUserAccount(getUserPrincipalUsername()));
        return ConvertDomainBean.convertListPollFolderToBean(folders);
    }

    /**
     * Create Poll Folder.
     * @param folderName
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */

    public FolderBean createPollFolder(final String folderName) throws EnMeNoResultsFoundException {
        final PollFolder pollFolder = new PollFolder();
        pollFolder.setCreatedBy(getUserAccount(getUserPrincipalUsername()));
        pollFolder.setCreatedAt(Calendar.getInstance().getTime());
        pollFolder.setFolderName(folderName);
        pollFolder.setUsers(getUserAccount(getUserPrincipalUsername()).getAccount());
        this.getPollDao().saveOrUpdate(pollFolder);
        return ConvertDomainBean.convertFolderToBeanFolder(pollFolder);
    }

    /**
     * Update FolderName.
     * @param folderId folder id
     * @param newFolderName folder name
     * @param username username
     * @return {@link FolderBean}
     * @throws EnMeNoResultsFoundException exception
     */
    public FolderBean updateFolderName(final Long folderId,
            final String newFolderName,
            final String username) throws EnMeNoResultsFoundException{
        final PollFolder folder = this.getPollFolder(folderId);
        if(folder == null){
            throw new EnMeNoResultsFoundException("poll folder not found");
        } else {
            folder.setFolderName(newFolderName);
            getPollDao().saveOrUpdate(folder);
        }
        return ConvertDomainBean.convertFolderToBeanFolder(folder);
    }

    /**
     * Get Poll Folder.
     * @param id
     * @return
     * @throws EnMeNoResultsFoundException
     */
    private PollFolder getPollFolder(final Long id) throws EnMeNoResultsFoundException{
        if(id == null){
             throw new EnMeNoResultsFoundException("poll folder id not found");
        }else {
        return this.getPollDao().getPollFolderById(id);
        }
    }

    /**
     * Remove PollFolder.
     * @param folderId
     * @throws EnMeNoResultsFoundException
     */
    public void removePollFolder(final Long folderId) throws EnMeNoResultsFoundException{
        final PollFolder folder = this.getPollFolder(folderId);
        if(folder != null){
            getPollDao().delete(folder);
        } else {
            throw new EnMeNoResultsFoundException("poll folder not found");
        }
    }

    public List<PollBean> listPollByUser(String currentUser)
            throws EnMeNoResultsFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Add poll to Folder.
     * @param folderId
     * @param username
     * @param pollId
     * @throws EnMeNoResultsFoundException
     */

    public void addPollToFolder(final Long folderId, final Long pollId)
                                throws EnMeNoResultsFoundException{
        final PollFolder pfolder = this.getPollFolderByFolderIdandUser(folderId, getUserAccount(getUserPrincipalUsername()));
        if (pfolder != null) {
            final Poll poll = getPollDao().getPollById(pollId, getUserAccount(getUserPrincipalUsername()));
            if (poll == null){
                throw new EnMeNoResultsFoundException("TweetPoll not found");
             }
            poll.setPollFolder(pfolder);
            getPollDao().saveOrUpdate(poll);
        } else {
            throw new EnMeNoResultsFoundException("TweetPoll folder not found");
    }
}

    /**
     * Get Poll folder.
     * @param folderId
     * @param userId
     * @return
     */
    public PollFolder getPollFolderByFolderIdandUser(final Long folderId, final UserAccount userAccount){
        return this.getPollDao().getPollFolderByIdandUser(folderId, userAccount);
    }

    /**
     * Get polls by creation date.
     * @param userId
     * @param date
     * @param maxResults
     * @param star
     * @return
     * @throws EnMeNoResultsFoundException
     */

    public List<PollBean> getPollsbyDate(final Date date, final Integer maxResults,
            final Integer start) throws EnMeNoResultsFoundException{
        List<Poll> pollList = new ArrayList<Poll>();
        if (date !=null){
            pollList = getPollDao().getPollByUserIdDate(date, getUserAccount(getUserPrincipalUsername()), maxResults, start);
        }
        else{
            throw new EnMeNoResultsFoundException("Date not found");
        }
        return ConvertDomainBean.convertSetToPollBean(pollList);
    }

    /**
     * Get poll by id and user.
     * @param pollId
     * @param account
     * @return
     * @throws EnMeNoResultsFoundException
     */
     public Poll getPollById(final Long pollId, final UserAccount account) throws EnMeNoResultsFoundException{
        final Poll poll = getPollDao().getPollById(pollId, account);
        if (poll == null) {
            log.error("poll invalid with this id "+pollId+ " and username:{"+account);
            throw new EnMePollNotFoundException("poll invalid with this id "+pollId+ " and username:{"+account);
        }
        return poll;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#getPollById(java.lang.Long)
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

    /**
     * Retrieve a {@link Poll} based on id.
     * @param pollId poll id.
     * @param account username account
     * @return {@link Poll}
     * @throws EnMeNoResultsFoundException
     */
    public Poll getPollById(final Long pollId, final String account) throws EnMeNoResultsFoundException {
    	 log.debug("getPollById pollId: "+pollId);
    	 log.debug("getPollById account: "+account);
         final Poll poll = this.getPollById(pollId, getUserAccount(account));
         if (poll == null) {
             log.error("poll invalid with this id "+pollId+ " and username:{"+account);
             throw new EnMePollNotFoundException("poll invalid with this id "+pollId+ " and username:{"+account);
         }
         return poll;
     }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#getPollSlugById(java.lang.Long, java.lang.String)
     */
    public Poll getPollSlugById(final Long pollId, final String slug) throws EnMeNoResultsFoundException{
        final Poll poll = this.getPollDao().getPollById(pollId, slug, true);
        if (poll == null) {
            log.error("poll invalid with this id "+pollId+ " and slug:{"+slug);
            throw new EnMePollNotFoundException("poll invalid with this id "+pollId+ " and slug:{"+slug);
        }
        return poll;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.IPollService#getPollsByUserName(java.lang.String, java.lang.Integer, java.lang.Integer)
     */

    public List<PollBean> getPollsByUserName(
            final String username,
            final Integer maxResults,
            final Integer start) throws EnMeNoResultsFoundException{
        log.debug("Poll username "+username);
        final List<Poll> polls = getPollDao()
             .retrievePollsByUserId(getUserAccount(getUserPrincipalUsername()), maxResults, start);
         log.info("Polls size "+ polls.size());
         final List<PollBean> pollBean = ConvertDomainBean.convertSetToPollBean(polls);
        return pollBean;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#getPolls(java.lang.Integer, java.lang.Integer, java.util.Date)
     */
    public List<Poll> getPollsByRange(final Integer maxResults,
            final Integer start, final Date range) {
        final List<Poll> polls = getPollDao().getPolls(
                maxResults, start, range);
        return polls;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#changeStatusPoll(java.lang.Long, java.lang.String)
     */
    public void changeStatusPoll(final Long pollId, final String username)
            throws EnMeNoResultsFoundException, EnmeFailOperation {
        final Poll poll = getPollById(pollId, username);
        if (poll != null) {
            poll.setCloseAfterDate(!(poll.getCloseAfterDate() == null ? false
                    : poll.getCloseAfterDate()));
            getPollDao().saveOrUpdate(poll);
        } else {
            throw new EnmeFailOperation("Fail Change Status Operation");
        }
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#closeAfterQuotaPoll(java.lang.Long, java.lang.String)
     */
    public void closeAfterQuotaPoll(final Long pollId, final String username)
            throws EnMeNoResultsFoundException, EnmeFailOperation {
        final Poll poll = getPollById(pollId, username);
        if (poll != null) {
            poll.setCloseAfterquota(!(poll.getCloseAfterquota() == null ? false
                    : poll.getCloseAfterquota()));
            getPollDao().saveOrUpdate(poll);
        } else {
            throw new EnmeFailOperation("Fail Change Status Operation");
        }
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#ipProtectionPoll(java.lang.Long, java.lang.String)
     */
    public void ipProtectionPoll(final Long pollId, final String username)
            throws EnMeNoResultsFoundException, EnmeFailOperation {
        final Poll poll = getPollById(pollId, username);
        if (poll != null) {
            poll.setIpRestriction(!(poll.getIpRestriction() == null ? false
                    : poll.getIpRestriction()));
            getPollDao().saveOrUpdate(poll);
        } else {
            throw new EnmeFailOperation("Fail Change Status Operation");
        }
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#ipEnableNotificationsPoll(java.lang.Long, java.lang.String)
     */
    public void enableNotificationsPoll(final Long pollId,
            final String username) throws EnMeNoResultsFoundException,
            EnmeFailOperation {
        final Poll poll = getPollById(pollId, username);
        if (poll != null) {
            poll.setNotifications(!(poll.getNotifications() == null ? false
                    : poll.getNotifications()));
            getPollDao().saveOrUpdate(poll);
        } else {
            throw new EnmeFailOperation("Fail Change Status Operation");
        }
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#setAdditionalInfoPoll(java.lang.Long, java.lang.String)
     */
    public void setAdditionalInfoPoll(final Long pollId, final String username)
            throws EnMeNoResultsFoundException, EnmeFailOperation {
        final Poll poll = getPollById(pollId, username);
        if (poll != null) {
            poll.setShowAdditionalInfo(!(poll.getShowAdditionalInfo() == null ? false
                    : poll.getShowAdditionalInfo()));
            getPollDao().saveOrUpdate(poll);
        } else {
            throw new EnmeFailOperation("Fail Change Status Operation");
        }
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#setPasswordRestrictionsPoll(java.lang.Long, java.lang.String)
     */
    public void setPasswordRestrictionsPoll(final Long pollId,
            final String username) throws EnMeNoResultsFoundException,
            EnmeFailOperation {
        final Poll poll = getPollById(pollId, username);
        if (poll != null) {
            poll.setPasswordRestrictions(!(poll.getPasswordRestrictions() == null ? false
                    : poll.getPasswordRestrictions()));
            getPollDao().saveOrUpdate(poll);
        } else {
            throw new EnmeFailOperation("Fail Change Status Operation");
        }
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#setShowResultsPoll(java.lang.Long, java.lang.String)
     */
    public void setShowResultsPoll(final Long pollId, final String username)
            throws EnMeNoResultsFoundException, EnmeFailOperation {
        final Poll poll = getPollById(pollId, username);
        if (poll != null) {
            poll.setShowResults(!(poll.getShowResults() == null ? false : poll
                    .getShowResults()));
            getPollDao().saveOrUpdate(poll);
        } else {
            throw new EnmeFailOperation("Fail Change Status Operation");
        }
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#getResultVotes(org.encuestame.persistence.domain.survey.Poll)
     */
    public List<PollBeanResult> getResultVotes(final Poll poll) {
    	log.debug("poll getResultVotes " + poll);
    	final List<PollBeanResult> results = new ArrayList<PollBeanResult>();
    	final List<Object[]> list = getPollDao().retrieveResultPolls(poll.getPollId(), poll.getQuestion().getQid());
        //log.debug("retrieveResultPolls==> "+list.size());
        for (Object[] objects : list) {
            final Long answerId = objects[0] == null ? null : Long.valueOf(objects[0].toString());
            final String answerString = objects[1] == null ? null : objects[1].toString();
            final String color = objects[2] == null ? null : objects[2].toString();
            final Long votes = objects[3] == null ? null : Long.valueOf(objects[3].toString());
            if (answerId != null) {
	            final PollBeanResult result = ConvertDomainBean.convertPollResultToBean(answerId, answerString, color, votes, poll.getQuestion());	            
	            results.add(result);
            } else {
                throw new IllegalArgumentException("answer id is empty");
            }
        }
        log.debug("poll PollBeanResult " + results.size());
        this.calculatePercents(results);
    	return results;
    }
    
    /**
     * Calculate the percents.
     * @param beanResults
     */
	private void calculatePercents(final List<PollBeanResult> beanResults) {
		double totalVotes = 0;
		for (PollBeanResult pollBeanResult : beanResults) {
			totalVotes += totalVotes + pollBeanResult.getResult();
		}
		for (PollBeanResult pollBeanResult : beanResults) {
			pollBeanResult.setPercent(EnMeUtils.calculatePercent(totalVotes, pollBeanResult.getResult()));
		}
	}

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#getPollDetailInfo(java.lang.Long)
     */
    public PollDetailBean getPollDetailInfo(final Long pollId) throws EnMeNoResultsFoundException{
        final PollDetailBean detail = new PollDetailBean();
        final Poll poll = getPoll(pollId);
        detail.setPollBean(ConvertDomainBean.convertPollDomainToBean(poll));
        detail.setResults(this.getResultVotes(poll));
        this.calculatePercents(detail.getResults());
        //set the list of answers
        detail.setListAnswers(ConvertDomainBean
                .convertAnswersToQuestionAnswerBean(getQuestionDao()
                        .getAnswersByQuestionId(poll.getQuestion().getQid())));
        //set the comments.
        detail.getPollBean().setTotalComments(this.getTotalCommentsbyType(detail.getPollBean().getId(), TypeSearchResult.POLL));
        return detail;
    }
    
    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#validatePollIP(java.lang.String, org.encuestame.persistence.domain.survey.Poll)
     */
    public PollResult validatePollIP(final String ip, final Poll poll) {
    	return getPollDao().validateVoteIP(ip, poll);
    }
}
