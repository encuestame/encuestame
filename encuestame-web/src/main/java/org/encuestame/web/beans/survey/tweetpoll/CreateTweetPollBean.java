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

package org.encuestame.web.beans.survey.tweetpoll;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.service.ISurveyService;
import org.encuestame.core.service.ITweetPollService;
import org.encuestame.core.service.util.MD5Utils;
import org.encuestame.utils.security.UnitTwitterAccountBean;
import org.encuestame.utils.web.UnitAnswersBean;
import org.encuestame.utils.web.UnitQuestionBean;
import org.encuestame.utils.web.UnitTweetPoll;
import org.encuestame.web.beans.MasterBean;

import twitter4j.Status;

/**
 * Create Tweet Poll.
 * @author Picado, Juan juan@encuestame.org
 * @since Feb 13, 2010 11:36:48 PM
 * @version $Id$
 */
public class CreateTweetPollBean extends MasterBean implements Serializable{

    /**
     * Serial.
     */
    private static final long serialVersionUID = -191208309931131495L;

    /** {@link UnitTweetPoll}. **/
    private UnitTweetPoll unitTweetPoll = new UnitTweetPoll();

    /** {@link UnitAnswersBean}. **/
    private UnitAnswersBean answersBean = new UnitAnswersBean();

    /** {@link UnitQuestionBean}. **/
    private UnitQuestionBean questionBean = new UnitQuestionBean();

    /** Resume Tweet. **/
    private String resumeTweet = new String();

    /** Twitter Account Selected. **/
    private String twitterAccountSelected;

    /**
     * List of Twitter Accounts
     */
    private List<TwitterAccountsPublicationBean>  accounts = new ArrayList<TwitterAccountsPublicationBean>();

    /**
     * Show or Hide Add Answers.
     */
    private Boolean showHideAddAnswers = true;

    /** Count Tweet. **/
    private Integer countTweet = CreateTweetPollBean.MAXIMUM_TWEET;

    private static final Integer MAXIMUM_TWEET = 140;

    /**
     * Questions Suggested.
     */
    private List<UnitQuestionBean> questionsSuggested = new ArrayList<UnitQuestionBean>();

    /**
     * Constructor.
     */
    public CreateTweetPollBean() {
    }

    /**
     * Save Question.
     */
    public final void saveQuestion(){
        try{
            log.info("Question Name "+questionBean.getQuestionName());
            this.questionBean.setUserId(getUsernameByName().getSecUser().getUid());
            getUnitTweetPoll().setQuestionBean(questionBean);
            addInfoMessage("Question Saved.", "");
            setResumeTweet(this.questionBean.getQuestionName());
            //TODO: refresh url
            //show up next wizard step
        }
        catch (Exception e) {
            addErrorMessage("Error save question", "");
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Create Short Sumilate Url.
     * @param answer answer
     */
    public final void createShortAnswerUrl(final UnitAnswersBean answer){
        try{
            final ISurveyService survey = getServicemanager().getApplicationServices().getSurveyService();
            answer.setAnswerHash(MD5Utils.md5(String.valueOf(java.util.Calendar.getInstance().getTimeInMillis())));
            log.info(getDomain());
            //Get Tiny Url.
            final String url = survey.getTwitterService().getTinyUrl(this.buildUrl(answer));
            log.info("tiny url "+url);
            answer.setUrl(url);
        }
        catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            addErrorMessage("Error to create Short Simulate Url", "");
        }
    }

    /**
     * Build Url.
     * @param answer answer
     * @return vote url
     */
    private String buildUrl(final UnitAnswersBean  answer){
        final StringBuffer stringBuffer = new StringBuffer(getDomain());
        stringBuffer.append(getTweetPollService().getTweetPath());
        stringBuffer.append(answer.getAnswerHash());
        return stringBuffer.toString();
    }

    /**
     * Add answer to question.
     **/
    public final void addAnswer(){
        try{
                if(getUnitTweetPoll().getQuestionBean() !=null){
                    this.createShortAnswerUrl(getAnswersBean());
                    //Saving Answer to Question Answers List.
                    this.questionBean.getListAnswers().add(getAnswersBean());
                    setAnswersBean(new UnitAnswersBean());
                    addInfoMessage("Answer Added", "");
                    if(this.questionBean.getListAnswers().size() == 2){
                        this.showHideAddAnswers = !this.showHideAddAnswers;
                    }
                    this.updateQuestionCountTweet();
                }
                else{
                    log.warn("You need create question first.");
                    addWarningMessage("You need create question first.", "");
                }
        }
        catch (Exception e) {
            addErrorMessage("error to add answer", "");
            log.error(e);
            e.printStackTrace();
        }
    }


    /**
     * Create Preview.
     */
    public void createPreview(){
        log.info("Creating Preview");
    }

    /**
     * Suggest.
     * @param suggest keyword.
     * @return
     */
    public void suggest(){
        final String pref = this.questionBean.getQuestionName();
        if(pref.length() > 1){
            log.debug("Suggestion Search with "+pref);
            this.questionsSuggested = getTweetPollService().listSuggestQuestion(pref, getSecurityContextUsername());
            log.info("suggested "+ this.questionsSuggested.size());
        }
    }

    /**
     * Delete All Answers.
     */
    public final void deleteAllAnswers(){
        try{
            getUnitTweetPoll().getQuestionBean().setListAnswers(new ArrayList<UnitAnswersBean>());
            addInfoMessage("Answer Cleared.", "");
        }catch (Exception e) {
            addErrorMessage("error to add answer", "");
            log.error(e);
        }
    }

    /**
     * Publish TweetPoll.
     */
    public final void publishTweetPoll(){
        this.createTweetPoll(Boolean.TRUE);
    }

    /**
     * Save Later To Publish TweetPoll.
     */
    public final void saveLaterTweetPoll(){
        this.createTweetPoll(Boolean.FALSE);
    }

    /**
     * Create Tweet Poll.
     */
    public final void createTweetPoll(final Boolean publish){
        final ITweetPollService tweetPollService = getTweetPollService();
        try {
            //Getting User Logged Id.
            final Long userId = getUsernameByName().getSecUser().getUid();
            //set user id to question bean.
            this.questionBean.setUserId(userId);
            getUnitTweetPoll().setQuestionBean(this.questionBean);
            // save question
            getSurveyService().createQuestion(getUnitTweetPoll().getQuestionBean());
            // save create tweet poll
            if (getUnitTweetPoll().getQuestionBean().getId() != null) {
                getUnitTweetPoll().setUserId(userId);
                //TODO: we need implement scheduled tweetPoll.
                getUnitTweetPoll().setScheduleDate(new Date());
                getUnitTweetPoll().setSchedule(false);
                getUnitTweetPoll().setCloseNotification(false);
                getUnitTweetPoll().setAllowLiveResults(false);
                getUnitTweetPoll().setSchedule(false); //TODO: false by default.
                getUnitTweetPoll().setPublishPoll(publish);
                getUnitTweetPoll().setResultNotification(false);
                getUnitTweetPoll().setAllowLiveResults(true);
                getUnitTweetPoll().setCloseNotification(false);
                tweetPollService.createTweetPoll(getUnitTweetPoll());
                if (getUnitTweetPoll().getPublishPoll()) {
                    final String tweetText = tweetPollService
                            .generateTweetPollText(getUnitTweetPoll(),
                                    getDomain());
                    final SecUsers sessionUser = getUsernameByName()
                            .getSecUser();
                    final Status status = tweetPollService.publicTweetPoll(
                            tweetText, sessionUser.getTwitterAccount(), sessionUser
                                    .getTwitterPassword());
                    final Long tweetId = status.getId();
                    if (tweetId != null) {
                        getUnitTweetPoll().setTweetId(tweetId);
                        getUnitTweetPoll().setPublicationDateTweet(
                                status.getCreatedAt());
                        tweetPollService.saveTweetId(getUnitTweetPoll());
                        log.info("tweeted :" + tweetId);
                    }
                }
                addInfoMessage("tweet poll message", "");
                log.debug("tweet poll created");
            }
            else{
                log.error("Question ID don't exitst");
            }
        }
        catch (EnMeExpcetion e) {
            addErrorMessage("error "+e, "");
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * @return the unitTweetPoll
     */
    public final UnitTweetPoll getUnitTweetPoll() {
        return unitTweetPoll;
    }

    /**
     * @param unitTweetPoll the unitTweetPoll to set
     */
    public final void setUnitTweetPoll(final UnitTweetPoll unitTweetPoll) {
        this.unitTweetPoll = unitTweetPoll;
    }

    /**
     * @return the answersBean
     */
    public final UnitAnswersBean getAnswersBean() {
        return answersBean;
    }

    /**
     * @param answersBean the answersBean to set
     */
    public final void setAnswersBean(final UnitAnswersBean answersBean) {
        this.answersBean = answersBean;
    }

    /**
     * @return the questionBean
     */
    public final UnitQuestionBean getQuestionBean() {
        if(questionBean.getQuestionName() != null){
           this.updateQuestionCountTweet();
        }
        return questionBean;
    }

    /**
     * Load Twitter Accounts.
     */
    public void loadTwitterAccounts(){
        try{
            log.debug("Loading Twitter Accounts");
            setAccounts(new ArrayList<TwitterAccountsPublicationBean>());
            final List<UnitTwitterAccountBean> accounts = getSecurityService().getUserLoggedVerifiedTwitterAccount(getUserPrincipalUsername());
            for (UnitTwitterAccountBean unitTwitterAccountBean : accounts) {
                final TwitterAccountsPublicationBean accountsPublicationBean = new  TwitterAccountsPublicationBean();
                accountsPublicationBean.setAccountBean(unitTwitterAccountBean);
                accountsPublicationBean.setActive(Boolean.FALSE);
                this.accounts.add(accountsPublicationBean);
            }
            log.debug("Twitter Accounts Loaded");
        }
        catch (Exception e) {
            log.error("Error Loading Twitter Accounts "+e.getMessage());
        }
    }

    /**
     * Select Twitter Account Selected.
     */
    public void selectTwitterAccount(){
            for (TwitterAccountsPublicationBean account : this.accounts) {
                log.debug("Twitter Account Selected "+getTwitterAccountSelected());
                if(account.getAccountBean().getAccount().equals(getTwitterAccountSelected())){
                    account.setActive(!account.getActive());
                }
                log.debug("Twitter Account Selected "+account.getAccountBean().getAccount() +" Status "+account.getActive());
            }
    }

    /**
     * Update Question Count Tweet.
     */
    public void updateQuestionCountTweet(){
        if(questionBean.getQuestionName() != null){
            this.resumeTweet = questionBean.getQuestionName();
        }
        if(this.questionBean.getListAnswers().size() >= 1){
            for (UnitAnswersBean answer : this.questionBean.getListAnswers()) {
                final StringBuffer answerString = new StringBuffer("");
                answerString.append(" ");
                answerString.append(answer.getAnswers());
                answerString.append(" ");
                answerString.append(answer.getUrl());
                log.info("answerString "+answerString);
                this.resumeTweet = this.resumeTweet + " "+answerString.toString();
                this.createPreview();
            }
        }
        this.updateCount();
    }

    /**
     * Update Count.
     */
    private void updateCount(){
        final Integer lenght = this.resumeTweet.length();
        this.countTweet = CreateTweetPollBean.MAXIMUM_TWEET - lenght;
    }

    /**
     * @param questionBean the questionBean to set
     */
    public final void setQuestionBean(final UnitQuestionBean questionBean) {
        this.questionBean = questionBean;
    }

    /**
     * @return the resumeTweet
     */
    public final String getResumeTweet() {
        return resumeTweet;
    }

    /**
     * @param resumeTweet the resumeTweet to set
     */
    public final void setResumeTweet(final String resumeTweet) {
        this.resumeTweet = resumeTweet;
    }

    /**
     * @return the countTweet
     */
    public final Integer getCountTweet() {
        return countTweet;
    }

    /**
     * @param countTweet the countTweet to set
     */
    public final void setCountTweet(final Integer countTweet) {
        this.countTweet = countTweet;
    }

    /**
     * @return the questionsSuggested
     */
    public List<UnitQuestionBean> getQuestionsSuggested() {
        return questionsSuggested;
    }

    /**
     * @param questionsSuggested the questionsSuggested to set
     */
    public void setQuestionsSuggested(List<UnitQuestionBean> questionsSuggested) {
        this.questionsSuggested = questionsSuggested;
    }

    /**
     * @return the showHideAddAnswers
     */
    public Boolean getShowHideAddAnswers() {
        return showHideAddAnswers;
    }

    /**
     * @param showHideAddAnswers the showHideAddAnswers to set
     */
    public void setShowHideAddAnswers(final Boolean showHideAddAnswers) {
        this.showHideAddAnswers = showHideAddAnswers;
    }

    /**
     * @return the accounts
     */
    public List<TwitterAccountsPublicationBean> getAccounts() {
        return accounts;
    }

    /**
     * @param accounts the accounts to set
     */
    public void setAccounts(final List<TwitterAccountsPublicationBean> accounts) {
        this.accounts = accounts;
    }

    /**
     * @return the twitterAccountSelected
     */
    public String getTwitterAccountSelected() {
        return twitterAccountSelected;
    }

    /**
     * @param twitterAccountSelected the twitterAccountSelected to set
     */
    public void setTwitterAccountSelected(String twitterAccountSelected) {
        this.twitterAccountSelected = twitterAccountSelected;
    }

}
