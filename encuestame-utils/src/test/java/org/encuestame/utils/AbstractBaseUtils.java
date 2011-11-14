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
package org.encuestame.utils;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.time.DateUtils;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.json.QuestionPatternBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.web.DashboardBean;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.PollBean;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.encuestame.utils.web.TweetPollResultsBean;
import org.encuestame.utils.web.UnitEmails;
import org.encuestame.utils.web.UnitGroupBean;
import org.encuestame.utils.web.UnitLists;
import org.encuestame.utils.web.UnitLocationBean;
import org.encuestame.utils.web.UnitLocationTypeBean;
import org.encuestame.utils.web.UnitProjectBean;
import org.encuestame.utils.web.UserAccountBean;

/**
 * Abstract base utils.
 * @author Picado, Juan juanATencuestame.org
 * @since
 */
public abstract class AbstractBaseUtils extends TestCase{
    /**
    * Create Project Bean.
    * @param projectName project bean.
    * @param leadId user leader id
    * @param userId user owner id
    * @return {@link UnitProjectBean}
    */
    public UnitProjectBean createProjectBean(final String projectName, final Long leadId, final Long userId){
        final UnitProjectBean projectBean = new UnitProjectBean();
        projectBean.setDateFinish(new Date());
        projectBean.setDateInit(new Date());
        projectBean.setLeader(leadId);
        projectBean.setUserId(userId);
        projectBean.setDescription("description");
        projectBean.setState("good");
        projectBean.setName(projectName);
        return projectBean;
    }

    /**
    * Create Location Type Bean.
     * @param locationName name
     * @param level level
     * @return {@link UnitLocationTypeBean}
     */
    public UnitLocationTypeBean createLocationTypeBean(
            final String locationName,
            final Integer level){
        final UnitLocationTypeBean locationTypeBean = new  UnitLocationTypeBean();
        locationTypeBean.setIdLocType(null);
        locationTypeBean.setLevel(level);
        locationTypeBean.setLocTypeDesc(locationName);
        return locationTypeBean;
    }


    /**
     * Create Location Bean.
     * @param active active
     * @param description description
     * @param lng longitud
     * @param lat latitud
     * @param level level
     * @param desc desc
     * @return {@link LocationBean}
     */
    public UnitLocationBean createLocationBean(
        final String active,
        final String description,
        final Float lng,
        final Float lat,
        final Integer level,
        final String desc){
        final UnitLocationBean locationBean = new UnitLocationBean();
        //locationBean.se
       // locationBean.setDescription(description);
       // locationBean.setLat(lat);
       // locationBean.setLng(lng);
       // locationBean.setLevel(level);
        //locationBean.setTidtype(1L);
        return locationBean;
    }

    /**
    * Create Unit Answer Bean.
    * @param answerId answerId
    * @param answers answers
    * @param answerHash answerHash
    * @param questionId questionId
    * @return {@link QuestionAnswerBean}
    */

    public QuestionAnswerBean createUnitAnswerBean(
        final Long answerId,
        final String answers,
        final String answerHash,
        final Long questionId){
        final QuestionAnswerBean unitAnswerBean = new QuestionAnswerBean();
        unitAnswerBean.setAnswerHash(answerHash);
        unitAnswerBean.setAnswerId(answerId);
        unitAnswerBean.setAnswers(answers);
        unitAnswerBean.setQuestionId(questionId);
        unitAnswerBean.setUrl("http://www.encuestame.org");
        return unitAnswerBean;
    }

    /**
    * Create Unit Question Bean.
    * @param questionId questionId
    * @param questionName questionName
    * @param version version
    * @param unitAnswer unitAnswer
    * @return {@link QuestionBean}
    */


    public QuestionBean createUnitQuestionBean(
        final Long questionId,
        final String questionName,
        final String version,
        final List unitAnswer,
        final QuestionPatternBean pattern
    ){
        final QuestionBean unitQuestionBean = new QuestionBean();
        unitQuestionBean.setId(questionId);
        unitQuestionBean.setQuestionName(questionName);
        unitQuestionBean.setVersion(version);
        unitQuestionBean.setUserId(1L);
        unitQuestionBean.setStateId(1L);
        return unitQuestionBean;
    }

    /**
    * Create Unit Pattern Bean.
    * @param classpattern classpattern
    * @param descripcion descripcion
    * @param finallity finallity
    * @param patternId patternId
    * @param label label
    * @param levelpattern levelpattern
    * @param patronType patronType
    * @param shortNumberString shortNumberString
    * @return {@link QuestionPatternBean}
    */

    public QuestionPatternBean createUnitPatternBean(
        final String classpattern,
        final String descripcion,
        final String finallity,
        final Long patternId,
        final String label,
        final String levelpattern,
        final String patronType,
        final Integer shortNumberString,
        final String template
        )
    {
        final QuestionPatternBean unitPatternBean = new QuestionPatternBean();
        unitPatternBean.setClasspattern(classpattern);
        unitPatternBean.setDescripcion(descripcion);
        unitPatternBean.setFinallity(finallity);
        unitPatternBean.setId(patternId);
        unitPatternBean.setLabel(label);
        unitPatternBean.setLevelpattern(levelpattern);
        unitPatternBean.setPatronType(patronType);
       // unitPatternBean.setServicemanagerBean(servicemanagerBean)
       // unitPatternBean.setShortNumberString(shortNumberString);
        unitPatternBean.setTemplate(template);

        return unitPatternBean;

    }

    /**
     * Create Unit Poll.
     * @param completedPoll completedPoll
     * @param creationDate creationDate
     * @param id idUnitPoll
     * @param questionBean UnitQuestionBean
     * @return
     */
    public PollBean createUnitPoll(
            final Boolean completedPoll,
            final Date creationDate,
            final Long id,
            final QuestionBean questionBean){
        final PollBean unitPoll = new PollBean();
        unitPoll.setCompletedPoll(completedPoll);
        unitPoll.setCreateDate(DateUtil.DOJO_DATE_FORMAT.format(creationDate));
        unitPoll.setId(id);
        unitPoll.setQuestionBean(questionBean);
        return unitPoll;
    }

    /**
     * Create Unit Group Bean
     * @param groupDescription
     * @param groupName
     * @param groupId
     * @param stateId
     * @return unitGroupBean
     */
     public UnitGroupBean createUnitGroupBean(
             final String groupDescription,
             final String groupName,
             final Long groupId,
             final Long stateId
     ){
         final UnitGroupBean unitGroupBean = new UnitGroupBean();
         unitGroupBean.setGroupDescription(groupDescription);
         unitGroupBean.setGroupName(groupName);
         unitGroupBean.setId(groupId);
         unitGroupBean.setStateId(stateId);
         return unitGroupBean;
     }

     /**
      * Create Unit Hash Tags.
      * @param hashTagName
      * @param hashId
      * @return unitHashTag
      */
     public HashTagBean createUnitHashTag(
             final String hashTagName,
             final Long hashId){
         final HashTagBean unitHashTag = new HashTagBean();
         unitHashTag.setId(hashId);
         unitHashTag.setHashTagName(hashTagName);
        return unitHashTag;
     }

     /**
      * Create Unit Lists.
      * @param createdAt
      * @param listName
      * @param listId
      * @param userId
      * @return unitLists
      */
     public UnitLists createUnitLists(
             final Date createdAt,
             final String listName,
             final Long listId,
             final Long userId){
         final UnitLists unitLists = new UnitLists();
         unitLists.setCreatedAt(createdAt);
         unitLists.setId(listId);
         unitLists.setListName(listName);
         unitLists.setUserId(userId);
         return unitLists;
     }

     /**
      * Create Unit Emails.
      * @param emailName
      * @param idEmail
      * @param listsId
      * @return
      */
     public UnitEmails createUnitEmails(
             final String emailName,
             final Long idEmail,
             final Long listsId){
         final UnitEmails unitEmails = new UnitEmails();
         unitEmails.setEmailName(emailName);
         unitEmails.setIdEmail(idEmail);
         unitEmails.setListsId(listsId);
        return unitEmails;
     }

     /**
      * Create unit poll complete.
      * @param closeNotification
      * @param completedPoll
      * @param creationDate
      * @param finishDate
      * @param hashTags
      * @param idPoll
      * @param publishPoll
      * @param questionBean
      * @param showResultsPoll
      * @return
      */
     public PollBean createUnitPollComplete(
             final Boolean closeNotification,
             final Boolean completedPoll,
             final Date creationDate,
             final Date finishDate,
             final List<HashTagBean> hashTags,
             final Long idPoll,
             final Boolean publishPoll,
             final QuestionBean questionBean,
             final Boolean showResultsPoll){
         final PollBean unitPollComplete = new PollBean();
         unitPollComplete.setCloseNotification(closeNotification);
         unitPollComplete.setCompletedPoll(completedPoll);
         unitPollComplete.setCreateDate(DateUtil.DOJO_DATE_FORMAT.format(creationDate));
         unitPollComplete.setFinishDate(finishDate);
         unitPollComplete.setHashTags(hashTags);
         unitPollComplete.setId(idPoll);
         unitPollComplete.setPublishPoll(publishPoll);
         unitPollComplete.setQuestionBean(questionBean);
         unitPollComplete.setShowResultsPoll(showResultsPoll);
         return unitPollComplete;
     }

     /**
      * Helper.
      * Create TweetPoll results bean.
      * @param anwerId
      * @param name
      * @param vote
      * @return
      */
     public TweetPollResultsBean createTweetPollResultsBean(final Long anwerId, final String name, final Long vote){
         final TweetPollResultsBean tpResultsBean = new TweetPollResultsBean();
         tpResultsBean.setAnswerId(anwerId);
         tpResultsBean.setAnswerName(name);
         tpResultsBean.setColor("#FFFF");
         tpResultsBean.setPercent("50");
         tpResultsBean.setVotes(vote);
         return tpResultsBean;
     }

     public TweetPollBean createTweetPollBean(){
         final TweetPollBean tpBean = new TweetPollBean();
         final Date myDate = new Date();
         tpBean.setAllowLiveResults(Boolean.TRUE);
         tpBean.setAllowRepeatedVotes(Boolean.TRUE);
         tpBean.setAnswerSwitchBeans(null);
         tpBean.setCaptcha(Boolean.TRUE);
         tpBean.setCloseNotification(Boolean.TRUE);
         tpBean.setCompleted(Boolean.TRUE);
         tpBean.setCreateDate(myDate.toString());
         tpBean.setDateToLimit(myDate.toString());
         tpBean.setDislikeVote(780L);
         tpBean.setFavorite(Boolean.TRUE);
         tpBean.setHashTags(null);
         tpBean.setHits(180L);
         tpBean.setId(null);
         tpBean.setItemType("TWEETPOLL");
         tpBean.setLikeVote(400L);
         tpBean.setLimitVotes(500);
         tpBean.setLimitVotesEnabled(Boolean.FALSE);
         tpBean.setMaxRepeatedVotes(2);
         tpBean.setOwnerUsername("jhonny");
         tpBean.setPublishPoll(Boolean.TRUE);
         tpBean.setQuestionBean(null);
         tpBean.setRelativeTime(null);
         tpBean.setRelevance(890L);
         tpBean.setResultNotification(Boolean.TRUE);
         tpBean.setResults(null);
         tpBean.setResumeLiveResults(Boolean.TRUE);
         tpBean.setResumeTweetPollDashBoard(null);
         tpBean.setSchedule(Boolean.TRUE);
         tpBean.setScheduleDate(myDate);
         tpBean.setTotalVotes(503L);
         tpBean.setTweetUrl(null);
         tpBean.setUpdateDate(null);
         tpBean.setUserId(null);
         return tpBean;
     }

     /**
      * Create dashboard bean.
      * @param desc
      * @param name
      * @param favorite
      * @param counter
      * @param layout
      * @param selected
      * @param sequence
      * @return
      */
    public DashboardBean createDashboardBean(final String desc,
            final String name, final Boolean favorite, final Integer counter,
            final String layout, final Boolean selected, final Integer sequence) {
        final DashboardBean myDashboard = new DashboardBean();
        myDashboard.setDashboardDesc(desc);
        myDashboard.setDashboardName(name);
        myDashboard.setFavorite(favorite);
        myDashboard.setFavoriteCounter(counter);
        myDashboard.setLayout(layout);
        myDashboard.setSelected(selected);
        myDashboard.setSequence(sequence);
        return myDashboard;
     }

    /**
     * Create user account bean.
     * @param name
     * @param email
     * @return
     */
    public UserAccountBean createUserAccountBean(final String name, final String email){
        final UserAccountBean userAcc = new UserAccountBean();
        userAcc.setName(name);
        userAcc.setEmail(email);
        return userAcc;
    }
}
