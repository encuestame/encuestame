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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.service.imp.IFrontEndService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.mvc.controller.AbstractBaseOperations;
import org.encuestame.mvc.controller.AbstractViewController;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.utils.enums.HitCategory;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.HomeBean;
import org.encuestame.utils.web.HashTagBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * HashTag Controller.
 *
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since Apr 15, 2011
 */
@Controller
public class HashTagController extends AbstractViewController {

    /** Log. **/
    private Log log = LogFactory.getLog(this.getClass());

    /** HashTag max results. **/
    private final Integer MAX_HASHTAG = 80;

    /** HashTag max results. **/
    private final Integer LIMIT_HASHTAG = 15;

    /** INIT_RESULTS **/
    private final Integer INIT_RESULTS = 0;

    /**
     * HashTag List.
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/cloud", method = RequestMethod.GET)
    public String hashTagController(ModelMap model, HttpServletRequest request,
            HttpServletResponse response) {
        final IFrontEndService service = getFrontService();
        final List<HashTagBean> hashTagList = service.getHashTags(MAX_HASHTAG,
                0, "hashTagsCloud"); // TODO: Add to file properties number 20
        model.addAttribute("hashtags", hashTagList);
        return "cloud";
    }

    /**
     * HashTag detail
     *
     * @param model
     * @param request
     * @param response
     * @param name
     * @return
     * @throws EnmeFailOperation
     */
    @RequestMapping(value = "/tag/{name}", method = RequestMethod.GET)
    public String tagController(ModelMap model, HttpServletRequest request,
            HttpServletResponse response, @PathVariable String name) {
        name = filterValue(name);

        final HashTag tag;
        try {

            tag = getFrontService().getHashTagItem(name);
            if (tag == null) {
                return "404";
            } else {
                try {
                     log.debug("tagController tag =>"+tag);
                     final String IP = getIpClient(request);
                     boolean hashTagVisite = getFrontService().checkPreviousHit(IP, tag.getHashTagId(), TypeSearchResult.HASHTAG);
                     // TODO: Check that previous hash Tag hit has been visited the same day.
                     log.debug("hashTagVisite =>"+hashTagVisite);
                     if (!hashTagVisite) {
                         getFrontService().registerHit(null, null, null, tag, IP, HitCategory.VISIT);
                     }
                } catch (Exception e) {
                    log.warn("Imposible register vote " + "tag id: " + tag.getHashTagId());
                }
                final List<HomeBean> lastPublications = getFrontService()
                        .searchLastPublicationsbyHashTag(tag, null, this.INIT_RESULTS,
                                LIMIT_HASHTAG, "hashtag", request);
                log.debug("tagController tweetPollbyTags =>"+lastPublications.size());
                log.debug("tagController tag =>"+tag);
                final HashTagBean bean =  ConvertDomainBean.convertHashTagDomain(tag);
                model.addAttribute("tagName", bean);
                model.addAttribute("tweetPolls", lastPublications);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            return "404";
        }
        return "tag/detail";
    }
}
