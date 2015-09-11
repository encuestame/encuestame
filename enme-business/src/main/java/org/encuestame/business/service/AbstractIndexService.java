
package org.encuestame.business.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.business.search.SearchManagerOperation;
import org.encuestame.core.service.AbstractBaseService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.web.HashTagBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provide of index/search layer.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 18, 2011
 */
@Service
public abstract class AbstractIndexService extends AbstractBaseService{

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());

    /** Search Manager Operation**/
    @Autowired(required = false)
    private SearchManagerOperation searchOperation; //TODO:ENCUESTAME-154

    /**
     * List Suggested Hash Tags.
     * @param hashTagKeyWord keyword to search
     * @param maxResults limit of results
     * @return list of hash tags.
     */
    public List<HashTagBean> listSuggestHashTags(
            final String hashTagKeyWord,
            final Integer maxResults,
            final Long[] exludes){
        final List<HashTag> tags = getHashTagDao().getListHashTagsByKeyword(hashTagKeyWord, maxResults, exludes);
        log.debug("Hash Tag Suggested size "+tags.size());
        return ConvertDomainBean.convertListHashTagsToBean(tags);
    }

    /**
     * Return suggested list of questions by keyword on indexed results.
     * @param keyword keyword to search
     * @param userId userId
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public List<Question> retrieveQuestionByKeyword(final String keyword, final Long userId)
           throws EnMeNoResultsFoundException{
        final List<Question> questions = getQuestionDao().retrieveIndexQuestionsByKeyword(keyword,
                                         userId, null, null);
        return questions;
    }
  
    /**
    * @return the searchOperation
    */
    public SearchManagerOperation getSearchOperation() {
        return searchOperation;
    }

    /**
    * @param searchOperation the searchOperation to set
    */
    public void setSearchOperation(final SearchManagerOperation searchOperation) {
        this.searchOperation = searchOperation;
    }  
}

