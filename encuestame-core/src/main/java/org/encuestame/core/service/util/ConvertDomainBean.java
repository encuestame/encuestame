/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.service.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.persistence.pojo.CatLocation;
import org.encuestame.core.persistence.pojo.Poll;
import org.encuestame.core.persistence.pojo.Project;
import org.encuestame.core.persistence.pojo.QuestionPattern;
import org.encuestame.core.persistence.pojo.Questions;
import org.encuestame.core.persistence.pojo.QuestionsAnswers;
import org.encuestame.core.persistence.pojo.SecGroups;
import org.encuestame.core.persistence.pojo.SecPermission;
import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.persistence.pojo.TweetPoll;
import org.encuestame.utils.web.UnitAnswersBean;
import org.encuestame.utils.web.UnitGroupBean;
import org.encuestame.utils.web.UnitLocationBean;
import org.encuestame.utils.web.UnitLocationTypeBean;
import org.encuestame.utils.web.UnitPatternBean;
import org.encuestame.utils.web.UnitPermission;
import org.encuestame.utils.web.UnitPoll;
import org.encuestame.utils.web.UnitProjectBean;
import org.encuestame.utils.web.UnitQuestionBean;
import org.encuestame.utils.web.UnitSessionUserBean;
import org.encuestame.utils.web.UnitTweetPoll;
import org.encuestame.utils.web.UnitUserBean;

/**
 * Convert Domain to  Beans.
 * @author Picado, Juan juan@encuestame.org
 * @since 03/12/2009 06:38:32
 * @version $Id$
 */
public class ConvertDomainBean {

    private static Log log = LogFactory.getLog(ConvertDomainBean.class);

    /**
     * Convert Domain user to Bean User.
     * @param domainUser Domain User
     * @return Bean User
     */
    @Deprecated
    public static final UnitUserBean convertUserDaoToUserBean(final SecUserSecondary domainUser) {
        final UnitUserBean user = new UnitUserBean();
        try {
            user.setName(domainUser.getCompleteName());
            user.setUsername(domainUser.getUsername());
            user.setEmail(domainUser.getUserEmail());
            user.setId(domainUser.getUid());
            user.setStatus(domainUser.isUserStatus());
            user.setPassword(domainUser.getPassword());
            user.setDateNew(domainUser.getEnjoyDate());
            user.setInviteCode(domainUser.getInviteCode());
        } catch (Exception e) {
            log.error("error user bean converter -" + e.getMessage());
        }
        return user;
    }

    /**
     * Convert {@link SecUsers} to {@link UnitSessionUserBean}.
     * @param user user
     * @return {@link UnitSessionUserBean}
     */
    public static final UnitSessionUserBean convertUserSessionToUserBean(final SecUsers user){
        final UnitSessionUserBean sessionUserBean =  new UnitSessionUserBean();
        sessionUserBean.setUserSessionId(user.getUid());
        sessionUserBean.setConsumerTwitterKey(user.getConsumerKey());
        sessionUserBean.setConsumerTwitterSecret(user.getConsumerSecret());
        sessionUserBean.setTwitterPassword(user.getTwitterPassword());
        sessionUserBean.setTwitterAccount(user.getTwitterAccount());
        sessionUserBean.setTwitterTwitterPing(user.getTwitterPing());
        return sessionUserBean;
    }

    /**
     * Convert {@link SecUserSecondary} to {@link UnitUserBean}.
     * @param secUserSecondary {@link SecUserSecondary}.
     * @return {@link UnitUserBean}
     */
    public static final UnitUserBean convertSecondaryUserToUserBean(final SecUserSecondary secUserSecondary){
        final UnitUserBean unitUserBean = new UnitUserBean();
        unitUserBean.setId(secUserSecondary.getUid());
        unitUserBean.setName(secUserSecondary.getCompleteName());
        unitUserBean.setEmail(secUserSecondary.getUserEmail());
        unitUserBean.setUsername(secUserSecondary.getUsername());
        unitUserBean.setStatus(secUserSecondary.isUserStatus());
        unitUserBean.setListGroups(convertSetToUnitGroupBean(secUserSecondary.getSecGroups()));
        unitUserBean.setListPermission(convertSetToUnitPermission(secUserSecondary.getSecUserPermissions()));
        return unitUserBean;
    }

    /**
     * Convert Domain Permission to Bean Permission.
     * @param userId user id
     * @return collection of permission
     * @throws Exception all exceptions.
  */
    public static final Collection<UnitPermission> convertSetToUnitPermission(final Set<SecPermission> permissions) {
        final Collection<UnitPermission> loadListPermission = new LinkedList<UnitPermission>();
        for (SecPermission secPermission : permissions) {
            loadListPermission.add(ConvertDomainBean.convertPermissionToBean(secPermission));
        }
        return loadListPermission;
    }

    /**
     * Convert set to unit group bean
     * @param userId user id
     * @return collection of groups beans.
     * @throws Exception
     */
    public static final Collection<UnitGroupBean> convertSetToUnitGroupBean(final Set<SecGroups> groups){
            final Collection<UnitGroupBean> loadListGroups = new LinkedList<UnitGroupBean>();
            for (SecGroups secGroups : groups) {
                 loadListGroups.add(ConvertDomainBean.convertGroupDomainToBean(secGroups));
            }
        return loadListGroups;
    }

    /**
     * Convert {@link SecGroups} to {@link UnitGroupBean}
     * @param groupDomain {@link SecGroups}
     * @return {@link UnitGroupBean}
     */
    public static final UnitGroupBean convertGroupDomainToBean(final SecGroups groupDomain) {
        final UnitGroupBean groupBean = new UnitGroupBean();
        groupBean.setId(groupDomain.getGroupId());
        groupBean.setGroupName(groupDomain.getGroupName());
        groupBean.setGroupDescription(groupDomain.getGroupDescriptionInfo());
        groupBean.setStateId(String.valueOf(groupDomain.getIdState()));
        return groupBean;
    }

    /**
     * Convert {@link CatLocation} to {@link UnitLocationBean}
     * @param location {@link CatLocation}
     * @return {@link UnitLocationBean}
     */
    public static final UnitLocationBean convertLocationToBean(final CatLocation location){
        final UnitLocationBean locationBean = new UnitLocationBean();
        /*locationBean.setTid(location.getLocateId());
        locationBean.setActive(location.getLocationActive());
        locationBean.setDescriptionLocation(location.getLocationDescription());
        locationBean.setLatitude(location.getLocationLatitude());
        locationBean.setLevel(location.getLocationLevel());
        locationBean.setLongitude(location.getLocationLongitude());
        locationBean.setLocationTypeId(location.getTidtype().getLocationTypeId());*/
        return locationBean;

    }

    /**
     * @param locationType {@link CatLocationType}
     * @return {@link UnitLocationTypeBean}
     */
 /*   public static final UnitLocationTypeBean convertLocationTypeToBean(final CatLocationType locationType){
        final UnitLocationTypeBean locationTypeBean = new UnitLocationTypeBean();
        locationTypeBean.setIdLocType(locationType.getLocationTypeId());
        locationTypeBean.setLocTypeDesc(locationType.getLocationTypeDescription());
        locationTypeBean.setLevel(locationType.getLocationTypeLevel());
        return locationTypeBean;

    }
*/
    /**
     * Convert {@link Project} to {@link UnitProjectBean}
      * @param project {@link UnitProjectBean}
     * @return {@link UnitProjectBean}
     */
    public static final UnitProjectBean convertProjectDomainToBean(final Project project) {
        final UnitProjectBean projectBean = new UnitProjectBean();
        projectBean.setName(project.getProjectDescription());
        projectBean.setDateFinish(project.getProjectDateFinish());
        projectBean.setDateInit(project.getProjectDateStart());
        projectBean.setId(project.getProyectId());
        projectBean.setDescription(project.getProjectInfo());
        projectBean.setState(project.getStateProject().getIdState());
        return projectBean;
    }


    /**
     * Convert {@link SecPermission} to {@link UnitPermission}
     * @param permission permission.
     * @return permBean
     */
    public static final UnitPermission convertPermissionToBean(final SecPermission permission){
      final UnitPermission permBean = new UnitPermission();
      permBean.setId(permission.getIdPermission());
      permBean.setDescription(permission.getPermissionDescription());
      permBean.setPermission(permission.getPermission());
      return permBean;
    }


    /**
     * Convert {@link QuestionPattern} to {@link UnitPatternBean}.
     * @param pattern  {@link QuestionPattern}
     * @return {@link UnitPatternBean}
     */
    public static final UnitPatternBean convertQuestionPatternToBean(final QuestionPattern pattern){
        final UnitPatternBean patterBean = new UnitPatternBean();
        patterBean.setId(pattern.getPatternId());
        patterBean.setPatronType(pattern.getPatternType());
        patterBean.setLabel(pattern.getLabelQid());
        return patterBean;
    }

    /**
     * Convert {@link Questions} to {@link UnitQuestionBean}.
     * @param questions {@link Questions}
     * @return {@link UnitQuestionBean}
     */
    public static final UnitQuestionBean convertQuestionsToBean(final Questions questions){
        final UnitQuestionBean questionBean = new UnitQuestionBean();
        questionBean.setId(questions.getQid());
        questionBean.setQuestionName(questions.getQuestion());
        questionBean.setUserId(questions.getSecUsersQuestion().getUid());
        questionBean.setStateId(questions.getCatState() == null ? null : questions.getCatState().getIdState());
        return questionBean;
    }

    /**
     * Convert {@link QuestionsAnswers} to {@link UnitAnswersBean}.
     * @param questionsAnswers {@link QuestionsAnswers}
     * @return {@link UnitAnswersBean}.
     */
    public static final UnitAnswersBean convertAnswerToBean(final QuestionsAnswers questionsAnswers){
            final UnitAnswersBean answersBean = new UnitAnswersBean();
            answersBean.setAnswerId(questionsAnswers.getQuestionAnswerId());
            answersBean.setAnswers(questionsAnswers.getAnswer());
            answersBean.setAnswerHash(questionsAnswers.getUniqueAnserHash());
            return answersBean;
    }

    /**
     * Convert {@link TweetPoll} to {@link UnitTweetPoll}.
     * @param poll tweet poll.
     * @return {@link UnitTweetPoll}
     */
    public static final UnitTweetPoll convertTweetPollToBean(final TweetPoll poll){
        final UnitTweetPoll unitTweetPoll = new UnitTweetPoll();
        unitTweetPoll.setId(poll.getTweetPollId());
        unitTweetPoll.setTweetId(poll.getTweetId());
        unitTweetPoll.setScheduleDate(poll.getScheduleDate());
        unitTweetPoll.setAllowLiveResults(poll.getAllowLiveResults());
        unitTweetPoll.setSchedule(poll.getScheduleTweetPoll());
        unitTweetPoll.setPublicationDateTweet(poll.getPublicationDateTweet());
        unitTweetPoll.setPublishPoll(poll.getPublishTweetPoll());
        unitTweetPoll.setResultNotification(poll.getResultNotification());
        unitTweetPoll.setUserId(poll.getTweetOwner().getUid());
        unitTweetPoll.setCloseNotification(poll.getCloseNotification());
        unitTweetPoll.setCompleted(poll.getCompleted());
        unitTweetPoll.setQuestionBean(convertQuestionsToBean(poll.getQuestion()));
        return unitTweetPoll;
    }

    /**
     * Convert {@link Poll} to {@link UnitPoll}
     * @param poll
     * @return unitPoll unitPoll
     */
    public static final UnitPoll convertPollDomainToBean(final Poll poll){
        final UnitPoll unitPoll = new UnitPoll();
        unitPoll.setId(poll.getPollId());
        unitPoll.setCompletedPoll(poll.getPollCompleted());
        unitPoll.setCreationDate(poll.getCreatedAt());
        unitPoll.setQuestionBean(ConvertDomainBean.convertQuestionsToBean(poll.getQuestion()));
       return unitPoll;

    }


    /**
     * Convert Set to Unit Poll bean
     * @param userId user id
     * @return collection of groups beans.
     * @throws Exception
     */
    public static final List<UnitPoll> convertSetToUnitPollBean(final List<Poll> polls){
            final List<UnitPoll> loadListPolls = new LinkedList<UnitPoll>();
            for (Poll poll : polls) {
                loadListPolls.add(ConvertDomainBean.convertPollDomainToBean(poll));
            }
        return loadListPolls;
    }


}


