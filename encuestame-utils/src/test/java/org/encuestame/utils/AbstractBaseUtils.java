package org.encuestame.utils;

import java.util.Date;
import java.util.List;

import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.json.QuestionPatternBean;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.encuestame.utils.web.UnitEmails;
import org.encuestame.utils.web.UnitGroupBean;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.UnitLists;
import org.encuestame.utils.web.UnitLocationBean;
import org.encuestame.utils.web.UnitLocationTypeBean;
import org.encuestame.utils.web.UnitPoll;
import org.encuestame.utils.web.UnitProjectBean;

import junit.framework.TestCase;

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
    public UnitPoll createUnitPoll(
            final Boolean completedPoll,
            final Date creationDate,
            final Long id,
            final QuestionBean questionBean){
        final UnitPoll unitPoll = new UnitPoll();
        unitPoll.setCompletedPoll(completedPoll);
        unitPoll.setCreationDate(creationDate);
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

     public UnitPoll createUnitPollComplete(
             final Boolean closeNotification,
             final Boolean completedPoll,
             final Date creationDate,
             final Date finishDate,
             final List<HashTagBean> hashTags,
             final Long idPoll,
             final Boolean publishPoll,
             final QuestionBean questionBean,
             final Boolean showResultsPoll){
         final UnitPoll unitPollComplete = new UnitPoll();
         unitPollComplete.setCloseNotification(closeNotification);
         unitPollComplete.setCompletedPoll(completedPoll);
         unitPollComplete.setCreationDate(creationDate);
         unitPollComplete.setFinishDate(finishDate);
         unitPollComplete.setHashTags(hashTags);
         unitPollComplete.setId(idPoll);
         unitPollComplete.setPublishPoll(publishPoll);
         unitPollComplete.setQuestionBean(questionBean);
         unitPollComplete.setShowResultsPoll(showResultsPoll);
         return unitPollComplete;

     }

}
