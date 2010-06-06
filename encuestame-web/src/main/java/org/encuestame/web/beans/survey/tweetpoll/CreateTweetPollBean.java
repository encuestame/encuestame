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
import java.util.List;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.service.ISurveyService;
import org.encuestame.core.service.ITweetPollService;
import org.encuestame.core.service.util.MD5Utils;
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

    final List<String> lista = new ArrayList<String>();

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
    public final void createShortSimulateUrl(final UnitAnswersBean answer){
        try{
            final ISurveyService survey = getServicemanager().getApplicationServices().getSurveyService();
            answer.setAnswerHash(MD5Utils.md5(String.valueOf(java.util.Calendar.getInstance().getTimeInMillis())));
            log.info(getDomain());
            final String url = survey.getTwitterService().getTinyUrl(buildUrl(answer));
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
                this.createShortSimulateUrl(getAnswersBean());
                getUnitTweetPoll().getQuestionBean().getListAnswers().add(getAnswersBean());
                setAnswersBean(new UnitAnswersBean());
                addInfoMessage("Answer Added", "");
            }
            else{
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
     * Suggest.
     * @param suggest keyword.
     * @return
     */
    public List<UnitQuestionBean> suggest(final Object suggest){
        String pref = (String) suggest;
        final ArrayList<UnitQuestionBean> result = new ArrayList<UnitQuestionBean>();
        final List<UnitQuestionBean> suggested = getTweetPollService().listSuggestQuestion(pref, getUsername());
        log.info("suggested "+suggested.size());
        for(UnitQuestionBean elem: suggested){
            if ((elem.getQuestionName().toLowerCase().indexOf(pref.toLowerCase()) == 0)) {
            log.info("question suggested "+elem.getQuestionName());
            result.add(elem);
            }
        }
        return result;
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
     * Create Tweet Poll.
     */
    public final void createTweetPoll(){
        final ITweetPollService tweetPollService = getTweetPollService();
        try{
          //save question
           getSurveyService().createQuestion(getUnitTweetPoll().getQuestionBean());
            //save create tweet poll
           getUnitTweetPoll().setUserId(getUsernameByName().getSecUser().getUid());
           //TODO: we need implement scheduled tweetPoll.
           getUnitTweetPoll().setScheduleDate(new Date());
           getUnitTweetPoll().setCloseNotification(false);
           getUnitTweetPoll().setAllowLiveResults(false);
           getUnitTweetPoll().setSchedule(false);
           getUnitTweetPoll().setPublishPoll(false);
           getUnitTweetPoll().setResultNotification(false);
           tweetPollService.createTweetPoll(getUnitTweetPoll());
           if(getUnitTweetPoll().getPublishPoll()){
               final String tweet = tweetPollService.generateTweetPollText(getUnitTweetPoll(), getDomain());
               final SecUsers sessionUser = getUsernameByName().getSecUser();
               final Status status = tweetPollService.publicTweetPoll(tweet, sessionUser.getTwitterAccount(), sessionUser.getTwitterPassword());
               final Long tweetId = status.getId();
               if(tweetId != null){
                   getUnitTweetPoll().setTweetId(tweetId);
                   getUnitTweetPoll().setPublicationDateTweet(status.getCreatedAt());
                   tweetPollService.saveTweetId(getUnitTweetPoll());
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
        return questionBean;
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
    public final Integer getCountTweet() {
        return countTweet;
    }

    /**
     * @param countTweet the countTweet to set
     */
    public final void setCountTweet(final Integer countTweet) {
        this.countTweet = countTweet;
    }
}
