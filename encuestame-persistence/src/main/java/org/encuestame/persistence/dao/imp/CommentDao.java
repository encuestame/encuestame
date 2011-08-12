/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
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
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.collections.set.ListOrderedSet;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.encuestame.persistence.dao.CommentsOperations;
import org.encuestame.persistence.domain.Comment;
import org.encuestame.persistence.domain.HashTag;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

/**
 * {@link Comment} Dao.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since August 11, 2011
 */
@Repository("commentDao")
public class CommentDao extends AbstractHibernateDaoSupport implements CommentsOperations{

	@Autowired
    public CommentDao(SessionFactory sessionFactory){
             setSessionFactory(sessionFactory);
    }

	/*
	 * (non-Javadoc)
	 * @see org.encuestame.persistence.dao.CommentsOperations#getListCommentsByKeyword(java.lang.String, java.lang.Integer, java.lang.Long[])
	 */
	@SuppressWarnings("unchecked")
	public List<Comment> getListCommentsByKeyword(
			final String keyword,
	        final Integer maxResults,
	        final Long[] excludes){
	        log.info("keyword "+keyword);
	        List<Comment> searchResult = (List) getHibernateTemplate().execute(
	                new HibernateCallback() {
	                    @SuppressWarnings("deprecation")
	                    public Object doInHibernate(org.hibernate.Session session) {
	                        List<Comment> searchResult = new ArrayList<Comment>();
	                        long start = System.currentTimeMillis();
	                        final Criteria criteria = session
	                                .createCriteria(HashTag.class);
	                        // limit results
	                        if (maxResults != null) {
	                            criteria.setMaxResults(maxResults.intValue());
	                        }
	                        if (excludes != null && excludes.length > 0) {
	                            for (int i = 0; i < excludes.length; i++) {
	                                log.debug("excluding hashtag... "+excludes[i]);
	                                criteria.add(Restrictions.ne("commentId", excludes[i]));
	                            }
	                        }
	                        searchResult = (List<Comment>) fetchMultiFieldQueryParserFullText(
	                                keyword, new String[] { "comment"},
	                                Comment.class, criteria, new SimpleAnalyzer());
	                        final List<Comment> listAllSearch = new LinkedList<Comment>();
	                        listAllSearch.addAll(searchResult);
	                        // Fetch result by phrase
	                        final List<Comment> phraseFullTestResult = (List<Comment>) fetchPhraseFullText(
	                                keyword, "comment", Comment.class,
	                                criteria, new SimpleAnalyzer());
	                        log.debug("phraseFullTestResult comment:{"
	                                + phraseFullTestResult.size());
	                        listAllSearch.addAll(phraseFullTestResult);
	                        // Fetch result by wildcard
	                        final List<Comment> wildcardFullTextResult = (List<Comment>) fetchWildcardFullText(
	                                keyword, "comment", Comment.class,
	                                criteria, new SimpleAnalyzer());
	                        log.debug("wildcardFullTextResult comment:{"
	                                + wildcardFullTextResult.size());
	                        listAllSearch.addAll(wildcardFullTextResult);
	                        // Fetch result by prefix
	                        final List<Comment> prefixQueryFullTextResuslts = (List<Comment>) fetchPrefixQueryFullText(
	                                keyword, "comment", Comment.class, criteria,
	                                new SimpleAnalyzer());
	                        log.debug("prefixQueryFullTextResuslts comment:{"
	                                + prefixQueryFullTextResuslts.size());
	                        listAllSearch.addAll(prefixQueryFullTextResuslts);
	                        // Fetch fuzzy results
	                        final List<Comment> fuzzyQueryFullTextResults = (List<Comment>) fetchFuzzyQueryFullText(
	                                keyword, "comment", Comment.class, criteria,
	                                new SimpleAnalyzer(), SIMILARITY_VALUE);
	                        log.debug("fuzzyQueryFullTextResults comment: {"
	                                + fuzzyQueryFullTextResults.size());
	                        listAllSearch.addAll(fuzzyQueryFullTextResults);

	                        log.debug("listAllSearch size:{" + listAllSearch.size());

	                        // removing duplcates
	                        final ListOrderedSet totalResultsWithoutDuplicates = ListOrderedSet
	                                .decorate(new LinkedList());
	                        totalResultsWithoutDuplicates.addAll(listAllSearch);

	                        /*
	                         * Limit results if is enabled.
	                         */
	                        List<Comment> totalList = totalResultsWithoutDuplicates
	                                .asList();
	                        if (maxResults != null) {
	                            log.debug("split to " + maxResults
	                                    + " to list with size " + totalList.size());
	                            totalList = totalList.size() > maxResults ? totalList
	                                    .subList(0, maxResults) : totalList;
	                        }
	                        long end = System.currentTimeMillis();
	                        //log.debug("HashTag{ totalResultsWithoutDuplicates:{"
	                        //        + totalList.size() + " items with search time:"
	                        //        + (end - start) + " milliseconds");
	                        return totalList;
	                    }
	                });
	        return searchResult;
	    }
}
