package org.encuestame.persistence.dao.search;

import org.apache.commons.collections.set.ListOrderedSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by jpicado on 30/01/16.
 * Generate and build a query with hibernate search.
 */
public class QueryBuilder<T> {


    private static Log log = LogFactory.getLog(QueryBuilder.class);

    /**
     *
     */
    SessionFactory sessionFactory;

    /**
     *
     * @param sessionFactory
     */
    public QueryBuilder(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Fetch by multi query parser full text.
     * @param keyword keyword to search
     * @param fields list of fields.
     * @param analyzer {@link Analyzer} reference.
     * @return list of results.
     */
    private Query fetchMultiFieldQueryParserFullText(
            final String keyword,
            final String[] fields,
            final Analyzer analyzer) {

        final MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, analyzer);
        Query query = null;
        try {
            query = parser.parse(keyword);
        } catch (ParseException e) {
            log.error("Error on Parse Query " + e);
        }
        return query;
    }


    /**
     * Querying for an exact match on one phrase.
     * @param keyword keyword
     * @param field field to search.
     * @return
     */
    private PhraseQuery fetchPhraseFullText(
            final String keyword,
            final String field) {
        final StringTokenizer st = new StringTokenizer(keyword, " ");
        final PhraseQuery query = new PhraseQuery();
        while (st.hasMoreTokens()) {
            query.add(new Term(field, st.nextToken()));
        }
        log.trace("fetchPhraseFullText Query :{"+query.toString()+"}");
        return query;
    }

    /**
     * Fetch full text session by regular expresion.
     * @param regExp regular expresion.
     * @param field field to search.
     * @return list of results.
     */
    private WildcardQuery fetchWildcardFullText(
            final String regExp,
            final String field) {
        final WildcardQuery query = new WildcardQuery(new Term(field,
                regExp));
        log.trace("fetchWildcardFullText Query :{"+query.toString()+"}");
        return query;
    }

    /**
     * Fetch by prefix as full text search.
     * @param keyword keyword
     * @param field field to search.
     * @return list of results.
     */
    private PrefixQuery fetchPrefixQueryFullText(
            final String keyword,
            final String field) {
        final PrefixQuery query = new PrefixQuery(new Term(field,
                keyword));
        log.trace("fetchPrefixQueryFullText Query :{"+query.toString()+"}");
        return query;
    }

    /**
     * Fetch fuzzy keywords as full text search.
     * @param keyword keyword
     * @param field field to search.
     * @return list of results.
     */
    private FuzzyQuery fetchFuzzyQueryFullText(
            final String keyword,
            final String field) {
        final FuzzyQuery query = new FuzzyQuery(new Term(field, keyword));
        log.trace("fetchPrefixQueryFullText Query :{" + query.toString() + "}");
        return query;
    }

    /**
     *
     * @param clazz
     * @param criteria
     * @param queryOperation
     * @return
     */
    private final List<T> query(
            final Class<?> clazz,
            final Criteria criteria,
            final Query queryOperation) {

        final FullTextSession fullTextSession = Search.getFullTextSession(this.sessionFactory.getCurrentSession());
        log.trace("fetchFullTextSession query:{" + queryOperation.toString()+"}");
        final FullTextQuery hibernateQuery = fullTextSession.createFullTextQuery(queryOperation, clazz);
        hibernateQuery.setCriteriaQuery(criteria);
        final List<T> result = hibernateQuery.list();
        log.info("fetchFullTextSession result size:{" + result.size()+"}");
        return result;
    }

    /**
     * Build the query.
     * @param criteria
     * @param keyword
     * @param maxResults
     * @param startOn
     * @param multipleFields
     * @param mainField
     * @param clazz
     * @return
     */
    public List<T> build(
            final Criteria criteria,
            final String keyword,
            final Integer maxResults,
            final Integer startOn,
            final String[] multipleFields,
            final String mainField,
            final Class<T> clazz) {
        long start = System.currentTimeMillis();
        final List<T> listAllSearch = new LinkedList<T>();

        // limit results
        if (maxResults != null) {
            criteria.setMaxResults(maxResults.intValue());
        }
        // start on page x
        if (startOn != null) {
            criteria.setFirstResult(startOn.intValue());
        }

        listAllSearch.addAll(query(clazz, criteria, fetchMultiFieldQueryParserFullText(keyword, multipleFields, Analyzers.standardAnalyzer())));
        listAllSearch.addAll(query(clazz, criteria, fetchPhraseFullText(keyword, mainField)));
        listAllSearch.addAll(query(clazz, criteria, fetchWildcardFullText(keyword, mainField)));
        listAllSearch.addAll(query(clazz, criteria, fetchPrefixQueryFullText(keyword, mainField)));
        listAllSearch.addAll(query(clazz, criteria, fetchFuzzyQueryFullText(keyword, mainField)));
        listAllSearch.addAll(query(clazz, criteria, fetchMultiFieldQueryParserFullText(keyword, multipleFields,  Analyzers.standardAnalyzer())));


        // removing duplicates

        final ListOrderedSet totalResultsWithoutDuplicates = ListOrderedSet.decorate(new LinkedList());
        totalResultsWithoutDuplicates.addAll(listAllSearch);
        List<T> totalList = totalResultsWithoutDuplicates.asList();
        if (maxResults != null && startOn != null) {
            if (log.isInfoEnabled()) {
                log.info("Query build non-filter size:{" + listAllSearch.size() + "}");
                log.info("split to " + maxResults
                        + " starting on " + startOn
                        + " to list with size " + totalList.size());
            }
            totalList = totalList.size() > maxResults ? totalList
                    .subList(startOn, maxResults) : totalList;
        }

        if (log.isInfoEnabled()) {
            long end = System.currentTimeMillis();
            long queryDuration = end - start;
            log.info("query time:{" + queryDuration + "}");
            log.info("query items:{" + totalList.size() + "}");
        }

        return totalList;
    }
}
