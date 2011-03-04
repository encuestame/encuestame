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
package org.encuestame.search.main;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.search.utils.Search;

/**
 * Poll Indexer.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since February 23, 2011
 * @version $Id$
 */
public class PollIndexer {
    private Search search;


    private Document generateDocumentFromPoll(Poll poll) {
        Document doc = new Document();
        doc.add(new Field("ID", String.valueOf(poll.getPollId()),
                Field.Store.YES, Field.Index.NO));
        doc.add(new Field("POLLNAME", poll.getName(),
                Field.Store.YES, Field.Index.ANALYZED));
        return doc;
    }

    public void indexPoll(Poll poll, String path) throws IOException {
        IndexWriter writer = search.getIndexer(path);
         try {
            Document doc = generateDocumentFromPoll(poll);
            writer.addDocument(doc);
            writer.commit();
            writer.optimize();
        } finally {
            writer.close();
        }
    }


}
