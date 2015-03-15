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
package org.encuestame.core.cron;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Reindex Job.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 8, 2010 9:49:41 PM
 * @version $Id:$
 */

public class ReIndexJob {

    /**
     * Log.
     */
    private static final Log log = LogFactory.getLog(ReIndexJob.class);

    /**
     * {@link IndexRebuilder}.
     */
    @Resource(name = "indexRebuilder")
    private IndexRebuilder indexRebuilder;

    /**
     * Reindex.
     */
    @Scheduled(cron = "${cron.reindex}")
    public void reindex(){
        if (EnMePlaceHolderConfigurer.getSystemInitialized()) {
            this.reindexData();
        }
    }

    /**
     * Reindex Data.
     */
    private void reindexData(){
        try {
            getIndexRebuilder().reindexEntities();
            log.info("reindexing entitities ...");
        } catch (Exception e) {
            //e.printStackTrace();
            log.error(e);
            ReIndexJob.log.error("Error Reindexing "+e.getMessage());
        }
    }

    public void setIndexRebuilder(IndexRebuilder indexRebuilder) {
        this.indexRebuilder = indexRebuilder;
    }

    /**
     * @return the indexRebuilder
     */
    public IndexRebuilder getIndexRebuilder() {
        return indexRebuilder;
    }

}