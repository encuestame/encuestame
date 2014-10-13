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

package org.encuestame.mvc.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.security.util.WidgetUtil;
import org.encuestame.core.service.imp.IFrontEndService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.mvc.controller.AbstractViewController;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeSearchException;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.HomeBean;
import org.encuestame.utils.web.UserAccountBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Home or FrontEnd Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 6, 2010 10:58:02 AM
 */
@Controller
public class HomeController extends AbstractViewController {

   /**
    * Log.
    */
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * Default max of home beans to display.
     */
    @Value("${front.home.items}") private Integer homeMaxItems;

    /**
     * Default max of hashtag to display.
     */
    @Value("${front.hashtags.items}") private Integer homeHashtagMaxItems;

    /**
     *
     */
    @Value("${front.profile.items}") private Integer profileDefaultItems;

    /**
     * Default date range.
     */
    private final String SHOW_ALL_RESULTS = "all";

   /**
    * Home Controller.
    * @param model model
    * @return template
    */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String homeController(ModelMap model, HttpServletRequest request,
            HttpServletResponse response) {
        final Boolean privateHome = EnMePlaceHolderConfigurer
                .getBooleanProperty("application.private");
        addi18nProperty(model, "home_item_comments", request, response);
        addi18nProperty(model, "submited_by", request, response);
        addi18nProperty(model, "home_item_votes", request, response);
        addi18nProperty(model, "home_item_views", request, response);
        addi18nProperty(model, "added", request, response);
        if (privateHome) {
            log.debug("signup is disabled");
            return "redirect:/user/signin";
        } else {
            final String view = filterValue(request.getParameter("view"));
            String period = filterValue(request.getParameter("period"));
            period = (period.isEmpty() ? SHOW_ALL_RESULTS : period);
            final IFrontEndService service = getFrontService();
            try {
                if (view.isEmpty()) {
                    model.addAttribute("items", service.getFrontEndItems(period, EnMeUtils.DEFAULT_START , this.homeMaxItems, request));
                } else {
                    if ("tweetpoll".equals(view)) {
                        model.addAttribute("items", ConvertDomainBean
                                .convertTweetPollListToHomeBean(service
                                        .searchItemsByTweetPoll(period, EnMeUtils.DEFAULT_START,
                                                this.homeMaxItems, request)));
                    } else if ("poll".equals(view)) {
                        model.addAttribute("items",
                                ConvertDomainBean
                                        .convertPollListToHomeBean(service
                                                .searchItemsByPoll(period, EnMeUtils.DEFAULT_START,
                                                        this.homeMaxItems)));
                    } else if ("survey".equals(view)) {
                        //TODO: ENCUESTAME-345
                        model.addAttribute("items", ListUtils.EMPTY_LIST);
                    } else {
                        model.addAttribute("items", service
                                .searchItemsByTweetPoll(period, EnMeUtils.DEFAULT_START, this.homeMaxItems,
                                        request));
                    }
                }
                //TODO: review this code, is used?
                model.addAttribute("hashTags", service.getHashTags(this.homeHashtagMaxItems, EnMeUtils.DEFAULT_START, ""));
                //TODO: search hashtags and other information.
                //TODO: comments: ENCUESTAME-346
            } catch (EnMeSearchException e) {
                log.error(e);
                return "error";
            }
            return "home";
        }
    }

    /**
    * Index view.
    * @param model
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap model, HttpServletRequest request,
            HttpServletResponse response) {
        return "redirect:/home";
    }

    /**
     * Humans Txt Definition.
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/humans.txt", method = RequestMethod.GET)
    public String humansTxT(ModelMap model, HttpServletRequest request,
            HttpServletResponse response) {
        return "redirect:/home";
    }

    /**
     * Robots Txt Definition.
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/robots.txt", method = RequestMethod.GET)
    public String robotsTxT(ModelMap model, HttpServletRequest request,
            HttpServletResponse response) {
        return "redirect:/home";
    }

    /**
     * Help View.
     * @param model model
     * @return template
     */
    @RequestMapping(value = "/user/help", method = RequestMethod.GET)
    public String dashBoardController(ModelMap model, UserAccount account) {
        return "user/help";
    }

    /**
     * Display a question view.
     * @param model {@link Model}
     * @param id the question id
     * @param slug the slug question name
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @return the tile view
     */
    @RequestMapping(value = "/question/detail/{id}/{slug}", method = RequestMethod.GET)
    public String questionController(
            final ModelMap model,
            @PathVariable String id,
            @PathVariable String slug,
            HttpServletRequest request,
            HttpServletResponse response) {
            try {
                model.put("question", getSearchService().getQuestionInfo(Long.valueOf(id)));
            } catch (EnMeNoResultsFoundException | NumberFormatException e) {
                 e.printStackTrace();
                 log.error(e);
                return "500";
            }
            return "question/detail";
    }

    /**
     *
     * @param model
     * @param type
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/embebed/iframe/preview/{type}/{id}", method = RequestMethod.GET)
    public String embebedPreviewIframe(
            final ModelMap model,
            @PathVariable String type,
            @PathVariable String id,
            HttpServletRequest request,
            HttpServletResponse response) {
            model.put("id", id);
            model.put("class_type", TypeSearchResult.getCSSClass(TypeSearchResult.getTypeSearchResult(type)));
            model.put("domain", WidgetUtil.getRelativeDomain(request));
            model.put("url", "#");
            return "display/iframe";
    }

    /**
     * Display the user profile.
     * @param model
     * @param username
     * @return
     */
    @RequestMapping(value = "/profile/{username:.*}", method = RequestMethod.GET)
    public String userProfileController(
            final ModelMap model,
            @PathVariable String username,
            HttpServletRequest request,
            HttpServletResponse response) {
        username = filterValue(username);
        try {
        final UserAccountBean accountBean = getSecurityService().searchUserByUsername(username);
            if (accountBean == null) {
                return "404";
            } else {
                //1 - load all items poll / survey / poll for {username} order by date
                //2 - hashtag created by {username}
                //3 - social link published by {username}
                //4 - last comments
                log.debug("user --> "+accountBean);
                model.put("profile", accountBean);
                final List<HomeBean> lastItems = getFrontService().getLastItemsPublishedFromUserAccount(username, this.profileDefaultItems, false,
                                     request);
                model.put("lastItems", lastItems);
                return "profile/view";
            }
        } catch (EnMeNoResultsFoundException e) {
             e.printStackTrace();
             log.error(e);
            return "500";
        }
    }

    /**
     * @param profileDefaultItems the profileDefaultItems to set
     */
    public void setProfileDefaultItems(final Integer profileDefaultItems) {
        this.profileDefaultItems = profileDefaultItems;
    }

    /**
     * @param homeMaxItems the homeMaxItems to set
     */
    public void setHomeMaxItems(final Integer homeMaxItems) {
        this.homeMaxItems = homeMaxItems;
    }

    /**
     * @param homeHashtagMaxItems the homeHashtagMaxItems to set
     */
    public void setHomeHashtagMaxItems(final Integer homeHashtagMaxItems) {
        this.homeHashtagMaxItems = homeHashtagMaxItems;
    }
}
