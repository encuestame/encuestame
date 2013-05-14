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

    /** **/
    private Long totalbyItems;

    /** **/
    private String url;

    /** **/
    private Long likeVotes;

    /** **/
    private Long disLikeVotes;

    /** **/
    private Integer currentPos;

    /** **/
    private Integer lastPos;


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
     * @return the totalbyItems
     */
    public Long getTotalbyItems() {
        return totalbyItems;
    }

    /**
     * @param totalbyItems the totalbyItems to set
     */
    public void setTotalbyItems(Long totalbyItems) {
        this.totalbyItems = totalbyItems;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * @return the likeVotes
     */
    public Long getLikeVotes() {
        return likeVotes;
    }

    /**
     * @param likeVotes the likeVotes to set
     */
    public void setLikeVotes(final Long likeVotes) {
        this.likeVotes = likeVotes;
    }

    /**
     * @return the disLikeVotes
     */
    public Long getDisLikeVotes() {
        return disLikeVotes;
    }

    /**
     * @param disLikeVotes the disLikeVotes to set
     */
    public void setDisLikeVotes(final Long disLikeVotes) {
        this.disLikeVotes = disLikeVotes;
    }

    /**
     * @return the currentPos
     */
    public Integer getCurrentPos() {
        return currentPos;
    }

    /**
     * @param currentPos the currentPos to set
     */
    public void setCurrentPos(final Integer currentPos) {
        this.currentPos = currentPos;
    }

    /**
     * @return the lastPos
     */
    public Integer getLastPos() {
        return lastPos;
    }

    /**
     * @param lastPos the lastPos to set
     */
    public void setLastPos(final Integer lastPos) {
        this.lastPos = lastPos;
    }

    public int compareTo(Object o) {
        ProfileRatedTopBean profile = (ProfileRatedTopBean) o;
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
