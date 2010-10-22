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
import java.util.Iterator;
import java.util.List;

import org.encuestame.business.security.util.HTMLInputFilter;
import org.encuestame.business.service.imp.ISurveyService;
import org.encuestame.business.service.imp.ITweetPollService;
import org.encuestame.business.service.util.MD5Utils;
import org.encuestame.core.exception.EnMeDomainNotFoundException;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.utils.security.UnitTwitterAccountBean;
import org.encuestame.utils.web.UnitAnswersBean;
import org.encuestame.utils.web.UnitHashTag;
import org.encuestame.utils.web.UnitQuestionBean;
import org.encuestame.utils.web.UnitTweetPoll;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;

/**
 * Create Tweet Poll.
 * @author Picado, Juan juan@encuestame.org
 * @since Feb 13, 2010 11:36:48 PM
 * @version $Id$
 */
public class CreateTweetPollBean extends AbstractMasterTweetPollBean implements Serializable{

     /** Serial. */
    private static final long serialVersionUID = -191208309931131495L;

    /** {@link UnitTweetPoll}. **/
    private UnitTweetPoll unitTweetPoll = new UnitTweetPoll();

    /** {@link UnitAnswersBean}. **/
    private UnitAnswersBean answersBean = new UnitAnswersBean();

    /** Resume Tweet. **/
    private String resumeTweet = new String();

    /** Question Name. **/
    private String questionName;

    /** HashTag Name. **/
    private String hashTag;

    /** Twitter Account Selected. **/
    private String twitterAccountSelected;

    /** If Length is exceeded. */
    private Boolean excededTweet = false;

    /** If is Valid Tweet. **/
    private Boolean validTweet = false;

    /** If valid, user can publish tweetpoll. **/
    private Boolean validPublish = false;

    /** Class Length. **/
    private String classLength = "normalState";

    /** Selected Answer. **/
    private UnitAnswersBean selectedAnswer = new UnitAnswersBean();

    /** If hash tag items is equals to zero. **/
    private Boolean showHashTagNew = true;

    /**
     * List of Twitter Accounts
     */
    private List<TwitterAccountsPublicationBean>  accounts = null;

    /**
     * Show or Hide Add Answers.
     */
    private Boolean showHideAddAnswers = true;

    /** Count Tweet. **/
    private Integer countTweet = MAXIMUM_TWEET;


    /** Default Limit Votes. **/
    private Boolean limitVotes = false;

    /** Questions Suggested. **/
    private List<UnitQuestionBean> questionsSuggested = new ArrayList<UnitQuestionBean>();

    /** HashTag Suggested. **/
    private List<UnitHashTag> listHashTags = new ArrayList<UnitHashTag>();

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
            log.info("Question Save Question");
            getUnitTweetPoll().getQuestionBean().setUserId(getUsernameByName().getSecUser().getUid());
            log.info("Question Save Saved User Id");
            getUnitTweetPoll().getQuestionBean().setQuestionName(getQuestionName());
            log.info("Question Save Saved Question Name");
            addInfoMessage("Question Saved.", "");
            this.updateQuestionCountTweet();
        }
        catch (Exception e) {
            addErrorMessage("Error save question", "");
            log.error(e);
        }
    }

    /**
     * Create Short Simulate URL.
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
            addErrorMessage("Error to create Short Simulate Url", "");
        }
    }

    /**
     * Remove Hash Tag.
     */
    public final void removeHashTag(){
        try{
            log.debug("removeHashTag");
            getUnitTweetPoll().getHashTags().indexOf(setTempHashTag(getHashTag()));
            log.debug("Before remove size "+getUnitTweetPoll().getHashTags().size());
            final Iterator<UnitHashTag> iterator = getUnitTweetPoll().getHashTags().iterator();
            while (iterator.hasNext()) {
                UnitHashTag hash = (UnitHashTag) iterator.next();
                  if (hash.equals(setTempHashTag(getHashTag()))){
                      log.debug("Removing .. "+hash.toString());
                      iterator.remove();
                }
            }
            this.updateQuestionCountTweet();
            if(this.excededTweet){
                setShowHideAddAnswers(Boolean.FALSE);
            }
            else{
                setShowHideAddAnswers(Boolean.TRUE);
            }
            log.debug("After remove size "+getUnitTweetPoll().getHashTags().size());
        }
        catch (Exception e) {
            log.error(e);
            addErrorMessage("Error removing hash tags", "");
        }
    }

    /**
     * Create Hash Tag.
     */
    public final void createHashTag(){
        try{
            log.debug("getUnitTweetPoll().getHashTags() "+ getUnitTweetPoll().getHashTags());
            if(getUnitTweetPoll().getHashTags() != null){
                log.debug("createHashTag size "+getUnitTweetPoll().getHashTags().size());
                log.debug("Adding HashTag "+getHashTag());
                this.getUnitTweetPoll().getHashTags().add(setTempHashTag(getHashTag()));
                //reset hash tag name.
                setHashTag(new String());
                addInfoMessage("Hash Tag Added", "");
                this.updateQuestionCountTweet();
                if(this.excededTweet){
                    this.showHideAddAnswers = false;
                }
                else{
                    this.showHideAddAnswers = true;
                }
            }
        }
        catch (Exception e) {
             addErrorMessage("Error adding Hash Tag", "");
             log.error(e);
        }
    }

    /**
     * Get Hash Tag.
     * @param suggest
     * @return
     */
    public ArrayList<UnitHashTag> getHashTags(final Object suggest) {
        final String pref = (String) suggest;
        final ArrayList<UnitHashTag> result = new ArrayList<UnitHashTag>();
        try{
            this.listHashTags = getTweetPollService().listSuggestHashTags(pref, CreateTweetPollBean.MAX_HASHTAGS);
            for (UnitHashTag elem : this.listHashTags) {
                if ((elem.getHashTagName().toLowerCase().indexOf(pref.toLowerCase()) == 0)) {
                    result.add(elem);
                }
            }
        }
        catch (Exception e) {
            addErrorMessage("Error retrieving Hash Tahgs", "Error retrieving Hash Tahgs");
        }
        return result;
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
                if(getUnitTweetPoll().getQuestionBean() != null){
                    this.createShortAnswerUrl(getAnswersBean());
                    //Saving Answer to Question Answers List.
                    this.getUnitTweetPoll().getQuestionBean().getListAnswers().add(getAnswersBean());
                    setAnswersBean(new UnitAnswersBean());
                    addInfoMessage("Answer Added", "");
                    this.updateQuestionCountTweet();
                    if(this.excededTweet){
                        this.showHideAddAnswers = false;
                    }
                    else{
                        this.showHideAddAnswers = true;
                    }
                }
                else{
                    log.warn("You need create question first.");
                    addWarningMessage("You need create question first.", "");
                }
        }
        catch (Exception e) {
            addErrorMessage("error to add answer", "");
            log.error(e);
        }
    }

    /**
     * Suggest.
     * @param suggest keyword.
     * @return
     */
    public void suggest(){
        final String pref = this.questionName;
        if(pref.length() > 1){
            try {
                this.questionsSuggested = getTweetPollService().listSuggestQuestion(pref, getUserPrincipalUsername());
            } catch (EnMeDomainNotFoundException e) {
                log.error(e);
                addErrorMessage(e.getMessage(),"");
            }
            //setValidTweet(Boolean.FALSE);
            this.saveQuestion();
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
    public final String publishTweetPoll(final MessageContext messageContext){
        return this.createTweetPoll(Boolean.TRUE, messageContext);
    }

    /**
     * Save Later To Publish TweetPoll.
     */
    public final String saveLaterTweetPoll(final MessageContext messageContext){
        return this.createTweetPoll(Boolean.FALSE, messageContext);
    }

    /**
     * Create Tweet Poll.
     */
    public final String createTweetPoll(final Boolean publish, final MessageContext messageContext){
        final ITweetPollService tweetPollService = getTweetPollService();
        try {
            //Getting User Logged Id.
            final Long userId = getUsernameByName().getSecUser().getUid();
            //set user id to question bean.
            this.getUnitTweetPoll().getQuestionBean().setUserId(userId);
            // save question
            getSurveyService().createQuestion(getUnitTweetPoll().getQuestionBean());
            // save create tweet poll
            if (getUnitTweetPoll().getQuestionBean().getId() != null) {
                getUnitTweetPoll().setUserId(userId);
                getUnitTweetPoll().setCloseNotification(Boolean.FALSE);
                getUnitTweetPoll().setResultNotification(Boolean.FALSE);
                getUnitTweetPoll().setPublishPoll(publish);
                tweetPollService.createTweetPoll(getUnitTweetPoll());
                //If publish is true and Scheduled is false, because if is scheduled we want
                //send later.
                if (getUnitTweetPoll().getPublishPoll() && !getUnitTweetPoll().getSchedule()) {
                    final String tweetText = tweetPollService
                            .generateTweetPollText(getUnitTweetPoll(),
                                    getDomain());
                   final List<UnitTwitterAccountBean> accountBeans = new ArrayList<UnitTwitterAccountBean>();
                   for (TwitterAccountsPublicationBean twitterAccountsPublicationBean : getAccounts()) {
                           if(twitterAccountsPublicationBean.getActive()){
                               accountBeans.add(twitterAccountsPublicationBean.getAccountBean());
                           }
                   }
                 log.debug("Accounts "+accountBeans.size());
                 tweetPollService.publicMultiplesTweetAccounts(accountBeans, getUnitTweetPoll().getId(), tweetText);
                }
                addInfoMessage("tweet poll message", "");
                log.debug("tweet poll created");
            }
            else{
                log.error("Question ID don't exitst");
            }
            return "yes";
        } catch (EnMeExpcetion e) {
              messageContext.addMessage(new MessageBuilder().error().source("publish").
                      defaultText("Opps. "+e.getMessage()).build());
            log.error(e);
            return "no";
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
     * Load Twitter Accounts.
     */
    public void loadTwitterAccounts(){
        try{
            if(this.accounts == null){
                setAccounts(new ArrayList<TwitterAccountsPublicationBean>());
                log.debug("Loading Twitter Accounts");
                final List<UnitTwitterAccountBean> accounts = getSecurityService().getUserLoggedVerifiedTwitterAccount(getUserPrincipalUsername());
                Integer i = 0;
                for (UnitTwitterAccountBean unitTwitterAccountBean : accounts) {
                    i = i+1;
                    final TwitterAccountsPublicationBean accountsPublicationBean = new  TwitterAccountsPublicationBean();
                    accountsPublicationBean.setAccountBean(unitTwitterAccountBean);
                    accountsPublicationBean.setActive(Boolean.FALSE);
                    accountsPublicationBean.setId(i);
                    this.accounts.add(accountsPublicationBean);
                }
                setValidPublish(Boolean.FALSE);
                this.validatePublish();
                log.debug("Twitter Accounts Loaded");
            }
            else{
                log.warn("Twitter Accounts are loaded.");
            }
        }
        catch (Exception e) {
            log.error("Error Loading Twitter Accounts "+e.getMessage());
        }
    }

    /**
     * Validate Publish.
     */
    private void validatePublish(){
        log.debug("validatePublish");
        Boolean somethingTrue = false;
        for (TwitterAccountsPublicationBean accounts : getAccounts()) {
            log.debug("validatePublish accounts.getActive() "+accounts.getActive());
            if(accounts.getActive()){
                somethingTrue = true;
                setValidPublish(Boolean.TRUE);
            }
        }
        //if any accounts is activated, we change flag to false.
        if(!somethingTrue){
            setValidPublish(Boolean.FALSE);
        }
        log.debug("validatePublish Publish "+this.validPublish);
    }

    /**
     * Reset Question Name.
     */
    public void resetQuestionName(){
        getUnitTweetPoll().getQuestionBean().setQuestionName("");
        setValidTweet(Boolean.FALSE);
    }

    /**
     * Select Twitter Account Selected.
     */
    public void selectTwitterAccount(){
            for (TwitterAccountsPublicationBean account : this.accounts) {
                if(account.getAccountBean().getAccount().equals(getTwitterAccountSelected())){
                    account.setActive(!account.getActive());
                }
            }
            this.validatePublish();
    }

    /**
     * Update Question Count Tweet.
     */
    public void updateQuestionCountTweet(){
        if(this.questionName != null){
            this.resumeTweet = this.questionName;
        }
        if(this.getUnitTweetPoll().getQuestionBean().getListAnswers().size() >= 1){
            for (UnitAnswersBean answer : this.getUnitTweetPoll().getQuestionBean().getListAnswers()) {
                final StringBuffer answerString = new StringBuffer("");
                answerString.append(" ");
                answerString.append(answer.getAnswers());
                answerString.append(" ");
                answerString.append(answer.getUrl());
                this.resumeTweet = this.resumeTweet + " "+answerString.toString();
            }
            //Add Hash Tag String.
            for (UnitHashTag tag : this.getUnitTweetPoll().getHashTags()) {
                final StringBuffer tagString = new StringBuffer("");
                tagString.append("#");
                tagString.append(tag.getHashTagName());
                this.resumeTweet = this.resumeTweet + " "+tagString.toString();
            }
        }
        if(this.resumeTweet.length() > MAXIMUM_TWEET){
            log.debug("Tweet length is exceded");
            this.excededTweet = true;
            setValidTweet(Boolean.FALSE);
        }
        else{
            this.excededTweet = false;
            if((this.getUnitTweetPoll().getQuestionBean().getQuestionName() != null
                   && this.getUnitTweetPoll().getQuestionBean().getQuestionName().length() > MINIMUM_QUESTION_NAME)){
                setValidTweet(Boolean.TRUE);
            }
            else{
                setValidTweet(Boolean.FALSE);
            }
        }
        this.updateCount();
        this.updateColorState();
    }

    /**
     * Color State.
     */
    public void updateColorState(){
        final Integer messageLenght = this.resumeTweet.length();
        if(messageLenght > MAXIMUM_TWEET){
            setClassLength("errorState");
        }
        else if(messageLenght < MINIMUM_QUESTION_NAME){
            setClassLength("questionErrorState");
        }
        else if(messageLenght > WARNING_TWEET && messageLenght < MAXIMUM_TWEET){
            setClassLength("warningState");
        }
        else if(messageLenght > MINIMUM_QUESTION_NAME && messageLenght < WARNING_TWEET){
            setClassLength("normalState");
        }
        else{
            setClassLength("normalState");
        }
    }

    /**
     * Update Count.
     */
    public void updateCount(){
        final Integer lenght = getResumeTweet().length();
        setCountTweet(CreateTweetPollBean.MAXIMUM_TWEET - lenght);
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

    /**
     * @return the questionName
     */
    public String getQuestionName() {
        return questionName;
    }

    /**
     * @param questionName the questionName to set
     */
    public void setQuestionName(String questionName) {
        //Add xss filter.
        this.questionName = new HTMLInputFilter().filter(questionName);
        if(this.questionName != null){
            this.updateQuestionCountTweet();
        }
    }

    /**
     * @return the excededTweet
     */
    public Boolean getExcededTweet() {
        return excededTweet;
    }

    /**
     * @param excededTweet the excededTweet to set
     */
    public void setExcededTweet(Boolean excededTweet) {
        this.excededTweet = excededTweet;
    }

    /**
     * @return the validTweet
     */
    public Boolean getValidTweet() {
        return validTweet;
    }

    /**
     * @param validTweet the validTweet to set
     */
    public void setValidTweet(Boolean validTweet) {
        this.validTweet = validTweet;
    }

    /**
     * @return the classLength
     */
    public String getClassLength() {
        return classLength;
    }

    /**
     * @param classLength the classLength to set
     */
    public void setClassLength(String classLength) {
        this.classLength = classLength;
    }

    /**
     * @return the selectedAnswer
     */
    public UnitAnswersBean getSelectedAnswer() {
        log.debug("GET selectedAnswer"+selectedAnswer.getAnswers());
        return selectedAnswer;
    }

    /**
     * @param selectedAnswer the selectedAnswer to set
     */
    public void setSelectedAnswer(UnitAnswersBean selectedAnswer) {
        log.debug("SET selectedAnswer"+selectedAnswer.getAnswers());
        this.selectedAnswer = selectedAnswer;
    }

    /**
     * Remove Answer.
     */
    public void removeAnswer(){
        log.debug("removeAnswer "+getSelectedAnswer());
        if(getSelectedAnswer() != null){
            log.debug("list answers size "+getUnitTweetPoll().getQuestionBean().getListAnswers().size());
            final Iterator<UnitAnswersBean> iterator = getUnitTweetPoll().getQuestionBean().getListAnswers().iterator();
                while (iterator.hasNext()) {
                    UnitAnswersBean unitAnswersBean = (UnitAnswersBean) iterator.next();
                    log.debug("Answer Response "+unitAnswersBean.getAnswers());
                    log.debug("Answer Selected "+getSelectedAnswer().getAnswers());
                    if(unitAnswersBean.equals(getSelectedAnswer())){
                        log.debug("ANSWER FOUND, REMOVE");
                        iterator.remove();
                        this.updateQuestionCountTweet();
                        if(this.excededTweet){
                            setShowHideAddAnswers(Boolean.FALSE);
                        }
                        else{
                            setShowHideAddAnswers(Boolean.TRUE);
                        }
                    }
                }
                log.debug("Finishd Remove");
            }
        else{
            log.warn("Impossible remove, selected answer is null");
        }
    }

    /**
     * @return the validPublish
     */
    public final Boolean getValidPublish() {
        return validPublish;
    }

    /**
     * @param validPublish the validPublish to set
     */
    public final void setValidPublish(Boolean validPublish) {
        this.validPublish = validPublish;
    }

    /**
     * @return the limitVotes
     */
    public final Boolean getLimitVotes() {
        return limitVotes;
    }

    /**
     * @param limitVotes the limitVotes to set
     */
    public final void setLimitVotes(final Boolean limitVotes) {
        this.limitVotes = limitVotes;
    }

    /**
     * @return the hashTag
     */
    public final String getHashTag() {
        return hashTag;
    }

    /**
     * @param hashTag the hashTag to set
     */
    public final void setHashTag(final String hashTag) {
        this.hashTag = hashTag;
    }

    /**
     * @return the listHashTags
     */
    public List<UnitHashTag> getListHashTags() {
        return listHashTags;
    }

    /**
     * @param listHashTags the listHashTags to set
     */
    public void setListHashTags(List<UnitHashTag> listHashTags) {
        this.listHashTags = listHashTags;
    }

    /**
     * @return the showHashTagNew
     */
    public Boolean getShowHashTagNew() {
        if(getUnitTweetPoll().getHashTags().size() <= 0){
            this.showHashTagNew = true;
        }
        else{
            this.showHashTagNew = false;
        }
        return showHashTagNew;
    }
}
