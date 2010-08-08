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
package org.encuestame.web.beans.survey.tweetpoll;

import java.io.Serializable;

import org.encuestame.utils.web.UnitAnswersBean;
import org.encuestame.utils.web.UnitHashTag;
import org.encuestame.utils.web.UnitTweetPoll;

/**
 * Description Class.
 * @author Picado, Juan juanATencuestame.org
 * @since Aug 7, 2010 5:26:20 PM
 * @version Id:
 */
public class EditTweetPollBean extends AbstractMasterTweetPollBean implements Serializable{

    /** Serial. */
    private static final long serialVersionUID = 171232309932133556L;

    /** Question Name. **/
    private String newQuestioName;

    /** {@link UnitTweetPoll}. **/
    private UnitTweetPoll unitTweetPoll = new UnitTweetPoll();

    /** Resume Tweet. **/
    private String resumeTweet;

    /** Question Name. **/
    private String questionName;

    /** Class Length. **/
    private String classLength = "normalState";

    /** Exceded Tweet. **/
    private Boolean excededTweet = false;

    /** If is Valid Tweet. **/
    private Boolean validTweet = false;

    /** Count Tweet. **/
    private Integer countTweet = MAXIMUM_TWEET;

    /**
     * Evaluate TweetPoll.
     *
     */
    public void evaluateTweetPoll(){
        log.debug("evaluateTweetPoll");
        if(unitTweetPoll != null){
            log.debug("unitTweetPoll "+unitTweetPoll.getId());
            log.debug("unitTweetPoll question Id"+unitTweetPoll.getQuestionBean().getId());
            log.debug("unitTweetPoll question Name "+unitTweetPoll.getQuestionBean().getQuestionName());
            this.updateQuestionCountTweet();
        }
        else{
            log.error("TweetPoll is null");
        }
    }

    /**
     * Update Question Name.
     */
    public void updateQuestionName(){
        log.debug("updating question name");
        try{
            getServicemanager().getApplicationServices().getTweetPollService().updateQuestionName(
                                getUnitTweetPoll().getQuestionBean().getId(),
                                   getUnitTweetPoll().getQuestionBean().getQuestionName());
            addInfoMessage("Question Updated","Question Updated");
            this.updateQuestionCountTweet();
        }
        catch (Exception e) {
            log.debug("Error "+e.getLocalizedMessage());
            addErrorMessage("Try to update later", "Try to update later");
        }
    }

    /**
     * @return the newQuestioName
     */
    public String getNewQuestioName() {
        return newQuestioName;
    }

    /**
     * @param newQuestioName the newQuestioName to set
     */
    public void setNewQuestioName(String newQuestioName) {
        this.newQuestioName = newQuestioName;
    }

    /**
     * @return the unitTweetPoll
     */
    public UnitTweetPoll getUnitTweetPoll() {
        return unitTweetPoll;
    }

    /**
     * @param unitTweetPoll the unitTweetPoll to set
     */
    public void setUnitTweetPoll(UnitTweetPoll unitTweetPoll) {
        this.unitTweetPoll = unitTweetPoll;
    }

    @Override
    public void updateQuestionCountTweet() {
         log.debug("updateQuestionCountTweet");
         setResumeTweet(getUnitTweetPoll().getQuestionBean().getQuestionName());
         if(this.getUnitTweetPoll().getQuestionBean().getListAnswers().size() >= 1){
             for (UnitAnswersBean answer : this.getUnitTweetPoll().getQuestionBean().getListAnswers()) {
                 final StringBuffer answerString = new StringBuffer("");
                 answerString.append(" ");
                 answerString.append(answer.getAnswers());
                 answerString.append(" ");
                 answerString.append(answer.getUrl());
                 setResumeTweet(getResumeTweet() + " "+answerString.toString());
             }
             //Add Hash Tag String.
             for (UnitHashTag tag : this.getUnitTweetPoll().getHashTags()) {
                 final StringBuffer tagString = new StringBuffer("");
                 tagString.append("#");
                 tagString.append(tag.getHashTagName());
                 setResumeTweet(getResumeTweet() + " "+tagString.toString());
             }
         }
         if(this.resumeTweet.length() > MAXIMUM_TWEET){
             log.debug("Tweet length is exceded");
             setExcededTweet(Boolean.TRUE);
             setValidTweet(Boolean.FALSE);
         }
         else{
             setExcededTweet(Boolean.FALSE);
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

    @Override
    public void updateColorState() {
        log.debug("updateColorState");
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

    @Override
    public void updateCount() {
        log.debug("updateCount");
        final Integer lenght = getResumeTweet().length();
        setCountTweet(CreateTweetPollBean.MAXIMUM_TWEET - lenght);

    }

    /**
     * @return the resumeTweet
     */
    public String getResumeTweet() {
        return resumeTweet;
    }

    /**
     * @param resumeTweet the resumeTweet to set
     */
    public void setResumeTweet(String resumeTweet) {
        this.resumeTweet = resumeTweet;
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
        this.questionName = questionName;
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
     * @return the countTweet
     */
    public Integer getCountTweet() {
        return countTweet;
    }

    /**
     * @param countTweet the countTweet to set
     */
    public void setCountTweet(Integer countTweet) {
        this.countTweet = countTweet;
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
}
