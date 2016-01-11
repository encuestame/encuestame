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
        final CommentOptions optApprove = ValidationUtils.getEnumFromString(CommentOptions.class, "APPROVE");
        assertEquals("Should be equals", CommentOptions.APPROVE, optApprove);

        final CommentOptions optRestrict = ValidationUtils.getEnumFromString(CommentOptions.class, "RESTRICT");
        assertEquals("Should be equals", CommentOptions.RESTRICT, optRestrict);

        final CommentOptions optModerate = ValidationUtils.getEnumFromString(CommentOptions.class, "MODERATE");
        assertEquals("Should be equals", CommentOptions.MODERATE, optModerate);

        final CommentOptions optPublished = ValidationUtils.getEnumFromString(CommentOptions.class, "PUBLISHED");
        assertEquals("Should be equals", CommentOptions.PUBLISHED, optPublished);

        final CommentOptions optSpam = ValidationUtils.getEnumFromString(CommentOptions.class, "SPAM");
        assertEquals("Should be equals", CommentOptions.SPAM, optSpam);

        final CommentOptions optAll = ValidationUtils.getEnumFromString(CommentOptions.class, "ALL");
        assertEquals("Should be equals", CommentOptions.ALL, optAll);
    }

    /**
     * Test Comments social options enum.
     */
    @Test
    public void testCommentsSocialOptions(){
        final CommentsSocialOptions likeVoteOpt = ValidationUtils.getEnumFromString(CommentsSocialOptions.class, "LIKE_VOTE");
        assertEquals("Should be equals", CommentsSocialOptions.LIKE_VOTE, likeVoteOpt);
        final CommentsSocialOptions disLikeVoteOpt = ValidationUtils.getEnumFromString(CommentsSocialOptions.class, "DISLIKE_VOTE");
        assertEquals("Should be equals", CommentsSocialOptions.DISLIKE_VOTE, disLikeVoteOpt);
    }

    /**
     * Test Permission enum.
     */
    @Test
    public void testEnMePermission(){
         final EnMePermission userPermission = ValidationUtils.getEnumFromString(EnMePermission.class, "ENCUESTAME_USER");

         assertEquals("Should be equals", EnMePermission.ENCUESTAME_USER, userPermission);

         final EnMePermission adminPermission = ValidationUtils.getEnumFromString(EnMePermission.class, "ENCUESTAME_ADMIN");
         assertEquals("Should be equals", EnMePermission.ENCUESTAME_ADMIN, adminPermission);

         final EnMePermission ownerPermission = ValidationUtils.getEnumFromString(EnMePermission.class, "ENCUESTAME_OWNER");
         assertEquals("Should be equals", EnMePermission.ENCUESTAME_OWNER, ownerPermission);

         final EnMePermission publisherPermission = ValidationUtils.getEnumFromString(EnMePermission.class, "ENCUESTAME_PUBLISHER");
         assertEquals("Should be equals", EnMePermission.ENCUESTAME_PUBLISHER, publisherPermission);

         final EnMePermission editorPermission = ValidationUtils.getEnumFromString(EnMePermission.class, "ENCUESTAME_EDITOR");
         assertEquals("Should be equals", EnMePermission.ENCUESTAME_EDITOR, editorPermission);

         final EnMePermission anonymousPermission = ValidationUtils.getEnumFromString(EnMePermission.class, "ENCUESTAME_ANONYMOUS");
         assertEquals("Should be equals", EnMePermission.ENCUESTAME_ANONYMOUS, anonymousPermission);

         final EnMePermission apiPermission =ValidationUtils.getEnumFromString(EnMePermission.class, "ENCUESTAME_API");
         assertEquals("Should be equals", EnMePermission.ENCUESTAME_API, apiPermission);

         final EnMePermission readPermission = ValidationUtils.getEnumFromString(EnMePermission.class, "ENCUESTAME_READ");
         assertEquals("Should be equals", EnMePermission.ENCUESTAME_READ, readPermission);

         final EnMePermission writePermission = ValidationUtils.getEnumFromString(EnMePermission.class, "ENCUESTAME_WRITE");
         assertEquals("Should be equals", EnMePermission.ENCUESTAME_WRITE, writePermission);
    }

    /**
     * Test gadget type options enum.
     */
    @Test
    public void testGadgetTypeOptions(){
        final GadgetType activityStreamOpt = ValidationUtils.getEnumFromString(GadgetType.class, "ACTIVITY_STREAM");
        assertEquals("Should be equals", GadgetType.ACTIVITY_STREAM, activityStreamOpt);

        final GadgetType commentsOpt = ValidationUtils.getEnumFromString(GadgetType.class, "COMMENTS");
        assertEquals("Should be equals", GadgetType.COMMENTS, commentsOpt);

        final GadgetType tweetPollVotesOpt = ValidationUtils.getEnumFromString(GadgetType.class, "TWEETPOLLS_VOTES");
        assertEquals("Should be equals", GadgetType.TWEETPOLLS_VOTES, tweetPollVotesOpt);
    }

    /**
     * Test layout option enum.
     */
    @Test
    public void testLayoutEnumOptions(){
        final LayoutEnum bbColumns = ValidationUtils.getEnumFromString(LayoutEnum.class, "BB_BLOCK");
        assertEquals("Should be equals" , LayoutEnum.BB_BLOCK, bbColumns);

        final LayoutEnum bColumns = ValidationUtils.getEnumFromString(LayoutEnum.class, "B_BLOCK");
        assertEquals("Should be equals", LayoutEnum.B_BLOCK, bColumns);

        final LayoutEnum abColumns =  ValidationUtils.getEnumFromString(LayoutEnum.class, "AB_COLUMN_BLOCK");
        assertEquals("Should be equals", LayoutEnum.AB_COLUMN_BLOCK, abColumns);

        final LayoutEnum baColumns = ValidationUtils.getEnumFromString(LayoutEnum.class, "BA_BLOCK_COLUMN");
        assertEquals("Should be equals", LayoutEnum.BA_BLOCK_COLUMN, baColumns);
    }

    /**
     * Test type search result options enum.
     */
    @Test
    public void testTypeSearchResultOptions(){
         final TypeSearchResult tweetPollOption =  ValidationUtils.getEnumFromString(TypeSearchResult.class, "TWEETPOLL");
         assertEquals("Should be equals", TypeSearchResult.TWEETPOLL, tweetPollOption);

         final TypeSearchResult profilePollOption = ValidationUtils.getEnumFromString(TypeSearchResult.class, "PROFILE");
         assertEquals("Should be equals", TypeSearchResult.PROFILE, profilePollOption);

         final TypeSearchResult pollOption = ValidationUtils.getEnumFromString(TypeSearchResult.class, "POLL");
         assertEquals("Should be equals", TypeSearchResult.POLL, pollOption);

         final TypeSearchResult surveyOption = ValidationUtils.getEnumFromString(TypeSearchResult.class, "SURVEY");
         assertEquals("Should be equals", TypeSearchResult.SURVEY, surveyOption);

         final TypeSearchResult questionOption = ValidationUtils.getEnumFromString(TypeSearchResult.class, "QUESTION");
         assertEquals("Should be equals", TypeSearchResult.QUESTION, questionOption);

         final TypeSearchResult hashTagOption = ValidationUtils.getEnumFromString(TypeSearchResult.class, "HASHTAG");
         assertEquals("Should be equals", TypeSearchResult.HASHTAG, hashTagOption);

        final TypeSearchResult hashTagRatedOption = ValidationUtils.getEnumFromString(TypeSearchResult.class, "HASHTAGRATED");
        assertEquals("Should be equals", TypeSearchResult.HASHTAGRATED, hashTagRatedOption);

        final TypeSearchResult socialNetworkOption = ValidationUtils.getEnumFromString(TypeSearchResult.class, "SOCIALNETWORK");
        assertEquals("Should be equals", TypeSearchResult.SOCIALNETWORK, socialNetworkOption);

        final TypeSearchResult hitsOption = ValidationUtils.getEnumFromString(TypeSearchResult.class, "HITS");
        assertEquals("Should be equals", TypeSearchResult.HITS, hitsOption);

        final TypeSearchResult votesOption = ValidationUtils.getEnumFromString(TypeSearchResult.class, "VOTES");
        assertEquals("Should be equals", TypeSearchResult.VOTES, votesOption);

        final TypeSearchResult allOption = ValidationUtils.getEnumFromString(TypeSearchResult.class, "ALL");
        assertEquals("Should be equals", TypeSearchResult.ALL, allOption);
    }

    /**
     * Test type search options enum.
     */
    @Test
    public void testTypeSearchOptions(){
        final TypeSearch keywordOpt = ValidationUtils.getEnumFromString(TypeSearch.class, "KEYWORD");
        assertEquals("Should be equals", TypeSearch.KEYWORD, keywordOpt);

        final TypeSearch lastDayOpt = ValidationUtils.getEnumFromString(TypeSearch.class, "LASTDAY");
        assertEquals("Should be equals", TypeSearch.LASTDAY, lastDayOpt);

        final TypeSearch lastWeekOpt = ValidationUtils.getEnumFromString(TypeSearch.class, "LASTWEEK");
        assertEquals("Should be equals", TypeSearch.LASTWEEK, lastWeekOpt);

        final TypeSearch thisMonthOpt = ValidationUtils.getEnumFromString(TypeSearch.class, "THISMONTH");
        assertEquals("Should be equals", TypeSearch.THISMONTH, thisMonthOpt);

        final TypeSearch last30DaysOpt = ValidationUtils.getEnumFromString(TypeSearch.class, "LAST30DAYS");
        assertEquals("Should be equals", TypeSearch.LAST30DAYS, last30DaysOpt);

        final TypeSearch last365DaysOpt = ValidationUtils.getEnumFromString(TypeSearch.class, "LAST365DAYS");
        assertEquals("Should be equals", TypeSearch.LAST365DAYS, last365DaysOpt);

        final TypeSearch favoritesOpt = ValidationUtils.getEnumFromString(TypeSearch.class, "FAVOURITES");
        assertEquals("Should be equals", TypeSearch.FAVOURITES, favoritesOpt);

        final TypeSearch scheduledOpt = ValidationUtils.getEnumFromString(TypeSearch.class, "SCHEDULED");
        assertEquals("Should be equals", TypeSearch.SCHEDULED, scheduledOpt);

        final TypeSearch allOpt = ValidationUtils.getEnumFromString(TypeSearch.class, "ALL");
        assertEquals("Should be equals", TypeSearch.ALL, allOpt);

        final TypeSearch byOwnerOpt = ValidationUtils.getEnumFromString(TypeSearch.class, "BYOWNER");
        assertEquals("Should be equals", TypeSearch.BYOWNER, byOwnerOpt);

        final TypeSearch folderOpt = ValidationUtils.getEnumFromString(TypeSearch.class, "FOLDER");
        assertEquals("Should be equals", TypeSearch.FOLDER, folderOpt);

        final TypeSearch dateOpt = ValidationUtils.getEnumFromString(TypeSearch.class, "DATE");
        assertEquals("Should be equals", TypeSearch.DATE, dateOpt);

    }

    /**
     * Test search periods enum.
     */
    @Test
    public void testSearchPeriods(){
        final SearchPeriods twentyFourHoursOpt = SearchPeriods.getPeriodString("24");
        assertEquals("Should be equals", SearchPeriods.TWENTYFOURHOURS, twentyFourHoursOpt);

        final SearchPeriods sevenDaysOpt = SearchPeriods.getPeriodString("7");
        assertEquals("Should be equals", SearchPeriods.SEVENDAYS, sevenDaysOpt);

        final SearchPeriods thirtyDaysOpt = SearchPeriods.getPeriodString("30");
        assertEquals("Should be equals", SearchPeriods.THIRTYDAYS, thirtyDaysOpt);

        final SearchPeriods allTimeOpt = SearchPeriods.getPeriodString("all");
        assertEquals("Should be equals", SearchPeriods.ALLTIME, allTimeOpt);

        final SearchPeriods oneYearTimeOpt = SearchPeriods.getPeriodString("365");
        assertEquals("Should be equals", SearchPeriods.ONEYEAR, oneYearTimeOpt);

       /*  final SearchPeriods yearsTimeOpt = SearchPeriods.getPeriodString("1095");
        assertEquals("Should be equals", "all", yearsTimeOpt);

       final SearchPeriods todayOpt = SearchPeriods.TWENTYFOURHOURS;
        assertEquals("Should be equals", "24", todayOpt.toNumber());

        final SearchPeriods weekOpt = SearchPeriods.SEVENDAYS;
        assertEquals("Should be equals", "7", weekOpt.toNumber());

        final SearchPeriods monthOpt = SearchPeriods.THIRTYDAYS;
        assertEquals("Should be equals", "30", monthOpt.toNumber());

        final SearchPeriods allOpt = SearchPeriods.ALLTIME;
        assertEquals("Should be equals", "1095", allOpt.toNumber());

        final SearchPeriods oneYearOpt = SearchPeriods.ONEYEAR;
        assertEquals("Should be equals", "365", oneYearOpt.toNumber());*/
    }

    /**
     * Test profile enum.
     */
    @Test
    public void testProfile(){
        final Profile emailOpt = ValidationUtils.getEnumFromString(Profile.class, "EMAIL");
        assertEquals("Should be equals", Profile.EMAIL, emailOpt);

        final Profile usernameOpt = ValidationUtils.getEnumFromString(Profile.class, "USERNAME");
        assertEquals("Should be equals", Profile.USERNAME, usernameOpt);

        final Profile languageOpt = ValidationUtils.getEnumFromString(Profile.class, "LANGUAGE");
        assertEquals("Should be equals", Profile.LANGUAGE, languageOpt);

        final Profile pictureOpt = ValidationUtils.getEnumFromString(Profile.class, "PICTURE");
        assertEquals("Should be equals", Profile.PICTURE, pictureOpt);

        final Profile privateOpt = ValidationUtils.getEnumFromString(Profile.class, "PRIVATE");
        assertEquals("Should be equals", Profile.PRIVATE, privateOpt);

        final Profile realNameOpt = ValidationUtils.getEnumFromString(Profile.class, "REAL_NAME");
        assertEquals("Should be equals", Profile.REAL_NAME, realNameOpt);

        final Profile welcomeeOpt = ValidationUtils.getEnumFromString(Profile.class, "WELCOME");
        assertEquals("Should be equals", Profile.WELCOME, welcomeeOpt);

        final Profile infoOpt = ValidationUtils.getEnumFromString(Profile.class, "PAGE_INFO");
        assertEquals("Should be equals", Profile.PAGE_INFO, infoOpt);
    }

    /**
     * Test social provider.
     */
    @Test
    public void testSocialProvider(){
        final SocialProvider twitterProvider = ValidationUtils.getEnumFromString(SocialProvider.class, "TWITTER");
        assertEquals("Should be equals", SocialProvider.TWITTER, twitterProvider);

        final SocialProvider facebookProvider = ValidationUtils.getEnumFromString(SocialProvider.class, "FACEBOOK");
        assertEquals("Should be equals", SocialProvider.FACEBOOK, facebookProvider);

        final SocialProvider identicaProvider = ValidationUtils.getEnumFromString(SocialProvider.class, "IDENTICA");
        assertEquals("Should be equals", SocialProvider.IDENTICA, identicaProvider);

        final SocialProvider linkedinProvider = ValidationUtils.getEnumFromString(SocialProvider.class, "LINKEDIN");
        assertEquals("Should be equals", SocialProvider.LINKEDIN, linkedinProvider);

        final SocialProvider mySpaceProvider = ValidationUtils.getEnumFromString(SocialProvider.class, "YAHOO");
        assertEquals("Should be equals", SocialProvider.YAHOO, mySpaceProvider);

        final SocialProvider googleBuzzProvider = ValidationUtils.getEnumFromString(SocialProvider.class, "GOOGLE_BUZZ");
        assertEquals("Should be equals", SocialProvider.GOOGLE_BUZZ, googleBuzzProvider);

        final SocialProvider yahooProvider = ValidationUtils.getEnumFromString(SocialProvider.class, "MYSPACE");
        assertEquals("Should be equals", SocialProvider.MYSPACE, yahooProvider  );
    }

    /**
     *  Test HitCategory Enum.
     */
    @Test
    public void testHitCategory(){
        final HitCategory catVote =ValidationUtils.getEnumFromString(HitCategory.class, "VOTE");
        assertEquals("Hit Category Should be equals", HitCategory.VOTE, catVote);
        final HitCategory catVisit = ValidationUtils.getEnumFromString(HitCategory.class, "VISIT");
        assertEquals("Hit Category Should be equals", HitCategory.VISIT, catVisit);
    }

    /**
     * Test RelativeTime Enum
     */
    @Test
    public void testRelativeTime(){
        final RelativeTimeEnum rightnow = ValidationUtils.getEnumFromString(RelativeTimeEnum.class, "RIGTH_NOW");
        assertEquals("Relative time should be equals", RelativeTimeEnum.RIGTH_NOW , rightnow);

        final RelativeTimeEnum oneSecond = ValidationUtils.getEnumFromString(RelativeTimeEnum.class, "ONE_SECOND_AGO");
        assertEquals("Relative time should be equals", RelativeTimeEnum.ONE_SECOND_AGO , oneSecond);

        final RelativeTimeEnum aSecondsAgo = ValidationUtils.getEnumFromString(RelativeTimeEnum.class, "SECONDS_AGO");
        assertEquals("Relative time should be equals", RelativeTimeEnum.SECONDS_AGO , aSecondsAgo);

        final RelativeTimeEnum aMinuteAgo = ValidationUtils.getEnumFromString(RelativeTimeEnum.class, "A_MINUTE_AGO");
        assertEquals("Relative time should be equals", RelativeTimeEnum.A_MINUTE_AGO , aMinuteAgo);

        final RelativeTimeEnum minutesAgo = ValidationUtils.getEnumFromString(RelativeTimeEnum.class, "MINUTES_AGO");
        assertEquals("Relative time should be equals", RelativeTimeEnum.MINUTES_AGO , minutesAgo);

        final RelativeTimeEnum anHourAgo = ValidationUtils.getEnumFromString(RelativeTimeEnum.class, "AN_HOUR_AGO");
        assertEquals("Relative time should be equals", RelativeTimeEnum.AN_HOUR_AGO , anHourAgo);

        final RelativeTimeEnum hoursAgo = ValidationUtils.getEnumFromString(RelativeTimeEnum.class, "HOURS_AGO");
        assertEquals("Relative time should be equals", RelativeTimeEnum.HOURS_AGO , hoursAgo);

        final RelativeTimeEnum yesterday = ValidationUtils.getEnumFromString(RelativeTimeEnum.class, "YESTERDAY");
        assertEquals("Relative time should be equals", RelativeTimeEnum.YESTERDAY , yesterday);

        final RelativeTimeEnum daysAgo = ValidationUtils.getEnumFromString(RelativeTimeEnum.class, "DAYS_AGO");
        assertEquals("Relative time should be equals", RelativeTimeEnum.DAYS_AGO , daysAgo);

        final RelativeTimeEnum aMonthAgo = ValidationUtils.getEnumFromString(RelativeTimeEnum.class, "ONE_MONTH_AGO");
        assertEquals("Relative time should be equals", RelativeTimeEnum.ONE_MONTH_AGO , aMonthAgo);

        final RelativeTimeEnum monthsAgo = ValidationUtils.getEnumFromString(RelativeTimeEnum.class, "MONTHS_AGO");
        assertEquals("Relative time should be equals", RelativeTimeEnum.MONTHS_AGO , monthsAgo);

        final RelativeTimeEnum oneYearAgo = ValidationUtils.getEnumFromString(RelativeTimeEnum.class, "ONE_YEAR_AGO");
        assertEquals("Relative time should be equals", RelativeTimeEnum.ONE_YEAR_AGO , oneYearAgo);

        final RelativeTimeEnum yearsAgo = ValidationUtils.getEnumFromString(RelativeTimeEnum.class, "YEARS_AGO");
        assertEquals("Relative time should be equals", RelativeTimeEnum.YEARS_AGO , yearsAgo);

    }

    /**
     * Test TypeDatabase Enum.
     */
    @Test
    public void testTypeDatabase(){
        final TypeDatabase oracleDBase = TypeDatabase.getTypeDatabaseByString("oracle");
        assertEquals("Database type should be equals", TypeDatabase.ORACLE , oracleDBase);

        final TypeDatabase hsqlDBase = TypeDatabase.getTypeDatabaseByString("hsqldb");
        assertEquals("Database type should be equals", TypeDatabase.HSQLDB , hsqlDBase);

        final TypeDatabase mysqlDBase = TypeDatabase.getTypeDatabaseByString("mysql");
        assertEquals("Database type should be equals", TypeDatabase.MYSQL , mysqlDBase);

        final TypeDatabase postgresDBase = TypeDatabase.getTypeDatabaseByString("postgres");
        assertEquals("Database type should be equals", TypeDatabase.POSTGRES , postgresDBase);

        final TypeDatabase mssqlDBase = TypeDatabase.getTypeDatabaseByString("mssql");
        assertEquals("Database type should be equals", TypeDatabase.MSSQL, mssqlDBase);

        final TypeDatabase derbyDBase = TypeDatabase.getTypeDatabaseByString("derby");
        assertEquals("Database type should be equals", TypeDatabase.DERBY, derbyDBase);

        final TypeDatabase db2DBase = TypeDatabase.getTypeDatabaseByString("db2");
        assertEquals("Database type should be equals", TypeDatabase.DB2 , db2DBase);
    }

    /**
     * Test DateClasificated Enum.
     */
    @Test
    public void testDateClasificated(){

        final DateClasificatedEnum dcToday = ValidationUtils.getEnumFromString(DateClasificatedEnum.class, "TODAY");
        assertEquals("DateClasificatedEnum should be equals", DateClasificatedEnum.TODAY , dcToday);

        final DateClasificatedEnum dcWeek = ValidationUtils.getEnumFromString(DateClasificatedEnum.class, "THIS_WEEK");
        assertEquals("DateClasificatedEnum should be equals", DateClasificatedEnum.THIS_WEEK, dcWeek);

        final DateClasificatedEnum dcMonth = ValidationUtils.getEnumFromString(DateClasificatedEnum.class, "THIS_MONTH");
        assertEquals("DateClasificatedEnum should be equals", DateClasificatedEnum.THIS_MONTH , dcMonth);

        final DateClasificatedEnum dcLastMonth = ValidationUtils.getEnumFromString(DateClasificatedEnum.class, "LAST_MONTH");
        assertEquals("DateClasificatedEnum  should be equals", DateClasificatedEnum.LAST_MONTH , dcLastMonth);

        final DateClasificatedEnum dcMonthsAgo = ValidationUtils.getEnumFromString(DateClasificatedEnum.class, "FEW_MONTHS_AGO");
        assertEquals("DateClasificatedEnum should be equals", DateClasificatedEnum.FEW_MONTHS_AGO , dcMonthsAgo);

        final DateClasificatedEnum dcLastYear = ValidationUtils.getEnumFromString(DateClasificatedEnum.class, "LAST_YEAR");
        assertEquals(" DateClasificatedEnum should be equals", DateClasificatedEnum.LAST_YEAR , dcLastYear);

        final DateClasificatedEnum dcLongTimeAgo = ValidationUtils.getEnumFromString(DateClasificatedEnum.class, "LONG_TIME_AGO");
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
        final ShowResultsOptions percentOpt = ValidationUtils.getEnumFromString(ShowResultsOptions.class, "PERCENT");
        assertEquals("Should be equals", ShowResultsOptions.PERCENT , percentOpt);

        final ShowResultsOptions restrictedOpt = ValidationUtils.getEnumFromString(ShowResultsOptions.class, "RESTRICTED");
        assertEquals("Should be equals", ShowResultsOptions.RESTRICTED , restrictedOpt);

        final ShowResultsOptions allOpt = ValidationUtils.getEnumFromString(ShowResultsOptions.class, "ALL");
        assertEquals("Should be equals", ShowResultsOptions.ALL , allOpt);
    }
}
