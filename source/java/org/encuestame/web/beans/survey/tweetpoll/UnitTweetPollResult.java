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

import org.encuestame.web.beans.survey.UnitAnswersBean;

/**
 * Unit TweetPollResults.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since Mar 16, 2010 8:48:19 PM
 * @version $Id: $
 */
public class UnitTweetPollResult {

    private UnitAnswersBean answersBean;

    private Long results;

    /**
     * @return the answersBean
     */
    public UnitAnswersBean getAnswersBean() {
        return answersBean;
    }

    /**
     * @param answersBean
     *            the answersBean to set
     */
    public void setAnswersBean(final UnitAnswersBean answersBean) {
        this.answersBean = answersBean;
    }

    /**
     * @return the results
     */
    public Long getResults() {
        return results;
    }

    /**
     * @param results
     *            the results to set
     */
    public void setResults(final Long results) {
        this.results = results;
    }
}
