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
package org.encuestame.core.search;

import org.encuestame.utils.enums.TypeSearchResult;

/**
 * Represent a global search item on results list.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 23, 2011
 */
public class GlobalSearchItem {

    /**
     * Id.
     */
    private Long id;

    /**
     * URI path.
     */
    private String urlLocation;

    /**
     * Score.
     */
    private Long score  = 0L;

    /**
     * Score hits.
     */
    private Long hits = 0L;

    /**
     * {@link TypeSearchResult}.
     */
    private TypeSearchResult typeSearchResult;

    /**
     * Item search title.
     */
    private String itemSearchTitle;

    /**
     * Description.
     */
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

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((hits == null) ? 0 : hits.hashCode());
        result = prime
                * result
                + ((itemSearchDescription == null) ? 0 : itemSearchDescription
                        .hashCode());
        result = prime * result
                + ((itemSearchTitle == null) ? 0 : itemSearchTitle.hashCode());
        result = prime * result + ((score == null) ? 0 : score.hashCode());
        result = prime
                * result
                + ((typeSearchResult == null) ? 0 : typeSearchResult.hashCode());
        result = prime * result
                + ((urlLocation == null) ? 0 : urlLocation.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof GlobalSearchItem)){
            return false;
        }
        final GlobalSearchItem other = (GlobalSearchItem) obj;
        if (hits == null) {
            if (other.hits != null) {
                return false;
            }
        } else if (!hits.equals(other.hits)) {
            return false;
        }
        if (itemSearchDescription == null) {
            if (other.itemSearchDescription != null) {
                return false;
            }
        } else if (!itemSearchDescription.equals(other.itemSearchDescription))
            return false;
        if (itemSearchTitle == null) {
            if (other.itemSearchTitle != null) {
                return false;
            }
        } else if (!itemSearchTitle.equals(other.itemSearchTitle))
            return false;
        if (score == null) {
            if (other.score != null) {
                return false;
            }
        } else if (!score.equals(other.score))
            return false;
        if (typeSearchResult != other.typeSearchResult) {
            return false;
        }
        if (urlLocation == null) {
            if (other.urlLocation != null) {
                return false;
            }
        } else if (!urlLocation.equals(other.urlLocation)) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "GlobalSearchItem [urlLocation=" + urlLocation + ", score="
                + score + ", hits=" + hits + ", typeSearchResult="
                + typeSearchResult + ", itemSearchTitle=" + itemSearchTitle
                + ", itemSearchDescription=" + itemSearchDescription + "]";
    }
}
