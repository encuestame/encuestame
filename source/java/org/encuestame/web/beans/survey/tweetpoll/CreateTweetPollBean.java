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

import java.util.ArrayList;
import java.util.Date;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.service.ISurveyService;
import org.encuestame.web.beans.MasterBean;
import org.encuestame.web.beans.survey.UnitAnswersBean;
import org.encuestame.web.beans.survey.UnitQuestionBean;

import twitter4j.Status;

/**
 * Create Tweet Poll.
 * @author Picado, Juan juan@encuestame.org
 * @since Feb 13, 2010 11:36:48 PM
 * @version $Id$
 */
public class CreateTweetPollBean extends MasterBean {

    /** {@link UnitTweetPoll}. **/
    private UnitTweetPoll unitTweetPoll = new UnitTweetPoll();

    /** {@link UnitAnswersBean}. **/
    private UnitAnswersBean answersBean = new UnitAnswersBean();

    /** {@link UnitQuestionBean}. **/
    private UnitQuestionBean questionBean = new UnitQuestionBean();

    /** Resume Tweet. **/
    private String resumeTweet;

    /** Count Tweet. **/
    private Integer countTweet;

    /**
     * Constructor.
     */
    public CreateTweetPollBean() {
    }

    /**
     * Save Question.
     */
    public void saveQuestion(){
        try{
            log.info("Question Name "+questionBean.getQuestionName());
            this.questionBean.setUserId(getUsernameByName().getSecUser().getUid());
            getUnitTweetPoll().setQuestionBean(questionBean);
            addInfoMessage("Question Saved.", "");
            setResumeTweet(this.questionBean.getQuestionName());
            //TODO: refresh url
        }catch (Exception e) {
            addErrorMessage("Error save question", "");
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Create Short Sumilate Url.
     * @param answer answer
     */
    public void createShortSimulateUrl(final UnitAnswersBean answer){
        try{
            final ISurveyService survey = getServicemanager().getApplicationServices().getSecurityService().getSurveyService();
            log.info("survey service "+survey);
            final String url = survey.getTwitterService().getTinyUrl("http://www.google.es");
            log.info("tiny url "+url);
            StringBuffer answerString = new StringBuffer(getResumeTweet());
            answerString.append(" ");
            answerString.append(answer.getAnswers());
            answerString.append(" ");
            answerString.append(url);
            log.info("answerString "+answerString);
            setResumeTweet(answerString.toString());
        }
        catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            addErrorMessage("Error to create Short Simulate Url", "");
        }
    }

    /**
     * Add answer to question.
     **/
    public void addAnswer(){
        try{
            if(getUnitTweetPoll().getQuestionBean() !=null){
                getUnitTweetPoll().getQuestionBean().getListAnswers().add(getAnswersBean());
                createShortSimulateUrl(getAnswersBean());
                setAnswersBean(new UnitAnswersBean());
                addInfoMessage("Answer Added", "");
            }
            else{
                addWarningMessage("You need create question first.", "");
            }
        }catch (Exception e) {
            addErrorMessage("error to add answer", "");
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Delete All Answers.
     */
    public void deleteAllAnswers(){
        try{
            getUnitTweetPoll().getQuestionBean().setListAnswers(new ArrayList<UnitAnswersBean>());
            addInfoMessage("Answer Cleared.", "");
        }catch (Exception e) {
            addErrorMessage("error to add answer", "");
            log.error(e);
        }
    }

    /**
     * Create Tweet Poll.
     */
    public void createTweetPoll(){
        final ISurveyService survey = getServicemanager().getApplicationServices().getSecurityService().getSurveyService();
        try{
          //save question
           survey.createQuestion(getUnitTweetPoll().getQuestionBean());
            //save create tweet poll
           getUnitTweetPoll().setUserId(getUsernameByName().getSecUser().getUid());
           getUnitTweetPoll().setPublishPoll(true);
           getUnitTweetPoll().setStartDateTweet(new Date());
           getUnitTweetPoll().setEndDateTweet(new Date());
           getUnitTweetPoll().setCloseNotification(false);
           getUnitTweetPoll().setReportResults(false);
           getUnitTweetPoll().setResultNotification(false);

           survey.createTweetPoll(getUnitTweetPoll());
           if(getUnitTweetPoll().getPublishPoll()){
               final String tweet = survey.generateTweetPollText(getUnitTweetPoll());
               final SecUsers sessionUser = getUsernameByName().getSecUser();
               final Status status = survey.publicTweetPoll(tweet, sessionUser.getTwitterAccount(), sessionUser.getTwitterPassword());
               final Long tweetId = status.getId();
               if(tweetId != null){
                   getUnitTweetPoll().setTweetId(tweetId);
                   getUnitTweetPoll().setPublicationDateTweet(status.getCreatedAt());
                   survey.saveTweetId(getUnitTweetPoll());
                   log.info("tweeted :"+tweetId);
               }
           }
            addInfoMessage("tweet poll message", "");
            log.debug("tweet poll created");
        }catch (EnMeExpcetion e) {
            addErrorMessage("error "+e, "");
            log.error(e);
            e.printStackTrace();
        }
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
    public void setUnitTweetPoll(final UnitTweetPoll unitTweetPoll) {
        this.unitTweetPoll = unitTweetPoll;
    }

    /**
     * @return the answersBean
     */
    public UnitAnswersBean getAnswersBean() {
        return answersBean;
    }

    /**
     * @param answersBean the answersBean to set
     */
    public void setAnswersBean(final UnitAnswersBean answersBean) {
        this.answersBean = answersBean;
    }

    /**
     * @return the questionBean
     */
    public UnitQuestionBean getQuestionBean() {
        return questionBean;
    }

    /**
     * @param questionBean the questionBean to set
     */
    public void setQuestionBean(final UnitQuestionBean questionBean) {
        this.questionBean = questionBean;
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
    public void setResumeTweet(final String resumeTweet) {
        this.updateCount(resumeTweet);
        this.resumeTweet = resumeTweet;
    }

    /**
     * Update Count.
     * @param resumeTweet
     */
    private void updateCount(final String resumeTweet){
        final Integer tweetLenght = resumeTweet.length();
        setCountTweet(getCountTweet() - tweetLenght);
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
}
