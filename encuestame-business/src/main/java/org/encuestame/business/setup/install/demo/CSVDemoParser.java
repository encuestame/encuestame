package org.encuestame.business.setup.install.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang.RandomStringUtils;
import org.encuestame.business.service.AbstractSurveyService;
import org.encuestame.core.cron.CalculateHashTagSize;
import org.encuestame.core.cron.CalculateRelevance;
import org.encuestame.core.service.imp.ICommentService;
import org.encuestame.core.service.imp.IFrontEndService;
import org.encuestame.core.service.imp.IPollService;
import org.encuestame.core.service.imp.ITweetPollService;
import org.encuestame.core.service.imp.SecurityOperations;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.core.util.SocialUtils;
import org.encuestame.persistence.dao.ITweetPoll;
import org.encuestame.persistence.domain.Comment;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.Hit;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.RestFullUtil;
import org.encuestame.utils.ShortUrlProvider;
import org.encuestame.utils.enums.HitCategory;
import org.encuestame.utils.enums.NotificationEnum;
import org.encuestame.utils.enums.Status;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.json.SocialAccountBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.security.SignUpBean;
import org.encuestame.utils.social.SocialProvider;
import org.encuestame.utils.social.SocialUserProfile;
import org.encuestame.utils.web.CommentBean;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.joda.time.DateTime;
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
	@Autowired
	public IFrontEndService frontEndService;
	
	/**
	 * 
	 */
	@Autowired
	public ITweetPoll tweetPoll;
	
	/**
	 * 
	 */
	@Autowired
	public ICommentService commentService;
	
	
	@Resource
	public CalculateHashTagSize calculateHashTagSize;
	
	@Resource
	public CalculateRelevance calculateRelevance;
	
	/**
	 * 
	 */
	private final static String PATH = "/org/encuestame/business/setup/install/demo/";
	
	/**
	 * 
	 */
	private final static String DEFAULT_FOLDER_NAME = "folder_";
	
	
	private static Integer VOTES_BY_TWEETPOLL = 500;
	
	private static Integer VOTES_BY_POLL = 500;
	
	private static Integer VOTES_BY_SURVEY = 500;
	
	private static Integer HASHTAB_BY_ITEM = 3;

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
		CSVReader csv = readCSVFile("hashtag.csv");
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
	private List<CommentBean> getComments() throws IOException {
		CSVReader csv = readCSVFile("comments.csv");
		String[] nextLine;
		final List<CommentBean> list = new ArrayList<CommentBean>();
		while ((nextLine = csv.readNext()) != null) {
			final CommentBean bean = new CommentBean();
			bean.setComment(nextLine[0]);
			list.add(bean);
		}
		return list;
	}	

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	private List<QuestionBean> getPollQuestions() throws IOException {
		final List<QuestionBean> questions = new ArrayList<QuestionBean>();
		CSVReader reader2 = readCSVFile("poll-questions.csv");
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
	 * @return
	 * @throws IOException
	 */
	private List<QuestionBean> getTpPollQuestions() throws IOException {
		final List<QuestionBean> questions = new ArrayList<QuestionBean>();
		CSVReader reader2 = readCSVFile("tp-questions.csv");
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
	public void executeCSVDemoInstall(Integer tpvotes, Integer pollvotes, Integer surveyVotes) throws IOException {
		VOTES_BY_TWEETPOLL = tpvotes == null ? VOTES_BY_TWEETPOLL : tpvotes;
		VOTES_BY_POLL = pollvotes == null ? VOTES_BY_POLL : pollvotes;
		VOTES_BY_SURVEY = surveyVotes == null ? VOTES_BY_SURVEY : surveyVotes;
		List<SignUpBean> users = getUsers();
		System.out.println("Creating users....");
		for (SignUpBean signUpBean : users) {
			getSecurity().singupUser(signUpBean, true);
		}
		System.out.println("Getting Hashtags....");
		final List<HashTagBean> hashtags = getHashtags();
		System.out.println("Users size: "+users.size());
		final List<QuestionBean> listQuestions = getPollQuestions();
		final List<QuestionBean> tpListQuestions = getTpPollQuestions();
		final List<UserAccount> userAccount = getAccountDao().findAll();
		for (UserAccount userAccount2 : userAccount) {
			SocialUserProfile x = new SocialUserProfile();
			x.setEmail(userAccount2.getUserEmail());
			x.setId(Long.valueOf(getRandomNumberRange(400, 5000)).toString());
			x.setProfileImageUrl("xxxxx");
			x.setProfileUrl("url");
			x.setUsername(userAccount2.getUsername());
			x.setName(userAccount2.getCompleteName());
			//create social account.
			final SocialAccount sac = getSecurity().addNewSocialAccount(
                    "12345", 
                    "12345", 
                    null, 
                    x,
                    SocialProvider.TWITTER, userAccount2);
		}
		final List<CommentBean> comments = getComments();
		int totalQuestions = listQuestions.size();
		int totalhashtagss = hashtags.size();
		int totalUsers = userAccount.size();
		int totalComments = comments.size();
		System.out.println("Users size: "+totalQuestions);		
		System.out.println("Iterating Questions.... Creating Tweetpolls / Poll");

		
		for (QuestionBean question : tpListQuestions) {		
			/*
			 * TWEETPOLL
			 */
			final TweetPollBean tweetPollBean = new TweetPollBean();
	        //tweetPollBean.getHashTags().addAll();
	        // save create tweet poll
	        double randomUser = getRandomNumberRange(0, totalUsers) - 1;
	        UserAccount u = userAccount.get(Double.valueOf(randomUser).intValue());
	        tweetPollBean.setUserId(u.getAccount().getUid());
	        tweetPollBean.setCloseNotification(Boolean.FALSE);
	        tweetPollBean.setResultNotification(Boolean.FALSE);
	        //tweetPollBean.setPublishPoll(Boolean.TRUE); // always TRUE
	        tweetPollBean.setSchedule(Boolean.FALSE);
	        try {
	        	//final Question qm = createQuestion(question, u, QuestionPattern.CUSTOMIZABLE_SELECTION);
				final TweetPoll tpx = getTweetPollService().createTweetPoll(tweetPollBean, question.getQuestionName(), u);

				double hits = getRandomNumberRange(10, 1500);
				for (int i = 0; i < hits; i++) {
					getFrontEndService().registerHit(tpx, null, null, null, EnMeUtils.ipGenerator(), HitCategory.VISIT);
				}
				
				double votes = getRandomNumberRange(10, 40);
				for (int i = 0; i < votes; i++) {
					getFrontEndService().registerVote(tpx.getTweetPollId(), TypeSearchResult.TWEETPOLL, EnMeUtils.ipGenerator());
				}				
				
				tpx.setHits(Long.valueOf(getRandomNumberRange(6, 5000)));
				tpx.setNumbervotes(Long.valueOf(getRandomNumberRange(40, 10000)));
				tpx.setPublishTweetPoll(true);
				Date x = createRandomDate();
				tpx.setCreateDate(x);
				tpx.setUpdatedDate(x);
				getTweetPollDao().saveOrUpdate(tpx);
				for (int i = 0; i < 5; i++) {
					System.out.println("Creating folder name ....");
					getTweetPollService().createTweetPollFolder(this.DEFAULT_FOLDER_NAME+"_"+i+"_", u.getUsername());
				}				
				System.out.println("New tweetpoll "+tpx.getTweetPollId());
				for (CommentBean commentBean2 : comments) {
					System.out.println("New COMMENT tweetpoll ");
					final Comment comment = new Comment();
					comment.setTweetPoll(tpx);
					comment.setCreatedAt(createRandomDate());
					comment.setComment(commentBean2.getComment());
					comment.setDislikeVote(Long.valueOf(getRandomNumberRange(20, 400)));
					comment.setLikeVote(Long.valueOf(getRandomNumberRange(100, 1500)));
					comment.setUser(userAccount.get(Double.valueOf(getRandomNumberRange(0, totalUsers) - 1).intValue()));
					getTweetPollDao().saveOrUpdate(comment);
					System.out.println("Saved COMMENT tweetpoll ");
				}				
				System.out.println("Add hashtag to tweetpoll ");
				for (int i = 0; i < HASHTAB_BY_ITEM; i++) {
					double htx = getRandomNumberRange(0, totalhashtagss) - 1;
					final HashTagBean b = hashtags.get(Double.valueOf(htx).intValue());
						System.out.println("Adding Hashtag "+b.getHashTagName()+ " to tp "+tpx.getTweetPollId());
						final HashTag h = getTweetPollService().addHashtagToTweetPoll(tpx, b);
						h.setUpdatedDate(createRandomDate());
						getTweetPollDao().saveOrUpdate(h);
				}				
				List<QuestionAnswerBean> dddd = question.getListAnswers();
				System.out.println("Ansswers in this questions  "+dddd.size());
				for (QuestionAnswerBean questionAnswerBean : dddd) {
					final QuestionAnswerBean answerBean = new QuestionAnswerBean(questionAnswerBean.getAnswers());
	                answerBean.setShortUrlType(ShortUrlProvider.NONE);
	                //create tweetpoll swithch
	                final TweetPollSwitch tweetPollSwitch = getTweetPollService().createTweetPollQuestionAnswer(answerBean, tpx);	
	                double totalVotes = getRandomNumberRange(0, VOTES_BY_TWEETPOLL) - 1;
	                System.out.println(totalVotes+" Votes for this tweetpolls switch id "+tweetPollSwitch.getSwitchId());
	                for (int i = 0; i < totalVotes; i++) {
	                	getTweetPollService().tweetPollVote(tweetPollSwitch, EnMeUtils.ipGenerator(), createRandomDate());
					}
					//social links.
	                //final List<SocialAccountBean> eeeee = getSecurity().getValidSocialAccounts(SocialProvider.TWITTER, true);
	               final List<SocialAccount>eeee =  getAccountDao().getSocialVerifiedAccountByUserAccount(getUserAccount(getUserPrincipalUsername()).getAccount(), SocialProvider.TWITTER);
	               for (SocialAccount socialAccountBean : eeee) {
	            	   for (int i = 0; i < 5; i++) {					
	            	    String tweetId = RandomStringUtils.random(6);
	                	final TweetPollSavedPublishedStatus publishedStatus = new TweetPollSavedPublishedStatus();
		                //social provider.
		                publishedStatus.setApiType(SocialProvider.TWITTER);
		                publishedStatus.setSocialAccount(socialAccountBean);
		                //adding tweetpoll
		                publishedStatus.setTweetPoll(tpx);
		                //store original tweet id.
		                 publishedStatus.setTweetId(tweetId);
		                 //store original publication date.
		                 publishedStatus.setPublicationDateTweet(createRandomDate());
		                 //success publish state..
		                 publishedStatus.setStatus(Status.SUCCESS);
		                 //store original tweet content.
		                 publishedStatus.setTweetContent(question.getQuestionName());
		                 getTweetPollDao().saveOrUpdate(publishedStatus);
		                 //create notification
		                 //createNotification(NotificationEnum.TWEETPOLL_PUBLISHED, "tweet published", socialAccount.getAccount());
		                 createNotification(NotificationEnum.SOCIAL_MESSAGE_PUBLISHED, question.getQuestionName(), SocialUtils.getSocialTweetPublishedUrl(
		                		 tweetId, u.getUsername(), socialAccountBean.getAccounType()), Boolean.TRUE);		                
	            	   }
					}	                
				}

			} catch (EnMeExpcetion e) {
				e.printStackTrace();
			}	        
		}
		
        /*
         * POLL
         */		
		System.out.println("Creating Polls...");
		for (QuestionBean question : listQuestions) {
			try {
				List<String> s = new ArrayList<String>();
				for (QuestionAnswerBean commentBean : question.getListAnswers()) {
					s.add(commentBean.getAnswers());
				}
				String[] arrayAnswers = new String[s.size()];
				arrayAnswers = s.toArray(arrayAnswers);						
				System.out.println("Qu Answers x ."+arrayAnswers);
				final Poll poll = getPollService().createPoll(question.getQuestionName(),arrayAnswers, true, "MODERATE", true);
				poll.setCreatedAt(createRandomDate());
				getTweetPollDao().saveOrUpdate(poll);
				double hits = getRandomNumberRange(10, 500);
				for (int i = 0; i < hits; i++) {
					getFrontEndService().registerHit(null, poll, null, null, EnMeUtils.ipGenerator(), HitCategory.VISIT);
				}				
				double votes = getRandomNumberRange(10, 40);
				for (int i = 0; i < votes; i++) {
					getFrontEndService().registerVote(poll.getPollId(), TypeSearchResult.POLL, EnMeUtils.ipGenerator());
				}
				System.out.println(" Polls ID ..."+poll.getPollId());
	                final List<QuestionAnswerBean> answer = getPollService().retrieveAnswerByQuestionId(poll.getQuestion().getQid());
	                for (QuestionAnswerBean questionAnswerBean : answer) {
					    double totalVotes = getRandomNumberRange(100, VOTES_BY_POLL) - 1;
		                System.out.println(totalVotes+":: Votes for this POLL switch id "+questionAnswerBean.getAnswers());
	                	for (int i = 0; i < totalVotes; i++) {
						getPollService().vote(poll.getPollId(),
								poll.getQuestion().getSlugQuestion(),
								EnMeUtils.ipGenerator(),questionAnswerBean.getAnswerId());
						}
					}	
	                System.out.println(" Polls ID ..."+poll.getCreatedAt());
			} catch (EnMeExpcetion e) {
				e.printStackTrace();
			}
		}
		final List<Hit> listHits = getAccountDao().getHibernateTemplate().find("from Hit");
		for (Hit hit : listHits) {
			hit.setHitDate(createRandomDate());
			getAccountDao().saveOrUpdate(hit);
		}
		System.out.println(" :: Calculating Relevance :: ");
		this.calculateRelevance.calculate();
		System.out.println(" :: Calculating Hashtag Size :: ");
		this.calculateHashTagSize.calculate();
		//retrive all hits
	}
	
	/**
	 * 
	 * @return
	 */
	private Date createRandomDate(){
		DateTime current = new DateTime(Calendar.getInstance().getTime());
		current = current.minusDays(getRandomNumberRange(400, 0));
		return current.toDate();
	}
	
	/**
	 * 
	 * @param max
	 * @param min
	 * @return
	 */
	private int getRandomNumberRange(int max, int min){
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

	/**
	 * @return the frontEndService
	 */
	public IFrontEndService getFrontEndService() {
		return frontEndService;
	}

	/**
	 * @param frontEndService the frontEndService to set
	 */
	public void setFrontEndService(IFrontEndService frontEndService) {
		this.frontEndService = frontEndService;
	}

	/**
	 * @return the commentService
	 */
	public ICommentService getCommentService() {
		return commentService;
	}

	/**
	 * @param commentService the commentService to set
	 */
	public void setCommentService(ICommentService commentService) {
		this.commentService = commentService;
	}

	/**
	 * @return the calculateHashTagSize
	 */
	public CalculateHashTagSize getCalculateHashTagSize() {
		return calculateHashTagSize;
	}

	/**
	 * @param calculateHashTagSize the calculateHashTagSize to set
	 */
	public void setCalculateHashTagSize(CalculateHashTagSize calculateHashTagSize) {
		this.calculateHashTagSize = calculateHashTagSize;
	}

	/**
	 * @return the calculateRelevance
	 */
	public CalculateRelevance getCalculateRelevance() {
		return calculateRelevance;
	}

	/**
	 * @param calculateRelevance the calculateRelevance to set
	 */
	public void setCalculateRelevance(CalculateRelevance calculateRelevance) {
		this.calculateRelevance = calculateRelevance;
	}
	
	
}
