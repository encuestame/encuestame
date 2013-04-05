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
package org.encuestame.mvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.encuestame.core.security.SecurityUtils;
import org.encuestame.core.service.MailService;
import org.encuestame.core.service.imp.MailServiceOperations;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.mvc.validator.ValidateOperations;
import org.encuestame.persistence.dao.INotification;
import org.encuestame.persistence.dao.imp.NotificationDao;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.web.UserAccountBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

/**
 * Abstract Json Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 15, 2010 11:31:20 AM
 */
@ControllerAdvice
public abstract class AbstractJsonController extends AbstractBaseOperations{

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /** Model. **/
    private ModelMap jsonMap = new ModelMap();

    /** {@link NotificationDao}. **/
    @Autowired
    private INotification notificationDao;

    /** Success. **/
    private Map<String, Object> sucess = new HashMap<String, Object>();

    /** Error JSON. **/
    private Map<String, Object> error = new HashMap<String, Object>();


    /**
     *  {@link MailService}.
     */
    @Resource()
    private MailServiceOperations mailService;

    /**
     * This is a successful message, when a services got nothing to answer.
     */
    private final Integer SUCCESS_RESPONSE = 0;

    /**
     * This error means a negative response, it's used when the something happends wrong.
     */
    private final Integer ERROR_RESPONSE = -1;

    /**
     * Return Data.
     * @return
     */
    protected ModelMap returnData() {
         Map<String, Object> response = new HashMap<String, Object>();
         Assert.notNull(this.sucess);
         Assert.notNull(this.error);
         response.put("success", this.sucess);
         response.put("error", this.error);
         return this.jsonMap.addAllAttributes(response);
    }

    /**
     * Set Error.
     * @param error error.
     */
    protected void setError(final Object error, final HttpServletResponse response){
         this.error = new HashMap<String, Object>();
         this.error.put("message", error);
         log.error("JSON error: "+error);
         this.sucess =  new HashMap<String, Object>();
         response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Set List of Errors.
     * @param listError
     * @param response
     */
    protected void setError(final HashMap<String, Object> listError,  final HttpServletResponse response){
        this.error = new HashMap<String, Object>();
        this.error.put("message", listError);
        this.sucess =  new HashMap<String, Object>();
        log.error("JSON error: "+error);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
   }

    /**
     * Set Item Response.
     * @param label
     * @param item
     */
    protected void setItemResponse(final String label, final Object item){
         this.sucess.put(label, item);
    }

    /**
     * Set Item Response.
     * @param label
     * @param item
     */
    protected void setItemResponse(final Map<String, Object> map){
         this.error = new HashMap<String, Object>();
         this.sucess = map;
    }

    /**
     * Set Succes Response.
     */
    protected void setSuccesResponse() {
        final Map<String, Object> response = new HashMap<String, Object>();
        response.put("r", this.SUCCESS_RESPONSE);
        setItemResponse(response);
    }

    /**
     * Create a success response with message
     * @param message the message
     */
    protected void setSuccesResponse(final String message) {
        final Map<String, Object> response = new HashMap<String, Object>();
        response.put("r", this.SUCCESS_RESPONSE);
        response.put("message", message == null ? "" : message);
        setItemResponse(response);
    }

    /**
     * Set a failed response.
     */
    protected void setFailedResponse() {
        final Map<String, Object> response = new HashMap<String, Object>();
        response.put("r", this.ERROR_RESPONSE);
        setItemResponse(response);
    }

    /**
     * Set Item Read Store Response.
     * @param label label
     * @param id id
     * @param items items
     */
    protected void setItemReadStoreResponse(final String label, final String id, final Object items){
         final Map<String, Object> store = new HashMap<String, Object>();
         store.put("identifier", id);
         store.put("label", label);
         store.put("items", items);
         setItemResponse(store);
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
            final HttpServletRequest request) {
      ModelAndView mav = new ModelAndView();
      mav.setViewName("jsonView");
      final Map<String, Object> response = new HashMap<String, Object>();
      response.put("message", ex.getMessage());
      response.put("description", getMessage("error.access.denied", request, null));
      response.put("status", httpResponse.SC_FORBIDDEN);
      response.put("session", SecurityUtils.checkIsSessionIsExpired(getSecCtx().getAuthentication()));
      response.put(EnMeUtils.ANONYMOUS_USER, SecurityUtils.checkIsSessionIsAnonymousUser(getSecCtx().getAuthentication()));
      httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
      mav.addObject("error",  response);
      return mav;
    }

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
     * Customized error for {@link EnMeExpcetion}.
     * @param ex {@link EnMeExpcetion}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(EnMeExpcetion.class)
    public ResponseEntity<String> handleEnMeExpcetion(EnMeExpcetion ex)   {
        return this.errorManage(ex);
    }


    /**
     * Get URL Domain.
     * @param request
     * @param realDomain
     * @return
     */
    public String getUrlDomain(final HttpServletRequest request, final Boolean realDomain){
        final StringBuilder builder = new StringBuilder();
        if(realDomain){
            String header = request.getHeader("X-Forwarded-Host");
            log.trace("Host header  "+header);
            if (header != null) {
                    // we are only interested in the first header entry
                    header = new StringTokenizer(header,",").nextToken().trim();
            }
            if (header == null) {
                    header = request.getHeader("Host");
            }
            builder.append(header);
            log.trace("Host "+builder.toString());
        } else {
            builder.append("/");
        }
        return builder.toString();
    }

    /**
     * Validate items based on context.
     * @param context could be signup, update profile or another one.
     * @param type
     * @param value
     * @return
     */
    protected  Map<String, Object> validate(final String context, final String type, String value, final  HttpServletRequest request) {
        value = value == null ? "" : value;
        final Map<String, Object> jsonResponse = new HashMap<String, Object>();
        final ValidateOperations validateOperations = new ValidateOperations(getSecurityService());
        boolean valid = false;
        if ("email".equals(type)) {
            if (value.isEmpty() || value.length() < ValidateOperations.MIN_EMAIL_LENGTH) {
                 log.debug("validate email emtpy");
                jsonResponse.put("msg", getMessage("secure.email.emtpy", request, null));
            } else {
                valid = validateOperations.validateUserEmail(value, null);
                log.debug("validate EMAIL"+valid);
                if (valid) {
                    jsonResponse.put("msg", getMessage("secure.email.valid", request, null));
                } else {
                    jsonResponse.put("msg", getMessage("secure.email.notvalid", request, null));
                }
            }
        } else if("username".equals(type)) {
            valid = validateOperations.validateUsername(value, null);
            if(value.isEmpty() || value.length() < ValidateOperations.MIN_USERNAME_LENGTH) {
                log.debug("validate username emtpy");
                jsonResponse.put("msg", getMessage("secure.username.empty", request, null));
            } else {
                log.debug("validate username NO emtpy");
                if (!valid) {
                    jsonResponse.put("msg", getMessage("secure.user.notvalid", request, null));
                    final List<String> suggestions = new ArrayList<String>();
                    for (int i = 0; i < 5; i++) {
                        suggestions.add(value+RandomStringUtils.randomAlphabetic(ValidateOperations.LENGTH_RANDOM_VALUE));
                    }
                    jsonResponse.put("suggestions", suggestions);
                } else {
                    jsonResponse.put("msg", getMessage("secure.username.valid", request, null));
                }
                jsonResponse.put("valid", valid);
            }
        } else if("realName".equals(type)) {
            if (value.isEmpty()){
                valid = false;
                jsonResponse.put("msg", getMessage("secure.realName.empty", request, null));
            } else {
                valid = true;
                jsonResponse.put("msg", getMessage("secure.realName.valid", request, null));
            }
        } else {
            jsonResponse.put("msg", getMessage("secure.type.not.valid", request, null));
        }
        jsonResponse.put("valid", valid);
        jsonResponse.put("color", "#RRR");
        return jsonResponse;
    }

    /**
     *
     * @param label
     * @param object
     */
    public void setSingleResponse(final String label, Object object) {
        final Map<String, Object> jsonResponse = new HashMap<String, Object>();
        jsonResponse.put(label, object);
        setItemResponse(jsonResponse);
    }

    /**
     * Get User.
     * @param userId user Id.
     * @return
     * @throws EnMeNoResultsFoundException exception
     */
    public UserAccountBean getUser(final Long userId) throws EnMeNoResultsFoundException{
        Assert.notNull(userId);
        return getSecurityService().getUserCompleteInfo(userId, getUserPrincipalUsername());
    }

    /**
     * @return the notificationDao
     */
    public INotification getNotificationDao() {
        return notificationDao;
    }

    /**
     * @param notificationDao the notificationDao to set
     */
    public void setNotificationDao(INotification notificationDao) {
        this.notificationDao = notificationDao;
    }

    /**
     * @return the mailServiceOperations
     */
    @Autowired
    public MailServiceOperations getMailService() {
        return mailService;
    }

    /**
     * @param mailServiceOperations the mailServiceOperations to set
     */
    public void setMailService(final MailServiceOperations mailServiceOperations) {
        this.mailService = mailServiceOperations;
    }
}
