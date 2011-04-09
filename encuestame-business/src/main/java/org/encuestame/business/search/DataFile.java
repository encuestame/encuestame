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

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

/**
 * Metadata file.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since Apr 7, 2011
 */
public class DataFile {

    /** Author file. **/
    private static String author = "";

    /** Title file. **/
    private static String title = "";

    /** producer file. **/
    private static String producer = "";

    /** Subject file. **/
    private static String subject = "";

    /**
     * Extract Metadata in PDF Documents.
     * @param pdDoc
     * @return
     */
    public static AttachmentIndex extractMetadataPDFDocument(final PDDocument pdDoc){
       PDDocumentInformation docInfo = pdDoc.getDocumentInformation();
       author = docInfo.getAuthor();
       title = docInfo.getTitle();
       producer = docInfo.getProducer();
       subject = docInfo.getSubject();
       AttachmentIndex attachmentMetadata =  IndexerFile.addMetadatatoBean(author, title, producer, subject);
       return attachmentMetadata;
    }
}
