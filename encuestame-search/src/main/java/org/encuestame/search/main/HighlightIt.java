package org.encuestame.search.main;

import java.io.FileWriter;
import java.io.StringReader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.util.Version;

/**
 *
 * @author dmorales
 *
 */
public class HighlightIt  {

    /** Text **/
    private static final String text =
        "In this section we'll show you how to make the simplest " +
        "programmatic query, searching for a single term, and then " +
        "we'll see how to use QueryParser to accept textual queries." +
        "In the sections that follow, we’ll take this simple example" +
        "further by detailing all the query types built into Lucene." +
        "We begin with the simplest search of all: searching for all" +
        "documents that contain a single term.";
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: HighlightIt <filename-out>");
            System.exit(-1);
        }
        String filename = args[0];
        String searchText = "section";
        /** Create the query **/
        QueryParser parser = new QueryParser(Version.LUCENE_29, "f", new StandardAnalyzer(Version.LUCENE_29));
        Query query = parser.parse(searchText);

        /** Customize tags**/
        SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<span class=\"highlight\">", "</span>");
        /** Tokenize text **/
        TokenStream tokens = new StandardAnalyzer(Version.LUCENE_29).tokenStream("f", new StringReader(text));

        /** Create Query Score **/
        QueryScorer scorer = new QueryScorer(query, "f");

        Highlighter highlighter = new Highlighter(formatter, scorer);

        highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer));
        /** Highlight best 3 fragments **/
        String result = highlighter.getBestFragments(tokens, text, 3, "...");

        /** Write highlighted in Html **/

        FileWriter writer = new FileWriter(filename);
        writer.write("<html>");
        writer.write("<style>\n" +
                ".highlight {\n" +
                " background: yellow;\n" +
                "}\n" +
        "</style>");
        writer.write("<body>");
        writer.write(result);
        writer.write("</body></html>");
        writer.close();
    }
}
