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
package org.encuestame.mvc.controller.json;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.persistence.domain.Comment;
import org.encuestame.utils.enums.CommentsSocialOptions;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.web.CommentBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Comment Json Controller.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since August 17, 2011
 */
@Controller
public class CommentJsonController extends AbstractJsonController {

    /** Log. **/
    private Logger log = Logger.getLogger(this.getClass());

    /**  Limit of results by default. **/
    private static final Integer LIMIT_DEFAULT = 10;


    /**
     * Get comments by tweetPoll.
     * @param tweetPollId
     * @param max
     * @param start
     * @param type
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/api/common/comment/comments/{type}.json", method = RequestMethod.GET)
    public ModelMap getCommentsbyTweetPoll(
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
            e.printStackTrace();
            setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     * Get comments by keyword.
     * @param limit
     * @param keyword
     * @param excludes
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value = "/api/common/comment/search.json", method = RequestMethod.GET)
    public ModelMap getComments(
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
                log.debug("Limit "+limit);
                log.debug("Keyword "+keyword);
                log.debug("excludes "+excludes);
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
     * Like or dislike vote on comment.
     * @param commentId
     * @param option
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value = "/api/common/comment/{option}.json", method = RequestMethod.GET)
    public ModelMap voteComment(
            @RequestParam(value = "commentId", required = true) Long commentId,
            @PathVariable String option,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
            try {
                CommentsSocialOptions socialOpt =  CommentsSocialOptions.getCommentsSocialOptions(option);
                getCommentService().voteCommentSocialOption(commentId, socialOpt);
                setSuccesResponse();
            } catch (Exception e) {
                 log.error(e);
                 setError(e.getMessage(), response);
            }
            return returnData();
        }

    /**
     *
     * @param mycomment
     * @param tweetPollId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/api/common/comment/create.json", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    public ModelMap createComment(
            @RequestParam(value = "comment", required = true) String mycomment,
            @RequestParam(value = "tweetPollId", required = true) Long tweetPollId,
            HttpServletRequest request,
            HttpServletResponse response){
         try {
             final CommentBean bean = new CommentBean();
             bean.setComment(mycomment);
             bean.setCreatedAt(new Date());
             bean.setId(tweetPollId);
             final Map<String, Object> jsonResponse = new HashMap<String, Object>();
             final Comment comment = getCommentService().createComment(bean);
             jsonResponse.put("comment", comment);
             setItemResponse(jsonResponse);
         } catch (Exception e) {
              log.error(e);
              setError(e.getMessage(), response);
         }
         return returnData();
     }

    /**
     *
     * @param commentOption
     * @param max
     * @param start
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/api/common/comment/rate/top.json", method = RequestMethod.GET)
    public ModelMap getTopRatedComments(
            @RequestParam(value = "commentOption", required = false) String commentOption,
            @RequestParam(value = "max", required = false) Integer max,
            @RequestParam(value = "start", required = false) Integer start,
            HttpServletRequest request, HttpServletResponse response) {
        try {

            final Map<String, Object> jsonResponse = new HashMap<String, Object>();
            final List<CommentBean> comments = getCommentService()
                    .getTopRatedComments(
                            CommentsSocialOptions
                                    .getCommentsSocialOptions(commentOption),
                            max, start);
            jsonResponse.put("topComments", comments);
            setItemResponse(jsonResponse);
        } catch (Exception e) {
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }
}
