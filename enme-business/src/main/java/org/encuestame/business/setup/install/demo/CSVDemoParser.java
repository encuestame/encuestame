package org.encuestame.business.setup.install.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.business.service.AbstractSurveyService;
import org.encuestame.core.cron.CalculateHashTagSize;
import org.encuestame.core.cron.CalculateRelevance;
import org.encuestame.core.security.web.SecurityUtils;
import org.encuestame.core.service.imp.ICommentService;
import org.encuestame.core.service.imp.IFrontEndService;
import org.encuestame.core.service.imp.IPollService;
import org.encuestame.core.service.imp.ITweetPollService;
import org.encuestame.core.service.imp.SecurityOperations;
import org.encuestame.core.util.EnMePlaceHolderConfigurer;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.core.util.SocialUtils;
import org.encuestame.persistence.dao.ITweetPoll;
import org.encuestame.persistence.domain.Comment;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.Hit;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollResult;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.RestFullUtil;
import org.encuestame.utils.ShortUrlProvider;
import org.encuestame.utils.enums.HitCategory;
import org.encuestame.utils.enums.NotificationEnum;
import org.encuestame.utils.enums.Status;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.security.SignUpBean;
import org.encuestame.utils.social.SocialProvider;
import org.encuestame.utils.social.SocialUserProfile;
import org.encuestame.utils.web.CommentBean;
import org.encuestame.utils.web.CreatePollBean;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.context.SecurityContextHolder;
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

    /**
     *
     */
    @Resource
    public CalculateHashTagSize calculateHashTagSize;

    /**
     *
     */
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


    /** Log. **/
    private Log log = LogFactory.getLog(this.getClass());

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

   /*
    * (non-Javadoc)
    * @see org.encuestame.business.setup.install.demo.CSVParser#executeCSVDemoInstall(java.lang.Integer, java.lang.Integer, java.lang.Integer)
    */
    @Override
    public void executeCSVDemoInstall(Integer tpvotes, Integer pollvotes, Integer surveyVotes) throws IOException, EnMeNoResultsFoundException {
        log.debug("Starting domain reindex...");
        long start = System.currentTimeMillis();
        List<SignUpBean> users = getUsers();
        log.debug("Creating users....");
        for (SignUpBean signUpBean : users) {
            getSecurity().signupUser(signUpBean, true);
        }
        log.debug("Getting Hashtags....");
        final List<HashTagBean> hashtags = getHashtags();
        log.debug("Users size: "+users.size());
        List<QuestionBean> listQuestions = getPollQuestions();
        List<QuestionBean> tpListQuestions = getTpPollQuestions();
        final List<UserAccount> userAccount = getAccountDao().findAll();
        for (UserAccount userAccount2 : userAccount) {
            SocialUserProfile socialProfileId = new SocialUserProfile();
            socialProfileId.setEmail(userAccount2.getUserEmail());
            final Long id = Long.valueOf(getRandomNumberRange(50, 5000) );
            final StringBuffer stringBuffer = new StringBuffer(id.toString());
            stringBuffer.append("-");
            stringBuffer.append(RandomStringUtils.randomAlphanumeric(100));
            socialProfileId.setId(stringBuffer.toString());
            socialProfileId.setProfileImageUrl("xxxxx");
            socialProfileId.setProfileUrl("url");
            socialProfileId.setUsername(userAccount2.getUsername());
            socialProfileId.setName(userAccount2.getCompleteName());
            //create social account.
            getSecurity().addNewSocialAccount(
                    "12345",
                    "12345",
                    null,
                    socialProfileId,
                    SocialProvider.TWITTER, userAccount2);
        }
        final List<CommentBean> comments = getComments();
        int totalQuestions = listQuestions.size();
        int totalhashtagss = hashtags.size();
        int totalUsers = userAccount.size();
        log.debug("Users size: "+totalQuestions);
        if (EnMePlaceHolderConfigurer.getBooleanProperty("demo.limit.tweetpolls")) {
            tpListQuestions = tpListQuestions.subList(0, EnMePlaceHolderConfigurer.getIntegerProperty("demo.limit.tweetpolls.quantity"));
        }

        if (EnMePlaceHolderConfigurer.getBooleanProperty("demo.limit.polls")){
            listQuestions = listQuestions.subList(0, EnMePlaceHolderConfigurer.getIntegerProperty("demo.limit.polls.quantity"));
        }

        log.trace("TOTAL TWEETPOLL TO PROCESS: "+tpListQuestions.size());
        log.trace("TOTAL POLL TO PROCESS: "+listQuestions.size());

        for (QuestionBean question : tpListQuestions) {
            /*
             * TWEETPOLL
             */
            final TweetPollBean tweetPollBean = new TweetPollBean();
            //tweetPollBean.getHashTags().addAll();
            // save create tweet poll
            double randomUser = RandomUtils.nextInt(totalUsers);
            UserAccount u = userAccount.get(Double.valueOf(randomUser).intValue());
            SecurityUtils.authenticate(u);
            tweetPollBean.setUserId(u.getAccount().getUid());
            tweetPollBean.setCloseNotification(Boolean.FALSE);

            tweetPollBean.setResultNotification(Boolean.FALSE);
            // all published by default
            tweetPollBean.setPublishPoll(Boolean.TRUE);
            // publish aleatory a publish poll.
//            int publish = RandomUtils.nextInt(10);
//            if (publish <= 7) {
//                tweetPollBean.setPublishPoll(Boolean.TRUE); // always TRUE
//            }
            // no scheduled tp in the demo data
            tweetPollBean.setSchedule(Boolean.FALSE);
            try {
                //final Question qm = createQuestion(question, u, QuestionPattern.CUSTOMIZABLE_SELECTION);
                final TweetPoll tweetPollDomain = getTweetPollService().createTweetPoll(tweetPollBean, question.getQuestionName(), u, null);

                //force to be published
                tweetPollDomain.setPublishTweetPoll(true);
                getTweetPollDao().saveOrUpdate(tweetPollDomain);

                double hits = getRandomNumberRange(2, EnMePlaceHolderConfigurer
                        .getIntegerProperty("demo.max.tweetpoll.hits"));
                for (int i = 0; i < hits; i++) {
                    getFrontEndService().registerHit(tweetPollDomain, null, null, null, EnMeUtils.ipGenerator(), HitCategory.VISIT);
                }

                double votes = getRandomNumberRange(10, EnMePlaceHolderConfigurer
                        .getIntegerProperty("demo.max.tweetpoll.votes"));
                for (int i = 0; i < votes; i++) {
                    getFrontEndService().registerVote(tweetPollDomain.getTweetPollId(), TypeSearchResult.TWEETPOLL, EnMeUtils.ipGenerator());
                }

                //TODO: make sense?
                //tpx.setHits(Long.valueOf(getRandomNumberRange(6, 5000)));
                //tpx.setNumbervotes(Long.valueOf(getRandomNumberRange(40, 10000)));

                tweetPollDomain.setPublishTweetPoll(true);
                Date tweetPollRandomDate = createRandomDate();
                tweetPollDomain.setCreateDate(tweetPollRandomDate);
                tweetPollDomain.setUpdatedDate(tweetPollRandomDate);
                getTweetPollDao().saveOrUpdate(tweetPollDomain);

                for (int i = 0; i < EnMePlaceHolderConfigurer
                        .getIntegerProperty("demo.max.tweetpoll.folder"); i++) {
                    log.debug("Creating folder name ....");
                    getTweetPollService().createTweetPollFolder(this.DEFAULT_FOLDER_NAME+"_"+i+"_", u.getUsername());
                }
                log.debug("New tweetpoll "+tweetPollDomain.getTweetPollId());

                for (CommentBean commentBean2 : comments) {
                    log.debug("New COMMENT tweetpoll ");
                    final Comment comment = new Comment();
                    comment.setTweetPoll(tweetPollDomain);
                    comment.setCreatedAt(createRandomDate());
                    comment.setComment(commentBean2.getComment());
                    comment.setDislikeVote(Long.valueOf(getRandomNumberRange(EnMePlaceHolderConfigurer
                            .getIntegerProperty("demo.min.comments.unlikes"), EnMePlaceHolderConfigurer
                            .getIntegerProperty("demo.max.comments.unlikes"))));
                    comment.setLikeVote(Long.valueOf(getRandomNumberRange(EnMePlaceHolderConfigurer
                            .getIntegerProperty("demo.min.comments.likes"), EnMePlaceHolderConfigurer
                            .getIntegerProperty("demo.max.comments.likes"))));
                    comment.setUser(userAccount.get(Double.valueOf(getRandomNumberRange(0, totalUsers) - 1).intValue()));
                    getTweetPollDao().saveOrUpdate(comment);
                    log.debug("Saved COMMENT tweetpoll ");
                }

                log.debug("Add hashtag to tweetpoll ");
                for (int i = 0; i < EnMePlaceHolderConfigurer
                        .getIntegerProperty("demo.hashtab.by.item"); i++) {
                    double htx = getRandomNumberRange(0, totalhashtagss) - 1;
                    final HashTagBean b = hashtags.get(Double.valueOf(htx).intValue());
                        log.debug("Adding Hashtag "+b.getHashTagName()+ " to tp "+tweetPollDomain.getTweetPollId());
                        final HashTag h = getTweetPollService().addHashtagToTweetPoll(tweetPollDomain, b);
                        h.setUpdatedDate(createRandomDate());
                        getTweetPollDao().saveOrUpdate(h);
                }

                List<QuestionAnswerBean> listOfAnswers = question.getListAnswers();
                log.debug("Ansswers in this questions  "+listOfAnswers.size());

                for (QuestionAnswerBean questionAnswerBean : listOfAnswers) {
                    final QuestionAnswerBean answerBean = new QuestionAnswerBean(questionAnswerBean.getAnswers());
                    answerBean.setShortUrlType(ShortUrlProvider.NONE);
                    //create tweetpoll swithch
                    final TweetPollSwitch tweetPollSwitch = getTweetPollService().createTweetPollQuestionAnswer(answerBean, tweetPollDomain, null);
                    double totalVotes = getRandomNumberRange(0, EnMePlaceHolderConfigurer.getIntegerProperty("demo.votes.by.tppoll")) - 1;
                    log.debug(totalVotes+" Votes for this tweetpolls switch id "+tweetPollSwitch.getSwitchId());
                    for (int i = 0; i < totalVotes; i++) {
                        getTweetPollService().tweetPollVote(tweetPollSwitch, EnMeUtils.ipGenerator(), createRandomDate());
                    }
                    //social links.
                    //final List<SocialAccountBean> eeeee = getSecurity().getValidSocialAccounts(SocialProvider.TWITTER, true);
                   final List<SocialAccount>eeee =  getAccountDao().getSocialVerifiedAccountByUserAccount(u.getAccount(), SocialProvider.TWITTER);
                   for (SocialAccount socialAccountBean : eeee) {
                       for (int i = 0; i < EnMePlaceHolderConfigurer
                                .getIntegerProperty("demo.max.tweetpoll.social.network.published"); i++) {
                        String tweetId = RandomStringUtils.random(6);
                        final TweetPollSavedPublishedStatus publishedStatus = new TweetPollSavedPublishedStatus();
                        //social provider.
                        publishedStatus.setApiType(SocialProvider.TWITTER);
                        publishedStatus.setSocialAccount(socialAccountBean);
                        //adding tweetpoll
                        publishedStatus.setTweetPoll(tweetPollDomain);
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
                //e.printStackTrace();
                log.error(e);
            }
        }

        /*
         * POLL
         */
        log.trace("Creating Polls...");
        for (QuestionBean question : listQuestions) {
            try {
                List<String> s = new ArrayList<String>();
                for (QuestionAnswerBean commentBean : question.getListAnswers()) {
                    s.add(commentBean.getAnswers());
                }
                //answer array
                String[] arrayAnswers = new String[s.size()];
                String[] arrayHashtag = new String[EnMePlaceHolderConfigurer.getIntegerProperty("demo.hashtab.by.item")];
                arrayAnswers = s.toArray(arrayAnswers);
                log.trace("Qu Answers x ."+arrayAnswers);
                //hashtag array
                for (int i = 0; i < EnMePlaceHolderConfigurer.getIntegerProperty("demo.hashtab.by.item"); i++) {
                    double htx = getRandomNumberRange(0, totalhashtagss) - 1;
                    final HashTagBean b = hashtags.get(Double.valueOf(htx).intValue());
                    arrayHashtag[i] = b.getHashTagName();
                }
                //List<HashTagBean> hashtagList = Arrays.asList(arrayHashtag);
                final CreatePollBean createPollBean = new CreatePollBean();
                //question.getQuestionName(),arrayAnswers, "ALL", "MODERATE", true, hashtagList);
                //poll.setCreateDate(createRandomDate()
                createPollBean.setShowComments("MODERATE");
                createPollBean.setResults("ALL");
                createPollBean.setQuestionName(question.getQuestionName());
                createPollBean.setAnswers(arrayAnswers);
                createPollBean.setFolder_name(null);
                createPollBean.setMultiple(Boolean.FALSE);
                createPollBean.setCloseDate(null);
                createPollBean.setLimitVote(null);
                createPollBean.setAllowAdd(Boolean.FALSE);
                createPollBean.setHashtags(arrayHashtag);
                createPollBean.setIsHidden(Boolean.FALSE);
                createPollBean.setIsPasswordProtected(Boolean.FALSE);
                final Poll poll = getPollService().createPoll(createPollBean);
                getTweetPollDao().saveOrUpdate(poll);
                double hits = getRandomNumberRange(EnMePlaceHolderConfigurer
                        .getIntegerProperty("demo.min.poll.hits"), EnMePlaceHolderConfigurer
                        .getIntegerProperty("demo.max.poll.hits"));

                for (int i = 0; i < hits; i++) {
                    getFrontEndService().registerHit(null, poll, null, null, EnMeUtils.ipGenerator(), HitCategory.VISIT);
                }

                double votes = getRandomNumberRange(EnMePlaceHolderConfigurer
                        .getIntegerProperty("demo.min.poll.votes"), EnMePlaceHolderConfigurer
                        .getIntegerProperty("demo.max.poll.votes"));

                for (int i = 0; i < votes; i++) {
                    getFrontEndService().registerVote(poll.getPollId(), TypeSearchResult.POLL, EnMeUtils.ipGenerator());
                }

                log.trace(" Polls ID ..."+poll.getPollId());
                    final List<QuestionAnswerBean> answer = getPollService().retrieveAnswerByQuestionId(poll.getQuestion().getQid());
                    for (QuestionAnswerBean questionAnswerBean : answer) {
                        double totalVotes = getRandomNumberRange(0, EnMePlaceHolderConfigurer.getIntegerProperty("demo.votes.by.poll")) - 1;
                        log.trace(totalVotes+":: Votes for this POLL switch id "+questionAnswerBean.getAnswers());
                        for (int i = 0; i < totalVotes; i++) {
                        getPollService().vote(poll,
                                poll.getQuestion().getSlugQuestion(),
                                EnMeUtils.ipGenerator(),questionAnswerBean.getAnswerId());
                        }
                    }
                    log.trace(" Polls ID ..."+poll.getCreateDate());
            } catch (EnMeExpcetion e) {
                //e.printStackTrace();
                log.error(e);
            }
        }
        //retrive all hits
        log.trace(" :: Update Hits Date :: ");
        @SuppressWarnings("unchecked")
        final List listHits = getAccountDao().getHibernateTemplate().find("from Hit");
        for (Hit hit : (List<Hit>) listHits) {
            hit.setHitDate(createRandomDate());
            getAccountDao().saveOrUpdate(hit);
        }

        log.trace(" :: Update Poll Result Date :: ");
        @SuppressWarnings("unchecked")
        final List pollResults = getAccountDao().getHibernateTemplate().find("from PollResult");
        for (PollResult hit : (List<PollResult>) pollResults) {
            hit.setVotationDate(createRandomDate());
            getAccountDao().saveOrUpdate(hit);
        }

        log.debug(" :: Calculating Relevance :: ");
        this.calculateRelevance.calculate();

        log.debug(" :: Calculating Hashtag Size :: ");
        this.calculateHashTagSize.calculate();

        log.debug(" :::::::::::::::::::::::::::::::::::::::::::::::::::  ");
        long end = System.currentTimeMillis();
        log.debug("Demo Data : took " + (end - start) + " milliseconds");
        log.debug(" :::::::::::::::::::::::::::::::::::::::::::::::::::  ");

        SecurityContextHolder.clearContext();
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
     * @return the pollService
     */
    public IPollService getPollService() {
        return pollService;
    }

    /**
     * @return the frontEndService
     */
    public IFrontEndService getFrontEndService() {
        return frontEndService;
    }

    /**
     * @return the commentService
     */
    public ICommentService getCommentService() {
        return commentService;
    }

    /**
     * @return the calculateHashTagSize
     */
    public CalculateHashTagSize getCalculateHashTagSize() {
        return calculateHashTagSize;
    }

    /**
     * @return the calculateRelevance
     */
    public CalculateRelevance getCalculateRelevance() {
        return calculateRelevance;
    }

}
