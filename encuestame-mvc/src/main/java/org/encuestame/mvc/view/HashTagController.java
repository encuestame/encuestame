/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
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
import org.encuestame.mvc.controller.AbstractBaseOperations;
import org.encuestame.persistence.exception.EnMeSearchException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * HashTag Controller.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since Apr 15, 2011
 */
@Controller
public class HashTagController extends AbstractBaseOperations{

    /** Log. **/
        private Log log = LogFactory.getLog(this.getClass());

        @RequestMapping(value = "/cloud", method = RequestMethod.GET)
        public String hashTagController(ModelMap model, HttpServletRequest request,
                HttpServletResponse response) {
            final IFrontEndService service = getFrontService();
           model.addAttribute("hashtags", service.getHashTags(20, 0));
           return "cloud";
        }
}
