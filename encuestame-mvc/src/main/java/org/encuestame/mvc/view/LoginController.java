
package org.encuestame.mvc.view;

import org.encuestame.mvc.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Login Controller.
 * @author Picado, Juan juan@encuestame.org
 * @since Mar 6, 2010 10:58:02 AM
 */
@Controller
public class LoginController extends BaseController{

    /**
     * Signin Controller.
     * @param model model
     * @return template
     */
    @RequestMapping(value = "/signin.jspx", method = RequestMethod.GET)
    public String signInController(final ModelMap model) {
        log.debug("login");
        return "signin";
    }

    /**
     * Forgot Password Controller.
     * @param model model
     * @return template
     */
    @RequestMapping(value = "/forgot.jspx", method = RequestMethod.GET)
    public String forgotPasswordController(final ModelMap model) {
        log.debug("forgot password");
        return "forgot";
    }

}
