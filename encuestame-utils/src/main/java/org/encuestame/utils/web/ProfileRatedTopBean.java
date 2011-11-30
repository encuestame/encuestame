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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Profile rated top Bean.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since  November 26, 2011
 */
public class ProfileRatedTopBean implements Serializable, Comparable<Object> {

    /** Serial. **/
    private static final long serialVersionUID = -1723618853843044791L;

    /** Log **/
    private Log log = LogFactory.getLog(this.getClass());

    /** Username**/
    private String username;

    /** **/
    private Long topValue;

    private Long tpoll;

    private Long poll;

    private Long survey;

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * @return the topValue
     */
    public Long getTopValue() {
        return topValue;
    }

    /**
     * @param topValue the topValue to set
     */
    public void setTopValue(final Long topValue) {
        this.topValue = topValue;
    }

    /**
     * @return the tpoll
     */
    public Long getTpoll() {
        return tpoll;
    }

    /**
     * @param tpoll the tpoll to set
     */
    public void setTpoll(Long tpoll) {
        this.tpoll = tpoll;
    }

    /**
     * @return the poll
     */
    public Long getPoll() {
        return poll;
    }

    /**
     * @param poll the poll to set
     */
    public void setPoll(Long poll) {
        this.poll = poll;
    }

    /**
     * @return the survey
     */
    public Long getSurvey() {
        return survey;
    }

    /**
     * @param survey the survey to set
     */
    public void setSurvey(Long survey) {
        this.survey = survey;
    }

    public int compareTo(Object o) {
        ProfileRatedTopBean profile = (ProfileRatedTopBean) o;
        log.debug("Profile Value: " + profile.getTopValue());
        log.debug("This profile Value: " + this.getTopValue());
        int CompareToValue = Float.compare(profile.getTopValue() == null ? 0
                : profile.getTopValue(),
                this.getTopValue() == null ? 0 : this.getTopValue());
      /*  if (CompareToValue == 0) {
            return this.getCreateDate().compareTo(home.getCreateDate());
        } else {
            log.debug(" Result Home Bean compare: " + CompareToValue);
            return CompareToValue;
        }*/
        return CompareToValue;
    }

}
