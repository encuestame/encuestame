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

import org.apache.commons.collections.ListUtils;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.encuestame.persistence.dao.IFrontEndDao;
import org.encuestame.persistence.dao.IHashTagDao;
import org.encuestame.persistence.dao.IPoll;
import org.encuestame.persistence.dao.ITweetPoll;
import org.encuestame.persistence.domain.AccessRate;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.Hit;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.enums.HitCategory;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.Status;
import org.encuestame.utils.enums.TypeSearchResult;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

/**
 * Front End Dao.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 16, 2010 10:53:26 PM
 */
@Repository("frontEndDao")
public class FrontEndDao extends AbstractHibernateDaoSupport implements IFrontEndDao{

    /** {@link HashTagDao}. **/
    @Autowired
    private IHashTagDao hashTagDao;
    /** {@link TweetPollDao}. **/
    @Autowired
    private ITweetPoll tweetPoll;
    /** {@link PollDao}. **/
    @Autowired
    private IPoll poll;

    /** Represent All Items in the time. **/
    private final Integer WITHOUT_FIRST_RESULTS = -1;


    /**
     * Constructor.
     * @param sessionFactory {@link SessionFactory}.
     */
    @Autowired
    public FrontEndDao(SessionFactory sessionFactory) {
           setSessionFactory(sessionFactory);
    }

    /**
     * Get TweetPoll Front End.
     * @param period date period
     * @param firstResult first results
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    @SuppressWarnings("unchecked")
    public final List<TweetPoll> getTweetPollFrontEnd(
            final SearchPeriods period,
            final Integer start,
            final Integer maxResults,
            final Integer firstResult) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(TweetPoll.class);
        criteria.createAlias("question", "question");
        calculateSearchPeriodsDates(period, criteria, "createDate");
        criteria.add(Restrictions.eq("publishTweetPoll", Boolean.TRUE)); //should be published
        criteria.add(Restrictions.gt("relevance", 0L));
        criteria.addOrder(Order.desc("relevance"));
        criteria.addOrder(Order.desc("createDate"));
        return (List<TweetPoll>) filterByMaxorStart(criteria, maxResults, start);
        //return getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
    }

    /**
     * Get Poll on Front End
     * @param period period
     * @param maxResults maxResults
     * @param firstResult firstResults.
     * @return list of poll.
     */
    @SuppressWarnings("unchecked")
    public final List<Poll> getPollFrontEnd(
            final SearchPeriods period,
            final Integer start,
            final Integer maxResults,
            final Integer firstResult) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
        criteria.createAlias("question", "question");
        calculateSearchPeriodsDates(period, criteria, "createDate");
        criteria.add(Restrictions.gt("relevance", 0L));
        criteria.add(Restrictions.eq("publish", Boolean.TRUE)); //should be published
        criteria.add(Restrictions.or(Restrictions.eq("isHidden", Boolean.FALSE), Restrictions.isNull("isHidden")));
        criteria.add(Restrictions.isNotNull("isPasswordProtected"));
        criteria.add(Restrictions.isNull("passProtection"));
        //criteria.add(Restrictions.isEmpty("passProtection"));
        criteria.addOrder(Order.desc("createDate"));
        criteria.addOrder(Order.desc("relevance"));
        List<Poll> polls = (List<Poll>) filterByMaxorStart(criteria, maxResults, start);
        return polls;
    }

    /*
     *
     */
    @SuppressWarnings("unchecked")
    public final List<Survey> getSurveyFrontEnd(final SearchPeriods period,
            final Integer start, final Integer maxResults,
            final Integer firstResult) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(Survey.class);
         //criteria.createAlias("question", "question");
        // TODO: Complete method, adding criteria params

        calculateSearchPeriodsDates(period, criteria, "createDate");
        criteria.add(Restrictions.gt("relevance", 0L));
        criteria.addOrder(Order.desc("relevance"));
        criteria.addOrder(Order.desc("createDate"));
        return (List<Survey>) filterByMaxorStart(criteria, maxResults, start);
    }

    /**
     * Get TweetPoll Last 24 Hours.
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    public final List<TweetPoll> getTweetPollFrontEndLast24(final Integer start, final Integer maxResults){
        return this.getTweetPollFrontEnd(SearchPeriods.TWENTYFOURHOURS, start, maxResults, this.WITHOUT_FIRST_RESULTS);
    }

    /**
     * Get TweetPoll Last 7 Days
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    public final List<TweetPoll> getTweetPollFrontEndLast7Days(final Integer start, final Integer maxResults) {
        return this.getTweetPollFrontEnd(SearchPeriods.SEVENDAYS , start, maxResults, this.WITHOUT_FIRST_RESULTS);
    }

    /**
     * Get TweetPoll Last 30 Days
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    public final List<TweetPoll> getTweetPollFrontEndLast30Days(final Integer start, final Integer maxResults){
        return this.getTweetPollFrontEnd(SearchPeriods.THIRTYDAYS , start, maxResults, this.WITHOUT_FIRST_RESULTS);
    }

    /**
     * Get TweetPoll all time.
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    public final List<TweetPoll> getTweetPollFrontEndAllTime(final Integer start, final Integer maxResults){
        return this.getTweetPollFrontEnd(SearchPeriods.ALLTIME , start, maxResults, this.WITHOUT_FIRST_RESULTS);
    }

    /**
     * Get Poll Last 24 Hours.
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    public final  List<Poll> getPollFrontEndLast24(final Integer start, final Integer maxResults){
        return this.getPollFrontEnd(SearchPeriods.TWENTYFOURHOURS, start, maxResults, this.WITHOUT_FIRST_RESULTS);
    }

    /**
     * Get Poll Last 7 Days
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    public List<Poll> getPollFrontEndLast7Days(final Integer start, final Integer maxResults){
        return this.getPollFrontEnd(SearchPeriods.SEVENDAYS ,start, maxResults, this.WITHOUT_FIRST_RESULTS);
    }

    /**
     * Get Poll Last 30 Days
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    public final List<Poll> getPollFrontEndLast30Days(final Integer start, final Integer maxResults){
        return this.getPollFrontEnd(SearchPeriods.THIRTYDAYS , start, maxResults, this.WITHOUT_FIRST_RESULTS);
    }

    /**
     * Get Poll on All Time.
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    public final List<Poll> getPollFrontEndAllTime(final Integer start, final Integer maxResults) {
        return this.getPollFrontEnd(SearchPeriods.ALLTIME , start, maxResults, this.WITHOUT_FIRST_RESULTS);
    }

    /**
     * Get TweetPoll Last 24 Hours.
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    public final List<Survey> getSurveyFrontEndLast24(final Integer start, final Integer maxResults){
        return this.getSurveyFrontEnd(SearchPeriods.TWENTYFOURHOURS, start, maxResults, this.WITHOUT_FIRST_RESULTS);
    }

    /**
     * Get TweetPoll Last 7 Days
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    public final List<Survey> getSurveyFrontEndLast7Days(final Integer start, final Integer maxResults) {
        return this.getSurveyFrontEnd(SearchPeriods.SEVENDAYS , start, maxResults, this.WITHOUT_FIRST_RESULTS);
    }

    /**
     * Get TweetPoll Last 30 Days
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    public final List<Survey> getSurveyFrontEndLast30Days(final Integer start, final Integer maxResults){
        return this.getSurveyFrontEnd(SearchPeriods.THIRTYDAYS , start, maxResults, this.WITHOUT_FIRST_RESULTS);
    }

    /**
     * Get TweetPoll all time.
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    public final List<Survey> getSurveyFrontEndAllTime(final Integer start, final Integer maxResults){
        return this.getSurveyFrontEnd(SearchPeriods.ALLTIME , start, maxResults, this.WITHOUT_FIRST_RESULTS);
    }


    /**
     * @return the hashTagDao
     */
    public IHashTagDao getHashTagDao() {
        return hashTagDao;
    }

    /**
     * @param hashTagDao the hashTagDao to set
     */
    public void setHashTagDao(final IHashTagDao hashTagDao) {
        this.hashTagDao = hashTagDao;
    }

    /**
     * @return the tweetPoll
     */
    public ITweetPoll getTweetPoll() {
        return tweetPoll;
    }

    /**
     * @param tweetPoll the tweetPoll to set
     */
    public void setTweetPoll(ITweetPoll tweetPoll) {
        this.tweetPoll = tweetPoll;
    }

    /**
     * @return the poll
     */
    public IPoll getPoll() {
        return poll;
    }

    /**
     * @param poll the poll to set
     */
    public void setPoll(IPoll poll) {
        this.poll = poll;
    }


    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IFrontEndDao#getAllHitsByType(org.encuestame.persistence.domain.tweetpoll.TweetPoll, org.encuestame.persistence.domain.survey.Poll, org.encuestame.persistence.domain.survey.Survey)
     */
    @SuppressWarnings("unchecked")
    public List getAllHitsByType(final TweetPoll tweetpoll,
            final Poll poll,
            final Survey survey) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(Hit.class);
        if (poll != null) {
            criteria.createAlias("poll", "poll");
            criteria.add(Restrictions.eq("poll", poll));
        } else if (tweetpoll != null) {
            criteria.createAlias("tweetPoll", "tweetPoll");
            criteria.add(Restrictions.eq("tweetPoll", tweetpoll));
        } else if (survey != null) {
            criteria.createAlias("survey", "survey");
            criteria.add(Restrictions.eq("survey", survey));
        }
        return getHibernateTemplate().findByCriteria(criteria);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IFrontEndDao#getHitsByIpAndType(java.lang.String, java.lang.Long, org.encuestame.persistence.domain.TypeSearchResult)
     */
    public List<Hit> getHitsByIpAndType(final String ipAddress, final Long id,
            final TypeSearchResult searchHitby) {
        log.debug("searching item hits by ipAddress ---> " + ipAddress);
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<Hit> searchResult = (List) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(org.hibernate.Session session) {
                        List<Hit> searchResult = new ArrayList<Hit>();
                        final Criteria criteria = session
                                .createCriteria(Hit.class);
                        if (searchHitby.equals(TypeSearchResult.TWEETPOLL)) {
                            criteria.createAlias("tweetPoll", "tweetPoll");
                            criteria.add(Restrictions.eq(
                                    "tweetPoll.tweetPollId", id));
                        } else if (searchHitby.equals(TypeSearchResult.POLL)) {
                            criteria.createAlias("poll", "poll");
                            criteria.add(Restrictions.eq("poll.pollId", id));
                        } else if (searchHitby.equals(TypeSearchResult.SURVEY)) {
                            criteria.createAlias("survey", "survey");
                            criteria.add(Restrictions.eq("survey.sid", id));
                        } else if (searchHitby.equals(TypeSearchResult.HASHTAG)) {
                            criteria.createAlias("hashTag", "hashTag");
                            criteria.add(Restrictions.eq("hashTag.hashTagId",
                                    id));
                        } else {
                            log.error(" Search hit result type undefined " + searchHitby);
                        }
                        //define the type of hit.
                        criteria.add(Restrictions.eq("hitCategory", HitCategory.VISIT));
                        searchResult = (List<Hit>) fetchPhraseFullText(
                                ipAddress, "ipAddress", Hit.class, criteria,
                                new SimpleAnalyzer());
                        log.debug("total hits results ---> "
                                + searchResult.size());
                        return searchResult;
                    }
                });
        return searchResult;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IFrontEndDao#getTotalHitsbyType(java.lang.Long, java.lang.String)
     */
    public final Long getTotalHitsbyType(final Long id, final TypeSearchResult searchHitby, final Integer period) {
        Date startDate = null;
        Date endDate = null;
        if (period != null) {
            final DateTime dateTime = new DateTime();
             endDate  = dateTime.toDate();
             startDate = DateUtil.minusDaysToCurrentDate(period, dateTime.toDate());
         }
        final DetachedCriteria criteria = DetachedCriteria.forClass(Hit.class);
        criteria.setProjection(Projections.rowCount());
        if (searchHitby.equals(TypeSearchResult.TWEETPOLL)) {
            criteria.createAlias("tweetPoll", "tweetPoll");
            criteria.add(Restrictions.eq("tweetPoll.tweetPollId", id));
        } else if (searchHitby.equals(TypeSearchResult.POLL)) {
            criteria.createAlias("poll", "poll");
            criteria.add(Restrictions.eq("poll.pollId", id));
        } else if (searchHitby.equals(TypeSearchResult.SURVEY)) {
            criteria.createAlias("survey", "survey");
            criteria.add(Restrictions.eq("survey.sid", id));
        } else if (searchHitby.equals(TypeSearchResult.HASHTAG)) {
            criteria.createAlias("hashTag", "hashTag");
            criteria.add(Restrictions.eq("hashTag.hashTagId", id));
        } else {
            log.error(" Search hit result type undefined " + searchHitby);
        }

        if (startDate != null && endDate != null) {
              criteria.add(Restrictions.between("hitDate", startDate, endDate));
        }

        //define as a VISIT category
        criteria.add(Restrictions.eq("hitCategory", HitCategory.VISIT));
        @SuppressWarnings("unchecked")
        List results = getHibernateTemplate().findByCriteria(criteria);
        log.debug("Retrieve total hits by  " + searchHitby + "--->"
                + results.size());
        return (Long) (results.get(0) == null ? 0 : results.get(0));
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.persistence.dao.IFrontEndDao#getHashTagHitsbyDateRange
     * (java.lang.Long, java.lang.Integer, java.lang.Integer, java.lang.Integer,
     * java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List getHashTagHitsbyDateRange(final Long tagId,
            final Integer period) {
        Date startDate = null;
        Date endDate = null;
        if (period != null) {
            final Calendar hi = Calendar.getInstance();
            hi.add(Calendar.DAY_OF_YEAR, -period);
            startDate = hi.getTime();
            endDate = Calendar.getInstance().getTime();

        }

        final DetachedCriteria criteria = DetachedCriteria.forClass(Hit.class);
        criteria.createAlias("hashTag", "hashTag");
        criteria.add(Restrictions.eq("hashTag.hashTagId", tagId));
        criteria.addOrder(Order.desc("hitDate"));
        criteria.add(Restrictions.between("hitDate", startDate, endDate));
        //define as a VISIT category
        criteria.add(Restrictions.eq("hitCategory", HitCategory.VISIT));
        return getHibernateTemplate().findByCriteria(criteria);

    }

    /**
     *
     * @param type
     * @param question
     * @return
     */
    public List getVotesByType(
            final TypeSearchResult type,
            final UserAccount userAccount,
            final Question question) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(Hit.class);
        criteria.add(Restrictions.eq("typeSearchResult", type));
        criteria.add(Restrictions.eq("question", question));
        criteria.add(Restrictions.eq("userAccount", userAccount));
        criteria.add(Restrictions.eq("hitCategory", HitCategory.VOTE));
        return getHibernateTemplate().findByCriteria(criteria);
    }

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.encuestame.persistence.dao.IFrontEndDao#getHashTagHitsRange(java.
	 * lang.Long, org.encuestame.utils.enums.SearchPeriods)
	 */
	@SuppressWarnings("unchecked")
	public List getHashTagHitsRange(final Long tagId,
			final SearchPeriods period) {

		final DetachedCriteria criteria = DetachedCriteria.forClass(Hit.class);
		criteria.createAlias("hashTag", "hashTag");
		criteria.add(Restrictions.eq("hashTag.hashTagId", tagId));
		criteria.addOrder(Order.desc("hitDate"));

		// define as a VISIT category
		criteria.add(Restrictions.eq("hitCategory", HitCategory.VISIT));

		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("hitDate"));

		projList.add(Projections.rowCount());
		criteria.setProjection(projList);

		return getHibernateTemplate().findByCriteria(criteria);

	}

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IFrontEndDao#getAccessRatebyItem(java.lang.String, java.lang.Long, java.lang.String)
     */
    public List<AccessRate> getAccessRatebyItem(final String ipAddress,
            final Long itemId, final TypeSearchResult searchbyType) {
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<AccessRate> searchResult = (List) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(org.hibernate.Session session) {
                        List<AccessRate> searchResult = new ArrayList<AccessRate>();
                        final Criteria criteria = session
                                .createCriteria(AccessRate.class);
                        if (searchbyType.equals(TypeSearchResult.TWEETPOLL)) {
                            criteria.createAlias("tweetPoll", "tweetPoll");
                            criteria.add(Restrictions.eq(
                                    "tweetPoll.tweetPollId", itemId));
                        } else if (searchbyType.equals(TypeSearchResult.SURVEY)) {
                            criteria.createAlias("survey", "survey");
                            criteria.add(Restrictions.eq("survey.sid", itemId));
                        } else if (searchbyType.equals(TypeSearchResult.POLL)) {
                            criteria.createAlias("poll", "poll");
                            criteria.add(Restrictions.eq("poll.pollId", itemId));
                        } else {
                            log.error(" Search access rate result type undefined " + searchbyType);
                        }
                        searchResult = (List<AccessRate>) fetchPhraseFullText(
                                ipAddress, "ipAddress", AccessRate.class,
                                criteria, new SimpleAnalyzer());
                        return searchResult;
                    }
                });
        return searchResult;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IFrontEndDao#getLinksByHomeItem(org.encuestame.persistence.domain.HashTag, org.encuestame.persistence.domain.security.UserAccount, org.encuestame.persistence.domain.tweetpoll.TweetPoll, org.encuestame.persistence.domain.survey.Survey, org.encuestame.persistence.domain.survey.Poll, org.encuestame.utils.enums.TypeSearchResult)
     */
    @SuppressWarnings("unchecked")
    public List getLinksByHomeItem(
            final HashTag hashTag,
            final UserAccount userAccount,
            final TweetPoll tweetPoll,
            final Survey survey,
            final Poll poll,
            final TypeSearchResult itemType,
            final SearchPeriods searchPeriods,
            final Integer start,
            final Integer max) {
        final DetachedCriteria criteria = DetachedCriteria
                .forClass(TweetPollSavedPublishedStatus.class);
        // define if is necessary execute a query, if not exist filters it's not necessary run a query
        boolean queryRequired = false;
        List tweetPollSavedPublishedStatus = ListUtils.EMPTY_LIST;
        if (itemType.equals(TypeSearchResult.TWEETPOLL)) {
            criteria.add(Restrictions.eq("tweetPoll", tweetPoll));
            queryRequired = true;
        } else if (itemType.equals(TypeSearchResult.SURVEY)) {
            criteria.add(Restrictions.eq("survey", survey));
            queryRequired = true;
        } else if (itemType.equals(TypeSearchResult.POLL)) {
            criteria.add(Restrictions.eq("poll", poll));
            queryRequired = true;
        } else if (itemType.equals(TypeSearchResult.HASHTAG)) {
            //social links by hashtag
            final List<TweetPoll> d = getTweetPoll().getTweetpollByHashTagName(hashTag.getHashTag(), null, null, TypeSearchResult.HASHTAG,
                    searchPeriods);
            final List<Poll> polls = getPoll().getPollByHashTagName(hashTag.getHashTag(), null, null, TypeSearchResult.HASHTAG, searchPeriods);
              // FUTURE: We need include Surveys by Hashtag
            // include on the query all published items by tweetpoll
            if (d.size() != 0) {
                criteria.add(Restrictions.in("tweetPoll", d));
                queryRequired = true;
            }
            // include on the query all published items by poll
            if (polls.size() != 0) {
                criteria.add(Restrictions.in("poll", polls));
                queryRequired = true;
            }
            //BUG: We have a serial problem here, if poll and tweetpoll are null the criteria retrieve ALL items. ENCUESTAME-490
        } else if (itemType.equals(TypeSearchResult.PROFILE)) {
            //TODO: future
            //return ListUtils.EMPTY_LIST;
            //queryRequired = true;
        } else {
            log.error("Item type not valid: " + itemType);
        }
        criteria.addOrder(Order.desc("publicationDateTweet"));
        //FIXME: why this line are commented?
        //criteria.add(Restrictions.isNotNull("apiType"));
        //criteria.add(Restrictions.isNotNull("tweetId"));
        criteria.add(Restrictions.eq("status", Status.SUCCESS));
        // if exist filters, we execute a query
        if (queryRequired) {
            tweetPollSavedPublishedStatus =  getHibernateTemplate().findByCriteria(criteria, start, max);
        }
        return tweetPollSavedPublishedStatus;
    }
}