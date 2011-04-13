/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.business.search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.business.setup.DirectorySetupOperations;
import org.encuestame.persistence.dao.IAccountDao;
import org.encuestame.persistence.dao.imp.AccountDaoImp;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * ReIndex Attachments Job.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since Mar 23, 2011
 */
public class ReIndexAttachmentsJob {

    /**
     * Log.
     */
    private static final Log log = LogFactory.getLog(ReIndexAttachmentsJob.class);

    /**
     * {@link AccountDaoImp}.
     */
    @Autowired
    private IAccountDao accountDao;

    /**
     * {@link IndexerManager}
     */
    @Autowired
    private IndexerManager indexerManager;


    /**
     * Enable/Disable autocreate missing directories.
     */
    private Boolean autoCreateDirectories = Boolean.FALSE;

    /**
     * Constructor.
     * @throws EnMeExpcetion if indexes directoty is null;
     */
    public ReIndexAttachmentsJob() throws EnMeExpcetion {    	//
    }

    /**
     * Return list of enabled account directories.
     * @return
     */
    private List<File> getListOfAccountEnabledDirectories(){
        final List<File> userDomainAttachmentsLocation = new ArrayList<File>();
        final List<Long> listOfAccounts = getAccountDao().getAccountsEnabled();
        log.debug("listOfAccounts enabled:{"+listOfAccounts.size());
        for (Long accountId : listOfAccounts) {
            final StringBuilder path = new StringBuilder(DirectorySetupOperations.getProfilesDirectory());
            path.append("/");
            path.append(accountId.toString());
            path.append("/");
            log.debug("Path builded "+path.toString());
            final File accountPath = new File(path.toString());
            if (accountPath.exists()) {
                userDomainAttachmentsLocation.add(accountPath);
            } else {
                log.warn("Account Id: "+accountId+ " profile propery is missing, enable autocreate to create missings directories");
                if (this.autoCreateDirectories) {
                    log.debug("Autocreate enabled: creating folder for profile:{"+accountId);
                    accountPath.mkdir();
                    if(accountPath.exists()){
                        userDomainAttachmentsLocation.add(accountPath);
                    }
                }
            }
        }
        return userDomainAttachmentsLocation;
    }

    /**
     * Reindex.
     */
    public void reindexAttachments(){
        log.debug("reindexAttachments");
        final List<File> userDomainAttachmentsLocation = this.getListOfAccountEnabledDirectories();
        log.debug("Location size:{"+userDomainAttachmentsLocation.size());
        try {
            if (userDomainAttachmentsLocation.size() > 0) {
                if (this.indexerManager == null) {
                    log.fatal("IndexManager is missing.");
                } else {
                    log.debug("Initialize Index Starting...");
                    Assert.notNull(userDomainAttachmentsLocation);
                    this.indexerManager.initializeIndex(userDomainAttachmentsLocation);
                }
            } else {
                log.debug("Nothing to index... ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.fatal("Index on reindex: "+e);
        }
    }

    /**
    * @param indexerManager the indexerManager to set
    */
    public void setIndexerManager(final IndexerManager indexerManager) {
        this.indexerManager = indexerManager;
    }


    /**
     * @return the accountDao
     */
    public IAccountDao getAccountDao() {
        return accountDao;
    }


    /**
     * @param accountDao the accountDao to set
     */
    public void setAccountDao(final IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * @return the autoCreateDirectories
     */
    public Boolean getAutoCreateDirectories() {
        return autoCreateDirectories;
    }

    /**
     * @param autoCreateDirectories the autoCreateDirectories to set
     */
    public void setAutoCreateDirectories(final Boolean autoCreateDirectories) {
        this.autoCreateDirectories = autoCreateDirectories;
    }
}
