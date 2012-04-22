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
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;

import junit.framework.Assert;

import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
import org.encuestame.persistence.domain.Comment;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.utils.enums.MethodJson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

/**
 * Comment Json Controller TestCase.
 *
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since August 17, 2011
 */
public class CommentJsonControllerTestCase extends AbstractJsonMvcUnitBeans {

    /** {@link TweetPoll} **/
    private TweetPoll tweetPoll;

    /** {@link Comment} **/
    private Comment comment;

    /** {@link Question} **/
    private Question question;

    @Before
    public void initJsonService(){
        this.question = createQuestion("Why the sky is blue?","html");
        this.tweetPoll = createTweetPollPublicated(true, true, new Date(), getSpringSecurityLoggedUserAccount(), this.question);
        this.comment = createDefaultTweetPollComment("My first comment", this.tweetPoll, getSpringSecurityLoggedUserAccount());
    }

    /**
     * Test get comments by unknown tweetPoll json.
     * @throws ServletException
     * @throws IOException
     */
    //@Test
    public void testGetCommentsbyUnknownTweetPoll() throws ServletException, IOException {
        initService("/api/common/comment/comments/tweetpoll.json", MethodJson.GET);
        setParameter("id", "1");
        final JSONObject response = callJsonService();
        final String error = getErrorsMessage(response);
        Assert.assertEquals(error, "tweetpoll [1] is not published");
    }

    /**
     * Test get comments by tweetPoll json.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testGetCommentsbyTweetPoll() throws ServletException, IOException {
        initService("/api/common/comment/comments/tweetpoll.json", MethodJson.GET);
        setParameter("id", this.tweetPoll.getTweetPollId().toString());
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        final JSONArray comments = (JSONArray) success.get("comments");
        Assert.assertEquals(comments.size(), 1);
    }

    /**
     * Test get comments by keyword json.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testGetComments() throws ServletException, IOException{
        createDefaultTweetPollComment("My first comment", this.tweetPoll, getSpringSecurityLoggedUserAccount());
        createDefaultTweetPollComment("My Second comment", this.tweetPoll, getSpringSecurityLoggedUserAccount());
        createDefaultTweetPollComment("My Third comment", this.tweetPoll, getSpringSecurityLoggedUserAccount());
        flushIndexes();
        initService("/api/common/comment/search.json", MethodJson.GET);
        setParameter("keyword", "comment");
        setParameter("limit", "10");
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        final JSONArray comments = (JSONArray) success.get("comments");
        Assert.assertEquals(comments.size(), 4);
    }

    /**
     * Like vote comment json.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testLikeVoteComment() throws ServletException, IOException{
        initService("/api/common/comment/like_vote.json", MethodJson.GET);
        setParameter("commentId", this.comment.getCommentId().toString());
        final JSONObject response = callJsonService();
        assertSuccessResponse(response);
    }

    /**
     * Dislike vote comment json.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testDislikeVoteComment() throws ServletException, IOException{
        initService("/api/common/comment/dislike_vote.json", MethodJson.GET);
        setParameter("commentId", this.comment.getCommentId().toString());
        final JSONObject response = callJsonService();
        assertSuccessResponse(response);
    }

    /**
     * Test create comment json.
     * @throws ServletException
     * @throws IOException
     */
     @Test
     public void testCreateComment() throws ServletException, IOException{
         initService("/api/common/comment/create.json", MethodJson.POST);
         setParameter("comment", "My Comment");
         setParameter("tweetPollId", this.tweetPoll.getTweetPollId().toString());
         final JSONObject response = callJsonService();
         final JSONObject success = getSucess(response);
         final JSONObject comment = (JSONObject) success.get("comment");
         Assert.assertEquals(comment.get("comment").toString(), "My Comment");
        }

    /**
     * Test get top rated comments.
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testGetTopRatedComments() throws ServletException, IOException {
        final Calendar myCal = Calendar.getInstance();
        myCal.add(Calendar.DATE, -1);
        
        createDefaultTweetPollCommentVoted("first comment", tweetPoll,
                getSpringSecurityLoggedUserAccount(), 150L, 420L,
                myCal.getTime());
        
        createDefaultTweetPollCommentVoted("second comment", tweetPoll,
                getSpringSecurityLoggedUserAccount(), 35L, 580L, new Date());
        myCal.add(Calendar.DATE, -2);
        
        createDefaultTweetPollCommentVoted("third comment", tweetPoll,
                getSpringSecurityLoggedUserAccount(), 325L, 70L,
                myCal.getTime());

        initService("/api/common/comment/rate/top.json", MethodJson.GET);
        setParameter("commentOption", "LIKE_VOTE");
        setParameter("max", "10");
        setParameter("start", "0");
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        System.out.println(success);
        final JSONArray comments = (JSONArray) success.get("topComments");
        //System.out.println("COMMENTS JSON TEST-->" + comments.size());
        Assert.assertEquals(comments.size(), 4);

    }
}
