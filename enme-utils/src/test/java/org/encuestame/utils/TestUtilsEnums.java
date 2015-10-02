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
        final CommentOptions optApprove = CommentOptions.getCommentOption("APPROVE");
        assertEquals("Should be equals", "APPROVE", optApprove.toString());
        final CommentOptions optRestrict = CommentOptions.getCommentOption("RESTRICT");
        assertEquals("Should be equals", "RESTRICT", optRestrict.toString());
        final CommentOptions optModerate = CommentOptions.getCommentOption("MODERATE");
        assertEquals("Should be equals", "MODERATE", optModerate.toString());

        final CommentOptions optPublished = CommentOptions.getCommentOption("PUBLISHED");
        assertEquals("Should be equals", "PUBLISHED", optPublished.toString());

        final CommentOptions optSpam = CommentOptions.getCommentOption("SPAM");
        assertEquals("Should be equals", "SPAM", optSpam.toString());

        final CommentOptions optAll = CommentOptions.getCommentOption("ALL");
        assertEquals("Should be equals", "ALL", optAll.toString());
    }

    /**
     * Test Comments social options enum.
     */
    @Test
    public void testCommentsSocialOptions(){
        final CommentsSocialOptions likeVoteOpt = CommentsSocialOptions.getCommentsSocialOptions("LIKE");
        assertEquals("Should be equals", "LIKE", likeVoteOpt.toString());
        final CommentsSocialOptions disLikeVoteOpt = CommentsSocialOptions.getCommentsSocialOptions("DISLIKE");
        assertEquals("Should be equals", "DISLIKE", disLikeVoteOpt.toString());
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

         final TypeSearchResult questionOption = TypeSearchResult.getTypeSearchResult("QUESTION");
         assertEquals("Should be equals", "QUESTION", questionOption.toString());

         final TypeSearchResult hashTagOption = TypeSearchResult.getTypeSearchResult("HASHTAG");
         assertEquals("Should be equals", "HASHTAG", hashTagOption.toString());

        final TypeSearchResult hashTagRatedOption = TypeSearchResult.getTypeSearchResult("HASHTAGRATED");
        assertEquals("Should be equals", "HASHTAGRATED", hashTagRatedOption.toString());

        final TypeSearchResult socialNetworkOption = TypeSearchResult.getTypeSearchResult("SOCIALNETWORK");
        assertEquals("Should be equals", "SOCIALNETWORK", socialNetworkOption.toString());

        final TypeSearchResult hitsOption = TypeSearchResult.getTypeSearchResult("HITS");
        assertEquals("Should be equals", "HITS", hitsOption.toString());

        final TypeSearchResult votesOption = TypeSearchResult.getTypeSearchResult("VOTES");
        assertEquals("Should be equals", "VOTES", votesOption.toString());

        final TypeSearchResult allOption = TypeSearchResult.getTypeSearchResult("ALL");
        assertEquals("Should be equals", "ALL", allOption.toString());
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

        final TypeSearch dateOpt = TypeSearch.getSearchString("date");
        assertEquals("Should be equals", "DATE", dateOpt.toString());

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

        final Profile welcomeeOpt = Profile.findProfile("WELCOME");
        assertEquals("Should be equals", "welcome", welcomeeOpt.toString());

        final Profile infoOpt = Profile.findProfile("PAGE_INFO");
        assertEquals("Should be equals", "page_info", infoOpt.toString());
    }

    /**
     * Test social provider.
     */
    @Test
    public void testSocialProvider(){
        final SocialProvider twitterProvider = SocialProvider.getProvider("TWITTER");
        assertEquals("Should be equals", "TWITTER", twitterProvider.toString());

        final SocialProvider facebookProvider = SocialProvider.getProvider("FACEBOOK");
        assertEquals("Should be equals", "FACEBOOK", facebookProvider.toString());

        final SocialProvider identicaProvider = SocialProvider.getProvider("IDENTICA");
        assertEquals("Should be equals", "IDENTICA", identicaProvider.toString());

        final SocialProvider linkedinProvider = SocialProvider.getProvider("LINKEDIN");
        assertEquals("Should be equals", "LINKEDIN", linkedinProvider.toString());

        final SocialProvider mySpaceProvider = SocialProvider.getProvider("YAHOO");
        assertEquals("Should be equals", "YAHOO", mySpaceProvider.toString());

        final SocialProvider googleBuzzProvider = SocialProvider.getProvider("GOOGLE_BUZZ");
        assertEquals("Should be equals", "GOOGLEBUZZ", googleBuzzProvider.toString());

        final SocialProvider yahooProvider = SocialProvider.getProvider("MYSPACE");
        assertEquals("Should be equals", "MYSPACE", yahooProvider.toString());
    }

    /**
     *  Test HitCategory Enum.
     */
    @Test
    public void testHitCategory(){
        final HitCategory catVote = HitCategory.getCategory("VOTE");
        assertEquals("Hit Category Should be equals", "VOTE", catVote.toString());
        final HitCategory catVisit = HitCategory.getCategory("VISIT");
        assertEquals("Hit Category Should be equals", "VISIT", catVisit.toString());
    }

    /**
     * Test RelativeTime Enum
     */
    @Test
    public void testRelativeTime(){
        final RelativeTimeEnum rightnow = RelativeTimeEnum.RIGTH_NOW;
        assertEquals("Relative time should be equals", "1" , rightnow.toNumber().toString());

        final RelativeTimeEnum oneSecond = RelativeTimeEnum.ONE_SECOND_AGO;
        assertEquals("Relative time should be equals", "15" , oneSecond.toNumber().toString());

        final RelativeTimeEnum aSecondsAgo = RelativeTimeEnum.SECONDS_AGO;
        assertEquals("Relative time should be equals", "70" , aSecondsAgo.toNumber().toString());

        final RelativeTimeEnum aMinuteAgo = RelativeTimeEnum.A_MINUTE_AGO;
        assertEquals("Relative time should be equals", "1" , aMinuteAgo.toNumber().toString());

        final RelativeTimeEnum minutesAgo = RelativeTimeEnum.MINUTES_AGO;
        assertEquals("Relative time should be equals", "80" , minutesAgo.toNumber().toString());

        final RelativeTimeEnum anHourAgo = RelativeTimeEnum.AN_HOUR_AGO;
        assertEquals("Relative time should be equals", "1" , anHourAgo.toNumber().toString());

        final RelativeTimeEnum hoursAgo = RelativeTimeEnum.HOURS_AGO;
        assertEquals("Relative time should be equals", "18" , hoursAgo.toNumber().toString());

        final RelativeTimeEnum yesterday = RelativeTimeEnum.YESTERDAY;
        assertEquals("Relative time should be equals", "1" , yesterday.toNumber().toString());

        final RelativeTimeEnum daysAgo = RelativeTimeEnum.DAYS_AGO;
        assertEquals("Relative time should be equals", "4" , daysAgo.toNumber().toString());

        final RelativeTimeEnum aMonthAgo = RelativeTimeEnum.ONE_MONTH_AGO;
        assertEquals("Relative time should be equals", "8" , aMonthAgo.toNumber().toString());

        final RelativeTimeEnum monthsAgo = RelativeTimeEnum.MONTHS_AGO;
        assertEquals("Relative time should be equals", "11" , monthsAgo.toNumber().toString());

        final RelativeTimeEnum oneYearAgo = RelativeTimeEnum.ONE_YEAR_AGO;
        assertEquals("Relative time should be equals", "1" , oneYearAgo.toNumber().toString());

        final RelativeTimeEnum yearsAgo = RelativeTimeEnum.YEARS_AGO;
        assertEquals("Relative time should be equals", "6" , yearsAgo.toNumber().toString());

    }

    /**
     * Test TypeDatabase Enum.
     */
    @Test
    public void testTypeDatabase(){
        final TypeDatabase oracleDBase = TypeDatabase.getTypeDatabaseByString("oracle");
        assertEquals("Database type should be equals", "ORACLE" , oracleDBase.toString());

        final TypeDatabase hsqlDBase = TypeDatabase.getTypeDatabaseByString("hsqldb");
        assertEquals("Database type should be equals", "HSQLDB" , hsqlDBase.toString());

        final TypeDatabase mysqlDBase = TypeDatabase.getTypeDatabaseByString("mysql");
        assertEquals("Database type should be equals", "MYSQL" , mysqlDBase.toString());

        final TypeDatabase postgresDBase = TypeDatabase.getTypeDatabaseByString("postgres");
        assertEquals("Database type should be equals", "POSTGRES" , postgresDBase.toString());

        final TypeDatabase mssqlDBase = TypeDatabase.getTypeDatabaseByString("mssql");
        assertEquals("Database type should be equals", "MSSQL", mssqlDBase.toString());

        final TypeDatabase derbyDBase = TypeDatabase.getTypeDatabaseByString("derby");
        assertEquals("Database type should be equals", "DERBY", derbyDBase.toString());

        final TypeDatabase db2DBase = TypeDatabase.getTypeDatabaseByString("db2");
        assertEquals("Database type should be equals", "DB2" , db2DBase.toString());
    }

    /**
     * Test DateClasificated Enum.
     */
    @Test
    public void testDateClasificated(){

        final DateClasificatedEnum dcToday = DateClasificatedEnum.TODAY;
        assertEquals("DateClasificatedEnum should be equals", "1" , dcToday.toNumber().toString());

        final DateClasificatedEnum dcWeek = DateClasificatedEnum.THIS_WEEK;
        assertEquals("DateClasificatedEnum should be equals", "7", dcWeek.toNumber().toString());

        final DateClasificatedEnum dcMonth = DateClasificatedEnum.THIS_MONTH;
        assertEquals("DateClasificatedEnum should be equals", "30" , dcMonth.toNumber().toString());

        final DateClasificatedEnum dcLastMonth = DateClasificatedEnum.LAST_MONTH;
        assertEquals("DateClasificatedEnum  should be equals", "180" , dcLastMonth.toNumber().toString());

        final DateClasificatedEnum dcMonthsAgo = DateClasificatedEnum.FEW_MONTHS_AGO;
        assertEquals("DateClasificatedEnum should be equals", "360" , dcMonthsAgo.toNumber().toString());

        final DateClasificatedEnum dcLastYear = DateClasificatedEnum.LAST_YEAR;
        assertEquals(" DateClasificatedEnum should be equals", "366" , dcLastYear.toNumber().toString());

        final DateClasificatedEnum dcLongTimeAgo = DateClasificatedEnum.LONG_TIME_AGO;
        assertEquals("DateClasificatedEnum should be equals", "720" , dcLongTimeAgo.toNumber().toString());
    }

    /**
     * Test RequestSourceType
     */
    @Test
    public void testRequestSourceType(){
        final RequestSourceType embedded = RequestSourceType.getSource("embedded");
        assertEquals("Request type should be equals", "embedded" , embedded.toString());

        final RequestSourceType normal = RequestSourceType.getSource("normal");
        assertEquals("Request type should be equals", "normal", normal.toString());
    }

    /**
     *
     */
    @Test
    public void testHashtagRate(){
        final HashTagRate label1 = HashTagRate.getHashTagMonthLabel("1");
        assertEquals("Month Label should be equals", "JANUARY" , label1.toString());

        final HashTagRate label2 = HashTagRate.getHashTagMonthLabel("2");
        assertEquals("Month Label should be equals", "FEBRUARY" , label2.toString());

        final HashTagRate label3 = HashTagRate.getHashTagMonthLabel("3");
        assertEquals("Month Label should be equals", "MARCH" , label3.toString());

        final HashTagRate label4 = HashTagRate.getHashTagMonthLabel("4");
        assertEquals("Month Label should be equals", "APRIL" , label4.toString());

        final HashTagRate label5 = HashTagRate.getHashTagMonthLabel("5");
        assertEquals("Month Label should be equals", "MAY" , label5.toString());

        final HashTagRate label6 = HashTagRate.getHashTagMonthLabel("6");
        assertEquals("Month Label should be equals", "JUNE" , label6.toString());

        final HashTagRate label7 = HashTagRate.getHashTagMonthLabel("7");
        assertEquals("Month Label should be equals", "JULY" , label7.toString());

        final HashTagRate label8 = HashTagRate.getHashTagMonthLabel("8");
        assertEquals("Month Label should be equals", "AUGUST" , label8.toString());

        final HashTagRate label9 = HashTagRate.getHashTagMonthLabel("9");
        assertEquals("Month Label should be equals", "SEPTEMBER" , label9.toString());

        final HashTagRate label10 = HashTagRate.getHashTagMonthLabel("10");
        assertEquals("Month Label should be equals", "OCTOBER" , label10.toString());

        final HashTagRate label11 = HashTagRate.getHashTagMonthLabel("11");
        assertEquals("Month Label should be equals", "NOVEMBER" , label11.toString());

        final HashTagRate label12 = HashTagRate.getHashTagMonthLabel("12");
        assertEquals("Month Label should be equals", "DECEMBER" , label12.toString());


        final HashTagRate lblWeek1 = HashTagRate.getHashTagWeekDayLabel("1");
        assertEquals("Week Label should be equals", "MONDAY" , lblWeek1.toString());


        final HashTagRate lblWeek2 = HashTagRate.getHashTagWeekDayLabel("2");
        assertEquals("Week Label should be equals", "TUESDAY" , lblWeek2.toString());


        final HashTagRate lblWeek3 = HashTagRate.getHashTagWeekDayLabel("3");
        assertEquals("Week Label should be equals", "WEDNESDAY" , lblWeek3.toString());

        final HashTagRate lblWeek4 = HashTagRate.getHashTagWeekDayLabel("4");
        assertEquals("Week Label should be equals", "THURSDAY", lblWeek4.toString());

        final HashTagRate lblWeek5 = HashTagRate.getHashTagWeekDayLabel("5");
        assertEquals("Week Label should be equals", "FRIDAY" , lblWeek5.toString());

        final HashTagRate lblWeek6 = HashTagRate.getHashTagWeekDayLabel("6");
        assertEquals("Week Label should be equals", "SATURDAY" , lblWeek6.toString());

        final HashTagRate lblWeek7 = HashTagRate.getHashTagWeekDayLabel("7");
        assertEquals("Week Label should be equals", "SUNDAY" , lblWeek7.toString());
    }

    /**
     * Test ShowResultsOptions Enum.
     */
    @Test
    public void testShowResultsOptions(){
        final ShowResultsOptions percentOpt = ShowResultsOptions.getShowResults("PERCENT");
        assertEquals("Should be equals", "PERCENT" , percentOpt.toString());

        final ShowResultsOptions restrictedOpt = ShowResultsOptions.getShowResults("RESTRICTED");
        assertEquals("Should be equals", "RESTRICTED" , restrictedOpt.toString());

        final ShowResultsOptions allOpt = ShowResultsOptions.getShowResults("ALL");
        assertEquals("Should be equals", "ALL" , allOpt.toString());
    }
}
