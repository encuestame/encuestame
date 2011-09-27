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
package org.encuestame.persistence.domain.tweetpoll;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.encuestame.persistence.domain.Rate;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;

/**
 * TweetPoll rate items.
 * @author Morales Diana, Paola paolaATencuestame.org
 * @since September 13, 2011
 */
@Entity
@Indexed(index="tweetpoll_rate")
@Table(name = "tweetpoll_rate")
public class TweetPollRate extends Rate {

    /** **/
    private Long tweetPollRateId;

    /** **/
    private TweetPoll tweetPoll;


     /**
     * @return the tweetPollRateId
     */
    @Id
    @DocumentId
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tweetPollRateId", unique = true, nullable = false)
    public Long getTweetPollRateId() {
        return tweetPollRateId;
    }

    /**
     * @param tweetPollRateId the tweetPollRateId to set
     */
    public void setTweetPollRateId(Long tweetPollRateId) {
        this.tweetPollRateId = tweetPollRateId;
    }

    /**
     * @return the tweetPoll
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    public TweetPoll getTweetPoll() {
        return tweetPoll;
    }

    /**
     * @param tweetPoll the tweetPoll to set
     */
    public void setTweetPoll(TweetPoll tweetPoll) {
        this.tweetPoll = tweetPoll;
    }
}
