package org.encuestame.utils.web;

import java.io.Serializable;

import org.encuestame.utils.web.stats.HashTagDetailStats;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * HashTag stats bean.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since January 10, 2012
 */
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class HashTagStatsBean implements Serializable {

    /** Serial **/
    private static final long serialVersionUID = -2620835370999916919L;

    /** Total hashTags usage in Poll, Survey and TweetPoll. **/
    @com.fasterxml.jackson.annotation.JsonProperty(value = "usage_by_item")
    private HashTagDetailStats usageByItem;

    /** Total HashTags usage in social network profiles. **/
    @com.fasterxml.jackson.annotation.JsonProperty(value = "total_usage_by_social_network")
    private HashTagDetailStats totalUsageBySocialNetwork;

    /** Total hits. **/
    @JsonProperty(value = "total_hits")
    private HashTagDetailStats totalHits;

    /** **/
    @JsonProperty(value = "usage_by_votes")
    private HashTagDetailStats usageByVotes;

    /**
     * @return the usageByItem
     */
    @JsonIgnore
    public HashTagDetailStats getUsageByItem() {
        return usageByItem;
    }

    /**
     * @param usageByItem the usageByItem to set
     */
    public void setUsageByItem(final HashTagDetailStats usageByItem) {
        this.usageByItem = usageByItem;
    }

    /**
     * @return the totalUsageBySocialNetwork
     */
    @JsonIgnore
    public HashTagDetailStats getTotalUsageBySocialNetwork() {
        return totalUsageBySocialNetwork;
    }

    /**
     * @param totalUsageBySocialNetwork the totalUsageBySocialNetwork to set
     */
    public void setTotalUsageBySocialNetwork(
            final HashTagDetailStats totalUsageBySocialNetwork) {
        this.totalUsageBySocialNetwork = totalUsageBySocialNetwork;
    }

    /**
     * @return the totalHits
     */
    @JsonIgnore
    public HashTagDetailStats getTotalHits() {
        return totalHits;
    }

    /**
     * @param totalHits the totalHits to set
     */
    public void setTotalHits(final HashTagDetailStats totalHits) {
        this.totalHits = totalHits;
    }

    /**
     * @return the usageByVotes
     */
    @JsonIgnore
    public HashTagDetailStats getUsageByVotes() {
        return usageByVotes;
    }

    /**
     * @param usageByVotes the usageByVotes to set
     */
    public void setUsageByVotes(final HashTagDetailStats usageByVotes) {
        this.usageByVotes = usageByVotes;
    }
}