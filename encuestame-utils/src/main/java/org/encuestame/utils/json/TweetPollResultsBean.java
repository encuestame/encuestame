package org.encuestame.utils.json;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TweetPollResultsBean {

    @JsonProperty("id")
    private long idTest;

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.idTest = id;
    }

    public long getId() {
        return idTest;
    }

}
