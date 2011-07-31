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
package org.encuestame.social.api.support;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Buzz Profile.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 20, 2011
 */
@JsonAutoDetect(value=JsonMethod.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuzzProfile  implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 3465481257637944255L;

    public BuzzProfile() {
        // TODO Auto-generated constructor stub
    }

    public BuzzProfile(BuzzObject data) {
        super();
        this.data = data;
    }

    @JsonProperty("data")
    private BuzzObject data = new BuzzObject();

    /**
     * @return the data
     */
    public BuzzObject getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(final BuzzObject data) {
        this.data = data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class BuzzObject implements Serializable {

        public BuzzObject() {
            // TODO Auto-generated constructor stub
        }

        public BuzzObject(String kind, String id, String displayName,
                String aboutMe, String profileUrl, List<Email> emails,
                List<Urls> url, List<Photos> photos,
                List<Organizations> organizations) {
            super();
            this.kind = kind;
            this.id = id;
            this.displayName = displayName;
            this.aboutMe = aboutMe;
            this.profileUrl = profileUrl;
            this.emails = emails;
            this.url = url;
            this.photos = photos;
            this.organizations = organizations;
        }

        /**
         *
         */
        private static final long serialVersionUID = 1192940499531381857L;
        @JsonProperty("kind")
        private String kind;
        @JsonProperty("id")
        private String id;
        @JsonProperty("displayName")
        private String displayName;
        @JsonProperty("aboutMe")
        private String aboutMe;
        @JsonProperty("profileUrl")
        private String profileUrl;
        @JsonProperty("emails")
        private List<Email> emails;
        @JsonProperty("url")
        private List<Urls> url;
        @JsonProperty("photos")
        private List<Photos> photos;
        @JsonProperty("organizations")
        private List<Organizations> organizations;

        /**
         * @return the kind
         */
        public String getKind() {
            return kind;
        }

        /**
         * @param kind
         *            the kind to set
         */
        public void setKind(String kind) {
            this.kind = kind;
        }

        /**
         * @return the id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id
         *            the id to set
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return the displayName
         */
        public String getDisplayName() {
            return displayName;
        }

        /**
         * @param displayName
         *            the displayName to set
         */
        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        /**
         * @return the aboutMe
         */
        public String getAboutMe() {
            return aboutMe;
        }

        /**
         * @param aboutMe
         *            the aboutMe to set
         */
        public void setAboutMe(String aboutMe) {
            this.aboutMe = aboutMe;
        }

        /**
         * @return the profileUrl
         */
        public String getProfileUrl() {
            return profileUrl;
        }

        /**
         * @param profileUrl
         *            the profileUrl to set
         */
        public void setProfileUrl(String profileUrl) {
            this.profileUrl = profileUrl;
        }

        /**
         * @return the emails
         */
        public List<Email> getEmails() {
            return emails;
        }

        /**
         * @param emails
         *            the emails to set
         */
        public void setEmails(List<Email> emails) {
            this.emails = emails;
        }

        /**
         * @return the url
         */
        public List<Urls> getUrl() {
            return url;
        }

        /**
         * @param url
         *            the url to set
         */
        public void setUrl(List<Urls> url) {
            this.url = url;
        }

        /**
         * @return the photos
         */
        public List<Photos> getPhotos() {
            return photos;
        }

        /**
         * @param photos
         *            the photos to set
         */
        public void setPhotos(List<Photos> photos) {
            this.photos = photos;
        }

        /**
         * @return the organizations
         */
        public List<Organizations> getOrganizations() {
            return organizations;
        }

        /**
         * @param organizations
         *            the organizations to set
         */
        public void setOrganizations(List<Organizations> organizations) {
            this.organizations = organizations;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "BuzzObject [kind=" + kind + ", id=" + id + ", displayName="
                    + displayName + ", aboutMe=" + aboutMe + ", profileUrl="
                    + profileUrl + ", emails=" + emails + ", url=" + url
                    + ", photos=" + photos + ", organizations=" + organizations
                    + "]";
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Email implements Serializable{
        /**
         *
         */
        private static final long serialVersionUID = -4547350268246035499L;
        @JsonProperty("value")
        private String value;
        @JsonProperty("type")
        private String type;
        @JsonProperty("type")
        private String primary;

        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }

        /**
         * @param value
         *            the value to set
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * @return the type
         */
        public String getType() {
            return type;
        }

        /**
         * @param type
         *            the type to set
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * @return the primary
         */
        public String getPrimary() {
            return primary;
        }

        /**
         * @param primary
         *            the primary to set
         */
        public void setPrimary(String primary) {
            this.primary = primary;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "Email [value=" + value + ", type=" + type + ", primary="
                    + primary + "]";
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Urls implements Serializable{

        public Urls() {
            // TODO Auto-generated constructor stub
        }

        /**
         *
         */
        private static final long serialVersionUID = -7721596664759401055L;
        @JsonProperty("value")
        private String value;
        @JsonProperty("type")
        private String type;

        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }

        /**
         * @param value
         *            the value to set
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * @return the type
         */
        public String getType() {
            return type;
        }

        /**
         * @param type
         *            the type to set
         */
        public void setType(String type) {
            this.type = type;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "Urls [value=" + value + ", type=" + type + "]";
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Photos implements Serializable {

        public Photos() {
            // TODO Auto-generated constructor stub
        }
        /**
         *
         */
        private static final long serialVersionUID = 7244076531433413961L;
        @JsonProperty("value")
        private String value;
        @JsonProperty("type")
        private String type;
        @JsonProperty("thumbnail")
        private String thumbnail;
        @JsonProperty("width")
        private String width;
        @JsonProperty("height")
        private String height;

        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }

        /**
         * @param value
         *            the value to set
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * @return the type
         */
        public String getType() {
            return type;
        }

        /**
         * @param type
         *            the type to set
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * @return the thumbnail
         */
        public String getThumbnail() {
            return thumbnail;
        }

        /**
         * @param thumbnail
         *            the thumbnail to set
         */
        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        /**
         * @return the width
         */
        public String getWidth() {
            return width;
        }

        /**
         * @param width
         *            the width to set
         */
        public void setWidth(String width) {
            this.width = width;
        }

        /**
         * @return the height
         */
        public String getHeight() {
            return height;
        }

        /**
         * @param height
         *            the height to set
         */
        public void setHeight(String height) {
            this.height = height;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "Photos [value=" + value + ", type=" + type + ", thumbnail="
                    + thumbnail + ", width=" + width + ", height=" + height
                    + "]";
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Organizations implements Serializable{

        public Organizations() {
            // TODO Auto-generated constructor stub
        }
        /**
         *
         */
        private static final long serialVersionUID = -3711106828281912643L;

        @JsonProperty("title")
        private String name;

        @JsonProperty("title")
        private String title;

        @JsonProperty("type")
        private String type;

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name
         *            the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the title
         */
        public String getTitle() {
           return title;
        }

        /**@JsonIgnoreProperties(ignoreUnknown = true)
         * @param title
         *            the title to set
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * @return the type
         */
        public String getType() {
            return type;
        }

        /**
         * @param type
         *            the type to set
         */
        public void setType(String type) {
            this.type = type;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "Organizations [name=" + name + ", title=" + title
                    + ", type=" + type + "]";
        }

    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BuzzProfile [data=" + data + "]";
    }


}