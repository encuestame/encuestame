/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.cron;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.Question;
import org.encuestame.persistence.domain.survey.TweetPollResult;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Index Rebuilder.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 8, 2010 10:54:12 PM
 * @version $Id:$
 */
public class IndexRebuilder {

    /**
     * Log.
     */
    private static final Log log = LogFactory.getLog(IndexRebuilder.class);

    /**
     * List of Classes.
     */
    private List<Class> classes;

    /**
     * {@link SessionFactory}.
     */
    @Autowired
    public HibernateTemplate hibernateTemplate;

    /**
     * Getter.
     * @return
     */
    public List<Class> getClasses() {
        return classes;
    }

    /**
     * Setter.
     * @param classes
     */
    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }

    /**
     * Reindex Entities.
     * @throws Exception exception.
     */
    public void reindexEntities() throws Exception {
        log.debug("reindexEntities");
        FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(getHibernateTemplate().getSessionFactory().openSession());
        reindex(fullTextSession, Question.class);
        reindex(fullTextSession, TweetPollResult.class);
        reindex(fullTextSession, HashTag.class);
        fullTextSession.close();
    }

    /**
     * Reindex.
     * @param fullTextSession
     * @param clazz
     */
    public static void reindex(final FullTextSession fullTextSession, final Class<?>
    clazz) {
            log.debug(clazz.getName() + " starting index ...");
            final long startTime = System.currentTimeMillis();
            fullTextSession.setFlushMode(FlushMode.MANUAL);
            fullTextSession.setCacheMode(CacheMode.IGNORE);
            final Transaction transaction = fullTextSession.beginTransaction();
            //Scrollable results will avoid loading too many objects in memory
            final ScrollableResults results = fullTextSession.createCriteria(clazz)
                .setFetchSize(100)
                .scroll(ScrollMode.FORWARD_ONLY);
            int index = 0;
            while(results.next()) {
                index++;
                fullTextSession.index(results.get(0));
                if (index % 100 == 0) {
                    fullTextSession.flushToIndexes();
                    fullTextSession.flush();
                    fullTextSession.clear();
                }
            }
            transaction.commit();
            log.debug(clazz.getName() + " Reindex end in "
                    + ((System.currentTimeMillis() - startTime) / 1000.0)
                    + " seconds.");
        }

    /**
     * @return the hibernateTemplate
     */
    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    /**
     * @param hibernateTemplate the hibernateTemplate to set
     */
    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

}
