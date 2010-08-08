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
    }

    @Override
    public void updateColorState() {
        log.debug("updateColorState");
    }

    @Override
    public void updateCount() {
        log.debug("updateCount");
    }
}
