/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.search.utils;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.poi.POIXMLException;

/**
 * Search Service.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since February 09, 2011
 * @version $Id$
 */
public class Search {

    /**
    * {@link IndexWriter} write in the Lucene Index.
    */
    private IndexWriter writer;

    private DocumentType docType;

    private Analyzer analyzer;

    /**
     * Get Index Directory.
     * @param indexDirPath Index Directory Path.
     * @throws IOException
     */
    public void getIndexer(String indexDirPath) throws IOException{
        Directory dir = FSDirectory.open(new File(indexDirPath));
        this.writer = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_29) ,
                true, IndexWriter.MaxFieldLength.UNLIMITED);

    }

    /**
     * Read Files in Index Store.
     * @param dataDir Index Store path.
     * @param filter
     * @return Num Documents read and indexed
     * @throws Exception
     */
    public int index(String dataDir) throws Exception {
        File[] files = new File(dataDir).listFiles();
        for (File f : files) {
            if (!f.isDirectory() && !f.isHidden() && f.exists() && f.canRead()) {
                indexFile(f);
            }
        }
        return writer.numDocs();

    }

    private void indexFile(File f) throws Exception {
        System.out.println("Indexing " + f.getCanonicalPath());
     //   Document doc = getDocument(f);
      //  writer.addDocument(doc); // Add Document to Lucene Index.
    }

    /**
     * Get Filename extension.
     * @param path FilePath.
     * @return filename extension
     */
    public String getExtension(String path) {
            final String ext = path.substring(path.lastIndexOf('.')+1);
            System.out.println("PATH ------> "+ path);
            System.out.println("EXT ------> "+ ext);
          return ext;
      }

    /**
     * Get Document to Index.
     * @param file
     * @param ext
     * @return
     * @throws POIXMLException
     * @throws Exception
     */
    public Document getDocument(File file, String ext) throws POIXMLException, Exception {
        Document doc = null;
        if("docx".equals(ext)){
            doc = docType.parseDocumentWord(file);
        }
        else if ("xls".equals(ext)){
            doc = docType.parseSpreadsheetsDocument(file);
        }
        else if ("pdf".equals(ext)){
            doc = docType.parsePDFDocument(file);
        } else {
            throw new IllegalArgumentException("not type defined");
        }
        return doc;

    }

    public void searchContent(String indexDir, String queryString, int valueHits) throws IOException,
                        ParseException {
        Directory dir = FSDirectory.open(new File(indexDir)); // Open Index
        IndexSearcher is = new IndexSearcher(dir, true);
        QueryParser parser = new QueryParser(Version.LUCENE_29, "content",
                            new StandardAnalyzer(Version.LUCENE_29));
        Query query = parser.parse(queryString); // Parse Query
        TopDocs hits = is.search(query, valueHits); // Search Index

        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = is.doc(scoreDoc.doc); // Retrieving matching document.
            System.out.println(doc.get("fullpath"));
        }
        is.close(); // Close IndexSearcher.
        }


    private Analyzer getAnalyzer() {
        return new StandardAnalyzer(Version.LUCENE_29);
    }


    /**
     * Index Writer Close.
     * @throws IOException
     */
    public void close() throws IOException {
        writer.close();
    }

    /**
    * @return the writer
    */
    public IndexWriter getWriter() {
        return writer;
    }

    /**
    * @param writer the writer to set
    */
    public void setWriter(IndexWriter writer) {
        this.writer = writer;
    }

    /**
    * @param analyzer the analyzer to set
    */
    public void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }



}
