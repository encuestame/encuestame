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
package org.encuestame.business.service.imp;

import java.util.List;

import org.encuestame.business.search.GlobalSearchItem;
import org.encuestame.core.service.ServiceOperations;
import org.encuestame.search.IndexerManager;

/**
 * Search Service Operations.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since February 09, 2011
 */
public interface SearchServiceOperations extends ServiceOperations{


    /**
     * Quick search by keyword.
     * @param keyword
     * @return
     */
    List<GlobalSearchItem> quickSearch(final String  keyword);

    /**
     * Quick search based on language analyzer.
     * @param keyword
     * @param language
     * @return
     */
    List<GlobalSearchItem> quickSearch(final String  keyword, final String language);

    /**
     *
     * @param keyword
     * @param language
     * @return
     */
    List<GlobalSearchItem> globalKeywordSearch(final String  keyword, final String language);

    /**
     *
     * @param keyword
     * @return
     */
    List<GlobalSearchItem> globalKeywordSearch(final String  keyword);




}
