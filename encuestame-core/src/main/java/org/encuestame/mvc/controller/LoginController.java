package org.encuestame.mvc.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.security.ISecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Login Controller.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since April 30, 2010 21:13:00 PM
 * @version $Id: $
 */

@Controller
public class LoginController {

    private Log log = LogFactory.getLog(this.getClass());

    private ISecurityContext securityContext;

    @RequestMapping("/login.do")
    public String login(ModelMap model) {
            log.info("user "+securityContext.getContext().getAuthentication().getName());
        return "";
    }

    @RequestMapping("/accessDenied.do")
    public ModelAndView accessDenied() {
        return new ModelAndView("redirect:/index.do");
    }

}
