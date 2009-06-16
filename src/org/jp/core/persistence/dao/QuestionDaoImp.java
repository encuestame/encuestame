package org.jp.core.persistence.dao;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.jp.core.persistence.dao.imp.IQuestionDao;
import org.jp.core.persistence.pojo.Questions;
import org.jp.core.persistence.pojo.QuestionsPatron;
import org.jp.core.persistence.pojo.SecUsers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * encuestame: system online surveys Copyright (C) 2009 encuestame Development
 * Team
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of version 3 of the GNU General Public License as published by the
 * Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * Id: QuestionDaoImp.java Date: 02/06/2009 19:44:39
 * 
 * @author juanpicado package: org.jp.core.persistence.dao
 * @version 1.0
 */

public class QuestionDaoImp extends HibernateDaoSupport implements IQuestionDao {

	private Log log = LogFactory.getLog(this.getClass());

	public QuestionDaoImp() {

	}

	/**
	 * 
	 * @param question
	 * @throws HibernateException
	 */
	public void createQuestion(Questions question) throws HibernateException {
		getHibernateTemplate().save(question);
	}

	/**
	 * 
	 * @return
	 * @throws HibernateException
	 */
	public List<Questions> loadAllQuestions() throws HibernateException {
		return getHibernateTemplate().find("from Questions");
	}

	/**
	 * load question patron
	 * 
	 * @return
	 * @throws HibernateException
	 */
	public List<QuestionsPatron> loadAllQuestionPattern()
			throws HibernateException {
		return getHibernateTemplate().find("from QuestionsPatron");
	}

	/**
	 * load patter info	
	 * @param id
	 * @return
	 */
	public QuestionsPatron loadPatternInfo(Integer id) throws HibernateException{
		QuestionsPatron pattern = (QuestionsPatron) getSession().load(QuestionsPatron.class, id);
			//getHibernateTemplate()
			//	.findByNamedQuery("Pattern.loadPatternInfo", id);
		// obtiene el primer elemento
		if (pattern !=null) {
			return  pattern;
		} else {
			throw new  HibernateException("pattert not found");
		}
	}

}
