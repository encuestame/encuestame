/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
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

import org.encuestame.core.service.ServiceOperations;
import org.encuestame.persistence.exception.EnMeSearchException;
import org.encuestame.utils.web.frontEnd.UnitSearchItem;

/**
 * Implementation for Front End Service.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 17, 2010 11:29:51 AM
 * @version $Id:$
 */
public interface IFrontEndService extends ServiceOperations {


    /**
     * Search Items By Keyword.
     * @param keyword keyword.
     * @param maxResults limit of results to return.
     * @return result of the search.
     * @throws EnMeSearchException search exception.
     */
    List<UnitSearchItem> searchItemsByKeyword(
                final String keyword,
                final String period,
                Integer maxResults)
           throws EnMeSearchException;

}
