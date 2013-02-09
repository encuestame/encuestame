package org.encuestame.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.security.SecurityUtils;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.web.frontEnd.WebMessage;
import org.encuestame.utils.web.frontEnd.WebMessage.WebInfoType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

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
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Customized error for {@link NoSuchRequestHandlingMethodException}.
     * @param ex {@link NoSuchRequestHandlingMethodException}.
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(NoSuchRequestHandlingMethodException.class)
    public ResponseEntity<String> handleNoSuchRequestHandlingMethodException(NoSuchRequestHandlingMethodException ex)   {
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
        WebMessage emptyError = new WebMessage(WebInfoType.ERROR, ex.getMessage(), ExceptionUtils.getFullStackTrace(ex),
                EnMePlaceHolderConfigurer.getIntegerProperty("encuestame.error.level"),
                EnMePlaceHolderConfigurer.getBooleanProperty("encuestame.error.display_bugtracking"));
        request.setAttribute("message", emptyError);
        return mav;
    }
}
