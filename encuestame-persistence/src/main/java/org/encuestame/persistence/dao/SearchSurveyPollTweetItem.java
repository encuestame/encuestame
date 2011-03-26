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
package org.encuestame.persistence.dao;

import java.util.List;

import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;

/**
 * Represent item on a Search.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 17, 2010 11:41:48 AM
 * @version $Id:$
 */
public class SearchSurveyPollTweetItem {

    /**
     * List of TweetPolls.
     */
    private List<TweetPoll> tweetPolls;

    /**
     * List of Polls.
     */
    private List<Poll> polls;

    /**
     * List of Surveys.
     */
    private List<Survey> surveys;

    /**
     * @return the tweetPolls
     */
    public List<TweetPoll> getTweetPolls() {
        return tweetPolls;
    }

    /**
     * @param tweetPolls the tweetPolls to set
     */
    public void setTweetPolls(final List<TweetPoll> tweetPolls) {
        this.tweetPolls = tweetPolls;
    }

    /**
     * @return the polls
     */
    public List<Poll> getPolls() {
        return polls;
    }

    /**
     * @param polls the polls to set
     */
    public void setPolls(final List<Poll> polls) {
        this.polls = polls;
    }

    /**
     * @return the surveys
     */
    public List<Survey> getSurveys() {
        return surveys;
    }

    /**
     * @param surveys the surveys to set
     */
    public void setSurveys(final List<Survey> surveys) {
        this.surveys = surveys;
    }
}
