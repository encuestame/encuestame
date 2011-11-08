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
package org.encuestame.utils;

import junit.framework.TestCase;

import org.encuestame.utils.enums.CommentOptions;
import org.encuestame.utils.enums.CommentsSocialOptions;
import org.encuestame.utils.enums.EnMePermission;
import org.encuestame.utils.enums.GadgetType;
import org.encuestame.utils.enums.LayoutEnum;
import org.encuestame.utils.enums.Profile;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.TypeSearch;
import org.encuestame.utils.enums.TypeSearchResult;
import org.junit.Test;

/**
 * @author dmorales
 *
 */
public class TestUtilsEnums extends TestCase{

    /**
     * Test Comments option enum.
     */
    @Test
    public void testCommentsOptionEnum(){
        final CommentOptions optApprove = CommentOptions.getCommentOption("APPROVE");
        assertEquals("Should be equals", "APPROVE", optApprove.toString());
        final CommentOptions optRestrict = CommentOptions.getCommentOption("RESTRICT");
        assertEquals("Should be equals", "RESTRICT", optRestrict.toString());
        final CommentOptions optModerate = CommentOptions.getCommentOption("MODERATE");
        assertEquals("Should be equals", "MODERATE", optModerate.toString());
    }

    /**
     * Test Comments social options enum.
     */
    @Test
    public void testCommentsSocialOptions(){
        final CommentsSocialOptions likeVoteOpt = CommentsSocialOptions.getCommentsSocialOptions("LIKE_VOTE");
        assertEquals("Should be equals", "LIKE_VOTE", likeVoteOpt.toString());
        final CommentsSocialOptions disLikeVoteOpt = CommentsSocialOptions.getCommentsSocialOptions("DISLIKE_VOTE");
        assertEquals("Should be equals", "DISLIKE_VOTE", disLikeVoteOpt.toString());
    }

    /**
     * Test Permission enum.
     */
    @Test
    public void testEnMePermission(){
         final EnMePermission userPermission = EnMePermission.getPermissionString("ENCUESTAME_USER");
         assertEquals("Should be equals", "ENCUESTAME_USER", userPermission.toString());

         final EnMePermission adminPermission = EnMePermission.getPermissionString("ENCUESTAME_ADMIN");
         assertEquals("Should be equals", "ENCUESTAME_ADMIN", adminPermission.toString());

         final EnMePermission ownerPermission = EnMePermission.getPermissionString("ENCUESTAME_OWNER");
         assertEquals("Should be equals", "ENCUESTAME_OWNER", ownerPermission.toString());

         final EnMePermission publisherPermission = EnMePermission.getPermissionString("ENCUESTAME_PUBLISHER");
         assertEquals("Should be equals", "ENCUESTAME_PUBLISHER", publisherPermission.toString());

         final EnMePermission editorPermission = EnMePermission.getPermissionString("ENCUESTAME_EDITOR");
         assertEquals("Should be equals", "ENCUESTAME_EDITOR", editorPermission.toString());

         final EnMePermission anonymousPermission = EnMePermission.getPermissionString("ENCUESTAME_ANONYMOUS");
         assertEquals("Should be equals", "ENCUESTAME_ANONYMOUS", anonymousPermission.toString());

         final EnMePermission apiPermission = EnMePermission.getPermissionString("ENCUESTAME_API");
         assertEquals("Should be equals", "ENCUESTAME_API", apiPermission.toString());

         final EnMePermission readPermission = EnMePermission.getPermissionString("ENCUESTAME_READ");
         assertEquals("Should be equals", "ENCUESTAME_READ", readPermission.toString());

         final EnMePermission writePermission = EnMePermission.getPermissionString("ENCUESTAME_WRITE");
         assertEquals("Should be equals", "ENCUESTAME_WRITE", writePermission.toString());
    }

    /**
     * Test gadget type options enum.
     */
    @Test
    public void testGadgetTypeOptions(){
        final GadgetType activityStreamOpt = GadgetType.getGadgetType("STREAM");
        assertEquals("Should be equals", "stream", activityStreamOpt.toString());

        final GadgetType commentsOpt = GadgetType.getGadgetType("COMMENTS");
        assertEquals("Should be equals", "comments", commentsOpt.toString());

        final GadgetType tweetPollVotesOpt = GadgetType.getGadgetType("TWEETPOLLSVOTES");
        assertEquals("Should be equals", "tweetpollsvotes", tweetPollVotesOpt.toString());
    }

    /**
     * Test layout option enum.
     */
    @Test
    public void testLayoutEnumOptions(){
         final LayoutEnum aaaColumns = LayoutEnum.getDashboardLayout("AAA");
         assertEquals("Should be equals", "AAA", aaaColumns.toString());

         final LayoutEnum bbColumns = LayoutEnum.getDashboardLayout("BB");
         assertEquals("Should be equals", "BB", bbColumns.toString());

         final LayoutEnum bColumns = LayoutEnum.getDashboardLayout("B");
         assertEquals("Should be equals", "B", bColumns.toString());

         final LayoutEnum abColumns = LayoutEnum.getDashboardLayout("AB");
         assertEquals("Should be equals", "AB", abColumns.toString());

         final LayoutEnum baColumns = LayoutEnum.getDashboardLayout("BA");
         assertEquals("Should be equals", "BA", baColumns.toString());
    }

    /**
     * Test type search result options enum.
     */
    @Test
    public void testTypeSearchResultOptions(){
         final TypeSearchResult tweetPollOption = TypeSearchResult.getTypeSearchResult("TWEETPOLL");
         assertEquals("Should be equals", "TWEETPOLL", tweetPollOption.toString());

         final TypeSearchResult profilePollOption = TypeSearchResult.getTypeSearchResult("PROFILE");
         assertEquals("Should be equals", "PROFILE", profilePollOption.toString());

         final TypeSearchResult pollOption = TypeSearchResult.getTypeSearchResult("POLL");
         assertEquals("Should be equals", "POLL", pollOption.toString());

         final TypeSearchResult surveyOption = TypeSearchResult.getTypeSearchResult("SURVEY");
         assertEquals("Should be equals", "SURVEY", surveyOption.toString());

         final TypeSearchResult attachmentOption = TypeSearchResult.getTypeSearchResult("ATTACHMENT");
         assertEquals("Should be equals", "ATTACHMENT", attachmentOption.toString());

         final TypeSearchResult questionOption = TypeSearchResult.getTypeSearchResult("QUESTION");
         assertEquals("Should be equals", "QUESTION", questionOption.toString());

         final TypeSearchResult hashTagOption = TypeSearchResult.getTypeSearchResult("HASHTAG");
         assertEquals("Should be equals", "HASHTAG", hashTagOption.toString());

    }

    /**
     * Test type search options enum.
     */
    @Test
    public void testTypeSearchOptions(){
        final TypeSearch keywordOpt = TypeSearch.getSearchString("KEYWORD");
        assertEquals("Should be equals", "KEYWORD", keywordOpt.toString());

        final TypeSearch lastDayOpt = TypeSearch.getSearchString("LASTDAY");
        assertEquals("Should be equals", "LASTDAY", lastDayOpt.toString());

        final TypeSearch lastWeekOpt = TypeSearch.getSearchString("LASTWEEK");
        assertEquals("Should be equals", "LASTWEEK", lastWeekOpt.toString());

        final TypeSearch thisMonthOpt = TypeSearch.getSearchString("THISMONTH");
        assertEquals("Should be equals", "THISMONTH", thisMonthOpt.toString());

        final TypeSearch last30DaysOpt = TypeSearch.getSearchString("LAST30DAYS");
        assertEquals("Should be equals", "LAST30DAYS", last30DaysOpt.toString());

        final TypeSearch last365DaysOpt = TypeSearch.getSearchString("LAST365DAYS");
        assertEquals("Should be equals", "LAST365DAYS", last365DaysOpt.toString());

        final TypeSearch favoritesOpt = TypeSearch.getSearchString("FAVOURITES");
        assertEquals("Should be equals", "FAVOURITES", favoritesOpt.toString());

        final TypeSearch scheduledOpt = TypeSearch.getSearchString("SCHEDULED");
        assertEquals("Should be equals", "SCHEDULED", scheduledOpt.toString());

        final TypeSearch allOpt = TypeSearch.getSearchString("ALL");
        assertEquals("Should be equals", "ALL", allOpt.toString());

        final TypeSearch byOwnerOpt = TypeSearch.getSearchString("BYOWNER");
        assertEquals("Should be equals", "BYOWNER", byOwnerOpt.toString());

        final TypeSearch folderOpt = TypeSearch.getSearchString("FOLDER");
        assertEquals("Should be equals", "FOLDER", folderOpt.toString());
    }

    /**
     * Test search periods enum.
     */
    @Test
    public void testSearchPeriods(){
        final SearchPeriods twentyFourHoursOpt = SearchPeriods.getPeriodString("24");
        assertEquals("Should be equals", "24", twentyFourHoursOpt.toString());

        final SearchPeriods sevenDaysOpt = SearchPeriods.getPeriodString("7");
        assertEquals("Should be equals", "7", sevenDaysOpt.toString());

        final SearchPeriods thirtyDaysOpt = SearchPeriods.getPeriodString("30");
        assertEquals("Should be equals", "30", thirtyDaysOpt.toString());

        final SearchPeriods allTimeOpt = SearchPeriods.getPeriodString("all");
        assertEquals("Should be equals", "all", allTimeOpt.toString());
    }

    /**
     * Test profile enum.
     */
    @Test
    public void testProfile(){
        final Profile emailOpt = Profile.findProfile("EMAIL");
        assertEquals("Should be equals", "email", emailOpt.toString());

        final Profile usernameOpt = Profile.findProfile("USERNAME");
        assertEquals("Should be equals", "username", usernameOpt.toString());

        final Profile languageOpt = Profile.findProfile("LANGUAGE");
        assertEquals("Should be equals", "language", languageOpt.toString());

        final Profile pictureOpt = Profile.findProfile("PICTURE");
        assertEquals("Should be equals", "picture", pictureOpt.toString());

        final Profile privateOpt = Profile.findProfile("PRIVATE");
        assertEquals("Should be equals", "private", privateOpt.toString());

        final Profile realNameOpt = Profile.findProfile("REAL_NAME");
        assertEquals("Should be equals", "completeName", realNameOpt.toString());
    }
}
