package org.encuestame.mvc.controller;

import net.tanesha.recaptcha.ReCaptcha;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RegisterUserController extends BaseController {

    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private ReCaptcha reCaptcha;

}
