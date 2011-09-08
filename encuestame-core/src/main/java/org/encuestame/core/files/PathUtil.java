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
package org.encuestame.core.files;

/**
 * Commons URI paths.
 * @author Picado, Juan juanATencuestame.org
 * @since Jan 22, 2011 9:44:41 PM
 */
public class PathUtil {

    /**
     * User sign in.
     */
    public static String signIn = "/user/signin";

    public static String DEFAUL_PICTURE_PREFIX = "file_";

    /**
     * Profile Images Folder.
     */
    public static final String profile = "/profile";


    public static String DEFAULT_SOCIAL_CALLBACK_PATH = "/social/back/";

    /**
     * Upload Folder.
     */
    public static final String upload = "/upload";

    /**
     * Folder to store index for lucene.
     */
    public static final String index = "/index";

    /**
     * Global Accounts Folder Name.
     */
    public static final String accounts = "/accounts";

    /**
    *
    */
   public static String configFileName = "encuestame-config.xml";

    /**
     *
     */
    public static final String account = "/account";

    /**
     *
     */
    public static final String profileUserImage = "/user/picture/profile";

    public static final String DASHBOARD_REDIRECT = "redirect:/user/dashboard";
}
