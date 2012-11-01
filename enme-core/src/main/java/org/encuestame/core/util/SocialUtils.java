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
package org.encuestame.core.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.utils.social.SocialProvider;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.util.Assert;

/**
 * Social Util Helpers.
 * @author Picado, Juan juanATencuestame.org
 * @since Feb 11, 2011 9:54:24 PM
 */
public class SocialUtils {

    /** Log. **/
    private static Logger log = Logger.getLogger(SocialUtils.class);

    /**
     * TinyUrl rest url.
     */
    public static final String TINY_URL = "http://tinyurl.com/api-create.php";

    /**
     * Google Url short url.
     */
    public static final String GOOGLE_SHORT_URL = "https://www.googleapis.com/urlshortener/v1/url";

    /**
     * Google url stats.
     */
    public static final String GOOGLE_SHORT_URL_STATS = "https://www.googleapis.com/urlshortener/v1/url?shortUrl=$1&projection=FULL";

    /**
     * Bity short url api.
     */
    public static final String BITLY_SHORT_URL = "http://api.bit.ly/shorten";

    /**
     * Facebook scopes.
     */
    public static final String FACEBOOK_SCOPE = "email,read_stream,publish_stream,user_status,user_location,offline_access";

    /**
     * Twitter limit.
     */
    public static final Integer TWITTER_LIMIT = 140;

    /**
     * Twitter authentictation error.
     * TODO: move to {@link SocialUtils}.
     */
    public static final int TWITTER_AUTH_ERROR = 401;

    /**
     * Get Google Stats from google short url.
     * @param googleShortUrl
     * @return
     * @throws IOException
     */
    public static String getGoGlStats(final String googleShortUrl) throws IOException {
        HttpClient httpclient = new HttpClient();
        String completeUrl = StringUtils.replace(SocialUtils.GOOGLE_SHORT_URL_STATS, "$1", googleShortUrl);
        HttpMethod method = new GetMethod(completeUrl);
        httpclient.executeMethod(method);
        String tinyUrl = method.getResponseBodyAsString();
        method.releaseConnection();
        return tinyUrl;
    }

    /**
     * Get Google Short Url.
     * @return
     * @throws EnmeFailOperation
     */
    public static String getGoGl(final String urlPath, String key) {
        log.debug("getGoGl url "+urlPath);
        log.debug("getGoGl key "+key);
        String shortUrl = null;
        URL simpleURL = null;
        HttpsURLConnection url = null;
        BufferedInputStream bStream = null;
        StringBuffer resultString = new StringBuffer("");
        String inputString = "{\"longUrl\":\"" + urlPath + "\"}";
        log.debug("getGoGl inputString "+inputString);
        try {
            simpleURL = new URL(
                    "https://www.googleapis.com/urlshortener/v1/url?key=" + key);
            url = (HttpsURLConnection) simpleURL.openConnection();
            url.setDoOutput(true);
            url.setRequestProperty("content-type", "application/json");
            PrintWriter pw = new PrintWriter(url.getOutputStream());
            pw.print(inputString);
            pw.close();
        } catch (Exception ex) {
            log.error(ex);
            shortUrl = urlPath;
        }
        try {
            bStream = new BufferedInputStream(url.getInputStream());
            int i;
            while ((i = bStream.read()) >= 0) {
                resultString.append((char) i);
            }
          //  final Object jsonObject = JSONValue.parse(resultString.toString());
         //   final JSONObject o = (JSONObject) jsonObject;
         //   shortUrl = (String) o.get("id");
        } catch (Exception ex) {
            SocialUtils.log.error(ex);
            shortUrl = urlPath;
        }
        return shortUrl;
    }

    /**
     * Get TinyUrl.
     *
     * @param string
     * @return
     * @throws HttpException
     * @throws IOException
     */
    public static String getTinyUrl(String string){
        String tinyUrl = string;
        HttpClientParams params = new HttpClientParams();
        params.setConnectionManagerTimeout(EnMePlaceHolderConfigurer.getIntegerProperty("application.timeout"));
        params.setSoTimeout(EnMePlaceHolderConfigurer.getIntegerProperty("application.timeout"));
        HttpClient httpclient = new HttpClient(params); //TODO: time out??
        //httpclient.setConnectionTimeout(EnMePlaceHolderConfigurer.getIntegerProperty("application.timeout"));
        log.debug("tiny url timeout "+EnMePlaceHolderConfigurer.getIntegerProperty("application.timeout"));
        //httpclient.setParams(params);
        HttpMethod method = new GetMethod(SocialUtils.TINY_URL);
        method.setQueryString(new NameValuePair[] { new NameValuePair("url",
                string) });
        try {
            log.debug("tiny url execute: "+string);
            httpclient.executeMethod(method);
            tinyUrl = method.getResponseBodyAsString();
        } catch (HttpException e) {
            log.error("HttpException "+ e);
            tinyUrl = string;
        } catch (IOException e) {
            log.error("IOException"+ e);
            tinyUrl = string;
        } finally{
            method.releaseConnection();
        }
        return tinyUrl;
    }

    /**
     * Short URL with bitly.com.
     * @param urlPath url
     * @param key bitly key
     * @param login bitly login
     * @return
     * @throws EnmeFailOperation
     */
    public static String getBitLy(final String urlPath, final String key, final String login){
        final HttpClient httpclient = new HttpClient();
        final HttpMethod method = new GetMethod(SocialUtils.BITLY_SHORT_URL);
        method.setQueryString(
                new NameValuePair[]{
                        new NameValuePair("longUrl",urlPath),
                        new NameValuePair("version","2.0.1"),
                        new NameValuePair("login", login),
                        new NameValuePair("apiKey", key),
                        new NameValuePair("format","json"),
                        new NameValuePair("history","1")
                        }
                );
        String responseXml = null;
        try {
            httpclient.executeMethod(method);
            //{"errorCode": 0, "errorMessage": "",
            //"results": {"http://www.encuestame.org": {"userHash": "gmks0X", "shortKeywordUrl": "", "hash": "hMMQuX",
           // "shortCNAMEUrl": "http://bit.ly/gmks0X", "shortUrl": "http://bit.ly/gmks0X"}},
            //"statusCode": "OK"}
            final Object jsonObject = JSONValue.parse(method.getResponseBodyAsString());
            log.debug("getBitLy: "+jsonObject.toString());
            final JSONObject o = (JSONObject) jsonObject;
            final JSONObject results = (JSONObject) o.get("results");
            final JSONObject url = (JSONObject) results.get(urlPath);
             responseXml = (String) url.get("shortUrl");
        } catch (HttpException e1) {
            log.error(e1);
            responseXml = urlPath;
        } catch (IOException e1) {
            log.error(e1);
            responseXml = urlPath;
        } catch (Exception e) {
            log.error(e);
            responseXml = urlPath;
        }
        return responseXml;
    }

    /**
     * Build the original  url to tweet in the social network.
     * @param id id
     * @param username username (only for twitter)
     * @return the url.
     */
    public static String getSocialTweetPublishedUrl(
            final String id,
            final String username,
            final SocialProvider provider) {
        Assert.notNull(id);
        final StringBuilder builder = new StringBuilder();
        if(SocialProvider.TWITTER.equals(provider)){
            String twitterUrl = EnMePlaceHolderConfigurer.getProperty("social.twitter");
            twitterUrl = twitterUrl.replace("{username}", username); //FIXME: possible NULLPOINTEREXCEPTION
            twitterUrl = twitterUrl.replace("{id}", id);
            builder.append(twitterUrl);
        } else if(SocialProvider.FACEBOOK.equals(provider)){
            String facebookUrl = EnMePlaceHolderConfigurer.getProperty("social.facebook");
            String[] array = id.split("_");
            log.debug("Facebook Id array:{"+array.length);
            if (array.length >= 2) {
                facebookUrl = facebookUrl.replace("{B}", array[0]);
                facebookUrl = facebookUrl.replace("{A}", array[1]);
                builder.append(facebookUrl);
            }
        } else if(SocialProvider.LINKEDIN.equals(provider)){
            builder.append(EnMePlaceHolderConfigurer.getProperty("social.linkedin"));
        } else if(SocialProvider.IDENTICA.equals(provider)){
             String identicaUrl = EnMePlaceHolderConfigurer.getProperty("social.identica");
             identicaUrl = identicaUrl.replace("{id}", id);
             builder.append(identicaUrl);
        }
        log.debug("getSocialTweetPublishedUrl "+builder.toString());
        return builder.toString();
    }

    /**
     * Build a real url profile based on id.
     * @param id social account id.
     * @param provider {@link SocialProvider}.
     * @return
     */
    public static String getSocialAccountProfileUrl(final String id,
            final SocialProvider provider){
        final StringBuilder builder = new StringBuilder();
        if(SocialProvider.TWITTER.equals(provider)){
            String twitterUrl = EnMePlaceHolderConfigurer.getProperty("social.profile.twitter");
            twitterUrl = twitterUrl.replace("{username}", id);
            builder.append(twitterUrl);
        } else if(SocialProvider.FACEBOOK.equals(provider)) {
             String twitterUrl = EnMePlaceHolderConfigurer.getProperty("social.profile.facebook");
             twitterUrl = twitterUrl.replace("{id}", id);
             builder.append(twitterUrl);
        } else if(SocialProvider.IDENTICA.equals(provider)) {
             String identicaUrl = EnMePlaceHolderConfigurer.getProperty("social.profile.identica");
             identicaUrl = identicaUrl.replace("{id}", id);
             builder.append(identicaUrl);
        }
        log.debug("getSocialTweetPublishedUrl "+builder.toString());
        return builder.toString();
    }
}
