/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
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

import junit.framework.TestCase;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.encuestame.search.main.TestUtil;

/**
 * Test Service Search.
 *
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since February 09, 2011
 * @version $Id: $
 */
public class TestSearch extends TestCase {
    final String queryString = "indexing";
    final String indexDir = "src/main/resources/Indexer2";

    public void testSearcher() throws CorruptIndexException, IOException, ParseException{

        Directory directory = FSDirectory.open(new File(indexDir));
        IndexReader reader = IndexReader.open(directory, true);
        IndexSearcher searcher = new IndexSearcher(reader);
        QueryParser parser = new QueryParser(Version.LUCENE_29, "content",
                    new StandardAnalyzer(Version.LUCENE_29));
        Query query = parser.parse(this.queryString); // Parse Qu
        TopDocs hits = searcher.search(query, 10); // Search Index
        System.out.println("Total number of HITS "+ hits.totalHits);
            for (ScoreDoc scoreDoc : hits.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc); // Retrieving matching document.
                System.out.println(doc);
                System.out.println("*****************************************");
                // get the filename
                System.out.println(doc.get("filename"));
                System.out.println(doc.getField("fullpath").stringValue());
            }
            searcher.close(); // Close IndexSearcher.
            System.out.println("Total number of docs "+reader.maxDoc());
        }
    /**
     *
     * @throws IOException
     * @throws InvalidTokenOffsetsException
     */
    public void testHits() throws IOException, InvalidTokenOffsetsException  {
        IndexSearcher searcher = new IndexSearcher(TestUtil.getBookIndexDirectory(), true);
        TermQuery query = new TermQuery(new Term("title", "action"));
        TopDocs hits = searcher.search(query, 10);
        QueryScorer scorer = new QueryScorer(query, "title");
        Highlighter highlighter = new Highlighter(scorer);
        highlighter.setTextFragmenter(
                new SimpleSpanFragmenter(scorer));
        Analyzer analyzer = new SimpleAnalyzer();
        for (ScoreDoc sd : hits.scoreDocs) {
                Document doc = searcher.doc(sd.doc);
                String title = doc.get("title");
                TokenStream stream = TokenSources.getAnyTokenStream(searcher.getIndexReader(),
                        sd.doc, "title", doc, analyzer);
                String fragment = highlighter.getBestFragment(stream, title);
                System.out.println(fragment);
            }
}
}
