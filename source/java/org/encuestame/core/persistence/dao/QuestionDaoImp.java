/**
 * encuestame:  system online surveys
 * Copyright (C) 2005-2008 encuestame Development Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of version 3 of the GNU General Public
 * License as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
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
        return getHibernateTemplate().find("from Questions");
    }

    /**
     * Load All Questions Patron.
     * @return  List of {@link QuestionPattern}
     * @throws HibernateException exception
     */
    @SuppressWarnings("unchecked")
    public List<QuestionPattern> loadAllQuestionPattern()
            throws HibernateException {
        return getHibernateTemplate().find("from QuestionsPatron");
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
