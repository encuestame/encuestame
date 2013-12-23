/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.business.search;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
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
import org.encuestame.core.search.DirectoryIndexStore;
import org.springframework.util.Assert;

/**
 * Search Utils.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since Mar 23, 2011
 */
public class SearchUtils {

    /****/
    protected static final String CONTENT = "content";

    /****/
    protected static final String FULLPATH = "fullpath";

    /****/
    protected static final String FILENAME = "filename";

    /** Lucene Version. **/
    public static final Version LUCENE_VERSION = Version.LUCENE_30;

    /**
    * Log
    */
    private static final Log log = LogFactory.getLog(SearchUtils.class);

    /**
    * Get Filename extension.
    * @param path fullname file
    * @return
    */
    public static String getExtension(final String path) {
       final String ext = path.substring(path.lastIndexOf('.') + 1);
       log.debug("Path file " + path);
       log.debug("Ext file " + ext);
       return ext;
   }

   /**
    * PDF Document content parser.
    * @param is Document content
    * @return
    * @throws IOException
    */
    public static COSDocument parseDocument(final InputStream is) throws IOException {
       PDFParser parser = null;
       parser = new PDFParser(is);
       parser.parse();
       return parser.getDocument();
   }

    /**
     * Add Lucene Document fields.
     * @param file
     * @param docText
     * @return
     * @throws IOException
     */
    public static Document addFields(final File file, final String docText) throws IOException{
        final String fullpath = file.getCanonicalPath();
        final String filename = file.getName();
        final Document doc = new Document();
        if (StringUtils.isNotEmpty(docText)) {
            doc.add(new Field(CONTENT, docText, Field.Store.NO,
                    Field.Index.ANALYZED));
            doc.add(new Field(FULLPATH, fullpath,
                    Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field(FILENAME, filename, Field.Store.YES,
                    Field.Index.NOT_ANALYZED));
        }
        return doc;
    }

   /**
    * Create PDF Document.
    * @param file {@link File}
    * @param Long attachmentId.
    * @return {@link Document}
    * @throws Exception
    */
    public static Document createPdfDocument(final File file) throws Exception {
       InputStream is = new FileInputStream(file);
       COSDocument cosDoc = null;
       String docText = "";
       PDDocument pdDoc = null;
       try {
           cosDoc = parseDocument(is);
           pdDoc = new PDDocument(cosDoc);
           PDFTextStripper stripper = new PDFTextStripper();
           docText = stripper.getText(pdDoc);
           log.debug("PDF Doc Text "+docText.length());
       }
       finally {
            if( pdDoc == null ) {
                log.error("PdDocument is null");
            } else {
                pdDoc.close();
            }
       }
       final Document doc = SearchUtils.addFields(file, docText);
       return doc;
   }

    /**
    * Create Document Word.
    * @param file {@link File}
    * @param Long attachmentId.
    * @return {@link Document}
    * @throws POIXMLException
    * @throws Exception
    */
    public static Document createWordDocument(final File file) throws POIXMLException,
           Exception {
       InputStream is = new FileInputStream(file);
       String bodyText = null;
       try {
           XWPFDocument wd = new XWPFDocument(is);
           XWPFWordExtractor wde = new XWPFWordExtractor(wd);
           bodyText = wde.getText();
       } catch (Exception e) {
           log.debug(e);
       }
       Document doc = SearchUtils.addFields(file, bodyText);
       return doc;
   }

    /**
    * Create Spreadsheets Document.
    * @param file Spreadsheet {@link File}.
    * @param Long attachmentId.
    * @return {@link Document}
    * @throws FileNotFoundException
    */
    public static Document createSpreadsheetsDocument(final File file) throws Exception {
       InputStream is = new FileInputStream(file);
       StringBuilder contents = new StringBuilder();
       POIFSFileSystem fileSystem = new POIFSFileSystem(is);
       HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
       for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
           HSSFSheet sheet = workBook.getSheetAt(i);
           Iterator<Row> rows = sheet.rowIterator();
           while (rows.hasNext()) {
               HSSFRow row = (HSSFRow) rows.next();
               // Display the row number
               log.debug(row.getRowNum());
               Iterator<Cell> cells = row.cellIterator();
               while (cells.hasNext()) {
                   HSSFCell cell = (HSSFCell) cells.next();
                   // Display the cell number of the current Row
                   switch (cell.getCellType()) {
                   case HSSFCell.CELL_TYPE_NUMERIC: {
                       log.debug(String.valueOf(cell
                               .getNumericCellValue()));
                       contents.append(
                               String.valueOf(cell.getNumericCellValue()))
                               .append(" ");
                       break;
                   }

                   case HSSFCell.CELL_TYPE_STRING: {
                       HSSFRichTextString richTextString = cell
                               .getRichStringCellValue();
                       log.debug(richTextString.toString());
                       contents.append(richTextString.toString()).append(" ");
                       break;
                   }

                   case HSSFCell.CELL_TYPE_BOOLEAN: {
                       contents.append(
                               String.valueOf(cell.getBooleanCellValue()))
                               .append(" ");
                       break;
                   }
                   }
               }
           }
       }
       Document doc = SearchUtils.addFields(file, contents.toString());
       return doc;
   }

    /**
    * Create Text Document.
    * @param file Text File.
    * @param Long attachmentId.
    * @return {@link Document}
    * @throws Exception
    */
    public static Document createTextDocument(final File file) throws Exception {
    	//FIXME: 'FileReader' is never closed
        final String docText = new FileReader(file).toString();
        final Document doc = SearchUtils.addFields(file, docText);
        return doc;
   }

    /**
     * Open Index Writer
     * @param directoryStore
     * @param indexWriter
     * @throws CorruptIndexException
     * @throws LockObtainFailedException
     * @throws IOException
     */
    public static IndexWriter openIndexWriter(
            final DirectoryIndexStore directoryStore, IndexWriter indexWriter)
            throws CorruptIndexException, LockObtainFailedException,
            IOException {
        final Directory directory = directoryStore.getDirectory();
        log.debug("Get Directory ----------" + directory.toString());
        if(indexWriter != null){
        indexWriter.close();
        }
        log.debug("Index Directory is locked?  ----------> " + indexWriter.isLocked(directory));
        indexWriter = new IndexWriter(directory, new StandardAnalyzer(
                SearchUtils.LUCENE_VERSION), true,
                IndexWriter.MaxFieldLength.UNLIMITED);
        Assert.notNull(indexWriter);
        return indexWriter;
    }

    /**
     * Close Index writer.
     * @param indexWriter
     * @throws CorruptIndexException
     * @throws IOException
     */
    public static void closeIndexWriter(final IndexWriter indexWriter) throws CorruptIndexException, IOException{
        Assert.notNull(indexWriter);
        if (indexWriter == null){
            log.error("Index writer is null");
        } else {
           indexWriter.close();
           log.debug("Index writer was closed");
        }
    }
}
