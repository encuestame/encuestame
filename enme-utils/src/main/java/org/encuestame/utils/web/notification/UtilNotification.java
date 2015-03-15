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
package org.encuestame.utils.web.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Notification Bean
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 18, 2010 11:17:11 PM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UtilNotification implements Serializable {

    /**
     * Serial.
     */
    private static final long serialVersionUID = 74374939382833094L;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "date")
    private String date;

    @JsonProperty(value = "type")
    private String type;

    @JsonProperty(value = "icon")
    private String icon;

    @JsonProperty(value = "hour")
    private String hour;

    @JsonProperty(value = "additionalDescription")
    private String additionalDescription;

    @JsonProperty(value = "url")
    private String url;

    @JsonProperty(value = "readed")
    private Boolean readed;

    @JsonProperty(value = "date_millis")
    private Date realDate;

    /**
     * @return the description
     */
    @JsonIgnore
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the date
     */
    @JsonIgnore
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the hour
     */
    @JsonIgnore
    public String getHour() {
        return hour;
    }

    /**
     * @param hour the hour to set
     */
    public void setHour(String hour) {
        this.hour = hour;
    }

    /**
     * @return the id
     */
    @JsonIgnore
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the icon
     */
    @JsonIgnore
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return the additionalDescription
     */
    @JsonIgnore
    public String getAdditionalDescription() {
        return additionalDescription;
    }

    /**
     * @param additionalDescription the additionalDescription to set
     */
    public void setAdditionalDescription(String additionalDescription) {
        this.additionalDescription = additionalDescription;
    }

    /**
     * @return the type
     */
    @JsonIgnore
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the url
     */
    @JsonIgnore
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
     * @return the readed
     */
    @JsonIgnore
    public Boolean getReaded() {
        return readed;
    }

    /**
     * @param readed the readed to set
     */
    public void setReaded(final Boolean readed) {
        this.readed = readed;
    }

    @JsonIgnore
    public Date getRealDate() {
        return realDate;
    }

    public void setRealDate(Date realDate) {
        this.realDate = realDate;
    }
}
