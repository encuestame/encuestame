package org.encuestame.search;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.encuestame.search.main.TestUtil;
import org.junit.Ignore;

/**
 * Adding documents to an index.
 * @author dmorales
<<<<<<< HEAD
 * 1- The setUp() method first creates a new RAMDirectory, to hold the index.
 * 2-  it creates an IndexWriter on this Directory.
 * 3- Finally, setUp() iterates over our content, creating a Document and Fields, and then
 *	adds the Document to the index.
 * 4- We create the IndexSearcher and execute a basic single-term query with the specified
string, returning the number of documents that matched.
 * 5- We verify the documents counts according to IndexReader and IndexWriter matches
 *	  how many documents we added.
  */


public class IndexingTest extends TestCase{
    protected String[] ids = { "1", "2" };
    protected String[] unindexed = { "Netherlands", "Italy" };
    protected String[] unstored = { "Amsterdam has lots of bridges",
            "Venice has lots of canals" };
    protected String[] text = { "Amsterdam", "Venice" };
    private Directory directory;

    protected void setUp() throws Exception {
        directory = new RAMDirectory();

        IndexWriter writer = getWriter(); // Create an Index writer.

        // Add documents to the index.
        for (int i = 0; i < ids.length; i++) {
            Document doc = new Document();
            doc.add(new Field("id", ids[i], Field.Store.YES,
                    Field.Index.NOT_ANALYZED));
            doc.add(new Field("country", unindexed[i], Field.Store.YES,
                    Field.Index.NO));
            doc.add(new Field("contents", unstored[i], Field.Store.NO,
                    Field.Index.ANALYZED));
            doc.add(new Field("city", text[i], Field.Store.YES,
                    Field.Index.ANALYZED));
            writer.addDocument(doc);
        }
        writer.close();
    }

    /**
    * Create IndexWriter
    * @return
    * @throws IOException
    */
    private IndexWriter getWriter() throws IOException {
        return new IndexWriter(directory, new WhitespaceAnalyzer(),
                IndexWriter.MaxFieldLength.UNLIMITED);
    }

    /**
     * Get number of hits.
     * @param fieldName
     * @param searchString
     * @return
     * @throws IOException
     */
    protected int getHitCount(String fieldName, String searchString)
            throws IOException {
        IndexSearcher searcher = new IndexSearcher(directory); // Create New Searcher.
        Term t = new Term(fieldName, searchString);
        Query query = new TermQuery(t); // Build simple single term query.
        int hitCount = TestUtil.hitCount(searcher, query);
        searcher.close();
        return hitCount;

    }

    /**
     * Verify writer document count.
     * @throws IOException
     */
    public void testIndexWriter() throws IOException {
        IndexWriter writer = getWriter();
        System.out.println("------->"+ writer.numDocs());
        assertEquals(ids.length, writer.numDocs());
        writer.close();
    }

    /**
     * Verify reader document.
     * @throws IOException
     */
    public void testIndexReader() throws IOException {
        IndexReader reader = IndexReader.open(directory);
        assertEquals(ids.length, reader.maxDoc());
        assertEquals(ids.length, reader.numDocs());
        reader.close();
    }

    /**
     * Deleting documents from an index.
     * @throws IOException
     */
    public void testDeleteBeforeOptimize() throws IOException {
        IndexWriter writer = getWriter();
        assertEquals(2, writer.numDocs()); // Verify 2 docs in index
        writer.deleteDocuments(new Term("id", "1")); // Delete first document (Field, DocumentId)
        System.out.println("-------Writer NumDocs Before>"+ writer.numDocs());
        writer.commit();
        assertTrue(writer.hasDeletions()); // Verify Index contains deletions.
        /** Verify 1 indexed doc returns the total number of deleted
         * or undeleted documents in the index **/
        assertEquals(2, writer.maxDoc());
        /**  Verify 1 deleted doc and returns the number of undeleted documents in an index. **/
        assertEquals(1, writer.numDocs());
         System.out.println("-------Writer NumDocs After>"+ writer.numDocs());
        writer.close();
        }

    /**
     * Deleting documents from an index.
     * we force Lucene to merge index segments, after deleting one document,
     * by optimizing the index.
     * @throws IOException
     */
    public void testDeleteAfterOptimize() throws IOException {
        IndexWriter writer = getWriter();
        assertEquals(2, writer.numDocs());
        writer.deleteDocuments(new Term("id", "1"));
        writer.optimize(); // Optimize to compact deletions.
        writer.commit();
        assertFalse(writer.hasDeletions());
        System.out.println("-------Writer Has Delete "+ writer.hasDeletions());
        assertEquals(1, writer.maxDoc());
        assertEquals(1, writer.numDocs());
        writer.close();
    }

     /**
      * Updating indexed Documents
      * @throws IOException
      */
    public void testUpdate() throws IOException {
        assertEquals(1, getHitCount("city", "Amsterdam"));
        System.out.println("-------HIT COUNT BEFORE>"+ getHitCount("city", "Amsterdam"));
        IndexWriter writer = getWriter();
        Document doc = new Document(); // Create new Document for Haag.
        doc.add(new Field("id", "1", Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("country", "Netherlands", Field.Store.YES, Field.Index.NO));
        doc.add(new Field("contents", "Den Haag has a lot of museums", Field.Store.NO, Field.Index.ANALYZED));
        doc.add(new Field("city", "Den Haag", Field.Store.YES, Field.Index.ANALYZED));
        writer.updateDocument(new Term("id", "1"), doc); // Replace with the new version.
        writer.close();
        System.out.println("-------HIT COUNT AFTER --->"+ getHitCount("city", "Amsterdam"));
        System.out.println("-------HIT COUNT AFTER DEN --->"+ getHitCount("city", "Den Haag"));
        // TODO: Verify why isn't counting new city Den Haag.
        assertEquals(0, getHitCount("city", "Amsterdam"));
        assertEquals(0, getHitCount("city", "Den Haag"));
        }

}
