package org.encuestame.search;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;


/**
 * Directory Operations.
 * @author Morales Diana, Juan juanATencuestame.org
 * @since Mar 20, 2011
 */
public class IndexerManager {


    private static final Log log = LogFactory.getLog(IndexerManager.class);

    private List<File> directoriesToIndex = new ArrayList<File>();

    private String indexesLocation;
    private IndexWriter indexWriter;

    /**
    *
    * @param files
    * @throws Exception
    */
    public IndexerManager(final List<File> files, final String indexesLocation) {
        this.indexesLocation = indexesLocation;
        this.directoriesToIndex = files;
    }

    /** Constructor. **/
    public IndexerManager() {
    }

    /**
    * @throws IOException
    * @throws LockObtainFailedException
    * @throws CorruptIndexException
    *
    */
    private void startIndexWriter() throws CorruptIndexException,
            LockObtainFailedException, IOException {
        final Directory dir = FSDirectory.open(new java.io.File(
                this.indexesLocation));
        this.indexWriter = new IndexWriter(dir, new StandardAnalyzer(
                Version.LUCENE_29), true, IndexWriter.MaxFieldLength.UNLIMITED);
    }

    /**
    *
    * @param dataDir
    * @param filter
    * @return
    * @throws Exception
    */
    public int index(final File dataDir, FileFilter filter) throws Exception {
        log.debug("Index file is directory: "+dataDir.isDirectory());
        File[] files = dataDir.listFiles();
        for (File f : files) {
            if (!f.isDirectory() && !f.isHidden() && f.exists() && f.canRead()
                    && (filter == null || filter.accept(f))) {
                this.indexFile(f);
            }
        }
        return this.indexWriter.numDocs();
    }

    /**
    *
    * @param f
    * @return
    * @throws Exception
    */
    protected Document getDocument(File f) throws Exception {
        Document doc = new Document();
        doc.add(new Field("contents", new FileReader(f))); // Index file
                                                            // contents
        doc.add(new Field("filename", f.getName(), Field.Store.YES,
                Field.Index.NOT_ANALYZED)); // Index File Name.
        doc.add(new Field("fullpath", f.getCanonicalPath(), Field.Store.YES,
                Field.Index.NOT_ANALYZED)); // Index Full Path.
        return doc;
    }

    /**
    *
    * @param f
    * @throws Exception
    */
    private void indexFile(File f) throws Exception {
        log.debug("Indexing " + f.getCanonicalPath());
        Document doc = getDocument(f);
        log.debug("Adding document..."+doc);
        this.indexWriter.addDocument(doc); // Add Document to Lucene Index.
    }

    /**
    * Initialize index process.
    *
    * @throws Exception
    */
    public void initializeIndex() throws Exception {
        log.debug("Initialize");
        this.startIndexWriter();
        for (File file : this.directoriesToIndex) {
            long start = System.currentTimeMillis();
            int numIndexed;
            try {
                numIndexed = this.index(file, new TextFilesFilter());
            } finally {
                this.close();
            }
            long end = System.currentTimeMillis();
            log.debug("Indexing " + numIndexed + " files took "
                    + (end - start) + " milliseconds");
        }
    }

    /**
    * Close Lucene IndexWriter.
    *
    * @throws IOException
    */
    public void close() throws IOException {
        this.indexWriter.close();
    }

    private static class TextFilesFilter implements FileFilter {
        public boolean accept(File path) {
            return path.getName().toLowerCase().endsWith(".txt");
        }
    }
}