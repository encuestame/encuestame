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

import java.util.ArrayList;
import java.util.List;


/**
 * Unit Poll Detail Bean.
 * @author Picado, Juan juanATencuestame.org
 * @since  March 04, 2011
 */
public class PollDetailBean {

    /**
     *
     */
    public java.util.List<PollBeanResult> results = new ArrayList<PollBeanResult>();

    /**
     *
     */
    public PollBean pollBean;


    /**
     * List of available answers.
     */
    public List<QuestionAnswerBean> listAnswers = new ArrayList<QuestionAnswerBean>();

    /**
     * @return the results
     */
    public java.util.List<PollBeanResult> getResults() {
        return results;
    }

    /**
     * @param results the results to set
     */
    public void setResults(final java.util.List<PollBeanResult> results) {
        this.results = results;
    }

    /**
     * @return the pollBean
     */
    public PollBean getPollBean() {
        return pollBean;
    }

    /**
     * @param pollBean the pollBean to set
     */
    public void setPollBean(final PollBean pollBean) {
        this.pollBean = pollBean;
    }

    /**
     * @return the listAnswers
     */
    public List<QuestionAnswerBean> getListAnswers() {
        return listAnswers;
    }

    /**
     * @param listAnswers the listAnswers to set
     */
    public void setListAnswers(final List<QuestionAnswerBean> listAnswers) {
        this.listAnswers = listAnswers;
    }
}
