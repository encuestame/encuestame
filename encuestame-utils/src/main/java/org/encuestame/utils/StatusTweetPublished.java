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
package org.encuestame.utils;

import java.util.Date;

/**
 * Description.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since May 10, 2011
 */
public class StatusTweetPublished {

    private String tweetId;

    private String textTweeted;

    private Date datePublished;

    public StatusTweetPublished() {
    }

    /**
     * @return the tweetId
     */
    public String getTweetId() {
        return tweetId;
    }

    /**
     * @param tweetId
     *            the tweetId to set
     */
    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    /**
     * @return the textTweeted
     */
    public String getTextTweeted() {
        return textTweeted;
    }

    /**
     * @param textTweeted
     *            the textTweeted to set
     */
    public void setTextTweeted(String textTweeted) {
        this.textTweeted = textTweeted;
    }

    /**
     * @return the datePublished
     */
    public Date getDatePublished() {
        return datePublished;
    }

    /**
     * @param datePublished
     *            the datePublished to set
     */
    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "StatusTweetPublished [tweetId=" + tweetId + ", textTweeted="
                + textTweeted + ", datePublished=" + datePublished + "]";
    }
}
