
package org.encuestame.business.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.encuestame.business.search.AttachmentSearchItem;
import org.encuestame.business.search.SearchManagerOperation;
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
     *
     * @param keyword
     * @param maxResults
     * @param field
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public List<AttachmentSearchItem> getAttachmentItem(final String keyword,
            final int maxResults, final String field) throws IOException,
            ParseException {
        final List<Document> attachItems = getSearchOperation().search(keyword,
                maxResults, field);
        return this.convertDocumentToListAttachment(attachItems);
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

    /**
     * Convert Document Lucene List to Attachment Search Item List.
     * @param docAttach Attachment
     * @return
     */
    public static final  List<AttachmentSearchItem> convertDocumentToListAttachment(final List<Document> docAttach) {
        final List<AttachmentSearchItem> attachmentItems = new ArrayList<AttachmentSearchItem>();
          for (Document item : docAttach) {
            attachmentItems.add(convertDoctoSearchItem(item));
          }
         return attachmentItems;
    }

    /**
     * Convert Lucene Document Item to Attachment Search Bean.
     * @param docItem Lucene Document
     * @return
     */
    public static final AttachmentSearchItem convertDoctoSearchItem(final Document document){
        final AttachmentSearchItem attachItem = new AttachmentSearchItem();
        attachItem.setAttachId(1L);
        attachItem.setDescription(document.get("filename"));
        return attachItem;
    }
}

