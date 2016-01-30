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
package org.encuestame.core.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.lucene.queryparser.classic.ParseException;
import org.encuestame.core.search.GlobalSearchItem;
import org.encuestame.core.service.ServiceOperations;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.enums.Language;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.QuestionBean; 

/**
 * Search Service Operations.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since February 09, 2011
 */
public interface SearchServiceOperations extends ServiceOperations {

    /**
     * Quick search by keyword.
     * @param keyword
     * @param limit
     * @return
     */
    Map<String, List<GlobalSearchItem>> quickSearch(final String keyword,
            final Integer start, final Integer limit);

    /**
     * Quick search based on language analyzer.
     *
     * @param keyword
     * @param language
     * @return
     * @throws EnMeNoResultsFoundException
     */
    Map<String, List<GlobalSearchItem>> quickSearch(
            final String keyword,
			final Language language,
            final Integer start,
            final Integer limit,
			final Integer limitByItem,
			final List<TypeSearchResult> resultsAllowed)
            throws EnMeNoResultsFoundException, IOException ;

    /**
     * @param keyword
     * @param language
     * @return
     */
    List<GlobalSearchItem> globalKeywordSearch(final String keyword,
            final String language, final Integer start, final Integer limit);

    /**
     *
     * @param keyword
     * @return
     */
    List<GlobalSearchItem> globalKeywordSearch(final String keyword,
            final Integer start, final Integer limit); 
    
    /**
     * 
     * @param questionId
     * @return
     * @throws EnMeNoResultsFoundException 
     */
    QuestionBean getQuestionInfo(final Long questionId) throws EnMeNoResultsFoundException;

}
