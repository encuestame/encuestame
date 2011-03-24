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
import org.encuestame.search.IndexerManager;

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
     *
     */
    private String indexStorePath;

    /**
     *
     */
    private String attachmentsPlacesHolder = "/home/dmorales/encuestame/profiles/1/";

    private String attachmentsPlacesHolder2 = "/home/dmorales/encuestame/profiles/2/";

    private IndexerManager indexerManager;


    /**
     * Constructor.
     */
    public ReIndexAttachmentsJob() {    	//
        this.indexStorePath = DirectorySetupOperations.getIndexesDirectory();
        log.debug("Index path location "+this.indexStorePath);

    }


    /**
     * Reindex.
     */
    public void reindexAttachments(){
        log.debug("reindexAttachments");
        final List<File> userDomainAttachmentsLocation = new ArrayList<File>();
        userDomainAttachmentsLocation.add(new File(this.attachmentsPlacesHolder));
        log.debug("Location size"+userDomainAttachmentsLocation.size());
        try {
            log.debug("IndexManager initialize....");
            this.indexerManager = new IndexerManager(userDomainAttachmentsLocation, this.indexStorePath);
            log.debug("Initialize Index Starting...");
            this.indexerManager.initializeIndex();
        } catch (Exception e) {
            log.fatal("Index on reindex: "+e);
        }
    }
}
