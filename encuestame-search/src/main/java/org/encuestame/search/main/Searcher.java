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

public class Searcher {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IllegalArgumentException,
			IOException, ParseException {
		if (args.length != 2) {
			throw new IllegalArgumentException("Usage: java "
					+ Searcher.class.getName() + " <index dir> <query>");
		}
		String indexDir = args[0];
		String q = args[1];
		search(indexDir, q);
	}

	public static void search(String indexDir, String q) throws IOException,
			ParseException {
		Directory dir = FSDirectory.open(new File(indexDir));


		IndexSearcher is = new IndexSearcher(dir);
		QueryParser parser = new QueryParser(Version.LUCENE_29, "contents",
				new StandardAnalyzer(Version.LUCENE_29));
		Query query = parser.parse(q);
		long start = System.currentTimeMillis();
		TopDocs hits = is.search(query, 10);
		long end = System.currentTimeMillis();

		System.err.println("Found " + hits.totalHits + " document(s) (in "
				+ (end - start) + " milliseconds) that matched query '" + q
				+ "':");

		for (ScoreDoc scoreDoc : hits.scoreDocs) {
			Document doc = is.doc(scoreDoc.doc);
			System.out.println(doc.get("fullpath"));
		}
		is.close();
	}
}
