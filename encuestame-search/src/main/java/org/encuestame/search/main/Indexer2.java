package org.encuestame.search.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocCollector;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.poi.POIXMLException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.pdfbox.cos.COSDocument;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDDocumentInformation;
import org.pdfbox.util.PDFTextStripper;



/**
 * Create an Index.
 * Store information indexed
 * @author dmorales
 */
public class Indexer2 {

    /**
     * @param indexDir The path to the index created with Indexer.
     * @param dataDir A query to use to search the index.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        if (args.length != 2) {
            throw new IllegalArgumentException(
                    "Usage: java "
                            + Indexer.class.getName()
                            + " <index dir> <data dir>");

        }
        String indexDir = args[0]; // Create Index in this directory.
        String dataDir = args[1]; // Index *.txt files from this directory

        long start = System.currentTimeMillis();
        Indexer2 indexer2 = new Indexer2(indexDir);
        int numIndexed;
        try {
           // final String extension = indexer2.getExtension(path)
            numIndexed = indexer2.index2(dataDir);
            indexer2.searcher(indexDir);
        } finally {
            indexer2.close();
        }
        long end = System.currentTimeMillis();
        System.out.println("Indexing " + numIndexed + " files took "
                + (end - start) + " milliseconds");
    }

    private IndexWriter writer;

    /**
     * Create Lucene IndexWriter.
     * @param indexDir
     * @throws IOException
     */
    public Indexer2(String indexDir) throws IOException {
        Directory dir = FSDirectory.open(new java.io.File(indexDir));
        writer = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_29),
                true, IndexWriter.MaxFieldLength.UNLIMITED);
    }

    /**
     * Close Lucene IndexWriter.
     * @throws IOException
     */
    public void close() throws IOException {
        writer.close();
    }

    /**
     * Return number of documents indexed.
     * @param dataDir
     * @param filter
     * @return
     * @throws Exception
     */
    public int index2(String dataDir) throws Exception {
        File[] files = new File(dataDir).listFiles();
        for (File f : files) {
            if (!f.isDirectory() && !f.isHidden() && f.exists() && f.canRead()
            ) {
                indexFile(f); // Write documents in Index
            }
        }
        return writer.numDocs();
    }

    /**
     * Index .txt files only using FileFilter
     * @author dmorales
     */
        public String getExtension(File path) {
            String ruta = path.getName().toString();
              final String ext = ruta.substring(ruta.lastIndexOf('.')+1);
              System.out.println("PATH ------> "+ ruta);
              System.out.println("EXT ------> "+ ext);
            return ext;
            //path.getName().toLowerCase().endsWith(ext);
        }


    /*protected Document getDocument(File f) throws Exception {
        Document doc = new Document();
        doc.add(new Field("contents", new FileReader(f))); // Index file contents
        doc.add(new Field("filename", f.getName(), Field.Store.YES,
                Field.Index.NOT_ANALYZED)); // Index File Name.
        doc.add(new Field("fullpath", f.getCanonicalPath(), Field.Store.YES,
                Field.Index.NOT_ANALYZED)); // Index Full Path.
        return doc;
    }*/

    private void indexFile(File f) throws Exception {
        System.out.println("Indexing " + f.getCanonicalPath());
        String ruta = f.getName().toString();
        final String ext = ruta.substring(ruta.lastIndexOf('.')+1);
        System.out.println("PATH ------> "+ ruta);
        System.out.println("EXT ------> "+ ext);
        Document doc = getDocument(f, ext);
        writer.addDocument(doc); // Add Document to Lucene Index.
    }


    public Document getDocument(File file, String ext) throws POIXMLException, Exception {
        Document doc = null;
        if("docx".equals(ext)){
            doc = getDocumentWord(file);
        }
        else if ("xls".equals(ext)){
            doc = getSpreadsheets(file);
        }
        else if ("pdf".equals(ext)){
            doc = getPDFDocument(file);
        } else {
            throw new IllegalArgumentException("not type defined");
        }
        return doc;

    }

    public Document getPDFDocument(File file) throws Exception {
        InputStream is = new FileInputStream (file);
        COSDocument cosDoc = null;
        cosDoc = parseDocument(is);
        PDDocument pdDoc = new PDDocument(cosDoc);
        String docText = null;
        PDFTextStripper stripper = new PDFTextStripper();
        docText = stripper.getText(pdDoc);
        Document doc = new Document();
        if (StringUtils.isNotEmpty(docText)) {
        doc.add(new Field("content", docText,Field.Store.NO,Field.Index.ANALYZED));
        }
        // extract PDF document's meta-data

        PDDocumentInformation docInfo = pdDoc.getDocumentInformation();
        String author = docInfo.getAuthor();
        String title = docInfo.getTitle();
        String keywords = docInfo.getKeywords();
        String summary = docInfo.getSubject();
        if (StringUtils.isNotEmpty(author)) {

        doc.add(new Field("author", author,Field.Store.YES,Field.Index.NOT_ANALYZED));
        }
        if (StringUtils.isNotEmpty(title)) {
        doc.add(new Field("title", title ,Field.Store.YES,Field.Index.ANALYZED));
        }
        if (StringUtils.isNotEmpty(keywords)) {

        doc.add(new Field("keywords", keywords, Field.Store.YES,Field.Index.ANALYZED));
        }
        if (StringUtils.isNotEmpty(summary)) {
        doc.add(new Field("summary", summary,Field.Store.YES,Field.Index.ANALYZED));
        }

        return doc;
        }

        private static COSDocument parseDocument(InputStream is) throws IOException {
        PDFParser parser= null;
        parser = new PDFParser(is);

        parser.parse();
        return parser.getDocument();
        }

    /**
     * Parse Document Word.
     */
    public Document getDocumentWord(File file) throws POIXMLException, Exception{
        InputStream is = new FileInputStream (file);
        String bodyText = null;
        StringBuilder content = new StringBuilder();
        try {
            XWPFDocument wd = new XWPFDocument(is);
            XWPFWordExtractor wde = new XWPFWordExtractor(wd);
            bodyText = wde.getText();
        }catch(Exception e) {
            e.printStackTrace();
        }
        Document doc = new Document();
        if(!bodyText.equals("") && bodyText != null) {
            doc.add(new Field("content",bodyText,Field.Store.NO,Field.Index.ANALYZED));
        }
        return doc;
    }


    /**
     * Parse Spreasheets.
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public Document getSpreadsheets(File file) throws Exception{
        InputStream is = new FileInputStream (file);
        StringBuilder contents = new StringBuilder();
        POIFSFileSystem fileSystem = new POIFSFileSystem(is);
        HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
        for (int i =0; i<workBook.getNumberOfSheets();i++) {
           HSSFSheet sheet = workBook.getSheetAt(i);
           Iterator<Row> rows = sheet.rowIterator();
           while (rows.hasNext()) {
            HSSFRow row = (HSSFRow)rows.next();
            //Display the row number
            System.out.println(row.getRowNum());
            Iterator<Cell> cells=row.cellIterator();
            while(cells.hasNext()) {
                HSSFCell cell = (HSSFCell) cells.next();
                //Display the cell number of the current Row

                switch(cell.getCellType()) {
                    case HSSFCell.CELL_TYPE_NUMERIC : {
                        System.out.println(String.valueOf(cell.getNumericCellValue()));
                        contents.append(String.valueOf(cell.getNumericCellValue())).append(" ");
                    break;
                    }

                    case HSSFCell.CELL_TYPE_STRING : {
                        HSSFRichTextString richTextString =cell.getRichStringCellValue();
                        System.out.println(richTextString.toString());
                        contents.append(richTextString.toString()).append(" ");
                        break;
                    }

                    case HSSFCell.CELL_TYPE_BOOLEAN : {
                        contents.append(String.valueOf(cell.getBooleanCellValue())).append(" ");
                    break;
                    }
                }
            }
        }
       }
       Document doc = new Document();
       doc.add(new Field("content",contents.toString(),Field.Store.YES,Field.Index.ANALYZED));
       System.out.println(contents.toString());
       return doc;
       }

    public void searcher(final String indexDir) throws CorruptIndexException, IOException, ParseException{
        IndexReader reader = IndexReader.open(indexDir);
        IndexSearcher searcher = new IndexSearcher(reader);
        QueryParser parser = new QueryParser(Version.LUCENE_29, "content",
                new StandardAnalyzer(Version.LUCENE_29));
        Query query = parser.parse("again");
        TopDocCollector collector = new TopDocCollector(100);
        TopDocs hits2 = searcher.search(query, 10); // Search Index

        searcher.search(query , collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;
        System.out.println("----------->" +hits2.totalHits );
        String[] stopWords = StopAnalyzer.ENGLISH_STOP_WORDS;
        for(int i=0 ; i<hits.length ; i++) {
        int doc = hits[i].doc;
        Document doc1 = searcher.doc(doc);
        System.out.println(doc1.get("filename"));
        System.out.println("Document id is "+doc);
        }
        System.out.println("Total number of docs "+reader.maxDoc());
    }

    }

