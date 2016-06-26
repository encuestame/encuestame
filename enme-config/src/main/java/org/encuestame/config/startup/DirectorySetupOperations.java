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
package org.encuestame.config.startup;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.util.FileUtils;
import org.encuestame.util.exception.EnmeFailOperation;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Directory Operations.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 20, 2011
 */
public class DirectorySetupOperations {

    /** Log. **/
    private static Log log = LogFactory.getLog(DirectorySetupOperations.class);
    /** Default picture directory name. **/
    private static final String PICTURES_DEFAULT_FOLDER = "pictures";
    /** Default profiles directory name. **/
    private static final String PROFILES_DEFAULT_FOLDER = "profiles";
    /** Default indexes directory name. **/
    private static final String INDEXES_DEFAULT_FOLDER = "indexes";

    private static final String SAMPLE_CONFIG_FILE = "encuestame-config-sample.xml";

    /**
     * Constructor.
     */
    public DirectorySetupOperations() {}

    /**
     * Check if ROOT directory exist, by default is not created.
     * @return if the directoy exist or not.
     * @throws EnmeFailOperation exception if exist issues on create directory.
     */
    public static boolean isHomeDirectoryValid() {
        final String directory = DirectorySetupOperations.getHomeDirectory();
        if (directory == null) {
            return false;
        }
        final File rootDir = new File(directory);
        log.debug("EnMe: Home directory: " + rootDir);
        return rootDir.exists();
    }

    /**
     * Create config file.
     * encuestame-config.xml
     * @throws IOException
     * @throws EnmeFailOperation
     */
    public static void createConfileFile() throws IOException, EnmeFailOperation {
        log.debug("createConfileFile");
        final StringBuffer config = new StringBuffer(getHomeDirectory());
        config.append(PathUtil.configFileName);
        final File configFile = new File(config.toString());
        if (!configFile.exists()) {
            final Resource resource = new ClassPathResource(SAMPLE_CONFIG_FILE);
            //log.debug("createConfileFile resource"+resource);
            //log.debug("createConfileFile configFile"+configFile.getAbsolutePath());
            //final File sourceFile = resource.getInputStream();
            //log.debug("createConfileFile sourceFile"+sourceFile.getAbsolutePath());
            FileUtils.copy(resource.getInputStream(), configFile);
            EnMePlaceHolderConfigurer.getConfigurationManager().reloadConfigFile();
        }
    }

    /**
     * Check if profiles directory exist, by default is not created.
     * @param createIfNotExist true to create if not exist.
     * @return if the directoy exist or not.
     * @throws EnmeFailOperation exception if exist issues on create directory.
     */
    public static boolean checkIfProfilesDirectoryExist(boolean createIfNotExist)
            throws EnmeFailOperation {
        final File rootDir = new File(
                DirectorySetupOperations.getProfilesDirectory());
        check(createIfNotExist, rootDir);
        return rootDir.exists();
    }

    /**
     *
     * @param createIfNotExist
     * @param rootDir
     * @throws EnmeFailOperation
     */
    private static void check(boolean createIfNotExist, File rootDir) throws EnmeFailOperation{
        if (createIfNotExist && !rootDir.exists())  {
            boolean indexes = rootDir.mkdirs();
            if (!indexes) {
                log.error("Encuestame Directory, not able to create picture folder");
                throw new EnmeFailOperation("EnMe: not able to create index folder");
            }
        }
    }

    /**
     * Check if pictures directory exist, by default is not created.
     * @param createIfNotExist true to create if not exist.
     * @return if the directoy exist or not.
     * @throws EnmeFailOperation exception if exist issues on create directory.
     */
    public static boolean checkIfPicturesDirectoryExist(boolean createIfNotExist)
            throws EnmeFailOperation {
        final File rootDir = new File(
                DirectorySetupOperations.getPictureDirectory());
        check(createIfNotExist, rootDir);
        return rootDir.exists();
    }

    /**
     * Check if indexed directory exist, by default is not created.
     * @param createIfNotExist true to create if not exist.
     * @return if the directoy exist or not.
     * @throws EnmeFailOperation exception if exist issues on create directory.
     */
    public static boolean checkIfIndexedDirectoryExist(boolean createIfNotExist)
            throws EnmeFailOperation {
        final File indexedDir = new File(DirectorySetupOperations.getIndexesDirectory());
        //if autocreate is enabled and directory not exist
        if (createIfNotExist && !indexedDir.exists()) {
            boolean indexes = indexedDir.mkdirs();
            if (!indexes) {
                log.error("Encuestame Directory, not able to create index folder");
                throw new EnmeFailOperation(
                        "EnMe: not able to create index folder");
            }
        }
        return indexedDir.exists();
    }

    /**
     * Get root directory path.
     * @return real path of home directory.
     * @throws EnmeFailOperation
     */
    public static String getHomeDirectory() {
        //trying to get the home from the system, if not exist get the home directory from the config file
        String root = System.getProperty("encuestame.home") == null ? EnMePlaceHolderConfigurer.getProperty("encuestame.home") : System.getProperty("encuestame.home");
        log.debug("Home via system property :: " + System.getProperty("encuestame.home"));
        EnMePlaceHolderConfigurer.setProperty("encuestame.home", root);
        boolean system_home = EnMePlaceHolderConfigurer.getBooleanProperty("encuestame.system.home");
        log.debug("getRootDirectory-->:: " + root);
        if (root == null && system_home) {
            log.warn("home is not well defined, getting catalina home as default location");
            //  if the others possibilities fails, get the catalina home as default
            root = System.getProperty("catalina.base");
            log.debug("catalina home " + root);
            root = (root == null ? (root + "/encuestame-store") : null);
            log.debug("catalina home complete: " + root);
        }
        if (root == null) {
          return null;
        } else {
            if (!root.endsWith("/")) {
                root = root + "/";
            }
            log.info("getRootFolder:{" + root);
           return root;
        }
    }

    /**
     * Get picture directory real path.
     * @return picture path.
     * @throws EnmeFailOperation
     */
    public static String getPictureDirectory() throws EnmeFailOperation {
        final StringBuffer picture = new StringBuffer(getHomeDirectory());
        picture.append(DirectorySetupOperations.PICTURES_DEFAULT_FOLDER);
        log.debug("getPictureDirectory:{ " + picture);
        return picture.toString();
    }

    /**
     * Get profile directory path.
     * @return real path of directory path.
     * @throws EnmeFailOperation
     */
    public static String getProfilesDirectory() throws EnmeFailOperation {
        final StringBuffer profiles = new StringBuffer(getHomeDirectory());
        profiles.append(DirectorySetupOperations.PROFILES_DEFAULT_FOLDER);
        log.debug("getProfilesDirectory:{" + profiles);
        return profiles.toString();
    }

    /**
     *
     * @param accountId
     * @return
     * @throws EnmeFailOperation
     */
    public static String getProfilesDirectory(final String accountId) throws EnmeFailOperation {
        final StringBuilder path = new StringBuilder(DirectorySetupOperations.getProfilesDirectory());
        path.append("/");
        path.append(accountId.toString());
        path.append("/");
        log.debug("Path builded "+path.toString());
        return path.toString();
    }

    /**
     * Get indexes directory path.
     * @return indexed real path.
     * @throws EnmeFailOperation
     */
    public static String getIndexesDirectory() throws EnmeFailOperation {
        final String index = getHomeDirectory() + DirectorySetupOperations.INDEXES_DEFAULT_FOLDER;
        log.debug("getIndexesDirectory " + index);
        return index;
    }

    /**
     * Create required directory structure.
     * @param createIfNoExist flag that create the internal structure if does not exist
     * @throws EnmeFailOperation
     */
    public static void validateInternalStructureDirectory(final boolean createIfNoExist) throws EnmeFailOperation {
            log.debug("EnMe: validateInternalStructureDirectory." + createIfNoExist);
            if (!DirectorySetupOperations.checkIfIndexedDirectoryExist(createIfNoExist)) {
                log.debug("EnMe: index folder not found, creating one...");
            }
            if (!DirectorySetupOperations
                    .checkIfProfilesDirectoryExist(createIfNoExist)) {
                log.debug("EnMe: index folder not found, creating one...");
            }
            if (!DirectorySetupOperations
                    .checkIfPicturesDirectoryExist(createIfNoExist)) {
                log.debug("EnMe: index folder not found, creating one...");
            }
    }
}
