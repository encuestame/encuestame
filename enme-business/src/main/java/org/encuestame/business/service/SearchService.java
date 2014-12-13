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
package org.encuestame.business.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.queryParser.ParseException;
import org.encuestame.business.search.AttachmentIndex;
import org.encuestame.business.search.IndexWriterManager;
import org.encuestame.business.search.IndexerFile;
import org.encuestame.business.search.UtilConvertToSearchItems;
import org.encuestame.core.search.GlobalSearchItem;
import org.encuestame.core.service.imp.SearchServiceOperations;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.Attachment;
import org.encuestame.persistence.domain.Comment;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.enums.TypeSearch;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.json.SearchBean;
import org.encuestame.utils.web.PollBean;
import org.encuestame.utils.web.UnitAttachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Search Service.
 *
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since February 09, 2011
 */
@Service
@Transactional
public class SearchService extends AbstractIndexService implements
        SearchServiceOperations {

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * {@link IndexWriterManager}.
     */
    @Autowired
    private IndexWriterManager indexWriter; //TODO:ENCUESTAME-154

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.SearchServiceOperations#quickSearch(java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    public Map<String, List<GlobalSearchItem>> quickSearch(String keyword,
            final Integer start, final Integer limit) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.business.service.imp.SearchServiceOperations#quickSearch
     * (java.lang.String, java.lang.String)
     */
    public Map<String, List<GlobalSearchItem>> quickSearch(
            final String keyword,
            String language,
            final Integer start,
            final Integer limit,
            Integer limitByItem,
            final List<TypeSearchResult> resultsAllowed)
            throws EnMeNoResultsFoundException, IOException, ParseException {
        @SuppressWarnings("unchecked")
        final Map<String, List<GlobalSearchItem>> hashset = new HashedMap();
        limitByItem = limitByItem == null ? 0 : limitByItem;
        if (resultsAllowed.indexOf(TypeSearchResult.QUESTION) != -1) {
            List<GlobalSearchItem> questionResult = UtilConvertToSearchItems
                    .convertQuestionToSearchItem(retrieveQuestionByKeyword(keyword,
                            null));
            if (limitByItem != 0 && questionResult.size() > limitByItem) {
                questionResult = questionResult.subList(0, limitByItem);
            }
            log.debug("questionResult " + questionResult.size());
            hashset.put("questions", questionResult);
        }

        if (resultsAllowed.indexOf(TypeSearchResult.PROFILE) != -1) {
             List<GlobalSearchItem> profiles = UtilConvertToSearchItems
                    .convertProfileToSearchItem(getAccountDao().getPublicProfiles(keyword, limit, start));
            if (limitByItem != 0 && profiles.size() > limitByItem) {
                profiles = profiles.subList(0, limitByItem);
            }
            log.debug("profiles " + profiles.size());
            hashset.put("profiles", profiles);
        }

        if (resultsAllowed.indexOf(TypeSearchResult.HASHTAG) != -1) {
            List<GlobalSearchItem> tags = UtilConvertToSearchItems
            .convertHashTagToSearchItem(getHashTagDao().getListHashTagsByKeyword(keyword, limit, null));
            if (limitByItem != 0 && tags.size() > limitByItem) {
                tags = tags.subList(0, limitByItem);
            }
            log.debug("tags " + tags.size());
            hashset.put("tags", tags);
        }

        if (resultsAllowed.indexOf(TypeSearchResult.ATTACHMENT) != -1) {
            List<GlobalSearchItem> attachments = UtilConvertToSearchItems
                                        .convertAttachmentSearchToSearchItem(getAttachmentItem(keyword, 10, "content"));
            if (limitByItem != 0 && attachments.size() > limitByItem) {
                attachments = attachments.subList(0, limitByItem);
            }
            log.debug("attachments " + attachments.size());
            hashset.put("attachments", attachments);
        }
        
		if (resultsAllowed.indexOf(TypeSearchResult.COMMENT) != -1) {
			// TODO: add comment search implementation+
			List<GlobalSearchItem> comments = UtilConvertToSearchItems
					.convertCommentToSearchItem(getCommentsOperations()
							.getCommentsByKeyword(keyword, 10, null));
			if (limitByItem != 0 && comments.size() > limitByItem) {
				comments = comments.subList(0, limitByItem);
			}
			log.debug("Comments " + comments.size());
			hashset.put("comments", comments);
		}
       // List<GlobalSearchItem> totalItems = new ArrayList<GlobalSearchItem>(hashset);

        //TODO: order by rated or something.

        //filter my limit
        /*if (limit != null && start != null) {
            log.debug("split to "+limit  + " starting on "+start + " to list with size "+totalItems.size());
            totalItems = totalItems.size() > limit ? totalItems
                    .subList(start, limit) : totalItems;
        }
        //auto enumerate results.
        int x = 1;
        for (int i = 0; i < totalItems.size(); i++) {
            totalItems.get(i).setId(Long.valueOf(x));
            x++;
        }*/
        return hashset;
    }

    /**
     * 
     */
    public List<GlobalSearchItem> globalKeywordSearch(String keyword,
            String language, final Integer start, final Integer limit) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.SearchServiceOperations#globalKeywordSearch(java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    public List<GlobalSearchItem> globalKeywordSearch(String keyword,
            final Integer start, final Integer limit) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 
     */
    public String indexAttachment(final File file, final Long attachmentId){
     long start = System.currentTimeMillis();
               try {
                AttachmentIndex attachmentBean = IndexerFile.createAttachmentDocument(file, attachmentId);
                IndexerFile.addToIndex(attachmentBean, this.indexWriter);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                log.error(e);
            }
            long end = System.currentTimeMillis();
            log.debug("Indexing " + "numIndexed "+ " files took " + (end - start)
                    + " milliseconds");

        return "Attachment indexed";
    }

    /**
     *
     * @param unitAttachment
     * @throws EnMeExpcetion
     */
    public final void addAttachment(final UnitAttachment unitAttachment) throws EnMeExpcetion{
        try {
            Attachment attachment = new Attachment();
            attachment.setAttachmentId(unitAttachment.getAttachmentId());
            attachment.setFilename(unitAttachment.getFilename());
            attachment.setUploadDate(unitAttachment.getUploadDate());
            this.getProjectDaoImp().saveOrUpdate(attachment);
        } catch (Exception e) {
            throw new EnMeExpcetion(e);
        }
    }

    /**
     * 
     * @param typeSearch
     * @param keyword
     * @param max
     * @param start
     * @return
     * @throws EnMeNoResultsFoundException
     * @throws EnMeExpcetion
     */
    public List<SearchBean> filterPollByItemsByType(
            final TypeSearch typeSearch,
            String keyword, Integer max, Integer start)
            throws EnMeNoResultsFoundException, EnMeExpcetion {
        log.trace("filterPollByItemsByType");
        log.trace("--> "+typeSearch);
        log.trace("--> "+keyword);
        log.trace("--> "+max);
        log.trace("--> "+start);
        final List<PollBean> list = new ArrayList<PollBean>();
        if (TypeSearch.KEYWORD.equals(typeSearch)) {
          //  list.addAll(this.searchPollByKeyword(keyword, max, start));
        } else if (TypeSearch.BYOWNER.equals(typeSearch)) {
            list.addAll(ConvertDomainBean.convertListToPollBean(getPollDao()
                    .findAllPollByEditorOwner(
                            getUserAccount(getUserPrincipalUsername()), max,
                            start)));
        } else if (TypeSearch.LASTDAY.equals(typeSearch)) {
            list.addAll(ConvertDomainBean.convertListToPollBean(this
                    .getPollDao().retrievePollToday(
                            getUserAccount(getUserPrincipalUsername())
                                    .getAccount(), max, start,
                            DateUtil.getNextDayMidnightDate())));
        } else if (TypeSearch.LASTWEEK.equals(typeSearch)) {
            list.addAll(ConvertDomainBean.convertListToPollBean(this
                    .getPollDao().retrievePollLastWeek(
                            getUserAccount(getUserPrincipalUsername())
                                    .getAccount(), max, start,
                            DateUtil.getNextDayMidnightDate())));
        } else if (TypeSearch.FAVOURITES.equals(typeSearch)) {
            list.addAll(ConvertDomainBean.convertListToPollBean(getPollDao()
                    .retrieveFavouritesPoll(
                            getUserAccount(getUserPrincipalUsername()), max,
                            start)));
        } else if (TypeSearch.ALL.equals(typeSearch)) {
            list.addAll(ConvertDomainBean.convertListToPollBean(getPollDao().retrievePollsByUserId(getUserAccountonSecurityContext(), max, start)));
        } else {
            throw new EnMeExpcetion("operation not valid");
        }
        log.debug("Poll Search Items : " + list.size());
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.SearchServiceOperations#getQuestionInfo(java.lang.Long)
     */
	@Override
	public QuestionBean getQuestionInfo(Long questionId) throws EnMeNoResultsFoundException {
		final Question question = getQuestionDao().retrieveQuestionById(questionId);
		if (question == null) {
			throw new EnMeNoResultsFoundException("question not found");
		}
		return ConvertDomainBean.convertQuestionsToBean(question);
	}

}
