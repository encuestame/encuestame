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
package org.encuestame.core.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.mail.MailServiceImpl;
import org.encuestame.core.persistence.domain.CatEmailLists;
import org.encuestame.core.persistence.domain.CatEmails;
import org.encuestame.core.persistence.domain.CatSubscribeEmails;
import org.encuestame.core.persistence.domain.SecUser;
import org.encuestame.core.persistence.domain.SecUserSecondary;
import org.encuestame.core.persistence.domain.notifications.Notification;
import org.encuestame.core.service.util.ConvertDomainBean;
import org.encuestame.core.service.util.MD5Utils;
import org.encuestame.core.service.util.MessageSourceFactoryBean;
import org.encuestame.utils.web.UnitEmails;
import org.encuestame.utils.web.UnitLists;
import org.encuestame.utils.web.UnitUserBean;
import org.hibernate.HibernateException;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.http.AccessToken;

/**
 * Service.
 * @author Picado, Juan juan@encuestame.org
 * @since 22/05/2009 1:02:45
 * @version $Id$
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
     *
     */
    public static int TWITTER_AUTH_ERROR = 401;

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
                final CatEmailLists listsDomain = new CatEmailLists();
                listsDomain.setCreatedAt(unitLists.getCreatedAt());
                listsDomain.setListName(unitLists.getListName());
                listsDomain.setUsuarioEmail(getSecUserDao().getUserById(unitLists.getUserId()));
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
                final CatEmailLists emailList = getEmailListsDao().getListEmailById(unitEmails.getListsId());
                final String codeSubscribe = MD5Utils.md5(String.valueOf(System.currentTimeMillis()));
                final CatEmails emailsDomain = new CatEmails();
                emailsDomain.setEmail(unitEmails.getEmailName());
                emailsDomain.setSubscribed(Boolean.FALSE); //By Default is FALSE, user need subscribe.
                emailsDomain.setIdListEmail(emailList);
                getEmailListsDao().saveOrUpdate(emailsDomain);
                unitEmails.setIdEmail(emailsDomain.getIdEmail());
                //Necesitamos crear el registro con el hash !!
                final CatSubscribeEmails subscribe = new CatSubscribeEmails();
                subscribe.setEmail(emailsDomain);
                subscribe.setList(emailList);
                subscribe.setHashCode(codeSubscribe);
                getEmailListsDao().saveOrUpdate(subscribe);
                this.serviceMail.send(emailsDomain.getEmail(),"Invitation to Subscribe Encuestame List","Invitation to Subscribe");

                //Enviamos correo al usuario para que confirme su subscripcion.
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
    public Notification createNotification(final NotificationEnum description, final String additional,  final SecUser secUser){
        final Notification notification = new Notification();
        notification.setDescription(description);
        notification.setSecUser(secUser);
        notification.setAdditionalDescription(additional);
        getNotificationDao().saveOrUpdate(notification);
        return notification;
    }

    public Boolean subscribeEmails(final String subscriptionCode, final String subscriptionOption) throws EnMeExpcetion{
        Boolean success = false;
        CatSubscribeEmails subscribe = getEmailListsDao().getSubscribeAccount(subscriptionCode);
        if (subscribe!=null){
            try {
                   CatEmails emails = new CatEmails();
                   if(subscriptionOption == "subscribe")
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
     * Verify OAuth Credentials
     * @param token token stored
     * @param secretToken secret Token
     * @param pin pin
     * @return
     * @throws TwitterException
     */
    public Boolean verifyCredentials(final String token,
                                     final String tokenSecret,
                                     final String consumerKey,
                                     final String consumerSecret,
                final String pin) throws TwitterException{
        Boolean verified = false;
        log.debug("verifyCredentials OAuth");
        log.debug("Token {"+token);
        log.debug("secretToken {"+tokenSecret);
        log.debug("pin {"+pin);
        log.debug("consumerKey {"+consumerKey);
        log.debug("consumerSecret {"+consumerSecret);
        Twitter twitter = null;
        try {
             twitter = new TwitterFactory().getInstance();
            if(token == null || token.isEmpty()){
                verified = false;
            } else {
                log.debug("Exist Previous Token.");
                final AccessToken accessToken = new AccessToken(token, tokenSecret);
                log.debug("Created Token "+accessToken);
                twitter = new TwitterFactory().getOAuthAuthorizedInstance(consumerKey, consumerSecret, accessToken);
                log.debug("Verifying Credentials");
                final User user = twitter.verifyCredentials();
                log.debug("Verifying Credentials User "+user);
                if (user != null) {
                    log.debug("Verify OAuth User " + user.getId());
                    verified = true;
                }
            }
        } catch (TwitterException te) {
            log.error("Twitter Error "+te.getMessage());
            if (AbstractBaseService.TWITTER_AUTH_ERROR == te.getStatusCode()) {
                log.error("Twitter Error "+te.getStatusCode());
                verified = false;
            } else {
                te.printStackTrace();
            }
            log.error("Verify OAuth Error " + te.getLocalizedMessage());
        }
        log.debug("verified "+verified);
        return verified;
    }

    /**
     *
     * @param token
     * @param tokenSecret
     * @param pin
     * @return
     * @throws TwitterException
     */
    private AccessToken getAccessToken(
            final Twitter twitter,
            final String token,
            final String tokenSecret) throws TwitterException {

            final AccessToken accessToken =  twitter.getOAuthAccessToken(token, tokenSecret);
            log.info(String.format("Auth Token {%s Token Secret {%s ", accessToken.getToken(), accessToken.getTokenSecret()));
            twitter.setOAuthAccessToken(accessToken);
            if (accessToken != null)
                    log.info(String.format("Got access token for user %s", accessToken.getScreenName()));
            return accessToken;
    }


    /**
     * Load list of users.
     * @return list of users with groups and permission
     * @throws Exception
     * @throws EnMeExpcetion excepcion
     */
    public List<UnitUserBean> loadListUsers(final String currentUsername) {
        log.info("currentUsername "+currentUsername);
        final List<UnitUserBean> loadListUsers = new LinkedList<UnitUserBean>();
        final SecUserSecondary secUserSecondary = getUser(currentUsername);
        log.info("secUserSecondary "+secUserSecondary);
            final Collection<SecUserSecondary> listUsers = getSecUserDao().retrieveListOwnerUsers(secUserSecondary.getSecUser());
            log.info("list users "+listUsers.size());
                for (SecUserSecondary secUserSecondary2 : listUsers) {
                    loadListUsers.add(ConvertDomainBean.convertSecondaryUserToUserBean(secUserSecondary2));
                }
        return loadListUsers;
    }

}
