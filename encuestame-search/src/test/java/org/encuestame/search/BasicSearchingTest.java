package org.encuestame.search;

import java.util.List;

import junit.framework.TestCase;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import org.encuestame.search.main.TestUtil;

public class BasicSearchingTest extends TestCase {

    public void testTerm() throws Exception {
        Directory dir = TestUtil.getBookIndexDirectory();
        IndexSearcher searcher = new IndexSearcher(dir);
        Term t = new Term("subject", "ant");
        Query query = new TermQuery(t);
        TopDocs docs = searcher.search(query, 10);
        System.out.println("Total hits ANT IN ACTION -->"+ docs.totalHits);
        assertEquals("Ant in Action", 1, docs.totalHits);
        t = new Term("subject", "junit");
        docs = searcher.search(new TermQuery(t), 10);
        System.out.println("Total hits Junit -->"+ docs.totalHits);
        assertEquals("Ant in Action, " + "JUnit in Action, Second Edition",	2, docs.totalHits);
        searcher.close();
        dir.close();
        }

    /**
     * QueryParser
     * @throws Exception
     */
    public void testQueryParser() throws Exception {
        Directory dir = TestUtil.getBookIndexDirectory();
        IndexSearcher searcher = new IndexSearcher(dir);
        QueryParser parser = new QueryParser(Version.LUCENE_29, "contents", new SimpleAnalyzer());

        Query query = parser.parse("+JUNIT +ANT -MOCK");
        TopDocs docs = searcher.search(query, 10);
        System.out.println("DOCS SEARCH -->"+docs.totalHits);
        assertEquals(1, docs.totalHits);

        System.out.println("---->"+docs.scoreDocs[0].doc);

        Document d = searcher.doc(docs.scoreDocs[0].doc);
        List unitList = d.getFields();
        for (int i = 0; i < unitList.size(); i++) {
            System.out.println(unitList.get(i));

        }
        System.out.println("---->"+d.getFields());
        //assertEquals("Ant in Action", d.get("title"));
        System.out.println("GET TITLE -->"+d.get("title"));

        query = parser.parse("mock OR junit");
        docs = searcher.search(query, 10);
        assertEquals("Ant in Action, " + "JUnit in Action, Second Edition", 2, docs.totalHits);
        searcher.close();
        dir.close();
        }


}
