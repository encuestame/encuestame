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
package org.encuestame.persistence.domain.survey;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.encuestame.persistence.domain.AbstractHit;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.hibernate.search.annotations.DocumentId;

/**
 * Poll hits.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since September 07, 2011
 */

@Entity
@Table(name = "poll_hits")
public class PollHits extends AbstractHit{

    /** Id. **/
    private Long id;

    /** {@link TweetPoll} **/
    private Poll poll;

    /**
     * @return the id
     */
    @Id
    @Column(name = "poll_hit_id", unique = true, nullable = true)
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the poll
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    public Poll getPoll() {
        return poll;
    }

    /**
     * @param poll the poll to set
     */
    public void setPoll(final Poll poll) {
        this.poll = poll;
    }
}
