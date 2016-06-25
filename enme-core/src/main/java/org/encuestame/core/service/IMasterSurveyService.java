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

 import org.encuestame.persistence.domain.question.Question;
 import org.encuestame.persistence.domain.question.QuestionAnswer;
 import org.encuestame.util.exception.EnMeNoResultsFoundException;
 import org.encuestame.utils.json.QuestionBean;
 import org.encuestame.utils.web.HashTagBean;
 import org.encuestame.utils.web.QuestionAnswerBean;

 import java.util.List;

 /**
 * Master Survey Service Interface.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since 27/05/2010 20:36:29
 * @version $Id:$
 */
public interface IMasterSurveyService extends ServiceOperations{

    /**
     * Suggestion Question List.
     * @param questionKeyword
     * @return
     */
    List<QuestionBean> listSuggestQuestion(final String questionKeyword, final String username) throws EnMeNoResultsFoundException;

    /**
     * List Suggested Hash Tags.
     * @param hashTagKeyWord
     * @param maxResults
     * @return
     */
    List<HashTagBean> listSuggestHashTags(final String hashTagKeyWord, final Integer maxResults,
            final Long[] exludes);

    /**
     * Save Question Answer.
     * @param answerBean answer
     */
    QuestionAnswer createQuestionAnswer(
            final QuestionAnswerBean answerBean,
            final Question question);

    /**
     *
     * @param id
     * @return
     * @throws EnMeNoResultsFoundException
     */
    QuestionAnswer getQuestionAnswerById(final Long id) throws EnMeNoResultsFoundException;

    /**
     * Retrieve Answer By Question Id.
     * @param questionId question Id
     * @return List of Answers
     */
    List<QuestionAnswerBean> retrieveAnswerByQuestionId(final Long questionId);
}
