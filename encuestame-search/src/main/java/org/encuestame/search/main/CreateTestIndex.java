package org.encuestame.search.main;


import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.text.ParseException;

import junit.framework.TestCase;

public class CreateTestIndex{

  public static Document getDocument(String rootDir, File file) throws IOException {
    Properties props = new Properties();
    props.load(new FileInputStream(file));

    Document doc = new Document();

    // category comes from relative path below the base directory
    String category = file.getParent().substring(rootDir.length());    //1
    category = category.replace(File.separatorChar, '/');              //1

    String isbn = props.getProperty("isbn");         //2
    String title = props.getProperty("title");       //2
    String author = props.getProperty("author");     //2
    String url = props.getProperty("url");           //2
    String subject = props.getProperty("subject");   //2

    String pubmonth = props.getProperty("pubmonth"); //2

    System.out.println(title + "\n" + author + "\n" + subject + "\n" + pubmonth + "\n" + category + "\n---------");

    doc.add(new Field("isbn",                     // 3
                      isbn,                       // 3
                      Field.Store.YES,            // 3
                      Field.Index.NOT_ANALYZED)); // 3
    doc.add(new Field("category",                 // 3
                      category,                   // 3
                      Field.Store.YES,            // 3
                      Field.Index.NOT_ANALYZED)); // 3
    doc.add(new Field("title",                    // 3
                      title,                      // 3
                      Field.Store.YES,            // 3
                      Field.Index.ANALYZED,       // 3
                      Field.TermVector.WITH_POSITIONS_OFFSETS));   // 3
    doc.add(new Field("title2",                   // 3
                      title.toLowerCase(),        // 3
                      Field.Store.YES,            // 3
                      Field.Index.NOT_ANALYZED_NO_NORMS,   // 3
                      Field.TermVector.WITH_POSITIONS_OFFSETS));  // 3

    // split multiple authors into unique field instances
    String[] authors = author.split(",");            // 3
    for (String a : authors) {                       // 3
      doc.add(new Field("author",                    // 3
                        a,                           // 3
                        Field.Store.YES,             // 3
                        Field.Index.NOT_ANALYZED,    // 3
                        Field.TermVector.WITH_POSITIONS_OFFSETS));   // 3
    }

    doc.add(new Field("url",                        // 3
                      url,                           // 3
                      Field.Store.YES,                // 3
                      Field.Index.NOT_ANALYZED_NO_NORMS));   // 3
    doc.add(new Field("subject",                     // 3  //4
                      subject,                       // 3  //4
                      Field.Store.YES,               // 3  //4
                      Field.Index.ANALYZED,          // 3  //4
                      Field.TermVector.WITH_POSITIONS_OFFSETS)); // 3  //4

    doc.add(new NumericField("pubmonth",          // 3
                             Field.Store.YES,     // 3
                             true).setIntValue(Integer.parseInt(pubmonth)));   // 3

    Date d; // 3
    try { // 3
      d = DateTools.stringToDate(pubmonth); // 3
    } catch (ParseException pe) { // 3
      throw new RuntimeException(pe); // 3
    }                                             // 3
    doc.add(new NumericField("pubmonthAsDay")      // 3
                 .setIntValue((int) (d.getTime()/(1000*3600*24))));   // 3

    for(String text : new String[] {title, subject, author, category}) {           // 3 // 5
      doc.add(new Field("contents", text,                             // 3 // 5
                        Field.Store.NO, Field.Index.ANALYZED,         // 3 // 5
                        Field.TermVector.WITH_POSITIONS_OFFSETS));    // 3 // 5
    }

    return doc;
  }

  private static String aggregate(String[] strings) {
    StringBuilder buffer = new StringBuilder();

    for (int i = 0; i < strings.length; i++) {
      buffer.append(strings[i]);
      buffer.append(" ");
    }

    return buffer.toString();
  }

  private static void findFiles(List<File> result, File dir) {
    for(File file : dir.listFiles()) {
      if (file.getName().endsWith(".properties")) {
        result.add(file);
      } else if (file.isDirectory()) {
        findFiles(result, file);
      }
    }
  }

  private static class MyStandardAnalyzer extends StandardAnalyzer {  // 6
    public MyStandardAnalyzer(Version matchVersion) {                 // 6
      super(matchVersion);                                            // 6
    }                                                                 // 6
    public int getPositionIncrementGap(String field) {                // 6
      if (field.equals("contents")) {                                 // 6
        return 100;                                                   // 6
      } else {                                                        // 6
        return 0;                                                     // 6
      }
    }
  }

  public static void main(String[] args) throws IOException {
    String dataDir = args[0];
    String indexDir = args[1];
    List<File> results = new ArrayList<File>();
    findFiles(results, new File(dataDir));
    System.out.println(results.size() + " books to index");
    Directory dir = FSDirectory.open(new File(indexDir));
    IndexWriter w = new IndexWriter(dir,
                                    new MyStandardAnalyzer(Version.LUCENE_29),
                                    true,
                                    IndexWriter.MaxFieldLength.UNLIMITED);
    for(File file : results) {
      Document doc = getDocument(dataDir, file);
      w.addDocument(doc);
    }
    w.close();
    dir.close();
  }
}

/*
  #1 Get category
  #2 Pull fields
  #3 Add fields to Document instance
  #4 Flag subject field
  #5 Add catch-all contents field
  #6 Custom analyzer to override multi-valued position increment
*/
