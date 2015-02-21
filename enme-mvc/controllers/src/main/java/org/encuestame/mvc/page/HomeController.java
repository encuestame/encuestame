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
        final Boolean privateHome = EnMePlaceHolderConfigurer.getBooleanProperty("application.private");
        addi18nProperty(model, "home_item_comments", request, response);
        addi18nProperty(model, "button_vote_error_message", request, response);
        addi18nProperty(model, "button_vote", request, response);
        addi18nProperty(model, "button_voted", request, response);
        addi18nProperty(model, "submited_by", request, response);
        addi18nProperty(model, "home_item_votes", request, response);
        addi18nProperty(model, "home_item_views", request, response);
        addi18nProperty(model, "added", request, response);
        if (privateHome) {
            log.info("signup is disabled");
            return "redirect:/user/signin";
        } else {
            final String view = filterValue(request.getParameter("view"));
            String period = filterValue(request.getParameter("period"));
            period = (period.isEmpty() ? SHOW_ALL_RESULTS : period);
            try {
                model.addAttribute("viewFilter", view);
                model.addAttribute("items", this.filterHomeItems(view, period, EnMeUtils.DEFAULT_START, homeMaxItems, request));
                model.addAttribute("hashTags", getFrontService().getHashTags(this.homeHashtagMaxItems, EnMeUtils.DEFAULT_START, ""));
            } catch (Exception e) {
                log.error(e);
                return "500";
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
            } catch (Exception e) {
                 log.error(e);
                return "404";
            }
            return "question/detail";
    }


    @RequestMapping(value = "/user/welcome", method = RequestMethod.GET)
    public String welcome(
            final ModelMap model,
            HttpServletRequest request,
            HttpServletResponse response) {

            final Boolean welcome = true;
            if (welcome) {
                request.setAttribute("hide_header_menu", true);
                return "user/welcome";
            } else {
                return "redirect:/user/dashboard";
            }

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
                log.debug("user --> " + accountBean);
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
}
