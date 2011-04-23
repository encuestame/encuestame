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
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.encuestame.persistence.exception.EnmeFailOperation;
//import org.json.simple.JSONObject;
//import org.json.simple.JSONValue;

/**
 * Social Util Helpers.
 * @author Picado, Juan juanATencuestame.org
 * @since Feb 11, 2011 9:54:24 PM
 */
public class SocialUtils {

    private static org.apache.commons.logging.Log log = LogFactory.getLog(SocialUtils.class);

    public final static String tinyURL = "http://tinyurl.com/api-create.php";

    public final static String googleURL = "https://www.googleapis.com/urlshortener/v1/url";

    public final static String googleURLStas = "https://www.googleapis.com/urlshortener/v1/url?shortUrl=$1&projection=FULL";

    public final static String bitLyUrlApi = "http://api.bit.ly/shorten";

    /**
     * Get Google Stats from google short url.
     * @param googleShortUrl
     * @return
     * @throws IOException
     */
    public static String getGoGlStats(final String googleShortUrl) throws IOException {
        HttpClient httpclient = new HttpClient();
        String completeUrl = StringUtils.replace(SocialUtils.googleURLStas, "$1", googleShortUrl);
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
        HttpClient httpclient = new HttpClient();
        HttpMethod method = new GetMethod(SocialUtils.tinyURL);
        method.setQueryString(new NameValuePair[] { new NameValuePair("url",
                string) });
        try {
            httpclient.executeMethod(method);
            tinyUrl = method.getResponseBodyAsString();
        } catch (HttpException e) {
            log.error(e);
            e.printStackTrace();
            tinyUrl = string;
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
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
        final HttpMethod method = new GetMethod(SocialUtils.bitLyUrlApi);
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
     //       final Object jsonObject = JSONValue.parse(method.getResponseBodyAsString());
  //          final JSONObject o = (JSONObject) jsonObject;
    //        final JSONObject results = (JSONObject) o.get("results");
   //         final JSONObject url = (JSONObject) results.get(urlPath);
     //       responseXml = (String) url.get("shortUrl");
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
}
