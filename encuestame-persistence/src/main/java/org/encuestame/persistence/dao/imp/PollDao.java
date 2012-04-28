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
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.set.ListOrderedSet;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.encuestame.persistence.dao.IPoll;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.encuestame.persistence.domain.survey.PollResult;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.RestFullUtil;
import org.encuestame.utils.enums.TypeSearchResult;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

/**
 * Poll Dao.
 * @author Morales,Diana Paola paolaATencuestame.org
 * @since March 15, 2009
 */
@Repository("pollDao")
public class PollDao extends AbstractHibernateDaoSupport implements IPoll {

    /**
     * Constructor.
     * @param sessionFactory
     */
    @Autowired
    public PollDao(SessionFactory sessionFactory) {
             setSessionFactory(sessionFactory);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#getPollFolderBySecUser(org.encuestame.persistence.domain.security.UserAccount)
     */
    @SuppressWarnings("unchecked")
    public List<PollFolder> getPollFolderByUserAccount(final UserAccount userAccount){
          final DetachedCriteria criteria = DetachedCriteria.forClass(PollFolder.class);
          criteria.add(Restrictions.eq("createdBy", userAccount));
          return getHibernateTemplate().findByCriteria(criteria);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#getPollsByPollFolder(org.encuestame.persistence.domain.security.UserAccount, org.encuestame.persistence.domain.survey.PollFolder)
     */
    @SuppressWarnings("unchecked")
    public List<Poll> getPollsByPollFolder(final UserAccount userAcc, final PollFolder folder){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
        criteria.add(Restrictions.eq("editorOwner", userAcc));
        criteria.add(Restrictions.eq("pollFolder", folder));
        return getHibernateTemplate().findByCriteria(criteria);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#getPollsByPollFolderId(org.encuestame.persistence.domain.security.UserAccount, org.encuestame.persistence.domain.survey.PollFolder)
     */
    @SuppressWarnings("unchecked")
    public List<Poll> getPollsByPollFolderId(final UserAccount userId, final PollFolder folder){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
        criteria.createAlias("editorOwner", "editorOwner");
        //criteria.add(Restrictions.eq("editorOwner.uid", userId));
        criteria.add(Restrictions.eq("pollFolder", folder));
        return getHibernateTemplate().findByCriteria(criteria);
    }

     /*
      * (non-Javadoc)
      * @see org.encuestame.persistence.dao.IPoll#findAllPollByUserId(org.encuestame.persistence.domain.security.UserAccount, java.lang.Integer, java.lang.Integer)
      */
    @SuppressWarnings("unchecked")
    public List<Poll> findAllPollByEditorOwner(
            final UserAccount userAcc,
            final Integer maxResults,
            final Integer start) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
        criteria.add(Restrictions.eq("editorOwner", userAcc));
        criteria.add(Restrictions.eq("publish", Boolean.TRUE));
        criteria.addOrder(Order.desc("createdAt"));
        return (List<Poll>) filterByMaxorStart(criteria, maxResults, start);
    }

    /**
     * Retrieve poll by {@link Account}.
     * @param userAcc
     * @param maxResults
     * @param start
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Poll> findAllPollByAccount(
            final Account account,
            final Integer maxResults,
            final Integer start){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
        criteria.add(Restrictions.eq("accountItem", account));
        criteria.add(Restrictions.eq("publish", Boolean.TRUE));
        criteria.addOrder(Order.desc("createdAt"));
        return (List<Poll>) filterByMaxorStart(criteria, maxResults, start);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#getPollByHashTagId(java.lang.Long, java.lang.Integer, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<Poll> getPollByHashTagName(final String tagName, final Integer startResults,
            final Integer limitResults, final TypeSearchResult filterby) {
        final DetachedCriteria detached = DetachedCriteria
                .forClass(Poll.class)
                .createAlias("hashTags", "hashTags")
                .setProjection(Projections.id())
                .add(Subqueries.propertyIn(
                        "hashTags.hashTagId",
                        DetachedCriteria
                                .forClass(HashTag.class, "hash")
                                .setProjection(Projections.id())
                                .add(Restrictions.in("hash.hashTag",
                                        new String[] { tagName }))));
        final DetachedCriteria criteria = DetachedCriteria.forClass(
                Poll.class, "poll");
        criteria.add(Subqueries.propertyIn("poll.pollId", detached));
        if (filterby.equals(TypeSearchResult.HASHTAG)) {
            criteria.addOrder(Order.desc("poll.createdAt"));
        } else if (filterby.equals(TypeSearchResult.HASHTAGRATED)) {
              criteria.addOrder(Order.desc("numbervotes"));
        }
        return getHibernateTemplate().findByCriteria(criteria, startResults, limitResults);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#getPollsbyHashTagNameAndDateRange(java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<Poll> getPollsbyHashTagNameAndDateRange(
            final String tagName, final Integer period,
            final Integer startResults, final Integer limit) {
        Date startDate = null;
        Date endDate = null;
        if (period != null) {
            final Calendar hi = Calendar.getInstance();
            hi.add(Calendar.DAY_OF_YEAR, -period);
            startDate = hi.getTime();
            endDate = Calendar.getInstance().getTime();

        }
        final DetachedCriteria detached = DetachedCriteria
                .forClass(Poll.class)
                .createAlias("hashTags", "hashTags")
                .setProjection(Projections.id())
                .add(Subqueries.propertyIn(
                        "hashTags.hashTagId",
                        DetachedCriteria
                                .forClass(HashTag.class, "hash")
                                .setProjection(Projections.id())
                                .add(Restrictions.in("hash.hashTag",
                                        new String[] { tagName }))));
        final DetachedCriteria criteria = DetachedCriteria.forClass(
                Poll.class, "poll");
        criteria.add(Subqueries.propertyIn("poll.pollId", detached));
        criteria.addOrder(Order.desc("poll.createdAt"));
        criteria.add(Restrictions.between("createdAt", startDate, endDate));
        criteria.add(Restrictions.eq("publish", Boolean.TRUE));
        return getHibernateTemplate().findByCriteria(criteria, startResults,
                limit);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#getPollById(java.lang.Long)
     */
    public Poll getPollById(final Long pollId) {
        return (Poll) getHibernateTemplate().get(Poll.class, pollId);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#getPollById(java.lang.Long, java.lang.String, boolean)
     */
    @SuppressWarnings("unchecked")
    public Poll getPollById(final Long pollId, final String slugQuestion, final boolean encode) {
        final DetachedCriteria detached = DetachedCriteria
        .forClass(Poll.class)
        .createAlias("question", "question");
        detached.add(Restrictions.eq("pollId", pollId));
        final String encodeString = encode ? RestFullUtil.encodeUTF8(slugQuestion) : slugQuestion;
        detached.add(Restrictions.eq("question.slugQuestion", encodeString));
        return (Poll) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(detached));
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#getPollFolderById(java.lang.Long)
     */
    public PollFolder getPollFolderById(final Long folderId) {
        return getHibernateTemplate().get(PollFolder.class, folderId);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#retrieveResultPolls(java.lang.Long, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> retrieveResultPolls(final Long pollId,
            final Long answerId) {
        final String pollResultsCounter = "select answer.questionAnswerId, answer.answer, answer.color,"
                + "count(poll.pollId) FROM PollResult "
                + "where poll.pollId= :pollId "
                + "group by answer.answer, answer.questionAnswerId, answer.color";
        return new ArrayList<Object[]>(getSession().createQuery(pollResultsCounter).setParameter("pollId", pollId)
                .list());
    }

     /*
      * (non-Javadoc)
      * @see org.encuestame.persistence.dao.IPoll#getPollsByQuestionKeyword(java.lang.String, org.encuestame.persistence.domain.security.UserAccount, java.lang.Integer, java.lang.Integer)
      */
    @SuppressWarnings("unchecked")
    public List<Poll> getPollsByQuestionKeyword(
            final String keyword, final UserAccount userAcc,
            final Integer maxResults,
            final Integer startOn){
            log.debug("keyword "+keyword);
            log.debug("userId " + userAcc);
            log.debug("fields " + new String[]{"question"});
            @SuppressWarnings("rawtypes")
            final List<Poll> searchResult = (List<Poll>) getHibernateTemplate().execute(new HibernateCallback() {
                public Object doInHibernate(org.hibernate.Session session) {
                    List<Question> searchResult = new ArrayList<Question>();
                    long start = System.currentTimeMillis();
                    final Criteria criteria = session.createCriteria(Poll.class);
                    //filter by account.
                    criteria.add(Restrictions.eq("editorOwner", userAcc));
                    //limit results
                    if (maxResults != null) {
                        criteria.setMaxResults(maxResults.intValue());
                    }
                    //start on page x
                    if (startOn != null) {
                        criteria.setFirstResult(startOn.intValue());
                    }
                    final String defaultField = "question.question";
                        final String[] fields = new String[] { defaultField };
                        searchResult = (List<Question>) fetchMultiFieldQueryParserFullText(
                                keyword, fields, Poll.class, criteria,
                                new SimpleAnalyzer());
                        final List listAllSearch = new LinkedList();
                        listAllSearch.addAll(searchResult);
                        // Fetch result by phrase
                        final List<Question> phraseFullTestResult = (List<Question>) fetchPhraseFullText(
                                keyword, defaultField, Poll.class, criteria,
                                new SimpleAnalyzer());
                        log.debug("phraseFullTestResult:{"
                                + phraseFullTestResult.size());
                        listAllSearch.addAll(phraseFullTestResult);
                        // Fetch result by wildcard
                        final List<Poll> wildcardFullTextResult = (List<Poll>) fetchWildcardFullText(
                                keyword, defaultField, Poll.class, criteria,
                                new SimpleAnalyzer());
                        log.debug("wildcardFullTextResult:{"
                                + wildcardFullTextResult.size());
                        listAllSearch.addAll(wildcardFullTextResult);
                        // Fetch result by prefix
                        final List<Poll> prefixQueryFullTextResuslts = (List<Poll>) fetchPrefixQueryFullText(
                                keyword, defaultField, Poll.class, criteria,
                                new SimpleAnalyzer());
                        log.debug("prefixQueryFullTextResuslts:{"
                                + prefixQueryFullTextResuslts.size());
                        listAllSearch.addAll(prefixQueryFullTextResuslts);
                        // Fetch fuzzy results
                        final List<Poll> fuzzyQueryFullTextResults = (List<Poll>) fetchFuzzyQueryFullText(
                                keyword, defaultField, Poll.class, criteria,
                                new SimpleAnalyzer(), SIMILARITY_VALUE);
                        log.debug("fuzzyQueryFullTextResults: {"
                                + fuzzyQueryFullTextResults.size());
                        listAllSearch.addAll(fuzzyQueryFullTextResults);
                        log.debug("listAllSearch size:{" + listAllSearch.size());
//                        // removing duplcates
                        final ListOrderedSet totalResultsWithoutDuplicates = ListOrderedSet
                                .decorate(new LinkedList());
                        totalResultsWithoutDuplicates.addAll(listAllSearch);
//                        /*
//                         * Limit results if is enabled.
//                         */
                        List<Poll> totalList = totalResultsWithoutDuplicates
                                .asList();
                        if (maxResults != null && startOn != null) {
                            log.debug("split to " + maxResults
                                    + " starting on " + startOn
                                    + " to list with size " + totalList.size());
                            totalList = totalList.size() > maxResults ? totalList
                                    .subList(startOn, maxResults) : totalList;
                        }
                        long end = System.currentTimeMillis();
                        log.debug("Poll{ totalResultsWithoutDuplicates:{"
                                + totalList.size() + " items with search time:"
                                + (end - start) + " milliseconds");
                        return totalList;
                    }
                });
        return (List<Poll>) searchResult;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#getPollByIdandUserId(java.lang.Long, org.encuestame.persistence.domain.security.UserAccount)
     */
    @SuppressWarnings("unchecked")
    public Poll getPollById(final Long pollId, UserAccount userAcc) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
        criteria.add(Restrictions.eq("editorOwner", userAcc));
        criteria.add(Restrictions.eq("pollId", pollId));
        return (Poll) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#getPollFolderByIdandUser(java.lang.Long, org.encuestame.persistence.domain.security.UserAccount)
     */
    @SuppressWarnings("unchecked")
    public PollFolder getPollFolderByIdandUser(final Long pollFolderId, final UserAccount userAcc){
        final DetachedCriteria criteria = DetachedCriteria.forClass(PollFolder.class);
        criteria.add(Restrictions.eq("createdBy", userAcc));
        criteria.add(Restrictions.eq("id", pollFolderId));
        return (PollFolder) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#getPollByUserIdDate(java.util.Date, org.encuestame.persistence.domain.security.UserAccount, java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<Poll> getPollByUserIdDate(
    		final Date date, 
    		final UserAccount userAcc,
            final Integer maxResults, 
            final Integer start ){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
        criteria.add(Restrictions.eq("editorOwner", userAcc));
        if ( date != null) {
        	criteria.add(Restrictions.between("createdAt", date, getNextDayMidnightDate()));
        }
        return (List<Poll>) filterByMaxorStart(criteria, maxResults, start);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#retrievePollsByUserId(org.encuestame.persistence.domain.security.UserAccount, java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<Poll> retrievePollsByUserId(final UserAccount userAcc,
            final Integer maxResults,
            final Integer start){
         final DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
         criteria.add(Restrictions.eq("publish", Boolean.TRUE));
         criteria.add(Restrictions.eq("editorOwner", userAcc));
         criteria.addOrder(Order.desc("createdAt"));
         return (List<Poll>) filterByMaxorStart(criteria, maxResults, start);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#getMaxPollLikeVotesbyUser(java.lang.Long, java.util.Date, java.util.Date)
     */
    public Long getMaxPollLikeVotesbyUser(final Long userId, final Date dateFrom, final Date dateTo) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
        criteria.setProjection(Projections.max("likeVote"));
        criteria.createAlias("editorOwner", "editorOwner");
        criteria.add(Restrictions.eq("editorOwner.uid", userId));
        criteria.add(Restrictions.between("createdAt", dateFrom, dateTo));
        @SuppressWarnings("unchecked")
        List<Long> results = getHibernateTemplate().findByCriteria(criteria);
        return (Long) (results.get(0) == null ? 0 : results.get(0));
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#getPolls(java.lang.Integer, java.lang.Integer, java.util.Date)
     */
    @SuppressWarnings("unchecked")
    public List<Poll> getPolls(
            final Integer maxResults,
            final Integer start,
            final Date range) {
        final DetachedCriteria criteria = DetachedCriteria
                .forClass(Poll.class);
        criteria.add(Restrictions.eq("publish", Boolean.TRUE));
        if (range != null) {
            criteria.add(Restrictions.gt("createdAt", range));
        }
        criteria.addOrder(Order.desc("createdAt"));
        return (List<Poll>) filterByMaxorStart(criteria, maxResults, start);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#retrieveFavouritesPoll(java.lang.Long, java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<Poll> retrieveFavouritesPoll(
            final UserAccount userAccount,
            final Integer maxResults,
            final Integer start) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
        criteria.createAlias("editorOwner","editorOwner");
        criteria.add(Restrictions.eq("favorites", Boolean.TRUE));
        criteria.add(Restrictions.eq("editorOwner", userAccount));
        return (List<Poll>) filterByMaxorStart(criteria, maxResults, start);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#retrievePollToday(java.lang.Long, java.lang.Integer, java.lang.Integer)
     */
    public List<Poll> retrievePollToday(
            final Account owner,
            final Integer maxResults,
            final Integer start,
            final Date startDate){
        return retrievePollByDate(owner, startDate, DateUtil.decreaseDateADay(startDate), maxResults, start);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#retrievePollByDate(java.lang.Long, java.util.Date, java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<Poll> retrievePollByDate(
            final Account owner,
            final Date initDate,
            final Date endDate,
            final Integer maxResults,
            final Integer start){
         final DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
         criteria.add(Restrictions.between("createdAt", initDate, endDate));
         criteria.add(Restrictions.eq("owner", owner));
         return (List<Poll>) filterByMaxorStart(criteria, maxResults, start);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#retrievePollLastWeek(java.lang.Long, java.lang.Integer, java.lang.Integer)
     */
    public List<Poll> retrievePollLastWeek(
            final Account owner,
            final Integer maxResults,
            final Integer start,
            final Date startDate) {
        return retrievePollByDate(owner, startDate, DateUtil.decreaseDateAsWeek(startDate), maxResults, start);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#getTotalPollsbyUser(org.encuestame.persistence.domain.security.UserAccount, java.lang.Boolean)
     */
    public final Long getTotalPollsbyUser(final UserAccount user,
            final Boolean publishStatus) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
        criteria.setProjection(Projections.rowCount());
        criteria.add(Restrictions.eq("editorOwner", user));
        criteria.add(Restrictions.eq("publish", publishStatus));
        @SuppressWarnings("unchecked")
        List<Long> results = getHibernateTemplate().findByCriteria(criteria);
        log.debug("Retrieve total polls by  " + user.getUsername() + "--->"
                + results.size());
        return (Long) (results.get(0) == null ? 0 : results.get(0));
    }
    
    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#validateVoteIP(java.lang.String, org.encuestame.persistence.domain.survey.Poll)
     */
    @SuppressWarnings("unchecked")
    public PollResult validateVoteIP(
    		final String ip,
            final Poll poll) {
        return (PollResult) DataAccessUtils
                .uniqueResult(getHibernateTemplate()
                        .findByNamedParam(
                                "from PollResult where ipAddress= :ipAddress and  poll = :poll",
                                new String[] { "ipAddress", "poll" },
                                new Object[] { ip, poll }));
    }
}