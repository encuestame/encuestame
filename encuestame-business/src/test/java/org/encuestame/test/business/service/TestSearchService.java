package org.encuestame.test.business.service;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import junit.framework.Assert;

import org.encuestame.business.service.imp.IPollService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.Email;
import org.encuestame.persistence.domain.EmailList;
import org.encuestame.persistence.domain.Question;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.encuestame.persistence.domain.survey.QuestionPattern;
import org.encuestame.test.business.service.config.AbstractServiceBase;
import org.encuestame.utils.web.UnitPoll;
import org.encuestame.utils.web.UnitQuestionBean;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestSearchService extends AbstractServiceBase {

    /** {@link Account} **/
    private Account user;

    /** {@link Question} **/
    private Question question;

    /** {@link QuestionPattern} **/
    private QuestionPattern questionPattern;

    /** {@link IPollService} **/
    @Autowired
    private IPollService pollService;

    /** {@link Poll} **/
    private Poll poll;

    private UserAccount userAccount;

    /**
     * Init.
     */
    @Before
    public void serviceInit(){
        this.user = createUser("testEncuesta", "testEncuesta123");
        this.userAccount = createUserAccount("diana", this.user);
        this.question = createQuestion("Why the roses are red?","html");
        this.questionPattern = createQuestionPattern("html");
        this.poll = createPoll(new Date(), this.question, "FDK125", this.user, Boolean.TRUE, Boolean.TRUE);
      }

    /**
     * Test createPoll.
     * @throws Exception exception
     */
    @Test
    public void testcreatePoll() throws Exception{
        final UnitQuestionBean question = ConvertDomainBean.convertQuestionsToBean(this.question);
        final UnitPoll unitPoll = ConvertDomainBean.convertPollDomainToBean(this.poll);
        unitPoll.setQuestionBean(question);
        assertNotNull(unitPoll);
        this.pollService.createPoll(unitPoll, this.userAccount.getUsername(), this.question);
    }



}
