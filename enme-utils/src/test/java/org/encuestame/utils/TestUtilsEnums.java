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

 import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.*;
import org.encuestame.utils.social.SocialProvider;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author dmorales
 *
 */
@Category(DefaultTest.class)
public class TestUtilsEnums extends TestCase{

    /**
     * Test Comments option enum.
     */
    @Test
    public void testCommentsOptionEnum(){
        final CommentOptions optApprove = EnumerationUtils.getEnumFromString(CommentOptions.class, "APPROVE");
        assertEquals("Should be equals", CommentOptions.APPROVE, optApprove);

        final CommentOptions optRestrict = EnumerationUtils.getEnumFromString(CommentOptions.class, "RESTRICT");
        assertEquals("Should be equals", CommentOptions.RESTRICT, optRestrict);

        final CommentOptions optModerate = EnumerationUtils.getEnumFromString(CommentOptions.class, "MODERATE");
        assertEquals("Should be equals", CommentOptions.MODERATE, optModerate);

        final CommentOptions optPublished = EnumerationUtils.getEnumFromString(CommentOptions.class, "PUBLISHED");
        assertEquals("Should be equals", CommentOptions.PUBLISHED, optPublished);

        final CommentOptions optSpam = EnumerationUtils.getEnumFromString(CommentOptions.class, "SPAM");
        assertEquals("Should be equals", CommentOptions.SPAM, optSpam);

        final CommentOptions optAll = EnumerationUtils.getEnumFromString(CommentOptions.class, "ALL");
        assertEquals("Should be equals", CommentOptions.ALL, optAll);
    }

    /**
     * Test Comments social options enum.
     */
    @Test
    public void testCommentsSocialOptions(){
        final CommentsSocialOptions likeVoteOpt = EnumerationUtils.getEnumFromString(CommentsSocialOptions.class, "LIKE_VOTE");
        assertEquals("Should be equals", CommentsSocialOptions.LIKE_VOTE, likeVoteOpt);
        final CommentsSocialOptions disLikeVoteOpt = EnumerationUtils.getEnumFromString(CommentsSocialOptions.class, "DISLIKE_VOTE");
        assertEquals("Should be equals", CommentsSocialOptions.DISLIKE_VOTE, disLikeVoteOpt);
    }

    /**
     * Test Permission enum.
     */
    @Test
    public void testEnMePermission(){
         final EnMePermission userPermission = EnumerationUtils.getEnumFromString(EnMePermission.class, "ENCUESTAME_USER");

         assertEquals("Should be equals", EnMePermission.ENCUESTAME_USER, userPermission);

         final EnMePermission adminPermission = EnumerationUtils.getEnumFromString(EnMePermission.class, "ENCUESTAME_ADMIN");
         assertEquals("Should be equals", EnMePermission.ENCUESTAME_ADMIN, adminPermission);

         final EnMePermission ownerPermission = EnumerationUtils.getEnumFromString(EnMePermission.class, "ENCUESTAME_OWNER");
         assertEquals("Should be equals", EnMePermission.ENCUESTAME_OWNER, ownerPermission);

         final EnMePermission publisherPermission = EnumerationUtils.getEnumFromString(EnMePermission.class, "ENCUESTAME_PUBLISHER");
         assertEquals("Should be equals", EnMePermission.ENCUESTAME_PUBLISHER, publisherPermission);

         final EnMePermission editorPermission = EnumerationUtils.getEnumFromString(EnMePermission.class, "ENCUESTAME_EDITOR");
         assertEquals("Should be equals", EnMePermission.ENCUESTAME_EDITOR, editorPermission);

         final EnMePermission anonymousPermission = EnumerationUtils.getEnumFromString(EnMePermission.class, "ENCUESTAME_ANONYMOUS");
         assertEquals("Should be equals", EnMePermission.ENCUESTAME_ANONYMOUS, anonymousPermission);

         final EnMePermission apiPermission =EnumerationUtils.getEnumFromString(EnMePermission.class, "ENCUESTAME_API");
         assertEquals("Should be equals", EnMePermission.ENCUESTAME_API, apiPermission);

         final EnMePermission readPermission = EnumerationUtils.getEnumFromString(EnMePermission.class, "ENCUESTAME_READ");
         assertEquals("Should be equals", EnMePermission.ENCUESTAME_READ, readPermission);

         final EnMePermission writePermission = EnumerationUtils.getEnumFromString(EnMePermission.class, "ENCUESTAME_WRITE");
         assertEquals("Should be equals", EnMePermission.ENCUESTAME_WRITE, writePermission);
    }

    /**
     * Test gadget type options enum.
     */
    @Test
    public void testGadgetTypeOptions(){
        final GadgetType activityStreamOpt = EnumerationUtils.getEnumFromString(GadgetType.class, "ACTIVITY_STREAM");
        assertEquals("Should be equals", GadgetType.ACTIVITY_STREAM, activityStreamOpt);

        final GadgetType commentsOpt = EnumerationUtils.getEnumFromString(GadgetType.class, "COMMENTS");
        assertEquals("Should be equals", GadgetType.COMMENTS, commentsOpt);

        final GadgetType tweetPollVotesOpt = EnumerationUtils.getEnumFromString(GadgetType.class, "TWEETPOLLS_VOTES");
        assertEquals("Should be equals", GadgetType.TWEETPOLLS_VOTES, tweetPollVotesOpt);
    }

    /**
     * Test type search result options enum.
     */
    @Test
    public void testTypeSearchResultOptions(){
         final TypeSearchResult tweetPollOption =  EnumerationUtils.getEnumFromString(TypeSearchResult.class, "TWEETPOLL");
         assertEquals("Should be equals", TypeSearchResult.TWEETPOLL, tweetPollOption);

         final TypeSearchResult profilePollOption = EnumerationUtils.getEnumFromString(TypeSearchResult.class, "PROFILE");
         assertEquals("Should be equals", TypeSearchResult.PROFILE, profilePollOption);

         final TypeSearchResult pollOption = EnumerationUtils.getEnumFromString(TypeSearchResult.class, "POLL");
         assertEquals("Should be equals", TypeSearchResult.POLL, pollOption);

         final TypeSearchResult surveyOption = EnumerationUtils.getEnumFromString(TypeSearchResult.class, "SURVEY");
         assertEquals("Should be equals", TypeSearchResult.SURVEY, surveyOption);

         final TypeSearchResult questionOption = EnumerationUtils.getEnumFromString(TypeSearchResult.class, "QUESTION");
         assertEquals("Should be equals", TypeSearchResult.QUESTION, questionOption);

         final TypeSearchResult hashTagOption = EnumerationUtils.getEnumFromString(TypeSearchResult.class, "HASHTAG");
         assertEquals("Should be equals", TypeSearchResult.HASHTAG, hashTagOption);

        final TypeSearchResult hashTagRatedOption = EnumerationUtils.getEnumFromString(TypeSearchResult.class, "HASHTAGRATED");
        assertEquals("Should be equals", TypeSearchResult.HASHTAGRATED, hashTagRatedOption);

        final TypeSearchResult socialNetworkOption = EnumerationUtils.getEnumFromString(TypeSearchResult.class, "SOCIALNETWORK");
        assertEquals("Should be equals", TypeSearchResult.SOCIALNETWORK, socialNetworkOption);

        final TypeSearchResult hitsOption = EnumerationUtils.getEnumFromString(TypeSearchResult.class, "HITS");
        assertEquals("Should be equals", TypeSearchResult.HITS, hitsOption);

        final TypeSearchResult votesOption = EnumerationUtils.getEnumFromString(TypeSearchResult.class, "VOTES");
        assertEquals("Should be equals", TypeSearchResult.VOTES, votesOption);

        final TypeSearchResult allOption = EnumerationUtils.getEnumFromString(TypeSearchResult.class, "ALL");
        assertEquals("Should be equals", TypeSearchResult.ALL, allOption);
    }

    /**
     * Test type search options enum.
     */
    @Test
    public void testTypeSearchOptions(){
        final TypeSearch keywordOpt = EnumerationUtils.getEnumFromString(TypeSearch.class, "KEYWORD");
        assertEquals("Should be equals", TypeSearch.KEYWORD, keywordOpt);

        final TypeSearch lastDayOpt = EnumerationUtils.getEnumFromString(TypeSearch.class, "LASTDAY");
        assertEquals("Should be equals", TypeSearch.LASTDAY, lastDayOpt);

        final TypeSearch lastWeekOpt = EnumerationUtils.getEnumFromString(TypeSearch.class, "LASTWEEK");
        assertEquals("Should be equals", TypeSearch.LASTWEEK, lastWeekOpt);

        final TypeSearch thisMonthOpt = EnumerationUtils.getEnumFromString(TypeSearch.class, "THISMONTH");
        assertEquals("Should be equals", TypeSearch.THISMONTH, thisMonthOpt);

        final TypeSearch last30DaysOpt = EnumerationUtils.getEnumFromString(TypeSearch.class, "LAST30DAYS");
        assertEquals("Should be equals", TypeSearch.LAST30DAYS, last30DaysOpt);

        final TypeSearch last365DaysOpt = EnumerationUtils.getEnumFromString(TypeSearch.class, "LAST365DAYS");
        assertEquals("Should be equals", TypeSearch.LAST365DAYS, last365DaysOpt);

        final TypeSearch favoritesOpt = EnumerationUtils.getEnumFromString(TypeSearch.class, "FAVOURITES");
        assertEquals("Should be equals", TypeSearch.FAVOURITES, favoritesOpt);

        final TypeSearch scheduledOpt = EnumerationUtils.getEnumFromString(TypeSearch.class, "SCHEDULED");
        assertEquals("Should be equals", TypeSearch.SCHEDULED, scheduledOpt);

        final TypeSearch allOpt = EnumerationUtils.getEnumFromString(TypeSearch.class, "ALL");
        assertEquals("Should be equals", TypeSearch.ALL, allOpt);

        final TypeSearch byOwnerOpt = EnumerationUtils.getEnumFromString(TypeSearch.class, "BYOWNER");
        assertEquals("Should be equals", TypeSearch.BYOWNER, byOwnerOpt);

        final TypeSearch folderOpt = EnumerationUtils.getEnumFromString(TypeSearch.class, "FOLDER");
        assertEquals("Should be equals", TypeSearch.FOLDER, folderOpt);

        final TypeSearch dateOpt = EnumerationUtils.getEnumFromString(TypeSearch.class, "DATE");
        assertEquals("Should be equals", TypeSearch.DATE, dateOpt);

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

        final SearchPeriods oneYearTimeOpt = SearchPeriods.getPeriodString("365");
        assertEquals("Should be equals", "365", oneYearTimeOpt.toString());

        final SearchPeriods yearsTimeOpt = SearchPeriods.getPeriodString("1095");
        assertEquals("Should be equals", "all", yearsTimeOpt.toString());

        final SearchPeriods todayOpt = SearchPeriods.TWENTYFOURHOURS;
        assertEquals("Should be equals", "24", todayOpt.toNumber().toString());

        final SearchPeriods weekOpt = SearchPeriods.SEVENDAYS;
        assertEquals("Should be equals", "7", weekOpt.toNumber().toString());

        final SearchPeriods monthOpt = SearchPeriods.THIRTYDAYS;
        assertEquals("Should be equals", "30", monthOpt.toNumber().toString());

        final SearchPeriods allOpt = SearchPeriods.ALLTIME;
        assertEquals("Should be equals", "1095", allOpt.toNumber().toString());

        final SearchPeriods oneYearOpt = SearchPeriods.ONEYEAR;
        assertEquals("Should be equals", "365", oneYearOpt.toNumber().toString());
    }

    /**
     * Test profile enum.
     */
    @Test
    public void testProfile(){
        final Profile emailOpt = EnumerationUtils.getEnumFromString(Profile.class, "EMAIL");
        assertEquals("Should be equals", Profile.EMAIL, emailOpt);

        final Profile usernameOpt = EnumerationUtils.getEnumFromString(Profile.class, "USERNAME");
        assertEquals("Should be equals", Profile.USERNAME, usernameOpt);

        final Profile languageOpt = EnumerationUtils.getEnumFromString(Profile.class, "LANGUAGE");
        assertEquals("Should be equals", Profile.LANGUAGE, languageOpt);

        final Profile pictureOpt = EnumerationUtils.getEnumFromString(Profile.class, "PICTURE");
        assertEquals("Should be equals", Profile.PICTURE, pictureOpt);

        final Profile privateOpt = EnumerationUtils.getEnumFromString(Profile.class, "PRIVATE");
        assertEquals("Should be equals", Profile.PRIVATE, privateOpt);

        final Profile realNameOpt = EnumerationUtils.getEnumFromString(Profile.class, "REAL_NAME");
        assertEquals("Should be equals", Profile.REAL_NAME, realNameOpt);

        final Profile welcomeeOpt = EnumerationUtils.getEnumFromString(Profile.class, "WELCOME");
        assertEquals("Should be equals", Profile.WELCOME, welcomeeOpt);

        final Profile infoOpt = EnumerationUtils.getEnumFromString(Profile.class, "PAGE_INFO");
        assertEquals("Should be equals", Profile.PAGE_INFO, infoOpt);
    }

    /**
     * Test social provider.
     */
    @Test
    public void testSocialProvider(){
        final SocialProvider twitterProvider = EnumerationUtils.getEnumFromString(SocialProvider.class, "TWITTER");
        assertEquals("Should be equals", SocialProvider.TWITTER, twitterProvider);

        final SocialProvider facebookProvider = EnumerationUtils.getEnumFromString(SocialProvider.class, "FACEBOOK");
        assertEquals("Should be equals", SocialProvider.FACEBOOK, facebookProvider);

        final SocialProvider identicaProvider = EnumerationUtils.getEnumFromString(SocialProvider.class, "IDENTICA");
        assertEquals("Should be equals", SocialProvider.IDENTICA, identicaProvider);

        final SocialProvider linkedinProvider = EnumerationUtils.getEnumFromString(SocialProvider.class, "LINKEDIN");
        assertEquals("Should be equals", SocialProvider.LINKEDIN, linkedinProvider);

        final SocialProvider mySpaceProvider = EnumerationUtils.getEnumFromString(SocialProvider.class, "YAHOO");
        assertEquals("Should be equals", SocialProvider.YAHOO, mySpaceProvider);

        final SocialProvider googleBuzzProvider = EnumerationUtils.getEnumFromString(SocialProvider.class, "GOOGLE_BUZZ");
        assertEquals("Should be equals", SocialProvider.GOOGLE_BUZZ, googleBuzzProvider);

        final SocialProvider yahooProvider = EnumerationUtils.getEnumFromString(SocialProvider.class, "MYSPACE");
        assertEquals("Should be equals", SocialProvider.MYSPACE, yahooProvider  );
    }

    /**
     *  Test HitCategory Enum.
     */
    @Test
    public void testHitCategory(){
        final HitCategory catVote =EnumerationUtils.getEnumFromString(HitCategory.class, "VOTE");
        assertEquals("Hit Category Should be equals", HitCategory.VOTE, catVote);
        final HitCategory catVisit = EnumerationUtils.getEnumFromString(HitCategory.class, "VISIT");
        assertEquals("Hit Category Should be equals", HitCategory.VISIT, catVisit);
    }

    /**
     * Test RelativeTime Enum
     */
    @Test
    public void testRelativeTime(){
        final RelativeTimeEnum rightnow = EnumerationUtils.getEnumFromString(RelativeTimeEnum.class, "RIGTH_NOW");
        assertEquals("Relative time should be equals", RelativeTimeEnum.RIGTH_NOW , rightnow);

        final RelativeTimeEnum oneSecond = EnumerationUtils.getEnumFromString(RelativeTimeEnum.class, "ONE_SECOND_AGO");
        assertEquals("Relative time should be equals", RelativeTimeEnum.ONE_SECOND_AGO , oneSecond);

        final RelativeTimeEnum aSecondsAgo = EnumerationUtils.getEnumFromString(RelativeTimeEnum.class, "SECONDS_AGO");
        assertEquals("Relative time should be equals", RelativeTimeEnum.SECONDS_AGO , aSecondsAgo);

        final RelativeTimeEnum aMinuteAgo = EnumerationUtils.getEnumFromString(RelativeTimeEnum.class, "A_MINUTE_AGO");
        assertEquals("Relative time should be equals", RelativeTimeEnum.A_MINUTE_AGO , aMinuteAgo);

        final RelativeTimeEnum minutesAgo = EnumerationUtils.getEnumFromString(RelativeTimeEnum.class, "MINUTES_AGO");
        assertEquals("Relative time should be equals", RelativeTimeEnum.MINUTES_AGO , minutesAgo);

        final RelativeTimeEnum anHourAgo = EnumerationUtils.getEnumFromString(RelativeTimeEnum.class, "AN_HOUR_AGO");
        assertEquals("Relative time should be equals", RelativeTimeEnum.AN_HOUR_AGO , anHourAgo);

        final RelativeTimeEnum hoursAgo = EnumerationUtils.getEnumFromString(RelativeTimeEnum.class, "HOURS_AGO");
        assertEquals("Relative time should be equals", RelativeTimeEnum.HOURS_AGO , hoursAgo);

        final RelativeTimeEnum yesterday = EnumerationUtils.getEnumFromString(RelativeTimeEnum.class, "YESTERDAY");
        assertEquals("Relative time should be equals", RelativeTimeEnum.YESTERDAY , yesterday);

        final RelativeTimeEnum daysAgo = EnumerationUtils.getEnumFromString(RelativeTimeEnum.class, "DAYS_AGO");
        assertEquals("Relative time should be equals", RelativeTimeEnum.DAYS_AGO , daysAgo);

        final RelativeTimeEnum aMonthAgo = EnumerationUtils.getEnumFromString(RelativeTimeEnum.class, "ONE_MONTH_AGO");
        assertEquals("Relative time should be equals", RelativeTimeEnum.ONE_MONTH_AGO , aMonthAgo);

        final RelativeTimeEnum monthsAgo = EnumerationUtils.getEnumFromString(RelativeTimeEnum.class, "MONTHS_AGO");
        assertEquals("Relative time should be equals", RelativeTimeEnum.MONTHS_AGO , monthsAgo);

        final RelativeTimeEnum oneYearAgo = EnumerationUtils.getEnumFromString(RelativeTimeEnum.class, "ONE_YEAR_AGO");
        assertEquals("Relative time should be equals", RelativeTimeEnum.ONE_YEAR_AGO , oneYearAgo);

        final RelativeTimeEnum yearsAgo = EnumerationUtils.getEnumFromString(RelativeTimeEnum.class, "YEARS_AGO");
        assertEquals("Relative time should be equals", RelativeTimeEnum.YEARS_AGO , yearsAgo);

    }

    /**
     * Test TypeDatabase Enum.
     */
    @Test
    public void testTypeDatabase(){
        final TypeDatabase oracleDBase = EnumerationUtils.getEnumFromString(TypeDatabase.class, "oracle");
        assertEquals("Database type should be equals", TypeDatabase.ORACLE, oracleDBase);

        final TypeDatabase hsqlDBase = EnumerationUtils.getEnumFromString(TypeDatabase.class, "hsqldb");
        assertEquals("Database type should be equals", TypeDatabase.HSQLDB , hsqlDBase);

        final TypeDatabase mysqlDBase = EnumerationUtils.getEnumFromString(TypeDatabase.class, "mysql");
        assertEquals("Database type should be equals", TypeDatabase.MYSQL, mysqlDBase);

        final TypeDatabase postgresDBase = EnumerationUtils.getEnumFromString(TypeDatabase.class, "postgres");
        assertEquals("Database type should be equals", TypeDatabase.POSTGRES , postgresDBase);

        final TypeDatabase mssqlDBase = EnumerationUtils.getEnumFromString(TypeDatabase.class, "mssql");
        assertEquals("Database type should be equals", TypeDatabase.MSSQL, mssqlDBase);

        final TypeDatabase derbyDBase = EnumerationUtils.getEnumFromString(TypeDatabase.class, "derby");
        assertEquals("Database type should be equals", TypeDatabase.DERBY, derbyDBase);

        final TypeDatabase db2DBase = EnumerationUtils.getEnumFromString(TypeDatabase.class, "db2");
        assertEquals("Database type should be equals", TypeDatabase.DB2 , db2DBase);
    }

    /**
     * Test DateClasificated Enum.
     */
    @Test
    public void testDateClasificated(){

        final DateClasificatedEnum dcToday = EnumerationUtils.getEnumFromString(DateClasificatedEnum.class, "TODAY");
        assertEquals("DateClasificatedEnum should be equals", DateClasificatedEnum.TODAY , dcToday);

        final DateClasificatedEnum dcWeek = EnumerationUtils.getEnumFromString(DateClasificatedEnum.class, "THIS_WEEK");
        assertEquals("DateClasificatedEnum should be equals", DateClasificatedEnum.THIS_WEEK, dcWeek);

        final DateClasificatedEnum dcMonth = EnumerationUtils.getEnumFromString(DateClasificatedEnum.class, "THIS_MONTH");
        assertEquals("DateClasificatedEnum should be equals", DateClasificatedEnum.THIS_MONTH , dcMonth);

        final DateClasificatedEnum dcLastMonth = EnumerationUtils.getEnumFromString(DateClasificatedEnum.class, "LAST_MONTH");
        assertEquals("DateClasificatedEnum  should be equals", DateClasificatedEnum.LAST_MONTH, dcLastMonth);

        final DateClasificatedEnum dcMonthsAgo = EnumerationUtils.getEnumFromString(DateClasificatedEnum.class, "FEW_MONTHS_AGO");
        assertEquals("DateClasificatedEnum should be equals", DateClasificatedEnum.FEW_MONTHS_AGO , dcMonthsAgo);

        final DateClasificatedEnum dcLastYear = EnumerationUtils.getEnumFromString(DateClasificatedEnum.class, "LAST_YEAR");
        assertEquals(" DateClasificatedEnum should be equals", DateClasificatedEnum.LAST_YEAR, dcLastYear);

        final DateClasificatedEnum dcLongTimeAgo = EnumerationUtils.getEnumFromString(DateClasificatedEnum.class, "LONG_TIME_AGO");
        assertEquals("DateClasificatedEnum should be equals", DateClasificatedEnum.LONG_TIME_AGO , dcLongTimeAgo);
    }

    /**
     * Test RequestSourceType
     */
    @Test
    public void testRequestSourceType(){
        final RequestSourceType embedded = RequestSourceType.getSource("embedded");
        assertEquals("Request type should be equals", RequestSourceType.EMBEDDED  , embedded);

        final RequestSourceType normal = RequestSourceType.getSource("normal");
        assertEquals("Request type should be equals", RequestSourceType.NORMAL, normal);
    }

    /**
     *
     */
    @Test
    public void testHashtagRate(){
        final HashTagRate label1 = HashTagRate.getHashTagMonthLabel("1");
        assertEquals("Month Label should be equals", HashTagRate.JANUARY  , label1);

        final HashTagRate label2 = HashTagRate.getHashTagMonthLabel("2");
        assertEquals("Month Label should be equals", HashTagRate.FEBRUARY , label2);

        final HashTagRate label3 = HashTagRate.getHashTagMonthLabel("3");
        assertEquals("Month Label should be equals", HashTagRate.MARCH, label3);

        final HashTagRate label4 = HashTagRate.getHashTagMonthLabel("4");
        assertEquals("Month Label should be equals", HashTagRate.APRIL , label4);

        final HashTagRate label5 = HashTagRate.getHashTagMonthLabel("5");
        assertEquals("Month Label should be equals", HashTagRate.MAY , label5);

        final HashTagRate label6 = HashTagRate.getHashTagMonthLabel("6");
        assertEquals("Month Label should be equals", HashTagRate.JUNE , label6);

        final HashTagRate label7 = HashTagRate.getHashTagMonthLabel("7");
        assertEquals("Month Label should be equals", HashTagRate.JULY , label7);

        final HashTagRate label8 = HashTagRate.getHashTagMonthLabel("8");
        assertEquals("Month Label should be equals", HashTagRate.AUGUST , label8);

        final HashTagRate label9 = HashTagRate.getHashTagMonthLabel("9");
        assertEquals("Month Label should be equals", HashTagRate.SEPTEMBER , label9);

        final HashTagRate label10 = HashTagRate.getHashTagMonthLabel("10");
        assertEquals("Month Label should be equals", HashTagRate.OCTOBER , label10);

        final HashTagRate label11 = HashTagRate.getHashTagMonthLabel("11");
        assertEquals("Month Label should be equals", HashTagRate.NOVEMBER , label11);

        final HashTagRate label12 = HashTagRate.getHashTagMonthLabel("12");
        assertEquals("Month Label should be equals", HashTagRate.DECEMBER , label12);


        final HashTagRate lblWeek1 = HashTagRate.getHashTagWeekDayLabel("1");
        assertEquals("Week Label should be equals", HashTagRate.MONDAY , lblWeek1);


        final HashTagRate lblWeek2 = HashTagRate.getHashTagWeekDayLabel("2");
        assertEquals("Week Label should be equals", HashTagRate.TUESDAY , lblWeek2);


        final HashTagRate lblWeek3 = HashTagRate.getHashTagWeekDayLabel("3");
        assertEquals("Week Label should be equals", HashTagRate.WEDNESDAY , lblWeek3);

        final HashTagRate lblWeek4 = HashTagRate.getHashTagWeekDayLabel("4");
        assertEquals("Week Label should be equals", HashTagRate.THURSDAY, lblWeek4);

        final HashTagRate lblWeek5 = HashTagRate.getHashTagWeekDayLabel("5");
        assertEquals("Week Label should be equals", HashTagRate.FRIDAY, lblWeek5);

        final HashTagRate lblWeek6 = HashTagRate.getHashTagWeekDayLabel("6");
        assertEquals("Week Label should be equals", HashTagRate.SATURDAY, lblWeek6);

        final HashTagRate lblWeek7 = HashTagRate.getHashTagWeekDayLabel("7");
        assertEquals("Week Label should be equals", HashTagRate.SUNDAY, lblWeek7);
    }

    /**
     * Test ShowResultsOptions Enum.
     */
    @Test
    public void testShowResultsOptions(){
        final ShowResultsOptions percentOpt = EnumerationUtils.getEnumFromString(ShowResultsOptions.class, "PERCENT");
        assertEquals("Should be equals", ShowResultsOptions.PERCENT , percentOpt);

        final ShowResultsOptions restrictedOpt = EnumerationUtils.getEnumFromString(ShowResultsOptions.class, "RESTRICTED");
        assertEquals("Should be equals", ShowResultsOptions.RESTRICTED, restrictedOpt);

        final ShowResultsOptions allOpt = EnumerationUtils.getEnumFromString(ShowResultsOptions.class, "ALL");
        assertEquals("Should be equals", ShowResultsOptions.ALL , allOpt);
    }
}
