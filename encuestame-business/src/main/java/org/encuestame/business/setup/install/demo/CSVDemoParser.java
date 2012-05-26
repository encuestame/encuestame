package org.encuestame.business.setup.install.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.encuestame.business.service.AbstractSurveyService;
import org.encuestame.core.service.imp.IPollService;
import org.encuestame.core.service.imp.ITweetPollService;
import org.encuestame.core.service.imp.SecurityOperations;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.RestFullUtil;
import org.encuestame.utils.enums.QuestionPattern;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.security.SignUpBean;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.encuestame.utils.web.UserAccountBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import au.com.bytecode.opencsv.CSVReader;

@Service
public class CSVDemoParser extends AbstractSurveyService implements CSVParser {
	
	/**
	 * 
	 */
	@Autowired
	public SecurityOperations security;
	
	/**
	 * 
	 */
	@Autowired
	public ITweetPollService tweetPollService;
	
	/**
	 * 
	 */
	@Autowired
	public IPollService pollService;
	
	/**
	 * 
	 */
	private final static String PATH = "/org/encuestame/business/setup/install/demo/";

	/**
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	private CSVReader readCSVFile(final String path) throws IOException {
		ClassPathResource reso = new ClassPathResource(PATH + path);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				reso.getInputStream()));
		CSVReader reader2 = new CSVReader(reader);
		return reader2;
	}

	/**
	 * 
	 */
	private List<SignUpBean> getUsers() throws IOException {
		CSVReader csv = readCSVFile("user.csv");
		String[] nextLine;
		final List<SignUpBean> list = new ArrayList<SignUpBean>();
		while ((nextLine = csv.readNext()) != null) {
			final SignUpBean user = new SignUpBean();
			user.setEmail(nextLine[0]+"."+nextLine[2]);
			user.setFullName(nextLine[3]);
			user.setPassword(nextLine[1]);
			user.setUsername(nextLine[0]);
			list.add(user);
		}
		return list;
	}
		
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	private List<HashTagBean> getHashtags() throws IOException {
		CSVReader csv = readCSVFile("user.csv");
		String[] nextLine;
		final List<HashTagBean> list = new ArrayList<HashTagBean>();
		while ((nextLine = csv.readNext()) != null) {
			final HashTagBean bean = new HashTagBean();
			bean.setHashTagName(nextLine[0]);
			list.add(bean);
		}
		return list;
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	private List<QuestionBean> getQuestions() throws IOException {
		final List<QuestionBean> questions = new ArrayList<QuestionBean>();
		CSVReader reader2 = readCSVFile("questions.csv");
		String[] nextLine;
		while ((nextLine = reader2.readNext()) != null) {
			final QuestionBean bean = new QuestionBean();
			bean.setQuestionName(nextLine[0]);
			bean.setHits(Double.valueOf(5 + (Math.random() * (3000 - 5)))
					.longValue());
			bean.setSlugName(RestFullUtil.slugify(nextLine[0]));
			final List<QuestionAnswerBean> listA = new ArrayList<QuestionAnswerBean>();
			listA.add(createAnswer(nextLine[1]));
			listA.add(createAnswer(nextLine[2]));
			listA.add(createAnswer(nextLine[3]));
			listA.add(createAnswer(nextLine[4]));
			bean.setListAnswers(listA);
			questions.add(bean);
		}
		return questions;
	}
	
	/**
	 * 
	 * @param text
	 * @return
	 */
	private QuestionAnswerBean createAnswer(String text) {
		final QuestionAnswerBean answer1 = new QuestionAnswerBean();
		answer1.setAnswers(text);
		return answer1;
	}

	/**
	 * 
	 */
	@Override
	public void executeCSVDemoInstall() throws IOException {
		List<SignUpBean> users = getUsers();
		System.out.println("Creating users....");
		for (SignUpBean signUpBean : users) {
			getSecurity().singupUser(signUpBean, true);
		}
		System.out.println("Getting Hashtags....");
		List<HashTagBean> hashtags = getHashtags();
//		for (HashTagBean hashTagBean : hashtags) {			
//			//getTweetPollService().createTweetPoll(tweetPollBean, question, user);
//		}
		System.out.println("Users size: "+users.size());
		List<QuestionBean> dd = getQuestions();
		final List<UserAccount> userAccount = getAccountDao().findAll();
		final List<Question> questions = getQuestionDao().loadAllQuestions();
		int totalQuestions = dd.size();
		System.out.println("Users size: "+totalQuestions);
		int totalUsers = userAccount.size();
		System.out.println("Iterating Questions.... Creating Tweetpolls / Poll");
		for (QuestionBean question : dd) {
			
			/*
			 * TWEETPOLL
			 */
			final TweetPollBean tweetPollBean = new TweetPollBean();
	        //tweetPollBean.getHashTags().addAll();
	        // save create tweet poll
	        double x = getRandomNumberRange(0 , totalUsers);
	        UserAccount u = userAccount.get(Double.valueOf(x).intValue());
	        tweetPollBean.setUserId(u.getAccount().getUid());
	        tweetPollBean.setCloseNotification(Boolean.FALSE);
	        tweetPollBean.setResultNotification(Boolean.FALSE);
	        //tweetPollBean.setPublishPoll(Boolean.TRUE); // always TRUE
	        tweetPollBean.setSchedule(Boolean.FALSE);
	        try {
	        	//final Question qm = createQuestion(question, u, QuestionPattern.CUSTOMIZABLE_SELECTION);
				final TweetPoll tpx = getTweetPollService().createTweetPoll(tweetPollBean, question.getQuestionName(), u);
				System.out.println("TP ID "+tpx.getTweetPollId());
			} catch (EnMeExpcetion e) {
				e.printStackTrace();
			}
	        /*
	         * POLL
	         */
		}
	}
	
	/**
	 * 
	 * @param max
	 * @param min
	 * @return
	 */
	private double getRandomNumberRange(int max, int min){
		return (int) (Math.random() * (max - min + 1) ) + min;
	}

	/**
	 * @return the security
	 */
	public SecurityOperations getSecurity() {
		return security;
	}

	/**
	 * @param security the security to set
	 */
	public void setSecurity(SecurityOperations security) {
		this.security = security;
	}

	/**
	 * @return the tweetPollService
	 */
	public ITweetPollService getTweetPollService() {
		return tweetPollService;
	}

	/**
	 * @param tweetPollService the tweetPollService to set
	 */
	public void setTweetPollService(ITweetPollService tweetPollService) {
		this.tweetPollService = tweetPollService;
	}

	/**
	 * @return the pollService
	 */
	public IPollService getPollService() {
		return pollService;
	}

	/**
	 * @param pollService the pollService to set
	 */
	public void setPollService(IPollService pollService) {
		this.pollService = pollService;
	}		
}
