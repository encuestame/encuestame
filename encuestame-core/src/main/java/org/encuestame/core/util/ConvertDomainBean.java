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
package org.encuestame.core.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.persistence.domain.EmailList;
import org.encuestame.persistence.domain.GeoPoint;
import org.encuestame.persistence.domain.GeoPointFolder;
import org.encuestame.persistence.domain.GeoPointType;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.Project;
import org.encuestame.persistence.domain.Status;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.question.QuestionPattern;
import org.encuestame.persistence.domain.security.Group;
import org.encuestame.persistence.domain.security.Permission;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.SocialAccount.TypeAuth;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.dao.IFolder;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.security.ProfileUserAccount;
import org.encuestame.utils.security.SocialAccountBean;
import org.encuestame.utils.web.TypeTreeNode;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.encuestame.utils.web.UnitFolder;
import org.encuestame.utils.web.UnitGroupBean;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.UnitLists;
import org.encuestame.utils.web.UnitLocationBean;
import org.encuestame.utils.web.UnitLocationFolder;
import org.encuestame.utils.web.UnitLocationTypeBean;
import org.encuestame.utils.web.UnitPatternBean;
import org.encuestame.utils.web.UnitPermission;
import org.encuestame.utils.web.UnitPoll;
import org.encuestame.utils.web.UnitProjectBean;
import org.encuestame.utils.web.QuestionBean;
import org.encuestame.utils.web.UnitSessionUserBean;
import org.encuestame.utils.web.UnitSurvey;
import org.encuestame.utils.web.TweetPollBean;
import org.encuestame.utils.web.UserAccountBean;
import org.encuestame.utils.web.UtilTreeNode;


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
    public static final UserAccountBean convertUserDaoToUserBean(final UserAccount domainUser) {
        final UserAccountBean user = new UserAccountBean();
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
     *
     * @param type
     * @return
     */
    public static final	 TypeAuth convertStringToEnum(final String type){
        if(type.equals(TypeAuth.PASSWORD.name())){
            return TypeAuth.PASSWORD;
        }
        else{
            return TypeAuth.OAUTH;
        }
    }


    /**
     * Social Account.
     * @param socialAccount {@link SocialAccount}.
     * @return {@link SocialAccountBean}
     */
    public static final SocialAccountBean convertSocialAccountToBean(final SocialAccount socialAccount){
           final SocialAccountBean socialAccountBean = new SocialAccountBean();
                   socialAccountBean.setAccount(socialAccount.getSocialAccountName());
                   socialAccountBean.setAccountId(socialAccount.getId());
                   socialAccountBean.setToken(socialAccount.getToken());
                   socialAccountBean.setSecretToken(socialAccount.getSecretToken());
                   socialAccountBean.setTypeAccount(socialAccount.getAccounType().toString());
                   socialAccountBean.setType(socialAccount.getType() == null
                          ? SocialAccount.TypeAuth.OAUTH.name() : socialAccount.getType().name());
           return socialAccountBean;
    }

    /**
     * Convert {@link HashTag} to {@link HashTagBean}.
     * @param hashTag name
     * @return
     */
    public static final HashTagBean convertHashTagDomain(final HashTag hashTag){
        final HashTagBean unitHashTag = new HashTagBean();
        unitHashTag.setHashTagName(hashTag.getHashTag());
        unitHashTag.setId(hashTag.getHashTagId());
        return unitHashTag;
    }

    /**
     * Convert List of {@link HashTag} to List of {@link HashTagBean}.
     * @param tags list of tags
     * @return
     */
    public static final  List<HashTagBean> convertListHashTagsToBean(final List<HashTag> tags) {
        final List<HashTagBean> listTags = new ArrayList<HashTagBean>();
        for (HashTag account : tags) {
            listTags.add(ConvertDomainBean.convertHashTagDomain(account));
        }
        return listTags;
    }

    /**
     * Convert List Twitter Accounts.
     * @param accounts
     * @return
     */
    public static final  List<SocialAccountBean> convertListSocialAccountsToBean(final List<SocialAccount> accounts) {
        final List<SocialAccountBean> loadListPermission = new ArrayList<SocialAccountBean>();
        for (SocialAccount account : accounts) {
            loadListPermission.add(ConvertDomainBean.convertSocialAccountToBean(account));
        }
        return loadListPermission;
    }

    /**
     * Email Lists.
     * @param twitterAccounts
     * @return
     */
    public static final UnitLists convertEmailListtoToBean(final EmailList emailLists){
           final UnitLists emailListsBean = new UnitLists();
                  emailListsBean.setCreatedAt(emailLists.getCreatedAt());
                  emailListsBean.setId(emailLists.getIdList());
                  emailListsBean.setListName(emailLists.getListName());
                  emailListsBean.setUserId(emailLists.getUsuarioEmail().getUid());
           return emailListsBean;
    }

    /**
     * Convert List Twitter Accounts.
     * @param accounts
     * @return
     */
    public static final  List<UnitLists> convertEmailListToBean(final List<EmailList> lists) {
        final List<UnitLists> loadEmailLists = new ArrayList<UnitLists>();
        for (EmailList emailList : lists) {
            loadEmailLists.add(ConvertDomainBean.convertEmailListtoToBean(emailList));
        }
        return loadEmailLists;
    }


    /**
     * Convert {@link Account} to {@link UnitSessionUserBean}.
     * @param user user
     * @return {@link UnitSessionUserBean}
     */
    public static final UnitSessionUserBean convertUserSessionToUserBean(final Account user){
        final UnitSessionUserBean sessionUserBean =  new UnitSessionUserBean();
        sessionUserBean.setUserSessionId(user.getUid());
     /*   sessionUserBean.setConsumerTwitterKey(user.getConsumerKey());
        sessionUserBean.setConsumerTwitterSecret(user.getConsumerSecret());
        sessionUserBean.setTwitterPassword(user.getTwitterPassword());
        sessionUserBean.setTwitterAccount(user.getTwitterAccount());
        sessionUserBean.setTwitterTwitterPing(user.getTwitterPing());
     */
        // TODO: Removed by ENCUESTAME-43
        return sessionUserBean;
    }

    /**
     * Convert {@link UserAccount} to {@link UserAccountBean}.
     * @param secUserSecondary {@link UserAccount}.
     * @return {@link UserAccountBean}
     */
    public static final UserAccountBean convertSecondaryUserToUserBean(final UserAccount secUserSecondary){
        final UserAccountBean unitUserBean = new UserAccountBean();
        if(secUserSecondary != null){
            unitUserBean.setId(secUserSecondary.getUid());
            unitUserBean.setName(secUserSecondary.getCompleteName());
            unitUserBean.setEmail(secUserSecondary.getUserEmail());
            unitUserBean.setUsername(secUserSecondary.getUsername());
            unitUserBean.setStatus(secUserSecondary.isUserStatus());
            unitUserBean.setGroupBean(convertGroupDomainToBean(secUserSecondary.getGroup()));
            unitUserBean.setGroupId(secUserSecondary.getGroup() == null ? null : secUserSecondary.getGroup().getGroupId());
            unitUserBean.setListPermission(convertSetToUnitPermission(secUserSecondary.getSecUserPermissions()));
            //System.out.println("Convert Enjoy Date "+secUserSecondary.getEnjoyDate());
            unitUserBean.setDateNew(secUserSecondary.getEnjoyDate());
            //System.out.println("Convert Enjoy Date 1"+unitUserBean.getDateNew());
            unitUserBean.setIpLastLogged(secUserSecondary.getLastIpLogged());
            unitUserBean.setLastTimeLogged(secUserSecondary.getLastTimeLogged());
        }
        return unitUserBean;
    }

    /**
     * Return user account limited profile info.
     * @param secUserSecondary
     * @return
     */
    public static final ProfileUserAccount convertUserAccountToUserProfileBean(final UserAccount secUserSecondary){
        final ProfileUserAccount unitUserBean = new ProfileUserAccount();
        if(secUserSecondary != null){
            unitUserBean.setName(secUserSecondary.getCompleteName());
            unitUserBean.setEmail(secUserSecondary.getUserEmail());
            unitUserBean.setUsername(secUserSecondary.getUsername());
            //TODO: Bug 112, add private, language y bio.
        }
        return unitUserBean;
    }

    /**
     * Convert Basic {@link UserAccount} to {@link UserAccountBean}.
     * @param secUserSecondary {@link UserAccount}.
     * @return {@link UserAccountBean}
     */
    public static final UserAccountBean convertBasicSecondaryUserToUserBean(final UserAccount secUserSecondary){
        final UserAccountBean unitUserBean = new UserAccountBean();
        if(secUserSecondary != null){
            unitUserBean.setId(secUserSecondary.getUid());
            unitUserBean.setName(secUserSecondary.getCompleteName());
            unitUserBean.setEmail(secUserSecondary.getUserEmail());
            unitUserBean.setUsername(secUserSecondary.getUsername());
            unitUserBean.setStatus(secUserSecondary.isUserStatus());
        }
        return unitUserBean;
    }

    /**
     * Convert List of {@link UserAccount} to {@link UserAccountBean}.
     * @param listUsers
     * @return
     */
    public static final List<UserAccountBean> convertCollectionUsersToBean(final Collection<UserAccount> listUsers) {
        final List<UserAccountBean> loadListUsers = new LinkedList<UserAccountBean>();
        for (UserAccount secUserSecondary : listUsers) {
            loadListUsers.add(ConvertDomainBean.convertSecondaryUserToUserBean(secUserSecondary));
        }
        return loadListUsers;
    }

    /**
     * Convert Domain Permission to Bean Permission.
     * @param userId user id
     * @return collection of permission
     * @throws Exception all exceptions.
  */
    public static final Collection<UnitPermission> convertSetToUnitPermission(final Set<Permission> permissions) {
        final Collection<UnitPermission> loadListPermission = new LinkedList<UnitPermission>();
        for (Permission permission : permissions) {
            loadListPermission.add(ConvertDomainBean.convertPermissionToBean(permission));
        }
        return loadListPermission;
    }

    /**
     * Convert set to unit group bean
     * @param userId user id
     * @return collection of groups beans.
     * @throws Exception
     */
    public static final Collection<UnitGroupBean> convertSetToUnitGroupBean(final Set<Group> groups){
            final Collection<UnitGroupBean> loadListGroups = new LinkedList<UnitGroupBean>();
            for (Group groupsList : groups) {
                 loadListGroups.add(ConvertDomainBean.convertGroupDomainToBean(groupsList));
            }
        return loadListGroups;
    }

    /**
     * Convert {@link Group} to {@link UnitGroupBean}
     * @param groupDomain {@link Group}
     * @return {@link UnitGroupBean}
     */
    public static final UnitGroupBean convertGroupDomainToBean(final Group groupDomain) {
        final UnitGroupBean groupBean = new UnitGroupBean();
        if (groupDomain != null) {
            groupBean.setId(groupDomain.getGroupId());
            groupBean.setGroupName(groupDomain.getGroupName());
            groupBean.setGroupDescription(groupDomain.getGroupDescriptionInfo());
            groupBean.setStateId(groupDomain.getIdState());
        }
        return groupBean;
    }

    /**
     * Convert {@link GeoPoint} to {@link UnitLocationBean}.
     * @param location {@link GeoPoint}
     * @return {@link UnitLocationBean}
     */
    public static final UnitLocationBean convertLocationToBean(final GeoPoint location){
        final UnitLocationBean locationBean = new UnitLocationBean();
        locationBean.setId(location.getLocateId());
        locationBean.setStatus(location.getLocationStatus()  == null ? Status.INACTIVE.name() : location.getLocationStatus().name());
        locationBean.setName(location.getLocationDescription());
        locationBean.setLat(location.getLocationLatitude());
        locationBean.setLng(location.getLocationLongitude());
        locationBean.setAccuracy(location.getLocationAccuracy());
        locationBean.setAddress(location.getLocationAddress());
        locationBean.setCountryName(location.getLocationCountryName());
        locationBean.setCountryCode(location.getLocationCountryCode());
        if(location.getTidtype() != null){
            locationBean.setTidtype(location.getTidtype().getLocationTypeId());
        }
        return locationBean;
    }

    /**
     * Convert List of Locations.
     * @param geoPoint List {@link GeoPoint}
     * @return List of {@link UnitLocationBean}
     */
    public static final List<UnitLocationBean> convertListToUnitLocationBean(final List<GeoPoint> geoPoint){
        final List<UnitLocationBean> listLocations = new ArrayList<UnitLocationBean>();
        for (GeoPoint location : geoPoint) {
            listLocations.add(ConvertDomainBean.convertLocationToBean(location));
        }
    return listLocations;
    }

    /**
     * @param locationType {@link GeoPointType}.
     * @return {@link UnitLocationTypeBean}
     */
   public static final UnitLocationTypeBean convertLocationTypeToBean(final GeoPointType locationType){
        final UnitLocationTypeBean locationTypeBean = new UnitLocationTypeBean();
        locationTypeBean.setIdLocType(locationType.getLocationTypeId());
        locationTypeBean.setLocTypeDesc(locationType.getLocationTypeDescription());
        locationTypeBean.setLevel(locationType.getLocationTypeLevel());
        return locationTypeBean;
    }

    /**
     * Convert {@link Project} to {@link UnitProjectBean}
      * @param project {@link UnitProjectBean}
     * @return {@link UnitProjectBean}
     */
    public static final UnitProjectBean convertProjectDomainToBean(final Project project) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_DATE);
        final UnitProjectBean projectBean = new UnitProjectBean();
        projectBean.setName(project.getProjectDescription());
        projectBean.setDateFinish(project.getProjectDateFinish());
        projectBean.setDateInit(project.getProjectDateStart());
        projectBean.setId(project.getProyectId());
        projectBean.setDescription(project.getProjectDescription());
        projectBean.setName(project.getProjectName());
        projectBean.setProjectInfo(project.getProjectInfo());
        if(project.getLead() != null){
            projectBean.setLeader(project.getLead().getUid());
        }
        projectBean.setPriority(project.getPriority().name());
        projectBean.setPublished(project.getPublished());
        projectBean.setState(project.getProjectStatus().name());
        if(project.getProjectDateStart() != null){
            projectBean.setFormatedDateInit(simpleDateFormat.format(project.getProjectDateStart()));
        }
        if(project.getProjectDateFinish() != null){
            projectBean.setFormatedDateFinish(simpleDateFormat.format(project.getProjectDateFinish()));
        }
        projectBean.setHide(project.getHideProject());
        projectBean.setNotify(project.getNotifyMembers());
        //TODO: add other properties.
        log.debug("project bean converted "+projectBean.toString());
        return projectBean;
    }


    /**
     * Convert {@link Permission} to {@link UnitPermission}
     * @param permission permission.
     * @return permBean
     */
    public static final UnitPermission convertPermissionToBean(final Permission permission){
      final UnitPermission permBean = new UnitPermission();
      permBean.setId(permission.getIdPermission());
      permBean.setDescription(permission.getPermissionDescription());
      permBean.setPermission(permission.getPermission().toString());
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
     * Convert {@link Question} to {@link QuestionBean}.
     * @param questions {@link Question}
     * @return {@link QuestionBean}
     */
    public static final QuestionBean convertQuestionsToBean(final Question questions){
        final QuestionBean questionBean = new QuestionBean();
        questionBean.setId(questions.getQid());
        questionBean.setQuestionName(questions.getQuestion());
        questionBean.setUserId(questions.getAccountQuestion().getUid());
        return questionBean;
    }

    /**
     * Convert {@link QuestionAnswer} to {@link QuestionAnswerBean}.
     * @param questionsAnswer {@link QuestionAnswer}
     * @return {@link QuestionAnswerBean}.
     */
    public static final QuestionAnswerBean convertAnswerToBean(final QuestionAnswer questionsAnswer){
            final QuestionAnswerBean answersBean = new QuestionAnswerBean();
            answersBean.setAnswerId(questionsAnswer.getQuestionAnswerId());
            answersBean.setAnswers(questionsAnswer.getAnswer());
            answersBean.setUrl(questionsAnswer.getUrlAnswer());
            answersBean.setAnswerHash(questionsAnswer.getUniqueAnserHash());
            return answersBean;
    }

    /**
     * Convert {@link TweetPoll} to {@link TweetPollBean}.
     * @param poll tweet poll.
     * @return {@link TweetPollBean}
     */
    public static final TweetPollBean convertTweetPollToBean(final TweetPoll poll){
        final TweetPollBean unitTweetPoll = new TweetPollBean();
        unitTweetPoll.setId(poll.getTweetPollId());
        unitTweetPoll.setScheduleDate(poll.getScheduleDate());
        unitTweetPoll.setCreateDate(DateUtil.getFormatDate(poll.getCreateDate()));
        unitTweetPoll.setAllowLiveResults(poll.getAllowLiveResults() == null ? false : poll.getAllowLiveResults());
        unitTweetPoll.setResumeLiveResults(poll.getResumeLiveResults() == null ? false : poll.getResumeLiveResults());
        unitTweetPoll.setSchedule(poll.getScheduleTweetPoll() == null ? false : poll.getScheduleTweetPoll());
        unitTweetPoll.setResultNotification(poll.getResultNotification() == null ? false : poll.getResultNotification());
        unitTweetPoll.setUserId(poll.getTweetOwner().getUid());
        unitTweetPoll.setCaptcha(poll.getCaptcha() == null ? false : poll.getCaptcha());
        unitTweetPoll.setCloseNotification(poll.getCloseNotification() == null ? false : poll.getCloseNotification());
        unitTweetPoll.setFavourites(poll.getFavourites() == null ? false : poll.getFavourites());
        unitTweetPoll.setCompleted(poll.getCompleted() == null ? false : poll.getCompleted());
        unitTweetPoll.setQuestionBean(convertQuestionsToBean(poll.getQuestion()));
        unitTweetPoll.setAllowRepeatedVotes(poll.getAllowRepatedVotes() == null ? false : poll.getAllowRepatedVotes());
        return unitTweetPoll;
    }

    /**
     * Convert {@link Poll} to {@link UnitPoll}.
     * @param poll
     * @return unitPoll unitPoll
     */
    public static final UnitPoll convertPollDomainToBean(final Poll poll){
        final UnitPoll unitPoll = new UnitPoll();
        unitPoll.setId(poll.getPollId());
        unitPoll.setCompletedPoll(poll.getPollCompleted());
        unitPoll.setCreationDate(poll.getCreatedAt());
        unitPoll.setQuestionBean(ConvertDomainBean.convertQuestionsToBean(poll.getQuestion()));
        unitPoll.setCloseNotification(poll.getCloseNotification());
        unitPoll.setPublishPoll(poll.getPublish());
        unitPoll.setShowResultsPoll(poll.getShowVotes());
        unitPoll.setFinishDate(poll.getEndDate());
       return unitPoll;

    }


    /**
     * Convert Set to Unit Poll bean.
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

    /**
     * Convert List of {@link GeoPointFolder}. to List of {@link UnitLocationFolder}.
     * @param GeoPointFolders {@link GeoPointFolder}.
     * @return
     */
    public static final List<UnitLocationFolder> convertListToUnitLocationFolderBean(final List<GeoPointFolder> geoPointFolders){
        final List<UnitLocationFolder> listFolders = new ArrayList<UnitLocationFolder>();
        for (GeoPointFolder locationFolder : geoPointFolders) {
            listFolders.add(ConvertDomainBean.convertGeoPointFolderDomainToBean(locationFolder));
        }
    return listFolders;
    }

    /**
     * Convert {@link GeoPointFolder}. to {@link UnitLocationFolder}.
     * @param geoPointFolder {@link GeoPointFolder}.
     * @return {@link UnitLocationFolder}.
     */
    public static UnitLocationFolder convertGeoPointFolderDomainToBean(final GeoPointFolder geoPointFolder){
        final UnitLocationFolder locationFolder = new UnitLocationFolder();
        locationFolder.setId(geoPointFolder.getLocationFolderId());
        locationFolder.setName(geoPointFolder.getLocationFolderName());
        locationFolder.setType(geoPointFolder.getFolderType().GROUPING.name());
        return locationFolder;
    }


    /**
     * Convert {@link UnitLocationFolder} to {@link UtilTreeNode}.
     * @param unitLocationSubFolder List {@link UnitLocationFolder}.
     * @return List {@link UtilTreeNode}.
     */
    public static List<UtilTreeNode> convertFolderToDragrable(
            final List<UnitLocationFolder> unitFolderLocationBeans,
            final TypeTreeNode typeTreeNode){
        final List<UtilTreeNode> itemDragables = new ArrayList<UtilTreeNode>();
        for (UnitLocationFolder unitLocation : unitFolderLocationBeans) {
            final UtilTreeNode dragable = new UtilTreeNode();
            dragable.setId(unitLocation.getId());
            dragable.setName(unitLocation.getName());
            dragable.setNode(typeTreeNode);
            itemDragables.add(dragable);
        }
        return itemDragables;
    }

     /**
      * Convert {@link UnitLocationBean} to {@link UtilTreeNode}.
      * @param unitLocationSubFolder List of {@link UnitLocationBean}.
      * @return
      */
    public static List<UtilTreeNode> convertItemToDragrable(
             final List<UnitLocationBean> unitLocationBeans,
             final TypeTreeNode typeTreeNode){
         final List<UtilTreeNode> itemDragables = new ArrayList<UtilTreeNode>();
         for (UnitLocationBean unitLocation : unitLocationBeans) {
             final UtilTreeNode dragable = new UtilTreeNode();
             log.info("convertItemToDragrable "+unitLocation.getId() +" "+unitLocation.getName());
             dragable.setId(unitLocation.getId());
             dragable.setName(unitLocation.getName());
             dragable.setNode(typeTreeNode);
             itemDragables.add(dragable);
         }
         log.info("itemDragables "+itemDragables.size());
         return itemDragables;
     }

    /**
     * Convert List to Unit Question bean.
     * @param question question id
     * @return collection of question beans.
     * @throws Exception
     */
    public static final List<QuestionBean> convertListToUnitQuestionBean(final List<Question> questions){
        final List<QuestionBean> loadListQuestions = new LinkedList<QuestionBean>();
            for (Question question : questions) {
                loadListQuestions.add(ConvertDomainBean.convertQuestionsToBean(question));
            }
        return loadListQuestions;
    }

    /**
     * Convert Folder to {@link UnitFolder}.
     * @param folder
     * @return
     */
    public static final UnitFolder convertFolderToBeanFolder(final IFolder folder){
        final UnitFolder unitFolder = new UnitFolder();
        unitFolder.setCreateAt(folder.getCreatedAt());
        unitFolder.setFolderName(folder.getFolderName());
        unitFolder.setId(folder.getId());
        return unitFolder;
    }

    /**
     * Convert List of {@link IFolder} to {@link UnitFolder}.
     * @param folders List of Folders.
     * @return
     */
    public static final List<UnitFolder> convertListToUniUnitFolder(final List<IFolder> folders){
        final List<UnitFolder> folderList = new LinkedList<UnitFolder>();
            for (IFolder folder : folders) {
                folderList.add(ConvertDomainBean.convertFolderToBeanFolder(folder));
            }
        return folderList;
    }

    public static final UnitSurvey convertSurveyDomaintoBean(final Survey survey){

        final UnitSurvey unitSurvey = new UnitSurvey();
        unitSurvey.setSid(survey.getSid());
        unitSurvey.setTicket(survey.getTicket());
        unitSurvey.setStartDate(survey.getStartDate());
        unitSurvey.setEndDate(survey.getEndDate());
        unitSurvey.setDateInterview(survey.getDateInterview());
        unitSurvey.setComplete(survey.getComplete());
        //unitSurvey.setUnitUserBean(ConvertDomainBean.convertUserSessionToUserBean(survey.getSecUsers()));
        unitSurvey.setCustomMessage(survey.getCustomMessage());
        unitSurvey.setCustomStartMessages(survey.getCustomStartMessages());
        //unitSurvey.setCustomFinalMessage(survey.getCustomFinalMessage());
        unitSurvey.setShowProgressBar(survey.getShowProgressBar());
        unitSurvey.setOptionalTitle(survey.getOptionalTitle());
        unitSurvey.setPasswordRestrictions(survey.getPasswordRestrictions());
        unitSurvey.setIpProtection(survey.getIpProtection());
        unitSurvey.setIpRestriction(survey.getIpRestriction());
        unitSurvey.setPassProtection(survey.getPassProtection());
        unitSurvey.setCloseAfterDate(survey.getCloseAfterDate());
        unitSurvey.setClosedDate(survey.getClosedDate());
        unitSurvey.setCloseAfterquota(survey.getCloseAfterquota());
        unitSurvey.setClosedQuota(survey.getClosedQuota());
        unitSurvey.setShowResults(survey.getShowResults());
        unitSurvey.setNumbervotes(survey.getNumbervotes());
        unitSurvey.setHits(survey.getHits());
        unitSurvey.setAdditionalInfo(survey.getAdditionalInfo());
        unitSurvey.setShowAdditionalInfo(survey.getShowAdditionalInfo());
        unitSurvey.setNotifications(survey.getNotifications());
        unitSurvey.setName(survey.getName());
        return unitSurvey;}


}
