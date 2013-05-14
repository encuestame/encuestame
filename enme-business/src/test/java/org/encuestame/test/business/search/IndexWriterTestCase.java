/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.test.business.search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.encuestame.business.search.IndexWriterManager;
import org.encuestame.business.search.IndexerManager;
import org.encuestame.business.search.SearchAttachmentManager;
import org.encuestame.core.service.imp.IIndexWriter;
import org.encuestame.test.business.service.config.AbstractServiceBase;
import org.encuestame.utils.categories.test.DefaultTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class description.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since Apr 12, 2011
 */
@Category(DefaultTest.class)
public class IndexWriterTestCase extends AbstractServiceBase{

    /**
    * IIndexWriter.
    */
    @Autowired
    private IIndexWriter indexWriter;

    @Autowired
    private IndexerManager indexerManager;

    @Autowired
    private SearchAttachmentManager searcher;

    /**
     * {@link File}.
     */
    private File file = new File("src/main/resources/search/");

    /**
     * Test {@link IndexWriterManager}.
     * @throws IOException
     */
    //@Test
    public void testIndexWriter() throws IOException{
        Assert.assertNotNull(this.indexWriter);
        //this.indexWriter.openIndexWriter();
        final IndexWriter i = this.indexWriter.getIndexWriter();
        //for (int j = 0; j < 50; j++) {
            Document doc = new Document();
            //doc.add(new Field("contents", new FileReader(f))); // Index file contents
            doc.add(new Field("filename", "DOJO FILENANME", Field.Store.YES,
                    Field.Index.NOT_ANALYZED)); // Index File Name.
            doc.add(new Field("fullpath", "/home/dmorales/dojofullpath", Field.Store.YES,
                    Field.Index.NOT_ANALYZED)); // Index Full Path.
            i.addDocument(doc); // Add Document to Lucene Index.
       // }
        this.indexWriter.closeIndexWriter();
    }



    @Test
    public void testIndexSearcher() throws Exception{
        Assert.assertNotNull(this.indexerManager);
        final List<File> files = new ArrayList<File>();
        Assert.assertTrue(file.exists());
        if(this.file.exists()){
            files.add(this.file);
        }
        //for (int i = 0; i < 10; i++) {
            this.indexerManager.initializeIndex(files);
        //}

        Assert.assertNotNull(this.searcher);
        final String queryText = "hello";
        final List<Document> queryList = searcher.search(queryText, 10, "filename");

        Assert.assertEquals(0, queryList.size());

    }

    /**
    * @param indexWriter the indexWriter to set
    */
    public void setIndexWriter(IIndexWriter indexWriter) {
        this.indexWriter = indexWriter;
    }

    /**
    * @param indexerManager the indexerManager to set
    */
    public void setIndexerManager(IndexerManager indexerManager) {
        this.indexerManager = indexerManager;
    }

}
