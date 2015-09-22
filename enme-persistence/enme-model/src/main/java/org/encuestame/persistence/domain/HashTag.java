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
package org.encuestame.persistence.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 * Hash Tags Domain.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 23, 2010 11:49:56 PM
 */

@Entity
@Indexed(index="HashTag")
@Table(name = "hash_tags")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HashTag {

    /** Hash Tag Id. **/
    private Long hashTagId;

    /** Tag String.**/
    private String hashTag;

    /** Total of hits. **/
    private Long hits;

    /** Last time updated. **/
    private Date updatedDate =  Calendar.getInstance().getTime();

    /** {@link TweetPoll} **/
    private Set<TweetPoll> tweetPoll = new HashSet<TweetPoll>();

    /** Size Tag**/
    private Long size;

    /** **/
    private Date createdAt;

    /**
     * @return the hashTagId
     */
    @Id
    @DocumentId
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hash_tag_id", unique = true, nullable = false)
    public  Long getHashTagId() {
        return hashTagId;
    }

    /**
     * @param hashTagId the hashTagId to set
     */
    public  void setHashTagId( Long hashTagId) {
        this.hashTagId = hashTagId;
    }

    /**
     * @return the hashTag
     */
    @Field(index=Index.YES, store=Store.YES)
    @Column(name = "tag")
    public  String getHashTag() {
        return hashTag;
    }

    /**
     * @param hashTag the hashTag to set
     */
    public  void setHashTag( String hashTag) {
        this.hashTag = hashTag;
    }

    /**
     * @return the hits
     */
    @Column(name = "hits")
    public Long getHits() {
        return hits;
    }

    /**
     * @param hits the hits to set
     */
    public void setHits(final Long hits) {
        this.hits = hits;
    }

    /**
     * @return the tweetPoll
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tweetpoll_hashtags",
               joinColumns = {@JoinColumn(name = "hastag_id")},
               inverseJoinColumns = {@JoinColumn(name = "tweetpoll_id")})
    public Set<TweetPoll> getTweetPoll() {
        return tweetPoll;
    }

    /**
     * @param tweetPoll the tweetPoll to set
     */
    public void setTweetPoll(Set<TweetPoll> tweetPoll) {
        this.tweetPoll = tweetPoll;
    }

    /**
    * @return the size
    */
    @Column(name = "size")
    public Long getSize() {
        return size;
    }

    /**
    * @param size the size to set
    */
    public void setSize(final Long size) {
        this.size = size;
    }

    /**
     * @return the updatedDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Field(index=Index.YES, store=Store.YES)
    @Column(name = "hashtag_updated_date", nullable = true)
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * This property is exchanged with the update_date.
     * This property holds the date of the last update of cron.
     * @return the createdAt
     */
    @Column(name = "created_updated")
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }
}
