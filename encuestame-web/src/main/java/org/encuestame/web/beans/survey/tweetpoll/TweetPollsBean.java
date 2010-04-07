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
import java.util.List;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.service.ISurveyService;
import org.encuestame.utils.web.UnitAnswersBean;
import org.encuestame.utils.web.UnitTweetPoll;
import org.encuestame.utils.web.UnitTweetPollResult;
import org.encuestame.web.beans.MasterBean;
import org.richfaces.component.html.HtmlDataTable;

import twitter4j.Status;

/**
 * Tweet Polls Bean.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since Feb 19, 2010 6:31:47 PM
 * @version $Id:$
 */
public class TweetPollsBean extends MasterBean {

    /** DataTable. **/
    private HtmlDataTable tweetDataTable;

    /** Constructor. **/
    public TweetPollsBean() {
    }

    /** List Tweets. **/
    public List<UnitTweetPoll> listTweets = new ArrayList<UnitTweetPoll>();

    /** Selected {@link UnitTweetPoll}. **/
    public UnitTweetPoll selectedTweetPoll = new UnitTweetPoll();

    /** Edit Answer. **/
    private Boolean editAnswer = false;

    /** Answer Id Update **/
    private Long answerIdUpdate;

    /** Answer Name. **/
    private String answerName;

    /** Selected Results. **/
    private List<UnitTweetPollResult> selectedResults = new ArrayList<UnitTweetPollResult>();

    private static final Integer TWEET_TEXT_LENGTH = 140;

    /**
     *
     */
    private void loadTweets() {
        try {
            this.listTweets = getServicemanager().getApplicationServices().getSurveyService()
            .getTweetsPollsByUserId(getUsernameByName().getSecUser().getUid());
             log.info("loading tweet polls");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Change Edit.
     */
    public final void changeEdit(){
         this.editAnswer = !this.editAnswer;
    }

    /**
     * Save Answer.
     */
    public final void saveAnswer(){
        log.info("Answer Id "+getAnswerIdUpdate());
        getUpdateItem(getAnswerIdUpdate());
        if(getAnswerIdUpdate() != null && getUpdateItem(getAnswerIdUpdate())!= null){
            try {
                getServicemanager().getApplicationServices().
                getSurveyService().updateAnswerByAnswerId(getAnswerIdUpdate(), getUpdateItem(getAnswerIdUpdate()));
                addInfoMessage("Updated Answer", " New name answer update to ["+getUpdateItem(getAnswerIdUpdate())+"]");
            }
            catch (EnMeExpcetion e) {
                log.error(e);
                e.printStackTrace();
                addErrorMessage(e.getMessage(), e.getMessage());
            }
        }else{
            addErrorMessage("Can not update Answer", "Can not update Answer");
        }
    }

    /**
     * Load Results.
     */
    public final void loadResults(){
        if(getSelectedTweetPoll().getId() != null){
            log.info("loadResults");
            setSelectedResults(getSurveyService().getResultsByTweetPollId(getSelectedTweetPoll().getId()));
        }
    }

    /**
     * Publish Tweeter.
     */
    public final void publishTweet() {
        try {
            log.info("loggin tweet");
            final ISurveyService survey = getServicemanager().getApplicationServices()
            .getSurveyService();
            final String tweetText = survey.generateTweetPollText(getSelectedTweetPoll(), getDomain());
            log.info("Largo Tweet"+tweetText.length());
            log.info( "Tweet a postear ->"+tweetText);
            if(tweetText.length() < TWEET_TEXT_LENGTH){
                final SecUsers user = getUsernameByName().getSecUser();
                final Status status = survey.publicTweetPoll(tweetText, user.getTwitterAccount(), user.getTwitterPassword());
                log.info(status.getId());
                log.info(status.getCreatedAt());
                log.info(status.getText());
                log.info(status.getUser());
                final Long tweetId = status.getId();
                if(tweetId != null){
                    getSelectedTweetPoll().setTweetId(tweetId);
                    getSelectedTweetPoll().setPublicationDateTweet(status.getCreatedAt());
                    getSelectedTweetPoll().setTwitterUserAccount(getUsernameByName().getSecUser().getTwitterAccount());
                    survey.saveTweetId(getSelectedTweetPoll());
                    log.info("tweeted :"+tweetId);
                }
                getSelectedTweetPoll().setPublishPoll(Boolean.TRUE);
                addInfoMessage("TweetPoll Posted", shortLongString(tweetText));
            }else{
                addWarningMessage("Exceded 140 characters", "You need shorten your tweet");
            }
        } catch (EnMeExpcetion e) {
            addErrorMessage("Error Publishing Tweet Poll", e.getMessage());
            e.printStackTrace();
            log.error(e);
        }
    }

    /**
     *
     */
    public void saveChanges(){

    }

    /**
     * Get Update Answer by Id.
     * @param updateId
     * @return
     */
    private String getUpdateItem(final Long updateId){
        String updateName = null;
        for (final UnitAnswersBean answer : getSelectedTweetPoll().getQuestionBean().getListAnswers()) {
            if(answer.getAnswerId().equals(updateId)){
                 updateName  = answer.getAnswers();
            }
        }
        return updateName;
    }

    /**
     * @return the listTweets
     */
    public final List<UnitTweetPoll> getListTweets() {
        loadTweets();
        return listTweets;
    }

    /**
     * @param listTweets
     *            the listTweets to set
     */
    public final void setListTweets(final List<UnitTweetPoll> listTweets) {
        this.listTweets = listTweets;
    }

    /**
     * See Details.
     */
    public final void seeDetails() {
        final UnitTweetPoll item = (UnitTweetPoll) getTweetDataTable()
                .getRowData();
    }

    /**
     * @return the tweetDataTable
     */
    public final HtmlDataTable getTweetDataTable() {
        return tweetDataTable;
    }

    /**
     * @param tweetDataTable
     *            the tweetDataTable to set
     */
    public final void setTweetDataTable(final HtmlDataTable tweetDataTable) {
        this.tweetDataTable = tweetDataTable;
    }

    /**
     * @return the selectedTweetPoll
     */
    public final UnitTweetPoll getSelectedTweetPoll() {
        return selectedTweetPoll;
    }

    /**
     * @param selectedTweetPoll
     *            the selectedTweetPoll to set
     */
    public final void setSelectedTweetPoll(final UnitTweetPoll selectedTweetPoll) {
        selectedTweetPoll.setTwitterUserAccount(getUsernameByName().getSecUser().getTwitterAccount());
        this.selectedTweetPoll = selectedTweetPoll;
    }

    /**
     * @return the editAnswer
     */
    public final Boolean getEditAnswer() {
        return editAnswer;
    }

    /**
     * @param editAnswer the editAnswer to set
     */
    public final void setEditAnswer(final Boolean editAnswer) {
        this.editAnswer = editAnswer;
    }

    /**
     * @return the answerIdUpdate
     */
    public final Long getAnswerIdUpdate() {
        return answerIdUpdate;
    }

    /**
     * @param answerIdUpdate the answerIdUpdate to set
     */
    public final void setAnswerIdUpdate(Long answerIdUpdate) {
        this.answerIdUpdate = answerIdUpdate;
    }

    /**
     * @return the answerName
     */
    public final String getAnswerName() {
        return answerName;
    }

    /**
     * @param answerName the answerName to set
     */
    public final void setAnswerName(String answerName) {
        this.answerName = answerName;
    }

    /**
     * @return the selectedResults
     */
    public final List<UnitTweetPollResult> getSelectedResults() {
        loadResults();
        return selectedResults;
    }

    /**
     * @param selectedResults the selectedResults to set
     */
    public final void setSelectedResults(final List<UnitTweetPollResult> selectedResults) {
        this.selectedResults = selectedResults;
    }
}
