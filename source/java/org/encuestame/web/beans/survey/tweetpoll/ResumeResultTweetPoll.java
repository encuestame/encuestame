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

/**
 * Resume Result.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since Feb 20, 2010 12:32:50 PM
 * @version $Id: $
 */
public class ResumeResultTweetPoll {

    private String label;

    private Integer result;


    /**
     * Constructor
     * @param label label
     * @param result result
     */
    public ResumeResultTweetPoll(String label, Integer result) {
        super();
        this.label = label;
        this.result = result;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label
     *            the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the result
     */
    public Integer getResult() {
        return result;
    }

    /**
     * @param result
     *            the result to set
     */
    public void setResult(Integer result) {
        this.result = result;
    }
}
