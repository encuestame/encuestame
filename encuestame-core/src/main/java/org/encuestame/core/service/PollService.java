/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */

package org.encuestame.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.mail.MailServiceImpl;
import org.encuestame.core.persistence.pojo.CatEmails;
import org.encuestame.core.persistence.pojo.Poll;
import org.encuestame.core.persistence.pojo.Questions;
import org.encuestame.core.persistence.pojo.QuestionsAnswers;
import org.encuestame.core.service.util.ConvertDomainBean;
import org.encuestame.core.service.util.MD5Utils;
import org.encuestame.utils.web.UnitAnswersBean;
import org.encuestame.utils.web.UnitLists;
import org.encuestame.utils.web.UnitPoll;
import org.encuestame.utils.web.UnitQuestionBean;
import org.springframework.stereotype.Service;

/**
 * Poll Service.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since  April 01, 2010
 * @version $Id: $
 */
@Service
public class PollService extends AbstractSurveyService implements IPollService{


    private Log log = LogFactory.getLog(this.getClass());

    /**
     *
     */
    public final void createPoll(final UnitPoll pollBean, final String currentUser) throws EnMeExpcetion{
        try {
            final Poll pollDomain = new Poll();
            final Questions question = getQuestionDao().retrieveQuestionById(pollBean.getQuestionBean().getId());
            if (question == null){
                 throw new EnMeExpcetion("question not found");
            }
            pollDomain.setCreatedAt(pollBean.getCreationDate());
            pollDomain.setPollOwner(getUser(currentUser).getSecUser());
            pollDomain.setPollCompleted(Boolean.FALSE);
            pollDomain.setCreatedAt(new Date());
            pollDomain.setPollHash(MD5Utils.md5(RandomStringUtils.randomAlphanumeric(500)));
            pollDomain.setQuestion(question);
            pollDomain.setCloseNotification(pollBean.getCloseNotification());
            pollDomain.setEndDate(pollBean.getFinishDate());
            pollDomain.setPublish(pollBean.getPublishPoll());
            pollDomain.setShowVotes(pollBean.getShowResultsPoll());
            this.getPollDao().saveOrUpdate(pollDomain);

        } catch (Exception e) {
            throw new EnMeExpcetion(e);
        }
    }

//FIXME: Reutilize method
    /**
     * Save Question Answer.
     * @param answerBean answer
     * @param question question
     */
    public void saveAnswer(final UnitAnswersBean answerBean, final Questions question){
            final QuestionsAnswers answer = new QuestionsAnswers();
            answer.setQuestions(question);
            answer.setAnswer(answerBean.getAnswers());
            answer.setUniqueAnserHash(answerBean.getAnswerHash());
            this.getQuestionDao().saveOrUpdate(answer);
            answerBean.setAnswerId(answer.getQuestionAnswerId());
    }

    //FIXME: Reutilize method
    /**
     * Create Question.
     * @param questionBean {@link UnitQuestionBean}.
     * @throws EnMeExpcetion exception
     */
    public void createQuestion(final UnitQuestionBean questionBean) throws EnMeExpcetion{
            try{
                final Questions question = new Questions();
                question.setQuestion(questionBean.getQuestionName());
               // question.setSecUsersQuestion(getClientDao().getClientById((questionBean.getUserId()));
                question.setQidKey(MD5Utils.md5(RandomStringUtils.randomAlphanumeric(500)));
                question.setSharedQuestion(false);
                getQuestionDao().saveOrUpdate(question);
                questionBean.setId(question.getQid());
                for (final UnitAnswersBean answerBean : questionBean.getListAnswers()) {
                    this.saveAnswer(answerBean, question);
                }
            }
            catch (Exception e) {
                throw new EnMeExpcetion(e);
            }
    }

    /**
     * List Poll By User.
     * @param currentUser currentUser
     * @return unitPoll
     */

    public List<UnitPoll> listPollByUser(final String currentUser){
        final List<UnitPoll> unitPoll = new ArrayList<UnitPoll>();
        final List<Poll> polls = getPollDao().findAllPollByUserId(getPrimaryUser(currentUser));
         for (Poll poll : polls) {
             unitPoll.add(ConvertDomainBean.convertPollDomainToBean(poll));
        }
        return unitPoll;
    }

    /**
     * List Poll by Question Keyword.
     * @param currentUser currentUser
     * @param keyword QuestionKeyword
     * @return {@link UnitPoll}
     */
    public List<UnitPoll> listPollbyQuestionKeyword(final String currentUser, final String keyword){
        final List<UnitPoll> unitPoll = new ArrayList<UnitPoll>();
        final List<Poll> polls = getPollDao().getPollsByQuestionKeyword(keyword);
               for (Poll poll : polls) {
                   unitPoll.add(ConvertDomainBean.convertPollDomainToBean(poll));
               }
               return unitPoll;
           }

    public void updateAnswersPoll( ){


    }

    public void updateQuestionPoll(UnitQuestionBean unitQuestionPoll)
            throws EnMeExpcetion {
        // TODO Auto-generated method stub

    }

    public String createUrlPoll(String domain, String hashUrl,
            String currentUser) {
        StringBuffer urlBuffer = new StringBuffer(domain);
        urlBuffer.append("/".concat(currentUser));
        urlBuffer.append("/".concat(hashUrl));
        return urlBuffer.toString();
    }

    public void publicPollByList(String urlPoll, UnitLists emailList) {
        final List<CatEmails> emailsList = getEmailListsDao().findEmailsByListId(emailList.getId());
        if(emailList !=null){
                 for (CatEmails emails : emailsList) {
                   getServiceMail().send(emails.getEmail(),"New Poll", urlPoll);

                  }


         }
         else{
             System.out.println("EN ELSE PUBLIC POLL");
             log.warn("Not Found Emails in your EmailList");
        }

    }

}
