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
package org.encuestame.persistence.dao.imp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.util.Version;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.enums.SearchPeriods;
import org.hibernate.Criteria;
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

/**
 * Abstract Base Dao Class extend Spring class {@link HibernateDaoSupport}
 * @author Picado, Juan juanATencuestame.org
 * @since October 30, 2009
 * @version $Id$
 */
public abstract class AbstractHibernateDaoSupport extends HibernateDaoSupport {

     protected Log log = LogFactory.getLog(this.getClass());

     /**
      * Session.
      */
     private Session session = null;

     /**
      * Default lucene version.
      */
     private Version version = Version.LUCENE_30;

     /**
      * Default value to similarity searchs.
      */
     protected final Float SIMILARITY_VALUE = 0.4f;

     /**
      * Save or Create entity.
      * @param obj obj
      * @throws HibernateException hibernate
      */
     //@Transactional(readOnly = false)
     public void saveOrUpdate(final Object obj) {
         getHibernateTemplate().saveOrUpdate(obj);
         getSession().flush();
     }

     /**
      *
      * @param obj
      * @throws HibernateException
      */
     public void merge(final Object obj) {
         getHibernateTemplate().merge(obj);
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
     public List<?> filterByMaxorStart(final DetachedCriteria criteria,
    		 final Integer maxResults,
             final Integer start) {          
          //if (maxResults == 0) {
        	//  log.warn("Max Results === 0 ??");          
          //}
          @SuppressWarnings("rawtypes")
          List<?> results = new ArrayList();
          if (maxResults != null && start != null) {
              results = getHibernateTemplate().findByCriteria(criteria, start, maxResults);
          } else {
              results = getHibernateTemplate().findByCriteria(criteria);
          }
          return results;
     }

    /**
     * Return current midnight date +1.
     * @return midnight date
     */
    public Date getNextDayMidnightDate(){
       //TODO: update references with DateUtil.
       return DateUtil.getNextDayMidnightDate();
    }

    /**
     * Return the current date midnight.
     * @return
     */
    //TODO: move to DateUtils
    public Date getCurrentdMidnightDate() {
        final DateMidnight midnightDate  = new DateTime().toDateMidnight();
        return midnightDate.toDate();
    }

    /**
     * Return the current date.
     * @return
     */
    //TODO: move to DateUtils
    public Date getCurrentdDateTime(){
        DateTime currentDate = new DateTime();
        return currentDate.toDate();
    }

    /**
     * Get comment time range.
     * @param range
     * @return
     */
    //TODO: move to DateUtils
    public Date getCommentTimeRange(final Integer range) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -range);
        return cal.getTime();
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
            final Class<?> clazz,
            final Criteria criteria,
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
        log.debug("fetchPhraseFullText Query :{"+query.toString()+"}");
        return fetchFullTextSession(clazz, criteria, analyzer, query);
    }

    /**
     * Fetch full text session by regular expresion.
     * @param regExp regular expresion.
     * @param field field to search.
     * @param clazz class reference
     * @param criteria criteria referece
     * @param analyzer {@link Analyzer}.
     * @return list of results.
     */
    public List<?> fetchWildcardFullText(
            final String regExp,
            final String field,
            final Class<?> clazz, final Criteria criteria,
            final Analyzer analyzer) {
        final WildcardQuery query = new WildcardQuery(new Term(field,
                regExp));
        log.debug("fetchWildcardFullText Query :{"+query.toString()+"}");
        return fetchFullTextSession(clazz, criteria, analyzer, query);
    }

    /**
     * Fetch by prefix as full text search.
     * @param keyword keyword
     * @param field field to search.
     * @param clazz class reference
     * @param criteria criteria referece
     * @param analyzer {@link Analyzer}.
     * @return list of results.
     */
    public List<?> fetchPrefixQueryFullText(
            final String keyword,
            final String field,
            final Class<?> clazz, final Criteria criteria,
            final Analyzer analyzer) {
        final PrefixQuery query = new PrefixQuery(new Term(field,
                keyword));
        log.debug("fetchPrefixQueryFullText Query :{"+query.toString()+"}");
        return fetchFullTextSession(clazz, criteria, analyzer, query);
    }

    /**
     * Fetch fuzzy keywords as full text search.
     * @param keyword keyword
     * @param field field to search.
     * @param clazz class reference
     * @param criteria criteria referece
     * @param analyzer {@link Analyzer}.
     * @param similarity similarity factor
     * @return list of results.
     */
    public List<?> fetchFuzzyQueryFullText(
            final String keyword,
            final String field,
            final Class<?> clazz,
            final Criteria criteria,
            final Analyzer analyzer,
            final Float similarity) {
        final FuzzyQuery query = new FuzzyQuery(new Term(field,
                keyword), similarity);
        log.debug("fetchPrefixQueryFullText Query :{"+query.toString()+"}");
        return fetchFullTextSession(clazz, criteria, analyzer, query);
    }

    /**
     * Abstract fetch full text session.
     * @param clazz class to search.
     * @param criteria {@link Criteria}
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
                        log.debug("fetchFullTextSession query:{" + queryOperation.toString()+"}");
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
    
    /**
     * Set a Criteria between date calculating start and end date.
     * @param searchPeriods {@link SearchPeriods}.
     * @param criteria DetachedCriteria
     */
    protected void calculateSearchPeriodsDates(
    		final SearchPeriods searchPeriods, 
    		final DetachedCriteria criteria,
    		final String dateProperty){
        if (searchPeriods != null) {
            final DateTime endDateTime = new DateTime();                      
            final DateTime startDateTime =  endDateTime.minusDays(searchPeriods.toDays());             
             if (endDateTime.isAfter(startDateTime)) {
            	 criteria.add(Restrictions.between(dateProperty, startDateTime.toDate(), endDateTime.toDate()));
             }             
         }
    }

    /**
     * @return the version
     */
    public Version getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Version version) {
        this.version = version;
    }
}
