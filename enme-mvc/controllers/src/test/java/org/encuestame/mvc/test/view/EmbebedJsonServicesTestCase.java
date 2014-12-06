/*
 *
 *  * Copyright 2014 encuestame
 *  *
 *  *  Licensed under the Apache License, Version 2.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *
 *  *       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *  Unless required by applicable law or agreed to in writing, software
 *  *  distributed under the License is distributed on an "AS IS" BASIS,
 *  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  See the License for the specific language governing permissions and
 *  *  limitations under the License.
 *
 */

package org.encuestame.mvc.test.view;

import org.encuestame.mvc.test.config.AbstractJSONPMvc;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.TypeSearchResult;
import org.joda.time.DateTime;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import java.util.HashMap;

/**
 * Created by jpicado on 05/12/14.
 */
@Category(DefaultTest.class)
public class EmbebedJsonServicesTestCase extends AbstractJSONPMvc {

    /**
     *
     */
    Poll poll;

    /**
     *
     */
    TweetPoll tweetPoll;

    /**
     *
     */
    UserAccount userAccount;

    /**
     *
     */
    HashTag hashTag;

    /**
     *
     */
    public final String WORDPRESS = "wordpress";

    /**
     *
     */
    public final String IFRAME = "iframe";

    /**
     *
     */
    public final String SCRIPT = "script";

    @Before
    public void initMVc() {
        DateTime dateTime = new DateTime();
        dateTime.minusWeeks(4);
        this.userAccount =  createUserAccount("testUser", createAccount());
        this.poll = createPoll(
                dateTime.toDate(),
                createQuestion("question 1", "Si"),
                userAccount,
                true,
                true);
        this.tweetPoll = createFastTweetPollVotes();
        this.hashTag = createHashTag("test");
    }

    /// Block of test to generate code

    @Test
    public void testJSONPollController() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", poll.getPollId().toString());
        JSONObject data = getCodeWidget(TypeSearchResult.POLL.toWidget(), SCRIPT, hashMap);
        String body = (String) data.get("body");
        validScript(body.toString());
    }

    @Test
    public void testJSONPollWordpressController() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", poll.getPollId().toString());
        JSONObject data = getCodeWidget(TypeSearchResult.POLL.toWidget(), SCRIPT, hashMap);
        String body = (String) data.get("body");
        validScript(body.toString());
    }

    @Test
    public void testJSONPollIframeController() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", poll.getPollId().toString());
        JSONObject data = getCodeWidget(TypeSearchResult.POLL.toWidget(), WORDPRESS, hashMap);
        String body = (String) data.get("body");
        validWordpres(body.toString());
    }

    @Test
    public void testJSONPollResultsScriptController() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", poll.getPollId().toString());
        JSONObject data = getCodeWidget(TypeSearchResult.POLLRESULT.toWidget(), SCRIPT, hashMap);
        String body = (String) data.get("body");
        validScript(body.toString());
    }

    @Test
    public void testJSONPollResultsWordpressController() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", poll.getPollId().toString());
        JSONObject data = getCodeWidget(TypeSearchResult.POLLRESULT.toWidget(), WORDPRESS, hashMap);
        String body = (String) data.get("body");
        validWordpres(body.toString());
    }

    @Test
    public void testJSONPollResultsIframeController() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", poll.getPollId().toString());
        JSONObject data = getCodeWidget(TypeSearchResult.POLLRESULT.toWidget(), IFRAME, hashMap);
        String body = (String) data.get("body");
        validIframe(body.toString());
    }

    @Test
    public void testJSONTweetPollResultsIframeController() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", tweetPoll.getTweetPollId().toString());
        JSONObject data = getCodeWidget(TypeSearchResult.TWEETPOLLRESULT.toWidget(), IFRAME, hashMap);
        String body = (String) data.get("body");
        validBody(body.toString(), "iframe");
    }

    @Test
    public void testJSONTweetPollResultsWordpressController() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", tweetPoll.getTweetPollId().toString());
        JSONObject data = getCodeWidget(TypeSearchResult.TWEETPOLLRESULT.toWidget(), WORDPRESS, hashMap);
        String body = (String) data.get("body");
        validWordpres(body.toString());
    }

    @Test
    public void testJSONTweetPollResultsScriptController() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", tweetPoll.getTweetPollId().toString());
        JSONObject data = getCodeWidget(TypeSearchResult.TWEETPOLLRESULT.toWidget(), SCRIPT, hashMap);
        String body = (String) data.get("body");
        validScript(body.toString());
    }

    @Test
    public void testJSONTweetPollIframeController() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", tweetPoll.getTweetPollId().toString());
        JSONObject data = getCodeWidget(TypeSearchResult.TWEETPOLL.toWidget(), IFRAME, hashMap);
        String body = (String) data.get("body");
        validBody(body.toString(), "iframe");
    }

    @Test
    public void testJSONTweetPollWordpressController() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", tweetPoll.getTweetPollId().toString());
        JSONObject data = getCodeWidget(TypeSearchResult.TWEETPOLL.toWidget(), WORDPRESS, hashMap);
        String body = (String) data.get("body");
        validWordpres(body.toString());
    }

    @Test
    public void testJSONTweetPollScriptController() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", tweetPoll.getTweetPollId().toString());
        JSONObject data = getCodeWidget(TypeSearchResult.TWEETPOLL.toWidget(), SCRIPT, hashMap);
        String body = (String) data.get("body");
        validScript(body.toString());
    }

    //profile

    @Test
    public void testJSONProfileIframeController() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", this.userAccount.getUid().toString());
        JSONObject data = getCodeWidget(TypeSearchResult.PROFILE.toWidget(), IFRAME, hashMap);
        String body = (String) data.get("body");
        validBody(body.toString(), "iframe");
    }


    @Test
    public void testJSONProfileWordpressController() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", this.userAccount.getUid().toString());
        JSONObject data = getCodeWidget(TypeSearchResult.PROFILE.toWidget(), WORDPRESS, hashMap);
        String body = (String) data.get("body");
        validWordpres(body.toString());
    }


    @Test
    public void testJSONProfileScriptController() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", this.userAccount.getUid().toString());
        JSONObject data = getCodeWidget(TypeSearchResult.PROFILE.toWidget(), SCRIPT, hashMap);
        String body = (String) data.get("body");
        validScript(body.toString());
    }

    //hashtag

    @Test
    public void testJSONHashtagIframeController() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", this.userAccount.getUid().toString());
        JSONObject data = getCodeWidget(TypeSearchResult.HASHTAG.toWidget(), IFRAME, hashMap);
        String body = (String) data.get("body");
        validBody(body.toString(), "iframe");
    }


    @Test
    public void testJSONHashtagWordpressController() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", this.userAccount.getUid().toString());
        JSONObject data = getCodeWidget(TypeSearchResult.HASHTAG.toWidget(), WORDPRESS, hashMap);
        String body = (String) data.get("body");
        validWordpres(body.toString());
    }


    @Test
    public void testJSONHashtagScriptController() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", this.userAccount.getUid().toString());
        JSONObject data = getCodeWidget(TypeSearchResult.HASHTAG.toWidget(), SCRIPT, hashMap);
        String body = (String) data.get("body");
        validScript(body.toString());
    }


    // wrong body

    @Test
    public void testJSONWrongBodyController() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", this.userAccount.getUid().toString());
        JSONObject data = getCodeWidget("wrong", SCRIPT, hashMap);
        String body = (String) data.get("body");
        validWrongBody(body.toString());
    }

    @Test
    public void testJSONWrongTypeController() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", this.userAccount.getUid().toString());
        JSONObject data = getCodeWidget(TypeSearchResult.PROFILE.toWidget(), "typeWrong", hashMap);
        String body = (String) data.get("body");
        logPrint("jota "+body);
        validWrongBody(body.toString());
    }

    @Test
    public void testJSONWrongUserIdController() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", "23456");
        JSONObject data = getCodeWidget(TypeSearchResult.PROFILE.toWidget(), SCRIPT, hashMap);
        String body = (String) data.get("body");
        validWrongBody(body.toString());
    }

    //generating widgets

    @Test
    public void testJSONWidgetPoll() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", poll.getPollId().toString());
        validHTMLBody(getWidget(TypeSearchResult.POLL.toWidget(), hashMap));
    }

    @Test
    public void testJSONWidgetPollResults() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", poll.getPollId().toString());
        validHTMLBody(getWidget(TypeSearchResult.POLLRESULT.toWidget(), hashMap));
    }

    @Test
    public void testJSONWidgetTPoll() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", tweetPoll.getTweetPollId().toString());
        validHTMLBody(getWidget(TypeSearchResult.TWEETPOLL.toWidget(), hashMap));
    }

    @Test
    public void testJSONWidgetProfile() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", userAccount.getUid().toString());
        validHTMLBody(getWidget(TypeSearchResult.PROFILE.toWidget(), hashMap));
    }

    @Test
    public void testJSONWidgetTPResults() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", tweetPoll.getTweetPollId().toString());
        validHTMLBody(getWidget(TypeSearchResult.TWEETPOLLRESULT.toWidget(), hashMap));
    }

    @Test
    @Ignore
    //FUTURE: hashtag has not been implemented yet
    public void testJSONWidgetHashtag() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", this.hashTag.getHashTagId().toString());
        validHTMLBody(getWidget(TypeSearchResult.HASHTAG.toWidget(), hashMap));
    }

    /// error managagment

    @Test
    public void testJSONWidgetPollWrongType() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", poll.getPollId().toString());
        validWrongBody(getWidget("wrongType", hashMap));
    }

    @Test
    public void testJSONWidgetPollWrongId() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", "124456");
        validWrongBody(getWidget(TypeSearchResult.TWEETPOLLRESULT.toWidget(), hashMap));
    }

    @Test
    public void testJSONWidgetPollWrongIdAndType() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", "124456");
        validWrongBody(getWidget("xxxxxx", hashMap));
    }

}