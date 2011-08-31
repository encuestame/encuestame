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
package org.encuestame.persistence.dao.imp;

import org.encuestame.persistence.dao.IApplicationDao;
import org.encuestame.persistence.domain.application.Application;
import org.encuestame.persistence.domain.application.ApplicationConnection;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.utils.SecureRandomStringKeyGenerator;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

/**
 * Data Repository for Applications Domains.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 23, 2010 1:05:45 PM
 * @version $Id:$
 */

@Repository("applicationDao")
public class ApplicationDao extends AbstractHibernateDaoSupport implements IApplicationDao{

    private StringEncryptor encryptor;

    private SecureRandomStringKeyGenerator keyGenerator;

    /** Account Id. **/
    @Autowired
    private AccountDaoImp accountDaoImp;

    /**
     * Constructor.
     * Inject {@link SessionFactory}.
     */
    @Autowired
    public ApplicationDao(SessionFactory sessionFactory) {
         setSessionFactory(sessionFactory);
    }

    /**
     * Get Application By Key.
     * @param key
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Application getApplicationByKey(final String key) throws Exception{
         final DetachedCriteria criteria = DetachedCriteria.forClass(Application.class);
         criteria.add(Restrictions.eq("apiKey", key) );
         final Application app = (Application) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
         log.debug("Application debug "+app);
         if(app == null){
             throw new Exception("application key not found");
         }
         return app;
    }

    //return jdbcTemplate.queryForObject("select c.member, a.apiKey,
    //c.accessToken, c.secret from AppConnection c inner join App a on c.app = a.id
    //where c.accessToken = ?",

    /**
     * Find App Connection by Access Token.
     * @throws EnMeNoResultsFoundException
     */
    @SuppressWarnings("unchecked")
    public ApplicationConnection findAppConnection(final String accessToken) throws EnMeNoResultsFoundException{
         final DetachedCriteria criteria = DetachedCriteria.forClass(ApplicationConnection.class);
         criteria.add(Restrictions.eq("accessToken", accessToken) );
         final ApplicationConnection app = (ApplicationConnection) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
         if(app == null){
            throw new EnMeNoResultsFoundException(accessToken);
         } else {
            return app;
         }
    }

    /**
     * Search Connection by Application Id and Account Id.
     * @param account
     * @param application
     * @return
     */
    @SuppressWarnings("unchecked")
    public ApplicationConnection searchConnectionByAppIdAndUserId(
            final UserAccount account, final Application application){
         final DetachedCriteria criteria = DetachedCriteria.forClass(ApplicationConnection.class);
         criteria.add(Restrictions.eq("account", account) );
         criteria.add(Restrictions.eq("application", application) );
         final ApplicationConnection app = (ApplicationConnection) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
        return app;
    }

    /**
     * Create Application Connection.
     * @param accountId user account id
     * @param apiKey key application.
     * @return
     * @throws Exception
     */
    public ApplicationConnection
           connectApplication(final Long accountId, final String apiKey) throws Exception{
        final Application application = getApplicationByKey(apiKey);
        final UserAccount account = getAccountDaoImp().getUserAccountById(accountId);
        final ApplicationConnection app = searchConnectionByAppIdAndUserId(account, application);
        ApplicationConnection applicationConnection = null;
        if (app != null) {
            log.debug("Removing application connection id "+app.getConnectionId());
            getHibernateTemplate().delete(app);
        }
        //create new application connection.
        applicationConnection = new ApplicationConnection();
        final String accessToken = keyGenerator.generateKey();
        final String secret = keyGenerator.generateKey();
        applicationConnection.setApiKey(apiKey);
        applicationConnection.setApplication(application);
        applicationConnection.setAccessToken(accessToken);
        applicationConnection.setSecret(secret);
        applicationConnection.setAccount(account);
        getHibernateTemplate().saveOrUpdate(applicationConnection);
        log.debug("Created New Application Connection "+applicationConnection.getConnectionId());
        return applicationConnection;
    }

    /**
     * @return the accountDaoImp
     */
    public AccountDaoImp getAccountDaoImp() {
        return accountDaoImp;
    }

    /**
     * @param accountDaoImp the accountDaoImp to set
     */
    public void setAccountDaoImp(final AccountDaoImp accountDaoImp) {
        this.accountDaoImp = accountDaoImp;
    }
}
