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
package org.encuestame.mvc.test.json;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;

import org.encuestame.mvc.controller.json.MethodJson;
import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

/**
 * LinksJsonController Test Case.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 11, 2011
 */
public class LinksJsonControllerTestCase extends AbstractJsonMvcUnitBeans {

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
        createTweetPollSavedPublishedSTatus(tweetPoll, "12345", socialAccount, "hi @encuestame !!");
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
        final JSONObject response = callJsonService();
        System.out.println(response);
        //{"error":{},"success":{"items":[],"label":"hashTagName","identifier":"id"}}
        final JSONObject success = getSucess(response);
/*        final JSONArray items = (JSONArray) success.get("items");
        final String label = (String) success.get("label");
        final String identifier = (String) success.get("identifier");
        Assert.assertNotNull(items);
        Assert.assertEquals(label, "hashTagName");
        Assert.assertEquals(identifier, "id");
        return items.size();*/
    }
}
