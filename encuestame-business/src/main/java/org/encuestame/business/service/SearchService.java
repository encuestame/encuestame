/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.business.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.set.ListOrderedSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.business.search.AttachmentSearchItem;
import org.encuestame.business.search.GlobalSearchItem;
import org.encuestame.business.search.UtilConvertToSearchItems;
import org.encuestame.business.service.imp.SearchServiceOperations;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;

/**
 * Search Service.
 *
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since February 09, 2011
 */
public class SearchService extends AbstractIndexService implements
        SearchServiceOperations {

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());

    public List<GlobalSearchItem> quickSearch(String keyword,
            final Integer start, final Integer limit) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.business.service.imp.SearchServiceOperations#quickSearch
     * (java.lang.String, java.lang.String)
     */
    public List<GlobalSearchItem> quickSearch(final String keyword,
            String language, final Integer start, final Integer limit)
            throws EnMeNoResultsFoundException {
        HashSet<GlobalSearchItem> hashset = new java.util.HashSet<GlobalSearchItem>();


        final List<GlobalSearchItem> questionResult = UtilConvertToSearchItems
                .convertQuestionToSearchItem(retrieveQuestionByKeyword(keyword,
                        null));
        final List<GlobalSearchItem> profiles = UtilConvertToSearchItems
                .convertProfileToSearchItem(getAccountDao().getPublicProfiles(keyword, limit, start));


        final List<GlobalSearchItem> tags = UtilConvertToSearchItems
        .convertHashTagToSearchItem(getHashTagDao().getListHashTagsByKeyword(keyword, limit));

        final List<GlobalSearchItem> attachments = UtilConvertToSearchItems
                                    .convertAttachmentSearchToSearchItem(getAttachmentItem(keyword));

        log.debug("questionResult " + questionResult.size());
        log.debug("profiles " + profiles.size());
        log.debug("tags " + tags.size());
        log.debug("attachments " + attachments.size());

        hashset.addAll(questionResult);
        hashset.addAll(profiles);
        hashset.addAll(tags);
        hashset.addAll(attachments);

        final List<GlobalSearchItem> totalItems = new ArrayList<GlobalSearchItem>(hashset);
        int x = 1;
        for (int i = 0; i < totalItems.size(); i++) {
            totalItems.get(i).setId(Long.valueOf(x));
            x++;
        }
        return totalItems;
    }

    public List<GlobalSearchItem> globalKeywordSearch(String keyword,
            String language, final Integer start, final Integer limit) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<GlobalSearchItem> globalKeywordSearch(String keyword,
            final Integer start, final Integer limit) {
        // TODO Auto-generated method stub
        return null;
    }
}
