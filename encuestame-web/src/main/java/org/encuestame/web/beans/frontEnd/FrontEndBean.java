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
package org.encuestame.web.beans.frontEnd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.encuestame.persistence.dao.SearchPeriods;
import org.encuestame.utils.web.UnitPoll;
import org.encuestame.utils.web.UnitTweetPoll;
import org.encuestame.utils.web.frontEnd.UnitSearchItem;
import org.encuestame.web.beans.MasterBean;


/**
 * FrontEnd Bean.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 26, 2010 8:18:11 PM
 * @version $Id:$
 */
public final class FrontEndBean extends MasterBean{

    /**
     * Tweet Poll List.
     */
    private List<UnitTweetPoll> tweetPollList = new ArrayList<UnitTweetPoll>();

    /**
     * Poll List.
     */
    private List<UnitPoll> pollList = new ArrayList<UnitPoll>();

    /**
     * Survey List.
     */
    private List<UnitPoll> surveyList = new ArrayList<UnitPoll>();

    /**
     * UnitSearchItem.
     */
    private List<UnitSearchItem> resulst = null;

    /**
     * Constructor.
     */
    public FrontEndBean() {
    }

    /**
     * @return the tweetPollList
     */
    public List<UnitTweetPoll> getTweetPollList() {
        return tweetPollList;
    }

    /**
     * @param tweetPollList the tweetPollList to set
     */
    public void setTweetPollList(List<UnitTweetPoll> tweetPollList) {
        this.tweetPollList = tweetPollList;
    }

    /**
     * @return the pollList
     */
    public List<UnitPoll> getPollList() {
        return pollList;
    }

    /**
     * @param pollList the pollList to set
     */
    public void setPollList(List<UnitPoll> pollList) {
        this.pollList = pollList;
    }

    /**
     * @return the surveyList
     */
    public List<UnitPoll> getSurveyList() {
        return surveyList;
    }

    /**
     * @param surveyList the surveyList to set
     */
    public void setSurveyList(List<UnitPoll> surveyList) {
        this.surveyList = surveyList;
    }

    /**
     * @return the resulst
     */
    public List<UnitSearchItem> getResulst() {
        if(resulst == null){
             try {
                    this.resulst = getFrontService().searchItemsByKeyword("a", SearchPeriods.ALLTIME.name(), 20);
                    log.debug("servicesManager"+getServicemanager());
                } catch (Exception e) {
                    log.error(e);
                    e.printStackTrace();
                }
        }
        return resulst;
    }

    /**
     * @param resulst the resulst to set
     */
    public void setResulst(List<UnitSearchItem> resulst) {
        this.resulst = resulst;
    }


}
