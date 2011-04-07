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
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnmeFailOperation;

/**
 * Social Util Helpers.
 * @author Picado, Juan juanATencuestame.org
 * @since Feb 11, 2011 9:54:24 PM
 */
public class SocialUtils {

    public final static String tinyURL = "http://tinyurl.com/api-create.php";

    public final static String googleURL = "https://www.googleapis.com/urlshortener/v1/url";

    public final static String googleURLStas = "https://www.googleapis.com/urlshortener/v1/url?shortUrl=$1&projection=FULL";

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
    public static String getGoGl(final String urlPath, String key) throws EnmeFailOperation {
        URL simpleURL = null;
        HttpsURLConnection url = null;
        BufferedInputStream bStream = null;
        StringBuffer resultString = new StringBuffer("");
        String inputString = "{\"longUrl\":\"" + urlPath + "\"}";
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
            throw new EnmeFailOperation("Exception in Connecting to API");
        }
        try {
            bStream = new BufferedInputStream(url.getInputStream());
            int i;
            while ((i = bStream.read()) >= 0) {
                resultString.append((char) i);
            }
        } catch (Exception ex) {
            return "Exception in Reading Result";
        }
        return resultString.toString();
    }

    /**
     * Get TinyUrl.
     *
     * @param string
     * @return
     * @throws HttpException
     * @throws IOException
     */
    public static String getTinyUrl(String string) throws HttpException,
            IOException {
        HttpClient httpclient = new HttpClient();

        HttpMethod method = new GetMethod(SocialUtils.tinyURL);
        method.setQueryString(new NameValuePair[] { new NameValuePair("url",
                string) });
        httpclient.executeMethod(method);
        String tinyUrl = method.getResponseBodyAsString();
        method.releaseConnection();
        return tinyUrl;
    }
}
