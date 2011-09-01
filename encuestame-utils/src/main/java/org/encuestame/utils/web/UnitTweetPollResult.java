/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.utils.web;

import java.io.Serializable;

/**
 * Unit TweetPollResults.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 16, 2010 8:48:19 PM
 * @deprecated use {@link TweetPollResultsBean}
 */
@Deprecated
public class UnitTweetPollResult implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5592511215675708669L;

    /**
     * {@link QuestionAnswerBean}
     */
    private QuestionAnswerBean answersBean;

    /**
     * Results.
     */
    private Long results;

    /**
     * @return the answersBean
     */
    public final QuestionAnswerBean getAnswersBean() {
        return answersBean;
    }

    /**
     * @param answersBean
     *            the answersBean to set
     */
    public final void setAnswersBean(final QuestionAnswerBean answersBean) {
        this.answersBean = answersBean;
    }

    /**
     * @return the results
     */
    public final Long getResults() {
        return results;
    }

    /**
     * @param results
     *            the results to set
     */
    public final void setResults(final Long results) {
        this.results = results;
    }
}
