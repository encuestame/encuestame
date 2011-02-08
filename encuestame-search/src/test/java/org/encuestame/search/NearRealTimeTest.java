package org.encuestame.search;

import junit.framework.TestCase;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class NearRealTimeTest extends TestCase {

	public void testNearRealTime() throws Exception {
		Directory dir = new RAMDirectory();
		IndexWriter writer = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_29), IndexWriter.MaxFieldLength.UNLIMITED);

		for(int i=0;i<10;i++) {

		Document doc = new Document();
 		doc.add(new Field("id", ""+i, Field.Store.NO, Field.Index.NOT_ANALYZED_NO_NORMS));
		doc.add(new Field("text", "aaa", Field.Store.NO, Field.Index.ANALYZED));
		writer.addDocument(doc);
 		}

		IndexReader reader = writer.getReader();
		IndexSearcher searcher = new IndexSearcher(reader);

		Query query = new TermQuery(new Term("text", "aaa"));
		TopDocs docs = searcher.search(query, 1);
		assertEquals(10, docs.totalHits);
		writer.deleteDocuments(new Term("id", "7"));
	 	Document doc = new Document();
		doc.add(new Field("id", "11", Field.Store.NO, Field.Index.NOT_ANALYZED_NO_NORMS));
		doc.add(new Field("text", "bbb", Field.Store.NO, Field.Index.ANALYZED));
		writer.addDocument(doc);

		IndexReader newReader = reader.reopen();
		assertFalse(reader == newReader);
		reader.close();
		searcher = new IndexSearcher(newReader);

		TopDocs hits = searcher.search(query, 10);
		assertEquals(9, hits.totalHits);
		query = new TermQuery(new Term("text", "bbb"));
		hits = searcher.search(query, 1);
		assertEquals(1, hits.totalHits);

		newReader.close();
		writer.close();
		}
 }


