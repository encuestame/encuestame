package org.encuestame.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterUserController {

    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping(value = "/register.me", method = RequestMethod.POST)
    public String doPost(
        HttpServletRequest req,
        @RequestParam("articleId") long articleId,
        @RequestParam("recaptcha_challenge_field") String challenge,
        @RequestParam("recaptcha_response_field") String response,
        //@ModelAttribute("comment") Comment comment,
        BindingResult result) {

    // Validate the form (other than the reCAPTCHA)
    //validator.validate(comment, result);

    // Validate the reCAPTCHA
    String remoteAddr = req.getRemoteAddr();
    ReCaptchaImpl reCaptcha = new ReCaptchaImpl();

    // Probably don't want to hardcode your private key here but
    // just to get it working is OK...
    reCaptcha.setPrivateKey("<your_private_key>");

    ReCaptchaResponse reCaptchaResponse =
        reCaptcha.checkAnswer(remoteAddr, challenge, response);

    if (!reCaptchaResponse.isValid()) {
        FieldError fieldError = new FieldError(
            "comment",
            "captcha",
            response,
            false,
            new String[] { "errors.badCaptcha" },
            null,
            "Please try again.");
        result.addError(fieldError);
    }

    // If there are errors, then validation fails.
    if (result.hasErrors()) {
        String path = "";
        log.debug("Form validation error; forwarding to " + path);
        return "forward:" + path;
    }

    // Else validation succeeds.
    log.debug("Form validation passed");
    //comment.setIpAddress(remoteAddr);
    //comment.setDate(new Date());

    // Post the comment
    log.debug("Posting the comment");
    //articleService.postComment(articleId, comment);
    log.debug("Comment posted");

    return "redirect:" + "" + "#comments";
}

}
