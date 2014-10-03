package org.encuestame.utils.web;

import java.util.ArrayList;
import java.util.List;

import org.encuestame.utils.json.TweetPollBean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Unit Tweetpoll Detail Bean.
 * @author Morales, Diana Paola paola ATencuestame.org
 * @since  October 01, 2014
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TweetPollDetailBean {

	/**
    *
    */
	@JsonProperty(value = "tweetPoll_results")
	public List<TweetPollResultsBean> results = new ArrayList<TweetPollResultsBean>();

	/**
    *
    */
	@JsonProperty(value = "tpoll_bean")
	public TweetPollBean tpollBean;

	/**
	 * List of available answers.
	 */
	@JsonProperty(value = "tpoll_list_answers")
	public List<QuestionAnswerBean> listAnswers = new ArrayList<QuestionAnswerBean>();

	/**
	 *
	 * @return the TweetpollResults
	 */
	@JsonIgnore
	public List<TweetPollResultsBean> getResults() {
		return results;
	}

	/**
	 *
	 * @param results
	 */
	public void setResults(List<TweetPollResultsBean> results) {
		this.results = results;
	}

	/**
	 *
	 * @return
	 */
	@JsonIgnore
	public TweetPollBean getTpollBean() {
		return tpollBean;
	}

	/**
	 *
	 * @param tpollBean
	 */
	public void setTpollBean(TweetPollBean tpollBean) {
		this.tpollBean = tpollBean;
	}

	/**
	 *
	 * @return
	 */
	@JsonIgnore
	public List<QuestionAnswerBean> getListAnswers() {
		return listAnswers;
	}

	/**
	 *
	 * @param listAnswers
	 */
	public void setListAnswers(List<QuestionAnswerBean> listAnswers) {
		this.listAnswers = listAnswers;
	}
}
