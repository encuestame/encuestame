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
package org.encuestame.core.persistence.dao;

import java.util.List;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.encuestame.core.persistence.dao.imp.IHashTagDao;
import org.encuestame.core.persistence.domain.HashTag;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * {@link HashTag} Dao.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 25, 2010 5:30:46 PM
 * @version Id:
 */
public class HashTagDao extends AbstractHibernateDaoSupport implements IHashTagDao {

    /**
     * Create Hash TAg.
     * @param hashTag
     * @throws HibernateException
     */
    public void createHashTag(final HashTag hashTag) throws HibernateException {
        saveOrUpdate(hashTag);
    }

    /**
     * Get HashTag By Name.
     * @param hashTag
     * @return
     * @throws HibernateException
     */
    @SuppressWarnings("unchecked")
    public HashTag getHashTagByName(final String hashTag)throws HibernateException {
        final DetachedCriteria criteria = DetachedCriteria.forClass(HashTag.class);
        criteria.add(Restrictions.eq("hashTag", hashTag) );
        return (HashTag) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
}

    /**
     * Get List of HashTags by Keyword.
     * @param keyword keyword
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<HashTag> getListHashTagsByKeyword(final String keyword, final Integer maxResults){
        log.info("keyword "+keyword);
        List<HashTag> searchResult = (List) getHibernateTemplate().execute(
                new HibernateCallback() {
                    @SuppressWarnings("deprecation")
                    public Object doInHibernate(org.hibernate.Session session) {
                        try {
                            final FullTextSession fullTextSession = Search.getFullTextSession(session);
                            //fullTextSession.flushToIndexes();
                            final MultiFieldQueryParser parser = new MultiFieldQueryParser(
                                                  new String[]{"hashTag"},
                                                  new SimpleAnalyzer());
                            final org.apache.lucene.search.Query query = parser.parse(keyword);
                            final FullTextQuery hibernateQuery = fullTextSession.createFullTextQuery(query, HashTag.class);
                            hibernateQuery.setMaxResults(maxResults);
                            final List<HashTag> result = hibernateQuery.list();
                            log.info("result LUCENE "+result.size());
                            return result;
                        }
                        catch (ParseException ex) {
                            log.error("Index Search Error "+ex.getMessage());
                            return null;
                        }
                    }
                });
        return searchResult;
    }
}
