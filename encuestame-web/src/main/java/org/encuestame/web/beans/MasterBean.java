/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.web.beans;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.encuestame.business.service.FrontEndService;
import org.encuestame.business.service.LocationService;
import org.encuestame.business.service.PollService;
import org.encuestame.business.service.SecurityService;
import org.encuestame.business.service.ServiceManager;
import org.encuestame.business.service.SurveyService;
import org.encuestame.business.service.TweetPollService;
import org.encuestame.business.service.imp.IFrontEndService;
import org.encuestame.business.service.imp.ILocationService;
import org.encuestame.business.service.imp.IPollService;
import org.encuestame.business.service.imp.ISecurityService;
import org.encuestame.business.service.imp.IServiceManager;
import org.encuestame.business.service.imp.ISurveyService;
import org.encuestame.business.service.imp.ITweetPollService;
import org.encuestame.core.exception.EnMeDomainNotFoundException;
import org.encuestame.core.security.EnMeUserDetails;
import org.encuestame.core.util.MD5Utils;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.utils.web.UnitHashTag;
import org.encuestame.utils.web.UnitLocationFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;


/**
 * Master Bean.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since 26/04/2009
 * @version $Id$
 */
public class MasterBean extends AbstractJSFContext{

    /** {@link ApplicationContext}. **/
    private ApplicationContext appContext;

    /** {@link ServiceManager} **/
    @Autowired
    protected IServiceManager servicemanager;

    /** Log. **/
    protected Logger log = Logger.getLogger(this.getClass());

    /** User Session Id. **/
    protected Long userSessionId;

    /** Short Number String. **/
    private Integer shortNumberString = 30;

    /** Gravatar Url. **/
    private static final String GRAVATAR_URL = "http://www.gravatar.com/avatar/";

    /** Gravatar Size. **/
    public static final Integer GRAVATAR_SIZE = 64;

    /**
     * Constructor.
     */
    public MasterBean() {
        super();
    }

    /**
     * Get Gravatar.
     * @param email email.
     * @param size size.
     */
    public final String getGravatar(final String email, Integer size){
        final String hash = MD5Utils.md5Hex(email);
         StringBuilder gravatarUl = new StringBuilder();
         gravatarUl.append(MasterBean.GRAVATAR_URL);
         gravatarUl.append(hash);
         gravatarUl.append("?s=");
         gravatarUl.append(size);
         return gravatarUl.toString();
    }

    /**
     * Get User Session Id.
     */
    protected final Long getUserSessionId(){
        return this.userSessionId;
    }

    /**
     * Description.
     * @param serviceBeanName bean name
     * @return {@link Object}
     */
    public final Object lookupService(String serviceBeanName) {
        return appContext.getBean(serviceBeanName);
    }

    /**
     * Getter {@link ServiceManager}.
     * @return {@link ServiceManager}
     */
    public final IServiceManager getServicemanager() {
        log.debug("IServiceManager "+this.servicemanager);
        return servicemanager;
    }

    /**
     * Setter {@link ServiceManager}.
     * @param servicemanagerBean {@link ServiceManager}
     */
    public final void setServicemanagerBean(final IServiceManager servicemanagerBean) {
        log.debug("SET SERVICE MANAGER "+servicemanagerBean);
        this.servicemanager = servicemanagerBean;
    }

    /**
     * @param propertieId propertie Id
     * @return the localized message if it is exists, otherwise the specified
     *         property id
     */
    public final String getMessageProperties(String propertieId) {
        return  getServicemanager().getMessageSource() == null ? propertieId
                : getServicemanager().getMessageSource().getMessage(
                        propertieId, null, null);
    }

    /**
     * Short Long String.
     * @param string string to abbreviate
     * @return abrreviate string.
     */
    public final String shortLongString(final String string){
        return StringUtils.abbreviate(string, getShortNumberString());
    }

    /**
     * Get Email User Detail.
     * @return
     */
    public final String getSecurityContextEmail(){
        final EnMeUserDetails details = this.getSecurityDetails();
        return details.getUserEmail();
    }

    /**
     * Get {@link UserAccount} by Name.
     * @return {@link UserAccount}
     */
    public final UserAccount getUsernameByName(){
        return getServicemanager().getApplicationServices().getSecurityService().findUserByUserName(this.getUserPrincipalUsername());
    }

    /**
     * Ignored Null.
     * @param attributeValue string value
     * @return string value or empty string
     */
    protected final String ignoreNull(String attributeValue) {
        if (attributeValue == null) {
            attributeValue = "";
        }
        return attributeValue;
    }

    /**
     * Get {@link SecurityService}
     * @return
     */
    protected final ISecurityService getSecurityService(){
        return getServicemanager().getApplicationServices().getSecurityService();
    }

    /**
     * @return the shortNumberString
     */
    public final Integer getShortNumberString() {
        return shortNumberString;
    }

    /**
     * @param shortNumberString the shortNumberString to set
     */
    public final void setShortNumberString(final Integer shortNumberString) {
        this.shortNumberString = shortNumberString;
    }

    /**
     * Get {@link SurveyService}.
     * @return the surveyService {@link SurveyService}.
     */
    public ISurveyService getSurveyService() {
         return getServicemanager().getApplicationServices().getSurveyService();
    }

    /**
     * Get {@link TweetPollService}.
     * @return {@link TweetPollService}.
     */
    public ITweetPollService getTweetPollService() {
         return getServicemanager().getApplicationServices().getTweetPollService();
    }

    /**
     * Get Location Service.
     * @return {@link LocationService}.
     */
    public ILocationService getLocationService(){
        return getServicemanager().getApplicationServices().getLocationService();
    }

    /**
     * Get Poll Services.
     * @return {@link PollService}.
     */
    public IPollService getPollService(){
        return getServicemanager().getApplicationServices().getPollService();
    }

    /**
     * Get {@link FrontEndService}.
     * @return {@link FrontEndService}.
     */
    public IFrontEndService getFrontService(){
        log.debug("Front Service getServicemanager "+getServicemanager());
        log.debug("Front Service getApplicationServices "+getServicemanager().getApplicationServices());
        log.debug("Front Service "+getServicemanager().getApplicationServices().getLocationService());
        return getServicemanager().getApplicationServices().getFrontEndService();
    }

    /**
     * Get Location Folders.
     * @return List of {@link UnitLocationFolder}.
     * @throws EnMeDomainNotFoundException
     */
    public List<UnitLocationFolder> getLocationFoldersByUsername() throws EnMeDomainNotFoundException{
        return  getLocationService().retrieveLocationFolderByUser(getUserPrincipalUsername());
    }


    /**
     * Create Temp Hash Tag.
     * @param name
     * @return
     */
    protected UnitHashTag setTempHashTag(final String name){
        log.debug("createHashTag "+name);
        //Always hash tag should be lower case.
        return new UnitHashTag(StringUtils.deleteWhitespace(name.toLowerCase()));
    }
}
