package org.encuestame.mvc.controller;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.security.web.SecurityUtils;
import org.encuestame.core.util.EnMePlaceHolderConfigurer;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.web.frontEnd.WebMessage;
import org.encuestame.utils.web.frontEnd.WebMessage.WebInfoType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Collection of utilities for Controller Views, Error Handlers only for Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Feb 08, 2013 06:31:20 AM
 */
@ControllerAdvice
public abstract class AbstractViewController extends AbstractBaseOperations{

    /**
    *
    */
   @Value("${encuestame.error.level}") protected Integer errorLevel;

   /**
    *
    */
   @Value("${encuestame.error.display_bugtracking}") protected Boolean bugTracking;


     /**
     * Log.
     */
     private Log log = LogFactory.getLog(this.getClass());

             /**
              * Customized error for {@link NoSuchRequestHandlingMethodException}.
              * @param ex {@link NoSuchRequestHandlingMethodException}.
              * @return {@link ResponseEntity}
              */

    @ExceptionHandler(NoSuchRequestHandlingMethodException.class)
    public ResponseEntity<String> handleNoSuchRequestHandlingMethodException(NoSuchRequestHandlingMethodException ex)   {
    	ex.printStackTrace();
       return this.errorManage(ex);
    }


    /**
     * Build a JSON error response based on the Exception message.
     * @param ex {@link Exception}
     * @return {@link ResponseEntity}
     */
    private ResponseEntity<String> errorManage(Exception ex) {
        ex.printStackTrace();
        log.debug("ServletRequestBindingException" + ex.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        final String text_error = ex.getMessage().replace("'", "");
        final String error = "{error : '" + text_error + "', success : null}";
        return new ResponseEntity<String>(error, headers, HttpStatus.BAD_REQUEST);
    }

    /**
     * Customized error for {@link ServletRequestBindingException}.
     * @param ex {@link ServletRequestBindingException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<String> handleServletRequestBindingException(ServletRequestBindingException ex)   {
    	ex.printStackTrace();
        return this.errorManage(ex);
    }

    /**
     * Handler for {@link AccessDeniedException}
     * @param ex exception
     * @return {@link ModelAndView}.
     */
    @SuppressWarnings("static-access")
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleException (final AccessDeniedException ex, HttpServletResponse httpResponse,
            HttpServletRequest request) {
      ex.printStackTrace();
       ModelAndView mav = new ModelAndView();
       mav.setViewName("tilesResolver");
       mav.setViewName("error/denied");
      final Map<String, Object> response = new HashMap<String, Object>();
      response.put("status", httpResponse.SC_FORBIDDEN);
      response.put("session", SecurityUtils.checkIsSessionIsExpired(getSecCtx().getAuthentication()));
      response.put(EnMeUtils.ANONYMOUS_USER, SecurityUtils.checkIsSessionIsAnonymousUser(getSecCtx().getAuthentication()));
      httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
      mav.addObject("error",  response);
      return mav;
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ModelAndView handleException(EnMeExpcetion ex, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("tilesResolver");
        mav.setViewName("error");
        ex.printStackTrace();
        WebMessage emptyError = new WebMessage(WebInfoType.ERROR, ex.getMessage(), ExceptionUtils.getFullStackTrace(ex),
                EnMePlaceHolderConfigurer.getIntegerProperty("encuestame.error.level"),
                EnMePlaceHolderConfigurer.getBooleanProperty("encuestame.error.display_bugtracking"));
        request.setAttribute("message", emptyError);
        return mav;
    }

    /**
     * In progress ENCUESTAME-676
     * @param modelMap
     * @param tweetPoll
     */
    public void setOgMetadataTweetPollInfo(
            final ModelMap modelMap,
            final TweetPoll tweetPoll,
            final HttpServletRequest request) {
        // https://developers.facebook.com/docs/sharing/best-practices?locale=es_LA
        modelMap.put("og_site_title", tweetPoll.getQuestion().getQuestion());
        modelMap.put("og_site_url_image", "");
        modelMap.put("og_site_name", EnMePlaceHolderConfigurer.getProperty("encuestame.site.name"));
        modelMap.put("og_site_description", EnMePlaceHolderConfigurer.getProperty("encuestame.site.description"));
        modelMap.put("fb_app_id", EnMePlaceHolderConfigurer.getProperty("facebook.api.id"));
        modelMap.put("og_site_type", EnMePlaceHolderConfigurer.getProperty("encuestame.site.type"));
        modelMap.put("og_locale", EnMePlaceHolderConfigurer.getProperty("encuestame.lang"));
        if (tweetPoll.getEditorOwner() != null) {
            modelMap.put("og_article_author", tweetPoll.getEditorOwner().getUsername());
        }
        modelMap.put("og_tags", ""); //FIXME: add hashtag list of the tweetpoll
    }
}
