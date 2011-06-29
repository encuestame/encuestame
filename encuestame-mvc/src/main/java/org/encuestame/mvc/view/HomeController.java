/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */

package org.encuestame.mvc.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.business.service.imp.IFrontEndService;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.mvc.controller.AbstractBaseOperations;
import org.encuestame.persistence.exception.EnMeSearchException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Home or FrontEnd Controller.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since Mar 6, 2010 10:58:02 AM
 */
@Controller
public class HomeController extends AbstractBaseOperations {

    /**
    * Log.
    */
    private Log log = LogFactory.getLog(this.getClass());

    /**
    * Home Controller.
    *
    * @param model
    *            model
    * @return template
    */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String homeController(ModelMap model, HttpServletRequest request,
            HttpServletResponse response) {
        final Boolean privateHome = EnMePlaceHolderConfigurer
                .getBooleanProperty("application.private");
        if (privateHome) {
            log.debug("signup is disabled");
            return "redirect:/user/signin";
        } else {
            final String view = filterValue(request.getParameter("view"));
            final String period = filterValue(request.getParameter("period"));
            final IFrontEndService service = getFrontService();
            try {
                if (view.isEmpty()) {
                    model.addAttribute("items", service.searchItemsByTweetPoll(period, 20, request));
                } else {
                    if ("tweetpoll".equals(view)){
                        model.addAttribute("items", service.searchItemsByTweetPoll(period, 20, request));
                    } else if("poll".equals(view)){
                        model.addAttribute("items", service.searchItemsByPoll(period, 20));
                    } else if("survey".equals(view)){
                        model.addAttribute("items", service.searchItemsByTweetPoll(period, 20, request));
                    }
                }
                model.addAttribute("hashTags", service.getHashTags(30, 0));
                //TODO: search hashtags and other information.
            } catch (EnMeSearchException e) {
                return "error";
            }
            return "home";
        }
    }

    /**
    * Index view.
    *
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
}
