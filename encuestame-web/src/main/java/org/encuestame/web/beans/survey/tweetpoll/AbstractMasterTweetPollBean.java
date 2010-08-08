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

import org.encuestame.web.beans.MasterBean;

/**
 * Helper TweetPoll Bean.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Aug 8, 2010 12:33:31 PM
 * @version Id:
 */
public abstract class AbstractMasterTweetPollBean extends MasterBean {

    /** Count Tweet. **/
    protected Integer countTweet = this.MAXIMUM_TWEET;

    /** Max Tweet Length. **/
    protected static final Integer MAXIMUM_TWEET = 140;

    /** Warning Tweet Length. **/
    protected static final Integer WARNING_TWEET = 70;

    /** Minimum Question Length. **/
    protected static final Integer MINIMUM_QUESTION_NAME = 10;

    /** Max Length Hash Tag Name. **/
    protected static final Integer MAX_HASHTAGS = 50;

    /**
     * Update Question Count Tweet.
     */
    public abstract void updateQuestionCountTweet();

    /**
     * Color State.
     */
    public abstract void updateColorState();

    /**
     * Update Count.
     */
    public abstract void updateCount();

}
