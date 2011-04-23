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
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.encuestame.business.setup.DirectorySetupOperations;
import org.encuestame.core.search.DirectoryIndexStore;

/**
 * Index Directory.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since Apr 8, 2011
 */
public class IndexFSDirectory implements DirectoryIndexStore {

    /** Log. **/
    private static final Log log = LogFactory.getLog(IndexFSDirectory.class);

   /** {@link Directory}. **/
    private Directory directory;


   /**
    * Get index {@link Directory}
    */
    public IndexFSDirectory() {
        final String directoryIndexPath = DirectorySetupOperations.getIndexesDirectory();
        log.debug("Index Directory -->"+ directoryIndexPath);
        try {
            if (directoryIndexPath != null) {
                this.directory = FSDirectory.open(new File(directoryIndexPath));
            } else {
                log.warn("Directory is null");
                //do action
            }
        } catch (IOException e) {
            log.fatal("Directory not found where save data");
            e.printStackTrace();
            //do action
            //System.getProperty("user.home");
        }
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.DirectoryIndexStore#getDirectory()
     */
    public Directory getDirectory(){
        return directory;
    }
}
