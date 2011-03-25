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
package org.encuestame.persistence.dao.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.encuestame.persistence.domain.question.Question;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 * Abstract Base Dao Class extend Spring class {@link HibernateDaoSupport}
 * @author Picado, Juan juan@encuestame.org
 * @since October 30, 2009
 * @version $Id$
 */
public abstract class AbstractHibernateDaoSupport extends HibernateDaoSupport {

     protected Log log = LogFactory.getLog(this.getClass());

     protected Session session = null;

     /**
      * Default lucene version.
      */
     protected Version version = Version.LUCENE_30;

     /**
      * Save or Create entity.
      * @param obj obj
      * @throws HibernateException hibernate
      */
     //@Transactional(readOnly = false)
     public void saveOrUpdate(final Object obj) throws HibernateException {
         getHibernateTemplate().saveOrUpdate(obj);
         getSession().flush();
     }

     /**
      * Delete object.
      * @param obj domain
      * @throws HibernateException exception
      */
     public void delete(Object obj) throws HibernateException {
          getHibernateTemplate().delete(obj);
          getSession().flush();
     }


     /**
      * Filter By Max or Start Items.
      * @param criteria {@link DetachedCriteria}
      * @param maxResults max results
      * @param start start.
      * @return
      */
     public List<?> filterByMaxorStart(final DetachedCriteria criteria, final Integer maxResults,
             final Integer start){
          List<?> results = new ArrayList();
          if(maxResults != null && start != null){
              results = getHibernateTemplate().findByCriteria(criteria, start, maxResults);
          } else {
              results = getHibernateTemplate().findByCriteria(criteria);
          }
          return results;
     }

    /**
     * Return current midnight date.
     * @return midnight date
     */
    public Date getCurrentMidnightDate(){
        DateTime midNight = new DateTime();
        midNight = midNight.plusDays(1);
        final DateMidnight midnightDate  = midNight.toDateMidnight();
        return midnightDate.toDate();
    }

    /**
     * Fetch by multi query parser full text.
     * @param keyword keyword to search
     * @param fields list of fields.
     * @param clazz class referece
     * @param criteria {@link Criteria} reference.
     * @param analyzer {@link Analyzer} reference.
     * @return list of results.
     */
    public List<?> fetchMultiFieldQueryParserFullText(
            final String keyword,
            final String[] fields,
            final Class<?> clazz, final Criteria criteria,
            final Analyzer analyzer) {
        final MultiFieldQueryParser parser = new MultiFieldQueryParser(
                version, fields, analyzer);
        Query query = null;
        try {
            query = parser.parse(keyword);
        } catch (ParseException e) {
            log.error("Error on Parse Query " + e);
        }
        return fetchFullTextSession(clazz, criteria, analyzer, query);
    }


    /**
     * Querying for an exact match on one phrase.
     * @param keyword keyword
     * @param field field to search.
     * @param clazz class reference
     * @param criteria criteria referece
     * @param analyzer {@link Analyzer}.
     * @return
     */
    public List<?> fetchPhraseFullText(
            final String keyword,
            final String field,
            final Class<?> clazz, final Criteria criteria,
            final Analyzer analyzer) {
        final StringTokenizer st = new StringTokenizer(keyword, " ");
        final PhraseQuery query = new PhraseQuery();
        while (st.hasMoreTokens()) {
                query.add(new Term(field, st.nextToken()));
        }
        return fetchFullTextSession(clazz, criteria, analyzer, query);
    }

    /**
     * Abstract fetch full text session.
     * @param clazz class to search.
     * @param criteria {@link Criteria}
     * @param fields fields to fetch
     * @param analyzer {@link Analyzer} reference
     * @param queryOperation {@link Query} reference
     * @return list of results.
     */
    public final List<?> fetchFullTextSession(
            final Class<?> clazz,
            final Criteria criteria,
            final Analyzer analyzer,
            final Query  queryOperation) {
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<?> searchResult = (List<?>) getHibernateTemplate()
                .execute(new HibernateCallback() {
                    public Object doInHibernate(org.hibernate.Session session) {
                        final FullTextSession fullTextSession = Search
                                .getFullTextSession(session);
                        log.debug("fetchFullTextSession query " + queryOperation.toString());
                        final FullTextQuery hibernateQuery = fullTextSession
                                .createFullTextQuery(queryOperation, clazz);
                        hibernateQuery.setCriteriaQuery(criteria);
                        final List<?> result = hibernateQuery.list();
                        log.info("fetchFullTextSession result size:{" + result.size()+"}");
                        return result;
                    }
                });
        return searchResult;
    }
}
