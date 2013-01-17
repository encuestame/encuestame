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
package org.encuestame.utils.web.search;

import java.io.Serializable;
import java.util.List;

import org.encuestame.utils.enums.TypeSearch;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.social.SocialProvider;

/**
 * Search.
 *
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since December 4, 2012
 */
public class Search implements Serializable {


    /** **/
    private static final long serialVersionUID = 2164671051410597590L;

    /** **/
    private String keyword;

    /** **/
    @Deprecated
    private String period;

    /** **/
    private TypeSearch typeSearch;

    /** **/
    private TypeSearchResult searchResult;

    /** **/
    private Boolean isComplete = Boolean.FALSE;

    /** **/
    private Boolean isFavourite = Boolean.FALSE;

    /** **/
    private Boolean isScheduled = Boolean.FALSE;

    /** **/
    private Integer start = 0;

    /** **/
    private Integer max = 20;

    /** **/
    private List<SocialProvider> providers;

    /** **/
    private List<Long> socialAccounts;


    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(final String keyword) {
        this.keyword = keyword;
    }

    /**
     * @return the period
     */
    @Deprecated
    public String getPeriod() {
        return period;
    }

    /**
     * @param period the period to set
     */
    @Deprecated
    public void setPeriod(final String period) {
        this.period = period;
    }


    /**
     * @return the isComplete
     */
    public Boolean getIsComplete() {
        return isComplete;
    }

    /**
     * @param isComplete the isComplete to set
     */
    public void setIsComplete(final Boolean isComplete) {
        this.isComplete = isComplete;
    }

    /**
     * @return the isFavourite
     */
    public Boolean getIsFavourite() {
        return isFavourite;
    }

    /**
     * @param isFavourite the isFavourite to set
     */
    public void setIsFavourite(final Boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    /**
     * @return the isScheduled
     */
    public Boolean getIsScheduled() {
        return isScheduled;
    }

    /**
     * @param isScheduled the isScheduled to set
     */
    public void setIsScheduled(final Boolean isScheduled) {
        this.isScheduled = isScheduled;
    }

    /**
     * @return the start
     */
    public Integer getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(final Integer start) {
        this.start = start;
    }

    /**
     * @return the max
     */
    public Integer getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(final Integer max) {
        this.max = max;
    }

    /**
     * @return the typeSearch
     */
    public TypeSearch getTypeSearch() {
        return typeSearch;
    }

    /**
     * @param typeSearch the typeSearch to set
     */
    public void setTypeSearch(final TypeSearch typeSearch) {
        this.typeSearch = typeSearch;
    }

    /**
     * @return the searchResult
     */
    public TypeSearchResult getSearchResult() {
        return searchResult;
    }

    /**
     * @param searchResult the searchResult to set
     */
    public void setSearchResult(final TypeSearchResult searchResult) {
        this.searchResult = searchResult;
    }

    /**
     * @return the providers
     */
    public List<SocialProvider> getProviders() {
        return providers;
    }

    /**
     * @param providers the providers to set
     */
    public void setProviders(List<SocialProvider> providers) {
        this.providers = providers;
    }

    /**
     * @return the socialAccounts
     */
    public List<Long> getSocialAccounts() {
        return socialAccounts;
    }

    /**
     * @param socialAccounts the socialAccounts to set
     */
    public void setSocialAccounts(List<Long> socialAccounts) {
        this.socialAccounts = socialAccounts;
    }
}
