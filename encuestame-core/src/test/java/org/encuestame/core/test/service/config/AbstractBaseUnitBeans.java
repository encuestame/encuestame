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
package org.encuestame.core.test.service.config;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.encuestame.core.persistence.pojo.Questions;
import org.encuestame.core.service.util.MD5Utils;
import org.encuestame.utils.web.UnitAnswersBean;
import org.encuestame.utils.web.UnitPatternBean;
import org.encuestame.utils.web.UnitQuestionBean;


/**
 * Abstract Base Unit Beans.
 * @author Morales Urbina, Diana paola@encuestame.org
 * @since 19/04/2010 20:54:56
 * @version $Id:$
 */

public abstract class AbstractBaseUnitBeans extends AbstractBase{

	/**
	 * Create Unit Question Helper.
	 * @param questionName
	 * @param stateId
	 * @param userId
	 * @param listAnswers
	 * @param pattern
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public UnitQuestionBean createUnitQuestionBean(
			final String questionName,
			final Long stateId,
			final Long userId,
			final List listAnswers,
			final UnitPatternBean pattern){
	     final UnitQuestionBean question = new UnitQuestionBean();
	     question.setQuestionName(questionName);
	     question.setStateId(stateId);
	     question.setUserId(userId);
	     question.setListAnswers(listAnswers);
	     question.setPattern(pattern);
	     return question;
	}

	/**
	 * Create Pattern Bean Helper.
	 * @param classpattern
	 * @param descripcionPattern
	 * @param levelpattern
	 * @param patronType
	 * @param template
	 * @return
	 */
	 public UnitPatternBean createPatternBean(
			 final String classpattern,
			 final String descripcionPattern,
			 final String levelpattern,
			 final String patronType,
			 final String template){
		 final UnitPatternBean unitPatternBean = new UnitPatternBean();
		 unitPatternBean.setClasspattern(classpattern);
		 unitPatternBean.setDescripcion(descripcionPattern);
		 unitPatternBean.setLevelpattern(levelpattern);
		 unitPatternBean.setPatronType(patronType);
		 unitPatternBean.setTemplate(template);
		return unitPatternBean;
	 }

	 public UnitAnswersBean createAnswersBean(
			 final String answerHash,
			 final String answers,
			 final Long questionId){
		 final UnitAnswersBean answerBean = new UnitAnswersBean();
		 answerBean.setAnswerHash(answerHash);
		 answerBean.setAnswers(answers);
		 answerBean.setQuestionId(questionId);

		return null;


	 }

}
