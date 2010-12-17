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
import org.encuestame.persistence.domain.CatEmailLists;
import org.encuestame.persistence.domain.CatLocation;
import org.encuestame.persistence.domain.CatLocationFolder;
import org.encuestame.persistence.domain.CatLocationType;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.Project;
import org.encuestame.persistence.domain.Question;
import org.encuestame.persistence.domain.Status;
import org.encuestame.persistence.domain.security.SecGroup;
import org.encuestame.persistence.domain.security.SecPermission;
import org.encuestame.persistence.domain.security.SecUser;
import org.encuestame.persistence.domain.security.SecUserSecondary;
import org.encuestame.persistence.domain.security.SecUserTwitterAccounts;
import org.encuestame.persistence.domain.security.SecUserTwitterAccounts.TypeAuth;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.QuestionPattern;
import org.encuestame.persistence.domain.survey.QuestionsAnswers;
import org.encuestame.persistence.domain.survey.Surveys;
import org.encuestame.persistence.domain.survey.TweetPoll;
import org.encuestame.persistence.dao.IFolder;
import org.encuestame.utils.security.UnitTwitterAccountBean;
import org.encuestame.utils.web.TypeTreeNode;
import org.encuestame.utils.web.UnitAnswersBean;
import org.encuestame.utils.web.UnitFolder;
import org.encuestame.utils.web.UnitGroupBean;
import org.encuestame.utils.web.UnitHashTag;
import org.encuestame.utils.web.UnitLists;
import org.encuestame.utils.web.UnitLocationBean;
import org.encuestame.utils.web.UnitLocationFolder;
import org.encuestame.utils.web.UnitLocationTypeBean;
import org.encuestame.utils.web.UnitPatternBean;
import org.encuestame.utils.web.UnitPermission;
import org.encuestame.utils.web.UnitPoll;
import org.encuestame.utils.web.UnitProjectBean;
import org.encuestame.utils.web.UnitQuestionBean;
import org.encuestame.utils.web.UnitSessionUserBean;
import org.encuestame.utils.web.UnitSurvey;
import org.encuestame.utils.web.UnitTweetPoll;
import org.encuestame.utils.web.UnitUserBean;
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
     * Twitter Account.
     * @param twitterAccounts
     * @return
     */
    public static final UnitTwitterAccountBean convertTwitterAccountToBean(final SecUserTwitterAccounts twitterAccounts){
           final UnitTwitterAccountBean twitterAccountBean = new UnitTwitterAccountBean();
                   twitterAccountBean.setAccount(twitterAccounts.getTwitterAccount());
                   twitterAccountBean.setSecret(twitterAccounts.getConsumerSecret());
                   twitterAccountBean.setKey(twitterAccounts.getConsumerKey());
                   twitterAccountBean.setPin(twitterAccounts.getTwitterPin() == null
                           ? "" : twitterAccounts.getTwitterPin().toString());
                   twitterAccountBean.setAccountId(twitterAccounts.getId());
                   twitterAccountBean.setToken(twitterAccounts.getToken());
                   twitterAccountBean.setSecretToken(twitterAccounts.getSecretToken());
                   twitterAccountBean.setTypeAccount(twitterAccounts.getAccounType().name());
                   twitterAccountBean.setType(twitterAccounts.getType() == null
                          ? SecUserTwitterAccounts.TypeAuth.PASSWORD.name() : twitterAccounts.getType().name());
           return twitterAccountBean;
    }

    /**
     * Convert {@link HashTag} to {@link UnitHashTag}.
     * @param hashTag name
     * @return
     */
    public static final UnitHashTag convertHashTagDomain(final HashTag hashTag){
        final UnitHashTag unitHashTag = new UnitHashTag();
        unitHashTag.setHashTagName(hashTag.getHashTag());
        unitHashTag.setId(hashTag.getHashTagId());
        return unitHashTag;
    }

    /**
     * Convert List of {@link HashTag} to List of {@link UnitHashTag}.
     * @param tags list of tags
     * @return
     */
    public static final  List<UnitHashTag> convertListHashTagsToBean(final List<HashTag> tags) {
        final List<UnitHashTag> listTags = new ArrayList<UnitHashTag>();
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
    public static final  List<UnitTwitterAccountBean> convertListTwitterAccountsToBean(final List<SecUserTwitterAccounts> accounts) {
        final List<UnitTwitterAccountBean> loadListPermission = new ArrayList<UnitTwitterAccountBean>();
        for (SecUserTwitterAccounts account : accounts) {
            loadListPermission.add(ConvertDomainBean.convertTwitterAccountToBean(account));
        }
        return loadListPermission;
    }

    /**
     * Email Lists.
     * @param twitterAccounts
     * @return
     */
    public static final UnitLists convertEmailListtoToBean(final CatEmailLists emailLists){
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
    public static final  List<UnitLists> convertEmailListToBean(final List<CatEmailLists> lists) {
        final List<UnitLists> loadEmailLists = new ArrayList<UnitLists>();
        for (CatEmailLists emailList : lists) {
            loadEmailLists.add(ConvertDomainBean.convertEmailListtoToBean(emailList));
        }
        return loadEmailLists;
    }




    /**
     * Convert {@link SecUser} to {@link UnitSessionUserBean}.
     * @param user user
     * @return {@link UnitSessionUserBean}
     */
    public static final UnitSessionUserBean convertUserSessionToUserBean(final SecUser user){
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
        if(secUserSecondary != null){
            unitUserBean.setId(secUserSecondary.getUid());
            unitUserBean.setName(secUserSecondary.getCompleteName());
            unitUserBean.setEmail(secUserSecondary.getUserEmail());
            unitUserBean.setUsername(secUserSecondary.getUsername());
            unitUserBean.setStatus(secUserSecondary.isUserStatus());
           // unitUserBean.setListGroups(convertSetToUnitGroupBean(secUserSecondary.getSecGroups()));
            unitUserBean.setListPermission(convertSetToUnitPermission(secUserSecondary.getSecUserPermissions()));
        }
        return unitUserBean;
    }
    /**
     * Convert Basic {@link SecUserSecondary} to {@link UnitUserBean}.
     * @param secUserSecondary {@link SecUserSecondary}.
     * @return {@link UnitUserBean}
     */
    public static final UnitUserBean convertBasicSecondaryUserToUserBean(final SecUserSecondary secUserSecondary){
        final UnitUserBean unitUserBean = new UnitUserBean();
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
    public static final Collection<UnitGroupBean> convertSetToUnitGroupBean(final Set<SecGroup> groups){
            final Collection<UnitGroupBean> loadListGroups = new LinkedList<UnitGroupBean>();
            for (SecGroup secGroups : groups) {
                 loadListGroups.add(ConvertDomainBean.convertGroupDomainToBean(secGroups));
            }
        return loadListGroups;
    }

    /**
     * Convert {@link SecGroup} to {@link UnitGroupBean}
     * @param groupDomain {@link SecGroup}
     * @return {@link UnitGroupBean}
     */
    public static final UnitGroupBean convertGroupDomainToBean(final SecGroup groupDomain) {
        final UnitGroupBean groupBean = new UnitGroupBean();
        groupBean.setId(groupDomain.getGroupId());
        groupBean.setGroupName(groupDomain.getGroupName());
        groupBean.setGroupDescription(groupDomain.getGroupDescriptionInfo());
        groupBean.setStateId(groupDomain.getIdState());
        return groupBean;
    }

    /**
     * Convert {@link CatLocation} to {@link UnitLocationBean}.
     * @param location {@link CatLocation}
     * @return {@link UnitLocationBean}
     */
    public static final UnitLocationBean convertLocationToBean(final CatLocation location){
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
     * @param catLocations List {@link CatLocation}
     * @return List of {@link UnitLocationBean}
     */
    public static final List<UnitLocationBean> convertListToUnitLocationBean(final List<CatLocation> catLocations){
        final List<UnitLocationBean> listLocations = new ArrayList<UnitLocationBean>();
        for (CatLocation location : catLocations) {
            listLocations.add(ConvertDomainBean.convertLocationToBean(location));
        }
    return listLocations;
    }

    /**
     * @param locationType {@link CatLocationType}.
     * @return {@link UnitLocationTypeBean}
     */
   public static final UnitLocationTypeBean convertLocationTypeToBean(final CatLocationType locationType){
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
     * Convert {@link SecPermission} to {@link UnitPermission}
     * @param permission permission.
     * @return permBean
     */
    public static final UnitPermission convertPermissionToBean(final SecPermission permission){
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
     * Convert {@link Question} to {@link UnitQuestionBean}.
     * @param questions {@link Question}
     * @return {@link UnitQuestionBean}
     */
    public static final UnitQuestionBean convertQuestionsToBean(final Question questions){
        final UnitQuestionBean questionBean = new UnitQuestionBean();
        questionBean.setId(questions.getQid());
        questionBean.setQuestionName(questions.getQuestion());
        questionBean.setUserId(questions.getSecUsersQuestion().getUid());
        questionBean.setStateId(questions.getCatState() == null ? null : questions.getCatState().getIdState());
        return questionBean;
    }

    /**
     * Convert {@link QuestionsAnswers} to {@link UnitAnswersBean}.
     * @param questionsAnswer {@link QuestionsAnswers}
     * @return {@link UnitAnswersBean}.
     */
    public static final UnitAnswersBean convertAnswerToBean(final QuestionsAnswers questionsAnswer){
            final UnitAnswersBean answersBean = new UnitAnswersBean();
            answersBean.setAnswerId(questionsAnswer.getQuestionAnswerId());
            answersBean.setAnswers(questionsAnswer.getAnswer());
            answersBean.setUrl(questionsAnswer.getUrlAnswer());
            answersBean.setAnswerHash(questionsAnswer.getUniqueAnserHash());
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
     * Convert List of {@link CatLocationFolder}. to List of {@link UnitLocationFolder}.
     * @param catLocationFolders {@link CatLocationFolder}.
     * @return
     */
    public static final List<UnitLocationFolder> convertListToUnitLocationFolderBean(final List<CatLocationFolder> catLocationFolders){
        final List<UnitLocationFolder> listFolders = new ArrayList<UnitLocationFolder>();
        for (CatLocationFolder locationFolder : catLocationFolders) {
            listFolders.add(ConvertDomainBean.convertCatLocationFolderDomainToBean(locationFolder));
        }
    return listFolders;
    }

    /**
     * Convert {@link CatLocationFolder}. to {@link UnitLocationFolder}.
     * @param catLocationFolder {@link CatLocationFolder}.
     * @return {@link UnitLocationFolder}.
     */
    public static UnitLocationFolder convertCatLocationFolderDomainToBean(final CatLocationFolder catLocationFolder){
        final UnitLocationFolder locationFolder = new UnitLocationFolder();
        locationFolder.setId(catLocationFolder.getLocationFolderId());
        locationFolder.setName(catLocationFolder.getLocationFolderName());
        locationFolder.setType(catLocationFolder.getFolderType().GROUPING.name());
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
    public static final List<UnitQuestionBean> convertListToUnitQuestionBean(final List<Question> questions){
        final List<UnitQuestionBean> loadListQuestions = new LinkedList<UnitQuestionBean>();
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

    public static final UnitSurvey convertSurveyDomaintoBean(final Surveys survey){

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