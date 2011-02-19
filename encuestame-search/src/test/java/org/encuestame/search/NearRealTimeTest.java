package org.encuestame.search;

import java.io.File;

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
            //FSDirectory.open(new File("src/main/resources/IndexTest2"));
        IndexWriter writer = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_29),
                                            IndexWriter.MaxFieldLength.UNLIMITED);

        // Adding document with two fields. Total Documents = 10
        for(int i=0;i<10;i++) {
            Document doc = new Document();
            doc.add(new Field("id", ""+i, Field.Store.NO, Field.Index.NOT_ANALYZED_NO_NORMS));
            doc.add(new Field("text", "aaa", Field.Store.NO, Field.Index.ANALYZED));
            writer.addDocument(doc); // Write document in index Directory
         }

        IndexReader reader = writer.getReader(); // Create Near-Realtime Reader. Flush and reflect new changes.

        IndexSearcher searcher = new IndexSearcher(reader); // Wrap reader in IndexSearcher.

        Query query = new TermQuery(new Term("text", "aaa"));
        TopDocs docs = searcher.search(query, 1);

        System.out.println("Total Hits first Doc-->"+ docs.totalHits);
        assertEquals(10, docs.totalHits); // Search returs 10 hits.

        // Check the max and number Docs in Index Directory before delete document step.
        // This will equals to total Documents(10)
        System.out.println("Max Doc Before -->" + reader.maxDoc());
        System.out.println("Num Doc Before -->" + reader.numDocs());

        writer.deleteDocuments(new Term("id", "7")); // Delete 1 document.
        assertFalse(writer.hasDeletions()); // Check for deletions

        Document doc = new Document(); // Add new document.
        doc.add(new Field("id", "11", Field.Store.NO, Field.Index.NOT_ANALYZED_NO_NORMS));
        doc.add(new Field("text", "bbb", Field.Store.NO, Field.Index.ANALYZED));
        writer.addDocument(doc);

        IndexReader newReader = reader.reopen(); // Reopen Reader.
        // Check the max and number Docs in Index Directory before delete document step.
        // here we can see the numDocs is minor because one document was deleted and the maxDoc increase
        // one by the new doc added before
        System.out.println("Max Doc After added -->" + newReader.maxDoc());
        System.out.println("Num Doc After added -->" + newReader.numDocs());
        assertFalse(reader == newReader); // Confirm reader is new
        reader.close(); // Close old reader

        searcher = new IndexSearcher(newReader);
        assertEquals(10, docs.totalHits);

        TopDocs hits = searcher.search(query, 10);
        System.out.println("Total Hits with query text aaa -->"+ hits.totalHits);
        assertEquals(9, hits.totalHits); // Verify 9 hits now
        query = new TermQuery(new Term("text", "bbb"));
        hits = searcher.search(query, 1);
        assertEquals(1, hits.totalHits); // Confirm new document matched.
        newReader.close();
        writer.close();
        }
 }


