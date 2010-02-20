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

import org.encuestame.web.beans.MasterBean;
import org.richfaces.component.html.HtmlDataTable;

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

    /**
     *
     */
    private void loadTweets() {
        try {
            this.listTweets = getServicemanager().getApplicationServices()
                    .getSecurityService().getSurveyService()
                    .getTweetsPollsByUserId(getUsernameByName().getSecUser().getUid());
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * @return the listTweets
     */
    public List<UnitTweetPoll> getListTweets() {
        loadTweets();
        return listTweets;
    }

    /**
     * @param listTweets
     *            the listTweets to set
     */
    public void setListTweets(final List<UnitTweetPoll> listTweets) {
        this.listTweets = listTweets;
    }

    /**
     * See Details.
     */
    public void seeDetails(){
        final UnitTweetPoll item = (UnitTweetPoll) getTweetDataTable().getRowData();
     }

    /**
     * @return the tweetDataTable
     */
    public HtmlDataTable getTweetDataTable() {
        return tweetDataTable;
    }

    /**
     * @param tweetDataTable
     *            the tweetDataTable to set
     */
    public void setTweetDataTable(final HtmlDataTable tweetDataTable) {
        this.tweetDataTable = tweetDataTable;
    }

}
