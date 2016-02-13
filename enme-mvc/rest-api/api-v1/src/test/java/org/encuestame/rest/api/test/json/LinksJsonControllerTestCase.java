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
package org.encuestame.rest.api.test.json;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import junit.framework.Assert;

import org.encuestame.mvc.test.config.AbstractJsonV1MvcUnitBeans;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.MethodJson;
import org.encuestame.utils.social.SocialProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * LinksJsonController Test Case.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 11, 2011
 */
@Category(DefaultTest.class)
public class LinksJsonControllerTestCase extends AbstractJsonV1MvcUnitBeans {

    /**
     * {@link TweetPoll}
     */
    private TweetPoll tweetPoll;

    /**
     *
     */
    @Before
    public void initService() {
        final UserAccount user = createUserAccount("jota", createAccount());
        final Question question = createQuestion("question1", user.getAccount());
        this.tweetPoll = createTweetPollPublicated(true, true, new Date(), user, question);
        final SocialAccount socialAccount = createSocialAccount(
                getProperty("twitter.test.token"),
                getProperty("twitter.test.tokenSecret"),
                user,
                getProperty("twitter.test.account"), Boolean.FALSE, SocialProvider.TWITTER);
        createTweetPollSavedPublishedStatus(tweetPoll, "12345", socialAccount, "hi @encuestame !!");
    }


    /**
     * @throws IOException
     * @throws ServletException
     *
     */
    @Test
    public void testLinks() throws ServletException, IOException{
        initService("/api/public/social/links/published.json", MethodJson.GET);
        setParameter("id", tweetPoll.getTweetPollId().toString());
        setParameter("type", "TWEETPOLL");
        //System.out.println("*************************");
        final Map<String, Object> success = getSuccess();
        //System.out.println(success);
        //System.out.println(success.get("links"));
        List d = (List) success.get("links");
        //System.out.println(d.size());
        Assert.assertEquals(d.size(), 0);
    }
}
