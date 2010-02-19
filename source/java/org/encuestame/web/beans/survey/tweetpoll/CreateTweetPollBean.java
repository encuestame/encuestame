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
import org.encuestame.web.beans.MasterBean;
import org.encuestame.web.beans.survey.UnitAnswersBean;

import twitter4j.Status;

/**
 * Create Tweet Poll.
 * @author Picado, Juan juan@encuestame.org
 * @since Feb 13, 2010 11:36:48 PM
 * @version $Id$
 */
public class CreateTweetPollBean extends MasterBean {

    /** Tweet Question. **/
    private String tweetQuestion;

    /** {@link UnitTweetPoll}. **/
    private UnitTweetPoll unitTweetPoll;


    /**
     * Constructor.
     */
    public CreateTweetPollBean() {
    }


    /**
     * Create Tweet Poll.
     */
    public void createTweetPoll(){
        final ISurveyService survey = getServicemanager().getApplicationServices().getSecurityService().getSurveyService();
        try{
           final UnitTweetPoll savedTweetPoll =  survey.createTweetPoll(getUnitTweetPoll());
           if(savedTweetPoll.getPublishPoll()){
               final String tweet = survey.generateTweetPollText(savedTweetPoll);
               final SecUsers sessionUser = getUsernameByName().getSecUser();
               final Status status = survey.publicTweetPoll(tweet, sessionUser.getTwitterAccount(), sessionUser.getTwitterPassword());
               final Long tweetId = status.getId();
               if(tweetId != null){
                   //TODO: update tweet id.
               }
           }
            addInfoMessage("tweet poll message", "");
            log.debug("tweet poll created");
        }catch (EnMeExpcetion e) {
            addErrorMessage("error "+e, "");
            log.error(e);
        }
    }

    /**
     *
     * @return
     */
    private String buildTweetQuestion(){
        //retrieve question
        //final String tweetQuestion = getUnitTweetPoll().getTweetPoll().getQuestionName();
        //final List<UnitAnswersBean> answers = getUnitTweetPoll().getAnswers();
        //if(siz)


        return tweetQuestion;
    }

    /**
     * @return the tweetQuestion
     */
    public String getTweetQuestion() {
        return tweetQuestion;
    }

    /**
     * @param tweetQuestion the tweetQuestion to set
     */
    public void setTweetQuestion(final String tweetQuestion) {
        this.tweetQuestion = tweetQuestion;
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
}
