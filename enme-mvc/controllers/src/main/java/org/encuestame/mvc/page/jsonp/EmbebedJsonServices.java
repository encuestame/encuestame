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

package org.encuestame.mvc.page.jsonp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.security.util.WidgetUtil;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.encuestame.mvc.page.jsonp.JavascriptEmbebedBody;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.enums.EmbeddedType;
import org.encuestame.utils.enums.PictureType;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.HomeBean;
import org.encuestame.utils.web.PollDetailBean;
import org.encuestame.utils.web.TweetPollDetailBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmbebedJsonServices extends AbstractJsonControllerV1 {

    /**
     *
     */
    @Autowired
    private VelocityEngine velocityEngine;

    /**
     *
     */
    private final String CODE_TEMPLATES = "/org/encuestame/business/widget/code/templates/";

    /**
     *
     */
    private final String HTML_TEMPLATES = "/org/encuestame/business/widget/templates";

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * Generate the body of selected item.
     * @param itemId
     * @param callback
     * @param request
     * @param response
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/api/jsonp/generate/code/{type}/embedded", method = RequestMethod.GET)
    public void embedded(
            @RequestParam(value = "id", required = true) Long itemId,
            @PathVariable final String type,
            @RequestParam(value = "callback", required = true) String callback,
            @RequestParam(value = "embedded_type", required = false) String embedded,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        PrintWriter out = response.getWriter();
        String text = "";
        final JavascriptEmbebedBody embebedBody = new JavascriptEmbebedBody();
        final Map model = new HashMap();
        final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            @SuppressWarnings("rawtypes")
            final String domain = WidgetUtil.getRelativeDomain(request);
            final TypeSearchResult typeItem = TypeSearchResult.getTypeSearchResult(type);
            if (typeItem != null) {
                final EmbeddedType embeddedType = EmbeddedType.getEmbeddedType(embedded);
                response.setContentType("text/javascript; charset=UTF-8");
                model.put("domain", domain);
                model.put("embedded_type", embeddedType.toString().toLowerCase());
                model.put("typeItem", typeItem.toString().toLowerCase());
                model.put("itemId", itemId);
                model.put("class_type", TypeSearchResult.getCSSClass(typeItem));
                model.put("domain_config", WidgetUtil.getDomain(request, true));
                if (TypeSearchResult.TWEETPOLL.equals(typeItem)) {
                    final TweetPoll tp = getTweetPollService().getTweetPollById(itemId);
                    model.put("url", EnMeUtils.createTweetPollUrlAccess(domain, tp));
                } else if (TypeSearchResult.POLL.equals(typeItem)) {
                    final Poll poll = getPollService().getPollById(itemId);
                    model.put("url", EnMeUtils.createUrlPollAccess(domain, poll));
                } else if (TypeSearchResult.TWEETPOLLRESULT.equals(typeItem)) {
                    final TweetPoll tp = getTweetPollService().getTweetPollById(itemId);
                    model.put("url", EnMeUtils.createTweetPollUrlAccess(domain, tp));
                } else if (TypeSearchResult.POLLRESULT.equals(typeItem)) {
                    final Poll poll = getPollService().getPollById(itemId);
                    model.put("url", EnMeUtils.createUrlPollAccess(domain, poll));
                } else if (TypeSearchResult.HASHTAG.equals(typeItem)) {
                    //FUTURE:
                    model.put("url", "");
                } else if (TypeSearchResult.PROFILE.equals(typeItem)) {
                    //FUTURE: should be username instead ID
                    final UserAccount user = getSecurityService().getUserbyId(itemId);
                    model.put("url", domain + "/profile/" + user.getUsername());
                }
                text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, CODE_TEMPLATES + embeddedType.toString().toLowerCase() + "_code.vm", "utf-8", model);
                String string = new String(text.getBytes("UTF-8"));
                embebedBody.setBody(string);
                final String json = ow.writeValueAsString(embebedBody);
                out.print(callback + "(" + json + ")");
            } else {
                createWrongBody(model, embebedBody);
                final String json = ow.writeValueAsString(embebedBody);
                out.print(callback + "(" + json + ")");
            }
        } catch (Exception e) {
            try {
                createWrongBody(model, embebedBody);
                final String json = ow.writeValueAsString(embebedBody);
                out.print(callback + "(" + json + ")");
            } catch (Exception ex) {
                ex.printStackTrace();
                log.fatal("creating wrong body has failed");
            }
        }
    }

    /**
     * Create a wrong body template.
     * @param model
     * @param embebedBody
     * @return
     */
    private void createWrongBody(Map model, JavascriptEmbebedBody embebedBody) throws Exception{
        String text = VelocityEngineUtils.mergeTemplateIntoString(
                velocityEngine, CODE_TEMPLATES + "wrong_type.vm", "utf-8", model);
        String string = new String(text.getBytes("UTF-8"));
        embebedBody.setBody(string);
    }

    /**
     * Generate the body of selected item.
     * @param pollId
     * @param callback
     * @param request
     * @param response
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/api/jsonp/{type}/embedded", method = RequestMethod.GET)
    public void typeJavascriptJSONP(
            // :TODO: Change parameter name pollId to id.
            @RequestParam(value = "id", required = true) Long pollId,
            @PathVariable final String type,
            @RequestParam(value = "callback", required = true) String callback,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        PrintWriter out = response.getWriter();
        String text = "";
        final int max_results = 1;
        final TypeSearchResult typeItem = TypeSearchResult.getTypeSearchResult(type);
        final JavascriptEmbebedBody embebedBody = new JavascriptEmbebedBody();
        final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        @SuppressWarnings("rawtypes")
        final Map model = new HashMap();
        logPrint("typeItem::==" + typeItem);
        try {
            final String domain = WidgetUtil.getDomain(request, true);
            final String logo = EnMePlaceHolderConfigurer.getProperty("application.logo.icon");
            model.put("logo_enme", domain + "/resources/" + logo);
            model.put("domain", domain);
            model.put("typeItem", typeItem == null ? "" : typeItem.toString().toLowerCase());
            model.put("itemId", pollId);
            model.put("domain_config", domain);
            response.setContentType("text/javascript; charset=UTF-8");
            if (TypeSearchResult.TWEETPOLL.equals(typeItem)) {
                // generate tweetpoll body
                // generate tweetpoll body
                final TweetPoll tweetPoll = getTweetPollService().getTweetPollById(pollId);
                model.put("tp", tweetPoll);
                model.put("editorOwner", tweetPoll.getEditorOwner());
                model.put("votes", tweetPoll.getLikeVote());
                model.put("hits", tweetPoll.getHits());
                model.put("date_published", EnMeUtils.formatDate(tweetPoll.getCreateDate(), "HH:mm - d MMMM yyyy"));
                model.put("owner_picture", domain + "/picture/profile/" + tweetPoll.getEditorOwner().getUsername() + "/thumbnail");
                model.put("owner_profile_url", domain + "/profile/" + tweetPoll.getEditorOwner().getUsername());
                StringBuffer buffer = new StringBuffer();
                String q = tweetPoll.getQuestion().getQuestion();
                final List<TweetPollSwitch> answers = getTweetPollService().getTweetPollSwitch(tweetPoll);
                final Set<HashTag> hashTags = tweetPoll.getHashTags();
                buffer.append(EnMeUtils.generateBodyTweetPollasHtml(domain, tweetPoll, q,answers, hashTags));
                model.put("body_text", buffer.toString());
                model.put("url_tpoll", domain +
                        "/tweetpoll/" + tweetPoll.getTweetPollId() + "/" + tweetPoll.getQuestion().getSlugQuestion());
                text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, HTML_TEMPLATES + "/tweetpoll_form.vm", "utf-8", model);
                final String string = new String(text.getBytes("UTF-8"));
                embebedBody.setBody(string);
            } else if (TypeSearchResult.TWEETPOLLRESULT.equals(typeItem)) {
                final TweetPoll tpoll = getTweetPollService().getTweetPollById(pollId);
                final TweetPollDetailBean tpollDetail = getTweetPollService().getTweetPollDetailInfo(pollId);
                model.put("owner_picture", domain + "/picture/profile/" + tpoll.getEditorOwner().getUsername() + "/thumbnail");
                model.put("editorOwner", tpoll.getEditorOwner());
                model.put("question", tpoll.getQuestion());
                model.put("url", EnMeUtils.createTweetPollUrlAccess(domain, tpoll));
                model.put("answersList", tpollDetail.getResults());
                model.put("date_published", EnMeUtils.formatDate(tpoll.getCreateDate(), "HH:mm - d MMMM yyyy"));
                text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, HTML_TEMPLATES + "/tweetpoll_votes.vm", "utf-8", model);
                embebedBody.setAditionalInfo(tpollDetail.getResults());
                final String string = new String(text.getBytes("UTF-8"));
                embebedBody.setBody(string);
            } else if (TypeSearchResult.POLL.equals(typeItem)) {
                // generate poll body
                final Poll poll = getPollService().getPollById(pollId);
                // ENCUESTAME-664 temporally disabled
                if (poll.getIsPasswordProtected() != null && poll.getIsPasswordProtected()) {
                    throw new EnMeExpcetion("password protected not embeddable");
                }
                final PollDetailBean detailBean = getPollService().getPollDetailInfo(poll.getPollId());
                model.put("owner_picture", domain + "/picture/profile/" + poll.getEditorOwner().getUsername() + "/thumbnail");
                model.put("editorOwner", poll.getEditorOwner());
                model.put("title", poll.getQuestion().getQuestion());
                model.put("date_published", EnMeUtils.formatDate(poll.getCreateDate(), "HH:mm - d MMMM yyyy"));
                model.put("poll", poll);
                model.put("action", WidgetUtil.getDomain(request) + "/poll/vote/post");
                model.put("detailBean", detailBean);
                model.put("vote_title", "Vote");
                text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, HTML_TEMPLATES + "/poll_form.vm", "utf-8", model);
                final String string = new String(text.getBytes("UTF-8"));
                embebedBody.setBody(string);
            }  else if (TypeSearchResult.POLLRESULT.equals(typeItem)) {
                // generate poll body
                final Poll poll = getPollService().getPollById(pollId);
                final PollDetailBean detailBean = getPollService().getPollDetailInfo(poll.getPollId());
                model.put("owner_picture", domain + "/picture/profile/" + poll.getEditorOwner().getUsername() + "/thumbnail");
                model.put("editorOwner", poll.getEditorOwner());
                model.put("question", poll.getQuestion());
                model.put("url", EnMeUtils.createUrlPollAccess(domain, poll));
                model.put("answersList", detailBean.getResults());
                model.put("date_published", EnMeUtils.formatDate(poll.getCreateDate(), "HH:mm - d MMMM yyyy"));
                text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, HTML_TEMPLATES + "/tweetpoll_votes.vm", "utf-8", model);
                embebedBody.setAditionalInfo(detailBean.getResults());
                final String string = new String(text.getBytes("UTF-8"));
                embebedBody.setBody(string);
            } else if (TypeSearchResult.HASHTAG.equals(typeItem)) {
                // generate hashtag body
                model.put("hellow", "world");
                text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, HTML_TEMPLATES + "/hashtag.vm", "utf-8", model);
                final String string = new String(text.getBytes("UTF-8"));
                embebedBody.setBody(string);
            } else if (TypeSearchResult.PROFILE.equals(typeItem)) {
                final UserAccount user = getSecurityService().getUserbyId(pollId);
                model.put("owner_picture", domain + "/picture/profile/" + user.getUsername() + "/thumbnail");
                model.put("editorOwner", user);
                model.put("profile", user.getUsername());
                model.put("owner_profile_url", domain + "/profile/" + user.getUsername());
                model.put("picture", getPictureService().getProfilePicture(user.getUsername(), PictureType.DEFAULT));
                model.put("total_tweets", getFrontService().getTotalItemsPublishedByType(user, Boolean.TRUE, TypeSearchResult.TWEETPOLL));
                model.put("total_poll", getFrontService().getTotalItemsPublishedByType(user, Boolean.TRUE, TypeSearchResult.POLL));
                model.put("total_survey", getFrontService().getTotalItemsPublishedByType(user, Boolean.TRUE, TypeSearchResult.SURVEY));
                final List<HomeBean> lastPublication = getFrontService().getLastItemsPublishedFromUserAccount(
                        user.getUsername(),
                        max_results,
                        Boolean.FALSE,
                        request);
                if (lastPublication.size() >= 1) {
                    model.put("last_publication", lastPublication.get(0));
                }
                text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, HTML_TEMPLATES + "/profile.vm", "utf-8", model);
                final String string = new String(text.getBytes("UTF-8"));
                embebedBody.setBody(string);
            } else {
                createWrongBody(model, embebedBody);
            }
            final String json = ow.writeValueAsString(embebedBody);
            out.print(callback + "(" + json + ")");
        } catch (Exception e) {
            //e.printStackTrace();
            log.error(e);
            try {
                createWrongBody(model, embebedBody);
                final String json = ow.writeValueAsString(embebedBody);
                out.print(callback + "(" + json + ")");
            } catch (Exception ex) {
                ex.printStackTrace();
                log.fatal("creating wrong body has failed");
            }
        }
    }

    /**
     *
     * @param pollId
     * @param callback
     * @param request
     * @param response
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
//    @RequestMapping(value = "/api/jsonp/poll.json", method = RequestMethod.GET)
//    public void pollJSONP(
//            @RequestParam(value = "id", required = true) Long pollId,
//            @RequestParam(value = "callback", required = true) String callback,
//            HttpServletRequest request, HttpServletResponse response)
//            throws JsonGenerationException, JsonMappingException, IOException {
//        PrintWriter out = response.getWriter();
//        try {
//            response.setContentType("text/javascript; charset=UTF-8");
//            getPollService().getPollById(pollId);
//            out.print(callback + "(" + Boolean.TRUE + ")");
//            // buffer.append(callback + "(" + Boolean.TRUE + ")");
//        } catch (EnMeExpcetion e) {
//            out.print(callback + "(" + Boolean.FALSE + ")");
//        }
//    }

    /**
     *
     * @param username
     * @param callback
     * @param request
     * @param response
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    //@RequestMapping(value = "/api/jsonp/user.json", method = RequestMethod.GET)
//    public void userJSONP(
//            @RequestParam(value = "id", required = true) String username,
//            @RequestParam(value = "callback", required = true) String callback,
//            HttpServletRequest request, HttpServletResponse response)
//            throws JsonGenerationException, JsonMappingException, IOException {
//        PrintWriter out = response.getWriter();
//        try {
//            response.setContentType("text/javascript; charset=UTF-8");
//            getSecurityService().getUserAccount(username);
//            out.print(callback + "(" + Boolean.TRUE + ")");
//            // buffer.append(callback + "(" + Boolean.TRUE + ")");
//        } catch (EnMeExpcetion e) {
//            out.print(callback + "(" + Boolean.FALSE + ")");
//        }
//    }
}
