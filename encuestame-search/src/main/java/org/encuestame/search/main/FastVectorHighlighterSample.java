package org.encuestame.search.main;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.vectorhighlight.BaseFragmentsBuilder;
import org.apache.lucene.search.vectorhighlight.FastVectorHighlighter;
import org.apache.lucene.search.vectorhighlight.FieldQuery;
import org.apache.lucene.search.vectorhighlight.FragListBuilder;
import org.apache.lucene.search.vectorhighlight.FragmentsBuilder;
import org.apache.lucene.search.vectorhighlight.ScoreOrderFragmentsBuilder;
import org.apache.lucene.search.vectorhighlight.SimpleFragListBuilder;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class FastVectorHighlighterSample {

    static final String[] DOCS = {
    " The quick brown fox jumps over the lazy dog",
    " quick gold fox jumped over the lazy black dog",
    " quick fox jumps over the black dog",
    " red fox jumped over the lazy dark gray dog"};

    static final String QUERY = "brown";
            //"quick OR fox OR \"lazy dog\"~1";
    static final String F = "f";
    static Directory dir = new RAMDirectory();
    static Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_29);

    public static void main(String[] args) throws Exception {
        System.out.println("ARGUMENTS-->"+args[0]);
        if (args.length != 1) {
        System.err.println("Usage: FastVectorHighlighterSample <filename>");
        System.exit(-1);
        }
        makeIndex();
        searchIndex(args[0]);
        }

        static void makeIndex() throws IOException {
            IndexWriter writer = new IndexWriter(dir, analyzer, true, MaxFieldLength.UNLIMITED);
            for(String d : DOCS){
                Document doc = new Document();
                doc.add(new Field(F, d, Store.YES, Index.ANALYZED, TermVector.WITH_POSITIONS_OFFSETS));
                writer.addDocument(doc);
            }
            writer.close();
        }


        static void searchIndex(String filename) throws Exception {
        QueryParser parser = new QueryParser(Version.LUCENE_29, F, analyzer);

        Query query = parser.parse(QUERY);
        FastVectorHighlighter highlighter = getHighlighter();
        FieldQuery fieldQuery = highlighter.getFieldQuery(query);
        IndexSearcher searcher = new IndexSearcher(dir, true);
        TopDocs docs = searcher.search(query, 10);

        FileWriter writer = new FileWriter(filename);
        writer.write("<html>");
        writer.write("<body>");
        writer.write("<p>QUERY : " + QUERY + "</p>");
        for(ScoreDoc scoreDoc : docs.scoreDocs) {
             String snippet = highlighter.getBestFragment( fieldQuery, searcher.getIndexReader(),
                     scoreDoc.doc, F, 100 );
             if (snippet != null) {
                 writer.write(scoreDoc.doc + " : " + snippet + "<br/>");
             }
         }
         writer.write("</body></html>");
         writer.close();
         searcher.close();
        }

        static FastVectorHighlighter getHighlighter() {
             FragListBuilder fragListBuilder = new SimpleFragListBuilder();
             FragmentsBuilder fragmentBuilder = new ScoreOrderFragmentsBuilder(BaseFragmentsBuilder.COLORED_PRE_TAGS, BaseFragmentsBuilder.COLORED_POST_TAGS);
             return new FastVectorHighlighter(true, true, fragListBuilder, fragmentBuilder);

        }
        }
