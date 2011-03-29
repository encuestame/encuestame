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
package org.encuestame.search;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.encuestame.business.search.SearchAttachmentManager;
import org.encuestame.test.config.AbstractBase;
import org.junit.Test;

/**
 * Test Search Attachment Documents.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since Mar 24, 2011
 */
public class TestSearchManager extends AbstractBase{

    /**
    * Search Documents.
    */
    @Test
    public void testSearchDocuments(){
        final SearchAttachmentManager manager = new SearchAttachmentManager(getProperty("data.test.index"));
        final String query = "java";
        final int max = 10;
        final String fieldIndex = "content";
        System.out.println("Index Directory-->"+ getProperty("data.test.index"));
        assertNotNull(manager);

        try {
        final List<Document> documentList = manager.search(query, max, fieldIndex);
        System.out.println("Total Documents ---->"+ documentList.size());
        assertNotNull(documentList.size());
        // To get all document properties uncomment the next lines
        for (Document document : documentList) {
            System.out.println("Document -->"+document);
            //To get any property, something like this
            System.out.println("Filename -->"+ document.get("filename"));
        }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
