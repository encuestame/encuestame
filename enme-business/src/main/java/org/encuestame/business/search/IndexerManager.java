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
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.poi.POIXMLException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Indexer Manager.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since Mar 23, 2011
 */
public class IndexerManager {

    /** Log. **/
    private static final Log log = LogFactory.getLog(IndexerManager.class);

    /** Index writer. **/
    @Autowired
    private IndexWriterManager indexWriterManager;

    /**
     * Check the file extensions allowed to index
     */
    private List extensionFilesAllowed;

    /** Constructor. **/
    public IndexerManager() {
    }

    /**
     * Initialize index process.
     * @throws Exception
     */
     public void initializeIndex(final List<File> filesDirectory) throws Exception {
         log.debug("InitializeIndex list of directories :"+filesDirectory.size());
         if (log.isDebugEnabled()) {
            for (Object docExtensions : getExtensionFilesAllowed()) {
                log.debug("Check the file extensions allowed to index: "+ docExtensions);
            }
         }
         for (File file : filesDirectory) {
             long start = System.currentTimeMillis();
             int numIndexed = 0;
             try {
                    numIndexed = this.index(file);
            } catch (Exception e) {
                    log.fatal("Initialize Index Exception --->"+e);
            }
            long end = System.currentTimeMillis();
            log.debug("Indexing " + numIndexed + " files took " + (end - start)
                     + " milliseconds");
         }
     }

    /**
    * Index Writer.
    * @throws IOException
    * @throws LockObtainFailedException
    * @throws CorruptIndexException
    *
    */

 /*   private void startIndexWriter() throws CorruptIndexException,
            LockObtainFailedException, IOException {
        final Directory dir = FSDirectory.open(new java.io.File(
                this.indexesLocation));
        this.indexWriter = new IndexWriter(dir, new StandardAnalyzer(
                Version.LUCENE_30), true, IndexWriter.MaxFieldLength.UNLIMITED);
    }*/

    /**
     * Read Files in Attachment Directory.
     * @param dataDir Attachment Directory
     * @return
     * @throws Exception
     */
    public int index(final File dataDir) throws Exception {
        log.debug("Index file is directory? --->: " + dataDir.isDirectory());
        File[] files = dataDir.listFiles();
        int numberDocs = 0;
        if ( files == null) {
            log.debug("No files in the directory ");
        } else {
            numberDocs = this.indexWriterManager.getIndexWriter().numDocs();
            log.debug("List of files in the directory :"+numberDocs);
            for (File f : files) {
                if (!f.isDirectory() && !f.isHidden() && f.exists() && f.canRead()) {
                    this.indexFile(f); // Write documents in Index
                }
            }
        }
        return numberDocs;
    }


   /**
    * Retrieve Document to Index.
    * @param file {@link File}
    * @param ext Filename extension
    * @return {@link Document}
    * @throws POIXMLException
    * @throws Exception
    */
    public Document getDocument(final File file, final String ext)
            throws POIXMLException, Exception {
        Document doc = null;
        log.debug("get Document extension " + ext);
            if ("docx".equals(ext) && getExtensionFilesAllowed().indexOf(ext) != -1) {
                   doc = SearchUtils.createWordDocument(file);
              } else if ("xls".equals(ext) && getExtensionFilesAllowed().indexOf(ext) != -1) {
                   doc = SearchUtils.createSpreadsheetsDocument(file);
              } else if ("pdf".equals(ext) && getExtensionFilesAllowed().indexOf(ext) != -1) {
                   doc = SearchUtils.createPdfDocument(file);
              } else if ("txt".equals(ext) && getExtensionFilesAllowed().indexOf(ext) != -1) {
                   doc = SearchUtils.createTextDocument(file);
              }
              else{
                log.warn("Doc extension is disabled ---------> "+ext);
              }
        return doc ;
    }

    /**
    * Adding Document to Index Directory.
    * @param file {@link File}
    * @throws Exception
    */
    private void indexFile(final File file) throws Exception {
        log.debug("Indexing " + file.getCanonicalPath());
        final String pathFileName = file.getName().toString();
        final String ext = SearchUtils.getExtension(pathFileName);
        final Document doc = this.getDocument(file, ext);
        if (doc == null) {
            log.warn("Document is null for this file: "+file.getAbsolutePath());
        } else {
            // Add Document to Lucene Index.
            this.indexWriterManager.getIndexWriter().addDocument(doc);
        }
    }

    /**
    * Close Lucene IndexWriter.
    * @throws IOException
    */
    public void close() throws IOException {
        this.indexWriterManager.getIndexWriter().commit();
        this.indexWriterManager.getIndexWriter().close();
    }

    /**
    * @param indexWriterManager the indexWriterManager to set
    */
    public void setIndexWriterManager(final IndexWriterManager indexWriterManager) {
        this.indexWriterManager = indexWriterManager;
    }

    /**
    * @return the extensionFilesAllowed
    */
    public List getExtensionFilesAllowed() {
        return extensionFilesAllowed;
    }

    /**
    * @param extensionFilesAllowed the extensionFilesAllowed to set
    */
    public void setExtensionFilesAllowed(final List extensionFilesAllowed) {
        log.debug("Num extension files available --> "+ extensionFilesAllowed.size());
        this.extensionFilesAllowed = extensionFilesAllowed;
    }
}
