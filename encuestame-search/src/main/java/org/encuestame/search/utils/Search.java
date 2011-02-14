/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.search.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * Search Service.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since February 09, 2011
 * @version $Id$
 */
public class Search {


    private IndexWriter writer;

    public void getIndexer(String indexDirPath) throws IOException{
        Directory dir = FSDirectory.open(new java.io.File(indexDirPath));
        this.writer = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_29) ,
                true, IndexWriter.MaxFieldLength.UNLIMITED);

    }


    public int index(String dataDir, FileFilter filter) throws Exception {
        File[] files = new File(dataDir).listFiles();
        for (File f : files) {
            if (!f.isDirectory() && !f.isHidden() && f.exists() && f.canRead()
                    && (filter == null || filter.accept(f))) {
                indexFile(f);
            }
        }
        return writer.numDocs();

    }

    private void indexFile(File f) throws Exception {
        System.out.println("Indexing " + f.getCanonicalPath());
     //   Document doc = getDocument(f);
      //  writer.addDocument(doc); // Add Document to Lucene Index.
    }

    /**
    * @return the writer
    */
    public IndexWriter getWriter() {
        return writer;
    }

    /**
    * @param writer the writer to set
    */
    public void setWriter(IndexWriter writer) {
        this.writer = writer;
    }



}
