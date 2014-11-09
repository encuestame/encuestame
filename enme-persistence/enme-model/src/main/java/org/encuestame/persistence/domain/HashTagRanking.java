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

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 * HashTag Ranking history Dao.
 *
 * @author Morales Diana Paola, paolaATencuestame.org
 * @since January 11, 2012
 */
@Entity
@Indexed(index = "HashTagRanking")
@Table(name = "hash_tags_ranking")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HashTagRanking {

    /** **/
    private Long rankId;

    /** {@link HashTag}.**/
    private HashTag hashTag;

    /** **/
    private Date rankingDate;

    /** **/
    private Double average;


    /**
     * @return the rankId
     */
    @Id
    @DocumentId
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rank_id", unique = true, nullable = false)
    public Long getRankId() {
        return rankId;
    }

    /**
     * @param rankId
     *            the rankId to set
     */
    public void setRankId(final Long rankId) {
        this.rankId = rankId;
    }

    /**
     * @return the hashTag
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    public HashTag getHashTag() {
        return hashTag;
    }

    /**
     * @param hashTag
     *            the hashTag to set
     */
    public void setHashTag(final HashTag hashTag) {
        this.hashTag = hashTag;
    }

    /**
     * @return the rankingDate
     */
    @Temporal(TemporalType.DATE)
    @Field(index=Index.TOKENIZED, store=Store.YES)
    @Column(name = "ranking_updated", nullable = true)
    public Date getRankingDate() {
        return rankingDate;
    }

    /**
     * @param rankingDate
     *            the rankingDate to set
     */
    public void setRankingDate(final Date rankingDate) {
        this.rankingDate = rankingDate;
    }

    /**
     * @return the average
     */
    @Column(name = "average")
    public Double getAverage() {
        return average;
    }

    /**
     * @param average
     *            the average to set
     */
    public void setAverage(final Double average) {
        this.average = average;
    }
}
