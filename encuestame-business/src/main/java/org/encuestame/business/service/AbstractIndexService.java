
package org.encuestame.business.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.QuestionBean;

/**
 * Provide of index/search layer.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 18, 2011
 */
public abstract class AbstractIndexService extends AbstractBaseService{

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * List Suggested Hash Tags.
     * @param hashTagKeyWord keyword to search
     * @param maxResults limit of results
     * @return list of hash tags.
     */
    public List<HashTagBean> listSuggestHashTags(final String hashTagKeyWord, final Integer maxResults){
        final List<HashTag> tags = getHashTagDao().getListHashTagsByKeyword(hashTagKeyWord, maxResults);
        log.debug("Hash Tag Suggested size "+tags.size());
        return ConvertDomainBean.convertListHashTagsToBean(tags);
    }

    /**
     * Return suggested list of questions by keyword on indexed results.
     * @param keyword keyword to search
     * @param username username logged
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public List<QuestionBean> searchIndexedQuestions(final String keyword, final String username)
           throws EnMeNoResultsFoundException{
        final List<Question> questions = getQuestionDao().retrieveIndexQuestionsByKeyword(keyword,
                                         getUserAccount(username).getAccount().getUid());
        return ConvertDomainBean.convertListToUnitQuestionBean(questions);
    }
}
