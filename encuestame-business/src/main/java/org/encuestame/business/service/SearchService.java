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
package org.encuestame.business.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.util.Version;
import org.encuestame.business.search.GlobalSearchItem;
import org.encuestame.business.service.imp.SearchServiceOperations;
import org.encuestame.persistence.domain.survey.Poll;

/**
 * Search Service.
 *
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since February 09, 2011
 * @version $Id$
 */
public class SearchService extends AbstractIndexService implements SearchServiceOperations {

    public void indexDocument(final String indexDirPath,
            final String indexDirStore) throws IOException {
    }

    public void searchContent(final String dirPath) {

    }

    public void IndexPoll(Poll poll, String path) throws IOException {

    }

    /**************************************************************************/

    public List<Poll> searchPolls(String searchString, String path,
            String fieldName, int results) throws IOException, ParseException {
        List<Poll> pollList = new ArrayList<Poll>();
        // IndexReader reader = IndexReader.open(path, true);
        // IndexReader reader = IndexReader.open(path);
        // Searcher searcher = new IndexSearcher(reader);
        Searcher searcher = null;
        QueryParser qp = new QueryParser(Version.LUCENE_29, fieldName,
                new StandardAnalyzer(Version.LUCENE_29));
        Query query = qp.parse(searchString);
        ScoreDoc[] docs = searcher.search(query, results).scoreDocs;

        for (int i = 0; i < docs.length; i++) {
            Document doc = searcher.doc(docs[i].doc);
            Poll articlePoll = new Poll();
            articlePoll.setPollId(1L);
            articlePoll.setName(doc.getField("TITLE").stringValue());
            pollList.add(articlePoll);
        }
        return pollList;
    }

    public Map<String, Object> searchPollPaginateResults(String searchString,
            String path, String fieldName, int page, int results)
            throws IOException, ParseException {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Poll> articles = new ArrayList<Poll>();
        // Searcher searcher = new IndexSearcher(IndexReader.open(path));
        // IndexReader reader = IndexReader.open(path);
        // Searcher searcher = new IndexSearcher(reader);
        Searcher searcher = null;
        QueryParser qp = new QueryParser(Version.LUCENE_29, fieldName,
                new StandardAnalyzer(Version.LUCENE_29));
        Query query = qp.parse(searchString);
        TopScoreDocCollector collector = TopScoreDocCollector.create(page
                * results, true);
        searcher.search(query, collector);
        ScoreDoc[] docs = collector.topDocs().scoreDocs;
        map.put("resultados", collector.getTotalHits());
        map.put("articulos", articles);
        int startResult = (page - 1) * results;
        if (startResult > docs.length) {
            return map;
        }
        int end = Math.min(docs.length, startResult + results);

        for (int i = startResult; i < end; i++) {
            Document doc = searcher.doc(docs[i].doc);
            Poll article = new Poll();
            article.setPollId(1L);
            article.setName(doc.getField("POLLNAME").stringValue());
            articles.add(article);
        }
        return map;
    }

    public List<Poll> searchArticle(String searchString, String path,
            String fieldName, int results) throws IOException, ParseException {
        List<Poll> articles = new ArrayList<Poll>();
        // IndexReader reader = IndexReader.open(path);
        // Searcher searcher = new IndexSearcher(reader);
        Searcher searcher = null;
        QueryParser qp = new QueryParser(Version.LUCENE_29, fieldName,
                new StandardAnalyzer(Version.LUCENE_29));
        Query query = qp.parse(searchString);
        ScoreDoc[] docs = searcher.search(query, results).scoreDocs;

        for (int i = 0; i < docs.length; i++) {
            Document doc = searcher.doc(docs[i].doc);
            Poll article = new Poll();
            article.setPollId(1L);
            article.setName((doc.getField("POLLNAME").stringValue()));
            articles.add(article);
        }
        return articles;
    }

    public void searchArticleWithPagination(String path) throws IOException,
            ParseException {
        SearchService searcher = new SearchService();
        Map<String, Object> map = searcher.searchPollPaginateResults(
                "articulo", path, "POLLNAME", 1, 2);
        List<Poll> articles = (List<Poll>) map.get("articulos");
        System.out.println("Búsqueda finalizada, resultados: "
                + articles.size() + " de " + map.get("resultados"));
        for (Poll articlePoll : articles) {
            System.out.println("ID " + articlePoll.getPollId());
            System.out.println("NAME POLL " + articlePoll.getName());
        }
    }

    private Document generateDocumentFromPoll(Poll poll) {
        Document doc = new Document();
        doc.add(new Field("ID", String.valueOf(poll.getPollId()),
                Field.Store.YES, Field.Index.NO));
        doc.add(new Field("POLLNAME", poll.getName(), Field.Store.YES,
                Field.Index.ANALYZED));
        return doc;
    }

    public List<GlobalSearchItem> quickSearch(String keyword) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<GlobalSearchItem> quickSearch(String keyword, String language) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<GlobalSearchItem> globalKeywordSearch(String keyword,
            String language) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<GlobalSearchItem> globalKeywordSearch(String keyword) {
        // TODO Auto-generated method stub
        return null;
    }


}
