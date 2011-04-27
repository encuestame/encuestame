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
package org.encuestame.business.service;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.business.mail.MailServiceImpl;
import org.encuestame.business.setup.DirectorySetupOperations;
import org.encuestame.core.files.PathUtil;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.core.util.MD5Utils;
import org.encuestame.core.util.MessageSourceFactoryBean;
import org.encuestame.persistence.domain.Email;
import org.encuestame.persistence.domain.EmailList;
import org.encuestame.persistence.domain.EmailSubscribe;
import org.encuestame.persistence.domain.notifications.Notification;
import org.encuestame.persistence.domain.notifications.NotificationEnum;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.web.UnitEmails;
import org.encuestame.utils.web.UnitLists;
import org.encuestame.utils.web.UserAccountBean;
import org.hibernate.HibernateException;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.http.AccessToken;

/**
 * Service.
 * @author Picado, Juan juan@encuestame.org
 * @since 22/05/2009 1:02:45
 */
public abstract class AbstractBaseService extends AbstractConfigurationService {

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * {@link MessageSourceFactoryBean}.
     */
    private MessageSourceFactoryBean messageSource;

    /**
     *  {@link MailServiceImpl}.
     */
    private MailServiceImpl serviceMail;

    /**
     * Constructor.
     */
    public AbstractBaseService() {}

    /**
     * Getter.
     * @return {@link MessageSourceFactoryBean}
     */
    public MessageSourceFactoryBean getMessageSource() {
        return messageSource;
    }


    /**
     * Setter.
     * @param messageSource {@link MessageSourceFactoryBean}
     */
    public void setMessageSource(MessageSourceFactoryBean messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Getter by propertie Id.
     * @param propertieId propertieId
     * @return value of propertie
     */
    public String getMessageProperties(String propertieId) {
        return getMessageSource() == null ? propertieId : getMessageSource()
                .getMessage(propertieId, null, null);
    }

    /**
     * @return {@link MailServiceImpl}.
     */
    public MailServiceImpl getServiceMail() {
        return serviceMail;
    }
    /**
     * @param serviceMail {@link MailServiceImpl}.
     */
    public void setServiceMail(MailServiceImpl serviceMail) {
        this.serviceMail = serviceMail;
    }

    /**
     * Create Email List.
     * @param unitLists
     * @return
     * @throws EnMeExpcetion
     */
    public UnitLists createEmailLists(final UnitLists unitLists) throws EnMeExpcetion{
        if (unitLists!=null){
            try {
                final EmailList listsDomain = new EmailList();
                listsDomain.setCreatedAt(unitLists.getCreatedAt());
                listsDomain.setListName(unitLists.getListName());
                listsDomain.setUsuarioEmail(getAccountDao().getUserById(unitLists.getUserId()));
                getEmailListsDao().saveOrUpdate(listsDomain);
                unitLists.setId(listsDomain.getIdList());
              } catch (HibernateException e) {
                  throw new EnMeExpcetion(e);
              } catch (Exception e) {
                  throw new EnMeExpcetion(e);
              }
              return unitLists;
          } else {
              throw new EnMeExpcetion("Email List is null");
          }
      }

    /**
     * Create Emails.
     * @param unitEmails
     * @return
     * @throws EnMeExpcetion
     */
    public UnitEmails createEmail(final UnitEmails unitEmails) throws EnMeExpcetion{
        if(unitEmails!= null){
            try {//
                final EmailList emailList = new EmailList();
                final String codeSubscribe = MD5Utils.md5(String.valueOf(System.currentTimeMillis()));
                final Email emailsDomain = new Email();
                emailsDomain.setEmail(unitEmails.getEmailName());
                emailsDomain.setSubscribed(Boolean.FALSE); //By Default is FALSE, user need subscribe.
                emailsDomain.setIdListEmail(emailList);
                getEmailListsDao().saveOrUpdate(emailsDomain);
                unitEmails.setIdEmail(emailsDomain.getIdEmail());
                //TODO: Necesitamos crear el registro con el hash !!
                final EmailSubscribe subscribe = new EmailSubscribe();
                subscribe.setEmail(emailsDomain);
                subscribe.setList(emailList);
                subscribe.setHashCode(codeSubscribe);
                getEmailListsDao().saveOrUpdate(subscribe);
                this.serviceMail.send(emailsDomain.getEmail(),"Invitation to Subscribe Encuestame List","Invitation to Subscribe");
                //TODO:Enviamos correo al usuario para que confirme su subscripcion.
            }
            catch (Exception e) {
                throw new EnMeExpcetion(e);
            }
            return unitEmails;
        } else {
            throw new EnMeExpcetion("Email is null");
        }
    }

    /**
     * Create Notification.
     * @param description
     * @param secUser
     * @return
     */
    public Notification createNotification(final NotificationEnum description, final String additional,  final Account account){
        final Notification notification = new Notification();
        notification.setDescription(description);
        notification.setAccount(account);
        notification.setAdditionalDescription(additional);
        getNotificationDao().saveOrUpdate(notification);
        return notification;
    }

    /**
     *
     * @param subscriptionCode
     * @param subscriptionOption
     * @return
     * @throws EnMeExpcetion
     */
    public Boolean subscribeEmails(final String subscriptionCode, final String subscriptionOption) throws EnMeExpcetion{
        Boolean success = false;
        EmailSubscribe subscribe = getEmailListsDao().getSubscribeAccount(subscriptionCode);
        if (subscribe!=null){
            try {
                   Email emails = new Email();
                   if(subscriptionOption.equals("subscribe"))
                   {
                       emails.setSubscribed(Boolean.TRUE);
                   }
                   else {
                       emails.setSubscribed(Boolean.FALSE);
                   }
                   success = Boolean.TRUE;
                }
            catch (Exception e) {
                 throw new EnMeExpcetion(e);
            }
            return success;
        }

        else {
            throw new EnMeExpcetion("Email Not Found in Subscribe List");
        }
     }

    /**
     * Get Access Token.
     * @param token
     * @param tokenSecret
     * @param pin
     * @return
     * @throws TwitterException
     */
    @SuppressWarnings("unused")
    private AccessToken getAccessToken(
            final Twitter twitter,
            final String token,
            final String tokenSecret) throws TwitterException {

            final AccessToken accessToken =  twitter.getOAuthAccessToken(token, tokenSecret);
            log.info(String.format("Auth Token {%s Token Secret {%s ", accessToken.getToken(), accessToken.getTokenSecret()));
            twitter.setOAuthAccessToken(accessToken);
            if (accessToken != null) {
                    log.info(String.format("Got access token for user %s", accessToken.getScreenName()));
            }
            return accessToken;
    }


    /**
     * Load list of users.
     * @return list of users with groups and permission
     * @throws EnMeNoResultsFoundException
     * @throws EnMeExpcetion excepcion
     */
    public List<UserAccountBean> loadListUsers(
           final String currentUsername,
           final Integer maxResults,
           final Integer start) throws EnMeNoResultsFoundException {
        log.info("currentUsername "+currentUsername);
        List<UserAccountBean> loadListUsers = new LinkedList<UserAccountBean>();
        final UserAccount userAccount = this.getUserAccount(currentUsername);
        if(userAccount != null){
            final Collection<UserAccount> listUsers = getAccountDao()
                 .retrieveListOwnerUsers(userAccount.getAccount(), start, maxResults);
                log.info("list users "+listUsers.size());
                loadListUsers = ConvertDomainBean.convertCollectionUsersToBean(listUsers);
        }
        return loadListUsers;
    }

    /**
     * Validate Owner Group.
     * @param user
     * @param loggedUserName
     * @return
     */
    private Boolean validateOwnerGroup(final UserAccount user, final String loggedUserName){
        Boolean validate = Boolean.FALSE;
        final UserAccount owner = getAccountDao().getUserByUsername(loggedUserName);
        if(user != null && owner != null){
            if(user.getAccount().getUid().equals(owner.getAccount().getUid())){
                validate = Boolean.TRUE;
            }
        }
        log.debug("validateOwnerGroup info "+validate);
        return validate;
    }

    /**
     * Get User Complete Info.
     * @param currentUsername
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public UserAccountBean getUserCompleteInfo(final Long userId, final String currentUsername) throws EnMeNoResultsFoundException {
        UserAccountBean userInfo = null;
        final UserAccount user = getAccountDao().getUserAccountById(userId);
        if(this.validateOwnerGroup(user, currentUsername)){
            userInfo =  ConvertDomainBean.convertSecondaryUserToUserBean(user);
            log.debug("getUserCompleteInfo info "+userInfo.getId());
        }
        return userInfo;
    }

    /**
     * Get Validated User.
     * @param userId
     * @param currentUsername
     * @return
     */
    public UserAccount getValidateUser(final Long userId, final String currentUsername){
        final UserAccount user = getAccountDao().getUserAccountById(userId);
        UserAccount expetedUser = null;
        if(this.validateOwnerGroup(user, currentUsername)){
            expetedUser = user;
        }
        return expetedUser;
    }

    /**
     *
     * @param account
     */
    public void createDirectoryAccount(final Account account){
        final File file = new File(DirectorySetupOperations.getProfilesDirectory(account.getUid().toString()));
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
