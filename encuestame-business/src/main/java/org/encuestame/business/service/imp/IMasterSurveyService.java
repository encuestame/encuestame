 /************************************************************************************
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

import org.encuestame.core.exception.EnMeDomainNotFoundException;
import org.encuestame.utils.web.UnitHashTag;
import org.encuestame.utils.web.UnitQuestionBean;

 /**
 * Master Survey Service Interface.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since 27/05/2010 20:36:29
 * @version $Id:$
 */
public interface IMasterSurveyService extends IService{

    /**
     * Suggestion Question List.
     * @param questionKeyword
     * @return
     */
    List<UnitQuestionBean> listSuggestQuestion(final String questionKeyword, final String username) throws EnMeDomainNotFoundException;

    /**
     * List Suggested Hash Tags.
     * @param hashTagKeyWord
     * @param maxResults
     * @return
     */
    List<UnitHashTag> listSuggestHashTags(final String hashTagKeyWord, final Integer maxResults);
}
