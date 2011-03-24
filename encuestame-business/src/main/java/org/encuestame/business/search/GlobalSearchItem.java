/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.business.search;

/**
 * Represent a global search item on results list.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 23, 2011
 */
public class GlobalSearchItem {

    private String urlLocation;

    private Long score;

    private Long hits;

    private TypeSearchResult typeSearchResult;

    private String itemSearchTitle;

    private String itemSearchDescription;

    /**
     * @return the urlLocation
     */
    public String getUrlLocation() {
        return urlLocation;
    }

    /**
     * @param urlLocation
     *            the urlLocation to set
     */
    public void setUrlLocation(String urlLocation) {
        this.urlLocation = urlLocation;
    }

    /**
     * @return the score
     */
    public Long getScore() {
        return score;
    }

    /**
     * @param score
     *            the score to set
     */
    public void setScore(Long score) {
        this.score = score;
    }

    /**
     * @return the hits
     */
    public Long getHits() {
        return hits;
    }

    /**
     * @param hits
     *            the hits to set
     */
    public void setHits(Long hits) {
        this.hits = hits;
    }

    /**
     * @return the typeSearchResult
     */
    public TypeSearchResult getTypeSearchResult() {
        return typeSearchResult;
    }

    /**
     * @param typeSearchResult
     *            the typeSearchResult to set
     */
    public void setTypeSearchResult(TypeSearchResult typeSearchResult) {
        this.typeSearchResult = typeSearchResult;
    }

    /**
     * @return the itemSearchTitle
     */
    public String getItemSearchTitle() {
        return itemSearchTitle;
    }

    /**
     * @param itemSearchTitle
     *            the itemSearchTitle to set
     */
    public void setItemSearchTitle(String itemSearchTitle) {
        this.itemSearchTitle = itemSearchTitle;
    }

    /**
     * @return the itemSearchDescription
     */
    public String getItemSearchDescription() {
        return itemSearchDescription;
    }

    /**
     * @param itemSearchDescription
     *            the itemSearchDescription to set
     */
    public void setItemSearchDescription(String itemSearchDescription) {
        this.itemSearchDescription = itemSearchDescription;
    }
}

/**
 * Type search result.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 23, 2011
 */
enum TypeSearchResult {
    TWEETPOLL, PROFILE, POLL, SURVEY, ATTACHMENTS
    // TODO: etc etc etc etc
}
