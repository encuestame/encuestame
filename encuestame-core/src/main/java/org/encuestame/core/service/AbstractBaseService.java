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

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.mail.MailServiceImpl;
import org.encuestame.core.persistence.pojo.CatEmailLists;
import org.encuestame.core.persistence.pojo.CatEmails;
import org.encuestame.core.persistence.pojo.CatSubscribeEmails;
import org.encuestame.core.service.util.MD5Utils;
import org.encuestame.core.service.util.MessageSourceFactoryBean;
import org.encuestame.utils.web.UnitEmails;
import org.encuestame.utils.web.UnitLists;
import org.hibernate.HibernateException;

/**
 * Service.
 * @author Picado, Juan juan@encuestame.org
 * @since 22/05/2009 1:02:45
 * @version $Id$
 */
public abstract class AbstractBaseService extends AbstractConfigurationService {

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

}
