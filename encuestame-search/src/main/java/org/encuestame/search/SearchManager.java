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
package org.encuestame.search;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * Query Search Manager to Lucene Index.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since Mar 24, 2011
 */
public class SearchManager {

    /** Log. **/
    private static final Log log = LogFactory.getLog(IndexerManager.class);

    /** Index Directory Path. **/
    final String directoryIndex;

    /** Query String. **/
    final String queryText;

    /** Option to Read Index Directory.**/
    private Boolean readDirectory = true;

    /** Index Directory**/
    private Directory directory;

    /** Index Searcher**/
    private IndexSearcher indexSearcher;

    /**
    * Constructor.
    */
    public SearchManager(final String directoryIndex, final String queryText) {
        this.directoryIndex = directoryIndex;
        this.queryText = queryText;
    }

    /**
     *
     * @throws IOException
     */
    private void startIndexSearcher() throws IOException{
        // Open Index Directory to Search.
      this.directory = FSDirectory.open(new File(this.directoryIndex));
      IndexSearcher indexSearcher = new IndexSearcher(this.directory, true);

    }

    private IndexSearcher indexSearch() throws CorruptIndexException, IOException{
        return this.indexSearcher = new IndexSearcher(this.directory, this.readDirectory);
    }

    /**
     * Search Query in Lucene Index
     * @param indexDir
     * @param q
     * @throws IOException
     * @throws ParseException
     */
    public void search() throws IOException, ParseException {
        QueryParser parser = new QueryParser(Version.LUCENE_30, "contents",
                  new StandardAnalyzer(Version.LUCENE_30));
        Query query = parser.parse(this.queryText); // Parse Query
        long start = System.currentTimeMillis();
        TopDocs hits = indexSearch().search(query, 10); // Search Index
        long end = System.currentTimeMillis();

        log.debug("Found " + hits.totalHits + " document(s) (in "
                + (end - start) + " milliseconds) that matched query '" + this.queryText
                + "':"); // Write search Stats

        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            // Retrieving matching document.
            Document doc = indexSearch().doc(scoreDoc.doc);
            log.debug("Fullpath Document -->"+ doc.get("fullpath"));
}
     // Close IndexSearcher.
        indexSearch().close();
}
}
