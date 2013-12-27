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

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.LockObtainFailedException;
import org.encuestame.core.search.DirectoryIndexStore;
import org.encuestame.core.service.imp.IIndexWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * Index Writer Manager.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since Mar 25, 2011
 */

public class IndexWriterManager implements IIndexWriter {

    /**
    * Log.
    */
    private static final Log log = LogFactory.getLog(IndexWriterManager.class);

    /**
    * {@link IndexWriter}.
    */    
    
    private IndexWriter indexWriter;


    /**
     * {@link DirectoryIndexStore}.
     */
    private DirectoryIndexStore directoryStore;

    /**
     * Checking index directory is open to write.
     */
    private Boolean isOpen = false;

    /**
     * Initialize writer lucene index directory.
     * @throws IOException
     */
    @PostConstruct
    public void openIndexWriter() {
        try {
            this.indexWriter = SearchUtils.openIndexWriter(getDirectoryStore(), this.indexWriter);
            Assert.notNull(this.indexWriter);
        } catch (CorruptIndexException e) {
            log.fatal(e);
        } catch (LockObtainFailedException e) {
            log.fatal(e);
        } catch (IOException e) {
            log.fatal(e);
        }
    }

    /**
     * Close Index.
     * @throws CorruptIndexException
     * @throws IOException
     */
    @PreDestroy
    public void closeIndexWriter() throws CorruptIndexException, IOException{
        Assert.notNull(this.indexWriter);
        SearchUtils.closeIndexWriter(indexWriter);
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
        Assert.notNull(this.indexWriter);
        return indexWriter;
    }

    /**
    * @return the directoryStore
    */
    public DirectoryIndexStore getDirectoryStore() {
        return directoryStore;
    }

    /**
    * @param directoryStore the directoryStore to set
    */
    public void setDirectoryStore(final DirectoryIndexStore directoryStore) {
        this.directoryStore = directoryStore;
    }
}
