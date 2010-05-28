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
package org.encuestame.core.service;

import java.util.List;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.dao.IMasterSurveyService;
import org.encuestame.core.persistence.pojo.Questions;
import org.encuestame.utils.web.UnitAnswersBean;
import org.encuestame.utils.web.UnitPoll;
import org.encuestame.utils.web.UnitQuestionBean;

/**
 * Poll Service Interface.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since May 16, 2010
 * @version $Id: $
 */
public interface IPollService extends IMasterSurveyService{

    /**
     *	Create Poll
     * @param pollBean
     * @param currentUser
     * @throws Exception
     */
  void createPoll(final UnitPoll pollBean, final String currentUser) throws Exception;

  /**
   * Save Answers Poll.
   * @param answerBean
   * @param question
   */
  void saveAnswer(final UnitAnswersBean answerBean, final Questions question);

  /**
   * Create Answers in Poll.
   * @param questionBean
   * @throws EnMeExpcetion
   */
  void createQuestion(final UnitQuestionBean questionBean) throws EnMeExpcetion;

  /**
   * List Poll by Question.
   * @param currentUser currentUser
   * @param keyword Question keyword
   * @return
   */
  List<UnitPoll> listPollbyQuestionKeyword(final String currentUser, final String keyword);

  /**
   * List Poll By User.
   * @param currentUser currentUser
   * @return
   */
  List<UnitPoll> listPollByUser(final String currentUser);

      /**
       * Update Question Poll.
       * @param unitQuestionPoll
       * @throws EnMeExpcetion exception
       */
  void updateQuestionPoll(final UnitQuestionBean unitQuestionPoll) throws EnMeExpcetion;

}
