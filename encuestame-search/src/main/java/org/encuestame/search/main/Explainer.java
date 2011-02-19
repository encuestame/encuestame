package org.encuestame.search.main;

import java.io.File;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Explainer {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
        System.err.println("Usage: Explainer <index dir> <query>");
        System.exit(1);
        }
        String indexDir = args[0];
        String queryExpression = args[1];
        Directory directory = FSDirectory.open(new File(indexDir));
        QueryParser parser = new QueryParser(Version.LUCENE_29, "contents", new SimpleAnalyzer());
                Query query = parser.parse(queryExpression);
                System.out.println("Query: " + queryExpression);
                IndexSearcher searcher = new IndexSearcher(directory);
                TopDocs topDocs = searcher.search(query, 10);
                for (ScoreDoc match : topDocs.scoreDocs) {
                Explanation explanation = searcher.explain(query, match.doc);
                 System.out.println("----------");
                Document doc = searcher.doc(match.doc);
                System.out.println(doc.get("title"));
                System.out.println(explanation.toString());
                }
                searcher.close();
                directory.close();
                }
     }
