package org.encuestame.search.main;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
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
 * Complement Indexer Classer.
 * Execute queries to search data in *.txt files
 * @author dmorales
 *
 */
public class Searcher {

    /**
    * @param indexDir The path to the index created with Indexer
    * @param q Query to search
    * @param args
    */
    public static void main(String[] args) throws IllegalArgumentException,
            IOException, ParseException {
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: java "
                    + Searcher.class.getName() + " <index dir> <query>");
        }
        String indexDir = args[0]; // Parse provided Index directory.
        String q = args[1]; // Parse provided query String.
        search(indexDir, q);
    }

    public static void search(String indexDir, String q) throws IOException,
            ParseException {
        Directory dir = FSDirectory.open(new File(indexDir)); // Open Index


        IndexSearcher is = new IndexSearcher(dir);
        QueryParser parser = new QueryParser(Version.LUCENE_29, "contents",
                new StandardAnalyzer(Version.LUCENE_29));
        Query query = parser.parse(q); // Parse Query
        long start = System.currentTimeMillis();
        TopDocs hits = is.search(query, 10); // Search Index
        long end = System.currentTimeMillis();

        System.err.println("Found " + hits.totalHits + " document(s) (in "
                + (end - start) + " milliseconds) that matched query '" + q
                + "':"); // Write search Stats

        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = is.doc(scoreDoc.doc); // Retrieving matching document.
            System.out.println(doc.get("fullpath"));
        }
        is.close(); // Close IndexSearcher.
    }
}
