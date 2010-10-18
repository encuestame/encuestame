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
package org.encuestame.utils.web.frontEnd;

import java.util.List;

import org.encuestame.utils.web.UnitPoll;
import org.encuestame.utils.web.UnitTweetPoll;

/**
 * Represent a Item on Search Form.
 * This item store list of poll, tweetpoll and survey.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 17, 2010 8:47:46 PM
 * @version $Id:$
 */
public class UnitSearchItem {

    /** List of TweetPolls. **/
    private List<UnitTweetPoll> tweetPolls;

    /** List of Polls. **/
    private List<UnitPoll>  polls;

    //List of Surveys.

    /**
     * @return the tweetPolls
     */
    public List<UnitTweetPoll> getTweetPolls() {
        return tweetPolls;
    }

    /**
     * @param tweetPolls the tweetPolls to set
     */
    public void setTweetPolls(List<UnitTweetPoll> tweetPolls) {
        this.tweetPolls = tweetPolls;
    }

    /**
     * @return the polls
     */
    public List<UnitPoll> getPolls() {
        return polls;
    }

    /**
     * @param polls the polls to set
     */
    public void setPolls(List<UnitPoll> polls) {
        this.polls = polls;
    }
}
