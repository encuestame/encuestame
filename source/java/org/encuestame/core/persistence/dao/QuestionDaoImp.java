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
package org.encuestame.core.persistence.dao;

import java.util.List;

import org.encuestame.core.persistence.dao.imp.IQuestionDao;
import org.encuestame.core.persistence.pojo.Questions;
import org.encuestame.core.persistence.pojo.QuestionPattern;
import org.hibernate.HibernateException;

/**
 * Question Dao.
 * @author Picado, Juan Carlos juan@encuestame.org
 * @since June 02, 2009
 * @version $Id$
 */
public class QuestionDaoImp extends AbstractHibernateDaoSupport implements IQuestionDao {

    /**
     * Create Question.
     * @param question question
     * @throws HibernateException exception
     */
    public void createQuestion(final Questions question) throws HibernateException {
        super.saveOrUpdate(question);
    }

    /**
     * Load All Questions.
     * @return List of {@link Questions}
     * @throws HibernateException exception
     */
    @SuppressWarnings("unchecked")
    public List<Questions> loadAllQuestions() throws HibernateException {
        return getHibernateTemplate().find("FROM Questions");
    }

    /**
     * Load All Questions Patron.
     * @return  List of {@link QuestionPattern}
     * @throws HibernateException exception
     */
    @SuppressWarnings("unchecked")
    public List<QuestionPattern> loadAllQuestionPattern()
            throws HibernateException {
        return getHibernateTemplate().find("FROM QuestionPattern");

    }

    /**
     * Load pattern info.
     * @param patronId patron id
     * @return QuestionPatron
     * @throws HibernateException exception
     */
    public QuestionPattern loadPatternInfo(final Long patronId) throws HibernateException{
        return (QuestionPattern) getHibernateTemplate().get(QuestionPattern.class, patronId);
    }
}
