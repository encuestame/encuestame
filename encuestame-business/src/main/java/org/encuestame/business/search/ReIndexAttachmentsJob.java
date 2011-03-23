package org.encuestame.business.search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.business.setup.DirectorySetupOperations;
import org.encuestame.core.cron.IndexRebuilder;
import org.encuestame.core.cron.ReIndexJob;
import org.encuestame.search.IndexerManager;

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
            log.fatal("Error al indexar "+e);
            e.printStackTrace();
        }
    }
}