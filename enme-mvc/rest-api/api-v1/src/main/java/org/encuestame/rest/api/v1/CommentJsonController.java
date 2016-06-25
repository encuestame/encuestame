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
package org.encuestame.rest.api.v1;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.encuestame.persistence.domain.Comment;
import org.encuestame.util.exception.EnMeNoResultsFoundException;
import org.encuestame.util.exception.EnmeNotAllowedException;
import org.encuestame.utils.EnumerationUtils;
import org.encuestame.utils.enums.CommentOptions;
import org.encuestame.utils.enums.CommentsSocialOptions;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.web.CommentBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Comment Json Controller.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since August 17, 2011
 */
@Controller
public class CommentJsonController extends AbstractJsonControllerV1 {

    /** Log. **/
    private static Log log = LogFactory.getLog(CommentJsonController.class);

            /**  Limit of results by default. **/
    private static final Integer LIMIT_DEFAULT = 10;


    /**
     * @api {get} /api/common/comment/comments/{type}.json Comments by type
     * @apiName GetCommentsTypes
     * @apiGroup Comments
     * @apiDescription Return all comments filtered by type.
     * @apiParam {string="tweetpoll","poll","survey"} type Filter Comments by type.
     * @apiParam {Number} id Unique identifier of the item (Ex. Twtpoll ID,  Poll ID, etc.) for which to retrieve comments
     * @apiParam {Number} [max] Defines the maximum number of results.
     * @apiParam {Number} [start] Defines the starting number of the page of results.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/comment/comments/tweetpoll.json?id=64&max=2&start=0
     * @apiPermission none
     * @apiSuccessExample
		{
			"error": {

			},
			"success": {
				"comments": [
	    			{
	        			"id": 381,
	        			"comment": "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system
						"created_at": "2012-01-24",
	        			"likeVote": 746,
	        			"dislike_vote": 143,
	        			"item_id": 64,
	        			"type": "TWEETPOLL",
	        			"url": "/tweetpoll/64/who-accidentally-started-the-craze-for-tanned-skin%3F/#comment381",
	        			"commented_by": "complete demo username",
	        			"commented_username": "demo3",
	        			"parent_id": null
	    			}
				]
			}
		}
     * @apiSuccess {Object} success
     * @apiSuccess {String} error
     * @apiError tweetpoll <code>id</code> is not published"
     * @apiErrorExample
		{
			"error":{
						"message":"tweetpoll [64] is not published"
					},
			"success":{
			}
		}
     */
    @RequestMapping(value = "/api/common/comment/comments/{type}.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap getCommentsbyItemType(
            @PathVariable String type,
            @RequestParam(value = "id", required = true) Long itemId,
            @RequestParam(value = "max", required = false) Integer max,
            @RequestParam(value = "start", required = false) Integer start,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            final Map<String, Object> jsonResponse = new HashMap<String, Object>();
            final List<Comment> comments = getCommentService().getComments(
                    TypeSearchResult.getTypeSearchResult(type), itemId, limitTotalMax(max),
                    start);
            final List<CommentBean> commentBean = ConvertDomainBean.convertListCommentDomainToBean(comments);
            jsonResponse.put("comments", commentBean);
            setItemResponse(jsonResponse);
        } catch (Exception e) {
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     * @api {get} /api/common/comment/search.json Comments by keyword
     * @apiName GetComments
     * @apiGroup Comments
     * @apiDescription Get a list of comments by keyword. Use keywords to search related comments . Allows you to search the comments that people make. You can see what you are talking about, in real time.
     * @apiParam {Number} [limit]   Maximum number of comments to be displayed as search result
     * @apiParam {String} [keyword] Keyword to search related comments.
     * @apiParam {Number[]} [excludes]
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/comment/search.json?limit=1&keyword=pleasure
     * @apiPermission none
     * @apiSuccessExample
 		{
			"error": { },
			"success": {
    			"comments": [
        			{
            			"id": 3,
            			"comment": "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system
            			"created_at": "2012-08-04",
            			"likeVote": 233,
            			"dislike_vote": 70,
            			"item_id": 1,
            			"type": "TWEETPOLL",
            			"url": "/tweetpoll/1/what-types-of-books-magazines-newspapers-do-you-read%3F/#comment3",
            			"commented_by": "complete demo username",
            			"commented_username": "demo6",
            			"parent_id": null
					}
    			]
			}
		}
     * @apiSuccess {Object} success
     * @apiSuccess {String} error
     */
    @RequestMapping(value = "/api/common/comment/search.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap getComments(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "excludes", required = false) Long[] excludes,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
            try {
                final Map<String, Object> jsonResponse = new HashMap<String, Object>();
                if(limit == null){
                    limit = LIMIT_DEFAULT;
                }
                if(keyword == null || keyword.isEmpty()){
                    jsonResponse.put("comments", ListUtils.EMPTY_LIST);
                    setItemResponse(jsonResponse);
                } else {
                    final List<CommentBean> comments = getCommentService().getCommentsbyKeyword(keyword, limit, null);
                    jsonResponse.put("comments", comments);
                     setItemResponse(jsonResponse);
                }
            } catch (Exception e) {
                 log.error(e);
                 setError(e.getMessage(), response);
            }
            return returnData();
        }


    /**
     * @api {get} /api/common/comments Comments by user
     * @apiName GetUsersComments
     * @apiGroup Comments
     * @apiDescription Return all comments that will be filtered by user.
     * @apiParam {Number} [limit] Defines the maximum number of results.
     * @apiParam {Number} [start] Defines the starting number of the page of results.
     * @apiParam {String="LIKE_VOTE", "DISLIKE_VOTE"} [option] Options available to vote a Comment.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/comments
     * @apiPermission ENCUESTAME_USER
     * @apiError Access is denied
     * @apiErrorExample
		{
			"error":{
						"message":"Access is denied",
						"session":true,
						"status":403,
						"description":"Tu no tienes acceso a este recurso",
						"anonymousUser":true
					}
		}
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/common/comments", method = RequestMethod.GET)
    public @ResponseBody ModelMap getComments(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "start", required = false) Integer start,
            @RequestParam(value = "option", required = false)  List<String> option,

            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
        	final List<CommentOptions> options = ConvertDomainBean.convertToCommentsOptions(option);
            final Map<String, Object> jsonResponse = new HashMap<String, Object>();
            List<CommentBean> comments = getCommentService().getCommentsbyUser(
                    limit, start, options);
            jsonResponse.put("comments", comments);
            setItemResponse(jsonResponse);
        } catch (Exception e) {
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }


    /**
     * @api {put} /api/common/comment/vote/{option} Vote on Comment
     * @apiName putVoteComment
     * @apiGroup Comments
     * @apiDescription Vote a comment with a like or dislike
     * @apiParam {Number} commentId Unique identifier of the item (Ex. Twtpoll ID,  Poll ID, etc.) that will be voted.
     * @apiParam {String="LIKE_VOTE", "DISLIKE_VOTE"} [commentOption] Options available to vote a Comment.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/comment/vote/like_vote
     * @apiPermission none
     */
    @RequestMapping(value = "/api/common/comment/vote/{option}", method = RequestMethod.PUT)
    public @ResponseBody ModelMap voteComment(
            @RequestParam(value = "commentId", required = true) Long commentId,
            @PathVariable String option,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
            try {
                CommentsSocialOptions socialOpt =  EnumerationUtils.getEnumFromString(CommentsSocialOptions.class, option);
                getCommentService().voteCommentSocialOption(commentId, socialOpt);
                setSuccesResponse();
            } catch (Exception e) {
                 log.error(e);
                 setError(e.getMessage(), response);
            }
            return returnData();
        }

    /**
     * @api {post} /api/common/comment/{type}/create.json Create Comment.
     * @apiName postCreateComment
     * @apiGroup Comments
     * @apiDescription Create a Comment.
     * @apiParam {String="tweetpoll","profile","poll","survey"} type
     * @apiParam {String} comment New Comment string
     * @apiParam {Number} tweetPollId - Unique identifier of the element to which an comment will be added.
     * @apiParam {Number} [commentId]- XXX
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/comment/{type}/create.json
     * @apiPermission ENCUESTAME_USER
     */
    @RequestMapping(value = "/api/common/comment/{type}/create.json", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    public @ResponseBody ModelMap createComment(
            @RequestParam(value = "comment", required = true) String mycomment,
            @RequestParam(value = "tweetPollId", required = true) Long tweetPollId,
            @RequestParam(value = "commentId", required = false) Long relatedCommentId,
            @PathVariable String type,
            HttpServletRequest request,
            HttpServletResponse response){
         try {
             final Map<String, Object> jsonResponse = new HashMap<String, Object>();
             jsonResponse.put("comment", createComment(filterValue(mycomment), tweetPollId, type, relatedCommentId, false));
             setItemResponse(jsonResponse);
         } catch (Exception e) {
              log.error(e);
              setError(e.getMessage(), response);
         }
         return returnData();
     }

    /**
     * Create a comment.
     * @param mycomment
     * @param tweetPollId
     * @param type
     * @param relatedCommentId
     * @param published
     * @return
     * @throws EnmeNotAllowedException
     * @throws EnMeNoResultsFoundException
     */
    private CommentBean createComment(
            final String mycomment,
            final Long tweetPollId,
            final String type,
            final Long relatedCommentId , boolean published) throws EnMeNoResultsFoundException, EnmeNotAllowedException {
        final CommentBean bean = new CommentBean();
         final TypeSearchResult typeResult = TypeSearchResult.getTypeSearchResult(filterValue(type));
         bean.setComment(filterValue(mycomment));
         bean.setCreatedAt(Calendar.getInstance().getTime());
         bean.setParentId(relatedCommentId);
         bean.setId(tweetPollId);
         bean.setType(typeResult);
         final Comment comment = getCommentService().createComment(bean);
         return ConvertDomainBean.convertCommentDomainToBean(comment);
    }

    /**
	 * @api {post} /api/admon/comment/{type}/create.json Edit Comment
	 * @apiName postEditComment
	 * @apiGroup Comments
	 * @apiDescription Update Comment
	 * @apiParam {String} comment Personal comment or rating on a particular type of survey.
	 * @apiParam {Number} tweetPollId - XXX
	 * @apiParam {Number} [commentId] - XXX
	 * @apiParam {String} type - XXX
	 * @apiVersion 1.0.0
	 * @apiSampleRequest http://www.encuestame.org/demo/api/admon/comment/{type}/create.json
	 * @apiPermission ENCUESTAME_EDITOR
	 */
    @RequestMapping(value = "/api/admon/comment/{type}/create.json", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ENCUESTAME_EDITOR')")
    public @ResponseBody ModelMap createEditorComment(
            @RequestParam(value = "comment", required = true) String mycomment,
            @RequestParam(value = "tweetPollId", required = true) Long tweetPollId,
            @RequestParam(value = "commentId", required = false) Long relatedCommentId,
            @PathVariable String type,
            HttpServletRequest request,
            HttpServletResponse response){
         try {
             final Map<String, Object> jsonResponse = new HashMap<String, Object>();
             jsonResponse.put("comment", createComment(filterValue(mycomment), tweetPollId, type, relatedCommentId, true));
         } catch (Exception e) {
              log.error(e);
              setError(e.getMessage(), response);
         }
         return returnData();
     }


    /**
  	 * @api {get} /api/common/comment/rate/top.json Top Comments
  	 * @apiName getTopComments
  	 * @apiGroup Comments
  	 * @apiDescription Retrieve the top comments.
  	 * @apiParam {String="LIKE_VOTE", "DISLIKE_VOTE"} [commentOption] Options available to vote a Comment.
  	 * @apiParam {Number} [max]  Maximum number of comments to be displayed as search result.
  	 * @apiParam {Number} [start] Defines the maximum number of results.
  	 * @apiVersion 1.0.0
  	 * @apiSampleRequest http://www.encuestame.org/demo/api/common/comment/rate/top.json?commentOption=LIKE_VOTE&amp;max=10&amp;start=0
  	 * @apiPermission none
  	 * @apiSuccessExample
			{
	    		"error": { },
	    		"success": {
	        		"topComments": [
	            		{
	                		"id": 457,
							"comment": "Comment Test",
	                		"created_at": "2012-09-11",
	                		"likeVote": 1,
	                		"dislike_vote": 1,
	                		"item_id": 78,
	                		"type": "TWEETPOLL",
	                		"url": "/tweetpoll/78/wie-alt-bist-du%3F/#comment457",
							"commented_by": "complete demo username",
							"commented_username": "demo10",
							"parent_id": null
	            		}
	        		]
    			}
			}
  	 */
    @RequestMapping(value = "/api/common/comment/rate/top.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap getTopRatedComments(
            @RequestParam(value = "commentOption", required = false) String commentOption,
            @RequestParam(value = "max", required = false) Integer max,
            @RequestParam(value = "start", required = false) Integer start,
            HttpServletRequest request, HttpServletResponse response) {
        try {

            final Map<String, Object> jsonResponse = new HashMap<String, Object>();
            final List<CommentBean> comments = getCommentService()
                    .getTopRatedComments(
                            EnumerationUtils.getEnumFromString(CommentsSocialOptions.class, commentOption),
                            max, start);
            jsonResponse.put("topComments", comments);
            setItemResponse(jsonResponse);
        } catch (Exception e) {
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }


    /**
  	 * @api {get} /api/common/comment/search/{typeSearch}/{status}/comments.json Comment Status
  	 * @apiName GetTypeCommentsAndStatus
  	 * @apiGroup Comments
  	 * @apiDescription Retrieve Comments by status
  	 * @apiParam {Number} id XXX.
  	 * @apiParam {Number} start Defines the starting number of the page of results.
  	 * @apiParam {Number} maxResults Defines the maximum number of results.
  	 * @apiParam {String="24","7", "30","all","365","1095"} period Filter search by time period.
  	 * @apiParam {String="tweetpoll", "poll", "survey", "profile"} typeSearch Filter comments by type.
  	 * @apiParam {String="restrict", "approve","moderate","published","spam","all"} status Filter comments by state.
  	 * @apiVersion 1.0.0
  	 * @apiSampleRequest http://www.encuestame.org/demo/api/common/comment/search/tweetpoll/moderate/comments.json
  	 * @apiPermission none
  	 */
	@RequestMapping(value = "/api/common/comment/search/{typeSearch}/{status}/comments.json", method = RequestMethod.GET)
	public @ResponseBody ModelMap retrieveCommentsByTypeAndStatus(
			@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "start", required = true) Integer start,
			@RequestParam(value = "max", required = true) Integer maxResults,
			@RequestParam(value = "period", required = true) String period,
			@PathVariable final String typeSearch,
			@PathVariable final String status, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			final TypeSearchResult type = TypeSearchResult
					.getTypeSearchResult(filterValue(typeSearch));

			final CommentOptions statusComm = EnumerationUtils.getEnumFromString(CommentOptions.class, filterValue(status));

			final SearchPeriods searchPeriod = SearchPeriods
					.getPeriodString(filterValue(period));

			final Map<String, Object> jsonResponse = new HashMap<String, Object>();
			final List<CommentBean> commentsByStatus = getCommentService()
					.retrieveCommentsByTypeAndStatus(id, type, maxResults,
							start, statusComm, (searchPeriod));

			jsonResponse.put("commentsbyStatus", commentsByStatus);
			setItemResponse(jsonResponse);
		} catch (Exception e) {
			log.error(e);
			setError(e.getMessage(), response);
		}
		return returnData();
	}
}
