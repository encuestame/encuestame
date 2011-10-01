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
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import org.apache.pdfbox.cos.COSDocument;
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

/**
 * Indexer File description.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since Apr 5, 2011
 */
public class IndexerFile {

    /** Attachment text content. **/
    protected static final String CONTENT = "content";

    /** Attachment full path. **/
    protected static final String FULLPATH = "fullpath";

    /** Attachment file name. **/
    protected static final String FILENAME = "filename";

    /** Attachment Id. **/
    protected static final String DOCUMENTID = "documentId";

    /** Attachment upload date. **/
    protected static final String UPLOAD_DATE = "uploadDate";

    /** Attachment type. **/
    protected static final String DOCUMENT_TYPE = "documentType";

    /** Attachment title. **/
    protected static final String ATTACHMENT_TITLE = "title";

    /** Log. **/
    private static final Log log = LogFactory.getLog(IndexerFile.class);

    /** Auto commit option. **/
    private boolean autoCommit = true;

    /**
     * Create standard lucene document
     * @param attachFile
     * @return {@link Document} doc
     */
    public static Document createStandardLuceneDocument(AttachmentIndex attachFile) {
            Document doc = new Document();
            doc.add(new Field(CONTENT, attachFile.getContent(), Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field(FULLPATH, attachFile.getFilepath(), Field.Store.YES, Field.Index.NO));
            doc.add(new Field(FILENAME, attachFile.getFilename(), Field.Store.YES, Field.Index.NO));
            doc.add(new Field(DOCUMENTID, attachFile.getDocumentId().toString(), Field.Store.YES, Field.Index.NO));
            doc.add(new Field(UPLOAD_DATE, attachFile.getUploadDate().toString(), Field.Store.YES, Field.Index.NO));
            doc.add(new Field(DOCUMENT_TYPE, attachFile.getDocumentType(), Field.Store.YES, Field.Index.NO));
            doc.add(new Field(ATTACHMENT_TITLE, attachFile.getTitle(), Field.Store.YES, Field.Index.NO));
           return doc;
        }

    /**
     * Add files to index
     * @param attachment
     */
    public static void addToIndex(final AttachmentIndex attachment, final IndexWriterManager indexWriter) {
        try {
            long start = System.currentTimeMillis();
            indexWriter.openIndexWriter();
            IndexerFile.addDocumentToIndex(attachment, indexWriter);
            log.debug("Add to search index for topic " + attachment.getFilename() + " in " + ((System.currentTimeMillis() - start) / 1000.000) + " s.");
        } catch (Exception e) {
            log.error("Exception while adding topic " + attachment.getFilename(), e);
        }
    }

    /**
     * Add document to index.
     * @param documentAttachment
     * @throws IOException
     */
    private static void addDocumentToIndex(final AttachmentIndex documentAttachment, final IndexWriterManager indexWriter)
                                    throws IOException {
        Document standardLuceneDocument =  createStandardLuceneDocument(documentAttachment);
        indexWriter.getIndexWriter().addDocument(standardLuceneDocument);
    }

    /**
     * Delete attachment from index.
     * @param topic
     */
     public void deleteAttachmentFromIndex(AttachmentIndex attachmentIndex, final IndexWriterManager indexWriter ) {
         try {
             long start = System.currentTimeMillis();
             // delete the current document
             indexWriter.getIndexWriter();
             this.deleteFromIndex(attachmentIndex, indexWriter);
             log.debug("Delete from search index for topic " + attachmentIndex.getFilename() + " in " + ((System.currentTimeMillis() - start) / 1000.000) + " s.");
         } catch (Exception e) {
             log.error("Exception while adding topic " + attachmentIndex.getFilename(), e);
         }
     }


    /**
     * Delete Document from index
     * @param topic
     * @throws IOException
     */
     private void deleteFromIndex(AttachmentIndex attachmentIndex, final IndexWriterManager indexWriter) throws IOException {
         indexWriter.getIndexWriter().deleteDocuments(new Term(FILENAME, attachmentIndex.getFilename()));
     }

    /**
     * Commit into lucene index.
     * @param commitNow
     * @throws IOException
     */
     private void commit(final boolean commitNow, final IndexWriterManager indexWriter) throws IOException {
         if (commitNow) {
             indexWriter.getIndexWriter().commit();
         }
     }

    /**
     * Create Attachment Document.
     * @param file
     * @return
     * @throws IOException
     */
     public static AttachmentIndex createAttachmentDocument(final File file, final Long attachmentId) throws IOException{
        final String path = file.getCanonicalPath();
        final String fileExtension = SearchUtils.getExtension(path);
        final String filename = file.getName();
        String contentText = "";
        AttachmentIndex attachmentIndexBean = new AttachmentIndex();
        log.debug("Creating attachment document type --> "+fileExtension);
        if ("docx".equals(fileExtension)) {
            XWPFWordExtractor parserDoc;
            try {
                //1- Parsear word Document
                parserDoc = IndexerFile.parseWordDocument(file);
                //2- Extract word document content
                contentText = IndexerFile.extractContentWordDocument(parserDoc);
                //3- Set values to Attachment Index

            } catch (POIXMLException e) {
                log.error("Fail createAttachmentDocument POIXMLException --> " + e);
            } catch (Exception e) {
                log.error("Fail createAttachmentDocument Exception --> " + e);
            }
         } else if ("pdf".equals(fileExtension)) {
            PDDocument parsePdf;
            parsePdf = IndexerFile.parsePdfDocument(file);
            try {
                contentText = IndexerFile.extractContentPdfDocument(parsePdf);
            } catch (Exception e) {
                log.error("Fail createAttachmentDocument PDF Exception --> " + e);
            }
         }
         else if ("xls".equals(fileExtension) ) {
            HSSFWorkbook parseSpreadsheets;
            try {
                parseSpreadsheets = IndexerFile.parseSpreadsheetsDocument(file);
                contentText = extractContentSpreadsheetsDocument(parseSpreadsheets);
            } catch (Exception e) {
                log.error("Fail createAttachmentDocument spreadsheets Exception --> " + e);
            }
         }
         else if ("txt".equals(fileExtension) ) {
            contentText = "Document text file";
         }
        attachmentIndexBean.setContent(contentText);
        attachmentIndexBean.setFilepath(path);
        attachmentIndexBean.setFilename(filename);
        attachmentIndexBean.setDocumentId(attachmentId);
        attachmentIndexBean.setUploadDate(new Date());
        attachmentIndexBean.setDocumentType(fileExtension);
        attachmentIndexBean.setTitle("ENCUESTAME - TITLE");
        return attachmentIndexBean;
     }

    /**
     * Parse Word Document.
     * @param file
     * @return
     * @throws POIXMLException
     * @throws Exception
     */
    public static XWPFWordExtractor parseWordDocument(final File file) throws POIXMLException, Exception {
        InputStream is = new FileInputStream(file);
        XWPFWordExtractor wde = null;
        try {
            XWPFDocument wd = new XWPFDocument(is);
            wde = new XWPFWordExtractor(wd);
            log.debug("Parse Word Document --------------------------> ");
        } catch (Exception e) {
            log.error("ERROR parse Word Document-------->"+ e);
        }
    return wde;
    }

    /**
     * Extract word document content.
     * @param wde
     * @return
     */
    public static String extractContentWordDocument(final XWPFWordExtractor wde){
        String bodyText = null;
        try {
            bodyText = wde.getText();
        } catch (Exception e) {
            log.error("ERROR extracting content Word Document-------->"+ e);
        }
        return bodyText;
    }

    /**
     * Parse pdf Document.
     * @param file
     * @return
     * @throws IOException
     */
     public static PDDocument parsePdfDocument(final File file) throws IOException {
         InputStream is = new FileInputStream(file);
         COSDocument cosDoc = null;
         PDDocument pdDoc = null;
         try {
             cosDoc = SearchUtils.parseDocument(is);
             pdDoc = new PDDocument(cosDoc);
         } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e);
        }
         finally {
              if( pdDoc == null ) {
                  log.error("PdDocument is null");
              } else {
                  pdDoc.close();
              }
         }

         return pdDoc;
     }

    /**
     * Extract content in PDF Document.
     * @param pdfDoc
     * @return
     * @throws Exception
     */
     public static String extractContentPdfDocument(final PDDocument pdfDoc) throws Exception {
         String docText = null;
         try {
             PDFTextStripper stripper = new PDFTextStripper();
             docText = stripper.getText(pdfDoc);
             log.debug("Extract content pdf document leng ----> "+ docText.length());
         }
         finally {
              if( docText == null ) {
                  log.error("****************   PDF content is null   *********************");
              }
         }
         return docText;
     }

    /**
     *
     * @param author
     * @param title
     * @param producer
     * @param subject
     * @return
     */
     public static AttachmentIndex addMetadatatoBean(final String author, final String title, final String producer,
            final String subject){
        AttachmentIndex attachmentPdfMetadata = new AttachmentIndex();
        if (StringUtils.isNotEmpty(author)) {
            attachmentPdfMetadata.setAuthor(author);
        }
        if (StringUtils.isNotEmpty(title)) {
            attachmentPdfMetadata.setTitle(title);

        }
        if (StringUtils.isNotEmpty(producer)) {
            attachmentPdfMetadata.setProducer(producer);
         }
        if (StringUtils.isNotEmpty(subject)) {
            attachmentPdfMetadata.setSubject(subject);
        }

        return attachmentPdfMetadata;
     }

     /**
      * Parse spreadsheets documents.
      * @param file
      * @return
      * @throws Exception
      */
     public static HSSFWorkbook parseSpreadsheetsDocument(final File file) throws Exception {
         InputStream is = new FileInputStream(file);
         POIFSFileSystem fileSystem = new POIFSFileSystem(is);
         HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
         return workBook;
     }

    /**
     * Extract spreadsheets content.
     * @param workBook
     * @return
     * @throws Exception
     */
     public static String extractContentSpreadsheetsDocument(final HSSFWorkbook workBook) throws Exception {
         StringBuilder contents = new StringBuilder();
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
         return contents.toString();
     }
}
