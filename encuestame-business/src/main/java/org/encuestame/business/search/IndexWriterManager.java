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
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.encuestame.business.setup.DirectorySetupOperations;

/**
 * Index Writer Manager.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since Mar 25, 2011
 */
public class IndexWriterManager  {

    /**
    * Log.
    */
    private static final Log log = LogFactory.getLog(IndexWriterManager.class);

    /**
    * {@link IndexWriter}.
    */
    private IndexWriter indexWriter;

    /**
     * Checking index directory is open to write.
     */
    private Boolean isOpen = false;

    /****/
    private static final Version LUCENE_VERSION = Version.LUCENE_30;

    /**
     * Initialize writer lucene index directory.
     * @throws IOException
     */
    @PostConstruct
    public void openIndexWriter() throws IOException{
        String dir = DirectorySetupOperations.getIndexesDirectory();
        log.debug("Index Directory -->"+ dir);
        final Directory directory = FSDirectory.open(new File(dir));
        try {
            this.indexWriter = new IndexWriter(directory, new StandardAnalyzer(
                    LUCENE_VERSION), true, IndexWriter.MaxFieldLength.UNLIMITED);
        } catch (CorruptIndexException e) {
             log.error(e);
        } catch (LockObtainFailedException e) {
             log.error(e);
        } catch (IOException e) {
             log.error(e);
        }
    }

    /**
     * Close Index.
     * @throws CorruptIndexException
     * @throws IOException
     */
    @PreDestroy
    public void closeIndexWriter() throws CorruptIndexException, IOException{
        if (this.indexWriter == null){
            log.error("Index writer is null");
        } else {
            this.indexWriter.close();
        }
    }

    /**
    * @return the isOpen
    */
    public Boolean getIsOpen() {
        return isOpen;
    }

    /**
    * @return the indexWriter
    */
    public IndexWriter getIndexWriter() {
        return indexWriter;
    }
}
